package com.atos.services.allocation;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.LanguageBean;
import com.atos.beans.NotificationBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.UserBean;
import com.atos.beans.allocation.AllocationBean;
import com.atos.beans.allocation.ValidateShipperReviewBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.allocation.AllocationManagementFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.allocation.AllocationManagementMapper;
import com.atos.quartz.AllocationAutorunIntradayClient;
import com.atos.runnable.allocation.AllocationBalanceTask;
import com.atos.utils.Constants;

@Service("AllocationManagService")
public class AllocationManagementServiceImpl implements AllocationManagementService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2136706972150724501L;
	
	private static final String strAllocationMaxDateOffset = "ALLOCATION.MAX.DATE.OFFSET";
	private static final String strAllocationReviewMaxPercentChangeOnshore = "ALLOCATION.REVIEW.MAXIMUM.PERCENT.CHANGE.ONSHORE";
	private static final String strAllocationReviewMaxPercentChangeOffshore = "ALLOCATION.REVIEW.MAXIMUM.PERCENT.CHANGE.OFFSHORE";
	private static final String strNotifTypeAllocationReviewManaged = "ALLOCATION.REVIEW.MANAGED";
	private static final String strNotifOrigin = "ALLOCATION";
	private static final String strNotifSeparator = "~";

	
	private static final Logger log = LogManager.getLogger("com.atos.services.allocation.AllocationManagementServiceImpl");
	
	@Autowired
	private AllocationManagementMapper amMapper;
	
	@Autowired
	private SystemParameterMapper sysParMapper;
	
	@Autowired
	private NotificationMapper notifMapper;	

	@Autowired
    private AllocationAutorunIntradayClient intradayService;

	@Autowired
	@Qualifier("allocationBalanceTaskExecutor")
	private ThreadPoolTaskExecutor allBalTaskExecutor;
	
	public BigDecimal selectFactorFromDefaultUnit(String otherUnit){
		return amMapper.selectFactorFromDefaultUnit(otherUnit);
	}
	
	public BigDecimal selectFactorToDefaultUnit(String otherUnit){
		return amMapper.selectFactorToDefaultUnit(otherUnit);
	}
	
	public Date selectOpenPeriodFirstDay(Map<String, Object> params) {
		return amMapper.selectOpenPeriodFirstDay(params);
	}
	
	public Integer selectAllocationMaxDateOffset(String userId, String lang) throws Exception {
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(new Date());	// Now
		bean.setParameter_name(strAllocationMaxDateOffset);
		bean.setUser_id(userId);
		bean.setLanguage(lang);
		sysParMapper.getIntegerSystemParameter(bean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(bean == null || bean.getInteger_exit()==null)
			throw new Exception("Error getting ALLOCATION.MAX.DATE.OFFSET parameter");
		
		return bean.getInteger_exit();
	}
	
	public Float selectAllocationReviewMaxPercentChange(Date gasDay, String userId, String lang, String systemCode)
			throws Exception {
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(gasDay);
		if ("ONSHORE".equals(systemCode)) {
			bean.setParameter_name(strAllocationReviewMaxPercentChangeOnshore);
		} else if ("OFFSHORE".equals(systemCode)) {

			bean.setParameter_name(strAllocationReviewMaxPercentChangeOffshore);
		}
		bean.setUser_id(userId);
		bean.setLanguage(lang);
		sysParMapper.getFloatSystemParameter(bean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(bean == null || bean.getFloat_exit()==null)
			throw new Exception("Error getting ALLOCATION.REVIEW.MAXIMUM.PERCENT.CHANGE parameter");
		
		return bean.getFloat_exit();
	}
	
	public Map<BigDecimal, Object> selectShipperId(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = amMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectContractId(AllocationManagementFilter filter){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = amMapper.selectContractId(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectPointId(BigDecimal systemId) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = amMapper.selectPointId(systemId);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}

	public List<AllocationBean> search(AllocationManagementFilter filter){
		
		// Se crea un nuevo filtro, para anadir los % e invocar la consulta.
		AllocationManagementFilter tmpFilter = new AllocationManagementFilter(filter);
		String tmpRevCode = filter.getReviewCode();
		
		if((tmpRevCode != null) && (!"".equalsIgnoreCase(tmpRevCode)))
			tmpFilter.setReviewCode("%" + tmpRevCode + "%");
		
		return amMapper.selectAllocations(tmpFilter);
	}
	
	@Transactional( rollbackFor = { Throwable.class })
	public String acceptReview(List<AllocationBean> _allocationList, 
								boolean _afterConfirm,
								UserBean _user, 
								LanguageBean _language,
			BigDecimal _factorToDefaultUnit, BigDecimal systemId, Map<String, Object> params) throws Exception {

    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String lineSeparator = System.getProperty("line.separator");
		String warnings = "";
		String errorList = "";
		
		if(_allocationList==null || _allocationList.size()==0)
			throw new ValidationException(msgs.getString("all_man_no_review_selected")); 
	
		// Si en el proceso de hacer insert en tabla, se produce algun error, puede quedar algunos beans con el reviewId nuevo
		// del registro insertado y que luego se hace rollback. En esta variable se guarda el id para reestablecerlo en caso 
		// de excepcion.
		BigDecimal[] originalReviewIds = new BigDecimal[_allocationList.size()];
		for(int i=0; i<originalReviewIds.length; i++) {
			originalReviewIds[i] = _allocationList.get(i).getReviewId();
			if(originalReviewIds[i]==null)
				throw new ValidationException(msgs.getString("all_man_only_accepted_alloc_reviewed"));
		}
		
		if(!_afterConfirm){
			// Se pasan todos los repartos seleccionados para que se validen por grupos de dia y punto.
			warnings = validateAcceptReview(_allocationList, _user, _language, systemId);
		}
		
		// Si hay warnings se sale sin hacer nada, hasta que el usuario confirme.
		if( warnings==null || "".equalsIgnoreCase(warnings) ) {
			for(AllocationBean allocation2: _allocationList){
				try {
					validateChangeReview(allocation2, _user, _language, params);
					saveIncidentReview(allocation2, Constants.ACCEPTED, _user, _language, _factorToDefaultUnit);
					sendNotification(allocation2, _user, _language, systemId);
				}
				catch(Exception e){
					errorList += "[" + allocation2.getReviewCode() + " (" +
							sdf.format(allocation2.getGasDay()) + "," +
							allocation2.getShipperCode() + "," +
							allocation2.getContractCode() + "," +
							allocation2.getNomPointCode() + ")]:" +
							e.getMessage() + lineSeparator;
				}
			}
			
			// Si ha habido alguna excepcion, se sale con excepcion para que se haga rollback y se muestre el error acumulado al usuario.
			if(!"".equalsIgnoreCase(errorList) ){
				// Se reestablecen los ids que hayan sido modificados por inserts para los que se va a hacer rollback.
				for(int i=0; i<originalReviewIds.length; i++)
					_allocationList.get(i).setReviewId(originalReviewIds[i]);
				
				throw new ValidationException(msgs.getString("all_man_no_alloc_updated") + lineSeparator +
												errorList);
			}
		}
		
		return warnings;
	}
	
	// Puede devolver un warning tras la validacion en BD. Si no, devuelve null.
	private String validateAcceptReview(List<AllocationBean> _allocationList,
										UserBean _user, 
			LanguageBean _language, BigDecimal systemId) throws Exception {
		String warnings = null;

		List<BigDecimal> allRevIds = new ArrayList<BigDecimal>();
		for(AllocationBean allRev: _allocationList)
			allRevIds.add(allRev.getReviewId());
		
		// Validacion en BD.
		ValidateShipperReviewBean vBean = new ValidateShipperReviewBean();
		vBean.setAllocationReviewIds(allRevIds);
		vBean.setUserName(_user.getUsername());
		vBean.setLanguageCode(_language.getLocale());
		vBean.setSystemId(systemId);
		amMapper.validateShipperReview(vBean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(vBean == null || vBean.getErrorCode()==null)
			throw new Exception("Error validating shipper review to accept.");
		
		int res = vBean.getErrorCode().intValue();
		if( res != 0) {
			if( res >= 1000 )	// Errores funcionales.
	    		throw new ValidationException(vBean.getErrorDesc());
			else				// Errores tecnicos.
	    		throw new Exception(vBean.getErrorDesc());
		}
		
		warnings = vBean.getWarning();
		
		return warnings;
	}
	
	@Transactional( rollbackFor = { Throwable.class })
	public void rejectReview(List<AllocationBean> _allocationList, 
								UserBean _user, 
								LanguageBean _language,
			BigDecimal _factorToDefaultUnit, Map<String, Object> params) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String lineSeparator = System.getProperty("line.separator");
		String errorList = "";
		
		if(_allocationList==null || _allocationList.size()==0)
			throw new ValidationException(msgs.getString("all_man_no_review_selected")); 
		
		for(AllocationBean allocation: _allocationList){		
		if(allocation.getReviewId()==null)
			throw new ValidationException(msgs.getString("all_man_only_rejected_alloc_reviewed"));
		}
		
		for(AllocationBean allocation2: _allocationList){
			try {
				validateChangeReview(allocation2, _user, _language, params);
				saveIncidentReview(allocation2, Constants.REJECTED, _user, _language, _factorToDefaultUnit);
				BigDecimal systemId = (BigDecimal) params.get("idnSystem");
				sendNotification(allocation2, _user, _language, systemId);
			}
			catch(Exception e){
				errorList += "[" + allocation2.getReviewCode() + " (" +
						sdf.format(allocation2.getGasDay()) + "," +
						allocation2.getShipperCode() + "," +
						allocation2.getContractCode() + "," +
						allocation2.getNomPointCode() + ")]:" +
						e.getMessage() + lineSeparator;
			}
		}
		
		// Si ha habido alguna excepcion, se sale con excepcion para que se haga rollback y se muestre el error acumulado al usuario.
		if(!"".equalsIgnoreCase(errorList) )
			throw new ValidationException(msgs.getString("all_man_no_alloc_updated") + lineSeparator +
											errorList);
	}

	// Validacion comun a Accept y Reject
	private void validateChangeReview(AllocationBean _allocation, UserBean _user, LanguageBean _language,
			Map<String, Object> params) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		// Se comprueba que el dia GasDay debe pertenecer al periodo con balance abierto 
		//(por si se cierra el mes mientras estamos en la pantalla).
		if (!inReviewPeriod(_allocation.getGasDay(), _user, _language, params))
			throw new ValidationException(msgs.getString("all_man_allocation_gasday_out_of_period_error"));

		if(versionHasChanged(_allocation))
			throw new ValidationException(msgs.getString("all_rev_allocation_review_changed_error"));
	}

	// periodo [primer_dia_abierto; Hoy-ALLOCATION.MAX.DATE.OFFSET]
	private boolean inReviewPeriod(Date _gasDay, UserBean _user, LanguageBean _language, Map<String, Object> params)
			throws Exception {
		Date openPeriodFirstDay = null;
		Integer allocationMaxDateOffset = null;
		Date responsePeriodStartDate = null;
		Date responsePeriodEndDate = null;
		
		// Primer dia de balance abierto.
		openPeriodFirstDay = selectOpenPeriodFirstDay(params);
   		allocationMaxDateOffset = selectAllocationMaxDateOffset(_user.getUsername(), _language.getLocale());
    	
		Calendar tmpEndDate = Calendar.getInstance();
		tmpEndDate.set(Calendar.HOUR_OF_DAY, 0);
		tmpEndDate.set(Calendar.MINUTE, 0);
		tmpEndDate.set(Calendar.SECOND, 0);
		tmpEndDate.set(Calendar.MILLISECOND, 0);
		tmpEndDate.add(Calendar.DAY_OF_MONTH, allocationMaxDateOffset * (-1));
		
		responsePeriodStartDate = openPeriodFirstDay;
		responsePeriodEndDate = tmpEndDate.getTime();
		
		return (responsePeriodStartDate.compareTo(_gasDay)<=0 && 
				responsePeriodEndDate.compareTo(_gasDay)>=0); 
	}

	private boolean versionHasChanged(AllocationBean _allocation) throws Exception {
		AllocationBean lastVersionReview = null;
		// Ojo, como no interesa saber la cantidad, no actualizo el factor de conversion.
		List<AllocationBean> lTmp = amMapper.selectLastAllocationReview(_allocation);
		
		// En esta pantalla se debe haber creado previamente una review initial. 
		if(lTmp==null || lTmp.size()==0)
			throw new Exception("Not found last Allocation Review to compare version.");
		
		// Se supone que solo se devuelve 1 elemento. Maxima version.
		lastVersionReview = lTmp.get(0);
		
		// En esta pantalla se debe haber creado previamente una review initial.
		if(lastVersionReview==null)
			throw new Exception("Not found last Allocation Review to compare version.");
		
		// En esta pantalla se debe haber creado previamente una review initial.
		if(_allocation.getReviewId()==null)
			throw new Exception("Not found Allocation Review to compare version.");
		
		return (_allocation.getReviewId().compareTo(lastVersionReview.getReviewId()) != 0);			
	}
	
	private void saveIncidentReview(AllocationBean _allocation,
									String _newStatus,
									UserBean _user, 
									LanguageBean _language,
									BigDecimal _factorToDefaultUnit) throws Exception {

		_allocation.setStatusCode(_newStatus);
		// Se mantiene el reviewCode que tenia hasta ahora la allocation review. 
		// Se guarda la cantidad de reparto disponible cuando se crea la nueva version de review.
		_allocation.setOriginalAllocationForReview(_allocation.getAllocationTPA());
		//_allocation.setIsWarned();	YA NO SE GUARDA ESTE CAMPO AL INSERTAR(El aviso no tiene sentido si se validan varias reviews a la vez).			
		_allocation.setConversionFactor(_factorToDefaultUnit);

		int res = amMapper.insertAllocationReview(_allocation);
		if(res!=1){
			throw new Exception("Error inserting into Allocation Review table.");   		
		}
	}

	private void sendNotification(AllocationBean _allocation, UserBean _user, LanguageBean _language,
			BigDecimal systemId) throws Exception {
    	int res = 0;
   	
		// Se genera la notificacion para el shipper, correspondiente a que el operador ha procesado su peticion de contrato.
		NotificationBean notif = new NotificationBean();
		notif.setType_code(strNotifTypeAllocationReviewManaged);	
		notif.setOrigin(strNotifOrigin);
		notif.setSystemId(systemId);
		// Para un mensaje como "The allocation for {1}, {2} and {3} has been reviewed by shipper (Review Code: {4})."
		// se pasa como parametro el dia de gas afectado, el contrato afectado,
		// el punto afectado y el codigo de propuesta.
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	String notifInfo = sdf.format(_allocation.getGasDay()) + strNotifSeparator +
    						_allocation.getContractCode() + strNotifSeparator +
    						_allocation.getNomPointCode() + strNotifSeparator +
    						_allocation.getReviewCode();	
		notif.setInformation(notifInfo);

		notif.setUser_id(_user.getUsername());
		notif.setIdn_user_group(_allocation.getShipperId());
		notif.setLanguage(_language.getLocale());
		
		notifMapper.getCreateNotification(notif);
		if( notif==null || notif.getInteger_exit()==null ){
			throw new Exception("Error sending notification to shipper.");
		}
		else {
			// En caso de error funcional, el procedimiento devuelve un codigo de error mayor o igual a 1000 y 
			// se devuelve una ValidationException (funcional). Esta excepcion se pintara en la ventana de mensajes al usuario.
			// En caso de error tecnico, el procedimiento devuelve un codigo de error menor que 1000 y distinto de cero.
			// se devuelve una Exception normal (error tecnico). En la ventana de mensajes al usuario se muestra un 
			// "error interno" y los detalles se llevan al log.
			res = notif.getInteger_exit().intValue();
			if( res != 0) {
				if( res >= 1000 )	// Errores funcionales.
		    		throw new ValidationException(notif.getError_desc());
				else				// Errores tecnicos.
		    		throw new Exception(notif.getError_desc());
			}				
		}
	}
	
	public void allocationAndBalance(Date _startDate, Date _endDate, UserBean _user, LanguageBean _lang,
			BigDecimal idnSystem) throws Exception {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	try{
        	// Se lanza un thread para seguir con el proceso de forma asincrona/desatendida.
        	// Si se alcanza el numero maximo de threads concurrentes definidos en el metTaskExecutor,
        	// el siguiente thread no se puede lanzar y se genera una org.springframework.core.task.TaskRejectedException
			allBalTaskExecutor.execute(new AllocationBalanceTask(_startDate, _endDate, _user, _lang, msgs, amMapper,
					notifMapper, idnSystem, intradayService));
        }   
        catch (TaskRejectedException tre) {	// Excepcion para el caso de que no se pueda generar un thread porque se ha alcanzado el maximo numero de threads.
        			// En caso de error, se ha de liberar el bloqueo.
					// En caso de ok, el bloqueo se libera en el thread.
			log.error(tre.getMessage(), tre);
			throw new ValidationException(msgs.getString("all_man_max_processes_reached_error"));
		}        
	}

}
