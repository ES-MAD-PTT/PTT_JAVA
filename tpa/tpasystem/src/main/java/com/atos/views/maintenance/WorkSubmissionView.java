package com.atos.views.maintenance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.FileBean;
import com.atos.beans.MessageBean;
import com.atos.beans.maintenance.WorkSubmissionBean;
import com.atos.filters.maintenance.WorkMaintenanceFilter;
import com.atos.services.maintenance.WorkSubmissionService;
import com.atos.utils.Constants;
import com.atos.views.MessagesView;
import com.atos.views.CommonView;

@ManagedBean(name="workSubmissionView")
@ViewScoped
public class WorkSubmissionView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8109788638819282920L;

	private static final Logger log = LogManager.getLogger(WorkSubmissionView.class);

	private WorkMaintenanceFilter filters;
	private List<WorkSubmissionBean> items;
	private List<WorkSubmissionBean> selecteds;
	private WorkSubmissionBean selected;
	private WorkSubmissionBean newMaintenance;
	private Map<String, Object> areas;
	
	private UploadedFile file;
	private FileBean uploadFile = null;

	@ManagedProperty("#{workSubmissionService}")
    transient private WorkSubmissionService service;
    
    public void setService(WorkSubmissionService service) {
        this.service = service;
    }

	@ManagedProperty("#{messagesView}")
    private MessagesView messages;

    public void setMessages(MessagesView messages) {
        this.messages = messages;
    }

	public WorkMaintenanceFilter getFilters() {
		return filters;
	}

	public void setFilters(WorkMaintenanceFilter filters) {
		this.filters = filters;
	}

	public List<WorkSubmissionBean> getItems() {
		return items;
	}

	public void setItems(List<WorkSubmissionBean> items) {
		this.items = items;
	}

    public List<WorkSubmissionBean> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<WorkSubmissionBean> selecteds) {
		this.selecteds = selecteds;
	}

	public WorkSubmissionBean getNewMaintenance() {
		return newMaintenance;
	}

	public void setNewMaintenance(WorkSubmissionBean newMaintenance) {
		this.newMaintenance = newMaintenance;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public WorkSubmissionBean getSelected() {
		return selected;
	}

	public void setSelected(WorkSubmissionBean selected) {
		this.selected = selected;
	}

	private Calendar tomorrow;
    
	public Calendar getTomorrow() {
		tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		tomorrow.set(Calendar.HOUR_OF_DAY, 0);
		tomorrow.set(Calendar.MINUTE, 0);
		tomorrow.set(Calendar.SECOND, 0);
		tomorrow.set(Calendar.MILLISECOND, 0);
		return tomorrow;
	}

	public void setTomorrow(Calendar tomorrow) {
		this.tomorrow = tomorrow;
	}

	@PostConstruct
    public void init() {
		initFilters();

		areas = service.getMaintenanceArea(getChangeSystemView().getIdn_active());
		
		initNewMaintenance();
    }

	public Map<String, Object> getMaintenanceCode() {
		return service.getMaintenanceCode();
    }
	
	public Map<String, Object> getSubAreas() {
		return service.getMaintenanceSubarea(filters);
	}

	public Map<String, Object> getSubAreas(BigDecimal idn_area) {
		WorkMaintenanceFilter f = new WorkMaintenanceFilter();
		f.setIdn_area(idn_area);
		return service.getMaintenanceSubarea(f);
	}

	public Map<String, Object> getArea() {
		return areas;
	}


	// Methods
	public void onSearch(){
		filters.setIdnActive(getChangeSystemView().getIdn_active());
		items = service.getEngineeringMaintenance(filters);
	}
	
	public void onClear(){
		initFilters();
    	
        if (items != null) {
            items.clear();
        }
    }
	public void onRowEdit(RowEditEvent event) {
		WorkSubmissionBean bean = (WorkSubmissionBean)event.getObject();

		//ch242 Start Date y End Date con cualquier fecha
    	//if(bean.getStart_date()==null || bean.getStart_date().before(Calendar.getInstance().getTime())){
	    //	messages.addMessage(Constants.head_menu[8],new MessageBean(Constants.ERROR,"Maintenance not updated", "From Date must be later than today", Calendar.getInstance().getTime()));
	    // 	return;
		//}
		
    	//if(bean.getEnd_date()!=null && bean.getEnd_date().before(Calendar.getInstance().getTime())){
	    //	messages.addMessage(Constants.head_menu[8],new MessageBean(Constants.ERROR,"Maintenance not updated", "To Date must be later than today or empty", Calendar.getInstance().getTime()));
	    //	return;
		//} else {
			if(bean.getEnd_date()!=null && bean.getEnd_date().before(bean.getStart_date())){
				messages.addMessage(Constants.head_menu[8],new MessageBean(Constants.ERROR,"Maintenance not updated", "From date must be earlier or equal to To date", Calendar.getInstance().getTime()));
		    	return;
			}
		//}
		
		String error = "0";
    	try {
			error = service.updateEngineeringMaintenance(bean, uploadFile);
    	} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		if(error!=null && error.equals("0")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.INFO,"Maintenance Inserted", "Data Stored: Maintanance " + bean.getMaintenance_code() + " updated", Calendar.getInstance().getTime()));	
		}else if(error!=null && error.equals("-1")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Inserted", "Error inserting Maintenance.", Calendar.getInstance().getTime()));		
		}else if(error!=null && error.equals("-2")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Inserted", "Error inserting Maintenance.", Calendar.getInstance().getTime()));		
		}else if(error!=null && error.equals("-3")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Inserted", "Error inserting Maintenance.", Calendar.getInstance().getTime()));		
		}
    	
    	items = service.getEngineeringMaintenance(filters);
		
	}
    public void onRowCancel(RowEditEvent event) {
    }
    public void delete(){
    	for(int i=0;i<selecteds.size();i++){
    		if(selecteds.get(i).getStatus().equals(Constants.PUBLISHED)){
    			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.WARNING,"Maintenance Deleted", "Maintenance " + selecteds.get(i).getMaintenance_code() + " is published and can not be deleted", Calendar.getInstance().getTime()));
    			continue;
    		}
    		String error = "0";
        	try {
    			error = service.deleteEngineeringMaintenance(selecteds.get(i));
        	} catch(Exception e) {
    			log.catching(e);
    			// we assign the return message 
    			error = e.getMessage();
    		}
    		if(error!=null && error.equals("0")){
    			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.INFO,"Maintenance Deleted", "Maintenance " + selecteds.get(i).getMaintenance_code() + " deleted", Calendar.getInstance().getTime()));	
    		}else if(error!=null && error.equals("-1")){
    			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Deleted", "Error deleting Maintenance.", Calendar.getInstance().getTime()));		
    		}
    		items = service.getEngineeringMaintenance(filters);
    	}
    }
    public void save(){
    	
    	/*	ch242
     	if(newMaintenance.getStart_date()==null || newMaintenance.getStart_date().before(Calendar.getInstance().getTime())){
	    	messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance not inserted", "From Date must be later than today", Calendar.getInstance().getTime()));
	    	return;
		}*/
		if(newMaintenance.getEnd_date()!=null && newMaintenance.getEnd_date().before(newMaintenance.getStart_date())){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance not inserted", "From date must be earlier or equal to To date", Calendar.getInstance().getTime()));
	    	return;
		}
    	
    	String error = "0";
    	try {
			error = service.insertEngineeringMaintenance(newMaintenance, uploadFile, getChangeSystemView().getIdn_active());
    	} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		if(error!=null && error.equals("0")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.INFO,"Maintenance Inserted", "Data Stored: New maintenance " + newMaintenance.getMaintenance_code() + " created", Calendar.getInstance().getTime()));	
		}else if(error!=null && error.equals("-1")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Inserted", "Error inserting Maintenance.", Calendar.getInstance().getTime()));		
		}else if(error!=null && error.equals("-2")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Inserted", "Error inserting Maintenance.", Calendar.getInstance().getTime()));		
		}else if(error!=null && error.equals("-3")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Inserted", "Error inserting Maintenance.", Calendar.getInstance().getTime()));		
		} else {
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Inserted", "Error inserting Maintenance: " + error, Calendar.getInstance().getTime()));
		}
    	
    	items = service.getEngineeringMaintenance(filters);
    	
    	initNewMaintenance();
    }
    public void cancel() {
    	initNewMaintenance();
        RequestContext.getCurrentInstance().reset("formNewEntity");
    }
    
    private void initFilters(){
		filters = new WorkMaintenanceFilter();
		filters.setIdnActive(getChangeSystemView().getIdn_active());
		filters.setStart_date(Calendar.getInstance().getTime());
    }
    
    private void initNewMaintenance(){
    	newMaintenance = new WorkSubmissionBean();
    	// Al cargar la pantalla se asigna el area A, como valor inicial del area, para que luego se carguen las subareas correspondientes.
		// newMaintenance.setIdn_area( new BigDecimal((String)
		// getKeyFromValue(areas,"A")));
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		newMaintenance.setStart_date(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.SECOND, -1);
		newMaintenance.setEnd_date(cal.getTime());
		
		file = null;
		uploadFile = null;
    }  
    
    public boolean renderSubmitted(WorkSubmissionBean item){
     	if(item.getStatus().equals(Constants.SUBMITTED)){
     		Calendar cal = Calendar.getInstance();
     		cal.add(Calendar.DAY_OF_MONTH, 1);
    		cal.set(Calendar.HOUR_OF_DAY, 0);
    		cal.set(Calendar.MINUTE, 0);
    		cal.set(Calendar.SECOND, 0);
    		cal.set(Calendar.MILLISECOND, 0);
    		cal.add(Calendar.SECOND, -1);
     		if(item.getStart_date().after(cal.getTime())){
     			return true;
     		} else {
     		    return false;
     		}
     	} else {
     		return false;
     	}
    }
    
    public void handleFileUpload(FileUploadEvent event) {
    	
		file = event.getFile();
		if(file!=null){
			uploadFile = new FileBean(file.getFileName(), file.getContentType(), file.getContents());
		}
        
		if(file==null || uploadFile==null){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Error saving file","The maintenance file should be selected", Calendar.getInstance().getTime()));
			return;
		}

    }

	public StreamedContent getMaintenanceFile() {
		if(selected==null){
	    	messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Downloading file", "There is no a register selected", Calendar.getInstance().getTime()));
	    	return null;
		} else {
			if(selected.getIdn_maintenance_file()==null){
		    	messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Downloading file", "There is no a file to download", Calendar.getInstance().getTime()));
		    	return null;
			}
			
		}
		TreeMap<String,BigDecimal> map = new TreeMap<String,BigDecimal>();
		map.put("idn_maintenance_file", selected.getIdn_maintenance_file());
		
		return service.getFile(map);
	}
    
	// Para obtener identificadores de area a partir de su codigo.
	// Hay una relación 1 a 1 entre codigos e identificadores.
	private static Object getKeyFromValue(Map hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}

	 public int getItemsSize() { 
			if(this.items!=null && !this.items.isEmpty()){
				return this.items.size();
			}else{
				return 0;
			}
		}
}
