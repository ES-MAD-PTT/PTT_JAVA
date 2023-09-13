package com.atos.views.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
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

import com.atos.beans.MessageBean;
import com.atos.beans.scadaAlarms.ScadaLabelBean;
import com.atos.beans.scadaAlarms.ScadaPointBean;
import com.atos.beans.scadaAlarms.TagnameManagementBean;
import com.atos.filters.scadaAlarms.TagnameManagementFilter;
import com.atos.services.scadaAlarms.TagnameManagementService;
import com.atos.utils.Constants;
import com.atos.views.ChangeSystemView;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;

@ManagedBean(name="tagnameManagementView")
@ViewScoped
public class TagNameManagementView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(TagNameManagementView.class);

	// TagnameManagementsFilter initial values for status.
	private static final String strNEW = "NEW";
	
	private TagnameManagementFilter filters;
	private TagnameManagementBean newTagnameManagement;
	
	private ScadaLabelBean newScadaLabel;
	private ScadaPointBean newScadaPoint;
	
	private List<TagnameManagementBean> items;
	private List<TagnameManagementBean> selecteds;
	
	

	 
	@ManagedProperty("#{tagnameManagementService}")
    transient private TagnameManagementService service;
    
    public void setService(TagnameManagementService service) {
        this.service = service;
    }

 
	public ScadaLabelBean getNewScadaLabel() {
		return newScadaLabel;
	}

	public void setNewScadaLabel(ScadaLabelBean newScadaLabel) {
		this.newScadaLabel = newScadaLabel;
	}

	public ScadaPointBean getNewScadaPoint() {
		return newScadaPoint;
	}

	public void setNewScadaPoint(ScadaPointBean newScadaPoint) {
		this.newScadaPoint = newScadaPoint;
	}

	public TagnameManagementBean getNewTagnameManagement() {
		return newTagnameManagement;
	}

	public void setNewTagnameManagement(TagnameManagementBean newTagnameManagement) {
		this.newTagnameManagement = newTagnameManagement;
	}
	

	//geters/seters
	 public TagnameManagementFilter getFilters() {
		return filters;
	}

	public void setFilters(TagnameManagementFilter filters) {
		this.filters = filters;
	}
	
	public List<TagnameManagementBean> getItems() {
		return items;
	}
	
	public void setItems(List<TagnameManagementBean> items) {
		this.items = items;
	}
       
	public List<TagnameManagementBean> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<TagnameManagementBean> selecteds) {
		this.selecteds = selecteds;
	}


	public Map<BigDecimal, Object> getScadaTagName() {
		return service.selectScadaTagName(getChangeSystemView().getIdn_active());
	}
	
	
	public Map<BigDecimal, Object> getPoints() {
		//return comboPoints;
		return service.selectPoints(getChangeSystemView().getIdn_active());
	}
	public Map<BigDecimal, Object> getLabels() {
		return service.selectLabels();
	}
	
	
	
	@PostConstruct
    public void init() {
		
    	filters = new TagnameManagementFilter();	
    	newTagnameManagement = new TagnameManagementBean();
    	newScadaLabel = new ScadaLabelBean();
    	newScadaPoint = new ScadaPointBean();
    	
   
    }


	public void onSearch(){
	
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectTagnameManagement(filters);
	}
	
	public void onClear(){
	
		filters = new TagnameManagementFilter();
		
        if (items != null) {
            items.clear();
        }
    }
	

	public void saveLabel(){
		String error = "0";
		try {
			error =  service.insertScadaLabel(newScadaLabel);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		String[] par2 = { newScadaLabel.getScadaLabel(),msgs.getString("ScadaLabel") };
		String[] params = {msgs.getString("ScadaLabel") };
		String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
		String summaryMsgOk =  getMessageResourceString("insert_ok", params);
		
		if(error!=null && error.equals("0")){
			String msg =  getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.INFO,summaryMsgOk,msg, Calendar.getInstance().getTime()));
			log.info("ScadaLabel Inserted ok" + newScadaLabel.toString(), Calendar.getInstance().getTime());
			
			
		} else if (error != null && error.equals("-1")) {
				
			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting ScadaLabel. The " + newScadaLabel.toString() +" already exists in the System ", Calendar.getInstance().getTime());
		}
    	
    	//items = service.selectLabels();
		newScadaLabel = new  ScadaLabelBean();
		
	}
	public void savePoint(){
		String error = "0";
		
		newScadaPoint.setIdn_pipeline_system(getChangeSystemView().getIdn_active());
		try {
			error =  service.insertScadaPoint(newScadaPoint);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		String[] par2 = { newScadaPoint.getScadaPoint(),msgs.getString("ScadaPoint") };
		String[] params = {msgs.getString("ScadaPoint") };
		String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
		String summaryMsgOk =  getMessageResourceString("insert_ok", params);
		
		if(error!=null && error.equals("0")){
			String msg =  getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk,msg, Calendar.getInstance().getTime()));
			log.info("ScadaPoint Inserted ok" + newScadaPoint.toString(), Calendar.getInstance().getTime());
			
		} else if (error != null && error.equals("-1")) {
		
			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting ScadaPoint. The " + newScadaPoint.toString() +" already exists in the System ", Calendar.getInstance().getTime());	
			
		}
    	//items = service.selectPoints();
		newScadaPoint = new  ScadaPointBean();
		
	}
	
	
	public void save(){
		
		
		String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	String[] params = {msgs.getString("tagname") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    	
    
    	if(newTagnameManagement.getBinary().equals("N")){
    		if (newTagnameManagement.getMin_alarm_threshold()==null && newTagnameManagement.getMax_alarm_threshold()==null ){
    			errorMsg = msgs.getString("tagname_mandatori_MinOrMax"); 
				getMessages().addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;  			
    		}
    		
    	}else{ 
    		
    		if (newTagnameManagement.getMin_alarm_threshold()!=null || newTagnameManagement.getMax_alarm_threshold()!=null ){
    			errorMsg = msgs.getString("tagname_notInformed_MinAndMax"); 
				getMessages().addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;
    		}
    	}
    	
    	if (newTagnameManagement.getMax_alarm_threshold() != null && newTagnameManagement.getMin_alarm_threshold() != null) {

			int res;
			res = newTagnameManagement.getMin_alarm_threshold().compareTo(newTagnameManagement.getMax_alarm_threshold());
			// 0 "Both values are equal "; 1 "First Value is greater "; -1 Second value is greater"
			if (res == 1) {
				errorMsg = msgs.getString("max_threshold_greater_min"); //max_preasure_greater_min=Max Pressure value shoul be greater or iqual tan Min Pressure.
				getMessages().addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				
				return;
			}
		}
    	
    	String[] par2 = { newTagnameManagement.getScada_tag_name(),msgs.getString("tagname") };
		String error = "0";
		try {
			error =  service.insertTagnameManagement(newTagnameManagement);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		
		if(error!=null && error.equals("0")){
			String msg =  getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk,msg, Calendar.getInstance().getTime()));
			log.info("TagnameManagement Inserted ok" + newTagnameManagement.toString(), Calendar.getInstance().getTime());
			
			
		} else if (error != null && error.equals("-1")) {
			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting TagnameManagement. The " + newTagnameManagement.toString() +" already exists in the System ", Calendar.getInstance().getTime());	
			
		}
    	
    	items = service.selectTagnameManagement(filters);
       	newTagnameManagement = new  TagnameManagementBean();
    	

	}

	public void onRowEdit(RowEditEvent event){
		TagnameManagementBean tagnameManagement = (TagnameManagementBean) event.getObject();
		String errorMsg = null;
		   
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("tagname")};
    	String summaryMsgOk = getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk= getMessageResourceString("update_noOk", params);
    	
    	if(tagnameManagement.getBinary().equals("N")){
    		if (tagnameManagement.getMin_alarm_threshold()==null && tagnameManagement.getMax_alarm_threshold()==null ){
    			errorMsg = msgs.getString("tagname_mandatori_MinOrMax"); 
				getMessages().addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;  			
    		}
    		
    	}else{ 
    		
    		if (tagnameManagement.getMin_alarm_threshold()!=null || tagnameManagement.getMax_alarm_threshold()!=null ){
    			errorMsg = msgs.getString("tagname_notInformed_MinAndMax"); 
				getMessages().addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;
    		}
    	}
    	if (tagnameManagement.getMax_alarm_threshold() != null && tagnameManagement.getMin_alarm_threshold() != null) {

			int res;
			res = tagnameManagement.getMin_alarm_threshold().compareTo(tagnameManagement.getMax_alarm_threshold());
			// 0 "Both values are equal "; 1 "First Value is greater "; -1 Second value is greater"
			if (res == 1) {
				errorMsg = msgs.getString("max_threshold_greater_min"); //max_preasure_greater_min=Max Pressure value shoul be greater or iqual tan Min Pressure.
				getMessages().addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				
				return;
			}
		}
		/*if(tagnameManagement.getBinary().equals("N")){
    		if (tagnameManagement.getMin_alarm_threshold()==null  ){
    			errorMsg = msgs.getString("tagname_mandatori_Min"); 
				getMessages().addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;
    			
    		}else if ( tagnameManagement.getMax_alarm_threshold()==null){
    			errorMsg = msgs.getString("tagname_mandatori_Max"); 
				getMessages().addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;
    		}
    		
    	}else{ 
    		
    		if (tagnameManagement.getMin_alarm_threshold()!=null  ){
    			errorMsg = msgs.getString("tagname_notInformed_Min"); 
				getMessages().addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;
    		}else if ( tagnameManagement.getMax_alarm_threshold()!=null){
    			errorMsg = msgs.getString("tagname_notInformed_Max"); 
				getMessages().addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;
    		}
    	}*/
		String error = "0";
		try {
			// En caso de que se quiera enviar una notificacion al usuario, se ha de indicar un systemId distinto de null.
			error = service.updateTagnameManagement(tagnameManagement);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}
		if (error != null && error.equals("0")) {
			getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.INFO, "Tagname Updated","Tagname Updated. Id " + tagnameManagement.getScada_tag_name(),Calendar.getInstance().getTime()));
			log.info("TagnameManagement Updated: " + tagnameManagement.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			getMessages().addMessage(Constants.head_menu[5],new MessageBean(Constants.ERROR, "Tagname Not Updated","Error updating  Tagname, Id Tagname: " + tagnameManagement.getScada_tag_name(),Calendar.getInstance().getTime()));
			log.info("TagnameManagement Not Updated", "Error updating  TagnameManagement " + tagnameManagement.toString(),Calendar.getInstance().getTime());
		}
		items = service.selectTagnameManagement(filters);
	}
	
	public void onRowCancel(RowEditEvent event){
		
	}
	
	public void cancelPoint(){
		newScadaPoint = new ScadaPointBean();
	}
	
	public void cancelLabel(){
		newScadaLabel = new ScadaLabelBean();
	}
	
	public void cancel(){

		newTagnameManagement = new TagnameManagementBean();
	
	}
	
    public String disabledField_Threshold(TagnameManagementBean item) {
    	//	El permiso ALARMS.TAGNAME_MANAGEMENT.TAGNAME.MOD debe permitir editar la columna is_enabled.
    	boolean tienePermiso=false;
    	filters.setIdn_user(getUser().getIdn_user());
    	filters.setPermission_code("ALARMS.TAGNAME_MANAGEMENT.THRESHOLD.MOD");
    	tienePermiso=	service.selectPermiso(filters);
    	if(tienePermiso){
    		return "false";	
    	}else 	{
    		return "true";
    	}
    		
    	
    }
    
    public String disabledField_Tagname(TagnameManagementBean item) {
    	//	El permiso ALARMS.TAGNAME_MANAGEMENT.THRESHOLD.MOD debe permitir editar las columnas ON_OFF_ALARM_THRESHOLD, MIN_ALARM_THRESHOLD y MAX_ALARM_THRESHOLD.
    	boolean tienePermiso=false;
    	filters.setIdn_user(getUser().getIdn_user());
    	filters.setPermission_code("ALARMS.TAGNAME_MANAGEMENT.TAGNAME.MOD");
    	tienePermiso=	service.selectPermiso(filters);
    	if(tienePermiso){
    		return "false";	
    	}else 	{
    		return "true";
    	}
    		
    	
    }
    
    public boolean renderItemEditor(TagnameManagementBean item){
   		return true;
    }
    
    private Map<String, String> valorIsBinary = new HashMap<String, String>();

    public Map<String, String> getBinary2(Boolean isBinary ) {

    	valorIsBinary = new HashMap<String, String>();
		if (isBinary) {
			valorIsBinary.put("0", "0");
			valorIsBinary.put("1", "1");
		}else{
			valorIsBinary.put(" ", " ");
		}

		return valorIsBinary;
	}    
}
