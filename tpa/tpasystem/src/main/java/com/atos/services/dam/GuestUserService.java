package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.GuestUserBean;
import com.atos.filters.dam.GuestUserFilter;

public interface GuestUserService extends Serializable {

	public List<GuestUserBean> selectGuestUsers(GuestUserFilter filter);

	public List<String> selectIds(String query);

	public Map<BigDecimal, Object> selectGuestUserId(); // NO SE USA

	public Map<BigDecimal, Object> selectShipperOperatorIDs();

	public List<String> selectName(String query);

	public SystemParameterBean getSystemParameter(String str);

	public String updateGuestUser(GuestUserBean guestUser) throws Exception;

	public String insertGuestUser(GuestUserBean guestUser) throws Exception;

	public List<String> selectRoles();

	public List<String> selectNoRolesUser(BigDecimal idn_user);

	public List<String> selectRolesUser(BigDecimal idn_user);

}