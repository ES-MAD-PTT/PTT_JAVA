package com.atos.services.dam;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.RoleBean;
import com.atos.filters.dam.RoleFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.RoleMapper;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private SystemParameterMapper systemParameterMapper;

	public List<RoleBean> selectRoles(RoleFilter filter) {
		return roleMapper.selectRoles(filter);

	}

	public BigDecimal[] selectSelectedProcess(BigDecimal idn_profile) {

		BigDecimal[] selectedProcess;
		selectedProcess = roleMapper.selectSelectedProcess(idn_profile);

		return selectedProcess;

	}

	public SystemParameterBean getSystemParameter(String str) {
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(systemParameterMapper.getSysdate().get(0));
		bean.setParameter_name(str);
		bean.setUser_id((String) SecurityUtils.getSubject().getPrincipal());
		bean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		systemParameterMapper.getIntegerSystemParameter(bean);
		return bean;
	}

	@Override
	public List<String> selectIds(String query) {
		return roleMapper.selectIds(query);
	}

	@Override
	public List<String> selectRole(String query) {
		return roleMapper.selectRole(query);

	}

	// offshore
	@Override
	public Map<BigDecimal, Object> selectComboRolesSystem(BigDecimal idn_system) {

		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = roleMapper.selectComboRolesSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectSystems(BigDecimal idn_system) {

		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = roleMapper.selectSystems(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectProcess() {

		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = roleMapper.selectProcess();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertRole(RoleBean role, Map<BigDecimal, Object> availableProcess, BigDecimal[] selectedProcess)
			throws Exception {

		List<String> list = roleMapper.getRoleId(role);
		if (list.size() > 0) {
			// the id is already inserted
			throw new Exception("-1");
		}

		int ins = roleMapper.insertProfile(role);
		if (ins != 1) {
			throw new Exception("-2");
		}

		int ins2 = roleMapper.insertProfilePipeline(role);
		if (ins2 != 1) {
			throw new Exception("-3");
		}

		// hay que hacer un for insertar todos los permisos
		Iterator it = availableProcess.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry ent = (Map.Entry) it.next();
			BigDecimal idn_permision_available = (BigDecimal) ent.getKey();
			role.setIdn_permission(idn_permision_available);
			// ponemos por defecto a 'N' el permiso
			role.setsIsEnabled("N");
			int ins3 = roleMapper.insertProfilePermission(role);
			if (ins3 != 1) {
				throw new Exception("-4");
			}
		}

		for (int i = 0; i < selectedProcess.length; i++) {

			role.setIdn_permission(selectedProcess[i]);
			List<BigDecimal> listIdnProfPer = roleMapper.getProfilePermission(role);

			if (listIdnProfPer.size() > 0) {
				role.setIdn_profile_permission(listIdnProfPer.get(0));

				role.setsIsEnabled("Y");

				int upd1 = roleMapper.updateProfilePermission(role);
				if (upd1 != 1) {
					throw new Exception("-5");
				}
			}

		}

		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String updateRole(RoleBean role, Map<BigDecimal, Object> availableProcess, BigDecimal[] selectedProcess)
			throws Exception {

		int upd = roleMapper.updateProfile(role);
		if (upd != 1) {
			throw new Exception("-1");
		}

		int upd2 = roleMapper.updateProfilePipeline(role);
		if (upd2 != 1) {
			throw new Exception("-2");
		}

		if (role.isbIsEnabled()) {
			role.setsIsEnabled("Y");
		} else
			role.setsIsEnabled("N");

		// actualizamos todos los permisos a no
		Iterator it = availableProcess.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry ent = (Map.Entry) it.next();
			BigDecimal idn_permision_available = (BigDecimal) ent.getKey();
			role.setIdn_permission(idn_permision_available);
			// ponemos por defecto a 'N' el permiso
			role.setsIsEnabled("N");
			int upd3 = roleMapper.updateProfilePermission(role);
			if (upd3 != 1) {
				// throw new Exception("-3");

				// 01/09/2017
				// cuando se da un Permission nuevo, no se le da de alta en la
				// tabla ProfilePersmision para todos
				// los perfiles... por eso en caso de actualizar permisos(poneR
				// o quitar) a un perfil hay que, en el caso de que no exista,
				// darle de alta:
				// se hace un insert con el valor a N en IsEnabled (en el
				// siguiente paso se ponen a S los que corresponda)
				int ins = roleMapper.insertProfilePermission(role);
				if (ins != 1) {
					throw new Exception("-4");
				}

			}
		}
		// ponemos a Y a los que tienen marcado el ckeck
		for (int i = 0; i < selectedProcess.length; i++) {
			role.setIdn_permission(selectedProcess[i]);
			List<BigDecimal> listIdnProfPer = roleMapper.getProfilePermission(role);

			if (listIdnProfPer.size() > 0) {
				role.setIdn_profile_permission(listIdnProfPer.get(0));
				role.setsIsEnabled("Y");

				int upd1 = roleMapper.updateProfilePermission(role);
				if (upd1 != 1) {
					throw new Exception("-3");
				}
			}

		}

		return "0";
	}

}
