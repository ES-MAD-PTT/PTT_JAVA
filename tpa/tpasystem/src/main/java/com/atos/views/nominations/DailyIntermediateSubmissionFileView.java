package com.atos.views.nominations;

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
import com.atos.services.nominations.DailyIntermediateSubmissionFileService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="dailyIntermediateSubmissionFileView")
@ViewScoped
public class DailyIntermediateSubmissionFileView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1804211253281868923L;
	private static final Logger log = LogManager.getLogger(DailyIntermediateSubmissionFileView.class);

	private String shipperComments;
	private UploadedFile file;
	private FileBean uploadFile;
	
	private String nominationSubmissionDeadlinePhrase = null;
	private String renominationSubmissionDeadlinePhrase = null;
	
	@ManagedProperty("#{dailyIntermediateSubmissionFileService}")
    transient private DailyIntermediateSubmissionFileService service;
    
    public void setService(DailyIntermediateSubmissionFileService service) {
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

		// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		String okMessage = null;
    	String errorMsg = null;
    	String summaryMsg = msgs.getString("daily_nomination_submission_file");   	
    	
    	IntermediateNomFileMailBean infmb = null;

    	// Antes de insertar el fichero en la BD se comprueba si existe plantilla/config de mape en la BD 
    	// (tabla tpa_toperation_template).
    	// En principio, no se deben permitir nominaciones diarias para offshore, pero si se inserta
    	// la configuracion en tpa_toperation_template, si se podria.
		if(!service.existsOpTemplate(getChangeSystemView().getIdn_active())){
			String infoMsg = msgs.getString("daily_nomination_not_allowed");
			getMessages().addMessage(Constants.head_menu[1],
					new MessageBean(Constants.INFO, summaryMsg, infoMsg, Calendar.getInstance().getTime()));
	    	log.info(infoMsg);
	    	return;
		}
    	
    	
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
			String error = "0";
			map = service.saveFile(uploadFile, shipperComments, getChangeSystemView().getIdn_active());
			okMessage = map.get("exit").toString();
			summaryMsg = msgs.getString("data_stored");

			getMessages().addMessage(Constants.head_menu[4],
					new MessageBean(Constants.INFO, summaryMsg, okMessage, Calendar.getInstance().getTime()));
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
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = msgs.getString("daily_nomination_template_file");
    	
    	// Se inicializa esta variable para coger el template correspondiente a partir del parametro de entrada.
    	StreamedContent templateFile =  null;
		try {  
			templateFile = service.getFile(getChangeSystemView().getIdn_active());
			
			if(templateFile==null){
				String infoMsg = msgs.getString("daily_nomination_no_template");
				getMessages().addMessage(Constants.head_menu[1],
						new MessageBean(Constants.INFO, summaryMsg, infoMsg, Calendar.getInstance().getTime()));
		    	log.info(infoMsg);
			}
		}
		catch(Exception e){
			String errorMsg = msgs.getString("download_error") + ".";
			getMessages().addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
		}
		
		return templateFile;
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
	
		mailService.sendEmail("DAILY_NOMINATION.SUBMISSION", values, getChangeSystemView().getIdn_active(), tso);
		//Para comprobar los valores que se pasan en el email
		getMessages().addMessage(Constants.head_menu[4],
				new MessageBean(Constants.INFO, "Mail values", texto, Calendar.getInstance().getTime())); 
	
		}
}
