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
import org.primefaces.event.ToggleEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.RoleBean;
import com.atos.filters.dam.RoleFilter;
import com.atos.services.dam.RoleService;
import com.atos.utils.Constants;
import com.atos.views.ChangeSystemView;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;

@ManagedBean(name = "roleView")
@ViewScoped
public class RoleView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(RoleView.class);

	private RoleFilter filters;
	private RoleBean newRole;
	private List<RoleBean> items;

	@ManagedProperty("#{roleService}")
	transient private RoleService service;

	private Map<BigDecimal, Object> comboSystems;
	private Map<BigDecimal, Object> availableProcess;

	private Calendar sysdate;
	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}


	public RoleFilter getFilters() {
		return filters;
	}

	public void setFilters(RoleFilter filters) {
		this.filters = filters;
	}

	public RoleBean getNewRole() {
		return newRole;
	}

	public void setNewRole(RoleBean newRole) {
		this.newRole = newRole;
	}

	public List<RoleBean> getItems() {
		return items;
	}

	public void setItems(List<RoleBean> items) {
		this.items = items;
	}

	public void setService(RoleService service) {
		this.service = service;
	}


	@PostConstruct
	public void init() {
		filters = new RoleFilter();
		newRole = new RoleBean();

		// LOOKING TO THE SYSDATE parameter BD
		sysdate = gettingValidDateStart();

		// StartDate New => sysdate +n
		newRole.setStartDate(sysdate.getTime());

		// cargamos los combos inicialment
		comboSystems = service.selectSystems(getChangeSystemView().getIdn_active());
		availableProcess = service.selectProcess();
	}

	// sysdte +valNumDate
	public Calendar gettingValidDateStart() {

		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD.USER");
		Integer valNumDate = valDate.getInteger_exit();
		Calendar sysdate = Calendar.getInstance();

		sysdate.setTime(valDate.getDate());
		sysdate.set(Calendar.HOUR_OF_DAY, 0);
		sysdate.set(Calendar.MINUTE, 0);
		sysdate.set(Calendar.SECOND, 0);
		sysdate.set(Calendar.MILLISECOND, 0);
		sysdate.add(Calendar.DAY_OF_MONTH, valNumDate.intValue());

		return sysdate;
	}

	public List<String> roleList(String query) {
		query = "%" + query + "%";
		return service.selectRole(query);
	}

	public Map<BigDecimal, Object> getSystems() {
		return comboSystems;
	}

	public Map<BigDecimal, Object> getRoles() {

		return service.selectComboRolesSystem(getChangeSystemView().getIdn_active());

	}

	public Map<BigDecimal, Object> getRolesSystem() {
		// return comboRoles;
		// hay que recargarlo de nuevo aki porque puede estar modificado en el update del grid
		return service.selectComboRolesSystem(getChangeSystemView().getIdn_active());
	}

	public Map<BigDecimal, Object> getAvailableProcess() {
		return availableProcess;
	}

	public void onSearch() {
		filters.setIdn_system(getChangeSystemView().getIdn_active()); // offShore
		items = service.selectRoles(filters);
	}

	public void onClear() {
		// RequestContext.getCurrentInstance().reset("form");
		filters = new RoleFilter();

		if (items != null) {
			items.clear();
		}
	}

	public void onRowEdit(RowEditEvent event) {
		RoleBean role = (RoleBean) event.getObject();
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("role") };
    	String summaryMsgOk =  getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
		
		// hay un caso en el update que si no existe el resitro en profilePermision hay que insertar
		// y en este caso la fecha Start es obligatoria. En Update no se actualiza.
		role.setStartDate(sysdate.getTime());

		String error = "0";
		try {
			// cargamos los permisos del rol
			if (role.getSelectedProcess().length == 0) {
				BigDecimal[] selectedProcess;
				selectedProcess = service.selectSelectedProcess(role.getIdn_profile());
				role.setSelectedProcess(selectedProcess);
			}

			error = service.updateRole(role, availableProcess, role.getSelectedProcess());

		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}
		
		String[] par2 = { role.getRole(),msgs.getString("role") };

		if (error != null && error.equals("0")) {
			String msg =  getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("role Updated: " + role.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  updateProfile " + role.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  updateProfilePipeline " + role.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-3")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  updateProfilePermission " + role.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-4")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  updateProfilePermission " + role.toString(), Calendar.getInstance().getTime());
		}

		items = service.selectRoles(filters);

	}

	public void onRowCancel(RowEditEvent event) {

	}

	public void cancel() {
		// RequestContext.getCurrentInstance().reset("formNewEntity");
		newRole = new RoleBean();
	}

	public void save() {

		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("role") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    	
		String error = "0";
		try {
			error = service.insertRole(newRole, availableProcess, newRole.getSelectedProcess());
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}
		
		String[] par2 = { newRole.getRole(),msgs.getString("role") };
		if (error != null && error.equals("0")) {
			String msg =  getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("Role Inserted ok" + newRole.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting Role. The " + newRole.toString() +" already exists in the System ", Calendar.getInstance().getTime());		
		} else if (error != null && error.equals("-2")) {
			String msg =  getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));   
			log.info("Error inserting insertProfile. The " + newRole.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-3")) {
			String msg =  getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));   
			log.info("Error inserting insertProfilePipeline. The " + newRole.toString(),Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-4")) {
			String msg =  getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));   
			log.info("Error inserting insertProfilePermission. The " + newRole.toString(),	Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-5")) {
			String msg =  getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));   
			log.info("Error inserting updateProfilePermission. The " + newRole.toString(),	Calendar.getInstance().getTime());
		}
		//offshore
		filters.setIdn_system(getChangeSystemView().getIdn_active());

		//items = service.selectRoles(filters);
		onSearch();

		// cargamos de nuevo el combo roles para que se refleje el alta
		// comboRoles=service.selectComboRoles();

		// clean the formu new after save
		newRole = new RoleBean();
	}

	public void onRowToggle(ToggleEvent event) {

		RoleBean item = ((RoleBean) event.getData());
		BigDecimal[] selectedProcess;
		selectedProcess = service.selectSelectedProcess(item.getIdn_profile());

		item.setSelectedProcess(selectedProcess);

	}
	public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}

}
