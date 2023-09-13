package com.atos.views.dam;

import java.io.Serializable;
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
import com.atos.beans.dam.OperatorBean;
import com.atos.filters.dam.OperatorFilter;
import com.atos.services.dam.OperatorService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="operatorView")
@ViewScoped
public class OperatorView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(OperatorView.class);

	private OperatorFilter filters;
	private OperatorBean newOperator;
	private List<OperatorBean> items;
	
	@ManagedProperty("#{operatorService}")
    transient private OperatorService service;
    
    public void setService(OperatorService service) {
        this.service = service;
    }

    private Calendar sysdate;
    public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}


	 public OperatorFilter getFilters() {
		return filters;
	}

	public void setFilters(OperatorFilter filters) {
		this.filters = filters;
	}
	
	public OperatorBean getNewOperator() {
		return newOperator;
	}

	public void setNewOperator(OperatorBean newOperator) {
		this.newOperator = newOperator;
	}
	
	
	public List<OperatorBean> getItems() {
		return items;
	}
	
	public void setItems(List<OperatorBean> items) {
		this.items = items;
	}
   
    
	@PostConstruct
    public void init() {
    	filters = new OperatorFilter();
    	newOperator = new OperatorBean();
    	
    	//LOOKING TO THE SYSDATE parameter BD
      	sysdate= gettingValidDateStart();
    	newOperator.setStartDate(sysdate.getTime());
    	
    	
    }
	
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
    
	public Map<String, Object> getIds() {
		return service.selectOperatorId();
	}

	public List<String> nameList(String query) {
		query = "%" + query + "%";
		return service.selectName(query);
    }
	

	// Methods
	public void onSearch(){
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_operator1').clearFilters()");
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
	
		if (filters.getEndDate()!=null ) {
			if(filters.getStartDate().after(filters.getEndDate())){
				String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
		    	return;
		    }
		}
		items = service.selectOperators(filters);
	}
	
	public void onClear(){
	//	RequestContext.getCurrentInstance().reset("form");
		filters = new OperatorFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
        if (items != null) {
            items.clear();
        }
    }



	public void onRowEdit(RowEditEvent event) {
  
    	OperatorBean operator = (OperatorBean) event.getObject();
    	//SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
    	//Integer valNumDate = valDate.getInteger_exit();
    
    	String errorMsg = null;
    
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("operator") };
    	String summaryMsgOk =  getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
    	
    	
    	if(operator.getEndDate()!=null){
    		if(operator.getStartDate().after(operator.getEndDate())){
    			errorMsg = msgs.getString("start_previous_end"); //start_previous_end= Start date must be previous to end date
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
    	    	items = service.selectOperators(filters);
    	    	return;
    		}
    		
    	}
    	
    	String error = "0";
		try {
			error = service.updateOperator(operator);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		String[] par2 = { operator.getId(),msgs.getString("operator") };
		
		if(error!=null && error.equals("0")){
  			String msg =  getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
    		log.info("Operator Updated: " + operator.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-1")){
      		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Operator Not Updated", "Error updating  User Group " + operator.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-2")){
      		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Operator Not Updated", "Error updating operator " + operator.toString(), Calendar.getInstance().getTime());
    	}
		items = service.selectOperators(filters);	    	
    	
    }
     
    public void onRowCancel(RowEditEvent event) {
    	
    }
    
    public void cancel() {
        //RequestContext.getCurrentInstance().reset("formNewEntity");
    	
    	newOperator = new OperatorBean();
    	newOperator.setStartDate(sysdate.getTime());
    }
    
    
    public void save(){
    	
		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
    	Integer valNumDate = valDate.getInteger_exit();
    	
    	String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("operator") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    
    	if(newOperator.getStartDate()!=null ){//no se puede dar.. la pantalla no deja
	    	if(newOperator.getStartDate().before(sysdate.getTime())){
	    		errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
		    	return;
			}
    	}
    	
    	if(newOperator.getEndDate()!=null ){
	    	if (newOperator.getEndDate().before(sysdate.getTime())  ){//no se puede dar .. la pantalla no deja
	    		errorMsg = msgs.getString("error_endDate_sysdate"); //error_endDate_sysdate= End Date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
		    	return;
			}
	    	if(newOperator.getStartDate().after(newOperator.getEndDate())){
	    		errorMsg = msgs.getString("error_startEarlierEnd"); //error_startEarlierEnd = Start Date must be earlier or equal to End Date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg );
		    	return;
	    	}
    	}
    	
    	String error = "0";
		try {
			error =  service.insertOperator(newOperator);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
	
		String[] par2 = { newOperator.getId(),msgs.getString("operator") };
		if(error!=null && error.equals("0")){
			String msg =  getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("Operator Inserted ok" + newOperator.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-1")){
			String msg =  getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));			
			log.info("Error inserting Operator. The " + newOperator.toString() +" already exists in the System ", Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-2")){
    		String msg =  getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));    		
    		log.info("Error inserting operator. Error inserting User Group"  + newOperator.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-3")){
			String msg =  getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Operator Not Inserted", "Error inserting operator. Error inserting Operator"  + newOperator.toString(), Calendar.getInstance().getTime());
    		
		}

		onSearch();
    	
    	//cargamos los operator para que se refleje la nueva alta
    	service.selectOperatorId();
    	
    	//clean the formu new after save
    	newOperator = new OperatorBean();
    	
    	//StartDate New => sysdate +1
    	newOperator.setStartDate(sysdate.getTime());
    }
    
    //Control methods dates
   	
    public String disabledEndDate(OperatorBean item){
    	if(item.getEndDate()!=null ){
    		if (item.getStartDate().before(sysdate.getTime()) && (item.getEndDate().before(sysdate.getTime()))){
        		return "true";
        	}	
    	}   	    	
    	return "false";
    }
    
	public String disabledField(OperatorBean item) {
    	//1.	En caso de un registro con la fecha Start Date posterior al día actual, el sistema habilitará Todos
 		//2.	En caso de un registro con la fecha Start Date anterior o igual al día actual y 
 			 //fecha End Date posterior al día actual (o vacía), el sistema habilitará SOLO END DATE
 		//3.	En caso de un registro con la fecha Start Date anterior o igual al día actual, se habilita  NADA
		
		//18/7/2016 modificado para que edite cuando la fecha startDay sea igual al sysdate
 		
 /*		if(item.getStartDate().after(sysdate.getTime())){
 			return "false";			
 		}
 		
 		if(item.getEndDate()!=null ){
	 		if (item.getStartDate().before(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime()) 
	 				&& ( item.getEndDate().after(sysdate.getTime()) || item.getEndDate().equals(""))){			
	 			return "true";
	 		}
 		}*/
		
		
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
    
    
    public boolean renderItemEditor(OperatorBean item){
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
