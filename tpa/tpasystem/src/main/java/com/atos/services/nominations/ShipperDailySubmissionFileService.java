package com.atos.services.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;


import com.atos.beans.FileBean;
import com.atos.beans.nominations.NominationBean;
import com.atos.filters.nominations.ShipperSubmissionFileFilter;

public interface ShipperDailySubmissionFileService extends Serializable{

	// Se utiliza este separador para incluir en una excepcion un codigo de error y su descripcion.
	public static final String errorSeparator = "#";

	public Map<String, Object> selectContractCodeByUser(ShipperSubmissionFileFilter filter);
	
	public BigDecimal selectIdnUserGroup(ShipperSubmissionFileFilter filter);
	
	public BigDecimal selectOperationCategory();
	
	public BigDecimal selectOperationTerm();

	public BigDecimal selectOperation(TreeMap<String,BigDecimal> map);
	
	public NominationBean selectIdnNomination(NominationBean bean);

	public String saveFile(ShipperSubmissionFileFilter filters, FileBean file) throws Exception;
	
	public String selectOperationSubmissionDeadlinePhrase(String _operType);

}
