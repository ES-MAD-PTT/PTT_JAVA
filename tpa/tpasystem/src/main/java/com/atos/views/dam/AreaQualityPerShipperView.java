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
import org.primefaces.event.ToggleEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.AreaQualityPerShipperBean;
import com.atos.beans.dam.ZoneBean;
import com.atos.filters.dam.AreaQualityPerShipperFilter;
import com.atos.services.dam.AreaQualityPerShipperService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;


@ManagedBean(name="areaQualityPerShipperView")
@ViewScoped
public class AreaQualityPerShipperView extends CommonView implements Serializable {
 

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(AreaQualityPerShipperView.class);
	
	private AreaQualityPerShipperFilter filters;
	private AreaQualityPerShipperBean newAreaQPP;
	private List<AreaQualityPerShipperBean> items;
	
	@ManagedProperty("#{areaQualityPerShipperService}")
    transient private AreaQualityPerShipperService service;
    
    public void setService(AreaQualityPerShipperService service) {
        this.service = service;
    }

	Calendar sysdate;
	public Calendar getSysdate() {
		return sysdate;
	}
	
	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}
	
	
	public AreaQualityPerShipperFilter getFilters() {
		return filters;
	}

	public void setFilters(AreaQualityPerShipperFilter filters) {
		this.filters = filters;
	}
	
	public AreaQualityPerShipperBean getNewAreaQPP() {
		return newAreaQPP;
	}

	public void setNewAreaQPP(AreaQualityPerShipperBean AreaQualityPerShipper) {
		this.newAreaQPP = AreaQualityPerShipper;
	}
	
	
	public List<AreaQualityPerShipperBean> getItems() {
		return items;
	}
	
	public void setItems(List<AreaQualityPerShipperBean> items) {
		this.items = items;
	}
    
    //para visualizar al gridQuality
    private Boolean renderedGridZona;
    private Boolean renderedGridQuality;
    
    public void verZone() {
    	sysdate= gettingValidDateStart();   
    	getNewAreaQPP().setStart_date_gas_Quality(sysdate.getTime());
    	setRenderedGridQuality(false);
    	setRenderedGridZona(true);
       
    }  
    
    public void verQuality() {
       	setRenderedGridQuality(true);
    	setRenderedGridZona(false);
     }
    
	
	public Boolean getRenderedGridZona() {
		return renderedGridZona;
	}

	public void setRenderedGridZona(Boolean renderedGridZona) {
		this.renderedGridZona = renderedGridZona;
	}

	public Boolean getRenderedGridQuality() {
		return renderedGridQuality;
	}

	public void setRenderedGridQuality(Boolean renderedGridQuality) {
		this.renderedGridQuality = renderedGridQuality;
	}

	//fin geter/seter
	
	@PostConstruct
    public void init() {
    	filters = new AreaQualityPerShipperFilter();
    	newAreaQPP = new AreaQualityPerShipperBean();
     	
    	//BUSCAMOS EL SYSDATE CON EL PARAMETRO DE BD
    	sysdate= gettingValidDateStart();   	
    	newAreaQPP.setStart_date_gas_Quality(sysdate.getTime());
    
    }
	
	
	//sysdte +1
    public Calendar gettingValidDateStart(){
    	
    	SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
    	Integer valNumDate = valDate.getInteger_exit();
    	Calendar sysdate = Calendar.getInstance(); 
    	
    	sysdate.setTime(valDate.getDate());
    	sysdate.set(Calendar.HOUR_OF_DAY, 0);
    	sysdate.set(Calendar.MINUTE, 0);
    	sysdate.set(Calendar.SECOND, 0);
    	sysdate.set(Calendar.MILLISECOND, 0);
    	sysdate.add(Calendar.DAY_OF_MONTH, valNumDate.intValue());
    	
        return sysdate;
    }
	
	public Map<BigDecimal, Object> getIdsArea() {
		return service.selectAreasIds(getChangeSystemView().getIdn_active());
	}
	
	public Map<BigDecimal, Object> getIdsShipper() {
		return service.selectShippersIds();
	}
	
	public void onSearch(){
		filters.setIdn_system(getChangeSystemView().getIdn_active());	
		filters.setParameterCode1("WI");
		filters.setParameterCode2("HV");
		items = service.selectAreasShipper(filters);
	}
	
	public void onClear(){
		filters = new AreaQualityPerShipperFilter();
        if (items != null) {
            items.clear();
        }
    }
	
	public void actualizarAreaDesc() {
		String area = service.getAreaDesc(newAreaQPP.getIdn_area());
		newAreaQPP.setArea_desc(area);
	}

	public void onRowEdit(RowEditEvent event) {

		AreaQualityPerShipperBean bean = (AreaQualityPerShipperBean) event.getObject();
	    
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
 		
    	String error = "0";
		try {
			error = service.update(bean);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		if(error!=null && error.equals("0")){
			String msg = msgs.getString("areaQPS_updated");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,msg, msg, Calendar.getInstance().getTime()));
    		log.info("Updated", "Updated: " + bean.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-1")){
    		String msg = msgs.getString("update_error");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,msg, msg, Calendar.getInstance().getTime()));
    		log.info("Not Updated", "Error updating " + bean.toString(), Calendar.getInstance().getTime());
    	}
		
		onSearch();
		
    }
     
    public void onRowCancel(RowEditEvent event) {
        
    }
    
    public void cancel() {
        RequestContext.getCurrentInstance().reset("formNewEntity");  	
    	newAreaQPP = new AreaQualityPerShipperBean();
    	newAreaQPP.setStart_date_gas_Quality(sysdate.getTime());
    }
    
    public void save(){
 

    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
     	//hacemos el insert
    	String error = "0";
		try {
			error =  service.insert(newAreaQPP);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		if(error!=null && error.equals("0")){
			String msg = msgs.getString("areaQPS_saved");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,msg, msg, Calendar.getInstance().getTime()));
			log.info("Values Inserted", "Values Inserted ok" + newAreaQPP.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-1")){
			String msg = msgs.getString("error_already_exits");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,msg,msg, Calendar.getInstance().getTime()));
			log.info("Inserted", "Error inserting. The " + newAreaQPP.toString() +" already exists in the System ", Calendar.getInstance().getTime());
		}else if (error!=null && error.equals("-2")){
			String msg = msgs.getString("error_inserting");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,msg, msg, Calendar.getInstance().getTime()));    	
    		log.info("Not Inserted", "Error inserting  GasQuality " + newAreaQPP.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-16")){
    		String msg = msgs.getString("max_value_greater_parameter");//max_value_greater_parameter=Max value should be greater than or equal to Min. Parameter
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,msg, msg + " Parameter: " + newAreaQPP.getParameterCode1(), Calendar.getInstance().getTime()));    	
    		log.info("Not Inserted", "Error inserting. 'Max value should be greater than or equal to Min. Parameter: " + newAreaQPP.getParameterCode2(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-3")){
    		String msg = msgs.getString("error_param_wi");//max_value_greater_parameter=Max value should be greater than or equal to Min. Parameter
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,msg, msg , Calendar.getInstance().getTime()));    	
    		log.info("Not Inserted", "Error inserting. Problem with param " + Constants.WI, Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-10")){
    		String msg = msgs.getString("error_param_hv");//max_value_greater_parameter=Max value should be greater than or equal to Min. Parameter
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,msg, msg , Calendar.getInstance().getTime()));    	
    		log.info("Not Inserted", "Error inserting. Problem with param " + Constants.HV, Calendar.getInstance().getTime());
    	}else if (error!=null){
    		String msg = msgs.getString("error_inserting");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,msg, msg, Calendar.getInstance().getTime()));    	
    		log.info("Not Inserted", "Error inserting ." + error, Calendar.getInstance().getTime());
    	}
    	
		onSearch();
    	
    }
    
    
    //ch1 cuando se edita un registro solo se permite al usuaio que elija la mayor de la fechas entre el sysdate o el startDate de la zona
    public Calendar buscaFechaMin(Date startDate){
    	
    	Calendar sysdateMin = Calendar.getInstance(); 
    	if (startDate != null) {
    		
    	    if (startDate.after(sysdate.getTime())){
   			   sysdateMin.setTime(startDate);
         	}else{
        		sysdateMin= gettingValidDateStart(); 
         	}	
    	}
    	
        return sysdateMin;
    }
    
   
    
    public String disabledField(ZoneBean item) {
    	//1.	En caso de un registro con la fecha Start Date posterior al día actual, el sistema habilitará Todos
 		//2.	En caso de un registro con la fecha Start Date anterior o igual al día actual y 
 			 //fecha End Date posterior al día actual (o vacía), el sistema habilitará SOLO END DATE
 		//3.	En caso de un registro con la fecha Start Date anterior o igual al día actual, se habilita  NADA 

    	//18/7/2016 modificado para que edite cuando la fecha startDay sea igual al sysdate
    	
 		/*if(item.getStartDateGasQuality().after(sysdate.getTime())){
 			return "false";			
 		}*/
 		
    	if(item.getStartDateGasQuality().after(sysdate.getTime()) || item.getStartDateGasQuality().equals(sysdate.getTime()) ){
 			return "false";			
 		}


 		if(item.getStartDateGasQuality().before(sysdate.getTime()) || item.getStartDateGasQuality().equals(sysdate.getTime()) ){ 
 			 return "true";
 		}
 		
 		return "true";
    }    	

    public boolean renderItemEditor(AreaQualityPerShipperBean item){
     		
	    	if (item.getStart_date_gas_Quality().before(sysdate.getTime()) ) {
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
