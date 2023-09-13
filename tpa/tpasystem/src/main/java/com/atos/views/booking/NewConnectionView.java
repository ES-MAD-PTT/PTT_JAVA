package com.atos.views.booking;

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

import com.atos.beans.MessageBean;
import com.atos.beans.booking.NewConnectionBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.booking.NewConnectionFilter;
import com.atos.services.booking.NewConnectionService;
import com.atos.utils.Constants;
import com.atos.views.MessagesView;
import com.atos.views.CommonView;

@ManagedBean(name="newConnectionView")
@ViewScoped
public class NewConnectionView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6982252705112104068L;

	private NewConnectionFilter filters;
	private List<NewConnectionBean> items;
	private NewConnectionBean selected;

	private static final Logger log = LogManager.getLogger("com.atos.views.booking.NewConnectionView");

	@ManagedProperty("#{newConnectionService}")
    transient private NewConnectionService service;
    
	public void setService(NewConnectionService service) {
		this.service = service;
	}

	//geters/seters

	public NewConnectionFilter getFilters() {
		return filters;
	}

	public void setFilters(NewConnectionFilter filters) {
		this.filters = filters;
	}

	public List<NewConnectionBean> getItems() {
		return items;
	}

	public void setItems(List<NewConnectionBean> items) {
		this.items = items;
	}
	
	public NewConnectionBean getSelected() {
		return selected;
	}

	public void setSelected(NewConnectionBean selected) {
		this.selected = selected;
	}
	
	@PostConstruct
    public void init() {
    	filters = new NewConnectionFilter();
    	
    	//offshore
    	filters.setIdn_system(getChangeSystemView().getIdn_active());
    	
    	if(getUser().isUser_type(Constants.SHIPPER)){
    		filters.setShipperId(getUser().getIdn_user_group());
    	}
    	
        items = service.search(filters);
        
        selected = new NewConnectionBean();
   }	

	// Para los elementos del combo del filtro de shippers.
	public Map<BigDecimal, Object> getShipperIds() {
		return service.selectShipperId();
	}

	
	public void onSearch(){

		//offshore
    	filters.setIdn_system(getChangeSystemView().getIdn_active());
    	
        items = service.search(filters);
        // En cada busqueda se resetea la fila seleccionada.
        selected = new NewConnectionBean();
	}

	
	public void onClear(){
		filters = new NewConnectionFilter();
    	
        if (items != null) {
            items.clear();
        }

        //offshore
    	filters.setIdn_system(getChangeSystemView().getIdn_active());
    	
        items = service.search(filters);
        // En cada busqueda se resetea la fila seleccionada.
        selected = new NewConnectionBean();
    }

	
	public void getXlsFile(NewConnectionBean _newConn) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = msgs.getString("new_conn_xlsFile");
    	String errorMsg = null;
    	
    	try {
    		service.getFileByOpFileId(_newConn);
    	}
    	catch(ValidationException ve){
    		errorMsg = msgs.getString("download_error") + " " + _newConn.getXlsFileName() + ": " + ve.getMessage();
    		getMessages().addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	return;    		
    	}
    	catch(Exception e){
    		errorMsg = msgs.getString("download_error") + " " + _newConn.getXlsFileName();
    		getMessages().addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}
	}

	
	public void getPdfFile(NewConnectionBean _newConn) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = msgs.getString("new_conn_pdfFile");
    	
    	try {
    		service.generatePdfFile(_newConn, getChangeSystemView().getIdn_active());
    	}
    	catch(Exception e){
    		String errorMsg = msgs.getString("generating_error") + ".";
    		getMessages().addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(e.getMessage(), e);
	    	return;    		
    	}
	}
	
	
	// Este booleano marcara si esta habilitado el filtro de shipper (isOperator = true) o esta deshabilitado, 
		// con un shipper fijo.
		public boolean getIsShipper() {
			
			return getUser().isUser_type(Constants.SHIPPER);
		}
}
