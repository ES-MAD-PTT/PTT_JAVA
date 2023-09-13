package com.atos.views.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.atos.beans.LanguageBean;
import com.atos.beans.MessageBean;
import com.atos.beans.UserBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.balancing.BalanceManagementFilter;
import com.atos.services.balancing.BalanceManagementService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="balManagView")
@ViewScoped
public class BalanceManagementView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2063600123306346670L;

	private static final String strAllocationTypeCommercial = "COMMERCIAL";
	private static final String strBalanceClosingTypeDefinitive = "DEFINITIVE";
	private BalanceManagementFilter filters;
	// Se usa esta variable intermedia entre la pantalla y el filtro para detectar cuando cambia el codigo de tipo
	// de cierre de balance y asi actualizar la fecha de cierre.
	private String balanceClosingTypeCode;
	
	private static final Logger log = LogManager.getLogger("com.atos.views.balancing.BalanceManagementView");
	
	@ManagedProperty("#{BalanceManagService}")
    transient private BalanceManagementService service;
    
	public void setService(BalanceManagementService service) {
		this.service = service;
	}
	
	
	@ManagedProperty("#{userBean}")
    private UserBean user;
	
	public void setUser(UserBean user){
		this.user = user;
	}

	@ManagedProperty("#{languageBean}")
    private LanguageBean language;
	
	public void setLanguage(LanguageBean language) {
		this.language = language;
	}

	//geters/seters
	public BalanceManagementFilter getFilters() {
		return filters;
	}

	public void setFilters(BalanceManagementFilter filters) {
		this.filters = filters;
	}

	public String getBalanceClosingTypeCode() {
		return balanceClosingTypeCode;
	}

	public void setBalanceClosingTypeCode(String balanceClosingTypeCode) {
		this.balanceClosingTypeCode = balanceClosingTypeCode;
		filters.setClosingTypeCode(balanceClosingTypeCode);
		filters.setClosingDate(
				getLastClosingDateFromClosingType(balanceClosingTypeCode, getChangeSystemView().getIdn_active()));
	}
	
	@PostConstruct
    public void init() {
		balanceClosingTypeCode = strBalanceClosingTypeDefinitive;
		
		filters = new BalanceManagementFilter();
		filters.setAllocTypeCode(strAllocationTypeCommercial);
		filters.setClosingTypeCode(strBalanceClosingTypeDefinitive);
		filters.setClosingDate(getLastClosingDateFromClosingType(strBalanceClosingTypeDefinitive,
				getChangeSystemView().getIdn_active()));
		filters.setUser(user.getUsername());
		filters.setLang(language.getLocale());
	}
	
	private Date getLastClosingDateFromClosingType(String _closingTypeCode, BigDecimal idnSystem) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("closingTypeCode", _closingTypeCode);
		params.put("idnSystem", getChangeSystemView().getIdn_active());
		params.put("sysCode", getChangeSystemView().getSystem());
		Date openPeriodFirstDay = service.selectOpenPeriodFirstDay(params);
		// La consulta devuelve el primer dia de mes. Para llamar al procedimiento hay que obtener el ultimo dia de ese mes.
		Calendar lastDay = Calendar.getInstance();
		lastDay.setTime(openPeriodFirstDay);
		lastDay.set(Calendar.HOUR_OF_DAY, 0);
		lastDay.set(Calendar.MINUTE, 0);
		lastDay.set(Calendar.SECOND, 0);
		lastDay.set(Calendar.MILLISECOND, 0);
		lastDay.set(Calendar.DAY_OF_MONTH,lastDay.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		return lastDay.getTime();
	}
	
	// Para los elementos del combo del filtro de tipos de cierre de balance.
	public Map<String, Object> getBalanceClosingTypes() {
		return service.selectBalanceClosingTypeCodes();
	}
	
	public void onCloseBalance(){
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	String summaryMsg = null;
    	String errorMsg = null;
		
    	try {
			filters.setIdnSystem(getChangeSystemView().getIdn_active());
			filters.setCodSystem(getChangeSystemView().getSystem());
    		service.closeBalance(filters, user);
    	}
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = ve.getMessage();
			getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(ve.getMessage(), ve);
    		return;
		} 	
    	catch(Exception e){
			summaryMsg = msgs.getString("saving_data_error");
        	errorMsg = msgs.getString("bal_man_balance_closing_error");
			getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}
    	
    	summaryMsg = msgs.getString("bal_man_balance_closing_ok");
    	String okMsg = msgs.getString("bal_man_balance_closing_executed");
		
		getMessages().addMessage(Constants.head_menu[9],
				new MessageBean(Constants.INFO, summaryMsg, okMsg, Calendar.getInstance().getTime()));
    	log.info(okMsg);
    	
    	// Tras ejecutar el cierre de balance se refresca la fecha del filtro en la pantalla.
		filters.setClosingDate(
				getLastClosingDateFromClosingType(filters.getClosingTypeCode(), getChangeSystemView().getIdn_active()));
	}
}
