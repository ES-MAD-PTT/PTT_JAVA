package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.TravellingTimeBean;
import com.atos.filters.dam.TravellingTimeFilter;

public interface TravellingTimeMapper {

	public List<TravellingTimeBean> selectTravellingTimes(TravellingTimeFilter filters);

	public List<ComboFilterNS> getIdsOriPointSystem(BigDecimal idn_system);

	public List<ComboFilterNS> getIdsDestPointSystem(BigDecimal idn_system);

	public List<BigDecimal> selectTravellingTimeDays(TravellingTimeBean travellingTime);

	public List<String> getTravellingTimeId(TravellingTimeBean bean);

	public int insertTravellingTime(TravellingTimeBean travellingTime);
	
	public String getOriginCode(TravellingTimeBean bean);
	public String getDestCode(TravellingTimeBean bean);

}
