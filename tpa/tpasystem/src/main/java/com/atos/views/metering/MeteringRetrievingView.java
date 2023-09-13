package com.atos.views.metering;

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
import org.primefaces.model.StreamedContent;

import com.atos.beans.LanguageBean;
import com.atos.beans.MessageBean;
import com.atos.beans.UserBean;
import com.atos.beans.metering.MeteringRetrievingBean;
import com.atos.filters.metering.MeteringQualityReportFilter;
import com.atos.filters.metering.MeteringRetrievingFilter;
import com.atos.services.metering.MeteringRetrievingService;
import com.atos.utils.Constants;
import com.atos.views.MessagesView;

@ManagedBean(name = "meteringRetrievingView")
@ViewScoped
public class MeteringRetrievingView implements Serializable {

	
	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(MeteringRetrievingView.class);

	private MeteringRetrievingFilter filters;
	private List<MeteringRetrievingBean> items;
	private List<MeteringRetrievingBean> itemsWarning;
	private MeteringRetrievingBean cabecera;
	

	@ManagedProperty("#{languageBean}")
    private LanguageBean language;
	
	public void setLanguage(LanguageBean language) {
		this.language = language;
	}
	
	private Map<BigDecimal, Object> comboShipper;
	
	@ManagedProperty("#{meteringRetrievingService}")
	transient private MeteringRetrievingService service;

	@ManagedProperty("#{messagesView}")
	private MessagesView messages;


	@ManagedProperty("#{userBean}")
	private UserBean user;

	public void setUser(UserBean user){
		this.user = user;
	}

	
	public MeteringRetrievingFilter getFilters() {
		return filters;
	}

	public void setFilters(MeteringRetrievingFilter filters) {
		this.filters = filters;
	}

	public void setService(MeteringRetrievingService service) {
		this.service = service;
	}

	public void setMessages(MessagesView messages) {
		this.messages = messages;
	}

	

	public List<MeteringRetrievingBean> getItems() {
		return items;
	}

	public void setItems(List<MeteringRetrievingBean> items) {
		this.items = items;
	}
	

	public List<MeteringRetrievingBean> getItemsWarning() {
		return itemsWarning;
	}


	public void setItemsWarning(List<MeteringRetrievingBean> itemsWarning) {
		this.itemsWarning = itemsWarning;
	}


	public MeteringRetrievingBean getCabecera() {
		return cabecera;
	}


	public void setCabecera(MeteringRetrievingBean cabecera) {
		this.cabecera = cabecera;
	}


	@PostConstruct
	public void init() {
		filters = new MeteringRetrievingFilter();
	
		Calendar calMaxToDate = Calendar.getInstance(); 
    	calMaxToDate.set(Calendar.HOUR_OF_DAY, 0);
    	calMaxToDate.set(Calendar.MINUTE, 0);
    	calMaxToDate.set(Calendar.SECOND, 0);
    	calMaxToDate.set(Calendar.MILLISECOND, 0);
    	
    	filters.setGeneratedDayFrom(calMaxToDate.getTime());
    	
    	calMaxToDate.set(Calendar.HOUR_OF_DAY, 23);
    	calMaxToDate.set(Calendar.MINUTE, 59);
    	calMaxToDate.set(Calendar.SECOND, 59);
    	calMaxToDate.set(Calendar.MILLISECOND, 59);
    	
    	filters.setGeneratedDayTo(calMaxToDate.getTime());


	}

	
	public Map<BigDecimal, Object> getShippers() {
		return comboShipper;
	}
	
	
	public Map<BigDecimal, Object> getMeteringInputCodes() {
		return service.selectMeteringInputCodes(filters);
	}
	
	

	// Methods
	public void onClear() {
		filters = new MeteringRetrievingFilter();
		
		Calendar calMaxToDate = Calendar.getInstance(); 
    	calMaxToDate.set(Calendar.HOUR_OF_DAY, 0);
    	calMaxToDate.set(Calendar.MINUTE, 0);
    	calMaxToDate.set(Calendar.SECOND, 0);
    	calMaxToDate.set(Calendar.MILLISECOND, 0);
    	
    	filters.setGeneratedDayFrom(calMaxToDate.getTime());
    	
    	calMaxToDate.set(Calendar.HOUR_OF_DAY, 23);
    	calMaxToDate.set(Calendar.MINUTE, 59);
    	calMaxToDate.set(Calendar.SECOND, 59);
    	calMaxToDate.set(Calendar.MILLISECOND, 59);
    	
    	filters.setGeneratedDayTo(calMaxToDate.getTime());
        if (items != null) {
            items.clear();
        }
		
	}
	
	public void onSearch() {
		
		// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	
    	
		if((filters.getGeneratedDayFrom()==null) || (filters.getGeneratedDayTo()==null)){
	    	messages.addMessage(Constants.head_menu[7],
	    			new MessageBean(Constants.ERROR,
	    					msgs.getString("menu_metering_retrieving"), 
	    					msgs.getString("met_man_required_from_to_error"), 
							Calendar.getInstance().getTime()));
	    	log.error("From field cannot be empty.");
	    	return;
    	}
    	
    	if((filters.getGeneratedDayFrom()!=null) && (filters.getGeneratedDayTo()!=null)){
    		if(filters.getGeneratedDayFrom().after(filters.getGeneratedDayTo())){
    	    	messages.addMessage(Constants.head_menu[7],
    	    			new MessageBean(Constants.ERROR,
    	    					msgs.getString("menu_metering_retrieving"), 
    							msgs.getString("from_must_earlier_equal"), 
    							Calendar.getInstance().getTime()));
    	    	log.error("From date must be previous or equal to To date.");
    	    	return;
    		}
    	}
    	
    	if (filters.getIdnMeteringInput() == null) {
    		messages.addMessage(Constants.head_menu[7],
	    			new MessageBean(Constants.ERROR,
	    					msgs.getString("menu_metering_retrieving"), 
	    					msgs.getString("met_ret_required_metering_input_error"), 
							Calendar.getInstance().getTime()));
	    	log.error("Metering Input Code cannot be empty.");
	    	return;
		}
		items = service.selectMeteringRetrieving(filters);
		itemsWarning = service.selectMeteringRetrievingWarning(filters);
		cabecera= service.selectCabMetRetrieving(filters);
		
	}

	public StreamedContent obtenerfileXml(){
		//return service.getFile(filters);
		return service.getFile(cabecera.getIdnMeteringInput());
	}
	
	
	public String renderFileXml(){
		
    	if (cabecera!=null && cabecera.getInputCode()!=null) {
      		return "false";
       	}else{
       		return "true";
       	}	
		
	}
	
	 public int getItemsSize() { 
			if(this.items!=null && !this.items.isEmpty()){
				return this.items.size();
			}else{
				return 0;
			}
		}
	
	 public int getItemsWarningSize() { 
			if(this.items!=null && !this.itemsWarning.isEmpty()){
				return this.itemsWarning.size();
			}else{
				return 0;
			}
		}
}