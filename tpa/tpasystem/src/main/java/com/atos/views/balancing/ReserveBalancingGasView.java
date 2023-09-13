package com.atos.views.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.MessageBean;
import com.atos.beans.ReserveBalancingGasDto;
import com.atos.beans.balancing.ReserveBalancingGasBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.balancing.ReserveBalancigGasFilter;
import com.atos.services.balancing.ReserveBalancingGasSbsService;
import com.atos.services.balancing.ReserveBalancingGasService;
import com.atos.utils.Constants;
import com.atos.utils.DateUtil;
import com.atos.views.CommonView;

@ManagedBean(name = "reserveBalancingGasView")
@ViewScoped
public class ReserveBalancingGasView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1273106250784866656L;
	private ReserveBalancigGasFilter filter;
//	@ManagedProperty("#{reserveBalancingGasBean}")
	private ReserveBalancingGasBean newReserve;

	@ManagedProperty("#{reserveBalancingGasService}")
	transient private ReserveBalancingGasService service;

	@ManagedProperty("#{reserveBalancingGasSbsService}")
	transient private ReserveBalancingGasSbsService sbsService;

	private ReserveBalancingGasDto selected;
	private List<ReserveBalancingGasDto> items;

	private static final Logger log = LogManager.getLogger("com.atos.views.balancing.ReserveBalancingGasView");

	private String dateSubmit;

	@PostConstruct
	public void init() {
		filter = new ReserveBalancigGasFilter();
		filter.setIdnUser(getUser().getIdn_user());
		Date today = DateUtil.getToday();
		filter.setFromDate(today);
		filter.setToDate(today);
		filter.setIdnSystem(getChangeSystemView().getIdn_active());
		if(getIsShipper()){
			
			filter.setShipperId(getUser().getIdn_user_group());
		}
		newReserve = new ReserveBalancingGasBean();
		newReserve.setSelDate(today);
		items = null;
		setDateSubmit();
		onSearch();
	}

	private void setDateSubmit() {
		try {
			Integer res = sbsService.getSubmitDate(getUser().getIdn_user().toString(), getLocale());
			String parm = null;
			switch (res) {
			case 1:
				parm = res + "st";
				break;
			case 2:
				parm = res + "nd";
				break;

			default:
				parm = res + "th";
				break;
			}
			Object[] params = { parm };
			dateSubmit = CommonView.getMessageResourceString("res_bal_sbs_submit", params);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public String getLocale() {
		return FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
	}

	public void save() {
		if (validateNew()) {
			try{
			service.save(newReserve);
			try {
					Map m = service.selectReserveBalId(newReserve.getShipper(), getChangeSystemView().getIdn_active());
			String contr = m.get(newReserve.getContractId()).toString();
					Date dat = newReserve.getSelDate();
					SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy", new Locale("en"));
					String sDat = sdf.format(dat);
					Object[] params = { contr, sDat };
					String msg = CommonView.getMessageResourceString("res_bal_insert_ok", params);

					getMessages().addMessage(Constants.head_menu[9], new MessageBean(Constants.INFO,
					CommonView.getMessageResourceString("conf_info", null), msg, Calendar.getInstance().getTime()));
		} catch (Exception e) {
		}


			}catch(Exception e){
				log.error("Error saving mothly contract", e);
				addMessage("res_bal_error_saving", Constants.ERROR, "res_bal_internal_error");
			}
			newReserve = new ReserveBalancingGasBean();
			//context.getELContext().getELResolver().setValue(context.getELContext(), null, "reserveBalancingGasBean",
			//		newReserve);
			onSearch();
		}
	}

	private boolean validateNew() {

		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
		if (newReserve.getShipper() == null) {
			addMessage("res_bal_shipper_req", Constants.ERROR, "validation_error");
			return false;
		}
		if (newReserve.getContractId() == null) {
			addMessage("res_bal_contract_req", Constants.ERROR, "validation_error");
			return false;

		}
		
		if (newReserve.getSelDate() == null) {

			addMessage("res_bal_date_req", Constants.ERROR, "validation_error");
			return false;
		} else {


			HashMap<String, Object> params = new HashMap<>();
			params.put("closingTypeCode", "DEFINITIVE");
			params.put("idnSystem", getChangeSystemView().getIdn_active());
			params.put("sysCode", getChangeSystemView().getSystem());
			Date lowLimit = service.selectOpenPeriodFirstDay(params);
			if (newReserve.getSelDate().before(lowLimit)) {

				SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
				String badDate = msgs.getString("res_bal_not_valid_month") + " "
						+ sdf.format(lowLimit);

				getMessages().addMessage(Constants.head_menu[9], new MessageBean(Constants.ERROR,
						msgs.getString("validation_error"), badDate, Calendar.getInstance().getTime()));
				log.error("Start date must be previous or equal to end date.");
				return false;
			}

		}

		if (newReserve.getFileName() == null || newReserve.getFileName().isEmpty()) {

			addMessage("res_bal_file_req", Constants.ERROR, "validation_error");
			return false;
		}

		try {
			if (!service.isValidDateForcontract(newReserve)) {

				SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
				String badDate = sdf.format(newReserve.getSelDate());
				String contract = (String) getService()
						.selectReserveBalId(newReserve.getShipper(), getChangeSystemView().getIdn_active())
						.get(newReserve.getContractId());
				String[] params = { badDate, contract };
				getMessages().addMessage(Constants.head_menu[9], new MessageBean(Constants.ERROR,
						msgs.getString("validation_error"),
						CommonView.getMessageResourceString("res_bal_bad_date", params),
						Calendar.getInstance().getTime()));

				return false;
			}
		} catch (Exception e) {
			log.error("error checking validity", e);
			addMessage("res_bal_file_req", Constants.ERROR, "validation_error");
		}
		return true;
	}

	private String validateForm() {
		String result = null;
		if(filter.getFromDate() == null){
			result = "res_bal_start_required";
			return result;
		}
		if(filter.getToDate() == null){
			result = "res_bal_end_required";
			return result;
		}
		if (filter.getFromDate().after(filter.getToDate())) {
			result = "res_bal_date_before";
		}
		return result;
	}

	public void onSearch() {
		String msgVal = validateForm();
		if (msgVal != null) {
			addMessage(msgVal, Constants.ERROR, "validation_error");
		} else {
			try{
			items = service.search(filter);
			}catch(Exception e){
				log.error("error searching monthly bal", e);
				addMessage("res_bal_search_error", Constants.ERROR, "res_bal_internal error");
			}
		}
	}

	public void shipperChanged(ValueChangeEvent vce) {
		if (vce.getNewValue().toString().length() > 0) {
			filter.setShipperId((BigDecimal) vce.getNewValue());
		}
	}

	public void newShipperChanged(ValueChangeEvent vce) {
		if (vce.getNewValue().toString().length() > 0) {
			newReserve.setShipper((BigDecimal) vce.getNewValue());
		}
	}

	public void fileUploadListener(FileUploadEvent e) {

		UploadedFile uUploadedFile = null;

		uUploadedFile = e.getFile();

		if (uUploadedFile != null) {
			newReserve.setFileName(uUploadedFile.getFileName());
			newReserve.setBinaryData(uUploadedFile.getContents());
		} else {

			addMessage("file_must_selected", Constants.ERROR, "validation_error");
			return;
		}
	}

	public void onClear() {
		init();
	}

	private void addMessage(String messageKey, int severity, String summary) {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
		getMessages().addMessage(Constants.head_menu[9],
				new MessageBean(Constants.ERROR, msgs.getString("filter_error"),
				msgs.getString(messageKey), Calendar.getInstance().getTime()));
	}

	public boolean getIsShipper() {
		return getUser().isUser_type(Constants.SHIPPER);
	}

	public Map<BigDecimal, Object> getShipperIds() {
		return getService().selectShipperId(filter.getIdnUser());
	}

	public Map<BigDecimal, Object> getReserveBalIds() {
		if (filter.getShipperId() != null) {
			return getService().selectReserveBalId(filter.getShipperId(), getChangeSystemView().getIdn_active());
		} else {

			return null;
		}
	}

	public Map<BigDecimal, Object> getNewReserveBalIds() {
		if (newReserve.getShipper() != null) {
			return getService().selectReserveBalId(newReserve.getShipper(), getChangeSystemView().getIdn_active());
		} else {

			return null;
		}
	}

	public void getFile(ReserveBalancingGasDto _contract) {
		// Utilizo un ResourceBundle local por si el scope fuera Session o
		// Application. En estos casos no se actualizaria el idioma.
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		String summaryMsg = msgs.getString("res_bal_con_downloading_contract_file");
		String errorMsg = null;

		try {
			service.getFile(_contract);
		} catch (ValidationException ve) {
			errorMsg = msgs.getString("download_error") + " " + _contract.getFileName() + ": " + ve.getMessage();
			getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			return;
		} catch (Exception e) {
			errorMsg = msgs.getString("download_error") + " " + _contract.getFileName();
			getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			// log.error(e.getMessage(), e);
			return;
		}
	}

	public ReserveBalancigGasFilter getFilter() {
		return filter;
	}

	public void setFilter(ReserveBalancigGasFilter filter) {
		this.filter = filter;
	}

	public ReserveBalancingGasService getService() {
		return service;
	}

	public void setService(ReserveBalancingGasService service) {
		this.service = service;
	}


	public ReserveBalancingGasBean getNewReserve() {
		return newReserve;
	}

	public void setNewReserve(ReserveBalancingGasBean newReserve) {
		this.newReserve = newReserve;
	}

	public ReserveBalancingGasDto getSelected() {
		return selected;
	}

	public void setSelected(ReserveBalancingGasDto selected) {
		this.selected = selected;
	}

	public List<ReserveBalancingGasDto> getItems() {
		return items;
	}

	public void setItems(List<ReserveBalancingGasDto> items) {
		this.items = items;
	}


	public ReserveBalancingGasSbsService getSbsService() {
		return sbsService;
	}

	public void setSbsService(ReserveBalancingGasSbsService sbsService) {
		this.sbsService = sbsService;
	}

	public String getDateSubmit() {
		return dateSubmit;
	}

	public void setDateSubmit(String dateSubmit) {
		this.dateSubmit = dateSubmit;
	}

	

	
	public StreamedContent getTemplate() throws ValidationException{
		
		//return service.getTemplate();
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
		
		String summaryMsg = msgs.getString("res_bal_con_downloading_template_file");
		String errorMsg = null;
		try {
			return service.getTemplate();
		} catch (ValidationException ve) {
			errorMsg = msgs.getString("download_error") + " template file. " + ve.getMessage();
			getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			return null; 
		} catch (Exception e) {
			errorMsg = msgs.getString("download_error") + " template file. ";
			getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			// log.error(e.getMessage(), e);
			return null; 
		}
	}
}
