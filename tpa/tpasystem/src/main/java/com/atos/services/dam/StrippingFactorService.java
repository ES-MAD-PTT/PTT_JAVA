package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.StrippingFactorBean;
import com.atos.filters.dam.StrippingFactorFilter;

public interface StrippingFactorService extends Serializable {

	public List<StrippingFactorBean> selectStrippingFactors(StrippingFactorFilter filter);

	public Map<BigDecimal, Object> selectStrippingFactorZoneSystem(BigDecimal idn_system);// offshore

	public SystemParameterBean getSystemParameter(String str);

	public String insertStrippingFactor(StrippingFactorBean strippingFactor) throws Exception;

	public String deleteStrippingFactor(StrippingFactorBean strippingFactor) throws Exception;

	public List<BigDecimal> getStrippingFactor(StrippingFactorBean strippingFactor);
}
