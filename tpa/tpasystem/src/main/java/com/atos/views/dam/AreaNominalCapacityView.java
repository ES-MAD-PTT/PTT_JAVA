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
import com.atos.beans.dam.AreaNominalCapacityBean;
import com.atos.filters.dam.AreaNominalCapacityFilter;
import com.atos.services.dam.AreaNominalCapacityService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="areaNominalCapacityView")
@ViewScoped
public class AreaNominalCapacityView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(AreaNominalCapacityView.class);

	private AreaNominalCapacityFilter filters;
	private AreaNominalCapacityBean newAreaNominalCapacity;
	private List<AreaNominalCapacityBean> items;
	
	@ManagedProperty("#{areaNominalCapacityService}")
    transient private AreaNominalCapacityService service;
    
    public void setService(AreaNominalCapacityService service) {
        this.service = service;
    }
    
   
    private Calendar sysdate;
    public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	
	public AreaNominalCapacityFilter getFilters() {
		return filters;
	}

	public void setFilters(AreaNominalCapacityFilter filters) {
		this.filters = filters;
	}
	
	public AreaNominalCapacityBean getNewAreaNominalCapacity() {
		return newAreaNominalCapacity;
	}

	public void setNewAreaNominalCapacity(AreaNominalCapacityBean newAreaNominalCapacity) {
		this.newAreaNominalCapacity = newAreaNominalCapacity;
	}
	
	
	public List<AreaNominalCapacityBean> getItems() {
		return items;
	}
	
	public void setItems(List<AreaNominalCapacityBean> items) {
		this.items = items;
	}
	 
    @PostConstruct
    public void init() {
    	filters = new AreaNominalCapacityFilter();
    	newAreaNominalCapacity = new AreaNominalCapacityBean();
    	 	   
        //LOOKING TO THE SYSDATE parameter BD
      	sysdate= gettingValidDateStart();
    	newAreaNominalCapacity.setStartDate(sysdate.getTime());
    	
    }
	
	 //sysdte +1
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
	public Map<BigDecimal, Object> getAreasSystem() {
		return service.selectAreaNominalCapacityAreaSystem(getChangeSystemView().getIdn_active());
	}
	
	public Map<BigDecimal, Object> getYears() {	
		int year = sysdate.get(Calendar.YEAR);
		return service.selectAreaNominalCapacityYear(year);
	}
	

	// Methods
	public void onSearch(){
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_areaNominalCapacity1').clearFilters()");
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg"); 
		
		
		if (filters.getEndDate()!=null ) {
			if(filters.getStartDate().after(filters.getEndDate())){
				String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
				return;
		    }
		}
		
		//offshore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		
		items = service.selectAreaNominalCapacitys(filters);
        
	}
	
	public void onClear(){
		//RequestContext.getCurrentInstance().reset("form");
		filters = new AreaNominalCapacityFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
        if (items != null) {
            items.clear();
        }
    }



	
	public void onRowEdit(RowEditEvent event) {
  
    	AreaNominalCapacityBean areaNominalCapacity = (AreaNominalCapacityBean) event.getObject();
    	
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		String[] params = {msgs.getString("areaNominalCapacity") };
    	String summaryMsgOk =  getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
    	
    	String error = "0";
        
    try {
    		// delete the record setting end date
	    	error= service.deleteAreaNominalCapacity(areaNominalCapacity);
	    	
			//Here NO Updates, all changes must be done are historical Insert
			error = service.insertAreaNominalCapacity(areaNominalCapacity);
			
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
    
    	String[] par2 = { areaNominalCapacity.getArea_code(),msgs.getString("areaNominalCapacity")};
    	
		if(error!=null && error.equals("0")){
			String msg =  getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("AreaNominalCapacity Updated", "AreaNominalCapacity Updated: " + areaNominalCapacity.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-1")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Error inserting AreaNominalCapacity. AreaNominalCapacity, Error: " +error + ". "+areaNominalCapacity.toString(), Calendar.getInstance().getTime());
    		
    	}else if (error!=null && error.equals("-10")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Error Updating AreaNominalCapacity. AreaNominalCapacity, Error: " +error + ". "+areaNominalCapacity.toString(), Calendar.getInstance().getTime());
    		
    	}else{
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error insertingg AreaNominalCapacity. AreaNominalCapacity, Error: " +error + ". "+areaNominalCapacity.toString(), Calendar.getInstance().getTime());
		}
		
//		Comentamos la busqueda porque la pantalla no refresca bien despues del update si volvemos a ordenar con la select
		//items = service.selectAreaNominalCapacitys(filters);
    }
     
    public void onRowCancel(RowEditEvent event) {
    	
    }
    
    public void cancel() {
    	//  RequestContext.getCurrentInstance().reset("formNewEntity");
    	newAreaNominalCapacity = new AreaNominalCapacityBean();
    	newAreaNominalCapacity.setStartDate(sysdate.getTime());
    }
    
    
    public void save(){
    	
    	SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
    	Integer valNumDate = valDate.getInteger_exit();
    	
    	
    	String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("areaNominalCapacity") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    	
    	
    	if(newAreaNominalCapacity.getStartDate()!=null ){
	    	if(newAreaNominalCapacity.getStartDate().before(sysdate.getTime())){
		    	//messages.addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,"AreaNominalCapacity Not Inserted", "Start date must be later to sysdate " + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
		    	errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
	    		getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
		    	return;
			}
    	}
    	
    	
    	String error = "0";
		try {
			error =  service.insertAreaNominalCapacity(newAreaNominalCapacity);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
    	
		if(error!=null && error.equals("0")){
			String msg = msgs.getString("areaNominalCapacity");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("AreaNominalCapacity Inserted ok" + newAreaNominalCapacity.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-1")){
			String msg = msgs.getString("areaNominalCapacity");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting AreaNominalCapacity. AreaNominalCapacity, Error: " +error + ". "+ newAreaNominalCapacity.toString(), Calendar.getInstance().getTime());
		}else{
			String msg = msgs.getString("areaNominalCapacity");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error insertingg AreaNominalCapacity. AreaNominalCapacity, Error: " +error + ". "+newAreaNominalCapacity.toString(), Calendar.getInstance().getTime());
		}
    	
		//offshore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
    	//items = service.selectAreaNominalCapacitys(filters);
		onSearch();
    	
    	//clean the formu new after save
    	newAreaNominalCapacity = new AreaNominalCapacityBean();
    	
    	//StartDate New => sysdate +1
    	newAreaNominalCapacity.setStartDate(sysdate.getTime());
    	
    }
    
    //Control methods dates
   	
   public String disabledField(AreaNominalCapacityBean item) {
	 //1.	En caso de un registro con la fecha Start Date posterior al día actual, el sistema habilitará Todos
		
	   if(item.getStartDate().after(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime())){
		   return "false";
	   }

	  	return "true";   	
	    	
	}
    
    
    public boolean renderItemEditor(AreaNominalCapacityBean item){
     		
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
