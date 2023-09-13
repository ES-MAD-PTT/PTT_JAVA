package com.atos.views.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.atos.beans.MessageBean;
import com.atos.beans.UserBean;
import com.atos.beans.nominations.ParkingAllocationBean;
import com.atos.beans.nominations.ParkingAllocationFormBean;
import com.atos.filters.nominations.ParkingAllocationFilter;
import com.atos.services.nominations.ParkingAllocationService;
import com.atos.utils.Constants;
import com.atos.views.ChangeSystemView;
import com.atos.views.MessagesView;

@ManagedBean(name="parkingAllocationView")
@ViewScoped
public class ParkingAllocationView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3897117224988921000L;

	private static final Logger log = LogManager.getLogger(ParkingAllocationView.class);

	private ParkingAllocationFilter filters;
	private ParkingAllocationFormBean form;
	private List<ParkingAllocationBean> items;
	private ResourceBundle msgs;

	@ManagedProperty("#{parkingAllocationService}")
    transient private ParkingAllocationService service;
    
    public void setService(ParkingAllocationService service) {
        this.service = service;
    }

	@ManagedProperty("#{messagesView}")
    private MessagesView messages;

    public void setMessages(MessagesView messages) {
        this.messages = messages;
    }

    @ManagedProperty("#{changeSystemView}")
	private ChangeSystemView systemView;

	public void setSystemView(ChangeSystemView systemView) {
		this.systemView = systemView;
	}
	
	@ManagedProperty("#{userBean}")
    private UserBean userBean;

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

	public ParkingAllocationFilter getFilters() {
		return filters;
	}

	public void setFilters(ParkingAllocationFilter filters) {
		this.filters = filters;
	}

	public List<ParkingAllocationBean> getItems() {
		return items;
	}

	public ParkingAllocationFormBean getForm() {
		return form;
	}

	public void setForm(ParkingAllocationFormBean form) {
		this.form = form;
	}

	@PostConstruct
    public void init() {
		msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	filters = new ParkingAllocationFilter();
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
    	cal.add(Calendar.DAY_OF_MONTH, 1);
    	
    	filters.setGas_day(cal.getTime());
    	
    	form = new ParkingAllocationFormBean();
    	form.setGas_day(cal.getTime());
    	
    	//offshore
    		Object[] tmpObjectArray = getZonesSystem().keySet().toArray();
    		filters.setIdn_zone(Arrays.copyOf(tmpObjectArray, tmpObjectArray.length, BigDecimal[].class));
    			
    
    }

	public Map<String, Object> getZonesCodes() {
		return service.selectParkingAllocationZones();
	}
	
	//offshore
	public Map<BigDecimal, Object> getZonesSystem() {
		return service.selectParkingAllocationZonesSystem(systemView.getIdn_active());
	}
	
	// Methods
	public void onSearch(){
		String summaryMsg = null;
		String errorMsg = null;
		
		summaryMsg = msgs.getString("error_parameters");
		if(filters.getGas_day()==null){		
			errorMsg = msgs.getString("date_selected");
			messages.addMessage(Constants.head_menu[4],	new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
		}
		
		items = service.selectParkingAllocation(filters);
        
	}
	
	public void onClear(){
		filters = new ParkingAllocationFilter();
		Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DAY_OF_MONTH, 1);
    	filters.setGas_day(cal.getTime());
    	
        if (items != null) {
            items.clear();
        }
        
    	form = new ParkingAllocationFormBean();
    	
    	//offshore
		Object[] tmpObjectArray = getZonesSystem().keySet().toArray();
		filters.setIdn_zone(Arrays.copyOf(tmpObjectArray, tmpObjectArray.length, BigDecimal[].class));
    }

	public double getUnparkNominated(BigDecimal idn_zone){
		TreeMap<BigDecimal,BigDecimal> map = new TreeMap<BigDecimal,BigDecimal>();
		for(int i=0;i<items.size();i++){
			if(idn_zone.equals(items.get(i).getIdn_zone())){
				if (items.get(i).getUnpark_nominated()!=null){
					if(map.containsKey(idn_zone)){					
							map.put(idn_zone, map.get(idn_zone).add(items.get(i).getUnpark_nominated()));				
					} else {
						map.put(idn_zone, items.get(i).getUnpark_nominated());
					}
				}
			}
		}
		
		if(map.get(idn_zone)==null){
			return 0;
		}else {
			return map.get(idn_zone).doubleValue();
		}
		
		
	}
	public double getParkNominated(BigDecimal idn_zone){
		TreeMap<BigDecimal,BigDecimal> map = new TreeMap<BigDecimal,BigDecimal>();
		for(int i=0;i<items.size();i++){
			if(idn_zone.equals(items.get(i).getIdn_zone())){
				if (items.get(i).getPark_nominated()!=null){
				
					if(map.containsKey(idn_zone)){	
							map.put(idn_zone, map.get(idn_zone).add(items.get(i).getPark_nominated()));				
					} else {
						map.put(idn_zone, items.get(i).getPark_nominated());
					}
				}
			}
		}
		
		if(map.get(idn_zone)==null){
			return 0;
		}else {
			return map.get(idn_zone).doubleValue();
		}
		
		
	}
	public double getParkAllocated(BigDecimal idn_zone){
		TreeMap<BigDecimal,BigDecimal> map = new TreeMap<BigDecimal,BigDecimal>();
		for(int i=0;i<items.size();i++){
			if(idn_zone.equals(items.get(i).getIdn_zone())){
				if (items.get(i).getPark_allocated()!=null){	
					if(map.containsKey(idn_zone)){
							map.put(idn_zone, map.get(idn_zone).add(items.get(i).getPark_allocated()));				
					} else {
						map.put(idn_zone, items.get(i).getPark_allocated());
					}
				}
			}
		}
		if(map.get(idn_zone)==null){
			return 0;
		}else {
			return map.get(idn_zone).doubleValue();
		}
		
		
	}
	
	public String getDefaultValue(BigDecimal idn_zone){
		ParkingAllocationFormBean formCal = new ParkingAllocationFormBean();
		formCal.setIdn_zone(idn_zone);
		formCal.setGas_day(filters.getGas_day());
		
		formCal.setDefault_value(service.getParkDefaultValue(formCal));
	
		if (formCal.getDefault_value()==null){
			return "";
		}
		else{
			BigDecimal bigDec = formCal.getDefault_value().setScale(3, RoundingMode.HALF_UP);
			return bigDec.toString();
			//return formCal.getDefault_value().toString();
		}
	}
	
	
	public String getLastValue(BigDecimal idn_zone){
		ParkingAllocationFormBean formCal = new ParkingAllocationFormBean();
		formCal.setIdn_zone(idn_zone);
		formCal.setGas_day(filters.getGas_day());
		
	  
		formCal.setLast_value(service.getParkLastUserParkValue(formCal));	
		
		if (formCal.getLast_value()==null){
			return "";
		}
		else{
			
			//return formCal.getLast_value().toString();
			BigDecimal bigDec = formCal.getLast_value().setScale(3, RoundingMode.HALF_UP);
			return bigDec.toString();
		}
	}
	
	//Pantalla Allocated *****************************************
	public String getAllocatedDefaultValue(BigDecimal idn_zone) throws ParseException{
		
		ParkingAllocationFormBean formCal = new ParkingAllocationFormBean();
		formCal.setIdn_zone(idn_zone);
		
			
		//ponemos la fecha de pantalla
		if (form.getGas_day()!=null){
			formCal.setGas_day(form.getGas_day());	
			formCal.setDefault_value(service.getParkDefaultValue(formCal));
		}
	
		if (formCal.getDefault_value()==null){
			form.setDefault_value(null);
		   
			return "";
		}
		else{ 
			form.setDefault_value(formCal.getDefault_value());
			//return formCal.getDefault_value().toString();
			BigDecimal bigDec = formCal.getDefault_value().setScale(3, RoundingMode.HALF_UP);
			return bigDec.toString();
		}
	}
	
public String getAllocatedLastValue(BigDecimal idn_zone){

		ParkingAllocationFormBean formCal = new ParkingAllocationFormBean();
		formCal.setIdn_zone(idn_zone);
		
		
		//ponemos la fecha de pantalla
		if (form.getGas_day()!=null){
			formCal.setGas_day(form.getGas_day());
			formCal.setLast_value(service.getParkLastUserParkValue(formCal));	
		}
		
		
		if (formCal.getLast_value()==null){
			return "";
		}
		else{
			
			//return formCal.getLast_value().toString();
			BigDecimal bigDec = formCal.getLast_value().setScale(3, RoundingMode.HALF_UP);
			return bigDec.toString();
		}
	}
	
	public void allocate(BigDecimal idn_zone, BigDecimal parking_value) throws Exception{
		
		ParkingAllocationFormBean formCal = new ParkingAllocationFormBean();
		//fecha de la pantalla sysdate mas 1 dia. No se deja cambiar en la pantalla
		if (form.getGas_day()!=null){
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(form.getGas_day()); // 
		    cal.add(Calendar.DAY_OF_YEAR, 0); 
		    cal.set(Calendar.HOUR_OF_DAY, 0);
	    	cal.set(Calendar.MINUTE, 0);
	    	cal.set(Calendar.SECOND, 0);
	    	cal.set(Calendar.MILLISECOND, 0);
			//formCal.setGas_day(form.getGas_day());
	    	formCal.setGas_day(cal.getTime());
		}
		
		formCal.setParking_value(form.getParking_value());
		
	
		if (form.getIdn_zone().toString().equals("-1")){
			messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Parking Allocation", "Zone not informed. ", Calendar.getInstance().getTime()));
	    	return;
			//formCal.setIdn_zone(null);
		}else{
			formCal.setIdn_zone(form.getIdn_zone());
			}

		String error = "0";
		try {
			error=service.allocated(formCal);
			
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String gasDayFormatted = format1.format(form.getGas_day().getTime());
	
		form.setZone_code(service.getZoneCode(form.getIdn_zone()));
		
		if(error!=null && error.equals("0")){
		
    		messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.INFO,"Parking Allocation. Allocated ok", "Parking Allocation for. " + form.getZone_code() + " and " + gasDayFormatted+ " ok." , Calendar.getInstance().getTime()));
    		log.info("Parking Allocation Allocated: " + form.toString(), Calendar.getInstance().getTime());
    	} else{
    		messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR, "Parking Allocation Not Ok","Error Allocating: " +error, Calendar.getInstance().getTime()));
			log.info("Error allocating. Parking Allocation, Error: " +error + ". "+ form.toString(), Calendar.getInstance().getTime());
    	}
		
		items = service.selectParkingAllocation(filters);
		
		//limpamos el form de allocate 
		form = new ParkingAllocationFormBean();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
    	cal.add(Calendar.DAY_OF_MONTH, 1);
		form.setGas_day(cal.getTime());
	}
	
	
	public void defaultValCal()throws Exception{
		
		ParkingAllocationFormBean formCal = new ParkingAllocationFormBean();
		
		if (form.getGas_day()!=null){
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(form.getGas_day()); // 
		    cal.add(Calendar.DAY_OF_YEAR, -1); //gasday menos 1 dia
		    cal.set(Calendar.HOUR_OF_DAY, 0);
	    	cal.set(Calendar.MINUTE, 0);
	    	cal.set(Calendar.SECOND, 0);
	    	cal.set(Calendar.MILLISECOND, 0);
			formCal.setGas_day(cal.getTime());
		}
		
		if (form.getIdn_zone().toString().equals("-1")){
			messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Parking Allocation", "Zone not informed. ", Calendar.getInstance().getTime()));
	    	return;
		}else{
			formCal.setIdn_zone(form.getIdn_zone());
			}


		String error = "0";
		try {
			error=service.defaultValueCalc(formCal);
			
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		if(error!=null && error.equals("0")){
			
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			String gasDayFormatted = format1.format(form.getGas_day().getTime());
			
			form.setZone_code(service.getZoneCode(form.getIdn_zone()));
			
    		messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.INFO,"Parking Allocation. Default Value Calculated ok", "Default Value for " + form.getZone_code() + " and " + gasDayFormatted+ " ok." , Calendar.getInstance().getTime()) );
    		log.info("Parking Allocation Default Value Calculated " + form.toString(), Calendar.getInstance().getTime());
    	} else{
    		messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR, "Parking Allocation Not Ok","Error Default Value Calculated: " +error, Calendar.getInstance().getTime()));
			log.info("Error Default Value Calculated. Parking Allocation, Error: " +error + ". "+ form.toString(), Calendar.getInstance().getTime());
    	}
		
		items = service.selectParkingAllocation(filters);
		
		//limpamos el form de allocate 
		form = new ParkingAllocationFormBean();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
    	cal.add(Calendar.DAY_OF_MONTH, 1);
		form.setGas_day(cal.getTime());
		
	}
	public void cancel(){
		form = new ParkingAllocationFormBean();
		Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DAY_OF_MONTH, 1);
		form.setGas_day(cal.getTime());
	}
	
	
	public String exitDefault(){
		
		//Si el campo default value tiene algun valor deshabilitamos el boton
		if (form.getDefault_value() != null){
			
			if (form.getDefault_value().toString()!=""){
				return "true";	
			}
		}
		
		//si no hay zona seleccionada deshabilitamos el boton
		if (form.getIdn_zone() != null){
			if (form.getIdn_zone().toString().equals("-1")){
				form.setParking_value(null);
				return "true";
			}
		}
		return "false";
	}
	
}
