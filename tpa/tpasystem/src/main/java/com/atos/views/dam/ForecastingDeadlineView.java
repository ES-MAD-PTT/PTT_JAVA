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
import com.atos.beans.dam.ForecastingDeadlineBean;
import com.atos.filters.dam.ForecastingDeadlineFilter;
import com.atos.services.dam.ForecastingDeadlineService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;


@ManagedBean(name="forecastingDeadlineView")
@ViewScoped
public class ForecastingDeadlineView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(ForecastingDeadlineView.class);

	private ForecastingDeadlineFilter filters;
	private ForecastingDeadlineBean newForecastingDeadline;
	private List<ForecastingDeadlineBean> items;
	
	@ManagedProperty("#{forecastingDeadlineService}")
    transient private ForecastingDeadlineService service;
    
	
	private Calendar sysdate;
	
	
	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	//geters/seters
	 public ForecastingDeadlineFilter getFilters() {
		return filters;
	}

	public void setFilters(ForecastingDeadlineFilter filters) {
		this.filters = filters;
	}
	
	public ForecastingDeadlineBean getNewForecastingDeadline() {
		return newForecastingDeadline;
	}

	public void setNewForecastingDeadline(ForecastingDeadlineBean newForecastingDeadline) {
		this.newForecastingDeadline = newForecastingDeadline;
	}
	
	
	public List<ForecastingDeadlineBean> getItems() {
		return items;
	}
	
	public void setItems(List<ForecastingDeadlineBean> items) {
		this.items = items;
	}
	 
    public void setService(ForecastingDeadlineService service) {
        this.service = service;
    }
    
    @PostConstruct
    public void init() {
    	filters = new ForecastingDeadlineFilter();
    	newForecastingDeadline = new ForecastingDeadlineBean();
   
    	//LOOKING TO THE SYSDATE parameter BD
    	sysdate= gettingValidDateStart();
    	
       	newForecastingDeadline.setStartDate(sysdate.getTime());
    
    	
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
	
    public Map<BigDecimal, Object> getTypes() {
		return service.selectTypes();
	}	

	
	public void onSearch(){
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_forecastingDeadline1').clearFilters()");
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		if (filters.getEndDate()!=null ) {
			if(filters.getStartDate().after(filters.getEndDate())){
				String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
		    	return;
		    }
		}
		items = service.selectForecastingDeadlines(filters) ;
	}
	
		
	public void onClear(){
			//RequestContext.getCurrentInstance().reset("form");
			filters = new ForecastingDeadlineFilter();
			filters.setStartDate(Calendar.getInstance().getTime());
	        if (items != null) {
	            items.clear();
	        }
	}

	
	public void onRowEdit(RowEditEvent event) {
		
		ForecastingDeadlineBean forecastringDeadline = (ForecastingDeadlineBean) event.getObject();
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		String[] params = {msgs.getString("forecastingDeadline") };
    	String summaryMsgOk =  getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
    	
		String error = "0";
		try {
			// delete the record setting end date
			error= service.deleteForecastingDeadline(forecastringDeadline);
			
			//Here NO Updates, all changes must be done are historical Insert
			error = service.updateForecastingDeadline(forecastringDeadline);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		String[] par2 = { forecastringDeadline.getType(),msgs.getString("forecastingDeadline")};
		
		if(error!=null && error.equals("0")){
			String msg =  getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
    		log.info("ForecastringDeadLine Updated", "forecastringDeadline Updated: " + forecastringDeadline.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-1")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Error insertingg ForecastingDeadLine. ForecastingDeadLine, Error: " +error + ". "+forecastringDeadline.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-10")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error Updating ForecastingDeadLine. DeleteForecastingDeadline, Error: " +error + ". "+forecastringDeadline.toString(), Calendar.getInstance().getTime());
    		
    	}else{
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			
			log.info("Error insertingg ForecastingDeadLine. ForecastingDeadLine, Error: " +error + ". "+forecastringDeadline.toString(), Calendar.getInstance().getTime());
		}
		
		//Comentamos la busqueda porque la pantalla no refresca bien despues del update si volvemos a ordenar con la select
		//items = service.selectForecastingDeadlines(filters);
    }
     
    public void onRowCancel(RowEditEvent event) {
    	
        
    }
    
    public void cancel() {
        //RequestContext.getCurrentInstance().reset("formNewEntity");
    	newForecastingDeadline = new ForecastingDeadlineBean();
		newForecastingDeadline.setStartDate(sysdate.getTime());

    }
    
    public void save(){
    	
    	SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
    	Integer valNumDate = valDate.getInteger_exit();
    
    	String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("forecastingDeadline") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    	
    	if(newForecastingDeadline.getStartDate()!=null ){
	    	if(newForecastingDeadline.getStartDate().before(sysdate.getTime())){
		    	//messages.addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,"ForecastingDeadline Not Inserted", "Start Date must be later to sysdate " + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
		    	errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
		    	return;
			}
    	}
    	
    	
    	
    	String error = "0";
		try {
			error =  service.insertForecastingDeadline(newForecastingDeadline);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
    	
    		
		if(error!=null && error.equals("0")){
			String msg = msgs.getString("forecastingDeadline");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("ForecastingDeadline Inserted ok" + newForecastingDeadline.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-1")){
			String msg = msgs.getString("forecastingDeadline");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting forecastringDeadline. ForecastingDeadline Id Oparation not found"  + newForecastingDeadline.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-2")){
			String msg = msgs.getString("forecastingDeadline");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
    		log.info("Error inserting forecastringDeadline. ForecastringDeadline, Error: " +error + ". "+newForecastingDeadline.toString(), Calendar.getInstance().getTime());
		}else{
			String msg = msgs.getString("forecastingDeadline");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error insertingg forecastringDeadline. ForecastringDeadline, Error: " +error + ". "+newForecastingDeadline.toString(), Calendar.getInstance().getTime());
		}
		
		
		onSearch();
		//clean the formu new after save
		newForecastingDeadline = new ForecastingDeadlineBean();
		
		//StartDate New => sysdate +1
	   	newForecastingDeadline.setStartDate(sysdate.getTime());

      }
    
    //Control methods dates
    
	public String disabledField(ForecastingDeadlineBean item) {
	   //no dateEnd
		//1.	En caso de un registro con la fecha Start Date posterior al día actual, el sistema habilitará Todos
		// se añade el equal
		 			
 	if(item.getStartDate().after(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime())){
 		return "false";
	}
		 		
 		
 		return "true";
			
	}
    
	
    public boolean renderItemEditor(ForecastingDeadlineBean item){
    	if (item.getStartDate().before(sysdate.getTime()) ) {
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
