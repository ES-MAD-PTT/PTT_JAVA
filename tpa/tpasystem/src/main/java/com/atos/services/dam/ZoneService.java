package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ZoneBean;
import com.atos.filters.dam.ZoneFilter;

public interface ZoneService extends Serializable {

	public List<ZoneBean> selectZones(ZoneFilter filter);

	public ZoneBean getQuality(ZoneBean zone);

	public List<String> selectIds(String query);

	public List<String> selectNames(String query);

	public Map<BigDecimal, Object> selectSystems(BigDecimal idn_system);

	public Map<BigDecimal, Object> selectZonesSystem(BigDecimal idn_system);// offshore

	public String getName(BigDecimal idn_zone);

	public ZoneBean getZone(BigDecimal idn_zone);

	public BigDecimal getPipelineSystem(BigDecimal idn_zone);

	public SystemParameterBean getSystemParameter(String str);

	public String updateZone(ZoneBean zone) throws Exception;

	public String insertZone(ZoneBean zone) throws Exception;

	public Map<BigDecimal, Object> selectTypesNewZone();

	public Map<BigDecimal, Object> selectTypesNewQuality();
}
