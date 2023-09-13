package com.atos.views.tariff;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.DataFormat;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.MessageBean;
import com.atos.beans.UserBean;
import com.atos.beans.tariff.TariffChargeDetailBean;
import com.atos.beans.tariff.TariffChargeReportBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.tariff.TariffChargeReportFilter;
import com.atos.services.tariff.TariffChargeReportService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name = "tariffChargeReportView")
@ViewScoped
public class TariffChargeReportView extends CommonView implements Serializable {

	private static final long serialVersionUID = 3798530949273594690L;

	private static final Logger log = LogManager.getLogger(TariffChargeReportView.class);

	private TariffChargeReportFilter filters;
	private TariffChargeReportFilter auxFilters;
	private TariffChargeReportBean newRunTariff;
	private TariffChargeReportBean newBacCalc;
	private List<TariffChargeReportBean> items;
	private List<TariffChargeDetailBean> itemsDetail;
	private TariffChargeReportBean selected;
	
	private boolean allowEdit;
	private boolean editingInBlock;

	private boolean b_invoiceSent;
	public void setB_invoiceSent(boolean b_invoiceSent) {
		this.b_invoiceSent = b_invoiceSent;
	}
	
	private String comments;
	public void setComments(String comments) {
		this.comments = comments;
	}
	private String typeSelected = "";
	public String getTypeSelected() {
		return typeSelected;
	}

	public void setTypeSelected(String typeSelected) {
		this.typeSelected = typeSelected;
	}
	
	private String modeSelected = "";
	public String getModeSelected() {
		return modeSelected;
	}

	public void setModeSelected(String modeSelected) {
		this.modeSelected = modeSelected;
	}

	private Map<BigDecimal, Object> comboShipper;
	
	@ManagedProperty("#{tariffChargeReportService}")
	transient private TariffChargeReportService service;


	public TariffChargeReportFilter getAuxFilters() {
		return auxFilters;
	}

	public void setAuxFilters(TariffChargeReportFilter auxFilters) {
		this.auxFilters = auxFilters;
	}

	
	public TariffChargeReportFilter getFilters() {
		return filters;
	}

	public void setFilters(TariffChargeReportFilter filters) {
		this.filters = filters;
	}

	public TariffChargeReportBean getNewRunTariff() {
		return newRunTariff;
	}

	public void setNewRunTariff(TariffChargeReportBean newRunTariff) {
		this.newRunTariff = newRunTariff;
	}

	public TariffChargeReportBean getNewBacCalc() {
		return newBacCalc;
	}

	public void setNewBacCalc(TariffChargeReportBean newBacCalc) {
		this.newBacCalc = newBacCalc;
	}

	public List<TariffChargeReportBean> getItems() {
		return items;
	}

	public void setItems(List<TariffChargeReportBean> items) {
		this.items = items;
	}

	public void setService(TariffChargeReportService service) {
		this.service = service;
	}

	public TariffChargeReportBean getSelected() {
		return selected;
	}

	public List<TariffChargeDetailBean> getItemsDetail() {
		return itemsDetail;
	}

	public void setItemsDetail(List<TariffChargeDetailBean> itemsDetail) {
		this.itemsDetail = itemsDetail;
	}

	public void setSelected(TariffChargeReportBean selected) {
		this.selected = selected;
	}
	
	public boolean getIsShipper() {
		return getUser().isUser_type(Constants.SHIPPER);
	}
	
	public boolean getIsOperator() {
		return getUser().isUser_type(Constants.OPERATOR);
	}

	public boolean getAllowEdit() {
		return allowEdit;
	}

	public void setAllowEdit(boolean allowEdit) {
		this.allowEdit = allowEdit;
	}

	public boolean getEditingInBlock() {
		return editingInBlock;
	}

	public void setEditingInBlock(boolean editingInBlock) {
		this.editingInBlock = editingInBlock;
	}

	public String getColumnsShipperOperator() {
		if(super.isShipper()) {
			return "1";
		} else {
			return "2";
		}
	}

	@PostConstruct
	public void init() {
		
		filters = new TariffChargeReportFilter();
		newRunTariff = new TariffChargeReportBean();
		newBacCalc = new TariffChargeReportBean();
	    allowEdit = true;
	    editingInBlock = false;
		selected = new TariffChargeReportBean();

		comboShipper= service.selectShippers(filters);
		
		//si el usuario que ha entrado en la pantalla es un shipper,
		//el combo mostrara solo su id
		if (getUser().isUser_type(Constants.SHIPPER)) {
			filters.setIdn_user_group(getUser().getIdn_user_group());
			filters.setIsShipper(true);
		}
	
	}

	
	public Map<BigDecimal, Object> getShippers() {
		return comboShipper;
	}
	
	
	public Map<BigDecimal, Object> getTariffIdComboFiltro() {
		if (filters.getShortDate()!= null && filters.getIdn_user_group() != null){
			filters.setIdn_system(getChangeSystemView().getIdn_active());
			return service.selectTariffIdComboFiltro(filters);
		}
		else return null;
	}

	
	public Map<BigDecimal, Object> getTariffIdComboBac() {
		if (auxFilters!=null){
			if (auxFilters.getShortDate()!= null && auxFilters.getIdn_user_group() != null){
				return service.selectTariffIdComboBac(auxFilters);
			}
			else return null;	
		}else
			return null;	
	}
	
	public boolean compare() {
		BigDecimal id = service.getIdFromTariffCharge(getCodeCompareTarifCharge());
		if(service.compare(filters.getIdn_tariff_charge()) || service.compare2(id))
			return true;
		return false;
	}

	public boolean renderEditBlock() {
		return allowEdit && getItemsSize()>0;
	}
	
	// Methods
	public void onSearch() {

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_tariffChargeReport1').clearFilters()");
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		if (filters.getIdn_user_group() == null) {
			String strError = msgs.getString("tariffChargeReport_shipper_informed");
	    	getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
			
			return;
		}
		
		if (filters.getShortDate() == null) {
			String strError = msgs.getString("tariffChargeReport_DateMonthYear_informed");
			getMessages().addMessage(Constants.head_menu[10],
					new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
			return;
		}

		if (filters.getIdn_tariff_charge() == null) {
			String strError = msgs.getString("tariffChargeReport_tariff_charged_informed");
			getMessages().addMessage(Constants.head_menu[10],
					new MessageBean(Constants.ERROR, msgs.getString("filter_error"), strError, Calendar.getInstance().getTime()));
			return;
		}

		
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		try {
		items = service.selectTariffChargeReports(filters);
		} catch (Exception e) {
			String strError = msgs.getString("tariffChargeReport_ErrorWhileWSarchingResults");
			e.printStackTrace();
			
			getMessages().addMessage(Constants.head_menu[10], new MessageBean(Constants.ERROR, "Internal Error",
					"Error while searching results", Calendar.getInstance().getTime()));
		}
		if(items!=null && items.size()>0) {
			this.typeSelected = items.get(0).getParameter_value_criteria();
			this.modeSelected = items.get(0).getParameter_value_mode();
		}
		
		//nos guardamos los filtros que usaron para la busqueda
		auxFilters = new TariffChargeReportFilter();
		auxFilters.setIdn_user_group(filters.getIdn_user_group());
		auxFilters.setShortDate(filters.getShortDate());
		auxFilters.setIdn_system(filters.getIdn_system());
		
		if(filters.getIdn_tariff_charge()!=null){
			auxFilters.setIdn_tariff_charge(filters.getIdn_tariff_charge());	
		}
		
		auxFilters.setIdTariff(service.getCodeTarifCharge(auxFilters));
		
		 // En cada busqueda se resetea la fila seleccionada.
        selected = new TariffChargeReportBean();
        allowEdit = true;
        editingInBlock=false;
	}
	
	public void onEditInBlock() {
		editingInBlock = true;
		allowEdit = false;
	}
	
	@Transactional( rollbackFor = { Throwable.class })
	public void onSaveInBlock() {
		String error = "0";
		try {
			newBacCalc.setIdn_system(getChangeSystemView().getIdn_active());
			newBacCalc.setIdn_tariff_charge(filters.getIdn_tariff_charge());
			error =  service.getNewVersion(newBacCalc);
			
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		String summaryMsg = msgs.getString("tariffChargeReport_update_block");	
		String errorMsg = null;
		
		if(error!=null && error.equals("0")){
			for(int i=0; i<items.size(); i++) {
				if(items.get(i).getAmount_operator()!=null ||
						(items.get(i).getComments_charge_month()!=null && items.get(i).getComments_charge_month()!=""))
					try {
						newBacCalc.setAmount_operator(items.get(i).getAmount_operator());
						newBacCalc.setComments_charge_month(items.get(i).getComments_charge_month());
						newBacCalc.setIdn_tariff_code_detail(items.get(i).getIdn_tariff_code_detail());
						newBacCalc.setIdn_tariff_charge(newBacCalc.getIdn_tariff_charge_month()); //Se actualiza para que funcione bien el update. Aquí se ha guardado el nuevo generado
						newBacCalc.setIdn_contract(items.get(i).getIdn_contract());
						error = service.updateNewVersion(newBacCalc);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(error!=null && !error.equals("0"))
					{
						errorMsg = msgs.getString("tariffChargeReport_Error_update_block");
						getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
						log.info("Error Error calculating BAC. The " + newBacCalc.toString() + "error: " + newBacCalc.getError_desc(), Calendar.getInstance().getTime());
						break;//para salir del bucle
					}
			}
			errorMsg = msgs.getString("tariffChargeReport_update_block_ok");
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.INFO, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			log.info("BAC calculated ok" + newBacCalc.toString(), Calendar.getInstance().getTime());
		}else {
			errorMsg = msgs.getString("tariffChargeReport_Error_update_block");
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			log.info("Error Error calculating BAC. The " + newBacCalc.toString() + "error: " + newBacCalc.getError_desc(), Calendar.getInstance().getTime());
		}
    		
    	// buscamos el tariff charge generado nuevo y lo cargamos.
    	BigDecimal idn_tariff_charge_new= service.getIdnLastTarifCharge(auxFilters);
		filters.setIdn_tariff_charge(idn_tariff_charge_new);
		onSearch();
		getFooterBac();
		
    	//clean the formu new after calculated
    	newBacCalc = new TariffChargeReportBean(); 
		allowEdit = true;
	}
	
	public void onCancelInBlock() {
		allowEdit = true;
		editingInBlock = false;
	}

	public void onClear() {
		// RequestContext.getCurrentInstance().reset("form");
		filters = new TariffChargeReportFilter();
		auxFilters = new TariffChargeReportFilter();
		

		if (items != null) {
			items.clear();
		}

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_tariffChargeReport1').clearFilters()");
		selected = new TariffChargeReportBean();
		allowEdit = true;
	    editingInBlock = false;
	}

	public void cancelRunTariff() {
		initNewRunTariff();
	}
	
	public void cancelBacCalc() {
		initNewBacCalc();
	}
	
	public void cancelComment(){
		
	}
	
	public void onRowEdit(RowEditEvent event) {

		TariffChargeReportBean tariffChargeReport = (TariffChargeReportBean) event.getObject();
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		String[] params = {msgs.getString("tariffChargeReport")};
		String summaryMsgOk = getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk= getMessageResourceString("update_noOk", params);
		
		if (tariffChargeReport.getQuantity()==null && tariffChargeReport.getFee()==null &&  tariffChargeReport.getAmount()==null ){
			String strError = msgs.getString("tariffChargeReport_AtLeastOneInformed");
			getMessages().addMessage(Constants.head_menu[10],
					new MessageBean(Constants.ERROR, summaryMsgNotOk, strError, Calendar.getInstance().getTime()));
			return;
		}
		
		tariffChargeReport.setIdn_system(getChangeSystemView().getIdn_active());
		String error = "0";
		
		try {
			error = service.updateTariffChargeMonth(tariffChargeReport, auxFilters);

		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			error = e.getMessage();
		}
		
		String[] par2 = {tariffChargeReport.getDetail_desc(),msgs.getString("tariffChargeReport") };
		
		if (error != null && (error.equals("0")|| error.equals("10"))) {
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.INFO,summaryMsgOk, summaryMsgOk + " - " + tariffChargeReport.getDetail_desc(), Calendar.getInstance().getTime()));
			log.info("TariffChargeReport Updated", "TariffChargeReport Updated: " + tariffChargeReport.toString(),Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting insertTariffCharge. TariffChargeReport, Error: " + error + ". "	+ tariffChargeReport.toString(), Calendar.getInstance().getTime());

		} else if (error != null && error.equals("-2")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error Inserting  insertSelectTariffChargeMonth. TariffChargeReport, Error: " + error + ". "+ tariffChargeReport.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-3")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error Inserting  updateTariffChargeMonth. TariffChargeReport, Error: " + error + ". "	+ tariffChargeReport.toString(), Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-4")) {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error Inserting  insertTariffChargeMonth. TariffChargeReport, Error: " + error + ". "	+ tariffChargeReport.toString(), Calendar.getInstance().getTime());
		} else {
			String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error insertingg TariffChargeReport. TariffChargeReport, Error: " + error + ". "	+ tariffChargeReport.toString(), Calendar.getInstance().getTime());
		}

		//si hemos generado version la cargamos y buscamos
		if (error.equals("10")){
			// buscamos el tariff charge generado nuevo y lo cargamos.
	    	BigDecimal idn_tariff_charge_new= service.getIdnLastTarifCharge(auxFilters);
			filters.setIdn_tariff_charge(idn_tariff_charge_new);
			onSearch();
		}else{
			//items = service.selectTariffChargeReports(filters);
			onSearch();
		}
				
	
		// esto aqui no funciona se queda colgada la pantalla... puede ser por el
		// summary de la tabla
		// RequestContext requestContext = RequestContext.getCurrentInstance();
		// requestContext.execute("PF('tariffChargeReports1').filter()");

	}

	public void onRowCancel(RowEditEvent event) {
		TariffChargeReportBean tariffChargeReport = (TariffChargeReportBean) event.getObject();
		System.out.println(tariffChargeReport.toString());
	}

	public boolean renderItemEditor(TariffChargeReportBean item) {
		if(editingInBlock) //Para no activar el lápiz si se está editando de forma global
			return false;
		else if (!item.equals(null)) {
			if (item.getIs_quantity_user_filled() != null && item.getIs_quantity_user_filled().equals("Y")) {
				return true;
			} else if (item.getIs_fee_user_filled() != null && item.getIs_fee_user_filled().equals("Y")) {
				return true;
			} else if (item.getIs_amount_user_filled() != null && item.getIs_amount_user_filled().equals("Y")) {
				return true;
			}
		}
		return false;
	}
	public boolean renderSeeDatil(TariffChargeReportBean item) {

		if (!item.equals(null)) {
			
			if(item.getTariff_code().equals("BAC.CHARGE") || item.getTariff_code().equals("DAMAGE.CHARGE")){
				return false;
			}
			
			if(item.getDetail_code().equals("IMBALANCE.PENALTY.POSITIVE") || item.getDetail_code().equals("IMBALANCE.PENALTY.NEGATIVE") ){
				return false;
			}
			
			/*if(item.getDetail_desc().equals("- Absolute Value of Sum of Adjusted Daily Imbalance (Positive)") 
			|| item.getTariff_desc().equals("- Absolute Value of Sum of Adjusted Daily Imbalance (Negative)") ){
				return false;
			}*/
		}
		return true;
	}
	
	public String disabledSeeDatil(TariffChargeReportBean item) {

		if (!item.equals(null)) {
			
			if(item.getTariff_code().equals("BAC.CHARGE") || item.getTariff_code().equals("DAMAGE.CHARGE")){
				return "true";
			}
			
			if(item.getTariff_desc().equals("IMBALANCE.PENALTY.POSITIVE") ||item.getTariff_desc().equals("IMBALANCE.PENALTY.POSITIVE") ){
				return "true";
			}
		}
		return "false";
	}

	public String disabledField(TariffChargeReportBean item) {

		if (!item.equals(null)) {

			if (item.getIs_quantity_user_filled() != null && item.getIs_quantity_user_filled().equals("Y")) {
				return "false";
			}

			if (item.getIs_fee_user_filled() != null && item.getIs_fee_user_filled().equals("Y")) {
				return "false";
			}

			if (item.getIs_amount_user_filled() != null && item.getIs_amount_user_filled().equals("Y")) {
				return "false";
			}

		}
		return "true";

	}

	public BigDecimal getTotalAmount() {
		BigDecimal total = new BigDecimal(0);
		for (int i = 0; i < items.size(); i++) {
			//if (items.get(i).getAmount() != null && !items.get(i).getDetail_desc().equals(Constants.Imbalance_Penalty_Charge) ) {
			//CH721_Fase III OFFSHORE: Tariff - Total de factura sin BAC
			//if (items.get(i).getAmount() != null && (!items.get(i).getDetail_desc().equals(Constants.Imbalance_Penalty_Charge) &&  !items.get(i).getDetail_desc().equals(Constants.Bac_Adjustment_charge))) {
			//	total = total.add(items.get(i).getAmount());
			//}
			
			if (items.get(i).getAmount_operator() != null && (!items.get(i).getDetail_code().equals("IMBALANCE.PENALTY.TOTAL") 
					 &&  !items.get(i).getDetail_code().equals("BAC.TOTAL"))) {
				total = total.add(items.get(i).getAmount_operator());
			} else if (items.get(i).getAmount() != null && (!items.get(i).getDetail_code().equals("IMBALANCE.PENALTY.TOTAL") 
						 &&  !items.get(i).getDetail_code().equals("BAC.TOTAL"))) {
				total = total.add(items.get(i).getAmount());
			}
		}
		return total;
	}
	
	
	
	public BigDecimal getTotalAmountCompare() {
		BigDecimal totalComp = new BigDecimal(0);
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getAmount_compare() != null && !items.get(i).getDetail_desc().equals(Constants.Imbalance_Penalty_Charge)) {
				totalComp = totalComp.add(items.get(i).getAmount_compare());
			}
		}
		return totalComp;
	}

	//CH721
	public BigDecimal getFooterBac() {
		BigDecimal total = new BigDecimal(0);
		
		total= service.getBacAcmount(filters);
		
		return total;
	}
	
	
	/********************************************************************************
	 * RUN TARIFF
	 *******************************************************************************/
	
	public void initNewRunTariff() {
		newRunTariff = new TariffChargeReportBean();
	}
	
	public void initNewBacCalc() {
		newBacCalc = new TariffChargeReportBean();
	}
	
	public void runTariff() {

		newRunTariff.setIdn_system(getChangeSystemView().getIdn_active());
		//obtenemos el pipeline syustem code para que runtariff se lo pase a las notificaciones
		newRunTariff.setPipeline_system_code(getChangeSystemView().getSystem());
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		String summaryMsgNotOk = msgs.getString("tariffChargeReport_RunTariffNoEjecuted");
		
		if (newRunTariff.getRunTariffDate() == null) {
			
			String strError = msgs.getString("tariffChargeReport_DateMonthYearRunTariffInformed");
			//getMessages().addMessage(Constants.head_menu[10], new MessageBean(Constants.ERROR, "Error dates",	"The Date Month/Year Charge in Run Tariff must be informed.", Calendar.getInstance().getTime()));
			
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR, summaryMsgNotOk, strError, Calendar.getInstance().getTime()));
			return;
		}

		if (newRunTariff.getIdn_user_group() == null) {
			String strError = msgs.getString("tariffChargeReport_shipper_informed");
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR, summaryMsgNotOk, strError, Calendar.getInstance().getTime()));
			return;
		}

		/*
		 * // El mes/año debe deben pertenecer al periodo con balance cerrado.
		 * // En caso contrario se mostrará el siguiente mensaje de error: //
		 * ‘Month/Year should be a balance closed period. if
		 * (newRunTariff.getRunTariffDate() != null) { if
		 * (newRunTariff.getRunTariffDate().after(firstOpenDay.getTime())) {
		 * messages.addMessage(Constants.head_menu[10], new
		 * MessageBean(Constants.ERROR, "TariffChargeReport Not Inserted",
		 * "Month/Year should be a  balance closed period. ",
		 * Calendar.getInstance().getTime())); return; } }
		 * 
		 */
		
		/*
		 * El mes/año debe deben pertenecer al mes que tenga repartos del
		 * shipper seleccionado para todos los días (es suficicente comprobar si
		 * tiene repartos para el último día del mes). En caso contrario se
		 * mostrará el siguiente mensaje de error: ‘Month/Year is incorrect.
		 * Allocation data is needed for all gas days
		 */
		 
		int res;
    	if (newRunTariff.getIdn_user_group()!=null && newRunTariff.getRunTariffDate()!=null){
    		
    		BigDecimal bgRepartos=service.getRepartosShipperDay(newRunTariff);
    		res = bgRepartos.compareTo(new BigDecimal(0));
        	//  0 "Both values are equal ";  1 "First Value is greater ";  -1 Second value is greater"
        	 
        	if( res != 1 ){
       		String strError = msgs.getString("tariffChargeReport_AllocationNeeded");
        		getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR,summaryMsgNotOk,strError, Calendar.getInstance().getTime()));
        		initNewRunTariff();
        		return;
        	}	
    	}
		
		String summaryMsg = msgs.getString("tariffChargeReport_execution_dialog_header");									
		String errorMsg = null;

		try {
			
			service.ejecRunTariff(newRunTariff,getUser());

			String okMsg = msgs.getString("tariffChargeReport_processing");
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
			log.info(okMsg);
		} catch (ValidationException ve) {
			errorMsg = ve.getMessage();
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
		} catch (Exception e) {
			errorMsg = msgs.getString("internal_error");
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			// Se guarda el detalle del error tecnico.
			log.error(e.getMessage(), e);
		}

		initNewRunTariff();

	}

		
			
	/********************************************************************************
	 * B.A.C.
	 *******************************************************************************/
	public void datosBac(){
		/* cesitamos para la llamada al procecimiento de calculo
		 * 
		 *  #{bac_idn_user_group,jdbcType=NUMERIC,mode=IN},
		 * 	#{bac_shortDate,jdbcType=DATE,mode=IN},
			#{bac_idn_tariff_charge_last,jdbcType=NUMERIC,mode=IN},
			#{bac_idn_tariff_charge_comp,jdbcType=NUMERIC,mode=IN},
				
		 */
		
		
		if (auxFilters.getIdn_user_group() != null){
			newBacCalc.setBac_idn_user_group(auxFilters.getIdn_user_group());
		}
		
		if (auxFilters.getShortDate() != null){
			newBacCalc.setBac_shortDate(auxFilters.getShortDate());	
		}

		BigDecimal valor =service.getIdnLastTarifCharge(auxFilters);
		if (valor!= null){
			newBacCalc.setBac_idn_tariff_charge_last(valor);
		}
	
			
		//para la pantalla
		String svalor = service.getCodeLastTarifCharge(auxFilters);
		if (svalor!= null){
			newBacCalc.setBac_code_tariff_charge_last(svalor);
		}
		
		svalor =service.getShipperGroupID(auxFilters);
		if (svalor!= null){
			newBacCalc.setBac_user_group_id(svalor);
		}
		
	}	
	
	
	public void bacCalc() {
		 String error = "0";
			try {
				newBacCalc.setIdn_system(getChangeSystemView().getIdn_active());
				error =  service.ejecBacCalc(newBacCalc);
				
			} catch(Exception e) {
				log.catching(e);
				// we assign the return message 
				error = e.getMessage();
			}
			
			ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
			String summaryMsg = msgs.getString("tariffChargeReport_BAC_calc");	
			String errorMsg = null;
			
			if(error!=null && error.equals("0")){
				errorMsg = msgs.getString("tariffChargeReport_BAC_calculated_ok");
				getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.INFO, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
				log.info("BAC calculated ok" + newBacCalc.toString(), Calendar.getInstance().getTime());
			}else {
				errorMsg = msgs.getString("tariffChargeReport_Error_calculating_BAC");
				getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
				log.info("Error Error calculating BAC. The " + newBacCalc.toString() + "error: " + newBacCalc.getError_desc(), Calendar.getInstance().getTime());
			}
	    		
	    	// buscamos el tariff charge generado nuevo y lo cargamos.
	    	BigDecimal idn_tariff_charge_new= service.getIdnLastTarifCharge(auxFilters);
			filters.setIdn_tariff_charge(idn_tariff_charge_new);
			onSearch();
			getFooterBac();
			
	    	//clean the formu new after calculated
	    	newBacCalc = new TariffChargeReportBean(); 	
		 }
	 
	
	public String renderInvoiceSent(){
		 if(items!= null  && items.size()>0){
			 return "false";
		 }else
			 return "true";
	}
	
	public String renderBacCalc(){
		int res;
		if (auxFilters != null){
			if (auxFilters.getIdn_user_group()!=null && auxFilters.getShortDate()!=null){
	    		
	    		auxFilters.setIdn_system(getChangeSystemView().getIdn_active());
	    		BigDecimal cont=service.renderBacCal(auxFilters);
	    		res = cont.compareTo(new BigDecimal(1));
	        	//  0 "Both values are equal ";  1 "First Value is greater ";  -1 Second value is greater"
	        	 
	        	if( res == 1 ){
	        		return "false";
	        	}else{
	        		return "true";
	        	}	
	    	}
		}
    	
    	return "true";
		
	}
	
	
	/********************************************************************************
	 *DETAIL
	 *******************************************************************************/
	public void seeDetail(TariffChargeReportBean item){
	 
	   String idShipper =service.getShipperGroupID(selected);
	   
	   item.setBac_user_group_id(idShipper);
	   item.setIdn_system(getChangeSystemView().getIdn_active());
	   
		if(item.getTariff_code().equals(Constants.CAPACITY_CHARGE)){
			itemsDetail = service.selectDetailCapacityCharge(item);
		}else if (item.getTariff_code().equals(Constants.COMMODITY_CHARGE) && item.getType_code().equals(Constants.ENTRY) ){
			itemsDetail = service.selectDetailComodityChargeEntry(item);
		}else if (item.getTariff_code().equals(Constants.COMMODITY_CHARGE) && item.getType_code().equals(Constants.EXIT) ){
			itemsDetail = service.selectDetailComodityChargeExit(item);
			
		}else if (item.getTariff_code().equals(Constants.OVERUSAGE_CHARGE) && item.getType_code().equals(Constants.ENTRY)){
			itemsDetail = service.selectDetailOverUsageChargeEntry(item);
		}else if (item.getTariff_code().equals(Constants.OVERUSAGE_CHARGE) && item.getType_code().equals(Constants.EXIT)){
			itemsDetail = service.selectDetailOverUsageChargeExit(item);	
		}else if (item.getTariff_code().equals(Constants.IMBALANCE_PENALTY_CHARGE)){
			itemsDetail = service.selectDetailImbalancePenalty(item);
		}
	}
	

	public boolean renderColumnDetail(TariffChargeReportBean item, int columna) {

		if (item !=null && item.getTariff_code() != null) {
			
			
			if ( (columna == 1 || columna == 2)  && item.getTariff_code().equals(Constants.CAPACITY_CHARGE)) {
				return true;
			
			} else if (columna == 31  && item.getTariff_code().equals(Constants.COMMODITY_CHARGE) && item.getType_code().equals(Constants.EXIT)) {
				return true;
			
			} else if (columna == 32 && item.getTariff_code().equals(Constants.COMMODITY_CHARGE) && item.getType_code().equals(Constants.ENTRY )) {
				return true;
				
			} else if ( columna==4 && item.getTariff_code().equals(Constants.COMMODITY_CHARGE)) {
				return true;
				
			}else if ((columna == 5 ||columna==6 || columna==7) && item.getTariff_code().equals(Constants.OVERUSAGE_CHARGE) && item.getType_code().equals(Constants.ENTRY)) {
				return true;
			
			}else if ( (columna == 8 || columna==9 ||columna==10)&& item.getTariff_code().equals(Constants.OVERUSAGE_CHARGE) && item.getType_code().equals(Constants.EXIT)) {
				return true;	

			}else if ((columna == 11||columna==12 ||columna==13) && item.getTariff_code().equals(Constants.IMBALANCE_PENALTY_CHARGE)) {
				return true;	
				
			}else
				return false;
		}
		return false;
		
	}
	
	
	public boolean renderColumnDetail_old(TariffChargeReportBean item, int columna) {

		if (item !=null) {
			
			if (item.getTariff_code() != null && columna == 1 && item.getTariff_code().equals(Constants.CAPACITY_CHARGE)) {
				return true;
			} else if (item.getTariff_code() != null && columna == 2 && item.getTariff_code().equals(Constants.CAPACITY_CHARGE)) {
				return true;
			} else if (item.getTariff_code() != null && columna == 3 && item.getTariff_code().equals(Constants.COMMODITY_CHARGE)) {
				return true;
			}else if (item.getTariff_code() != null && columna == 4 && item.getTariff_code().equals(Constants.COMMODITY_CHARGE)) {
				return true;
			}else if (item.getTariff_code() != null && columna == 5 && item.getTariff_code().equals(Constants.OVERUSAGE_CHARGE) && item.getType_code().equals(Constants.ENTRY)) {
				return true;
			}else if (item.getTariff_code() != null && columna == 6 && item.getTariff_code().equals(Constants.OVERUSAGE_CHARGE) && item.getType_code().equals(Constants.ENTRY)) {
				return true;
			}else if (item.getTariff_code() != null && columna == 7 && item.getTariff_code().equals(Constants.OVERUSAGE_CHARGE) && item.getType_code().equals(Constants.ENTRY)) {
				return true;
			}else if (item.getTariff_code() != null && columna == 8 && item.getTariff_code().equals(Constants.OVERUSAGE_CHARGE) && item.getType_code().equals(Constants.EXIT)) {
				return true;	
			}else if (item.getTariff_code() != null && columna == 9 && item.getTariff_code().equals(Constants.OVERUSAGE_CHARGE) && item.getType_code().equals(Constants.EXIT)) {
				return true;	
			}else if (item.getTariff_code() != null && columna == 10 && item.getTariff_code().equals(Constants.OVERUSAGE_CHARGE) && item.getType_code().equals(Constants.EXIT)) {
				return true;	
			}else if (item.getTariff_code() != null && columna == 11 && item.getTariff_code().equals(Constants.IMBALANCE_PENALTY_CHARGE)) {
				return true;	
			}else if (item.getTariff_code() != null && columna == 12 && item.getTariff_code().equals(Constants.IMBALANCE_PENALTY_CHARGE)) {
				return true;
			//CH711 SAT FASE III 
			}else if (item.getTariff_code() != null && columna == 13 && item.getTariff_code().equals(Constants.IMBALANCE_PENALTY_CHARGE)) {
				return true;
				
			}else
				return false;
		}
		return false;
		
	}

	/********************************************************************************
	 * INVOICE SENT Y Tariff ID (Compare) 
	 *******************************************************************************/
	public String getCodeCompareTarifCharge(){
		 return service.getCodeCompareTarifCharge(auxFilters);
	 }
	
	public boolean isB_invoiceSent() {
		
	String result= service.getInvoiceSent(auxFilters);

		if (result !=null){
			if(result.equals("Y") ){
				 this.setB_invoiceSent(true);
			}
			if(result.equals("N")){
				this.setB_invoiceSent(false);
			}
		}
			  
		return b_invoiceSent;
	}

	public void invoiceSentUpdate() {
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		String summaryMsg = msgs.getString("tariffChargeReport_Invoice_Sent");	
		String errorMsg = null;
		
	      String svalor = b_invoiceSent ? "Y" : "N";
	      auxFilters.setIs_invoice_sent(svalor);
	  
	      String dateString= null;
	      
	      String idShipper =service.getShipperGroupID(auxFilters);  
	   //   String idTariff= service.getCodeTarifCharge(auxFilters);
	      
	     String error = "0";
	     String errorUpdateFalse = "0";
	     TariffChargeReportFilter filter = new TariffChargeReportFilter();
	    		try {

	    			DateFormat df = new SimpleDateFormat("MMMM/yyyy");
		            dateString = df.format(auxFilters.getShortDate());
		            if(svalor.equals("Y"))
		            {
		            	
		            	filter.setIdn_system(auxFilters.getIdn_system());
		            	filter.setIdn_user_group(auxFilters.getIdn_user_group());
		            	filter.setShortDate(auxFilters.getShortDate());
		            	errorUpdateFalse = service.invoiceSentFalseUpdate(filter);
		            }
		            if(errorUpdateFalse.equals("0"))
		            	error = service.invoiceSentUpdate(auxFilters);
		            else {
		            	errorMsg = msgs.getString("tariffChargeReport_Invoice_SentNook");
						getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR, summaryMsg, errorMsg + "Id User:" + filter.getIdn_user_group() + " .Date: "+ filter.getShortDate(), Calendar.getInstance().getTime()));
		        		log.info("Error updated FALSE Invoice Sent. Error updated FALSE Invoice Sent: "  + filter.toString(), Calendar.getInstance().getTime());
		            }
	    		} catch(Exception e) {
	    			log.catching(e);
	    			// we assign the return message 
	    			error = e.getMessage();
	    		}
	    		

				
				
				
	    		if(error!=null && error.equals("0")){
	    			errorMsg = msgs.getString("tariffChargeReport_Invoice_Sentok");
					getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.INFO, summaryMsg, errorMsg + "Id User:" + idShipper + " .Date: "+ dateString +" .TarifCharge: "+ auxFilters.getIdTariff() , Calendar.getInstance().getTime()));
	    			log.info("Updated Invoice Sent ok. " + auxFilters.toString(), Calendar.getInstance().getTime());
	    		
	    		}else if(error!=null && error.equals("-1")){
	        		errorMsg = msgs.getString("tariffChargeReport_Invoice_SentNook");
					getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR, summaryMsg, errorMsg + "Id User:" + idShipper + " .Date: "+ dateString +" .TarifCharge: "+ auxFilters.getIdTariff(), Calendar.getInstance().getTime()));
	        		log.info("Error  updated Invoice Sent. Error updated Invoice Sent: "  + auxFilters.toString(), Calendar.getInstance().getTime());
	    		}
	        
	    }
	
	/********************************************************************************
	 * OFF SPEC
	 *******************************************************************************/
	public String getOffSpec(){
		String valor = service.getOffSpec(auxFilters);
		if(valor!= null){
			if (valor.equals("1")){
				valor = "Offspec issue(s) registered for the month."; 
			}else{ 
				valor=" ";
			}
		}
		 return valor;
	 }

	
	/**********************************
	 * EXCEL
	 ***********************************/

	/**
	 * Create a style on base workbook
	 * 
	 * @param font            Font used by the style
	 * @param cellAlign       Cell alignment for contained text (see {@link HSSFCellStyle})
	 * @param cellColor       Cell background color (see {@link HSSFColor})
	 * @param cellBorder      Cell has border (<code>true</code>) or not (<code>false</code>)
	 * @param cellBorderColor Cell border color (see {@link HSSFColor})
	 * 
	 * @return New cell style
	 */
	
	private HSSFWorkbook wb;
	// Fonts
	private HSSFFont headerFont;
	private HSSFFont contentFont;
	private HSSFFont totalFont;
	// Styles
	private HSSFCellStyle cellStyleHeader;
	private HSSFCellStyle cellStyleHide;
	
	private HSSFCellStyle cellStyleTwoeDec; 
	private HSSFCellStyle cellStyleThreeDec; 
	private HSSFCellStyle cellStyleFourDec;
	private HSSFCellStyle cellStylZeroDec;
	
	private HSSFCellStyle cellStyleText;
	private HSSFCellStyle cellStyleTotal;
	private HSSFCellStyle cellStyleTotalTwoeDec; 
	private HSSFCellStyle cellStyleTotalThreeDec; 
	
	
	
	/**
	 * Create a new font on base workbook
	 * 
	 * @param fontColor       Font color (see {@link HSSFColor})
	 * @param fontHeight      Font height in points
	 * @param fontBold        Font is boldweight (<code>true</code>) or not (<code>false</code>)
	 * 
	 * @return New cell style
	 */
	private HSSFFont createFont(short fontColor, short fontHeight, boolean fontBold) {
 
		HSSFFont font = wb.createFont();
		font.setBold(fontBold);
		font.setColor(fontColor);
		font.setFontName("Calibri");
		font.setFontHeightInPoints(fontHeight);
 
		return font;
	}
	private HSSFCellStyle createStyle(HSSFFont font, short cellAlign, short cellColor, boolean cellBorder, short cellBorderColor, short border, 
			boolean numberFormat,short dataFormat) {
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(cellAlign);
		style.setFillForegroundColor(cellColor);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
 
		if (cellBorder) {
			style.setBorderTop(border);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setBorderBottom(border);
 
			style.setTopBorderColor(cellBorderColor);
			style.setLeftBorderColor(cellBorderColor);
			style.setRightBorderColor(cellBorderColor);
			style.setBottomBorderColor(cellBorderColor);
		}
		
		 if(numberFormat){
			 style.setDataFormat(dataFormat);
		 }
	  
		
		
		return style;
	}
	
	public void postProcessXLS2(Object document) {
		
		wb = new HSSFWorkbook();
		wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		
		wb.setSheetName(0, "TariffChargeReport");		
				
		// Generate fonts 
		//headerFont  = createFont(HSSFColor.WHITE.index, (short)12, true);
		headerFont  = createFont(HSSFColor.BLACK.index, (short)12, true);
		contentFont = createFont(HSSFColor.BLACK.index, (short)10, false);
		totalFont = createFont(HSSFColor.BLACK.index, (short)12, true);
		
		
		// Generate styles
		DataFormat format = wb.createDataFormat();
		
		cellStyleHeader  =  createStyle(headerFont,  HSSFCellStyle.ALIGN_CENTER, HSSFColor.GREY_40_PERCENT.index, true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		cellStyleHide  =    createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_HAIR, false, format.getFormat("#,##"));
		cellStyleText  =    createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##"));
		cellStyleTotal  =    createStyle(totalFont,  HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		cellStyleTwoeDec =  createStyle(contentFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.00"));
		cellStyleThreeDec = createStyle(contentFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.000"));
		cellStyleFourDec =  createStyle(contentFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.0000"));
		
		cellStyleTotalTwoeDec =  createStyle(totalFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,        true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, true, format.getFormat("#,##0.00"));
		cellStylZeroDec  =        createStyle(contentFont, HSSFCellStyle.ALIGN_RIGHT,   HSSFColor.WHITE.index,       true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##"));

		
		//CABECERA: quitamos el see datail
		for (int i = 0; i < header.getPhysicalNumberOfCells() - 1; i++) {
			HSSFCell cell = header.getCell(i);
			cell.setCellStyle(cellStyleHeader);
			if (i == 12) {
				cell.setCellStyle(cellStyleHide);
				cell.setCellValue(" ");
			}
			
		}
		//ancho de la cabecera
		header.setHeight((short) 0x249);
		
		// el total
		double total = 0;
		double totalAmount = 0;
		
		
		// Para convertir las cantidades string en double.
	    NumberFormat nf = NumberFormat.getInstance(new Locale(getLanguage().getLocale()));
	    
		//DETALLE	
	     for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				for (int j = 0; j <= sheet.getRow(i).getPhysicalNumberOfCells() - 1; j++) {
					
					HSSFCell cell = sheet.getRow(i).getCell(j);
					cell.setCellStyle(cellStyleText);
					String tmpStrValue = cell.getStringCellValue();

					//quitamos el caracter de las celdas grises
					if(tmpStrValue.equals("_")){
						tmpStrValue="";
						cell.setCellValue(tmpStrValue);
					}
					
					if(j==4 ||j==6 || j==7 || j==8 || j==9 || j==10 || j==11){
						
						try {
							if (tmpStrValue=="")
		   						cell.setCellValue(tmpStrValue);
		   					else
		   						cell.setCellValue(nf.parse(tmpStrValue).doubleValue());
						}
		           			catch(Exception e){
		           				log.catching(e);
	           			}	
					}
					
					
					if (j == 4) {
						//SAT CH524 cell.setCellStyle(cellStyleThreeDec);
						cell.setCellStyle(cellStyleThreeDec);
					}
					if (j == 6) {
						cell.setCellStyle(cellStylZeroDec);
					}
					
					if (j == 7) {
						cell.setCellStyle(cellStyleFourDec);
					}
					if (j == 8) {
						cell.setCellStyle(cellStyleTwoeDec);
						//if(items.get(i - 1).getAmount()!=null){
						  if(items.get(i - 1).getAmount()!=null && !items.get(i-1).getDetail_desc().equals(Constants.Imbalance_Penalty_Charge)) {
							total = total + items.get(i - 1).getAmount().doubleValue();
						}
					}
					if (j == 9) {
						cell.setCellStyle(cellStyleTwoeDec);
					}					
					if (j == 10) {
						cell.setCellStyle(cellStyleTwoeDec);
	       				
						//if (items.get(i - 1).getAmount_compare()!=null){
						  if (items.get(i - 1).getAmount_compare() != null && !items.get(i-1).getDetail_desc().equals(Constants.Imbalance_Penalty_Charge)) {
							totalAmount = totalAmount + items.get(i - 1).getAmount_compare().doubleValue();	
							
						}
					}
					if (j == 11) {
						cell.setCellStyle(cellStyleTwoeDec);
					}					
					// el SEE DETAIL
					if (j == 12) {
						cell.setCellStyle(cellStyleHide);
						cell.setCellValue(" ");
					}
					// el lapiz
					if (j == 14) {
						cell.setCellStyle(cellStyleHide);
						cell.setCellValue(" ");
					}
					
				}//for (int j = 0; j <= sheet.getRow(i).getPhysicalNumberOfCells() - 1; j++) {
				
				for (int k = 0; k < sheet.getRow(i).getPhysicalNumberOfCells() - 1; k++) {
					sheet.autoSizeColumn(k);
				}
				
	     } //for (int i = 1; i < sheet.getPhysicalNumberOfRows()
		
	   //totales
			int newRow = sheet.getPhysicalNumberOfRows();
			sheet.createRow(newRow);

			HSSFCell cell = sheet.getRow(newRow).createCell(7);
			cell.setCellValue("Total:");
			cell.setCellStyle(cellStyleTotal);

			cell = sheet.getRow(newRow).createCell(8);
			cell.setCellValue(total);		
			cell.setCellStyle(cellStyleTotalTwoeDec);
	
			cell = sheet.getRow(newRow).createCell(10);	
			cell.setCellValue(totalAmount);
			cell.setCellStyle(cellStyleTotalTwoeDec);
			
			//CH721
			int newRowFoot = sheet.getPhysicalNumberOfRows();
			sheet.createRow(newRowFoot);
			cell = sheet.getRow(newRowFoot).createCell(0);
			cell.setCellValue("Balancing Adjustment Charge (BAC) :");
			cell.setCellStyle(cellStyleTotal);
			
			cell = sheet.getRow(newRowFoot).createCell(1);
			
			//B003	CH743
			BigDecimal totalBac= null;
			totalBac = service.getBacAcmount(filters);
			if (totalBac!=null){
				cell.setCellValue(totalBac.doubleValue());	
				cell.setCellStyle(cellStyleTotalTwoeDec);
			}
					
			
			sheet.autoSizeColumn(0);
	}
	
	public void detailProcessXLS2(Object document) {
		wb = new HSSFWorkbook();
		wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		wb.setSheetName(0, "DetailChargeReport");	
		
		
		// Generate fonts
		headerFont  = createFont(HSSFColor.BLACK.index, (short)12, true);
		contentFont = createFont(HSSFColor.BLACK.index, (short)10, false);
		totalFont = createFont(HSSFColor.BLACK.index, (short)12, true);
		
		
		// Generate styles
		DataFormat format = wb.createDataFormat();
		
		cellStyleHeader  =  createStyle(headerFont,  HSSFCellStyle.ALIGN_CENTER, HSSFColor.GREY_40_PERCENT.index, true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		cellStyleText  =    createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##"));
		cellStyleTwoeDec =  createStyle(contentFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.00"));
		cellStyleThreeDec = createStyle(contentFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.000"));
		
		//CABECERA: 
		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			HSSFCell cell = header.getCell(i);
			cell.setCellStyle(cellStyleHeader);
			if(sheet.getRow(i)==null) {
				continue;
			}
			for (int k = 0; k < sheet.getRow(i).getPhysicalNumberOfCells(); k++) {
				sheet.autoSizeColumn(k);
			}
			
		}
		
		
		// Para convertir las cantidades string en double.
	    NumberFormat nf = NumberFormat.getInstance(new Locale(getLanguage().getLocale()));
	    
		//DETALLE	
	     for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				for (int j = 0; j <= sheet.getRow(i).getPhysicalNumberOfCells() - 1; j++) {
					
					HSSFCell cell = sheet.getRow(i).getCell(j);
					
					
				if (j == 0) {
						cell.setCellStyle(cellStyleText);
					}
							
					String tmpStrValue = cell.getStringCellValue();
					
       			if(j==1){
       				//SAT CH524 cell.setCellStyle(cellStyleThreeDec);
       				//cell.setCellStyle(cellStyleTwoeDec);
       				cell.setCellStyle(cellStyleThreeDec);
       				try {
           				cell.setCellValue(nf.parse(tmpStrValue).doubleValue());
           			}
           			catch(Exception e){
           				log.catching(e);
           			}
       			}else if (j==2){
       				/*// SAT CH522 cell.setCellStyle(cellStyleTwoeDec);
       				if (selected.getTariff_code().equals(Constants.OVERUSAGE_CHARGE)&& (selected.getType_code().equals(Constants.ENTRY))) {
       				    //SAT CH524 cell.setCellStyle(cellStyleThreeDec);
       					cell.setCellStyle(cellStyleTwoeDec);	
       				}else{
       					cell.setCellStyle(cellStyleTwoeDec);
       				}*/
       				if (selected.getTariff_code().equals(Constants.CAPACITY_CHARGE) || selected.getTariff_code().equals(Constants.COMMODITY_CHARGE)) {
       					cell.setCellStyle(cellStyleTwoeDec);
       				} else {
       					cell.setCellStyle(cellStyleThreeDec);
       				}
       				try {
           				cell.setCellValue(nf.parse(tmpStrValue).doubleValue());
           			}
           			catch(Exception e){
           				log.catching(e);
           			}
       			}else if (j==3){
       				cell.setCellStyle(cellStyleTwoeDec);
       				try {
           				cell.setCellValue(nf.parse(tmpStrValue).doubleValue());
           			}
           			catch(Exception e){
           				log.catching(e);
           			}
       			}
       			
				}
				
	     }//for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++)
		
	}

	
 public String getComments(){ 
		 return service.getComments(auxFilters);
 }	
 public String getIdTariff(){ 
	 return service.getCodeTarifCharge(auxFilters);
}
 public void updateComment(){
    
      auxFilters.setComments(comments);
   
      String dateString= null;
      
      String idShipper =service.getShipperGroupID(auxFilters);
     // String idTariff= service.getCodeTarifCharge(auxFilters);
      
     	String error = "0";
    		try {

    			DateFormat df = new SimpleDateFormat("MMMM/yyyy");
	            dateString = df.format(auxFilters.getShortDate());
	        	
				error = service.updateTariffCharge(auxFilters);
    			
    		} catch(Exception e) {
    			log.catching(e);
    			// we assign the return message 
    			error = e.getMessage();
    		}
    		if(error!=null && error.equals("0")){
    			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.INFO,"Comment", "Commnent Updated ok. Id User:" + idShipper + ". Date: "+ dateString +". TarifCharge: "+ auxFilters.getIdTariff(), Calendar.getInstance().getTime()));
    			log.info("Updated Invoice Sent ok" + filters.toString(), Calendar.getInstance().getTime());
    		
    		}else if(error!=null && error.equals("-1")){
        		getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR,"Comment Not  updated", "Error updated Comment. Error updated Comment. Id Shipper: "  +idShipper + ". Date: "+ dateString + ". TarifCharge: "+auxFilters.getIdTariff(), Calendar.getInstance().getTime()));
        		log.info("Error  updated Invoice Sent. Error updated Invoice Sent"  + filters.toString(), Calendar.getInstance().getTime());
    		}
       // }

	
 }
 
 
 public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
 	
 
	public Map<String, Object> getTypeCriteria() {
		return service.selectTypeCriteria();
	}
	
	public Map<String, Object> getModeCriteria() {
		return service.selectModeCriteria();
	}
	
	public void selectTypeUpdate() {
		
		if(typeSelected==null || typeSelected.equals("")) {
    		getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR,"Type selected not updated", "You must select a value in Select Type", Calendar.getInstance().getTime()));
    		log.info("Type selected not updated, you must select a value in Select Type", Calendar.getInstance().getTime());
			return;
		}
		
		if(filters.getIdn_tariff_charge()!=null) {
			HashMap<String,Object> map = new HashMap<String,Object>();
			
			
			map.put("idn_tariff_charge", filters.getIdn_tariff_charge());
			map.put("parameter_value", typeSelected);
			int result = service.insertTypeSelected(map);
			if(result==0) {
	    		getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.INFO,"Type selected updated", "Type selected updated", Calendar.getInstance().getTime()));
	    		log.info("Type selected updated", Calendar.getInstance().getTime());
			} else {
	    		getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR,"Type selected not updated", "Type selected not updated", Calendar.getInstance().getTime()));
	    		log.info("Type selected not updated", Calendar.getInstance().getTime());
			}
			

		} else {
    		getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR,"Can not update type selected", "Can not update type selected, Tariff ID not selected", Calendar.getInstance().getTime()));
    		log.info("Can not update type selected, Tariff ID not selected", Calendar.getInstance().getTime());
			this.typeSelected="";
			return;
		}
		
		onSearch();
	}
	
	public void selectModeUpdate() {
		
		if(modeSelected==null || modeSelected.equals("")) {
    		getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR,"Mode selected not updated", "You must select a value in Select Type (Mode)", Calendar.getInstance().getTime()));
    		log.info("Mode selected not updated, you must select a value in Select Type (Mode)", Calendar.getInstance().getTime());
			return;
		}
		
		if(filters.getIdn_tariff_charge()!=null) {
			HashMap<String,Object> map = new HashMap<String,Object>();
			
			
			map.put("idn_tariff_charge", filters.getIdn_tariff_charge());
			map.put("parameter_value", modeSelected);
			int result = service.insertModeSelected(map);
			if(result==0) {
	    		getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.INFO,"Mode selected updated", "Mode selected updated", Calendar.getInstance().getTime()));
	    		log.info("Mode selected updated", Calendar.getInstance().getTime());
			} else {
	    		getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR,"Mode selected not updated", "Mode selected not updated", Calendar.getInstance().getTime()));
	    		log.info("Mode selected not updated", Calendar.getInstance().getTime());
			}
			

		} else {
    		getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR,"Can not update mode selected", "Can not update mode selected, Tariff ID not selected", Calendar.getInstance().getTime()));
    		log.info("Can not update mode selected, Tariff ID not selected", Calendar.getInstance().getTime());
			this.modeSelected="";
			return;
		}
		
		onSearch();
	}

}