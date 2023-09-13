package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.List;


import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.GuestUserBean;
import com.atos.filters.dam.GuestUserFilter;

public interface GuestUserMapper {

	public List<GuestUserBean> selectGuestUsers(GuestUserFilter filters);
	public List<ComboFilterNS> selectGuestUserId();
	public List<String> selectIds(String query);
	public List<ComboFilterNS> selectShipperOperatorIDs();
	
	
	public List<String> selectName(String query);
	public List<String> selectRoles();
	
	public List<String> selectRolesUser(BigDecimal idnuser);
	public List<String> selectNoRolesUser(BigDecimal idnuser);
	public List<String> getGuestUserId(GuestUserBean bean);
	
	public int insertGuestUser(GuestUserBean guestUser);
	public int insertUserProfile(GuestUserBean guestUser);
	public int deleteUserProfile(GuestUserBean guestUser);
	
	public int updateGuestUser(GuestUserBean guestUser);
	public int updateGuestUserConPass(GuestUserBean guestUser);
		
	public List<BigDecimal> getProfileId(String profile_code);
	
	
	
	
	
	
}
