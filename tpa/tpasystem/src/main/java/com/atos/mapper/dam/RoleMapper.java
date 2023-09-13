package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.RoleBean;
import com.atos.filters.dam.RoleFilter;

public interface RoleMapper {

	public List<RoleBean> selectRoles(RoleFilter filters);

	public List<String> selectIds(String query);

	public List<String> selectRole(String query);

	public List<ComboFilterNS> selectSystems(BigDecimal idn_system);// offshore

	public List<ComboFilterNS> selectComboRolesSystem(BigDecimal idn_system);// offshore

	public List<ComboFilterNS> selectProcess();

	public int insertProfile(RoleBean role);

	public int updateProfile(RoleBean role);

	public int insertProfilePipeline(RoleBean role);

	public int updateProfilePipeline(RoleBean role);

	public int insertProfilePermission(RoleBean role);

	public int updateProfilePermission(RoleBean role);

	public List<String> getRoleId(RoleBean bean);

	public List<BigDecimal> getProfilePermission(RoleBean bean);

	public List<String> getRoleCode(RoleBean role);

	public BigDecimal[] selectSelectedProcess(BigDecimal idn_profile);

}
