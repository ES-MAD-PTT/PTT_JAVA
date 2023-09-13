package com.atos.views.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
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
import org.primefaces.event.RowEditEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.scadaAlarms.ScadaAlarmBean;
import com.atos.filters.scadaAlarms.ScadaAlarmsFilter;
import com.atos.services.scadaAlarms.ScadaAlarmService;
import com.atos.utils.Constants;
import com.atos.views.ChangeSystemView;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;

@ManagedBean(name="scadaAlarmView")
@ViewScoped
public class ScadaAlarmView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(ScadaAlarmView.class);

	// ScadaAlarmsFilter initial values for status.
	private static final String strNEW = "NEW";
	private static final String strPUBLISHED = "PUBLISHED";
	private static final String strDELETED = "DELETED";
	private static final String strFINISHED = "FINISHED";
	
	private static final String strSCADA = "SCADA";
	private static final String strTPA = "TPA";
	
	private ScadaAlarmsFilter filters;
	private ScadaAlarmBean newScadaAlarms;
	private List<ScadaAlarmBean> items;
	private List<ScadaAlarmBean> selecteds;
	
	//private Map<BigDecimal, Object> areas; //offshore
	private Map<BigDecimal, Object> areasSystem;

	//private Map<BigDecimal, Object> comboAlarmName; //offshore
	 private Map<BigDecimal, Object> comboAlarmNameSystem;
	 
	//private Map<BigDecimal, Object> comboAlarmLabel;
	//private Map<BigDecimal, Object> comboAlarmMetering;
	
	
	@ManagedProperty("#{scadaAlarmService}")
    transient private ScadaAlarmService service;
    
    public void setService(ScadaAlarmService service) {
        this.service = service;
    }

    private Calendar sysdate;
    
    
    public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	public ScadaAlarmBean getNewScadaAlarms() {
		return newScadaAlarms;
	}

	public void setNewScadaAlarms(ScadaAlarmBean newScadaAlarms) {
		this.newScadaAlarms = newScadaAlarms;
	}

	//geters/seters
	 public ScadaAlarmsFilter getFilters() {
		return filters;
	}

	public void setFilters(ScadaAlarmsFilter filters) {
		this.filters = filters;
	}
	
	public List<ScadaAlarmBean> getItems() {
		return items;
	}
	
	public void setItems(List<ScadaAlarmBean> items) {
		this.items = items;
	}
       
	public List<ScadaAlarmBean> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<ScadaAlarmBean> selecteds) {
		this.selecteds = selecteds;
	}

	/* offshore	
    public Map<BigDecimal, Object> getAreas() {
		return this.areas;
		
	}*/
	//offshore
	public Map<BigDecimal, Object> getAreasSystem() {
		return this.areasSystem;
		
	}
	
	
	/* offshore
	 public Map<BigDecimal, Object> getAlarmName() {
		return comboAlarmName;
	}*/
	public Map<BigDecimal, Object> getAlarmNameSystem() {
		return comboAlarmNameSystem;
	}
	
	
	public Map<BigDecimal, Object> getAlarmLabel(ScadaAlarmBean it) {	
		if(it==null){
			return service.selectAlarmLabel(null);
		} else {
			return service.selectAlarmLabel(it);
		}
	}
	
	
	//public Map<BigDecimal, Object> getAlarmMetering() {
	//	return service.selectAlarmMetering();
	//}
	
	
	public Map<BigDecimal, Object> getAlarmMetering(ScadaAlarmBean it) {
		if(it==null){
			return service.selectAlarmMetering(null);
		} else {
			return service.selectAlarmMetering(it);
		}
	}
	
	public Map<String, Object> getAlarmBinary(ScadaAlarmBean it) {
		if(it==null){
			return service.selectAlarmBinary(null);
		} else {
			return service.selectAlarmBinary(it);
		}
		
		
	}
	
	public Map<String, Object> getAlarmType(ScadaAlarmBean it) {
		if(it==null){
			return service.selectAlarmType(null);
		} else {
			return service.selectAlarmType(it);
		}
	}
	
	
	@PostConstruct
    public void init() {
		
    	filters = new ScadaAlarmsFilter();	
    	newScadaAlarms = new ScadaAlarmBean();
    	
    	String[] aStrStatus = {strNEW, strPUBLISHED, strDELETED, strFINISHED};
    	filters.setStatus(aStrStatus);
    	
    	String[] aStrOrigin = {strSCADA, strTPA};
    	filters.setOrigin(aStrOrigin);
    	
    	  
      	sysdate= Calendar.getInstance();
      //task 439212
    	sysdate.set(Calendar.HOUR_OF_DAY, 0);
    	sysdate.set(Calendar.MINUTE, 0);
    	sysdate.set(Calendar.SECOND, 0);
    	sysdate.set(Calendar.MILLISECOND, 0);
    
      	
      	newScadaAlarms.setScada_timestamp(sysdate.getTime());
      	filters.setStartDate(sysdate.getTime());
      	
      //cargamos los combos inicialment
      
     //offshore
     //comboAlarmName= service.selectAlarmName();
       comboAlarmNameSystem= service.selectAlarmNameSystem(getChangeSystemView().getIdn_active());
    }


	public void onSearch(){
		if (filters.getEndDate()!=null ) {
			if(filters.getStartDate().after(filters.getEndDate())){
		    	getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.ERROR,"Error dates", "Start date must be previous to End date", Calendar.getInstance().getTime()));
		    	return;
		    }
		}
		
		//offshore
	//	areas = service.selectAreas();
		areasSystem= service.selectAreasSystem(getChangeSystemView().getIdn_active());
		
		//offShore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		
		items = service.selectScadaAlarms(filters);
	}
	
	public void onClear(){
	//	RequestContext.getCurrentInstance().reset("form");

		filters = new ScadaAlarmsFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
    	String[] aStrStatus = {strNEW, strPUBLISHED, strDELETED, strFINISHED};
    	filters.setStatus(aStrStatus);
    	
    	String[] aStrOrigin = {strSCADA, strTPA};
    	filters.setOrigin(aStrOrigin);
    	
        if (items != null) {
            items.clear();
        }
    }
	
	//actualmente este metodo no se llama.
	public void saveMasivo(){
		for(int i=0;i<items.size();i++){
			ScadaAlarmBean alarm = items.get(i);
			
			if(alarm.isEdit()){
				String error = "0";
				try {
					// En caso de que se quiera enviar una notificacion al usuario, se ha de indicar un systemId distinto de null.
					error = service.updateScadaAlarm(alarm,null);
				} catch (Exception e) {
					log.catching(e);
					// we assign the return message
					error = e.getMessage();
				}
				if (error != null && error.equals("0")) {
					getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.INFO, "Event Updated","Event Updated. Id " + alarm.getAlarm_id(),Calendar.getInstance().getTime()));
					log.info("ScadaAlarm Updated: " + alarm.toString(), Calendar.getInstance().getTime());
				} else if (error != null && error.equals("-1")) {
					getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.ERROR, "Event Not Updated","Error updating  Event, Id Event: " + alarm.getAlarm_id(),Calendar.getInstance().getTime()));
					log.info("ScadaAlarm Not Updated", "Error updating  ScadaAlarm " + alarm.toString(),Calendar.getInstance().getTime());
				}
			}
		}
		items = service.selectScadaAlarms(filters);
	}

	
	public void save(){
		
		String error = "0";
		try {
			error =  service.insertScadaAlarms(newScadaAlarms);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		
		if(error!=null && error.equals("0")){
			getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.INFO,"Event Inserted", "Event Inserted ok. Id: " +  newScadaAlarms.getAlarm_id(), Calendar.getInstance().getTime()));
			log.info("MeteredPoint Inserted ok" + newScadaAlarms.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.ERROR, "Event Not Inserted","Error inserting  Event, Id scadaAlarm: " + newScadaAlarms.getAlarm_id(),Calendar.getInstance().getTime()));
			log.info("ScadaAlarm Not Inserted", "Error inserting  ScadaAlarm " + newScadaAlarms.toString(),Calendar.getInstance().getTime());
		}
    	
    	items = service.selectScadaAlarms(filters);
    	
    	//offshore
    	//areas = service.selectAreas();
    	areasSystem= service.selectAreasSystem(getChangeSystemView().getIdn_active());
    
    	//clean the formu new after save
    	newScadaAlarms = new  ScadaAlarmBean();
    	
    	//StartDate New => sysdate +1
    	newScadaAlarms.setScada_timestamp(sysdate.getTime()); 
	}
	
	
	public void publish(){
		for(int i=0;i<selecteds.size();i++){
			
			
			ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
	    	String[] params = {msgs.getString("event")};
	    	String summaryMsgOk = getMessageResourceString("update_ok", params);
	    	String summaryMsgNotOk= getMessageResourceString("update_noOk", params);
	    	
			ScadaAlarmBean alarm = selecteds.get(i);

			if (alarm.getStatus().equals(strNEW)) {
				alarm.setStatus(strPUBLISHED);
			} else {
				//getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.INFO,summaryMsgOk, summaryMsgOk + " Id: " + alarm.getAlarm_id(), Calendar.getInstance().getTime()));	

				getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.WARNING, "Event Published","The state of the event is not NEW. You can not Publish this event: Id " + alarm.getAlarm_id(),Calendar.getInstance().getTime()));
				log.info("ScadaAlarm Published. The state of the alarm is not NEW. You can not Publish this alarm" + alarm.toString(), Calendar.getInstance().getTime());
				continue;
			}
			
			if(alarm.getCapacity_restriction()==null){
				getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.ERROR, "Event Not Published","The field Capacity Restriction should be informed" ,Calendar.getInstance().getTime()));
				log.error("ScadaAlarm Not Published. The field Capacity Restriction should be informed "+ alarm.toString(), Calendar.getInstance().getTime());
				continue;
			} else if(alarm.getIdn_area()==null){
				getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.ERROR, "Event Not Published","The field Area should be informed" ,Calendar.getInstance().getTime()));
				log.error("ScadaAlarm Not Published. The field Area should be informed "+ alarm.toString(), Calendar.getInstance().getTime());
				continue;
			} 
			/*CH236 de la Fase1 - SCADA Alarms - Campo "Temp Resolution Time"  opcional
			 * else if(alarm.getTemp_resolution_time()==null){
				messages.addMessage(Constants.head_menu[5],new MessageBean(Constants.ERROR, "ScadaAlarm Not Published","The field Temp Resolution Time should be informed" ,Calendar.getInstance().getTime()));
				log.error("ScadaAlarm Not Published. The field Temp Resolution Time should be informed "+ alarm.toString(), Calendar.getInstance().getTime());
				continue;
			} */
			else if(alarm.getSeverity()==null){
				getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.ERROR, "Event Not Published","The field Severiry should be informed" ,Calendar.getInstance().getTime()));
				log.error("ScadaAlarm Not Published. The field Severiry should be informed "+ alarm.toString(), Calendar.getInstance().getTime());
				continue;
			}
			
			String error = "0";
			try {
				error = service.updateScadaAlarm(alarm,getChangeSystemView().getIdn_active());
			} catch (Exception e) {
				log.catching(e);
				// we assign the return message
				error = e.getMessage();
			}
			if (error != null && error.equals("0")) {
				getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.INFO, "Event Updated","Event Updated. Id " + alarm.getAlarm_id(),Calendar.getInstance().getTime()));
				log.info("ScadaAlarm Updated: " + alarm.toString(), Calendar.getInstance().getTime());
			} else if (error != null && error.equals("-1")) {
				getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.ERROR, "Event Not Updated","Error updating  Event, Id scadaAlarm: " + alarm.getAlarm_id(),Calendar.getInstance().getTime()));
				log.info("ScadaAlarm Not Updated", "Error updating  ScadaAlarm " + alarm.toString(),Calendar.getInstance().getTime());
			}
		}
		items = service.selectScadaAlarms(filters);
	}
	
	public void delete(){
		for(int i=0;i<selecteds.size();i++){
			ScadaAlarmBean alarm = selecteds.get(i);
			
			// SOLO SE PUEDEN BORRAR SI SU ESTADO PREVIO ES NEW
			if (alarm.getStatus().equals(strNEW)) {
				
				alarm.setStatus(strDELETED);
			} else if(alarm.getStatus().equals(strPUBLISHED)) {
				
				alarm.setStatus(strFINISHED); 
			} else {
				getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.WARNING, "Event Deleted","The state of the alarm is not NEW. You can not Delete this event: Id " + alarm.getAlarm_id(),Calendar.getInstance().getTime()));
				log.info("ScadaAlarm Deleted. The state of the alarm is not NEW. You can not Delete this alarm" + alarm.toString(), Calendar.getInstance().getTime());
				continue;
			}
			
			String error = "0";
			try {
				// En caso de que se quiera enviar una notificacion al usuario, se ha de indicar un systemId distinto de null.
				error = service.updateScadaAlarm(alarm,null);
			} catch (Exception e) {
				log.catching(e);
				// we assign the return message
				error = e.getMessage();
			}
			if (error != null && error.equals("0")) {
				getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.INFO, "Event Updated","Event Updated. Id " + alarm.getAlarm_id(),Calendar.getInstance().getTime()));
				log.info("ScadaAlarm Updated: " + alarm.toString(), Calendar.getInstance().getTime());
			} else if (error != null && error.equals("-1")) {
				getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.ERROR, "Event Not Updated","Error updating  Event, Id Event: " + alarm.getAlarm_id(),Calendar.getInstance().getTime()));
				log.info("ScadaAlarm Not Updated", "Error updating  ScadaAlarm " + alarm.toString(),Calendar.getInstance().getTime());
			}
			
		}
		items = service.selectScadaAlarms(filters);
	}

	public void onRowEdit(RowEditEvent event){
		ScadaAlarmBean scadaAlarm = (ScadaAlarmBean) event.getObject();
		scadaAlarm.setEdit(true);
		scadaAlarm.setArea_code(service.selectArea(scadaAlarm.getIdn_area()));
		

		String error = "0";
		try {
			// En caso de que se quiera enviar una notificacion al usuario, se ha de indicar un systemId distinto de null.
			error = service.updateScadaAlarm(scadaAlarm,null);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}
		if (error != null && error.equals("0")) {
			getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.INFO, "Event Updated","Event Updated. Id " + scadaAlarm.getAlarm_id(),Calendar.getInstance().getTime()));
			log.info("ScadaAlarm Updated: " + scadaAlarm.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.ERROR, "Event Not Updated","Error updating  Event, Id Event: " + scadaAlarm.getAlarm_id(),Calendar.getInstance().getTime()));
			log.info("ScadaAlarm Not Updated", "Error updating  ScadaAlarm " + scadaAlarm.toString(),Calendar.getInstance().getTime());
		}
		
	}
	
	public void onRowCancel(RowEditEvent event){
		
	}
	
	
	public void cancel(){

		newScadaAlarms = new ScadaAlarmBean();
		newScadaAlarms.setScada_timestamp(sysdate.getTime());
	}
	
    public String disabledField(ScadaAlarmBean item) {
    if (item.getStatus().equals(strNEW)){
    		return "false";
    	}else 
    	  return "true";	
    }
    
    public boolean renderItemEditor(ScadaAlarmBean item){
   	if (item.getStatus().equals(strNEW)){
    		return true;
    	}else 
    		return false;	
    }
    
}
