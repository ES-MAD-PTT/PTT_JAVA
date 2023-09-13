package com.atos.views.allocation;

import java.math.BigDecimal;
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
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.atos.beans.MessageBean;
import com.atos.beans.allocation.AdjustBean;
import com.atos.beans.allocation.AdjustBeanQryResult;
import com.atos.beans.allocation.ContractBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.allocation.AllocationAdjustFilter;
import com.atos.services.allocation.AllocationAdjustService;
import com.atos.services.allocation.AllocationManagementService;
import com.atos.utils.Constants;
import com.atos.utils.DateUtil;
import com.atos.views.CommonView;

@ManagedBean(name = "allocMonth")
@ViewScoped
public class AllocationAdjustView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7459899697721259863L;
	private static final Logger log = LogManager.getLogger("com.atos.views.balancing.AllocationMonthlyAdjustement");
	@ManagedProperty("#{allocationAdjustService}")
	private transient AllocationAdjustService service;
	@ManagedProperty("#{AllocationManagService}")
	transient private AllocationManagementService allocMngservice;
	private AllocationAdjustFilter filter;
	private List<AdjustBeanQryResult> resultList;
	private Date responsePeriodStartDate = null;
	private Date responsePeriodEndDate = null;
	private Date openPeriodFirstDay = null;
	private Integer allocationMaxDateOffset = null;
	private AdjustBean newAdjust;
	private List<ContractBean> origContractsIds;
	private List<ContractBean> destContractsIds;

	public AllocationAdjustView() {
		super.setHead(8);
	}

	@PostConstruct
	public void init() {
		setNewAdjust(new AdjustBean());
		newAdjust.setMonthYear(getPreviousMonth());

		initFilter();
		try {
			allocationMaxDateOffset = allocMngservice.selectAllocationMaxDateOffset(getUser().getUsername(),
					getLanguage().getLocale());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		setResponsePeriod(allocationMaxDateOffset);
	}

	public void onSave() {
		if (validate()) {
			newAdjust.setUser(getUser().getUsername());
			newAdjust.setLanguage("en");
			try {
				AdjustBean result = service.save(newAdjust);
				if (result.getErrorCode() != 0) {
					addComposedMessage(getMessageResourceString("internal_error", null), result.getErrorDesc(),
							Constants.ERROR);

				}
				addMessage("alloc_report_save_ok", "alloc_report_save_ok", Constants.INFO);
				onSearch();

			} catch (Exception e) {

				addComposedMessage(getMessageResourceString("internal_error", null),
						"an error has occurred while saving data ", Constants.ERROR);
				log.error(e.getMessage(), e);
			}
		}

	}

	public String getShipperName() {
		return getUser().getUser_group_id();
	}

	private boolean validate() {
		boolean result = true;
		try {
			validatePresence(newAdjust.getMonthYear(), "res_bal_month");
			validatePresence(newAdjust.getOrigShipperId(), "alloc_adjust_origin_shipper");
			validatePresence(newAdjust.getOrigContractId(), "alloc_adjust_origin_contract");
			validatePresence(newAdjust.getOrigZone(), "alloc_adjust_origin_zone");

			validatePresence(newAdjust.getDestShipperId(), "alloc_adjust_destination_shipper");
			validatePresence(newAdjust.getDestContractId(), "alloc_adjust_destination_contract");
			validatePresence(newAdjust.getDestZone(), "alloc_adjust_destination_zone");
			validatePresence(newAdjust.getAdjustValue(), "alloc_adjust_value");

		} catch (ValidationException e) {
			result = false;

		} catch (Exception e) {

			addComposedMessage(getMessageResourceString("internal_error", null),
					"an error has occurred while validating data ", Constants.ERROR);
		}
		Calendar lastDayPreviousMonth = Calendar.getInstance();
		lastDayPreviousMonth.add(Calendar.MONTH, -1);
		int max = lastDayPreviousMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
		lastDayPreviousMonth.set(Calendar.DAY_OF_MONTH, max);
		if (newAdjust.getStartDate().before(openPeriodFirstDay)
				|| newAdjust.getStartDate().after(lastDayPreviousMonth.getTime())) {
			String d1 = DateUtil.getFormattedDate(openPeriodFirstDay);
			String d2 = DateUtil.getFormattedDate(lastDayPreviousMonth.getTime());
			String[] params = { d1, d2 };
			String msg = getMessageResourceString("alloc_adjust_invalid_date", params);
			String summary = super.getMessageResourceString("validation_error", null);
			addComposedMessage(summary, msg, Constants.ERROR);
			return false;

		}
		if (newAdjust.getAdjustValue() != null && newAdjust.getAdjustValue().signum() == -1) {
			addMessage("validation_error", "alloc_adjust_negative", Constants.ERROR);
			result = false;
		}

		return result;
	}

	private void validatePresence(Object field, String label) throws ValidationException {
		if (field == null || (field instanceof String && ((String) field).isEmpty())) {

			String fieldName = super.getMessageResourceString(label, null);
			String params[] = { fieldName };
			String msgText = getMessageResourceString("alloc_adjust_required", params);
			String summary = super.getMessageResourceString("validation_error", null);
			addComposedMessage(summary, msgText, Constants.ERROR);
			throw new ValidationException(msgText);
		}
	}

	public Map<BigDecimal, Object> getZoneIds() {
		Map<BigDecimal, Object> res = service.getZoneIds(newAdjust.getOrigContractId());
		if (!res.isEmpty()) {
			newAdjust.setOrigZone(res.entrySet().iterator().next().getKey());
		}
		return res;
	}

	public Map<BigDecimal, Object> getZoneIdsDest() {
		Map<BigDecimal, Object> res = service.getZoneIds(newAdjust.getDestContractId());
		if (!res.isEmpty()) {
			newAdjust.setDestZone(res.entrySet().iterator().next().getKey());
		}
		return res;
	}

	public void onExecute() {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		String summaryMsg = getChangeSystemView().getSystem() + ": "
				+ msgs.getString("all_man_execution_dialog_header");

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

		// Si se ejectua el proceso en background, no tiene sentido actualizar
		// los datos.
		// onSearch();
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

	public void onClear() {
		initFilter();
		onSearch();
	}

	public List<ContractBean> getContractIds() {

		adjustDates();
		return service.selectContractId(filter);
	}

	public List<ContractBean> getDestContractIds() {
		if (newAdjust.getOrigContractId() == null || origContractsIds.isEmpty()) {
			return new ArrayList<ContractBean>();
		}
			ContractBean orig = origContractsIds.stream()
					.filter(c -> c.getIdn_contract().equals(newAdjust.getOrigContractId())).findFirst().get();
			if (newAdjust.getOrigShipperId() != null) {
				newAdjust.setOrigContractStartDate(orig.getContract_start());
				newAdjust.setOrigContractEndDate(orig.getContract_end());
				destContractsIds = service.selectDestinationContractId(newAdjust);

				return destContractsIds;
			} else {
				return new ArrayList<ContractBean>();
			}

	}

	public void onChangeDestContract(AjaxBehaviorEvent event) {

		if (!destContractsIds.isEmpty()) {
			newAdjust.setGasDay(calculateGasDay());
		}
	}

	public void onChangeNewContractDate(AjaxBehaviorEvent event) {
		newAdjust.setOrigContractId(null);
		newAdjust.setDestContractId(null);
	}

	public Date calculateGasDay() {
		List<Date> dates = new ArrayList<>();
		if (newAdjust.getOrigContractEndDate() != null) {
			dates.add(newAdjust.getOrigContractEndDate());
		} else {
			return null;
		}
		ContractBean dst = null;
		if (!destContractsIds.isEmpty()) {
			dst = destContractsIds.stream()
				.filter(dest -> dest.getIdn_contract().equals(newAdjust.getDestContractId())).findFirst().get();
		}
		if (dst != null) {
			dates.add(dst.getContract_end());
		} else {
			return null;
		}
		dates.add(newAdjust.getEndDate());

		return dates.stream().min(Date::compareTo).get();

	}

	public List<ContractBean> getOriginContractIds() {
		Date d = newAdjust.getMonthYear();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		newAdjust.setStartDate(cal.getTime());
		int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, max);
		newAdjust.setEndDate(DateUtil.adjustEndDate(cal.getTime()));
		if (newAdjust.getOrigShipperId() != null) {
			origContractsIds = service.selectOriginContractId(newAdjust);
			return origContractsIds;
		} else {
			return null;
		}
	}

	public Map<BigDecimal, Object> getShipperIds() {
		return service.selectShipperId();
	}

	public void onSearch() {
		String msgVal = validateForm();
		if (msgVal != null) {
			addMessage("filter_error", msgVal, Constants.ERROR);
		} else {
			try {
				adjustDates();
				resultList = service.search(filter);
			} catch (Exception e) {
				log.error("error searching monthly bal: " + e.getMessage(), e);
				addMessage("res_bal_search_error", "res_bal_internal_error", Constants.ERROR);
			}
		}
	}

	private void adjustDates() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(filter.getStartDate());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		filter.setStartDate(cal.getTime());
		cal.setTime(filter.getEnDate());
		int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, max);

		filter.setEnDate(DateUtil.adjustEndDate(cal.getTime()));
	}

	private String validateForm() {
		String result = null;
		if (filter.getStartDate() == null) {
			result = "res_bal_start_required";
			return result;
		}
		if (filter.getEnDate() == null) {
			result = "res_bal_end_required";
			return result;
		}
		if (filter.getStartDate().after(filter.getEnDate())) {
			result = "res_bal_date_before";
		}
		return result;
	}

	private void initFilter() {
		filter = new AllocationAdjustFilter();
		filter.setStartDate(getPreviousMonth());
		filter.setEnDate(getPreviousMonth());
		filter.setIdnSystem(getChangeSystemView().getIdn_active());
		if (isShipper()) {
			filter.setShipperId(super.getUser().getIdn_user_group());
			newAdjust.setOrigShipperId(filter.getShipperId());
			newAdjust.setDestShipperId(filter.getShipperId());
		}
		newAdjust.setIdnSystem(getChangeSystemView().getIdn_active());
		filter.setStatusCode(new String[] { Constants.NOT_REVIEWED, Constants.INITIAL, Constants.ACCEPTED,
				Constants.REJECTED, Constants.ALLOCATED });

	}

	private Date getPreviousMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	public AllocationAdjustFilter getFilter() {
		return filter;
	}

	public void setFilter(AllocationAdjustFilter filter) {
		this.filter = filter;
	}

	public AllocationAdjustService getService() {
		return service;
	}

	public void setService(AllocationAdjustService service) {
		this.service = service;
	}

	public List<AdjustBeanQryResult> getResultList() {
		return resultList;
	}

	public void setResultList(List<AdjustBeanQryResult> resultList) {
		this.resultList = resultList;
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

	public AllocationManagementService getAllocMngservice() {
		return allocMngservice;
	}

	public void setAllocMngservice(AllocationManagementService allocMngservice) {
		this.allocMngservice = allocMngservice;
	}

	public AdjustBean getNewAdjust() {
		return newAdjust;
	}

	public void setNewAdjust(AdjustBean newAdjust) {
		this.newAdjust = newAdjust;
	}

}
