package com.atos.views.scadaAlarms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.FileBean;
import com.atos.beans.MessageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.UserBean;
import com.atos.beans.allocation.AllocationBean;
import com.atos.beans.allocation.AllocationReportDetailDto;
import com.atos.beans.allocation.AllocationReportDto;
import com.atos.beans.dam.GuestUserBean;
import com.atos.beans.dam.ShipperBean;
import com.atos.beans.scadaAlarms.EmergencyDiffDayBean;
import com.atos.beans.scadaAlarms.EmergencyDiffDayDetailsBean;
import com.atos.beans.scadaAlarms.EmergencyDiffDayIsShipperBean;
import com.atos.beans.scadaAlarms.IdEventBean;
import com.atos.beans.scadaAlarms.ScadaLabelBean;
import com.atos.beans.scadaAlarms.ScadaPointBean;
import com.atos.beans.scadaAlarms.TagnameManagementBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.dam.GuestUserFilter;
import com.atos.filters.scadaAlarms.EmergencyDifficultDayFilter;
import com.atos.filters.scadaAlarms.TagnameManagementFilter;
import com.atos.mapper.scadaAlarms.EmergencyDiffDayMapper;
import com.atos.services.MailService;
import com.atos.services.allocation.AllocationReviewService;
import com.atos.services.balancing.BalanceManagementService;
import com.atos.services.dam.ShipperService;
import com.atos.services.scadaAlarms.EmergencyDiffDayService;
import com.atos.services.scadaAlarms.TagnameManagementService;
import com.atos.utils.Constants;
import com.atos.views.ChangeSystemView;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;
import com.atos.views.dam.GuestUserView;

@ManagedBean(name="emergencyDiffDayView")
@ViewScoped
public class EmergencyDiffDayView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4826249901263281893L;

	private EmergencyDifficultDayFilter filters;
	
	private List<EmergencyDiffDayBean> items;
	
	private EmergencyDiffDayBean selected;
	
	private EmergencyDiffDayBean newEvent;
	private EmergencyDiffDayDetailsBean editEventShipper;
	
	private UploadedFile file;
	private FileBean uploadFile = null;
	private FileBean uploadFileCloseOperator = null;
	private FileBean uploadFileOpenShipper = null;
	private FileBean uploadFileCloseShipper = null;
	
	private Date currentDate = new Date();	

	private static final Logger log = LogManager.getLogger(EmergencyDiffDayView.class);
	
	@ManagedProperty("#{userBean}")
    private UserBean userBean;

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
    
	@ManagedProperty("#{messagesView}")
    private MessagesView messages;

    public void setMessages(MessagesView messages) {
        this.messages = messages;
    }
	
	@ManagedProperty("#{emergencyDiffDayService}")
    transient private EmergencyDiffDayService service;
    
    public void setService(EmergencyDiffDayService service) {
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

	public EmergencyDifficultDayFilter getFilters() {
		return filters;
	}

	public void setFilters(EmergencyDifficultDayFilter filters) {
		this.filters = filters;
	}
	
	public List<EmergencyDiffDayBean> getItems() {
		return items;
	}
	
	public void setItems(List<EmergencyDiffDayBean> items) {
		this.items = items;
	}
       
	public EmergencyDiffDayBean getSelected() {
		return selected;
	}

	public void setSelected(EmergencyDiffDayBean selected) {
		this.selected = selected;
	}
	
	public Map<BigDecimal, Object> getEventType() {
		return service.selectEventType();
	}
	
	public Map<BigDecimal, Object> getZone() {
		return service.selectZone(getChangeSystemView().getIdn_active());
	}
	
	public EmergencyDiffDayBean getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(EmergencyDiffDayBean newEvent) {
		this.newEvent = newEvent;
	}
	
	public void setEditEventShipper(EmergencyDiffDayDetailsBean editEventShipper) {
		this.editEventShipper = editEventShipper;
	}
	
	public EmergencyDiffDayDetailsBean getEditEventShipper() {
		return editEventShipper;
	}
	
	public FileBean getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(FileBean file) {
		this.uploadFile = file;
	}
	
	public FileBean getUploadFileCloseOperator() {
		return uploadFileCloseOperator;
	}

	public void setUploadFileCloseOperator(FileBean file) {
		this.uploadFileCloseOperator = file;
	}

	public FileBean getUploadFileOpenShipper() {
		return uploadFileOpenShipper;
	}

	public void setUploadFileOpenShipper(FileBean file) {
		this.uploadFileOpenShipper = file;
	}
	
	public FileBean getUploadFileCloseShipper() {
		return uploadFileCloseShipper;
	}

	public void setUploadFileCloseShipper(FileBean file) {
		this.uploadFileCloseShipper = file;
	}
	
	public Date getCurrentDate() {
		
		if(newEvent.getStart_date()!=null) {
			if(newEvent.getStart_date().before(currentDate)) {
				currentDate= newEvent.getStart_date();
			}
		}
		return currentDate;
	}
	
	@PostConstruct
    public void init() {
		
    	filters = new EmergencyDifficultDayFilter();	
       	newEvent = new EmergencyDiffDayBean();
       	editEventShipper = new EmergencyDiffDayDetailsBean();
       	selected = new EmergencyDiffDayBean();
	
		List<String> shippersSource = new ArrayList<String>();
	    List<String> shippersTarget = new ArrayList<String>();
	    shippersSource = service.selectShippers();
	    newEvent.setShippers(new DualListModel<String>(shippersSource, shippersTarget));
    }
	
	public void onSearch() 
	{
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		filters.setIsShipper(isShipper());
		filters.setIdn_user_group(userBean.getIdn_user_group());
		
		items = service.selectEmergencyDiffDay(filters);
	}
	
	public void onClear(){
		
		filters = new EmergencyDifficultDayFilter();
		
        if (items != null) {
            items.clear();
        }
    }

	public List<EmergencyDiffDayDetailsBean> getDetails(BigDecimal idn_tso_event) {
		
		EmergencyDiffDayIsShipperBean b = new EmergencyDiffDayIsShipperBean();
		boolean shipper = isShipper();
		b.setShipper(shipper);
		b.setIdn_tso_event(idn_tso_event);
		b.setIdn_user_group(super.getUser().getIdn_user_group());
		return service.selectEmergencyDiffDayDetails(b);
	}
	
	public List<EmergencyDiffDayDetailsBean> getAllDetails(BigDecimal idn_tso_event) {
		EmergencyDiffDayIsShipperBean b = new EmergencyDiffDayIsShipperBean();
		boolean shipper = isShipper();
		b.setShipper(shipper);
		b.setIdn_tso_event(idn_tso_event);
		b.setIdn_user_group(super.getUser().getIdn_user_group());
		return service.selectEmergencyDiffDayAllDetails(b);
	}
	
	public void cancel() {
		uploadFile = null;
		uploadFileCloseOperator = null;
		System.gc();
	}
	
	public void onRowCancel(RowEditEvent event) {
		cancel();
	}
	
	public void onRowEdit(RowEditEvent event) {
		EmergencyDiffDayBean bean = (EmergencyDiffDayBean) event.getObject();
		String error = "0";
		
		
    	Date eDate = null, sDate = null;
        if(bean.getEnd_date()!=null)
        	eDate = getZeroTimeDate(bean.getEnd_date());
        if(bean.getStart_date()!=null)
        	sDate = getZeroTimeDate(bean.getStart_date());

 		if(bean.getEnd_date()!=null && eDate.before(sDate)) {
				messages.addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, "Error end date",
						"The end date shold be equal or greater than the start date ", Calendar.getInstance().getTime()));
				onSearch();
				return;
		}
		
		if(uploadFileCloseOperator!=null && uploadFileCloseOperator.getFileName()!=null && uploadFileCloseOperator.getFileName()!="") {
			if(eDate==null) {
				messages.addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, "Error end date",
						"The end date is empty ", Calendar.getInstance().getTime()));
				return;
			}
			else {
				bean.setClosing_file_name(uploadFileCloseOperator.getFileName());
				bean.setClosing_binary_data(uploadFileCloseOperator.getContents());
				//bean.setStatus("CLOSED");
				bean.setStatus("PENDING SHIPPERS");
			}
		}
		else {
			if(selectCloseOperatorFile(bean)!=null) {
				InputStream is = selectCloseOperatorFile(bean).getStream();
				if(is!=null) {
					try {
						bean.setClosing_binary_data(getBytesFromInputStream(is));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			bean.setStatus("OPENED");
		}
		if(uploadFile!=null && uploadFile.getFileName()!=null && uploadFile.getFileName()!="") {
			bean.setOpening_file_name(uploadFile.getFileName());
			bean.setOpening_binary_data(uploadFile.getContents());
		}	
		else {
			if(selectOpenOperatorFile(bean)!=null) {
				InputStream is = selectOpenOperatorFile(bean).getStream();
				if(is!=null) {
					try {
						bean.setOpening_binary_data(getBytesFromInputStream(is));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		try {
			error = service.updateEvent(bean);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		uploadFile=null;
		uploadFileCloseOperator=null;
		onSearch();
	      messages.addMessage(Constants.head_menu[5], new MessageBean(Constants.INFO, "Changes saved",
					"Changes saved successfully", Calendar.getInstance().getTime()));
	}
	
	public static byte[] getBytesFromInputStream(InputStream is) throws IOException {
	    ByteArrayOutputStream os = new ByteArrayOutputStream(); 
	    byte[] buffer = new byte[0xFFFF];
	    for (int len = is.read(buffer); len != -1; len = is.read(buffer)) { 
	        os.write(buffer, 0, len);
	    }
	    return os.toByteArray();
	}
	
	public void cancelEditShipper() {
		uploadFileCloseShipper = null;
		uploadFileOpenShipper = null;
		System.gc();
	}
	
	public boolean disabledComment(BigDecimal idn_tso_event)
	{
		EmergencyDiffDayBean parent = new EmergencyDiffDayBean();
		parent = service.selectEmergencyDiffDayParent(editEventShipper.getIdn_tso_event());
		if(parent!=null) {
			if(parent.getStatus().equals("CLOSED") || parent.getStatus().equals("PENDING SHIPPERS"))
				return true;
			else
				return false;
		}
		return false;
	}
	
	public boolean disabledOpenFileShipper(BigDecimal idn_tso_event)
	{
		EmergencyDiffDayBean parent = new EmergencyDiffDayBean();
		parent = service.selectEmergencyDiffDayParent(editEventShipper.getIdn_tso_event());
		if(parent!=null) {
			//if(parent.getStatus().equals("OPENED") || (parent.getStatus().equals("CLOSED") && selectOpenShipperFile(editEventShipper)==null))
			if(parent.getStatus().equals("OPENED") || (parent.getStatus().equals("PENDING SHIPPERS") && selectOpenShipperFile(editEventShipper)==null))	
				return false;
			else
				return true;
		}
		return false;
	}
	
	public boolean disabledCloseFileShipper(BigDecimal idn_tso_event)
	{
		EmergencyDiffDayBean parent = new EmergencyDiffDayBean();
		parent = service.selectEmergencyDiffDayParent(editEventShipper.getIdn_tso_event());
		if(parent!=null) {
			//if(parent.getStatus().equals("OPENED") || (parent.getStatus().equals("CLOSED") && selectCloseShipperFile(editEventShipper)==null))
			if(selectCloseOperatorFile(parent)==null || selectOpenShipperFile(editEventShipper)==null)
				return true;
			if(parent.getStatus().equals("OPENED") || (parent.getStatus().equals("PENDING SHIPPERS") && selectCloseShipperFile(editEventShipper)==null))
				return false;
			else
				return true;
		}
		return false;
	}
	
	public boolean allShippersClosed(List<EmergencyDiffDayDetailsBean> list, BigDecimal idn) {
		for(int i=0; i<list.size(); i++)
			if(list.get(i).getClosing_file_name()==null && list.get(i).getIdn_tso_event_shipper().compareTo(idn)!=0)
				return false;
		return true;
	}
	
	public void saveEditShipper() {
		
		EmergencyDiffDayDetailsBean bean = new EmergencyDiffDayDetailsBean();
		
		String error = "0";
		
		bean = editEventShipper;
		String action = "";
		boolean opened=false;
		EmergencyDiffDayBean parent = new EmergencyDiffDayBean();
		parent = service.selectEmergencyDiffDayParent(editEventShipper.getIdn_tso_event());
		List<EmergencyDiffDayDetailsBean> list = getAllDetails(parent.getIdn_tso_event());
		if(parent!=null) 
			opened=parent.getStatus().equals("OPENED");
		
		if(opened || (!opened && selectCloseShipperFile(bean)==null)) {
			if(uploadFileCloseShipper!=null && uploadFileCloseShipper.getFileName()!=null && uploadFileCloseShipper.getFileName()!="") {
				bean.setClosing_file_name(uploadFileCloseShipper.getFileName());
				bean.setClosing_binary_data(uploadFileCloseShipper.getContents());
			}
			else {
				if(selectCloseShipperFile(bean)!=null) {
					InputStream is = selectCloseShipperFile(bean).getStream();
					action = "CLOSE";
					if(is!=null) {
						try {
							bean.setClosing_binary_data(getBytesFromInputStream(is));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		else {
			if(selectCloseShipperFile(bean)!=null) {
				InputStream is = selectCloseShipperFile(bean).getStream();
				action = "CLOSE";
				if(is!=null) {
					try {
						bean.setClosing_binary_data(getBytesFromInputStream(is));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		if(opened || (!opened && selectOpenShipperFile(bean)==null)) {
			if(uploadFileOpenShipper!=null && uploadFileOpenShipper.getFileName()!=null && uploadFileOpenShipper.getFileName()!="") {
				bean.setOpening_file_name(uploadFileOpenShipper.getFileName());
				bean.setOpening_binary_data(uploadFileOpenShipper.getContents());
			}	
			else {
				if(selectOpenShipperFile(bean)!=null) {
					InputStream is = selectOpenShipperFile(bean).getStream();
					action = "OPEN";
					if(is!=null) {
						try {
							bean.setOpening_binary_data(getBytesFromInputStream(is));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		else {
			if(selectOpenShipperFile(bean)!=null) {
				InputStream is = selectOpenShipperFile(bean).getStream();
				action = "OPEN";
				if(is!=null) {
					try {
						bean.setOpening_binary_data(getBytesFromInputStream(is));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		try {
			error = service.updateEventShipper(bean);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		if(uploadFileCloseShipper!=null && allShippersClosed(list, bean.getIdn_tso_event_shipper())) {
			parent.setStatus("CLOSED");
			try {
				error = service.updateStatusEvent(parent);
			} catch(Exception e) {
				log.catching(e);
				// we assign the return message 
				error = e.getMessage();
			}
			for(int i=0; i<list.size(); i++) {//for (String item : list.parent.getShippers().getTarget()){
				try {
					BigDecimal userGroupId = list.get(i).getIdn_user_group();//service.selectIdnUserGroup(item);
					String zona;
					if(parent.getZone_desc().equals("0")) {
			    		zona = "0";
			    	}
			    	else
			    		zona = parent.getZone_desc();
					sendMail(parent, zona, "End", userGroupId, "Shipper");
				}
				catch (Exception e) {
					log.catching(e);
					// we assign the return message 
					error = e.getMessage();
				}
			}
		}
		//NO se ha avisado aún a todos porque no se ha cerrado el evento
		if(!parent.getStatus().equals("CLOSED")) {
			BigDecimal tso = new BigDecimal(-1);
			try {
				tso = serviceAlloc.getDefaultOperatorId(getUser(), getLanguage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String zona;
			if(parent.getZone_desc().equals("0")) {
	    		zona = "0";
	    	}
	    	else
	    		zona = parent.getZone_desc();
			sendMail(parent, zona, action, tso, "Tso");
		}
			
		uploadFileOpenShipper=null;
		uploadFileCloseShipper=null;
		
		//selected = null;
		getDetails(bean.getIdn_tso_event());
		onSearch();
		messages.addMessage(Constants.head_menu[5], new MessageBean(Constants.INFO, "Changes saved",
				"Changes saved successfully", Calendar.getInstance().getTime()));
	}	
	
	private static Date getZeroTimeDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = calendar.getTime();
		return date;
	}
	
	
	public void save() {
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
        	
    	IdEventBean bean = new IdEventBean();
    	String zona = null;
    	bean = service.getId();
    	newEvent.setEvent_id(bean.getP_id());
    	if(newEvent.getZone_desc().equals("0")) {
    		newEvent.setZone_desc(null);
    		zona = "0";
    	}
    	else
    		zona = newEvent.getZone_desc();
    	
    	if(uploadFile==null){
    		messages.addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, "Error saving file",
					"The open file should be selected", Calendar.getInstance().getTime()));
			return;
		}
    	
    	if(newEvent.getShippers().getTarget().size()==0) {
    		messages.addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, "Error selecting shipper(s)",
					"Some shipper should be selected", Calendar.getInstance().getTime()));
			return;
    	}
    	
    	
    	Date today = new Date();
    	Date eDate = null;
        Date t = getZeroTimeDate(today);
        if(newEvent.getEnd_date()!=null)
        	eDate = getZeroTimeDate(newEvent.getEnd_date());
 		Date sDate = getZeroTimeDate(newEvent.getStart_date());

 		
    	/*if(sDate.before(t)) {
    		messages.addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, "Error start date",
					"The start date should be equal or greater than today", Calendar.getInstance().getTime()));
			return;
    	}*/
		if(newEvent.getEnd_date()!=null && eDate.before(sDate)) {
				messages.addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, "Error end date",
						"The end date shold be equal or greater than the start date ", Calendar.getInstance().getTime()));
				return;
		}
    	String error = "0";
    	String insert="-1";
    	String insertShipper="-1";
		try {
			insert = service.insertEvent(newEvent, uploadFile);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		String info = service.selectEventTypeFromId(newEvent.getType_desc()) + " ~ " + newEvent.getEvent_id();
		BigDecimal userGroupId;	
		
		if(insert.equals("0")) {
			for (String item : newEvent.getShippers().getTarget()){
				try {
					userGroupId = service.selectIdnUserGroup(item);
					service.sendNotification("TSO.EVENT.CREATED", "EVENT", info, userGroupId, getChangeSystemView().getIdn_active());
					sendMail(newEvent, zona, "Start", userGroupId, "Shipper");
					EmergencyDiffDayDetailsBean b = new EmergencyDiffDayDetailsBean();
					b.setIdn_tso_event(newEvent.getIdn_tso_event());
					b.setIdn_user_group(userGroupId);
					insertShipper = service.insertEventShipper(b);
					if(insertShipper.equals("-1"))
					{
						messages.addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, "Error inserting shipper event",
								"Error inserting data of shipper", Calendar.getInstance().getTime()));
						return;
					}
				} catch(Exception e) {
					log.catching(e);
					// we assign the return message 
					error = e.getMessage();
				}
			}
		}
		
		else {
			messages.addMessage(Constants.head_menu[5], new MessageBean(Constants.ERROR, "Error inserting",
					"Error inserting data ", Calendar.getInstance().getTime()));
			return;
		}

    	//We return to search
    	onSearch();
    	
    	//clean the form new after save
    	newEvent = new EmergencyDiffDayBean();
    	
    	List<String> shippersSource = new ArrayList<String>();
        List<String> shippersTarget = new ArrayList<String>();
        shippersSource = service.selectShippers();
        newEvent.setShippers(new DualListModel<String>(shippersSource, shippersTarget));
        
        uploadFile = null;
        uploadFileCloseOperator = null;
        uploadFileOpenShipper = null;
        uploadFileCloseShipper = null;
        
        messages.addMessage(Constants.head_menu[5], new MessageBean(Constants.INFO, "New Event",
				"New event created", Calendar.getInstance().getTime()));
	}
	
	public boolean isShipper() {
		if(userBean.isUser_type(Constants.SHIPPER))
			return true;
		return false;
	}
	
	public void handleFileCloseUploadOperator(FileUploadEvent event) {
    	
		file = event.getFile();
		if(file!=null){
			uploadFileCloseOperator = new FileBean(file.getFileName(), file.getContentType(), file.getContents());
		}
        
		if(file==null || uploadFileCloseOperator==null){
			messages.addMessage(Constants.head_menu[5],new MessageBean(Constants.ERROR,"Error saving file","The close file should be selected", Calendar.getInstance().getTime()));
			return;
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		    	
		file = event.getFile();
		if(file!=null){
			uploadFile = new FileBean(file.getFileName(), file.getContentType(), file.getContents());
		}
        
		if(file==null || uploadFile==null){
			messages.addMessage(Constants.head_menu[5],new MessageBean(Constants.ERROR,"Error saving file","The open file should be selected", Calendar.getInstance().getTime()));
			return;
		}
    }
	
	public void handleFileCloseUploadShipper(FileUploadEvent event) {
    	
		file = event.getFile();
		if(file!=null){
			uploadFileCloseShipper = new FileBean(file.getFileName(), file.getContentType(), file.getContents());
		}
        
		if(file==null || uploadFileCloseShipper==null){
			messages.addMessage(Constants.head_menu[5],new MessageBean(Constants.ERROR,"Error saving file","The close shipper file should be selected", Calendar.getInstance().getTime()));
			return;
		}
	}
	
	public void handleFileOpenUploadShipper(FileUploadEvent event) {
    	
		file = event.getFile();
		if(file!=null){
			uploadFileOpenShipper = new FileBean(file.getFileName(), file.getContentType(), file.getContents());
		}
        
		if(file==null || uploadFileOpenShipper==null){
			messages.addMessage(Constants.head_menu[5],new MessageBean(Constants.ERROR,"Error saving file","The open shipper file should be selected", Calendar.getInstance().getTime()));
			return;
		}
	}
	
	public StreamedContent selectOpenOperatorFile(EmergencyDiffDayBean bean) {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
		
		String summaryMsg = msgs.getString("userGuide_downloading_file");
		String errorMsg = null;
		try {
			return service.getOpeningOperatorFile(bean);
		} catch (ValidationException ve) {
			errorMsg = msgs.getString("download_error") + " " + msgs.getString("userGuide") + ve.getMessage();
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			return null; 
		} catch (Exception e) {
			errorMsg = msgs.getString("download_error") + " User Guide. ";
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));			
			return null; 
		}	
	}
	
	public StreamedContent selectCloseOperatorFile(EmergencyDiffDayBean bean) {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
		
		String summaryMsg = msgs.getString("userGuide_downloading_file");
		String errorMsg = null;
		try {
			return service.getClosingOperatorFile(bean);
		} catch (ValidationException ve) {
			errorMsg = msgs.getString("download_error") + " " + msgs.getString("userGuide") + ve.getMessage();
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			return null; 
		} catch (Exception e) {
			errorMsg = msgs.getString("download_error") + " User Guide. ";
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));			
			return null; 
		}	
	}
	
	public StreamedContent selectOpenShipperFile(EmergencyDiffDayDetailsBean bean) {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
		
		String summaryMsg = msgs.getString("userGuide_downloading_file");
		String errorMsg = null;
		try {
			return service.getOpeningShipperFile(bean);
		} catch (ValidationException ve) {
			errorMsg = msgs.getString("download_error") + " " + msgs.getString("userGuide") + ve.getMessage();
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			return null; 
		} catch (Exception e) {
			errorMsg = msgs.getString("download_error") + " User Guide. ";
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));			
			return null; 
		}	
	}
	
	public StreamedContent selectCloseShipperFile(EmergencyDiffDayDetailsBean bean) {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
		
		String summaryMsg = msgs.getString("userGuide_downloading_file");
		String errorMsg = null;
		try {
			return service.getClosingShipperFile(bean);
		} catch (ValidationException ve) {
			errorMsg = msgs.getString("download_error") + " " + msgs.getString("userGuide") + ve.getMessage();
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			return null; 
		} catch (Exception e) {
			errorMsg = msgs.getString("download_error") + " User Guide. ";
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));			
			return null; 
		}	
	}
	
	public void sendMail(EmergencyDiffDayBean bean, String zona, String startEnd, BigDecimal to, String typeTo) {
		HashMap<String,String> values = new HashMap<String,String>();
	
		values.put("1", startEnd);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		values.put("2", df.format(bean.getStart_date()));	
		if(bean.getEnd_date()!=null)
			values.put("3", df.format(bean.getEnd_date()));
		else
			values.put("3", " ");
		String zonaDesc;
		if(zona.equals("0"))
			zonaDesc = "Others";
		else
			zonaDesc = service.getZoneDesc(BigDecimal.valueOf(Double.valueOf(zona)));
		values.put("4", zonaDesc);
		
		String type="";
		if(bean.getType_desc().equals("Emergency") && typeTo.equals("Shipper"))
			type = "TSO.EMERGENCY.TSO.CHANGE";
		else if(bean.getType_desc().equals("Emergency") && typeTo.equals("Tso"))
			type = "TSO.EMERGENCY.SHIPPER.CHANGE";
		else if(bean.getType_desc().equals("Difficult day") && typeTo.equals("Shipper"))
			type = "TSO.DIFFICULT_DAY.TSO.CHANGE";
		else if(bean.getType_desc().equals("Difficult day") && typeTo.equals("Tso"))
			type = "TSO.DIFFICULT_DAY.SHIPPER.CHANGE";
					 
			type = "TSO.DIFFICULT_DAY.TSO.CHANGE";
		mailService.sendEmail(type, values, getChangeSystemView().getIdn_active(), to);
		//Para comprobar los valores que se pasan en el email
		messages.addMessage(Constants.head_menu[5],
				new MessageBean(Constants.INFO, "Mail values", "StartEnd"+values.get("1")+",StartDate:"+values.get("2")+",EndDate:"+values.get("3")+",Zone:"+values.get("4")+". Destinatario:"+to, Calendar.getInstance().getTime()));
	}
}
