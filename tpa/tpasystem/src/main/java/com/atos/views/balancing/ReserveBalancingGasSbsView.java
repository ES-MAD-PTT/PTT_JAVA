package com.atos.views.balancing;

import java.io.Serializable;
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
import javax.faces.event.ValueChangeEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.RowEditEvent;

import com.atos.beans.LanguageBean;
import com.atos.beans.MessageBean;
import com.atos.beans.ReserveBalancingGasSbsDto;
import com.atos.beans.balancing.ReserveBalancingGasSbsBean;
import com.atos.filters.balancing.ReserveBalancigGasSbsFilter;
import com.atos.services.balancing.ReserveBalancingGasSbsService;
import com.atos.services.balancing.ReserveBalancingGasService;
import com.atos.utils.Constants;
import com.atos.utils.DateUtil;
import com.atos.views.CommonView;

@ManagedBean(name = "reserveBalancingGasSbsView")
@ViewScoped
public class ReserveBalancingGasSbsView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3303326835250734785L;

	@ManagedProperty("#{reserveBalancingGasService}")
	transient private ReserveBalancingGasService service;
	@ManagedProperty("#{reserveBalancingGasSbsService}")
	transient private ReserveBalancingGasSbsService sbsService;
	@ManagedProperty("#{languageBean}")
	private LanguageBean language;

	private List<ReserveBalancingGasSbsDto> items;

	private ReserveBalancingGasSbsDto selected;

	private ReserveBalancingGasSbsBean newOperation;

	private ReserveBalancigGasSbsFilter filter;
	private Map<BigDecimal, Object> mPoints;

	private static final Logger log = LogManager.getLogger("com.atos.views.balancing.ReserveBalancingGasSbsView");

	@PostConstruct
	public void init() {
		super.setHead(5);
		filter = new ReserveBalancigGasSbsFilter();
		Date today = DateUtil.getToday();
		filter.setFromDate(today);
		filter.setToDate(today);
		filter.setShipper(getIsShipper());
		filter.setIdnSystem(getChangeSystemView().getIdn_active());
		newOperation = new ReserveBalancingGasSbsBean();
		newOperation.setStartDate(today);
		newOperation.setEndDate(today);
		newOperation.setIdnSystem(getChangeSystemView().getIdn_active());
		if (getIsShipper()) {

			filter.setShipperId(super.getUser().getIdn_user_group());
			newOperation.setShipper(filter.getShipperId());
		}

		onSearch();
	}

	public void newShipperChanged(ValueChangeEvent vce) {
		if (vce.getNewValue() != null && vce.getNewValue().toString().length() > 0) {
			newOperation.setShipper((BigDecimal) vce.getNewValue());
		}
	}

	public void zoneChanged(ValueChangeEvent vce) {
		if (vce.getNewValue() != null && vce.getNewValue().toString().length() > 0) {
			newOperation.setZoneId((BigDecimal) vce.getNewValue());
		}
	}

	public void contractChanged(ValueChangeEvent vce) {
		newOperation.setContractId((BigDecimal) vce.getNewValue());
	}

	public void onRowEdit(RowEditEvent event) {
		ReserveBalancingGasSbsDto dto = (ReserveBalancingGasSbsDto) event.getObject();

		if (dto.getQuantity() == null) {
			addMessage("validation_error", "res_bal_sbs_qua_req", Constants.ERROR);
			return;
		} else if (dto.getQuantity() < 0) {
			addMessage("validation_error", "res_bal_sbs_qua_neg", Constants.ERROR);
			return;

		}

		boolean valid = isInOperationInPeridod(dto.getGasDay());
		ReserveBalancingGasSbsBean operation = new ReserveBalancingGasSbsBean();
		if (valid) {
			operation.setContractId(dto.getIdnResbalContract());
			operation.setNominationPoint(dto.getIdnSystemPoint());
			operation.setInsertDate(dto.getGasDay());
			operation.setCapContractCode(dto.getIdnContract());
			operation.setQuantity(new BigDecimal(dto.getQuantity()));
			operation.setComments(dto.getComments());
			operation.setOperatorComments(dto.getOperatorComments());
			operation.setZoneId(dto.getIdnZone());
			operation.setStartDate(dto.getGasDay());
			operation.setEndDate(dto.getGasDay());
			Date invalidDate = isIncontract(operation);
			if (invalidDate == null) {

				try {
					if (!dto.getIdnUserGroup().equals(getUser().getIdn_user_group())) {
						sendNotification(dto, "RESERVE.BAL.SUBMISSION.MOD");
					}

					sbsService.singleSave(operation);

					sendUpdateMessage(dto, operation);

				} catch (Exception e) {
					log.error("Error saving Balancing gas submission", e);
					addMessage("internal_error", "saving_data_error", Constants.ERROR);
				}
			} else {

				addInvalidDateMessage(operation, invalidDate);
			}
		}
	}

	private void sendUpdateMessage(ReserveBalancingGasSbsDto dto, ReserveBalancingGasSbsBean operation) {
		try {
			String contr = dto.getContractCode();
			String ship = dto.getUserGroupId();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Object[] params = { contr, ship, "" };

			String sdate = sdf.format(dto.getGasDay());
			params[2] = sdate;
			String msg = getMessageResourceString("res_bal_sbs_mod_ok", params);
			super.getMessages().addMessage(Constants.head_menu[9], new MessageBean(Constants.INFO,
					getMessageResourceString("res_bal_sbs_bal", null), msg, Calendar.getInstance().getTime()));
		} catch (Exception e) {
			log.error("Error sending update message", e);
		}
	}

	private void sendNotification(ReserveBalancingGasSbsDto dto, String notificationTypeCode) {
		try {
			sbsService.sendNotification(dto, getUser(), getLanguage().getLocale(), notificationTypeCode,
					getChangeSystemView().getSystem(), getChangeSystemView().getIdn_active());
		} catch (Exception e) {
			log.error("could not send notification", e);
		}

	}

	public void onRowCancel(RowEditEvent event) {
	}

	public Map<BigDecimal, Object> getReserveBalIds() {
		if (filter.getShipperId() != null) {
			return getService().selectReserveBalId(filter.getShipperId(), getChangeSystemView().getIdn_active());
		} else {

			return null;
		}
	}

	public Map<BigDecimal, Object> getNewReserveBalIds() {
		if (newOperation.getShipper() != null) {
			return getService().selectReserveBalId(newOperation.getShipper(), getChangeSystemView().getIdn_active());
		} else {

			return null;
		}
	}

	public void save() {
		String result = newOperation.validate();
		if (result != null) {
			addMessage("validation_error", result, Constants.ERROR);
		} else {

			boolean valid = isInOperationInPeridod(newOperation.getStartDate());
			if (valid) {
				Date invalidDate = isIncontract(newOperation);
				if (invalidDate == null) {
					try {
						List<Date> dates = save(newOperation);
						StringBuilder queue = new StringBuilder();
						try {
							Map m = service.selectReserveBalId(newOperation.getShipper(),
									getChangeSystemView().getIdn_active());
							String contr = m.get(newOperation.getContractId()).toString();
							String ship = getShipperIds().get(newOperation.getShipper()).toString();
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							Object[] params = { contr, ship, "" };

							ReserveBalancingGasSbsDto dto = new ReserveBalancingGasSbsDto();
							if (!getUser().getIdn_user_group().equals(newOperation.getShipper())) {
								dto.setIdnUserGroup(newOperation.getShipper());
								dto.setContractCode((String) getNewReserveBalIds().get(newOperation.getContractId()));
								dto.setPointCode((String) getPoints().get(newOperation.getNominationPoint()));
								dto.setCapContractCode(
										(String) getNewCapContractIds().get(newOperation.getCapContractCode()));

							}
							for (Date d : dates) {
								String sdate = sdf.format(d);
								params[2] = sdate;
								String msg = getMessageResourceString("res_bal_sbs_insert_ok", params);
								queue.append(msg).append("\n");
								dto.setGasDay(d);
								sendNotification(dto, "RESERVE.BAL.SUBMISSION.NEW");
							}

							super.getMessages().addMessage(Constants.head_menu[9],
									new MessageBean(Constants.INFO, getMessageResourceString("res_bal_sbs_bal", null),
											queue.toString(), Calendar.getInstance().getTime()));
						} catch (Exception e) {
						}
					} catch (Exception e1) {

						log.error("Error saving Balancing gas submission", e1);
						addMessage("internal_error", "saving_data_error", Constants.ERROR);
					}
				} else {
					addInvalidDateMessage(newOperation, invalidDate);
				}
			}

		}
	}

	private void addInvalidDateMessage(ReserveBalancingGasSbsBean operation, Date invalidDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String sDate = sdf.format(invalidDate);
		mPoints = getPoints(operation);
		Object[] params = { mPoints == null ? "Not Available" : mPoints.get(operation.getNominationPoint()), sDate };
		String msg = getMessageResourceString("res_bal_sbs_not_included", params);
		super.getMessages().addMessage(Constants.head_menu[9], new MessageBean(Constants.ERROR,
				getMessageResourceString("validation_error", null), msg, Calendar.getInstance().getTime()));

	}

	private List<Date> save(ReserveBalancingGasSbsBean newOperation2) throws Exception {
		return sbsService.save(newOperation2);
	}

	private Date isIncontract(ReserveBalancingGasSbsBean newOperation2) {
		Date result = null;
		List<Date> invalidDates = sbsService.getInValidDates(newOperation2);
		if (!invalidDates.isEmpty()) {
			result = invalidDates.get(0);
		}
		return result;
	}

	private boolean isInOperationInPeridod(Date dateRef) {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		HashMap<String, Object> params = new HashMap<>();
		params.put("closingTypeCode", "DEFINITIVE");
		params.put("idnSystem", getChangeSystemView().getIdn_active());
		params.put("sysCode", getChangeSystemView().getSystem());
		Date lowLimit = service.selectOpenPeriodFirstDay(params);
		if (dateRef.before(lowLimit)) {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String badDate = msgs.getString("res_bal_not_valid_month") + " " + sdf.format(lowLimit);
			super.getMessages().addMessage(Constants.head_menu[super.getHead()], new MessageBean(Constants.ERROR,
					msgs.getString("validation_error"), badDate, Calendar.getInstance().getTime()));
			return false;
		}
		return true;
	}

	public Map<BigDecimal, Object> getPoints(ReserveBalancingGasSbsBean operation) {
		if (operation.getZoneId() != null && operation.getContractId() != null) {
			return sbsService.getPoints(operation);
		} else {

			return null;
		}
	}

	public Map<BigDecimal, Object> getPoints() {
		if (newOperation.getZoneId() != null && newOperation.getContractId() != null) {
			return sbsService.getPoints(newOperation);
		} else {

			return null;
		}
	}

	public Map<BigDecimal, Object> getCapContractIds() {
		return sbsService.getCapContractsIds(filter);
	}

	public Map<BigDecimal, Object> getNewCapContractIds() {
		ReserveBalancigGasSbsFilter f = new ReserveBalancigGasSbsFilter();
		f.setShipperId(newOperation.getShipper());
		f.setIdnSystem(getChangeSystemView().getIdn_active());
		return sbsService.getCapContractsIds(f);
	}

	public Map<BigDecimal, Object> getZones() {
		return sbsService.getZones(newOperation.getContractId(), getChangeSystemView().getIdn_active());
	}

	public Map<BigDecimal, Object> getZonesForSearch() {
		return sbsService.getZonesForSearch(getChangeSystemView().getIdn_active());
	}

	private String validateForm() {
		String result = null;
		if (filter.getFromDate() == null) {
			result = "res_bal_sbs_start_req";
			return result;
		}
		if (filter.getToDate() != null && filter.getFromDate().after(filter.getToDate())) {
			result = "res_bal_sbs_date_before";
		}
		return result;
	}

	public void onSearch() {
		String msgVal = validateForm();
		if (msgVal != null) {
			addMessage("filter_error", msgVal, Constants.ERROR);
		} else {

			items = sbsService.search(filter);
		}
	}

	public void onClear() {
		init();
	}

	public boolean getIsShipper() {
		return super.getUser().isUser_type(Constants.SHIPPER);
	}

	public Map<BigDecimal, Object> getShipperIds() {
		return service.selectShipperId(getUser().getIdn_user());
	}

	public ReserveBalancingGasService getService() {
		return service;
	}

	public void setService(ReserveBalancingGasService service) {
		this.service = service;
	}

	public ReserveBalancigGasSbsFilter getFilter() {
		return filter;
	}

	public void setFilter(ReserveBalancigGasSbsFilter filter) {
		this.filter = filter;
	}

	public ReserveBalancingGasSbsService getSbsService() {
		return sbsService;
	}

	public void setSbsService(ReserveBalancingGasSbsService sbsService) {
		this.sbsService = sbsService;
	}

	public LanguageBean getLanguage() {
		return language;
	}

	public void setLanguage(LanguageBean language) {
		this.language = language;
	}

	public List<ReserveBalancingGasSbsDto> getItems() {
		return items;
	}

	public void setItems(List<ReserveBalancingGasSbsDto> items) {
		this.items = items;
	}

	public ReserveBalancingGasSbsDto getSelected() {
		return selected;
	}

	public void setSelected(ReserveBalancingGasSbsDto selected) {
		this.selected = selected;
	}

	public ReserveBalancingGasSbsBean getNewOperation() {
		return newOperation;
	}

	public void setNewOperation(ReserveBalancingGasSbsBean newOperation) {
		this.newOperation = newOperation;
	}

}
