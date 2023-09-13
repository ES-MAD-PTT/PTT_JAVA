package com.atos.mapper.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.QualityPublicationBean;
import com.atos.filters.nominations.QualityPublicationFilter;

public interface QualityPublicationMapper extends Serializable{

	public List<ComboFilterNS> selectZones();
	public List<ComboFilterNS> selectAreasSystem(BigDecimal idn_system); //offshore	
	public List<QualityPublicationBean> selectQualityPublication(QualityPublicationFilter filter);
	public List<ComboFilterNS> selectOperationId(QualityPublicationFilter filters);
}
