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
import org.primefaces.event.RowEditEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.dam.EmailNotificationManagementBean;
import com.atos.filters.dam.EmailNotificationManagementFilter;
import com.atos.services.dam.EmailNotificationManagementService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="emailNotifManagementView")
@ViewScoped
public class EmailNotificationManagementView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(EmailNotificationManagementView.class);
	
	private EmailNotificationManagementFilter filters;
	private List<EmailNotificationManagementBean> items;
	
	@ManagedProperty("#{EmailNotificationManagementService}")
    transient private EmailNotificationManagementService service;
    
    public void setService(EmailNotificationManagementService service) {
        this.service = service;
    }

	Calendar sysdate;
	public Calendar getSysdate() {
		return sysdate;
	}
	
	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}
	
	
	public EmailNotificationManagementFilter getFilters() {
		return filters;
	}

	public void setFilters(EmailNotificationManagementFilter filters) {
		this.filters = filters;
	}
	
	public List<EmailNotificationManagementBean> getItems() {
		return items;
	}
	
	public void setItems(List<EmailNotificationManagementBean> items) {
		this.items = items;
	}
	
	@PostConstruct
    public void init() {
    	filters = new EmailNotificationManagementFilter();
    }
	
	public Map<BigDecimal, Object> getIdsModule() {
		return service.selectModulesIds();
	}
	
	public void onSearch(){
		items = service.selectEmails(filters); 
	}
	
	public void onClear(){
		filters = new EmailNotificationManagementFilter();
        if (items != null) {
            items.clear();
        }
    }
	
	public void onRowEdit(RowEditEvent event) {

		EmailNotificationManagementBean bean = (EmailNotificationManagementBean) event.getObject();
	    
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
 		
    	String error = "0";
		bean.setUser(getUser().getUsername());
		if(bean.isEnabled())
			bean.setStrEnabled("Y");
		else
			bean.setStrEnabled("N");
    	try {
			error = service.insertLogAndUpdateEmail(bean);
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
    
    public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
}

