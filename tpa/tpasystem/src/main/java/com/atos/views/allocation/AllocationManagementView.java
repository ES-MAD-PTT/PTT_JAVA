package com.atos.views.allocation;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import com.atos.beans.MessageBean;
import com.atos.beans.allocation.AllocationBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.allocation.AllocationManagementFilter;
import com.atos.services.MailService;
import com.atos.services.allocation.AllocationManagementService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="AllocationManagView")
@ViewScoped
public class AllocationManagementView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5980784834639303828L;
	private static final String EnergyUnit = "MMBTU/D";					// Unidad de medida de la pagina.
	private static final String strIsWarnedN="N";
	private BigDecimal factorFromDefaultUnit = null;
	private BigDecimal factorToDefaultUnit = null;
	
	private AllocationManagementFilter filters;
	private List<AllocationBean> items;
	private List<AllocationBean> selectedItems;
	private String acceptWarnings; 		// For warnings in accept validation in DB.
	// Por rendimiento, openPeriodFistDay a AllocationMaxDateOffset, solo se consultan 1 vez durante la vista.
	// Se comprobaran antes de guardar datos de reviews.
	private Date openPeriodFirstDay = null;
	private Integer allocationMaxDateOffset = null;
	// Estas fechas se utilizan para validar dia de gas, pintar el intervalo de ejecucion de repartos y balances
	// y en notificaciones.
	private Date responsePeriodStartDate = null;
	private Date responsePeriodEndDate = null;
	private static final Logger log = LogManager.getLogger("com.atos.views.allocation.AllocationManagementView");
	
	
	@ManagedProperty("#{AllocationManagService}")
    transient private AllocationManagementService service;
    
	public void setService(AllocationManagementService service) {
		this.service = service;
	}
	
	@ManagedProperty("#{mailService}")
    transient private MailService mailService;

    public void setMailService(MailService mailService) {
    	this.mailService = mailService;
    }
	
	//geters/seters
	public AllocationManagementFilter getFilters() {
		return filters;
	}

	public void setFilters(AllocationManagementFilter filters) {
		this.filters = filters;
	}
	
	public List<AllocationBean> getItems() {
		return items;
	}

	public void setItems(List<AllocationBean> items) {
		this.items = items;
	}
	
	public List<AllocationBean> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(List<AllocationBean> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public String getAcceptWarnings() {
		return acceptWarnings;
	}

	public void setAcceptWarnings(String acceptWarnings) {
		this.acceptWarnings = acceptWarnings;
	}

	public Date getResponsePeriodStartDate() {
		return responsePeriodStartDate;
	}

	public void setResponsePeriodStartDate(Date responsePeriodStartDate) {
		this.responsePeriodStartDate = responsePeriodStartDate;
	}

	public Date getResponsePeriodEndDate() {
		return responsePeriodEndDate;
	}

	public void setResponsePeriodEndDate(Date responsePeriodEndDate) {
		this.responsePeriodEndDate = responsePeriodEndDate;
	}

	public Float getAllocationReviewMaxPercentChange(Date gasDay) {
		Float tmpFloat = null;
		try {
			tmpFloat = service.selectAllocationReviewMaxPercentChange(gasDay, getUser().getUsername(),
					getLanguage().getLocale(), getChangeSystemView().getSystem());
		}
		catch(Exception e){
			log.error(e.getMessage(), e);
		}
		
		return tmpFloat;
	}

	// Este booleano marcara si esta habilitado el filtro de shipper (isOperator = true) o esta deshabilitado, 
	// con un shipper fijo.
	public boolean getIsShipper() {
		return getUser().isUser_type(Constants.SHIPPER);
	}
	
	@PostConstruct
    public void init() {
		factorFromDefaultUnit = service.selectFactorFromDefaultUnit(EnergyUnit);
		factorToDefaultUnit = service.selectFactorToDefaultUnit(EnergyUnit);
    	try{
    		allocationMaxDateOffset = service.selectAllocationMaxDateOffset(getUser().getUsername(), getLanguage().getLocale());
		}
		catch(Exception e){
			log.error(e.getMessage(), e);
		}
		setResponsePeriod(allocationMaxDateOffset);
		filters = initFilter(allocationMaxDateOffset, factorFromDefaultUnit);
        items = service.search(filters);
        selectedItems =  new ArrayList<AllocationBean>();
	}

	private AllocationManagementFilter initFilter(Integer _allocationMaxDateOffset, BigDecimal _factorFromDefaultUnit){
		AllocationManagementFilter tmpFilter = new AllocationManagementFilter();
		Calendar tmpToday = Calendar.getInstance();
		tmpToday.set(Calendar.HOUR_OF_DAY, 0);
		tmpToday.set(Calendar.MINUTE, 0);
		tmpToday.set(Calendar.SECOND, 0);
		tmpToday.set(Calendar.MILLISECOND, 0);
		tmpToday.add(Calendar.DAY_OF_MONTH, _allocationMaxDateOffset * (-1));
		tmpFilter.setGasDay(tmpToday.getTime());
		// Si no es operador, se fija el filtro al shipper del usuario.
		if(getIsShipper())
			tmpFilter.setShipperId(getUser().getIdn_user_group());
		tmpFilter.setStatusCode(new String[]{Constants.NOT_REVIEWED,
											Constants.INITIAL,
											Constants.ACCEPTED,
											Constants.REJECTED,
											Constants.ALLOCATED});
		tmpFilter.setFactorFromDefaultUnit(_factorFromDefaultUnit);
		
		tmpFilter.setSystemId(getChangeSystemView().getIdn_active());
		
		return tmpFilter;
	}
	
	// periodo [primer_dia_abierto; Hoy - allocationMaxDateOffset] 
	private void setResponsePeriod(Integer _allocationMaxDateOffset){
		// Primer dia de balance abierto.

		HashMap<String, Object> params = new HashMap<>();
		params.put("closingTypeCode", "DEFINITIVE");
		params.put("idnSystem", getChangeSystemView().getIdn_active());
		params.put("sysCode", getChangeSystemView().getSystem());
		openPeriodFirstDay = service.selectOpenPeriodFirstDay(params);

		Calendar tmpEndDate = Calendar.getInstance();
		tmpEndDate.set(Calendar.HOUR_OF_DAY, 0);
		tmpEndDate.set(Calendar.MINUTE, 0);
		tmpEndDate.set(Calendar.SECOND, 0);
		tmpEndDate.set(Calendar.MILLISECOND, 0);
		tmpEndDate.add(Calendar.DAY_OF_MONTH, _allocationMaxDateOffset * (-1));
		
		responsePeriodStartDate = openPeriodFirstDay;
		responsePeriodEndDate = tmpEndDate.getTime();
	}
	
	private boolean inResponsePeriod(Date gasDay){
		return (responsePeriodStartDate.compareTo(gasDay)<=0 && 
				responsePeriodEndDate.compareTo(gasDay)>=0); 
	}
	
	public boolean mayBeResponded(AllocationBean _alBean){
		if(_alBean==null)
			return false;
		
		// Si no existe review, no se puede responder.
		if(_alBean.getStatusCode()==null)
			return false;
				
		// Si el estado no es INITIAL o ACCEPTED o ALLOCATED, no se permite cambiar.
		if(_alBean.getStatusCode()!=null && 
				!(Constants.INITIAL.equalsIgnoreCase(_alBean.getStatusCode()) ||
					Constants.ACCEPTED.equalsIgnoreCase(_alBean.getStatusCode()) ||
					Constants.REJECTED.equalsIgnoreCase(_alBean.getStatusCode()) ))
			return false;
		
		return (inResponsePeriod(_alBean.getGasDay()));
	}
	
	
	// Para los elementos del combo del filtro de shippers.
	public Map<BigDecimal, Object> getShipperIds() {
		return service.selectShipperId();
	}	
	
	// Para los elementos del combo del filtro de contratos.
	public Map<BigDecimal, Object> getContractIds() {
		return service.selectContractId(filters);
	}
	
	// Para los elementos del combo del filtro de puntos.
	public Map<BigDecimal, Object> getPointIds() {
		
		Map<BigDecimal, Object> tmp = service.selectPointId(filters.getSystemId());
		return tmp;
	}
	
	public void onSearch(){
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	if(filters.getGasDay()==null){
	    	getMessages().addMessage(Constants.head_menu[8],
	    			new MessageBean(Constants.ERROR,
	    					msgs.getString("validation_error"), 
							msgs.getString("all_rev_gas_day_empty_error"), 
							Calendar.getInstance().getTime()));
	    	log.error("Gas Day must be selected.");
	    	return;
    	}
    	
        items = service.search(filters);
        // En cada busqueda se resetea las filas seleccionada.
        selectedItems =  new ArrayList<AllocationBean>();
    }

	public void onClear(){
		filters = initFilter(allocationMaxDateOffset, factorFromDefaultUnit);
	  	
        if (items != null) {
            items.clear();
        }
        items = service.search(filters);
        // En cada busqueda se resetea las filas seleccionada.
        selectedItems =  new ArrayList<AllocationBean>();
    }
	
	public void onAccept(boolean _afterConfirm){
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		String lineSeparator = System.getProperty("line.separator");
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    	String summaryMsg = null;
    	String errorMsg = null;
    	String warnings = null;		// Para guardar warnings procedentes de la validacion en BD.
		
    	try {
			HashMap<String, Object> params = new HashMap<>();
			params.put("closingTypeCode", "DEFINITIVE");
			params.put("idnSystem", getChangeSystemView().getIdn_active());
			params.put("sysCode", getChangeSystemView().getSystem());
			warnings = service.acceptReview(selectedItems, _afterConfirm, getUser(), getLanguage(), factorToDefaultUnit,
					getChangeSystemView().getIdn_active(), params);
    		// Si hay warnings, no se han guardado datos, y se muestra mensaje al usuario para que vuelva a Acceptar.
    		if((warnings != null) && (!"".equalsIgnoreCase(warnings))){
    			this.acceptWarnings = warnings;
    			RequestContext context = RequestContext.getCurrentInstance();
    			context.update("confirmAcceptFormId");
    			context.execute("PF('w_confirmAcceptDlg').show()");
    			return;
    		}
    	}
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = ve.getMessage();
    		getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;
		} 	
    	catch(Exception e){
			summaryMsg = msgs.getString("saving_data_error");
        	errorMsg = msgs.getString("update_error") + " " + msgs.getString("all_rev_allocation_review");
        	getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}
    	
    	summaryMsg = msgs.getString("all_rev_allocation_review") + " " + msgs.getString("updated");
    	String okMsg = msgs.getString("all_man_allocation_accepted") + lineSeparator;
		for(AllocationBean allocation: selectedItems){
			okMsg += "[" + allocation.getReviewCode() + " (" +
					sdf.format(allocation.getGasDay()) + "," +
					allocation.getShipperCode() + "," +
					allocation.getContractCode() + "," +
					allocation.getNomPointCode() + ")]" + 
					lineSeparator;
			sendMail(allocation, Constants.ACCEPT);
		}
		
		getMessages().addMessage(Constants.head_menu[8],
				new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
    	log.info(okMsg);

    	// Se actualiza la vista.
    	items = service.search(filters); 		
	}
	
	public void onDiscardAccept(){
		// NO HACE FALTA ESTE PASO, PORQUE YA NO SE GUARDA EL CAMPO IS_WARNED.
		// En este paso, los datos de la vista dejan de ser iguales a los registros en BD, puesto que al dejar el flag por 
		// defecto se pierde si en la ultima version de la review, se había
		// avisado al usuario.
		// Pero no es problema porque al actualizar la BD, se hace un nuevo insert, nueva version y se vuelve a comprobar
		// si hay que avisar por warning al usuario. De este modo el nuevo registro en BD es coherente.
		//for(AllocationBean allocation: selectedItems)
		//	allocation.setIsWarned(strIsWarnedN);		// Se dejan todas las reviews de repartos con el flag por defecto.
	}
	
	public void onReject(){
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		String lineSeparator = System.getProperty("line.separator");
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    	String summaryMsg = null;
    	String errorMsg = null;
		
    	try {
			HashMap<String, Object> params = new HashMap<>();
			params.put("closingTypeCode", "DEFINITIVE");
			params.put("idnSystem", getChangeSystemView().getIdn_active());
			params.put("sysCode", getChangeSystemView().getSystem());
			service.rejectReview(selectedItems, getUser(), getLanguage(), factorToDefaultUnit, params);
    	}
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = ve.getMessage();
    		getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;
		} 	
    	catch(Exception e){
			summaryMsg = msgs.getString("saving_data_error");
        	errorMsg = msgs.getString("update_error") + " " + msgs.getString("all_rev_allocation_review");
        	getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}
    	
    	summaryMsg = msgs.getString("all_rev_allocation_review") + " " + msgs.getString("updated");
    	String okMsg = msgs.getString("all_man_allocation_rejected") + lineSeparator;
		for(AllocationBean allocation: selectedItems){
			okMsg += "[" + allocation.getReviewCode() + " (" +
					sdf.format(allocation.getGasDay()) + "," +
					allocation.getShipperCode() + "," +
					allocation.getContractCode() + "," +
					allocation.getNomPointCode() + ")]" + 
					lineSeparator;
			sendMail(allocation, Constants.REJECT);
		}
		
		getMessages().addMessage(Constants.head_menu[8],
				new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
    	log.info(okMsg);

    	// Se actualiza la vista.
    	items = service.search(filters);
	}

	public void onExecute(){
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		String summaryMsg = getChangeSystemView().getSystem() + ": "
				+ msgs.getString("all_man_execution_dialog_header");

    	String errorMsg = null;
    	
		try {
			service.allocationAndBalance(responsePeriodStartDate, responsePeriodEndDate, getUser(), getLanguage(),
					getChangeSystemView().getIdn_active());
			
	    	String okMsg = msgs.getString("met_man_processing");
	    	getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
	    	log.info(okMsg);
		} 
		catch(ValidationException ve){
			errorMsg = ve.getMessage();
			getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));    		
		}
		catch (Exception e) {
			errorMsg = msgs.getString("internal_error");
			getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			// Se guarda el detalle del error tecnico.
	    	log.error(e.getMessage(), e);			
		}
    	
		// Si se ejectua el proceso en background, no tiene sentido actualizar los datos.
        // onSearch();
	}
	
	
    
    public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
    
    public void sendMail(AllocationBean allocation, String action) {
		HashMap<String,String> values = new HashMap<String,String>();
		
		String type = "";
		
		if(action.equals(Constants.ACCEPT))
			type = "ALLOCATION.MANAGEMENT.ACCEPT";
		else if (action.equals(Constants.REJECT))
			type = "ALLOCATION.MANAGEMENT.REJECT";
	
		values.put("1", allocation.getShipperCode());
		values.put("2", allocation.getContractCode());
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	String dateString = df.format(allocation.getGasDay());
		values.put("3", dateString);
		values.put("4", allocation.getNomPointCode());
		
		mailService.sendEmail(type, values, getChangeSystemView().getIdn_active(), allocation.getShipperId());
		//Para comprobar los valores que se pasan en el email
		getMessages().addMessage(Constants.head_menu[8],
				new MessageBean(Constants.INFO, "Mail values", "Shipper"+values.get("1")+",Contrato:"+values.get("2")+",Fecha:"+values.get("3")+",Nom. Point:"+values.get("4")+". Destinatario:"+allocation.getShipperId(), Calendar.getInstance().getTime()));
	}
}
