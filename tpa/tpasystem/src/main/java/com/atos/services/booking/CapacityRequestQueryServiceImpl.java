package com.atos.services.booking;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import java.math.BigDecimal;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.NotificationBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.booking.CapacityRequestBean;
import com.atos.beans.booking.ContractAttachTypeBean;
import com.atos.beans.booking.ContractAttachmentBean;
import com.atos.beans.booking.ContractRejectedPointBean;
import com.atos.beans.booking.OperationFileBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.booking.CRManagementFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.booking.CapacityRequestMapper;
import com.atos.utils.Constants;

import org.apache.shiro.SecurityUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("CRQueryService")
public class CapacityRequestQueryServiceImpl implements CapacityRequestQueryService{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3387425404695877624L;
	private static final String strNotifTypeCodeToOperator = "CAPACITY.REQUEST.DELETED";
	private static final String strNotifTypeCodeToShipper = "CONTRACT.CAP_REQUEST_MANAGED";
	private static final String strNotifOrigin = "CONTRACT";
	
	@Autowired
	private CapacityRequestMapper crMapper;
	@Autowired
	private SystemParameterMapper systemParameterMapper;
	@Autowired
	private NotificationMapper notifMapper;	
	
	private List<ComboFilterNS> getLOperators() {
		return crMapper.selectOperators();
	}

	public Map<BigDecimal, Object> selectShipperId(){
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
			List<ComboFilterNS> list = crMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}


	public Map<BigDecimal, Object> selectContractTypes(){
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
			List<ComboFilterNS> list = crMapper.selectContractTypes();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}	


	public List<CapacityRequestBean> search(CRManagementFilter filter){
		
		// Se crea un nuevo filtro, para anadir los % e invocar la consulta.
		CRManagementFilter tmpFilter = new CRManagementFilter(filter);
		String tmpCapReqCode = filter.getCapacityRequestCode();
		
		if((tmpCapReqCode != null) && (!"".equalsIgnoreCase(tmpCapReqCode)))
			tmpFilter.setCapacityRequestCode("%" + filter.getCapacityRequestCode() + "%");
		
		return crMapper.selectCapacityRequests(tmpFilter);
	}


	public void selectRejectedPoints(CapacityRequestBean cr) {
		
		cr.setRejectedPointCodes(crMapper.selectRejectedPoints(cr));
	}


	public void selectRequestedPoints(CapacityRequestBean cr){
 		HashMap<BigDecimal, String> beanMap = new HashMap<BigDecimal, String>();
 		List<String> beanList = new ArrayList<String>();
 		
 		List<ComboFilterNS> comboList = crMapper.selectRequestedPoints(cr);
		for (ComboFilterNS combo : comboList) {
			if (combo == null) continue;
			beanMap.put(combo.getKey(), combo.getValue());
			beanList.add(combo.getValue());
		}
		cr.setHmRequestedPoints(beanMap);
		cr.setRequestedPointCodes(beanList);
	}

	public SystemParameterBean getSystemParameter(String str){
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(systemParameterMapper.getSysdate().get(0));
		bean.setParameter_name(str);
		bean.setUser_id((String)SecurityUtils.getSubject().getPrincipal());
		bean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		systemParameterMapper.getIntegerSystemParameter(bean);
		return bean;
	}

	public void getFileByOpFileId(CapacityRequestBean cr) throws Exception{
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		BigDecimal tmpIdnOperationFile = cr.getIdnOperationFile();		
		if(tmpIdnOperationFile == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));
		
		List<OperationFileBean> lData = crMapper.getFileByOpFileId(tmpIdnOperationFile);
		if(lData == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));
		
		// Solo se va a tener un fichero por cada capacity request.
		OperationFileBean tmpOfBean = lData.get(0);
		if(tmpOfBean == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));		

		byte[] ba = tmpOfBean.getBinaryData();
		if(ba == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));		
		
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);
		DefaultStreamedContent tmpSCont = 
				new DefaultStreamedContent(bais, 
											"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", 
											cr.getXlsFileName());
		cr.setXlsFile(tmpSCont);
	}


	// Se anota aqui como transaccional porque Spring requiere que sea un metodo publico, expuesto en el interfaz.
	// https://docs.spring.io/spring/docs/current/spring-framework-reference/html/transaction.html
	// Anteriormente las transacciones se hacian mas internamente; mover la transaccion aqui es equivalente porque no se pueden producir
	// excepciones despues de las transacciones que habia hasta ahora, que provoquen nuevos rollbacks no previstos hasta ahora.	
	@Transactional( rollbackFor = { Throwable.class })
    public void changeStatusCapacityRequest(String _action, CapacityRequestBean _crbSelected) throws Exception {
        
    	if( CapacityRequestQueryService.Reject_Tech_Shipper.equalsIgnoreCase(_action) ||
    			CapacityRequestQueryService.Reject_Tech_Operator.equalsIgnoreCase(_action) )
    		rejectTechCapacityRequest(_action, _crbSelected);
    	else
    		throw new Exception("Wrong Action to change Capacity Request: " + _action);
    }


    private void rejectTechCapacityRequest(String _action, CapacityRequestBean _crbSelected) throws Exception {
        
    	validateRejectTechCapacityRequest(_crbSelected);
    	saveRejectTechCapacityRequest(_action, _crbSelected);
    }	


    private void validateRejectTechCapacityRequest(CapacityRequestBean _crbSelected) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	if(_crbSelected.getDualListPointCodes().getTarget().isEmpty())
    		throw new ValidationException(msgs.getString("cr_man_rejected_points") + ": " + msgs.getString("empty_field"));
    }

    // Se mueve la anotacion mas arriba, porque Spring requiere que sea un metodo publico, expuesto en el interfaz.
	//@Transactional( rollbackFor = { Throwable.class })
    private void saveRejectTechCapacityRequest(String _action, CapacityRequestBean _crbSelected) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	int res = 0;

    	try {	    	
	    	ContractRejectedPointBean tmpCrpb = null;
	    	BigDecimal bdTmpPointId = null;
	    	HashMap<BigDecimal, String> hmPoints = _crbSelected.getHmRequestedPoints();
	    	List<String> tmpList = _crbSelected.getRejectedPointCodes();
			for (String strRejectedPointCode : tmpList) {
				if (strRejectedPointCode == null) continue;
				
				bdTmpPointId = (BigDecimal) getKeyFromValue(hmPoints, strRejectedPointCode);
				
				tmpCrpb = new ContractRejectedPointBean();
				tmpCrpb.setContractRequestId( _crbSelected.getId());
				tmpCrpb.setSystemPointId(bdTmpPointId);
				
				res = crMapper.insertRejectedPoint(tmpCrpb);
		    	if(res!=1){
		    		throw new Exception(msgs.getString("insert_error") + " " + msgs.getString("cr_man_rejected_point"));    		
		    	}	    	
			}


	    	if( CapacityRequestQueryService.Reject_Tech_Shipper.equalsIgnoreCase(_action) )
		    	_crbSelected.setStatus(Constants.SHIPPER_REJECTED);
	    	else if ( CapacityRequestQueryService.Reject_Tech_Operator.equalsIgnoreCase(_action) )
		    	_crbSelected.setStatus(Constants.PTT_REJECTED);
	    	else
	    		throw new Exception("Wrong Action to change Capacity Request: " + _action);

	    	res = crMapper.updateCRReject(_crbSelected);
	    	if(res!=1){
	    		throw new Exception(msgs.getString("update_error") + " " + msgs.getString("cr_man_capacity_request"));    		
	    	}

	    	// Si la reject la ha hecho un shipper, se envia notificacion a todos los user_groups operadores.
	    	// Si la reject la ha hecho un operador, se envia notificacion al shipper asociado a la request.
	    	if( CapacityRequestQueryService.Reject_Tech_Shipper.equalsIgnoreCase(_action) )
	    		sendNotificationToOperators(_crbSelected);
	    	else if ( CapacityRequestQueryService.Reject_Tech_Operator.equalsIgnoreCase(_action) )
	    		sendNotificationToShipper(_crbSelected);			
    	}
	    catch (Throwable t){
	    	// Si hubiera algun problema al actualizar la Capacity Request, vuelvo a dejar el bean en el estado anterior,
	    	// para que la vista este alineada con la BD. 
	    	// Al relanzar la excepcion, la transaccion hara rollback y no se guardaran cambios en BD.
	    	_crbSelected.setStatus(Constants.SUBMITTED);
	    	_crbSelected.getRequestedPointCodes().clear();
	    	_crbSelected.getRejectedPointCodes().clear();
	    	_crbSelected.getDualListPointCodes().setSource(_crbSelected.getRequestedPointCodes());
	    	_crbSelected.getDualListPointCodes().setTarget(_crbSelected.getRejectedPointCodes());
	    	_crbSelected.getHmRequestedPoints().clear();
	    	_crbSelected.setManagementComments(null);
	    	throw t;
	    }
    
    }


	private void sendNotificationToOperators(CapacityRequestBean _crbSelected) throws Exception {
		List<ComboFilterNS> tmpLOperators = getLOperators();
		
		if(_crbSelected != null && tmpLOperators != null) {
			for(int i=0; i<tmpLOperators.size(); i++) {
				if (_crbSelected.getId() != null && tmpLOperators.get(i) != null)
					sendNotification(strNotifTypeCodeToOperator, 
										_crbSelected.getRequestCode(), 
										tmpLOperators.get(i).getKey(),
										_crbSelected.getIdn_system());
			}
		}
		
	}


	private void sendNotificationToShipper(CapacityRequestBean _crbSelected) throws Exception {
		if(_crbSelected != null)
			sendNotification(strNotifTypeCodeToShipper, 
								_crbSelected.getRequestCode(),
								_crbSelected.getShipperId(),
								_crbSelected.getIdn_system());
	}


	private void sendNotification(String _notifTypeCode, String _info, BigDecimal _userGroupId, BigDecimal _systemId) throws Exception {		
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	int res = 0;
   	
		// Se genera la notificacion para el shipper, correspondiente a que el operador ha procesado su peticion de contrato.
		NotificationBean notif = new NotificationBean();
		notif.setType_code(_notifTypeCode);
		notif.setSystemId(_systemId);
		notif.setOrigin(strNotifOrigin);
		// Para un mensaje como "Capacity request {1} has been managed by operator"
		// se pasa como parametro el id de Capapacity Request, unicamente.
		notif.setInformation(_info);
		notif.setIdn_user_group(_userGroupId);
		notif.setUser_id((String)SecurityUtils.getSubject().getPrincipal());
		notif.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		
		notifMapper.getCreateNotification(notif);
		if( notif==null || notif.getInteger_exit()==null ){
			throw new Exception(msgs.getString("error_sending_notification_to_shipper"));
		}
		else {
			// En caso de error funcional, el procedimiento devuelve un codigo de error mayor o igual a 1000 y 
			// se devuelve una ValidationException (funcional). Esta excepcion se pintara en la ventana de mensajes al usuario.
			// En caso de error tecnico, el procedimiento devuelve un codigo de error menor que 1000 y distinto de cero.
			// se devuelve una Exception normal (error tecnico). En la ventana de mensajes al usuario se muestra un 
			// "error interno" y los detalles se llevan al log.
			res = notif.getInteger_exit().intValue();
			if( res != 0) {
				if( res >= 1000 )	// Errores funcionales.
		    		throw new ValidationException(notif.getError_desc());
				else				// Errores tecnicos.
		    		throw new Exception(notif.getError_desc());
			}				
		}
	}


	public List<ContractAttachTypeBean> selectContractAttachTypes(){
		
		return crMapper.selectContractAttachTypes();
	}


	public void selectAdditionalDocs(CapacityRequestBean cr) {
		
		// Cada vez que se consulta desde la vista, se consulta la BD,
		// para que se vaya actualizando la vista, en caso de que se guarden
		// docuementos nuevos, o se borren.
		cr.setAdditionalDocs(crMapper.selectAdditionalDocs(cr.getId()));
	}


	public void selectAdditionalDocsBankGuarantee(CapacityRequestBean cr) {
		
		// Cada vez que se consulta desde la vista, se consulta la BD,
		// para que se vaya actualizando la vista, en caso de que se guarden
		// docuementos nuevos, o se borren.
		cr.setAdditionalDocs(crMapper.selectAdditionalDocsBankGuarantee(cr.getId()));
	}


	public void insertContractAttachment(CapacityRequestBean _selectedCapReq, ContractAttachmentBean _newDoc) throws Exception {
		
		validateInsertContractAttachment(_selectedCapReq, _newDoc);
		crMapper.insertContractAttachment(_newDoc);
	}

	
	private void validateInsertContractAttachment(CapacityRequestBean _selectedCapReq, ContractAttachmentBean _newDoc) 
		throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
			
		// 1.- Se ha de comprobar que con el nuevo documento, no se supera el numero
		// maximo de documentos de su tipo.
		BigDecimal bdTipoDocNewDoc = _newDoc.getContractAttachTypeId();
		int iMaxNumberTipoDocNewDoc = 0;
		List<ContractAttachTypeBean> lDocTypes = crMapper.selectContractAttachTypes();
		if( lDocTypes != null ) {
			for(int i=0; i<lDocTypes.size(); i++) {
				if(lDocTypes.get(i) != null &&
					lDocTypes.get(i).getContractAttachTypeId() != null) {
	
					if(lDocTypes.get(i).getContractAttachTypeId().compareTo(bdTipoDocNewDoc) == 0) {
						if(lDocTypes.get(i).getMaxNumber() != null)
							iMaxNumberTipoDocNewDoc = lDocTypes.get(i).getMaxNumber().intValue();
					}

				} else {
					throw new Exception(msgs.getString("cr_man_doc_type_list_not_available"));
				}

			}
		} else {
			throw new Exception(msgs.getString("cr_man_doc_type_list_not_available"));
		}


		int iCurrentNumDocsByDocTypeInCapReq = 0;
		// Se compara con los documentos que ya tiene la CapacityRequest.
		// Si la Capacity Request no tuviera documentos adicionales, no pasa nada.
		if(  _selectedCapReq != null && 
				_selectedCapReq.getAdditionalDocs()	!= null ) {
			
			for(int j=0; j<_selectedCapReq.getAdditionalDocs().size(); j++) {
				if(	_selectedCapReq.getAdditionalDocs().get(j) != null &&
						_selectedCapReq.getAdditionalDocs().get(j).getContractAttachTypeId() != null ) {
	
					if( _selectedCapReq.getAdditionalDocs().get(j).getContractAttachTypeId().compareTo(bdTipoDocNewDoc) == 0 ) {
						iCurrentNumDocsByDocTypeInCapReq++;
					}
				}
			}
		}
		
		// Si el numero de documentos (del tipo que se quiere anadir), antes de anadir, ya es igual al maximo por tipo, se devuelve una excepcion y
		// no se deja anadir.
		if(iCurrentNumDocsByDocTypeInCapReq >= iMaxNumberTipoDocNewDoc)
			throw new ValidationException(msgs.getString("cr_man_max_doc_type_number_reached"));		
	}
	
	public void getDocFile(ContractAttachmentBean _contracDoc) throws Exception{
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	// Solo se consulta el fichero 1 vez de base de datos. Si ya existe, no se consulta.
    	if( _contracDoc.getScFile() == null) {

    		BigDecimal tmpContractAttachmentId = _contracDoc.getContractAttachmentId();
			if(tmpContractAttachmentId == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));

    		List<ContractAttachmentBean> lData = crMapper.getFileByContractAttachmentId(tmpContractAttachmentId);
	
			if(lData == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));
			
			// Solo se va a tener un fichero por cada contract attachment.
			ContractAttachmentBean tmpCaBean = lData.get(0);
			if(tmpCaBean == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));		
	
			byte[] ba = tmpCaBean.getBinaryData();
			if(ba == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));		
			
			_contracDoc.setBinaryData(Arrays.copyOf(ba, ba.length));
    	}
    }	


	public void deleteDocFile(ContractAttachmentBean _contracDoc) throws Exception{
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	int res = crMapper.updateContractAttachmentDelete(_contracDoc);
    	if(res!=1){
    		throw new Exception(msgs.getString("delete_error") + " " + msgs.getString("cr_man_additional_doc"));    		
    	}
	}


	// Para obtener identificadores de punto a partir de su codigo.
	// Hay una relacion 1 a 1 entre codigos e identificadores.
	private static Object getKeyFromValue(Map hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}


	//ch 361256
	@Override
	public void deleteCapacityRequest(CapacityRequestBean _crbSelected) throws Exception {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		_crbSelected.setStatus(Constants.SHIPPER_REJECTED);
		
    	int res = crMapper.updateCRComplete(_crbSelected);
    	if(res!=1){
    		throw new Exception(msgs.getString("delete_error") + " " + msgs.getString("cr_man_delete"));    		
    	}
	}
}
