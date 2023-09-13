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
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.AreaBean;
import com.atos.filters.dam.AreaFilter;
import com.atos.services.dam.AreaService;
import com.atos.utils.Constants;
import com.atos.views.ChangeSystemView;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;


@ManagedBean(name="areaView")
@ViewScoped
public class AreaView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(AreaView.class);
	
	private AreaFilter filters;
	private AreaBean newArea;
	private List<AreaBean> items;
	
	@ManagedProperty("#{areaService}")
    transient private AreaService service;
    
	
		
	private Map<BigDecimal, Object> comboSystems;
	
	//offshore, El combo zona ahora es constante no depende de system se puede cargar en el ini
	private Map<BigDecimal, Object> comboZones;
		
	private Calendar sysdate;
	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}


	public AreaFilter getFilters() {
		return filters;
	}

	public void setFilters(AreaFilter filters) {
		this.filters = filters;
	}
	
	public AreaBean getNewArea() {
		return newArea;
	}

	public void setNewArea(AreaBean newArea) {
		this.newArea = newArea;
	}
	
	
	public List<AreaBean> getItems() {
		return items;
	}
	
	public void setItems(List<AreaBean> items) {
		this.items = items;
	}
	 
    public void setService(AreaService service) {
        this.service = service;
    }
    

	@PostConstruct
    public void init() {
    	filters = new AreaFilter();
    	newArea = new AreaBean();
     	
    	//LOOKING TO THE SYSDATE parameter BD
    	sysdate= gettingValidDateStart();
    	
    	//StartDate New => sysdate +n
    	newArea.setStartDate(sysdate.getTime());
    	    	
    	//offshore
    	comboSystems=service.selectSystems(getChangeSystemView().getIdn_active());
    	comboZones= service.selectZonesSystem(getChangeSystemView().getIdn_active());
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


    //offshore
	public Map<BigDecimal, Object> getAreaIdSystem() {
		return service.selectIds(getChangeSystemView().getIdn_active());
    }
	
	public List<String> nameList(String query) {
		query = "%" + query + "%";
		return service.selectNames(query);
    }
	
	
	
	public Map<BigDecimal, Object> getSystems() {
		//return service.selectSystems();
		return comboSystems;
	}
	
	public Map<BigDecimal, Object> getZonesSystem() {
		return comboZones;
	}

	// Methods
	public void onSearch(){
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_area1').clearFilters()");
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
	
		if (filters.getEndDate()!=null ) {
			if(filters.getStartDate().after(filters.getEndDate())){
		    	String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
		    	return;
		    }
		}
		
		//offShore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectAreas(filters);
       
	}
	
	public void onClear(){
	  //  RequestContext.getCurrentInstance().reset("form");
		filters = new AreaFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
		
        if (items != null) {
            items.clear();
        }
        
        RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_area1').clearFilters()");
    }



	public void onRowEdit(RowEditEvent event) {
		AreaBean area = (AreaBean) event.getObject();
	  	
		String errorMsg = null;
	    
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("area") };
    	String summaryMsgOk =  getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
		
      	if(area.getEndDate()!=null ){
    		if(area.getStartDate().after(area.getEndDate())){
    	    	errorMsg = msgs.getString("start_previous_end"); //start_previous_end= Start date must be previous to end date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
    	    	
		    	items = service.selectAreas(filters);
    	    	return;
    		}
    		
    	}
    		
    	
    	String error = "0";
		try {
			error = service.updateArea(area);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		String[] par2 = { area.getId(),msgs.getString("area")};
		
		if(error!=null && error.equals("0")){
    		String msg =  getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
    		log.info("Area Updated", "area Updated: " + area.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-1")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Area Not Updated", "Error updating  Area " + area.toString(), Calendar.getInstance().getTime());
    	}
		
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		
		//offshore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectAreas(filters);
		requestContext.execute("PF('w_area1').filter()");

    }
     
    public void onRowCancel(RowEditEvent event) {
    }
    
  
    
    public void cancel() {
       // RequestContext.getCurrentInstance().reset("formNewEntity");
    	initNewBean();
    }
    
    
    public void initNewBean(){
    	newArea = new AreaBean();
    	newArea.setStartDate(sysdate.getTime());    	
    }
    
    public void save(){
  	
		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
    	Integer valNumDate = valDate.getInteger_exit();
    
    	String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("area") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    	
    	if(newArea.getStartDate()!=null ){
	    	if(newArea.getStartDate().before(sysdate.getTime())){
		    	errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
			
		    	return;
			}
    	}
    	
    	if(newArea.getEndDate()!=null ){
	    	if (newArea.getEndDate().before(sysdate.getTime())  ){
				errorMsg = msgs.getString("error_endDate_sysdate"); //error_endDate_sysdate= End Date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
		    	return;
			}
	    	
	    	if(newArea.getStartDate().after(newArea.getEndDate())){
	    		errorMsg = msgs.getString("error_startEarlierEnd"); //error_startEarlierEnd = Start Date must be earlier or equal to End Date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg );
		    	return;
	    	}
	    		
    	}
    	
    	String error = "0";
		try {
			error =  service.insertArea(newArea);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		String[] par2 = {newArea.getId(),msgs.getString("area") };
		
		if(error!=null && error.equals("0")){
			String msg = getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("Area Inserted ok" + newArea.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-1")){
			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Area Inserted", "Error inserting area. The " + newArea.toString() +" already exists in the System ", Calendar.getInstance().getTime());
		}
		
		//offshore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
    	//items = service.selectAreas(filters);
		onSearch();
    	initNewBean();
    	
    }
    
    	
    public String disabledEndDate(AreaBean item){
    	if(item.getEndDate()!=null ){
    		if (item.getStartDate().before(sysdate.getTime()) && (item.getEndDate().before(sysdate.getTime()))){
        		return "true";
        	}	
    	}   	    	
    	return "false";
    }
    	
    
    public String disabledField(AreaBean item) {
    	//1.	En caso de un registro con la fecha Start Date posterior al día actual, el sistema habilitará Todos
 		//2.	En caso de un registro con la fecha Start Date anterior o igual al día actual y 
 			 //fecha End Date posterior al día actual (o vacía), el sistema habilitará SOLO END DATE
 		//3.	En caso de un registro con la fecha Start Date anterior o igual al día actual, se habilita  NADA 
 		
    	//18/7/2016 modificado para que edite cuando la fecha startDay sea igual al sysdate
    	
 		/*if(item.getStartDate().after(sysdate.getTime())){
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
    
    
    public boolean renderItemEditor(AreaBean item){
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
