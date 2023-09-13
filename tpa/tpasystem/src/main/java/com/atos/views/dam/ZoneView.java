package com.atos.views.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
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
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ToggleEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ZoneBean;
import com.atos.filters.dam.ZoneFilter;
import com.atos.services.dam.ZoneService;
import com.atos.utils.Constants;
import com.atos.views.ChangeSystemView;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;


@ManagedBean(name="zoneView")
@ViewScoped
public class ZoneView extends CommonView implements Serializable {
 

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(ZoneView.class);
	
	private ZoneFilter filters;
	private ZoneBean newZone;
	private List<ZoneBean> items;
	
	@ManagedProperty("#{zoneService}")
    transient private ZoneService service;

	private boolean modParametros =false;

	private Map<BigDecimal, Object> comboSystems;
	private Map<BigDecimal, Object> comboIds;
	
	private Map<BigDecimal, Object> comboTypesNewZone;
	private Map<BigDecimal, Object> comboTypesNewQuality;
	

	Calendar sysdate;
	public Calendar getSysdate() {
		return sysdate;
	}
	
	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}
	
	
	public ZoneFilter getFilters() {
		return filters;
	}

	public void setFilters(ZoneFilter filters) {
		this.filters = filters;
	}
	
	public ZoneBean getNewZone() {
		return newZone;
	}

	public void setNewZone(ZoneBean newZone) {
		this.newZone = newZone;
	}
	
	
	public List<ZoneBean> getItems() {
		return items;
	}
	
	public void setItems(List<ZoneBean> items) {
		this.items = items;
	}
	 
    public void setService(ZoneService service) {
        this.service = service;
    }
    
    
    //para visualizar al gridQuality
    private Boolean renderedGridZona;
    private Boolean renderedGridQuality;
    
    public void verZone() {
    	
    	getNewZone().setName("");
    	sysdate= gettingValidDateStart();   
    	getNewZone().setStartDateGasQuality(sysdate.getTime());
    	setRenderedGridQuality(false);
    	setRenderedGridZona(true);
       
    }  
    
    public void verQuality() {
       	setRenderedGridQuality(true);
    	setRenderedGridZona(false);
     }
    
	
	public Boolean getRenderedGridZona() {
		return renderedGridZona;
	}

	public void setRenderedGridZona(Boolean renderedGridZona) {
		this.renderedGridZona = renderedGridZona;
	}

	public Boolean getRenderedGridQuality() {
		return renderedGridQuality;
	}

	public void setRenderedGridQuality(Boolean renderedGridQuality) {
		this.renderedGridQuality = renderedGridQuality;
	}

	//fin geter/seter
	
	@PostConstruct
    public void init() {
    	filters = new ZoneFilter();
    	newZone = new ZoneBean();
     	
    	//BUSCAMOS EL SYSDATE CON EL PARAMETRO DE BD
    	sysdate= gettingValidDateStart();   	
    	newZone.setStartDateGasQuality(sysdate.getTime());
    
    	comboSystems=service.selectSystems(getChangeSystemView().getIdn_active());
    	comboTypesNewZone=service.selectTypesNewZone();
    	comboTypesNewQuality=service.selectTypesNewQuality();
    }
	
	
	//sysdte +1
    public Calendar gettingValidDateStart(){
    	
    	SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
    	Integer valNumDate = valDate.getInteger_exit();
    	Calendar sysdate = Calendar.getInstance(); 
    	
    	//StartDate filter => sysdate
    	sysdate.setTime(valDate.getDate());
    	sysdate.set(Calendar.HOUR_OF_DAY, 0);
    	sysdate.set(Calendar.MINUTE, 0);
    	sysdate.set(Calendar.SECOND, 0);
    	sysdate.set(Calendar.MILLISECOND, 0);
    	sysdate.add(Calendar.DAY_OF_MONTH, valNumDate.intValue());
    	
        return sysdate;
    }

	
	public List<String> idList(String query) {
		query = "%" + query + "%";
		return service.selectIds(query);
    }
	
	public List<String> nameList(String query) {
		query = "%" + query + "%";
		return service.selectNames(query);
    }
	

	public Map<BigDecimal, Object> getSystems() {
		return comboSystems;
	}
	
	//ch1
	public Map<BigDecimal, Object> getTypesNewZone() {
		return comboTypesNewZone;
	}
	
	public Map<BigDecimal, Object> getTypesNewQuality() {
		return comboTypesNewQuality;
	}
	
	//offshore
	public Map<BigDecimal, Object> getIdsSystem() {
		return service.selectZonesSystem(getChangeSystemView().getIdn_active());
	}
	
	public void onSearch(){
		filters.setIdn_system(getChangeSystemView().getIdn_active());	
		items = service.selectZones(filters);
		modParametros=false;
	}
	
	public void onClear(){
		//RequestContext.getCurrentInstance().reset("form");
		filters = new ZoneFilter();
        if (items != null) {
            items.clear();
        }
    }

	public void onRowEdit(RowEditEvent event) {

		ZoneBean zone = (ZoneBean) event.getObject();
	    
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("zone") };
    	String summaryMsgOk = CommonView.getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk= CommonView.getMessageResourceString("update_noOk", params);
		
		
		//si no se expanden los parametros no se cargan en el bean por eso los cargamos denuevo
		if(!modParametros){
			zone =service.getQuality(zone);
			modParametros=false;
		}
		
    	String error = "0";
		try {
			error = service.updateZone(zone);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		String[] par2 = { zone.getId(),msgs.getString("zone")};
		
		if(error!=null && error.equals("0")){
    		String msg = CommonView.getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
    		log.info("Zone Updated", "zone Updated: " + zone.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-1")){
    		String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Zone Not Updated", "Error updating  Zone " + zone.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-2")){
    		String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Zone Not Updated", "Error updating  GasQuality " + zone.toString(), Calendar.getInstance().getTime());
    		
    	}else if (error!=null && error.equals("-10")){
    		String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error Updating Zone deleteZoneGasQuality. Zone, Error: " +error + ". "+zone.toString(), Calendar.getInstance().getTime());
		
    	}else if (error!=null && error.equals("-16")){
    		String msg = msgs.getString("max_value_greater_parameter");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg + " Parameter: " + zone.getParameterCode(), Calendar.getInstance().getTime()));
    		log.info("Zone Not Updated", "Error updating  Zone: "+zone.getId() + "'Max value should be greater than or equal to Min. Parameter: " + zone.getParameterCode(), Calendar.getInstance().getTime());
    	}else if (error!=null){
    		String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Zone Not Updated", "Error updating  Zone." + error, Calendar.getInstance().getTime());
    	}
		
		items = service.selectZones(filters);
		modParametros=false;
    }
     
    public void onRowCancel(RowEditEvent event) {
        
    }
    
    public void cancel() {
        //RequestContext.getCurrentInstance().reset("formNewEntity");  	
    	newZone = new ZoneBean();
    	newZone.setStartDateGasQuality(sysdate.getTime());
    }
    
    public void save(){
 

    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("zone") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    	
    	//hacemos el insert
    	String error = "0";
		try {
			error =  service.insertZone(newZone);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		String[] par2 = {newZone.getName(),msgs.getString("zone") };
		
		if(error!=null && error.equals("0")){
			String msg = getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("Zone Inserted", "Zone Inserted ok" + newZone.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-1")){
			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Zone Inserted", "Error inserting Zone. The " + newZone.toString() +" already exists in the System ", Calendar.getInstance().getTime());
		}else if (error!=null && error.equals("-2")){
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));    	
    		log.info("Zone Not Inserted", "Error inserting  GasQuality " + newZone.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-16")){
    		String msg = msgs.getString("max_value_greater_parameter");//max_value_greater_parameter=Max value should be greater than or equal to Min. Parameter
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg + " Parameter: " + newZone.getParameterCode(), Calendar.getInstance().getTime()));    	
    		log.info("Zone Not Inserted", "Error inserting  Zone. 'Max value should be greater than or equal to Min. Parameter: " + newZone.getParameterCode(), Calendar.getInstance().getTime());
    	}else if (error!=null){
    		String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));    	
    		log.info("Zone Not Inserted", "Error inserting  Zone." + error, Calendar.getInstance().getTime());
    	}
    	
		//offshore
		comboIds=service.selectZonesSystem(getChangeSystemView().getIdn_active());
		
		//offShore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
    	items = service.selectZones(filters);
    	
    	//clean the formu new after save
    	newZone = new ZoneBean();
    	
    	//StartDate New => sysdate +1
    	//Calendar sysdate= dateValidStart();
    	newZone.setStartDateGasQuality(sysdate.getTime());
    	
    }
    
    public void onRowToggle(ToggleEvent event) {
    	ZoneBean zone = (ZoneBean) event.getData();
    	zone= service.getQuality(zone);
    	modParametros=true;
    }
    
    
    //ch1 cuando se edita un registro solo se permite al usuaio que elija la mayor de la fechas entre el sysdate o el startDate de la zona
    public Calendar buscaFechaMin(Date startDate){
    	
    	Calendar sysdateMin = Calendar.getInstance(); 
    	if (startDate != null) {
    		
    	    if (startDate.after(sysdate.getTime())){
   			   sysdateMin.setTime(startDate);
         	}else{
        		sysdateMin= gettingValidDateStart(); 
         	}	
    	}
    	
        return sysdateMin;
    }
    
    //ch1 se busca la fecha min que el usario puede dar una Quality la mayor entre la startdate de la zona y el sysdate+parametro
    public void buscaZone(BigDecimal idn_zone){
    	Calendar sysdateMinQuality = Calendar.getInstance(); 
    	
    	ZoneBean zone= new ZoneBean();
    	zone = service.getZone(idn_zone);
    	 
    	 getNewZone().setName(zone.getName());
    	 getNewZone().setIdn_pipeline_system(zone.getIdn_pipeline_system());
    
    	 if (zone.getStartDate().after(sysdate.getTime())){
       		sysdateMinQuality.setTime(zone.getStartDate());
     	}else{
     		sysdateMinQuality= gettingValidDateStart(); 
     	}
    	
    	// getNewZone().setStartDate(sysdateMinQuality.getTime());
    	 getNewZone().setStartDateGasQuality(sysdateMinQuality.getTime());
    }
    
    public String disabledField(ZoneBean item) {
    	//1.	En caso de un registro con la fecha Start Date posterior al día actual, el sistema habilitará Todos
 		//2.	En caso de un registro con la fecha Start Date anterior o igual al día actual y 
 			 //fecha End Date posterior al día actual (o vacía), el sistema habilitará SOLO END DATE
 		//3.	En caso de un registro con la fecha Start Date anterior o igual al día actual, se habilita  NADA 

    	//18/7/2016 modificado para que edite cuando la fecha startDay sea igual al sysdate
    	
 		/*if(item.getStartDateGasQuality().after(sysdate.getTime())){
 			return "false";			
 		}*/
 		
    	if(item.getStartDateGasQuality().after(sysdate.getTime()) || item.getStartDateGasQuality().equals(sysdate.getTime()) ){
 			return "false";			
 		}


 		if(item.getStartDateGasQuality().before(sysdate.getTime()) || item.getStartDateGasQuality().equals(sysdate.getTime()) ){ 
 			 return "true";
 		}
 		
 		return "true";
    }    	

    public boolean renderItemEditor(ZoneBean item){
     		
	    	if (item.getStartDateGasQuality().before(sysdate.getTime()) ) {
	    		return false;
	    		
	    	}else{
	    		return true;
	    	}
    }
    
    public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
}
