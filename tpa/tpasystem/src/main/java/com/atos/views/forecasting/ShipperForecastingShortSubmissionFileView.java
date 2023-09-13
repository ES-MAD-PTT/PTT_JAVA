package com.atos.views.forecasting;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.FileBean;
import com.atos.beans.MessageBean;
import com.atos.beans.ValidateIntervalBean;
import com.atos.filters.forecasting.ShipperForecastingSubmissionFileFilter;
import com.atos.services.forecasting.ShipperForecastingShortSubmissionFileService;
import com.atos.utils.Constants;
import com.atos.views.ChangeSystemView;
import com.atos.views.MessagesView;

@ManagedBean(name="shipperForecastingShortSubmissionFileView")
@ViewScoped
public class ShipperForecastingShortSubmissionFileView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5586722369847896940L;

	private static final Logger log = LogManager.getLogger(ShipperForecastingShortSubmissionFileView.class);

	private ShipperForecastingSubmissionFileFilter filters;
	private UploadedFile file;
	private FileBean uploadFile;
	
	@ManagedProperty("#{shipperForecastingShortSubmissionFileService}")
    transient private ShipperForecastingShortSubmissionFileService service;

	@ManagedProperty("#{changeSystemView}")
	transient ChangeSystemView changeSystemView;
    
    public void setService(ShipperForecastingShortSubmissionFileService service) {
        this.service = service;
    }

	@ManagedProperty("#{messagesView}")
    private MessagesView messages;

    public void setMessages(MessagesView messages) {
        this.messages = messages;
    }

	public ShipperForecastingSubmissionFileFilter getFilters() {
		return filters;
	}

	public void setFilters(ShipperForecastingSubmissionFileFilter filters) {
		this.filters = filters;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	private Calendar sysdate;
	public Calendar getSysdate() {
			return sysdate;
	}
	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}
	
	@PostConstruct
    public void init() {
    	filters = new ShipperForecastingSubmissionFileFilter();
    	filters.setUser((String)SecurityUtils.getSubject().getPrincipal());
		filters.setIdnSystem(changeSystemView.getIdn_active());
    	sysdate = Calendar.getInstance();
    	
    	/*Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DAY_OF_MONTH, 1);
    	filters.setStart_date(cal.getTime());*/
    }
		


	public void handleFileUpload(FileUploadEvent event) {String summaryMsg = null;
	String errorMsg = null;
	
	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
	summaryMsg = msgs.getString("saving_error");
	
	if(filters.getStart_date()==null){
		errorMsg = msgs.getString("from_date_selected");
		messages.addMessage(Constants.head_menu[2],
				new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
    	log.error(errorMsg);
		return;
	}
	
	if(filters.getStart_date()==null  || filters.getEnd_date()==null ){
		//errorMsg = msgs.getString("date_selected");
		errorMsg = " From Date is not valid for this type of forecasting";
		messages.addMessage(Constants.head_menu[2],
				new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
    	log.error(errorMsg);
		return;
	}
	Calendar cal = Calendar.getInstance();
	if(cal.getTime().after(filters.getStart_date()) ||  cal.getTime().after(filters.getEnd_date()) ){
		//errorMsg = msgs.getString("start_previous_end"); defect 285768
		errorMsg = msgs.getString("from_to_future");
		messages.addMessage(Constants.head_menu[2],
				new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
    	log.error(errorMsg);
		return;
	}
	
	if ((filters.getStart_date().after(filters.getEnd_date()))){
		errorMsg = msgs.getString("from_must_earlier_equal");
		messages.addMessage(Constants.head_menu[2],
				new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
    	log.error(errorMsg);    		
		return;
	}    	
	

	file = event.getFile();
	if(file!=null){
		uploadFile = new FileBean(file.getFileName(), file.getContentType(), file.getContents());
	}
    
	if(file==null || uploadFile==null){			
		errorMsg = msgs.getString("forecasting_file_selected");
		messages.addMessage(Constants.head_menu[2],
				new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
    	log.error(errorMsg);    					
		return;
	}
			
		
		String error = "0";
		try {
		error = service.saveFile(filters, uploadFile);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(error!=null && error.equals("-1")){
			errorMsg = msgs.getString("forecasting_short_not_exit");
			messages.addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);				
		} else if(error!=null && error.equals("-2")){			
			errorMsg = msgs.getString("failed_insert_operation_file");
			messages.addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);										
		} else if(error!=null && error.equals("-3")){			
			errorMsg = msgs.getString("user_group_not_exit");
			messages.addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);															
		} else if(error!=null && error.equals("-4")){
			errorMsg = msgs.getString("failed_insert_operation_file");
			messages.addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);										
		} else if(error!=null && error.equals("-5")){				
			errorMsg = msgs.getString("period_not_valid");
			messages.addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg,   sdf.format(filters.getStart_date()) +"-"  + sdf.format(filters.getEnd_date()) +  errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);										
						
		} else if(error!=null && error.equals("-6")){			
			errorMsg = msgs.getString("hour_short_deadline");
			messages.addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg,   sdf.format(filters.getStart_date()) +"-"  + sdf.format(filters.getEnd_date()) +  errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);					
		} else if(error!=null && error.equals("0")){		
			summaryMsg = msgs.getString("file_saved");
			errorMsg = msgs.getString("forecasting_created");
			messages.addMessage(Constants.head_menu[2],
					new MessageBean(Constants.INFO, summaryMsg,   sdf.format(filters.getStart_date()) +"-"  + sdf.format(filters.getEnd_date()) +  errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);				
		} else {
			summaryMsg = msgs.getString("saving_error");
			errorMsg = msgs.getString("failed_save");
			messages.addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
		}
       
	}
	
	public void handleKeyEvent() {
	       
	    	String summaryMsg = null;
			String errorMsg = " From Date is not valid for this type of forecasting";
			
			summaryMsg = "";
			
	    	Date interval_start = this.filters.getStart_date();
	    	
	    	if (interval_start!=null ){
	    		ValidateIntervalBean valForecastingInterval = service.getValidateForecastingShortInterval(interval_start);
	       	 
	        	Date max_end_date = valForecastingInterval.getMax_end_date();
	        	
	        	if (max_end_date!=null){
	        		filters.setEnd_date(max_end_date);
	        		return;
	        	} else {
	        		messages.addMessage(Constants.head_menu[2],
	    					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	    	log.error(errorMsg);
	        		filters.setEnd_date(null);
	        		return;
	        	}  		
	    	}
	    }

	public ChangeSystemView getChangeSystemView() {
		return changeSystemView;
	}

	public void setChangeSystemView(ChangeSystemView changeSystemView) {
		this.changeSystemView = changeSystemView;
	}
	
}
