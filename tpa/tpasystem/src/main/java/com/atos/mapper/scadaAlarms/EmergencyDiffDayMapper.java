package com.atos.mapper.scadaAlarms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.scadaAlarms.EmergencyDiffDayBean;
import com.atos.beans.scadaAlarms.EmergencyDiffDayDetailsBean;
import com.atos.beans.scadaAlarms.EmergencyDiffDayIsShipperBean;
import com.atos.beans.scadaAlarms.IdEventBean;
import com.atos.filters.scadaAlarms.EmergencyDifficultDayFilter;

public interface EmergencyDiffDayMapper {
	
	public List<ComboFilterNS> selectEventTypeCombo();
	public List<ComboFilterNS> selectZoneCombo(BigDecimal idn_system);
	public List<EmergencyDiffDayBean> selectEmergencyDiffDay(EmergencyDifficultDayFilter filter);
	public List<EmergencyDiffDayDetailsBean> selectEmergencyDiffDayDetails(EmergencyDiffDayIsShipperBean filter);
	public List<EmergencyDiffDayDetailsBean> selectEmergencyDiffDayAllDetails(EmergencyDiffDayIsShipperBean filter);
	public List<String> selectShippers();
	public EmergencyDiffDayBean selectEmergencyDiffDayParent(BigDecimal idn_tso_event);
	public Date getSysdate();
	public IdEventBean getIdEvent(IdEventBean bean);
	public int insertEvent(EmergencyDiffDayBean emergencyDiffDayBean);
	public int insertEventShipper(EmergencyDiffDayDetailsBean emergencyDiffDayDetailsBean);
	public BigDecimal selectIdnTsoEvent();
	public BigDecimal selectIdnUserGroup(String shipper);
	public int updateEvent(EmergencyDiffDayBean emergencyDiffDayBean);
	public int updateEventShipper(EmergencyDiffDayDetailsBean emergencyDiffDayDetailsBean);
	public int updateStatusEvent(EmergencyDiffDayBean emergencyDiffDayBean);
	public String selectEventTypeFromId(String idn_tso_event_tpe);
	public EmergencyDiffDayBean selectOpenOperatorFile(EmergencyDiffDayBean bean);
	public EmergencyDiffDayBean selectCloseOperatorFile(EmergencyDiffDayBean bean);
	public EmergencyDiffDayDetailsBean selectOpenShipperFile(EmergencyDiffDayDetailsBean bean);
	public EmergencyDiffDayDetailsBean selectCloseShipperFile(EmergencyDiffDayDetailsBean bean);
	public String getZoneDesc(BigDecimal zone);
}
