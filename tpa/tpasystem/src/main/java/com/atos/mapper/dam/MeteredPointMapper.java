package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilter;
import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.MeteredPointBean;
import com.atos.beans.dam.MeteredPointBeanDateRange;
import com.atos.filters.dam.MeteredPointFilter;

public interface MeteredPointMapper {

	public List<MeteredPointBean> selectMeteredPoints(MeteredPointFilter filters);

	public List<MeteredPointBean> selectMeteredPointsQuery(MeteredPointFilter filters);
	
	public List<String> selectIds(String query);

	public List<ComboFilterNS> selectIdsCombo();

	public List<ComboFilterNS> selectIdsComboSystem(BigDecimal idn_system);

	public List<ComboFilterNS> selectPointTypes();

	public List<ComboFilterNS> selectSystem();

	public List<ComboFilterNS> selectZonesSystem(BigDecimal idn_system);// offshore

	public List<ComboFilterNS> selectAreasSystem(BigDecimal idn_system);// offshore

	public List<ComboFilterNS> selectCustomerTypes(BigDecimal idn_system_point_type);

	public List<ComboFilter> selectMeteringIds();
	
	public List<ComboFilterNS> selectMeteredPointIds();
	
	public List<MeteredPointBean> selectMeteredPointValues(BigDecimal id);

	public List<ComboFilterNS> selectNominationPointsSystem(MeteredPointBean newMeteredPoint);

	public List<ComboFilterNS> selectQualityPoint();

	public List<ComboFilterNS> selectQualityPointSystem(MeteredPointBean newMeteredPoint);

	public List<ComboFilterNS> selectContractPointsSystem(MeteredPointBean newMeteredPoint);

	public int insertMeteredPoint(MeteredPointBean meteredPoint);

	public int insertMeteredPointParam(MeteredPointBean meteredPoint);

	public int updateMeteredPoint(MeteredPointBean meteredPoint);

	public int updateMeteredPointParam(MeteredPointBean meteredPoint);

	public List<String> getMeteredPointId(MeteredPointBean bean);

	public List<ComboFilterNS> selectAreaNominationPoint(MeteredPointBean newMeteredPoint);

	public List<ComboFilterNS> selectSubAreaNominationPoint(MeteredPointBean newMeteredPoint);

	public List<String> getMeteredPointCode(MeteredPointBean meteredPoint);

	public List<String> getMeteredPointCodeNewPeriod(MeteredPointBean meteredPoint);

	public int updateMocMetering(MeteredPointBean meteredPoint);

	public int deleteMeteredPointParam(MeteredPointBean meteredPoint);

	public Date getMeteredPointParamStarDate(MeteredPointBean meteredPoint);

	public String getAreaCode(BigDecimal idn_nomination_point);

	public BigDecimal getAreaID(BigDecimal idn_nomination_point);

	public String getSystemCode(BigDecimal idn_nomination_point);

	public String getZoneCode(BigDecimal idn_nomination_point);
	
	public BigDecimal getMOCavailable (String value);

	public String generarMOC(MeteredPointBean meteredPoint);

	public String getValidateDateRange(MeteredPointBeanDateRange bean);

	public List<BigDecimal> getValidateGroupID (MeteredPointBean meteredPoint);

	public List<BigDecimal> getValidateGroupIDEdit (MeteredPointBean meteredPoint);

}
