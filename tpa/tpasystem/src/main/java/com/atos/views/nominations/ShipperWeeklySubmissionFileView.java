package com.atos.views.nominations;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.FileBean;
import com.atos.beans.MessageBean;
import com.atos.filters.nominations.ShipperSubmissionFileFilter;
import com.atos.services.nominations.ShipperWeeklySubmissionFileService;
import com.atos.utils.Constants;
import com.atos.views.MessagesView;

@ManagedBean(name="shipperWeeklySubmissionFileView")
@ViewScoped
public class ShipperWeeklySubmissionFileView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1804211253281868923L;
	private static final Logger log = LogManager.getLogger(ShipperWeeklySubmissionFileView.class);

	private ShipperSubmissionFileFilter filters;
	private UploadedFile file;
	private FileBean uploadFile;
	private int dayStartWeek;

	private String nominationSubmissionDeadlinePhrase = null;
	private String renominationSubmissionDeadlinePhrase = null;
	
	@ManagedProperty("#{shipperWeeklySubmissionFileService}")
    transient private ShipperWeeklySubmissionFileService service;
    
    public void setService(ShipperWeeklySubmissionFileService service) {
        this.service = service;
    }

	@ManagedProperty("#{messagesView}")
    private MessagesView messages;

    public void setMessages(MessagesView messages) {
        this.messages = messages;
    }

	public ShipperSubmissionFileFilter getFilters() {
		return filters;
	}

	public void setFilters(ShipperSubmissionFileFilter filters) {
		this.filters = filters;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public int getDayStartWeek() {
		return dayStartWeek;
	}

	public void setDayStartWeek() {
		this.dayStartWeek = service.selectStartDayOfWeek();
	}

	public String getNominationSubmissionDeadlinePhrase() {
		return nominationSubmissionDeadlinePhrase;
	}

	public String getRenominationSubmissionDeadlinePhrase() {
		return renominationSubmissionDeadlinePhrase;
	}


	@PostConstruct
    public void init() {
    	filters = new ShipperSubmissionFileFilter();
    	Calendar cal = Calendar.getInstance();
    	int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
    	if(day_of_week == Calendar.MONDAY){
    		cal.add(Calendar.DAY_OF_MONTH, 6);
    	} else if(day_of_week == Calendar.TUESDAY){
    		cal.add(Calendar.DAY_OF_MONTH, 5);
    	} else if(day_of_week == Calendar.WEDNESDAY){
    		cal.add(Calendar.DAY_OF_MONTH, 4);
    	} else if(day_of_week == Calendar.THURSDAY){
    		cal.add(Calendar.DAY_OF_MONTH, 3);
    	} else if(day_of_week == Calendar.FRIDAY){
    		cal.add(Calendar.DAY_OF_MONTH, 2);
    	} else if(day_of_week == Calendar.SATURDAY){
    		cal.add(Calendar.DAY_OF_MONTH, 1);
    	}
    	this.setDayStartWeek();
    	filters.setStart_date(cal.getTime());
    	
    	nominationSubmissionDeadlinePhrase = service.selectOperationSubmissionDeadlinePhrase(Constants.STANDARD_RECEPTION);
    	renominationSubmissionDeadlinePhrase = service.selectOperationSubmissionDeadlinePhrase(Constants.RENOMINATION_RECEPTION);    	
    }
	
	
	public Map<String, Object> getContractCodes() {
		filters.setUser((String)SecurityUtils.getSubject().getPrincipal());
		return service.selectContractCodeByUser(filters);
	}


	public void handleFileUpload(FileUploadEvent event) {
		if(filters.getStart_date()==null){
			messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error saving file","You must select a date", Calendar.getInstance().getTime()));
			return;
		}
		Calendar cal = Calendar.getInstance();
    	if(cal.getTime().after(filters.getStart_date())){
			messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error saving file","The Gas Day should be a future gas day", Calendar.getInstance().getTime()));
			return;
    	}
		if(filters.getContract_code()==null || filters.getContract_code().equals("")){
			messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error saving file","You must select a Contract Code", Calendar.getInstance().getTime()));
			return;
		}
		file = event.getFile();
		if(file!=null){
			uploadFile = new FileBean(file.getFileName(), file.getContentType(), file.getContents());
		}

        if(file==null || uploadFile==null){
			messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error saving file","The nomination file should be selected", Calendar.getInstance().getTime()));
			return;
		}

		
		// Para poder diferenciar errores no controlados por un lado y resultado OK
		// por otro, los errores, sean controlados o no, se van a devolver como 
		// excepciones.
		String error = null;
        String ret = null;
		try {
			ret = service.saveFile(filters, uploadFile);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}

		if(error!=null && error.equals("-1")){
			messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error saving file","The operation NOMINATION + WEEKLY does not exist", Calendar.getInstance().getTime()));			
		} else if(error!=null && error.equals("-2")){
			messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error saving file","Failed when trying to insert the file in operation file", Calendar.getInstance().getTime()));			
		} else if(error!=null && error.equals("-3")){
			messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error saving file","The user group does not exist", Calendar.getInstance().getTime()));			
		} else if(error!=null && error.equals("-4")){
			messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error saving file","Failed when trying to insert the file in nomination", Calendar.getInstance().getTime()));			
		} else if(error!=null && error.equals("-5")){
			messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error saving file","Failed when trying to insert the file in nomination, incrementing the version", Calendar.getInstance().getTime()));			
		} else if(error!=null && error.startsWith("-6")){
			String[] tmpaString = error.split(ShipperWeeklySubmissionFileService.errorSeparator, 2);
			// Se muestra al usuario el mensaje de error que se recibe tras el codigo de error.
			messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR,"Error saving file",tmpaString[1], Calendar.getInstance().getTime()));
		} else if(error!=null){
			// Aqui se detectarian errores no controlados (ORACLE, etc).
			// Este error tecnico no se muestra al usuario. Solo se muestra un error generico.
			messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error saving file","Failed to save file.", Calendar.getInstance().getTime()));						
		} else {
			// Si ret == null, se pinta null.	
			messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.INFO,"File saved correctly","Data stored. Nomination has been created: " + ret, Calendar.getInstance().getTime()));
		}
        
    }

}
