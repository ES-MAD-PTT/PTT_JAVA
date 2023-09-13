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
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ContractDeadlineBean;
import com.atos.filters.dam.ContractDeadlineFilter;
import com.atos.services.dam.ContractDeadlineService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name = "contractDeadlineView")
@ViewScoped
public class ContractDeadlineView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(ContractDeadlineView.class);

	private ContractDeadlineFilter filters;
	private ContractDeadlineBean newContractDeadline;
	private List<ContractDeadlineBean> items;

	@ManagedProperty("#{contractDeadlineService}")
	transient private ContractDeadlineService service;

	private Calendar sysdate;

	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	public ContractDeadlineFilter getFilters() {
		return filters;
	}

	public void setFilters(ContractDeadlineFilter filters) {
		this.filters = filters;
	}

	public ContractDeadlineBean getNewContractDeadline() {
		return newContractDeadline;
	}

	public void setNewContractDeadline(ContractDeadlineBean newContractDeadline) {
		this.newContractDeadline = newContractDeadline;
	}

	public List<ContractDeadlineBean> getItems() {
		return items;
	}

	public void setItems(List<ContractDeadlineBean> items) {
		this.items = items;
	}

	public void setService(ContractDeadlineService service) {
		this.service = service;
	}

	@PostConstruct
	public void init() {
		filters = new ContractDeadlineFilter();
		newContractDeadline = new ContractDeadlineBean();

		filters.setStartDate(Calendar.getInstance().getTime());

		// LOOKING TO THE SYSDATE parameter BD
		sysdate = gettingValidDateStart();
		newContractDeadline.setStartDate(sysdate.getTime());
		BigDecimal bd1 = new BigDecimal(1);
		//newContractDeadline.setGasDay(bd1);

	}

	// sysdte +1
	public Calendar gettingValidDateStart() {

		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
		Integer valNumDate = valDate.getInteger_exit();
		Calendar sysdate = Calendar.getInstance();
		// StartDate filter => sysdate
		filters.setStartDate(sysdate.getTime());

		sysdate.setTime(valDate.getDate());
		sysdate.set(Calendar.HOUR_OF_DAY, 0);
		sysdate.set(Calendar.MINUTE, 0);
		sysdate.set(Calendar.SECOND, 0);
		sysdate.set(Calendar.MILLISECOND, 0);
		sysdate.add(Calendar.DAY_OF_MONTH, valNumDate.intValue());

		return sysdate;
	}

	//combo Process Type
	public Map<BigDecimal, Object> getProcessType() {
		return service.selectDeadlineType();
	}

	//combo tipo
	public Map<BigDecimal, Object> getTypes() {
		return service.selectTypes();
	}

	public void onSearch() {
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_contractDeadline1').clearFilters()");
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		if (filters.getEndDate() != null) {
			if (filters.getStartDate().after(filters.getEndDate())) {
				String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
		    	return;
				
			}
		}
		items = service.selectContractDeadlines(filters);
	}

	public void onClear() {
		// RequestContext.getCurrentInstance().reset("form");
		filters = new ContractDeadlineFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
		if (items != null) {
			items.clear();
		}
	}

	public void onRowEdit(RowEditEvent event) {
		ContractDeadlineBean contractDeadline = (ContractDeadlineBean) event.getObject();
		
		
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("contractDeadline") };
    	String summaryMsgOk =  getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
    	
    	
    	if(contractDeadline.getMonthLimit().equals(BigDecimal.ZERO) && (contractDeadline.getMonthsBefore().equals(BigDecimal.ZERO))){
    		String errorMsg = msgs.getString("error_monthLimitBefore"); //error_startDate_sysdate= Start date must be later to sysdate
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
			log.error(errorMsg);
			return;
			
		}
    	
    	
		String error = "0";
		try {

			// function that checks whether to allow sending / RV calendar
			// re-contracts . adm is allowed. renom lime
			error = service.validateContractOrders(contractDeadline);

			// delete the record setting end date
			error = service.deleteContractDeadline(contractDeadline);

			// Here NO Updates, all changes must be done are historical Insert
			// error = service.updateContractDeadline(contractDeadline);
			error = service.insertContractDeadline(contractDeadline);

		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}
		
		String[] par2 = { contractDeadline.getDeadline_desc()+"-" +contractDeadline.getType(),msgs.getString("contractDeadline")};
		
		if (error != null && error.equals("0")) {
			String msg =  getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("ContractDeadline Updated: " + contractDeadline.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  ContractDeadline. ContractDeadline Id Operation not found, Error: " + error + ". "+ contractDeadline.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  ContractDeadline. InsertContractDeadline, Error: " + error + ". "+ contractDeadline.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-10")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error Updating ContractDeadline. DeleteContractDeadline, Error: " + error + ". "+ contractDeadline.toString(), Calendar.getInstance().getTime());
		} else {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  ContractDeadline. ValidateContractOrders, Error: " + error + ". "+ contractDeadline.toString(), Calendar.getInstance().getTime());
		}
		
		items = service.selectContractDeadlines(filters);
	}

	public void onRowCancel(RowEditEvent event) {
	

	}

	public void cancel() {
		// RequestContext.getCurrentInstance().reset("formNewEntity");

		// clean the formu new after save
		newContractDeadline = new ContractDeadlineBean();
		newContractDeadline.setStartDate(sysdate.getTime());
		BigDecimal bd1 = new BigDecimal(1);
		//newContractDeadline.setGasDay(bd1);
	}

	public void save() {
		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
		Integer valNumDate = valDate.getInteger_exit();

		String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("contractDeadline") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    	
    	
		if (newContractDeadline.getStartDate() != null) {
			if (newContractDeadline.getStartDate().before(sysdate.getTime())) {
				errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
				return;
			}
		}
		
		if(newContractDeadline.getMonthLimit().equals(BigDecimal.ZERO) && (newContractDeadline.getMonthsBefore().equals(BigDecimal.ZERO))){
			errorMsg = msgs.getString("error_monthLimitBefore"); //error_startDate_sysdate= Start date must be later to sysdate
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
			log.error(errorMsg);
			return;
			
		}

		String error = "0";
		try {
			error = service.validateContractOrders(newContractDeadline);
			error = service.insertContractDeadline(newContractDeadline);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}

		
		if (error != null && error.equals("0")) {
			String msg = msgs.getString("contractDeadline");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("ContractDeadline Inserted ok" + newContractDeadline.toString(),	Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg = msgs.getString("contractDeadline");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting contractDeadline. ContractDeadline Id Operation not found"	+ newContractDeadline.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg = msgs.getString("contractDeadline");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting ContractDeadline. ContractDeadline, Error: " + error + ". "+ newContractDeadline.toString(), Calendar.getInstance().getTime());
		} else {
			String msg = msgs.getString("contractDeadline");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting ContractDeadline. ContractDeadline, Error: " + error + ". " + newContractDeadline.toString(), Calendar.getInstance().getTime());
		}

		//items = service.selectContractDeadlines(filters);
		onSearch();

		// clean the formu new after save
		newContractDeadline = new ContractDeadlineBean();

		// StartDate New => sysdate +1
		newContractDeadline.setStartDate(sysdate.getTime());
		BigDecimal bd1 = new BigDecimal(1);
		//newContractDeadline.setGasDay(bd1);
	}

	public void valorDefectoGasDay() {

		BigDecimal bd1 = new BigDecimal(1);
		BigDecimal bd2 = new BigDecimal(2);

		if (newContractDeadline.getIdn_operation_term().equals(bd1)) {
			//newContractDeadline.setGasDay(bd1);
		} else if (newContractDeadline.getIdn_operation_term().equals(bd2)) {
			//newContractDeadline.setGasDay(bd2);
		}

	}

	public String disabledField(ContractDeadlineBean item) {
		// 1. En caso de un registro con la fecha Start Date posterior al día
		// actual, el sistema habilitará Todos

		if (item.getStartDate().after(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime())) {
			return "false";
		}

		/*
		 * if(item.getStartDate().after(sysdate.getTime())){
		 *  return "false"; 
		 *  }
		 * 
		 * 
		 * if(item.getStartDate().before(sysdate.getTime()) || item.getStartDate().equals(sysdate.getTime()) ){
		 *  return "true"; 
		 *  }
		 */
		return "true";

	}

	public boolean renderItemEditor(ContractDeadlineBean item) {

		if (item.getStartDate().before(sysdate.getTime())) {
			return false;

		} else {
			return true;
		}

	}
	
	public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}

}
