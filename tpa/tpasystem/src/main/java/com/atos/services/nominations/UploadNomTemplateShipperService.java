package com.atos.services.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.primefaces.model.StreamedContent;

import com.atos.beans.nominations.UploadNomTemplateShipperBean;
import com.atos.filters.nominations.UploadNomTemplateShipperFilter;

public interface UploadNomTemplateShipperService extends Serializable{
	
	public Map<BigDecimal, Object> selectShipperId();
	
	public Map<BigDecimal, Object> selectOperationId();
	
	public Map<BigDecimal, Object> selectContractId(UploadNomTemplateShipperFilter filter);
	
	public List<UploadNomTemplateShipperBean> search(UploadNomTemplateShipperFilter filters);

	public int insertTemplateShipper (UploadNomTemplateShipperBean bean) throws Exception;
	
	public int updateTemplateShipper (UploadNomTemplateShipperBean bean) throws Exception;

	public StreamedContent getFile(BigDecimal idn_nom_template_contract);
}
