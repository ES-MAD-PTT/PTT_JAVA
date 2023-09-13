package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.AreaNominalCapacityBean;
import com.atos.filters.dam.AreaNominalCapacityFilter;

public interface AreaNominalCapacityService extends Serializable {

	public List<AreaNominalCapacityBean> selectAreaNominalCapacitys(AreaNominalCapacityFilter filter);

	public Map<BigDecimal, Object> selectAreaNominalCapacityAreaSystem(BigDecimal idn_system);// offshore

	public SystemParameterBean getSystemParameter(String str);

	public String deleteAreaNominalCapacity(AreaNominalCapacityBean areaNominalCapacity) throws Exception;

	public String insertAreaNominalCapacity(AreaNominalCapacityBean areaNominalCapacity) throws Exception;

	public Map<BigDecimal, Object> selectAreaNominalCapacityYear(int year);
}
