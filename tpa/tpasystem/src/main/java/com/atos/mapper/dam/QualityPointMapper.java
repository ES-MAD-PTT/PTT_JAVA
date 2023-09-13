package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.atos.beans.ComboFilter;
import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.QualityPointBean;
import com.atos.filters.dam.QualityPointFilter;

public interface QualityPointMapper {

public List<QualityPointBean> selectQualityPoints(QualityPointFilter filters);


	public List<ComboFilterNS> selectPointTypes();
	public List<ComboFilterNS> selectSystem();
	public List<ComboFilterNS> selectQualityPointSystem(BigDecimal idn_system);
	

	public int insertQualityPoint(QualityPointBean qualityPoint);
	public int insertQualityPointParam(QualityPointBean qualityPoint);
	
	public int updateQualityPoint(QualityPointBean qualityPoint);
	public int updateQualityPointParam(QualityPointBean qualityPoint);
	
	public List<String> getQualityPointCode(QualityPointBean qualityPoint);
	public int deleteQualityPointParam(QualityPointBean qualityPoint);
	public Date getQualityPointParamStarDate(QualityPointBean qualityPoint);
	public String getSystemCode(BigDecimal idn_nomination_point);

	

}
