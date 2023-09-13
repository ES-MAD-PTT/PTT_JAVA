package com.atos.views.allocation;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;

import com.atos.beans.MessageBean;
import com.atos.beans.allocation.AllocationBean;
import com.atos.beans.allocation.AllocationIntradayBean;
import com.atos.beans.allocation.AllocationIntradayDetailBean;
import com.atos.beans.allocation.AllocationReportDetailDto;
import com.atos.beans.allocation.AllocationReportDto;
import com.atos.exceptions.ValidationException;
import com.atos.filters.allocation.AllocRepQryFilter;
import com.atos.filters.allocation.AllocationIntradayFilter;
import com.atos.filters.allocation.AllocationManagementFilter;
import com.atos.filters.nominations.IntradayNomSummaryFilter;
import com.atos.services.allocation.AllocationIntradayService;
import com.atos.services.allocation.AllocationManagementService;
import com.atos.utils.Constants;
import com.atos.utils.DateUtil;
import com.atos.views.CommonView;

@ManagedBean(name="allocationIntradayView")
@ViewScoped
public class AllocationIntradayView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5980784834639303828L;
	private static final String EnergyUnit = "MMBTU/D";					// Unidad de medida de la pagina.
	
	private BigDecimal factorFromDefaultUnit = null;
	private AllocationIntradayFilter filters;
	private List<AllocationIntradayBean> items;	
	private List<AllocationIntradayDetailBean> detailItems;
	private Map<String, List<AllocationIntradayDetailBean>> detailMap;
	private List<AllocationIntradayBean> selectedItems;
	private Calendar sysdate;
	private Date openPeriodFirstDay = null;
	private Integer allocationMaxDateOffset = null;
	private Date responsePeriodStartDate = null;
	//private Date responsePeriodEndDate = null;
	private static final Logger log = LogManager.getLogger("com.atos.views.allocation.AllocationIntradayView");
	
	
	@ManagedProperty("#{AllocationIntradayService}")
    transient private AllocationIntradayService service;
	
	@ManagedProperty("#{AllocationManagService}")
	transient private AllocationManagementService e_service;
    
	public void setService(AllocationIntradayService service) {
		this.service = service;
	}
	
	public void setE_service(AllocationManagementService service) {
		this.e_service = service;
	}
	
	//geters/seters
	public AllocationIntradayFilter getFilters() {
		return filters;
	}

	public void setFilters(AllocationIntradayFilter filters) {
		this.filters = filters;
	}
	
	public List<AllocationIntradayBean> getItems() {
		return items;
	}

	public void setItems(List<AllocationIntradayBean> items) {
		this.items = items;
	}
	
	public List<AllocationIntradayBean> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(List<AllocationIntradayBean> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	public Date getResponsePeriodStartDate() {
		return responsePeriodStartDate;
	}

	public void setResponsePeriodStartDate(Date responsePeriodStartDate) {
		this.responsePeriodStartDate = responsePeriodStartDate;
	}

	/*public Date getResponsePeriodEndDate() {
		return responsePeriodEndDate;
	}

	public void setResponsePeriodEndDate(Date responsePeriodEndDate) {
		this.responsePeriodEndDate = responsePeriodEndDate;
	}*/

	// Este booleano marcara si esta habilitado el filtro de shipper (isOperator = true) o esta deshabilitado, 
	// con un shipper fijo.
	public boolean getIsShipper() {
		return getUser().isUser_type(Constants.SHIPPER);
	}
	
	@PostConstruct
    public void init() {
		factorFromDefaultUnit = e_service.selectFactorFromDefaultUnit(EnergyUnit);
		try{
    		allocationMaxDateOffset = service.selectAllocationMaxDateOffset(getUser().getUsername(), getLanguage().getLocale());
		}
		catch(Exception e){
			log.error(e.getMessage(), e);
		}
		setResponsePeriod(allocationMaxDateOffset);
		filters = new AllocationIntradayFilter();
		filters.setSystemId(getChangeSystemView().getIdn_active());
		filters.setIsShipper(getIsShipper());	
		Date d = DateUtil.getToday();
		filters.setDateFrom(d);
		filters.setDateTo(d);
		sysdate = gettingMaxDate();
		if (getIsShipper()) {
			filters.setShipperId(getUser().getIdn_user_group());
		}
		filters.setFactorFromDefaultUnit(factorFromDefaultUnit);
	}

	public Calendar gettingMaxDate(){
    	
    	sysdate = Calendar.getInstance(); 
    	//StartDate filter => sysdate
    	sysdate.set(Calendar.HOUR_OF_DAY, 0);
    	sysdate.set(Calendar.MINUTE, 0);
    	sysdate.set(Calendar.SECOND, 0);
    	sysdate.set(Calendar.MILLISECOND, 0);
    	
        return sysdate;
    }
	
	private void setResponsePeriod(Integer _allocationMaxDateOffset){
		// Primer dia de balance abierto.

		/*HashMap<String, Object> params = new HashMap<>();
		params.put("closingTypeCode", "DEFINITIVE");
		params.put("idnSystem", getChangeSystemView().getIdn_active());
		params.put("sysCode", getChangeSystemView().getSystem());
		openPeriodFirstDay = service.selectOpenPeriodFirstDay(params);*/

		Calendar tmpEndDate = Calendar.getInstance();
		tmpEndDate.set(Calendar.HOUR_OF_DAY, 0);
		tmpEndDate.set(Calendar.MINUTE, 0);
		tmpEndDate.set(Calendar.SECOND, 0);
		tmpEndDate.set(Calendar.MILLISECOND, 0);
		tmpEndDate.add(Calendar.DAY_OF_MONTH, _allocationMaxDateOffset * (-1));
		
		responsePeriodStartDate=tmpEndDate.getTime();
		
		/*responsePeriodStartDate = openPeriodFirstDay;
		responsePeriodEndDate = tmpEndDate.getTime();*/
	}
	
	// Para los elementos del combo del filtro de shippers.
	public Map<BigDecimal, Object> getShipperIds() {
		return service.selectShipperId();
	}	
	
	// Para los elementos del combo del filtro de contratos.
	public Map<BigDecimal, Object> getContractIds() {
		return service.selectContractId(filters);
	}
	
	// Para los elementos del combo del filtro de puntos.
	public Map<BigDecimal, Object> getPointIds() {
		
		Map<BigDecimal, Object> tmp = service.selectPointId(filters.getSystemId());
		return tmp;
	}
	
	private boolean validate() {
		boolean result = true;
		if (filters.getDateFrom() == null) {
			addMessage("validation_error", "alloc_report_val_df", Constants.ERROR);
			result = false;
			return result;
		}
		if (filters.getDateTo() == null) {
			addMessage("validation_error", "alloc_report_val_dt", Constants.ERROR);
			result = false;
			return result;
		}
		if (filters.getDateTo().before(filters.getDateFrom())) {

			addMessage("validation_error", "alloc_report_val_dates", Constants.ERROR);
			result = false;
			return result;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(filters.getDateFrom());
		cal.add(Calendar.DAY_OF_YEAR, 30);
		if (filters.getDateTo().after(cal.getTime())) {

			addMessage("validation_error", "alloc_report_val_datediff", Constants.ERROR);
			result = false;
			return result;
		}
		return result;
	}
	
	public void postProcessXLS(Object document) {
		XSSFWorkbook wb = (XSSFWorkbook) document;
		XSSFSheet sheet = wb.getSheetAt(0);
		wb.setSheetName(0, "OverUsage Main");
		XSSFRow header = sheet.getRow(0);
		NumberFormat nf = NumberFormat.getInstance(new Locale(getLanguage().getLocale()));

		// Generate fonts
		XSSFFont headerFont = createFont(wb, Color.BLACK, (short) 12, true);
		XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
		// Generate styles
		DataFormat format = wb.createDataFormat();

		XSSFCellStyle cellStyleHeader = createStyle(wb, headerFont, XSSFCellStyle.ALIGN_CENTER, Color.cyan.darker(),
				true, Color.BLACK, XSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		XSSFCellStyle cellStyleHeaderGas = createStyle(wb, headerFont, XSSFCellStyle.ALIGN_CENTER, Color.lightGray,
				true, Color.BLACK, XSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		XSSFCellStyle cellStyleText = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##"));
		XSSFCellStyle cellStyleFourDec = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.0000"));
		XSSFCellStyle cellStyleThreeDec = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.000"));
		XSSFCellStyle cellStyleTwoDec = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.00"));

		String tmpStrValue = null;
		int i = -1; // Filas
		int j = -1; // Columnas
		ArrayList<String> cabeceras = new ArrayList<>();

		try {
			// Cabecera i=0
			for (j = 0; j < header.getPhysicalNumberOfCells(); j++) {
				XSSFCell cell = header.getCell(j);
				cell.setCellStyle(cellStyleHeader);
				String val = cell.getStringCellValue();
				if (!val.isEmpty()) {
					cabeceras.add(val);
				}
			}
			cabeceras.add(5, "Nomination Point ID");
			cabeceras.remove(cabeceras.size()-1);

			for (i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

				XSSFRow row = sheet.getRow(i);

				for (j = 0; j < row.getPhysicalNumberOfCells(); j++) {
					XSSFCell cell = row.getCell(j);

					// Se borran los valores de la primera columna.
					if (j == 0) {
						cell.setCellValue("");
					}

					if (j < 7 ) { // Estas
																					// columnas
																					// deben
																					// ser
																					// de
																					// texto
																					// y
																					// el
																					// resto
																					// numericas.
						cell.setCellStyle(cellStyleText);
					} else {

						//cell.setCellStyle(cellStyleTwoDec);
						cell.setCellStyle(cellStyleFourDec);

						if (cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {

							tmpStrValue = cell.getStringCellValue();
							if (tmpStrValue != null && (!"".equalsIgnoreCase(tmpStrValue))) {
								cell.setCellType(Cell.CELL_TYPE_NUMERIC);

								try {
									cell.setCellValue(nf.parse(tmpStrValue).doubleValue());
								} catch (Exception e) {
									log.error(e.getMessage(), e);
								}

								// Se resetea la variable temporal.
								tmpStrValue = null;
							}
						}
					}

					// Se hace abajo, porque consume tiempo.
					// sheet.autoSizeColumn(j, true);
				}
			}

			// Se supone que todas las filas tienen el mismo numero de columnas.
			// Se actualiza el final el tamaño de las columnas porque consumiria
			// mucho tiempo si se hiciera fila por fila.
			for (j = 0; j < sheet.getRow(0).getPhysicalNumberOfCells(); j++) {
				sheet.autoSizeColumn(j, true);
			}

			XSSFSheet sheet1 = wb.createSheet("Overusage Detail");
			// CABECERA
			XSSFRow rowHead = sheet1.createRow(0);
			int[] idx = { 0, 0 };
			cabeceras.add("Res. Bal. Gas (MMBTU/D)");
			cabeceras.stream().forEachOrdered(item -> {

				Cell cab = rowHead.createCell(idx[0]++);
				cab.setCellStyle(cellStyleHeader);
				cab.setCellValue(item);

			});
			idx[0] = 0;
			idx[1] = 1;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			detailItems.stream().forEachOrdered(det -> {
				XSSFRow row = sheet1.createRow(idx[1]++);
				Cell c = row.createCell(idx[0]++);
				c.setCellValue(sdf.format(det.getGasDay()));
				c.setCellStyle(cellStyleText);
				c = row.createCell(idx[0]++);
				c.setCellValue(det.getShipperCode());
				c.setCellStyle(cellStyleText);
				c = row.createCell(idx[0]++);
				c.setCellValue(det.getShipperName());
				c.setCellStyle(cellStyleText);
				c = row.createCell(idx[0]++);
				c.setCellValue(det.getContractCode());
				c.setCellStyle(cellStyleText);
				c = row.createCell(idx[0]++);
				c.setCellValue(det.getPointCode());
				c.setCellStyle(cellStyleText);
				c = row.createCell(idx[0]++);
				c.setCellValue(det.getNominationPoint());
				c.setCellStyle(cellStyleText);

				c = row.createCell(idx[0]++);
				c.setCellValue(det.getPointTypeCode());
				c.setCellStyle(cellStyleText);

				c = row.createCell(idx[0]++);
				if(det.getContractedValue() != null){
					c.setCellValue(det.getContractedValue().doubleValue()); 
//					c.setCellStyle(cellStyleTwoDec);
					c.setCellStyle(cellStyleFourDec);
				}
				else{
					c.setCellType(Cell.CELL_TYPE_BLANK);
					c.setCellStyle(cellStyleText);
				}

				c = row.createCell(idx[0]++);
				if(det.getNominatedValue() != null){
					c.setCellValue(det.getNominatedValue().doubleValue()); 
//					c.setCellStyle(cellStyleTwoDec);
					c.setCellStyle(cellStyleFourDec);
				}
				else{
					c.setCellType(Cell.CELL_TYPE_BLANK);
					c.setCellStyle(cellStyleText);
				}

				c = row.createCell(idx[0]++);
				if(det.getAllocatedValue() != null){
					c.setCellValue(det.getAllocatedValue().doubleValue()); 
//					c.setCellStyle(cellStyleTwoDec);
					c.setCellStyle(cellStyleFourDec);
				}
				else{
					c.setCellType(Cell.CELL_TYPE_BLANK);
					c.setCellStyle(cellStyleText);
				}
				
				
				c = row.createCell(idx[0]++);
				if(det.getBalancingGas() != null){
					c.setCellValue(det.getBalancingGas().doubleValue()); 
//					c.setCellStyle(cellStyleTwoDec);
					c.setCellStyle(cellStyleFourDec);
				}
				else{
					c.setCellType(Cell.CELL_TYPE_BLANK);
					c.setCellStyle(cellStyleText);
				}
				idx[0]=0;

			});
			for (j = 0; j < sheet1.getRow(0).getPhysicalNumberOfCells(); j++) {
				sheet1.autoSizeColumn(j, true);
			}

		} catch (Exception e) {

			ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
					.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
			getMessages().addMessage(Constants.head_menu[8], new MessageBean(Constants.ERROR,
					msgs.getString("internal_error"), "error exportig data", Calendar.getInstance().getTime()));
			log.error("error exporting data: " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private XSSFCellStyle createStyle(XSSFWorkbook wb, XSSFFont font, short cellAlign, Color cellColor,
			boolean cellBorder, Color cellBorderColor, short border, boolean numberFormat, short dataFormat) {

		XSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(cellAlign);
		style.setFillForegroundColor(new XSSFColor(cellColor));
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

		if (cellBorder) {
			style.setBorderTop(border);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setBorderBottom(border);

			XSSFColor xsCellBorderColor = new XSSFColor(cellBorderColor);
			style.setTopBorderColor(xsCellBorderColor);
			style.setLeftBorderColor(xsCellBorderColor);
			style.setRightBorderColor(xsCellBorderColor);
			style.setBottomBorderColor(xsCellBorderColor);
		}

		if (numberFormat) {
			style.setDataFormat(dataFormat);
		}

		return style;
	}
	private XSSFFont createFont(XSSFWorkbook wb, Color fontColor, short fontHeight, boolean fontBold) {

		XSSFFont font = wb.createFont();
		font.setBold(fontBold);
		font.setColor(new XSSFColor(fontColor));
		font.setFontName("Calibri");
		font.setFontHeightInPoints(fontHeight);

		return font;
	}
	
	public void onSearch(){
		if (validate() && validateShipperActions(filters.getShipperId())) {
			items = service.search(filters);
			detailItems = service.searchDetail(filters);
			detailMap = service.getDetailMap(detailItems);
		}
    }

	public void onClear(){
		init();
		if (items != null) {
            items.clear();
        }
		/*filters = initFilter(allocationMaxDateOffset, factorFromDefaultUnit);
	  	
        if (items != null) {
            items.clear();
        }
        items = service.search(filters);
        // En cada busqueda se resetea las filas seleccionada.
        selectedItems =  new ArrayList<AllocationBean>(); */
    }
	
	

	public void onExecute(){
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		String summaryMsg = getChangeSystemView().getSystem() + ": "
				+ msgs.getString("all_man_execution_dialog_header");

    	String errorMsg = null;
    	
		try {
			service.allocationAndBalance(responsePeriodStartDate, responsePeriodStartDate, getUser(), getLanguage(),
					getChangeSystemView().getIdn_active());
			
	    	String okMsg = msgs.getString("met_man_processing");
	    	getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
	    	log.info(okMsg);
		} 
		catch(ValidationException ve){
			errorMsg = ve.getMessage();
			getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));    		
		}
		catch (Exception e) {
			errorMsg = msgs.getString("internal_error");
			getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			// Se guarda el detalle del error tecnico.
	    	log.error(e.getMessage(), e);			
		}
    	
		// Si se ejectua el proceso en background, no tiene sentido actualizar los datos.
        // onSearch();
	}
	
	public List<AllocationIntradayDetailBean> getDetails(AllocationIntradayBean dto) {
		return detailMap.get(dto.getIdnAllocation() + "-" + dto.getIdnContract() + "-" + dto.getIdnContractPoint());
	}

	public List<AllocationIntradayDetailBean> searchDetail(AllocationIntradayFilter filters) {
		return service.searchDetail(filters);
	}
	
    
    public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
}
