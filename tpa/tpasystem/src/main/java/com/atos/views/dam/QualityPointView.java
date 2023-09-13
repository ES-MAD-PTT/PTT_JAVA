package com.atos.views.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.DataFormat;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.QualityPointBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.dam.QualityPointFilter;
import com.atos.services.dam.QualityPointService;
import com.atos.utils.Constants;
import com.atos.utils.POIXSSFExcelUtils;
import com.atos.views.CommonView;

@ManagedBean(name = "qualityPointView")
@ViewScoped
public class QualityPointView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(QualityPointView.class);

	private QualityPointFilter filters;
	private QualityPointBean newQualityPoint;
	private List<QualityPointBean> items;

	private Map<BigDecimal, Object> comboPointTypes;
	
	@ManagedProperty("#{qualityPointService}")
	transient private QualityPointService service;
	
	private Calendar sysdate;

	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	
	public QualityPointFilter getFilters() {
		return filters;
	}

	public void setFilters(QualityPointFilter filters) {
		this.filters = filters;
	}

	public QualityPointBean getNewQualityPoint() {
		return newQualityPoint;
	}

	public void setNewQualityPoint(QualityPointBean newQualityPoint) {
		this.newQualityPoint = newQualityPoint;
	}

	public List<QualityPointBean> getItems() {
		return items;
	}

	public void setItems(List<QualityPointBean> items) {
		this.items = items;
	}

	public void setService(QualityPointService service) {
		this.service = service;
	}

	@PostConstruct
	public void init() {
		filters = new QualityPointFilter();
		newQualityPoint = new QualityPointBean();

		// LOOKING TO THE SYSDATE parameter BD
		sysdate = gettingValidDateStart();
		newQualityPoint.setStartDate(sysdate.getTime());

		// offShore
		filters.setIdnPipelineSystem(getChangeSystemView().getIdn_active());

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


	public Map<BigDecimal, Object> getSystems() {
		return service.selectSystems();
	}
	public Map<BigDecimal, Object> getPointTypes() {
		return comboPointTypes;
	}

	//SELECT DEL combo del filtro 
	public Map<BigDecimal, Object> getQualityPointSystem() {
		return service.selectQualityPointSystem(getChangeSystemView().getIdn_active());

	}

		// Methods
	public void onSearch() {

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_qualityPoint1').clearFilters()");
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
		filters.setIdnPipelineSystem(getChangeSystemView().getIdn_active());
		items = service.selectQualityPoints(filters);

	}

	public void onClear() {
		// RequestContext.getCurrentInstance().reset("form");
		filters = new QualityPointFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
		if (items != null) {
			items.clear();
		}

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_qualityPoint1').clearFilters()");
	}

	public void onRowEdit(RowEditEvent event) {

		QualityPointBean qualityPoint = (QualityPointBean) event.getObject();
		//SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
		
		
		String errorMsg = null;
	    
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("qualityPoint") };
    	String summaryMsgOk =  getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
  		

		if (qualityPoint.getEndDate() != null) {
			if (qualityPoint.getStartDate().after(qualityPoint.getEndDate())) {
				errorMsg = msgs.getString("start_previous_end"); //start_previous_end= Start date must be previous to end date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
    	    				
				items = service.selectQualityPoints(filters);
				return;
			}

		}

	

		String error = "0";
		try {
			error = service.updateQualityPoint(qualityPoint);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}

		String[] par2 = { qualityPoint.getPointCode(),msgs.getString("qualityPoint")};
//rosa dar una vuelta a los retornos...		
		if (error != null && error.equals("0")) {
			String msg =  getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("qualityPoint Updated: " + qualityPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  QualityPoint " + qualityPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating/insert QualityPointParam " + qualityPoint.toString(),	Calendar.getInstance().getTime());

		} else if (error != null && error.equals("-10")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error Updating QualityPointParam. QualityPointParam, Error: " + error + ". "	+ qualityPoint.toString(), Calendar.getInstance().getTime());
		} else {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating/insert QualityPoint. QualityPoint, Error: " + error + ". "	+ qualityPoint.toString(), Calendar.getInstance().getTime());
		}

		// offShore
		filters.setIdnPipelineSystem(getChangeSystemView().getIdn_active());

		items = service.selectQualityPoints(filters);

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_qualityPoint1').filter()");

	}

	public void onRowCancel(RowEditEvent event) {
	

	}

	public void cancel() {
		// RequestContext.getCurrentInstance().reset("formNewEntity");
		newQualityPoint = new QualityPointBean();
		newQualityPoint.setStartDate(sysdate.getTime());
	}

	public void save() {

		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
		Integer valNumDate = valDate.getInteger_exit();

		String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("qualityPoint") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
		
		if (newQualityPoint.getStartDate() != null) { //No se va a dar.. la pantalla no lo permite
			if (newQualityPoint.getStartDate().before(sysdate.getTime())) {
				errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
				return;
			}
		}

		if (newQualityPoint.getEndDate() != null) {//No se va a dar.. la pantalla no lo permite
			if (newQualityPoint.getEndDate().before(sysdate.getTime())) {
				errorMsg = msgs.getString("error_endDate_sysdate"); //error_endDate_sysdate= End Date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
		    	return;
			}
			if (newQualityPoint.getStartDate().after(newQualityPoint.getEndDate())) {
				errorMsg = msgs.getString("error_startEarlierEnd"); //error_startEarlierEnd = Start Date must be earlier or equal to End Date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg );
				return;
			}

		}


		String error = "0";
		try {
			error = service.insertQualityPoint(newQualityPoint);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}

		String[] par2 = {newQualityPoint.getPointCode(),msgs.getString("qualityPoint") };
//rosa dar una vuelta a los retornos		
		if (error != null && error.equals("0")) {
			String msg = getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("QualityPoint Inserted ok" + newQualityPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting QualityPoint. The " + newQualityPoint.toString()	+ " already exists in the System ", Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));    	
			log.info("Error inserting qualityPoint. Error inserting QualityPoint" + newQualityPoint.toString(),	Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-3")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));    	
			log.info("Error inserting qualityPoint. Error inserting QualityPointParam" + newQualityPoint.toString(),Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-4")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));    	
			log.info("Error inserting qualityPoint. Error updated MocMetering" + newQualityPoint.toString(),Calendar.getInstance().getTime());
		}


		onSearch();
	//	service.selectIdsSystem(getChangeSystemView().getIdn_active());
		service.selectQualityPointSystem(getChangeSystemView().getIdn_active());
		
		// clean the formu new after save
		newQualityPoint = new QualityPointBean();

		// StartDate New => sysdate +1
		newQualityPoint.setStartDate(sysdate.getTime());
	}

	// Control methods dates
	public String disabledEndDate(QualityPointBean item) {
		if (item.getEndDate() != null) {
			if (item.getStartDate().before(sysdate.getTime()) && (item.getEndDate().before(sysdate.getTime()))) {
				return "true";
			}
		}
		return "false";
	}

	public String disabledField(QualityPointBean item) {
		// 1. En caso de un registro con la fecha Start Date posterior al día actual, el sistema habilitará Todos
		// 2. En caso de un registro con la fecha Start Date anterior o igual al día actual y fecha End Date posterior al día actual (o vacía), el sistema habilitará SOLO END DATE
		// 3. En caso de un registro con la fecha Start Date anterior o igual al día actual, se habilita NADA

		
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

	public boolean renderItemEditor(QualityPointBean item) {
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
		
		wb.setSheetName(0, "QualityPoint");		
				
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
		/*			if (j ==4 || j ==5 ||j ==6) {
						cell.setCellStyle(cellStyleThreeDec);
						
					}
					
						//el mail que sale raro
					if (j == 19) {
						cell.setCellStyle(cellStyleText);
						
					}*/
					
					// el lapiz
					if (j == 5) {
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
