package com.atos.views.maintenance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.StreamedContent;

import com.atos.beans.MessageBean;
import com.atos.beans.maintenance.WorkOperatorMaintenanceBean;
import com.atos.beans.maintenance.WorkSubmissionBean;
import com.atos.filters.maintenance.WorkMaintenanceFilter;
import com.atos.services.maintenance.WorkUpdateService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;

@ManagedBean(name="workUpdateView")
@ViewScoped
public class WorkUpdateView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8109788638819282920L;

	private static final Logger log = LogManager.getLogger(WorkUpdateView.class);

	// Para controlar si se habilitan campos en la pantalla.
	private static final String strModificationPermission = ".MAINTENANCE.MAINTENANCE_WORK_UPDATE.MOD";
	
	private WorkMaintenanceFilter filters;
	private List<WorkSubmissionBean> items;
	private WorkSubmissionBean selected;
	private List<WorkSubmissionBean> selectedItems;
	private WorkOperatorMaintenanceBean newOperatorMan;
	private Map<String, Object> areas;
	
	private String color = "pubbluecss";

	@ManagedProperty("#{workUpdateService}")
    transient private WorkUpdateService service;
    
    public void setService(WorkUpdateService service) {
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

    public WorkSubmissionBean getSelected() {
		return selected;
	}

	public void setSelected(WorkSubmissionBean selected) {
		this.selected = selected;
	}

	public List<WorkSubmissionBean> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(List<WorkSubmissionBean> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public WorkOperatorMaintenanceBean getNewOperatorMan() {
		return newOperatorMan;
	}

	public void setNewOperatorMan(WorkOperatorMaintenanceBean newOperatorMan) {
		this.newOperatorMan = newOperatorMan;
	}

	private Calendar sysdate;
    
    public Calendar getSysdate() {
		return sysdate;
	}


	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean testColor(String color){
		if(selected!=null){
			if(selected.getCss_color()!=null){
				if(selected.getCss_color().equals(color)){
					return true;
				} else {
					return false;
				}
			} else {
				if(this.color.equals(color)){
					return true;
				} else {
					return false;
				}
			}
		} else {
			if(this.color.equals(color)){
				return true;
			} else {
				return false;
			}
		}
	}
	
	@PostConstruct
    public void init() {
		initFilters();
		
		areas = service.getMaintenanceArea(getChangeSystemView().getIdn_active());
		
		initNewWorkOperatorMaintenance();
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

	public void onRowToggle(ToggleEvent event) {
    	selected = (WorkSubmissionBean) event.getData();
    }

	public void onRowEdit(RowEditEvent event) {
		WorkOperatorMaintenanceBean bean = (WorkOperatorMaintenanceBean)event.getObject();
		for(int i=0;i<selected.getOperator_data().size() ;i++){
			if(selected.getOperator_data().get(i).getIdn_maintenance_subarea().doubleValue()==bean.getIdn_maintenance_subarea().doubleValue()){
				bean.setIdn_maintenance(selected.getOperator_data().get(i).getIdn_maintenance());
			}
			if(selected.getOperator_data().get(i).getIdn_maintenance_subarea().doubleValue()!=bean.getIdn_maintenance_subarea().doubleValue() &&  selected.getOperator_data().get(i).getIdn_subarea().equals(bean.getIdn_subarea())){
				messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Updated Error", "The subarea "+ bean.getSubarea() +" should be informed just once.", Calendar.getInstance().getTime()));
				return;
			}
		}
		String error = "0";
    	try {
			error = service.updateOperationsMaintenance(bean);
    	} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		if(error!=null && error.equals("0")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.INFO,"Maintenance operator updated", "Maintenance operator updated", Calendar.getInstance().getTime()));	
		}else if(error!=null && error.equals("-1")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance operator updated error", "Error updating operator Maintenance.", Calendar.getInstance().getTime()));		
		}
		items = service.getEngineeringMaintenance(filters);
		
	}

	public void onRowCancel(RowEditEvent event) {
    }

	private boolean isModified(WorkSubmissionBean candidate) {
		WorkMaintenanceFilter s = new WorkMaintenanceFilter();
		s.setIdnActive(super.getChangeSystemView().getIdn_active());
		s.setIdn_maintenance(candidate.getMaintenance_code());
		List<WorkSubmissionBean> l = service.getEngineeringMaintenance(s);
		WorkSubmissionBean c = l.get(0);
		return c == null || !"SUBMITTED".equals(c.getStatus());

	}

	public void saveColor() {
		if (isModified(selected)) {
			String[] params = { selected.getMaintenance_code() };
			String msg = super.getMessageResourceString("maintenance_state_changed", params);
			String summary = super.getMessageResourceString("update_error", null);
			messages.addMessage(Constants.head_menu[3], new MessageBean(Constants.ERROR, summary, msg, new Date()));
			return;
		}
		selected.setCss_color(this.color);

    	String error = "0";
    	try {
    		error = service.updateCssColorMaintenance(selected);
	    } catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		if(error!=null && error.equals("0")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.INFO,"Maintenance Operator Updated", "Maintenance color updated", Calendar.getInstance().getTime()));	
		}else if(error!=null && error.equals("-1")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Operator Updated", "Error updating color maintenance.", Calendar.getInstance().getTime()));		
		}
		
	}
	
	public void save(){
		if(selected==null){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Inserting", "You must select a row.", Calendar.getInstance().getTime()));
			return;
		}
		for(int i=0;i<selected.getOperator_data().size() ;i++){
			if (isModified(selected)) {
				String[] params = { selected.getMaintenance_code() };
				String msg= super.getMessageResourceString("maintenance_state_changed", params);
				String summary = super.getMessageResourceString("update_error", null);
				messages.addMessage(Constants.head_menu[3], new MessageBean(Constants.ERROR, summary, msg, new Date()));
				return;
			}
			if(selected.getOperator_data().get(i).getIdn_subarea().equals(newOperatorMan.getIdn_subarea())){
				messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Inserting Error", "The subarea "+ selected.getOperator_data().get(i).getSubarea() +" should be informed just once.", Calendar.getInstance().getTime()));
				selected.setOperator_comments(selected.getOperator_comments_old());
				return;
			}
		}
		//ch240 ahora puede venir vacio
		if (newOperatorMan.getCapacity_affected()!= null){
			if(newOperatorMan.getCapacity_affected().doubleValue()<0){
				messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Inserting Error", "The Cap. value shoul be informed as positive value o iqual to 0", Calendar.getInstance().getTime()));
				return;
				
			}
		}
		String error = "0";
    	try {
    		newOperatorMan.setIdn_maintenance(selected.getIdn_maintenance());
			error = service.insertOperationsMaintenance(newOperatorMan, selected);
    	} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		if(error!=null && error.equals("0")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.INFO,"Maintenance Inserting", "Maintenance operator inserted correctly", Calendar.getInstance().getTime()));	
		}else if(error!=null && error.equals("-1")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Inserting", "Error inserting operator maintenance.", Calendar.getInstance().getTime()));		
		}else if(error!=null && error.equals("-2")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Inserting", "Error inserting operator comment.", Calendar.getInstance().getTime()));		
		} else {
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Inserting", "Error inserting operator maintenance: " + error, Calendar.getInstance().getTime()));
		}
		
		items = service.getEngineeringMaintenance(filters);
		
		initNewWorkOperatorMaintenance();

    	if(selected!=null){
    		selected.setOperator_comments("");
    	}
    }

	public void cancel() {
		initNewWorkOperatorMaintenance();
        RequestContext.getCurrentInstance().reset("formNewEntity");
    }

	private void initFilters(){
		filters = new WorkMaintenanceFilter();
		filters.setIdnActive(getChangeSystemView().getIdn_active());
		filters.setStart_date(Calendar.getInstance().getTime());
	}
	
    private void initNewWorkOperatorMaintenance(){
    	newOperatorMan = new WorkOperatorMaintenanceBean();
    	// Al cargar la pantalla se asigna el area A, como valor inicial del area, para que luego se carguen las subareas correspondientes.
		// newOperatorMan.setIdn_area( new BigDecimal((String)
		// getKeyFromValue(areas,"A")));
    }
    
    public void publish(){
    	for(int i=0;i<selectedItems.size();i++){
    		String error = "0";
        	try {
    			error = service.updateOperationsPublishMaintenance(selectedItems.get(i), getChangeSystemView().getIdn_active());
        	} catch(Exception e) {
    			log.catching(e);
    			// we assign the return message 
    			error = e.getMessage();
    		}
    		if(error!=null && error.equals("0")){
    			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.INFO,"Maintenance Published", "Maintenance " + selectedItems.get(i).getMaintenance_code() + " published", Calendar.getInstance().getTime()));	
    		}else if(error!=null && error.equals("-1")){
    			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Published", "Error publishing Maintenance.", Calendar.getInstance().getTime()));		
    		}
    		items = service.getEngineeringMaintenance(filters);
    	}
    }
    public void delete(){
    	for(int i=0;i<selectedItems.size();i++){
    		String error = "0";
        	try {
    			error = service.deleteEngineeringMaintenance(selectedItems.get(i));
        	} catch(Exception e) {
    			log.catching(e);
    			// we assign the return message 
    			error = e.getMessage();
    		}
    		if(error!=null && error.equals("0")){
    			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.INFO,"Maintenance Operator Deleted", "Maintenance " + selectedItems.get(i).getMaintenance_code() + " deleted", Calendar.getInstance().getTime()));	
    		}else if(error!=null && error.equals("-1")){
    			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Operator Deleted", "Error deleting Maintenance.", Calendar.getInstance().getTime()));		
    		}
    		items = service.getEngineeringMaintenance(filters);
    	}
    }
    
    public void saveComment(){
    	String error = "0";
		if (isModified(selected)) {
			String[] params = { selected.getMaintenance_code() };
			String msg = super.getMessageResourceString("maintenance_state_changed", params);
			String summary = super.getMessageResourceString("update_error", null);
			messages.addMessage(Constants.head_menu[3], new MessageBean(Constants.ERROR, summary, msg, new Date()));
			return;
		}
    	try {
    		error = service.updateOperationsCommentMaintenance(selected);
	    } catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		if(error!=null && error.equals("0")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.INFO,"Maintenance Operator Updated", "Maintenance comment updated", Calendar.getInstance().getTime()));	
		}else if(error!=null && error.equals("-1")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Operator Updated", "Error updating comment maintenance.", Calendar.getInstance().getTime()));		
		}
    }
    
    public boolean renderSubmitted(WorkSubmissionBean item){
    	
     	Subject subject = SecurityUtils.getSubject();
		if(!subject.isPermitted(getChangeSystemView().getSystem() + strModificationPermission))
     		return false;			
		
     	if(! item.getStatus().equals(Constants.SUBMITTED))
     		return false;
     	
     	Calendar cal = Calendar.getInstance();
 		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.SECOND, -1);
 		if(!item.getStart_date().after(cal.getTime()))
 			return false;
 			
     	return true;
    }
    
    public boolean enabledModifPermission(WorkSubmissionBean item){
    	
     	Subject subject = SecurityUtils.getSubject();
     	return subject.isPermitted(getChangeSystemView().getSystem() + strModificationPermission);
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

    public void saveActualEndDate(SelectEvent event){
    	Date date = (Date) event.getObject();
		if (date.before(selected.getStart_date())) {
			messages.addMessage(Constants.head_menu[8], new MessageBean(Constants.ERROR, "Maintenance not updated",
					"From date must be earlier or equal to To date", Calendar.getInstance().getTime()));
			return;
		}
    	selected.setActual_end_date(date);
    	String error = "0";
    	try {
    		error = service.updateActualEndDateMaintenance(selected);
	    } catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		if(error!=null && error.equals("0")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.INFO,"Maintenance Operator Updated", "Publication Actual End Date updated", Calendar.getInstance().getTime()));	
		}else if(error!=null && error.equals("-1")){
			messages.addMessage(Constants.head_menu[3],new MessageBean(Constants.ERROR,"Maintenance Operator Updated", "Error updating Actual End Date publication.", Calendar.getInstance().getTime()));		
		}
	
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
