package com.atos.views.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormat;
import org.primefaces.event.RowEditEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.NominationPointBean;
import com.atos.filters.dam.NominationPointFilter;
import com.atos.services.dam.NominationPointService;
import com.atos.utils.Constants;
import com.atos.utils.POIXSSFExcelUtils;
import com.atos.views.CommonView;

@ManagedBean(name = "nominationPointView")
@ViewScoped
public class NominationPointView  extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(NominationPointView.class);

	private NominationPointFilter filters;
	private NominationPointBean newNominationPoint;
	private List<NominationPointBean> items;

	@ManagedProperty("#{nominationPointService}")
	transient private NominationPointService service;

	private Calendar sysdate;

	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}


	private Map<BigDecimal, Object> comboPointTypes;
	private Map<BigDecimal, Object> comboSystems;
	private Map<BigDecimal, Object> comboZonas;


	public NominationPointFilter getFilters() {
		return filters;
	}

	public void setFilters(NominationPointFilter filters) {
		this.filters = filters;
	}

	public NominationPointBean getNewNominationPoint() {
		return newNominationPoint;
	}

	public void setNewNominationPoint(NominationPointBean newNominationPoint) {
		this.newNominationPoint = newNominationPoint;
	}

	public List<NominationPointBean> getItems() {
		return items;
	}

	public void setItems(List<NominationPointBean> items) {
		this.items = items;
	}

	public void setService(NominationPointService service) {
		this.service = service;
	}


	@PostConstruct
	public void init() {
		filters = new NominationPointFilter();
		newNominationPoint = new NominationPointBean();

		// LOOKING TO THE SYSDATE parameter BD
		sysdate = gettingValidDateStart();
		newNominationPoint.setStartDate(sysdate.getTime());

		// cargamos los combos inicialment
		comboPointTypes = service.selectPointTypes();

		// offshore
		comboSystems = service.selectSystems(getChangeSystemView().getIdn_active());
		comboZonas = service.selectZonesSystem(getChangeSystemView().getIdn_active());
		filters.setIdn_system(getChangeSystemView().getIdn_active());
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

	
	// offshore
	public Map<BigDecimal, Object> getIds() {
		return service.selectIdsSystem(getChangeSystemView().getIdn_active());
	}

	public Map<BigDecimal, Object> getSystems() {
		return comboSystems;
	}

	public Map<BigDecimal, Object> getAreas(NominationPointBean it) {
		if (it == null) {
			return service.selectAreas(null);
		} else {
			return service.selectAreas(it);
		}

	}

	// offShore nuevo combo que filtra por el system
	public Map<BigDecimal, Object> getZonesSystem() {
		return comboZonas;
	}

	public Map<BigDecimal, Object> getAreasSystem() {
		return service.selectAreasSystem(getChangeSystemView().getIdn_active());
	}


	public Map<BigDecimal, Object> getPointTypes() {
		return comboPointTypes;
	}


	public void onSearch() {
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		if (filters.getEndDate() != null) {
			if (filters.getStartDate().after(filters.getEndDate())) {
				String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
				return;
			}
		}
		
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectNominationPoints(filters);

	}

	public void onClear() {
		// RequestContext.getCurrentInstance().reset("form");
		filters = new NominationPointFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
		if (items != null) {
			items.clear();
		}
	}

	public void onRowEdit(RowEditEvent event) {

		NominationPointBean nominationPoint = (NominationPointBean) event.getObject();

		String errorMsg = null;
	    
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("nominationPoint") };
    	String summaryMsgOk = CommonView.getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk= CommonView.getMessageResourceString("update_noOk", params);
		
		if (nominationPoint.getEndDate() != null) {
			if (nominationPoint.getStartDate().after(nominationPoint.getEndDate())) {
				errorMsg = msgs.getString("start_previous_end"); //start_previous_end= Start date must be previous to end date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;
			}

		}

		if (nominationPoint.getMaxPressure() != null && nominationPoint.getMinPressure() != null) {
			int res;
			res = nominationPoint.getMinPressure().compareTo(nominationPoint.getMaxPressure());

			 // 0 "Both values are equal; 1 "First Value is greater; -1 Second value is greater "
			if (res == 1) {
				errorMsg = msgs.getString("max_preasure_greater_min"); //max_preasure_greater_min=Max Pressure value shoul be greater or iqual tan Min Pressure.
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;
			}

		}

		String error = "0";
		try {
			error = service.updateNominationPoint(nominationPoint);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}
		String[] par2 = {nominationPoint.getId(),msgs.getString("nominationPoint")};
		
		if (error != null && error.equals("0")) {
			String msg = CommonView.getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("NominationPoint Updated: " + nominationPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  NominationPoint " + nominationPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating/insert NominationPointParam " + nominationPoint.toString(),	Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-10")) {
			String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error Updating NominationPointParam. NominationPointParam, Error: " + error + ". "	+ nominationPoint.toString(), Calendar.getInstance().getTime());
		} else {
			String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating/insert NominationPoint. NominationPoint, Error: " + error + ". "+ nominationPoint.toString(), Calendar.getInstance().getTime());
		}
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectNominationPoints(filters);
	}

	public void onRowCancel(RowEditEvent event) {
	

	}

	public void cancel() {
		// RequestContext.getCurrentInstance().reset("formNewEntity");
		newNominationPoint = new NominationPointBean();
		newNominationPoint.setStartDate(sysdate.getTime());
	}

	public void save() {

		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
		Integer valNumDate = valDate.getInteger_exit();
		
		String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("nominationPoint") };
    	String summaryMsgOk = CommonView.getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk= CommonView.getMessageResourceString("insert_noOk", params);

		if (newNominationPoint.getStartDate() != null) {
			if (newNominationPoint.getStartDate().before(sysdate.getTime())) {
				errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
				return;
			}
		}

		if (newNominationPoint.getEndDate() != null) {
			if (newNominationPoint.getEndDate().before(sysdate.getTime())) {
				errorMsg = msgs.getString("error_endDate_sysdate"); //error_endDate_sysdate= End Date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
				return;
			}
			if (newNominationPoint.getStartDate().after(newNominationPoint.getEndDate())) {
				errorMsg = msgs.getString("error_startEarlierEnd"); //error_startEarlierEnd = Start Date must be earlier or equal to End Date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg );
				return;
			}

		}

		if (newNominationPoint.getMaxPressure() != null && newNominationPoint.getMinPressure() != null) {

			int res;
			res = newNominationPoint.getMinPressure().compareTo(newNominationPoint.getMaxPressure());

			// 0 Both values are equal;1 "First Value is greater ; -1 Second value is greater
			if (res == 1) {
				errorMsg = msgs.getString("max_preasure_greater_min"); //max_preasure_greater_min=Max Pressure value shoul be greater or iqual tan Min Pressure.
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;
			}
		}

		String error = "0";
		try {
			error = service.insertNominationPoint(newNominationPoint);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}

		String[] par2 = {newNominationPoint.getId(),msgs.getString("nominationPoint") };
		
		if (error != null && error.equals("0")) {
			String msg = getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("NominationPoint Inserted ok" + newNominationPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting NominationPoint. The " + newNominationPoint.toString() + " already exists in the System ", Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));			
			log.info("Error inserting nominationPoint. Error inserting NominationPoint" + newNominationPoint.toString(),Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-3")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting nominationPoint. Error inserting NominationPointParam"+ newNominationPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-4")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting nominationPoint. Error inserting ContractPoint" + newNominationPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-5")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting nominationPoint. Error inserting NominationPointParam(contract)"	+ newNominationPoint.toString(), Calendar.getInstance().getTime());
		} else {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting nominationPoint. Generic Error: "	+ newNominationPoint.toString(), Calendar.getInstance().getTime());
		}

		filters.setIdn_system(getChangeSystemView().getIdn_active());
		//items = service.selectNominationPoints(filters);
		onSearch();

		// cargamos de nuevo el combo Ids para que se refleje el alta offShore
		service.selectIdsSystem(getChangeSystemView().getIdn_active());

		// clean the formu new after save
		newNominationPoint = new NominationPointBean();

		// StartDate New => sysdate +1
		newNominationPoint.setStartDate(sysdate.getTime());

	}

	public String disabledEndDate(NominationPointBean item) {
		if (item.getEndDate() != null) {
			if (item.getStartDate().before(sysdate.getTime()) && (item.getEndDate().before(sysdate.getTime()))) {
				return "true";
			}
		}
		return "false";
	}

	public String disabledField(NominationPointBean item) {
		// 1. En caso de un registro con la fecha Start Date posterior al día actual, el sistema habilitará Todos
		// 2. En caso de un registro con la fecha Start Date anterior o igual al día actual y fecha End Date posterior al día actual (o vacía), el sistema habilitará SOLO END DATE
		// 3. En caso de un registro con la fecha Start Date anterior o igual al día actual, se habilita NADA

		// 18/7/2016 modificado para que edite cuando la fecha startDay sea igual al sysdate

		/*
		 * if(item.getStartDate().after(sysdate.getTime())){ return "false"; }
		 * 
		 * if(item.getEndDate()!=null ){ 
		 * if (item.getStartDate().before(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime()) && ( item.getEndDate().after(sysdate.getTime()) || item.getEndDate().equals(""))){ return "true"; } }
		 */

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

		return "true";
	}

	public boolean renderItemEditor(NominationPointBean item) {
		if (item.getEndDate() != null) {
			if (item.getStartDate().before(sysdate.getTime()) && item.getEndDate().before(sysdate.getTime())) {
				return false;

			} else {
				return true;
			}
		} else
			return true;
	}

	
	public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
	
	public void postProcessXLS(Object document) {
		
		HSSFWorkbook wb;
		
			wb = new HSSFWorkbook();
			wb = (HSSFWorkbook) document;
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow header = sheet.getRow(0);
			
			wb.setSheetName(0, "NominationPoint");		
					
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
						if (j ==8 || j ==9 ||j ==10) {
							cell.setCellStyle(cellStyleThreeDec);
							
						}
						
						// el lapiz
						if (j == 13) {
							cell.setCellStyle(cellStyleHide);
							cell.setCellValue(" ");
						}
						
					}//for (int j = 0; j <= sheet.getRow(i).getPhysicalNumberOfCells() - 1; j++) {
					
					for (int k = 0; k < sheet.getRow(i).getPhysicalNumberOfCells() - 1; k++) {
						sheet.autoSizeColumn(k);
					}
					
		     } //for (int i = 1; i < sheet.getPhysicalNumberOfRows()
		}
}
