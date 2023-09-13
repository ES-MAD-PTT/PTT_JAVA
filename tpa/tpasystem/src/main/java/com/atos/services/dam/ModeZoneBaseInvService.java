package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ModeZoneBaseInvBean;
import com.atos.filters.dam.ModeZoneBaseInvFilter;

public interface ModeZoneBaseInvService extends Serializable {

	public List<ModeZoneBaseInvBean> selectModeZoneBaseInv(ModeZoneBaseInvFilter filter);

	public Map<BigDecimal, Object> selectZonesSystem(BigDecimal idn_system);// offshore
	
	public Map<BigDecimal, Object> selectModesSystem(BigDecimal idn_zone);// offshore

	public SystemParameterBean getSystemParameter(String str);

	public String updateModeZoneBaseInv(ModeZoneBaseInvBean area) throws Exception;

	public String insertModeZoneBaseInv(ModeZoneBaseInvBean area) throws Exception;

}
