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
import com.atos.beans.dam.LinePackRequirementBean;
import com.atos.beans.dam.ShrinkageFactorBean;
import com.atos.filters.dam.LinePackRequirementFilter;
import com.atos.services.dam.LinePackRequirementService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="linePackRequirementView")
@ViewScoped
public class LinePackRequirementView extends CommonView implements Serializable {
	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(LinePackRequirementView.class);

	private LinePackRequirementFilter filters;
	private LinePackRequirementBean newLinePackRequirement;
	private List<LinePackRequirementBean> items;
	private String msgDlg;
	
	@ManagedProperty("#{linePackRequirementService}")
    transient private LinePackRequirementService service;
    
    public void setService(LinePackRequirementService service) {
        this.service = service;
    }

   
    private Calendar sysdate;
    public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	 public LinePackRequirementFilter getFilters() {
		return filters;
	}

	public void setFilters(LinePackRequirementFilter filters) {
		this.filters = filters;
	}
	
	public LinePackRequirementBean getNewLinePackRequirement() {
		return newLinePackRequirement;
	}

	public void setNewLinePackRequirement(LinePackRequirementBean newLinePackRequirement) {
		this.newLinePackRequirement = newLinePackRequirement;
	}
	
	public List<LinePackRequirementBean> getItems() {
		return items;
	}
	
	public void setItems(List<LinePackRequirementBean> items) {
		this.items = items;
	}
	 
	
    public String getMsgDlg() {
		return msgDlg;
	}

	public void setMsgDlg(String msgDlg) {
		this.msgDlg = msgDlg;
	}

	@PostConstruct
    public void init() {
    	filters = new LinePackRequirementFilter();
    	newLinePackRequirement = new LinePackRequirementBean();
    	 	   
        //LOOKING TO THE SYSDATE parameter BD
      	sysdate= gettingValidDateStart();
    	newLinePackRequirement.setStartDate(sysdate.getTime());
    	
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
		return service.selectLinePackRequirementZoneSystem(getChangeSystemView().getIdn_active());
	}


	public void onSearch(){
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_linePackRequirement1').clearFilters()");
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
		items = service.selectLinePackRequirements(filters);
        
	}
	
	public void onClear(){
		//RequestContext.getCurrentInstance().reset("form");
		filters = new LinePackRequirementFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
        if (items != null) {
            items.clear();
        }
    }


	public void onRowEdit(RowEditEvent event) {
  
    	LinePackRequirementBean linePackRequirement = (LinePackRequirementBean) event.getObject();
    
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		String[] params = {msgs.getString("linePackRequirement") };
    	String summaryMsgOk =  getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
    	
    	String error = "0";
		try {
			// delete the record setting end date
			error = service.deleteLinePackRequirement(linePackRequirement);
		
			//HERE NO UPDATES, all changes must be done are historical INSER
			error = service.insertLinePackRequirement(linePackRequirement);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		String[] par2 = { linePackRequirement.getZone_code(),msgs.getString("linePackRequirement")};
		
		if(error!=null && error.equals("0")){
			String msg =  getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
    		log.info("LinePackRequirement Updated", "LinePackRequirement Updated: " + linePackRequirement.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-1")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Error inserting LinePackRequirement. LinePackRequirement, Error: " +error + ". "+linePackRequirement.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-10")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.info("Error Updating LinePackRequirement. DeleteLinePackRequirement, Error: " +error + ". "+linePackRequirement.toString(), Calendar.getInstance().getTime());
    	}else{
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error insertingg LinePackRequirement. LinePackRequirement, Error: " +error + ". "+linePackRequirement.toString(), Calendar.getInstance().getTime());
		}
		
		//Comentamos la busqueda porque la pantalla no refresca bien despues del update si volvemos a ordenar con la select
		//items = service.selectLinePackRequirements(filters);
    }
     
    public void onRowCancel(RowEditEvent event) {
   
    }
    
    public void cancel() {
    	//  RequestContext.getCurrentInstance().reset("formNewEntity");
    	newLinePackRequirement = new LinePackRequirementBean();
    	newLinePackRequirement.setStartDate(sysdate.getTime());
    }
    
    
    public void saveBD(){
    	
    	SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
    	Integer valNumDate = valDate.getInteger_exit();
    	
    	String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("linePackRequirement") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    	
    	if(newLinePackRequirement.getStartDate()!=null ){
	    	if(newLinePackRequirement.getStartDate().before(sysdate.getTime())){
	    		errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
	    		getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
		    	return;
			}
    	}
    	
    	
    	String error = "0";
		try {
			error =  service.insertLinePackRequirement(newLinePackRequirement);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
    	
		if(error!=null && error.equals("0")){
			String msg = msgs.getString("linePackRequirement");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("LinePackRequirement Inserted ok" + newLinePackRequirement.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-1")){
			String msg = msgs.getString("linePackRequirement");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting LinePackRequirement. LinePackRequirement, Error: " +error + ". "+ newLinePackRequirement.toString(), Calendar.getInstance().getTime());
		}else{
			String msg = msgs.getString("linePackRequirement");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error insertingg LinePackRequirement. LinePackRequirement, Error: " +error + ". "+newLinePackRequirement.toString(), Calendar.getInstance().getTime());
		}
    	
		//offshore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
    	//items = service.selectLinePackRequirements(filters);
		onSearch();
    	
    	//clean the formu new after save
    	newLinePackRequirement = new LinePackRequirementBean();
    	
    	//StartDate New => sysdate +1
    	newLinePackRequirement.setStartDate(sysdate.getTime());
    	
    }
    
    //Control methods dates
   	
   public String disabledField(LinePackRequirementBean item) {
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
    
    
    public boolean renderItemEditor(LinePackRequirementBean item){
     		
	    	if (item.getStartDate().before(sysdate.getTime())) {
	    		return false;
	    		
	    	}else{
	    		return true;
	    	}
    	
    }

    public void save(){
    	String errorMsg = null;
		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
    	Integer valNumDate = valDate.getInteger_exit();
    	
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("linePackRequirement") };
    	
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    	
    	if(newLinePackRequirement.getStartDate()!=null ){
	    	if(newLinePackRequirement.getStartDate().before(sysdate.getTime())){
		    	errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
	    		getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
		    	return;
			}
    	}
    	
    	//comprobamos si ya esxiste
    	List<LinePackRequirementBean> list = service.getLinePackRequirement(newLinePackRequirement);
    	
		if(list.size()>0){
			
			RequestContext context = RequestContext.getCurrentInstance();
			String[] paramss = {list.get(0).getZone_code() };
	    	String summaryMsgOk =  getMessageResourceString("linePackRequirement_confirmation_text", paramss);
			
	    	this.msgDlg= summaryMsgOk;
	    	
			context.update("w_confirmSaveDlg");
    	    context.execute("PF('w_confirmSaveDlg').show()");
    		return;
    	}else{
    		
    		saveBD();
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
