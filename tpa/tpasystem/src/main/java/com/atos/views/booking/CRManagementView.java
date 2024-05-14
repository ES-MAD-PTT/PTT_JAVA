package com.atos.views.booking;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.MessageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.booking.CRMFilterCtl;
import com.atos.beans.booking.CapacityRequestBean;
import com.atos.beans.booking.ContractAttachTypeBean;
import com.atos.beans.booking.ContractAttachmentBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.booking.CRManagementFilter;
import com.atos.services.MailService;
import com.atos.services.booking.CRManagementService;
import com.atos.utils.Constants;
import com.atos.utils.DateUtil;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;

@ManagedBean(name="CRManagementView")
@ViewScoped
public class CRManagementView extends CommonView  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2379904021154751481L;
	
	private static BigDecimal[] abdAllContractTypeIds;	
	private CRManagementFilter filters;
	private List<CapacityRequestBean> items;
	private CapacityRequestBean selected;
	// Para controlar el plazo limite para poder dar viabilidad a una request.
	private Calendar limitContStartDate;
	// Listado de todos los tipos de documentos posibles.
	private List<ContractAttachTypeBean> lDocTypes;
	// Para anadir un documento nuevo a la capacityRequest seleccionada.
	private ContractAttachmentBean newDocument;
	// Este documento no sera visible desde la pantalla. Solo se usa para guardar un objeto entre
	// llamadas de dos metodos.
	private ContractAttachmentBean documentToDelete;
	
	private static final Logger log = LogManager.getLogger("com.atos.views.booking.CRManagementView");
	
	@ManagedProperty("#{CRManagementService}")
    transient private CRManagementService service;
	@ManagedProperty("#{filterCtl}")
	private CRMFilterCtl backFileter;
    
	public void setService(CRManagementService service) {
		this.service = service;
	}

	@ManagedProperty("#{messagesView}")
    private MessagesView messages;

	public void setMessages(MessagesView messages) {
		this.messages = messages;
	}
	
	@ManagedProperty("#{mailService}")
    transient private MailService mailService;

    public void setMailService(MailService mailService) {
    	this.mailService = mailService;
    }
	
	//geters/seters
	public CRManagementFilter getFilters() {
		return filters;
	}

	public void setFilters(CRManagementFilter filters) {
		this.filters = filters;
	}

	public List<CapacityRequestBean> getItems() {
		return items;
	}

	public void setItems(List<CapacityRequestBean> items) {
		this.items = items;
	}

	public CapacityRequestBean getSelected() {
		return selected;
	}

	public void setSelected(CapacityRequestBean selected) {
		this.selected = selected;
	}

	public List<ContractAttachTypeBean> getlDocTypes() {
		return lDocTypes;
	}

	public ContractAttachmentBean getNewDocument() {
		return newDocument;
	}

	public void setNewDocument(ContractAttachmentBean newDocument) {
		this.newDocument = newDocument;
	}


	@PostConstruct
    public void init() {
    	// Se requiere que en la pantalla de inicio se ponga por defecto todos los posibles tipos de contratos.
    	Object[] tmpObjectArray = getContractTypes().keySet().toArray();
    	abdAllContractTypeIds = Arrays.copyOf(tmpObjectArray, tmpObjectArray.length, BigDecimal[].class);

    	initFilters();
		
        items = service.search(filters);
        
        selected = new CapacityRequestBean();
 
		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
		Integer valNumDate = valDate.getInteger_exit();
        limitContStartDate = Calendar.getInstance(); 
        limitContStartDate.setTime(valDate.getDate());
        limitContStartDate.set(Calendar.HOUR_OF_DAY, 0);
        limitContStartDate.set(Calendar.MINUTE, 0);
        limitContStartDate.set(Calendar.SECOND, 0);
        limitContStartDate.set(Calendar.MILLISECOND, 0);
        limitContStartDate.add(Calendar.DAY_OF_MONTH, valNumDate.intValue());
        
		lDocTypes = service.selectContractAttachTypes();
        
    	newDocument = new ContractAttachmentBean();
    }	

	private void initFilters(){
		filters = new CRManagementFilter();
    	
		if(isShipper())
			filters.setShipperId(getUser().getIdn_user_group());
    	filters.setEndDate(DateUtil.getToday());
    	filters.setStartDate(DateUtil.getToday());

    	filters.setContractTypeId(abdAllContractTypeIds);
    	// Se requiere que en la pantalla de inicio se ponga por defecto el filtro de submitted.
    	filters.setStatus(new String[]{Constants.SUBMITTED});
		if (backFileter.isReturning()) {
			filters = backFileter.getFilter();
			backFileter.setReturning(false);
		}
		
		//offshore
    	filters.setIdn_system(getChangeSystemView().getIdn_active());
	}
	
	public void linkAction(ActionEvent event) {
		if (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
			event.setPhaseId(PhaseId.INVOKE_APPLICATION);
			event.queue();
			return;
		}
		backFileter.setRequestCode((String) event.getComponent().getAttributes().get("request_code"));
		backFileter.setContractType((String) event.getComponent().getAttributes().get("contract_type"));
		backFileter.setShipperName((String) event.getComponent().getAttributes().get("shipper_name"));
		backFileter.setOrigin(CRMFilterCtl.ORIGING_CAP_REQUEST);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("CRReview.xhtml");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	
	// Para los elementos del combo del filtro de shippers.
	public Map<BigDecimal, Object> getShipperIds() {
		return service.selectShipperId();
	}	


	// Para los elementos del combo del filtro de contractTypes.
	public Map<BigDecimal, Object> getContractTypes() {
		return service.selectContractTypes();
	}


	// Para traducir los estados de la BD a las etiquetas definidas en messages.properties.
	public String getStatusExternalCode(String _status) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		String strStatusExternalCode = null;
		
		if(Constants.SUBMITTED.equalsIgnoreCase(_status))
			strStatusExternalCode = msgs.getString("cr_man_submitted");
		else if(Constants.ACCEPTED.equalsIgnoreCase(_status))
			strStatusExternalCode = msgs.getString("cr_man_accepted");
		else if(Constants.COMPLETED.equalsIgnoreCase(_status))
			strStatusExternalCode = msgs.getString("cr_man_completed");
		else if(Constants.PTT_REJECTED.equalsIgnoreCase(_status))
			strStatusExternalCode = msgs.getString("cr_man_rejected");
		else if(Constants.SHIPPER_REJECTED.equalsIgnoreCase(_status))
			strStatusExternalCode = msgs.getString("cr_man_shipper_rejected");
		else
			strStatusExternalCode = msgs.getString("cr_man_undefined");
		
		return strStatusExternalCode;
	}


	// Para traducir los codigos de tipos de documentos de la BD a las etiquetas definidas en messages.properties.
	public String getDocTypeExternalCode(BigDecimal _docTypeId) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	String strDocTypeCode = null;
    	String strDocTypeExternalCode = null;
    	
    	for(int i=0; i<lDocTypes.size(); i++) {
    		if(_docTypeId.compareTo(lDocTypes.get(i).getContractAttachTypeId()) == 0)
    			strDocTypeCode = lDocTypes.get(i).getTypeCode();
    	}
    	
		if(strDocTypeCode != null) {
			
			if(Constants.BANK_GUARANTEE.equalsIgnoreCase(strDocTypeCode))
				strDocTypeExternalCode = msgs.getString("cr_man_bank_guarantee");
			else if(Constants.SIGNED_CONTRACT.equalsIgnoreCase(strDocTypeCode))
				strDocTypeExternalCode = msgs.getString("cr_man_signed_contract");
			else if(Constants.OTHER.equalsIgnoreCase(strDocTypeCode))
				strDocTypeExternalCode = msgs.getString("cr_man_other");
			else 
				strDocTypeExternalCode = msgs.getString("cr_man_undefined_doc_type");
		}
		return strDocTypeExternalCode;
	}

	
	public void onSearch(){
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	if((filters.getStartDate()!=null) && (filters.getEndDate()!=null)){
    		if(filters.getStartDate().after(filters.getEndDate())){
    	    	messages.addMessage(Constants.head_menu[1],
    	    			new MessageBean(Constants.ERROR,
    	    					msgs.getString("menu_cr_management"), 
    							msgs.getString("dates_order"), 
    							Calendar.getInstance().getTime()));
    	    	log.error("Start date must be previous or equal to end date.");
    	    	return;
    		}
    	}
    	
    	
    	//offshore
    	filters.setIdn_system(getChangeSystemView().getIdn_active());
    			
		backFileter.setFilter(filters);
        items = service.search(filters);
        // En cada busqueda se resetea la fila seleccionada.
        selected = new CapacityRequestBean();
        // No hace falta resetear newDocument porque se resetea en createNewDocFile().
    	// newDocument = new ContractAttachmentBean();
    }


	public void onClear(){
		
		initFilters();
    	
        if (items != null) {
            items.clear();
        }
               
		backFileter.setFilter(filters);
        items = service.search(filters);
        // En cada busqueda se resetea la fila seleccionada y el nuevo documento.
        selected = new CapacityRequestBean();
        // No hace falta resetear newDocument porque se resetea en createNewDocFile().
    	// newDocument = new ContractAttachmentBean();        
    }


	public boolean isReadyForFeasibility(CapacityRequestBean _capReq){
		
		// Solo se permite dar viabilidad si la capacity request tiene estado submitted y no se ha pasado el calendario de viabilidad.
   		if(_capReq.getContractStartDate().before(limitContStartDate.getTime())){
			// No envio un mensaje de aviso, porque se genera el mismo aviso
			// cada vez que se recarga la página.
   			/*    	    	messages.addMessage(Constants.head_menu[1],
    	    			new MessageBean(Constants.WARNING,
    	    							msgs.getString("menu_cr_management"), 
    	    							msgs.getString("cr_man_capacity_request") + " " + 
    	    									msgs.getString("with") + " " + msgs.getString("cr_man_contract_startdate") + " " + 
    	    									_capReq.getContractStartDate() + " " + 
    	    									msgs.getString("cr_man_gone_out_of_date"), 
    	    							Calendar.getInstance().getTime()));
   			 */
   			log.error("Capacity Request with ID " + _capReq.getId() + 
   					" and Contract Start Date " + _capReq.getContractStartDate() +
   					" cannot be approved/rejected. It has exceeded its feasibility calendar." );
    	    return false;
    	}
  			
		return true;
	}

	
	public void selectRejectedPoints(CapacityRequestBean _capReq){
		service.selectRejectedPoints(_capReq);
	}

	
	public void selectRequestedPoints(CapacityRequestBean _capReq){
		service.selectRequestedPoints(_capReq);
	}

	
    public void onAcceptCapacityRequest() {
    	onChangeStatusCapacityRequest(CRManagementService.Accept);
    }

    public void onCompleteCapacityRequest() {
    	onChangeStatusCapacityRequest(CRManagementService.Complete);    	
    }

    public void onRejectTechCapacityRequest() {
    	onChangeStatusCapacityRequest(CRManagementService.Reject_Tech);    	
    }
    
    public void onRejectDefCapacityRequest() {
    	onChangeStatusCapacityRequest(CRManagementService.Reject_Def);    	
    }

    public void onChangeStatusCapacityRequest(String _action) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = null;
    	String errorMsg = null;

    	try {
        	if(CRManagementService.Accept.equalsIgnoreCase(_action))
	        	summaryMsg = msgs.getString("cr_man_accept_dialog_header");
        	else if(CRManagementService.Complete.equalsIgnoreCase(_action))
	        	summaryMsg = msgs.getString("cr_man_complete_dialog_header");
        	else if(CRManagementService.Reject_Tech.equalsIgnoreCase(_action))
	        	summaryMsg = msgs.getString("cr_man_reject_dialog_header");
        	else if(CRManagementService.Reject_Def.equalsIgnoreCase(_action))
	        	summaryMsg = msgs.getString("cr_man_reject_dialog_header");
        	else
        		throw new Exception("Wrong Action to change Capacity Request: " + _action);
	
    		service.changeStatusCapacityRequest(_action, selected);
    	}
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = ve.getMessage();
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(ve.getMessage(), ve);
    		return;
		} 	
    	catch(Exception e){
			summaryMsg = msgs.getString("saving_data_error");
        	errorMsg = msgs.getString("update_error") + " " + msgs.getString("cr_man_capacity_request") + " " +
					msgs.getString("with_id") + selected.getId();
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}

    	String okMsg = msgs.getString("cr_man_cr_updated") + " " + selected.getRequestCode();    	
    	messages.addMessage(Constants.head_menu[1],
				new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
    	log.info(okMsg);
    	sendMail(_action);
    	
    	//offshore
    	filters.setIdn_system(getChangeSystemView().getIdn_active());
    	
    	
    	// Se actualiza la vista.
		backFileter.setFilter(filters);
    	items = service.search(filters); 
    }


	public void getFile(CapacityRequestBean _capReq) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = msgs.getString("cr_man_xlsFile");
    	String errorMsg = null;
    	
    	try {
    		service.getFileByOpFileId(_capReq);
    	}
    	catch(ValidationException ve){
    		errorMsg = msgs.getString("download_error") + " " + _capReq.getXlsFileName() + ": " + ve.getMessage();
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(ve.getMessage(), ve);
    		return;    		
    	}
    	catch(Exception e){
    		errorMsg = msgs.getString("download_error") + " " + _capReq.getXlsFileName();
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}
	}


	public void selectAdditionalDocs(CapacityRequestBean _capReq){
		service.selectAdditionalDocs(_capReq);

        // No hace falta resetear newDocument porque se resetea en createNewDocFile().
    	// newDocument = new ContractAttachmentBean();	
	}


	// Se genera el nuevo documento al cargar el dialogo addDocDlg, para que en ese dialogo este
	// disponible para anadirle comentarios y el tipo de documento.
	public void createNewDocFile() {
    	// Se descarta el documento nuevo que se hubiera generado hasta ahora y se crea un nuevo.
    	newDocument = new ContractAttachmentBean();
    	newDocument.setContractRequestId(selected.getId());
    	// Inicialmente se asigna el primer elemento de la lista de tipos de contratos del combo 
    	// que se muestra al usuario. Si el usuario cambia el valor, se actualiza el bean newDocument.
    	newDocument.setContractAttachTypeId(lDocTypes.get(0).getContractAttachTypeId());
	}


	public void handleDocFileUpload( FileUploadEvent event) {

		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	UploadedFile uUploadedFile = null;
    	String summaryMsg = null;
    	String errorMsg = null;
		
		uUploadedFile = event.getFile();
		if(uUploadedFile!=null){
			newDocument.setFileName(uUploadedFile.getFileName());
			newDocument.setBinaryData(uUploadedFile.getContents());
			// Los comentarios (opcional) y el identificador de tipo de documento, lo rellena el usuario en el dialogo addDoc.
			// Por ahora no hace falta completar en newDocument el campo attachType.
		} else {
	    	summaryMsg = msgs.getString("cr_man_additional_doc");			
    		errorMsg = msgs.getString("file_must_selected");
			messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
		}

		try {
			service.insertContractAttachment(selected, newDocument);
			
			summaryMsg = msgs.getString("cr_man_capacity_request");
	    	String okMsg = msgs.getString("cr_man_capacity_request") + " " +
	    					msgs.getString("with") + " " +
	    					msgs.getString("cr_man_additional_doc") + ".";    	
	    	messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
	    	log.info(okMsg);
		} 
    	catch(ValidationException ve){
			summaryMsg = msgs.getString("saving_data_error");
    		errorMsg = msgs.getString("saving_error") + ": " + ve.getMessage();
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(ve.getMessage(), ve);
    	}
		catch (Exception e) {
			summaryMsg = msgs.getString("saving_data_error");
    		errorMsg = msgs.getString("saving_error") + ": " + msgs.getString("internal_error");
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
    		// Se guarda el detalle del error tecnico.
	    	log.error(e.getMessage(), e);			
		}
		
		// Se refresca la lista de documentos asociados.
		selectAdditionalDocs(selected);
    }


	public void getDocFile(ContractAttachmentBean _contracDoc) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	String summaryMsg = msgs.getString("cr_man_downloading_add_doc");
    	String errorMsg = null;
    	
    	try {
			service.getDocFile(_contracDoc);    	
    	}
    	catch(ValidationException ve){
    		errorMsg = msgs.getString("download_error") + " " + _contracDoc.getFileName() + ": " + ve.getMessage();
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(ve.getMessage(), ve);
    		return;
    	}
    	catch(Exception e){
    		errorMsg = msgs.getString("download_error") + " " + _contracDoc.getFileName();
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}
	}

	
	public void onConditionalDeleteDocFile(ContractAttachmentBean _contracDoc){
		
		//Se guarda el documento a borrar en espera de que se ejecute el metodo de borrado, tras haberlo confirmado el usuario.
		documentToDelete = _contracDoc;
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('w_confirmDeleteDocDlg').show()");
	}


	public void deleteDocFile() {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	String summaryMsg = msgs.getString("cr_man_deleting_add_doc");
    	String errorMsg = null;
    	
    	try {
			service.deleteDocFile(documentToDelete);

			summaryMsg = msgs.getString("cr_man_deleting_add_doc");
	    	String okMsg = msgs.getString("cr_man_additional_doc") + " " +
	    					msgs.getString("deleted");
	    	messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
	    	log.info(okMsg);
    	}
    	catch(ValidationException ve){
    		errorMsg = msgs.getString("delete_error") + " " + documentToDelete.getFileName() + ": " + ve.getMessage();
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));   		
	    	log.error(ve.getMessage(), ve);
    	}
    	catch(Exception e){
    		errorMsg = msgs.getString("delete_error") + " " + documentToDelete.getFileName();
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);    		
    	}
    	
		// Se refresca la lista de documentos asociados.
		selectAdditionalDocs(selected);
	}
	
	public void getSignedContractTemplateFile(CapacityRequestBean _capReq) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = msgs.getString("cr_man_signed_contract_template");
    	String errorMsg = null;
    	
    	try {
    		service.generateSignedContractTemplate(_capReq);
    	}
    	catch(ValidationException ve){
    		errorMsg = msgs.getString("download_error") + " " + _capReq.getSignedContTemplDocxFileName() + ": " + ve.getMessage();
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(ve.getMessage(), ve);
    		return;
    	}
    	catch(Exception e){
    		errorMsg = msgs.getString("download_error") + " " + _capReq.getSignedContTemplDocxFileName();
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}
	}

	public CRMFilterCtl getBackFileter() {
		return backFileter;
	}

	public void setBackFileter(CRMFilterCtl backFileter) {
		this.backFileter = backFileter;
	}
	
	 public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
	 
	 public void sendMail(String _action) {
		HashMap<String,String> values = new HashMap<String,String>();
		String type = "";
		String texto = "";
		
		if(CRManagementService.Accept.equalsIgnoreCase(_action)) {
			values.put("1", selected.getShipperCode());
			values.put("2", selected.getRequestCode());
			values.put("3", selected.getContractCode());
			values.put("4", selected.getContractTypeCode());
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String dateStringStart = df.format(selected.getContractStartDate());
			String dateStringEnd = df.format(selected.getContractEndDate());
			values.put("5", dateStringStart);
			values.put("6", dateStringEnd);
			type = "CAP_REQUEST.ACCEPT";
			texto = "Shipper:"+values.get("1")+",Request Id:"+values.get("2")+",Contract Id:"+values.get("3")+",Contract type:"+values.get("4")+
					",Start Date:"+values.get("5")+",End Date:"+values.get("6");
		}
        	
    	else if(CRManagementService.Complete.equalsIgnoreCase(_action)) {
			values.put("1", selected.getContractId().toString());
			values.put("2", selected.getContractTypeCode());
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String dateStringStart = df.format(selected.getContractStartDate());
			String dateStringEnd = df.format(selected.getContractEndDate());
			values.put("3", dateStringStart);
			values.put("4", dateStringEnd);
			type = "CAP_REQUEST.COMPLETE";
			texto = "Contract Id:"+values.get("1")+",Contract type:"+values.get("2")+
					",Start Date:"+values.get("3")+",End Date:"+values.get("4");
    	}
    	else if(CRManagementService.Reject_Tech.equalsIgnoreCase(_action) 
    			|| CRManagementService.Reject_Def.equalsIgnoreCase(_action))
    	{
    		values.put("1", selected.getShipperCode());
			values.put("2", selected.getRequestCode());
			values.put("3", selected.getContractTypeCode());
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String dateStringStart = df.format(selected.getContractStartDate());
			String dateStringEnd = df.format(selected.getContractEndDate());
			values.put("4", dateStringStart);
			values.put("5", dateStringEnd);
			type = "CAP_REQUEST.REJECT";
			texto = "Shipper:"+values.get("1")+",Request Id:"+values.get("2")+",Contract type:"+values.get("3")+
					",Start Date:"+values.get("4")+",End Date:"+values.get("5");
    	}
		
		mailService.sendEmail(type, values, getChangeSystemView().getIdn_active(), selected.getShipperId());
		//Para comprobar los valores que se pasan en el email
		messages.addMessage(Constants.head_menu[1],
				new MessageBean(Constants.INFO, "Mail values", texto, Calendar.getInstance().getTime()));
	}
}
