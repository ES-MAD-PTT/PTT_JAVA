package com.atos.mapper.nominations;

import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.UploadNomTemplateShipperBean;
import com.atos.filters.nominations.UploadNomTemplateShipperFilter;

public interface UploadNomTemplateShipperMapper {
	
	public List<ComboFilterNS> selectShipperId();
	
	public List<ComboFilterNS> selectOperationId();
	
	public List<ComboFilterNS> selectContractId(UploadNomTemplateShipperFilter filter);
	
	public List<UploadNomTemplateShipperBean> search(UploadNomTemplateShipperFilter filters);
	
	public int insertTemplateShipper(UploadNomTemplateShipperBean bean);
	
	public UploadNomTemplateShipperBean selectTemplateFile(BigDecimal idn_nom_template_contract);
	
	public int updateTemplateShipper(UploadNomTemplateShipperBean bean);
}
