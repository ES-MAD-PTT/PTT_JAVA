package com.atos.views.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
import com.atos.beans.dam.MeteredPointShipperBean;
import com.atos.filters.dam.MeteredPointsShipperFilter;
import com.atos.services.dam.MeteredPointsShipperService;
import com.atos.utils.Constants;
import com.atos.utils.DateUtil;
import com.atos.views.CommonView;

@ManagedBean(name = "meteredPointShipperView")
@ViewScoped
public class MeteredPointShipperView  extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(MeteredPointShipperView.class);
	
	private List<MeteredPointShipperBean> items;
	private MeteredPointShipperBean selection;
	private List<MeteredPointShipperBean> selectionTableAddEdit;
	List<MeteredPointShipperBean> copySelectionTableAddEdit;
	private List<MeteredPointShipperBean> allDataTableAddEdit;
	private Boolean disabledEdit;
	private Boolean renderedEndDateEdit;
	private Boolean renderedButtonAcceptEdit;
	private Boolean edit;

	//Filters
	private MeteredPointsShipperFilter filters;
	//private Map<BigDecimal, Object> shippers;
	private Map<BigDecimal, Object> meteredPoints;
	private Map<BigDecimal, Object> customerTypes;
	private Map<BigDecimal, Object> groups;
	
	private Date sysdate = DateUtil.getToday();

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

	public Date getSysdate() {
		return sysdate;
	}

	public void setSysdate(Date sysdate) {
		this.sysdate = sysdate;
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

	public Boolean getRenderedEndDateEdit() {
		return renderedEndDateEdit;
	}

	public void setRenderedEndDateEdit(Boolean renderedEndDateEdit) {
		this.renderedEndDateEdit = renderedEndDateEdit;
	}

	public Boolean getRenderedButtonAcceptEdit() {
		return renderedButtonAcceptEdit;
	}

	public void setRenderedButtonAcceptEdit(Boolean renderedButtonAcceptEdit) {
		this.renderedButtonAcceptEdit = renderedButtonAcceptEdit;
	}

	public List<MeteredPointShipperBean> getSelectionTableAddEdit() {
		return selectionTableAddEdit;
	}

	public void setSelectionTableAddEdit(List<MeteredPointShipperBean> selectionTableAddEdit) {
		this.selectionTableAddEdit = selectionTableAddEdit;
	}

	public List<MeteredPointShipperBean> getAllDataTableAddEdit() {
		return allDataTableAddEdit;
	}

	public void setAllDataTableAddEdit(List<MeteredPointShipperBean> allDataTableAddEdit) {
		this.allDataTableAddEdit = allDataTableAddEdit;
	}

	public Boolean getDisabledEdit() {
		return disabledEdit;
	}

	public void setDisabledEdit(Boolean disabledEdit) {
		this.disabledEdit = disabledEdit;
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
	}

	public void onSearch() {
		items = new ArrayList<MeteredPointShipperBean>();
		items = service.selectMeteredPointShipper(filters);

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
		selection.setIdnSystem(getChangeSystemView().getIdn_active());
		selection.setUserName(getUser().getUsername());
		chargeAddEditTable();
		disabledEdit = false;
		renderedButtonAcceptEdit = true;
		renderedEndDateEdit = true;
		edit = false;
	}
	
	public void prepareEdit(MeteredPointShipperBean item) {
		edit = true;
		selection = item;
		selection.setIdnSystem(getChangeSystemView().getIdn_active());
		selection.setUserName(getUser().getUsername());
		selectionTableAddEdit = new ArrayList<MeteredPointShipperBean>();
		allDataTableAddEdit = new ArrayList<MeteredPointShipperBean>();
		copySelectionTableAddEdit = new ArrayList<MeteredPointShipperBean>();
		if(selection != null) {
			selectionTableAddEdit = service.selectAllDataMeteredPointShipper(selection);
			copySelectionTableAddEdit.addAll(selectionTableAddEdit);
		}
		selection.setIdnMeteringPoint(null);
		chargeAddEditTable();
		if(selection.getIdnShipper() != null && selection.getStartDate().before(Calendar.getInstance().getTime()) && selection.getEndDate().after(Calendar.getInstance().getTime())) {
			//Cuando el dia de hoy está entre las dos fecahs
			//Deshabilitado todo y se puede cambiar fecha fin
			disabledEdit = true;
			renderedButtonAcceptEdit = true;
			renderedEndDateEdit = true;
		}
		if(selection.getIdnShipper() != null && selection.getEndDate().before(Calendar.getInstance().getTime())) {
			//Cuando las fechas son a pasado
			//Deshabilitado todo
			disabledEdit = true;
			renderedButtonAcceptEdit = false;
			renderedEndDateEdit = false;
		}
		if(selection.getIdnShipper() != null && selection.getStartDate().after(Calendar.getInstance().getTime())) {
			//Cuando las fechas son a futuro 
			//habilitar todo
			disabledEdit = false;
			renderedButtonAcceptEdit = true;
			renderedEndDateEdit = false;
		}
		
	}
	
	public void save() throws Throwable {
		String error = "0";
		String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	String[] params = {msgs.getString("metPointShipper_msg") };
    	String summaryMsgOk = CommonView.getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk= CommonView.getMessageResourceString("insert_noOk", params);
    			
		if(selection.getIdnShipper() == null) {
			errorMsg = msgs.getString("metPointShipper_need_shipperId"); 
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
			log.error(errorMsg);
			return;
		}
		if(selection.getStartDate() == null) {
			errorMsg = msgs.getString("metPointShipper_need_dateFrom"); 
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
			log.error(errorMsg);
			return;
		}
		if(selection.getEndDate() == null) {
			errorMsg = msgs.getString("metPointShipper_need_dateTo"); 
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
			log.error(errorMsg);
			return;
		}
		//Si no hay nada seleccionado en la tabla se saca mensaje
		if(selectionTableAddEdit == null || selectionTableAddEdit.isEmpty()) {
			errorMsg = msgs.getString("metPointShipper_need_meteredPointID"); 
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
			log.error(errorMsg);
			return;
		}		
		if(!edit) {
			//Lo que tenemos seleccionado ya existe en base de datos sacamos mensaje
			List<MeteredPointShipperBean> metPoint = service.selectAllDataMeteredPointShipper(selection);
			boolean exist = metPoint.stream()
	                .anyMatch(item -> item.getIdnMeteringPoint().equals(selection.getIdnMeteringPoint()));
			if(exist) {
				errorMsg = msgs.getString("metPointShipper_alreadySaved_meteredPoint"); 
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				return;
			}
			//Si ya existen registro de metered point form shipper se saca mensaje
			MeteredPointsShipperFilter filter = new MeteredPointsShipperFilter(selection.getIdnShipper(), selection.getStartDate(), selection.getEndDate());
			List<MeteredPointShipperBean> metPointShipper = service.selectMeteredPointShipper(filter);
			if(metPointShipper != null && !metPointShipper.isEmpty() && metPointShipper.size() > 0) {
				errorMsg = msgs.getString("metPointShipper_exist_meteredPoint_thoseDates"); 
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				return;
			}
			error = service.insertMeteredPointShipper(selection, selectionTableAddEdit);
			switch (error) {
			case "0":
				String[] par2 = {selection.getShipper(), msgs.getString("metPointShipper_msg") };
				String msg = getMessageResourceString("inserting_ok", par2);
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('dlgNewContractNomPoint').hide();");
				break;
			case "1":
				errorMsg = msgs.getString("metPointShipper_error_inserting"); 
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				break;
			}
		}else if(edit) {
			String updateMsgOk = CommonView.getMessageResourceString("update_ok", params);
	    	String updateMsgNotOk= CommonView.getMessageResourceString("update_noOk", params);
			if(!renderedEndDateEdit) {
				// Filtrar objetos que se agregarán
		        List<MeteredPointShipperBean> addMeteredPoint = selectionTableAddEdit.stream()
		                .filter(object -> copySelectionTableAddEdit.stream().noneMatch(o -> o.getCompositeKey().compareTo(object.getCompositeKey()) == 0))
		                .collect(Collectors.toList());
	
		        // Filtrar objetos que se eliminarán
		        List<MeteredPointShipperBean> deleteMeteredPoint = copySelectionTableAddEdit.stream()
		                .filter(object -> selectionTableAddEdit.stream().noneMatch(o -> o.getCompositeKey().compareTo(object.getCompositeKey()) == 0))
		                .collect(Collectors.toList());
				
		        if(addMeteredPoint != null && !addMeteredPoint.isEmpty()) {
		        	error = service.insertMeteredPointShipper(selection, addMeteredPoint);
		        }
		        if(deleteMeteredPoint != null && !deleteMeteredPoint.isEmpty()) {
		        	error = service.deleteMeteredPointShipper(deleteMeteredPoint);
		        }
			}else {
				error = service.updateDateMeteredPointShipper(selection);
			}
			switch (error) {
			case "0":
				String[] par2 = {selection.getShipper(), msgs.getString("metPointShipper_msg") };
				String msg = getMessageResourceString("updating_ok", par2);
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,updateMsgOk, msg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('dlgNewContractNomPoint').hide();");
				break;
			case "1":
				errorMsg = msgs.getString("metPointShipper_error_update"); 
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, updateMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				break;
			}
		}
	}
	
	public void chargeAddEditTable() {
		if(selection != null) {
			allDataTableAddEdit = service.selectMetPointCustomerGroup(selection);
		}
	}
	
	public Map<BigDecimal, Object> getMeteredPoints() {
		if(selection != null) {
			meteredPoints = service.selectMeteredPoin(selection);
		}
		return meteredPoints;
	}

	public Map<BigDecimal, Object> getCustomerTypes() {
		if(selection != null) {
			customerTypes = service.selectCustomerType(selection);
		}
		return customerTypes;
	}

	public Map<BigDecimal, Object> getGroups() {
		if(selection != null) {
			groups = service.selectGroupId(selection);
		}
		return groups;
	}
	
	public void delete() {
		
	}
	
	public void postProcessXLS(Object document) {
		
	}
	
	public void onRowEdit(RowEditEvent event) {
		
	}
	
	public void onRowCancel(RowEditEvent event) {
		
	}

	//*********************Filters*************************************************
	private void initializeFilters() {
		filters = new MeteredPointsShipperFilter();
		filters.setIdnSystem(getChangeSystemView().getIdn_active());
	}
	
	public Map<BigDecimal, Object> getShippers() {
		return service.selectShippers();
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
