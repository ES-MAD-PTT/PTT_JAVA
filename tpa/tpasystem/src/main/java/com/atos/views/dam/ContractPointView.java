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
import com.atos.beans.dam.ContractPointBean;
import com.atos.filters.dam.ContractPointFilter;
import com.atos.services.dam.ContractPointService;
import com.atos.utils.Constants;
import com.atos.utils.POIXSSFExcelUtils;
import com.atos.views.CommonView;

@ManagedBean(name = "contractPointView")
@ViewScoped
public class ContractPointView  extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(ContractPointView.class);

	private ContractPointFilter filters;
	private ContractPointBean newContractPoint;
	private List<ContractPointBean> items;

	@ManagedProperty("#{contractPointService}")
	transient private ContractPointService service;

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


	public ContractPointFilter getFilters() {
		return filters;
	}

	public void setFilters(ContractPointFilter filters) {
		this.filters = filters;
	}

	public ContractPointBean getNewContractPoint() {
		return newContractPoint;
	}

	public void setNewContractPoint(ContractPointBean newContractPoint) {
		this.newContractPoint = newContractPoint;
	}

	public List<ContractPointBean> getItems() {
		return items;
	}

	public void setItems(List<ContractPointBean> items) {
		this.items = items;
	}

	public void setService(ContractPointService service) {
		this.service = service;
	}


	@PostConstruct
	public void init() {
		filters = new ContractPointFilter();
		newContractPoint = new ContractPointBean();

		// LOOKING TO THE SYSDATE parameter BD
		sysdate = gettingValidDateStart();
		newContractPoint.setStartDate(sysdate.getTime());

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

	public Map<BigDecimal, Object> getAreas(ContractPointBean it) {
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
		items = service.selectContractPoints(filters);

	}

	public void onClear() {
		// RequestContext.getCurrentInstance().reset("form");
		filters = new ContractPointFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
		if (items != null) {
			items.clear();
		}
	}

	public void onRowEdit(RowEditEvent event) {

		ContractPointBean contractPoint = (ContractPointBean) event.getObject();

		String errorMsg = null;
	    
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("contractPoint") };
    	String summaryMsgOk = CommonView.getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk= CommonView.getMessageResourceString("update_noOk", params);
		
		if (contractPoint.getEndDate() != null) {
			if (contractPoint.getStartDate().after(contractPoint.getEndDate())) {
				errorMsg = msgs.getString("start_previous_end"); //start_previous_end= Start date must be previous to end date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;
			}

		}

		String error = "0";
		try {
			error = service.updateContractPoint(contractPoint);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}
		String[] par2 = {contractPoint.getId(),msgs.getString("contractPoint")};
		
		if (error != null && error.equals("0")) {
			String msg = CommonView.getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("ContractPoint Updated: " + contractPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  ContractPoint " + contractPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating/insert ContractPointParam " + contractPoint.toString(),	Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-10")) {
			String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error Updating ContractPointParam. ContractPointParam, Error: " + error + ". "	+ contractPoint.toString(), Calendar.getInstance().getTime());
		} else {
			String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating/insert ContractPoint. ContractPoint, Error: " + error + ". "+ contractPoint.toString(), Calendar.getInstance().getTime());
		}
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectContractPoints(filters);
	}

	public void onRowCancel(RowEditEvent event) {
	

	}

	public void cancel() {
		// RequestContext.getCurrentInstance().reset("formNewEntity");
		newContractPoint = new ContractPointBean();
		newContractPoint.setStartDate(sysdate.getTime());
	}

	public void save() {

		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
		Integer valNumDate = valDate.getInteger_exit();
		
		String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("contractPoint") };
    	String summaryMsgOk = CommonView.getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk= CommonView.getMessageResourceString("insert_noOk", params);

		if (newContractPoint.getStartDate() != null) {
			if (newContractPoint.getStartDate().before(sysdate.getTime())) {
				errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
				return;
			}
		}

		if (newContractPoint.getEndDate() != null) {
			if (newContractPoint.getEndDate().before(sysdate.getTime())) {
				errorMsg = msgs.getString("error_endDate_sysdate"); //error_endDate_sysdate= End Date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
				return;
			}
			if (newContractPoint.getStartDate().after(newContractPoint.getEndDate())) {
				errorMsg = msgs.getString("error_startEarlierEnd"); //error_startEarlierEnd = Start Date must be earlier or equal to End Date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg );
				return;
			}

		}

		String error = "0";
		try {
			error = service.insertContractPoint(newContractPoint);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}

		String[] par2 = {newContractPoint.getId(),msgs.getString("contractPoint") };
		
		if (error != null && error.equals("0")) {
			String msg = getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("ContractPoint Inserted ok" + newContractPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting ContractPoint. The " + newContractPoint.toString() + " already exists in the System ", Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));			
			log.info("Error inserting contractPoint. Error inserting ContractPoint" + newContractPoint.toString(),Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-3")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting contractPoint. Error inserting ContractPointParam"+ newContractPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-4")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting contractPoint. Error inserting ContractPoint" + newContractPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-5")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting contractPoint. Error inserting ContractPointParam(contract)"	+ newContractPoint.toString(), Calendar.getInstance().getTime());
		} else {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting contractPoint. Generic Error: "	+ newContractPoint.toString(), Calendar.getInstance().getTime());
		}

		filters.setIdn_system(getChangeSystemView().getIdn_active());
		//items = service.selectContractPoints(filters);
		onSearch();

		// cargamos de nuevo el combo Ids para que se refleje el alta offShore
		service.selectIdsSystem(getChangeSystemView().getIdn_active());

		// clean the formu new after save
		newContractPoint = new ContractPointBean();

		// StartDate New => sysdate +1
		newContractPoint.setStartDate(sysdate.getTime());

	}

	public String disabledEndDate(ContractPointBean item) {
		if (item.getEndDate() != null) {
			if (item.getStartDate().before(sysdate.getTime()) && (item.getEndDate().before(sysdate.getTime()))) {
				return "true";
			}
		}
		return "false";
	}

	public String disabledField(ContractPointBean item) {
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

	public boolean renderItemEditor(ContractPointBean item) {
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
			
			wb.setSheetName(0, "ContractPoint");		
					
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
						/*String tmpStrValue = cell.getStringCellValue();

						
						// el NOMINAL capacity , presures
						if (j ==8 || j ==9 ||j ==10) {
							cell.setCellStyle(cellStyleThreeDec);
							
						}
						*/
						// el lapiz
						if (j >= 8) {
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
