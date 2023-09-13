package com.atos.views.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.primefaces.context.RequestContext;

import com.atos.beans.MessageBean;
import com.atos.beans.booking.BulletinBoardMailBean;
import com.atos.beans.booking.ReleaseCapacityManagementBean;
import com.atos.beans.booking.ReleaseCapacityManagementInfoMailBean;
import com.atos.beans.booking.ReleaseCapacitySubmissionBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.booking.ReleaseCapacityManagementFilter;
import com.atos.services.MailService;
import com.atos.services.booking.ReleaseCapacityManagementService;
import com.atos.utils.Constants;
import com.atos.views.MessagesView;
import com.atos.views.CommonView;

@ManagedBean(name="relCapManView")
@ViewScoped
public class ReleaseCapacityManagementView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4905609065971091423L;


	private static final String strFeasibility = "Feasibility";
	private Map<BigDecimal, Object> shipperIds;	
	private ReleaseCapacityManagementFilter filters;
	private List<ReleaseCapacityManagementBean> items;
	private ReleaseCapacityManagementBean selected;

	
	private static final Logger log = LogManager.getLogger("com.atos.views.booking.ReleaseCapacityManagementView");

	@ManagedProperty("#{relCapManService}")
    transient private ReleaseCapacityManagementService service;
    
	public void setService(ReleaseCapacityManagementService service) {
		this.service = service;
	}

	@ManagedProperty("#{messagesView}")
    private MessagesView messages;

	public void setMessages(MessagesView messages) {
		this.messages = messages;
	}
	
	@ManagedProperty("#{mailService}")
    transient private MailService mailService;

    public void setMailService(MailService mailService) {
    	this.mailService = mailService;
    }

	//geters/seters

	// Para los elementos del combo del filtro de shippers.
	public Map<BigDecimal, Object> getShipperIds() {
		return shipperIds;
	}
	
	public ReleaseCapacityManagementFilter getFilters() {
		return filters;
	}

	public void setFilters(ReleaseCapacityManagementFilter filters) {
		this.filters = filters;
	}

	public List<ReleaseCapacityManagementBean> getItems() {
		return items;
	}

	public void setItems(List<ReleaseCapacityManagementBean> items) {
		this.items = items;
	}
	
	public ReleaseCapacityManagementBean getSelected() {
		return selected;
	}

	public void setSelected(ReleaseCapacityManagementBean selected) {
		this.selected = selected;
	}

	
	@PostConstruct
    public void init() {	
   	
		shipperIds = service.selectShipperId();
		
		initFilters();
		
        items = service.search(filters);
        
        selected = new ReleaseCapacityManagementBean();
   }	

	private void initFilters(){
    	filters = new ReleaseCapacityManagementFilter();
    	
    	// Se requiere que en la pantalla de inicio se ponga por defecto el filtro de submitted.
    	filters.setStatusCode(Constants.SUBMITTED);
		if(isShipper())
			filters.setShipperId(getUser().getIdn_user_group());
    	
		//offshore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
	}
	
	// Para traducir los estados de la BD a las etiquetas definidas en messages.properties.
	public String getStatusExternalCode(String _status) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		String strStatusExternalCode = null;
		
		if(Constants.SUBMITTED.equalsIgnoreCase(_status))
			strStatusExternalCode = msgs.getString("rel_cap_man_submitted");
		else if(Constants.ACCEPTED.equalsIgnoreCase(_status))
			strStatusExternalCode = msgs.getString("rel_cap_man_accepted");
		else if(Constants.COMPLETED.equalsIgnoreCase(_status))
			strStatusExternalCode = msgs.getString("rel_cap_man_completed");
		else if(Constants.PTT_REJECTED.equalsIgnoreCase(_status))
			strStatusExternalCode = msgs.getString("cr_man_rejected");
		else if(Constants.SHIPPER_REJECTED.equalsIgnoreCase(_status))
			strStatusExternalCode = msgs.getString("rel_cap_man_shipper_rejected");
		else
			strStatusExternalCode = msgs.getString("rel_cap_man_undefined");
		
		return strStatusExternalCode;
	}
	
	
	public Map<BigDecimal, Object> getContractToCodes(BigDecimal _shipperIdTo, BigDecimal _contractIdFrom) {
		
		Map<BigDecimal, Object> tmpContracts = null;
		
		if (_shipperIdTo != null) {
			tmpContracts = service.selectContracts(_shipperIdTo,getChangeSystemView().getIdn_active() );
			
			// Si existiera el id de contrato en el resultado de la consulta, se elimina. 
			// Para que el usuario no pueda liberar capacidad sobre el mismo contrato.
			if(_contractIdFrom != null)
				tmpContracts.remove(_contractIdFrom);
		}

		return tmpContracts;
	}	

	public void onSearch(){	
        items = service.search(filters);
        // En cada busqueda se resetea la fila seleccionada.
        selected = new ReleaseCapacityManagementBean();           
	}

	
	public void onClear(){
		initFilters();
    	
        if (items != null) {
            items.clear();
        }
      		
        items = service.search(filters);
        // En cada busqueda se resetea la fila seleccionada.
        selected = new ReleaseCapacityManagementBean();
    }
	

	public void onConditionalSearch(){
		RequestContext context = RequestContext.getCurrentInstance();

		if (tableHasChanged()) {
		    context.execute("PF('w_confirmSearchDlg').show()");
		} else {
			onSearch();
		}
	}


	public void onConditionalClear(){
		RequestContext context = RequestContext.getCurrentInstance();

		if (tableHasChanged()) {
		    context.execute("PF('w_confirmClearDlg').show()");
		} else {
			onClear();
		}
	}

	private boolean tableHasChanged() {
		
		ReleaseCapacityManagementBean tmpBean = null;
		
		for(int i=0; i<items.size(); i++) {
			
			tmpBean = items.get(i);
			// Si la accion NO es vacia porque el estado es SUBMITTED y la accion ha cambiado a algo distinto a FEASIBILITY.
			if( (tmpBean.getAction() != null) &&
				(! strFeasibility.equalsIgnoreCase(tmpBean.getAction())) )
				return true;
		}
		
		return false;
	}
	
	public void selectDetails(ReleaseCapacityManagementBean _clickedBean) {

		ReleaseCapacityManagementBean tmpBean = null;
		List<ReleaseCapacitySubmissionBean> tmpDetails = null;

		selected = _clickedBean;
		BigDecimal tmpCapacityRequestId = selected.getCapacityRequestId();
		
		// Los datos de detalle extraidos, se guardan tambien en items, para no
		// tener que realizar otra vez la consulta.
		// Si los detalles se hubieran consultado previamente, no se vuelven a consultar.
		for(int i=0; i<items.size(); i++) {
			
			tmpBean = items.get(i);
			if( tmpBean.getCapacityRequestId() == tmpCapacityRequestId ) {
				
				if(tmpBean.getDetailsFrom() == null) {
					tmpDetails = service.selectDetailsFrom(tmpCapacityRequestId);
					tmpBean.setDetailsFrom(tmpDetails);
				} else {
					tmpDetails = tmpBean.getDetailsFrom();
				}
				
				selected.setDetailsFrom(tmpDetails);
				
				// Una vez encontrado, no hace falta buscar mas.
				break;
			}
		}
	
	}

	
	public void save() {		
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = null;
    	String errorMsg = null;
    	// En la validacion se ha completado informacion de las requests, que se utiliza a la hora de hacer save().
    	List<ReleaseCapacityManagementBean> itemsToSave;    	
    	
		try{
			// Dentro de validate, solo se hacen validaciones sobre beans con action Accepted.
			itemsToSave = service.validate(items);
			
			
			// A la vez que se hacen las validaciones se va completando la informacion a guardar en BD.
			
			//service.save(itemsToSave); offshore
			service.save(itemsToSave,getChangeSystemView().getIdn_active());
			for(int i=0; i<itemsToSave.size(); i++)
				if(itemsToSave.get(i).getAction().equals("Accept"))
					sendMail(itemsToSave.get(i));
			
			summaryMsg = msgs.getString("data_stored");
			messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.INFO, summaryMsg, summaryMsg, Calendar.getInstance().getTime()));
			
	    	// Si la operacion va bien, se actualiza la vista. Si no, se deja como esta para que el usuario
			// corrija y pueda volver a enviar.

	    	items = service.search(filters);
	        // En cada busqueda se resetea la fila seleccionada.
	        selected = new ReleaseCapacityManagementBean();  	    	
		} 
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = msgs.getString("rel_cap_man_data_not_stored") + " " + ve.getMessage();
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));		
		}
		catch (Exception e) {
			summaryMsg = msgs.getString("saving_data_error");
    		errorMsg = msgs.getString("rel_cap_man_data_not_stored") + " " + msgs.getString("internal_error");
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
    		// Se guarda el detalle del error tecnico.
	    	log.error(e.getMessage(), e);			
		}
	}

	public void sendMail(ReleaseCapacityManagementBean b) {
		HashMap<String,String> values = new HashMap<String,String>();
			values.put("1", b.getContractCodeFrom());
			ReleaseCapacityManagementInfoMailBean m = service.selectContractInfoMail(b.getContractIdFrom());
			values.put("2", m.getTerm_desc());
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String dateStringStart = df.format(m.getStart_date());
			String dateStringEnd = df.format(m.getEnd_date());
			values.put("3", dateStringStart);
			values.put("4", dateStringEnd);
			String texto = "Contract:"+values.get("1")+",Contract Type:"+values.get("2")+
					",Start Date:"+values.get("3")+",End Date:"+values.get("4");
		
		mailService.sendEmail("CAPACITY_RELEASE.ACCEPT", values, getChangeSystemView().getIdn_active(), b.getShipperIdFrom());
		//Para comprobar los valores que se pasan en el email
		messages.addMessage(Constants.head_menu[1],
				new MessageBean(Constants.INFO, "Mail values", texto, Calendar.getInstance().getTime())); 
	}

}
