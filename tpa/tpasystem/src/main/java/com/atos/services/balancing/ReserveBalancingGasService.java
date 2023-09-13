package com.atos.services.balancing;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ReserveBalancingGasDto;
import com.atos.beans.balancing.ReserveBalancingGasBean;
import com.atos.beans.dam.UserGuideBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.balancing.ReserveBalancigGasFilter;
import com.atos.mapper.allocation.AllocationManagementMapper;
import com.atos.mapper.balancing.ReserveBalancingGasMapper;

@Service("reserveBalancingGasService")
public class ReserveBalancingGasService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6368558764812655568L;
	@Autowired
	ReserveBalancingGasMapper mapper;
	@Autowired
	private AllocationManagementMapper amMapper;
	
	public Map<BigDecimal, Object> selectShipperId(BigDecimal idnUser){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.selectShipperId(idnUser);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public List<ReserveBalancingGasDto> search(ReserveBalancigGasFilter filter){
		return mapper.search(filter);
	}
	
	
	public Map<BigDecimal, Object> selectReserveBalId(BigDecimal shipperId, BigDecimal idnSystem) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		HashMap<String, BigDecimal> params = new HashMap<>();
		params.put("idnUserGroup", shipperId);
		params.put("idnSystem", idnSystem);
		List<ComboFilterNS> list = mapper.selectReserveBalId(params);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}

	public void save(ReserveBalancingGasBean newReserve) {
		mapper.save(newReserve);
		
	}

	public void getFile(ReserveBalancingGasDto _contract) throws ValidationException {

    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	// Solo se consulta el fichero 1 vez de base de datos. Si ya existe, no se consulta.
    	if( _contract.getScFile() == null) {

    		BigDecimal contractId = _contract.getIdnResbalForecastFile();
			if(contractId == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));

    		List<ReserveBalancingGasDto> lData = mapper.getFile(contractId);
	
			if(lData == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));
			
			// Solo se va a tener un fichero por cada contract attachment.
			ReserveBalancingGasDto tmpBean = lData.get(0);
			if(tmpBean == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));		
	
			byte[] ba = tmpBean.getBinaryData();
			if(ba == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));		
			
			_contract.setBinaryData(Arrays.copyOf(ba, ba.length));
    	}
		
	}

	public Date selectOpenPeriodFirstDay(Map<String, Object> params) {
		return amMapper.selectOpenPeriodFirstDay(params);
	}

	public boolean isValidDateForcontract(ReserveBalancingGasBean newReserve) {
		int result = mapper.checkValidDateForContract(newReserve);
		return result != 0;
	}
	
	public StreamedContent getTemplate() throws ValidationException {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		UserGuideBean userGuide = new UserGuideBean();
		List<UserGuideBean> l = mapper.selectTemplate(userGuide);
		
		if(l == null || l.size()==0  ){
			throw new ValidationException(msgs.getString("no_file_to_download"));
		} else {
			UserGuideBean b = l.get(0);
			
			if(b == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));	
			
						
			
			ByteArrayInputStream ba = new ByteArrayInputStream(b.getBinary_data());

			return new DefaultStreamedContent(ba, "", b.getDocument_name());
		}
	}

}
