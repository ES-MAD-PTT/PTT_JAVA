package com.atos.views.quality;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.FileBean;
import com.atos.beans.MessageBean;
import com.atos.beans.quality.OffSpecActionFileBean;
import com.atos.beans.quality.OffSpecIncidentBean;
import com.atos.beans.quality.OffSpecResponseBean;
import com.atos.beans.quality.OffSpecStatusBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.quality.OffSpecGasReportManagementFilter;
import com.atos.services.quality.OffSpecGasReportManagementService;
import com.atos.services.quality.OffSpecGasReportResponseService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;

@ManagedBean(name="OSGRResponseView")
@ViewScoped
public class OffSpecGasReportResponseView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 920551701830503048L;

	private UploadedFile file;
	private FileBean uploadFile = null;
	private OffSpecGasReportManagementFilter filters;
	private List<OffSpecIncidentBean> items;
	private OffSpecIncidentBean selected;
	private List<BigDecimal> disclosedStatusIds;


//	private List<OffSpecActionBean> allActions;
//	private Map<BigDecimal, OffSpecActionBean> mapAllActions;
	private List<FileBean> files;
	private Map<BigDecimal, Object> actions;
	
	// Para que el usuario se pueda descargar los diagramas de flujos.
	private StreamedContent scEventFlowDiagFile;
	//Se deja comentado por si en el futuro se quisiera dar respuesta a incidencias de tipo Request.
	//private StreamedContent scRequestFlowDiagFile;
	private static final String strEventFlowTypeCode = "EVENT";
	
	private static final Logger log = LogManager.getLogger("com.atos.views.quality.OffSpecGasReportManagementView");
	
	@ManagedProperty("#{OSGRResponseService}")
    transient private OffSpecGasReportResponseService service;
    
	public void setService(OffSpecGasReportResponseService service) {
		this.service = service;
	}
	
	@ManagedProperty("#{OSGRManagementService}")
    transient private OffSpecGasReportManagementService serviceManagement;
	
	public void setServiceManagement(OffSpecGasReportManagementService serviceManagement) {
		this.serviceManagement = serviceManagement;
	}

	@ManagedProperty("#{messagesView}")
    private MessagesView messages;

    public void setMessages(MessagesView messages) {
        this.messages = messages;
    }
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

	public StreamedContent getScEventFlowDiagFile() {
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/qualityEventFlow.png");
        scEventFlowDiagFile = new DefaultStreamedContent(stream, "image/png", "qualityEventFlow.png");
        
		return scEventFlowDiagFile;
	}

	public void setScEventFlowDiagFile(StreamedContent scEventFlowDiagFile) {
		this.scEventFlowDiagFile = scEventFlowDiagFile;
	}
	
	public FileBean getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(FileBean file) {
		this.uploadFile = file;
	}

	public Map<BigDecimal, Object> getActions() {
		return actions;
	}
	public void setActions(Map<BigDecimal, Object> actions) {
		this.actions = actions;
	}
//	public boolean isDisclosed(OffSpecIncidentBean item) {
//		//return (disclosedStatusId.compareTo(_statusId)==0 || disclosedStatusIdNewFlow.compareTo(_statusId)==0);
//		boolean distinctShipper = item.getOriginatorShipperId().compareTo(getUser().getIdn_user_group()) != 0;
//		return (distinctShipper && disclosedStatusIds.contains(item.getStatusId()));
//	}
	
	@PostConstruct
    public void init() {
//		if(allActions == null) {
//			allActions = serviceManagement.selectAllActions();
//			if(allActions != null && !allActions.isEmpty()) {
//				mapAllActions = new HashMap<BigDecimal, OffSpecActionBean>();
//				mapAllActions = allActions.stream().collect(Collectors.toMap(OffSpecActionBean::getIdnOffspecAction, action -> action));
//			}
//		}
		try{
			disclosedStatusIds = service.getDisclosedStatusIds();
			//disclosedStatusIdNewFlow = service.getDisclosedStatusIdNewFlow();
		}
		catch(Exception e){
			log.error(e.getMessage(), e);
	    	return;			
		}
		
		filters = initFilter();
        items = service.search(filters);
        selected = new OffSpecIncidentBean();
	}
	
	private OffSpecGasReportManagementFilter initFilter(){
		
		OffSpecGasReportManagementFilter tmpFilter = new OffSpecGasReportManagementFilter();
		
		// Si no es operador, se fija el filtro al shipper del usuario.
		if(! Constants.OPERATOR.equalsIgnoreCase(getUser().getUser_type())) {
			tmpFilter.setShipperId(getUser().getIdn_user_group());
		} else {
			// Si es operador, se inicializa el filtro con el primer shipper de la lista.
			List<ComboFilterNS> tmpAllShippers = service.getAllShippers();
			ComboFilterNS tmpFirstShipper = tmpAllShippers.get(0);
			tmpFilter.setShipperId(tmpFirstShipper.getKey());
		}
		
		// Por ahora en la pantalla solo se mostraran estados correspondientes a Events.
		// Se deja comentado por si en el futuro se pudieran dar respuestas a Requests.
		//List<ComboFilterNS> allIncidentTypes = service.selectIncidentTypes();
		//// Como inicializacion se traen los estados del primer tipo de incidencia.
		//if(allIncidentTypes!=null && allIncidentTypes.size()>0)
		//	tmpFilter.setIncidentTypeId(allIncidentTypes.get(0).getKey());
		//else
		//	tmpFilter.setIncidentTypeId(null);
    	
		// En sucesivas consultas de estados, siempre se usara este tipo de incidencia (Event).
    	BigDecimal tmpIncidentTypeId =  service.selectIncidentTypeIdFromCode(strEventFlowTypeCode);
    	tmpFilter.setIncidentTypeId(tmpIncidentTypeId);
    	
		BigDecimal[] tmpStatusIds = disclosedStatusIds.toArray(new BigDecimal[disclosedStatusIds.size()]);
    	tmpFilter.setStatusId(tmpStatusIds);

		Integer[] tmpResStatusIds = new Integer[3];
		tmpResStatusIds[0] = OffSpecGasReportManagementFilter.resStatusNoResponsedId;
		tmpFilter.setResStatusId(tmpResStatusIds);
		
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

	// Para los elementos del combo del filtro de shippers.
	// Si el usuario es un operador, se obtienen todos los shippers.
	// Si no, solo se obtiene el propio shipper.
	public Map<BigDecimal, Object> getShipperIds() {
		return service.selectShipperId();
	}

	public List<OffSpecStatusBean> getStatusIds(){
		// Si el parametro de entrada (incidentTypeId) fuera nulo, en la consulta se traera todos los estados.
		List<OffSpecStatusBean> lCurrentStatus = service.selectStatusIds(filters);
		
		// Para actualizar el filtro.
		BigDecimal[] tmpStatusIds = new BigDecimal[lCurrentStatus.size()];
		for(int i=0; i<lCurrentStatus.size(); i++) {
			// Solo se marca el estado Disclosed si esta entre los devueltos por la consulta, segun el tipo de incidencia.
			//if(disclosedStatusId.compareTo(lCurrentStatus.get(i).getStatusId())==0) {
			if(disclosedStatusIds.contains(lCurrentStatus.get(i).getStatusId())) {
				tmpStatusIds[i] = lCurrentStatus.get(i).getStatusId();
				break;
			}
		}
		
		filters.setStatusId(tmpStatusIds);
		
		return lCurrentStatus;
	}
	
	public void onSearch(){
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	if( filters.getStartDate() == null ){
	    	getMessages().addMessage(Constants.head_menu[6],
	    			new MessageBean(Constants.ERROR,
	    					msgs.getString("menu_OSG_report_response"), 
							msgs.getString("osgr_man_required_start_date_error"), 
							Calendar.getInstance().getTime()));
	    	log.error("Start date must be informed.");
	    	return;
		}
    	
    	if((filters.getStartDate()!=null) && (filters.getEndDate()!=null)){
    		if(filters.getStartDate().after(filters.getEndDate())){
    	    	getMessages().addMessage(Constants.head_menu[6],
    	    			new MessageBean(Constants.ERROR,
    	    					msgs.getString("menu_OSG_report_response"), 
    							msgs.getString("dates_order"), 
    							Calendar.getInstance().getTime()));
    	    	log.error("Start date must be previous or equal to end date.");
    	    	return;
    		}
    	}
    	
        items = service.search(filters);
        // En cada busqueda se resetea la fila seleccionada.
        selected = new OffSpecIncidentBean();
	}
	
	public void onClear(){
		
		filters = initFilter();
		  	
        if (items != null) {
            items.clear();
        }

        items = service.search(filters);
        // En cada busqueda se resetea la fila seleccionada.
        selected = new OffSpecIncidentBean();
	}
	
	public void prepareResponse(OffSpecIncidentBean item) {
		selected = new OffSpecIncidentBean();
		actions = new HashMap<BigDecimal, Object>();
		selected = item;
		selected.getFirstResponse().setUserComments(null);
		selected.getFirstResponse().setResponseValue("OK");
		actions = service.selectShipperAction(selected);
		if(actions != null && !actions.isEmpty()) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('nextStatusDlg').show();");
		}else {
			ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
			String errorMsg = msgs.getString("osgr_man_noActionEvent");
			messages.addMessage(Constants.head_menu[6],
					new MessageBean(Constants.WARNING, "", errorMsg, Calendar.getInstance().getTime()));
	    	return;
		}
	}
	
	public void onRowEdit() {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = null;
    	String errorMsg = null;
    	if(selected != null && selected.getIdnAction() == null) {
			summaryMsg = msgs.getString("saving_data_error");
			errorMsg = msgs.getString("osgr_man_required_action");
			messages.addMessage(Constants.head_menu[6],
					new MessageBean(Constants.WARNING, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;
		}
		if(selected != null && selected.getFilesAction().size() < 1) {
			summaryMsg = msgs.getString("saving_data_error");
    		errorMsg = msgs.getString("osrg_man_mandatoryOneFile");
    		messages.addMessage(Constants.head_menu[6],
					new MessageBean(Constants.WARNING, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;
    	}
		
    	try {
    		service.saveResponse(selected, getUser(), disclosedStatusIds);
    	}
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = ve.getMessage();
    		messages.addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;
		} 	
    	catch(Exception e){
			summaryMsg = msgs.getString("saving_data_error");
        	errorMsg = msgs.getString("update_error") + " " + msgs.getString("osgr_man_Response") + " " + 
        			msgs.getString("related_to") + " " +
        			msgs.getString("osgr_man_off_specification") + " " + selected.getIncidentTypeDesc() + " " +
					msgs.getString("with_id") + " " + selected.getIncidentCode();
        	messages.addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}
    	
    	summaryMsg = msgs.getString("osgr_man_off_specification") + " " + selected.getIncidentTypeDesc() + " " +
    					msgs.getString("osgr_man_Response") + " " + msgs.getString("updated");
    	String okMsg = msgs.getString("osgr_man_Response") + " " + 
		    			msgs.getString("related_to") + " " +
		    			msgs.getString("osgr_man_off_specification") + " " + selected.getIncidentTypeDesc() + " " +
						msgs.getString("with_id") + " " + 
						selected.getIncidentCode() + " " + 
						msgs.getString("updated");     	
    	messages.addMessage(Constants.head_menu[6],
				new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
    	log.info(okMsg);
    	RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('nextStatusDlg').hide();");
    	// Se actualiza la vista.
    	items = service.search(filters); 	
    }
	//CH706
//	public boolean renderedOperatorComments(OffSpecIncidentBean item){
//		
//		if (item!=null){
//			if(Constants.OPERATOR.equalsIgnoreCase(getUser().getUser_type())
//					||
//					Constants.SHIPPER.equalsIgnoreCase(getUser().getUser_type()) && 
//					item.getFirstResponse().getResponseValue() != null && 
//					item.getFirstResponse().getResponseValue().equals("KO")
//						) {
//					return true;	
//					
//				}else{
//					return false;
//				}
//			
//		}
//		return false;	
//	}
	
	public void handleFileUpload(FileUploadEvent event) {
		file = event.getFile();
		OffSpecActionFileBean uploadFile = null;
		if(file != null){
			uploadFile = new OffSpecActionFileBean(selected.getIncidentId(), selected.getFirstResponse().getGroupId(), selected.getIdnAction(), file.getFileName(), file.getContents(), getUser().getUsername());
			selected.getFilesAction().add(uploadFile);
		}
		if(file == null || uploadFile == null){
			getMessages().addMessage(Constants.head_menu[6],new MessageBean(Constants.ERROR,"Error saving file","The file should be selected", Calendar.getInstance().getTime()));
			return;
		}
    }
	
	public String getFileName() {
		String value = "";
		if(selected != null && !selected.getFilesAction().isEmpty()) {
			value = selected.getFilesAction().get(0).getFileName();
		}
		return value;
	}
	
	public StreamedContent selectFile(OffSpecResponseBean bean) {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
		
		String summaryMsg = msgs.getString("userGuide_downloading_file");
		String errorMsg = null;
		try {
			return service.getResponseFile(bean);
		} catch (ValidationException ve) {
			errorMsg = msgs.getString("download_error") + " " + msgs.getString("userGuide") + ve.getMessage();
			messages.addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			return null; 
		} catch (Exception e) {
			errorMsg = msgs.getString("download_error") + " User Guide. ";
			messages.addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));			
			return null; 
		}	
	}
	
	public boolean renderedEditResponse(OffSpecIncidentBean item) {
		boolean value = false;
		if(item != null) {
			value = item.getGroupId().compareTo(getUser().getIdn_user_group()) == 0;
		}
		return value;
	}
	
	public void acceptRejectAction(String responseValue) {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = msgs.getString("saving_data_error");
    	String errorMsg = null;
    	if(responseValue.equals("OK") && selected != null && selected.getFilesAction().size() < 1) {
    		errorMsg = msgs.getString("osrg_man_mandatoryOneFile");
    		getMessages().addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;
    	}
		try {
			if(selected != null) {
				int res = serviceManagement.acceptRejectAction(selected, responseValue, getUser());
				if(res != 1) { errorMsg = msgs.getString("osgr_man_errorChangeAction");
					 getMessages().addMessage(Constants.head_menu[6], new
					 MessageBean(Constants.ERROR, summaryMsg, errorMsg,
					 Calendar.getInstance().getTime())); return; 
				} 
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
    		getMessages().addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;
		}
		summaryMsg = msgs.getString("osgr_man_updatedSuccessfully");
		String[] params = { selected.getIncidentCode() };
		String msg = super.getMessageResourceString("osgr_man_changeActionOK", params);
		getMessages().addMessage(Constants.head_menu[3], new MessageBean(Constants.INFO, summaryMsg, msg, new Date()));
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('nextStatusDlg').hide();");
		onSearch();
	}
	
	public void selectActionFiles(OffSpecIncidentBean item) {
		 if(selected != null) {
			 selected.setFilesAction(new ArrayList<OffSpecActionFileBean>());
			 OffSpecResponseBean newItem = new OffSpecResponseBean();
			 newItem.setIncidentId(item.getIncidentId());
			 newItem.setGroupId(item.getGroupId());
			 newItem.setIdnAction(item.getIdnAction());
			 selected.getFilesAction().addAll(serviceManagement.selectActionFiles(newItem));
		 }else {
			 selected.setFilesAction(new ArrayList<OffSpecActionFileBean>());
		 }
	 }
}
