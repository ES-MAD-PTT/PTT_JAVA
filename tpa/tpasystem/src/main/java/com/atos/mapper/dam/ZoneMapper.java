package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.ZoneBean;
import com.atos.filters.dam.ZoneFilter;

public interface ZoneMapper {

	public List<ZoneBean> selectZones(ZoneFilter filters);

	public List<String> selectIds(String query);

	public List<String> selectNames(String query);

	public List<ComboFilterNS> selectSystems(BigDecimal idn_system);

	public List<ComboFilterNS> selectZonesSystem(BigDecimal idn_system);

	public int insertZone(ZoneBean zone);

	public int insertZoneGasQuality(ZoneBean zone);

	public int insertZoneGasValue(ZoneBean zone);

	public int updateZone(ZoneBean zone);

	public List<String> getZoneId(ZoneBean bean);

	public List<String> getZoneCode(ZoneBean zone);

	public ZoneBean getMaxMin(ZoneBean zone);

	public String getName(BigDecimal idn_zone);

	public ZoneBean getZone(BigDecimal idn_zone);

	public BigDecimal getPipelineSystem(BigDecimal idn_zone);

	public List<ComboFilterNS> selectTypesNewZone();

	public List<ComboFilterNS> selectTypesNewQuality();

	public int deleteZoneGasQuality(ZoneBean zone);

	public Date getGasQualityStarDate(ZoneBean zone);

}
