package com.atos.services.allocation;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ElementIdBean;
import com.atos.beans.LanguageBean;
import com.atos.beans.NotificationBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.UserBean;
import com.atos.beans.allocation.AllocationBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.allocation.AllocationReviewFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.allocation.AllocationReviewMapper;
import com.atos.services.balancing.BalanceManagementService;
import com.atos.utils.Constants;

@Service("AllocationReviewService")
public class AllocationReviewServiceImpl implements AllocationReviewService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2136706972150724501L;

	private static final String strAllocationReviewOpenDaysOnshore = "ALLOCATION.REVIEW.OPEN.DAYS.ONSHORE";
	private static final String strAllocationReviewOpenDaysOffshore = "ALLOCATION.REVIEW.OPEN.DAYS.OFFSHORE";
	private static final String strAllocationMaxDateOffset = "ALLOCATION.MAX.DATE.OFFSET";
	private static final String strInitialStatus = "INITIAL";		// Estado inicial de la Allocation Review.
	private static final String strIsWarnedN="N";					// En el estado inicial, isWarned siempre vale 'N'.
	private static final String strDefOperatorParamName = "DEFAULT.OPERATOR";
	private static final String strNotifTypeAllocationReviewGenerated = "ALLOCATION.REVIEW.GENERATED";
	private static final String strNotifOrigin = "ALLOCATION";
	private static final String strNotifSeparator = "~";
	private static final String ONSHORE = "ONSHORE";
	private static final String OFFSHORE = "OFFSHORE";
	
	@Autowired
	private AllocationReviewMapper arMapper;
	
	@Autowired
	private SystemParameterMapper sysParMapper;
	
	@Autowired
	private NotificationMapper notifMapper;	
	@Autowired
	private BalanceManagementService balMagService;

	public BigDecimal selectFactorFromDefaultUnit(String otherUnit){
		return arMapper.selectFactorFromDefaultUnit(otherUnit);
	}
	
	public BigDecimal selectFactorToDefaultUnit(String otherUnit){
		return arMapper.selectFactorToDefaultUnit(otherUnit);
	}
	
	
	public Integer selectAllocationReviewOpenDays(String userId, String lang, String codSystem) throws Exception {
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(new Date());	// Now
		if(ONSHORE.equals(codSystem)){
			bean.setParameter_name(strAllocationReviewOpenDaysOnshore);
		} else if (OFFSHORE.equals(codSystem)) {

			bean.setParameter_name(strAllocationReviewOpenDaysOffshore);
		}
		bean.setUser_id(userId);
		bean.setLanguage(lang);
		sysParMapper.getIntegerSystemParameter(bean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(bean == null || bean.getInteger_exit()==null)
			throw new Exception("Error getting ALLOCATION.REVIEW.OPEN.DAYS parameter");
		
		return bean.getInteger_exit();
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
	
	public Map<BigDecimal, Object> selectShipperId(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = arMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectContractId(AllocationReviewFilter filter){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = arMapper.selectContractId(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectZones(String systemCode){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = arMapper.selectZonesFromSystemCode(systemCode);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectAreas(AllocationReviewFilter filter){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = arMapper.selectAreasFromZoneId(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectPointId(AllocationReviewFilter filter){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = arMapper.selectPointId(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}

	public List<AllocationBean> search(AllocationReviewFilter filter){
		
		// Se crea un nuevo filtro, para añadir los % e invocar la consulta.
		AllocationReviewFilter tmpFilter = new AllocationReviewFilter(filter);
		String tmpRevCode = filter.getReviewCode();
		
		if((tmpRevCode != null) && (!"".equalsIgnoreCase(tmpRevCode)))
			tmpFilter.setReviewCode("%" + tmpRevCode + "%");
		
		return arMapper.selectAllocations(tmpFilter);
	}
	
	public void saveReview(AllocationBean _allocation, 
							UserBean _user, 
							LanguageBean _language,
			BigDecimal _factorToDefaultUnit, Map<String, Object> params) throws Exception {
		validateReview(_allocation, _user, _language, params);
		saveIncidentReview(_allocation, _user, _language, _factorToDefaultUnit);
		BigDecimal systemId = (BigDecimal) params.get("idnSystem");
		sendNotification(_allocation, _user, _language, systemId);
	}
	
	private void validateReview(AllocationBean _allocation, UserBean _user, LanguageBean _language,
			Map<String, Object> params)
			throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		if(_allocation.getReviewedAllocation()==null)
			throw new ValidationException(msgs.getString("all_rev_allocation_shipper_not_informed_error"));

		if(_allocation.getReviewedAllocation().compareTo(new BigDecimal(0)) < 0)
			throw new ValidationException(msgs.getString("all_rev_allocation_shipper_negative_error"));
		
		// Se comprueba que el dia GasDay debe pertenecer al periodo con balance abierto 
		//(por si se cierra el mes mientras estamos en la pantalla).
		if (!inReviewPeriod(_allocation.getGasDay(), _user, _language, params))
			throw new ValidationException(msgs.getString("all_rev_allocation_gasday_out_of_period_error"));

		if(versionHasChanged(_allocation))
			throw new ValidationException(msgs.getString("all_rev_allocation_review_changed_error"));
	}

	// periodo [max(Hoy - ALLOCATION.REVIEW.OPEN.DAYS, primer_dia_abierto); Hoy], 
	// donde ALLOCATION.REVIEW.OPEN.DAYS es el parámetro de sistema
	private boolean inReviewPeriod(Date _gasDay, UserBean _user, LanguageBean _language, Map<String, Object> params)
			throws Exception {
		Date openPeriodFirstDay = null;
		Integer allocationReviewOpenDays = null;
		Date reviewPeriodStartDate = null;
		Date reviewPeriodEndDate = null;
		
		// Primer dia de balance abierto.
		openPeriodFirstDay = balMagService.selectOpenPeriodFirstDay(params);
		allocationReviewOpenDays = selectAllocationReviewOpenDays(_user.getUsername(), _language.getLocale(),
				params.get("sysCode").toString());
    	
		Calendar tmpToday = Calendar.getInstance();
		tmpToday.set(Calendar.HOUR_OF_DAY, 0);
		tmpToday.set(Calendar.MINUTE, 0);
		tmpToday.set(Calendar.SECOND, 0);
		tmpToday.set(Calendar.MILLISECOND, 0);
		
		Calendar tmpStartDate = Calendar.getInstance();
		tmpStartDate.set(Calendar.HOUR_OF_DAY, 0);
		tmpStartDate.set(Calendar.MINUTE, 0);
		tmpStartDate.set(Calendar.SECOND, 0);
		tmpStartDate.set(Calendar.MILLISECOND, 0);
		tmpStartDate.add(Calendar.DAY_OF_MONTH, allocationReviewOpenDays * (-1));
		
		if(tmpStartDate.getTime().after(openPeriodFirstDay))
			reviewPeriodStartDate = tmpStartDate.getTime();
		else
			reviewPeriodStartDate = openPeriodFirstDay;
		
		reviewPeriodEndDate = tmpToday.getTime();
		
		return (reviewPeriodStartDate.compareTo(_gasDay)<=0 && 
				reviewPeriodEndDate.compareTo(_gasDay)>=0); 
	}

	private boolean versionHasChanged(AllocationBean _allocation){
		AllocationBean lastVersionReview = null;
		// Ojo, como no interesa saber la cantidad, no actualizo el factor de conversion.
		List<AllocationBean> lTmp = arMapper.selectLastAllocationReview(_allocation);
		
		// Si no se han obtenido resultados, es porque hasta el momento no hay ninguna version.
		if(lTmp==null || lTmp.size()==0)
			return false;
		
		// Se supone que solo se devuelve 1 elemento. Maxima version.
		lastVersionReview = lTmp.get(0);
		
		// Si no se han obtenido resultados, es porque hasta el momento no hay ninguna version.
		if(lastVersionReview==null)
			return false;
		
		// Si hasta ahora no habia revision en la consulta principal, y hemos llegado hasta aqui porque 
		// al volver a consultar, hemos encontrado una revision, entonces ha cambiado la version.
		if(_allocation.getReviewId()==null)
			return true;
		
		return (_allocation.getReviewId().compareTo(lastVersionReview.getReviewId()) != 0);			
	}
	
	private void saveIncidentReview(AllocationBean _allocation, 
									UserBean _user, 
									LanguageBean _language,
									BigDecimal _factorToDefaultUnit) throws Exception {
		
		_allocation.setStatusCode(strInitialStatus);
		// Solo se genera nuevo codigo si no existia revision previa (con codigo previo) para el dia, contrato y punto. 
		if(_allocation.getReviewCode() == null)
			_allocation.setReviewCode(getNewAllocationReviewCode(_user, _language));
		// Se guarda la cantidad de reparto disponible cuando se crea la nueva version de review.
		_allocation.setOriginalAllocationForReview(_allocation.getAllocationTPA());
		_allocation.setIsWarned(strIsWarnedN);
		_allocation.setConversionFactor(_factorToDefaultUnit);
		
		int res = arMapper.insertAllocationReview(_allocation);
		if(res!=1){
    		throw new Exception("Error inserting into Allocation Review table.");   		
    	}
	}
	
	private String getNewAllocationReviewCode(UserBean _user, LanguageBean _language) throws Exception {
		
		ElementIdBean tmpBean = new ElementIdBean();
		
		tmpBean.setGenerationCode(Constants.ALLOCATION_REVIEW_POINT);
		// Si se deja la fecha a nulo, en BD se toma systemdate.
		tmpBean.setDate(null);
		tmpBean.setUser(_user.getUsername());
		tmpBean.setLanguage(_language.getLocale());

		sysParMapper.getElementId(tmpBean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(tmpBean == null || (tmpBean.getIntegerExit() != 0))
			throw new Exception(tmpBean.getErrorDesc());
		
		return tmpBean.getId();
	}

	private void sendNotification(AllocationBean _allocation, UserBean _user, LanguageBean _language,
			BigDecimal systemId) throws Exception {
    	int res = 0;
   	
		// Se genera la notificacion para el shipper, correspondiente a que el operador ha procesado su peticion de contrato.
		NotificationBean notif = new NotificationBean();
		notif.setType_code(strNotifTypeAllocationReviewGenerated);	
		notif.setOrigin(strNotifOrigin);
		notif.setSystemId(systemId);
		// Para un mensaje como "The allocation for {1}, {2} and {3} has been reviewed by shipper (Review Code: {4})."
		// se pasa como parametro el día de gas afectado, el contrato afectado,
		// el punto afectado y el código de propuesta.
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	String notifInfo = sdf.format(_allocation.getGasDay()) + strNotifSeparator +
    						_allocation.getContractCode() + strNotifSeparator +
    						_allocation.getNomPointCode() + strNotifSeparator +
    						_allocation.getReviewCode();	
		notif.setInformation(notifInfo);

		notif.setUser_id(_user.getUsername());
		notif.setIdn_user_group(getDefaultOperatorId(_user, _language));
		notif.setLanguage(_language.getLocale());
		
		notifMapper.getCreateNotification(notif);
		if( notif==null || notif.getInteger_exit()==null ){
			throw new Exception("Error sending notification to operator.");
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
	
	public BigDecimal getDefaultOperatorId(UserBean _user, LanguageBean _lang) throws Exception{
		String defOperatorCode;
		BigDecimal defOperatorId = null;
		
		if(defOperatorId == null) {
			defOperatorCode = getSystemParameter(strDefOperatorParamName, _user, _lang);
			List<BigDecimal> lTmpIds = arMapper.selectGroupIdFromGroupCode(defOperatorCode);
			defOperatorId = lTmpIds.get(0);
		}
		
		return defOperatorId;
	}
	
	private String getSystemParameter(String str, UserBean _user, LanguageBean _lang) throws Exception{
		String resParam = null;
		
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(sysParMapper.getSysdate().get(0));
		bean.setParameter_name(str);
		bean.setUser_id(_user.getUsername());
		bean.setLanguage(_lang.getLocale());
		sysParMapper.getStringSystemParameter(bean);
		if( bean==null || bean.getString_exit()==null ){
			// Se envia error tecnico para no mostrar error al usuario.
			throw new Exception("Error getting system parameter.");
		}
		resParam = bean.getString_exit();
	
		return resParam;
	}

	public BalanceManagementService getBalMagService() {
		return balMagService;
	}

	public void setBalMagService(BalanceManagementService balMagService) {
		this.balMagService = balMagService;
	}

}
