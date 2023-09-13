package com.atos.views.nominations;

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
import com.atos.beans.SystemParameterBean;
import com.atos.beans.nominations.IntradayNomSummaryBean;
import com.atos.filters.nominations.IntradayNomSummaryFilter;
import com.atos.services.nominations.IntradayNomSummaryService;
import com.atos.utils.Constants;
import com.atos.utils.DateUtil;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;


@ManagedBean(name="intradayNomSummaryView")
@ViewScoped
public class IntradayNomSummaryView extends CommonView implements Serializable {

	
	private static final long serialVersionUID = 2379627102030784947L;
	private static final Logger log = LogManager.getLogger(IntradayNomSummaryView.class);
	
	private Calendar sysdate;
	
	// Filters
	private IntradayNomSummaryFilter filters;
	
	// Main
	private List<IntradayNomSummaryBean> items;
	
	@ManagedProperty("#{intradayNomSummaryService}")
    transient private IntradayNomSummaryService service;
     
    public void setService(IntradayNomSummaryService service) {
        this.service = service;
    }
    
    @ManagedProperty("#{messagesView}")
    private MessagesView messages;

    public void setMessages(MessagesView messages) {
        this.messages = messages;
    }
    
    public IntradayNomSummaryFilter getFilters() {
		return filters;
	}
    
	public void setFilters(IntradayNomSummaryFilter filters) {
		this.filters = filters;
	}

	public List<IntradayNomSummaryBean> getItems() {
		return items;
	}
	
	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	@PostConstruct
    public void init() {
		filters = new IntradayNomSummaryFilter();
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		filters.setIsShipper(getIsShipper());		
		if (getIsShipper()) {
			filters.setShipperId(getUser().getIdn_user_group());
		}
		Date d = DateUtil.getToday();
		filters.setGasDayFrom(d);
		filters.setGasDayTo(d);
		sysdate = gettingMaxDate();
		if(filters.isCheckIntraday())
			filters.setStrCheckIntraday("Y");
		else
			filters.setStrCheckIntraday("N");
    }
	
	private boolean validate() {
		boolean result = true;
		if (filters.getGasDayFrom() == null) {
			messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "Gas Day From should no be empty",
					"Error filtering data", Calendar.getInstance().getTime()));
			result = false;
			return result;
		}
		if (filters.getGasDayTo() == null) {
			messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "Gas Day To should no be empty",
					"Error filtering data", Calendar.getInstance().getTime()));
			result = false;
			return result;
		}
		return result;
	}
	
	// Methods
		public void onSearch(){
			if(validate())
				items = service.selectIntradayNomSummary(filters);
		}
		
		public void onClear(){
			init();
	    	
	        if (items != null) {
	            items.clear();
	        }
	    }
		
	    public Calendar gettingMaxDate(){
	    		    	
	    	sysdate = Calendar.getInstance(); 
	    	sysdate.set(Calendar.HOUR_OF_DAY, 0);
	    	sysdate.set(Calendar.MINUTE, 0);
	    	sysdate.set(Calendar.SECOND, 0);
	    	sysdate.set(Calendar.MILLISECOND, 0);
	    	
	        return sysdate;
	    }
		
		// Este booleano marcara si esta habilitado el filtro de shipper (isOperator = true) o esta deshabilitado, 
		// con un shipper fijo.
		public boolean getIsShipper() {
			return getUser().isUser_type(Constants.SHIPPER);
		}
		
		// Para los elementos del combo del filtro de shippers.
		public Map<BigDecimal, Object> getShipperIds() {
			return service.selectShipperId();
		}
		
		public Map<BigDecimal, Object> getContractIds() {
			return service.selectContractId(filters);
		}
		
		public Map<BigDecimal, Object> getSystemPointsIds() {
			return service.selectSystemPointId(filters.getContractId());
		}
		
		 public int getItemsSize() { 
			if(this.items!=null && !this.items.isEmpty()){
				return this.items.size();
			}else{
				return 0;
			}
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
				XSSFCellStyle cellStyleThreeDec = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT, Color.WHITE, true,
						Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.000"));
				
				ArrayList<Integer> allTotalRows = new ArrayList<Integer>();
				
				for(int i=0;i<sheet.getPhysicalNumberOfRows();i++){
		        	XSSFCell cell = sheet.getRow(i).getCell(1);
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

							if (j == 0 || j == 1 || j == 2 || j == 3 || j == 4 || j == 5 || j == 6) { // Estas
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
								cell.setCellStyle(cellStyleThreeDec);
								
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

			public void updateCheck() {
				if(filters.isCheckIntraday()) {
					filters.setStrCheckIntraday("Y");
				}
				else if(!filters.isCheckIntraday()) {
					filters.setStrCheckIntraday("N");
				}
			}
		
}

