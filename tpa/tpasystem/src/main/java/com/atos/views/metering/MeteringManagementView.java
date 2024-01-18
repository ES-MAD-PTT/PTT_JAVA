package com.atos.views.metering;

import java.awt.Color;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.FileBean;
import com.atos.beans.MessageBean;
import com.atos.beans.metering.MeasureGasQualityParamBean;
import com.atos.beans.metering.MeasurementBean;
import com.atos.beans.metering.PointDto;
import com.atos.exceptions.ValidationException;
import com.atos.filters.metering.MeteringManagementFilter;
import com.atos.services.allocation.AllocationIntradayService;
import com.atos.services.allocation.AllocationManagementService;
import com.atos.services.metering.MeteringManagementService;
import com.atos.utils.Constants;
import com.atos.utils.DateUtil;
import com.atos.views.CommonView;

@ManagedBean(name="MetManagementView")
@ViewScoped
public class MeteringManagementView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4836870871150966259L;

	private MeteringManagementFilter filters;
	// En los combos encadenados, al hacerse algun cambio se puede llegar a hacer la misma consulta a BD varias veces.
	// En esta variable se guarda el valor de los ultimos filtros. Si no han cambiado, no se repiten las consultas.
	private MeteringManagementFilter prevFilters;		// Previous filters.
	// Variables de control para almacenar si alguno de los combos ha cambiado. Se resetean al consultar el get la primera vez.
	private boolean dateChanged = false;
	private boolean filteredZonesChanged = false;
	private boolean filteredAreasChanged = false;
	private boolean filteredPointsChanged = false;
	// Se guarda el combo/lista de zonas para que no se consulte continuamente la BD, cuando se actualice el filtro.
	// Con esto, si se añade una zona a un sistema (onshore/offshore), no se
	// actualizaria el filtro hasta que se cargue otra vez la vista.
	// (Al cambiar de sistema de la pantalla, se carga la vista completa).
	private Map<BigDecimal, Object> zonesCombo = null;
	private Map<BigDecimal, Object> areasCombo = null;
	private Map<BigDecimal, Object> pointsCombo = null;
	private Map<BigDecimal, Object> codesCombo = null;
	
	// Filtro para la actualizacion de medidas, utilizando el webservice. No se usa la lista de puntos en este filtro.
	private MeteringManagementFilter updateMetsfilters;
	private List<MeasurementBean> items;
	private Date openPeriodFistDay;
	private StreamedContent templateFile =  null;
	
	private static final Logger log = LogManager.getLogger("com.atos.views.metering.MeteringManagementView");

	private Integer allocationMaxDateOffset = null;

	private Date checkDate;
	private List<PointDto> checkedPoints;
	
	@ManagedProperty("#{MetManagementService}")
    transient private MeteringManagementService service;
    
	public void setService(MeteringManagementService service) {
		this.service = service;
	}
	
	@ManagedProperty("#{AllocationManagService}")
	transient private AllocationManagementService e_service;
    
	public void setE_service(AllocationManagementService service) {
		this.e_service = service;
	}

	@ManagedProperty("#{AllocationIntradayService}")
    transient private AllocationIntradayService i_service;
	
	public void setService(AllocationIntradayService i_service) {
		this.i_service = i_service;
	}
	private Date responsePeriodStartDate = null;

	public Date getResponsePeriodStartDate() {
		return responsePeriodStartDate;
	}

	public void setResponsePeriodStartDate(Date responsePeriodStartDate) {
		this.responsePeriodStartDate = responsePeriodStartDate;
	}


	//geters/seters
	public MeteringManagementFilter getFilters() {
		return filters;
	}

	public void setFilters(MeteringManagementFilter filters) {
		this.filters = filters;
	}

	public Date getLastOKMeteringInputDate(){
		return service.selectLastOKMeteringInputDate();
	}
	
	public MeteringManagementFilter getUpdateMetsfilters() {
		return updateMetsfilters;
	}

	public void setUpdateMetsfilters(MeteringManagementFilter updateMetsfilters) {
		this.updateMetsfilters = updateMetsfilters;
	}
	
	public List<MeasurementBean> getItems() {
		return items;
	}

	public void setItems(List<MeasurementBean> items) {
		this.items = items;
	}

	// Aqui no hace falta actualizar el filtro de zonas porque las etiquetas de los combos no se reducen 
	// por haber cambiado un filtro anterior.
	public Map<BigDecimal, Object> getZones(String systemCode) {
		if(zonesCombo==null)
			zonesCombo = service.selectZones(systemCode);
		
		// Se comprueba si el filtro de zonas ha cambiado.
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
	public Map<BigDecimal, Object> getMeteringPoints() {
		//return service.selectMeteringPoints(filters);			//Esta era la consulta a BD inicial
		
		if(pointsCombo==null || getFilteredAreasChanged()) {			

			pointsCombo = service.selectMeteringPoints(filters);
			BigDecimal[] bdaOldPoints = filters.getSystemPointId();
			
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
			
			filters.setSystemPointId(alNewPointsFilter.toArray(new BigDecimal[alNewPointsFilter.size()]));
		}
		
		// Se comprueba si el filtro de puntos ha cambiado.
		setFilteredPointsChanged();
		
		// Se actualiza el filtro de puntos previo.
		if(filters!=null){
			BigDecimal[] tmpPointIds = new BigDecimal[filters.getSystemPointId().length];
			System.arraycopy( filters.getSystemPointId(), 0, tmpPointIds, 0, filters.getSystemPointId().length );
			prevFilters.setSystemPointId(tmpPointIds);
		}
		
		return pointsCombo;
	}

	// Cuando en los combos se consulten las etiquetas a mostrar, se actualizan tambien los filtros.
	// En este caso se comprueba si el meteringInputCode escogido forma parte de los valores posibles del combo.
	// Si no es asi se deja a nulo, significando que se va a extraer la ultima version.
	// De este modo se actualizan en cascada los valores de los combos (que van leyendo de filtros).
	public Map<BigDecimal, Object> getMeteringInputCodes() {
		//return service.selectMeteringInputCodes(filters);			//Esta era la consulta a BD inicial

		if(codesCombo==null || getFilteredPointsChanged() || isDateChanged()) {
	
			codesCombo = service.selectMeteringInputCodes(filters);
			BigDecimal bdOldInputCode = filters.getMeteringInputId();
			
			if(bdOldInputCode!=null){
				BigDecimal[] bdaNewInputCodesCombo = codesCombo.keySet().toArray(new BigDecimal[codesCombo.keySet().size()]);
				BigDecimal bdNewInputCode = null;
				for(int i=0; i<bdaNewInputCodesCombo.length; i++){
					if(bdaNewInputCodesCombo[i].compareTo(bdOldInputCode)==0)
						bdNewInputCode = bdOldInputCode;
				}		
				
				// Si tras comprobar todos los valores del combo, no coincide con ninguno del que hay escogido en el filtro, hasta ese momento,
				// entonces de deja el valor del filtro a nulo, significando que se va a extraer la ultima version.
				filters.setMeteringInputId(bdNewInputCode);
			}
			
			// Una vez cargado los codigos de carga de medidas, desactivo el flag de fechas. 
			setDateChanged(false);
		}
		
		// No se actualiza el filtro previo porque no hay un combo posterior afectado por este filtro en el que 
		// se necesite saber si ha cambiado el filtro.
		//prevFilters.setMeteringInputId(bdNewInputCode);
		
		// En cualquier caso, tras haber actualizado, el combo si ha sido necesario, no hay que combos posteriores y ya se puede hacer Search.
		enableSearch();
		
		return codesCombo;
	}

	public boolean isDateChanged() {
		return dateChanged;
	}

	public void setDateChanged(boolean dateChanged) {
		this.dateChanged = dateChanged;
	}

	public void onDateChanged() {
		setDateChanged(true);
	}

	// Devuelvo el boolean porque en el momento de comprobar el filtro de zonas, me interesa saber si ha cambiado o no.
	private boolean setFilteredZonesChanged(){
		filteredZonesChanged = checkFilteredZonesChanged();
		return filteredZonesChanged;
	}
	
	// En cuanto se consulta una vez la variable se resetea.
	private boolean getFilteredZonesChanged(){
		boolean res = filteredZonesChanged;
		// se resetea la variable.
		filteredZonesChanged = false;
		return res;
	}
	
	private boolean setFilteredAreasChanged(){
		filteredAreasChanged = checkFilteredAreasChanged();
		return filteredAreasChanged;
	}
	
	// En cuanto se consulta una vez la variable se resetea.
	private boolean getFilteredAreasChanged(){
		boolean res = filteredAreasChanged;
		// se resetea la variable.
		filteredAreasChanged = false;
		return res;
	}
	
	private boolean setFilteredPointsChanged(){
		filteredPointsChanged = checkFilteredPointsChanged();
		return filteredPointsChanged;
	}
	
	// En cuanto se consulta una vez la variable se resetea.
	private boolean getFilteredPointsChanged(){
		boolean res = filteredPointsChanged;
		// se resetea la variable.
		filteredPointsChanged = false;
		return res;
	}
	
	private boolean checkFilteredZonesChanged(){
		return checkArraysChanged(prevFilters.getZoneIds(), filters.getZoneIds());
	}

	private boolean checkFilteredAreasChanged(){
		return checkArraysChanged(prevFilters.getAreaIds(), filters.getAreaIds());
	}
	
	private boolean checkFilteredPointsChanged(){
		return checkArraysChanged(prevFilters.getSystemPointId(), filters.getSystemPointId());
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
	
	public StreamedContent getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(StreamedContent templateFile) {
		this.templateFile = templateFile;
	}
	
	
	@PostConstruct
    public void init() {
    	
		initFiltersCombos();
		
    	updateMetsfilters = new MeteringManagementFilter();
    	
    	//Task 600066 La fecha from por defecto solicitan sea D-1 (ayer),
		//updateMetsfilters.setGasDayFrom(DateUtil.getToday());
		Calendar ayer = Calendar.getInstance();
		ayer.add(Calendar.DAY_OF_YEAR,-1);	
		updateMetsfilters.setGasDayFrom(ayer.getTime());
	
		
		try{
    		allocationMaxDateOffset = i_service.selectAllocationMaxDateOffset(getUser().getUsername(), getLanguage().getLocale());
		}
		catch(Exception e){
			log.error(e.getMessage(), e);
		}
		
		setResponsePeriod(allocationMaxDateOffset);

    	updateMetsfilters.setGasDayTo(Calendar.getInstance().getTime());
    	
    	// En el inicio no hace falta validar las fechas porque se han inicializado con valores coherentes.
        items = service.search(filters);
	}
	
	private void initFiltersCombos(){
		
    	filters = new MeteringManagementFilter();

    	// Primer dia de balance abierto.
    	openPeriodFistDay = service.selectOpenPeriodFirstDay();
		filters.setGasDayFrom(DateUtil.getToday());

		Calendar calMaxToDate = Calendar.getInstance();
		calMaxToDate.set(Calendar.HOUR_OF_DAY, 0);
		calMaxToDate.set(Calendar.MINUTE, 0);
		calMaxToDate.set(Calendar.SECOND, 0);
		calMaxToDate.set(Calendar.MILLISECOND, 0);
		// Se descuenta el offset, si lo hubiera.
		try {
			calMaxToDate.add(Calendar.DAY_OF_MONTH,
					(-1) * service.getMeteringMaxDateOffset(getUser().getUsername(), getLanguage().getLocale()));
		} catch (Exception e) { // Si saltara una excepcion, solo se pintaria
								// mal el filtro. Luego al solicitar datos,
								// volveria a fallar
			log.error(e.getMessage(), e);
		}
		filters.setGasDayTo(calMaxToDate.getTime());
		checkDate = calMaxToDate.getTime();

		zonesCombo = service.selectZones(getChangeSystemView().getSystem());
    	// Se rellena el array de puntos del filtro, transformando en array el conjunto de keys, 
		// especificando como clase un array de BigDecimal del tamaño adecuado.
    	filters.setZoneIds(zonesCombo.keySet().toArray(new BigDecimal[zonesCombo.keySet().size()]));
    	
    	areasCombo = service.selectAreas(filters);
    	filters.setAreaIds(areasCombo.keySet().toArray(new BigDecimal[areasCombo.keySet().size()]));
    	
    	pointsCombo = service.selectMeteringPoints(filters);
    	filters.setSystemPointId(pointsCombo.keySet().toArray(new BigDecimal[pointsCombo.keySet().size()]));
    	
    	codesCombo = service.selectMeteringInputCodes(filters);
    	// El valor del filtro por defecto de inputCodes es nulo, asi que no se actualiza.
    	
		filters.setIdn_user(super.getUser().getIdn_user());
		filters.setType_code(super.getUser().getUser_type());
    	
    	// Se da valor inicial al filtro "previo" para luego poder saber si los filtros han ido cambiando.
    	prevFilters = new MeteringManagementFilter(filters);
	}

	public void onCheck() {
		if (checkDate == null) {

			ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
					.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
			getMessages().addMessage(Constants.head_menu[7],
					new MessageBean(Constants.ERROR, msgs.getString("menu_metering_management"),
							msgs.getString("all_rev_gas_day_empty_error"), Calendar.getInstance().getTime()));
			return;
		}
		checkedPoints = service.getCheckedPoints(checkDate);
	}

	public void exportChecks(Object document) {
		XSSFWorkbook wb = (XSSFWorkbook) document;
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow header = sheet.getRow(0);

		// Generate fonts
		XSSFFont headerFont = createFont(wb, Color.BLACK, (short) 12, true);
		XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
		// Generate styles
		DataFormat format = wb.createDataFormat();

		XSSFCellStyle cellStyleHeader = createStyle(wb, headerFont, XSSFCellStyle.ALIGN_CENTER, Color.cyan.darker(),
				true, Color.BLACK, XSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		XSSFCellStyle cellStyleText = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##"));

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

					cell.setCellStyle(cellStyleText);
				}
			}

			for (j = 0; j < sheet.getRow(0).getPhysicalNumberOfCells(); j++) {
				sheet.autoSizeColumn(j, true);
			}
		} catch (Exception e) {

			ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
					.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
			getMessages().addMessage(Constants.head_menu[7], new MessageBean(Constants.ERROR,
					msgs.getString("internal_error"), "error exportig data", Calendar.getInstance().getTime()));
			log.error("error exporting data.", e);
			e.printStackTrace();
		}
	}
	public void onSearch(){
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		if((filters.getGasDayFrom()==null) || (filters.getGasDayTo()==null)){
			getMessages().addMessage(Constants.head_menu[7],
	    			new MessageBean(Constants.ERROR,
	    					msgs.getString("menu_metering_management"), 
	    					msgs.getString("met_man_required_from_to_error"), 
							Calendar.getInstance().getTime()));
	    	log.error("From field cannot be empty.");
	    	return;
    	}
    	
    	if((filters.getGasDayFrom()!=null) && (filters.getGasDayTo()!=null)){
    		if(filters.getGasDayFrom().after(filters.getGasDayTo())){
    			getMessages().addMessage(Constants.head_menu[7],
    	    			new MessageBean(Constants.ERROR,
    	    					msgs.getString("menu_metering_management"), 
    							msgs.getString("from_must_earlier_equal"), 
    							Calendar.getInstance().getTime()));
    	    	log.error("From date must be previous or equal to To date.");
    	    	return;
    		}
    	}
    	
        items = service.search(filters);
	}
	
	public void onClear(){
		initFiltersCombos();
    	
		Calendar ayer = Calendar.getInstance();
		ayer.add(Calendar.DAY_OF_YEAR, -1);
		updateMetsfilters.setGasDayFrom(ayer.getTime());
		// updateMetsfilters.setGasDayFrom(openPeriodFistDay);
    	updateMetsfilters.setGasDayTo(Calendar.getInstance().getTime());
    	
    	// Se actualiza la vista. No hace falta validacion de parametros porque se han especificado aqui directamente.
        items = service.search(filters);     
    }
	
	public void onExecute(){
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	String summaryMsg = msgs.getString("met_man_execution_dialog_header");
    	String errorMsg = null;
    	
		try {
			service.updateMeasurementsFromWebservice(updateMetsfilters, getUser(), getLanguage());
			
	    	String okMsg = msgs.getString("met_man_processing");
	    	getMessages().addMessage(Constants.head_menu[7],
					new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
	    	log.info(okMsg);
		} 
		catch(ValidationException ve){
			errorMsg = ve.getMessage();
			getMessages().addMessage(Constants.head_menu[7],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));    		
		}
		catch (Exception e) {
			errorMsg = msgs.getString("internal_error");
			getMessages().addMessage(Constants.head_menu[7],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			// Se guarda el detalle del error tecnico.
	    	log.error(e.getMessage(), e);			
		}
    	
		// Si se ejectua el proceso en background, no tiene sentido actualizar los datos.
        // onSearch();
		
		// llamamos a acumInventory, baseInventory e intraday
		try {
			service.updateWebservice(responsePeriodStartDate, responsePeriodStartDate, getUser(), getLanguage(),
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
			getMessages().addMessage(Constants.head_menu[7],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
		}

		try {
			service.updateMeasurementsFromFile(fbUploadedFile, getUser(), getLanguage(), getChangeSystemView());
			summaryMsg = msgs.getString("data_stored");
			okMessage = msgs.getString("met_man_processing");
			getMessages().addMessage(Constants.head_menu[7],
					new MessageBean(Constants.INFO, summaryMsg, okMessage, Calendar.getInstance().getTime()));
				
		} 
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = ve.getMessage();
    		getMessages().addMessage(Constants.head_menu[7],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));		
		}
		catch (Exception e) {
			summaryMsg = msgs.getString("saving_data_error");
    		errorMsg = msgs.getString("met_man_sub_data_not_stored") + " " + msgs.getString("internal_error");
    		getMessages().addMessage(Constants.head_menu[7],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
    		// Se guarda el detalle del error tecnico.
	    	log.error(e.getMessage(), e);			
		}
		
		// Si se ejectua el proceso en background, no tiene sentido actualizar los datos.
        // onSearch();
    }
	
    public void selectTemplateFile() {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
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
		}
	}
    
	public void onRowToggle(ToggleEvent event) {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	String summaryMsg = msgs.getString("met_man_fetching_gas_params");
    	String errorMsg = null;
    	
		try {
			MeasurementBean item = ((MeasurementBean) event.getData());
			item.setGasParams(service.selectGasQualityParams(item));
		} 
		catch (Exception e) {
			errorMsg = msgs.getString("internal_error");
			getMessages().addMessage(Constants.head_menu[7],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			// Se guarda el detalle del error tecnico.
	    	log.error(e.getMessage(), e);			
		}    
	}
	
	public void postProcessXLS(Object document) {
		XSSFWorkbook wb = (XSSFWorkbook) document;
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow header = sheet.getRow(0);
        // Para convertir las cantidades string en double.
        NumberFormat nf = NumberFormat.getInstance(new Locale(getLanguage().getLocale()));

		// Generate fonts
        XSSFFont headerFont  = createFont(wb, Color.BLACK, (short)12, true);
        XSSFFont contentFont = createFont(wb, Color.BLACK, (short)10, false);
		// Generate styles
		DataFormat format = wb.createDataFormat();
		
		XSSFCellStyle cellStyleHeader  =  createStyle(wb, headerFont,  XSSFCellStyle.ALIGN_CENTER, Color.cyan.darker(), true, Color.BLACK, XSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		XSSFCellStyle cellStyleHeaderGas= createStyle(wb, headerFont,  XSSFCellStyle.ALIGN_CENTER, Color.lightGray,		true, Color.BLACK, XSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		XSSFCellStyle cellStyleText  =    createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT,   Color.WHITE,         true, Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##"));
		XSSFCellStyle cellStyleFourDec = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT,  Color.WHITE,         true, Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.0000"));
		XSSFCellStyle cellStyleThreeDec = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT,  Color.WHITE,         true, Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.000"));
		XSSFCellStyle cellStyleTwoDec =   createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT,  Color.WHITE,         true, Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.00"));
		
		// Se oculta la primera columna.
        sheet.setColumnHidden(0, true);
		
		String tmpStrValue = null;
		int i = -1;  	// Filas
		int j = -1;		// Columnas

		try{
			// Cabecera i=0
			completeGasParamsHeaders(header);
	        for (j = 0; j < header.getPhysicalNumberOfCells(); j++) {
				XSSFCell cell = header.getCell(j);
    			if(j<9)
    				cell.setCellStyle(cellStyleHeader);
    			else
    				cell.setCellStyle(cellStyleHeaderGas);			
			}
			
	        // Resto tabla
	        for(i=1;i<sheet.getPhysicalNumberOfRows();i++){

	        	XSSFRow row = sheet.getRow(i);
				// Se añaden a la derecha los datos de calidad de gas que se
				// hayan consultado.
	        	// El entero corresponde al indice en la lista de valores que hay que copiar a la fila de la excel. 
	        	completeGasParamsValues(i-1, row);
	        	
	        	for(j=0;j<row.getPhysicalNumberOfCells();j++){
	        		XSSFCell cell = row.getCell(j);
		        	
    	        	// Se borran los valores de la primera columna.
    	        	if(j==0) {
    	        		cell.setCellValue("");
    	        	}
    	        	
					if ( j < 6 || j == 9 || j == 10 || j == 11 || j==12 ) { // Estas
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
						cell.setCellType(Cell.CELL_TYPE_STRING);
		        	}
		        	else {

		      /*  		if (j == 6)			// Flow - volume
	        				cell.setCellStyle(cellStyleFourDec);
	        			else if (j == 8)	// Energy
	        				cell.setCellStyle(cellStyleTwoDec);
	        			else if(j == 30)	// Mercury Hg
	        				cell.setCellStyle(cellStyleFourDec);
	        			else*/
	        				cell.setCellStyle(cellStyleThreeDec);
	        			
		        		if(cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {
		        		
		        			tmpStrValue = cell.getStringCellValue();
		        			if(tmpStrValue != null && (!"".equalsIgnoreCase(tmpStrValue))) {
			        			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			        			
			        			try {
			        				cell.setCellValue(nf.parse(tmpStrValue).doubleValue());
			        			}
			        			catch(Exception e){
			        				log.catching(e);
			        			}
		
			        			// Se resetea la variable temporal.
			        			tmpStrValue = null;
		        			} 
		        		}
	        		}

		        	// Se hace abajo, porque consume tiempo.
		        	//sheet.autoSizeColumn(j, true);
	        	}
	        }
	        
	        // Se supone que todas las filas tienen el mismo numero de columnas.
			// Se actualiza el final el tamaño de las columnas porque consumiria
			// mucho tiempo si se hiciera fila por fila.
        	for(j=0;j<sheet.getRow(0).getPhysicalNumberOfCells();j++){
        		sheet.autoSizeColumn(j, true);
        	}
        
		} catch (Exception e) {
			log.error("Error in postProcessXLS. Row: " + i + ",Column: " + j);
			log.catching(e);
		}		
    }

	// Busca cualquier lista de parametros de gas de cualquier registro de medida.
	private void completeGasParamsHeaders(XSSFRow headerRow){
		
		if(items==null)
			return;
		
		MeasurementBean met = null;
		// Por si alguna medida no tuviera datos de calidad de gas, se cogen los nombres de los parametros a partir de la primera medida disponible.
		for(int i = 0; i<items.size(); i++){
			met = items.get(i);
			if(met != null) {
				List<MeasureGasQualityParamBean> gasParams = met.getGasParams();
				if(gasParams==null || gasParams.size()==0)
					continue;								// Si la medida no tuviera parametros de gas, se busca otra medida.
				
				int nextCellId = headerRow.getPhysicalNumberOfCells();
				for(MeasureGasQualityParamBean gasParam : gasParams){
					String value = gasParam.getParamDesc() + " (" + gasParam.getUnitDesc() + ")";
					
					XSSFCell cell = headerRow.createCell(nextCellId++);
					cell.setCellValue(value);
				}
				
				// Tras haber anadido las cabeceras a partir de una de las filas, ya no hay que recorrer mas. 
				break;
			}
		}
	}
	
	private void completeGasParamsValues(int metId, XSSFRow row){
		
		if(items==null)
			return;
		
		MeasurementBean met = items.get(metId);
		if(met == null)
			return;
		
		List<MeasureGasQualityParamBean> gasParams = met.getGasParams();
		if(gasParams==null || gasParams.size()==0)
			return;
		
		int nextCellId = row.getPhysicalNumberOfCells();
		for(MeasureGasQualityParamBean gasParam : gasParams){
			BigDecimal value = gasParam.getValue();
			
			XSSFCell cell = row.createCell(nextCellId++);
			if(value!=null)
				cell.setCellValue(value.doubleValue());
			else
				cell.setCellValue("");
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
	
	private XSSFCellStyle createStyle(XSSFWorkbook wb, XSSFFont font, short cellAlign, Color cellColor, boolean cellBorder, Color cellBorderColor, short border, 
			boolean numberFormat,short dataFormat) {

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

		if(numberFormat){
			style.setDataFormat(dataFormat);
		}

		return style;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public List<PointDto> getCheckedPoints() {
		return checkedPoints;
	}

	public void setCheckedPoints(List<PointDto> checkedPoints) {
		this.checkedPoints = checkedPoints;
	}
	
	 public int getItemsSize() { 
			if(this.items!=null && !this.items.isEmpty()){
				return this.items.size();
			}else{
				return 0;
			}
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
		
	 
}
