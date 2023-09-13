package com.atos.services.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.nominations.QualityPublicationShipperBean;
import com.atos.filters.nominations.QualityPublicationShipperFilter;


public interface QualityPublicationShipperService extends Serializable {

	public Map<BigDecimal, Object> selectZones();
	public Map<BigDecimal, Object> selectAreasSystem(BigDecimal idn_system); //offshore
	public Map<BigDecimal, Object> selectShippers();
	public List<QualityPublicationShipperBean> search(QualityPublicationShipperFilter filter);
}
