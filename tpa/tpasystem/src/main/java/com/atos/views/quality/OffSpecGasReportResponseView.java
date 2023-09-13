package com.atos.views.quality;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.FileBean;
import com.atos.beans.MessageBean;
import com.atos.beans.quality.OffSpecIncidentBean;
import com.atos.beans.quality.OffSpecResponseBean;
import com.atos.beans.quality.OffSpecStatusBean;
import com.atos.beans.scadaAlarms.EmergencyDiffDayBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.quality.OffSpecGasReportManagementFilter;
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
	//private BigDecimal disclosedStatusId = null;
	//private BigDecimal disclosedStatusIdNewFlow = null; //Por el cambio de flujo
	
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

	// Se deja comentado por si en el futuro se quisiera dar respuesta a incidencias de tipo Request.
	//public StreamedContent getScRequestFlowDiagFile() {
	//    InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/images/qualityRequestFlow.png");
	//    scRequestFlowDiagFile = new DefaultStreamedContent(stream, "image/png", "qualityRequestFlow.png");
	//    
	//	return scRequestFlowDiagFile;
	//}
	//
	//public void setScRequestFlowDiagFile(StreamedContent scRequestFlowDiagFile) {
	//	this.scRequestFlowDiagFile = scRequestFlowDiagFile;
	//}

	public boolean isDisclosed(BigDecimal _statusId) {
		//return (disclosedStatusId.compareTo(_statusId)==0 || disclosedStatusIdNewFlow.compareTo(_statusId)==0);
		return (disclosedStatusIds.contains(_statusId));
	}
	
	@PostConstruct
    public void init() {

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
	
	public void onRowEdit(RowEditEvent _event) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = null;
    	String errorMsg = null;

		OffSpecIncidentBean _incid = (OffSpecIncidentBean) _event.getObject();
		
		if(uploadFile!=null && uploadFile.getFileName()!=null) {
			_incid.getFirstResponse().setFileName(uploadFile.getFileName());
			_incid.getFirstResponse().setAttachedFile(uploadFile.getContents());
		}
		
		if(_incid.getFirstResponse().getFileOnResponse().equals("EM")) {
			if(_incid.getFirstResponse().getAttachedFile()==null) {
				summaryMsg = msgs.getString("osgr_man_empty_file");
				messages.addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, summaryMsg, Calendar.getInstance().getTime()));
    			return;
			}
		}
		
    	try {
    		service.saveResponse(_incid, getUser(), disclosedStatusIds);
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
        			msgs.getString("osgr_man_off_specification") + " " + _incid.getIncidentTypeDesc() + " " +
					msgs.getString("with_id") + " " + _incid.getIncidentCode();
        	messages.addMessage(Constants.head_menu[6],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}
    	
    	summaryMsg = msgs.getString("osgr_man_off_specification") + " " + _incid.getIncidentTypeDesc() + " " +
    					msgs.getString("osgr_man_Response") + " " + msgs.getString("updated");
    	String okMsg = msgs.getString("osgr_man_Response") + " " + 
		    			msgs.getString("related_to") + " " +
		    			msgs.getString("osgr_man_off_specification") + " " + _incid.getIncidentTypeDesc() + " " +
						msgs.getString("with_id") + " " + 
						_incid.getIncidentCode() + " " + 
						msgs.getString("updated");     	
    	messages.addMessage(Constants.head_menu[6],
				new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
    	log.info(okMsg);

    	// Se actualiza la vista.
    	items = service.search(filters); 		
        // Se deja marcado (selected) el incidente que se acaba de editar. 
        selected = _incid;
    }
	//CH706
	public boolean renderedOperatorComments(OffSpecIncidentBean item){
		
		if (item!=null){
			if(Constants.OPERATOR.equalsIgnoreCase(getUser().getUser_type())
					||
					Constants.SHIPPER.equalsIgnoreCase(getUser().getUser_type()) && 
					item.getFirstResponse().getResponseValue()!=null && 
					item.getFirstResponse().getResponseValue().equals("KO")
						) {
					return true;	
					
				}else{
					return false;
				}
			
		}
		return false;	
	}
	
	public void handleFileUpload(FileUploadEvent event) {
    	
		file = event.getFile();
		if(file!=null){
			uploadFile = new FileBean(file.getFileName(), file.getContentType(), file.getContents());
		}
        
		if(file==null || uploadFile==null){
			messages.addMessage(Constants.head_menu[6],new MessageBean(Constants.ERROR,"Error saving file","The open file should be selected", Calendar.getInstance().getTime()));
			return;
		}
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
}
