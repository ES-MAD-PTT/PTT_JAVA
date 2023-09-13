package com.atos.views.allocation;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.allocation.AllocationBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.allocation.AllocationReviewFilter;
import com.atos.services.MailService;
import com.atos.services.allocation.AllocationReviewService;
import com.atos.services.balancing.BalanceManagementService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="AllocationReviewView")
@ViewScoped
public class AllocationReviewView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5980784834639303828L;
	private static final String EnergyUnit = "MMBTU/D";					// Unidad de medida de la pagina.
	private BigDecimal factorFromDefaultUnit = null;
	private BigDecimal factorToDefaultUnit = null;
	
	private AllocationReviewFilter filters;
	// En los combos encadenados, al hacerse algun cambio se puede llegar a hacer la misma consulta a BD varias veces.
	// En esta variable se guarda el valor de los ultimos filtros. Si no han cambiado, no se repiten las consultas.
	private AllocationReviewFilter prevFilters;		// Previous filters.
	// Variables de control para almacenar si alguno de los combos ha cambiado. Se resetean al consultar el get la primera vez.
	private boolean filteredZonesChanged = false;
	private boolean filteredAreasChanged = false;
	// Se guarda el combo/lista de zonas para que no se consulte continuamente la BD, cuando se actualice el filtro.
	// Con esto, si se añade una zona a un sistema (onshore/offshore), no se
	// actualizaria el filtro hasta que se cargue otra vez la vista.
	// (Al cambiar de sistema de la pantalla, se carga la vista completa).
	private Map<BigDecimal, Object> zonesCombo = null;
	private Map<BigDecimal, Object> areasCombo = null;
	private Map<BigDecimal, Object> pointsCombo = null;

	private List<AllocationBean> items;
	private AllocationBean selected;
	// Por rendimiento, openPeriodFistDay y allocationReviewOpenDays, solo se consultan 1 vez durante la vista.
	// ambos se comprobaran antes de guardar datos de reviews.
	private Date openPeriodFirstDay = null;	
	private Integer allocationReviewOpenDays = null;
	private Integer allocationMaxDateOffset = null;
	private Date reviewPeriodStartDate = null;
	private Date reviewPeriodEndDate = null;
	private static final Logger log = LogManager.getLogger("com.atos.views.allocation.AllocationReviewView");
	
	
	@ManagedProperty("#{AllocationReviewService}")
    transient private AllocationReviewService service;

	@ManagedProperty("#{BalanceManagService}")
	transient private BalanceManagementService balMagService;
    
	public void setService(AllocationReviewService service) {
		this.service = service;
	}
	
	@ManagedProperty("#{mailService}")
    transient private MailService mailService;

    public void setMailService(MailService mailService) {
    	this.mailService = mailService;
    }
	
	//geters/seters
	public AllocationReviewFilter getFilters() {
		return filters;
	}

	public void setFilters(AllocationReviewFilter filters) {
		this.filters = filters;
	}
	
	public List<AllocationBean> getItems() {
		return items;
	}

	public void setItems(List<AllocationBean> items) {
		this.items = items;
	}

	public AllocationBean getSelected() {
		return selected;
	}

	public void setSelected(AllocationBean selected) {
		this.selected = selected;
	}

	// Este booleano marcara si esta habilitado el filtro de shipper (isOperator = true) o esta deshabilitado, 
	// con un shipper fijo.
	public boolean getIsShipper() {
		return getUser().isUser_type(Constants.SHIPPER);
	}
	
	@PostConstruct
    public void init() {
		factorFromDefaultUnit = service.selectFactorFromDefaultUnit(EnergyUnit);
		factorToDefaultUnit = service.selectFactorToDefaultUnit(EnergyUnit);
    	try{
    		allocationMaxDateOffset = service.selectAllocationMaxDateOffset(getUser().getUsername(), getLanguage().getLocale());
		}
		catch(Exception e){
			log.error(e.getMessage(), e);
		}
		setReviewPeriod();
		filters = initFiltersCombos(allocationMaxDateOffset, factorFromDefaultUnit);
        items = service.search(filters);
		selected = new AllocationBean();
	}

	private AllocationReviewFilter initFiltersCombos(Integer _allocationMaxDateOffset, BigDecimal _factorFromDefaultUnit){
		AllocationReviewFilter tmpFilter = new AllocationReviewFilter();
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
		tmpFilter.setStatusCode(new String[]{Constants.NOT_REVIEWED,
											Constants.INITIAL,
											Constants.ACCEPTED,
											Constants.REJECTED,
											Constants.ALLOCATED});
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
    	prevFilters = new AllocationReviewFilter(tmpFilter);
		
		return tmpFilter;
	}
	
	// periodo [max(Hoy - ALLOCATION.REVIEW.OPEN.DAYS, primer_dia_abierto); Hoy], 
	// donde ALLOCATION.REVIEW.OPEN.DAYS es el parámetro de sistema
	private void setReviewPeriod(){
		// Primer dia de balance abierto.

		HashMap<String, Object> params = new HashMap<>();
		params.put("closingTypeCode", "DEFINITIVE");
		params.put("idnSystem", getChangeSystemView().getIdn_active());
		params.put("sysCode", getChangeSystemView().getSystem());
		openPeriodFirstDay = balMagService.selectOpenPeriodFirstDay(params);
    	try{
			allocationReviewOpenDays = service.selectAllocationReviewOpenDays(getUser().getUsername(),
					getLanguage().getLocale(), getChangeSystemView().getSystem());
		}
		catch(Exception e){
			log.error(e.getMessage(), e);
		}
    	
		Calendar tmpToday = Calendar.getInstance();
		tmpToday.set(Calendar.HOUR_OF_DAY, 0);
		tmpToday.set(Calendar.MINUTE, 0);
		tmpToday.set(Calendar.SECOND, 0);
		tmpToday.set(Calendar.MILLISECOND, 0);
		
		Calendar tmpStartDate = Calendar.getInstance();
		tmpStartDate.set(Calendar.HOUR_OF_DAY, 0);
		tmpStartDate.set(Calendar.MINUTE, 0);
		tmpStartDate.set(Calendar.SECOND, 0);
		tmpStartDate.set(Calendar.MILLISECOND, 0);
		tmpStartDate.add(Calendar.DAY_OF_MONTH, allocationReviewOpenDays * (-1));
		
		if(tmpStartDate.getTime().after(openPeriodFirstDay))
			reviewPeriodStartDate = tmpStartDate.getTime();
		else
			reviewPeriodStartDate = openPeriodFirstDay;
		
		reviewPeriodEndDate = tmpToday.getTime();
	}
	
	private boolean inReviewPeriod(Date gasDay){
		return (reviewPeriodStartDate.compareTo(gasDay)<=0 && 
				reviewPeriodEndDate.compareTo(gasDay)>=0); 
	}
	
	public boolean mayBeReviewed(AllocationBean _alBean){
		if(_alBean==null)
			return false;
		
		if(_alBean.getStatusCode()!=null && Constants.ACCEPTED.equalsIgnoreCase(_alBean.getStatusCode()))
			return false;
		
		return (inReviewPeriod(_alBean.getGasDay()));
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
    	
        items = service.search(filters);
        // En cada busqueda se resetea la fila seleccionada.
        selected = new AllocationBean();
    }

	public void onClear(){
		filters = initFiltersCombos(allocationMaxDateOffset, factorFromDefaultUnit);
	  	
        if (items != null) {
            items.clear();
        }
        items = service.search(filters);
        // En cada busqueda se resetea la fila seleccionada.
        selected = new AllocationBean();     
    }
	
	public void onRowEdit(RowEditEvent _event) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	// Etiquetas para sustituir en el mensaje de ok.
    	final String sDateLabel = "DDD";
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	final String sContractLabel = "CCC";
    	final String sPointLabel = "PPP";
    	final String sReviewCodeLabel ="XXX";

    	String summaryMsg = null;
    	String errorMsg = null;

    	AllocationBean _allocation = (AllocationBean) _event.getObject();
		
    	try {

			HashMap<String, Object> params = new HashMap<>();
			params.put("closingTypeCode", "DEFINITIVE");
			params.put("idnSystem", getChangeSystemView().getIdn_active());
			params.put("sysCode", getChangeSystemView().getSystem());
			service.saveReview(_allocation, getUser(), getLanguage(), factorToDefaultUnit,
					params);
    	}
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = ve.getMessage();
    		getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;
		} 	
    	catch(Exception e){
			summaryMsg = msgs.getString("saving_data_error");
        	errorMsg = msgs.getString("update_error") + " " + msgs.getString("all_rev_allocation_review");
        	getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}
    	
    	summaryMsg = msgs.getString("all_rev_allocation_review") + " " + msgs.getString("updated");
    	String okMsg = msgs.getString("all_rev_allocation_reviewed");
    	okMsg = okMsg.replace(sDateLabel, sdf.format(_allocation.getGasDay()));
    	okMsg = okMsg.replace(sContractLabel, _allocation.getContractCode());
    	okMsg = okMsg.replace(sPointLabel, _allocation.getNomPointCode());
    	okMsg = okMsg.replace(sReviewCodeLabel, _allocation.getReviewCode());
   	
    	getMessages().addMessage(Constants.head_menu[8],
				new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
    	log.info(okMsg);
    	
    	sendMail(_allocation);

    	// Se actualiza la vista.
    	items = service.search(filters); 		
        // Se deja marcado (selected) el incidente que se acaba de editar. 
        selected = _allocation;
    }

	public BalanceManagementService getBalMagService() {
		return balMagService;
	}

	public void setBalMagService(BalanceManagementService balMagService) {
		this.balMagService = balMagService;
	}
    
    public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
    
    public void sendMail(AllocationBean allocation) {
		HashMap<String,String> values = new HashMap<String,String>();
	
		values.put("1", allocation.getShipperCode());
		values.put("2", allocation.getContractCode());
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	String dateString = df.format(allocation.getGasDay());
		values.put("3", dateString);
		values.put("4", allocation.getNomPointCode());
		
		BigDecimal tso = new BigDecimal(-1);
		try {
			tso = service.getDefaultOperatorId(getUser(), getLanguage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mailService.sendEmail("ALLOCATION.REVIEW.QUERY", values, getChangeSystemView().getIdn_active(), tso);
		//Para comprobar los valores que se pasan en el email
		getMessages().addMessage(Constants.head_menu[8],
				new MessageBean(Constants.INFO, "Mail values", "Shipper"+values.get("1")+",Contrato:"+values.get("2")+",Fecha:"+values.get("3")+",Nom. Point:"+values.get("4")+". Destinatario:"+tso, Calendar.getInstance().getTime()));
	}
}
