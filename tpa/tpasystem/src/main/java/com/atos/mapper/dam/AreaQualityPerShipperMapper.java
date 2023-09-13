package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.AreaQualityPerShipperBean;
import com.atos.beans.dam.AreaQualityPerShipperDetailsBean;
import com.atos.beans.dam.ZoneBean;
import com.atos.filters.dam.AreaQualityPerShipperFilter;
import com.atos.filters.dam.ZoneFilter;

public interface AreaQualityPerShipperMapper {

	public List<ComboFilterNS>selectAreasIds(BigDecimal idn_system);
	
	public List<ComboFilterNS>selectShippersIds();
	
	public List<AreaQualityPerShipperBean> selectAreasShipper(AreaQualityPerShipperFilter filter);

	public List<AreaQualityPerShipperDetailsBean> getMaxMin();
	
	public String areaDesc(BigDecimal area);
	
	public int insert(AreaQualityPerShipperBean bean);
	
	public int insertParamsWi(AreaQualityPerShipperBean bean);
	
	public int insertParamsHv(AreaQualityPerShipperBean bean);
	
	public int updateStartDateGasQuality(AreaQualityPerShipperBean bean);

}
