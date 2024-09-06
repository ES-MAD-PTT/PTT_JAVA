package com.atos.views.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormat;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.MeteredPointBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.dam.MeteredPointFilter;
import com.atos.services.dam.MeteredPointService;
import com.atos.utils.Constants;
import com.atos.utils.POIXSSFExcelUtils;
import com.atos.views.CommonView;

@ManagedBean(name = "meteredPointView")
@ViewScoped
public class MeteredPointView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(MeteredPointView.class);

	private MeteredPointFilter filters;
	private MeteredPointBean newMeteredPoint;
	private MeteredPointBean newMeteredPointNewPeriod;
	private MeteredPointBean newPeriodMeteredPoint;
	private List<MeteredPointBean> items, items_backup;

	private BigDecimal idn_area_old = null;
	private String area_old = null;
	
	@ManagedProperty("#{meteredPointService}")
	transient private MeteredPointService service;
	private Calendar sysdate;

	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}


	private Map<BigDecimal, Object> comboPointTypes;
	public MeteredPointFilter getFilters() {
		return filters;
	}

	public void setFilters(MeteredPointFilter filters) {
		this.filters = filters;
	}

	public MeteredPointBean getNewMeteredPoint() {
		return newMeteredPoint;
	}

	public void setNewMeteredPoint(MeteredPointBean newMeteredPoint) {
		this.newMeteredPoint = newMeteredPoint;
	}

	public MeteredPointBean getNewPeriodMeteredPoint() {
		return newPeriodMeteredPoint;
	}

	public void setNewPeriodMeteredPoint(MeteredPointBean newPeriodMeteredPoint) {
		this.newPeriodMeteredPoint = newPeriodMeteredPoint;
	}

	public List<MeteredPointBean> getItems() {
		
		this.items_backup = new ArrayList<MeteredPointBean>();
		if(this.items!=null) {
			for(int i=0;i<this.items.size();i++) {
				MeteredPointBean b = new MeteredPointBean();
				b.setIdn_system_point(items.get(i).getIdn_system_point());
				b.setIdn_system_point_param(items.get(i).getIdn_system_point_param());
				b.setStartDate(items.get(i).getStartDate());
				b.setEndDate(items.get(i).getEndDate());
				this.items_backup.add(b);
			}
		}
		
		return items;
	}

	public void setItems(List<MeteredPointBean> items) {
		this.items = items;
	}

	public void setService(MeteredPointService service) {
		this.service = service;
	}

	@PostConstruct
	public void init() {
		filters = new MeteredPointFilter();
		newMeteredPoint = new MeteredPointBean();
		newPeriodMeteredPoint = new MeteredPointBean();
		newPeriodMeteredPoint.setIdn_pipeline_system(getChangeSystemView().getIdn_active());

		// LOOKING TO THE SYSDATE parameter BD
		sysdate = gettingValidDateStart();
		newMeteredPoint.setStartDate(sysdate.getTime());

		// offShore
		filters.setIdn_system(getChangeSystemView().getIdn_active());

		// cargamos los combos inicialment
		comboPointTypes = service.selectPointTypes();

	}

	// sysdte +1
	public Calendar gettingValidDateStart() {

		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
		Integer valNumDate = valDate.getInteger_exit();
		Calendar sysdate = Calendar.getInstance();
		// StartDate filter => sysdate
		filters.setStartDate(sysdate.getTime());

		sysdate.setTime(valDate.getDate());
		sysdate.set(Calendar.HOUR_OF_DAY, 0);
		sysdate.set(Calendar.MINUTE, 0);
		sysdate.set(Calendar.SECOND, 0);
		sysdate.set(Calendar.MILLISECOND, 0);
		sysdate.add(Calendar.DAY_OF_MONTH, valNumDate.intValue());

		return sysdate;
	}

	public List<String> IdList(String query) {
		query = "%" + query + "%";
		return service.selectIds(query);
	}

	public Map<BigDecimal, Object> getIds() {
		// return service.selectIds(); offshore
		return service.selectIdsSystem(getChangeSystemView().getIdn_active());
	}

	public Map<BigDecimal, Object> getSystems() {
		return service.selectSystems();
	}

	// offShore nuevo combo que filtra por el system
	public Map<BigDecimal, Object> getZonesSystem() {
		return service.selectZonesSystem(getChangeSystemView().getIdn_active());

	}

	// offshore
	public Map<BigDecimal, Object> getAreasSystem() {
		return service.selectAreasSystem(getChangeSystemView().getIdn_active());
	}

	public Map<String, Object> getMeteringIds() {
		return service.selectMeteringIds();
	}

	public Map<BigDecimal, Object> getMeteringPointIds() {
		return service.selectMeteredPointIds();
	}


	public Map<BigDecimal, Object> getPointTypes() {
		return comboPointTypes;
	}

	public Map<BigDecimal, Object> getQualityPointSystem(MeteredPointBean it) {
		if (it == null) {
			return service.selectQualityPointSystem(null);
		} else {

			// offshore añadimos el sistema de la pantalla
			it.setIdn_pipeline_system(getChangeSystemView().getIdn_active());
			return service.selectQualityPointSystem(it);
		}
	}

	public Map<BigDecimal, Object> getCustomerTypesNew(MeteredPointBean it) {
		// return comboCustomerTypes;
		//BigDecimal valVacio = new BigDecimal(-1);

		if (it == null) {
			return service.selectCustomerTypes(null);
		} else {
			// deseleccionamos el combo Nomination point poniendo el valor -1
			// no vale la solucion (cuando hace save tambien pasa por aqui y al
			// limpiar nominationpoint no carga subareas ni contracPoint)
			// it.setIdn_system_point_nomination(valVacio);
			return service.selectCustomerTypes(it.getIdn_system_point_type());
		}
	}

	public Map<BigDecimal, Object> getCustomerTypes(MeteredPointBean it) {
		if (it == null) {
			return service.selectCustomerTypes(null);
		} else {
			return service.selectCustomerTypes(it.getIdn_system_point_type());
		}
	}

	public Map<BigDecimal, Object> getNominationPointsSystem(MeteredPointBean it) {
		if (it == null) {
			return service.selectNominationPointsSystem(null);
		} else {
			// offshore añadimos el sistema de la pantalla
			it.setIdn_pipeline_system(getChangeSystemView().getIdn_active());
			return service.selectNominationPointsSystem(it);
		}
	}

	public Map<BigDecimal, Object> getContractPointsSystem(MeteredPointBean it) {
		if (it == null) {
			return service.selectContractPointsSystem(null);
		} else {
			// el contract point depende del area del punto de nominacion
			it.setIdn_area(service.getAreaID(it.getIdn_system_point_nomination()));

			// offshore añadimos el sistema de la pantalla
			it.setIdn_pipeline_system(getChangeSystemView().getIdn_active());
			return service.selectContractPointsSystem(it);
		}
	}

	public Map<BigDecimal, Object> getAreasNomination() {
		return service.selectAreaNominationPoint(newMeteredPoint);

	}

	public String getAreaCode(MeteredPointBean it) {
		return service.getAreaCode(it.getIdn_system_point_nomination());
	}

	public String getArea(MeteredPointBean it) {
		if(it==null) {
			return "";
		}
		if(this.idn_area_old==null) {
			this.idn_area_old = it.getIdn_area();
			this.area_old = it.getArea();
		}
		if(it.getNewId()==null) {
			it.setIdn_area(this.idn_area_old);
			it.setArea(this.area_old);
			return it.getArea();
		} else {
			it.setIdn_area(service.getAreaID(it.getIdn_system_point_nomination()));
			it.setArea(service.getAreaCode(it.getIdn_system_point_nomination()));
			return it.getArea();
		}
	}
	
	public String getSystemCode(MeteredPointBean it) {
		return service.getSystemCode(it.getIdn_system_point_nomination());
	}

	public String getZoneCode(MeteredPointBean it) {
		return service.getZoneCode(it.getIdn_system_point_nomination());
	}

	public Map<BigDecimal, Object> getSubAreasNomination(MeteredPointBean it) {

		if (it == null) {
			return service.selectSubAreaNominationPoint(null);
		} else {
			return service.selectSubAreaNominationPoint(it);
		}

	}

	// Methods
	public void onSearch() {

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_meteredPoint1').clearFilters()");
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		if (filters.getEndDate() != null) {
			if (filters.getStartDate().after(filters.getEndDate())) {
				String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
				return;
			}
		}
		
		// offShore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectMeteredPoints(filters);
	}

	public void onSearchQuery() {

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_meteredPoint1').clearFilters()");
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		if (filters.getEndDate() != null) {
			if (filters.getStartDate().after(filters.getEndDate())) {
				String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
				return;
			}
		}
		
		// offShore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectMeteredPointsQuery(filters);
	}

	public void onClear() {
		// RequestContext.getCurrentInstance().reset("form");
		filters = new MeteredPointFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
		if (items != null) {
			items.clear();
		}

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_meteredPoint1').clearFilters()");
	}

	public void onRowEdit(RowEditEvent event) {

		MeteredPointBean meteredPoint = (MeteredPointBean) event.getObject();
		//SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
		
		
		String errorMsg = null;
	    
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("meteredPoint") };
    	String summaryMsgOk =  getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
  		

		if (meteredPoint.getEndDate() != null) {
			if (meteredPoint.getStartDate().after(meteredPoint.getEndDate())) {
				errorMsg = msgs.getString("start_previous_end"); //start_previous_end= Start date must be previous to end date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
    	    				
				items = service.selectMeteredPoints(filters);
				return;
			}

		}

		// maxPressure > minPressure
		int res;
		if (meteredPoint.getMaxPressure() != null && meteredPoint.getMinPressure() != null) {
			res = meteredPoint.getMinPressure().compareTo(meteredPoint.getMaxPressure());
			// 0 "Both values are equal "; 1 "First Value is greater "; -1	Second value is greater"
			if (res == 1) {
				errorMsg = msgs.getString("max_preasure_greater_min"); //max_preasure_greater_min=Max Pressure value shoul be greater or iqual tan Min Pressure.
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;
			}
		}

		// the nominal capacity > zero
		if (meteredPoint.getNominalCapacity() != null) {
			if (meteredPoint.getNominalCapacity().compareTo(BigDecimal.ZERO) <= 0) {
				errorMsg = msgs.getString("nominal_capacity_greater_zero"); //nominal_capacity_greater_zero=Nominal Capacity shoul be grater than zero.
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;
			}
		}

		String validateDateRangeError="";
		try {
			boolean validate = true;
			for(int i=0;i<this.items_backup.size();i++) {
				if(meteredPoint.getIdn_system_point().equals(this.items_backup.get(i).getIdn_system_point()) &&
						meteredPoint.getIdn_system_point_param().equals(this.items_backup.get(i).getIdn_system_point_param())) {
					if(meteredPoint.getStartDate()!=null && this.items_backup.get(i).getStartDate()!=null && 
							meteredPoint.getStartDate().equals(this.items_backup.get(i).getStartDate())) {
						if(meteredPoint.getEndDate()!=null && this.items_backup.get(i).getEndDate()!=null) {
							if(meteredPoint.getEndDate().equals(this.items_backup.get(i).getEndDate())){
								validate = false;
							} else {
								validate = true;
							}
						} else {
							if(meteredPoint.getEndDate()!=null ||  this.items_backup.get(i).getEndDate()!=null) {
								validate = true;
							} else {
								validate = false;
							}
						}
					} else {
						validate = true;
					}
				}
					
			}
			if(validate) {
				service.validateDateRange(meteredPoint, getUser(), getLanguage(), "Edit");
			}
		} catch(Exception e) {
			validateDateRangeError = e.getMessage();
		}
		if(!validateDateRangeError.equals("")) {
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, validateDateRangeError, Calendar.getInstance().getTime()));
			log.info("Validate Date Range error", Calendar.getInstance().getTime());
			return;
		}
		if(!service.getValidateGroupIDEdit(meteredPoint)) {
			errorMsg = msgs.getString("groupId_used"); //meteringID used
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
		}
		
		String error = "0";
		try {
			error = service.updateMeteredPoint(meteredPoint);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}

		String[] par2 = { meteredPoint.getId(),msgs.getString("meteredPoint")};
		
		if (error != null && error.equals("0")) {
			String msg =  getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("meteredPoint Updated: " + meteredPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  MeteredPoint " + meteredPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating/insert MeteredPointParam " + meteredPoint.toString(),	Calendar.getInstance().getTime());

		} else if (error != null && error.equals("-10")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error Updating MeteredPointParam. MeteredPointParam, Error: " + error + ". "	+ meteredPoint.toString(), Calendar.getInstance().getTime());
		} else {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating/insert MeteredPoint. MeteredPoint, Error: " + error + ". "	+ meteredPoint.toString(), Calendar.getInstance().getTime());
		}

		// offShore
		filters.setIdn_system(getChangeSystemView().getIdn_active());

		items = service.selectMeteredPoints(filters);

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_meteredPoint1').filter()");

	}

	public void onRowCancel(RowEditEvent event) {
	

	}

	public void cancel() {
		// RequestContext.getCurrentInstance().reset("formNewEntity");
		newMeteredPoint = new MeteredPointBean();
		newMeteredPoint.setStartDate(sysdate.getTime());
	}

	public void cancelPeriod() {
		// RequestContext.getCurrentInstance().reset("formNewEntity");
		newPeriodMeteredPoint = new MeteredPointBean();
		newPeriodMeteredPoint.setIdn_pipeline_system(getChangeSystemView().getIdn_active());
	}

	public void save() {

		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
		Integer valNumDate = valDate.getInteger_exit();

		String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("meteredPoint") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
		
		if (newMeteredPoint.getStartDate() != null) { //No se va a dar.. la pantalla no lo permite
			if (newMeteredPoint.getStartDate().before(sysdate.getTime())) {
				errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
				return;
			}
		}

		if (newMeteredPoint.getEndDate() != null) {//No se va a dar.. la pantalla no lo permite
			if (newMeteredPoint.getEndDate().before(sysdate.getTime())) {
				errorMsg = msgs.getString("error_endDate_sysdate"); //error_endDate_sysdate= End Date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
		    	return;
			}
			if (newMeteredPoint.getStartDate().after(newMeteredPoint.getEndDate())) {
				errorMsg = msgs.getString("error_startEarlierEnd"); //error_startEarlierEnd = Start Date must be earlier or equal to End Date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg );
				return;
			}

		}

		if (newMeteredPoint.getMaxPressure() != null && newMeteredPoint.getMinPressure() != null) {

			int res;
			res = newMeteredPoint.getMinPressure().compareTo(newMeteredPoint.getMaxPressure());
			// 0 "Both values are equal "; 1 "First Value is greater "; -1 Second value is greater"
			if (res == 1) {
				errorMsg = msgs.getString("max_preasure_greater_min"); //max_preasure_greater_min=Max Pressure value shoul be greater or iqual tan Min Pressure.
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				
				return;
			}
		}

		// the nominal capacity > zero
		if (newMeteredPoint.getNominalCapacity().compareTo(BigDecimal.ZERO) <= 0) {
			errorMsg = msgs.getString("nominal_capacity_greater_zero"); //nominal_capacity_greater_zero=Nominal Capacity shoul be grater than zero.
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
		}
		
		// the MOC selected is available yet
		if (service.getMOCAvailable(newMeteredPoint.getMeteringID()).compareTo(BigDecimal.ZERO) != 0) {
			errorMsg = msgs.getString("meteredID_used"); //meteringID used
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
		}
		if(!service.getValidateGroupID(newMeteredPoint)) {
			errorMsg = msgs.getString("groupId_used"); //meteringID used
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
		}

		String error = "0";
		try {
			error = service.insertMeteredPoint(newMeteredPoint);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}

		String[] par2 = {newMeteredPoint.getId(),msgs.getString("meteredPoint") };
		
		if (error != null && error.equals("0")) {
			String msg = getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("MeteredPoint Inserted ok" + newMeteredPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting MeteredPoint. The " + newMeteredPoint.toString()	+ " already exists in the System ", Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));    	
			log.info("Error inserting meteredPoint. Error inserting MeteredPoint" + newMeteredPoint.toString(),	Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-3")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));    	
			log.info("Error inserting meteredPoint. Error inserting MeteredPointParam" + newMeteredPoint.toString(),Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-4")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));    	
			log.info("Error inserting meteredPoint. Error updated MocMetering" + newMeteredPoint.toString(),Calendar.getInstance().getTime());
		} else {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));    	
			log.info("Error inserting meteredPoint. Generic Error: " + newMeteredPoint.toString(),Calendar.getInstance().getTime());
		}

		//items = service.selectMeteredPoints(filters);
		//onSearch();
		service.selectIdsSystem(getChangeSystemView().getIdn_active());

		// clean the formu new after save
		newMeteredPoint = new MeteredPointBean();

		// StartDate New => sysdate +1
		newMeteredPoint.setStartDate(sysdate.getTime());
	}

	public void savePeriod() {

		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
		Integer valNumDate = valDate.getInteger_exit();

		String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("meteredPoint") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
		
    	if(newPeriodMeteredPoint.getNewId()==null || newPeriodMeteredPoint.getNewId().equals("")) {
    		newPeriodMeteredPoint.setIdn_area(this.idn_area_old);
    		newPeriodMeteredPoint.setArea(this.area_old);
    	}
    	
		if (newPeriodMeteredPoint.getStartDate() != null) { //No se va a dar.. la pantalla no lo permite
			if (newPeriodMeteredPoint.getStartDate().before(sysdate.getTime())) {
				errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
				return;
			}
		}

		if (newPeriodMeteredPoint.getEndDate() != null) {//No se va a dar.. la pantalla no lo permite
			if (newPeriodMeteredPoint.getEndDate().before(sysdate.getTime())) {
				errorMsg = msgs.getString("error_endDate_sysdate"); //error_endDate_sysdate= End Date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
		    	return;
			}
			if (newPeriodMeteredPoint.getStartDate().after(newPeriodMeteredPoint.getEndDate())) {
				errorMsg = msgs.getString("error_startEarlierEnd"); //error_startEarlierEnd = Start Date must be earlier or equal to End Date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg );
				return;
			}

		}

		if (newPeriodMeteredPoint.getMaxPressure() != null && newPeriodMeteredPoint.getMinPressure() != null) {

			int res;
			res = newPeriodMeteredPoint.getMinPressure().compareTo(newPeriodMeteredPoint.getMaxPressure());
			// 0 "Both values are equal "; 1 "First Value is greater "; -1 Second value is greater"
			if (res == 1) {
				errorMsg = msgs.getString("max_preasure_greater_min"); //max_preasure_greater_min=Max Pressure value shoul be greater or iqual tan Min Pressure.
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				
				return;
			}
		}

		// the nominal capacity > zero
		if (newPeriodMeteredPoint.getNominalCapacity().compareTo(BigDecimal.ZERO) <= 0) {
			errorMsg = msgs.getString("nominal_capacity_greater_zero"); //nominal_capacity_greater_zero=Nominal Capacity shoul be grater than zero.
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
		}

		String validateDateRangeError="";
		try {
			newPeriodMeteredPoint.setIdn_system_point_param(new BigDecimal(-1));
			service.validateDateRange(newPeriodMeteredPoint, getUser(), getLanguage(), "New");
		} catch(Exception e) {
			validateDateRangeError = e.getMessage();
		}
		if(!validateDateRangeError.equals("")) {
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, validateDateRangeError, Calendar.getInstance().getTime()));
			log.info("Validate Date Range error", Calendar.getInstance().getTime());
			return;
		}
		if(!service.getValidateGroupIDEdit(newPeriodMeteredPoint)) {
			errorMsg = msgs.getString("groupId_used"); //meteringID used
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
		}	
		
		
		
		String error = "0";
		try {
			try {
				newMeteredPointNewPeriod = service.selectMeteredPoint(newPeriodMeteredPoint);
				
				if(newMeteredPointNewPeriod != null) {
					newMeteredPointNewPeriod.setStartDate(newPeriodMeteredPoint.getStartDate());
					service.updateMeteredPointNewPeriod(newMeteredPointNewPeriod);				
				}						
			} catch (Exception e) {
				log.catching(e);
			}
			
			if(newPeriodMeteredPoint.getNewId()!= null && !newPeriodMeteredPoint.getNewId().isEmpty()) {
				MeteredPointBean bean = new MeteredPointBean(newPeriodMeteredPoint);
				error = service.deleteOldMeteredPoint(bean);

				newPeriodMeteredPoint.setPoint_code(newPeriodMeteredPoint.getNewId());
				error = service.insertMeteredPoint(newPeriodMeteredPoint);
				
			}else {
				error = service.insertMeteredPointNewPeriod(newPeriodMeteredPoint);
			}
			
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}

		String[] par2 = {newPeriodMeteredPoint.getPoint_code(),msgs.getString("meteredPoint") };
		
		if (error != null && error.equals("0")) {
			String msg = getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("MeteredPoint Inserted ok" + newPeriodMeteredPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-3")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));    	
			log.info("Error inserting meteredPoint. Error inserting MeteredPointParam" + newPeriodMeteredPoint.toString(),Calendar.getInstance().getTime());
		} else {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));    	
			log.info("Error inserting meteredPoint. Generic Error: " + newPeriodMeteredPoint.toString(),Calendar.getInstance().getTime());
		}

		//items = service.selectMeteredPoints(filters);
		onSearch();
		service.selectIdsSystem(getChangeSystemView().getIdn_active());

		// clean the formu new after save
		newPeriodMeteredPoint = new MeteredPointBean();
		newMeteredPoint = new MeteredPointBean();
		newPeriodMeteredPoint.setIdn_pipeline_system(getChangeSystemView().getIdn_active());

	
	}
	
	// Control methods dates
	public String disabledEndDate(MeteredPointBean item) {
		if (item.getEndDate() != null) {
			if (item.getStartDate().before(sysdate.getTime()) && (item.getEndDate().before(sysdate.getTime()))) {
				return "true";
			}
		}
		return "false";
	}

	public String disabledField(MeteredPointBean item) {
		// 1. En caso de un registro con la fecha Start Date posterior al día actual, el sistema habilitará Todos
		// 2. En caso de un registro con la fecha Start Date anterior o igual al día actual y fecha End Date posterior al día actual (o vacía), el sistema habilitará SOLO END DATE
		// 3. En caso de un registro con la fecha Start Date anterior o igual al día actual, se habilita NADA

		// 18/7/2016 modificado para que edite cuando la fecha startDay sea igual al sysdate

		/*
		 * if(item.getStartDate().after(sysdate.getTime())){ return "false"; }
		 * 
		 * if(item.getEndDate()!=null ){ 
		 * 	if  (item.getStartDate().before(sysdate.getTime()) ||  item.getStartDate().equals(sysdate.getTime()) && ( item.getEndDate().after(sysdate.getTime()) || item.getEndDate().equals(""))){
		 *  return "true"; } 
		 *  }
		 */
// CH748 se permite edicion total sin importar fechas
		if (item.getStartDate().after(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime())) {
			return "false";
		}

		if (item.getStartDate().before(sysdate.getTime()) && (item.getEndDate() == null)
				|| item.getEndDate().after(sysdate.getTime())) {
			return "true";
		}

		if (item.getStartDate().before(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime())) {
			return "true";
		}

		//return "true";
		return "false";
	}

	public String disabledPointType(MeteredPointBean item) {
		return "true";
	}
	public boolean renderItemEditor(MeteredPointBean item) {
		if (item.getEndDate() != null) {
			if (item.getStartDate().before(sysdate.getTime()) && item.getEndDate().before(sysdate.getTime())) {
				return false;

			} else {
				return true;
			}
		} else
			return true; 
		//Cambiado por pruebas SAT CR5
		/*if (item.getStartDate().after(sysdate.getTime()))
				return true;
		else return false;*/
	}

	public void generarMOC() {

		// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		String summaryMsg = null;
		String errorMsg = null;

		try {
			service.generarMOC(getUser(), getLanguage());
		} catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
			errorMsg = ve.getMessage();
			
			getMessages().addMessage(Constants.head_menu[0],	new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			log.error(ve.getMessage(), ve);
			return;
		} catch (Exception e) {
			summaryMsg = msgs.getString("saving_data_error");
			errorMsg = msgs.getString("generating_MOC_error");
			getMessages().addMessage(Constants.head_menu[0],	new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			log.error(e.getMessage(), e);
			return;
		}

		summaryMsg = msgs.getString("generating_MOC_ok");
		String okMsg = msgs.getString("generating_MOC_executed");

		getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
		log.info(okMsg);

	}
	
	public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}

	public void selectMeteredPointID(ValueChangeEvent e) {
		String value = "";
		if (e.getNewValue() != null) {
			value = (String) e.getNewValue();
		}
		BigDecimal idn_system_point = new BigDecimal (value);
		newPeriodMeteredPoint.setIdn_system_point(idn_system_point);
		MeteredPointBean bean = service.selectMeteredPointValues(newPeriodMeteredPoint.getIdn_system_point());
		bean.setIdn_pipeline_system(newPeriodMeteredPoint.getIdn_pipeline_system());
		newPeriodMeteredPoint = bean;
		this.idn_area_old = newPeriodMeteredPoint.getIdn_area();
		this.area_old = newPeriodMeteredPoint.getArea();
	}
	
	
public void postProcessXLS(Object document) {

	HSSFWorkbook wb;
	
		wb = new HSSFWorkbook();
		wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		
		wb.setSheetName(0, "MeteredPoint");		
				
		// Generate styles
		DataFormat format = wb.createDataFormat();
		
		HSSFCellStyle cellStyleHeader  =  POIXSSFExcelUtils.createStyleHeader(wb);
		HSSFCellStyle cellStyleHide  =     POIXSSFExcelUtils.createStyleHide(wb);
		HSSFCellStyle cellStyleText  =     POIXSSFExcelUtils.createStyleText(wb);
		HSSFCellStyle cellStyleTotal  =     POIXSSFExcelUtils.createStyleTotal(wb);
		HSSFCellStyle cellStyleTwoeDec =   POIXSSFExcelUtils.createStyleTwoDec(wb);
		HSSFCellStyle cellStyleThreeDec =  POIXSSFExcelUtils.createStyleThreeDec(wb);
		HSSFCellStyle cellStyleFourDec =   POIXSSFExcelUtils.createStyleFourDec(wb);
		HSSFCellStyle cellStyleTotalTwoeDec =   POIXSSFExcelUtils.createStyleTotalTwoDec(wb);
		HSSFCellStyle cellStylZeroDec  =        POIXSSFExcelUtils.createStyleZeroDec(wb);
		
		//CABECERA: quitamos el see datail
		for (int i = 0; i < header.getPhysicalNumberOfCells() - 1; i++) {
			HSSFCell cell = header.getCell(i);
			cell.setCellStyle(cellStyleHeader);
			if (i == 22) {
				cell.setCellStyle(cellStyleHide);
				cell.setCellValue(" ");
			}
			
		}
		//ancho de la cabecera
		header.setHeight((short) 0x249);

		//DETALLE	
	     for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				for (int j = 0; j <= sheet.getRow(i).getPhysicalNumberOfCells() - 1; j++) {
					
					HSSFCell cell = sheet.getRow(i).getCell(j);
					cell.setCellStyle(cellStyleText);
					String tmpStrValue = cell.getStringCellValue();

					
					// el NOMINAL capacity , presures
					if (j ==4 || j ==5 ||j ==6) {
						cell.setCellStyle(cellStyleThreeDec);
						
					}
					
						//el mail que sale raro
					if (j == 19) {
						cell.setCellStyle(cellStyleText);
						
					}
					
					// el lapiz
					if (j == 22) {
						cell.setCellStyle(cellStyleHide);
						cell.setCellValue(" ");
					}
					
				}//for (int j = 0; j <= sheet.getRow(i).getPhysicalNumberOfCells() - 1; j++) {
//				for (int k = 0; k < sheet.getRow(i).getPhysicalNumberOfCells() - 1; k++) {
//					sheet.autoSizeColumn(k);
//				}
				
	     } //for (int i = 1; i < sheet.getPhysicalNumberOfRows()
	     
		for (int k = 0; k < sheet.getRow(0).getPhysicalNumberOfCells() - 1; k++) {
			sheet.autoSizeColumn(k);
		}		
	}
}
