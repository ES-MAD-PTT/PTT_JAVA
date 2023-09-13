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
import com.atos.beans.dam.StrippingFactorBean;
import com.atos.filters.dam.StrippingFactorFilter;
import com.atos.services.dam.StrippingFactorService;
import com.atos.utils.Constants;
import com.atos.views.ChangeSystemView;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;

@ManagedBean(name="strippingFactorView")
@ViewScoped
public class StrippingFactorView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(StrippingFactorView.class);
	private StrippingFactorFilter filters;
	private StrippingFactorBean newStrippingFactor;
	private List<StrippingFactorBean> items;
	
	@ManagedProperty("#{strippingFactorService}")
    transient private StrippingFactorService service;
    
    public void setService(StrippingFactorService service) {
        this.service = service;
    }

    private Calendar sysdate;
    public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	 public StrippingFactorFilter getFilters() {
		return filters;
	}

	public void setFilters(StrippingFactorFilter filters) {
		this.filters = filters;
	}
	
	public StrippingFactorBean getNewStrippingFactor() {
		return newStrippingFactor;
	}

	public void setNewStrippingFactor(StrippingFactorBean newStrippingFactor) {
		this.newStrippingFactor = newStrippingFactor;
	}
	
	public List<StrippingFactorBean> getItems() {
		return items;
	}
	
	public void setItems(List<StrippingFactorBean> items) {
		this.items = items;
	}
	 

	@PostConstruct
    public void init() {
    	filters = new StrippingFactorFilter();
    	newStrippingFactor = new StrippingFactorBean();
    	
    	//LOOKING TO THE SYSDATE parameter BD
      	sysdate= gettingValidDateStart();
    	newStrippingFactor.setStartDate(sysdate.getTime());
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
	public Map<BigDecimal, Object> getZonesSystem() {
		return service.selectStrippingFactorZoneSystem(getChangeSystemView().getIdn_active());
	}
	
	public void onSearch(){
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_strippingFactor1').clearFilters()");
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg"); 
		
		if (filters.getEndDate()!=null ) {
			if(filters.getStartDate().after(filters.getEndDate())){
				String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
				return;
		    }
		}
		
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectStrippingFactors(filters);
        
	}
	
	public void onClear(){
		//RequestContext.getCurrentInstance().reset("form");
		filters = new StrippingFactorFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
        if (items != null) {
            items.clear();
        }
    }

	public void onRowEdit(RowEditEvent event) {
  
    	StrippingFactorBean strippingFactor = (StrippingFactorBean) event.getObject();
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
		String[] params = {msgs.getString("strippingFactor") };
    	String summaryMsgOk =  getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
    	String error = "0";
    	
		try {
			
			// delete the record setting end date
			error = service.deleteStrippingFactor(strippingFactor);
			
			//HERE NO UPDATES, all changes must be done are historical INSER
			error = service.insertStrippingFactor(strippingFactor);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		String[] par2 = { strippingFactor.getZone_code(),msgs.getString("strippingFactor")};
		
		if(error!=null && error.equals("0")){
			String msg =  getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("StrippingFactor Updated", "StrippingFactor Updated: " + strippingFactor.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-1")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Error inserting StrippingFactor. StrippingFactor, Error: " +error + ". "+strippingFactor.toString(), Calendar.getInstance().getTime());
    		
    	}else if (error!=null && error.equals("-10")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Error Updating StrippingFactor. StrippingFactor, Error: " +error + ". "+strippingFactor.toString(), Calendar.getInstance().getTime());
    	}else{
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Error insertingg StrippingFactor. StrippingFactor, Error: " +error + ". "+strippingFactor.toString(), Calendar.getInstance().getTime());
		}
		
		//Comentamos la busqueda porque la pantalla no refresca bien despues del update si volvemos a ordenar con la select
		//items = service.selectStrippingFactors(filters);
    	
    }
     
    public void onRowCancel(RowEditEvent event) {
    }
    
    public void cancel() {
        //RequestContext.getCurrentInstance().reset("formNewEntity");
    	newStrippingFactor = new StrippingFactorBean();
    	newStrippingFactor.setStartDate(sysdate.getTime());
    }
    
    public void saveBD(){
    	
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
   	
    	String[] params = {msgs.getString("strippingFactor") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    	
    	String error = "0";
		try {
			error =  service.insertStrippingFactor(newStrippingFactor);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
    	
		
		//String[] par2 = { newStrippingFactor.getZone_code(),msgs.getString("strippingFactor") };
		if(error!=null && error.equals("0")){
			//String msg =  getMessageResourceString("inserting_ok", par2);
			String msg = msgs.getString("strippingFactor");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("StrippingFactor Inserted ok" + newStrippingFactor.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-1")){
			//String msg =  getMessageResourceString("error_inserting", par2);
			String msg = msgs.getString("strippingFactor");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting StrippingFactor. StrippingFactor, Error: " +error + ". "+ newStrippingFactor.toString(), Calendar.getInstance().getTime());
		}else{
			//String msg =  getMessageResourceString("error_inserting", par2);
			String msg = msgs.getString("strippingFactor");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error insertingg StrippingFactor. StrippingFactor, Error: " +error + ". "+newStrippingFactor.toString(), Calendar.getInstance().getTime());
		}
    	
    	
		//offShore
		filters.setIdn_system(getChangeSystemView().getIdn_active());	
    	//items = service.selectStrippingFactors(filters);
		onSearch();
    	
    	//clean the formu new after save
    	newStrippingFactor = new StrippingFactorBean();
    	
    	//StartDate New => sysdate +1
    	newStrippingFactor.setStartDate(sysdate.getTime());
    }
    
    public void save(){
    	String errorMsg = null;
		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
    	Integer valNumDate = valDate.getInteger_exit();
    	
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("strippingFactor") };
    	
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    	
    	if(newStrippingFactor.getStartDate()!=null ){
	    	if(newStrippingFactor.getStartDate().before(sysdate.getTime())){
		    	errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
	    		getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
		    	return;
			}
    	}
    	
    	//comprobamos si ya esxiste
    	List<BigDecimal> list = service.getStrippingFactor(newStrippingFactor);
    	
		if(list.size()>0){
			RequestContext context = RequestContext.getCurrentInstance();
    	    context.execute("PF('w_confirmSaveDlg').show()");
    		return;
    	}else{
    		
    		saveBD();
    	}
    }
    
  //Control methods dates
       
	public String disabledField(StrippingFactorBean item) {
		//1.	En caso de un registro con la fecha Start Date posterior al día actual, el sistema habilitará Todos
		
		
		if(item.getStartDate().after(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime())){
 			return "false";
		 }
		 		
		 /*if(item.getStartDate().after(sysdate.getTime())){
		 	return "false";
 		}
		 		
		 		
 		if(item.getStartDate().before(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime()) ){
 			 return "true";
		}
		*/
		return "true";   
	    	
	  }
    
    
    public boolean renderItemEditor(StrippingFactorBean item){
     
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
