package com.atos.mapper.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.QualityPubShipperBean;
import com.atos.filters.nominations.QualityPubShipperFilter;

public interface QualityPubShipperMapper extends Serializable{

	public List<ComboFilterNS> selectZones();
	public List<ComboFilterNS> selectAreasSystem(BigDecimal idn_system); //offshore	
	public List<QualityPubShipperBean> selectQualityPubShipper(QualityPubShipperFilter filter);
}
