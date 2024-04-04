package com.atos.views.dam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;

import com.atos.beans.MessageBean;
import com.atos.beans.dam.MeteredPointShipperBean;
import com.atos.filters.dam.MeteredPointsShipperFilter;
import com.atos.services.dam.MeteredPointsShipperService;
import com.atos.utils.Constants;
import com.atos.utils.DateUtil;
import com.atos.utils.POIXSSFExcelUtils;
import com.atos.views.CommonView;

@ManagedBean(name = "meteredPointShipperView")
@ViewScoped
public class MeteredPointShipperView  extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(MeteredPointShipperView.class);
	
	private List<MeteredPointShipperBean> items;
	private MeteredPointShipperBean selection;
	private List<MeteredPointShipperBean> selectionTableAddEdit;
	List<MeteredPointShipperBean> copySelectionTableAddEdit;
	private List<MeteredPointShipperBean> allDataTableAddEdit;
	private Boolean disabledEdit;
	private Boolean renderedEndDateEdit;
	private Boolean renderedButtonAcceptEdit;
	private Boolean edit;

	//Filters
	private MeteredPointsShipperFilter filters;
	//private Map<BigDecimal, Object> shippers;
	private Map<BigDecimal, Object> meteredPoints;
	private Map<BigDecimal, Object> customerTypes;
	private Map<BigDecimal, Object> groups;
	
	private Date sysdate = DateUtil.getToday();

	@ManagedProperty("#{meteredPointsShipperService}")
	transient private MeteredPointsShipperService service;

	public void setService(MeteredPointsShipperService service) {
		this.service = service;
	}

	public List<MeteredPointShipperBean> getItems() {
		return items;
	}

	public void setItems(List<MeteredPointShipperBean> items) {
		this.items = items;
	}

	public Date getSysdate() {
		return sysdate;
	}

	public void setSysdate(Date sysdate) {
		this.sysdate = sysdate;
	}

	public MeteredPointShipperBean getSelection() {
		if(selection == null) {
			selection = new MeteredPointShipperBean();
		}
		return selection;
	}

	public void setSelection(MeteredPointShipperBean selection) {
		this.selection = selection;
	}

	public Boolean getRenderedEndDateEdit() {
		return renderedEndDateEdit;
	}

	public void setRenderedEndDateEdit(Boolean renderedEndDateEdit) {
		this.renderedEndDateEdit = renderedEndDateEdit;
	}

	public Boolean getRenderedButtonAcceptEdit() {
		return renderedButtonAcceptEdit;
	}

	public void setRenderedButtonAcceptEdit(Boolean renderedButtonAcceptEdit) {
		this.renderedButtonAcceptEdit = renderedButtonAcceptEdit;
	}

	public List<MeteredPointShipperBean> getSelectionTableAddEdit() {
		return selectionTableAddEdit;
	}

	public void setSelectionTableAddEdit(List<MeteredPointShipperBean> selectionTableAddEdit) {
		this.selectionTableAddEdit = selectionTableAddEdit;
	}

	public List<MeteredPointShipperBean> getAllDataTableAddEdit() {
		return allDataTableAddEdit;
	}

	public void setAllDataTableAddEdit(List<MeteredPointShipperBean> allDataTableAddEdit) {
		this.allDataTableAddEdit = allDataTableAddEdit;
	}

	public Boolean getDisabledEdit() {
		return disabledEdit;
	}

	public void setDisabledEdit(Boolean disabledEdit) {
		this.disabledEdit = disabledEdit;
	}

	public MeteredPointsShipperFilter getFilters() {
		return filters;
	}

	public void setFilters(MeteredPointsShipperFilter filters) {
		this.filters = filters;
	}

	@PostConstruct
	public void init() {
		initializeFilters();
	}

	public void onSearch() {
		items = new ArrayList<MeteredPointShipperBean>();
		items = service.selectMeteredPointShipper(filters);

	}

	public void onClear() {
		// RequestContext.getCurrentInstance().reset("form");
//		filters = new ContractNomPointFilter();
//		filters.setStartDate(Calendar.getInstance().getTime());
//		if (items != null) {
//			items.clear();
//		}
	}
	
	public void prepareNew() {
		selection = new MeteredPointShipperBean();
		selection.setIdnSystem(getChangeSystemView().getIdn_active());
		selection.setUserName(getUser().getUsername());
		chargeAddEditTable();
		disabledEdit = false;
		renderedButtonAcceptEdit = true;
		renderedEndDateEdit = true;
		edit = false;
	}
	
	public void prepareEdit(MeteredPointShipperBean item) {
		edit = true;
		selection = item;
		selection.setIdnSystem(getChangeSystemView().getIdn_active());
		selection.setUserName(getUser().getUsername());
		selectionTableAddEdit = new ArrayList<MeteredPointShipperBean>();
		allDataTableAddEdit = new ArrayList<MeteredPointShipperBean>();
		copySelectionTableAddEdit = new ArrayList<MeteredPointShipperBean>();
		if(selection != null) {
			selectionTableAddEdit = service.selectAllDataMeteredPointShipper(selection);
			copySelectionTableAddEdit.addAll(selectionTableAddEdit);
		}
		selection.setIdnMeteringPoint(null);
		chargeAddEditTable();
		if(selection.getIdnShipper() != null && selection.getStartDate().before(Calendar.getInstance().getTime()) && selection.getEndDate().after(Calendar.getInstance().getTime())) {
			//Cuando el dia de hoy está entre las dos fecahs
			//Deshabilitado todo y se puede cambiar fecha fin
			disabledEdit = true;
			renderedButtonAcceptEdit = true;
			renderedEndDateEdit = true;
		}
		if(selection.getIdnShipper() != null && selection.getEndDate().before(Calendar.getInstance().getTime())) {
			//Cuando las fechas son a pasado
			//Deshabilitado todo
			disabledEdit = true;
			renderedButtonAcceptEdit = false;
			renderedEndDateEdit = false;
		}
		if(selection.getIdnShipper() != null && selection.getStartDate().after(Calendar.getInstance().getTime())) {
			//Cuando las fechas son a futuro 
			//habilitar todo
			disabledEdit = false;
			renderedButtonAcceptEdit = true;
			renderedEndDateEdit = false;
		}
		
	}
	
	public void save() throws Throwable {
		String error = "0";
		String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	String[] params = {msgs.getString("metPointShipper_msg") };
    	String summaryMsgOk = CommonView.getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk= CommonView.getMessageResourceString("insert_noOk", params);
    			
		if(selection.getIdnShipper() == null) {
			errorMsg = msgs.getString("metPointShipper_need_shipperId"); 
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
			log.error(errorMsg);
			return;
		}
		if(selection.getStartDate() == null) {
			errorMsg = msgs.getString("metPointShipper_need_dateFrom"); 
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
			log.error(errorMsg);
			return;
		}
		if(selection.getEndDate() == null) {
			errorMsg = msgs.getString("metPointShipper_need_dateTo"); 
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
			log.error(errorMsg);
			return;
		}
		//Si no hay nada seleccionado en la tabla se saca mensaje
		if(selectionTableAddEdit == null || selectionTableAddEdit.isEmpty()) {
			errorMsg = msgs.getString("metPointShipper_need_meteredPointID"); 
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
			log.error(errorMsg);
			return;
		}		
		if(!edit) {
			//Lo que tenemos seleccionado ya existe en base de datos sacamos mensaje
			List<MeteredPointShipperBean> metPoint = service.selectAllDataMeteredPointShipper(selection);
			boolean exist = metPoint.stream()
	                .anyMatch(item -> item.getIdnMeteringPoint().equals(selection.getIdnMeteringPoint()));
			if(exist) {
				errorMsg = msgs.getString("metPointShipper_alreadySaved_meteredPoint"); 
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				return;
			}
			//Si ya existen registro de metered point form shipper se saca mensaje
			MeteredPointsShipperFilter filter = new MeteredPointsShipperFilter(selection.getIdnShipper(), selection.getStartDate(), selection.getEndDate());
			List<MeteredPointShipperBean> metPointShipper = service.selectMeteredPointShipper(filter);
			if(metPointShipper != null && !metPointShipper.isEmpty() && metPointShipper.size() > 0) {
				errorMsg = msgs.getString("metPointShipper_exist_meteredPoint_thoseDates"); 
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				return;
			}
			error = service.insertMeteredPointShipper(selection, selectionTableAddEdit);
			switch (error) {
			case "0":
				String[] par2 = {selection.getShipper(), msgs.getString("metPointShipper_msg") };
				String msg = getMessageResourceString("inserting_ok", par2);
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('dlgNewContractNomPoint').hide();");
				break;
			case "1":
				errorMsg = msgs.getString("metPointShipper_error_inserting"); 
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				break;
			}
		}else if(edit) {
			String updateMsgOk = CommonView.getMessageResourceString("update_ok", params);
	    	String updateMsgNotOk= CommonView.getMessageResourceString("update_noOk", params);
			if(!renderedEndDateEdit) {
				// Filtrar objetos que se agregarán
		        List<MeteredPointShipperBean> addMeteredPoint = selectionTableAddEdit.stream()
		                .filter(object -> copySelectionTableAddEdit.stream().noneMatch(o -> o.getCompositeKey().compareTo(object.getCompositeKey()) == 0))
		                .collect(Collectors.toList());
	
		        // Filtrar objetos que se eliminarán
		        List<MeteredPointShipperBean> deleteMeteredPoint = copySelectionTableAddEdit.stream()
		                .filter(object -> selectionTableAddEdit.stream().noneMatch(o -> o.getCompositeKey().compareTo(object.getCompositeKey()) == 0))
		                .collect(Collectors.toList());
				
		        if(addMeteredPoint != null && !addMeteredPoint.isEmpty()) {
		        	error = service.insertMeteredPointShipper(selection, addMeteredPoint);
		        }
		        if(deleteMeteredPoint != null && !deleteMeteredPoint.isEmpty()) {
		        	error = service.deleteMeteredPointShipper(deleteMeteredPoint);
		        }
			}else {
				error = service.updateDateMeteredPointShipper(selection);
			}
			switch (error) {
			case "0":
				String[] par2 = {selection.getShipper(), msgs.getString("metPointShipper_msg") };
				String msg = getMessageResourceString("updating_ok", par2);
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,updateMsgOk, msg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('dlgNewContractNomPoint').hide();");
				break;
			case "1":
				errorMsg = msgs.getString("metPointShipper_error_update"); 
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, updateMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				break;
			}
		}
	}
	
	public void chargeAddEditTable() {
		if(selection != null) {
			allDataTableAddEdit = service.selectMetPointCustomerGroup(selection);
		}
	}
	
	public Map<BigDecimal, Object> getMeteredPoints() {
		if(selection != null) {
			meteredPoints = service.selectMeteredPoin(selection);
		}
		return meteredPoints;
	}

	public Map<BigDecimal, Object> getCustomerTypes() {
		if(selection != null) {
			customerTypes = service.selectCustomerType(selection);
		}
		return customerTypes;
	}

	public Map<BigDecimal, Object> getGroups() {
		if(selection != null) {
			groups = service.selectGroupId(selection);
		}
		return groups;
	}
	
	public void downloadFile() {
		File file = loadRequest();
		POIXSSFExcelUtils.downloadFile(file);
	}

	//*********************Filters*************************************************
	private void initializeFilters() {
		filters = new MeteredPointsShipperFilter();
		filters.setIdnSystem(getChangeSystemView().getIdn_active());
	}
	
	public Map<BigDecimal, Object> getShippers() {
		return service.selectShippers();
	}
	
	//*********************Other methods***********************************************
	
	private File loadRequest() {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		File file = null;
		try {
			Row row = null;
			Cell cell = null;
			int rowNum = 0;
			XSSFWorkbook workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet("Data");
			Font contentFontBold = POIXSSFExcelUtils.createFont(workBook, IndexedColors.BLACK.index, (short)11, true);
			Font contentFont = POIXSSFExcelUtils.createFont(workBook, IndexedColors.BLACK.index, (short)11, false);
			CellStyle cellStyleTableBlueCenter = POIXSSFExcelUtils.createStyle(workBook, contentFontBold, CellStyle.ALIGN_CENTER, IndexedColors.PALE_BLUE.index, 
					true, IndexedColors.GREY_80_PERCENT.index, CellStyle.BORDER_THIN, false, workBook.getCreationHelper().createDataFormat().getFormat("dd-MMM-yyyy"));
			CellStyle cellStyleTableGreyCenter = POIXSSFExcelUtils.createStyle(workBook, contentFontBold, CellStyle.ALIGN_CENTER, IndexedColors.GREY_25_PERCENT.index, 
					true, IndexedColors.GREY_80_PERCENT.index, CellStyle.BORDER_THIN, false, workBook.getCreationHelper().createDataFormat().getFormat("dd-MMM-yyyy"));
			CellStyle cellStyleTableFormatNumber = POIXSSFExcelUtils.createStyle(workBook, contentFont, CellStyle.ALIGN_RIGHT, IndexedColors.WHITE.index, 
					true, IndexedColors.GREY_80_PERCENT.index, CellStyle.BORDER_THIN, true, workBook.getCreationHelper().createDataFormat().getFormat("#,##0.00"));
			CellStyle cellStyleTableFormatDate = POIXSSFExcelUtils.createStyle(workBook, contentFont, CellStyle.ALIGN_LEFT, IndexedColors.WHITE.index, 
					true, IndexedColors.GREY_80_PERCENT.index, CellStyle.BORDER_THIN, true, workBook.getCreationHelper().createDataFormat().getFormat("dd-MMM-yyyy"));
			CellStyle cellNormalStyle = POIXSSFExcelUtils.createStyle(workBook, contentFont, CellStyle.ALIGN_LEFT, IndexedColors.WHITE.index, 
					true, IndexedColors.GREY_80_PERCENT.index, CellStyle.BORDER_THIN, false, workBook.getCreationHelper().createDataFormat().getFormat("#,##0.00"));
			List<String> headerTable1 = Arrays.asList(msgs.getString("metPointShipper_shipper"), msgs.getString("metPointShipper_startDate"), msgs.getString("metPointShipper_endDate"));
			List<String> headerTable2 = Arrays.asList(msgs.getString("metPointShipper_metered_point_id"), msgs.getString("metPointShipper_customer_type"), msgs.getString("metPointShipper_group_id"));
			//Creamos la cabecera de la tabla principal
			POIXSSFExcelUtils.createSimpleHeaderTable(sheet, row, cell, rowNum, 0, headerTable1, cellStyleTableBlueCenter);
			rowNum ++;
			if(items != null && !items.isEmpty()) {
				for(int i = 0; i < items.size(); i++) {
					row = sheet.createRow(rowNum);
					Object[] properties1 = {
							items.get(i).getShipper(), items.get(i).getStartDate(), items.get(i).getEndDate()
					    };
					POIXSSFExcelUtils.createCellsTable(row, cell, 0, cellStyleTableFormatDate, cellStyleTableFormatNumber, cellNormalStyle, properties1);
					rowNum++;
					//Creamos la cabecera de los detalles con sus detalles
					POIXSSFExcelUtils.createSimpleHeaderTable(sheet, row, cell, rowNum, 1, headerTable2, cellStyleTableGreyCenter);
					selectionTableAddEdit = new ArrayList<MeteredPointShipperBean>();
					selectionTableAddEdit = service.selectAllDataMeteredPointShipper(items.get(i));
					rowNum++;
					if(selectionTableAddEdit != null && !selectionTableAddEdit.isEmpty()) {
						for(int m = 0; m < selectionTableAddEdit.size(); m++) {
							row = sheet.createRow(rowNum);
							Object[] properties2 = {
									selectionTableAddEdit.get(m).getMeteringPoint(), selectionTableAddEdit.get(m).getCustomerType(), selectionTableAddEdit.get(m).getGroup()
							    };
							POIXSSFExcelUtils.createCellsTable(row, cell, 1, cellStyleTableFormatDate, cellStyleTableFormatNumber, cellNormalStyle, properties2);
							rowNum++;
						}
					}
				}
			}
//			if(items != null && !items.isEmpty()) {
//				for(int i = 0; i < items.size(); i++) {
//					row = sheet.createRow(rowNum);
//					for(int j = 0; j < 3; j++) {
//						cell = row.createCell(j);
//						switch (j) {
//						case 0:
//							cell.setCellValue(items.get(i).getShipper());
//							break;
//						case 1:
//							cell.setCellValue(items.get(i).getStartDate());
//							break;
//						case 2:
//							cell.setCellValue(items.get(i).getEndDate());
//							break;
//						}
//					}
//					rowNum++;
//					row = sheet.createRow(rowNum);
//					for(int k = 0; k < headerTable2.size(); k++) {
//						cell = row.createCell(k);
//						cell.setCellValue(headerTable2.get(k));
//					}
//					selectionTableAddEdit = new ArrayList<MeteredPointShipperBean>();
//					selectionTableAddEdit = service.selectAllDataMeteredPointShipper(items.get(i));
//					rowNum++;
//					if(selectionTableAddEdit != null && !selectionTableAddEdit.isEmpty()) {
//						for(int m = 0; m < selectionTableAddEdit.size(); m++) {
//							row = sheet.createRow(rowNum);
//							for(int n = 0; n < 3; n++) {
//								cell = row.createCell(n);
//								switch (n) {
//								case 0:
//									cell.setCellValue(selectionTableAddEdit.get(m).getMeteringPoint());
//									break;
//								case 1:
//									cell.setCellValue(selectionTableAddEdit.get(m).getCustomerType());
//									break;
//								case 2:
//									cell.setCellValue(selectionTableAddEdit.get(m).getGroup());
//									break;
//								}
//							}
//							rowNum++;
//						}
//					}
//				}
//			}

			FileOutputStream outFile = new FileOutputStream("MeteredPointShipper.xlsx");
			workBook.write(outFile);
			outFile.close();
			file = new File("MeteredPointShipper.xlsx");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
