package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.AreaNominalCapacityBean;
import com.atos.filters.dam.AreaNominalCapacityFilter;

public interface AreaNominalCapacityMapper {

	public List<AreaNominalCapacityBean> selectAreaNominalCapacitys(AreaNominalCapacityFilter filters);

	public List<ComboFilterNS> selectAreaNominalCapacityAreaSystem(BigDecimal idn_system); // offshore

	public List<String> getAreaNominalCapacityId(AreaNominalCapacityBean bean);

	public int insertAreaNominalCapacity(AreaNominalCapacityBean areaNominalCapacity);

	public int deleteAreaNominalCapacity(AreaNominalCapacityBean areaNominalCapacity);

	public Date getAreaNominalStartDate(AreaNominalCapacityBean shrinkageFactor);

}
