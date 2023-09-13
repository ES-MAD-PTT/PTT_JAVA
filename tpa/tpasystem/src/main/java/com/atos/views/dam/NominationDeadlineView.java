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
import com.atos.beans.dam.NominationDeadlineBean;
import com.atos.filters.dam.NominationDeadlineFilter;
import com.atos.services.dam.NominationDeadlineService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name = "nominationDeadlineView")
@ViewScoped
public class NominationDeadlineView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(NominationDeadlineView.class);

	private NominationDeadlineFilter filters;
	private NominationDeadlineBean newNominationDeadline;
	private List<NominationDeadlineBean> items;

	@ManagedProperty("#{nominationDeadlineService}")
	transient private NominationDeadlineService service;

	private Calendar sysdate;

	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	public NominationDeadlineFilter getFilters() {
		return filters;
	}

	public void setFilters(NominationDeadlineFilter filters) {
		this.filters = filters;
	}

	public NominationDeadlineBean getNewNominationDeadline() {
		return newNominationDeadline;
	}

	public void setNewNominationDeadline(NominationDeadlineBean newNominationDeadline) {
		this.newNominationDeadline = newNominationDeadline;
	}

	public List<NominationDeadlineBean> getItems() {
		return items;
	}

	public void setItems(List<NominationDeadlineBean> items) {
		this.items = items;
	}

	public void setService(NominationDeadlineService service) {
		this.service = service;
	}

	@PostConstruct
	public void init() {
		filters = new NominationDeadlineFilter();
		newNominationDeadline = new NominationDeadlineBean();

		filters.setStartDate(Calendar.getInstance().getTime());

		// LOOKING TO THE SYSDATE parameter BD
		sysdate = gettingValidDateStart();
		newNominationDeadline.setStartDate(sysdate.getTime());
		BigDecimal bd1 = new BigDecimal(1);
		newNominationDeadline.setGasDay(bd1);

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

	public Map<BigDecimal, Object> getDeadlineType() {
		return service.selectDeadlineType();
	}

	public Map<BigDecimal, Object> getTypes() {
		return service.selectTypes();
	}

	public void onSearch() {
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_nominationDeadline1').clearFilters()");
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		if (filters.getEndDate() != null) {
			if (filters.getStartDate().after(filters.getEndDate())) {
				String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],
						new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
		    	return;
				
			}
		}
		items = service.selectNominationDeadlines(filters);
	}

	public void onClear() {
		// RequestContext.getCurrentInstance().reset("form");
		filters = new NominationDeadlineFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
		if (items != null) {
			items.clear();
		}
	}

	public void onRowEdit(RowEditEvent event) {
		NominationDeadlineBean nominationDeadline = (NominationDeadlineBean) event.getObject();
		
		
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("nominationDeadline") };
    	String summaryMsgOk =  getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
    	
		String error = "0";
		try {

			// function that checks whether to allow sending / RV calendar
			// re-nominations . adm is allowed. renom lime
			error = service.validateNominationOrders(nominationDeadline);

			// delete the record setting end date
			error = service.deleteNominationDeadline(nominationDeadline);

			// Here NO Updates, all changes must be done are historical Insert
			// error = service.updateNominationDeadline(nominationDeadline);
			error = service.insertNominationDeadline(nominationDeadline);

		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}
		
		String[] par2 = { nominationDeadline.getDeadline_desc()+"-" +nominationDeadline.getType(),msgs.getString("nominationDeadline")};
		
		if (error != null && error.equals("0")) {
			String msg =  getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("NominationDeadline Updated: " + nominationDeadline.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  NominationDeadline. NominationDeadline Id Operation not found, Error: " + error + ". "+ nominationDeadline.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  NominationDeadline. InsertNominationDeadline, Error: " + error + ". "+ nominationDeadline.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-10")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error Updating NominationDeadline. DeleteNominationDeadline, Error: " + error + ". "+ nominationDeadline.toString(), Calendar.getInstance().getTime());
		} else {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  NominationDeadline. ValidateNominationOrders, Error: " + error + ". "+ nominationDeadline.toString(), Calendar.getInstance().getTime());
		}
		
		items = service.selectNominationDeadlines(filters);
	}

	public void onRowCancel(RowEditEvent event) {
	

	}

	public void cancel() {
		// RequestContext.getCurrentInstance().reset("formNewEntity");

		// clean the formu new after save
		newNominationDeadline = new NominationDeadlineBean();
		newNominationDeadline.setStartDate(sysdate.getTime());
		BigDecimal bd1 = new BigDecimal(1);
		newNominationDeadline.setGasDay(bd1);
	}

	public void save() {
		SystemParameterBean valDate = service.getSystemParameter("VALIDITY.START.DAYS.AHEAD");
		Integer valNumDate = valDate.getInteger_exit();

		String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("nominationDeadline") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params);
    	
    	
		if (newNominationDeadline.getStartDate() != null) {
			if (newNominationDeadline.getStartDate().before(sysdate.getTime())) {
				errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg + valNumDate.intValue() + " days", Calendar.getInstance().getTime()));
				log.error(errorMsg + valNumDate.intValue() + " days");
				return;
			}
		}

		String error = "0";
		try {
			error = service.validateNominationOrders(newNominationDeadline);
			error = service.insertNominationDeadline(newNominationDeadline);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}

		
		if (error != null && error.equals("0")) {
			String msg = msgs.getString("nominationDeadline");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("NominationDeadline Inserted ok" + newNominationDeadline.toString(),	Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg = msgs.getString("nominationDeadline");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting nominationDeadline. NominationDeadline Id Operation not found"	+ newNominationDeadline.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg = msgs.getString("nominationDeadline");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting NominationDeadline. NominationDeadline, Error: " + error + ". "+ newNominationDeadline.toString(), Calendar.getInstance().getTime());
		} else {
			String msg = msgs.getString("nominationDeadline");
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting NominationDeadline. NominationDeadline, Error: " + error + ". " + newNominationDeadline.toString(), Calendar.getInstance().getTime());
		}

		//items = service.selectNominationDeadlines(filters);
		onSearch();

		// clean the formu new after save
		newNominationDeadline = new NominationDeadlineBean();

		// StartDate New => sysdate +1
		newNominationDeadline.setStartDate(sysdate.getTime());
		BigDecimal bd1 = new BigDecimal(1);
		newNominationDeadline.setGasDay(bd1);
	}

	public void valorDefectoGasDay() {

		BigDecimal bd1 = new BigDecimal(1);
		BigDecimal bd2 = new BigDecimal(2);

		if (newNominationDeadline.getIdn_operation_term().equals(bd1)) {
			newNominationDeadline.setGasDay(bd1);
		} else if (newNominationDeadline.getIdn_operation_term().equals(bd2)) {
			newNominationDeadline.setGasDay(bd2);
		}

	}

	public String disabledField(NominationDeadlineBean item) {
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

	public boolean renderItemEditor(NominationDeadlineBean item) {

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
