package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.LanguageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.UserBean;
import com.atos.beans.dam.MeteredPointBean;
import com.atos.filters.dam.MeteredPointFilter;

public interface MeteredPointService extends Serializable {

	public List<MeteredPointBean> selectMeteredPoints(MeteredPointFilter filter);
	
	public MeteredPointBean selectMeteredPoint(MeteredPointBean item);

	public List<MeteredPointBean> selectMeteredPointsQuery(MeteredPointFilter filter);

	public List<String> selectIds(String query);

	public Map<BigDecimal, Object> selectPointTypes();

	public Map<BigDecimal, Object> selectIdsSystem(BigDecimal idn_system);// offshore

	public Map<BigDecimal, Object> selectSystems();

	public Map<BigDecimal, Object> selectZonesSystem(BigDecimal idn_system);// offshore

	public Map<BigDecimal, Object> selectAreasSystem(BigDecimal idn_system);// offshore

	public Map<BigDecimal, Object> selectCustomerTypes(BigDecimal bigDecimal);

	public Map<String, Object> selectMeteringIds();

	public Map<BigDecimal, Object> selectMeteredPointIds();
	
	public MeteredPointBean selectMeteredPointValues(BigDecimal id);

	public Map<BigDecimal, Object> selectNominationPointsSystem(MeteredPointBean newMeteredPoint); // offshore

	public Map<BigDecimal, Object> selectQualityPointSystem(MeteredPointBean newMeteredPoint); // offshore

	public Map<BigDecimal, Object> selectContractPointsSystem(MeteredPointBean newMeteredPoint);// offshore

	public SystemParameterBean getSystemParameter(String str);

	public String updateMeteredPoint(MeteredPointBean meteredPoint) throws Exception;
	
	public String updateMeteredPointNewPeriod(MeteredPointBean meteredPoint) throws Exception;
	
	public String updatePointCode(MeteredPointBean meteredPoint) throws Exception;

	public String insertMeteredPoint(MeteredPointBean meteredPoint) throws Exception;

	public String insertMeteredPointParam(MeteredPointBean meteredPoint) throws Exception;

	public Map<BigDecimal, Object> selectAreaNominationPoint(MeteredPointBean newMeteredPoint);

	public Map<BigDecimal, Object> selectSubAreaNominationPoint(MeteredPointBean newMeteredPoint);

	public String getAreaCode(BigDecimal Idn_Nomination_Point);

	public BigDecimal getAreaID(BigDecimal Idn_Nomination_Point);

	public String getZoneCode(BigDecimal Idn_Nomination_Point);

	public String getSystemCode(BigDecimal Idn_Nomination_Point);
	
	public BigDecimal getMOCAvailable(String value);

	public void generarMOC(UserBean user, LanguageBean lang) throws Exception;

	public void validateDateRange(MeteredPointBean newPeriodMeteredPoint, UserBean user, LanguageBean lang, String type) throws Exception;

	public String insertMeteredPointNewPeriod(MeteredPointBean newPeriodMeteredPoint) throws Exception;

	public boolean getValidateGroupID (MeteredPointBean meteredPoint);

	public boolean getValidateGroupIDEdit (MeteredPointBean meteredPoint);
}
