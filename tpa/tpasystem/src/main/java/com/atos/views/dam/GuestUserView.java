package com.atos.views.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DualListModel;

import com.atos.beans.MessageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.GuestUserBean;
import com.atos.filters.dam.GuestUserFilter;
import com.atos.services.dam.GuestUserService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="guestUserView")
@ViewScoped
public class GuestUserView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(GuestUserView.class);
	
	private GuestUserFilter filters;
	private GuestUserBean newGuestUser;
	private List<GuestUserBean> items;
	
	
	@ManagedProperty("#{guestUserService}")
    transient private GuestUserService service;
    
    public void setService(GuestUserService service) {
        this.service = service;
    }
    

    private Map<BigDecimal, Object> comboShipperOperatorIDs;
    
    private Calendar sysdate;
    public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	 public GuestUserFilter getFilters() {
		return filters;
	}

	public void setFilters(GuestUserFilter filters) {
		this.filters = filters;
	}
	
	public GuestUserBean getNewGuestUser() {
		return newGuestUser;
	}

	public void setNewGuestUser(GuestUserBean newGuestUser) {
		this.newGuestUser = newGuestUser;
	}
	
	
	public List<GuestUserBean> getItems() {
		return items;
	}
	
	public void setItems(List<GuestUserBean> items) {
		this.items = items;
	}
  //fin geter/seter

	
	@PostConstruct
    public void init() {
    	filters = new GuestUserFilter();
    	newGuestUser = new GuestUserBean();
    	
    	List<String> rolesSource = new ArrayList<String>();
        List<String> rolesTarget = new ArrayList<String>();
        
        rolesSource = service.selectRoles();
        newGuestUser.setRolesUsu(new DualListModel<String>(rolesSource, rolesTarget));
        
        comboShipperOperatorIDs = service.selectShipperOperatorIDs();
        
      //LOOKING TO THE SYSDATE parameter BD
    	sysdate= gettingValidDateStart();
    	newGuestUser.setStartDate(sysdate.getTime());
    	
    }
	  //sysdte +1
    public Calendar gettingValidDateStart(){
    	
    	SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD.USER");
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
	
    
    public List<String> idList(String query) {
		query = "%" + query + "%";
		return service.selectIds(query);
    }
    
	 
    public List<String> nameList(String query) {
		query = "%" + query + "%";
		return service.selectName(query);
    }
	public Map<BigDecimal, Object> getIds() {
		return service.selectGuestUserId();
	}
	
	public Map<BigDecimal, Object> getShipperOperatorIDs() {
		return comboShipperOperatorIDs;
	}

	
	// Methods
	public void onSearch(){
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		if (filters.getEndDate()!=null ) {
			if(filters.getStartDate().after(filters.getEndDate())){
				String strError = msgs.getString("start_previous_end");
				getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
	    	return;
		    }
		}
		items = service.selectGuestUsers(filters);
	}
	
	public void onClear(){
		//RequestContext.getCurrentInstance().reset("form");
		filters = new GuestUserFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
        if (items != null) {
            items.clear();
        }
    }

	public void onRowEdit(RowEditEvent event) {
  
    	GuestUserBean guestUser = (GuestUserBean) event.getObject();
    	
    	String errorMsg = null;
        
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("user") };
    	String summaryMsgOk =  getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
    	
    	
    	
    	if(guestUser.getEndDate()!=null){
    		if(guestUser.getStartDate().after(guestUser.getEndDate())){
    			errorMsg = msgs.getString("start_previous_end"); //start_previous_end= Start date must be previous to end date
		    	getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
    	    	items = service.selectGuestUsers(filters);
    	    	return;
    		}
    		
    	}
    	    	
    	
    	String error = "0";
		try {
			//if not roles asigned find in bd
			if (guestUser.getRolesUsu().getTarget().size()==0){
				//los busco
				List<String> rolesSource = new ArrayList<String>();
		        List<String> rolesTarget = new ArrayList<String>();
		        rolesSource = service.selectNoRolesUser(guestUser.getIdn_user());
		        rolesTarget=service.selectRolesUser(guestUser.getIdn_user());
		        guestUser.setRolesUsu(new DualListModel<String>(rolesSource, rolesTarget));
			}
			//updated...
			error = service.updateGuestUser(guestUser);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
    	
		String[] par2 = { guestUser.getId(),msgs.getString("user") };
		
    	if(error!=null && error.equals("0")){
    		String msg =  getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
    		log.info("GuestUser Updated: " + guestUser.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-1")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("GuestUser Not Updated GuestUser", "Error updating guestUser " + guestUser.toString(), Calendar.getInstance().getTime());
    	} else if(error!=null && error.equals("-2")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("GuestUserProfile  Delete Profile", "Error updating guestUser " + guestUser.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-3")){
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
	    	log.info("GuestUserProfile  getProfileId", "Error updating guestUser " + guestUser.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-4")){
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("GuestUserProfile insertUserProfile", "Error updating guestUser " + guestUser.toString(), Calendar.getInstance().getTime());
		}else{ 
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating guestUser " + guestUser.toString(), Calendar.getInstance().getTime());
	    }   	
    	
    	items.clear();
    	items = service.selectGuestUsers(filters);
    	
    }
     
    public void onRowCancel(RowEditEvent event) {
    	
    }
    
    public void cancel() {
       // RequestContext.getCurrentInstance().reset("formNewEntity");
    
    	newGuestUser = new GuestUserBean();
      	newGuestUser.setStartDate(sysdate.getTime());
    	
    	List<String> rolesSource = new ArrayList<String>();
        List<String> rolesTarget = new ArrayList<String>();
        rolesSource = service.selectRoles();
        newGuestUser.setRolesUsu(new DualListModel<String>(rolesSource, rolesTarget));
    }
    
    
    public void save(){
    	
    	SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
    	Integer valNumDate = valDate.getInteger_exit();
    	String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("user") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    	
    	if(newGuestUser.getStartDate()!=null ){
	    	if(newGuestUser.getStartDate().before(sysdate.getTime())){
	    		errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
		    	return;
			}
    	}
    	
    	if(newGuestUser.getEndDate()!=null ){
	    	if (newGuestUser.getEndDate().before(sysdate.getTime())  ){
				errorMsg = msgs.getString("error_endDate_sysdate"); //error_endDate_sysdate= End Date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
		    	return;
			}
	    	if(newGuestUser.getStartDate().after(newGuestUser.getEndDate())){
	    		errorMsg = msgs.getString("error_startEarlierEnd"); //error_startEarlierEnd = Start Date must be earlier or equal to End Date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	return;
	    	}
	    		
    	}
    	
    	String error = "0";
		try {
			error = service.insertGuestUser(newGuestUser);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}

		String[] par2 = { newGuestUser.getId(),msgs.getString("user") };
		
		if(error!=null && error.equals("0")){
			String msg =  getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("User Inserted ok" + newGuestUser.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-1")){
			String msg =  getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));	
    	    log.info("Error inserting GuestUser. The ID: " + newGuestUser.toString() +" already exists in the System ", Calendar.getInstance().getTime());
			  
		}else if(error!=null && error.equals("-2")){
			String msg =  getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));   
    		log.info("Error inserting insertGuestUser. Error inserting User"  + newGuestUser.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-3")){
			String msg =  getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));   
    		log.info("Error inserting insertUserProfile. Error inserting UserProfile"  + newGuestUser.toString(), Calendar.getInstance().getTime());
		}
		
  	
    	//We return to search
    	//items = service.selectGuestUsers(filters);
    	onSearch();
    	
    	//clean the formu new after save
    	newGuestUser = new GuestUserBean();
    
    	//StartDate New => sysdate +1
      	newGuestUser.setStartDate(sysdate.getTime());
    	
    	List<String> rolesSource = new ArrayList<String>();
        List<String> rolesTarget = new ArrayList<String>();
        rolesSource = service.selectRoles();
        newGuestUser.setRolesUsu(new DualListModel<String>(rolesSource, rolesTarget));

    }
    
    //Control methods dates    
    public String disabledEndDate(GuestUserBean item){
    	
    	if(item.getEndDate()!=null ){    		
    		if (item.getStartDate().before(sysdate.getTime()) && (item.getEndDate().before(sysdate.getTime()))){
        		return "true";
        	}	
    	}   	    	
    	return "false";
    }
    	
 	public String disabledField(GuestUserBean item) {
 		//1.	En caso de un registro con la fecha Start Date posterior al día actual, el sistema habilitará Todos
 		//2.	En caso de un registro con la fecha Start Date anterior o igual al día actual y 
 	    //		fecha End Date posterior al día actual (o vacía), el sistema habilitará SOLO END DATE
 		//3.	En caso de un registro con la fecha Start Date anterior o igual al día actual, se habilita  NADA 
 		
 		/*
 		 * //18/7/2016 modificado para que edite cuando la fecha startDay sea igual al sysdate
 		 * 
 		if(item.getStartDate().after(sysdate.getTime())){
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
    
    
    public boolean renderItemEditor(GuestUserBean item){
     	if(item.getEndDate()!=null ){	
	    	if (item.getStartDate().before(sysdate.getTime()) && item.getEndDate().before(sysdate.getTime())) {
	    		return false;
	    		
	    	}else{
	    		return true;
	    	}
    	}else return true;
    }

    
    	
	public void onRowToggle(ToggleEvent event) {
		GuestUserBean item = ((GuestUserBean) event.getData());
		List<String> rolesSource = new ArrayList<String>();
        List<String> rolesTarget = new ArrayList<String>();
       
        //Unassigned roles
        rolesSource = service.selectNoRolesUser(item.getIdn_user());

        //User roles
        rolesTarget=service.selectRolesUser(item.getIdn_user());
        
        item.setRolesUsu(new DualListModel<String>(rolesSource, rolesTarget));
    
	}	
	
	public String disabledFieldNew(GuestUserBean item) {   
		
		//23/6/2017.- los campos perfil, tf, address, email y fax. No se desabilitan nunca.
   	   //lo dejo en un metodo, por si en el fututo hubiera que meter alguna condicion 	
    	return "false";
    	
    }
	public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
	
}
