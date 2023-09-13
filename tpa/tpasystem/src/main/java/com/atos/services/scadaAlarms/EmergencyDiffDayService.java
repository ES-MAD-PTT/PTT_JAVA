package com.atos.services.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.primefaces.model.StreamedContent;

import com.atos.beans.FileBean;
import com.atos.beans.scadaAlarms.EmergencyDiffDayBean;
import com.atos.beans.scadaAlarms.EmergencyDiffDayDetailsBean;
import com.atos.beans.scadaAlarms.EmergencyDiffDayIsShipperBean;
import com.atos.beans.scadaAlarms.IdEventBean;
import com.atos.filters.scadaAlarms.EmergencyDifficultDayFilter;

public interface EmergencyDiffDayService extends Serializable {
	public Map<BigDecimal, Object> selectEventType();
	public Map<BigDecimal, Object> selectZone(BigDecimal idn_system);
	public List<EmergencyDiffDayBean> selectEmergencyDiffDay(EmergencyDifficultDayFilter filter);
	public List<EmergencyDiffDayDetailsBean> selectEmergencyDiffDayDetails(EmergencyDiffDayIsShipperBean b);
	public List<EmergencyDiffDayDetailsBean> selectEmergencyDiffDayAllDetails(EmergencyDiffDayIsShipperBean b);
	public List<String> selectShippers();
	public EmergencyDiffDayBean selectEmergencyDiffDayParent(BigDecimal idn_tso_event);
	public IdEventBean getId();
	public String insertEvent(EmergencyDiffDayBean emergencyDiffDayBean, FileBean file) throws Exception;
	public String insertEventShipper(EmergencyDiffDayDetailsBean emergencyDiffDayDetailsBean) throws Exception;
	public BigDecimal selectIdnUserGroup(String shipper);
	public void sendNotification(String notifTypeCode, String origin, String info, BigDecimal userGroupId, BigDecimal systemId) throws Exception;	
	public String updateEvent(EmergencyDiffDayBean emergencyDiffDayBean) throws Exception;
	public String updateEventShipper(EmergencyDiffDayDetailsBean emergencyDiffDayDetailsBean) throws Exception;
	public String updateStatusEvent(EmergencyDiffDayBean emergencyDiffDayBean) throws Exception;
	public String selectEventTypeFromId(String idn_tso_event_tpe);
	public StreamedContent getOpeningOperatorFile(EmergencyDiffDayBean emergencyDiffDayBean) throws Exception;
	public StreamedContent getClosingOperatorFile(EmergencyDiffDayBean emergencyDiffDayBean) throws Exception;
	public StreamedContent getOpeningShipperFile(EmergencyDiffDayDetailsBean emergencyDiffDayDetailsBean) throws Exception;
	public StreamedContent getClosingShipperFile(EmergencyDiffDayDetailsBean emergencyDiffDayDetailsBean) throws Exception;
	public String getZoneDesc(BigDecimal zone);
}
