package com.atos.services.balancing;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.balancing.ReserveBalancingGasContractBean;
import com.atos.beans.balancing.ReserveBalancingGasContractDetailBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.balancing.ReserveBalancingGasContractsFilter;
import com.atos.mapper.allocation.AllocationManagementMapper;
import com.atos.mapper.balancing.ReserveBalancingGasContractsMapper;

@Service("resBalGasContractsService")
public class ReserveBalancingGasContractsServiceImpl implements ReserveBalancingGasContractsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3293121454413882521L;
	
	@Autowired
	private ReserveBalancingGasContractsMapper rbgMapper;
	@Autowired
	private AllocationManagementMapper amMapper;
	
	public Map<BigDecimal, Object> selectShipperId(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = rbgMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}

	public Map<BigDecimal, Object> selectValidShipperId(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = rbgMapper.selectValidShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectReserveContractCodes(ReserveBalancingGasContractsFilter filter){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = rbgMapper.selectReserveContractCodesFromShipperId(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectZones(String systemCode){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = rbgMapper.selectZonesFromSystemCode(systemCode);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectValidZones(String systemCode){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = rbgMapper.selectValidZonesFromSystemCode(systemCode);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectPointTypes(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = rbgMapper.selectPointTypes();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			// En mybatis ya se ha filtrado el tipo CONNECTION.
			// Solo se guardan los tipos que sean ENTRY o EXIT.
			//if (Constants.ENTRY.equalsIgnoreCase(combo.getValue()) ||
			//		Constants.EXIT.equalsIgnoreCase(combo.getValue()))
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectValidPoints(ReserveBalancingGasContractDetailBean det){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = rbgMapper.selectValidPoints(det);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public List<ReserveBalancingGasContractBean> search(ReserveBalancingGasContractsFilter filter){
		return rbgMapper.selectReserveContracts(filter);
	}
	
	public void getFile(ReserveBalancingGasContractBean _contract) throws Exception{
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	// Solo se consulta el fichero 1 vez de base de datos. Si ya existe, no se consulta.
    	if( _contract.getScFile() == null) {

    		BigDecimal contractId = _contract.getContractId();
			if(contractId == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));

    		List<ReserveBalancingGasContractBean> lData = rbgMapper.getFileFromResBalGasContractId(contractId);
	
			if(lData == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));
			
			// Solo se va a tener un fichero por cada contract attachment.
			ReserveBalancingGasContractBean tmpBean = lData.get(0);
			if(tmpBean == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));		
	
			byte[] ba = tmpBean.getBinaryData();
			if(ba == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));		
			
			_contract.setBinaryData(Arrays.copyOf(ba, ba.length));
    	}
    }
	
	// Se anota aqui como transaccional porque Spring requiere que sea un metodo publico, expuesto en el interfaz.
	// https://docs.spring.io/spring/docs/current/spring-framework-reference/html/transaction.html
	// Anteriormente las transacciones se hacian mas internamente; mover la transaccion aqui es equivalente porque no se pueden producir
	// excepciones despues de las transacciones que habia hasta ahora, que provoquen nuevos rollbacks no previstos hasta ahora.	
	@Transactional( rollbackFor = { Throwable.class })
	public void save(ReserveBalancingGasContractBean _newContract, Map<String, Object> params) throws Exception {
		validateContract(_newContract, params);
		saveContract(_newContract);
	}
	
	private void validateContract(ReserveBalancingGasContractBean _newContract, Map<String, Object> params)
			throws Exception {
		validateMandatoryFields(_newContract);
		validateContractCode(_newContract);
		validateDates(_newContract, params);
		validateReservedCapacity(_newContract);
	}
	
	private void validateMandatoryFields(ReserveBalancingGasContractBean _newContract) throws Exception{
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String errMsg = msgs.getString("the_mandatory_field_error");
    	String strFieldLabel = "XXX";
    	
    	if(_newContract.getContractCode()==null || "".equalsIgnoreCase(_newContract.getContractCode())){
    		errMsg = errMsg.replace(strFieldLabel, msgs.getString("res_bal_con_contract_id_short"));
    		throw new ValidationException(errMsg);
    	}

    	if(_newContract.getShipperId()==null){
    		errMsg = errMsg.replace(strFieldLabel, msgs.getString("res_bal_con_shipper"));
    		throw new ValidationException(errMsg);
    	}
    	
    	if(_newContract.getDetails()==null || _newContract.getDetails().size()==0){
    		errMsg = msgs.getString("res_bal_con_none_detail_error");
    		throw new ValidationException(errMsg);
    	} else {
    		for(ReserveBalancingGasContractDetailBean tmpDetail: _newContract.getDetails()){   			
    			// En el bean solo se habran rellenado identificadores de las entidades, no los codigos.
    	    	
    	    	
    	    	if(tmpDetail.getPointId()==null){
    	    		errMsg = errMsg.replace(strFieldLabel, msgs.getString("res_bal_con_nom_point_id") + " " + msgs.getString("res_bal_con_in_details_table"));
    	    		throw new ValidationException(errMsg);
    	    	}
    	    	
    	    	if(tmpDetail.getStartDate()==null){
    	    		errMsg = errMsg.replace(strFieldLabel, msgs.getString("res_bal_con_start_date") + " " + msgs.getString("res_bal_con_in_details_table"));
    	    		throw new ValidationException(errMsg);
    	    	}
    	    	
    	    	if(tmpDetail.getEndDate()==null){
    	    		errMsg = errMsg.replace(strFieldLabel, msgs.getString("res_bal_con_end_date") + " " + msgs.getString("res_bal_con_in_details_table"));
    	    		throw new ValidationException(errMsg);
    	    	}
    	    	
    	    	if(tmpDetail.getDailyReservedCap()==null){
    	    		errMsg = errMsg.replace(strFieldLabel, msgs.getString("res_bal_con_reserved_cap") + " " + msgs.getString("res_bal_con_in_details_table"));
    	    		throw new ValidationException(errMsg);
    	    	}
    		}
    	}
	}
	
	private void validateContractCode(ReserveBalancingGasContractBean _newContract) throws Exception{
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		List<ReserveBalancingGasContractBean> tmpList = rbgMapper.selectReserveContractsFromCode(_newContract);
		if(tmpList!=null && tmpList.size()>0)
			throw new ValidationException(msgs.getString("res_bal_con_repeated_contract_code_error"));	
	}
	
	private void validateDates(ReserveBalancingGasContractBean _newContract, Map<String, Object> params)
			throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		Date openPeriodFirstDay = amMapper.selectOpenPeriodFirstDay(params);
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");  
    	String strFieldLabel = "XXX";
    	
		List<ReserveBalancingGasContractDetailBean> tmpDetails = _newContract.getDetails();
    	ReserveBalancingGasContractDetailBean tmpDetailA = null;
    	ReserveBalancingGasContractDetailBean tmpDetailB = null;	// Para recorrer la lista de detalles por segunda vez.

    	// Se ha comprobado previamente que hay lista de detalle con algun elemento.
		for(int i=0; i<tmpDetails.size(); i++){
			tmpDetailA = tmpDetails.get(i);
			
	    	if((tmpDetailA.getStartDate()!=null) && (tmpDetailA.getEndDate()!=null)){
	    		if(tmpDetailA.getStartDate().after(tmpDetailA.getEndDate()))
    	    		throw new ValidationException(msgs.getString("dates_order"));
	    	}
	    	
	    	if(openPeriodFirstDay!=null)
		    	if(tmpDetailA.getStartDate().before(openPeriodFirstDay))
		    		throw new ValidationException(msgs.getString("res_bal_con_start_date_not_previous_error") + " " + 
		    										sdf.format(openPeriodFirstDay) + ".");

	    	// Revision de solapes de fechas.
	    	// Si se cruzan todos los detalles consigo mismos, solo se compara la mitad de la matriz
	    	// porque es equivalente comparar A con B que B con A.
			for(int j=0; j<i; j++){
				tmpDetailB = tmpDetails.get(j);
				
				// Solo se comparan fechas de un mismo punto.
				if(tmpDetailA.getPointId().compareTo(tmpDetailB.getPointId())==0){
					if(tmpDetailB.getEndDate().compareTo(tmpDetailA.getStartDate())>=0 &&
							tmpDetailB.getStartDate().compareTo(tmpDetailA.getEndDate())<=0){
						String errMsg = msgs.getString("res_bal_con_overlapping_error");
						errMsg = errMsg.replace(strFieldLabel, tmpDetailA.getPointCode());
	    	    		throw new ValidationException(errMsg);
					}
				}
			}
		}
	}
	
	private void validateReservedCapacity(ReserveBalancingGasContractBean _newContract) throws Exception{
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
		for(ReserveBalancingGasContractDetailBean tmpDetail: _newContract.getDetails()){
			if(tmpDetail.getDailyReservedCap()!=null)
				if(tmpDetail.getDailyReservedCap().compareTo(BigDecimal.ZERO) < 0)
    	    		throw new ValidationException(msgs.getString("res_bal_con_capacity_negative_error"));
		}
	}

    // Se mueve la anotacion mas arriba, porque Spring requiere que sea un metodo publico, expuesto en el interfaz.	
	//@Transactional( rollbackFor = { Throwable.class })
	private void saveContract(ReserveBalancingGasContractBean _newContract) throws Exception{
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	// Se inserta el contrato.
    	int res = rbgMapper.insertResBalGasContract(_newContract);
    	if(res!=1){
    		throw new Exception(msgs.getString("insert_error") + " " + msgs.getString("res_bal_con_contract"));   		
	    }
  	
    	for(ReserveBalancingGasContractDetailBean det : _newContract.getDetails()){
    		// Se asigna en el detalle el contractId que se acaba de generar con la secuencia de BD al hacer insert del cotrato.
    		det.setContractId(_newContract.getContractId());
    		
    		res = rbgMapper.insertResBalGasContractDetail(det);
        	if(res!=1){
        		throw new Exception(msgs.getString("insert_error") + " " + msgs.getString("res_bal_con_in_details_table"));   		
    	    }
    	}
	}

	@Transactional(rollbackFor = { Throwable.class })
	public void deleteContract(BigDecimal contractId) throws Exception {
		rbgMapper.deleteContractDetail(contractId);
		rbgMapper.deleteContract(contractId);
	}

	@Override
	public boolean checkDeletableContract(BigDecimal contractId) {
		Integer result = rbgMapper.checkDeletableContract(contractId);
		return result == 0;
	}

	@Override
	public Map<BigDecimal, Object> selectAreas(String systemCode, ReserveBalancingGasContractDetailBean det) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("systemCode", systemCode);
		params.put("zoneId", det.getZoneId());

		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = rbgMapper.getAreas(params);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
}

