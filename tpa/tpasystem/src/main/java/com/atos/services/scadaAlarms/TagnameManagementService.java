package com.atos.services.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.scadaAlarms.ScadaLabelBean;
import com.atos.beans.scadaAlarms.ScadaPointBean;
import com.atos.beans.scadaAlarms.TagnameManagementBean;
import com.atos.filters.scadaAlarms.TagnameManagementFilter;

public interface TagnameManagementService extends Serializable{

	
	//public Map<BigDecimal, Object> selectPoints(TagnameManagementBean it);
	public Map<BigDecimal, Object> selectPoints(BigDecimal idn_system);
	public Map<BigDecimal, Object> selectLabels();
	public Map<BigDecimal, Object> selectScadaTagName(BigDecimal idn_system);
	public boolean selectPermiso(TagnameManagementFilter filter);
	
	public List<TagnameManagementBean> selectTagnameManagement(TagnameManagementFilter filter);
	
	//public SystemParameterBean getSystemParameter(String str);

	public String updateTagnameManagement(TagnameManagementBean scadaAlarm) throws Exception;
	public String insertTagnameManagement(TagnameManagementBean scadaAlarm) throws Exception;
	
	public String insertScadaLabel(ScadaLabelBean label) throws Exception;
	public String insertScadaPoint(ScadaPointBean point) throws Exception;

}
