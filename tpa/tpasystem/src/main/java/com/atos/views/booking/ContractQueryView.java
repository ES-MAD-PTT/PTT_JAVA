package com.atos.views.booking;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

import com.atos.beans.MessageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.booking.CRMFilterCtl;
import com.atos.beans.booking.ContractAttachTypeBean;
import com.atos.beans.booking.ContractAttachmentBean;
import com.atos.beans.booking.ContractQueryBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.booking.CRManagementFilter;
import com.atos.services.booking.ContractQueryService;
import com.atos.utils.Constants;
import com.atos.utils.DateUtil;
import com.atos.views.CommonView;

@ManagedBean(name="ContractQueryView")
@ViewScoped
public class ContractQueryView extends CommonView  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2379904021154751481L;
	
	private static BigDecimal[] abdAllContractTypeIds;

	private CRManagementFilter filters;
	private List<ContractQueryBean> items;
	private ContractQueryBean selected;
	// Para controlar el plazo limite para poder dar viabilidad a una request.
	private Calendar limitContStartDate;
	// La etiqueta para el boton de Reject sera distinta si el usuario es shipper u operador.
	private String rejectButtonLabel;

	// Listado de todos los tipos de documentos posibles.
	private List<ContractAttachTypeBean> lDocTypes;
	// Listado de documentos que puede ver un shipper. En principio solo "BANK_GUARANTEE".
	private List<ContractAttachTypeBean> lShipperDocTypes;
	// Para anadir un documento nuevo a la capacityRequest seleccionada.
	private ContractAttachmentBean newDocument;
	// Este documento no sera visible desde la pantalla. Solo se usa para guardar un objeto entre
	// llamadas de dos metodos.
	private ContractAttachmentBean documentToDelete;
	
	private static final Logger log = LogManager.getLogger("com.atos.views.booking.ContractQueryView");
	
	@ManagedProperty("#{ContractQueryService}")
    transient private ContractQueryService service;
	@ManagedProperty("#{filterCtl}")
	private CRMFilterCtl backFileter;
    
	public void setService(ContractQueryService service) {
		this.service = service;
	}
	
	//geters/seters
	public CRManagementFilter getFilters() {
		return filters;
	}

	public void setFilters(CRManagementFilter filters) {
		this.filters = filters;
	}

	public List<ContractQueryBean> getItems() {
		return items;
	}

	public void setItems(List<ContractQueryBean> items) {
		this.items = items;
	}

	public ContractQueryBean getSelected() {
		return selected;
	}

	public void setSelected(ContractQueryBean selected) {
		this.selected = selected;
	}

	private void setRejectButtonLabel() {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		if( !isShipper() )
			this.rejectButtonLabel = msgs.getString("cr_man_reject");
		else
			this.rejectButtonLabel = msgs.getString("delete");
	}
	
	public String getRejectButtonLabel() {
		return rejectButtonLabel;
	}

	public ContractAttachmentBean getNewDocument() {
		return newDocument;
	}

	public void setNewDocument(ContractAttachmentBean newDocument) {
		this.newDocument = newDocument;
	}

	@PostConstruct
    public void init() {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		setRejectButtonLabel();
		
    	// Se requiere que en la pantalla de inicio se ponga por defecto todos los posibles tipos de contratos.
    	Object[] tmpObjectArray = getContractTypes().keySet().toArray();
    	abdAllContractTypeIds = Arrays.copyOf(tmpObjectArray, tmpObjectArray.length, BigDecimal[].class);
    	
    	initFilters();
    	
        items = service.search(filters);
        
        selected = new ContractQueryBean();
        
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
		lShipperDocTypes = new ArrayList<ContractAttachTypeBean>();
		int iBankGuaranteeTypePosition = -1;
		if( lDocTypes != null ) {
			for(int i=0; i<lDocTypes.size(); i++) {
				if( lDocTypes.get(i) != null &&
						ContractAttachTypeBean.BANK_GUARANTEE.equalsIgnoreCase(lDocTypes.get(i).getTypeCode())) {
					lShipperDocTypes.add(lDocTypes.get(i));
					iBankGuaranteeTypePosition = i;
				}
			}
		} else {
			log.error(msgs.getString("cr_man_doc_type_list_not_available"));
		}

		// Se fuerza a que Bank Guarantee sea el primer elemento de la lista lDocTypes. 
		// Porque desde BD podria tener otro orden, e interesa que cuando se cree newDocument ( createNewDocFile() ), 
		// por defecto sea el primer elemento del combo.
		if(iBankGuaranteeTypePosition != -1) {  // Si se recupero BANK_GUARANTEE de la BD.
			if(iBankGuaranteeTypePosition != 0) {  // Si BANK_GUARANTEE no esta en la primera posicion.
				ContractAttachTypeBean tmpBankGuaranteeType = lDocTypes.get(iBankGuaranteeTypePosition);
				ContractAttachTypeBean tmpTypeInPositionCero = lDocTypes.get(0);
				lDocTypes.set(0, tmpBankGuaranteeType);
				lDocTypes.set(iBankGuaranteeTypePosition, tmpTypeInPositionCero);
			}
		}
		
    	newDocument = new ContractAttachmentBean();
    }	
	
	private void initFilters() {
		filters = new CRManagementFilter();
		if (isShipper())
			filters.setShipperId(getUser().getIdn_user_group());
		filters.setEndDate(DateUtil.getToday());
		filters.setStartDate(DateUtil.getToday());

		filters.setContractTypeId(abdAllContractTypeIds);
		// Se requiere que en la pantalla de inicio se ponga por defecto el
		// filtro de submitted.
		filters.setStatus(new String[] { Constants.SUBMITTED });

		if (backFileter.isReturning()) {
			filters = backFileter.getFilter();
			backFileter.setReturning(false);
		}

		// offshore
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
		backFileter.setOrigin(CRMFilterCtl.ORIGING_CAP_QUERY);
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
    	    	getMessages().addMessage(Constants.head_menu[1],
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
    	
        items = service.search(filters);
        // En cada busqueda se resetea la fila seleccionada.
        selected = new ContractQueryBean();
		backFileter.setFilter(filters);
	}
	
	public void onClear(){
		initFilters();
		backFileter.setFilter(filters);
		
        if (items != null) {
            items.clear();
        }
		
        items = service.search(filters);
        // En cada busqueda se resetea la fila seleccionada.
        selected = new ContractQueryBean();
    }



	public void getFile(ContractQueryBean _capReq) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = msgs.getString("cr_man_xlsFile");
    	String errorMsg = null;    	
    	
    	try {
    		service.getFileByOpFileId(_capReq);
    	}
    	catch(ValidationException ve){
    		errorMsg = msgs.getString("download_error") + " " + _capReq.getXlsFileName() + ": " + ve.getMessage();
    		getMessages().addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;    		
    	}
    	catch(Exception e){
    		errorMsg = msgs.getString("download_error") + " " + _capReq.getXlsFileName();
    		getMessages().addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}
	}

	
	public List<ContractAttachTypeBean> getDocTypesList() {

		if(!isShipper())
			return lDocTypes;
		else
			return lShipperDocTypes;
	}

	
	public void selectAdditionalDocs(ContractQueryBean _capReq){
		
		if(!isShipper())
			service.selectAdditionalDocs(_capReq);
		else
			service.selectAdditionalDocsBankGuarantee(_capReq);
		
        // No hace falta resetear newDocument porque se resetea en createNewDocFile().
    	// newDocument = new ContractAttachmentBean();	
	}




	public CRMFilterCtl getBackFileter() {
		return backFileter;
	}

	public void setBackFileter(CRMFilterCtl backFileter) {
		this.backFileter = backFileter;
	}
	
	public int getItemsSize() {
		if (this.items != null && !this.items.isEmpty()) {
			return this.items.size();
		} else {
			return 0;
		}
	}
}
