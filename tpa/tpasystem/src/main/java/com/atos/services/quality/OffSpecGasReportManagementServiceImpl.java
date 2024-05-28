package com.atos.services.quality;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ElementIdBean;
import com.atos.beans.LanguageBean;
import com.atos.beans.NotificationBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.UserBean;
import com.atos.beans.quality.OffSpecActionBean;
import com.atos.beans.quality.OffSpecActionFileBean;
import com.atos.beans.quality.OffSpecFileAttachBean;
import com.atos.beans.quality.OffSpecFileBean;
import com.atos.beans.quality.OffSpecGasQualityParameterBean;
import com.atos.beans.quality.OffSpecIncidentBean;
import com.atos.beans.quality.OffSpecResponseBean;
import com.atos.beans.quality.OffSpecStatusBean;
import com.atos.beans.quality.OffSpecStatusRuleBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.quality.OffSpecGasReportManagementFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.quality.OffSpecGasReportManagementMapper;
import com.atos.utils.Constants;

@Service("OSGRManagementService")
public class OffSpecGasReportManagementServiceImpl implements OffSpecGasReportManagementService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 228537544460524661L;
	
	@Autowired
	private OffSpecGasReportManagementMapper osgrmMapper;
	@Autowired
	private SystemParameterMapper systemParameterMapper;
	@Autowired
	private NotificationMapper notifMapper;
	
	private static final String strNotifOrigin = "QUALITY";
	private static final String strNotifSeparator = "~";
	private static final String strDefOperatorParamName = "DEFAULT.OPERATOR";
	private static final String strGeneratedNotificationTypeCode = "OFFSPEC.REPORTED";
	private static final String strRequestFlowTypeDesc = "Request";
	private static final String strEventFlowTypeDesc = "Event";
	
	// No se usan variables locales, porque el servicio tiene scope de singleton, por defecto. Se pasa por parametro desde la vista,
	// o se consulta a la BD.
	//private UserBean user;
    //private LanguageBean language;
	//private List<ComboFilterNS> allShippers = null;
	//private List<BigDecimal> allShipperIds = null;
	//private List<ComboFilterNS> allShippersForInsert = null;	
	//private List<ComboFilterNS> allQualityPoints = null;
	//private List<ComboFilterNS> allQualityPointsForInsert = null;
	
	private static final String strRequestGeneratedStatusCode = "RQ.GENERATED";
	private static final String strEventGeneratedStatusCode = "EV.GENERATED";
	//private BigDecimal generatedStatusId = null;

	public BigDecimal getDefaultOperatorId(UserBean _user, LanguageBean _lang) throws Exception{
		String defOperatorCode;
		BigDecimal defOperatorId = null;
		
		if(defOperatorId == null) {
			defOperatorCode = getSystemParameter(strDefOperatorParamName, _user, _lang);
			List<BigDecimal> lTmpIds = osgrmMapper.selectGroupIdFromGroupCode(defOperatorCode);
			defOperatorId = lTmpIds.get(0);
		}
		
		return defOperatorId;
	}
		
	public List<ComboFilterNS> selectIncidentTypes(){
		return osgrmMapper.selectIncidentTypes();	
	}
	
	public Map<BigDecimal, Object> selectQualityPoints(BigDecimal systemId){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();

		List<ComboFilterNS> allQualityPoints = osgrmMapper.selectQualityPoints(systemId);
		
		for (ComboFilterNS combo : allQualityPoints) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 	
	}

	public Map<BigDecimal, Object> selectQualityPointsForInsert(BigDecimal systemId){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();

		List<ComboFilterNS> allQualityPointsForInsert = osgrmMapper.selectQualityPointsForInsert(systemId);
		
		for (ComboFilterNS combo : allQualityPointsForInsert) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 	
	}
	
	private String getQualityPointCodeFromId(BigDecimal _pointId){
		return osgrmMapper.selectPointCodeFromId(_pointId);
	}
	
	public Map<BigDecimal, Object> selectShipperId(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();

		List<ComboFilterNS> allShippers = osgrmMapper.selectShipperId();
			
		for (ComboFilterNS combo : allShippers) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectShipperIdForInsert(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> allShippersForInsert = osgrmMapper.selectShipperIdForInsert();
			
		for (ComboFilterNS combo : allShippersForInsert) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}

	private List<BigDecimal> getAllShipperIdsForInsert() {
		
		List<ComboFilterNS> allShippers = osgrmMapper.selectShipperIdForInsert();
		ArrayList<BigDecimal> allShipperIds = new ArrayList<BigDecimal>();
		for(ComboFilterNS combo :allShippers)
			allShipperIds.add(combo.getKey());
		
		return allShipperIds;
	}
	
	public List<OffSpecStatusBean> selectStatusIds(OffSpecGasReportManagementFilter filter){
		return osgrmMapper.selectStatusIds(filter);
	}

	public List<OffSpecStatusRuleBean> selectStatusRules(String userId){
		return osgrmMapper.selectStatusRulesByUserId(userId);
	}
	
	public List<OffSpecGasQualityParameterBean> selectGasQualityParameters(){
		return osgrmMapper.selectGasQualityParameters();
	}
	
	public List<OffSpecIncidentBean> search(OffSpecGasReportManagementFilter filter,
											UserBean _user){
		
		List<OffSpecIncidentBean> tmpLIncidents = null;
		
		// Se crea un nuevo filtro, para anadir los % e invocar la consulta.
		OffSpecGasReportManagementFilter tmpFilter = new OffSpecGasReportManagementFilter(filter);
		String tmpIncidCode = filter.getIncidentCode();
		
		if((tmpIncidCode != null) && (!"".equalsIgnoreCase(tmpIncidCode)))
			tmpFilter.setIncidentCode("%" + tmpIncidCode + "%");

		// Se consultan las respuestas asociadas a cada incidencia.
		// Si el usuario no es operador, se filtran las respuestas segun el filtro de shipper.
		Map<String, BigDecimal> params = new HashMap<String, BigDecimal>();
		if(! Constants.OPERATOR.equalsIgnoreCase(_user.getUser_type())) 
			params.put("shipperId", filter.getShipperId());
		
		tmpLIncidents = osgrmMapper.selectIncidents(tmpFilter);
		if(tmpLIncidents!=null)
			for(OffSpecIncidentBean incid: tmpLIncidents) {
				params.put("incidentId", incid.getIncidentId());
				incid.setDiscloseResponses(osgrmMapper.selectDiscloseResponsesFromIncidentId(params));
				incid.setActionsFree(osgrmMapper.selectFreeActions(incid).stream().collect(
						Collectors.toMap(ComboFilterNS::getKey, ComboFilterNS::getValue, (e1, e2) -> e1, LinkedHashMap::new)));
			}
		
		return tmpLIncidents;
	}
	
	public void getFile(OffSpecIncidentBean incident) throws Exception{
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	// Solo se consulta el fichero 1 vez de base de datos. Si ya existe, no se consulta.
    	if( incident.getScFile() == null) {

    		BigDecimal tmpIncidentVersionId = incident.getIncidentVersionId();
			if(tmpIncidentVersionId == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));

    		List<OffSpecIncidentBean> lData = osgrmMapper.getFileByOffSpecLogId(tmpIncidentVersionId);
	
			if(lData == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));
			
			// Solo se va a tener un fichero por cada version-log de incidente.
			OffSpecIncidentBean osiBean = lData.get(0);
			if(osiBean == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));		
	
			byte[] ba = osiBean.getBinaryData();
			if(ba == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));		
			
			incident.setBinaryData(Arrays.copyOf(ba, ba.length));
    	}
    }
	

	public void getFileResponse(OffSpecResponseBean bean) throws Exception{
		// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	// Solo se consulta el fichero 1 vez de base de datos. Si ya existe, no se consulta.
    	if( bean.getScFile() == null) {

    		OffSpecResponseBean res = osgrmMapper.selectResponseFile(bean);
	
			if(res == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));
			
			byte[] ba = res.getAttachedFile();
			if(ba == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));		
			
			bean.setAttachedFile(Arrays.copyOf(ba, ba.length));
    	}
    }

	// Se anota aqui como transaccional porque Spring requiere que sea un metodo publico, expuesto en el interfaz.
	// https://docs.spring.io/spring/docs/current/spring-framework-reference/html/transaction.html
	@Transactional( rollbackFor = { Throwable.class })
	public void changeStatus(OffSpecIncidentBean _incid, UserBean _user, LanguageBean _lang) throws Exception {
		
		validateNewStatus(_incid);
		saveIncident(_incid, _user);
	}
	
	private void validateNewStatus(OffSpecIncidentBean _incid) throws ValidationException {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		String strRequiredFieldsNotInformed = null;
		
		if(_incid.getChosenNextStatusRule().shipperIsEditMandatory() && (_incid.getNewOriginatorShipperId() == null))
			strRequiredFieldsNotInformed = " " + msgs.getString("osgr_man_shipper");
		
		if(_incid.getChosenNextStatusRule().commentIsEditMandatory() && ("".equalsIgnoreCase(_incid.getNewComments()))) {
			strRequiredFieldsNotInformed = (strRequiredFieldsNotInformed == null)? " " : strRequiredFieldsNotInformed + ", ";
			strRequiredFieldsNotInformed += msgs.getString("osgr_man_comments");
		}
		
		if(_incid.getChosenNextStatusRule().fileIsEditMandatory() && (_incid.getNewUlFile().getSize() == 0)) {
			strRequiredFieldsNotInformed = (strRequiredFieldsNotInformed == null)? " " : strRequiredFieldsNotInformed + ", ";
			strRequiredFieldsNotInformed += msgs.getString("osgr_man_attached_file");
		}
		
		if(_incid.getChosenNextStatusRule().endDateIsEditMandatory() && (_incid.getNewEndDate() == null)) {
			strRequiredFieldsNotInformed = (strRequiredFieldsNotInformed == null)? " " : strRequiredFieldsNotInformed + ", ";
			strRequiredFieldsNotInformed += msgs.getString("osgr_man_end_date");
		}
		
		if(strRequiredFieldsNotInformed != null) {
			strRequiredFieldsNotInformed += ".";
			throw new ValidationException(msgs.getString("the_following_mandatory_fields_error") + " " + strRequiredFieldsNotInformed);
		}
		
		// Si se ha rellenado newEndDate se comprueba que sea mayor que startDate.
		if(_incid.getNewEndDate()!=null){
			if(_incid.getNewEndDate().before(_incid.getStartDate()))
				throw new ValidationException(msgs.getString("osgr_man_end_date_earlier_start_date_error"));
		}
		
    	// Se comprueba que no se haya cambiado el estado de la incidencia desde que se cargo el menu principal.
    	String msgNotFound = msgs.getString("osgr_man_off_specification_event") + " " + 
								msgs.getString("with_id") + " " + _incid.getIncidentCode() + " " + 
								msgs.getString("not_found") + ".";
    	List<OffSpecIncidentBean> tmpLIncid = osgrmMapper.selectIncidentFromId(_incid.getIncidentId());
    	OffSpecIncidentBean currIncident = null;
    	if(tmpLIncid==null)
    		throw new ValidationException(msgNotFound);
    	
    	currIncident = tmpLIncid.get(0);
    	if(currIncident==null)
    		throw new ValidationException(msgNotFound);
    	
    	if(currIncident.getStatusId().compareTo(_incid.getStatusId()) != 0)
    		throw new ValidationException(msgs.getString("osgr_man_status_already_changed_error"));
	}
		
    private void saveIncident(OffSpecIncidentBean _incid, UserBean _user) throws Exception {
		// Se actualiza la cabecera de la incidencia off-spec.
		int res = osgrmMapper.updateOffSpecIncident(_incid);
		if(res!=1){
    		throw new Exception("Error inserting into Off Specification Event table.");   		
    	}      		
		
		_incid.setUserId(_user.getIdn_user());
		_incid.setCommentsShipper(_incid.getInitialComments());
		_incid.setCommentsOperator(_incid.getOperatorComments());
		
		// Se inserta una nueva version en la tabla de detalle (log).
		res = osgrmMapper.insertOffSpecIncidentLog(_incid);
		if(res!=1){
    		throw new Exception("Error inserting into Off Specification Event Log table.");   		
    	}

		if(_incid.getChosenNextStatusRule().getNextStatusCode().equals("EV.ACCEPTED - CLOSED")) {
			//Insertamos los ficheros
			for(OffSpecActionFileBean item : _incid.getFilesAction()) {
				OffSpecFileBean file = new OffSpecFileBean(item.getFileName(), item.getBinaryData(), item.getUserName());
				osgrmMapper.insertFileNewEvent(file);
				OffSpecFileAttachBean fileAttach = new OffSpecFileAttachBean(_incid.getIncidentId(), file.getIdnOffspecFile(),
									_incid.getChosenNextStatusRule().getNextStatusId(), _user.getUsername());
				osgrmMapper.insertFileAttachNewEvent(fileAttach);
			}
		}
    }
    
    @Override
    public Integer saveAction(OffSpecIncidentBean _incid, UserBean _user, boolean isShipper) throws Exception {
    	//saveIncident(_incid, _user);
    	int res = 1;
		for(int i = 0; i < _incid.getMultiShippers().size(); i++) {
			Object idnShipperObject = _incid.getMultiShippers().get(i);
			BigDecimal idnShipper = new BigDecimal(String.valueOf(idnShipperObject));
			
			OffSpecResponseBean osResponse  = new OffSpecResponseBean();
			//Cargamos los comentarios en sus respectivos campos dependiendo si es operator o shipper
    		if(isShipper) {
    			osResponse.setUserComments(_incid.getNewComments());
    		}else {
    			osResponse.setOperatorComments(_incid.getNewComments());
    		}
			osResponse.setIncidentId(_incid.getIncidentId());
			osResponse.setGroupId(idnShipper);
			osResponse.setIsResponded(OffSpecResponseBean.isRespondedNo);
			osResponse.setUserId(_user.getIdn_user());
			osResponse.setIdnAction(_incid.getIdnAction());
			
			// Se inserta un registro en la tabla de respuestas para el disclose.
			res = osgrmMapper.insertOffSpecResponse(osResponse);
			if(res!=1){
	    		throw new Exception("Error inserting into Off Specification Event Response table.");   		
	    	}
			res = osgrmMapper.updateActionOffspec(_incid);
			if(res!=1){
	    		throw new Exception("Error when changing action in " + _incid.getIncidentCode());   		
	    	}
		}	
		//Insertamos los ficheros
		for(OffSpecActionFileBean item : _incid.getFilesAction()) {
			osgrmMapper.insertFileAction(item);
		}
		return res;
    }
	
	/*
	 * private void discloseIncident(OffSpecIncidentBean _incid, BigDecimal
	 * _receiverShipperId, UserBean _user) throws Exception {
	 * 
	 * OffSpecResponseBean osResponse = new OffSpecResponseBean();
	 * 
	 * osResponse.setIncidentId(_incid.getIncidentId());
	 * osResponse.setGroupId(_receiverShipperId);
	 * osResponse.setIsResponded(OffSpecResponseBean.isRespondedNo);
	 * osResponse.setUserId(_user.getIdn_user());
	 * 
	 * // Se inserta un registro en la tabla de respuestas para el disclose. int res
	 * = osgrmMapper.insertOffSpecResponse(osResponse); if(res!=1){ throw new
	 * Exception("Error inserting into Off Specification Event Response table."); }
	 * }
	 */
	
	private void cleanIncident(OffSpecIncidentBean _incid) throws Exception {
		// Una vez actualizadas las tablas, se podrian actualizar los datos fijos del bean, con los nuevos.
		// Por ejemplo:
		// _incid.setStatusId(_incid.getNewStatusId());
		// Pero la fecha lastModifiedDate corresponde al campo de auditoria aud_last_date que se autogenera, de manera que hay que volver a consultar 
		// todos los datos de la tabla.
		// No merece la pena limpiar los datos nuevos generados por el cambio de estado, porque al repetirse la consulta, los beans de incidencias se crean desde cero.
		/*_incid.setChosenNextStatusRule(null);
		_incid.setNewStatusId(null);
		_incid.setNewOriginatorShipperId(null);
		_incid.setNewComments(null);
		_incid.setNewUlFile(null);
		_incid.setNewEndDate(null);*/
	}
	
	public void sendNewStatusNotification(OffSpecIncidentBean _incid, UserBean _user, LanguageBean _lang, BigDecimal _systemId) throws Exception {

		int res = 0;
    	List<BigDecimal> lNotifRecipients = null;
    	
		NotificationBean notif = new NotificationBean();
		notif.setType_code(_incid.getChosenNextStatusRule().getNotificationTypeCode());
		notif.setSystemId(_systemId);
		notif.setOrigin(strNotifOrigin);
		String info = _incid.getQualityPointCode() + strNotifSeparator +
						_incid.getNewIncidentTypeDesc() + strNotifSeparator +
						_incid.getIncidentCode();
		notif.setInformation(info);
		notif.setUser_id(_user.getUsername());
		notif.setLanguage(_lang.getLocale());
		
		BigDecimal origShipperId = _incid.getNewOriginatorShipperId();
		// Si no se ha rellenado el shipper en la ultima pantalla, se toma el que hubiera antes.
		if( origShipperId == null)
			origShipperId = _incid.getOriginatorShipperId();
		lNotifRecipients = getNotifRecipients(_incid.getChosenNextStatusRule().getNotificationMode(), 
												origShipperId,
												_user,
												_lang);
		
		for(BigDecimal recipient :lNotifRecipients) {
			
			notif.setIdn_user_group(recipient);
		
			notifMapper.getCreateNotification(notif);
			if( notif==null || notif.getInteger_exit()==null ){
				// Se envia error tecnico para no mostrar error al usuario.
				throw new Exception("Error sending notification.");
			}
			else {
				// Se envia error tecnico para no mostrar error al usuario.
				res = notif.getInteger_exit().intValue();
				if( res != 0)
					throw new Exception("Error sending notification.");			
			}
		}
	}

	public List<BigDecimal> getNotifRecipients(String _notifMode, 
												BigDecimal _origShipperId,
												UserBean _user,
												LanguageBean _lang) throws Exception {
		
		List<BigDecimal> lRecipients = new ArrayList<BigDecimal>();
		
		if(OffSpecStatusRuleBean.originatorShipperNotifMode.equalsIgnoreCase(_notifMode)) {
			if(_origShipperId != null)
				lRecipients.add(_origShipperId);
			else
				throw new Exception("originatorShipper not available.");
			
		} else if(OffSpecStatusRuleBean.defaultOperatorNotifMode.equalsIgnoreCase(_notifMode)) {
			lRecipients.add(getDefaultOperatorId(_user, _lang));
			
		} else if(OffSpecStatusRuleBean.otherShippersNotifMode.equalsIgnoreCase(_notifMode)) {
			
			List<BigDecimal>allShipperIds = getAllShipperIdsForInsert();
			for(BigDecimal bdShipperId :allShipperIds){
				
				if((_origShipperId != null) && (bdShipperId.compareTo(_origShipperId) == 0))
					continue;
				
				lRecipients.add(bdShipperId);
			}
			
		} else if(OffSpecStatusRuleBean.allShippersNotifMode.equalsIgnoreCase(_notifMode)) {
			
			lRecipients = getAllShipperIdsForInsert();
			
		} else if(OffSpecStatusRuleBean.noneNotifMode.equalsIgnoreCase(_notifMode)) {
			// La NotifMode NONE, no deberia darse aqui, porque se ha probado previamente
			// que la notification_type no sea nula.
			// En cualquier caso, el resultado de buscar receptores para este tipo de 
			// notificacion es una lista vacia.			
		} else
			throw new Exception("Invalid Notification mode: " + _notifMode + ".");
		
		return lRecipients;
	}
	
	private List<BigDecimal> getEmailRecipients(String _emailMode, BigDecimal _origShipperId, UserBean _user,
			LanguageBean _lang) throws Exception {

		List<BigDecimal> lRecipients = new ArrayList<BigDecimal>();

		if (OffSpecStatusRuleBean.originatorShipperNotifMode.equalsIgnoreCase(_emailMode)) {
			if (_origShipperId != null)
				lRecipients.add(_origShipperId);
			else
				throw new Exception("originatorShipper not available.");

		} else if (OffSpecStatusRuleBean.defaultOperatorNotifMode.equalsIgnoreCase(_emailMode)) {
			lRecipients.add(getDefaultOperatorId(_user, _lang));

		} else if (OffSpecStatusRuleBean.otherShippersNotifMode.equalsIgnoreCase(_emailMode)) {

			List<BigDecimal> allShipperIds = getAllShipperIdsForInsert();
			for (BigDecimal bdShipperId : allShipperIds) {

				if ((_origShipperId != null) && (bdShipperId.compareTo(_origShipperId) == 0))
					continue;

				lRecipients.add(bdShipperId);
			}

		} else if (OffSpecStatusRuleBean.allShippersNotifMode.equalsIgnoreCase(_emailMode)) {

			lRecipients = getAllShipperIdsForInsert();

		} else if (OffSpecStatusRuleBean.noneNotifMode.equalsIgnoreCase(_emailMode)) {
		// La EmailMode NONE, no deberia darse aqui, porque se ha probado previamente
		// que el email_type no sea nula.
		// En cualquier caso, el resultado de buscar receptores para este tipo de 
		// notificacion es una lista vacia.			
				} else
			throw new Exception("Invalid Notification mode: " + _emailMode + ".");

		return lRecipients;
	}
	
	private String getSystemParameter(String str, UserBean _user, LanguageBean _lang) throws Exception{
		String resParam = null;
		
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(systemParameterMapper.getSysdate().get(0));
		bean.setParameter_name(str);
		bean.setUser_id(_user.getUsername());
		bean.setLanguage(_lang.getLocale());
		systemParameterMapper.getStringSystemParameter(bean);
		if( bean==null || bean.getString_exit()==null ){
			// Se envia error tecnico para no mostrar error al usuario.
			throw new Exception("Error getting system parameter.");
		}
		resParam = bean.getString_exit();
	
		return resParam;
	}
	
	// Se anota aqui como transaccional porque Spring requiere que sea un metodo publico, expuesto en el interfaz.
	// https://docs.spring.io/spring/docs/current/spring-framework-reference/html/transaction.html
	@Transactional( rollbackFor = { Throwable.class })
	public void createRequest(OffSpecIncidentBean _incid, UserBean _user, LanguageBean _lang) throws Exception {
		
		validateNewRequest(_incid);
		saveNewRequest(_incid, _user, _lang);
	}

	// Se anota aqui como transaccional porque Spring requiere que sea un metodo publico, expuesto en el interfaz.
	// https://docs.spring.io/spring/docs/current/spring-framework-reference/html/transaction.html
	@Transactional( rollbackFor = { Throwable.class })
	public void createEvent(OffSpecIncidentBean _incid, UserBean _user, LanguageBean _lang) throws Exception {
		
		validateNewEvent(_incid);
		saveNewEvent(_incid, _user, _lang);
	}
	
	private void validateNewRequest(OffSpecIncidentBean _incid) throws ValidationException {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	if(_incid.getStartDate()==null) 
    		throw new ValidationException(msgs.getString("osgr_man_required_start_date_error"));
    	
    	if(_incid.getEndDate()==null) 
    		throw new ValidationException(msgs.getString("osgr_man_required_end_date_error"));
    	
    	if(_incid.getOriginatorShipperId()==null)
    		throw new ValidationException(msgs.getString("osgr_man_required_orig_shipper_error"));
    	
    	if(_incid.getQualityPointId()==null)
    		throw new ValidationException(msgs.getString("osgr_man_required_quality_point_error"));
    	
		Calendar tmpYesterday = Calendar.getInstance();
		tmpYesterday.set(Calendar.HOUR_OF_DAY, 0);
		tmpYesterday.set(Calendar.MINUTE, 0);
		tmpYesterday.set(Calendar.SECOND, 0);
		tmpYesterday.set(Calendar.MILLISECOND, 0);
		tmpYesterday.add(Calendar.DAY_OF_MONTH, -1);
    	if(_incid.getStartDate().before(tmpYesterday.getTime()))
    		throw new ValidationException(msgs.getString("osgr_man_start_date_future_error"));    		
		
    	if(_incid.getStartDate().after(_incid.getEndDate()))
    		throw new ValidationException(msgs.getString("osgr_man_end_date_earlier_start_date_error"));
    	
    	boolean foundValue = false;
    	for(OffSpecGasQualityParameterBean param :_incid.getGasParams()) {
    		if(param.getValue()!= null) {
    			foundValue = true;
    			break;
    		}
    	}
    	if(! foundValue)
    		throw new ValidationException(msgs.getString("osgr_man_param_one_error"));
	}
	
	private void validateNewEvent(OffSpecIncidentBean _incid) throws ValidationException {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	if(_incid.getStartDate()==null) 
    		throw new ValidationException(msgs.getString("osgr_man_required_start_date_error"));
    	
    	if(_incid.getQualityPointId()==null)
    		throw new ValidationException(msgs.getString("osgr_man_required_quality_point_error"));

		Calendar tmpYesterday = Calendar.getInstance();
		tmpYesterday.set(Calendar.HOUR_OF_DAY, 0);
		tmpYesterday.set(Calendar.MINUTE, 0);
		tmpYesterday.set(Calendar.SECOND, 0);
		tmpYesterday.set(Calendar.MILLISECOND, 0);
		tmpYesterday.add(Calendar.DAY_OF_MONTH, -1);   		
		
    	boolean foundValue = false;
    	for(OffSpecGasQualityParameterBean param :_incid.getGasParams()) {
    		if(param.getValue()!= null) {
    			foundValue = true;
    			break;
    		}
    	}
    	if(! foundValue)
    		throw new ValidationException(msgs.getString("osgr_man_param_one_error"));
	}
	
    private void saveNewRequest(OffSpecIncidentBean _incid, UserBean _user, LanguageBean _lang) throws Exception {
		
		BigDecimal generatedStatusId = null;
		
		List<BigDecimal> tmpLStatusId = osgrmMapper.selectStatusIdFromStatusCode(strRequestGeneratedStatusCode);
		if(tmpLStatusId!=null)
				generatedStatusId = tmpLStatusId.get(0);
			else
				throw new Exception("GENERATED status not found in database.");
		
		_incid.setStatusId(generatedStatusId);
		// Para reutilizar la query de mybatis, se rellenan los campos new.
		_incid.setNewStatusId(generatedStatusId);
		
		saveNewIncident(_incid, _user, _lang);
	}

    private void saveNewEvent(OffSpecIncidentBean _incid, UserBean _user, LanguageBean _lang) throws Exception {
		BigDecimal generatedStatusId = null;
		
		List<BigDecimal> tmpLStatusId = osgrmMapper.selectStatusIdFromStatusCode(strEventGeneratedStatusCode);
		if(tmpLStatusId!=null)
				generatedStatusId = tmpLStatusId.get(0);
			else
				throw new Exception("GENERATED status not found in database.");
		
		_incid.setStatusId(generatedStatusId);
		// Para reutilizar la query de mybatis, se rellenan los campos new.
		_incid.setNewStatusId(generatedStatusId);
		
		saveNewIncident(_incid, _user, _lang);
	}
			
    private void saveNewIncident(OffSpecIncidentBean _incid, UserBean _user, LanguageBean _lang) throws Exception {
	
		_incid.setIncidentCode(getNewIncidentCode(_user, _lang));
		_incid.setUserId(_user.getIdn_user());
		_incid.setOriginatorShipperId(_user.getIdn_user_group());
		_incid.setOriginatorShipperCode(_user.getUser_group_id());
		//_incid.setGroupId(_user.getIdn_user_group());

		// Para reutilizar la query de mybatis, se rellenan los campos new.
		//_incid.setNewComments(_incid.getComments());
		
		// Se inserta la cabecera de la incidencia off-spec.
		int res = osgrmMapper.insertOffSpecIncident(_incid);
		if(res!=1)
    		throw new Exception("Error inserting into Off Specification Event table.");
		
		// Se inserta una nueva version en la tabla de detalle (log).
		res = osgrmMapper.insertOffSpecIncidentLog(_incid);
		if(res!=1)
    		throw new Exception("Error inserting into Off Specification Event Log table.");

		// Se insertan los parametros de calidad a los que se haya dado valor en la pantalla.
		for(OffSpecGasQualityParameterBean qParam: _incid.getGasParams()){
			if(qParam.getValue()!=null){
				qParam.setIncidentId(_incid.getIncidentId());
				res = osgrmMapper.insertGasQualityParameter(qParam);
				
				if(res!=1)
					throw new Exception("Error inserting into Off Specification Event Gas Quality table.");   		
			}
		}
		
		//Insertamos los ficheros
		for(OffSpecFileBean item : _incid.getFiles()) {
			osgrmMapper.insertFileNewEvent(item);
			OffSpecFileAttachBean fileAttach = new OffSpecFileAttachBean(_incid.getIncidentId(), item.getIdnOffspecFile(), null, _user.getUsername());
			osgrmMapper.insertFileAttachNewEvent(fileAttach);
		}
    }
	
	private String getNewIncidentCode(UserBean _user, LanguageBean _lang) throws Exception {
		
		ElementIdBean tmpBean = new ElementIdBean();
		tmpBean.setGenerationCode(Constants.OFFSPEC_GAS);
		// Si se deja la fecha a nulo, en BD se toma systemdate.
		tmpBean.setDate(null);
		tmpBean.setUser(_user.getUsername());
		tmpBean.setLanguage(_lang.getLocale());

		systemParameterMapper.getElementId(tmpBean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(tmpBean == null || (tmpBean.getIntegerExit() != 0))
			throw new Exception(tmpBean.getErrorDesc());
		
		return tmpBean.getId();
	}
	
	public void sendNewRequestNotification(OffSpecIncidentBean _incid, UserBean _user, LanguageBean _lang, BigDecimal _systemId) throws Exception {
		_incid.setIncidentTypeDesc(strRequestFlowTypeDesc);
	    sendNewIncidentNotification(_incid, _user, _lang, _systemId);
	}

	public void sendNewEventNotification(OffSpecIncidentBean _incid, UserBean _user, LanguageBean _lang, BigDecimal _systemId) throws Exception {
		_incid.setIncidentTypeDesc(strEventFlowTypeDesc);
	    sendNewIncidentNotification(_incid, _user, _lang, _systemId);
	}
	
	private void sendNewIncidentNotification(OffSpecIncidentBean _incid, UserBean _user, LanguageBean _lang, BigDecimal _systemId) throws Exception {
    	int res = 0;
    	
		// Se genera la notificacion para el operador por defecto.
		NotificationBean notif = new NotificationBean();
		notif.setType_code(strGeneratedNotificationTypeCode);
		notif.setSystemId(_systemId);
		notif.setOrigin(strNotifOrigin);
		String info = getQualityPointCodeFromId(_incid.getQualityPointId()) + strNotifSeparator +
						_incid.getIncidentTypeDesc() + strNotifSeparator +
						_incid.getIncidentCode();
		notif.setInformation(info);
		notif.setUser_id(_user.getUsername());
		notif.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		notif.setIdn_user_group(getDefaultOperatorId(_user, _lang));
		
		notifMapper.getCreateNotification(notif);
		if( notif==null || notif.getInteger_exit()==null ){
			// Se envia error tecnico para no mostrar error al usuario.
			throw new Exception("Error sending notification.");
		}
		else {
			// Se envia error tecnico para no mostrar error al usuario.
			res = notif.getInteger_exit().intValue();
			if( res != 0)
				throw new Exception("Error sending notification.");			
		}
	}

	//CH706
	@Transactional(rollbackFor = { Throwable.class })
	public String updateTransporterComments(OffSpecResponseBean response) throws Exception {
		int upd1 = osgrmMapper.updateTransporterComments(response);
		if (upd1 != 1) {
			throw new Exception("-1");
		}

		

		return "0";
		
	}
	
	public String getZoneCode(OffSpecIncidentBean incident) {
		return osgrmMapper.getZoneCode(incident);
	}

	@Override
	public List<OffSpecActionBean> selectAllActions() {
		return osgrmMapper.selectAllActions();
	}

	@Override
	public List<OffSpecFileBean> selectFiles(OffSpecIncidentBean item, String statusCode, String userGroupType) {
		Integer idnStatusCode = null;
		if(userGroupType == null){
			idnStatusCode = item.getStatusId().intValue();
		}
		return osgrmMapper.selectFiles(item.getIncidentId(), idnStatusCode, userGroupType);
	}

	@Override
	public String selectCommentsShipperOperator(OffSpecIncidentBean item) {
		return osgrmMapper.selectCommentsShipperOperator(item);
	}

	@Override
	public List<OffSpecActionFileBean> selectActionFiles(OffSpecResponseBean item) {
		return osgrmMapper.selectActionFiles(item);
	}

	@Override
	public Integer acceptRejectAction(OffSpecIncidentBean _incid, String responseValue, UserBean _user) throws Exception {
		OffSpecResponseBean response = new  OffSpecResponseBean(_incid.getIncidentId(), _incid.getMultiShippers().get(0), OffSpecResponseBean.isRespondedYes, 
				responseValue, new Date(), _incid.getNewComments(), _user.getIdn_user());
		
		// Se inserta un registro en la tabla de respuestas para el disclose.
		int res = osgrmMapper.insertOffSpecResponse(response);
		if(res!=1){
    		throw new Exception("Error inserting into Off Specification Event Response table.");   		
    	}
		res = osgrmMapper.updateActionOffspec(_incid);
		if(res!=1){
    		throw new Exception("Error when changing action in " + _incid.getIncidentCode());   		
    	}
		//Insertamos los ficheros
		for(OffSpecActionFileBean item : _incid.getFilesAction()) {
			osgrmMapper.insertFileAction(item);
		}
		return res;
	}

	@Override
	public OffSpecIncidentBean selectInfoStatusAcceptedClosed(OffSpecIncidentBean item) {
		return osgrmMapper.selectInfoStatusAcceptedClosed(item);
	}
}
