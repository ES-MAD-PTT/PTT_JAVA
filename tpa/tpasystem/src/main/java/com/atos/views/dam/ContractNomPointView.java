package com.atos.views.dam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.RowEditEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.dam.ContractNomPointBean;
import com.atos.filters.dam.ContractNomPointFilter;
import com.atos.services.dam.ContractNomPointService;
import com.atos.utils.Constants;
import com.atos.utils.POIXSSFExcelUtils;
import com.atos.views.CommonView;

@ManagedBean(name = "contractNomPointView")
@ViewScoped
public class ContractNomPointView  extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(ContractNomPointView.class);

	private ContractNomPointFilter filters;
	private ContractNomPointBean newContractNomPoint;
	private ContractNomPointBean editContractNomPoint = new ContractNomPointBean();
	private ContractNomPointBean contractNomPointIdShipper = new ContractNomPointBean();
	private List<ContractNomPointBean> items;
	private List<ContractNomPointBean> selecteds = new ArrayList<ContractNomPointBean>();
	private List<ContractNomPointBean> selectedsFornNew = new ArrayList<ContractNomPointBean>();
	private List<ContractNomPointBean> selectedsFornEdit = new ArrayList<ContractNomPointBean>();
	private List<ContractNomPointBean> selectedsFornEditDelete = new ArrayList<ContractNomPointBean>();
	private ContractNomPointBean selected;
	
//	private StreamedContent fileSC;
//	private File file;

	@ManagedProperty("#{contractNomPointService}")
	transient private ContractNomPointService service;

	private Calendar sysdate = Calendar.getInstance();

	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}


	private Map<BigDecimal, Object> shippers;
	private Map<BigDecimal, Object> contractIds;
	private Map<BigDecimal, Object> contractForm;
	private Map<BigDecimal, Object> contractNomPoints;
	private Map<BigDecimal, Object> contractNomPointsForm;
	private Map<BigDecimal, Object> nominationPoints;
	private Map<BigDecimal, Object> nominationPointsForm;


	public ContractNomPointFilter getFilters() {
		return filters;
	}

	public void setFilters(ContractNomPointFilter filters) {
		this.filters = filters;
	}

	public ContractNomPointBean getNewContractNomPoint() {
		return newContractNomPoint;
	}

	public ContractNomPointBean getEditContractNomPoint() {
		return editContractNomPoint;
	}

	public void setEditContractNomPoint(ContractNomPointBean editContractNomPoint) {
		this.editContractNomPoint = editContractNomPoint;
	}

	public ContractNomPointBean getContractNomPointIdShipper() {
		return contractNomPointIdShipper;
	}

	public void setContractNomPointIdShipper(ContractNomPointBean contractNomPointIdShipper) {
		this.contractNomPointIdShipper = contractNomPointIdShipper;
	}

	public void setNewContractNomPoint(ContractNomPointBean newContractNomPoint) {
		this.newContractNomPoint = newContractNomPoint;
	}

	public List<ContractNomPointBean> getItems() {
		return items;
	}

	public void setItems(List<ContractNomPointBean> items) {
		this.items = items;
	}

	public void setService(ContractNomPointService service) {
		this.service = service;
	}


	public List<ContractNomPointBean> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<ContractNomPointBean> selecteds) {
		this.selecteds = selecteds;
	}

	public ContractNomPointBean getSelected() {
		return selected;
	}

	public void setSelected(ContractNomPointBean selected) {
		this.selected = selected;
	}

	public List<ContractNomPointBean> getSelectedsFornNew() {
		return selectedsFornNew;
	}

	public void setSelectedsFornNew(List<ContractNomPointBean> selectedsFornNew) {
		this.selectedsFornNew = selectedsFornNew;
	}

	public List<ContractNomPointBean> getSelectedsFornEdit() {
		return selectedsFornEdit;
	}

	public void setSelectedsFornEdit(List<ContractNomPointBean> selectedsFornEdit) {
		this.selectedsFornEdit = selectedsFornEdit;
	}

	public List<ContractNomPointBean> getSelectedsFornEditDelete() {
		return selectedsFornEditDelete;
	}

	public void setSelectedsFornEditDelete(List<ContractNomPointBean> selectedsFornEditDelete) {
		this.selectedsFornEditDelete = selectedsFornEditDelete;
	}
	
//	@SuppressWarnings("resource")
//	public StreamedContent getFileSC() throws FileNotFoundException {
//		if (file != null) {
//			InputStream is = new FileInputStream(file);
//			fileSC = DefaultStreamedContent.builder().name(file.getName())
//					.contentType(new MimetypesFileTypeMap().getContentType(file)).stream(() -> is).build();
//		}
//		return fileSC;
//	}
//
//	public void setFileSC(StreamedContent fileSC) {
//		this.fileSC = fileSC;
//	}

	@PostConstruct
	public void init() {
		filters = new ContractNomPointFilter();
		newContractNomPoint = new ContractNomPointBean();
		newContractNomPoint.setStartDate(sysdate.getTime());
		newContractNomPoint.setIdn_system(getChangeSystemView().getIdn_active());

		// cargamos los combos inicialment
		shippers = service.selectShippers();
		contractIds = service.selectContractIds(filters);
		contractNomPoints = service.selectContractPoints(filters);
		nominationPoints = service.selectNominationPoints(filters);
		nominationPointsForm = service.selectNominationPointsForm(newContractNomPoint);
		
		filters.setIdn_system(getChangeSystemView().getIdn_active());

	}

	public Map<BigDecimal, Object> getShippers() {
		return service.selectShippers();
	}

	public Map<BigDecimal, Object> getContractIds() {
		return service.selectContractIds(filters);
	}

	public Map<BigDecimal, Object> getContractForm() {
		return service.selectContractForm(newContractNomPoint);
	}

	public Map<BigDecimal, Object> getContractNomPointsForm() {
		return service.selectContractNomPointsForm(newContractNomPoint);
	}

	public Map<BigDecimal, Object> getContractPoints() {
		return service.selectContractPoints(filters);
	}

	public Map<BigDecimal, Object> getNominationPoints() {
		return service.selectNominationPoints(filters);
	}
	public Map<BigDecimal, Object> contractPointsTabla(BigDecimal idn_contract) {
		ContractNomPointFilter tableFilters= new ContractNomPointFilter();
		tableFilters.setIdn_contract(idn_contract);
		tableFilters.setIdn_system(getChangeSystemView().getIdn_active());
		tableFilters.setStartDate(sysdate.getTime());
		return service.selectContractPoints(tableFilters);
	}
	public Map<BigDecimal, Object> nominationPointsTabla(BigDecimal idn_contract, BigDecimal idn_contract_point) {
		ContractNomPointFilter tableFilters= new ContractNomPointFilter();
		tableFilters.setIdn_contract(idn_contract);
		tableFilters.setIdn_contract_point(idn_contract_point);
		tableFilters.setIdn_system(getChangeSystemView().getIdn_active());
		tableFilters.setStartDate(sysdate.getTime());
		return service.selectNominationPoints(tableFilters);
	}
	public Map<BigDecimal, Object> getNominationPointsForm() {
		return service.selectNominationPointsForm(newContractNomPoint);
	}
	//Para rellenar la tabla del new
	public void contractNomPointsTable(){
		if (newContractNomPoint.getIdn_contract_point() != null && newContractNomPoint.getIdn_contract_point().compareTo(BigDecimal.ZERO) == 0) {
		    newContractNomPoint.setIdn_contract_point(null);
		    selecteds = service.selectContractNomPointsNullFormTable(newContractNomPoint);
		}

		
		if(newContractNomPoint.getIdn_contract_point() == null) {
			selecteds = service.selectContractNomPointsNullFormTable(newContractNomPoint);
		}else{
			selecteds = service.selectContractNomPointsFormTable(newContractNomPoint);
		}
		
	}

	public void onSearch() {
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		if (filters.getEndDate() != null) {
			if (filters.getStartDate().after(filters.getEndDate())) {
				String strError = msgs.getString("start_previous_end");
		    	getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
				return;
			}
		}
		
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectContractNomPoints(filters);

	}

	public void onClear() {
		// RequestContext.getCurrentInstance().reset("form");
		filters = new ContractNomPointFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
		if (items != null) {
			items.clear();
		}
	}

	public void onRowEdit(RowEditEvent event) {

		ContractNomPointBean contractNomPoint = (ContractNomPointBean) event.getObject();

		String errorMsg = null;
	    
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("contractNomPoint") };
    	String summaryMsgOk = CommonView.getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk= CommonView.getMessageResourceString("update_noOk", params);
		
		if (contractNomPoint.getEndDate() != null) {
			if (contractNomPoint.getStartDate().after(contractNomPoint.getEndDate())) {
				errorMsg = msgs.getString("start_previous_end"); //start_previous_end= Start date must be previous to end date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;
			}

		}

		String error = "0";
		try {
			error = service.updateContractNomPoint(contractNomPoint);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}
		String[] par2 = {contractNomPoint.getContract_id()+"-"+contractNomPoint.getNomination_point(),msgs.getString("contractNomPoint")};
		
		if (error != null && error.equals("0")) {
			String msg = CommonView.getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("ContractNomPoint Updated: " + contractNomPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  ContractNomPoint " + contractNomPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating/insert ContractNomPointParam " + contractNomPoint.toString(),	Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-10")) {
			String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error Updating ContractNomPointParam. ContractNomPointParam, Error: " + error + ". "	+ contractNomPoint.toString(), Calendar.getInstance().getTime());
		} else {
			String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating/insert ContractNomPoint. ContractNomPoint, Error: " + error + ". "+ contractNomPoint.toString(), Calendar.getInstance().getTime());
		}
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectContractNomPoints(filters);
	}

	public void onRowCancel(RowEditEvent event) {
	

	}

	public void cancel() {
		// RequestContext.getCurrentInstance().reset("formNewEntity");
		newContractNomPoint = new ContractNomPointBean();
		newContractNomPoint.setStartDate(sysdate.getTime());
		newContractNomPoint.setIdn_system(getChangeSystemView().getIdn_active());
		selecteds = new ArrayList<ContractNomPointBean>();

	}

	public void save() {

		String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("contractNomPoint") };
    	String summaryMsgOk = CommonView.getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk= CommonView.getMessageResourceString("insert_noOk", params);

		if (newContractNomPoint.getStartDate() != null) {
			if (newContractNomPoint.getStartDate().before(sysdate.getTime())) {
				errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				return;
			}
		}

		if (newContractNomPoint.getEndDate() != null) {
			if (newContractNomPoint.getEndDate().before(sysdate.getTime())) {
				errorMsg = msgs.getString("error_endDate_sysdate"); //error_endDate_sysdate= End Date must be later to sysdate
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				return;
			}
			if (newContractNomPoint.getStartDate().after(newContractNomPoint.getEndDate())) {
				errorMsg = msgs.getString("error_startEarlierEnd"); //error_startEarlierEnd = Start Date must be earlier or equal to End Date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg );
				return;
			}

		}else {
			Date dateEndContract = service.selectDateContra(newContractNomPoint);
			
			if(dateEndContract != null) {
				// Convertir Date a LocalDate
			    LocalDate localDateEndContract = dateEndContract.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			    
			    // Formatear LocalDate como una cadena de texto con el formato deseado: día/mes/año
			    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			    String dateEndContractString = localDateEndContract.format(formatter);
			    
				
				errorMsg = msgs.getString("error_date_contract");
				errorMsg = errorMsg + dateEndContractString;
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg );
				return;
			}
		}

		String error = "0";
		try {
			
			BigDecimal existNumSlop = BigDecimal.ZERO;
			List<BigDecimal> listIdnContractNomPoint = new ArrayList<>();
						
			for (ContractNomPointBean item : selectedsFornNew) {
				listIdnContractNomPoint.add(item.getIdn_nomination_point());				
	        }
			
			newContractNomPoint.setLisIdnNominationPoint(listIdnContractNomPoint);
			existNumSlop = service.selectExistingNumSlop(newContractNomPoint);
			
			if (existNumSlop.compareTo(BigDecimal.ZERO) != 0) {
				errorMsg = msgs.getString("error_no_existing_overlap");
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				newContractNomPoint = new ContractNomPointBean();
				selectedsFornNew = new ArrayList<ContractNomPointBean>();
				return;
			}
			
			if(listIdnContractNomPoint.size() !=0 ) {
				
				
				if(listIdnContractNomPoint != null) {
					for (BigDecimal elemento : listIdnContractNomPoint) {
						
			            newContractNomPoint.setIdn_nomination_point(elemento);
			            error = service.insertContractNomPoint(newContractNomPoint);
			        }
				}
			}else {
				errorMsg = msgs.getString("error_no_point_existing");
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				newContractNomPoint = new ContractNomPointBean();
				selectedsFornNew = new ArrayList<ContractNomPointBean>();
				return;
			}
			
			
			listIdnContractNomPoint = new ArrayList<>();
			
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}
		
		List<BigDecimal> listIdnContractNomPoint = new ArrayList<>();
		
		for (ContractNomPointBean item : selectedsFornNew) {
			listIdnContractNomPoint.add(item.getIdn_nomination_point());				
        }
		
		if(listIdnContractNomPoint != null) {
			for (BigDecimal elemento : listIdnContractNomPoint) {
				ContractNomPointBean contractNomPointIdShipper = service.selectContraCodeById(newContractNomPoint);
				newContractNomPoint.setIdn_nomination_point(elemento);
				ContractNomPointBean nominationById = service.selectCodeNomPointById(newContractNomPoint);						

				// Obtener la lista de códigos de nominación
				List<String> listaCodeNominationPoint = newContractNomPoint.getListCodeNominationPoint();

				// Verificar si la lista es null y, si lo es, inicializarla
				if (listaCodeNominationPoint == null) {
				    listaCodeNominationPoint = new ArrayList<>();
				    newContractNomPoint.setListCodeNominationPoint(listaCodeNominationPoint);
				}

				// Añadir el código de nominación a la lista
				String nominationPoint = nominationById.getNomination_point(); 
				listaCodeNominationPoint.add(nominationPoint);
				
				newContractNomPoint.setContract_id(contractNomPointIdShipper.getContract_id());
	            newContractNomPoint.setIdn_nomination_point(elemento);
	        }
		}
		
		
		// Obtener la lista de códigos de nominación como una cadena con comas entre cada elemento
		String listaComoCadena = String.join(",", newContractNomPoint.getListCodeNominationPoint());
		String[] par2 = {newContractNomPoint.getContract_id()+"  -  "+listaComoCadena,msgs.getString("contractNomPoint") };
		
		if (error != null && error.equals("0")) {
			String msg = getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("ContractNomPoint Inserted ok" + newContractNomPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting ContractNomPoint. The " + newContractNomPoint.toString() + " already exists in the System ", Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));			
			log.info("Error inserting contractNomPoint. Error inserting ContractNomPoint" + newContractNomPoint.toString(),Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-3")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting contractNomPoint. Error inserting ContractNomPointParam"+ newContractNomPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-4")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting contractNomPoint. Error inserting ContractNomPoint" + newContractNomPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-5")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting contractNomPoint. Error inserting ContractNomPointParam(contract)"	+ newContractNomPoint.toString(), Calendar.getInstance().getTime());
		} else {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting contractNomPoint. Generic Error: "	+ newContractNomPoint.toString(), Calendar.getInstance().getTime());
		}

		

		// clean the formu new after save
		newContractNomPoint = new ContractNomPointBean();
		selectedsFornNew = new ArrayList<ContractNomPointBean>();
		selecteds = new ArrayList<ContractNomPointBean>();
		
		onSearch();

	}
	
	
	
	public void prepareEdit(ContractNomPointBean itemEdit) {		
		
		contractNomPointIdShipper = service.selectIdShipper(itemEdit);
		newContractNomPoint = itemEdit;
		selectedsFornNew = service.selectContractNomPointsFormEdit(newContractNomPoint);
		
		List<BigDecimal> listIdnContractNomPoint = new ArrayList<>();
		for (ContractNomPointBean item : selectedsFornNew) {
			listIdnContractNomPoint.add(service.getIdnSystemPoint(item));				
        }
		// Verifica si todos los elementos son iguales
        boolean allEquals = true;
        BigDecimal primerElemento = listIdnContractNomPoint.get(0); // Obtén el primer elemento
        for (BigDecimal elemento : listIdnContractNomPoint) {
            if (!elemento.equals(primerElemento)) {
            	allEquals = false;
                break;
            }
        }

        // Imprime el resultado
        if (allEquals) {
        	newContractNomPoint.setIdn_contract_point(primerElemento);
        } else {
        	newContractNomPoint.setIdn_contract_point(null);
        }
		
		selectedsFornEdit = selectedsFornNew;
		newContractNomPoint.setStartDate(newContractNomPoint.getStartDateActive());
		newContractNomPoint.setEndDate(newContractNomPoint.getEndDateActive());
		newContractNomPoint.setIdn_system(getChangeSystemView().getIdn_active());
		contractNomPointsTable();
		listIdnContractNomPoint.clear();
	}
	
	public void edit() {

		String errorMsg = null;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("contractNomPoint") };
    	String summaryMsgOk = CommonView.getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk= CommonView.getMessageResourceString("insert_noOk", params);

//		if (newContractNomPoint.getStartDate() != null) {
//			if (newContractNomPoint.getStartDate().before(sysdate.getTime())) {
//				errorMsg = msgs.getString("error_startDate_sysdate"); //error_startDate_sysdate= Start date must be later to sysdate
//				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
//				log.error(errorMsg);
//				return;
//			}
//		}
    	sysdate.add(Calendar.DAY_OF_MONTH, -1);
		if (newContractNomPoint.getEndDate() != null) {
			sysdate.add(Calendar.DAY_OF_MONTH, -1);
			if (newContractNomPoint.getEndDate().before(sysdate.getTime())) {
				errorMsg = msgs.getString("error_endDate_sysdate_short"); //error_endDate_sysdate_short= End Date must be later or equal to sysdate
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				return;
			}
			sysdate = Calendar.getInstance();
			if (newContractNomPoint.getStartDate().after(newContractNomPoint.getEndDate())) {
				errorMsg = msgs.getString("error_startEarlierEnd"); //error_startEarlierEnd = Start Date must be earlier or equal to End Date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg );
				return;
			}

		}else {
			Date dateEndContract = service.selectDateContra(newContractNomPoint);
			
			if(dateEndContract != null) {
				// Convertir Date a LocalDate
			    LocalDate localDateEndContract = dateEndContract.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			    
			    // Formatear LocalDate como una cadena de texto con el formato deseado: día/mes/año
			    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			    String dateEndContractString = localDateEndContract.format(formatter);
			    
				
				errorMsg = msgs.getString("error_date_contract");
				errorMsg = errorMsg + dateEndContractString;
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg );
				return;
			}
		}

		String error = "0";
		try {
			List<BigDecimal> listIdnContractNomPoint = new ArrayList<>();
			List<BigDecimal> listIdnContractNomPointPrevious = new ArrayList<>();
			List<BigDecimal> listIdnContractNomPointDelete = new ArrayList<>();
			List<BigDecimal> listIdnContractNomPointDeleteIdnContractPoint = new ArrayList<>();
			
			
			// Aqui guardamos en listIdnContractNomPointPrevious los Idn_nomination_point que estaban seleccionados antes del edit
			for (ContractNomPointBean item : selectedsFornEdit) {
				listIdnContractNomPointPrevious.add(item.getIdn_nomination_point());				
	        }
			
			// Aqui guardamos en listIdnContractNomPoint los Idn_nomination_point que se han seleccionado nuevos
			for (ContractNomPointBean item : selectedsFornNew) {
				listIdnContractNomPoint.add(item.getIdn_nomination_point());				
	        }
			
			newContractNomPoint.setLisIdnNominationPoint(listIdnContractNomPoint);
			
			if(listIdnContractNomPoint.size() !=0 ) {
				
				// Aqui guardamos en listIdnContractNomPointDelete los Idn_nomination_point que existian en listIdnContractNomPointPrevious 
				//pero no existen ahora en listIdnContractNomPoint para borrarlos de BBDD
				for (BigDecimal idnContractNomPointPrevious : listIdnContractNomPointPrevious) {
				        listIdnContractNomPointDelete.add(idnContractNomPointPrevious);				    
				}
				
				//Aqui guardamos el Idn_contract_point en nuestra lista listIdnContractNomPointDeleteIdnContractPoint idn_contract_nom_point
				for (ContractNomPointBean item : selectedsFornEdit) {
				    BigDecimal idnNominationPoint = item.getIdn_nomination_point();
				    if (listIdnContractNomPointDelete.contains(idnNominationPoint)) {
				        listIdnContractNomPointDeleteIdnContractPoint.add(item.getIdn_contract_nom_point());
				    }
				}
				
				//Borramos los Idn_nomination_point BBDD
				if(listIdnContractNomPointDeleteIdnContractPoint != null) {
					for (BigDecimal elemento : listIdnContractNomPointDeleteIdnContractPoint) {
			            newContractNomPoint.setIdn_contract_nom_point(elemento);
			            error = service.deleteContractNomPoint(newContractNomPoint);
			        }
				}
				
				//Insertamos los Idn_nomination_point nuevos en BBDD
				if(listIdnContractNomPoint != null) {
					for (BigDecimal elemento : listIdnContractNomPoint) {
						
			            newContractNomPoint.setIdn_nomination_point(elemento);
			            error = service.insertContractNomPoint(newContractNomPoint);
			        }
				}
				
				listIdnContractNomPoint.clear();
				listIdnContractNomPointPrevious.clear();
				listIdnContractNomPointDelete.clear();
			}else {
				errorMsg = msgs.getString("error_no_point_existing");
				getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
				log.error(errorMsg);
				newContractNomPoint = new ContractNomPointBean();
				selectedsFornNew = new ArrayList<ContractNomPointBean>();
				return;
			}
			
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}
		
		List<BigDecimal> listIdnContractNomPoint = new ArrayList<>();
		
		for (ContractNomPointBean item : selectedsFornNew) {
			listIdnContractNomPoint.add(item.getIdn_nomination_point());				
        }
		
		if(listIdnContractNomPoint != null) {
			for (BigDecimal elemento : listIdnContractNomPoint) {
				newContractNomPoint.setIdn_nomination_point(elemento);
				ContractNomPointBean nominationById = service.selectCodeNomPointById(newContractNomPoint);						

				// Obtener la lista de códigos de nominación
				List<String> listaCodeNominationPoint = newContractNomPoint.getListCodeNominationPoint();

				// Verificar si la lista es null y, si lo es, inicializarla
				if (listaCodeNominationPoint == null) {
				    listaCodeNominationPoint = new ArrayList<>();
				    newContractNomPoint.setListCodeNominationPoint(listaCodeNominationPoint);
				}

				// Añadir el código de nominación a la lista
				String nominationPoint = nominationById.getNomination_point(); 
				listaCodeNominationPoint.add(nominationPoint);
				
	            newContractNomPoint.setIdn_nomination_point(elemento);
	        }
		}

		// Obtener la lista de códigos de nominación como una cadena con comas entre cada elemento
		String listaComoCadena = String.join(",", newContractNomPoint.getListCodeNominationPoint());
		String[] par2 = {newContractNomPoint.getContract_id()+"-"+listaComoCadena,msgs.getString("contractNomPoint") };
		
		if (error != null && error.equals("0")) {
			String msg = getMessageResourceString("inserting_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("ContractNomPoint Inserted ok" + newContractNomPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.info("Error inserting ContractNomPoint. The " + newContractNomPoint.toString() + " already exists in the System ", Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));			
			log.info("Error inserting contractNomPoint. Error inserting ContractNomPoint" + newContractNomPoint.toString(),Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-3")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting contractNomPoint. Error inserting ContractNomPointParam"+ newContractNomPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-4")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting contractNomPoint. Error inserting ContractNomPoint" + newContractNomPoint.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-5")) {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting contractNomPoint. Error inserting ContractNomPointParam(contract)"	+ newContractNomPoint.toString(), Calendar.getInstance().getTime());
		} else {
			String msg = getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting contractNomPoint. Generic Error: "	+ newContractNomPoint.toString(), Calendar.getInstance().getTime());
		}

		onSearch();

		// clean the formu new after save
		newContractNomPoint = new ContractNomPointBean();
		selectedsFornNew = new ArrayList<ContractNomPointBean>();
		selectedsFornEditDelete = new ArrayList<ContractNomPointBean>();
		selectedsFornEdit = new ArrayList<ContractNomPointBean>();

	}
	
	public void onCellEdit(ContractNomPointBean selection) {}

    public void delete(){
    	for(int i=0;i<selecteds.size();i++){
    		String error = "0";
        	try {
    			error = service.deleteContractNomPoint(selecteds.get(i));
        	} catch(Exception e) {
    			log.catching(e);
    			// we assign the return message 
    			error = e.getMessage();
    		}
    		if(error!=null && error.equals("0")){
    			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,"Contract Nom. Point Deleted", "Contract id - Nomination Point: " + selecteds.get(i).getContract_id() +"-" + selecteds.get(i).getNomination_point() + " deleted", Calendar.getInstance().getTime()));	
    		}else if(error!=null && error.equals("-1")){
    			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,"Contract Nom. Point Deleted", "Error deleting Contract Nom. Point Deleted.", Calendar.getInstance().getTime()));		
    		}
    		
    	}
    	onSearch();
    }
    
    public boolean isStartDateBeforeTomorrow() {
        if (newContractNomPoint.getIdn_contract_point() == null) {
            return false; // Si el idn_contract_point es null, entonces la fecha de inicio no puede estar antes de mañana
        }

        Calendar tomorrow = Calendar.getInstance();
        
        // Establecer la hora, minuto, segundo y milisegundo a 0 para el día de mañana
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MILLISECOND, 0);
        
        Date endDate = newContractNomPoint.getEndDate(); 
        
        // Comprobar si startDate es antes de mañana
        return endDate != null && endDate.before(tomorrow.getTime());
    }
    
    public boolean isEndDatePast() {
    	Date endDate = newContractNomPoint.getEndDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Establecer la fecha actual en el calendario
        calendar.add(Calendar.DAY_OF_MONTH, -1); // Restar un día
        
        Date yesterday = calendar.getTime();

        // Comparar endDate con la fecha actual menos un día
        return endDate != null && endDate.before(yesterday);
    }
    
    public Date getToday() {
        return new Date();
    }
    
    public boolean isEndDateBeforeTomorrow() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1); // Sumar un día al calendario
        
        // Establecer la hora, minuto, segundo y milisegundo a 0 para el día de mañana
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MILLISECOND, 0);
        
        Date endDate = newContractNomPoint.getEndDate(); 
        
        // Comprobar si startDate es antes de mañana
        return endDate != null && endDate.before(tomorrow.getTime());
    }
    
    public boolean isEndDateBeforeToday() {
        Calendar tomorrow = Calendar.getInstance();
        
        // Establecer la hora, minuto, segundo y milisegundo a 0 para el día de mañana
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MILLISECOND, 0);
        
        Date endDate = newContractNomPoint.getEndDate(); 
        
        // Comprobar si startDate es antes de mañana
        return endDate != null && endDate.before(tomorrow.getTime());
    }
    
    public boolean isStartDateBeforeToday() {
    	Calendar tomorrow = Calendar.getInstance();
    	tomorrow.add(Calendar.DAY_OF_MONTH, 1); // Obtener la fecha de mañana

    	// Establecer la hora, minuto, segundo y milisegundo a 0 para mañana
    	tomorrow.set(Calendar.HOUR_OF_DAY, 0);
    	tomorrow.set(Calendar.MINUTE, 0);
    	tomorrow.set(Calendar.SECOND, 0);
    	tomorrow.set(Calendar.MILLISECOND, 0);

    	Date startDate = newContractNomPoint.getStartDate();

    	// Comprobar si startDate es antes de mañana
    	return startDate != null && startDate.before(tomorrow.getTime());

    }
    
    
  //**************************************Excel*************************************************************************
	
	public void downloadFile() {
		File file = loadRequest();
		POIXSSFExcelUtils.downloadFile(file);
	}
	
	private File loadRequest() {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		File file = null;
		try {
			Row row = null;
			Cell cell = null;
			int rowNum = 0;
			XSSFWorkbook workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet("Data");
			Font contentFontBold = POIXSSFExcelUtils.createFont(workBook, IndexedColors.BLACK.index, (short)11, true);
			Font contentFont = POIXSSFExcelUtils.createFont(workBook, IndexedColors.BLACK.index, (short)11, false);
			CellStyle cellStyleTableBlueCenter = POIXSSFExcelUtils.createStyle(workBook, contentFontBold, CellStyle.ALIGN_CENTER, IndexedColors.PALE_BLUE.index, 
					true, IndexedColors.GREY_80_PERCENT.index, CellStyle.BORDER_THIN, true, workBook.getCreationHelper().createDataFormat().getFormat("dd-MMM-yyyy"));
			CellStyle cellStyleTableGreyCenter = POIXSSFExcelUtils.createStyle(workBook, contentFontBold, CellStyle.ALIGN_CENTER, IndexedColors.GREY_25_PERCENT.index, 
					true, IndexedColors.GREY_80_PERCENT.index, CellStyle.BORDER_THIN, true, workBook.getCreationHelper().createDataFormat().getFormat("dd-MMM-yyyy"));
			CellStyle cellStyleTableFormatNumber = POIXSSFExcelUtils.createStyle(workBook, contentFont, CellStyle.ALIGN_RIGHT, IndexedColors.WHITE.index, 
					true, IndexedColors.GREY_80_PERCENT.index, CellStyle.BORDER_THIN, true, workBook.getCreationHelper().createDataFormat().getFormat("#,##0.00"));
			CellStyle cellStyleTableFormatDate = POIXSSFExcelUtils.createStyle(workBook, contentFont, CellStyle.ALIGN_LEFT, IndexedColors.WHITE.index, 
					true, IndexedColors.GREY_80_PERCENT.index, CellStyle.BORDER_THIN, true, workBook.getCreationHelper().createDataFormat().getFormat("dd-MM-yyyy"));
			CellStyle cellNormalStyle = POIXSSFExcelUtils.createStyle(workBook, contentFont, CellStyle.ALIGN_LEFT, IndexedColors.WHITE.index, 
					true, IndexedColors.GREY_80_PERCENT.index, CellStyle.BORDER_THIN, true, workBook.getCreationHelper().createDataFormat().getFormat("#,##0.00"));
			
			List<String> headerTable1 = Arrays.asList(msgs.getString("contractNomPoint_id_shipper"), msgs.getString("contractNomPoint_id_contract"), 
					msgs.getString("contractNomPoint_date_from_contract"),msgs.getString("contractNomPoint_date_to_contract"),msgs.getString("contractNomPoint_date_from_active"),
					msgs.getString("contractNomPoint_date_to_active"));
			List<String> headerTable2 = Arrays.asList(msgs.getString("contractNomPoint_contract_point"), msgs.getString("contractNomPoint_nomination_point"));
			
			POIXSSFExcelUtils.createSimpleHeaderTable(sheet, row, cell, rowNum, 0, headerTable1, cellStyleTableBlueCenter);
			rowNum ++;
			if(items != null && !items.isEmpty()) {
				for(int i = 0; i < items.size(); i++) {
					row = sheet.createRow(rowNum);
					Object[] properties1 = {
							items.get(i).getShipper(),items.get(i).getContract_id(), items.get(i).getStartDate(), items.get(i).getEndDate(), items.get(i).getStartDateActive(), items.get(i).getEndDateActive()
					    };
					POIXSSFExcelUtils.createCellsTable(row, cell, 0, cellStyleTableFormatDate, cellStyleTableFormatNumber, cellNormalStyle, properties1);
					rowNum++;
					//Creamos la cabecera de los detalles con sus detalles
					POIXSSFExcelUtils.createSimpleHeaderTable(sheet, row, cell, rowNum, 2, headerTable2, cellStyleTableGreyCenter);
					selectedsFornNew = service.selectContractNomPointsFormEdit(items.get(i));
					rowNum++;
					if(selectedsFornNew != null && !selectedsFornNew.isEmpty()) {
						for(int m = 0; m < selectedsFornNew.size(); m++) {
							row = sheet.createRow(rowNum);
							Object[] properties2 = {
									selectedsFornNew.get(m).getContract_point(), selectedsFornNew.get(m).getNomination_point()
							    };
							POIXSSFExcelUtils.createCellsTable(row, cell, 2, cellStyleTableFormatDate, cellStyleTableFormatNumber, cellNormalStyle, properties2);
							rowNum++;
						}
					}
				}
			}

			FileOutputStream outFile = new FileOutputStream("ContractNomPointRelation.xlsx");
			workBook.write(outFile);
			outFile.close();
			file = new File("ContractNomPointRelation.xlsx");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	
	
}
