package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ShrinkageFactorBean;
import com.atos.filters.dam.ShrinkageFactorFilter;

public interface ShrinkageFactorService extends Serializable {

	public List<ShrinkageFactorBean> selectShrinkageFactors(ShrinkageFactorFilter filter);

	public Map<BigDecimal, Object> selectShrinkageFactorZoneSystem(BigDecimal idn_system);// offshore

	public SystemParameterBean getSystemParameter(String str);

	public String insertShrinkageFactor(ShrinkageFactorBean shrinkageFactor) throws Exception;

	public String deleteShrinkageFactor(ShrinkageFactorBean shrinkageFactor) throws Exception;// update
	
	public List<ShrinkageFactorBean> getShrinkageFactor(ShrinkageFactorBean shrinkageFactor);
}
