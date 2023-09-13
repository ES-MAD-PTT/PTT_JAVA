package com.atos.views.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.primefaces.model.DualListModel;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.MessageBean;
import com.atos.beans.SystemParameterBean;

import com.atos.beans.dam.ShipperBean;
import com.atos.filters.dam.ShipperFilter;
import com.atos.services.dam.ShipperService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="shipperView")
@ViewScoped
public class ShipperView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(ShipperView.class);
	private ShipperFilter filters;
	// Listado de areas inicial y comun para el dialogo de shipper-point en tabla principal y de new shipper.
	private List<ComboFilterNS> areasCombo = null;
	// Array con las areas marcadas para el dialogo Lista de Contratos del dialogo New Shipper.
	private BigDecimal[] newShipperFilterAreaIds;		 
	private ShipperBean newShipper;
	// Array con las areas marcadas para el dialogo Lista de Contratos de la tabla principal.
	// Comun para todos los shippers.
	private BigDecimal[] selectedFilterAreaIds;
	private ShipperBean selected;
	private List<ShipperBean> items;
	
	@ManagedProperty("#{shipperService}")
    transient private ShipperService service;
    
    public void setService(ShipperService service) {
        this.service = service;
    }

    private Calendar sysdate;
    public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	public ShipperFilter getFilters() {
		return filters;
	}

	public void setFilters(ShipperFilter filters) {
		this.filters = filters;
	}
	
	public BigDecimal[] getNewShipperFilterAreaIds() {
		return newShipperFilterAreaIds;
	}

	public void setNewShipperFilterAreaIds(BigDecimal[] newShipperFilterAreaIds) {
		this.newShipperFilterAreaIds = newShipperFilterAreaIds;
	}

	public BigDecimal[] getSelectedFilterAreaIds() {
		return selectedFilterAreaIds;
	}

	public void setSelectedFilterAreaIds(BigDecimal[] selectedFilterAreaIds) {
		this.selectedFilterAreaIds = selectedFilterAreaIds;
	}

	public List<ComboFilterNS> getAreasCombo() {
		return areasCombo;
	}


	public ShipperBean getNewShipper() {
		return newShipper;
	}

	public void setNewShipper(ShipperBean newShipper) {
		this.newShipper = newShipper;
	}
	
	public ShipperBean getSelected() {
		return selected;
	}

	public void setSelected(ShipperBean selected) {
		this.selected = selected;
	}

	public List<ShipperBean> getItems() {
		return items;
	}
	
	public void setItems(List<ShipperBean> items) {
		this.items = items;
	}
	 
    
	@PostConstruct
    public void init() {
		
    	filters = new ShipperFilter();
    	//BUSCAMOS EL SYSDATE CON EL PARAMETRO DE BD
    	sysdate= gettingValidDateStart();
    	areasCombo = service.selectAreas(getChangeSystemView().getIdn_active());
    	
    	cleanNewShipper();
    	cleanSelectedShipper();
    	
    }
	
	private void cleanNewShipper() {
    	newShipper = new ShipperBean();
    	
    	//StartDate New => sysdate +n
    	newShipper.setStartDate(sysdate.getTime());
    	initDualList(newShipper);
    	
    	//Inicialmente se han escogido todas las areas.
    	newShipperFilterAreaIds = new BigDecimal[this.areasCombo.size()];
    	int i = 0;
    	for (ComboFilterNS cfNSArea : this.areasCombo)
    		newShipperFilterAreaIds[i++]=cfNSArea.getKey();
	}
	
	private void cleanSelectedShipper() {
    	selected = new ShipperBean();
    	
    	//Inicialmente se han escogido todas las areas.
    	selectedFilterAreaIds = new BigDecimal[this.areasCombo.size()];
    	int i = 0;
    	for (ComboFilterNS cfNSArea : this.areasCombo)
    		selectedFilterAreaIds[i++]=cfNSArea.getKey();
	}
	
	// En la tabla principal, cuando se carga el dialogo de puntos de contratos, se empieza 
	// seleccionando todas las areas. Asi coincide con la carga inicial de puntos de contratos
	// de los shippers.
	public void reloadSelectedFilterAreaIds() {
    	//Inicialmente se han escogido todas las areas.
    	selectedFilterAreaIds = new BigDecimal[this.areasCombo.size()];
    	int i = 0;
    	for (ComboFilterNS cfNSArea : this.areasCombo)
    		selectedFilterAreaIds[i++]=cfNSArea.getKey();
	}
	
	public void reloadNewFilterAreaIds() {
    	//Inicialmente se han escogido todas las areas.
    	newShipperFilterAreaIds = new BigDecimal[this.areasCombo.size()];
    	int i = 0;
    	for (ComboFilterNS cfNSArea : this.areasCombo)
    		newShipperFilterAreaIds[i++]=cfNSArea.getKey();
	}
	
	//sysdte +valNumDate
    public Calendar gettingValidDateStart(){
    	
    	SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
    	Integer valNumDate = valDate.getInteger_exit();
    	Calendar sysdate = Calendar.getInstance(); 
    	//StartDate filter => sysdate
    	filters.setStartDate(sysdate.getTime());
    	
    	sysdate.setTime(valDate.getDate());
    	sysdate.set(Calendar.HOUR_OF_DAY, 0);
    	sysdate.set(Calendar.MINUTE, 0);
    	sysdate.set(Calendar.SECOND, 0);
    	sysdate.set(Calendar.MILLISECOND, 0);
    	sysdate.add(Calendar.DAY_OF_MONTH, valNumDate.intValue());
    	
        return sysdate;
    }
	
	private void initDualList(ShipperBean shipper) {
    	List<ComboFilterNS> lAllContractPoints = 
    			service.selectContractPoints(getChangeSystemView().getIdn_active());
    	List<ComboFilterNS> lSourceContractPoints = new ArrayList<ComboFilterNS>();
		List<ComboFilterNS> lTargetContractPoints = new ArrayList<ComboFilterNS>();
    	for (ComboFilterNS cfnsPointAll : lAllContractPoints) {
			ComboFilterNS cfnsPoint = new ComboFilterNS(cfnsPointAll.getKey(),cfnsPointAll.getValue());
			lSourceContractPoints.add(cfnsPoint);
		}
    	shipper.setdLContractPoints(new DualListModel<ComboFilterNS>(lSourceContractPoints, lTargetContractPoints));
	}
	
	public void refreshDualList(ShipperBean shipper) {
		service.refreshDualList(shipper, getChangeSystemView().getIdn_active(), selectedFilterAreaIds);
	}
	
	public void refreshNewDualList(ShipperBean shipper) {
		service.refreshDualList(shipper, getChangeSystemView().getIdn_active(), newShipperFilterAreaIds);
	}
	
	public Map<String, Object> getIds() {
		return service.selectShipperId();
	}

	public List<String> companyNameList(String query) {
		query = "%" + query + "%";
		return service.selectCompanyName(query);
    }
	

	// Methods
	public void onSearch(){
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_shipper1').clearFilters()");
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		if (filters.getEndDate()!=null ) {
			if(filters.getStartDate().after(filters.getEndDate())){
		    	String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
		    	return;
		    }
		}
		
		items = service.selectShippers(filters, getChangeSystemView().getIdn_active());
		cleanSelectedShipper();
	}
	
	public void onClear(){
		//RequestContext.getCurrentInstance().reset("form");
		filters = new ShipperFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
        if (items != null) {
            items.clear();
        }
        cleanSelectedShipper();
    }

 
    public void onRowEditInit(RowEditEvent event) {
    	
    	ShipperBean shipper = (ShipperBean) event.getObject();
    	shipper.setEditing(true);
    	
    	// Se rellena la Lista dual previa, para guardar datos antes de editar, por si hubiera que hacer Cancel/Rollback.
    	// Estructura con datos iniciales.
    	DualListModel<ComboFilterNS> dlPoints = shipper.getdLContractPoints();
    	List<ComboFilterNS> lSourcePoints = dlPoints.getSource();
    	List<ComboFilterNS> lTargetPoints = dlPoints.getTarget();
    	
    	// Estructura para guardar datos previos. Hay que hacer deep copy.
    	DualListModel<ComboFilterNS> dlPrevPoints = new DualListModel<ComboFilterNS>();;
    	List<ComboFilterNS> lPrevSourcePoints = new ArrayList<ComboFilterNS>();
    	List<ComboFilterNS> lPrevTargetPoints = new ArrayList<ComboFilterNS>();
    	
    	for(ComboFilterNS cfnsPoint: lSourcePoints)
    		lPrevSourcePoints.add(new ComboFilterNS(cfnsPoint.getKey(), cfnsPoint.getValue()));

    	for(ComboFilterNS cfnsPoint: lTargetPoints)
    		lPrevTargetPoints.add(new ComboFilterNS(cfnsPoint.getKey(), cfnsPoint.getValue()));
    	
    	dlPrevPoints.setSource(lPrevSourcePoints);
    	dlPrevPoints.setTarget(lPrevTargetPoints);
    	
    	shipper.setdLPrevContractPoints(dlPrevPoints);
    }

	public void onRowEdit(RowEditEvent event) {
  
    	ShipperBean shipper = (ShipperBean) event.getObject();
    	String errorMsg = null;
   
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("shipper")};
    	String summaryMsgOk = getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk= getMessageResourceString("update_noOk", params);
    	
    	if(shipper.getEndDate()!=null){
    		if(shipper.getStartDate().after(shipper.getEndDate())){
    			errorMsg = msgs.getString("start_previous_end"); //start_previous_end= Start date must be previous to end date
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
		    	return;
    		}
    		
    	}
    	
    	
    	String error = "0";
		try {
			error = service.updateShipper(shipper, this.selectedFilterAreaIds);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		String[] par2 = {shipper.getId(),msgs.getString("shipper") };
		
		if(error!=null && error.equals("0")){
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, summaryMsgOk + " Id: " + shipper.getId(), Calendar.getInstance().getTime()));	
			log.info("Shipper Updated ok: " + shipper.toString(), Calendar.getInstance().getTime());
 
    	}else if (error!=null && error.equals("-1")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.error("Error updating shipper. Error updating Table User Group: "  + shipper.toString(), Calendar.getInstance().getTime());		
    		
    	}else if (error!=null && error.equals("-2")){
    		String msg =  getMessageResourceString("error_updating", par2);
    		getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.error("Error updating shipper. Error updating Table Shipper: "  + shipper.toString(), Calendar.getInstance().getTime());

    	}else if (error!=null && error.equals("-3")){
    		String msg =  getMessageResourceString("error_updating", par2);
    		getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.error("Error updating shipper. Error inserting into Table Shipper-Point: "  + shipper.toString(), Calendar.getInstance().getTime());

    	}else if (error!=null && error.equals("-4")){
    		String msg =  getMessageResourceString("error_updating", par2);
    		getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.error("Error updating shipper. Error deleting from Table Shipper-Point: "  + shipper.toString(), Calendar.getInstance().getTime());
    	
    	}else {
    		String msg =  getMessageResourceString("error_updating", par2);
    		getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.error("Error updating shipper. Generic Error: "  + shipper.toString(), Calendar.getInstance().getTime());
    	}
		
		// Aunque parece que no hace falta actualizar "editing" y "dLPrevContractPoints"
		// porque se vuelven a cargar todos los shippers. SI es necesario, porque 
		// se mantienen los valores de las estructuras java entre invocaciones.
		shipper.setEditing(false);
    	shipper.setdLPrevContractPoints(null);
		
		items = service.selectShippers(filters, getChangeSystemView().getIdn_active());
    }
     
    public void onRowCancel(RowEditEvent event) {
    	
    	ShipperBean shipper = (ShipperBean) event.getObject();

		shipper.setEditing(false);
		// Recupero la dualList que habia guardado al empezar a editar. 
    	shipper.setdLContractPoints(shipper.getdLPrevContractPoints());
    	// Reinicio la lista previa.
    	shipper.setdLPrevContractPoints(null);
    }
    
    public void cancel() {
       // RequestContext.getCurrentInstance().reset("formNewEntity");
    	
    	cleanNewShipper();
    }
    
    
    public void save(){
    	
    	SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
    	Integer valNumDate = valDate.getInteger_exit();
    	
    	String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	String[] params = {msgs.getString("shipper") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    
    	if(newShipper.getStartDate()!=null ){
	    	if(newShipper.getStartDate().before(sysdate.getTime())){//no se puede dar.. la pantalla no deja
	    		errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
				return;
		    	
			}
    	}
    	
    	if(newShipper.getEndDate()!=null ){
	    	if (newShipper.getEndDate().before(sysdate.getTime())  ){ //no se puede dar.. la pantalla no deja
	    		errorMsg = msgs.getString("error_endDate_sysdate"); //error_endDate_sysdate= End Date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
		    	return;
			}
	    	if(newShipper.getStartDate().after(newShipper.getEndDate())){
	    		errorMsg = msgs.getString("error_startEarlierEnd"); //error_startEarlierEnd = Start Date must be earlier or equal to End Date
				getMessages().addMessage(Constants.head_menu[0],
				new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg );
		    	return;
	    	}
	    		
    	}
    
    	String error = "0";
		try {
			error =  service.insertShipper(newShipper);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
    	
		String[] par2 = { newShipper.getId(),msgs.getString("shipper") };
		
		if(error!=null && error.equals("0")){
			String msg =  getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk,msg, Calendar.getInstance().getTime()));
			log.info("Shipper Inserted ok" + newShipper.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-1")){
			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.error("Error inserting Shipper. The " + newShipper.toString() +" already exists in the System ", Calendar.getInstance().getTime());		
		}else if(error!=null && error.equals("-2")){
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.error("Error inserting shipper. Error inserting Table User Group: "  + newShipper.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-3")){
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.error("Error inserting shipper. Error inserting Table Shipper: "  + newShipper.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-4")){
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.error("Error inserting shipper. Error inserting Table Shipper-Point: "  + newShipper.toString(), Calendar.getInstance().getTime());
		}else {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.error("Error inserting shipper. Generic Error: "  + newShipper.toString(), Calendar.getInstance().getTime());
		}
    	
    	onSearch();
    	
    	//cargamos de nuevo el combo shipper para que se refleje el alta
    	service.selectShipperId();
    	    	
    	cleanNewShipper();
    }
    
    	
    public String disabledEndDate(ShipperBean item){
    	if(item.getEndDate()!=null ){
    		if (item.getStartDate().before(sysdate.getTime()) && (item.getEndDate().before(sysdate.getTime()))){
        		return "true";
        	}	
    	}   	    	
    	return "false";
    }
    	
   public String disabledField(ShipperBean item) {
		//1.	En caso de un registro con la fecha Start Date posterior al día actual, el sistema habilitará Todos
 		//2.	En caso de un registro con la fecha Start Date anterior o igual al día actual y 
 			 //fecha End Date posterior al día actual (o vacía), el sistema habilitará SOLO END DATE
 		//3.	En caso de un registro con la fecha Start Date anterior o igual al día actual, se habilita  NADA
		
		
		
		/* 18/7/2016 modificado para que edite cuando la fecha startDay sea igual al sysdate
 		if(item.getStartDate().after(sysdate.getTime())){
 			return "false";			
 		}
 		
 		if(item.getEndDate()!=null ){
	 		if (item.getStartDate().before(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime()) 
	 				&& ( item.getEndDate().after(sysdate.getTime()) || item.getEndDate().equals(""))){			
	 			return "true";
	 		}
 		}
 		*/
 		
		if(item.getStartDate().after(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime()) ){
 			return "false";			
 		}
 		
		if (item.getStartDate().before(sysdate.getTime())  
	 			&& ( item.getEndDate()==null) || item.getEndDate().after(sysdate.getTime())){			
	 		return "true";
	 	}
 		
 		
 		if(item.getStartDate().before(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime()) ){ 
 			 return "true";
 		}
 		
 		return "true";
	}
    
    
    public boolean renderItemEditor(ShipperBean item){
     	if(item.getEndDate()!=null ){	
	    	if (item.getStartDate().before(sysdate.getTime()) && item.getEndDate().before(sysdate.getTime())) {
	    		return false;
	    		
	    	}else{
	    		return true;
	    	}
    	}else return true;
    }
    
    public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
	    
}
