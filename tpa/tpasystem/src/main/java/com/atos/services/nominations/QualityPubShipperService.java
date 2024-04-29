package com.atos.services.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.nominations.QualityPubShipperBean;
import com.atos.filters.nominations.QualityPubShipperFilter;


public interface QualityPubShipperService extends Serializable {

	public Map<BigDecimal, Object> selectZones();
	public Map<BigDecimal, Object> selectAreasSystem(BigDecimal idn_system); //offshore
	public List<QualityPubShipperBean> search(QualityPubShipperFilter filter);
}
