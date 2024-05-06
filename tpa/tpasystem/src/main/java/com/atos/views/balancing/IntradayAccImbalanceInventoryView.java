package com.atos.views.balancing;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.FileBean;
import com.atos.beans.MessageBean;
import com.atos.beans.balancing.IntradayAccImbalanceInventoryBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.balancing.IntradayAccImbalanceInventoryFilter;
import com.atos.services.balancing.IntradayAccImbalanceInvService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="intradayAccImbInvView")
@ViewScoped
public class IntradayAccImbalanceInventoryView extends CommonView implements Serializable {
	
	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(IntradayAccImbalanceInventoryView.class);
	
	private static final String excelTotalLabel ="TOTAL";
	private static final String excelTotalLabelG ="TOTAL";
	
	private IntradayAccImbalanceInventoryFilter filters, filters_form;
	private List<IntradayAccImbalanceInventoryBean> items;
	
	private StreamedContent templateFile =  null;
	
	@ManagedProperty("#{intradayAccImbInvService}")
    transient private IntradayAccImbalanceInvService service;
		
	private Calendar sysdate;
	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	public IntradayAccImbalanceInventoryFilter getFilters() {
		return filters;
	}

	public void setFilters(IntradayAccImbalanceInventoryFilter filters) {
		this.filters = filters;
	}
	
	public IntradayAccImbalanceInventoryFilter getFilters_form() {
		return filters_form;
	}

	public void setFilters_form(IntradayAccImbalanceInventoryFilter filters_form) {
		this.filters_form = filters_form;
	}

	public List<IntradayAccImbalanceInventoryBean> getItems() {
		return items;
	}
	
	public void setItems(List<IntradayAccImbalanceInventoryBean> items) {
		this.items = items;
	}
	 
    public void setService(IntradayAccImbalanceInvService service) {
        this.service = service;
    }
    
    public StreamedContent getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(StreamedContent templateFile) {
		this.templateFile = templateFile;
	}

	@PostConstruct
    public void init() {
    	filters = new IntradayAccImbalanceInventoryFilter();
    	filters.setSystemId(getChangeSystemView().getIdn_active());
    	filters_form = new IntradayAccImbalanceInventoryFilter();
    	filters_form.setSystemId(getChangeSystemView().getIdn_active());
    	//LOOKING TO THE SYSDATE parameter BD
    	sysdate= gettingValidDateStart();
    }
	
	//sysdate 
    public Calendar gettingValidDateStart(){
    	
    	Calendar sysdate = Calendar.getInstance(); 
    	filters.setGasDay(sysdate.getTime());
    	filters_form.setGasDay(sysdate.getTime());
    	
    	sysdate.setTime(sysdate.getTime());
    	sysdate.set(Calendar.HOUR_OF_DAY, 0);
    	sysdate.set(Calendar.MINUTE, 0);
    	sysdate.set(Calendar.SECOND, 0);
    	sysdate.set(Calendar.MILLISECOND, 0);
    	
        return sysdate;
    }
	//sysdate 
    public Calendar gettingValidDateStartForm(){
    	
    	Calendar sysdate = Calendar.getInstance(); 
    	filters_form.setGasDay(sysdate.getTime());
    	
    	sysdate.setTime(sysdate.getTime());
    	sysdate.set(Calendar.HOUR_OF_DAY, 0);
    	sysdate.set(Calendar.MINUTE, 0);
    	sysdate.set(Calendar.SECOND, 0);
    	sysdate.set(Calendar.MILLISECOND, 0);
    	
        return sysdate;
    }


	
	// Methods
	public void onSearch(){
		
		filters.setSystemId(getChangeSystemView().getIdn_active());
		filters_form.setSystemId(getChangeSystemView().getIdn_active());
		items = service.selectIntradayAccImbalanceInv(filters);
       
	}

	public void callWS() {
		try {
			service.callWS();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, "Error calling inventory webservices", e.getMessage(), Calendar.getInstance().getTime()));
	    	log.error(e.getMessage());
		}
		onSearch();
	}
	
	public void onClear(){
		init();
        if (items != null) {
            items.clear();
        }
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('w_intradayAccImbInv1').clearFilters()");
    }
     
    public void cancel() {
    
    }
    
    public Map<BigDecimal, Object> getTimestampIds() {
    	if(filters.getStrLastVersion().equals("N")) {
    		if(isShipper()) {
    			filters.setIsShipper("Y");
    		}
    		return service.selectTimestampIds(filters);
    	} else {
			return null;
    	}
	}
    public Map<BigDecimal, Object> getTimestampIds_form() {
   		return service.selectTimestampIds(filters_form);
	}
    
	// Para crear la cabecera de la hoja excel.
	public void preProcessXLS(Object document) {
		XSSFWorkbook wbSource = null;	// Para la plantilla excel.
        XSSFWorkbook wb = (XSSFWorkbook) document;
        ByteArrayInputStream bais = null;

        /* Para cargar plantilla desde fichero local. Solo se usa en Desarrollo. 
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
	    }*/
		
		try {
			bais = service.getReportTemplate(getChangeSystemView().getIdn_active(), isShipper());
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
	
	private boolean containsInt(int[] array, int target){
		
		for(int i=0; i<array.length; i++)
			if(array[i]==target)
				return true;
		
		return false;
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


	public void postProcessXLS(Object document) {
		
		// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	// Al procesar la excel, algunos parametros cambian entre la plantilla onshore y la offshore.
    	int initialRowToProcess;
    	int[] aiPercentageCells;		// Para mostrar 2 decimales en las cantidades.
    	if(getChangeSystemView().isOnshore()){
    		initialRowToProcess = 4;
    		aiPercentageCells = new int[] {39, 49, 50, 51};
    	} else {	// offshore
    		initialRowToProcess = 4;
    		aiPercentageCells = new int[] {18, 22};
    	}
    	
		XSSFWorkbook wb = (XSSFWorkbook) document;
		wb.setSheetName(0, msgs.getString("intradayAccImbInv_sheet_name"));
        XSSFSheet sheet = wb.getSheetAt(0);
        // Para convertir las cantidades string en double.
        NumberFormat nf = NumberFormat.getInstance(new Locale(getLanguage().getLocale()));
        
        // La primera linea se machaca en el proceso.
        // Asi que en la plantilla esta vacia y luego se oculta.
        XSSFRow header0 = sheet.getRow(0);
        header0.getCTRow().setHidden(true);
        
        XSSFCellStyle cellStyleRow = wb.createCellStyle();
        cellStyleRow.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleRow.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleRow.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleRow.setBorderLeft(XSSFCellStyle.BORDER_THIN);

        byte[] rgb = new byte[3];
        rgb[0] = (byte) 153; // red
        rgb[1] = (byte) 230; // green
        rgb[2] = (byte) 230; // blue
        XSSFColor totalColor = new XSSFColor(rgb); // #f2dcdb
        
        XSSFCellStyle cellStyleTotalRow = wb.createCellStyle();
        cellStyleTotalRow.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setFillForegroundColor(totalColor);
        cellStyleTotalRow.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        
        byte[] rgbG = new byte[3];
        rgb[0] = (byte) 94; // red
        rgb[1] = (byte) 130; // green
        rgb[2] = (byte) 129; // blue
        XSSFColor totalColorG = new XSSFColor(rgbG); // #f2dcdb
        
        XSSFCellStyle cellStyleTotalRowG = wb.createCellStyle();
        cellStyleTotalRowG.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRowG.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRowG.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRowG.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRowG.setFillForegroundColor(totalColorG);
        cellStyleTotalRowG.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        
		DataFormat format = wb.createDataFormat();
		short twoDecimalFormat = format.getFormat("#,##0.00");
        short threeDecimalFormat = format.getFormat("#,##0.000");
        short fourDecimalFormat = format.getFormat("#,##0.0000");
        
        // Este estilo se aplica a cantidades que son porcentajes (2 decimales).
        XSSFCellStyle cellStyleTwoDec = wb.createCellStyle();
        cellStyleTwoDec.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleTwoDec.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleTwoDec.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleTwoDec.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleTwoDec.setDataFormat(twoDecimalFormat);
        
        XSSFCellStyle cellStyleTotalTwoDec = wb.createCellStyle();
        cellStyleTotalTwoDec.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalTwoDec.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalTwoDec.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalTwoDec.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalTwoDec.setDataFormat(twoDecimalFormat);
        cellStyleTotalTwoDec.setFillForegroundColor(totalColor);
        cellStyleTotalTwoDec.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
		// Este estilo se aplica al resto de cantidades (3 decimales).
        XSSFCellStyle cellStyleThreeDec = wb.createCellStyle();
        cellStyleThreeDec.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleThreeDec.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleThreeDec.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleThreeDec.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleThreeDec.setDataFormat(threeDecimalFormat);
		
        XSSFCellStyle cellStyleTotalThreeDec = wb.createCellStyle();
        cellStyleTotalThreeDec.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalThreeDec.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalThreeDec.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalThreeDec.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalThreeDec.setDataFormat(threeDecimalFormat);
        cellStyleTotalThreeDec.setFillForegroundColor(totalColor);
        cellStyleTotalThreeDec.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        
     //  (4 decimales).
        XSSFCellStyle cellStyleFourDec = wb.createCellStyle();
        cellStyleFourDec.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleFourDec.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleFourDec.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleFourDec.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleFourDec.setDataFormat(fourDecimalFormat);
		
        XSSFCellStyle cellStyleTotalFourDec = wb.createCellStyle();
        cellStyleTotalFourDec.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalFourDec.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalFourDec.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalFourDec.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalFourDec.setDataFormat(fourDecimalFormat);
        cellStyleTotalFourDec.setFillForegroundColor(totalColor);
        cellStyleTotalFourDec.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        
        
		String tmpStrValue = null;
		// Almacena los numeros de filas que hay que marcar como Totales.
		ArrayList<Integer> alTotalRows = new ArrayList<Integer>();
		ArrayList<Integer> alTotalRowsG = new ArrayList<Integer>();
		int fila = -1;  	// Filas
		int columna = -1;		// Columnas

		try{
			// Las filas de la 0 a la 4 son cabeceras.
			
	        for(int i=initialRowToProcess;i<sheet.getPhysicalNumberOfRows();i++){
	        	fila = i;
	        	Integer iRow = new Integer(i);
	        	
	        	for(int j=0;j<sheet.getRow(i).getPhysicalNumberOfCells();j++){
	        		columna = j;
	        		XSSFCell cell = sheet.getRow(i).getCell(j);
		        	
		        	// Hay datos numericos a partir de la columna 1 y hasta la 12 (si se empieza por la cero).
	        		if(j==0 || j>12) {
		        			cell.setCellStyle(cellStyleRow);
	        		} else {
	        			tmpStrValue = cell.getStringCellValue();
	        			//cell.setCellType(Cell.CELL_TYPE_NUMERIC);
	        			if(tmpStrValue != null && (!"".equalsIgnoreCase(tmpStrValue))) {
	        				cell.setCellType(Cell.CELL_TYPE_NUMERIC);   			
		          			cell.setCellStyle(cellStyleFourDec);
		        			
		        			try {
		        				cell.setCellValue(nf.parse(tmpStrValue).doubleValue());
		        			}
		        			catch(Exception e){
		        				log.catching(e);
		        			}
	
		        			// Se resetea la variable temporal.
		        			tmpStrValue = null;
	        			} else {
	        				cell.setCellStyle(cellStyleRow);
	        			}
	        		
			        	if(j==1) {
	        				Color color = getColorCell(items.get(i-initialRowToProcess).getE_total_inventory_colour());
	        				XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
	        		        XSSFCellStyle style = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT,color, true,
	        						Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##0.0000"));
	        		        style.setDataFormat(fourDecimalFormat);
	        				cell.setCellStyle(style);	 
	        			}
	        			if(j==2) {
	        				Color color = getColorCell(items.get(i-initialRowToProcess).getW_total_inventory_colour());
	        				XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
	        		        XSSFCellStyle style = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT,color, true,
	        						Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##0.0000"));
	        		        style.setDataFormat(fourDecimalFormat);
	        				cell.setCellStyle(style);	
	        			}
	        			if(j==3) {
	        				Color color = getColorCell(items.get(i-initialRowToProcess).getE_base_inventory_colour());
	        				XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
	        		        XSSFCellStyle style = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT,color, true,
	        						Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##0.0000"));
	        		        style.setDataFormat(fourDecimalFormat);
	        				cell.setCellStyle(style);	
	        			}if(j==4) {
	        				Color color = getColorCell(items.get(i-initialRowToProcess).getW_base_inventory_colour());
	        				XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
	        		        XSSFCellStyle style = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT,color, true,
	        						Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##0.0000"));
	        		        style.setDataFormat(fourDecimalFormat);
	        				cell.setCellStyle(style);	 
	        			}
	        			if(j==5) {
	        				Color color = getColorCell(items.get(i-initialRowToProcess).getE_inventory_operator_colour());
	        				XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
	        		        XSSFCellStyle style = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT,color, true,
	        						Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##0.0000"));
	        		        style.setDataFormat(fourDecimalFormat);
	        				cell.setCellStyle(style);
	        			}
	        			else if(j==6) {
	        				Color color = getColorCell(items.get(i-initialRowToProcess).getW_inventory_operator_colour());
	        				XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
	        		        XSSFCellStyle style = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT,color, true,
	        						Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##0.0000"));
	        		        style.setDataFormat(fourDecimalFormat);
	        				cell.setCellStyle(style);	
	        			}
	        			else if(j==7) {
	        				Color color = getColorCell(items.get(i-initialRowToProcess).getE_other_shippers_colour());
	        				XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
	        		        XSSFCellStyle style = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT,color, true,
	        						Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##0.0000"));
	        		        style.setDataFormat(fourDecimalFormat);
	        				cell.setCellStyle(style);
	        			}
	        			else if(j==8) {
	        				Color color = getColorCell(items.get(i-initialRowToProcess).getW_other_shippers_colour());
	        				XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
	        		        XSSFCellStyle style = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT,color, true,
	        						Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##0.0000"));
	        		        style.setDataFormat(fourDecimalFormat);
	        				cell.setCellStyle(style);	
	        			}
	        			else if(j==9) {
	        				Color color = getColorCell(items.get(i-initialRowToProcess).getE_others_colour());
	        				XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
	        		        XSSFCellStyle style = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT,color, true,
	        						Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##0.0000"));
	        		        style.setDataFormat(fourDecimalFormat);
	        				cell.setCellStyle(style);	
	        			}
	        			else if(j==10) {
	        				Color color = getColorCell(items.get(i-initialRowToProcess).getW_others_colour());
	        				XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
	        		        XSSFCellStyle style = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT,color, true,
	        						Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##0.0000"));
	        		        style.setDataFormat(fourDecimalFormat);
	        				cell.setCellStyle(style);
	        			}
	        			else if(j==11) {
	        				Color color = getColorCell(items.get(i-initialRowToProcess).getE_pttshipper_colour());
	        				XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
	        		        XSSFCellStyle style = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT,color, true,
	        						Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##0.0000"));
	        		        style.setDataFormat(fourDecimalFormat);
	        				cell.setCellStyle(style);	 
	        			}
	        			else if(j==12) {
	        				Color color = getColorCell(items.get(i-initialRowToProcess).getW_pttshipper_colour());
	        				XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
	        		        XSSFCellStyle style = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT,color, true,
	        						Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##0.0000"));
	        		        style.setDataFormat(fourDecimalFormat);
	        				cell.setCellStyle(style);	 
	        			}
		        	// Se hace abajo, porque consume tiempo.
		        	//sheet.autoSizeColumn(j, true);
	        		}
	        	}
	        }
	        
	        // Se supone que todas las filas tienen el mismo numero de columnas.
			// Se actualiza el final el tamaño de las columnas porque consumiria
			// mucho tiempo si se hiciera fila por fila.
        	for(int j=0;j<sheet.getRow(initialRowToProcess-1).getPhysicalNumberOfCells();j++){
        		columna = j;
        		sheet.autoSizeColumn(j, true);
        	}
        
		} catch (Exception e) {
			log.error("Error in postProcessXLS. Row: " + fila + ",Column: " + columna, e);
			log.catching(e);
		}	
	}
	
	public Color getColorCell(String color) {
		if(color!=null) {
			if(color.equalsIgnoreCase("red"))
				return Color.RED;
			else if (color.equalsIgnoreCase("orange"))
				return Color.ORANGE;
			else if (color.equalsIgnoreCase("green"))
				return Color.GREEN;
			else if (color.equalsIgnoreCase("yellow"))
				return Color.YELLOW;
			else return Color.WHITE;
		}
		return Color.WHITE;
	}
	
	
	/*	Para insertar la plantilla excel en la BD. Solo se usa en Desarrollo. */
	/*public void handleDocFileUpload( FileUploadEvent event) {
    	UploadedFile uUploadedFile = null;
    	String summaryMsg = null;
    	String errorMsg = null;
    	ReportTemplateBean newTemplate = new ReportTemplateBean();
    	newTemplate.setTempCode("BALANCE_INTRADAY_REPORT");
    	newTemplate.setTempDesc("Balance Intraday Report Template");
    	
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
    
    public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
    
    public void handleFileUpload( FileUploadEvent event) {

		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	UploadedFile uUploadedFile = null;
    	FileBean fbUploadedFile = null;
    	String okMessage = null;
    	String summaryMsg = null;
    	String errorMsg = null;
		
		uUploadedFile = event.getFile();
		if(uUploadedFile!=null){
			fbUploadedFile = new FileBean(uUploadedFile.getFileName(), uUploadedFile.getContentType(), uUploadedFile.getContents());
		}
        
		if(uUploadedFile==null || fbUploadedFile==null){
	    	summaryMsg = msgs.getString("saving_error");			
    		errorMsg = msgs.getString("file_must_selected");
			getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
		}

		try {
			service.updateWsFromFile(fbUploadedFile, getUser(), getLanguage(), getChangeSystemView());
			summaryMsg = msgs.getString("data_stored");
			okMessage = msgs.getString("met_man_processing");
			getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.INFO, summaryMsg, okMessage, Calendar.getInstance().getTime()));
				
		} 
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = ve.getMessage();
    		getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));		
		}
		catch (Exception e) {
			summaryMsg = msgs.getString("saving_data_error");
    		errorMsg = msgs.getString("met_man_sub_data_not_stored") + " " + msgs.getString("internal_error");
    		getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
    		// Se guarda el detalle del error tecnico.
	    	log.error(e.getMessage(), e);			
		}
		
		// Si se ejectua el proceso en background, no tiene sentido actualizar los datos.
        // onSearch();
    }
	
    public void selectTemplateFile() {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	/*ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = msgs.getString("met_man_template_file");
    	
    	// Se inicializa esta variable para coger el template correspondiente a partir del parametro de entrada.
		try {  
			templateFile = service.selectTemplateFile(getChangeSystemView().getIdn_active());
		}
		catch(Exception e){
			String errorMsg = msgs.getString("download_error") + ".";
			getMessages().addMessage(Constants.head_menu[7],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
		}*/
	}
    
    public void updateTimestamp() {
    	if(filters.getStrLastVersion().equals("Y"))
    		filters.setTimestampVar(null);
    }

    public void cancelTimestamp() {
    	filters_form = new IntradayAccImbalanceInventoryFilter();
    	filters_form.setSystemId(getChangeSystemView().getIdn_active());
    	//LOOKING TO THE SYSDATE parameter BD
    	sysdate= gettingValidDateStartForm();
    	
    }
    public void saveTimestamp() {
    	
    	String ret = service.saveTimestamp(filters_form,getUser().getUsername());
    	if(ret.equals("0")) {
    		getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.INFO,"Timestamp saved","Timestamp saved", Calendar.getInstance().getTime()));
    	} else {
    		getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, "Error modifing timestamps", "Error modifing timestamps", Calendar.getInstance().getTime()));
	    	
    	}
    	
    }
}
