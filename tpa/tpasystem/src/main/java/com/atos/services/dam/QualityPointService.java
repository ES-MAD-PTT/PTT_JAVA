package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.LanguageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.UserBean;
import com.atos.beans.dam.QualityPointBean;
import com.atos.filters.dam.QualityPointFilter;

public interface QualityPointService extends Serializable {

public List<QualityPointBean> selectQualityPoints(QualityPointFilter filter);
	
public Map<BigDecimal, Object> selectPointTypes();
public Map<BigDecimal, Object> selectSystems();
public Map<BigDecimal, Object> selectQualityPointSystem(BigDecimal idn_system); // offshore
	public SystemParameterBean getSystemParameter(String str);
	public String updateQualityPoint(QualityPointBean qualityPoint) throws Exception;
	public String insertQualityPoint(QualityPointBean qualityPoint) throws Exception;

}
