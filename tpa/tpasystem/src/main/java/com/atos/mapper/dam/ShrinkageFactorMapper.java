package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.ShrinkageFactorBean;
import com.atos.beans.dam.StrippingFactorBean;
import com.atos.filters.dam.ShrinkageFactorFilter;

public interface ShrinkageFactorMapper {

	public List<ShrinkageFactorBean> selectShrinkageFactors(ShrinkageFactorFilter filters);

	public List<ComboFilterNS> selectShrinkageFactorZoneSystem(BigDecimal idn_system); // offshore

	public List<String> getShrinkageFactorId(ShrinkageFactorBean bean);

	public int insertShrinkageFactor(ShrinkageFactorBean shrinkageFactor);

	public int deleteShrinkageFactor(ShrinkageFactorBean shrinkageFactor); // es update

	public Date getShrinkageFactorStarDate(ShrinkageFactorBean shrinkageFactor);
	
	public List<ShrinkageFactorBean> getShrinkageFactors(ShrinkageFactorBean shrinkageFactor);
}
