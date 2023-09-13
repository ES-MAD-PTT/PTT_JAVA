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
import com.atos.beans.dam.TravellingTimeBean;
import com.atos.filters.dam.TravellingTimeFilter;
import com.atos.services.dam.TravellingTimeService;
import com.atos.utils.Constants;
import com.atos.views.ChangeSystemView;
import com.atos.views.CommonView;


@ManagedBean(name="travellingTimeView")
@ViewScoped
public class TravellingTimeView  extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(TravellingTimeView.class);
	
	private TravellingTimeFilter filters;
	private TravellingTimeBean newTravellingTime;
	private List<TravellingTimeBean> items;
	

	@ManagedProperty("#{travellingTimeService}")
    transient private TravellingTimeService service;
	
	private Calendar sysdate;
	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	
	public TravellingTimeFilter getFilters() {
		return filters;
	}

	public void setFilters(TravellingTimeFilter filters) {
		this.filters = filters;
	}
	
	public TravellingTimeBean getNewTravellingTime() {
		return newTravellingTime;
	}

	public void setNewTravellingTime(TravellingTimeBean newTravellingTime) {
		this.newTravellingTime = newTravellingTime;
	}
	
	
	public List<TravellingTimeBean> getItems() {
		return items;
	}
	
	public void setItems(List<TravellingTimeBean> items) {
		this.items = items;
	}
	 
    public void setService(TravellingTimeService service) {
        this.service = service;
    }
    
    
	@PostConstruct
    public void init() {
    	filters = new TravellingTimeFilter();
    	newTravellingTime = new TravellingTimeBean();
     	
    	//LOOKING TO THE SYSDATE parameter BD
    	sysdate= gettingValidDateStart();
    	
    	//StartDate New => sysdate +n
    	newTravellingTime.setStartDate(sysdate.getTime());
  
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


                                 
	public Map<BigDecimal, Object> getIdsOriPointSystem() {
		return service.getIdsOriPointSystem(getChangeSystemView().getIdn_active());
    }
	

	public Map<BigDecimal, Object> getIdsDestPointSystem() {
		return service.getIdsDestPointSystem(getChangeSystemView().getIdn_active());
    }
	

	// Methods
	public void onSearch(){
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_travellingTime1').clearFilters()");
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
	
		if (filters.getEndDate()!=null ) {
			if(filters.getStartDate().after(filters.getEndDate())){
				String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
		    	return;
		    }
		}
		
		//offShore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectTravellingTimes(filters);
       
	}
	
	
	public void onClear(){
	  //  RequestContext.getCurrentInstance().reset("form");
		filters = new TravellingTimeFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
		
        if (items != null) {
            items.clear();
        }
        
        RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_travellingTime1').clearFilters()");
   }
	
	

    public void onRowCancel(RowEditEvent event) {
    }
    
  
    
    public void cancel() {
       // RequestContext.getCurrentInstance().reset("formNewEntity");
    	initNewBean();
    }
    
    
    public void initNewBean(){
    	newTravellingTime = new TravellingTimeBean();
    	newTravellingTime.setStartDate(sysdate.getTime());    	
    }
    
    public void save(){
    	
    	String errorMsg = null; 	
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
     	

    	String[] params = {msgs.getString("travellingTime") };
    	String summaryMsgOk = CommonView.getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk= CommonView.getMessageResourceString("insert_noOk", params);
    
    	
    	if(newTravellingTime.getStartDate()!=null ){
	    	if(newTravellingTime.getStartDate().before(sysdate.getTime())){
		    	errorMsg = msgs.getString("error_today"); //error_today= Start Date must be later than today
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
		    	return;
			}
    	}
    	
    	if(newTravellingTime.getIdn_origin_point().equals(null) ){
    		
    		String errMsg = msgs.getString("the_mandatory_field_error");//the_mandatory_field_error=The mandatory field XXX must be informed.
        	String strFieldLabel = "XXX";
        	errMsg = errMsg.replace(strFieldLabel, msgs.getString("travellingTime_Origin_Point_ID"));
	    	getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsgNotOk, errMsg, Calendar.getInstance().getTime()));
	    	log.error(errMsg);
	    	return;
    	}
    	
    	if(newTravellingTime.getIdn_destination_point().equals(null) ){
    		
    		String errMsg = msgs.getString("the_mandatory_field_error");
        	String strFieldLabel = "XXX";
        	errMsg = errMsg.replace(strFieldLabel, msgs.getString("travellingTime_Destination_Point_ID"));
	    	getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsgNotOk, errMsg, Calendar.getInstance().getTime()));
	    	log.error(errMsg);
	    	return;
    	}
    	
    	
    	if(newTravellingTime.getIdn_origin_point().equals(newTravellingTime.getIdn_destination_point())){
    		errorMsg = msgs.getString("error_ori_dest_point");
	    	getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
	    	return;
    	}
    	
    	
    	if (newTravellingTime.getTravelllingTime() != null) {
    		if (newTravellingTime.getTravelllingTime().compareTo(BigDecimal.ZERO) < 0) {
    			errorMsg = msgs.getString("error_trav_days");
    	    	getMessages().addMessage(Constants.head_menu[0],
    					new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
    	    	log.error(errorMsg);
    			return;
    		}
    	}
    	
   	
    	String error = "0";
		try {
			error =  service.insertTravellingTime(newTravellingTime);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		newTravellingTime.setOriginPointCode(service.getOriginCode(newTravellingTime));
		newTravellingTime.setDestinationPointCode(service.getDestCode(newTravellingTime));
	
		
		if(error!=null && error.equals("0")){
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, summaryMsgOk + " Origin: " + newTravellingTime.getOriginPointCode() + ", Destination: " + newTravellingTime.getDestinationPointCode(), Calendar.getInstance().getTime()));
			log.info("TravellingTime Inserted ok" + newTravellingTime.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-1")){
			errorMsg = msgs.getString("travellingTime_already_registered");//travelling Time data is already registered for this Origin Point and its Destination Point
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
			log.error("Error inserting Travelling Time. Already exist The Point Origin: " + newTravellingTime.getOriginPointCode() + " and  Destination: " + newTravellingTime.getDestinationPointCode(),  Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-2")){
			errorMsg = msgs.getString("travellingTime_error_ins");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
			log.error("Error inserting Travelling Time. Error inserting Table TravelllingTime: " + newTravellingTime.toString(), Calendar.getInstance().getTime());
		}
		
	    onSearch();
    	initNewBean();
    }
    
    public String enabledTravellingDay() throws Exception{
  
    	if (newTravellingTime.getIdn_origin_point()!=null &&  newTravellingTime.getDestination()!=null ){
    		List<BigDecimal> list = service.selectTravellingTimeDays(newTravellingTime);
    		
        	//si no existe para p-origen. Creamos uno nuevo
        	if (list.size() == 0 ){
        		if(newTravellingTime.getDestination().equals("Y")){ //si destino es Y, ponemos 1 por defecto y dejamos cambiar
             		return "false";	
        		}else{ //si destino es No, ponemos 0 por defecto y no dejamos camiarlo
             		return "true";
        		}
         	}else { //si ya existe para P-origen
	        		int res =list.get(0).compareTo(BigDecimal.ZERO);
	        		if(res == 0){
	        			if(newTravellingTime.getDestination().equals("N")){
	        				return "true";
	        			}else{
	        				return "false";
	        			}
	        			
	        		}else{
	        			return "true";
	        		}
        			
        		}
        		
        }else{

			return "false";
    	} 
    }
    
    
    
 public void findTravellingTime(TravellingTimeBean newTravellingTime) throws Exception{
    	

    	if (newTravellingTime.getIdn_origin_point()!=null &&  newTravellingTime.getDestination()!=null ){
    		
    		List<BigDecimal> list = service.selectTravellingTimeDays(newTravellingTime);
    		
        	//si no existe para p-origen. Creamos uno nuevo
        	if (list.size() == 0 ){
        		
        		if(newTravellingTime.getDestination().equals("Y")){ //si destino es Y, ponemos 1 por defecto y dejamos cambiar
        			newTravellingTime.setTravelllingTime(BigDecimal.ONE);
        		}else{ //si destino es No, ponemos 0 por defecto y no dejamos camiarlo
        			newTravellingTime.setTravelllingTime(BigDecimal.ZERO);
        		}
        	}else { //si ya existe para P-origen
	        		int res =list.get(0).compareTo(BigDecimal.ZERO);
	        		if(res == 0){ //si se ha metido un con destino a N el valor sera cero 
	        			if(newTravellingTime.getDestination().equals("N")){//si destino es N el valor debe ir a zero
	        				newTravellingTime.setTravelllingTime(BigDecimal.ZERO); 
	        			}else{
	        				newTravellingTime.setTravelllingTime(BigDecimal.ONE); //puede meter lo que quiera pero por defecto 1
	        			}
	        			
	        		}else{//si metio el destino diferente a N
	        			
	        			if(newTravellingTime.getDestination().equals("N")){//si destino es N el valor debe ir a zero
	        				newTravellingTime.setTravelllingTime(BigDecimal.ZERO);
	        			}else{ //si el destino es Yes y ya habia otro metido, ponemos ese y no se lo dejamos cambiar
	        				newTravellingTime.setTravelllingTime(list.get(0));
	        			}
	        			
	        		}
        			
        		}
        		
        }else{
	    	newTravellingTime.setTravelllingTime(null);
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
