package com.atos.mapper.dam;

import java.util.Date;
import java.util.List;

import com.atos.beans.dam.PimsGraphicMenuBean;
import com.atos.filters.dam.PimsGraphicMenuFilter;


public interface PimsGraphicMenuMapper {

	public List<PimsGraphicMenuBean> selectPmisGraphicMenus(PimsGraphicMenuFilter filters);

	public List<String> selectPmisGraphicMenusValue();
	
	public List<String> getSystemParameterId(PimsGraphicMenuBean bean);
	

	public int insertSystemParameter(PimsGraphicMenuBean systemParameter);

	public int deleteSystemParameter(PimsGraphicMenuBean systemParameter);

	public Date getSystemParameterStarDate(PimsGraphicMenuBean systemParameter);

	public String getCheckValueSystemParameter(PimsGraphicMenuBean systemParameter);

	public String getParameterCode(PimsGraphicMenuBean systemParameter);

	public String getParameterDesc(PimsGraphicMenuBean systemParameter);
}
