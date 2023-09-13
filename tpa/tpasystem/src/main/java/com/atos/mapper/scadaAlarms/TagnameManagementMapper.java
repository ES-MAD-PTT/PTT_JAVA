package com.atos.mapper.scadaAlarms;

import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

import com.atos.beans.ComboFilter;
import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.ShipperBean;
import com.atos.beans.scadaAlarms.ScadaLabelBean;
import com.atos.beans.scadaAlarms.ScadaPointBean;
import com.atos.beans.scadaAlarms.TagnameManagementBean;
import com.atos.filters.scadaAlarms.TagnameManagementFilter;

public interface TagnameManagementMapper {
	
	public List<ComboFilterNS> selectPoints(BigDecimal idn_system);
	public List<ComboFilterNS> selectLabels();
	public List<ComboFilterNS> selectScadaTagName(BigDecimal idn_system);
	public BigDecimal selectPermiso(TagnameManagementFilter filters);
	public List<TagnameManagementBean> selectTagnameManagement(TagnameManagementFilter filters);
	public List<String> getTagnameId(TagnameManagementBean bean);
	public int insertTagnameManagements(TagnameManagementBean item) throws Exception;
	public int updateTagnameManagement(TagnameManagementBean scadaAlarm);
	
	public List<String> getLabelId(ScadaLabelBean label);
	public int insertScadaLabel(ScadaLabelBean label) throws Exception;
	
	public List<String> getPointId(ScadaPointBean point);
	public int insertScadaPoint(ScadaPointBean point) throws Exception;
	
	
	
	
	
}
