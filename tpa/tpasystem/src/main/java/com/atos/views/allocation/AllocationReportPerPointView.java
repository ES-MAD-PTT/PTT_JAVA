package com.atos.views.allocation;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.atos.beans.MessageBean;
import com.atos.beans.allocation.AllocationReportPerPointBean;
import com.atos.filters.allocation.AllocationReportPerPointFilter;
import com.atos.services.allocation.AllocationReportPerPointService;
import com.atos.utils.Constants;
import com.atos.utils.DateUtil;
import com.atos.views.CommonView;

@ManagedBean(name = "AllocationReportPerPointView")
@ViewScoped
public class AllocationReportPerPointView extends CommonView implements Serializable {


	private static final long serialVersionUID = -8682819137997451254L;
	
	private static final String excelTotalLabel ="Total";
	
	private static final Logger log = LogManager.getLogger("com.atos.views.allocation.AllocationReportPerPointView");
	private AllocationReportPerPointFilter filters;
	private List<AllocationReportPerPointBean> resultList;

	@ManagedProperty("#{allocationReportPerPointService}")
	transient private AllocationReportPerPointService service;

	public List<AllocationReportPerPointBean> getResultList() {
		return resultList;
	}

	public void setResultList(List<AllocationReportPerPointBean> resultList) {
		this.resultList = resultList;
	}

	@PostConstruct
	public void init() {
		super.setHead(8);
		filters = new AllocationReportPerPointFilter();
		Date d = DateUtil.getToday();
		filters.setIsShipper(getIsShipper());		
		if (getIsShipper()) {
			filters.setShipperId(getUser().getIdn_user_group());
		}
		filters.setDateFrom(d);
		filters.setDateTo(d);		
		filters.setSystemId(getChangeSystemView().getIdn_active());
		
		Map<BigDecimal, Object> pointsCombo = service.selectPointId(filters);
    	filters.setNomPointId(pointsCombo.keySet().toArray(new BigDecimal[pointsCombo.keySet().size()]));
		
		//onSearch();
	}

	public boolean getIsShipper() {
		return getUser().isUser_type(Constants.SHIPPER);
	}
	
	private boolean validate() {
		boolean result = true;
		if (filters.getDateFrom() == null) {
			addMessage("validation_error", "alloc_report_per_point_val_df", Constants.ERROR);
			result = false;
			return result;
		}
		if (filters.getDateTo() == null) {
			addMessage("validation_error", "alloc_report_per_point_val_dt", Constants.ERROR);
			result = false;
			return result;
		}
		if (filters.getDateTo().before(filters.getDateFrom())) {

			addMessage("validation_error", "alloc_report_per_point_val_dates", Constants.ERROR);
			result = false;
			return result;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(filters.getDateFrom());
		cal.add(Calendar.DAY_OF_YEAR, 30);
		if (filters.getDateTo().after(cal.getTime())) {

			addMessage("validation_error", "alloc_report_per_point_val_datediff", Constants.ERROR);
			result = false;
			return result;
		}
		return result;
	}
	
	public void postProcessXLS(Object document) {

		XSSFWorkbook wb = (XSSFWorkbook) document;
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow header = sheet.getRow(0);
		NumberFormat nf = NumberFormat.getInstance(new Locale(getLanguage().getLocale()));

		// Generate fonts
		XSSFFont headerFont = createFont(wb, Color.BLACK, (short) 12, true);
		XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
		// Generate styles
		DataFormat format = wb.createDataFormat();
		
		byte[] rgb = new byte[3];
        rgb[0] = (byte) 153; // red
        rgb[1] = (byte) 230; // green
        rgb[2] = (byte) 230; // blue
        XSSFColor totalColor = new XSSFColor(rgb); // #f2dcdb
        Color tColor = new Color(153,230,230);
        
		XSSFCellStyle cellStyleTotalRow = wb.createCellStyle();
        cellStyleTotalRow.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setFillForegroundColor(totalColor);
        cellStyleTotalRow.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

		XSSFCellStyle cellStyleHeader = createStyle(wb, headerFont, XSSFCellStyle.ALIGN_CENTER, Color.cyan.darker(),
				true, Color.BLACK, XSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		XSSFCellStyle cellStyleText = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##"));
		XSSFCellStyle cellStyleFourDec = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.0000"));
		XSSFCellStyle cellStyleFourDecTotal = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT, tColor, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.0000"));
		XSSFCellStyle cellStyleTwoDec = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.00"));
		
		ArrayList<Integer> allTotalRows = new ArrayList<Integer>();
		
		for(int i=0;i<sheet.getPhysicalNumberOfRows();i++){
        	XSSFCell cell = sheet.getRow(i).getCell(1);

   			String tmpStrValue = cell.getStringCellValue();
   			if(tmpStrValue.startsWith(excelTotalLabel))
   				allTotalRows.add(new Integer(i));
        }

		String tmpStrValue = null;
		int i = -1; // Filas
		int j = -1; // Columnas

		try {
			// Cabecera i=0
			for (j = 0; j < header.getPhysicalNumberOfCells(); j++) {
				XSSFCell cell = header.getCell(j);
				cell.setCellStyle(cellStyleHeader);
			}

			for (i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

				XSSFRow row = sheet.getRow(i);

				for (j = 0; j < row.getPhysicalNumberOfCells(); j++) {
					XSSFCell cell = row.getCell(j);

					// Se borran los valores de la primera columna.

					if (j == 0 || j == 1 || j == 2 || j == 3 || j == 4 || j == 5) { // Estas
																					// columnas
																					// deben
																					// ser
																					// de
																					// texto
																					// y
																					// el
																					// resto
																					// numericas.
						if(allTotalRows.contains(i))
		        			cell.setCellStyle(cellStyleTotalRow);
		        		else
		        			cell.setCellStyle(cellStyleText);						
					} else {

						if(allTotalRows.contains(i))
							cell.setCellStyle(cellStyleFourDecTotal);
						else
							cell.setCellStyle(cellStyleFourDec);
						
						if (cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {

							tmpStrValue = cell.getStringCellValue();
							if (tmpStrValue != null && (!"".equalsIgnoreCase(tmpStrValue))) {
								cell.setCellType(Cell.CELL_TYPE_NUMERIC);

								try {
									cell.setCellValue(nf.parse(tmpStrValue).doubleValue());
								} catch (Exception e) {
									log.catching(e);
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
		} catch (Exception e) {

			ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
					.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
			getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.ERROR, msgs.getString("internal_error"),
							"error exportig data", Calendar.getInstance().getTime()));
			log.error("error exporting data: " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private XSSFFont createFont(XSSFWorkbook wb, Color fontColor, short fontHeight, boolean fontBold) {

		XSSFFont font = wb.createFont();
		font.setBold(fontBold);
		font.setColor(new XSSFColor(fontColor));
		font.setFontName("Calibri");
		font.setFontHeightInPoints(fontHeight);

		return font;
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

	public void onSearch() {

		//if (validate() && validateShipperActions(filters.getShipperId())) {
		if (validate()) {	
			resultList = service.search(filters);
		}
		//}

	}

	public void onClear() {
		init();
		
        if (resultList != null) {
        	resultList.clear();
        }
	}

	public Map<BigDecimal, Object> getShipperIds() {
		return service.selectShipperId();
	}

	// Para los elementos del combo del filtro de contratos.
	/*public Map<BigDecimal, Object> getContractIds() {
		return service.selectContractId(filters);
	}*/

	public Map<BigDecimal, Object> getPointIds() {

		return service.selectPointId(filters);		
	}

	public AllocationReportPerPointFilter getFilters() {
		return filters;
	}

	public void setFilters(AllocationReportPerPointFilter filters) {
		this.filters = filters;
	}

	public AllocationReportPerPointService getService() {
		return service;
	}

	public void setService(AllocationReportPerPointService service) {
		this.service = service;
	}

    public int getItemsSize() { 
		if(this.resultList!=null && !this.resultList.isEmpty()){
			return this.resultList.size();
		}else{
			return 0;
		}
	}
}
