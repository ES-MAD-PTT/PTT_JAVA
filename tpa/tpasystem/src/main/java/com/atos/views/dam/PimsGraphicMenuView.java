package com.atos.views.dam;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
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
import com.atos.beans.dam.PimsGraphicMenuBean;
import com.atos.filters.dam.PimsGraphicMenuFilter;
import com.atos.services.dam.PimsGraphicMenuService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;


@ManagedBean(name="pimsGraphicMenuView")
@ViewScoped

public class PimsGraphicMenuView extends CommonView implements Serializable {
	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(PimsGraphicMenuView.class);

	private PimsGraphicMenuFilter filters;
	private List<PimsGraphicMenuBean> items;
	
	@ManagedProperty("#{pimsGraphicMenuService}")
    transient private PimsGraphicMenuService service;
	
    public void setService(PimsGraphicMenuService service) {
        this.service = service;
    }

    private Calendar sysdate;
    public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	 public PimsGraphicMenuFilter getFilters() {
		return filters;
	}

	public void setFilters(PimsGraphicMenuFilter filters) {
		this.filters = filters;
	}
	
	public List<PimsGraphicMenuBean> getItems() {
		return items;
	}
	
	public void setItems(List<PimsGraphicMenuBean> items) {
		this.items = items;
	}
	 
    @PostConstruct
    public void init() {
    	
    	filters = new PimsGraphicMenuFilter();
    	
    	sysdate= gettingValidDateStart();
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
	

	public void onSearch(){
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_pimsParameter1').clearFilters()");
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg"); 
		
		if (filters.getEndDate()!=null ) {
			if(filters.getStartDate().after(filters.getEndDate())){
				String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
		    	return;
		    }
		}
		items = service.selectPmisGraphicMenus(filters);
        System.out.println("items");
	}
	
	public void onClear(){
		//RequestContext.getCurrentInstance().reset("form");
		filters = new PimsGraphicMenuFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
        if (items != null) {
            items.clear();
        }
        
        RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_systemParameter1').clearFilters()");
		
    }


	public void onRowEdit(RowEditEvent event) {
  
    	PimsGraphicMenuBean systemParameter = (PimsGraphicMenuBean) event.getObject();
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		String[] params = {msgs.getString("systemParameter") };
    	String summaryMsgOk =  getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
    	
    
    	String error = "0";
		try {
			
			//comprobamos si el valor es permitido
			error= service.getCheckValueSystemParameter(systemParameter);
			
			// delete the record setting end date
			error = service.deleteSystemParameter(systemParameter);
		
			//HERE NO UPDATES, all changes must be done are historical INSERT
			error = service.insertSystemParameter(systemParameter);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
	
		String[] par2 = { systemParameter.getParameter_desc() + ". Value: " + systemParameter.getParameter_value(),msgs.getString("systemParameter")};
		
		if(error!=null && error.equals("0")){
			String msg =  getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
    		log.info("SystemParameter Updated", "SystemParameter Updated: " + systemParameter.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-1")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Error inserting SystemParameter. InsertSystemParameter, Error: " +error + ". "+systemParameter.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-10")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Error Updating SystemParameter. DeleteSystemParameter, Error: " +error + ". "+systemParameter.toString(), Calendar.getInstance().getTime());
    	}else{
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error insertingg SystemParameter. SystemParameter, Error: " +error + ". "+systemParameter.toString(), Calendar.getInstance().getTime());
		}
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		items = service.selectPmisGraphicMenus(filters);
		requestContext.execute("PF('w_pimsParameter1').filter()");
    }
     
    public void onRowCancel(RowEditEvent event) {
    	
    }
    
    
    //Control methods dates
   	
   public String disabledField(PimsGraphicMenuBean item) {
	   
	   		if(item.getEndDate()==null) {
	   			return "true";
	   		} else  if(item.getEndDate().after(sysdate.getTime())) {
	   			return "true";
	   		}
	   		return "false";
	 //1.	En caso de un registro con la fecha Start Date posterior al día actual, el sistema habilitará Todos
			
/*	 		if(item.getStartDate().after(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime())){
	  			return "false";
	  		}
	  		return "true";*/   	
	  }
    
   
    
    public boolean renderItemEditor(PimsGraphicMenuBean item){
     		
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
 