package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.SystemParameterDamBean;
import com.atos.filters.dam.SystemParameterDamFilter;

public interface SystemParameterDamMapper {

	public List<SystemParameterDamBean> selectSystemParameters(SystemParameterDamFilter filters);

	public List<ComboFilterNS> selectSystemParameterParameters(BigDecimal idn_module);

	public List<ComboFilterNS> selectSystemParameterModules();

	public List<String> getSystemParameterId(SystemParameterDamBean bean);

	public int insertSystemParameter(SystemParameterDamBean systemParameter);

	public int deleteSystemParameter(SystemParameterDamBean systemParameter);

	public Date getSystemParameterStarDate(SystemParameterDamBean systemParameter);

	public String getCheckValueSystemParameter(SystemParameterDamBean systemParameter);

	public String getParameterCode(SystemParameterDamBean systemParameter);

	public String getParameterDesc(SystemParameterDamBean systemParameter);

	public BigDecimal obtenerModuloInicial();

	public BigDecimal obtenerModuloTariff();
}
