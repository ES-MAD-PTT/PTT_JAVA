package com.atos.views.balancing;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
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

import com.atos.beans.MessageBean;
import com.atos.beans.balancing.ShipperDailyStatusBean;
import com.atos.beans.balancing.ShipperDailyStatusOffshoreBean;
import com.atos.filters.balancing.ShipperDailyStatusFilter;
import com.atos.services.balancing.ShipperDailyStatusService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="shipperDailyStatusView")
@ViewScoped
public class ShipperDailyStatusView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10642654929157783L;

	private static final String excelTotalLabel ="TOTAL:";
	
	private ShipperDailyStatusFilter filters;
	private List<ShipperDailyStatusBean> items;
	private List<ShipperDailyStatusOffshoreBean> itemsOffshore;
	private ShipperDailyStatusBean selected;
	private ShipperDailyStatusOffshoreBean selectedOffshore;
	
	private static final Logger log = LogManager.getLogger("com.atos.views.balancing.ShipperDailyStatusView");

	@ManagedProperty("#{ShipperDailyStatusService}")
    transient private ShipperDailyStatusService service;
    
	public void setService(ShipperDailyStatusService service) {
		this.service = service;
	}
	
	//geters/seters
	public ShipperDailyStatusFilter getFilters() {
		return filters;
	}

	public void setFilters(ShipperDailyStatusFilter filters) {
		this.filters = filters;
	}

	public List<ShipperDailyStatusBean> getItems() {
		return items;
	}

	public void setItems(List<ShipperDailyStatusBean> items) {
		this.items = items;
	}
	
	public List<ShipperDailyStatusOffshoreBean> getItemsOffshore() {
		return itemsOffshore;
	}

	public void setItemsOffshore(List<ShipperDailyStatusOffshoreBean> itemsOffshore) {
		this.itemsOffshore = itemsOffshore;
	}

	public ShipperDailyStatusBean getSelected() {
		return selected;
	}

	public void setSelected(ShipperDailyStatusBean selected) {
		this.selected = selected;
	}

	public ShipperDailyStatusOffshoreBean getSelectedOffshore() {
		return selectedOffshore;
	}

	public void setSelectedOffshore(ShipperDailyStatusOffshoreBean selectedOffshore) {
		this.selectedOffshore = selectedOffshore;
	}
	// Este booleano marcara si esta habilitado el filtro de shipper (isOperator = true) o esta deshabilitado, 
	// con un shipper fijo.
	public boolean getIsShipper() {
		return getUser().isUser_type(Constants.SHIPPER);
	}
	
	@PostConstruct
    public void init() {
	
		filters = initFilter();
	}
	
	private ShipperDailyStatusFilter initFilter(){
		
		ShipperDailyStatusFilter tmpFilter = new ShipperDailyStatusFilter();
		tmpFilter.setFromDate(Calendar.getInstance().getTime());
		
		// Si no es operador, se fija el filtro al shipper del usuario.
		if(getIsShipper())
			tmpFilter.setShipperId(getUser().getIdn_user_group());
		
		tmpFilter.setSystemId(getChangeSystemView().getIdn_active());
		
		return tmpFilter;
	}
	
	// Para los elementos del combo del filtro de shippers.
	public Map<BigDecimal, Object> getShipperIds() {
		return service.selectShipperId();
	}
	
	public void onSearch(){
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		if (filters.getFromDate() == null) {
			String strError = msgs.getString("shipp_daily_Gas_Day") + ": " + msgs.getString("empty_field");
			getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
			return;
		}
		
		if(getChangeSystemView().isOnshore()) {
			items = service.search(filters);
	        // En cada busqueda se resetea la fila seleccionada.
	        selected = new ShipperDailyStatusBean();
		} else {
			itemsOffshore = service.searchOffshore(filters);
	        selectedOffshore = new ShipperDailyStatusOffshoreBean();
		}
    }

	public void onClear(){
		filters = initFilter();
	
		if(getChangeSystemView().isOnshore()) {
	        if (items != null) {
	            items.clear();
	        }
		} else {
	        if (itemsOffshore != null) {
	        	itemsOffshore.clear();
	        }
		}
		
        onSearch();
        // Dentro de onSearch, en cada busqueda se resetea la fila seleccionada.
    }

	
/*	// Para crear la cabecera de la hoja excel.
	public void preProcessXLS(Object document) {
		XSSFWorkbook wbSource = null;	// Para la plantilla excel.
        XSSFWorkbook wb = (XSSFWorkbook) document;
        ByteArrayInputStream bais = null;

         Para cargar plantilla desde fichero local. Solo se usa en Desarrollo. 
    	String templateFilePath = "C://Pablo//proyectos//160425_PTT//03.Desarrollo//tmp//170308_balanceShipperReport/balanceReportTemplate.xlsx";
		InputStream is = null;

		try {
		    is = new FileInputStream(templateFilePath);
		    wbSource = new XSSFWorkbook(is);
		    
			if(wbSource!=null && wb!=null)
				copySheets(wbSource.getSheetAt(0), wb.getSheetAt(0));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.catching(e);
			return;
		} catch (IOException e) {
			log.catching(e);
			return;
		}
	    finally {
	        if (is != null) {
	            try {
	            	wbSource.close();
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		
		try {
			bais = service.getReportTemplate();
		    wbSource = new XSSFWorkbook(bais); 
	        
			if(wbSource!=null && wb!=null)
				service.copySheets(wbSource.getSheetAt(0), wb.getSheetAt(0));
			
		} catch (Exception e) {
			log.catching(e);
			return;
		}
	    finally {
            try {
            	if(bais != null)
            		bais.close();		// Con un ByteArrayInputStream no haria falta cerrar el stream.
            	if(wbSource != null)
            		wbSource.close();
            } catch (IOException e) {
    			log.catching(e);
            }
	    }
		
		// Para pintar la cabecera copiada, para depurar.        
		//FileOutputStream outPutStream = null;
		//String templateCopyFilePath = "C://Pablo//proyectos//160425_PTT//03.Desarrollo//tmp//170308_balanceShipperReport/balanceReportTemplateCopy.xlsx";        
		//try {
		//    outPutStream = new FileOutputStream(templateCopyFilePath);
		//    wb.write(outPutStream);
		//} catch (IOException e) {
		//    e.printStackTrace();
		//} finally {
		//    if (outPutStream != null) {
		//        try {
		//            outPutStream.flush();
		//            outPutStream.close();
		//        } catch (IOException e) {
		//            e.printStackTrace();
		//        }
		//    }
		//}
	}
	
	*/
	
	/*
	private HSSFWorkbook wb2;
	
	public void postProcessXLS(Object document) {
		
		SimpleDateFormat summSdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		
		wb2 = new HSSFWorkbook();
		wb2 = (HSSFWorkbook) document;
		HSSFSheet sheet = wb2.getSheetAt(0);
		HSSFRow header0 = sheet.getRow(0);
       
      
        // Para convertir las cantidades string en double.
        NumberFormat nf = NumberFormat.getInstance(new Locale(language.getLocale()));
        
        // La primera linea se machaca en el proceso.
        // Asi que en la plantilla esta vacia y luego se oculta.
      //  HSSFRow header0 = sheet.getRow(0);
      //  header0.getCTRow().setHidden(true);
        
        HSSFCellStyle cellStyleRow = wb.createCellStyle();
        cellStyleRow.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleRow.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleRow.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleRow.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        
        HSSFCellStyle cellStyleHeader = wb.createCellStyle();
        cellStyleRow.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
        cellStyleRow.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
        cellStyleRow.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
        cellStyleRow.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);

        byte[] rgb = new byte[3];
        rgb[0] = (byte) 153; // red
        rgb[1] = (byte) 230; // green
        rgb[2] = (byte) 230; // blue
        HSSFColor totalColor = new HSSFColor(); // #f2dcdb
        
        HSSFCellStyle cellStyleTotalRow = wb.createCellStyle();
        cellStyleTotalRow.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setFillForegroundColor((byte) 153);
        cellStyleTotalRow.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        
		DataFormat format = wb.createDataFormat();
		short twoDecimalFormat = format.getFormat("#,##0.00");
        short threeDecimalFormat = format.getFormat("#,##0.000");
        
       
    	// Este estilo se aplica al resto de cantidades (3 decimales).
        HSSFCellStyle cellStyleThreeDec = wb.createCellStyle();
        cellStyleThreeDec.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleThreeDec.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleThreeDec.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleThreeDec.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleThreeDec.setDataFormat(threeDecimalFormat);
		
        HSSFCellStyle cellStyleTotalThreeDec = wb.createCellStyle();
        cellStyleTotalThreeDec.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalThreeDec.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalThreeDec.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalThreeDec.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalThreeDec.setDataFormat(threeDecimalFormat);
        cellStyleTotalThreeDec.setFillForegroundColor((byte) 153);
        cellStyleTotalThreeDec.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        
		String tmpStrValue = null;
		// Almacena los numeros de filas que hay que marcar como Totales.
		ArrayList<Integer> alTotalRows = new ArrayList<Integer>();
		int i = -1;  	// Filas
		int j = -1;		// Columnas

		try{
			
			//HSSFCell cellH02 = header0.getCell(2);
		//	cellH02.setCellValue("Summary Pane");
			HSSFCell cellH00 = header0.getCell(0);
			cellH00.setCellStyle(cellStyleHeader);
			cellH00.setCellValue("Date");
			
			
			HSSFCell cellH01 = header0.getCell(1);
			cellH01.setCellStyle(cellStyleHeader);
			cellH01.setCellValue("Shipper");
			
			
			HSSFCell cellH02 = header0.getCell(2);
			cellH02.setCellStyle(cellStyleHeader);
			cellH02.setCellValue("Contract");
			
			
			for(int x=3;x<sheet.getRow(1).getPhysicalNumberOfCells();x=x+3){
				HSSFCell cellH03 = header0.getCell(x);
				cellH03.setCellStyle(cellStyleHeader);
				cellH03.setCellValue("East");
				
				HSSFCell cellH04 = header0.getCell(x+1);
				cellH04.setCellStyle(cellStyleHeader);
				cellH04.setCellValue("West");
				
				HSSFCell cellH05 = header0.getCell(x+2);
				cellH05.setCellStyle(cellStyleHeader);
				cellH05.setCellValue("East-West");
			}
			
	
			int newRow = sheet.getPhysicalNumberOfRows();
			
		//	sheet.createRow(newRow);
		//	HSSFCell cell = sheet.getRow(newRow).createCell(0);
			
	        for(i=1;i<sheet.getPhysicalNumberOfRows();i++){
	        	Integer iRow = new Integer(i);
	        	
	        	for(j=0;j<sheet.getRow(i).getPhysicalNumberOfCells();j++){
	        		HSSFCell cell = sheet.getRow(i).getCell(j);
		        	
		        	// Hay datos numericos a partir de la columna 2 (si se empieza por la cero).
		        	if(j<=2) {
		        		if(alTotalRows.contains(iRow))
		        			cell.setCellStyle(cellStyleTotalRow);
		        		else
		        			cell.setCellStyle(cellStyleRow);
	        		} else {
	        			tmpStrValue = cell.getStringCellValue();
	        			if(tmpStrValue != null && (!"".equalsIgnoreCase(tmpStrValue))) {
		        			//cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		        			
		        		
				        	if(alTotalRows.contains(iRow))
				       			cell.setCellStyle(cellStyleTotalThreeDec);
				       		else
				       			cell.setCellStyle(cellStyleThreeDec);		        				
		        			
		        			
		        			try {
		        				cell.setCellValue(nf.parse(tmpStrValue).doubleValue());
		        			}
		        			catch(Exception e){
		        				log.catching(e);
		        			}
	
		        			// Se resetea la variable temporal.
		        			tmpStrValue = null;
	        			} else {
			        		if(alTotalRows.contains(iRow))
			        			cell.setCellStyle(cellStyleTotalRow);
			        		else
			        			cell.setCellStyle(cellStyleRow);
	        			}
	        		}
		        	// Se hace abajo, porque consume tiempo.
		        	sheet.autoSizeColumn(j, true);
	        	}
	        }
	        
	    
		} catch (Exception e) {
			log.error("Error in postProcessXLS. Row: " + i + ",Column: " + j);
			log.catching(e);
		}		
    }
	*/
	
	
	/*	Para insertar la plantilla excel en la BD. Solo se usa en Desarrollo. */
	/*public void handleDocFileUpload( FileUploadEvent event) {
    	UploadedFile uUploadedFile = null;
    	String summaryMsg = null;
    	String errorMsg = null;
    	ReportTemplateBean newTemplate = new ReportTemplateBean();
    	newTemplate.setTempCode("BALANCE_REPORT");
    	newTemplate.setTempDesc("Balance Report Template");
    	
		uUploadedFile = event.getFile();
		if(uUploadedFile!=null){
			newTemplate.setFileName(uUploadedFile.getFileName());
			newTemplate.setBinaryData(uUploadedFile.getContents());
		} else {
	    	summaryMsg = "Error insertando fichero template.";			
    		errorMsg = summaryMsg;
			messages.addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
		}

		try {
			service.insertReportTemplate(newTemplate);
			
			summaryMsg = "Fichero insertado";
	    	String okMsg = summaryMsg;    	
	    	messages.addMessage(Constants.head_menu[9],
					new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
	    	log.info(okMsg);
		} 
		catch (Exception e) {
	    	summaryMsg = "Error 2 insertando fichero template.";			
    		errorMsg = summaryMsg;
    		messages.addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
    		// Se guarda el detalle del error tecnico.
	    	log.error(e.getMessage(), e);			
		}
    }*/
	
	
	

	/**********************************
	 * EXCEL
	 ***********************************/
	/**
	 * Create a style on base workbook
	 * 
	 * @param font            Font used by the style
	 * @param cellAlign       Cell alignment for contained text (see {@link HSSFCellStyle})
	 * @param cellColor       Cell background color (see {@link HSSFColor})
	 * @param cellBorder      Cell has border (<code>true</code>) or not (<code>false</code>)
	 * @param cellBorderColor Cell border color (see {@link HSSFColor})
	 * 
	 * @return New cell style
	 */
	
	private HSSFWorkbook wb;
	// Fonts
	private HSSFFont headerFont;
	private HSSFFont contentFont;
	private HSSFFont totalFont;
	// Styles
	private HSSFCellStyle cellStyleHeader;

	private HSSFCellStyle cellStyleTwoDec; 
	private HSSFCellStyle cellStyleThreeDec; 
	private HSSFCellStyle cellStyleTotalTwoDec; 
	private HSSFCellStyle cellStyleTotalThreeDec; 
	
	private HSSFCellStyle cellStyleText;
	private HSSFCellStyle cellStyleTotal;
	
	/**
	 * Create a new font on base workbook
	 * 
	 * @param fontColor       Font color (see {@link HSSFColor})
	 * @param fontHeight      Font height in points
	 * @param fontBold        Font is boldweight (<code>true</code>) or not (<code>false</code>)
	 * 
	 * @return New cell style
	 */
	private HSSFFont createFont(short fontColor, short fontHeight, boolean fontBold) {
 
		HSSFFont font = wb.createFont();
		font.setBold(fontBold);
		font.setColor(fontColor);
		font.setFontName("Calibri");
		font.setFontHeightInPoints(fontHeight);
 
		return font;
	}
	private HSSFCellStyle createStyle(HSSFFont font, short cellAlign, short cellColor, boolean cellBorder, short cellBorderColor, short border, 
			boolean numberFormat,short dataFormat) {
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(cellAlign);
		style.setFillForegroundColor(cellColor);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
 
		if (cellBorder) {
			style.setBorderTop(border);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setBorderBottom(border);
 
			style.setTopBorderColor(cellBorderColor);
			style.setLeftBorderColor(cellBorderColor);
			style.setRightBorderColor(cellBorderColor);
			style.setBottomBorderColor(cellBorderColor);
		}
		
		 if(numberFormat){
			 style.setDataFormat(dataFormat);
		 }
	  
		
		
		return style;
	}
	
	
public void processXLS(Object document) {
		
		wb = new HSSFWorkbook();
		wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		
		wb.setSheetName(0, "ShipperDailyStatus");	
				
		// Generate fonts
		headerFont  = createFont(HSSFColor.BLACK.index, (short)12, true);
		contentFont = createFont(HSSFColor.BLACK.index, (short)10, false);
		totalFont = createFont(HSSFColor.BLACK.index, (short)12, true);
		
		
		// Generate styles
		DataFormat format = wb.createDataFormat();
		
		cellStyleHeader  =  createStyle(headerFont,  HSSFCellStyle.ALIGN_CENTER, HSSFColor.GREY_40_PERCENT.index, true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		cellStyleText  =    createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##"));
		cellStyleTotal  =    createStyle(totalFont,  HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		cellStyleTwoDec =  createStyle(contentFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.00"));
		cellStyleThreeDec = createStyle(contentFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.000"));
		cellStyleTotalTwoDec =  createStyle(totalFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,        true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, true, format.getFormat("#,##0.00"));
		cellStyleTotalThreeDec = createStyle(totalFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,        true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, true, format.getFormat("#,##0.000"));

				
	
		
		//CABECERA: 
			//HSSFCell cellH02 = header0.getCell(2);
			//	cellH02.setCellValue("Summary Pane");
			HSSFCell cellH00 = header.getCell(0);
			cellH00.setCellValue("Date");
			
			
			HSSFCell cellH01 = header.getCell(1);
			cellH01.setCellValue("Shipper");
			
			
			HSSFCell cellH02 = header.getCell(2);
			cellH02.setCellValue("Contract");
			
			
			for(int x=3;x<sheet.getRow(1).getPhysicalNumberOfCells();x=x+3){
				HSSFCell cellH03 = header.getCell(x);		
				cellH03.setCellValue("East");
				HSSFCell cellH04 = header.getCell(x+1);
				cellH04.setCellValue("West");
				HSSFCell cellH05 = header.getCell(x+2);
				cellH05.setCellValue("East-West");
			}
			
		 for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			HSSFCell cell = header.getCell(i);
			cell.setCellStyle(cellStyleHeader);
			
			
		}
		
		// Para convertir las cantidades string en double.
	    NumberFormat nf = NumberFormat.getInstance(new Locale(getLanguage().getLocale()));
	    String tmpStrValue = null;
	    
	 // Almacena los numeros de filas que hay que marcar como Totales.
	 		ArrayList<Integer> alTotalRows = new ArrayList<Integer>();
	 		int i = -1;  	// Filas
	 		int j = -1;		// Columnas
	 		
	 // Se busca en la columna 1, la cadena "TOTAL:" para saber si hay que marcar esa fila de otro color o no.
        for( i=5;i<sheet.getPhysicalNumberOfRows();i++){
        	HSSFCell cell = sheet.getRow(i).getCell(1);

   			tmpStrValue = cell.getStringCellValue();
   			if(tmpStrValue.startsWith(excelTotalLabel))
   				alTotalRows.add(new Integer(i));
        }
        
		//DETALLE	
	     for ( i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
	    	 
	    		Integer iRow = new Integer(i);
				for ( j = 0; j <= sheet.getRow(i).getPhysicalNumberOfCells() - 1; j++) {
					HSSFCell cell = sheet.getRow(i).getCell(j);
		        	// Hay datos numericos a partir de la columna 2 (si se empieza por la cero).
		        	if(j<=2) {
		        		if(alTotalRows.contains(iRow))
		        			cell.setCellStyle(cellStyleTotal);
		        		else
		        			cell.setCellStyle(cellStyleText);
	        		} else {
	        			 tmpStrValue = cell.getStringCellValue();
	        			if(tmpStrValue != null && (!"".equalsIgnoreCase(tmpStrValue))) {
		        			//cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		        			
		        		
				        	if(alTotalRows.contains(iRow))
				       			cell.setCellStyle(cellStyleTotalThreeDec);
				       		else
				       			cell.setCellStyle(cellStyleThreeDec);		        				
		        			
		        			
		        			try {
		        				cell.setCellValue(nf.parse(tmpStrValue).doubleValue());
		        			}
		        			catch(Exception e){
		        				log.catching(e);
		        			}
	
		        			// Se resetea la variable temporal.
		        			tmpStrValue = null;
	        			} else {
			        		if(alTotalRows.contains(iRow))
			        			cell.setCellStyle(cellStyleTotal);
			        		else
			        			cell.setCellStyle(cellStyleText);
	        			}
	        		}
		        	// Se hace abajo, porque consume tiempo.
		        	sheet.autoSizeColumn(j, true);
	     }//fin detalle
		
	     }
     }

public int getItemsSize() { 
	if(this.items!=null && !this.items.isEmpty()){
		return this.items.size();
	}else{
		return 0;
	}
}


public int getItemsOffShoreSize() { 
	if(this.itemsOffshore!=null && !this.itemsOffshore.isEmpty()){
		return this.itemsOffshore.size();
	}else{
		return 0;
	}
}
}
