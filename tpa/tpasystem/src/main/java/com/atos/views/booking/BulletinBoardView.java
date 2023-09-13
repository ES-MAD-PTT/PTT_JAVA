package com.atos.views.booking;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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

import com.atos.beans.MessageBean;
import com.atos.beans.booking.BulletinBoardBean;
import com.atos.beans.booking.BulletinBoardMailBean;
import com.atos.beans.booking.CapacityRequestSubmissionBean;
import com.atos.beans.booking.OperationFileBean;
import com.atos.exceptions.ValidationException;
import com.atos.services.MailService;
import com.atos.services.allocation.AllocationReviewService;
import com.atos.services.booking.BulletinBoardService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="BulletinBoardView")
@ViewScoped
public class BulletinBoardView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1839170737958438832L;

	private List<BulletinBoardBean> items;

	// CH011 - 05/09/16 - Se anade a esta pagina la funcionalidad de CRSubmission de descarga de template y envio ficheros excel.	
	// Esta es la template que se presentara al usuario. Segun pulse el enlace de una fila u otra, se va actualizando.
	private StreamedContent templateFile =  null;
	
	@ManagedProperty("#{BulletinBoardService}")
    transient private BulletinBoardService service;
	
	public void setService(BulletinBoardService service) {
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

	
	private static final Logger log = LogManager.getLogger("com.atos.views.booking.BulletinBoardView");
	
	// getters/setters 
	public List<BulletinBoardBean> getItems() {
		return items;
	}

	public void setItems(List<BulletinBoardBean> items) {
		this.items = items;
	}
	
	public StreamedContent getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(StreamedContent templateFile) {
		this.templateFile = templateFile;
	}
	
	@PostConstruct
    public void init() {
        items = service.search(getUser(), getLanguage());  
    }

	
    public void selectTemplateFile( String termCode ) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = msgs.getString("cr_bb_template_file");
    	
    	// Se inicializa esta variable para coger el template correspondiente a partir del parametro de entrada.
		try {  
			templateFile = service.selectTemplateFile(termCode, getChangeSystemView());
		}
		catch(Exception e){
			String errorMsg = msgs.getString("download_error") + ".";
			getMessages().addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
		}
	}


	public void handleFileUpload( FileUploadEvent event) {

		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	UploadedFile uUploadedFile = null;
    	OperationFileBean ofbUploadedFile = null;
    	String strTermCode = null;
    	//String resWarnings = null;
    	String okMessage = null;
    	String okSummaryMessage = null;
    	String summaryMsg = null;
    	String errorMsg = null;
    	
    	BulletinBoardMailBean bbmb=null;
    	
    	CapacityRequestSubmissionBean crsb;

    	strTermCode = (String) event.getComponent().getAttributes().get("fileUploadTermCode");
		
		uUploadedFile = event.getFile();
		if(uUploadedFile!=null){
			ofbUploadedFile = new OperationFileBean();
			ofbUploadedFile.setFileName(uUploadedFile.getFileName());
			ofbUploadedFile.setBinaryData(uUploadedFile.getContents());
		}
        
		if(uUploadedFile==null || ofbUploadedFile==null){
	    	summaryMsg = msgs.getString("saving_error");			
    		errorMsg = msgs.getString("file_must_selected");
			getMessages().addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
		}

		try {
			crsb = service.saveFile(strTermCode, ofbUploadedFile, getUser(), getLanguage(), getChangeSystemView());
			okSummaryMessage = msgs.getString("data_stored");
			if( crsb.getWarnings() != null )
				okMessage = okSummaryMessage + 
							" There are some warnings in the loaded file.";
						/*	" Warnings: " + 
							resWarnings.replace(";", System.getProperty("line.separator"));*/
			else
				okMessage = okSummaryMessage;

			getMessages().addMessage(Constants.head_menu[1],
					new MessageBean(Constants.INFO, okSummaryMessage, okMessage, Calendar.getInstance().getTime()));
			if(crsb.getIdn_contract_request()!=null)
				bbmb = service.getInfoMailBulletingBoard(crsb.getIdn_contract_request());
			sendMail(bbmb);
				
		} 
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = ve.getMessage();
    		getMessages().addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));		
		}
		catch (Exception e) {
			summaryMsg = msgs.getString("saving_data_error");
    		errorMsg = msgs.getString("rel_cap_sub_data_not_stored") + " " + msgs.getString("internal_error");
    		getMessages().addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
    		// Se guarda el detalle del error tecnico.
	    	log.error(e.getMessage(), e);			
		}
    }
	
	public void sendMail(BulletinBoardMailBean b) {
		HashMap<String,String> values = new HashMap<String,String>();
			values.put("1", b.getUser_group_id());
			values.put("2", b.getRequest_code());
			values.put("3", b.getTerm_desc());
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String dateStringStart = df.format(b.getStart_date());
			String dateStringEnd = df.format(b.getEnd_date());
			values.put("4", dateStringStart);
			values.put("5", dateStringEnd);
			String texto = "Shipper:"+values.get("1")+",Request Code:"+values.get("2")+",Contract Type:"+values.get("3")+
					",Start Date:"+values.get("4")+",End Date:"+values.get("5");
			
			BigDecimal tso = new BigDecimal(-1);
			try {
				tso = serviceAlloc.getDefaultOperatorId(getUser(), getLanguage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		mailService.sendEmail("CAP_REQUEST.SUBMISSION", values, getChangeSystemView().getIdn_active(), tso);
		//Para comprobar los valores que se pasan en el email
		getMessages().addMessage(Constants.head_menu[1],
				new MessageBean(Constants.INFO, "Mail values", texto, Calendar.getInstance().getTime())); 
	}
}
