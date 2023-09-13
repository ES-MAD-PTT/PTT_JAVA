package com.atos.views.quality;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.MessageBean;

import com.atos.beans.quality.OffSpecGasQualityParameterBean;
import com.atos.beans.quality.OffSpecIncidentBean;
import com.atos.beans.quality.OffSpecResponseBean;
import com.atos.beans.quality.OffSpecStatusBean;
import com.atos.beans.quality.OffSpecStatusRuleBean;
import com.atos.beans.scadaAlarms.EmergencyDiffDayBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.quality.OffSpecGasReportManagementFilter;
import com.atos.services.MailService;
import com.atos.services.quality.OffSpecGasReportManagementService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

import net.sf.ehcache.config.Searchable;

@ManagedBean(name="OSGRManagementView")
@ViewScoped
public class OffSpecGasReportManagementView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9017289475790934846L;

	// allStatus lleva la lista de todos los estados, al poner el filtro de incidentTypeId = null.
	// Si fuera necesario cambiar de tipo de incidencia al cambiar de estado, ya estaria preparado el codigo.
	private List<OffSpecStatusBean> allStatus;
	private HashMap<BigDecimal, OffSpecStatusBean> hmAllStatus;		// StatusId y objeto Status.
	private List<OffSpecStatusRuleBean> allStatusRules;
	private static final String rulesIdSeparador = "_";
	// La clave de este hashmap sera el status Id origen concatenado con el status Id final, junto con el separador "_".
	private HashMap<String, OffSpecStatusRuleBean> hmAllStatusRules;
	private List<OffSpecGasQualityParameterBean> allQualityParameters;
	
	private OffSpecGasReportManagementFilter filters;
	private List<OffSpecIncidentBean> items;
	private OffSpecIncidentBean selected;
	// Se guardan dos variables separadas, una para request y otra para event. 
	// Para guardar el estado por separado, por si el usuario quiere crear alternativamente una u otra.
	// En el codigo, si un metodo se aplica a los dos tipos de incidencias, uso nombre Incident.
	private OffSpecIncidentBean newRequest;
	private OffSpecIncidentBean newEvent;
	
	// Para que el usuario se pueda descargar los diagramas de flujos.
	private StreamedContent scEventFlowDiagFile;
	private StreamedContent scRequestFlowDiagFile;
	
	private static final Logger log = LogManager.getLogger("com.atos.views.quality.OffSpecGasReportManagementView");
	
	@ManagedProperty("#{OSGRManagementService}")
    transient private OffSpecGasReportManagementService service;
    
	public void setService(OffSpecGasReportManagementService service) {
		this.service = service;
	}
	
	@ManagedProperty("#{mailService}")
    transient private MailService mailService;

    public void setMailService(MailService mailService) {
    	this.mailService = mailService;
    }

	// To control shiro permissions.
	private Subject subject;
	private static final String strOperatorManagementPermission = ".QUALITY.OFFSPEC.REPORT_MANAGEMENT.MAN_OPERATOR";	
	private static final String strShipperManagementPermission = ".QUALITY.OFFSPEC.REPORT_MANAGEMENT.MAN_SHIPPER";
	
	private static final String strRequestFlowTypeDesc = "Request";
	private static final String strEventFlowTypeDesc = "Event";

	
	//geters/seters
	public OffSpecGasReportManagementFilter getFilters() {
		return filters;
	}

	public void setFilters(OffSpecGasReportManagementFilter filters) {
		this.filters = filters;
	}

	public List<OffSpecIncidentBean> getItems() {
		return items;
	}

	public void setItems(List<OffSpecIncidentBean> items) {
		this.items = items;
	}

	public OffSpecIncidentBean getSelected() {
		return selected;
	}

	public void setSelected(OffSpecIncidentBean selected) {
		this.selected = selected;
	}

	public OffSpecIncidentBean getNewRequest() {
		return newRequest;
	}

	public void setNewRequest(OffSpecIncidentBean newRequest) {
		this.newRequest = newRequest;
	}

	public OffSpecIncidentBean getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(OffSpecIncidentBean newEvent) {
		this.newEvent = newEvent;
	}

	public StreamedContent getScEventFlowDiagFile() {
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/qualityEventFlow.png");
        scEventFlowDiagFile = new DefaultStreamedContent(stream, "image/png", "qualityEventFlow.png");
        
		return scEventFlowDiagFile;
	}

	public void setScEventFlowDiagFile(StreamedContent scEventFlowDiagFile) {
		this.scEventFlowDiagFile = scEventFlowDiagFile;
	}

	public StreamedContent getScRequestFlowDiagFile() {
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/qualityRequestFlow.png");
        scRequestFlowDiagFile = new DefaultStreamedContent(stream, "image/png", "qualityRequestFlow.png");
        
		return scRequestFlowDiagFile;
	}

	public void setScRequestFlowDiagFile(StreamedContent scRequestFlowDiagFile) {
		this.scRequestFlowDiagFile = scRequestFlowDiagFile;
	}

	// Este booleano marcara si esta habilitado el filtro de shipper (isOperator = true) o esta deshabilitado, 
	// con un shipper fijo.
	public boolean getIsOperator() {
		return getUser().isUser_type(Constants.OPERATOR);
	}

	@PostConstruct
    public void init() {
		
    	subject = SecurityUtils.getSubject();
		
		// Se cargan la lista de todos los estados, al poner el filtro de incidentTypeId = null.
    	// Si fuera necesario cambiar de tipo de incidencia al cambiar de estado, ya estaria preparado el codigo.
		OffSpecGasReportManagementFilter tmpFilter = new OffSpecGasReportManagementFilter();
		tmpFilter.setIncidentTypeId(null);
		allStatus = service.selectStatusIds(tmpFilter);
		allStatusRules = service.selectStatusRules(getUser().getUsername());
		hmAllStatus = linkAllStatus(allStatus, allStatusRules);
		hmAllStatusRules = linkAllStatusRules(allStatusRules);
		allQualityParameters = service.selectGasQualityParameters();
		
   		filters = initFilter();
		
        items = service.search(filters, getUser());
        updateIncidentInfo(hmAllStatus, items);
    		
        selected = new OffSpecIncidentBean();
        newRequest = initNewRequest();
        newEvent = initNewIncident();
	}
	
	private OffSpecIncidentBean initNewRequest() {
		OffSpecIncidentBean tmpInc = new OffSpecIncidentBean();
		
		// Se hace una copia de la lista inicial de parametros de calidad de gas.
		List<OffSpecGasQualityParameterBean> tmpList = new ArrayList<OffSpecGasQualityParameterBean>();
		for(OffSpecGasQualityParameterBean bean: allQualityParameters)
			tmpList.add(new OffSpecGasQualityParameterBean(bean));
		
		tmpInc.setGasParams(tmpList);
		Calendar tmpTomorrow = Calendar.getInstance();
		tmpTomorrow.set(Calendar.HOUR_OF_DAY, 0);
		tmpTomorrow.set(Calendar.MINUTE, 0);
		tmpTomorrow.set(Calendar.SECOND, 0);
		tmpTomorrow.set(Calendar.MILLISECOND, 0);
		tmpTomorrow.add(Calendar.DAY_OF_MONTH, 1);
		tmpInc.setStartDate(tmpTomorrow.getTime());
		tmpInc.setEndDate(tmpTomorrow.getTime());
		
		// Si no es operador, se fija el filtro al shipper del usuario.
		if(! Constants.OPERATOR.equalsIgnoreCase(getUser().getUser_type())) {
			tmpInc.setOriginatorShipperId(getUser().getIdn_user_group());
			tmpInc.setOriginatorShipperCode(getUser().getUser_group_id());
		}
		
		return tmpInc;
	}
	
	private OffSpecIncidentBean initNewIncident() {
		OffSpecIncidentBean tmpInc = new OffSpecIncidentBean();
		
		// Se hace una copia de la lista inicial de parametros de calidad de gas.
		List<OffSpecGasQualityParameterBean> tmpList = new ArrayList<OffSpecGasQualityParameterBean>();
		for(OffSpecGasQualityParameterBean bean: allQualityParameters)
			tmpList.add(new OffSpecGasQualityParameterBean(bean));
		
		tmpInc.setGasParams(tmpList);
		Calendar tmpTomorrow = Calendar.getInstance();
		tmpTomorrow.set(Calendar.HOUR_OF_DAY, 0);
		tmpTomorrow.set(Calendar.MINUTE, 0);
		tmpTomorrow.set(Calendar.SECOND, 0);
		tmpTomorrow.set(Calendar.MILLISECOND, 0);
		tmpTomorrow.add(Calendar.DAY_OF_MONTH, 1);
		tmpInc.setStartDate(tmpTomorrow.getTime());
		
		return tmpInc;
	}
	
	// Para los elementos del combo del filtro de Type of incident.
	public Map<BigDecimal, Object> getIncidentTypes(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();

		List<ComboFilterNS> allIncidentTypes = service.selectIncidentTypes();
		
		for (ComboFilterNS combo : allIncidentTypes) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 	
	}
	
	// Para los elementos del combo del filtro de QualityPoints.
	public Map<BigDecimal, Object> getQualityPoints() {
		return service.selectQualityPoints(getChangeSystemView().getIdn_active());
	}

	// Para los elementos del combo del filtro de QualityPoints.
	// En inserciones hay que comprobar la vigencia del punto.
	public Map<BigDecimal, Object> getQualityPointsForInsert() {
		return service.selectQualityPointsForInsert(getChangeSystemView().getIdn_active());
	}

	// Para los elementos del combo del filtro de shippers.
	public Map<BigDecimal, Object> getShipperIds() {
		return service.selectShipperId();
	}
	
	// Para los elementos del combo del filtro de shippers. 
	// En inserciones hay que comprobar la vigencia del shipper.
	public Map<BigDecimal, Object> getShipperIdsForInsert() {
		return service.selectShipperIdForInsert();
	}

	public List<OffSpecStatusBean> getStatusIds(){
		// Si el parametro de entrada (incidentTypeId) fuera nulo, en la consulta se traera todos los estados.
		List<OffSpecStatusBean> lCurrentStatus = service.selectStatusIds(filters);
		
		BigDecimal[] tmpStatusIds = new BigDecimal[lCurrentStatus.size()];
		for(int i=0; i<lCurrentStatus.size(); i++)
			tmpStatusIds[i] = lCurrentStatus.get(i).getStatusId();
		filters.setStatusId(tmpStatusIds);
		
		return lCurrentStatus;
	}
	
	public Date getYesterday(){
		Calendar tmpYesterday = Calendar.getInstance();
		tmpYesterday.set(Calendar.HOUR_OF_DAY, 0);
		tmpYesterday.set(Calendar.MINUTE, 0);
		tmpYesterday.set(Calendar.SECOND, 0);
		tmpYesterday.set(Calendar.MILLISECOND, 0);
		tmpYesterday.add(Calendar.DAY_OF_MONTH, -1);
		return tmpYesterday.getTime();
	}
	
	public void onSearch(){
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	if( filters.getStartDate() == null ){
	    	getMessages().addMessage(Constants.head_menu[6],
	    			new MessageBean(Constants.ERROR,
	    					msgs.getString("menu_OSG_report_management"), 
							msgs.getString("osgr_man_required_start_date_error"), 
							Calendar.getInstance().getTime()));
	    	log.error("Start date must be informed.");
	    	return;
		}
    	
    	if((filters.getStartDate()!=null) && (filters.getEndDate()!=null)){
    		if(filters.getStartDate().after(filters.getEndDate())){
    	    	getMessages().addMessage(Constants.head_menu[6],
    	    			new MessageBean(Constants.ERROR,
    	    					msgs.getString("menu_OSG_report_management"), 
    							msgs.getString("dates_order"), 
    							Calendar.getInstance().getTime()));
    	    	log.error("Start date must be previous or equal to end date.");
    	    	return;
    		}
    	}
    	
        items = service.search(filters, getUser());
        updateIncidentInfo(hmAllStatus, items);
        
        // En cada busqueda se resetea la fila seleccionada.
        selected = new OffSpecIncidentBean();
	}
	
	public void onClear(){
		
		filters = initFilter();
		  	
        if (items != null) {
            items.clear();
        }

        items = service.search(filters, getUser());
        updateIncidentInfo(hmAllStatus, items);
        
        // En cada busqueda se resetea la fila seleccionada.
        selected = new OffSpecIncidentBean();
	}
	
	
	public void getFile(OffSpecIncidentBean _incident) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = msgs.getString("cr_man_xlsFile");
    	String errorMsg = null;    	
    	
    	try {
    		service.getFile(_incident);
    	}
    	catch(ValidationException ve){
    		errorMsg = msgs.getString("download_error") + " " + _incident.getFileName() + ": " + ve.getMessage();
    		getMessages().addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;    		
    	}
    	catch(Exception e){
    		errorMsg = msgs.getString("download_error") + " " + _incident.getFileName();
    		getMessages().addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}
	}
	

	private OffSpecGasReportManagementFilter initFilter(){
		
		OffSpecGasReportManagementFilter tmpFilter = new OffSpecGasReportManagementFilter();
		
		// Si no es operador, se fija el filtro al shipper del usuario.
		if(! Constants.OPERATOR.equalsIgnoreCase(getUser().getUser_type())) {
			tmpFilter.setShipperId(getUser().getIdn_user_group());
			tmpFilter.setShipperCode(getUser().getUser_group_id());
		}

    	List<ComboFilterNS> allIncidentTypes = service.selectIncidentTypes();
    	// Como inicializacion se traen los estados del primer tipo de incidencia.
    	if(allIncidentTypes!=null && allIncidentTypes.size()>0)
    		tmpFilter.setIncidentTypeId(allIncidentTypes.get(0).getKey());
    	else
    		tmpFilter.setIncidentTypeId(null);
    	
		// Si el parametro de entrada (incidentTypeId) fuera nulo, en la consulta se traera todos los estados.
		List<OffSpecStatusBean> lCurrentStatus = service.selectStatusIds(tmpFilter);
		
		BigDecimal[] tmpStatusIds = new BigDecimal[lCurrentStatus.size()];
		for(int i=0; i<lCurrentStatus.size(); i++)
			tmpStatusIds[i] = lCurrentStatus.get(i).getStatusId();
		tmpFilter.setStatusId(tmpStatusIds);		
		
		Calendar tmpToday = Calendar.getInstance();
        tmpToday.set(Calendar.HOUR_OF_DAY, 0);
        tmpToday.set(Calendar.MINUTE, 0);
        tmpToday.set(Calendar.SECOND, 0);
        tmpToday.set(Calendar.MILLISECOND, 0);
        tmpFilter.setStartDate(tmpToday.getTime());
    	// endDate no se incializa. Debe ser vacio inicialmente.
        
        tmpFilter.setSystemId(getChangeSystemView().getIdn_active());
    	
    	return tmpFilter;
	}

	/*
	 * En este metodo se va a asociar la lista de allStatus con un hashMap, para que luego sea mas facil buscar estados.
	 * Tambien se asocian los estados entre si (a partir de las reglas) para que sea facil encontrar los siguientes estados.
	 */
	private static HashMap<BigDecimal, OffSpecStatusBean> linkAllStatus( List<OffSpecStatusBean> _allStatus,
																		 List<OffSpecStatusRuleBean> _allStatusRules ){
		HashMap<BigDecimal, OffSpecStatusBean> tmpHm = new HashMap<BigDecimal, OffSpecStatusBean>();
		
		for(OffSpecStatusBean status: _allStatus)
			tmpHm.put(status.getStatusId(), status);
		
		for(OffSpecStatusRuleBean rule: _allStatusRules){
			BigDecimal tmpOrigStatusId = rule.getCurrentStatusId();
			BigDecimal tmpDestStatusId = rule.getNextStatusId();
			OffSpecStatusBean tmpOrigStatus = tmpHm.get(tmpOrigStatusId);
			OffSpecStatusBean tmpDestStatus = tmpHm.get(tmpDestStatusId);
			tmpOrigStatus.getNextStatusSet().add(tmpDestStatus);
		}
			
		return tmpHm;
	}

	/*
	 * En este metodo se va a asociar la lista de hmAllStatusRules con un hashMap, para que luego sea mas facil buscar reglas de estados.
	 */
	private static HashMap<String, OffSpecStatusRuleBean> linkAllStatusRules( List<OffSpecStatusRuleBean> _allStatusRules ){
		HashMap<String, OffSpecStatusRuleBean> tmpHm = new HashMap<String, OffSpecStatusRuleBean>();
		
		for(OffSpecStatusRuleBean rule: _allStatusRules)
			tmpHm.put(rule.getCurrentStatusId() + rulesIdSeparador + rule.getNextStatusId(), rule);
			
		return tmpHm;
	}

	private void updateIncidentInfo( HashMap<BigDecimal, OffSpecStatusBean> _hmAllStatus, 
									  List<OffSpecIncidentBean> _items ) {
		
		for(OffSpecIncidentBean incid : _items){
			incid.setGroupId(getUser().getIdn_user_group());
			incid.setStatus(_hmAllStatus.get(incid.getStatusId()));
		}
	}
	
	public void setChosenNextStatusRule(OffSpecIncidentBean _incident, BigDecimal _chosenNextStatusId) {
	
		selected = _incident;
		selected.setNewStatusId(_chosenNextStatusId);
		// Para incluir el tipo de incidente (Request, Event) en notificaciones.
		selected.setNewIncidentTypeDesc(this.hmAllStatus.get(_chosenNextStatusId).getIncidentTypeDesc());
		OffSpecStatusRuleBean tmpRule = this.hmAllStatusRules.get(selected.getStatusId() + rulesIdSeparador + _chosenNextStatusId);
		selected.setChosenNextStatusRule(tmpRule);
		
		// Si en el combo de cambio de estado, se puede incluir fecha, se le da valor por defecto.
		if(tmpRule.endDateIsEditable()) {
			// Se inicializan las newEndDate, respecto a la mayor entre la hora actual y a la startDate.
			Date now = new Date();
			Date tmpStartDate = selected.getStartDate();
			Date tmpNewEndDate = (now.after(tmpStartDate))? now : tmpStartDate;
			selected.setNewEndDate(tmpNewEndDate);
		}
	}
	
    public void onChangeStatus() {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = null;
    	String errorMsg = null;
    	List<BigDecimal> lEmailRecipients = null;

    	try {

    		service.changeStatus(selected, getUser(), getLanguage());
    		
    		// Se envian notificaciones fuera de la transaccion que guarda los datos en BD. Si fallara
    		// la notificacion solo se muestra mensaje al usuario.
    		// Algunas reglas no tienen notificacion asociada.
    		if(selected.getChosenNextStatusRule().getNotificationTypeCode()!=null)
    			service.sendNewStatusNotification(selected, getUser(), getLanguage(), getChangeSystemView().getIdn_active());
    		if(selected.getChosenNextStatusRule().getEmailTypeCode()!=null) {
    			BigDecimal origShipperId = selected.getNewOriginatorShipperId();
    			// Si no se ha rellenado el shipper en la ultima pantalla, se toma el que hubiera antes.
    			if( origShipperId == null)
    				origShipperId = selected.getOriginatorShipperId();
    			lEmailRecipients = service.getNotifRecipients(selected.getChosenNextStatusRule().getNotificationMode(), 
    													origShipperId,getUser(), getLanguage());
    			
    			for(BigDecimal recipient :lEmailRecipients) 
    				sendMail(selected, selected.getChosenNextStatusRule().getEmailTypeCode(), recipient);
    		}
    	}
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = ve.getMessage();
    		getMessages().addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;
		} 	
    	catch(Exception e){
			summaryMsg = msgs.getString("saving_data_error");
        	errorMsg = msgs.getString("update_error") + " " + 
        			msgs.getString("osgr_man_off_specification") + " " + selected.getIncidentTypeDesc() + " " +
					msgs.getString("with_id") + " " + selected.getIncidentCode();
    		getMessages().addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}
    	
    	summaryMsg = msgs.getString("osgr_man_off_specification") + " " + selected.getIncidentTypeDesc() + " " +
    					msgs.getString("osgr_man_status_change");
    	String okMsg = msgs.getString("osgr_man_off_specification") + " " + selected.getIncidentTypeDesc() + " " + 
    					msgs.getString("osgr_man_incid_updated") + " " + selected.getIncidentCode();    	
    	getMessages().addMessage(Constants.head_menu[6],
				new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
    	log.info(okMsg);

    	// Se actualiza la vista.
    	items = service.search(filters, getUser()); 
        updateIncidentInfo(hmAllStatus, items);
    }
    
    public void onNewRequest() {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = null;
    	String errorMsg = null;

    	try {
    		newRequest.setIncidentTypeDesc(strRequestFlowTypeDesc);
    		service.createRequest(newRequest, getUser(), getLanguage());
    		// Se envian notificaciones fuera de la transaccion que guarda los datos en BD. Si fallara
    		// la notificacion solo se muestra mensaje al usuario.
    		service.sendNewRequestNotification(newRequest, getUser(), getLanguage(), getChangeSystemView().getIdn_active());
    	}
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = msgs.getString("osgr_man_new_off_specification_request") + ": " + ve.getMessage();
    		getMessages().addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;
		} 	
    	catch(Exception e){
			summaryMsg = msgs.getString("saving_data_error");
        	errorMsg = msgs.getString("update_error") + " " + msgs.getString("osgr_man_off_specification_request") + " " +
					msgs.getString("with_id") + " " + newRequest.getIncidentCode();
    		getMessages().addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}

    	summaryMsg = msgs.getString("osgr_man_new_off_specification_request");
    	String okMsg = msgs.getString("osgr_man_off_specification") + " " + newRequest.getIncidentTypeDesc() + " " + 
    					msgs.getString("osgr_man_incid_created") + " " + newRequest.getIncidentCode();    	
    	getMessages().addMessage(Constants.head_menu[6],
				new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
    	log.info(okMsg);    	

    	// Se actualiza la vista.
    	items = service.search(filters, getUser()); 
        updateIncidentInfo(hmAllStatus, items);
        // Para marcar la incidencia recien creada.
        selected = newRequest;
    	// Cuando se haya volcado a la BD, hay que limpiar el objeto.
        newRequest = initNewRequest();
    }

    public void onNewEvent() {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = null;
    	String errorMsg = null;

    	try {
    		newEvent.setIncidentTypeDesc(strEventFlowTypeDesc);
    		service.createEvent(newEvent, getUser(), getLanguage());
    		// Se envian notificaciones fuera de la transaccion que guarda los datos en BD. Si fallara
    		// la notificacion solo se muestra mensaje al usuario.
    		service.sendNewEventNotification(newEvent, getUser(), getLanguage(), getChangeSystemView().getIdn_active());
    		//Sólo se manda correo si el que crea el evento es Shipper. Si es operador no tiene sentido
    		if(isShipper())
    			sendMail(newEvent,"OFFSPEC.EVENT.GENERATED", service.getDefaultOperatorId(getUser(), getLanguage()));
    	}
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = msgs.getString("osgr_man_new_off_specification_event") + ": " + ve.getMessage();
    		getMessages().addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;
		} 	
    	catch(Exception e){
			summaryMsg = msgs.getString("saving_data_error");
        	errorMsg = msgs.getString("update_error") + " " + msgs.getString("osgr_man_off_specification_event") + " " +
					msgs.getString("with_id") + " " + newEvent.getIncidentCode();
    		getMessages().addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}

    	summaryMsg = msgs.getString("osgr_man_new_off_specification_event");
    	String okMsg = msgs.getString("osgr_man_off_specification") + " " + newEvent.getIncidentTypeDesc() + " " + 
    					msgs.getString("osgr_man_incid_created") + " " + newEvent.getIncidentCode();    	
    	getMessages().addMessage(Constants.head_menu[6],
				new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
    	log.info(okMsg);    	

    	// Se actualiza la vista.
    	items = service.search(filters, getUser()); 
        updateIncidentInfo(hmAllStatus, items);
        // Para marcar la incidencia recien creada.
        selected = newEvent;
    	// Cuando se haya volcado a la BD, hay que limpiar el objeto.
        newEvent = initNewIncident();
    }
    
	public boolean hasOperatorManagPermission(){
		return subject.isPermitted(getChangeSystemView().getSystem() + strOperatorManagementPermission);
	}
	
	public boolean hasManagementPermission(){
		return ( subject.isPermitted(getChangeSystemView().getSystem() + strOperatorManagementPermission) || 
				subject.isPermitted(getChangeSystemView().getSystem() + strShipperManagementPermission) );
	}
	
	 public int getItemsSize() { 
			if(this.items!=null && !this.items.isEmpty()){
				return this.items.size();
			}else{
				return 0;
			}
		}
	 
	 
	 //CH706
	 public void onRowEdit(RowEditEvent event) {
		  
		 OffSpecResponseBean response = (OffSpecResponseBean) event.getObject();
	    	
	   
	    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
	    	String[] params = {msgs.getString("osgr_man_operator_commets")};
	    	String summaryMsgOk = getMessageResourceString("update_ok", params);
	    	String summaryMsgNotOk= getMessageResourceString("update_noOk", params);
	    	
	    	
	    	String error = "0";
			try {
				error = service.updateTransporterComments(response);
			} catch(Exception e) {
				log.catching(e);
				// we assign the return message 
				error = e.getMessage();
			}
			
			String[] par2 = {response.getGroupCode(),msgs.getString("shipper") };
			
			if(error!=null && error.equals("0")){
				getMessages().addMessage(Constants.head_menu[6],new MessageBean(Constants.INFO,summaryMsgOk, summaryMsgOk + " Shipper: " + response.getGroupCode(), Calendar.getInstance().getTime()));	
				log.info("Transporter Response Comment Updated ok: " + response.toString(), Calendar.getInstance().getTime());
	 
	    	}else if (error!=null && error.equals("-1")){
	    		String msg =  getMessageResourceString("error_updating", par2);
				getMessages().addMessage(Constants.head_menu[6],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
	    		log.info("Error updating Transporter Response Comment. Error updating Table Toffspec_Response: "  + response.toString(), Calendar.getInstance().getTime());		
	    		
	    	}
			
			
	    }

	 public void onRowCancel(RowEditEvent event) {
	    	
	    }
	 
	 public boolean showFile(String bean) {
		 if(bean.equals("NV"))
			 return false;
		 return true;
	 }
	 
	 public void selectFile(OffSpecResponseBean bean) {
			ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
					.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
			
			String summaryMsg = msgs.getString("osgr_man_downloading_file");
			String errorMsg = null;
			try {
				service.getFileResponse(bean);
			} catch (ValidationException ve) {
				errorMsg = msgs.getString("download_error") + " " + msgs.getString("osgr_man_file") + ve.getMessage();
				getMessages().addMessage(Constants.head_menu[6],
						new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
				return; 
			} catch (Exception e) {
				errorMsg = msgs.getString("download_error") + " Response file. ";
				getMessages().addMessage(Constants.head_menu[6],
						new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));			
				return; 
			}	
		}
	 
	 public void sendMail(OffSpecIncidentBean selected, String type, BigDecimal to) {
			HashMap<String,String> values = new HashMap<String,String>();

			values.put("1", selected.getOriginatorShipperCode());
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String dateString = df.format(selected.getStartDate());
			values.put("2", dateString);
			selected.setIdn_pipeline_system(getChangeSystemView().getIdn_active());
			String zone = service.getZoneCode(selected);
			values.put("3", zone);
			
			mailService.sendEmail(type, values, getChangeSystemView().getIdn_active(), to);
			//Para comprobar los valores que se pasan en el email
			getMessages().addMessage(Constants.head_menu[6],
					new MessageBean(Constants.INFO, "Mail values", "Shipper"+values.get("1")+",Fecha:"+values.get("2")+",Zona:"+values.get("3")+". Destinatario:"+to, Calendar.getInstance().getTime()));
		}
}
