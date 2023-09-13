package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.AreaBean;
import com.atos.filters.dam.AreaFilter;

public interface AreaService extends Serializable {

	public List<AreaBean> selectAreas(AreaFilter filter);

	public List<String> selectNames(String query);

	public Map<BigDecimal, Object> selectIds(BigDecimal idn_system);// offshore

	public Map<BigDecimal, Object> selectZonesSystem(BigDecimal idn_system);// offshore

	public Map<BigDecimal, Object> selectSystems(BigDecimal idn_system);// offshore

	public SystemParameterBean getSystemParameter(String str);

	public String updateArea(AreaBean area) throws Exception;

	public String insertArea(AreaBean area) throws Exception;

}
