package com.atos.views.balancing;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.atos.beans.MessageBean;
import com.atos.beans.balancing.BalanceReportBean;
import com.atos.beans.balancing.BalanceReportOffshoreBean;
import com.atos.filters.balancing.BalanceReportFilter;
import com.atos.services.balancing.BalanceReportService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="balReportView")
@ViewScoped
public class BalanceReportView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10642654929157783L;

	private static final String excelTotalLabel ="TOTAL:";
	private static final String excelTotalLabelG ="TOTAL";
	
	private BalanceReportFilter filters;
	private List<BalanceReportBean> items;
	private List<BalanceReportOffshoreBean> itemsOffshore;
	private BalanceReportBean selected;
	private BalanceReportOffshoreBean selectedOffshore;
	
	private static final Logger log = LogManager.getLogger("com.atos.views.balancing.BalanceReportView");

	@ManagedProperty("#{BalanceReportService}")
    transient private BalanceReportService service;
    
	public void setService(BalanceReportService service) {
		this.service = service;
	}
	
	//geters/seters
	public BalanceReportFilter getFilters() {
		return filters;
	}

	public void setFilters(BalanceReportFilter filters) {
		this.filters = filters;
	}

	public List<BalanceReportBean> getItems() {
		return items;
	}

	public void setItems(List<BalanceReportBean> items) {
		this.items = items;
	}
	
	public List<BalanceReportOffshoreBean> getItemsOffshore() {
		return itemsOffshore;
	}

	public void setItemsOffshore(List<BalanceReportOffshoreBean> itemsOffshore) {
		this.itemsOffshore = itemsOffshore;
	}

	public BalanceReportBean getSelected() {
		return selected;
	}

	public void setSelected(BalanceReportBean selected) {
		this.selected = selected;
	}

	public BalanceReportOffshoreBean getSelectedOffshore() {
		return selectedOffshore;
	}

	public void setSelectedOffshore(BalanceReportOffshoreBean selectedOffshore) {
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
		// En la carga de la pantalla no se hace busqueda porque faltaria poner el valor por defecto de la fecha del filtro.
        //items = service.search(filters);
        selected = new BalanceReportBean();
        selectedOffshore = new BalanceReportOffshoreBean();
	}
	
	private BalanceReportFilter initFilter(){
		BalanceReportFilter tmpFilter = new BalanceReportFilter();
		Calendar tmpToday = Calendar.getInstance();
		tmpToday.set(Calendar.DAY_OF_MONTH, 1);			// Para obtener el primer dia del mes.
		tmpToday.set(Calendar.HOUR_OF_DAY, 0);
		tmpToday.set(Calendar.MINUTE, 0);
		tmpToday.set(Calendar.SECOND, 0);
		tmpToday.set(Calendar.MILLISECOND, 0);
		tmpFilter.setFromDate(tmpToday.getTime());

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
			String strError = msgs.getString("bal_rep_period") + ": " + msgs.getString("empty_field");
			getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
			return;
		}
		
		if(getChangeSystemView().isOnshore()) {
	        items = service.search(filters);
	        // En cada busqueda se resetea la fila seleccionada.
	        selected = new BalanceReportBean();
		}
		else {
			itemsOffshore = service.searchOffshore(filters);
			// En cada busqueda se resetea la fila seleccionada.
	        selectedOffshore = new BalanceReportOffshoreBean();
		}
		//RequestContext.getCurrentInstance().update("form:fromDate");
    }

	public void onClear(){
		filters = initFilter();
		
		if(getChangeSystemView().isOnshore()) {
	        if (items != null) {
	            items.clear();
	        }
			// Al hacer clear no se hace busqueda porque faltaria poner el valor por defecto de la fecha del filtro.
	        //items = service.search(filters);
	        // En cada busqueda se resetea la fila seleccionada.
	        selected = new BalanceReportBean();
		} else {
	        if (itemsOffshore != null) {
	        	itemsOffshore.clear();
	        }
			// En cada busqueda se resetea la fila seleccionada.
	        selectedOffshore = new BalanceReportOffshoreBean();
		}
    }

	
	// Para crear la cabecera de la hoja excel.
	public void preProcessXLS(Object document) {
		XSSFWorkbook wbSource = null;	// Para la plantilla excel.
        XSSFWorkbook wb = (XSSFWorkbook) document;
        ByteArrayInputStream bais = null;

        // Para cargar plantilla desde fichero local. Solo se usa en Desarrollo. 
    	/*String templateFilePath = "C://Pablo//proyectos//160425_PTT//03.Desarrollo//tmp//170726_balances_filtroOnshore//170801_plantilla//balance_report_offshore.xlsx";
		InputStream is = null;

		try {
		    is = new FileInputStream(templateFilePath);
		    wbSource = new XSSFWorkbook(is);
		    
			if(wbSource!=null && wb!=null)
				service.copySheets(wbSource.getSheetAt(0), wb.getSheetAt(0));
			
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
			bais = service.getReportTemplate(getChangeSystemView().getIdn_active());
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
	
	public void postProcessXLS(Object document) {
		
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	// Al procesar la excel, algunos parametros cambian entre la plantilla onshore y la offshore.
    	int initialRowToProcess;
    	int[] aiPercentageCells;		// Para mostrar 2 decimales en las cantidades.
    	if(getChangeSystemView().isOnshore()){
    		initialRowToProcess = 3;
    		aiPercentageCells = new int[] {39, 52, 53, 54};
    	} else {	// offshore
    		initialRowToProcess = 4;
    		aiPercentageCells = new int[] {18, 22};
    	}
    	
    	
		SimpleDateFormat summSdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		
		XSSFWorkbook wb = (XSSFWorkbook) document;
		wb.setSheetName(0, msgs.getString("bal_rep_sheet_name"));
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
        rgbG[0] = (byte) 217; // red
        rgbG[1] = (byte) 208; // green
        rgbG[2] = (byte) 86; // blue
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
        
        XSSFCellStyle cellStyleTotalGTwoDec = wb.createCellStyle();
        cellStyleTotalGTwoDec.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalGTwoDec.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalGTwoDec.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalGTwoDec.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalGTwoDec.setDataFormat(twoDecimalFormat);
        cellStyleTotalGTwoDec.setFillForegroundColor(totalColorG);
        cellStyleTotalGTwoDec.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
/*		// Este estilo se aplica al resto de cantidades (3 decimales).
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
        cellStyleTotalThreeDec.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);*/
        
		// Este estilo se aplica al resto de cantidades (4 decimales).
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
        
        XSSFCellStyle cellStyleTotalGFourDec = wb.createCellStyle();
        cellStyleTotalGFourDec.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalGFourDec.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalGFourDec.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalGFourDec.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalGFourDec.setDataFormat(fourDecimalFormat);
        cellStyleTotalGFourDec.setFillForegroundColor(totalColorG);
        cellStyleTotalGFourDec.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        
		String tmpStrValue = null;
		// Almacena los numeros de filas que hay que marcar como Totales.
		ArrayList<Integer> alTotalRows = new ArrayList<Integer>();
		ArrayList<Integer> alTotalRowsG = new ArrayList<Integer>();
		int i = -1;  	// Filas
		int j = -1;		// Columnas

		try{
			Date tmpVersionDate = null;
			if(getChangeSystemView().isOnshore() && items!=null && items.size()>0)
				tmpVersionDate = items.get(0).getVersionDate();	// Se coge la fecha del primer item, porque todos los items tendran la misma fecha.
			
			if(getChangeSystemView().isOffshore() && itemsOffshore!=null && itemsOffshore.size()>0)
				tmpVersionDate = itemsOffshore.get(0).getVersionDate();
				
			if(tmpVersionDate!= null) {
				XSSFCell summPaneCell = sheet.getRow(1).getCell(1);
				tmpStrValue = summPaneCell.getStringCellValue();
				tmpStrValue += ": " + msgs.getString("bal_rep_bal_generated_on") + " ";
				tmpStrValue += summSdf.format(tmpVersionDate);
				summPaneCell.setCellValue(tmpStrValue);
			}
			
			// Las filas de la 0 a la 4 son cabeceras en onshore. En offshore son de la 0 a la 3.
			// Se busca en la columna 1, la cadena "TOTAL:" para saber si hay que marcar esa fila de otro color o no.
	        for(i=initialRowToProcess;i<sheet.getPhysicalNumberOfRows();i++){
	        	XSSFCell cell = sheet.getRow(i).getCell(1);

       			tmpStrValue = cell.getStringCellValue();
       			if(tmpStrValue.startsWith(excelTotalLabel))
       				alTotalRows.add(new Integer(i));
       			else if(tmpStrValue.startsWith(excelTotalLabelG))
       				alTotalRowsG.add(new Integer(i));
	        }
	        			
	        for(i=initialRowToProcess;i<sheet.getPhysicalNumberOfRows();i++){
	        	Integer iRow = new Integer(i);
	        	
	        	for(j=0;j<sheet.getRow(i).getPhysicalNumberOfCells();j++){
	        		XSSFCell cell = sheet.getRow(i).getCell(j);
		        	
		        	// Hay datos numericos a partir de la columna 3 (si se empieza por la cero).
		        	if(j<=3) {
		        		if(alTotalRows.contains(iRow))
		        			cell.setCellStyle(cellStyleTotalRow);
		        		else if(alTotalRowsG.contains(iRow))
		        			cell.setCellStyle(cellStyleTotalRowG);
		        		else
		        			cell.setCellStyle(cellStyleRow);
	        		} else {
	        			tmpStrValue = cell.getStringCellValue();
	        			if(tmpStrValue != null && (!"".equalsIgnoreCase(tmpStrValue))) {
		        			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		        			
		        			// Columnas correspondientes a porcentajes que solo deben mostrar 2 decimales.
		        			// Se mira si la columna en curso (j) esta contenida en el array de columnas que deben tener 2 decimales.
		        			if(containsInt(aiPercentageCells, j)){
				        		if(alTotalRows.contains(iRow))
				        			cell.setCellStyle(cellStyleTotalTwoDec);
				        		else if(alTotalRowsG.contains(iRow))
				        			cell.setCellStyle(cellStyleTotalGTwoDec);
				        		else
				        			cell.setCellStyle(cellStyleTwoDec);
		        			}
		        			else {
				        		if(alTotalRows.contains(iRow))
				        			cell.setCellStyle(cellStyleTotalFourDec);
				        		else if(alTotalRowsG.contains(iRow))
				        			cell.setCellStyle(cellStyleTotalGFourDec);
				        		else
				        			cell.setCellStyle(cellStyleFourDec);		        				
		        			}
		        			
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
			        		else if(alTotalRowsG.contains(iRow))
			        			cell.setCellStyle(cellStyleTotalRowG);
			        		else
			        			cell.setCellStyle(cellStyleRow);
	        			}
	        		}
		        	// Se hace abajo, porque consume tiempo.
		        	//sheet.autoSizeColumn(j, true);
	        	}
	        }
	        
	        // Se supone que todas las filas tienen el mismo numero de columnas.
			// Se actualiza el final el tamaño de las columnas porque consumiria
			// mucho tiempo si se hiciera fila por fila.
        	for(j=0;j<sheet.getRow(initialRowToProcess).getPhysicalNumberOfCells();j++){
        		sheet.autoSizeColumn(j, true);
        	}
        
		} catch (Exception e) {
			log.error("Error in postProcessXLS. Row: " + i + ",Column: " + j, e);
			log.catching(e);
		}		
    }
	
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
