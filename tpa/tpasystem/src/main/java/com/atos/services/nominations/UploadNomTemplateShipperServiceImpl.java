package com.atos.services.nominations;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.UploadNomTemplateShipperBean;
import com.atos.filters.nominations.UploadNomTemplateShipperFilter;
import com.atos.mapper.nominations.UploadNomTemplateShipperMapper;

@Service("uploadNomTemplateShipperService")
public class UploadNomTemplateShipperServiceImpl implements UploadNomTemplateShipperService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7519246586679794246L;
	
	@Autowired
	private UploadNomTemplateShipperMapper tfsMapper;	
	
	@Override
	public Map<BigDecimal, Object> selectShipperId() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = tfsMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public Map<BigDecimal, Object> selectOperationId() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = tfsMapper.selectOperationId();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public Map<BigDecimal, Object> selectContractId(UploadNomTemplateShipperFilter filter){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = tfsMapper.selectContractId(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public List<UploadNomTemplateShipperBean> search(UploadNomTemplateShipperFilter filters) {
		return tfsMapper.search(filters);
	}
	
	@Override
	public int insertTemplateShipper(UploadNomTemplateShipperBean bean) throws Exception{
		tfsMapper.insertTemplateShipper(bean);	
		return 0;
	}
	
	@Override
	public int updateTemplateShipper(UploadNomTemplateShipperBean bean) throws Exception{
		tfsMapper.updateTemplateShipper(bean);	
		return 0;
	}

	@Override
	public StreamedContent getFile(BigDecimal idn_nom_template_contract) {

		UploadNomTemplateShipperBean l = tfsMapper.selectTemplateFile(idn_nom_template_contract);
		
		ByteArrayInputStream ba = new ByteArrayInputStream(l.getBinary_data());
		return new DefaultStreamedContent(ba, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", l.getDocument_name());
	
	}
}
