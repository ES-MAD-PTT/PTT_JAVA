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
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ContractDeadlineSpecialBean;
import com.atos.filters.dam.ContractDeadlineSpecialFilter;
import com.atos.services.dam.ContractDeadlineSpecialService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;


@ManagedBean(name="contractDeadlineSpecialView")
@ViewScoped
public class ContractDeadlineSpecialView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(ContractDeadlineSpecialView.class);

	private ContractDeadlineSpecialFilter filters;
	private ContractDeadlineSpecialBean newContractDeadlineSpecial;
	private List<ContractDeadlineSpecialBean> items;
	
	@ManagedProperty("#{contractDeadlineSpecialService}")
    transient private ContractDeadlineSpecialService service;
    
	
	private Calendar sysdate;
	
	
	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	//geters/seters
	 public ContractDeadlineSpecialFilter getFilters() {
		return filters;
	}

	public void setFilters(ContractDeadlineSpecialFilter filters) {
		this.filters = filters;
	}
	
	public ContractDeadlineSpecialBean getNewContractDeadlineSpecial() {
		return newContractDeadlineSpecial;
	}

	public void setNewContractDeadlineSpecial(ContractDeadlineSpecialBean newContractDeadlineSpecial) {
		this.newContractDeadlineSpecial = newContractDeadlineSpecial;
	}
	
	
	public List<ContractDeadlineSpecialBean> getItems() {
		return items;
	}
	
	public void setItems(List<ContractDeadlineSpecialBean> items) {
		this.items = items;
	}
	 
    public void setService(ContractDeadlineSpecialService service) {
        this.service = service;
    }
    
    @PostConstruct
    public void init() {
    	filters = new ContractDeadlineSpecialFilter();
    	newContractDeadlineSpecial = new ContractDeadlineSpecialBean();
   
    	//LOOKING TO THE SYSDATE parameter BD
    	sysdate= gettingValidDateStart();
    	
       	//newContractDeadlineSpecial.setStartDate(sysdate.getTime());
    
    	
    }
	
    //sysdte +1
    public Calendar gettingValidDateStart(){
    	
    	SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
    	Integer valNumDate = valDate.getInteger_exit();
    	Calendar sysdate = Calendar.getInstance(); 
    	//StartDate filter => sysdate
    	//filters.setStartDate(sysdate.getTime());
    	
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
		requestContext.execute("PF('w_contractDeadlineSpecial1').clearFilters()");
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		if (filters.getEndDate()!=null ) {
			if(filters.getStartDate().after(filters.getEndDate())){
				String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
		    	return;
		    }
		}
		items = service.selectContractDeadlineSpecials(filters) ;
	}
	
		
	public void onClear(){
			//RequestContext.getCurrentInstance().reset("form");
			filters = new ContractDeadlineSpecialFilter();
			//filters.setStartDate(Calendar.getInstance().getTime());
	        if (items != null) {
	            items.clear();
	        }
	}

	
	public void onRowEdit(RowEditEvent event) {
		
		ContractDeadlineSpecialBean contractDeadlineSpecial = (ContractDeadlineSpecialBean) event.getObject();
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		String[] params = {msgs.getString("contractDeadlineSpecial") };
		String summaryMsgOk =  getMessageResourceString("update_ok", params);
		String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
		
		int val = dateValidation(contractDeadlineSpecial);
		
		if(val==0) {
		
			String error = "0";
			try {
				// delete the record setting end date
				error= service.deleteContractDeadlineSpecial(contractDeadlineSpecial);
				
				//Here NO Updates, all changes must be done are historical Insert
				error = service.updateContractDeadlineSpecial(contractDeadlineSpecial);
			} catch(Exception e) {
				log.catching(e);
				// we assign the return message 
				error = e.getMessage();
			}
			
			String[] par2 = { contractDeadlineSpecial.getOperation_desc(),msgs.getString("contractDeadlineSpecial")};
			
			if(error!=null && error.equals("0")){
				String msg =  getMessageResourceString("updating_ok", par2);
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
	    		log.info("ContractDeadLine Updated", "contractDeadlineSpecial Updated: " + contractDeadlineSpecial.toString(), Calendar.getInstance().getTime());
	    	}else if (error!=null && error.equals("-1")){
	    		String msg =  getMessageResourceString("error_updating", par2);
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
	    		log.info("Error inserting ContractDeadLine. ContractDeadLine, Error: " +error + ". "+contractDeadlineSpecial.toString(), Calendar.getInstance().getTime());
	    	}else if (error!=null && error.equals("-10")){
	    		String msg =  getMessageResourceString("error_updating", par2);
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
				log.info("Error Updating ContractDeadLine. DeleteContractDeadlineSpecial, Error: " +error + ". "+contractDeadlineSpecial.toString(), Calendar.getInstance().getTime());
	    		
	    	}else{
	    		String msg =  getMessageResourceString("error_updating", par2);
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
				
				log.info("Error inserting ContractDeadLine. ContractDeadLine, Error: " +error + ". "+contractDeadlineSpecial.toString(), Calendar.getInstance().getTime());
			}
		}
		else {			
			onSearch();			
		}
		
		//Comentamos la busqueda porque la pantalla no refresca bien despues del update si volvemos a ordenar con la select
		//items = service.selectContractDeadlineSpecials(filters);
    }
     
    public void onRowCancel(RowEditEvent event) {
    	
        
    }
    
    public void cancel() {
        //RequestContext.getCurrentInstance().reset("formNewEntity");
    	newContractDeadlineSpecial = new ContractDeadlineSpecialBean();
		//newContractDeadlineSpecial.setStartDate(sysdate.getTime());

    }
    
    public void save(){
    	
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	String[] params = {msgs.getString("contractDeadlineSpecial") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    	    	
    	int val = dateValidation(newContractDeadlineSpecial);
  
    	if(val==0) {
	    	String error = "0";
			try {
				error =  service.insertContractDeadlineSpecial(newContractDeadlineSpecial);
			} catch(Exception e) {
				log.catching(e);
				// we assign the return message 
				error = e.getMessage();
			}
	    	
	    		
			if(error!=null && error.equals("0")){
				String msg = msgs.getString("contractDeadlineSpecial");
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
				log.info("ContractDeadlineSpecial Inserted ok" + newContractDeadlineSpecial.toString(), Calendar.getInstance().getTime());
			}else if(error!=null && error.equals("-1")){
				String msg = msgs.getString("contractDeadlineSpecial");
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
				log.info("Error inserting contractDeadlineSpecial. ContractDeadlineSpecial Id Oparation not found"  + newContractDeadlineSpecial.toString(), Calendar.getInstance().getTime());
			}else if(error!=null && error.equals("-2")){
				String msg = msgs.getString("contractDeadlineSpecial");
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
	    		log.info("Error inserting contractDeadlineSpecial. ContractDeadlineSpecial, Error: " +error + ". "+newContractDeadlineSpecial.toString(), Calendar.getInstance().getTime());
			}else{
				String msg = msgs.getString("contractDeadlineSpecial");
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
				log.info("Error inserting contractDeadlineSpecial. ContractDeadlineSpecial, Error: " +error + ". "+newContractDeadlineSpecial.toString(), Calendar.getInstance().getTime());
			}
			
			
			onSearch();
			//clean the formu new after save
			newContractDeadlineSpecial = new ContractDeadlineSpecialBean();
			
			//StartDate New => sysdate +1
		   	//newContractDeadlineSpecial.setStartDate(sysdate.getTime());
    	}

      }
    
    //Control methods dates
    
	public String disabledField(ContractDeadlineSpecialBean item) {
	   //no dateEnd
		//1.	En caso de un registro con la fecha Start Date posterior al día actual, el sistema habilitará Todos
		// se añade el equal
		 			
 	if(item.getStartDate().after(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime())){
 		return "false";
	}
		 		
 		
 		return "true";
			
	}
	
	public int dateValidation(ContractDeadlineSpecialBean contractDeadlineSpecial) {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
    	Integer valNumDate = valDate.getInteger_exit();
    	
    	Date today = new Date();
    	Date t = getZeroTimeDate(today);
		String errorMsg = null;
		String[] params = {msgs.getString("contractDeadlineSpecial") };
    	
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
    	
    	//Control de fechas para contratos de CORTO PLAZO
/*    	if(contractDeadlineSpecial.getIdn_operation_term().equals(new BigDecimal(3)) ||
    			contractDeadlineSpecial.getIdn_operation_term().equals(new BigDecimal(4)) ||
    			contractDeadlineSpecial.getIdn_operation_term().equals(new BigDecimal(5))) {*/
    		if(contractDeadlineSpecial.getStartDate()!=null ){
    	    	if(contractDeadlineSpecial.getStartDate().before(t)){
    		    	errorMsg = msgs.getString("error_startDate_sysdate_short"); //error_startDate_sysdate= Start date must be later to sysdate
    				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
    				log.error(errorMsg);
    				//onSearch();
    		    	return -1;
    			}
        	}
    		if(contractDeadlineSpecial.getSubmission_deadline().before(contractDeadlineSpecial.getStartDate())) {
    			errorMsg = msgs.getString("error_submission_deadline_short"); 
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				//onSearch();
				return -1;
    		}
    		if(contractDeadlineSpecial.getManagement_deadline().before(contractDeadlineSpecial.getSubmission_deadline())) {
    			errorMsg = msgs.getString("error_management_deadline_short"); 
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				//onSearch();
				return -1;
    		}    		
 /*   	}
    	//Control de fechas para el resto de contratos
    	else {
    		if(contractDeadlineSpecial.getStartDate()!=null ){
    			if(contractDeadlineSpecial.getStartDate().before(t)) {
    		    	errorMsg = msgs.getString("error_startDate_sysdate_short"); //error_startDate_sysdate= Start date must be later to sysdate
    		    	getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
    				log.error(errorMsg);
    		    	//getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + ":" + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
    				//log.error(errorMsg + valNumDate.intValue() + " days");
    				//onSearch();
    		    	return -1;
    			}
        	}
    		if(contractDeadlineSpecial.getSubmission_deadline()!=null ){
    	    	if(contractDeadlineSpecial.getSubmission_deadline().before(sysdate.getTime())){
    		    	errorMsg = msgs.getString("error_submission_deadline_sysdate");
    				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + ":" + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
    				log.error(errorMsg + valNumDate.intValue() + " days");
    				//onSearch();
    		    	return -1;
    			}
        	}
    		if(contractDeadlineSpecial.getManagement_deadline().before(contractDeadlineSpecial.getSubmission_deadline())
    				|| contractDeadlineSpecial.getManagement_deadline().equals(contractDeadlineSpecial.getSubmission_deadline())) {
    			errorMsg = msgs.getString("error_management_deadline"); 
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				//onSearch();
				return -1;
    		}    		
    	} */
    	//Control de fechas para todo tipo de contrato
    	if(contractDeadlineSpecial.getContract_min_start_date().before(contractDeadlineSpecial.getManagement_deadline()) 
				|| contractDeadlineSpecial.getContract_min_start_date().equals(contractDeadlineSpecial.getManagement_deadline())) {
			errorMsg = msgs.getString("error_contract_min_start_date"); 
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
			log.error(errorMsg);
			//onSearch();
			return -1;
		}
		if(contractDeadlineSpecial.getContract_max_start_date().before(contractDeadlineSpecial.getContract_min_start_date())) {
			errorMsg = msgs.getString("error_contract_max_start_date"); 
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
			log.error(errorMsg);
			//onSearch();
			return -1;
		}
    	return 0;
	}
	
	private static Date getZeroTimeDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = calendar.getTime();
		return date;
	}
    
	
    public boolean renderItemEditor(ContractDeadlineSpecialBean item){
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
