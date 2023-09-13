package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.NominationPointBean;
import com.atos.filters.dam.NominationPointFilter;

public interface NominationPointService extends Serializable {

	public List<NominationPointBean> selectNominationPoints(NominationPointFilter filter);

	//public List<String> selectIds(String query);

	public Map<BigDecimal, Object> selectIdsSystem(BigDecimal idn_system);// offshore

	public Map<BigDecimal, Object> selectPointTypes();

	public Map<BigDecimal, Object> selectSystems(BigDecimal idn_system);// offshore

	public Map<BigDecimal, Object> selectZonesSystem(BigDecimal idn_system); // offshore

	public Map<BigDecimal, Object> selectAreasSystem(BigDecimal idn_system); // offshore

	public Map<BigDecimal, Object> selectAreas(NominationPointBean meteredPoint);

	public SystemParameterBean getSystemParameter(String str);

	public String updateNominationPoint(NominationPointBean nominationPoint) throws Exception;

	public String insertNominationPoint(NominationPointBean nominationPoint) throws Exception;

	public Map<BigDecimal, Object> selectAreaNominationPoint(NominationPointBean newNominationPoint);

	public Map<BigDecimal, Object> selectSubAreaNominationPoint(NominationPointBean newNominationPoint);

}
