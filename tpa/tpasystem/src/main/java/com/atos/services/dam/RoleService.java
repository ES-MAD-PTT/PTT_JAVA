package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.RoleBean;
import com.atos.filters.dam.RoleFilter;

public interface RoleService extends Serializable {

	public List<RoleBean> selectRoles(RoleFilter filter);

	public List<String> selectIds(String query);

	public List<String> selectRole(String query);

	public Map<BigDecimal, Object> selectSystems(BigDecimal idn_system);// offshore

	public Map<BigDecimal, Object> selectComboRolesSystem(BigDecimal idn_system);// offshore

	public Map<BigDecimal, Object> selectProcess();

	public SystemParameterBean getSystemParameter(String str);

	public String updateRole(RoleBean role, Map<BigDecimal, Object> availableProcess, BigDecimal[] selectedProcess)
			throws Exception;

	public String insertRole(RoleBean role, Map<BigDecimal, Object> availableProcess, BigDecimal[] selectedProcess)
			throws Exception;

	public BigDecimal[] selectSelectedProcess(BigDecimal idn_profile);

}
