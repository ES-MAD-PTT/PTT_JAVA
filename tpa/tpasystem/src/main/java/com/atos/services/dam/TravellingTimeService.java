package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.TravellingTimeBean;
import com.atos.filters.dam.TravellingTimeFilter;

public interface TravellingTimeService extends Serializable {

	public List<TravellingTimeBean> selectTravellingTimes(TravellingTimeFilter filter);

	public Map<BigDecimal, Object> getIdsOriPointSystem(BigDecimal idn_system);

	public Map<BigDecimal, Object> getIdsDestPointSystem(BigDecimal idn_system);

	public List<BigDecimal> selectTravellingTimeDays(TravellingTimeBean bean) throws Exception;

	public SystemParameterBean getSystemParameter(String str);

	public String insertTravellingTime(TravellingTimeBean bean) throws Exception;

	public String getOriginCode(TravellingTimeBean bean);

	public String getDestCode(TravellingTimeBean bean);

}
