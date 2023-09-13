package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.StrippingFactorBean;
import com.atos.filters.dam.StrippingFactorFilter;

public interface StrippingFactorMapper {

	public List<StrippingFactorBean> selectStrippingFactors(StrippingFactorFilter filters);

	public List<ComboFilterNS> selectStrippingFactorZoneSystem(BigDecimal idn_system); // offshore

	public List<String> getStrippingFactorId(StrippingFactorBean bean);

	public int insertStrippingFactor(StrippingFactorBean strippingFactor);

	public int deleteStrippingFactor(StrippingFactorBean strippingFactor);// update

	public Date getStrippingFactorStarDate(StrippingFactorBean strippingFactor);

	public List<BigDecimal> getStrippingFactors(StrippingFactorBean strippingFactor);

}
