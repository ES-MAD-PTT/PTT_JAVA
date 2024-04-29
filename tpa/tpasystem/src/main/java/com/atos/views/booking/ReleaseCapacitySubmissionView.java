package com.atos.views.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.primefaces.context.RequestContext;

import com.atos.beans.MessageBean;
import com.atos.beans.booking.ReleaseCapacitySubmissionBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.booking.ReleaseCapacitySubmissionFilter;
import com.atos.services.booking.ReleaseCapacitySubmissionService;
import com.atos.utils.Constants;
import com.atos.views.MessagesView;
import com.atos.views.CommonView;

@ManagedBean(name="relCapSubView")
@ViewScoped
public class ReleaseCapacitySubmissionView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -963815903936289049L;

	private static final String strSystemPointTypeEntry = "ENTRY";
	
	private ReleaseCapacitySubmissionFilter filters;
	private List<ReleaseCapacitySubmissionBean> items;
//	private BigDecimal shipperId;					// ShipperId del usuario que se consulta al iniciar.
	// Para marcar si ha cambiado algun dato en la tabla, para luego mostrar el dialogo de confirmacion o no.
	private boolean bChangedTable = false;
	
	private Date dateFrom;
	private Date dateTo;
	
	private boolean toOperator;
	
	private static final Logger log = LogManager.getLogger("com.atos.views.booking.ReleaseCapacitySubmissionView");

	@ManagedProperty("#{relCapSubService}")
    transient private ReleaseCapacitySubmissionService service;
    
	public void setService(ReleaseCapacitySubmissionService service) {
		this.service = service;
	}

	@ManagedProperty("#{messagesView}")
    private MessagesView messages;

	public void setMessages(MessagesView messages) {
		this.messages = messages;
	}

	//geters/seters

	public ReleaseCapacitySubmissionFilter getFilters() {
		return filters;
	}

	public void setFilters(ReleaseCapacitySubmissionFilter filters) {
		this.filters = filters;
	}

	public List<ReleaseCapacitySubmissionBean> getItems() {
		return items;
	}

	public void setItems(List<ReleaseCapacitySubmissionBean> items) {
		this.items = items;
	}

	public boolean isbChangedTable() {	
		return bChangedTable;
	}

	
	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
	public boolean isToOperator() {
		return toOperator;
	}

	public void setToOperator(boolean toOperator) {
		this.toOperator = toOperator;
	}

	@PostConstruct
    public void init() {
//		String userId = (String)SecurityUtils.getSubject().getPrincipal();
//		
//		shipperId = null;
//
//		Map<BigDecimal, Object> mShipper = service.selectShipperIdByUserId(userId);
//		
//		Set<BigDecimal> sShipperIds = mShipper.keySet();
//		// Esta situacion no se debe dar. No deberían existir 2 usuarios en BD con el mismo 
//		// codigo de usuario; en ese caso, podria estar asociado a 2 shippers y no 
//		// se sabria cual es el shipper que hay que utilizar. 
//		// En este caso se guarda un log y se sigue con el primer shipper obtenido.
//		if (sShipperIds.size()>1)
//			log.error("It has detected two users with the same user_id, related to two different shippers.");
//
//		// Esta situacion, tampoco se debe dar. Seria el caso de un usuario que no fuera operador ni shipper.
//		// Se puede seguir adelante estableciendo un shipperId no válido para que no obtenga datos de otros shippers.
//		if (sShipperIds.size() == 0) {
//			log.error("The user is not related to any shipper.");
//			// Con shipperId igual a -1, no se podra obtener ningun contrato para mostrar por pantalla.
//			shipperId = new BigDecimal(-1);
//		}
//			
//		Iterator<BigDecimal> itrShipperIds = sShipperIds.iterator();
//		while(itrShipperIds.hasNext()) {
//			shipperId = itrShipperIds.next();
//		    // Guardo el primer valor de la consulta (solo deberia tener uno) y luego salgo.
//			// No es necesario guardar el codigo del shipper.
//		    break;
//		}			
				
    	toOperator = true;
		filters = new ReleaseCapacitySubmissionFilter();
    	
        items = service.search(filters);
   }	
	
	public void onUpdateDates() {		
		dateFrom = service.getStartDate(filters.getcontractId());
		dateTo = service.getEndDate(filters.getcontractId());
	}


	// Para los elementos del combo del filtro de shippers.
	public Map<BigDecimal, Object> getContractCodes() {
		
		if(isShipper()) {
			return service.selectContracts(getUser().getIdn_user_group(), getChangeSystemView().getIdn_active());
		} else {
			return service.selectContractsOperator(getChangeSystemView().getIdn_active());
		}
	}	
	
	
	// Para los elementos del combo del filtro de shippers.
	public Map<BigDecimal, Object> getPointCodes() {
		return service.selectSystemPoints(filters.getcontractId());
	}	
	
	
	// Para los elementos del combo del filtro de shippers.
	public List<Integer> getYears() {
		return service.selectAgreementStartYears(filters.getcontractId());
	}	


	public void onSearch(){
	    	
        items = service.search(filters);
		bChangedTable = false;        
	}

	
	public void onClear(){
		filters = new ReleaseCapacitySubmissionFilter();
    	
        if (items != null) {
            items.clear();
        }

        items = service.search(filters);
		bChangedTable = false;	
    }
	
	
	public void onChangeTable() {
		bChangedTable = true;
	}


	public void onConditionalSearch(){
		RequestContext context = RequestContext.getCurrentInstance();

		if (bChangedTable) {
		    context.execute("PF('w_confirmSearchDlg').show()");
		} else {
			onSearch();
		}
	}
	

	public void onConditionalClear(){
		RequestContext context = RequestContext.getCurrentInstance();

		if (bChangedTable) {
		    context.execute("PF('w_confirmClearDlg').show()");
		} else {
			onClear();
		}
	}
	
	//Para poder llamar desde isDisabledMMscfd a isDisabledBBTuDay
	public boolean isZeroBBTuDay( ReleaseCapacitySubmissionBean _rcsBean ){
		//Si el valor contratado es 0, no se puede editar
		if(_rcsBean.getContratedBBTuDay().compareTo(new Float(0))==0)
			return true;
		return false;
	}
	
	
	// No estara habilitado el campo releaseBBTuDay si la fecha de inicio es menor que hoy.
	public boolean isDisabledBBTuDay( ReleaseCapacitySubmissionBean _rcsBean ){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    Date date = new Date();  
		
		Date rcsbAgreementStartDate = _rcsBean.getAgreementStartDate(); 
		
		// Si no se pudiera obtener el valor la fecha de comienzo, se dejara el campo disabled. 
		if( rcsbAgreementStartDate != null ) {
			return (rcsbAgreementStartDate.before(date));
		}
		return true;
	}


	// No estara habilitado el campo releaseMMscfd, si se cumple la misma validacion que releaseBBTuDay o el punto no es de entrada.
	public boolean isDisabledMMscfd( ReleaseCapacitySubmissionBean _rcsBean ){
		//Si el valor contratado es 0, no se puede editar
		if(_rcsBean.getContratedMMscfd()!=null && _rcsBean.getContratedMMscfd().compareTo(new Float(0))==0)
				return true;
		return ( (!strSystemPointTypeEntry.equalsIgnoreCase(_rcsBean.getPointTypeCode())) ||
				isDisabledBBTuDay(_rcsBean) );
	}

	
	public void save() {		
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String summaryMsg = null;
    	String errorMsg = null;
  	
		try{
			if( bChangedTable )
				service.save(getUser().getIdn_user_group(), filters.getcontractId(), items, getChangeSystemView().getIdn_active(), toOperator); 
			else
				throw new ValidationException(msgs.getString("no_data"));						
			
			summaryMsg = msgs.getString("data_stored");
			messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.INFO, summaryMsg, summaryMsg, Calendar.getInstance().getTime()));
			
	    	// Si la operacion va bien, se actualiza la vista. Si no, se deja como esta para que el usuario
			// corrija y pueda volver a enviar.
	    	items = service.search(filters); 
			bChangedTable = false;
		} 
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
    		errorMsg = msgs.getString("rel_cap_sub_data_not_stored") + " " + ve.getMessage();
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));		
		}
		catch (Exception e) {
			summaryMsg = msgs.getString("saving_data_error");
    		errorMsg = msgs.getString("rel_cap_sub_data_not_stored") + " " + msgs.getString("internal_error");
    		messages.addMessage(Constants.head_menu[1],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
    		// Se guarda el detalle del error tecnico.
	    	log.error(e.getMessage(), e);			
		}		
	}

}
