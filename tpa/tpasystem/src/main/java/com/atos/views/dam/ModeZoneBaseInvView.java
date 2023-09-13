package com.atos.views.dam;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.atos.beans.MessageBean;
import com.atos.beans.dam.ModeZoneBaseInvBean;
import com.atos.filters.dam.ModeZoneBaseInvFilter;
import com.atos.services.dam.ModeZoneBaseInvService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="modeZoneBaseInvView")
@ViewScoped
public class ModeZoneBaseInvView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(AreaView.class);
	
	private ModeZoneBaseInvFilter filters;
	private ModeZoneBaseInvBean newModeZoneBaseInv;
	private List<ModeZoneBaseInvBean> items;
	
	@ManagedProperty("#{modeZoneBaseInvService}")
    transient private ModeZoneBaseInvService service;
    
	//offshore, El combo zona ahora es constante no depende de system se puede cargar en el ini
	private Map<BigDecimal, Object> comboZones;
		
	private Calendar sysdate;
	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	public ModeZoneBaseInvFilter getFilters() {
		return filters;
	}

	public void setFilters(ModeZoneBaseInvFilter filters) {
		this.filters = filters;
	}
	
	public ModeZoneBaseInvBean getNewModeZoneBaseInv() {
		return newModeZoneBaseInv;
	}

	public void setNewModeZoneBaseInv(ModeZoneBaseInvBean newModeZoneBaseInv) {
		this.newModeZoneBaseInv = newModeZoneBaseInv;
	}
	
	public List<ModeZoneBaseInvBean> getItems() {
		return items;
	}
	
	public void setItems(List<ModeZoneBaseInvBean> items) {
		this.items = items;
	}
	 
    public void setService(ModeZoneBaseInvService service) {
        this.service = service;
    }
    

	@PostConstruct
    public void init() {
    	filters = new ModeZoneBaseInvFilter();
    	newModeZoneBaseInv = new ModeZoneBaseInvBean();
     	
    	//LOOKING TO THE SYSDATE parameter BD
    	sysdate= gettingValidDateStart();
    	
    	//StartDate New => sysdate +n
    	newModeZoneBaseInv.setStartDate(sysdate.getTime());
    	    	
    	//offshore
    	//comboSystems=service.selectSystems(getChangeSystemView().getIdn_active());
    	comboZones= service.selectZonesSystem(getChangeSystemView().getIdn_active());
    }
	
	//sysdate 
    public Calendar gettingValidDateStart(){
    	
    	Calendar sysdate = Calendar.getInstance(); 
    	filters.setStartDate(sysdate.getTime());
    	
    	sysdate.setTime(sysdate.getTime());
    	sysdate.set(Calendar.HOUR_OF_DAY, 0);
    	sysdate.set(Calendar.MINUTE, 0);
    	sysdate.set(Calendar.SECOND, 0);
    	sysdate.set(Calendar.MILLISECOND, 0);
    	
        return sysdate;
    }


	public Map<BigDecimal, Object> getZonesSystem() {
		return comboZones;
	}
	
	public Map<BigDecimal, Object> getModesSystem() {
		if(filters.getIdn_zone()!=null)
			return service.selectModesSystem(filters.getIdn_zone());
		return null;
	}
	
	public Map<BigDecimal, Object> selectModesSystemZone(BigDecimal idn_zone) {
		return service.selectModesSystem(idn_zone);
	}
	
	public Map<BigDecimal, Object> getModesSystemNew() {
		if(newModeZoneBaseInv.getIdn_zone()!=null)
			return service.selectModesSystem(newModeZoneBaseInv.getIdn_zone());
		return null;
	}

	// Methods
	public void onSearch(){
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_modeZoneBaseInv1').clearFilters()");
		
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectModeZoneBaseInv(filters);
       
	}
	
	public void onClear(){
		init();
        if (items != null) {
            items.clear();
        }
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('w_modeZoneBaseInv1').clearFilters()");
    }


	public void onRowEdit(RowEditEvent event) {
		ModeZoneBaseInvBean zoneMode = (ModeZoneBaseInvBean) event.getObject();
	    
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("ModeZone") };
    	String summaryMsgOk =  getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
		zoneMode.setUsername(getUser().getUsername());
    	
    	String error = "0";
		try {
			error = service.updateModeZoneBaseInv(zoneMode);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		String[] par2 = {msgs.getString("ModeZone")};
		
		if(error!=null && error.equals("0")){
    		String msg =  getMessageResourceString("update_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
    		log.info("Zone Mode Updated", "zone mode Updated: " + zoneMode.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-1")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Zone Mode Not Updated", "Error updating Zone Mode " + zoneMode.toString(), Calendar.getInstance().getTime());
    	}
		
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		
		//offshore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectModeZoneBaseInv(filters);
		requestContext.execute("PF('w_modeZoneBaseInv1').filter()");
		

    }
     
    public void onRowCancel(RowEditEvent event) {
    }
    
  
    
    public void cancel() {
    	initNewBean();
    }
    
    
    public void initNewBean(){
    	newModeZoneBaseInv = new ModeZoneBaseInvBean();
    	newModeZoneBaseInv.setStartDate(sysdate.getTime());    	
    }
    
    public void save(){
  	
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("ModeZone") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params); 
    	
    	String error = "0";
		try {
			newModeZoneBaseInv.setUsername(getUser().getUsername());
			error =  service.insertModeZoneBaseInv(newModeZoneBaseInv);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		String[] par2 = {msgs.getString("ModeZone") };
		
		if(error!=null && error.equals("0")){
			String msg = getMessageResourceString("insert_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("Mode Zone Inserted ok" + newModeZoneBaseInv.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-1")){
			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Mode Zone Inserted", "Error inserting area. The " + newModeZoneBaseInv.toString() +" already exists in the System ", Calendar.getInstance().getTime());
		}
		
		//offshore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
    	items = service.selectModeZoneBaseInv(filters);
		onSearch();
    	initNewBean();
    	
    	
    }	
    
    
    public boolean renderItemEditor(ModeZoneBaseInvBean item){
     	
    	if (item.getStartDate().before(sysdate.getTime())) {
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
