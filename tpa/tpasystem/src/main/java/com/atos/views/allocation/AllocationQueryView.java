package com.atos.views.allocation;

import java.awt.Color;
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

import com.atos.beans.MessageBean;
import com.atos.beans.allocation.AllocationQueryBean;
import com.atos.filters.allocation.AllocationQueryFilter;
import com.atos.services.allocation.AllocationQueryService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="AllocationQueryView")
@ViewScoped
public class AllocationQueryView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7170692799132613852L;

	private static final String EnergyUnit = "MMBTU/D";					// Unidad de medida de la pagina.
	private BigDecimal factorFromDefaultUnit = null;
	
	private AllocationQueryFilter filters;
	// En los combos encadenados, al hacerse algun cambio se puede llegar a hacer la misma consulta a BD varias veces.
	// En esta variable se guarda el valor de los ultimos filtros. Si no han cambiado, no se repiten las consultas.
	private AllocationQueryFilter prevFilters;		// Previous filters.
	// Variables de control para almacenar si alguno de los combos ha cambiado. Se resetean al consultar el get la primera vez.
	private boolean filteredZonesChanged = false;
	private boolean filteredAreasChanged = false;
	// Se guarda el combo/lista de zonas para que no se consulte continuamente la BD, cuando se actualice el filtro.
	// Con esto, si se añade una zona a un sistema (onshore/offshore), no se actualizaria el filtro hasta que se cargue otra vez la vista.
	// (Al cambiar de sistema de la pantalla, se carga la vista completa).
	private Map<BigDecimal, Object> zonesCombo = null;
	private Map<BigDecimal, Object> areasCombo = null;
	private Map<BigDecimal, Object> pointsCombo = null;

	private List<AllocationQueryBean> items;
	// Por rendimiento, allocationMaxDateOffset solo se consulta 1 vez durante la vista.
	private Integer allocationMaxDateOffset = null;

	private static final Logger log = LogManager.getLogger("com.atos.views.allocation.AllocationQueryView");

	@ManagedProperty("#{AllocationQueryService}")
    transient private AllocationQueryService service;
    
	public void setService(AllocationQueryService service) {
		this.service = service;
	}
		
	//geters/seters
	public AllocationQueryFilter getFilters() {
		return filters;
	}

	public void setFilters(AllocationQueryFilter filters) {
		this.filters = filters;
	}
	
	public List<AllocationQueryBean> getItems() {
		return items;
	}

	public void setItems(List<AllocationQueryBean> items) {
		this.items = items;
	}
	
	// Este booleano marcara si esta habilitado el filtro de shipper (isOperator = true) o esta deshabilitado, 
	// con un shipper fijo.
	public boolean getIsShipper() {
		return getUser().isUser_type(Constants.SHIPPER);
	}
	
	@PostConstruct
    public void init() {
		factorFromDefaultUnit = service.selectFactorFromDefaultUnit(EnergyUnit);
    	try{
    		allocationMaxDateOffset = service.selectAllocationMaxDateOffset(getUser().getUsername(), getLanguage().getLocale());
		}
		catch(Exception e){
			log.error(e.getMessage(), e);
		}
		filters = initFiltersCombos(allocationMaxDateOffset, factorFromDefaultUnit);
        items = service.search(filters);
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

	private AllocationQueryFilter initFiltersCombos(Integer _allocationMaxDateOffset, BigDecimal _factorFromDefaultUnit){
		AllocationQueryFilter tmpFilter = new AllocationQueryFilter();
		Calendar tmpToday = Calendar.getInstance();
		tmpToday.set(Calendar.HOUR_OF_DAY, 0);
		tmpToday.set(Calendar.MINUTE, 0);
		tmpToday.set(Calendar.SECOND, 0);
		tmpToday.set(Calendar.MILLISECOND, 0);
		tmpToday.add(Calendar.DAY_OF_MONTH, _allocationMaxDateOffset * (-1));
		tmpFilter.setGasDay(tmpToday.getTime());
		// Si no es operador, se fija el filtro al shipper del usuario.
		if(getIsShipper())
			tmpFilter.setShipperId(getUser().getIdn_user_group());
		tmpFilter.setFactorFromDefaultUnit(_factorFromDefaultUnit);
		
		tmpFilter.setSystemId(getChangeSystemView().getIdn_active());
		
		zonesCombo = service.selectZones(getChangeSystemView().getSystem());
    	// Se rellena el array de puntos del filtro, transformando en array el conjunto de keys, 
		// especificando como clase un array de BigDecimal del tamaño adecuado.
		tmpFilter.setZoneIds(zonesCombo.keySet().toArray(new BigDecimal[zonesCombo.keySet().size()]));
    	
    	areasCombo = service.selectAreas(tmpFilter);
    	tmpFilter.setAreaIds(areasCombo.keySet().toArray(new BigDecimal[areasCombo.keySet().size()]));
    	
    	pointsCombo = service.selectPointId(tmpFilter);
    	tmpFilter.setNomPointIds(pointsCombo.keySet().toArray(new BigDecimal[pointsCombo.keySet().size()]));    	
    	
    	// Se da valor inicial al filtro "previo" para luego poder saber si los filtros han ido cambiando.
    	prevFilters = new AllocationQueryFilter(tmpFilter);
		
		return tmpFilter;
	}
	
	// Para los elementos del combo del filtro de shippers.
	public Map<BigDecimal, Object> getShipperIds() {
		return service.selectShipperId();
	}	
	
	// Para los elementos del combo del filtro de contratos.
	public Map<BigDecimal, Object> getContractIds() {
		return service.selectContractId(filters);
	}

	// Aqui no hace falta actualizar el filtro de zonas porque las etiquetas de los combos no se reducen 
	// por haber cambiado un filtro anterior.
	public Map<BigDecimal, Object> getZones(String systemCode) {

		if(zonesCombo==null)
			zonesCombo = service.selectZones(systemCode);
		
		// Se comprueba si el filtro de areas ha cambiado.
		setFilteredZonesChanged();
		
		// Se actualiza el filtro de zonas previo.
		if(filters!=null){
			BigDecimal[] tmpZoneIds = new BigDecimal[filters.getZoneIds().length];
			System.arraycopy( filters.getZoneIds(), 0, tmpZoneIds, 0, filters.getZoneIds().length );
			prevFilters.setZoneIds(tmpZoneIds);
		}
		
		return zonesCombo;
	}

	// Cuando en los combos se consulten las etiquetas a mostrar, se actualizan tambien los filtros.
	// En el filtro se mantienen los valores, quitando aquellos que desaparezcan de las etiquetas del combo.
	// De este modo se actualizan en cascada los valores de los combos (que van leyendo de filtros).
	public Map<BigDecimal, Object> getAreas() {
		//return service.selectAreas(filters);			//Esta era la consulta a BD inicial
				
		if(areasCombo==null || getFilteredZonesChanged()) {
			
			areasCombo = service.selectAreas(filters);
			BigDecimal[] bdaOldAreas = filters.getAreaIds();
			
			BigDecimal[] bdaNewAreasCombo = areasCombo.keySet().toArray(new BigDecimal[areasCombo.keySet().size()]);
			ArrayList<BigDecimal> alNewAreasFilter = new ArrayList<BigDecimal>();
			for(int i=0; i<bdaNewAreasCombo.length; i++){
				old_areas_loop:
				for(int j=0; j<bdaOldAreas.length; j++){
					if(bdaNewAreasCombo[i].compareTo(bdaOldAreas[j])==0){
						alNewAreasFilter.add(bdaOldAreas[j]);
						continue old_areas_loop;
					}
				}
			}
			
			filters.setAreaIds(alNewAreasFilter.toArray(new BigDecimal[alNewAreasFilter.size()]));
		}
		
		// Se comprueba si el filtro de areas ha cambiado.
		setFilteredAreasChanged();
		
		// Se actualiza el filtro de areas previo.
		if(filters!=null){
			BigDecimal[] tmpAreaIds = new BigDecimal[filters.getAreaIds().length];
			System.arraycopy( filters.getAreaIds(), 0, tmpAreaIds, 0, filters.getAreaIds().length );
			prevFilters.setAreaIds(tmpAreaIds);
		}
		
		return areasCombo;
	}

	// Cuando en los combos se consulten las etiquetas a mostrar, se actualizan tambien los filtros.
	// En el filtro se mantienen los valores, quitando aquellos que desaparezcan de las etiquetas del combo.
	// De este modo se actualizan en cascada los valores de los combos (que van leyendo de filtros).
	public Map<BigDecimal, Object> getPointIds() {
		//return service.selectPointId(filters);			//Esta era la consulta a BD inicial
		
		if(pointsCombo==null || getFilteredAreasChanged()) {			

			pointsCombo = service.selectPointId(filters);
			BigDecimal[] bdaOldPoints = filters.getNomPointIds();
			
			BigDecimal[] bdaNewPointsCombo = pointsCombo.keySet().toArray(new BigDecimal[pointsCombo.keySet().size()]);
			ArrayList<BigDecimal> alNewPointsFilter = new ArrayList<BigDecimal>();
			for(int i=0; i<bdaNewPointsCombo.length; i++){
				old_points_loop:
				for(int j=0; j<bdaOldPoints.length; j++){
					if(bdaNewPointsCombo[i].compareTo(bdaOldPoints[j])==0){
						alNewPointsFilter.add(bdaOldPoints[j]);
						continue old_points_loop;
					}
				}
			}
			
			filters.setNomPointIds(alNewPointsFilter.toArray(new BigDecimal[alNewPointsFilter.size()]));
		}
		
		// No se actualiza el filtro previo porque no hay un combo posterior afectado por este filtro en el que 
		// se necesite saber si ha cambiado el filtro.
		//setFilteredPointsChanged();
		//if(filters!=null){
		//	BigDecimal[] tmpPointIds = new BigDecimal[filters.getSystemPointId().length];
		//	System.arraycopy( filters.getSystemPointId(), 0, tmpPointIds, 0, filters.getSystemPointId().length );
		//	prevFilters.setSystemPointId(tmpPointIds);
		//}
		
		// En cualquier caso, tras haber actualizado, el combo si ha sido necesario, no hay que combos posteriores y ya se puede hacer Search.
		enableSearch();
		
		return pointsCombo;
	}	
	
	private void setFilteredZonesChanged(){
		filteredZonesChanged = checkFilteredZonesChanged();
	}
	// En cuanto se consulta una vez la variable se resetea.
	private boolean getFilteredZonesChanged(){
		boolean res = filteredZonesChanged;
		// se resetea la variable.
		filteredZonesChanged = false;
		return res;
	}
	private void setFilteredAreasChanged(){
		filteredAreasChanged = checkFilteredAreasChanged();
	}
	// En cuanto se consulta una vez la variable se resetea.
	private boolean getFilteredAreasChanged(){
		boolean res = filteredAreasChanged;
		// se resetea la variable.
		filteredAreasChanged = false;
		return res;
	}
	
	private boolean checkFilteredZonesChanged(){
		return checkArraysChanged(prevFilters.getZoneIds(), filters.getZoneIds());
	}

	private boolean checkFilteredAreasChanged(){
		return checkArraysChanged(prevFilters.getAreaIds(), filters.getAreaIds());
	}
	
	private boolean checkArraysChanged(BigDecimal[] prevIds, BigDecimal[] currIds){
	
		if(prevIds==null && currIds==null)
			return false;
		
		if((prevIds==null && currIds!=null) ||
			(prevIds!=null && currIds==null))
			return true;
		
		if(prevIds.length != currIds.length)
			return true;
		
		for(int i=0; i<prevIds.length; i++)
			if(prevIds[i].compareTo(currIds[i])!=0)
				return true;
		
		return false;
	}
	
	private void disableSearch(){
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("disableSearch();");
	}
	
	private void enableSearch(){
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("enableSearch();");
	}
	
	public void onSearch(){
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	if(filters.getGasDay()==null){
    		getMessages().addMessage(Constants.head_menu[8],
	    			new MessageBean(Constants.ERROR,
	    					msgs.getString("validation_error"), 
							msgs.getString("all_rev_gas_day_empty_error"), 
							Calendar.getInstance().getTime()));
	    	log.error("Gas Day must be selected.");
	    	return;
    	}
    	
    	if((filters.getGeneratedFrom()!=null) && (filters.getGeneratedTo()!=null)){
    		if(filters.getGeneratedFrom().after(filters.getGeneratedTo())){
    	    	getMessages().addMessage(Constants.head_menu[8],
    	    			new MessageBean(Constants.ERROR,
    	    					msgs.getString("validation_error"), 
    							msgs.getString("from_must_previous_equal"), 
    							Calendar.getInstance().getTime()));
    	    	log.error("From date should be previous or equal to To date.");
    	    	return;
    		}
    	}
    	
        items = service.search(filters);
    }

	public void onClear(){
		filters = initFiltersCombos(allocationMaxDateOffset, factorFromDefaultUnit);
	  	
        if (items != null) {
            items.clear();
        }
        items = service.search(filters);    
    }
    
    public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
}
