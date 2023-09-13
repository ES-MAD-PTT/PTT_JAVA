package com.atos.services.balancing;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.NotificationBean;
import com.atos.beans.ReserveBalancingGasSbsDto;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.UserBean;
import com.atos.beans.balancing.ReserveBalancingGasSbsBean;
import com.atos.filters.balancing.ReserveBalancigGasSbsFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.balancing.ReserveBalancingGasSbsMapper;

@Service("reserveBalancingGasSbsService")
public class ReserveBalancingGasSbsService {

	@Autowired
	private ReserveBalancingGasSbsMapper mapper;
	
	@Autowired
	private SystemParameterMapper sysParMapper;

	@Autowired
	private NotificationMapper notifMapper;
	
	public void sayHello(){
		System.out.println("hello");
	}


	public ReserveBalancingGasSbsMapper getMapper() {
		return mapper;
	}

	public void setMapper(ReserveBalancingGasSbsMapper mapper) {
		this.mapper = mapper;
	}


	public Map<BigDecimal, Object> getCapContractsIds(ReserveBalancigGasSbsFilter filter) {

		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.getCapContractsIds(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}


	public Map<BigDecimal, Object> getZones(BigDecimal contractId, BigDecimal idnSystem) {

		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		HashMap<String, BigDecimal> params = new HashMap<>();
		params.put("idnResbalContract", contractId);
		params.put("idnSystem", idnSystem);
		List<ComboFilterNS> list = mapper.getZones(params);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		

	}


	public Map<BigDecimal, Object> getZonesForSearch(BigDecimal idnSystem) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.getZonesForSearch(idnSystem);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}


	public Integer getSubmitDate(String userId, String language) throws Exception {

		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(new Date());	// Now
		bean.setParameter_name("BALANCING.RESERVE.DAY_MONTH");
		bean.setUser_id(userId);
		bean.setLanguage(language);
		sysParMapper.getIntegerSystemParameter(bean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(bean == null || bean.getInteger_exit()==null)
			throw new Exception("Error getting BALANCING.RESERVE.DAY_MONTH");
		
		return bean.getInteger_exit();
	}


	public SystemParameterMapper getSysParMapper() {
		return sysParMapper;
	}


	public void setSysParMapper(SystemParameterMapper sysParMapper) {
		this.sysParMapper = sysParMapper;
	}


	public List<ReserveBalancingGasSbsDto> search(ReserveBalancigGasSbsFilter filter) {
		return mapper.search(filter);
	}


	public Map<BigDecimal, Object> getPoints(ReserveBalancingGasSbsBean newOperation) {

		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.getPoints(newOperation);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}

	public List<Date> getInValidDates(ReserveBalancingGasSbsBean newOperation) {
		return mapper.getInvalidDates(newOperation);
	}

	@Transactional(rollbackFor = { Throwable.class })
	public List<Date> save(ReserveBalancingGasSbsBean newOperation2) throws Exception {
		List<Date> result = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(newOperation2.getStartDate());
		while (!cal.getTime().after(newOperation2.getEndDate())) {
			newOperation2.setInsertDate(cal.getTime());
			mapper.insertOperation(newOperation2);
			result.add(cal.getTime());
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
		return result;
	}

	@Transactional(rollbackFor = { Throwable.class })
	public void singleSave(ReserveBalancingGasSbsBean operation) throws Exception {

		mapper.insertOperation(operation);
	}

	public void sendNotification(ReserveBalancingGasSbsDto dto, UserBean user, String locale,
			String notificationTypeCode, String system, BigDecimal systemId) {
		NotificationBean notif = new NotificationBean();
		StringBuilder params = new StringBuilder();
		String strNotifSeparator = "~";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		params.append(user.getUser_group_id()).append(strNotifSeparator).append(sdf.format(dto.getGasDay()))
				.append(strNotifSeparator).append(dto.getContractCode()).append(strNotifSeparator);
		params.append(dto.getCapContractCode()).append(strNotifSeparator).append(dto.getPointCode());
		params.append(strNotifSeparator).append(system);
		notif.setOrigin("BALANCING");
		notif.setSystemId(systemId);
		notif.setType_code(notificationTypeCode);
		notif.setInformation(params.toString());
		notif.setIdn_user_group(dto.getIdnUserGroup());
		notif.setUser_id(user.getUsername());
		notif.setLanguage(locale);
		notifMapper.getCreateNotification(notif);
	}

	public NotificationMapper getNotifMapper() {
		return notifMapper;
	}

	public void setNotifMapper(NotificationMapper notifMapper) {
		this.notifMapper = notifMapper;
	}
		



}
