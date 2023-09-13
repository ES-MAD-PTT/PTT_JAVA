package com.atos.services.booking;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ElementIdBean;
import com.atos.beans.booking.CapacityRequestBean;
import com.atos.beans.booking.ContractAgreementBean;
import com.atos.beans.booking.ContractPointBean;
import com.atos.beans.booking.ReleaseCapacitySubmissionBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.booking.ReleaseCapacitySubmissionFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.booking.ReleaseCapacitySubmissionMapper;
import com.atos.utils.Constants;


@Service("relCapSubService")
public class ReleaseCapacitySubmissionServiceImpl implements ReleaseCapacitySubmissionService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4768642409448920399L;

	private static final String strYearLabel = "AAA";
	
	@Autowired
	private ReleaseCapacitySubmissionMapper rcsMapper;

	@Autowired
	private SystemParameterMapper sysParMapper;
	

	public Map<BigDecimal, Object> selectShipperIdByUserId(String userId) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = rcsMapper.selectShipperIdByUserId(userId);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}

	
	/*public Map<BigDecimal, Object> selectContracts(BigDecimal shipperId) {	
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = rcsMapper.selectContractsByShipperId(shipperId);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}*/
	public Map<BigDecimal, Object> selectContracts(BigDecimal shipperId, BigDecimal idn_system) {	
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = rcsMapper.selectContractsByShipperId(shipperId,idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}

	
	public Map<BigDecimal, Object> selectSystemPoints(BigDecimal contractId) {	
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = rcsMapper.selectSystemPointsByContractId(contractId);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}

	
	public List<Integer> selectAgreementStartYears(BigDecimal contractId) {	

		return rcsMapper.selectAgreementStartYearsByContractId(contractId);
	}

	
	public List<ReleaseCapacitySubmissionBean> search(ReleaseCapacitySubmissionFilter filter) {
		
		return rcsMapper.selectReleaseCapacitySubmissionPoints(filter);
	}


	// Se anota aqui como transaccional porque Spring requiere que sea un metodo publico, expuesto en el interfaz.
	// https://docs.spring.io/spring/docs/current/spring-framework-reference/html/transaction.html
	// Anteriormente las transacciones se hacian mas internamente; mover la transaccion aqui es equivalente porque no se pueden producir
	// excepciones despues de las transacciones que habia hasta ahora, que provoquen nuevos rollbacks no previstos hasta ahora.	
	@Transactional( rollbackFor = { Throwable.class })
	public void save(BigDecimal shipperId, BigDecimal contractId, List<ReleaseCapacitySubmissionBean> lrcsBeans, BigDecimal idn_system, boolean toOperator) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
		CapacityRequestBean tmpCapReqBean = null;
		
		validate(lrcsBeans);
		
		tmpCapReqBean = prepareCapacityRequest(shipperId, contractId, lrcsBeans);
		
		if( tmpCapReqBean == null ){
			throw new ValidationException(msgs.getString("no_data"));
		}
		else {
			//offshore
			tmpCapReqBean.setIdn_system(idn_system);
			if(toOperator)
				tmpCapReqBean.setToOperator("Y");
			else tmpCapReqBean.setToOperator("N");
			insert(tmpCapReqBean);
		}
	}


	
	private void validate(List<ReleaseCapacitySubmissionBean> lrcsBeans) throws Exception {
		validatePositiveQuantityVolume(lrcsBeans);
		//validateContractWithoutHoles(lrcsBeans);
		validateContractDifPositive(lrcsBeans);
		validateEntryVsExitPointsCapacity(lrcsBeans);
	}
	
	private void validatePositiveQuantityVolume(List<ReleaseCapacitySubmissionBean> lrcsBeans) throws ValidationException {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		for(ReleaseCapacitySubmissionBean rcsBean : lrcsBeans) {
			Float myReleaseBBTuDay = rcsBean.getReleaseBBTuDay();
			Float myReleaseMMscfd = rcsBean.getReleaseMMscfd();

			if( (myReleaseBBTuDay == null) && (myReleaseMMscfd != null))
				throw new ValidationException(msgs.getString("rel_cap_sub_validation_release_volume_without_quantity") + " " +
						rcsBean.getSystemPointCode() + ").");	
			if( ("ENTRY".equalsIgnoreCase(rcsBean.getPointTypeCode())) && (myReleaseBBTuDay != null) && (myReleaseMMscfd == null))
				throw new ValidationException(msgs.getString("rel_cap_sub_validation_release_quantity_without_volume") + " " +
						rcsBean.getSystemPointCode() + ").");	
			if( (myReleaseBBTuDay != null && myReleaseBBTuDay == 0) || 
					(myReleaseMMscfd != null && myReleaseMMscfd == 0))
				throw new ValidationException(msgs.getString("rel_cap_sub_validation_positive_values"));
		}
	}
	
	public Date getStartDate(BigDecimal idContract) {
		return rcsMapper.getStartDate(idContract);
	}
	
	public Date getEndDate(BigDecimal idContract) {
		return rcsMapper.getEndDate(idContract);
	}
	
	private void validateContractDifPositive(List<ReleaseCapacitySubmissionBean> lrcsBeans) throws Exception {
		//Se comprueba que la diferencia entre lo contratado y la release sea mayor que 0 para energía y volumen
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		for(ReleaseCapacitySubmissionBean rcsBean : lrcsBeans) {
			if(rcsBean.getReleaseBBTuDay()!=null){ 
				if(rcsBean.getContratedBBTuDay()-rcsBean.getReleaseBBTuDay()<=0) {
					throw new ValidationException(msgs.getString("rel_cap_sub_validation_dif_positive"));				}
			}
			if(rcsBean.getReleaseMMscfd()!=null){ 
				if(rcsBean.getContratedMMscfd()-rcsBean.getReleaseMMscfd()<=0) {
					throw new ValidationException(msgs.getString("rel_cap_sub_validation_dif_positive"));
				}
			}
		}
	}

	//NO SE USA YA. SE USABA ANTES CUANDO SE TRABAJABA CON AÑOS EN VEZ DE PERIODOS
	private void validateContractWithoutHoles_OldWithYears(List<ReleaseCapacitySubmissionBean> lrcsBeans) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	// Este arbol contendra un elemento por punto (systemPointId), y ese elemento sera una treemap con los
    	// beans asociados al punto, organizados por fecha.
    	// Es una estructura que solo se va a usar para leer los datos.
    	TreeMap<BigDecimal, TreeMap<Integer, ReleaseCapacitySubmissionBean>> tmpAllData = 
    			new TreeMap<BigDecimal, TreeMap<Integer, ReleaseCapacitySubmissionBean>>();
		BigDecimal tmpSystemPointId  = null;
		TreeMap<Integer, ReleaseCapacitySubmissionBean> tmpPointData = null;
    	ReleaseCapacitySubmissionBean tmpRcsBean = null;
    	Float tmpContratedBBTuDay = null;
    	Float tmpReleaseBBTuDay = null;
    	Float tmpContratedMMscfd = null;
    	Float tmpReleaseMMscfd = null;
    	
    	// Para mostrar en mensajes a usuario codigos de puntos, en lugar de identificadores.
    	TreeMap<BigDecimal, String> tmpSystemPointCodes = new TreeMap<BigDecimal, String>();
    	
    	// Para separar todos los beans por puntos.
		for(ReleaseCapacitySubmissionBean rcsBean : lrcsBeans) {
			
			tmpSystemPointId = rcsBean.getSystemPointId();

			if( !tmpAllData.containsKey(tmpSystemPointId)) {
				tmpPointData = new TreeMap<Integer, ReleaseCapacitySubmissionBean>();
				//MMCFtmpPointData.put(rcsBean.getYear(), rcsBean);
				tmpAllData.put(tmpSystemPointId, tmpPointData);
				
				tmpSystemPointCodes.put(tmpSystemPointId, rcsBean.getSystemPointCode());
			}
			else {
				tmpPointData = tmpAllData.get(tmpSystemPointId);
				/*MMCFif( tmpPointData.containsKey(rcsBean.getYear()))
					throw new Exception(msgs.getString("rel_cap_sub_duplicated_year_data_error") + " " +
							rcsBean.getSystemPointCode() + ").");
				
				tmpPointData.put(rcsBean.getYear(), rcsBean);	MMCF*/					
			}
		}
		
		// Se definen arrays con una posicicion para controlar los datos de cada punto.
		// Con estos arrays se detectará cuando se pasa de que la suma de cantidades
		// contratadas y para las que se ha pedido release, pase de cero a distinto de cero
		// y viceversa con el fin de obtener huecos de contratacion.
		// Si se pasa de tener cantidad ( iEstadoConCantidad ), a no tener cantidad ( iEstadoSinCantidad )
		// y finalmente a volver a tener cantidad entonces se ha detectado un hueco.
		final int iEstadoInicial = 0;
		final int iEstadoConCantidad = 1;
		final int iEstadoSinCantidad = 2;

		int[] aiBBTuDayEstado = new int[tmpAllData.size()];
		int[] aiMMscfdEstado = new int[tmpAllData.size()];
		for(int i=0; i<tmpAllData.size(); i++){
			aiBBTuDayEstado[i] = iEstadoInicial;
			aiMMscfdEstado[i] = iEstadoInicial;
		}		

		// Contador de datos de puntos.
		int i = 0; 
    	for (BigDecimal tmpSystemPointId2: tmpAllData.keySet())
    	{		
    		tmpPointData = tmpAllData.get(tmpSystemPointId2);

			int smallestYear = Collections.min(tmpPointData.keySet());
			
			// Se buscan huecos entre los beans.
			// Se obtienen los beans asociados a años consecutivos empezando por el mas pequeño.
			for(int j=smallestYear; j<(smallestYear + tmpPointData.size()); j++) {
				tmpRcsBean = tmpPointData.get(j);
				// Si no existieran datos para un año del intervalo.
				if( tmpRcsBean == null )
					throw new Exception(msgs.getString("rel_cap_sub_missing_year_data_error") + " " +
							tmpSystemPointCodes.get(tmpSystemPointId2) + " " +
								msgs.getString("rel_cap_sub_year") + " " + j + ".");					
				
		    	tmpContratedBBTuDay = tmpRcsBean.getContratedBBTuDay();
		    	tmpReleaseBBTuDay = tmpRcsBean.getReleaseBBTuDay();
		    	tmpContratedMMscfd = tmpRcsBean.getContratedMMscfd();
		    	tmpReleaseMMscfd = tmpRcsBean.getReleaseMMscfd();
				
		    	// A la hora de valorar huecos, se consideran las cantidades nulas como ceros.
		    	tmpContratedBBTuDay = (tmpContratedBBTuDay != null) ? tmpContratedBBTuDay : new Float(0);
		    	tmpReleaseBBTuDay = (tmpReleaseBBTuDay != null) ? tmpReleaseBBTuDay : new Float(0);
		    	tmpContratedMMscfd = (tmpContratedMMscfd != null) ? tmpContratedMMscfd : new Float(0);
		    	tmpReleaseMMscfd = (tmpReleaseMMscfd != null) ? tmpReleaseMMscfd : new Float(0);
		    	
				Float fBBTuDayNeto = tmpContratedBBTuDay - tmpReleaseBBTuDay;
				Float fMMscfdNeto = tmpContratedMMscfd - tmpReleaseMMscfd;
				
				if( (fBBTuDayNeto < 0) || (fMMscfdNeto < 0) )
					throw new ValidationException(msgs.getString("rel_cap_sub_validation_too_much_released") + " " +
							tmpRcsBean.getSystemPointCode() + " " +
								msgs.getString("rel_cap_sub_year") + " " + j + ".");	

				// COMPROBACION para quantity.					
				if(aiBBTuDayEstado[i] == iEstadoInicial && fBBTuDayNeto > 0)
					aiBBTuDayEstado[i] = iEstadoConCantidad;
				
				if(aiBBTuDayEstado[i] == iEstadoConCantidad && fBBTuDayNeto == 0)
					aiBBTuDayEstado[i] = iEstadoSinCantidad;
				
				// Aqui se ha detectado un hueco.
				if(aiBBTuDayEstado[i] == iEstadoSinCantidad && fBBTuDayNeto > 0)
					throw new ValidationException(msgs.getString("rel_cap_sub_validation_time_holes") + " " +
							tmpRcsBean.getSystemPointCode() + ").");
				
				// COMPROBACION para volumen.
				if(aiMMscfdEstado[i] == iEstadoInicial && fMMscfdNeto > 0)
					aiMMscfdEstado[i] = iEstadoConCantidad;
				
				if(aiMMscfdEstado[i] == iEstadoConCantidad && fMMscfdNeto == 0)
					aiMMscfdEstado[i] = iEstadoSinCantidad;
				
				// Aqui se ha detectado un hueco.
				if(aiMMscfdEstado[i] == iEstadoSinCantidad && fMMscfdNeto > 0)
					throw new ValidationException(msgs.getString("rel_cap_sub_validation_time_holes") + " " +
							tmpRcsBean.getSystemPointCode() + ").");				

			}	// Bucle de cada lista
			
			i++;
		}	// Bucle de datos/listas asociados a puntos.
	}

	
	private void validateEntryVsExitPointsCapacity_OldWithYears(List<ReleaseCapacitySubmissionBean> lrcsBeans) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	// Este arboles contendran el acumulado de cantidades por año.
    	TreeMap<Integer, Float> tmpReleaseBBTuDaybyYearEntry = new TreeMap<Integer, Float>();
    	TreeMap<Integer, Float> tmpReleaseBBTuDaybyYearExit = new TreeMap<Integer, Float>();
		// MMscfd: Esta unidad no se valida porque en puntos de salida no se puede especificar, 
		// de manera que el volumen no es comparable con el de los puntos de entrada.
   	
		Integer tmpYear  = null;
		Float tmpFloat = null;
    	
    	// Se recopilan los datos.
		for(ReleaseCapacitySubmissionBean rcsBean : lrcsBeans) {
			if("ENTRY".equalsIgnoreCase(rcsBean.getPointTypeCode())){
				
				// Solo se anotan capacidades en los puntos en los que se ha liberado capacidad.
				if( rcsBean.getReleaseBBTuDay() != null){
					
					//MMCFtmpYear = rcsBean.getYear();
					if(! tmpReleaseBBTuDaybyYearEntry.containsKey(tmpYear)) {
						tmpReleaseBBTuDaybyYearEntry.put(tmpYear, rcsBean.getReleaseBBTuDay());
					} 
					else {
						tmpFloat = new Float(tmpReleaseBBTuDaybyYearEntry.get(tmpYear).floatValue());
						tmpFloat += rcsBean.getReleaseBBTuDay();
						tmpReleaseBBTuDaybyYearEntry.put(tmpYear, tmpFloat);
					}
					
				}
				
			} else if ("EXIT".equalsIgnoreCase(rcsBean.getPointTypeCode())){

				// Solo se anotan capacidades en los puntos en los que se ha liberado capacidad.				
				if( rcsBean.getReleaseBBTuDay() != null){
					
					//MMCFtmpYear = rcsBean.getYear();
					if(! tmpReleaseBBTuDaybyYearExit.containsKey(tmpYear)) {
						tmpReleaseBBTuDaybyYearExit.put(tmpYear, rcsBean.getReleaseBBTuDay());
					} 
					else {
						tmpFloat = new Float(tmpReleaseBBTuDaybyYearExit.get(tmpYear).floatValue());
						tmpFloat += rcsBean.getReleaseBBTuDay();
						tmpReleaseBBTuDaybyYearExit.put(tmpYear, tmpFloat);
					}
					
				}							
				
			} else {
				throw new Exception(msgs.getString("rel_cap_sub_invalid_point_type") + " " +
						rcsBean.getSystemPointCode() + ").");				
			}
		}

		
		// Se compara entre Entry y exit. Año por año.
		// Se comparan los datos de todos los años. Si no hubiera dato para un año, se entiende que es cero.
		int smallestYear = -1;
		int biggestYear = -1;
		Float tmpReleaseValueEntry = null;
		Float tmpReleaseValueExit = null;
		
		// BBTuDay
		if(tmpReleaseBBTuDaybyYearEntry.keySet().size() > 0){
			
			if(tmpReleaseBBTuDaybyYearExit.keySet().size() > 0){
				
				smallestYear = Collections.min(tmpReleaseBBTuDaybyYearEntry.keySet());
				tmpYear = Collections.min(tmpReleaseBBTuDaybyYearExit.keySet());
				// Se toma el mayor de los mas pequeños. Sera el menor año con datos en los dos arrays.
				smallestYear = ( smallestYear < tmpYear ) ? smallestYear : tmpYear;
				
				biggestYear = Collections.max(tmpReleaseBBTuDaybyYearEntry.keySet());
				tmpYear = Collections.max(tmpReleaseBBTuDaybyYearExit.keySet());
				biggestYear = ( biggestYear > tmpYear ) ? biggestYear : tmpYear;
		
				for(int i=smallestYear; i<=biggestYear; i++) {
					// Se intenta recuperar los valores agregados para todos los años. Si no hay valor para un año, se toma como cero.
					tmpReleaseValueEntry = tmpReleaseBBTuDaybyYearEntry.get(i);
					tmpReleaseValueEntry = (tmpReleaseValueEntry != null) ? tmpReleaseValueEntry : new Float(0);
					tmpReleaseValueExit = tmpReleaseBBTuDaybyYearExit.get(i);
					tmpReleaseValueExit = (tmpReleaseValueExit != null) ? tmpReleaseValueExit : new Float(0);
					
					// Se lanza excepcion si el acumulado de capacidad liberada en puntos de entrada NO es menor o igual
					// que el acumulado en puntos de salida. 
					// Solo para los puntos en los que se haya liberado capacidad. No para todo el contrato.
					if( tmpReleaseValueEntry.compareTo(tmpReleaseValueExit) > 0 ) {
						String errorMsg = msgs.getString("rel_cap_sub_validation_entry_vs_exit"); 
						errorMsg = errorMsg.replace(strYearLabel, (new Integer(i)).toString());
						throw new ValidationException(errorMsg);
					}
				}
			} 
			else {	// Si se ha liberado capacidad en puntos de entrada y no hay liberacion para 
					// puntos de salida, se lanza excepcion. 
					// La capacidad liberada en puntos de entrada no puede ser cero, por una validacion anterior.
				throw new ValidationException(msgs.getString("rel_cap_sub_validation_entry_vs_exit2"));	
			}			
		}
		// Si no se ha liberado capacidad en puntos de entrada, se supone que se ha liberado solo en  
		// puntos de salida (en la vista se comprueba que se haya hecho algun cambio antes de llamar a 
		// service.save(). 
		// No hay problema en liberar capacidad solo en puntos de salida, por lo cual no se lanza error.
			
		// MMscfd: Esta unidad no se valida porque en puntos de salida no se puede especificar, 
		// de manera que el volumen no es comparable con el de los puntos de entrada.
	}
	
	
	private void validateEntryVsExitPointsCapacity(List<ReleaseCapacitySubmissionBean> lrcsBeans) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	// Este arboles contendran el acumulado de cantidades por año.
    	TreeMap<String, Float> tmpReleaseBBTuDaybyYearEntry = new TreeMap<String, Float>();
    	TreeMap<String, Float> tmpReleaseBBTuDaybyYearExit = new TreeMap<String, Float>();
		// MMscfd: Esta unidad no se valida porque en puntos de salida no se puede especificar, 
		// de manera que el volumen no es comparable con el de los puntos de entrada.
   	
		String tmpPeriod  = null;
		Float tmpFloat = null;
    	
    	// Se recopilan los datos.
		for(ReleaseCapacitySubmissionBean rcsBean : lrcsBeans) {
			if("ENTRY".equalsIgnoreCase(rcsBean.getPointTypeCode())){
				
				// Solo se anotan capacidades en los puntos en los que se ha liberado capacidad.
				if( rcsBean.getReleaseBBTuDay() != null){
					
					tmpPeriod = rcsBean.getAgreementStartDate()+"/"+rcsBean.getAgreementEndDate();
					if(! tmpReleaseBBTuDaybyYearEntry.containsKey(tmpPeriod)) {
						tmpReleaseBBTuDaybyYearEntry.put(tmpPeriod, rcsBean.getReleaseBBTuDay());
					} 
					else {
						tmpFloat = new Float(tmpReleaseBBTuDaybyYearEntry.get(tmpPeriod).floatValue());
						tmpFloat += rcsBean.getReleaseBBTuDay();
						tmpReleaseBBTuDaybyYearEntry.put(tmpPeriod, tmpFloat);
					}
					
				}
				
			} else if ("EXIT".equalsIgnoreCase(rcsBean.getPointTypeCode())){

				// Solo se anotan capacidades en los puntos en los que se ha liberado capacidad.				
				if( rcsBean.getReleaseBBTuDay() != null){
					
					tmpPeriod = rcsBean.getAgreementStartDate()+"/"+rcsBean.getAgreementEndDate();
					if(! tmpReleaseBBTuDaybyYearExit.containsKey(tmpPeriod)) {
						tmpReleaseBBTuDaybyYearExit.put(tmpPeriod, rcsBean.getReleaseBBTuDay());
					} 
					else {
						tmpFloat = new Float(tmpReleaseBBTuDaybyYearExit.get(tmpPeriod).floatValue());
						tmpFloat += rcsBean.getReleaseBBTuDay();
						tmpReleaseBBTuDaybyYearExit.put(tmpPeriod, tmpFloat);
					}
					
				}							
				
			} else {
				throw new Exception(msgs.getString("rel_cap_sub_invalid_point_type") + " " +
						rcsBean.getSystemPointCode() + ").");				
			}
		}

		
		// Se compara entre Entry y exit. Periodo por Periodo.
		// Se comparan los datos de todos los periodos. Si no hubiera dato para un periodos, se entiende que es cero.
		
		Float tmpReleaseValueEntry = null;
		Float tmpReleaseValueExit = null;
		
		// BBTuDay
				if(tmpReleaseBBTuDaybyYearEntry.keySet().size() > 0){
					
					if(tmpReleaseBBTuDaybyYearExit.keySet().size() > 0){
						Iterator<String> it = tmpReleaseBBTuDaybyYearEntry.keySet().iterator();
						
						while(it.hasNext()) {
							String key = it.next();
							
							tmpReleaseValueEntry = tmpReleaseBBTuDaybyYearEntry.get(key);
							tmpReleaseValueEntry = (tmpReleaseValueEntry != null) ? tmpReleaseValueEntry : new Float(0);
							tmpReleaseValueExit = tmpReleaseBBTuDaybyYearExit.get(key);
							tmpReleaseValueExit = (tmpReleaseValueExit != null) ? tmpReleaseValueExit : new Float(0);
							
							// Se lanza excepcion si el acumulado de capacidad liberada en puntos de entrada NO es menor o igual
							// que el acumulado en puntos de salida. 
							// Solo para los puntos en los que se haya liberado capacidad. No para todo el contrato.
							if( tmpReleaseValueEntry.compareTo(tmpReleaseValueExit) != 0 ) {
								String errorMsg = msgs.getString("rel_cap_sub_validation_entry_vs_exit"); 
								throw new ValidationException(errorMsg);
							}
						}
					} 
					else {	// Si se ha liberado capacidad en puntos de entrada y no hay liberacion para 
							// puntos de salida, se lanza excepcion. 
							// La capacidad liberada en puntos de entrada no puede ser cero, por una validacion anterior.
						throw new ValidationException(msgs.getString("rel_cap_sub_validation_entry_vs_exit2"));	
					}			
				}
		
		// Si no se ha liberado capacidad en puntos de entrada, se supone que se ha liberado solo en  
		// puntos de salida (en la vista se comprueba que se haya hecho algun cambio antes de llamar a 
		// service.save(). 
		// No hay problema en liberar capacidad solo en puntos de salida, por lo cual no se lanza error.
			
		// MMscfd: Esta unidad no se valida porque en puntos de salida no se puede especificar, 
		// de manera que el volumen no es comparable con el de los puntos de entrada.
	}


	
	// Con este metodo se crea un arbol de capacityRequest - contractAgreement - contractPoint, para que luego sea
	// mas facil hacer los inserts en BD.
	private CapacityRequestBean prepareCapacityRequest(BigDecimal shipperId, 
														BigDecimal contractId, 
														List<ReleaseCapacitySubmissionBean> lrcsBeans) throws Exception {    	

		CapacityRequestBean tmpCapReqBean = null;
		TreeMap<Integer, ContractAgreementBean> tmpContractAgreements = null;
		int tmpContractAgreementsSize = -1;
		ContractAgreementBean tmpContAgreeBean = null;
		boolean tmpContAgreeBeanNew = false;
		Date tmpAgreeStartDate = null;
		Date tmpAgreeEndDate = null;
		TreeMap<Integer, ContractPointBean> tmpContractPoints = null;
		int tmpContractPointsSize = -1;
		ContractPointBean tmpContPointBean = null;
		Float tmpReleaseBBTuDay = null;
		Float tmpReleaseMMscfd = null;
		
		// El operationId es comun para todos los registros, puesto que es unico en el contrato, y el contrato se ha fijado con el filtro de la pagina.
		BigDecimal tmpOperationId = null;
		// Estas fechas corresponderan a la menor StartDate y a la mayor EndDate de las fechas de los agreements. 
		Date tmpCapReqStartDate = null;
		Date tmpCapReqEndDate = null;
			
		for(ReleaseCapacitySubmissionBean rcsBean : lrcsBeans) {

			tmpReleaseBBTuDay = rcsBean.getReleaseBBTuDay();
			tmpReleaseMMscfd = rcsBean.getReleaseMMscfd();
				
			// En validatePositiveQuantityVolume, se ha validado que siempre que hay release de volumen, tambien haya de quantity.
			// De este modo, para todos los datos para los que se haya pedido release, siempre va a venir volumen. Y si no, es que no se ha pedido 
			// release sobre el punto y año.
			// Se obtienen solo los datos que ha actualizado/modificado el usuario.
			if( tmpReleaseBBTuDay != null ) {
				
				// Este valor solo hace falta generarlo una vez.
				if( tmpOperationId == null)
					tmpOperationId = rcsBean.getOperationId();
				
				tmpContPointBean = new ContractPointBean();
				tmpContPointBean.setSystemPointId(rcsBean.getSystemPointId());
				tmpContPointBean.setQuantity(tmpReleaseBBTuDay * (-1));
				tmpContPointBean.setMaxHourQuantity(new Float(0));
				if(tmpReleaseMMscfd != null)							// Puede ocurrir que no se especifique el volumen.
					tmpContPointBean.setVolume(tmpReleaseMMscfd * (-1));
				tmpContPointBean.setMaxHourVolume(new Float(0));
				// releasedToId, receivedFromId y meterinPoint, no se rellenan. Se quedan null.
				// isNewConnection no se rellena. Se queda null en el bean y 'N' en base de datos.
				
				if( tmpContractAgreements == null) 
					tmpContractAgreements = new TreeMap<Integer, ContractAgreementBean>();
				
				tmpContractAgreementsSize = tmpContractAgreements.size();
				
				contract_agreement_loop:
				// Para buscar el tmpContAgreeBean para asociarle el punto.
				for(Integer i : tmpContractAgreements.keySet()) {
					tmpContAgreeBean = tmpContractAgreements.get(i);
					tmpAgreeStartDate = tmpContAgreeBean.getStartDate();
					tmpAgreeEndDate = tmpContAgreeBean.getEndDate();
					
					if((tmpAgreeStartDate.compareTo(rcsBean.getAgreementStartDate()) == 0) &&
						(tmpAgreeEndDate.compareTo(rcsBean.getAgreementEndDate()) == 0) ) {
						// Encontre el agreement. Se utiliza el guardado en tmpContAgreeBean
						break contract_agreement_loop;
					}
					else {	// Todavia no he encontrado el agreement
						tmpContAgreeBean = null;
						continue contract_agreement_loop;
					}
				}
				
				if(tmpContAgreeBean == null) { // Hay que crear un nuevo tmpContAgreeBean
					
					tmpAgreeStartDate = rcsBean.getAgreementStartDate();
					tmpAgreeEndDate = rcsBean.getAgreementEndDate();
					
					tmpContAgreeBean = new ContractAgreementBean();
					tmpContAgreeBean.setStartDate(tmpAgreeStartDate);
					tmpContAgreeBean.setEndDate(tmpAgreeEndDate);

					// Se actualizan las fechas (extremos) para la Capacity Request.
					if( tmpCapReqStartDate ==  null )
						tmpCapReqStartDate = tmpAgreeStartDate;
					else
						tmpCapReqStartDate = ( tmpCapReqStartDate.before(tmpAgreeStartDate) )? tmpCapReqStartDate : tmpAgreeStartDate;
					
					if( tmpCapReqEndDate ==  null )
						tmpCapReqEndDate = tmpAgreeEndDate;
					else
						tmpCapReqEndDate = ( tmpCapReqEndDate.after(tmpAgreeEndDate) )? tmpCapReqEndDate : tmpAgreeEndDate;
					
						
					tmpContAgreeBeanNew = true;
				}
				
				tmpContractPoints = tmpContAgreeBean.getTmContractPoints();
				if(tmpContractPoints == null)
					tmpContractPoints = new TreeMap<Integer, ContractPointBean>();
				
				tmpContractPointsSize = tmpContractPoints.size();
				
				tmpContractPoints.put(tmpContractPointsSize, tmpContPointBean);
				tmpContAgreeBean.setTmContractPoints(tmpContractPoints);			// Se hace set del mapa y se machaca el anterior que hubiera.
				
				if(tmpContAgreeBeanNew) {
					tmpContractAgreements.put(tmpContractAgreementsSize, tmpContAgreeBean);
					// Hay que dejar el flag en el valor que estaba.
					tmpContAgreeBeanNew = false;
				}
				
			}	// if  - recoge datos de las filas en las que hay valor para las cantidades release.
		}		// for - recorre todas las filas
		
		// Si se han generado datos anteriormente, se guardan en el objeto CapacityRequestBean de salida.
		// Si no, se devuelve un null.
		if(tmpContractAgreements != null) {
			tmpCapReqBean = new CapacityRequestBean();
			tmpCapReqBean.setRequestCode(getNewCapacityRequestCode());
			tmpCapReqBean.setShipperId(shipperId);
			tmpCapReqBean.setContractTypeId(tmpOperationId);
			tmpCapReqBean.setContractId(contractId);
			tmpCapReqBean.setContractStartDate(tmpCapReqStartDate);
			tmpCapReqBean.setContractEndDate(tmpCapReqEndDate);
			tmpCapReqBean.setTmContractAgreements(tmpContractAgreements);	
		}
		
		return tmpCapReqBean;
	}

	
	private String getNewCapacityRequestCode() throws Exception {
		
		ElementIdBean tmpBean = new ElementIdBean();
		tmpBean.setGenerationCode(Constants.BOOKING_CAPACITY);
		// Si se deja la fecha a nulo, en BD se toma systemdate.
		tmpBean.setDate(null);
		tmpBean.setUser((String)SecurityUtils.getSubject().getPrincipal());
		tmpBean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());

		sysParMapper.getElementId(tmpBean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(tmpBean == null || (tmpBean.getIntegerExit() != 0))
			throw new Exception(tmpBean.getErrorDesc());
		
		return tmpBean.getId();
	}

	// Se mueve la anotacion mas arriba, porque Spring requiere que sea un metodo publico, expuesto en el interfaz.	
	//@Transactional( rollbackFor = { Throwable.class })	
	private void insert(CapacityRequestBean crb) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");		
		TreeMap<Integer, ContractAgreementBean> tmpContractAgreements = null;
		ContractAgreementBean tmpContAgreeBean = null;
		TreeMap<Integer, ContractPointBean> tmpContractPoints = null;
		ContractPointBean tmpContPointBean = null;
		
		//Llamar al getRequestCode y guardar en crb.setRequestCode(requestCode);
		
    	int res = rcsMapper.insertContractRequest(crb);
		if(res!=1){
    		throw new Exception(msgs.getString("insert_error") + " " + msgs.getString("rel_cap_sub_contract_request"));   		
    	}
		
		tmpContractAgreements = crb.getTmContractAgreements();
		for(Integer i : tmpContractAgreements.keySet()) {
			tmpContAgreeBean = tmpContractAgreements.get(i);
			// El identificador del contractRequest se establece en el insert anterior. En este paso se incluye en contractAgreement.
			tmpContAgreeBean.setIdnContractRequest(crb.getId());
			
			
			res = rcsMapper.insertContractAgreement(tmpContAgreeBean);
			if(res!=1){
	    		throw new Exception(msgs.getString("insert_error") + " " + msgs.getString("rel_cap_sub_contract_agreement"));   		
	    	}
			
			tmpContractPoints = tmpContAgreeBean.getTmContractPoints();
			for(Integer j : tmpContractPoints.keySet()) {
				tmpContPointBean = tmpContractPoints.get(j);
				// El identificador del contractAgreement se establece en el insert anterior. En este paso se incluye en contractPoint.
				tmpContPointBean.setContractAgreementId(tmpContAgreeBean.getIdnContractAgreement());
				
				res = rcsMapper.insertContractPoint(tmpContPointBean);
				if(res!=1){
		    		throw new Exception(msgs.getString("insert_error") + " " + msgs.getString("rel_cap_sub_contract_point"));   		
		    	}
			}
    	}
		
	}
}
