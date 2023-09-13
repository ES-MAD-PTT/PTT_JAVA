package com.atos.views.allocation;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import org.primefaces.event.RowEditEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.allocation.AllocationBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.allocation.AllocationReviewSupplyEWFilter;
import com.atos.services.allocation.AllocationReviewSupplyEWService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="AllocReviewSupplyEWView")
@ViewScoped
public class AllocationReviewSupplyEWView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6669765129033877234L;
	private static final String EnergyUnit = "MMBTU/D";					// Unidad de medida de la pagina.
	private BigDecimal factorFromDefaultUnit = null;
	private BigDecimal factorToDefaultUnit = null;
	
	private AllocationReviewSupplyEWFilter filters;
	private List<AllocationBean> items;
	private AllocationBean selected;
	private Integer allocationMaxDateOffset = null;
	// Por rendimiento, openPeriodFistDay solo se consulta 1 vez durante la vista.
	// Se comprobara antes de guardar datos de reviews.
	private Date reviewPeriodStartDate = null;
	private Date reviewPeriodEndDate = null;
	private static final Logger log = LogManager.getLogger("com.atos.views.allocation.AllocationReviewSupplyEWView");
	
	
	@ManagedProperty("#{AllocationReviewSupplyEWService}")
    transient private AllocationReviewSupplyEWService service;
    
	public void setService(AllocationReviewSupplyEWService service) {
		this.service = service;
	}
	
	//geters/seters
	public AllocationReviewSupplyEWFilter getFilters() {
		return filters;
	}

	public void setFilters(AllocationReviewSupplyEWFilter filters) {
		this.filters = filters;
	}
	
	public List<AllocationBean> getItems() {
		return items;
	}

	public void setItems(List<AllocationBean> items) {
		this.items = items;
	}

	public AllocationBean getSelected() {
		return selected;
	}

	public void setSelected(AllocationBean selected) {
		this.selected = selected;
	}

	public Date getReviewPeriodStartDate() {
		return reviewPeriodStartDate;
	}

	public void setReviewPeriodStartDate(Date reviewPeriodStartDate) {
		this.reviewPeriodStartDate = reviewPeriodStartDate;
	}

	public Date getReviewPeriodEndDate() {
		return reviewPeriodEndDate;
	}

	public void setReviewPeriodEndDate(Date reviewPeriodEndDate) {
		this.reviewPeriodEndDate = reviewPeriodEndDate;
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
		setReviewPeriod(allocationMaxDateOffset);
		filters = initFilter(allocationMaxDateOffset, factorFromDefaultUnit);
        items = service.search(filters);
		selected = new AllocationBean();
	}

	private AllocationReviewSupplyEWFilter initFilter(Integer _allocationMaxDateOffset, BigDecimal _factorFromDefaultUnit){
		AllocationReviewSupplyEWFilter tmpFilter = new AllocationReviewSupplyEWFilter();
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
											Constants.ACCEPTED,
											Constants.ALLOCATED});
		
		tmpFilter.setFactorFromDefaultUnit(_factorFromDefaultUnit);
		
		tmpFilter.setSystemId(getChangeSystemView().getIdn_active());
		tmpFilter.setSystem("ONSHORE");
		
		return tmpFilter;
	}
	
	// periodo [primer_dia_abierto; Hoy - allocationMaxDateOffset] 
	private void setReviewPeriod(Integer _allocationMaxDateOffset){
		// Primer dia de balance abierto.
		HashMap<String, Object> params = new HashMap<>();
		params.put("idnSystem", getChangeSystemView().getIdn_active());
		params.put("closingTypeCode", "DEFINITIVE");
		params.put("sysCode", "ONSHORE");

		reviewPeriodStartDate = service.selectOpenPeriodFirstDay(params);
    	
		Calendar tmpEndDate = Calendar.getInstance();
		tmpEndDate.set(Calendar.HOUR_OF_DAY, 0);
		tmpEndDate.set(Calendar.MINUTE, 0);
		tmpEndDate.set(Calendar.SECOND, 0);
		tmpEndDate.set(Calendar.MILLISECOND, 0);
		tmpEndDate.add(Calendar.DAY_OF_MONTH, _allocationMaxDateOffset * (-1));
		reviewPeriodEndDate = tmpEndDate.getTime();
	}
	
	private boolean inReviewPeriod(Date gasDay){
		return (reviewPeriodStartDate.compareTo(gasDay)<=0 && 
				reviewPeriodEndDate.compareTo(gasDay)>=0); 
	}
	
	public boolean mayBeReviewed(AllocationBean _alBean){
		if(_alBean==null)
			return false;
		
		if(getIsShipper())
			return false;
		
		// Se permite modificar la review aunque el estado sea ACCEPTED, por si el operador se equivocara y quisiera corregir.
		//if(_alBean.getStatusCode()!=null && Constants.ACCEPTED.equalsIgnoreCase(_alBean.getStatusCode()))
		//	return false;
		
		return (inReviewPeriod(_alBean.getGasDay()));
	}
	
	
	// Para los elementos del combo del filtro de shippers.
	public Map<BigDecimal, Object> getShipperIds() {
		return service.selectShipperId();
	}	
	
	// Para los elementos del combo del filtro de contratos.
	public Map<BigDecimal, Object> getContractIds() {
		return service.selectContractId(filters);
	}
	
	// Para los elementos del combo del filtro de conceptos.
	public Map<BigDecimal, Object> getConceptIds() {
		return service.selectConceptId("ONSHORE");
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
        // En cada busqueda se resetea la fila seleccionada.
        selected = new AllocationBean();
    }

	public void onClear(){
		filters = initFilter(allocationMaxDateOffset, factorFromDefaultUnit);
	  	
        if (items != null) {
            items.clear();
        }
        items = service.search(filters);
        // En cada busqueda se resetea la fila seleccionada.
        selected = new AllocationBean();     
    }
	
	public void onRowEdit(RowEditEvent _event) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	// Etiquetas para sustituir en el mensaje de ok.
    	final String sDateLabel = "DDD";
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	final String sContractLabel = "CCC";
    	final String sZoneLabel = "ZZZ";
    	final String sConceptLabel = "YYY";
    	final String sReviewCodeLabel ="XXX";

    	String summaryMsg = null;
    	String errorMsg = null;

    	AllocationBean _allocation = (AllocationBean) _event.getObject();
		
    	try {

			HashMap<String, Object> params = new HashMap<>();
			params.put("sysCode", "ONSHORE");
			params.put("closingTypeCode", "DEFINITIVE");
			params.put("idnSystem", getChangeSystemView().getIdn_active());
			service.saveReview(_allocation, getUser(), getLanguage(), factorToDefaultUnit, params);
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
    	String okMsg = msgs.getString("all_rev_sup_allocation_reviewed");
    	okMsg = okMsg.replace(sDateLabel, sdf.format(_allocation.getGasDay()));
    	okMsg = okMsg.replace(sContractLabel, _allocation.getContractCode());
    	okMsg = okMsg.replace(sZoneLabel, _allocation.getZoneCode());
    	okMsg = okMsg.replace(sConceptLabel, _allocation.getNominationConceptDesc());
    	okMsg = okMsg.replace(sReviewCodeLabel, _allocation.getReviewCode());
   	
    	getMessages().addMessage(Constants.head_menu[8],
				new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
    	log.info(okMsg);

    	// Se actualiza la vista.
    	items = service.search(filters); 		
        // Se deja marcado (selected) el incidente que se acaba de editar. 
        selected = _allocation;
    }
	
	public void onExecute(){
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		String summaryMsg = getChangeSystemView().getSystem() + ": "
				+ msgs.getString("all_man_execution_dialog_header");
    	String errorMsg = null;
    	
		try {
			service.allocationAndBalance(reviewPeriodStartDate, reviewPeriodEndDate, getUser(), getLanguage(),
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
}
