package com.atos.views.nominations;

import java.io.Serializable;
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
import com.atos.beans.nominations.IntermediateNomFileMailBean;
import com.atos.exceptions.ValidationException;
import com.atos.services.MailService;
import com.atos.services.allocation.AllocationReviewService;
import com.atos.services.nominations.WeeklyIntermediateSubmissionFileService;
import com.atos.utils.Constants;
import com.atos.views.MessagesView;
import com.atos.views.CommonView;

@ManagedBean(name="weeklyIntermediateSubmissionFileView")
@ViewScoped
public class WeeklyIntermediateSubmissionFileView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1804211253281868923L;
	private static final Logger log = LogManager.getLogger(WeeklyIntermediateSubmissionFileView.class);

	private String shipperComments;
	private UploadedFile file;
	private FileBean uploadFile;

	private String nominationSubmissionDeadlinePhrase = null;
	private String renominationSubmissionDeadlinePhrase = null;
	
	@ManagedProperty("#{weeklyIntermediateSubmissionFileService}")
    transient private WeeklyIntermediateSubmissionFileService service;
    
    public void setService(WeeklyIntermediateSubmissionFileService service) {
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

	public String getShipperComments() {
		return shipperComments;
	}

	public void setShipperComments(String shipperComments) {
		this.shipperComments = shipperComments;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getNominationSubmissionDeadlinePhrase() {
		return nominationSubmissionDeadlinePhrase;
	}

	public String getRenominationSubmissionDeadlinePhrase() {
		return renominationSubmissionDeadlinePhrase;
	}

	@PostConstruct
    public void init() {
    	shipperComments = null;
    	nominationSubmissionDeadlinePhrase = service.selectOperationSubmissionDeadlinePhrase(Constants.STANDARD_RECEPTION);
    	renominationSubmissionDeadlinePhrase = service.selectOperationSubmissionDeadlinePhrase(Constants.RENOMINATION_RECEPTION);
    }
	
	public void handleFileUpload(FileUploadEvent event) {
		
		String okMessage = null;
    	String okSummaryMessage = null;
    	String summaryMsg = null;
    	String errorMsg = null;
    	
    	IntermediateNomFileMailBean infmb = null;

		// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		file = event.getFile();
		if(file!=null){
			uploadFile = new FileBean(file.getFileName(), file.getContentType(), file.getContents());
		}
        
		if(file==null || uploadFile==null){
			getMessages().addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error saving file","The nomination file should be selected", Calendar.getInstance().getTime()));
			return;
		}

		try {
			HashMap<String,Object> map = null;
			map = service.saveFile(uploadFile, shipperComments, getChangeSystemView().getIdn_active());
			okMessage = map.get("exit").toString();
			okSummaryMessage = msgs.getString("data_stored");

			getMessages().addMessage(Constants.head_menu[4],
					new MessageBean(Constants.INFO, okSummaryMessage, okMessage, Calendar.getInstance().getTime()));
			if(map.get("idn")!=null) {
				BigDecimal idn = BigDecimal.valueOf(Double.valueOf(map.get("idn").toString()));
				infmb = service.getInfoMailSubNomBean(idn);
				sendMail(infmb);
			}
				
		} 
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = ve.getMessage();
    		getMessages().addMessage(Constants.head_menu[4],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));		
		}
		catch (Exception e) {
			summaryMsg = msgs.getString("saving_data_error");
    		errorMsg = msgs.getString("data_not_stored_error") + " " + msgs.getString("internal_error");
    		getMessages().addMessage(Constants.head_menu[4],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
    		// Se guarda el detalle del error tecnico.
	    	log.error(e.getMessage(), e);			
		}
    }

	public StreamedContent getTemplate(){
		return service.getFile(getChangeSystemView().getIdn_active());
	}
	
	public void sendMail(IntermediateNomFileMailBean b) {
		HashMap<String,String> values = new HashMap<String,String>();
		values.put("1", b.getUser_group_id());
		values.put("2", b.getContract_code());
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String dateStringStart = df.format(b.getStart_date());
		values.put("3", dateStringStart);
		String texto = "Shipper:"+values.get("1")+",Contract Code:"+values.get("2")+",Start Date:"+values.get("3");
		
		BigDecimal tso = new BigDecimal(-1);
		try {
			tso = serviceAlloc.getDefaultOperatorId(getUser(), getLanguage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	mailService.sendEmail("WEEKLY_NOMINATION.SUBMISSION", values, getChangeSystemView().getIdn_active(), tso);
	//Para comprobar los valores que se pasan en el email
	getMessages().addMessage(Constants.head_menu[4],
			new MessageBean(Constants.INFO, "Mail values", texto, Calendar.getInstance().getTime())); 

	}
}
