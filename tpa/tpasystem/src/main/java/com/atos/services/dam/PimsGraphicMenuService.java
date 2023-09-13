package com.atos.services.dam;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.PimsGraphicMenuBean;
import com.atos.filters.dam.PimsGraphicMenuFilter;

public interface PimsGraphicMenuService extends Serializable {

	public List<PimsGraphicMenuBean> selectPmisGraphicMenus(PimsGraphicMenuFilter filter);

	public String insertSystemParameter(PimsGraphicMenuBean SystemParameter) throws Exception;

	public String deleteSystemParameter(PimsGraphicMenuBean SystemParameter) throws Exception;

	public String getCheckValueSystemParameter(PimsGraphicMenuBean systemParameter) throws Exception;

	public String selectPmisGraphicMenusValue();
	
	public SystemParameterBean getSystemParameter(String str);
}
