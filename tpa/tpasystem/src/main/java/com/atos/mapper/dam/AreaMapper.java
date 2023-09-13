package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.AreaBean;
import com.atos.filters.dam.AreaFilter;

public interface AreaMapper {

	public List<AreaBean> selectAreas(AreaFilter filters);

	public List<ComboFilterNS> selectIdsCombo(BigDecimal idn_system);

	public List<String> selectNames(String query);

	public List<ComboFilterNS> selectZonesSystem(BigDecimal idn_system); // offShore

	public List<ComboFilterNS> selectSystems(BigDecimal idn_system);// offShore

	public List<String> getAreaId(AreaBean bean);

	public int insertArea(AreaBean area);

	public int updateArea(AreaBean area);

}
