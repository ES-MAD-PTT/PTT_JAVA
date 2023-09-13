package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.AreaNominalCapacityBean;
import com.atos.beans.dam.AreaQualityPerShipperBean;
import com.atos.beans.dam.AreaQualityPerShipperDetailsBean;
import com.atos.beans.dam.ZoneBean;
import com.atos.filters.dam.AreaQualityPerShipperFilter;
import com.atos.filters.dam.ZoneFilter;

public interface AreaQualityPerShipperService extends Serializable {

	public  Map<BigDecimal, Object> selectAreasIds(BigDecimal idn_system);
	
	public  Map<BigDecimal, Object> selectShippersIds();
	
	public List<AreaQualityPerShipperBean> selectAreasShipper(AreaQualityPerShipperFilter filter);
	
	public List<AreaQualityPerShipperDetailsBean> getMaxMin();
	
	public String insert(AreaQualityPerShipperBean bean) throws Exception;
	
	public String getAreaDesc(BigDecimal idn_area);
	
	public String update(AreaQualityPerShipperBean bean) throws Exception;
	
	public SystemParameterBean getSystemParameter(String str);
}
