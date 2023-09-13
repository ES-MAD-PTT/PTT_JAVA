package com.atos.mapper.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ReserveBalancingGasSbsDto;
import com.atos.beans.balancing.ReserveBalancingGasSbsBean;
import com.atos.filters.balancing.ReserveBalancigGasSbsFilter;

public interface ReserveBalancingGasSbsMapper extends Serializable {

	List<ComboFilterNS> getCapContractsIds(ReserveBalancigGasSbsFilter filter);

	List<ComboFilterNS> getZones(HashMap<String, BigDecimal> params);

	List<ComboFilterNS> getZonesForSearch(BigDecimal idnSystem);

	List<ReserveBalancingGasSbsDto> search(ReserveBalancigGasSbsFilter filter);

	List<ComboFilterNS> getPoints(ReserveBalancingGasSbsBean newOperation);

	List<Date> getInvalidDates(ReserveBalancingGasSbsBean newOperation);

	void insertOperation(ReserveBalancingGasSbsBean newOperation2);

	void updateOperation(ReserveBalancingGasSbsDto dto);

}
