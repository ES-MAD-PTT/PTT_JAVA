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
import com.atos.beans.dam.SystemParameterDamBean;
import com.atos.filters.dam.SystemParameterDamFilter;
import com.atos.services.dam.SystemParameterDamService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;


@ManagedBean(name="systemParameterView")
@ViewScoped

public class SystemParameterDamView extends CommonView implements Serializable {
	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(SystemParameterDamView.class);

	private SystemParameterDamFilter filters;
	private SystemParameterDamBean newSystemParameter;
	private List<SystemParameterDamBean> items;
	private BigDecimal bgModuleTarif;
	
	
	@ManagedProperty("#{systemParameterDamService}")
    transient private SystemParameterDamService service;
	
    public void setService(SystemParameterDamService service) {
        this.service = service;
    }

    private Calendar sysdate;
    public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	 public SystemParameterDamFilter getFilters() {
		return filters;
	}

	public void setFilters(SystemParameterDamFilter filters) {
		this.filters = filters;
	}
	
	public SystemParameterDamBean getNewSystemParameter() {
		return newSystemParameter;
	}

	public void setNewSystemParameter(SystemParameterDamBean newSystemParameter) {
		this.newSystemParameter = newSystemParameter;
	}
	
	public List<SystemParameterDamBean> getItems() {
		return items;
	}
	
	public void setItems(List<SystemParameterDamBean> items) {
		this.items = items;
	}
	 
    @PostConstruct
    public void init() {
    	
    	filters = new SystemParameterDamFilter();
    	newSystemParameter = new SystemParameterDamBean();
    	 	   
        //LOOKING TO THE SYSDATE parameter BD
      	sysdate= gettingValidDateStart();
    	newSystemParameter.setStartDate(sysdate.getTime());
    	
    	filters.setIdn_parameter_module(service.obtenerModuloInicial());
    	bgModuleTarif= service.obtenerModuloTariff();
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

    public Map<BigDecimal, Object> getModules() {
		return service.selectSystemParameterModules();
	}

    public Map<BigDecimal, Object> getParameters(BigDecimal idn_module) {
	
		if(idn_module==null){
			return service.selectSystemParameterParameters(null);
		} else {
			return service.selectSystemParameterParameters(idn_module);
		}

	}

	public void onSearch(){
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_systemParameter1').clearFilters()");
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg"); 
		
		if (filters.getEndDate()!=null ) {
			if(filters.getStartDate().after(filters.getEndDate())){
				String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
		    	return;
		    }
		}
		items = service.selectSystemParameters(filters);
        
	}
	
	public void onClear(){
		//RequestContext.getCurrentInstance().reset("form");
		filters = new SystemParameterDamFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
        if (items != null) {
            items.clear();
        }
        
        RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_systemParameter1').clearFilters()");
		
    }


	public void onRowEdit(RowEditEvent event) {
  
    	SystemParameterDamBean systemParameter = (SystemParameterDamBean) event.getObject();
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
		items = service.selectSystemParameters(filters);
		requestContext.execute("PF('w_systemParameter1').filter()");
    }
     
    public void onRowCancel(RowEditEvent event) {
    	
    }
    
    public void cancel() {
    	//  RequestContext.getCurrentInstance().reset("formNewEntity");
    	newSystemParameter = new SystemParameterDamBean();
    	newSystemParameter.setStartDate(sysdate.getTime());
    }
    
    
    public void save(){
    	
    	String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("systemParameter") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    	
    	
    /*
     * . Por hacer lo mismo en la pantalla de System Param. que en otras pantallas de DAM, he dejado la restriccion de Start Date solo a a futuro y no deberia tener ninguna restr¡ccion. 	
     * if(newSystemParameter.getStartDate()!=null ){
	    	if(newSystemParameter.getStartDate().before(sysdate.getTime())){
		    	messages.addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,"SystemParameter Not Inserted", "Start date must be later to sysdate " + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
		    	return;
			}
    	}
    	*/
    	
    	//es raro que salte esta restriccion porque ya por pantalla no se deja que ponga otro dia que no sea 1 en caso de Tariff. Pero por si acaso...
    	if (newSystemParameter.getIdn_parameter_module().equals(bgModuleTarif)){
    		Calendar cal = Calendar.getInstance();
 			cal.setTime(newSystemParameter.getStartDate()); // 
 			if (cal.get(Calendar.DAY_OF_MONTH) != 1){
 				errorMsg = msgs.getString("systemParameter_day_month"); //systemParameter_day_month=The day of the month must be 1
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	return;
    		}
    	}
    	
    	String error = "0";
		try {
			//comprobamos si el valor es permitido
			error= service.getCheckValueSystemParameter(newSystemParameter);
			error =  service.insertSystemParameter(newSystemParameter);
			
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
    	
		String[] par2 = { newSystemParameter.getParameter_desc() + ". Value: " + newSystemParameter.getParameter_value(), msgs.getString("systemParameter")};
		if(error!=null && error.equals("0")){
			String msg =  getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("SystemParameter Inserted ok" + newSystemParameter.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-1")){
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting SystemParameter. SystemParameter, Error: " +error + ". "+ newSystemParameter.toString(), Calendar.getInstance().getTime());
		}else{
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error insertingg System Parameter Value. System Parameter, Error: " +error + ". "+newSystemParameter.toString(), Calendar.getInstance().getTime());
		}
    	
    	
    	//items = service.selectSystemParameters(filters);
    	onSearch();
    	
    	//clean the formu new after save
    	newSystemParameter = new SystemParameterDamBean();
    	
    	//StartDate New => sysdate +1
    	newSystemParameter.setStartDate(sysdate.getTime());
    	
    }
    
    //Control methods dates
   	
   public String disabledField(SystemParameterDamBean item) {
	 //1.	En caso de un registro con la fecha Start Date posterior al día actual, el sistema habilitará Todos
			
	 		if(item.getStartDate().after(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime())){
	  			return "false";
	  		}
	  		return "true";   	
	  }
    
   
    
    public boolean renderItemEditor(SystemParameterDamBean item){
     		
	    	if (item.getStartDate().before(sysdate.getTime())) {
	    		return false;
	    		
	    	}else{
	    		return true;
	    	}
    	
    }
    
    public void onModuleChange(){
    	//Si el modulo elegido es Tariff en la fecha startDate solo permitimos el primer dia de cada mes
    	if (newSystemParameter.getIdn_parameter_module().equals(bgModuleTarif)){
    	    Calendar cal = Calendar.getInstance();
 			cal.setTime(newSystemParameter.getStartDate()); // 
 		    cal.set(Calendar.DAY_OF_MONTH,1); //Solo el dia 1 del mes
 		    cal.set(Calendar.HOUR_OF_DAY, 0);
 	    	cal.set(Calendar.MINUTE, 0);
 	    	cal.set(Calendar.SECOND, 0);
 	    	cal.set(Calendar.MILLISECOND, 0);
 	    	newSystemParameter.setStartDate(cal.getTime());
    		
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
 