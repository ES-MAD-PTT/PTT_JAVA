package com.atos.views.nominations;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.atos.beans.MessageBean;
import com.atos.beans.nominations.ShippersNominationReportsDailyBean;
import com.atos.beans.nominations.ShippersNominationsReportsBean;
import com.atos.filters.nominations.ShippersNominationsReportsFilter;
import com.atos.services.nominations.ShippersNominationsReportsService;
import com.atos.utils.Constants;
import com.atos.utils.POIXSSFExcelUtils;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;

@ManagedBean(name="shippersNominationsReportsView")
@ViewScoped
public class ShippersNominationsReportsView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1804211253281868923L;
	private static final Logger log = LogManager.getLogger(ShippersNominationsReportsView.class);

	private ShippersNominationsReportsFilter filters;
	private List<ShippersNominationsReportsBean> items;
	private ShippersNominationsReportsBean selected;
	
	// Detail tabs
	private List<ShippersNominationReportsDailyBean> east_tab, west_tab, mix_tab;

	private boolean showHideDaysEast1 = true; 
	private String widthEast1 = "4300px"; 
	private boolean showHideDaysEast2 = true; 
	private String widthEast2 = "2500px"; 
	private boolean showHideDaysWest1 = true; 
	private String widthWest1 = "4300px"; 
	private boolean showHideDaysWest2 = true; 
	private String widthWest2 = "2850px"; 
	private boolean showHideDaysEastWest1 = true; 
	private String widthEastWest1 = "4300px"; 
	private boolean showHideDaysEastWest2 = true; 
	private String widthEastWest2 = "2850px"; 

	// Detail tabs park/unpark
	private List<ShippersNominationReportsDailyBean> east_tab_park, west_tab_park, mix_tab_park;
	

	@ManagedProperty("#{shippersNominationsReportsService}")
    transient private ShippersNominationsReportsService service;
    
    public void setService(ShippersNominationsReportsService service) {
        this.service = service;
    }

	@ManagedProperty("#{messagesView}")
    private MessagesView messages;

    public void setMessages(MessagesView messages) {
        this.messages = messages;
    }

	public ShippersNominationsReportsFilter getFilters() {
		return filters;
	}

	public void setFilters(ShippersNominationsReportsFilter filters) {
		this.filters = filters;
	}

	public List<ShippersNominationsReportsBean> getItems() {
		return items;
	}

	public void setItems(List<ShippersNominationsReportsBean> items) {
		this.items = items;
	}

	public ShippersNominationsReportsBean getSelected() {
		return selected;
	}

	public void setSelected(ShippersNominationsReportsBean selected) {
		this.selected = selected;
	}

	public List<ShippersNominationReportsDailyBean> getEast_tab() {
		return east_tab;
	}

	public List<ShippersNominationReportsDailyBean> getWest_tab() {
		return west_tab;
	}

	public List<ShippersNominationReportsDailyBean> getMix_tab() {
		return mix_tab;
	}

	public List<ShippersNominationReportsDailyBean> getEast_tab_park() {
		return east_tab_park;
	}

	public List<ShippersNominationReportsDailyBean> getWest_tab_park() {
		return west_tab_park;
	}

	public List<ShippersNominationReportsDailyBean> getMix_tab_park() {
		return mix_tab_park;
	}


	@PostConstruct
    public void init() {
    	filters = new ShippersNominationsReportsFilter();
    	filters.setIdn_system(getChangeSystemView().getIdn_active());
    	filters.setIsShipper(getIsShipper());		
		if (getIsShipper()) {
			filters.setShipper_id(getUser().getIdn_user_group());
		}
    }
	
	public void onSearch(){
		items = service.selectShippersNomReports(filters);
	}
	
	public void onClear(){
    	init();
        if (items != null) {
            items.clear();
        }
    }
	
	public Map<BigDecimal, Object> getIdsShipper() {
		return service.selectShippersIds();
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
		XSSFFont contentFontRed = createFont(wb, Color.RED, (short) 10, false);
		// Generate styles
		DataFormat format = wb.createDataFormat();

		XSSFCellStyle cellStyleHeader = createStyle(wb, headerFont, XSSFCellStyle.ALIGN_CENTER, Color.cyan.darker(),
				true, Color.BLACK, XSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		XSSFCellStyle cellStyleText = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##"));
		XSSFCellStyle cellStyleThreeDec = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.000"));
		XSSFCellStyle cellStyleThreeDecRed = createStyle(wb, contentFontRed, XSSFCellStyle.ALIGN_RIGHT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.000"));
		
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

					if (j == 0 || j == 1 || j == 2 ) { // Estas
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
						
						if(j==6 && items.get(i-1).getIs_warning().equals("Y")) {
							cell.setCellStyle(cellStyleThreeDecRed);
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
	
	public boolean getIsShipper() {
		return getUser().isUser_type(Constants.SHIPPER);
	}
	
/*	public String is_warning_difference(ShippersNominationsReportsBean bean) {
		if (bean.getDifference().compareTo(BigDecimal.ZERO) < 0)
			return "Y";
		return "N";
	}*/

	public void onRowSelect(ShippersNominationsReportsBean bean) {
		this.setSelected(bean);
		
		Map<String, BigDecimal> map = service.selectZonesNomination();
		
		bean.setIdn_zone(map.get(Constants.EAST));
		east_tab  = service.selectShipperNomReportsDailyDetail(bean);
		east_tab_park = service.selectShipperNomReportsParkUnpark(bean);

		bean.setIdn_zone(map.get(Constants.WEST));
		west_tab  = service.selectShipperNomReportsDailyDetail(bean);
		west_tab_park = service.selectShipperNomReportsParkUnpark(bean);

		
		bean.setIdn_zone(map.get(Constants.MIX));
		mix_tab  = service.selectShipperNomReportsDailyDetail(bean);
		mix_tab_park = service.selectShipperNomReportsParkUnpark(bean);
		
    }
	
	 public void daysDetailEast1() {
		 if(this.showHideDaysEast1==false) {
			 this.showHideDaysEast1=true;
		 } else {
			 this.showHideDaysEast1= false;
		 }
	 }
	 public boolean getShowHideDaysEast1() {
		 if(this.showHideDaysEast1 == true) {
			 this.widthEast1 = "4300px";
			 return true;
		 } else {
			 this.widthEast1 = "1900px";
			 return false;
		 }
	 }
	public String getWidthEast1() {
		if(this.showHideDaysEast1 == true) {
			this.widthEast1 = "4300px";
		} else {
			this.widthEast1 = "1900px";
		}
		return widthEast1; 
	}

	public void daysDetailEast2() {
		 if(this.showHideDaysEast2==false) {
			 this.showHideDaysEast2=true;
		 } else {
			 this.showHideDaysEast2= false;
		 }
	 }
	 public boolean getShowHideDaysEast2() {
		 if(this.showHideDaysEast2 == true) {
			 this.widthEast2 = "2850px";
			 return true;
		 } else {
			 this.widthEast2 = "450px";
			 return false;
		 }
	 }
	 public String getWidthEast2() {
		 if(this.showHideDaysEast2 == true) {
			 this.widthEast2 = "2850px";
		 } else {
			 this.widthEast2 = "450px";
		 }
		 return widthEast2; 
	 }

	public void daysDetailWest1() {
		 if(this.showHideDaysWest1==false) {
			 this.showHideDaysWest1=true;
		 } else {
			 this.showHideDaysWest1= false;
		 }
	 }
	 public boolean getShowHideDaysWest1() {
		 if(this.showHideDaysWest1 == true) {
			 this.widthWest1 = "4300px";
			 return true;
		 } else {
			 this.widthWest1 = "1900px";
			 return false;
		 }
	 }

	 public String getWidthWest1() {
		 if(this.showHideDaysWest1 == true) {
			 this.widthWest1 = "4300px";
		 } else {
			 this.widthWest1 = "1900px";
		 }
		 return widthWest1; 
	 }
	 
	 public void daysDetailWest2() {
		 if(this.showHideDaysWest2==false) {
			 this.showHideDaysWest2=true;
		 } else {
			 this.showHideDaysWest2= false;
		 }
	 }
	 public boolean getShowHideDaysWest2() {
		 if(this.showHideDaysWest2 == true) {
			 this.widthWest2 = "2850px";
			 return true;
		 } else {
			 this.widthWest2 = "450px";
			 return false;
		 }
	 }

	 public String getWidthWest2() {
		 if(this.showHideDaysWest2 == true) {
			 this.widthWest2 = "2850px";
		 } else {
			 this.widthWest2 = "450px";
		 }
		 return widthWest2; 
	 }
	 
	 public void daysDetailEastWest1() {
		 if(this.showHideDaysEastWest1==false) {
			 this.showHideDaysEastWest1=true;
		 } else {
			 this.showHideDaysEastWest1= false;
		 }
	 }
	 public boolean getShowHideDaysEastWest1() {
		 if(this.showHideDaysEastWest1 == true) {
			 this.widthEastWest1 = "4300px";
			 return true;
		 } else {
			 this.widthEastWest1 = "1900px";
			 return false;
		 }
	 }

	 public String getWidthEastWest1() {
		 if(this.showHideDaysEastWest1 == true) {
			 this.widthEastWest1 = "4300px";
		 } else {
			 this.widthEastWest1 = "1900px";
		 }
		 return widthEastWest1; 
	 }
	 
	 public void daysDetailEastWest2() {
		 if(this.showHideDaysEastWest2==false) {
			 this.showHideDaysEastWest2=true;
		 } else {
			 this.showHideDaysEastWest2= false;
		 }
	 }
	 public boolean getShowHideDaysEastWest2() {
		 if(this.showHideDaysEastWest2 == true) {
			 this.widthEastWest2 = "2850px";
			 return true;
		 } else {
			 this.widthEastWest2 = "450px";
			 return false;
		 }
	 }

	 public String getWidthEastWest2() {
		 if(this.showHideDaysEastWest2 == true) {
			 this.widthEastWest2 = "2850px";
		 } else {
			 this.widthEastWest2 = "450px";
		 }
		 return widthEastWest2; 
	 }
	 
	//**************************************Excel*************************************************************************
		
			public void downloadFile() {
				File file = loadRequest();
				POIXSSFExcelUtils.downloadFile(file);
			}
			
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
							true, IndexedColors.GREY_25_PERCENT.index, CellStyle.BORDER_THIN, true, workBook.getCreationHelper().createDataFormat().getFormat("dd-MMM-yyyy"));
					CellStyle cellStyleTableGreyCenter = POIXSSFExcelUtils.createStyle(workBook, contentFontBold, CellStyle.ALIGN_CENTER, IndexedColors.GREY_25_PERCENT.index, 
							true, IndexedColors.GREY_25_PERCENT.index, CellStyle.BORDER_THIN, true, workBook.getCreationHelper().createDataFormat().getFormat("dd-MMM-yyyy"));
					CellStyle cellStyleTableFormatNumber = POIXSSFExcelUtils.createStyle(workBook, contentFont, CellStyle.ALIGN_RIGHT, IndexedColors.WHITE.index, 
							true, IndexedColors.GREY_25_PERCENT.index, CellStyle.BORDER_THIN, true, workBook.getCreationHelper().createDataFormat().getFormat("#,##0.00"));
					CellStyle cellStyleTableFormatDate = POIXSSFExcelUtils.createStyle(workBook, contentFont, CellStyle.ALIGN_LEFT, IndexedColors.WHITE.index, 
							true, IndexedColors.GREY_25_PERCENT.index, CellStyle.BORDER_THIN, true, workBook.getCreationHelper().createDataFormat().getFormat("dd-MM-yyyy"));
					CellStyle cellNormalStyle = POIXSSFExcelUtils.createStyle(workBook, contentFont, CellStyle.ALIGN_LEFT, IndexedColors.WHITE.index, 
							true, IndexedColors.GREY_25_PERCENT.index, CellStyle.BORDER_THIN, true, workBook.getCreationHelper().createDataFormat().getFormat("#,##0.00"));
					
					List<String> headerTable1 = Arrays.asList(msgs.getString("shipperNomReport_gasDay"), msgs.getString("shipperNomReport_shipper"), 
							msgs.getString("shipperNomReport_shipperName"),msgs.getString("shipperNomReport_contracted"),msgs.getString("shipperNomReport_energy"),
							msgs.getString("shipperNomReport_overusage"), msgs.getString("shipperNomReport_imbalance"));
					List<String> headerTable2 = Arrays.asList(msgs.getString("shipperNomReport_gasDay"), msgs.getString("shipperNomReport_shipper"), 
							msgs.getString("shipperNomReport_shipperName"), msgs.getString("shipperNomReport_area"), 
							msgs.getString("shipperNomReport_contracted"), msgs.getString("shipperNomReport_energy"), msgs.getString("shipperNomReport_overusage"));
					
					POIXSSFExcelUtils.createSimpleHeaderTable(sheet, row, cell, rowNum, 0, headerTable1, cellStyleTableBlueCenter);
					rowNum ++;
					if(items != null && !items.isEmpty()) {
						for(int i = 0; i < items.size(); i++) {
							row = sheet.createRow(rowNum);
							Object[] properties1 = {
									items.get(i).getGas_day(),items.get(i).getUser_group_id(), items.get(i).getUser_group_name(), items.get(i).getContracted_energy(), 
									items.get(i).getNominated_energy(), items.get(i).getOverusage(), items.get(i).getImbalance()
						    };
						
							POIXSSFExcelUtils.createCellsTable(row, cell, 0, cellStyleTableFormatDate, cellStyleTableFormatNumber, cellNormalStyle, properties1);
							rowNum++;
							//Creamos la cabecera de los detalles con sus detalles
							POIXSSFExcelUtils.createSimpleHeaderTable(sheet, row, cell, rowNum, 1, headerTable2, cellStyleTableGreyCenter);
							rowNum++;
							
							for(int m = 0; m < items.get(i).getDetails().size(); m++) {
								row = sheet.createRow(rowNum);
								Object[] properties2 = {
										items.get(i).getDetails().get(m).getGas_day(), items.get(i).getDetails().get(m).getUser_group_id(), 
										items.get(i).getDetails().get(m).getUser_group_name(), items.get(i).getDetails().get(m).getArea_code(), items.get(i).getDetails().get(m).getContracted_energy(),
										items.get(i).getDetails().get(m).getNominated_energy(), items.get(i).getDetails().get(m).getOverusage()
								    };
								POIXSSFExcelUtils.createCellsTable(row, cell, 1, cellStyleTableFormatDate, cellStyleTableFormatNumber, cellNormalStyle, properties2);
								rowNum++;
								}
						}	
					}

					FileOutputStream outFile = new FileOutputStream("NominationsShipperReportDaily.xlsx");
					workBook.write(outFile);
					outFile.close();
					file = new File("NominationsShipperReportDaily.xlsx");

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return file;
			}
	
}
