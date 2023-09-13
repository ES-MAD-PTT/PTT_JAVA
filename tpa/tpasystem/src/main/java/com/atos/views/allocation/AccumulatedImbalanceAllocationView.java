package com.atos.views.allocation;

import java.math.BigDecimal;
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
import org.primefaces.event.RowEditEvent;

import com.atos.beans.ErrorBean;
import com.atos.beans.MessageBean;
import com.atos.beans.allocation.AccumulatedImbalanceAllocationBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.allocation.AccumulatedImbalanceAllocationFilter;
import com.atos.filters.dam.AreaFilter;
import com.atos.services.allocation.AccumulatedImbalanceAllocationService;
import com.atos.services.allocation.AllocationManagementService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;


@ManagedBean(name = "accumulatedImbalanceAlloc")
@ViewScoped
public class AccumulatedImbalanceAllocationView extends CommonView {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5217506561928919769L;

	private static final Logger log = LogManager.getLogger(AccumulatedImbalanceAllocationView.class);

	@ManagedProperty("#{accumulatedImbalanceAllocationService}")
    transient private AccumulatedImbalanceAllocationService service;
    
    public void setService(AccumulatedImbalanceAllocationService service) {
        this.service = service;
    }
 
	@ManagedProperty("#{AllocationManagService}")
	transient private AllocationManagementService allocMngservice;
	
	public void setallocMngservice(AllocationManagementService service) {
        this.allocMngservice = service;
    }
    
    private AccumulatedImbalanceAllocationFilter filters;
	private List<AccumulatedImbalanceAllocationBean> items;

	private Date responsePeriodStartDate = null;
	private Date responsePeriodEndDate = null;
	private Integer allocationMaxDateOffset = null;
	private Date openPeriodFirstDay = null;

	
	
	public AccumulatedImbalanceAllocationFilter getFilters() {
		return filters;
	}

	public void setfilters(AccumulatedImbalanceAllocationFilter filters) {
		this.filters = filters;
	}

	public List<AccumulatedImbalanceAllocationBean> getItems() {
		return items;
	}

	public void setItems(List<AccumulatedImbalanceAllocationBean> items) {
		this.items = items;
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

	@PostConstruct
	public void init() {
		filters = new AccumulatedImbalanceAllocationFilter();
		filters.setIsShipper(getIsShipper());		
		if (getIsShipper()) {
			filters.setShipperId(getUser().getIdn_user_group());
		}
		try {
			allocationMaxDateOffset = allocMngservice.selectAllocationMaxDateOffset(getUser().getUsername(),
					getLanguage().getLocale());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		setResponsePeriod(allocationMaxDateOffset);
		
	}
	private void setResponsePeriod(Integer _allocationMaxDateOffset) {
		// Primer dia de balance abierto.

		HashMap<String, Object> params = new HashMap<>();
		params.put("closingTypeCode", "DEFINITIVE");
		params.put("idnSystem", getChangeSystemView().getIdn_active());
		params.put("sysCode", getChangeSystemView().getSystem());
		openPeriodFirstDay = allocMngservice.selectOpenPeriodFirstDay(params);

		Calendar tmpEndDate = Calendar.getInstance();
		tmpEndDate.set(Calendar.HOUR_OF_DAY, 0);
		tmpEndDate.set(Calendar.MINUTE, 0);
		tmpEndDate.set(Calendar.SECOND, 0);
		tmpEndDate.set(Calendar.MILLISECOND, 0);
		tmpEndDate.add(Calendar.DAY_OF_MONTH, _allocationMaxDateOffset * (-1));

		responsePeriodStartDate = openPeriodFirstDay;
		responsePeriodEndDate = tmpEndDate.getTime();
	}

	public void onExecute() {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		String summaryMsg = getChangeSystemView().getSystem() + ": "
				+ msgs.getString("adjust_alloc_execution_dialog_header");

		String errorMsg = null;

		try {
			allocMngservice.allocationAndBalance(responsePeriodStartDate, responsePeriodEndDate, getUser(),
					getLanguage(), getChangeSystemView().getIdn_active());

			String okMsg = msgs.getString("met_man_processing");
			getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
			log.info(okMsg);
		} catch (ValidationException ve) {
			errorMsg = ve.getMessage();
			getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
		} catch (Exception e) {
			errorMsg = msgs.getString("internal_error");
			getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			// Se guarda el detalle del error tecnico.
			log.error(e.getMessage(), e);
		}

		
	}

	public void onClear() {
			filters = new AccumulatedImbalanceAllocationFilter();
			
	        if (items != null) {
	            items.clear();
	        }
	        
	        RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("PF('w_accumulatedImbalanceallocations1').clearFilters()");
	}

	public Map<BigDecimal, Object> getShipperId() {
		return service.selectShipperId();
	}
	public Map<BigDecimal, Object> getZoneId() {
		return service.getZoneIds(getChangeSystemView().getIdn_active());
	}

	public void onSearch() {
		String msgVal = validateForm();
		if (msgVal != null) {
			addMessage("filters_error", msgVal, Constants.ERROR);
		} else {
			try {
				filters.setIdn_active_system(getChangeSystemView().getIdn_active());
				items = service.search(filters);
			} catch (Exception e) {
				log.error("error searching monthly bal: " + e.getMessage(), e);
				addMessage("adjust_alloc_search_error", "adjust_alloc_internal_error", Constants.ERROR);
			}
		}
	}

	private String validateForm() {
		String result = null;
		if (filters.getStartDate() == null) {
			result = "adjust_alloc_start_required";
			return result;
		}
		if (filters.getEndDate() == null) {
			result = "adjust_alloc_end_required";
			return result;
		}
		if (filters.getStartDate().after(filters.getEndDate())) {
			result = "adjust_alloc_date_before";
			return result;
		}
		if(!getIsShipper() && filters.getShipperId()==null ) {
			result = "adjust_alloc_shipper_required";
			return result;
		}
			
		return result;
	}
	
	public boolean getIsShipper() {
		return getUser().isUser_type(Constants.SHIPPER);
	}

    public boolean renderItemEditor(AccumulatedImbalanceAllocationBean item){
 		
    	if(item.getIsEditable().equals("Y")) {
    		return true;
    	} else {
    		return false;
    	}
	
    }

	public void onRowEdit(RowEditEvent event) {
		
		AccumulatedImbalanceAllocationBean bean = (AccumulatedImbalanceAllocationBean)event.getObject();
		//MMCF He cambiado en la comprobación getImbalanceCorrection por getAccImbalanceMonthCorrection
		if(bean.getAccImbalanceMonthCorrection()==null) {
			addMessage("adjust_alloc_imbalance_correction_no_value", "adjust_alloc_imbalance_correction_empty", Constants.ERROR);
			return;
		}
		
		bean.setUser(getUser().getUsername());
		bean.setLanguage(getLanguage().getLocale());
		bean.setIdn_active_system(getChangeSystemView().getIdn_active());
		BigDecimal idnNomConcept = service.getNomConcept("Alloc_acc_month_adjust");
		bean.setIdnNominationConcept(idnNomConcept);
		
		ErrorBean error = service.getImbalanceCorrection(bean);
		
		if(error.getError_code().intValue()==0 && error.getError_msg().equals("OK")) {
			addMessage("adjust_alloc_imbalance_correction_correct", "adjust_alloc_imbalance_correction_correct", Constants.INFO);
		} else {
			addMessage("adjust_alloc_imbalance_correction_incorrect", error.getError_msg(), Constants.ERROR);
		}
		
		onSearch();
		return;
		
		
	}
    public void onRowCancel(RowEditEvent event) {
    	AccumulatedImbalanceAllocationBean bean = (AccumulatedImbalanceAllocationBean)event.getObject();
    	//MMCF He cambiado setImbalanceCorrection(null) por setAccImbalanceMonthCorrection(null)
    	bean.setAccImbalanceMonthCorrection(null);
    }

}
