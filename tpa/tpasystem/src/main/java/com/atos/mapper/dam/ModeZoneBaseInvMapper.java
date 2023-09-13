package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.ModeZoneBaseInvBean;
import com.atos.filters.dam.ModeZoneBaseInvFilter;


public interface ModeZoneBaseInvMapper {

	public List<ModeZoneBaseInvBean> selectModeZoneBaseInv(ModeZoneBaseInvFilter filters);

	public List<ComboFilterNS> selectIdsCombo(BigDecimal idn_system);

	public List<ComboFilterNS> selectZonesSystem(BigDecimal idn_system); 

	public List<ComboFilterNS> selectModesSystem(BigDecimal idn_zone);

	public List<String> getModeZoneBaseInvId(ModeZoneBaseInvBean modeZoneBaseInv);

	public int insertModeZoneBaseInv(ModeZoneBaseInvBean modeZoneBaseInv);

	public int updateModeZoneBaseInv(ModeZoneBaseInvBean modeZoneBaseInv);

}
