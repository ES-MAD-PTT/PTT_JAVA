package com.atos.views.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.MessageBean;
import com.atos.beans.balancing.ReserveBalancingGasContractBean;
import com.atos.beans.balancing.ReserveBalancingGasContractDetailBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.balancing.ReserveBalancingGasContractsFilter;
import com.atos.services.balancing.ReserveBalancingGasContractsService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="ResBalGasContractsView")
@ViewScoped
public class ReserveBalancingGasContractsView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -331113851705696799L;

	private ReserveBalancingGasContractsFilter filters;
	private List<ReserveBalancingGasContractBean> items;
	private ReserveBalancingGasContractBean selected;
	private ReserveBalancingGasContractBean newContract;
	private List<ReserveBalancingGasContractDetailBean> selectedNewDetails;
	// Esta variable se consulta 1 vez y tiene duracion de la vista.
	private Map<BigDecimal, Object> allValidPoints;	
	
	private static final Logger log = LogManager.getLogger("com.atos.views.balancing.ReserveBalancingGasContractsView");
	
	@ManagedProperty("#{resBalGasContractsService}")
    transient private ReserveBalancingGasContractsService service;
    
	public void setService(ReserveBalancingGasContractsService service) {
		this.service = service;
	}
	
	//geters/seters
	public ReserveBalancingGasContractsFilter getFilters() {
		return filters;
	}

	public void setFilters(ReserveBalancingGasContractsFilter filters) {
		this.filters = filters;
	}

	public List<ReserveBalancingGasContractBean> getItems() {
		return items;
	}

	public void setItems(List<ReserveBalancingGasContractBean> items) {
		this.items = items;
	}

	public ReserveBalancingGasContractBean getSelected() {
		return selected;
	}

	public void setSelected(ReserveBalancingGasContractBean selected) {
		this.selected = selected;
	}

	public ReserveBalancingGasContractBean getNewContract() {
		return newContract;
	}

	public void setNewContract(ReserveBalancingGasContractBean newContract) {
		this.newContract = newContract;
	}

	public List<ReserveBalancingGasContractDetailBean> getSelectedNewDetails() {
		return selectedNewDetails;
	}

	public void setSelectedNewDetails(List<ReserveBalancingGasContractDetailBean> selectedNewDetails) {
		this.selectedNewDetails = selectedNewDetails;
	}

	// Este booleano marcara si esta habilitado el filtro de shipper (isOperator = true) o esta deshabilitado, 
	// con un shipper fijo.
	public boolean getIsShipper() {
		return getUser().isUser_type(Constants.SHIPPER);
	}
	
	@PostConstruct
    public void init() {
		// Se consultan todos los puntos vigentes sin filtro de zona o tipo de punto.
		allValidPoints = service.selectValidPoints(new ReserveBalancingGasContractDetailBean());
		filters = initFilter();
		filters.setIdnSystem(getChangeSystemView().getIdn_active());
        items = service.search(filters);
        selected = new ReserveBalancingGasContractBean();
        createNewContract();
    	selectedNewDetails = new ArrayList<ReserveBalancingGasContractDetailBean>();
	}
	
	public void removeContract() {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
		if(selected != null && selected.getContractId() != null){
			try {
				if(service.checkDeletableContract(selected.getContractId())){
					service.deleteContract(selected.getContractId());
					try {
					items = service.search(filters);
					} catch (Exception e) {
						// should not happen
					}
					String[] params = { selected.getContractCode() };
					String msg = CommonView.getMessageResourceString("res_bal_con_contract_deleted", params);

					getMessages().addMessage(Constants.head_menu[9], new MessageBean(Constants.INFO,
							msgs.getString("conf_info"), msg, Calendar.getInstance().getTime()));
				}
					else{
					String[] params = { selected.getContractCode() };
					String msg = CommonView.getMessageResourceString("res_bal_con_no_deletable", params);

					getMessages().addMessage(Constants.head_menu[9], new MessageBean(Constants.ERROR,
							msgs.getString("validation_error"), msg, Calendar.getInstance().getTime()));
					}
			} catch (Exception e) {

				getMessages().addMessage(Constants.head_menu[9], new MessageBean(Constants.ERROR, "contract NOT deleted",
						"Error deleting contract", Calendar.getInstance().getTime()));
				log.error("Error deleting contract", e);
				e.printStackTrace();
			}
		}
		else{


			getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, msgs.getString("validation_error"),
							msgs.getString("res_bal_select_contract"), Calendar.getInstance().getTime()));
		}
	}

	private ReserveBalancingGasContractsFilter initFilter(){
		ReserveBalancingGasContractsFilter tmpFilter = new ReserveBalancingGasContractsFilter();
		
		// Si no es operador, se fija el filtro al shipper del usuario.
		if(! Constants.OPERATOR.equalsIgnoreCase(getUser().getUser_type())) {
			tmpFilter.setShipperId(getUser().getIdn_user_group());
		} 
		
		Calendar tmpToday = Calendar.getInstance();
		tmpToday.set(Calendar.HOUR_OF_DAY, 0);
		tmpToday.set(Calendar.MINUTE, 0);
		tmpToday.set(Calendar.SECOND, 0);
		tmpToday.set(Calendar.MILLISECOND, 0);
		tmpFilter.setStartDate(tmpToday.getTime());

		return tmpFilter;
	}
	
	// Para los elementos del combo del filtro de shippers.
	public Map<BigDecimal, Object> getShipperIds() {
		return service.selectShipperId();
	}

	// Para newContract.
	public Map<BigDecimal, Object> getValidShipperIds() {
		return service.selectValidShipperId();
	}
	
	public Map<BigDecimal, Object> getReserveContractCodes(ReserveBalancingGasContractsFilter filter) {
		return service.selectReserveContractCodes(filter);
	}
	
	public Map<BigDecimal, Object> getZones(String systemCode) {
		return service.selectZones(systemCode);
	}

	// Para details de newContract.
	public Map<BigDecimal, Object> getValidZones(String systemCode) {
		return service.selectZones(systemCode);
	}

	public Map<BigDecimal, Object> getAreas(String systemCode, ReserveBalancingGasContractDetailBean det) {
		return service.selectAreas(systemCode, det);
	}
	
	public Map<BigDecimal, Object> getPointTypes() {
		return service.selectPointTypes();
	}
		
	public Map<BigDecimal, Object> getValidPoints(ReserveBalancingGasContractDetailBean det) {
		det.setIdnSystem(getChangeSystemView().getIdn_active());
		return service.selectValidPoints(det);
	}
	
	public void onSearch(){
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	if((filters.getStartDate()!=null) && (filters.getEndDate()!=null)){
    		if(filters.getStartDate().after(filters.getEndDate())){
    	    	getMessages().addMessage(Constants.head_menu[9],
    	    			new MessageBean(Constants.ERROR,
    	    					msgs.getString("menu_reserve_balancing_gas_contracts"), 
    							msgs.getString("dates_order"), 
    							Calendar.getInstance().getTime()));
    	    	log.error("Start date must be previous or equal to end date.");
    	    	return;
    		}
    	}
		
        items = service.search(filters);
        // En cada busqueda se resetea la fila seleccionada.
        selected = new ReserveBalancingGasContractBean();
        // Se resetea newDocument porque no se hace al pulsar el boton.
        createNewContract();
    }

	public void onClear(){
		filters = initFilter();
		filters.setIdnSystem(getChangeSystemView().getIdn_active());
	  	
        if (items != null) {
            items.clear();
        }
        
        items = service.search(filters);
        // En cada busqueda se resetea la fila seleccionada.
        selected = new ReserveBalancingGasContractBean();
        // Se resetea newDocument porque no se hace al pulsar el boton.
        createNewContract();
    }
	
	public void getFile(ReserveBalancingGasContractBean _contract) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	String summaryMsg = msgs.getString("res_bal_con_downloading_contract_file");
    	String errorMsg = null;
    	
    	try {
			service.getFile(_contract);    	
    	}
    	catch(ValidationException ve){
    		errorMsg = msgs.getString("download_error") + " " + _contract.getFileName() + ": " + ve.getMessage();
    		getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;    		
    	}
    	catch(Exception e){
    		errorMsg = msgs.getString("download_error") + " " + _contract.getFileName();
    		getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}
	}
	
	// Se genera el nuevo contrato al cargar el dialogo newContractDlg, para que en ese dialogo este
	// disponible para anadirle el resto de campos.
	public void createNewContract() {
    	// Se descarta el documento nuevo que se hubiera generado hasta ahora y se crea un nuevo.
    	newContract = new ReserveBalancingGasContractBean();
		// Si no es operador, se fija el filtro al shipper del usuario.
		if(! Constants.OPERATOR.equalsIgnoreCase(getUser().getUser_type()))
			newContract.setShipperId(getUser().getIdn_user_group());
		
    	selectedNewDetails = new ArrayList<ReserveBalancingGasContractDetailBean>();
	}

	public void handleDocFileUpload( FileUploadEvent event) {

		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	UploadedFile uUploadedFile = null;
    	String summaryMsg = null;
    	String errorMsg = null;
		
		uUploadedFile = event.getFile();
		if(uUploadedFile!=null){
			newContract.setFileName(uUploadedFile.getFileName());
			newContract.setBinaryData(uUploadedFile.getContents());
		} else {
	    	summaryMsg = msgs.getString("cr_man_additional_doc");			
    		errorMsg = msgs.getString("file_must_selected");
			getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
		}
    }
	
	public void createNewDetail() {
		
		ReserveBalancingGasContractDetailBean tmpDetail = new ReserveBalancingGasContractDetailBean();
		
		newContract.getDetails().add(tmpDetail);
	}
	
	public void deleteSelectedDetails() {
		
		List<ReserveBalancingGasContractDetailBean> tmpFromDetails = newContract.getDetails();
		List<ReserveBalancingGasContractDetailBean> tmpToDetails = new ArrayList<ReserveBalancingGasContractDetailBean>();
		// OJO, cada vez que se borra un elemento se mueven los restantes hacia el principio para cerrar el 
		// hueco. Pero como buscamos los details en los que coincida el randomId, no hay problema de que se descoloque.
		contract_det_loop:
		for(int i=0; i<tmpFromDetails.size(); i++){
			ReserveBalancingGasContractDetailBean fromDetail = tmpFromDetails.get(i);

			for(int j=0; j<selectedNewDetails.size(); j++){
				ReserveBalancingGasContractDetailBean selectedDetail = selectedNewDetails.get(j);

				if(fromDetail.getRandomId().compareTo(selectedDetail.getRandomId())==0)
					continue contract_det_loop;
			}
			// Si no se ha encontrado en la lista de los que hay que borrar, se anade a la nueva lista.
			tmpToDetails.add(fromDetail);
		}
		
		newContract.setDetails(tmpToDetails);
    	selectedNewDetails = new ArrayList<ReserveBalancingGasContractDetailBean>();
	}
	
    public void onSave() {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = null;
    	String errorMsg = null;

    	try {
    		updateCodes(newContract);
			newContract.setIdnSystem(getChangeSystemView().getIdn_active());

			HashMap<String, Object> params = new HashMap<>();
			params.put("closingTypeCode", "DEFINITIVE");
			params.put("idnSystem", getChangeSystemView().getIdn_active());
			params.put("sysCode", getChangeSystemView().getSystem());
			service.save(newContract, params);
    	}
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = ve.getMessage();
    		getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;
		} 	
    	catch(Exception e){
			summaryMsg = msgs.getString("saving_data_error");
        	errorMsg = msgs.getString("insert_error") + " " + msgs.getString("res_bal_con_contract") + " " +
					msgs.getString("with_id") + newContract.getContractCode();
    		getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}

    	summaryMsg = msgs.getString("res_bal_con_new_contract");
    	String okMsg = msgs.getString("res_bal_con_saved") + " " + newContract.getContractCode();    	
    	getMessages().addMessage(Constants.head_menu[9],
				new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
    	log.info(okMsg);

    	//Una vez guardado los datos, se resetea newContract. Si hubiera habido un error al guardar
    	//se mantienen los datos para que el usuario pueda corregir.
		createNewContract();
    	// Se actualiza la vista.
    	items = service.search(filters); 
    }
    
    // Se actualizan los codigos de puntos, para luego poder pintar un mensaje de error en el ServiceImpl.
    private void updateCodes(ReserveBalancingGasContractBean _contract){
    	String tmpPointCode = null;
    	
    	if(_contract.getDetails()!=null && _contract.getDetails().size()>0){
    		for(ReserveBalancingGasContractDetailBean det : _contract.getDetails()){
    			tmpPointCode = (String) allValidPoints.get(det.getPointId());
    			det.setPointCode(tmpPointCode);
    		}
    	}
    }

}
