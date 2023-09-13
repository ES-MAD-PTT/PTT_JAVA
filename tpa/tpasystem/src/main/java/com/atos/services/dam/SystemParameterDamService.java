package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.SystemParameterDamBean;
import com.atos.filters.dam.SystemParameterDamFilter;

public interface SystemParameterDamService extends Serializable {

	public List<SystemParameterDamBean> selectSystemParameters(SystemParameterDamFilter filter);

	public Map<BigDecimal, Object> selectSystemParameterModules();

	public Map<BigDecimal, Object> selectSystemParameterParameters(BigDecimal idn_module);

	public SystemParameterBean getSystemParameter(String str);

	public String insertSystemParameter(SystemParameterDamBean SystemParameter) throws Exception;

	public String deleteSystemParameter(SystemParameterDamBean SystemParameter) throws Exception;

	public String getCheckValueSystemParameter(SystemParameterDamBean systemParameter) throws Exception;

	public BigDecimal obtenerModuloInicial();

	public BigDecimal obtenerModuloTariff();
}
