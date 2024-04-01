package com.atos.views.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.RowEditEvent;

import com.atos.beans.dam.MeteredPointShipperBean;
import com.atos.filters.dam.MeteredPointsShipperFilter;
import com.atos.services.dam.MeteredPointsShipperService;
import com.atos.utils.DateUtil;
import com.atos.views.CommonView;

@ManagedBean(name = "meteredPointShipperView")
@ViewScoped
public class MeteredPointShipperView  extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(MeteredPointShipperView.class);
	
	private List<MeteredPointShipperBean> items;
	private MeteredPointShipperBean selection;

	//Filters
	private MeteredPointsShipperFilter filters;
	//private Map<BigDecimal, Object> shippers;
	private Map<BigDecimal, Object> meteredPoints;
	private Map<BigDecimal, Object> customerTypes;
	private Map<BigDecimal, Object> groups;

	@ManagedProperty("#{meteredPointsShipperService}")
	transient private MeteredPointsShipperService service;

	public void setService(MeteredPointsShipperService service) {
		this.service = service;
	}

	public List<MeteredPointShipperBean> getItems() {
		return items;
	}

	public void setItems(List<MeteredPointShipperBean> items) {
		this.items = items;
	}

	public MeteredPointShipperBean getSelection() {
		if(selection == null) {
			selection = new MeteredPointShipperBean();
		}
		return selection;
	}

	public void setSelection(MeteredPointShipperBean selection) {
		this.selection = selection;
	}

	public MeteredPointsShipperFilter getFilters() {
		return filters;
	}

	public void setFilters(MeteredPointsShipperFilter filters) {
		this.filters = filters;
	}

	@PostConstruct
	public void init() {
		initializeFilters();
		items = new ArrayList<MeteredPointShipperBean>();
		items.add(new MeteredPointShipperBean(new BigDecimal(1), "NGP-S16-001(PTT)", DateUtil.getToday(), DateUtil.getToday(), "ACC", "IPP", "Exit-A2"));
		items.add(new MeteredPointShipperBean(new BigDecimal(2), "NGP-S17-002(EGAT)", DateUtil.getToday(), DateUtil.getToday(), "ATT", "IND", "Exit-C1"));
		items.add(new MeteredPointShipperBean(new BigDecimal(3), "NGP-S17-002(EGAT)" , DateUtil.getToday(), DateUtil.getToday(), "ABP2", "SPP", "Exit-A1"));
	}

	public void onSearch() {
		
//		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
//		
//		if (filters.getEndDate() != null) {
//			if (filters.getStartDate().after(filters.getEndDate())) {
//				String strError = msgs.getString("start_previous_end");
//		    	getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
//				return;
//			}
//		}
//		
//		filters.setIdn_system(getChangeSystemView().getIdn_active());
//		items = service.selectContractNomPoints(filters);

	}

	public void onClear() {
		// RequestContext.getCurrentInstance().reset("form");
//		filters = new ContractNomPointFilter();
//		filters.setStartDate(Calendar.getInstance().getTime());
//		if (items != null) {
//			items.clear();
//		}
	}
	
	public void prepareNew() {
		selection = new MeteredPointShipperBean();
	}
	
	public void delete() {
		
	}
	
	public void postProcessXLS(Object document) {
		
	}
	
	public void onRowEdit(RowEditEvent event) {
		
	}
	
	public void onRowCancel(RowEditEvent event) {
		
	}
	
	public void save() {
		
	}

	//*********************Filters*************************************************
	private void initializeFilters() {
		filters = new MeteredPointsShipperFilter();
		filters.setIdnSystem(getChangeSystemView().getIdn_active());
	}
	
	public Map<BigDecimal, Object> getShippers() {
		return service.selectShippers();
	}

	public Map<BigDecimal, Object> getMeteredPoints() {
		if(meteredPoints == null && filters != null) {
			meteredPoints = service.selectMeteredPoin(filters);
		}
		return meteredPoints;
	}

	public Map<BigDecimal, Object> getCustomerTypes() {
		if(filters != null) {
			customerTypes = service.selectCustomerType(filters);
		}
		return customerTypes;
	}

	public Map<BigDecimal, Object> getGroups() {
		return groups;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//*********************************************************************************
//	public void onRowEdit(RowEditEvent event) {
//
//		ContractNomPointBean contractNomPoint = (ContractNomPointBean) event.getObject();
//
//		String errorMsg = null;
//	    
//    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
//    	String[] params = {msgs.getString("contractNomPoint") };
//    	String summaryMsgOk = CommonView.getMessageResourceString("update_ok", params);
//    	String summaryMsgNotOk= CommonView.getMessageResourceString("update_noOk", params);
//		
//		if (contractNomPoint.getEndDate() != null) {
//			if (contractNomPoint.getStartDate().after(contractNomPoint.getEndDate())) {
//				errorMsg = msgs.getString("start_previous_end"); //start_previous_end= Start date must be previous to end date
//				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
//		    	log.error(errorMsg);
//				return;
//			}
//
//		}
//
//		String error = "0";
//		try {
//			error = service.updateContractNomPoint(contractNomPoint);
//		} catch (Exception e) {
//			log.catching(e);
//			// we assign the return message
//			error = e.getMessage();
//		}
//		String[] par2 = {contractNomPoint.getContract_id()+"-"+contractNomPoint.getNomination_point(),msgs.getString("contractNomPoint")};
//		
//		if (error != null && error.equals("0")) {
//			String msg = CommonView.getMessageResourceString("updating_ok", par2);
//			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
//			log.info("ContractNomPoint Updated: " + contractNomPoint.toString(), Calendar.getInstance().getTime());
//		} else if (error != null && error.equals("-1")) {
//			String msg = CommonView.getMessageResourceString("error_updating", par2);
//			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
//			log.info("Error updating  ContractNomPoint " + contractNomPoint.toString(), Calendar.getInstance().getTime());
//		} else if (error != null && error.equals("-2")) {
//			String msg = CommonView.getMessageResourceString("error_updating", par2);
//			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
//			log.info("Error updating/insert ContractNomPointParam " + contractNomPoint.toString(),	Calendar.getInstance().getTime());
//		} else if (error != null && error.equals("-10")) {
//			String msg = CommonView.getMessageResourceString("error_updating", par2);
//			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
//			log.info("Error Updating ContractNomPointParam. ContractNomPointParam, Error: " + error + ". "	+ contractNomPoint.toString(), Calendar.getInstance().getTime());
//		} else {
//			String msg = CommonView.getMessageResourceString("error_updating", par2);
//			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
//			log.info("Error updating/insert ContractNomPoint. ContractNomPoint, Error: " + error + ". "+ contractNomPoint.toString(), Calendar.getInstance().getTime());
//		}
//		filters.setIdn_system(getChangeSystemView().getIdn_active());
//		items = service.selectContractNomPoints(filters);
//	}
//
//	public void onRowCancel(RowEditEvent event) {
//	
//
//	}
//
//	public void cancel() {
//		// RequestContext.getCurrentInstance().reset("formNewEntity");
//		newContractNomPoint = new ContractNomPointBean();
//		newContractNomPoint.setStartDate(sysdate.getTime());
//		newContractNomPoint.setIdn_system(getChangeSystemView().getIdn_active());
//
//	}
//
//	public void save() {
//
//		String errorMsg = null;
//    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
//    	
//    	
//    	String[] params = {msgs.getString("contractNomPoint") };
//    	String summaryMsgOk = CommonView.getMessageResourceString("insert_ok", params);
//    	String summaryMsgNotOk= CommonView.getMessageResourceString("insert_noOk", params);
//
//		if (newContractNomPoint.getStartDate() != null) {
//			if (newContractNomPoint.getStartDate().before(sysdate.getTime())) {
//				errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
//				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
//				log.error(errorMsg);
//				return;
//			}
//		}
//
//		if (newContractNomPoint.getEndDate() != null) {
//			if (newContractNomPoint.getEndDate().before(sysdate.getTime())) {
//				errorMsg = msgs.getString("error_endDate_sysdate"); //error_endDate_sysdate= End Date must be later to sysdate
//				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
//				log.error(errorMsg);
//				return;
//			}
//			if (newContractNomPoint.getStartDate().after(newContractNomPoint.getEndDate())) {
//				errorMsg = msgs.getString("error_startEarlierEnd"); //error_startEarlierEnd = Start Date must be earlier or equal to End Date
//				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
//				log.error(errorMsg );
//				return;
//			}
//
//		}
//
//		String error = "0";
//		try {
//			error = service.insertContractNomPoint(newContractNomPoint);
//		} catch (Exception e) {
//			log.catching(e);
//			// we assign the return message
//			error = e.getMessage();
//		}
//
//		String[] par2 = {newContractNomPoint.getContract_id()+"-"+newContractNomPoint.getNomination_point(),msgs.getString("contractNomPoint") };
//		
//		if (error != null && error.equals("0")) {
//			String msg = getMessageResourceString("inserting_ok", par2);
//			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
//			log.info("ContractNomPoint Inserted ok" + newContractNomPoint.toString(), Calendar.getInstance().getTime());
//		} else if (error != null && error.equals("-1")) {
//			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
//			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
//			log.info("Error inserting ContractNomPoint. The " + newContractNomPoint.toString() + " already exists in the System ", Calendar.getInstance().getTime());
//		} else if (error != null && error.equals("-2")) {
//			String msg = getMessageResourceString("error_inserting", par2);
//			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));			
//			log.info("Error inserting contractNomPoint. Error inserting ContractNomPoint" + newContractNomPoint.toString(),Calendar.getInstance().getTime());
//		} else if (error != null && error.equals("-3")) {
//			String msg = getMessageResourceString("error_inserting", par2);
//			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
//			log.info("Error inserting contractNomPoint. Error inserting ContractNomPointParam"+ newContractNomPoint.toString(), Calendar.getInstance().getTime());
//		} else if (error != null && error.equals("-4")) {
//			String msg = getMessageResourceString("error_inserting", par2);
//			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
//			log.info("Error inserting contractNomPoint. Error inserting ContractNomPoint" + newContractNomPoint.toString(), Calendar.getInstance().getTime());
//		} else if (error != null && error.equals("-5")) {
//			String msg = getMessageResourceString("error_inserting", par2);
//			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
//			log.info("Error inserting contractNomPoint. Error inserting ContractNomPointParam(contract)"	+ newContractNomPoint.toString(), Calendar.getInstance().getTime());
//		} else {
//			String msg = getMessageResourceString("error_inserting", par2);
//			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
//			log.info("Error inserting contractNomPoint. Generic Error: "	+ newContractNomPoint.toString(), Calendar.getInstance().getTime());
//		}
//
//		onSearch();
//
//		// clean the formu new after save
//		newContractNomPoint = new ContractNomPointBean();
//
//	}


}
