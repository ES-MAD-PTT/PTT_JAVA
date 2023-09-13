package com.atos.views.forecasting;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.FileBean;
import com.atos.beans.MessageBean;
import com.atos.beans.forecasting.ForecastingMailBean;
import com.atos.services.MailService;
import com.atos.services.allocation.AllocationReviewService;
import com.atos.services.forecasting.ForecastingMediumIntermediateSubmissionFileService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="forecastingMediumIntermediateSubmissionFileView")
@ViewScoped
public class ForecastingMediumIntermediateSubmissionFileView extends CommonView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8346186093545699295L;

	private static final Logger log = LogManager.getLogger(ForecastingMediumIntermediateSubmissionFileView.class);

	private UploadedFile file;
	private FileBean uploadFile;
	
	@ManagedProperty("#{forecastingMediumIntermediateSubmissionFileService}")
    transient private ForecastingMediumIntermediateSubmissionFileService service;
    
    public void setService(ForecastingMediumIntermediateSubmissionFileService service) {
        this.service = service;
    }
    
    @ManagedProperty("#{mailService}")
    transient private MailService mailService;

    public void setMailService(MailService mailService) {
    	this.mailService = mailService;
    }
    
    @ManagedProperty("#{AllocationReviewService}")
    transient private AllocationReviewService serviceAlloc;
    
	public void setServiceAlloc(AllocationReviewService serviceAlloc) {
		this.serviceAlloc = serviceAlloc;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	@PostConstruct
    public void init() {
    }
	
	public void handleFileUpload(FileUploadEvent event) {
		String summaryMsg = null;
		String errorMsg = null;
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		summaryMsg = msgs.getString("saving_error");
		
		file = event.getFile();
		if(file!=null){
			uploadFile = new FileBean(file.getFileName(), file.getContentType(), file.getContents());
		}
        
		if(file==null || uploadFile==null){		
			errorMsg = msgs.getString("forecasting_file_selected");
			getMessages().addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
		}

		
		HashMap<String,Object> map = null;
		String error = "0";

		try {
			map = service.saveFile(uploadFile, getUser().getUsername(), getChangeSystemView().getIdn_active());
			error = map.get("exit").toString();
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}

		if(error!=null && error.equals("-1")){
			errorMsg = msgs.getString("forecasting_medium_not_exit");
			getMessages().addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);			
		} else if(error!=null && error.equals("-2")){
			errorMsg = msgs.getString("failed_insert_operation_file");
			getMessages().addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);			
		} else if(error!=null && error.equals("-3")){			
			errorMsg = msgs.getString("file_format_incorrect");
			getMessages().addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);			
		}else if(error!=null && error.startsWith("-4")){
			
			getMessages().addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, error.substring(3), Calendar.getInstance().getTime()));
	    	log.error(errorMsg);			
		}else if(error!=null && error.startsWith("-5")){			
			errorMsg = msgs.getString("data_not_stored");
			getMessages().addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);			
		}
		else if(error!=null){
			summaryMsg = msgs.getString("file_saved");
			errorMsg = msgs.getString("forecasting_created");
			getMessages().addMessage(Constants.head_menu[2],
					new MessageBean(Constants.INFO, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
	    	BigDecimal idn_forecasting = (BigDecimal)map.get("idn");
	    	ForecastingMailBean fmb = service.getInfoShipperSubForecastingBean(idn_forecasting);
	    	sendMail(fmb);
		} else {
			summaryMsg = msgs.getString("saving_error");
			errorMsg = msgs.getString("failed_save");
			getMessages().addMessage(Constants.head_menu[2],
					new MessageBean(Constants.INFO, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
		}
        
        //messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.INFO,"File uploaded",file.getFileName() + " is uploaded. Now, you need to push the Save button to store the file", Calendar.getInstance().getTime()));
    }
	public StreamedContent getTemplate(){
		return service.getFile(getChangeSystemView().getIdn_active());
	}
	
	public void sendMail(ForecastingMailBean fmb) {
		HashMap<String,String> values = new HashMap<String,String>();
			values.put("1", fmb.getUser_group_id());
			values.put("2", fmb.getTerm_desc());
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String dateStringStart = df.format(fmb.getStart_date());
			String dateStringEnd = df.format(fmb.getEnd_date());
			values.put("3", dateStringStart);
			values.put("4", dateStringEnd);
			String texto = "Shipper:"+values.get("1")+",Term:"+values.get("2")+
					",Start Date:"+values.get("3")+",End Date:"+values.get("4");
			
			BigDecimal tso = new BigDecimal(-1);
			try {
				tso = serviceAlloc.getDefaultOperatorId(getUser(), getLanguage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		mailService.sendEmail("FORECASTING.SUBMISSION", values, getChangeSystemView().getIdn_active(), tso);
		//Para comprobar los valores que se pasan en el email
		getMessages().addMessage(Constants.head_menu[2],
				new MessageBean(Constants.INFO, "Mail values", texto, Calendar.getInstance().getTime())); 
	}
}
