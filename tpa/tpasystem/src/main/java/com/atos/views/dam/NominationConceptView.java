package com.atos.views.dam;

import java.awt.Color;
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
import com.atos.beans.dam.NominationConceptBean;
import com.atos.filters.dam.NominationConceptFilter;
import com.atos.services.dam.NominationConceptService;
import com.atos.utils.Constants;
import com.atos.views.ChangeSystemView;
import com.atos.views.CommonView;

@ManagedBean(name = "NominationConceptView")
@ViewScoped
public class NominationConceptView  extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	
	private NominationConceptFilter filters;
	
	private NominationConceptFilter filtersNew;

	private List<NominationConceptBean> items;
	
	private static final Logger log = LogManager.getLogger("com.atos.views.dam.NominationConceptView");

	@ManagedProperty("#{nominationConceptService}")
    transient private NominationConceptService service;
    
	public void setService(NominationConceptService service) {
		this.service = service;
	}
	
	@ManagedProperty("#{changeSystemView}")
	private ChangeSystemView systemView;

	public void setSystemView(ChangeSystemView systemView) {
		this.systemView = systemView;
	}
		
	//geters/seters
	public NominationConceptFilter getFilters() {
		return filters;
	}

	public void setFilters(NominationConceptFilter filters) {
		this.filters = filters;
	}
	
	public NominationConceptFilter getFiltersNew() {
		return filtersNew;
	}

	public void setFiltersNew(NominationConceptFilter filtersNew) {
		this.filtersNew = filtersNew;
	}

	public List<NominationConceptBean> getItems() {
		return items;
	}

	public void setItems(List<NominationConceptBean> items) {
		this.items = items;
	}
	
	// Este booleano marcara si esta habilitado el filtro de shipper (isOperator = true) o esta deshabilitado, 
	// con un shipper fijo.
	public boolean getIsShipper() {
		return getUser().isUser_type(Constants.SHIPPER);
	}
	
	@PostConstruct
    public void init() {
		
		filters = initFiltersCombos();
		filtersNew = initFiltersCombos();
        
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

		XSSFCellStyle cellStyleHeader = createStyle(wb, headerFont, XSSFCellStyle.ALIGN_CENTER, Color.cyan.darker(),
				true, Color.BLACK, XSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		XSSFCellStyle cellStyleText = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##"));
		XSSFCellStyle cellStyleFourDec = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.0000"));
		XSSFCellStyle cellStyleTwoDec = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.00"));

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

					if (j == 0 || j == 1 || j == 2 || j == 3 || j == 4 || j == 9) { // Estas
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

						if (j < 8)
							cell.setCellStyle(cellStyleFourDec);
						else
							cell.setCellStyle(cellStyleTwoDec);
						
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

	private NominationConceptFilter initFiltersCombos(){
		NominationConceptFilter tmpFilter = new NominationConceptFilter();		
		return tmpFilter;
	}
	
	// Para los elementos del combo del filtro de Nomination Concept.
	public Map<BigDecimal, Object> getNomConcept() {
		return service.selectNominationConceptCombo(systemView.getIdn_active());
	}
	
	public Map<BigDecimal, Object> getUnitType() {
		return service.selectNominationConceptComboUnitType(systemView.getIdn_active());
	}
	
	// Para los elementos del combo del filtro de Type Concept Nomination.
		public Map<BigDecimal, Object> getNomConceptType() {
			return service.selectNominationConceptTypeCombo(systemView.getIdn_active());
		}
	
	public void onSearch(){
    	
    	filters.setIdn_system(getChangeSystemView().getIdn_active()); // offShore
		items = service.selectNominationConcept(filters);
    }

	public void onClear(){
		filters = initFiltersCombos();
		filtersNew = initFiltersCombos();
	  	
        if (items != null) {
            items.clear();
        }
        //items = service.search(filters);    
    }
    
    public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
    
    public void handleDialogClose() {
        
    }

    
    public void cancel() {
		filtersNew = new NominationConceptFilter();		

	}
    
    public void save() {

		String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("contractNomPoint") };
    	String summaryMsgOk = CommonView.getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk= CommonView.getMessageResourceString("insert_noOk", params);

		if (filtersNew.getNomConcept() != null) {
			
		}
		
		if (filtersNew.getNomConceptType() != null) {
					
		}

		String error = "0";
		try {
			Integer count = service.getCountNominationConcept(filtersNew);
			
			if(count == 0) {
				if ("zona".equals(filtersNew.getType())) {
					filtersNew.setIs_area_concept("N");
					filtersNew.setIs_zone_concept("Y");
				}else {
					filtersNew.setIs_area_concept("Y");
					filtersNew.setIs_zone_concept("N");
				}
				
			}else {
				errorMsg = msgs.getString("existing_nomination_concept"); //existing_nomination_concept= The Nomination Concept already exists
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;
			}
//			error = service.insertConceptNom(filtersNew);			
			
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}		

		// clean the formu new after save
	
		filtersNew = new NominationConceptFilter();
		
		onSearch();

	}
}
