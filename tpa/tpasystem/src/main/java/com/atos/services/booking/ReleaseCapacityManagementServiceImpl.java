package com.atos.services.booking;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.atos.beans.booking.ContractConsolidateBean;
import com.atos.beans.booking.ContractPointBean;
import com.atos.beans.booking.ContractRejectedPointBean;
import com.atos.beans.booking.ReleaseCapacityManagementBean;
import com.atos.beans.booking.ReleaseCapacityManagementInfoMailBean;
import com.atos.beans.booking.ReleaseCapacitySubmissionBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.booking.ReleaseCapacityManagementFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.booking.ReleaseCapacityManagementMapper;
import com.atos.utils.Constants;


@Service("relCapManService")
public class ReleaseCapacityManagementServiceImpl implements ReleaseCapacityManagementService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7023639261431168844L;
	
	@Autowired
	private ReleaseCapacityManagementMapper rcmMapper;

	@Autowired
	private SystemParameterMapper sysParMapper;
	
	private static final String strAccept = "Accept";
	private static final String strReject = "Reject";
	private static final String strPointLabel = "XXX";
	private static final String strContractFromLabel = "YYY";
	private static final String strContractToLabel = "ZZZ";
	private static final String strYearLabel = "AAA";

	
	public Map<BigDecimal, Object> selectShipperId(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = rcmMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	/*public Map<BigDecimal, Object> selectContracts(BigDecimal shipperId) {	
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = rcmMapper.selectContractsByShipperId(shipperId);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	*/
	public Map<BigDecimal, Object> selectContracts(BigDecimal shipperId, BigDecimal idn_system) {	
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = rcmMapper.selectContractsByShipperId(shipperId,idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public List<ReleaseCapacityManagementBean> search(ReleaseCapacityManagementFilter filter) {
		
		return rcmMapper.selectReleaseCapacityManagementRequests(filter);
	}
	
	public List<ReleaseCapacitySubmissionBean> selectDetailsFrom(BigDecimal capacityRequestId) {

		return rcmMapper.selectPointsByCapacityRequestId(capacityRequestId);
	}
	
	
	public List<ReleaseCapacityManagementBean> validate(List<ReleaseCapacityManagementBean> lrcmBeans) throws Exception {
		
		List<ReleaseCapacityManagementBean> lrcmAcceptedBeans = null;
		List<ReleaseCapacityManagementBean> lrcmRejectedBeans = null;		

		
		// Solo se hacen validaciones sobre beans con action Accepted.
		lrcmAcceptedBeans = getBeansByAction(strAccept, lrcmBeans);
		// IMPORTANTE: Dentro de los metodos posteriores se actualiza lrcmAcceptedBeans, al pasarse por referencia.
		// IMPORTANTE2: La primera vez que se envia la peticion a la pantalla, los metodos van agregando informacion a lrcmAcceptedBeans
		// 				PERO si se vuelve a enviar la peticion sin refrescar la pantalla (search o clean), se reutiliza la estructura que 
		// 				ya estaba rellena.
		validateContractToExists(lrcmAcceptedBeans);		// Se ha modificado incluyendo los codigos de contrato To.
		completeDetailsFrom(lrcmAcceptedBeans);					// Completa detalles From.
		// Tras ejecutar este metodo validateComparingContractsFromTo(), para cada ReleaseCapacityManagementBean, se devuelve:
		// - Origen (From), SOLO los puntos en los anos que se estan liberando (los de la request de liberacion)
		// - Destino (To) TODOS los puntos del contrato destino (no solo los que se van a liberar).
		//					Los puntos-fecha que se deban liberar tendran rellena la releaseBBTuDay. 
		validateComparingContractsFromTo(lrcmAcceptedBeans);
		// Tras ejecutar este metodo validateContractsWithoutHoles(), para cada ReleaseCapacityManagementBean, se tiene:
		// - Origen (From), SOLO los puntos que se estan liberando (los de la request de liberacion) en todos los anos.
		// - Destino (To) TODOS los puntos del contrato destino (no solo los que se van a liberar).
		//					Los puntos-fecha que se deban liberar tendran rellena la releaseBBTuDay.
		validateContractsWithoutHoles(lrcmAcceptedBeans);
		
		lrcmRejectedBeans = getBeansByAction(strReject, lrcmBeans);
		completeDetailsFrom(lrcmRejectedBeans);
		// Se anaden los beans con accion Reject a los de Accept para devolverlos y que los tome la funcion de actualizar en BD.
		for(int i = 0; i<lrcmRejectedBeans.size(); i++)
			lrcmAcceptedBeans.add( lrcmRejectedBeans.get(i) );
		
		return lrcmAcceptedBeans;
	}
	
	
	// Se obtiene un listado de los beans segun su action (Accept, Reject).
	private List<ReleaseCapacityManagementBean> getBeansByAction(String strAction, List<ReleaseCapacityManagementBean> lrcmBeans) throws Exception {
		
		List<ReleaseCapacityManagementBean> tmpBeanList = new ArrayList<ReleaseCapacityManagementBean>();
		ReleaseCapacityManagementBean tmpBean = null;
		
		for(int i=0; i<lrcmBeans.size(); i++) {
			tmpBean = lrcmBeans.get(i);
			
			if( strAction.equalsIgnoreCase(tmpBean.getAction()) ) {
				tmpBeanList.add(tmpBean);
			}
		}
		
		return tmpBeanList;
	}
	
	
	private void validateContractToExists(List<ReleaseCapacityManagementBean> lrcmBeans) throws Exception {

    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		ReleaseCapacityManagementBean tmpBean = null;
		
		for(int i=0; i<lrcmBeans.size(); i++) {
			tmpBean = lrcmBeans.get(i);
			
			// Ya se ha verificado previamente que la lista de beans solo tiene accion Accept.
			//Comrpobar que no es toOperator, sino toShipper
			//if( strAccept.equalsIgnoreCase(tmpBean.getAction()) &&
			if ( tmpBean.getToOperator().equals("N") && tmpBean.getContractIdTo() == null ) {
				throw new ValidationException(msgs.getString("rel_cap_man_validation_contract_to_exists") + " " +
												msgs.getString("rel_cap_man_submission_time") + ": " +
												tmpBean.getSubmittedTimestamp());
			}
			
			// Los codigos de contrato se consultan para pintar el combo de la pantalla, pero luego no se guardan en el bean de la pantalla.
			// Se recupera aqui.
			// Como tmpBean es una referencia al objeto de la lista lrcmBeans, al actualizar tmpBean se actualiza la lista.
			tmpBean.setContractCodeTo(rcmMapper.selectContractCodeFromId(tmpBean.getContractIdTo()));
		}
	}

	
	// Para completar la lista de punto-fecha-cantidades de la request origen, asocidada a cada ReleaseCapacityManagementBean.
	private void completeDetailsFrom(List<ReleaseCapacityManagementBean>  _lrcmBean) {

		ReleaseCapacityManagementBean tmpBean = null;
		List<ReleaseCapacitySubmissionBean> tmpDetails = null;
		
		// Si los detalles se hubieran consultado previamente, no se vuelven a consultar.
		for(int i=0; i<_lrcmBean.size(); i++) {
			
			tmpBean = _lrcmBean.get(i);

			if(tmpBean.getDetailsFrom() == null) {
				tmpDetails = selectDetailsFrom(tmpBean.getCapacityRequestId());
				tmpBean.setDetailsFrom(tmpDetails);
			}
		}
	}	


	// Se realizan las validaciones siguientes, a la vez que se guardan estructuras de datos.
	// 1.- Los puntos indicados para el contrato del donante deben estar contratados en el contrato del receptor 
	// para los mismos anos.
	// 2.- LA VALIDACION SIGUIENTE NO TIENE SENTIDO, asi que se elimina del Diseno.
	// La capacidad a liberar por cada punto y ano en el contrato del donante no puede superar la capacidad 
	// contratada en el contrato del receptor.
	// 
	// Se devuelve la lista de requests porque se han anadido:
	// - Detalles (puntos, fechas, cantidades) de origen From.
	// - la capacidad a liberar en los detalles destino.
	// Tras ejecutar este metodo, para cada ReleaseCapacityManagementBean, se devuelve:
	// - Origen (From), SOLO los puntos que se estan liberando (los de la request de liberacion)
	// - Destino (To) TODOS los puntos del contrato destino (no solo los que se van a liberar).
	private void validateComparingContractsFromTo(List<ReleaseCapacityManagementBean> lrcmBeans) throws Exception {
		
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		ReleaseCapacityManagementBean tmpBean = null;
		List<ReleaseCapacitySubmissionBean> tmplDetailsFrom = null;
		List<ReleaseCapacitySubmissionBean> tmplDetailsTo = null;
		ReleaseCapacitySubmissionBean tmpDetailFrom = null;
		ReleaseCapacitySubmissionBean tmpDetailTo = null;
		BigDecimal tmpSystemPointFrom = null;
		Date tmpAgreementStartDateFrom = null;
		Date tmpAgreementEndDateFrom = null;
		BigDecimal tmpSystemPointTo = null;
		Date tmpAgreementStartDateTo = null;
		Date tmpAgreementEndDateTo = null;
		Float tmpReleaseBBTuDayFrom = null;
		Float tmpReleaseMMscfdFrom = null;
		boolean bFoundPointDates = false;
		String errorMsg = null;
		
		// Bucle de beans liberaciones de capacidad.
		for(int i=0; i<lrcmBeans.size(); i++) {
			tmpBean = lrcmBeans.get(i);
				if(tmpBean.getToOperator().equals("N")) {		
				// Podria haberse cargado previamente los detalles de from. Si los hay, no se consultan en BD.
				tmplDetailsFrom = tmpBean.getDetailsFrom();
				if( tmplDetailsFrom == null ){
					tmplDetailsFrom = rcmMapper.selectPointsByCapacityRequestId(tmpBean.getCapacityRequestId()); 
					tmpBean.setDetailsFrom(tmplDetailsFrom);				
				}
				
				tmplDetailsTo = rcmMapper.selectPointsByContractId(tmpBean.getContractIdTo());
				if(tmplDetailsTo == null ) {
					errorMsg = msgs.getString("rel_cap_man_validation_empty_contract");
					// Se usa la etiqueta strContractFromLabel, porque es la que tiene el mensaje configurado.
					errorMsg = errorMsg.replace(strContractFromLabel, tmpBean.getContractCodeTo());												
					throw new ValidationException(errorMsg);				
				}
				
				tmpBean.setDetailsTo(tmplDetailsTo);
	
				// Bucle de detalles (puntos, fechas) de cada liberacion de capacidad (origen).
				details_from:
				for(int j=0; j<tmplDetailsFrom.size(); j++) {
					tmpDetailFrom = tmplDetailsFrom.get(j);
					tmpSystemPointFrom = tmpDetailFrom.getSystemPointId();
					tmpAgreementStartDateFrom = tmpDetailFrom.getAgreementStartDate();
					tmpAgreementEndDateFrom = tmpDetailFrom.getAgreementEndDate();
					tmpReleaseBBTuDayFrom = tmpDetailFrom.getReleaseBBTuDay();
					tmpReleaseMMscfdFrom = tmpDetailFrom.getReleaseMMscfd();
	
					// La primera vez que se envia la peticion a la pantalla, este metodo solo recibe detalles from con los puntos fechas que se pidieron
					// en la release. 
					// Pero si se vuelve a enviar la peticion sin refrescar la pantalla (search o clean), se reutiliza la estructura que ya estaba rellena
					// y aqui pueden llegar detalles sin tmpReleaseBBTuDayFrom, que no habria que tener en cuenta.
					if(tmpReleaseBBTuDayFrom == null)
						continue details_from;				
					
					// Se resetea el booleano antes de buscar en la lista de todos los detalles destino.
					bFoundPointDates = false;
					
					// Bucle de detalles (punto, fechas) de contrato destino de una liberacion de capacidad.
					details_to:
					for(int k=0; k<tmplDetailsTo.size(); k++) {
						tmpDetailTo = tmplDetailsTo.get(k);
						tmpSystemPointTo = tmpDetailTo.getSystemPointId();
						tmpAgreementStartDateTo = tmpDetailTo.getAgreementStartDate();
						tmpAgreementEndDateTo = tmpDetailTo.getAgreementEndDate();
						
						if( (tmpSystemPointFrom != null) && 
								(tmpSystemPointTo != null) && 
								(tmpSystemPointFrom.compareTo(tmpSystemPointTo) == 0) &&
								(tmpAgreementStartDateFrom != null) && 
								(tmpAgreementStartDateTo != null) && 
								(tmpAgreementStartDateFrom.compareTo(tmpAgreementStartDateTo) == 0 ) &&
								(tmpAgreementEndDateFrom != null) && 
								(tmpAgreementEndDateTo != null) && 
								(tmpAgreementEndDateFrom.compareTo(tmpAgreementEndDateTo) == 0 ) ) {
							bFoundPointDates = true;
													
							// Una vez localizado el detalle destino que corresponde al origen, se guarda el dato de capacidad a liberar,
							// para utilizarlo en posteriores validaciones y en el insert.
							// Se multiplica la capacidad liberada por (-1) por que en el contrato destino hay que sumar capacidad...
							// ... que se puede entender como una liberacion negativa.
							// Asi se puede utilizar el mismo metodo validateContractWithoutHoles, tanto para validar huecos en detalles From y To.
							tmpDetailTo.setReleaseBBTuDay(tmpReleaseBBTuDayFrom * (-1));
							if(tmpReleaseMMscfdFrom != null)
								tmpDetailTo.setReleaseMMscfd(tmpReleaseMMscfdFrom * (-1));
							
							// Una vez encontrado los detalles destino que corresponden al origen, no hace falta seguir buscando detalles destino.
							break details_to;
						}
						
					}	// details_to
					
					if( !bFoundPointDates ) {
						errorMsg = msgs.getString("rel_cap_man_validation_not_found_point_dates_to");
						errorMsg = errorMsg.replace(strPointLabel, tmpDetailFrom.getSystemPointCode());
						errorMsg = errorMsg.replace(strContractFromLabel, tmpBean.getContractCodeFrom());
						if(tmpBean.getContractCodeTo()!=null)
							errorMsg = errorMsg.replace(strContractToLabel, tmpBean.getContractCodeTo());
						//MMCFerrorMsg = errorMsg.replace(strYearLabel, tmpDetailFrom.getYear().toString());					
											
						throw new ValidationException(errorMsg);		
					}
				}
					
			} 	// details_from
		}
	}


	private void validateContractsWithoutHoles(List<ReleaseCapacityManagementBean> lrcmBeans) throws Exception {		

			ReleaseCapacityManagementBean tmpRcmBean = null;
		BigDecimal tmpContractIdFrom = null;
		List<ReleaseCapacitySubmissionBean> tmplrcsDetailsFrom = null;
		List<ReleaseCapacitySubmissionBean> tmplrcsDetailsTo = null;		

		for(int i = 0; i<lrcmBeans.size(); i++) {
			tmpRcmBean = lrcmBeans.get(i);
			tmpContractIdFrom = tmpRcmBean.getContractIdFrom();
			tmplrcsDetailsFrom = tmpRcmBean.getDetailsFrom();
			tmplrcsDetailsTo = tmpRcmBean.getDetailsTo();				

			// Validar From
			// Completar From: Hay que obtener la capacidad contratada asociada al contrato de la request. Para luego revisar los huecos.
			// 					Ademas, para los puntos que se estan liberando, tambien las cantidades contratadas en los anos que no se liberan.
			// Es la misma validacion que se hizo ya sobre Release Capacity Submission, pero con datos actualizados ahora,
			// por si alguna Aceptacion intermedia hubiera cambiado la foto del contrato total.
			completeContractQuantities(tmpContractIdFrom, tmplrcsDetailsFrom);
			//MMCF Ya no se usa. Eso era antes con los años
			//validateContractWithoutHoles(tmpRcmBean.getContractCodeFrom(), tmplrcsDetailsFrom);			
			
			// Validar To: Los detalles To, ya tienen completo los valores de capacidad contratada y a liberar.
			// Detalles To, podria tener huecos, si durante unos ano tiene contrato cero y a partir de la liberacion,
			// se anade capacidad, dejando un hueco.
			//MMCF //MMCF Ya no se usa. Eso era antes con los años
			//validateContractWithoutHoles(tmpRcmBean.getContractCodeTo(), tmplrcsDetailsTo);
		}
	}
	

	// Completar From: Hay que obtener la capacidad contratada asociada al contrato de la request. Para luego revisar los huecos.
	// 					Ademas, para los puntos que se estan liberando, tambien las cantidades contratadas en los anos que no se liberan.
	private void completeContractQuantities(BigDecimal contractId,
											List<ReleaseCapacitySubmissionBean> lrcsBeansReleaseInfo)
			throws Exception {
		
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En esContracts casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

  	
    	// Este arbol contendra un elemento por punto (systemPointId), y ese elemento sera una treemap con los
    	// beans asociados al punto, organizados por fecha (ano).
    	// Los agreements (fechas) del contrato original y de la request de liberacion deben tener su fecha de inicio y fin alineados:
    	// Los agreements de la request de liberacion se generan a partir de los del conrato original.
    	// Es necesario tener la foto global de la lrcsBeansReleaseInfo de puntos y fechas en cada punto, 
    	// para saber cuando hay que completar a partir de los datos del contrato original 
    	// un ReleaseCapacitySubmissionBean( caracterizado por (punto, fecha)).
    	// Esta estructura se usara solo para leer datos.
    	TreeMap<BigDecimal, TreeMap<String, ReleaseCapacitySubmissionBean>> tmpAllReleaseData = 
    			new TreeMap<BigDecimal, TreeMap<String, ReleaseCapacitySubmissionBean>>();
		BigDecimal tmpSystemPointId  = null;
		TreeMap<String, ReleaseCapacitySubmissionBean> tmpPointData = null;   	

    	// Para separar todos los beans por puntos.
		for(ReleaseCapacitySubmissionBean rcsBean : lrcsBeansReleaseInfo) {
			
			tmpSystemPointId = rcsBean.getSystemPointId();

			if( !tmpAllReleaseData.containsKey(tmpSystemPointId)) {
				tmpPointData = new TreeMap<String, ReleaseCapacitySubmissionBean>();
				tmpPointData.put(rcsBean.getAgreementStartDate()+"/"+rcsBean.getAgreementEndDate(), rcsBean);
				tmpAllReleaseData.put(tmpSystemPointId, tmpPointData);
			}
			else {
				tmpPointData = tmpAllReleaseData.get(tmpSystemPointId);
				if( tmpPointData.containsKey(rcsBean.getAgreementStartDate()+"/"+rcsBean.getAgreementEndDate()))
					throw new Exception(msgs.getString("rel_cap_man_duplicated_year_data_error") + " " +
							rcsBean.getSystemPointCode() + ").");
				
				tmpPointData.put(rcsBean.getAgreementStartDate()+"/"+rcsBean.getAgreementEndDate(), rcsBean);					
			}
		}
		// En este punto, se tiene una estructura tmpAllData, con toda la informacion de la entrada lrcsBeansReleaseInfo.
		
		
    	ReleaseCapacitySubmissionBean tmpDetailRelease = null;
		ReleaseCapacitySubmissionBean tmpDetailRelease2 = null;		// Para guardar los detalles de release que sea necesario generar nuevos.		
		ReleaseCapacitySubmissionBean tmpDetailContract = null;
		BigDecimal tmpSystemPointRelease = null;
		Date tmpAgreementStartDateRelease = null;
		Date tmpAgreementEndDateRelease = null;
		BigDecimal tmpSystemPointContract = null;
		Date tmpAgreementStartDateContract = null;
		Date tmpAgreementEndDateContract = null;
		Float tmpContratedBBTuDayContract = null;
		Float tmpContratedMMscfdContract = null;		
		String errorMsg = null;
		
		
		List<ReleaseCapacitySubmissionBean> lrcsBeansContractInfo = null;
		
		lrcsBeansContractInfo = rcmMapper.selectPointsByContractId(contractId);
		
		if(lrcsBeansContractInfo == null ) {
			errorMsg = msgs.getString("rel_cap_man_validation_empty_contract");
			errorMsg = errorMsg.replace(strContractFromLabel, rcmMapper.selectContractCodeFromId(contractId));
			throw new ValidationException(errorMsg);				
		}

		// Bucle de detalles (punto, fechas) de cada liberacion de capacidad (origen).
		// Como dentro del bucle se van anadiendo elementos a la lista, se toma el tamano inicial de la lista,
		// para solo iterar por los elementos iniciales.
		int lrcsBeansReleaseInfo_size = lrcsBeansReleaseInfo.size();
		for(int i=0; i<lrcsBeansReleaseInfo_size; i++) {
			tmpDetailRelease = lrcsBeansReleaseInfo.get(i);
			tmpSystemPointRelease = tmpDetailRelease.getSystemPointId();
			tmpAgreementStartDateRelease = tmpDetailRelease.getAgreementStartDate();
			tmpAgreementEndDateRelease = tmpDetailRelease.getAgreementEndDate();
			
			// Bucle de detalles (punto, fechas) de contrato asociado a una liberacion de capacidad.
			for(int k=0; k<lrcsBeansContractInfo.size(); k++) {
				tmpDetailContract = lrcsBeansContractInfo.get(k);
				tmpSystemPointContract = tmpDetailContract.getSystemPointId();
				tmpAgreementStartDateContract = tmpDetailContract.getAgreementStartDate();
				tmpAgreementEndDateContract = tmpDetailContract.getAgreementEndDate();
				tmpContratedBBTuDayContract = tmpDetailContract.getContratedBBTuDay();
				tmpContratedMMscfdContract = tmpDetailContract.getContratedMMscfd();
				
				if( tmpSystemPointRelease.compareTo(tmpSystemPointContract) == 0 ) {

					// Se obtiene la informacion del punto que se ha guardado antes.
					tmpPointData = tmpAllReleaseData.get(tmpSystemPointRelease);
					
					if( (tmpAgreementStartDateRelease.compareTo(tmpAgreementStartDateContract) == 0 ) && 
							(tmpAgreementEndDateRelease.compareTo(tmpAgreementEndDateContract) == 0 ) ) {
						// Una vez localizado el detalle de contrato que corresponde al de release, se guarda el dato de contrato 
						// en los detalles de release, para utilizarlo en posteriores validaciones.
						tmpDetailRelease.setContratedBBTuDay(tmpContratedBBTuDayContract);
						tmpDetailRelease.setContratedMMscfd(tmpContratedMMscfdContract);
					}
					else {
						// Para completar los nodos para los que hay punto en la release pero no hay fecha,
						// una vez que estamos en un punto de la release, si la fecha del contrato no esta
						// entre las fechas del resto de la release, se anade el par punto-fecha nueva para la release
						// con la cantidad contratada.
						if( ! tmpPointData.containsKey(tmpDetailContract.getAgreementStartDate()+"/"+tmpDetailContract.getAgreementEndDate())) {
							// Si no coincide la fecha de la request con la del contrato, se genera detalle con los datos del contrato
							// y con cantidad released igual a cero, para poder validar luego los huecos.
							tmpDetailRelease2 = new ReleaseCapacitySubmissionBean(tmpDetailContract);
							// No relleno las cantidades release, porque son las que marcan los nodos para los que se pidio release inicialmente.
							//tmpDetailRelease2.setReleaseBBTuDay(new Float(0));
							//tmpDetailRelease2.setReleaseMMscfd(new Float(0));
							
							lrcsBeansReleaseInfo.add(tmpDetailRelease2);
							
							// Se actualiza tambien la estructura de control.
							tmpPointData.put(tmpDetailContract.getAgreementStartDate()+"/"+tmpDetailContract.getAgreementEndDate(), tmpDetailRelease2);
						}
					}
						
				}
				
			}	// for details_contract
			
		}  // for details_release
	}


	// Como parametro de entrada de este metodo se tiene:
	// - Origen (From), SOLO los puntos que se estan liberando (los de la request de liberacion) en todos los anos del contrato.
	// - Destino (To) TODOS los puntos del contrato destino (no solo los que se van a liberar).
	// Se devuelve esta lista para utilizar las cantidades Totales(Netas) calculadas en este metodo.
	private void validateContractWithoutHoles(String strContractCode, List<ReleaseCapacitySubmissionBean> lrcsBeans) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	// Este arbol contendra un elemento por punto (systemPointId), y ese elemento sera una treemap con los
    	// beans asociados al punto, organizados por fecha.
    	TreeMap<BigDecimal, TreeMap<String, ReleaseCapacitySubmissionBean>> tmpAllData = 
    			new TreeMap<BigDecimal, TreeMap<String, ReleaseCapacitySubmissionBean>>();
		BigDecimal tmpSystemPointId  = null;
		TreeMap<String, ReleaseCapacitySubmissionBean> tmpPointData = null;
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
				tmpPointData = new TreeMap<String, ReleaseCapacitySubmissionBean>();
				tmpPointData.put(rcsBean.getAgreementStartDate()+"/"+rcsBean.getAgreementEndDate(), rcsBean);
				tmpAllData.put(tmpSystemPointId, tmpPointData);
				
				tmpSystemPointCodes.put(tmpSystemPointId, rcsBean.getSystemPointCode());
			}
			else {
				tmpPointData = tmpAllData.get(tmpSystemPointId);
				if( tmpPointData.containsKey(rcsBean.getAgreementStartDate()+"/"+rcsBean.getAgreementEndDate()))
					throw new Exception(msgs.getString("rel_cap_man_duplicated_year_data_error") + " " +
							rcsBean.getSystemPointCode() + " - " +
							msgs.getString("rel_cap_man_contract") + " " + strContractCode + ").");
							
				
				tmpPointData.put(rcsBean.getAgreementStartDate()+"/"+rcsBean.getAgreementEndDate(), rcsBean);						
			}
		}
		
		// Se definen arrays con una posicicion para controlar los datos de cada punto.
		// Con estos arrays se detectara cuando se pasa de que la suma de cantidades
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

    		//Estaba para años pero como no se usa...
			int smallestYear = 2000;//MMCFCollections.min(tmpPointData.keySet());
			
			// Se buscan huecos entre los beans.
			// Se obtienen los beans asociados a anos consecutivos empezando por el mas pequeno.
			for(int j=smallestYear; j<(smallestYear + tmpPointData.size()); j++) {
				tmpRcsBean = tmpPointData.get(j);
				// Si no existieran datos para un ano del intervalo.
				if( tmpRcsBean == null )
					throw new Exception(msgs.getString("rel_cap_man_missing_year_data_error") + " " +
							tmpSystemPointCodes.get(tmpSystemPointId2) + " - " +
								msgs.getString("rel_cap_man_year") + " " + j + " - " +
								msgs.getString("rel_cap_man_contract") + " " + strContractCode + ".");			
				
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
					throw new ValidationException(msgs.getString("rel_cap_man_validation_too_much_released") + " " +
							tmpRcsBean.getSystemPointCode() + " - " +
								msgs.getString("rel_cap_man_year") + " " + j + " - " +
								msgs.getString("rel_cap_man_contract") + " " + strContractCode + ").");

				// COMPROBACION para quantity.					
				if(aiBBTuDayEstado[i] == iEstadoInicial && fBBTuDayNeto > 0)
					aiBBTuDayEstado[i] = iEstadoConCantidad;
				
				if(aiBBTuDayEstado[i] == iEstadoConCantidad && fBBTuDayNeto == 0)
					aiBBTuDayEstado[i] = iEstadoSinCantidad;
				
				// Aqui se ha detectado un hueco.
				if(aiBBTuDayEstado[i] == iEstadoSinCantidad && fBBTuDayNeto > 0)
					throw new ValidationException(msgs.getString("rel_cap_man_validation_time_holes") + " " +
							tmpRcsBean.getSystemPointCode() + " - " +
							msgs.getString("rel_cap_man_year") + " " + (j-1) + " - " +
							msgs.getString("rel_cap_man_contract") + " " + strContractCode + " - " +
							msgs.getString("rel_cap_man_concept") + " " + msgs.getString("rel_cap_man_MMBTu_day") + ").");
					// (j-1) Cuando se detecta un hueco es a ano pasado. Por esto, al enviar el error se resta uno, 
					// para mostrarlo asi al usuario.
				
				// COMPROBACION para volumen.
				if(aiMMscfdEstado[i] == iEstadoInicial && fMMscfdNeto > 0)
					aiMMscfdEstado[i] = iEstadoConCantidad;
				
				if(aiMMscfdEstado[i] == iEstadoConCantidad && fMMscfdNeto == 0)
					aiMMscfdEstado[i] = iEstadoSinCantidad;
				
				// Aqui se ha detectado un hueco.
				if(aiMMscfdEstado[i] == iEstadoSinCantidad && fMMscfdNeto > 0)
					throw new ValidationException(msgs.getString("rel_cap_man_validation_time_holes") + " " +
							tmpRcsBean.getSystemPointCode() + " - " +
							msgs.getString("rel_cap_man_year") + " " + (j-1) + " - " +
							msgs.getString("rel_cap_man_contract") + " " + strContractCode + " - " +
							msgs.getString("rel_cap_man_concept") + " " + msgs.getString("rel_cap_man_MMscfd") + ").");		
					// (j-1) Cuando se detecta un hueco es a ano pasado. Por esto, al enviar el error se resta uno, 
					// para mostrarlo asi al usuario.
				
			}	// Bucle de cada lista
			
			i++;
		}	// Bucle de datos/listas asociados a puntos.
	}

	
	// Este metodo es transaccional. O se hace accept o reject de todos las request o no se hace de ninguno.
	@Transactional( rollbackFor = { Throwable.class })	
	public void save(List<ReleaseCapacityManagementBean> lrcmBeans, BigDecimal idn_system) throws Exception {
    	
    	ReleaseCapacityManagementBean tmpRcmBean = null; 
    	
    	for( int i=0; i<lrcmBeans.size(); i++ ) {
    		tmpRcmBean = lrcmBeans.get(i);
    		
    		//offshore
    		tmpRcmBean.setIdn_system(idn_system);
    		
    		if(strAccept.equalsIgnoreCase(tmpRcmBean.getAction())) {
    			// Actualizar Request y contrato de From
    			saveAcceptFromInfo(tmpRcmBean);
    			
    			// Insert request y actualizar Contrato de To
    			if(tmpRcmBean.getToOperator().equals("N"))
    				saveAcceptToInfo(tmpRcmBean);

    		} else if (strReject.equalsIgnoreCase(tmpRcmBean.getAction())) {
    			// Actualizar Request e insertar lista de puntos rechazados.
    			saveRejectFromInfo(tmpRcmBean);  			    			
    		}
    		// No deberia haber otro tipo de acciones. Si hay otro tipo no se hace nada, se descarta.
    	}
	}

	
	// Para guardar los datos relativos a la capacity request origen, en caso Reject.
	private void saveRejectFromInfo(ReleaseCapacityManagementBean rcmBean) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	List<ReleaseCapacitySubmissionBean> tmpLrcsDetailsFrom = rcmBean.getDetailsFrom();
    	ReleaseCapacitySubmissionBean tmpRcsDetailFrom = null;
    	BigDecimal tmpSysPointId = null;
    	List<BigDecimal> processedSystemPoints = new ArrayList<BigDecimal>();
    	ContractRejectedPointBean tmpCrpBean = null;
    	
    	int res = -1; // Resultado de operaciones sobre BD.

		// Actualizar Request
    	rcmBean.setStatusCode(Constants.PTT_REJECTED);
    	res = rcmMapper.updateContractRequest(rcmBean);
		if(res!=1){
    		throw new Exception(msgs.getString("update_error") + " " + msgs.getString("rel_cap_man_contract_request"));   		
    	}
		
		// Insertar listado de puntos rechazados: Todos los puntos de la request.
		for( int i=0; i<tmpLrcsDetailsFrom.size(); i++) {
			tmpRcsDetailFrom = tmpLrcsDetailsFrom.get(i);
			tmpSysPointId = tmpRcsDetailFrom.getSystemPointId();
			
			// Se hace un insert de Contract Reject List por cada System Point diferente, asociado a la request (From).
			if( ! processedSystemPoints.contains(tmpSysPointId)) {
				processedSystemPoints.add(tmpSysPointId);
				
				tmpCrpBean = new ContractRejectedPointBean();
				tmpCrpBean.setContractRequestId(rcmBean.getCapacityRequestId());
				tmpCrpBean.setSystemPointId(tmpSysPointId);
				
				res = rcmMapper.insertRejectedPoint(tmpCrpBean);
				if(res!=1){
		    		throw new Exception(msgs.getString("insert_error") + " " + msgs.getString("rel_cap_man_contract_rejected_point"));   		
		    	} 				
			}
		}		
	}

	
	// Para guardar los datos relativos a la capacity request origen, en caso Accept.
	private void saveAcceptFromInfo(ReleaseCapacityManagementBean rcmBean) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	ContractConsolidateBean tmpContractConsolidateBean = null;
    	ContractPointBean tmpContractPointBean = null;
    	List<ReleaseCapacitySubmissionBean> tmpLrcsDetailsFrom = rcmBean.getDetailsFrom();
    	ReleaseCapacitySubmissionBean tmpRcsDetailFrom = null;
    	BigDecimal tmpCAgreeId = null;
    	List<BigDecimal> processedContractAgreements = new ArrayList<BigDecimal>();

    	
    	int res = -1; // Resultado de operaciones sobre BD. 
    	
		// Actualizar Request
		rcmBean.setStatusCode(Constants.COMPLETED);
    	res = rcmMapper.updateContractRequest(rcmBean);
		if(res!=1){
    		throw new Exception(msgs.getString("update_error") + " " + msgs.getString("rel_cap_man_contract_request"));   		
    	} 
		

		for( int i=0; i<tmpLrcsDetailsFrom.size(); i++) {
			tmpRcsDetailFrom = tmpLrcsDetailsFrom.get(i);
			
			// Si no esta rellena una cantidad release es porque el par punto-fecha no pertenece a la request de la 
			// release (se obtuvo posteriormente para una validacion), y no hay que actualizar la BD con este dato.
			// Solo es necesario comprobar ReleaseBBTuDay porque en cualquier release debe estar relleno.
			if( tmpRcsDetailFrom.getReleaseBBTuDay() == null )
				continue;
			
			// Insertar Contract Consolidates
			tmpCAgreeId = tmpRcsDetailFrom.getContractAgreementId();
			
			// Se hace un insert de Contract Consolidate por cada Contract Agreement diferente.
			if( ! processedContractAgreements.contains(tmpCAgreeId)) {
				processedContractAgreements.add(tmpCAgreeId);
				
				tmpContractConsolidateBean = new ContractConsolidateBean();
				tmpContractConsolidateBean.setIdnContract(rcmBean.getContractIdFrom());
				tmpContractConsolidateBean.setIdnContractAgreement(tmpCAgreeId);
				
				res = rcmMapper.insertContractConsolidate(tmpContractConsolidateBean);
				if(res!=1){
		    		throw new Exception(msgs.getString("insert_error") + " " + msgs.getString("rel_cap_man_contract_consolidate"));   		
		    	} 				
			}
			
			// Actualizar Contract Points			
			tmpContractPointBean = new ContractPointBean();
			tmpContractPointBean.setReleasedToId(rcmBean.getContractIdTo());
			tmpContractPointBean.setContractPointId(tmpRcsDetailFrom.getContractPointId());
			
			res = rcmMapper.updateContractPoint(tmpContractPointBean);
			if(res!=1){
	    		throw new Exception(msgs.getString("update_error") + " " + msgs.getString("rel_cap_man_contract_point"));   		
	    	}			
		}
	}

	
	// Para guardar los datos relativos al contrato destino.
	private void saveAcceptToInfo(ReleaseCapacityManagementBean rcmBean) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
 				
		CapacityRequestBean tmpCapReqBean = null;  	
 			
		// Se prepara la rama capacityRequest - contractAgreement - contractPoint
		tmpCapReqBean = prepareCapacityRequestTo(rcmBean.getContractIdFrom(),
												rcmBean.getShipperIdTo(),
												rcmBean.getContractIdTo(),
												rcmBean.getDetailsTo());
		
	
		
		if( tmpCapReqBean == null ){
			throw new ValidationException(msgs.getString("no_data"));
		}
		else {
			//offshore
			tmpCapReqBean.setIdn_system(rcmBean.getIdn_system());
			
			// Se guarda la rama capacityRequest - contractAgreement - contractPoint - contractConsolidate.
			insert(tmpCapReqBean);
		}
	} 	


	// Con este metodo se crea un arbol de capacityRequest - contractAgreement - contractPoint, para que luego sea
	// mas facil hacer los inserts en BD.
	private CapacityRequestBean prepareCapacityRequestTo(BigDecimal contractIdFrom,
														BigDecimal shipperIdTo, 
														BigDecimal contractIdTo, 
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
		
		// El operationId es comun para todos los registros, puesto que es unico en el contrato.
		// Este campo se ha obtenido en la consulta selectPointsByContractId.
		BigDecimal tmpOperationId = null;
		// Estas fechas corresponderan a la menor StartDate y a la mayor EndDate de las fechas de los agreements. 
		Date tmpCapReqStartDate = null;
		Date tmpCapReqEndDate = null;
		
		details_to:
		for(ReleaseCapacitySubmissionBean rcsBean : lrcsBeans) {

			tmpReleaseBBTuDay = rcsBean.getReleaseBBTuDay();
			// En la lista de detalles To, van todos los puntos con todos los agreements del contrato.
			// Cuando se ha guardado la cantidad Release se ha marcado aquellos puntos - agreements sobre los que se 
			// requiere ceder capacidad.
			if(tmpReleaseBBTuDay == null)
				continue details_to;
			
			// Respecto al volumen, en los puntos de salida, no envia el dato el usuario, asi que no se puede utilizar
			// para marcar los  puntos - agreements sobre los que se requiere ceder capacidad.
			// Solo mas abajo se comprueba que sea distinto de null antes de guardar el dato.
			tmpReleaseMMscfd = rcsBean.getReleaseMMscfd();
								
			// Este valor solo hace falta generarlo una vez.
			if( tmpOperationId == null)
				tmpOperationId = rcsBean.getOperationId();
			
			tmpContPointBean = new ContractPointBean();
			tmpContPointBean.setSystemPointId(rcsBean.getSystemPointId());
			// En los detalles To, se guardo tmpReleaseBBTuDay como negativos y ahora se deja en positivo, porque To, como receptor
			// de capacidad, tiene adendas con cantidades positivas.
			tmpContPointBean.setQuantity(tmpReleaseBBTuDay * (-1));
			tmpContPointBean.setMaxHourQuantity(new Float(0));
			if(tmpReleaseMMscfd != null)							// Puede ocurrir que no se especifique el volumen.
				tmpContPointBean.setVolume(tmpReleaseMMscfd * (-1));
			tmpContPointBean.setMaxHourVolume(new Float(0));
			// releasedToId y meterinPoint, no se rellenan. Se quedan null.
			// isNewConnection no se rellena. Se queda null en el bean y 'N' en base de datos.
			tmpContPointBean.setReceivedFromId(contractIdFrom);
			
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
		}		// for 
		
		// Si se han generado datos anteriormente, se guardan en el objeto CapacityRequestBean de salida.
		// Si no, se devuelve un null.
		if(tmpContractAgreements != null) {
			tmpCapReqBean = new CapacityRequestBean();
			tmpCapReqBean.setRequestCode(getNewCapacityRequestCode());
			tmpCapReqBean.setShipperId(shipperIdTo);
			tmpCapReqBean.setContractTypeId(tmpOperationId);
			tmpCapReqBean.setContractId(contractIdTo);
			tmpCapReqBean.setStatus(Constants.COMPLETED);
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


	private void insert(CapacityRequestBean crb) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");		
		TreeMap<Integer, ContractAgreementBean> tmpContractAgreements = null;
		ContractAgreementBean tmpContAgreeBean = null;
		TreeMap<Integer, ContractPointBean> tmpContractPoints = null;
		ContractPointBean tmpContPointBean = null;
		ContractConsolidateBean tmpContractConsolidateBean = null;
		
    	int res = rcmMapper.insertContractRequest(crb);
		if(res!=1){
    		throw new Exception(msgs.getString("insert_error") + " " + msgs.getString("rel_cap_man_contract_request"));   		
    	}
		
		tmpContractAgreements = crb.getTmContractAgreements();
		for(Integer i : tmpContractAgreements.keySet()) {
			tmpContAgreeBean = tmpContractAgreements.get(i);
			// El identificador del contractRequest se establece en el insert anterior. En este paso se incluye en contractAgreement.
			tmpContAgreeBean.setIdnContractRequest(crb.getId());
			
			res = rcmMapper.insertContractAgreement(tmpContAgreeBean);
			if(res!=1){
	    		throw new Exception(msgs.getString("insert_error") + " " + msgs.getString("rel_cap_man_contract_agreement"));   		
	    	}
			
			tmpContractPoints = tmpContAgreeBean.getTmContractPoints();
			for(Integer j : tmpContractPoints.keySet()) {
				tmpContPointBean = tmpContractPoints.get(j);
				// El identificador del contractAgreement se establece en el insert anterior. En este paso se incluye en contractPoint.
				tmpContPointBean.setContractAgreementId(tmpContAgreeBean.getIdnContractAgreement());
				
				res = rcmMapper.insertContractPoint(tmpContPointBean);
				if(res!=1){
		    		throw new Exception(msgs.getString("insert_error") + " " + msgs.getString("rel_cap_man_contract_point"));   		
		    	}
			}

					
			tmpContractConsolidateBean = new ContractConsolidateBean();
			tmpContractConsolidateBean.setIdnContract(crb.getContractId());
			tmpContractConsolidateBean.setIdnContractAgreement(tmpContAgreeBean.getIdnContractAgreement());
			
			res = rcmMapper.insertContractConsolidate(tmpContractConsolidateBean);
			if(res!=1){
	    		throw new Exception(msgs.getString("insert_error") + " " + msgs.getString("rel_cap_man_contract_consolidate"));   		
	    	}
    	}	
	}

	
	public ReleaseCapacityManagementInfoMailBean selectContractInfoMail (BigDecimal idn_contract) {
		return rcmMapper.selectContractInfoMail(idn_contract);
	}
		
	
}
