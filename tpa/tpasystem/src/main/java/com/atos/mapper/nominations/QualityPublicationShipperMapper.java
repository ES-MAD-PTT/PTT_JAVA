package com.atos.mapper.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.QualityPublicationShipperBean;
import com.atos.filters.nominations.QualityPublicationShipperFilter;


public interface QualityPublicationShipperMapper extends Serializable{

	public List<ComboFilterNS> selectZones();
	public List<ComboFilterNS> selectAreasSystem(BigDecimal idn_system); //offshore	
	public List<ComboFilterNS> selectShippers();
	public List<QualityPublicationShipperBean> selectQualityPublicationShipper(QualityPublicationShipperFilter filter);

}
