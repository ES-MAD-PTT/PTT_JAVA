package com.atos.views.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.context.RequestContext;
import org.primefaces.event.data.PageEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.booking.CRMFilterCtl;
import com.atos.beans.booking.CRReviewDto;
import com.atos.beans.booking.CapacityDetailDto;
import com.atos.beans.booking.CapacityRequestMailBean;
import com.atos.beans.booking.GasQualityDto;
import com.atos.services.MailService;
import com.atos.services.booking.ContractReviewService;
import com.atos.utils.Constants;
import com.atos.views.ChangeSystemView;
import com.atos.views.CommonView;

@ManagedBean(name = "cRReview")
@ViewScoped
public class CRReview extends CommonView implements Serializable {

	private static final int MAX_VALUE = 1;
	private static final int MIN_VALUE = 0;
	private static final Logger log = LogManager.getLogger("om.atos.views.booking.CRReview");
	private static final long serialVersionUID = 3823587531263862234L;
	public static final int QUANTITY_UNIT_FLOW = 0;
	public static final int QUANTITY_UNIT_ENERGY = 1;
	public static final String FORMAT_LONG_CONTRACT = "MMM/yyyy";
	public static final String FORMAT_CONTRACT = "MMM/yyyy";

	@ManagedProperty("#{changeSystemView}")
	ChangeSystemView changeSystemView;
	@ManagedProperty("#{filterCtl}")
	private CRMFilterCtl backFileter;
	private List<CRReviewDto> resultList;
	private List<CRReviewDto> unFilteredResultList;
	private List<CRReviewDto> resultListExit;
	private List<CRReviewDto> unFilteredResultListExit;
	private List<GasQualityDto> resultListGC;
	private List<GasQualityDto> unFilteredResultListGC;
	private List<String> period;
	private int entryQuantityUnit;
	private List<CapacityDetailDto> capacityEntryList;
	private List<CapacityDetailDto> unFilteredCapacityEntryList;
	private List<CapacityDetailDto> capacityExitList;
	private List<CapacityDetailDto> unFilteredCapacityExitList;
	private Map<String, CapacityDetailDto> capacityEntriesMap; // refactored
	private Map<String, CapacityDetailDto> capacityExitMap;
	@ManagedProperty("#{contractReviewService}")
	private ContractReviewService crService;
	private Map<String, List<GasQualityDto>> mapQualityDetails;
	private String nConFilter = "";
	private String nConFilterExit = "";
	private String nConFilterGC = "";
	private List<Predicate<CRReviewDto>> filters = new ArrayList<>();
	private List<Predicate<CRReviewDto>> filtersExit = new ArrayList<>();
	private List<Predicate<GasQualityDto>> filtersGC = new ArrayList<>();
	private List<Date> datesEntry = new ArrayList<>();
	private List<Date> datesFromEntry = new ArrayList<>();
	private List<Date> datesToEntry = new ArrayList<>();
	private List<Date> datesFromExit = new ArrayList<>();
	private List<Date> datesToExit = new ArrayList<>();
	private String dateFromEntry;
	private String dateToEntry;
	private String dateFromExit;
	private String dateToExit;
	private String dateFromGC;
	private String dateToGC;
	private BigDecimal numericVal;
	private boolean hasPermission;
	private String comments;
	private boolean shortContract;
	private boolean editableRequest;


	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("en"));
	private SimpleDateFormat sdfCombo = new SimpleDateFormat("dd/MM/yyyy", new Locale("en"));
	private int firstExit;
	private int firstEntry;
	
	@ManagedProperty("#{mailService}")
    transient private MailService mailService;

    public void setMailService(MailService mailService) {
    	this.mailService = mailService;
    }


	/**
	 * 
	 */


	@PostConstruct
	private void init() {
		super.setHead(1);
		initComponents();
		loadData();
	}

	private void loadData() {
		try{
		backFileter.setReturning(true);
			shortContract = backFileter.getContractType().toLowerCase().indexOf("short") != -1;
		String format = shortContract ? FORMAT_CONTRACT : FORMAT_LONG_CONTRACT;

		sdf = new SimpleDateFormat(format, new Locale("en"));
			unFilteredResultList = crService.getPoints(backFileter.getRequestCode(), "ENTRY");
	/*		if(backFileter.getOrigin() == CRMFilterCtl.ORIGING_CAP_QUERY) {
				unFilteredCapacityEntryList = crService.getCapacitiesContractQuery(backFileter.getRequestCode(), "ENTRY");
			} else {*/
				unFilteredCapacityEntryList = crService.getCapacities(backFileter.getRequestCode(), "ENTRY");
//			}
		comments = (unFilteredCapacityEntryList.size()==0 ? null : unFilteredCapacityEntryList.get(0).getComments());
		datesEntry = crService.getDateList(unFilteredCapacityEntryList);
		datesFromEntry = crService.getDatesForFilters(unFilteredResultList, true);
		datesToEntry = crService.getDatesForFilters(unFilteredResultList, false);

			unFilteredResultListExit = crService.getPoints(backFileter.getRequestCode(), "EXIT");
/*			if(backFileter.getOrigin() == CRMFilterCtl.ORIGING_CAP_QUERY) {
				unFilteredCapacityExitList = crService.getCapacitiesContractQuery(backFileter.getRequestCode(), "EXIT");			
			} else {*/
				unFilteredCapacityExitList = crService.getCapacities(backFileter.getRequestCode(), "EXIT");
		//	}
		datesFromExit = crService.getDatesForFilters(unFilteredResultListExit, true);
		datesToExit = crService.getDatesForFilters(unFilteredResultListExit, false);
		// gas quality
			unFilteredResultListGC = crService.getQualityListList(backFileter.getRequestCode());
		mapQualityDetails = crService.getGasQualityDetails(unFilteredResultListGC);
		unFilteredResultListGC = crService.reduceList(unFilteredResultListGC);
		restoreEntryOriginalData();
		restoreExitOriginalData();
		restoreGCOriginalData();
		setCapacityEntriesMap(crService.getCapacitiesMap(capacityEntryList, format));
		resultList.addAll(crService.getSubtotals(capacityEntryList));
			resultList.addAll(crService.getTotals(capacityEntryList));
		resultListExit.addAll(crService.getSubtotals(capacityExitList));
			resultListExit.addAll(crService.getTotals(capacityExitList));
			resultListExit.addAll(crService.getDiffEntryExit(resultList, resultListExit));
			capacityExitMap = crService.getCapacitiesMap(capacityExitList, format);
		
		period = crService.getYears(datesEntry, format);
		} catch (Exception e) {
			log.error("Error loading screen data", e);
			e.printStackTrace();
			addMessage("internal_error", "crew_error_load_data", Constants.ERROR);
	}
	}

	public void commentListener(AjaxBehaviorEvent e) {
		InputTextarea comp = (InputTextarea) e.getComponent();
		String val = (String) comp.getValue();
		comments = val;
	}

	public void save() {
		try {

			HashMap<String, Object> result = crService.save(unFilteredCapacityEntryList, unFilteredCapacityExitList,
					backFileter.getRequestCode(), backFileter.getShipperName(), shortContract,
					sdf, getUser(), comments);
		Integer res = (Integer) result.get("par_error_code");
		if(res == 0){
			super.addMessage("alloc_report_save_ok", "alloc_report_save_ok", Constants.INFO);
			
			List<CapacityDetailDto> modifiedEntries = crService.getModified(unFilteredCapacityEntryList);
			if(!modifiedEntries.isEmpty())
			{
				sendMail(modifiedEntries.get(0));
			}
			else {
				List<CapacityDetailDto> modifiedExits = crService.getModified(unFilteredCapacityExitList);
				if(!modifiedExits.isEmpty())
					sendMail(modifiedExits.get(0));
			}
		}
		else{
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
				String[] afail = { (String) result.get("par_error_desc") };
			String fail = super.getMessageResourceString("alloc_report_save_ko",afail  );
				super.getMessages().addMessage(Constants.head_menu[1], new MessageBean(Constants.ERROR,
						msgs.getString("crew_error_save_data"), fail, Calendar.getInstance().getTime()));
		}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error saving screen data", e);
			addMessage("internal_error", "crew_error_save_data", Constants.ERROR);
		}
		loadData();
	}

	public void reset() {
		loadData();
	}

	public boolean isEditableData() {
		return hasPermission && editableRequest;
	}


	@Deprecated
	public Map<String, Double> getMampForCapEntryDayly() {
		// return capacityEntriesMap.get("dailyBookingMbtu");
		throw new UnsupportedOperationException();
	}

	public void onPageChangeExit(PageEvent event) {
		setFirstExit(((DataTable) event.getSource()).getFirst());
	}

	public void onPageChangeEntry(PageEvent event) {
		setFirstEntry(((DataTable) event.getSource()).getFirst());
	}

	public void validate(FacesContext ctx, UIComponent comp, Object value) {
		Double dval;
		try {
			dval = (Double) value;
		} catch (Exception e) {
			dval = null;
		}
		if (value == null || dval == 0 || dval < 0) {
			EditableValueHolder input = (EditableValueHolder) comp;
			input.resetValue();
			FacesMessage msg = new FacesMessage("invalid value", "invalid value");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

	public void mtuChange(String year, String entryPoint, String attr) {

		CapacityDetailDto mod = unFilteredCapacityEntryList.stream()
				.filter(cp -> cp.getEntryPoint().equals(entryPoint) && sdf.format(cp.getMonthYear()).equals(year))
				.findFirst().get();
		CapacityDetailDto val = capacityEntriesMap.get(composeKey(year, entryPoint));

		switch (attr) {
		case "1":
			mod.setDailyBookingmbtu(val.getDailyBookingmbtu());
			mod.setDailyBookingmbtEdited(true);
			break;
		case "2":
			mod.setHourlyBookingMmbtu(val.getHourlyBookingMmbtu());
			mod.setHourlyBookingMmbtuEdited(true);
			break;
		case "3":
			mod.setDailyBookingMmscfd(val.getDailyBookingMmscfd());
			mod.setDailyBookingMmscfdEdited(true);
			break;
		case "4":
			mod.setHourlyBookingMmscfd(val.getHourlyBookingMmscfd());
			mod.setHourlyBookingMmscfdEdited(true);
			break;

		default:
			break;
		}
	}

	public void mtuExitChange(String year, String entryPoint, String attr) {

		CapacityDetailDto mod = unFilteredCapacityExitList.stream()
				.filter(cp -> cp.getEntryPoint().equals(entryPoint) && sdf.format(cp.getMonthYear()).equals(year))
				.findFirst().get();
		CapacityDetailDto val = capacityExitMap.get(composeKey(year, entryPoint));
		switch (attr) {
		case "1":
			mod.setDailyBookingmbtu(val.getDailyBookingmbtu());
			mod.setDailyBookingmbtEdited(true);
			break;
		case "2":
			mod.setHourlyBookingMmbtu(val.getHourlyBookingMmbtu());
			mod.setHourlyBookingMmbtuEdited(true);
			break;
		case "3":
			mod.setDailyBookingMmscfd(val.getDailyBookingMmscfd());
			mod.setDailyBookingMmscfdEdited(true);
			break;
		case "4":
			mod.setHourlyBookingMmscfd(val.getHourlyBookingMmscfd());
			mod.setHourlyBookingMmscfdEdited(true);
			break;

		default:
			break;
		}

	}

	public void refreshEntryTable() {
		applyEntryFilters(false);
	}

	public void refreshExitTable() {
		applyExitFilters(false);
	}

	private void applyLabels() {

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("setTableLabels()");
	}

	public void changeVal() {
		String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("numvalue");
		numericVal = new BigDecimal(value);
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_chgVal').show()");
	}

	private boolean validateFilters(String sfrom, String sto) {
		if (sfrom == null || sfrom.isEmpty() || sto == null || sto.isEmpty()) {
			return true;
		} else {
			try {
				Date fr = sdfCombo.parse(sfrom);
				Date to = sdfCombo.parse(sto);
				if (fr.after(to)) {
					return false;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return true;

		}
	}
	private List<Predicate<CRReviewDto>> composeFilters(List<Predicate<CRReviewDto>> lfilter, String lcon, String lfrom,
			String lto) {
		lfilter = new ArrayList<>();
		if (lcon != null && !lcon.isEmpty()) {
			lfilter.add(getConnFilter(lcon));
		}

		try {
			if (lfrom != null && !lfrom.isEmpty()) {
				lfilter.add(getDateFromFilter(sdfCombo.parse(lfrom)));
			}

			if (lto != null && !lto.isEmpty()) {
				lfilter.add(getDateToFilter(sdfCombo.parse(lto)));

			}
		} catch (Exception e) {
		}

		return lfilter;
	}

	private List<Predicate<GasQualityDto>> composeFiltersGC(List<Predicate<GasQualityDto>> lfilter, String lcon,
			String lfrom, String lto) {
		lfilter = new ArrayList<>();
		if (lcon != null && !lcon.isEmpty()) {
			lfilter.add(getConnFilterGC(lcon));
		}

		try {
			if (lfrom != null && !lfrom.isEmpty()) {
				lfilter.add(getDateFromFilterGC(sdfCombo.parse(lfrom)));
			}

			if (lto != null && !lto.isEmpty()) {
				lfilter.add(getDateToFilterGC(sdfCombo.parse(lto)));

			}
		} catch (Exception e) {
		}

		return lfilter;
	}

	public String getDateAsString(Date d) {
		return sdfCombo.format(d);
	}

	private void restoreExitOriginalData() {
		resultListExit = new ArrayList<>(unFilteredResultListExit);
		capacityExitList = new ArrayList<>(unFilteredCapacityExitList);
	}
	
	
	private void restoreGCOriginalData() {
		resultListGC = new ArrayList<>(unFilteredResultListGC);
	}

	private void restoreEntryOriginalData() {
		resultList = new ArrayList<>(unFilteredResultList);
		capacityEntryList = new ArrayList<>(unFilteredCapacityEntryList);
	}

	private Predicate<CRReviewDto> getConnFilter(String filterConn) {
		return dto -> dto.getNewConnection() != null && dto.getNewConnection().equals(filterConn);
	}

	private Predicate<CRReviewDto> getDateFromFilter(Date dateFrom) {
		return dto -> dateFrom.equals(dto.getDateTo()) || dateFrom.before(dto.getDateTo());
	}

	private Predicate<CRReviewDto> getDateToFilter(Date dateTo) {
		return dto -> dateTo.equals(dto.getDateFrom()) || dateTo.after(dto.getDateFrom());
	}


	private Predicate<GasQualityDto> getConnFilterGC(String filterConn) {
		return dto -> dto.getNewConn() != null && dto.getNewConn().equals(filterConn);
	}

	private Predicate<GasQualityDto> getDateFromFilterGC(Date dateFrom) {
		return dto -> dateFrom.equals(dto.getDateTo()) || dateFrom.before(dto.getDateTo());
	}

	private Predicate<GasQualityDto> getDateToFilterGC(Date dateTo) {

		return dto -> dto.getDateTo().equals(dateTo) || dto.getDateFrom().equals(dateTo)
				|| (dateTo.after(dto.getDateFrom()) && dto.getDateTo().before(dateTo));
	}

	public void dateFromGCChange(ValueChangeEvent e) {
		String value = "";
		if (e.getNewValue() != null) {
			value = (String) e.getNewValue();
		}
		setDateFromGC(value);
		if (!validateFilters(value, this.getDateToGC())) {
			this.setDateToGC(null);
		}

		applyGCFilters();

	}

	public void dateToGCChange(ValueChangeEvent e) {
		String value = "";
		if (e.getNewValue() != null) {
			value = (String) e.getNewValue();
		}
		setDateToGC(value);
		if (!validateFilters(this.getDateFromGC(), value)) {
			this.setDateFromGC(null);
		}
		applyGCFilters();

	}

	public void dateFromEntryChange(ValueChangeEvent e) {
		String value = "";
		if (e.getNewValue() != null) {
			value = (String) e.getNewValue();
		}
		setDateFromEntry(value);
		if (!validateFilters(value, this.getDateToEntry())) {
			// empty opposite filter
			this.setDateToEntry(null);
		}
		applyEntryFilters(true);

	}

	public void dateToEntryChange(ValueChangeEvent e) {
		String value = "";
		if (e.getNewValue() != null) {
			value = (String) e.getNewValue();
		}
		setDateToEntry(value);
		if (!validateFilters(this.getDateFromEntry(), value)) {
			// empty opposite filter
			this.setDateFromEntry(null);
		}
		applyEntryFilters(true);

	}

	public void dateFromExitChange(ValueChangeEvent e) {
		String value = "";
		if (e.getNewValue() != null) {
			value = (String) e.getNewValue();
		}
		setDateFromExit(value);
		if (!validateFilters(value, this.getDateToEntry())) {
			this.setDateToExit(null);
		}

		applyExitFilters(true);

	}

	public void dateToExitChange(ValueChangeEvent e) {
		String value = "";
		if (e.getNewValue() != null) {
			value = (String) e.getNewValue();
		}
		setDateToExit(value);
		if (!validateFilters(this.getDateFromExit(), value)) {
			this.setDateFromExit(null);
		}
		applyExitFilters(true);

	}

	private void applyGCFilters() {
		restoreGCOriginalData();
		filtersGC = composeFiltersGC(filtersGC, nConFilterGC, dateFromGC, dateToGC);
		if (!filtersGC.isEmpty()) {
			resultListGC = resultListGC.stream().filter(filtersGC.stream().reduce(p -> true, Predicate::and))
				.collect(Collectors.toList());
		}
		applyLabels();
	}

	private void applyEntryFilters(boolean initPagination) {
		if (initPagination) {
			setFirstEntry(0);
		}
		restoreEntryOriginalData();
		filters = composeFilters(filters, nConFilter, dateFromEntry, dateToEntry);
		if (filters.isEmpty()) {

			resultList.addAll(crService.getSubtotals(capacityEntryList));
			resultList.addAll(crService.getTotals(capacityEntryList));
		}
		else {

		resultList = resultList.stream().filter(filters.stream().reduce(p -> true, Predicate::and))
				.collect(Collectors.toList());
		Set<String> points = resultList.stream().map(CRReviewDto::getEntryPoint).collect(Collectors.toSet());
		capacityEntryList = capacityEntryList.stream().filter(cp -> points.contains(cp.getEntryPoint()))
				.collect(Collectors.toList());
		resultList.addAll(crService.getSubtotals(capacityEntryList));
			resultList.addAll(crService.getTotals(capacityEntryList));
		}
		applyLabels();
	}

	private void applyExitFilters(boolean initPagination) {
		if (initPagination) {
			setFirstExit(0);
		}
		restoreExitOriginalData();
		filtersExit = composeFilters(filtersExit, nConFilterExit, dateFromExit, dateToExit);
		if (filtersExit.isEmpty()) {

			resultListExit.addAll(crService.getSubtotals(capacityExitList));
			resultListExit.addAll(crService.getTotals(capacityExitList));
			resultListExit.addAll(crService.getDiffEntryExit(resultList, resultListExit));
		} else {

			resultListExit = resultListExit.stream().filter(filtersExit.stream().reduce(p -> true, Predicate::and))
					.collect(Collectors.toList());
			Set<String> points = resultListExit.stream().map(CRReviewDto::getEntryPoint).collect(Collectors.toSet());
			capacityExitList = capacityExitList.stream().filter(cp -> points.contains(cp.getEntryPoint()))
					.collect(Collectors.toList());
			resultListExit.addAll(crService.getSubtotals(capacityExitList));
			resultListExit.addAll(crService.getTotals(capacityExitList));
			resultListExit.addAll(crService.getDiffEntryExit(resultList, resultListExit));
		}

		applyLabels();
	}

	public void newConnChangeGC(ValueChangeEvent e) {
		String value = "";
		if (e.getNewValue() != null) {
			value = e.getNewValue().toString();
		}
		setnConFilterGC(value);
		applyGCFilters();

	}

	public void newConnChange(ValueChangeEvent e) {
		String value = "";
		if (e.getNewValue() != null) {
			value = e.getNewValue().toString();
		}
		setnConFilter(value);
		applyEntryFilters(true);

	}

	public void newConnChangeExit(ValueChangeEvent e) {
		String value = "";
		if (e.getNewValue() != null) {
			value = e.getNewValue().toString();
		}
		setnConFilterExit(value);
		applyExitFilters(true);

	}

	public Double getMaxValueGC(String element, String point) {
		return getValueForElement(element, point, 0, MAX_VALUE);
	}

	public Double getMinValueGC(String element, String point) {
		return getValueForElement(element, point, 0, MIN_VALUE);
	}

	private Double getValueForElement(String element, String point, int i, int valueType) {
		Double result = null;
		String key = point + "-" + element;
		try {
			if (valueType == 0) {
				result = mapQualityDetails.get(key).get(0).getMinvalue();
			} else {

				result = mapQualityDetails.get(key).get(0).getMaxvalue();
			}

		} catch (Exception e) {
		}
		return result;
	}

	@Deprecated
	public Double getValueForExitMaximunHourBookingMbtu(String period, String entryPoint) {

		// return
		// capacityExitMap.get("hourlyBookingMbtu").get(composeKey(period,
		// entryPoint));
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public Double getValueForExitDailyBookingMbtu(String period, String entryPoint) {

		// return capacityExitMap.get("dailyBookingMbtu").get(composeKey(period,
		// entryPoint));
		throw new UnsupportedOperationException();
	}

	//////////////////////

	@Deprecated
	public Double getValueForEntryDailyBookingMmscfd(String period, String entryPoint) {

		// return
		// capacityEntriesMap.get("dailyBookingMmscfd").get(composeKey(period,
		// entryPoint));
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public Double getValueForEntryMaximunHourBookingMmscfd(String period, String entryPoint) {

		// return
		// capacityEntriesMap.get("hourlyBookingMmscfd").get(composeKey(period,
		// entryPoint));
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public Double getValueForEntryMaximunHourBookingMbtu(String period, String entryPoint) {

		// return
		// capacityEntriesMap.get("hourlyBookingMbtu").get(composeKey(period,
		// entryPoint));
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public Double getValueForEntryDailyBookingMbtu(String period, String entryPoint) {

		// return
		// capacityEntriesMap.get("dailyBookingMbtu").get(composeKey(period,
		// entryPoint));
		throw new UnsupportedOperationException();
	}

	public String composeKey(String period, String entryPoint) {
		String key = entryPoint + "-" + period;
		return key;
	}

	public void entrySwitcher(ActionEvent actionEvent) {
		if (entryQuantityUnit == QUANTITY_UNIT_ENERGY) {
			entryQuantityUnit = QUANTITY_UNIT_FLOW;
		} else {

			entryQuantityUnit = QUANTITY_UNIT_ENERGY;

		}
	}

	public String getEntryQuantityUnitText() {
		if (entryQuantityUnit == QUANTITY_UNIT_FLOW) {
			return super.getMessageResourceString("crew_cuantity_unit_flow", null);
		} else {

			return super.getMessageResourceString("crew_cuantity_unit_energy", null);
		}
	}

	private void initComponents() {
		this.setEntryQuantityUnit(QUANTITY_UNIT_ENERGY);
		resultList = new ArrayList<>();
		capacityEntryList = new ArrayList<>();
		resultListExit = new ArrayList<>();
		capacityExitList = new ArrayList<>();
		editableRequest = crService.isEditable(backFileter.getRequestCode());
		hasPermission = backFileter.getOrigin() == CRMFilterCtl.ORIGING_CAP_REQUEST && SecurityUtils.getSubject()
				.isPermitted(changeSystemView.getSystem() + ".BOOKING.CR_MANAGEMENT.MOD");
	}

	public boolean getCanRead() {
		boolean result = false;
		switch (backFileter.getOrigin()) {
		case CRMFilterCtl.ORIGING_CAP_REQUEST:
			result = SecurityUtils.getSubject()
					.isPermitted(changeSystemView.getSystem() + ".BOOKING.CR_MANAGEMENT.QUERY");
			break;

		case CRMFilterCtl.ORIGING_CAP_QUERY:
			result = SecurityUtils.getSubject().isPermitted(changeSystemView.getSystem() + ".BOOKING.CR_QUERY.QUERY");
			break;
		default:
			result = false;
			break;
		}
		return result;
	}

	public Integer getDynamicBlockSize() {
		return 115 * period.size();
	}

	public List<CRReviewDto> getResultList() {
		return resultList;
	}

	public void setResultList(List<CRReviewDto> resultList) {
		this.resultList = resultList;
	}


	public int getEntryQuantityUnit() {
		return entryQuantityUnit;
	}

	public void setEntryQuantityUnit(int entryQuantityUnit) {
		this.entryQuantityUnit = entryQuantityUnit;
	}

	public List<CRReviewDto> getResultListExit() {
		return resultListExit;
	}

	public void setResultListExit(List<CRReviewDto> resultListExit) {
		this.resultListExit = resultListExit;
	}

	public List<GasQualityDto> getResultListGC() {
		return resultListGC;
	}

	public void setResultListGC(List<GasQualityDto> resultListGC) {
		this.resultListGC = resultListGC;
	}

	public ContractReviewService getCrService() {
		return crService;
	}

	public void setCrService(ContractReviewService crService) {
		this.crService = crService;
	}

	public String getnConFilter() {
		return nConFilter;
	}

	public void setnConFilter(String nConFilter) {
		this.nConFilter = nConFilter;
	}

	public List<Date> getDatesEntry() {
		return datesEntry;
	}

	public void setDatesEntry(List<Date> datesEntry) {
		this.datesEntry = datesEntry;
	}

	public String getDateFromEntry() {
		return dateFromEntry;
	}

	public void setDateFromEntry(String dateFromEntry) {
		this.dateFromEntry = dateFromEntry;
	}

	public String getDateToEntry() {
		return dateToEntry;
	}

	public void setDateToEntry(String dateToEntry) {
		this.dateToEntry = dateToEntry;
	}

	public String getDateFromExit() {
		return dateFromExit;
	}

	public void setDateFromExit(String dateFromExit) {
		this.dateFromExit = dateFromExit;
	}

	public String getDateToExit() {
		return dateToExit;
	}

	public void setDateToExit(String dateToExit) {
		this.dateToExit = dateToExit;
	}

	public String getnConFilterExit() {
		return nConFilterExit;
	}

	public void setnConFilterExit(String nConFilterExit) {
		this.nConFilterExit = nConFilterExit;
	}

	public String getnConFilterGC() {
		return nConFilterGC;
	}

	public void setnConFilterGC(String nConFilterGC) {
		this.nConFilterGC = nConFilterGC;
	}

	public String getDateFromGC() {
		return dateFromGC;
	}

	public void setDateFromGC(String dateFromGC) {
		this.dateFromGC = dateFromGC;
	}

	public String getDateToGC() {
		return dateToGC;
	}

	public void setDateToGC(String dateToGC) {
		this.dateToGC = dateToGC;
	}


	public List<String> getPeriod() {
		return period;
	}

	public void setPeriod(List<String> period) {
		this.period = period;
	}

	public CRMFilterCtl getBackFileter() {
		return backFileter;
	}

	public void setBackFileter(CRMFilterCtl backFileter) {
		this.backFileter = backFileter;
	}

	public BigDecimal getNumericVal() {
		return numericVal;
	}

	public void setNumericVal(BigDecimal numericVal) {
		this.numericVal = numericVal;
	}

	public List<Date> getDatesFromEntry() {
		return datesFromEntry;
	}

	public void setDatesFromEntry(List<Date> datesFromEntry) {
		this.datesFromEntry = datesFromEntry;
	}

	public List<Date> getDatesToEntry() {
		return datesToEntry;
	}

	public void setDatesToEntry(List<Date> datesToEntry) {
		this.datesToEntry = datesToEntry;
	}

	public List<Date> getDatesFromExit() {
		return datesFromExit;
	}

	public void setDatesFromExit(List<Date> datesFromExit) {
		this.datesFromExit = datesFromExit;
	}

	public List<Date> getDatesToExit() {
		return datesToExit;
	}

	public void setDatesToExit(List<Date> datesToExit) {
		this.datesToExit = datesToExit;
	}

	public Map<String, CapacityDetailDto> getCapacityEntriesMap() {
		return capacityEntriesMap;
	}

	public void setCapacityEntriesMap(Map<String, CapacityDetailDto> capacityEntriesMap) {
		this.capacityEntriesMap = capacityEntriesMap;
	}

	public Map<String, CapacityDetailDto> getCapacityExitMap() {
		return capacityExitMap;
	}

	public void setCapacityExitMap(Map<String, CapacityDetailDto> capacityExitMap) {
		this.capacityExitMap = capacityExitMap;
	}

	public ChangeSystemView getChangeSystemView() {
		return changeSystemView;
	}

	public void setChangeSystemView(ChangeSystemView changeSystemView) {
		this.changeSystemView = changeSystemView;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getFirstExit() {
		return firstExit;
	}

	public void setFirstExit(int firstExit) {
		this.firstExit = firstExit;
	}

	public int getFirstEntry() {
		return firstEntry;
	}

	public void setFirstEntry(int firstEntry) {
		this.firstEntry = firstEntry;
	}

	public void sendMail(CapacityDetailDto b) {
		HashMap<String,String> values = new HashMap<String,String>();
		CapacityRequestMailBean crm = crService.getMailData(backFileter.getRequestCode());
			values.put("1", crm.getContract_num());
			values.put("2", crm.getOperation_desc());
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String dateStringStart = df.format(crm.getStart_date());
			String dateStringEnd = df.format(crm.getEnd_date());
			values.put("3", dateStringStart);
			values.put("4", dateStringEnd);
			String texto = "Contract:"+values.get("1")+",Contract Type:"+values.get("2")+
					",Start Date:"+values.get("3")+",End Date:"+values.get("4");
		
		mailService.sendEmail("CAP_REQUEST.TSO.MODIFY.BOOKED", values, getChangeSystemView().getIdn_active(),crm.getIdn_user_group());
		//Para comprobar los valores que se pasan en el email
		getMessages().addMessage(Constants.head_menu[1],
				new MessageBean(Constants.INFO, "Mail values", texto, Calendar.getInstance().getTime())); 
	}
	
	public boolean booleanBackGroud(String stringDate) {
		boolean backgroundColor = false;
		if(stringDate != null && !stringDate.isEmpty()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/yyyy", Locale.ENGLISH);
			try {
	            Date date = dateFormat.parse(stringDate);
	            Calendar calendar = Calendar.getInstance();
	            calendar.setTime(date);
	
	            int month = calendar.get(Calendar.MONTH) + 1; // Sumamos 1 porque los meses en Calendar van de 0 a 11
	            int year = calendar.get(Calendar.YEAR);
	
	            Calendar cal = Calendar.getInstance();
				int currentMonth = cal.get(Calendar.MONTH) + 1;
				int currentYear = cal.get(Calendar.YEAR);
				if (year < currentYear || (year == currentYear && month <= currentMonth)) {
					backgroundColor = true;
		        } else {
		        	backgroundColor = false;
		        }
	        } catch (ParseException e) {
	            System.out.println("Error al analizar la fecha.");
	            e.printStackTrace();
	        }
		}
		return backgroundColor;
	}
}
