package com.atos.services.dam;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.SimpleByteSource;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.GuestUserBean;
import com.atos.filters.dam.GuestUserFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.GuestUserMapper;

@Service("guestUserService")
public class GuestUserServiceImpl implements GuestUserService {

	/**
	* 
	*/
	private static final long serialVersionUID = 5738619896981240370L;
	@Autowired
	private GuestUserMapper guestUserMapper;
	@Autowired
	private SystemParameterMapper systemParameterMapper;

	public List<GuestUserBean> selectGuestUsers(GuestUserFilter filter) {

		List<GuestUserBean> listUsuarios = guestUserMapper.selectGuestUsers(filter);

		for (int i = 0; i < listUsuarios.size(); i++) {

			BigDecimal idn_user = listUsuarios.get(i).getIdn_user();

			List<String> rolesSource = new ArrayList<String>();
			List<String> rolesTarget = new ArrayList<String>();

			// Unassigned roles
			rolesSource = guestUserMapper.selectNoRolesUser(idn_user);

			// User roles
			rolesTarget = guestUserMapper.selectRolesUser(idn_user);

			listUsuarios.get(i).setRolesUsu(new DualListModel<String>(rolesSource, rolesTarget));
		}

		return listUsuarios;
	}

	public Map<BigDecimal, Object> selectGuestUserId() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = guestUserMapper.selectGuestUserId();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;

	}

	@Override
	public List<String> selectIds(String query) {
		return guestUserMapper.selectIds(query);
	}

	// NO SE USA
	public Map<BigDecimal, Object> selectShipperOperatorIDs() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = guestUserMapper.selectShipperOperatorIDs();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;

	}

	public List<String> selectName(String query) {
		return guestUserMapper.selectName(query);
	}

	public List<String> selectRoles() {
		return guestUserMapper.selectRoles();
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertGuestUser(GuestUserBean guestUser) throws Exception {

		List<String> list = guestUserMapper.getGuestUserId(guestUser);

		if (list.size() > 0) {
			// return 1; // the id is already inserted
			throw new Exception("-1");
		}

		// insert the user
		// We encrypt the pass

		String salt512 = new BigInteger(500, new SecureRandom()).toString(32);
		Sha512Hash hash512 = new Sha512Hash(guestUser.getPassword(), (new SimpleByteSource(salt512)).getBytes());
		String saltedHashedPassword512 = hash512.toHex();

		guestUser.setSalt(salt512);
		guestUser.setPassword(saltedHashedPassword512);

		int ins1 = guestUserMapper.insertGuestUser(guestUser);
		if (ins1 != 1) {
			throw new Exception("-2");
		}

		int ins2 = 0;
		// insert all roles
		for (int i = 0; i < guestUser.getRolesUsu().getTarget().size(); i++) {

			String profile_code = guestUser.getRolesUsu().getTarget().get(i);

			// find the idn of profile
			List<BigDecimal> idn_profile = guestUserMapper.getProfileId(profile_code);
			guestUser.setIdn_profile(idn_profile.get(0));

			// insert in user_profile
			if (idn_profile.size() > 0) {
				ins2 = guestUserMapper.insertUserProfile(guestUser);
				if (ins2 != 1) {
					throw new Exception("-3");
				}
			}

		}

		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String updateGuestUser(GuestUserBean guestUser) throws Exception {

		// if they have not mofidicado the pass this comes to zero or empty
		if (guestUser.getPassword() == null || guestUser.getPassword().equals("")) {

			int upd1 = guestUserMapper.updateGuestUser(guestUser);
			if (upd1 != 1) {
				throw new Exception("-1");
			}

		} else {
			// If you have changed the password do a different inset adding the
			// modified password
			// First, we encrypt the pass

			if (guestUser.getPassword() != null) {
				String salt512 = new BigInteger(500, new SecureRandom()).toString(32);
				Sha512Hash hash512 = new Sha512Hash(guestUser.getPassword(),
						(new SimpleByteSource(salt512)).getBytes());
				String saltedHashedPassword512 = hash512.toHex();

				guestUser.setSalt(salt512);
				guestUser.setPassword(saltedHashedPassword512);

				int upd1 = guestUserMapper.updateGuestUserConPass(guestUser);
				if (upd1 != 1) {
					throw new Exception("-1");
				}

			}

		}

		// delete all the old roles user to upload the new
		int del = 0;
		del = guestUserMapper.deleteUserProfile(guestUser);
		/*
		 * if(del<=0){ return 2; //error borrado hrow new Exception("-2"); }
		 */

		int ins = 0;
		for (int i = 0; i < guestUser.getRolesUsu().getTarget().size(); i++) {
			String profile_code = guestUser.getRolesUsu().getTarget().get(i);

			// search el idn del profile
			List<BigDecimal> list_idn_profile = guestUserMapper.getProfileId(profile_code);
			if (list_idn_profile.size() <= 0) {
				throw new Exception("-3");
			}

			guestUser.setIdn_profile(list_idn_profile.get(0));
			if (list_idn_profile.size() > 0) {
				ins = guestUserMapper.insertUserProfile(guestUser);
				if (ins != 1) {
					throw new Exception("-4");
				}
			}
		}
		return "0";
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

	public List<String> selectNoRolesUser(BigDecimal idn_user) {

		List<String> rolesSource = new ArrayList<String>();
		// roles sin asignar
		rolesSource = guestUserMapper.selectNoRolesUser(idn_user);

		return rolesSource;

	}

	public List<String> selectRolesUser(BigDecimal idn_user) {

		List<String> rolesTarget = new ArrayList<String>();
		// roles of del user
		rolesTarget = guestUserMapper.selectRolesUser(idn_user);

		return rolesTarget;
	}

}
