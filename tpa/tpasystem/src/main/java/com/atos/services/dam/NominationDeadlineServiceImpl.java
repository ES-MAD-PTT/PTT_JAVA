package com.atos.services.dam;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.atos.beans.dam.NominationDeadlineBean;
import com.atos.filters.dam.NominationDeadlineFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.NominationDeadlineMapper;

@Service("nominationDeadlineService")
public class NominationDeadlineServiceImpl implements NominationDeadlineService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5738619896981240370L;
	@Autowired
	private NominationDeadlineMapper nominationDeadlineMapper;
	@Autowired
	private SystemParameterMapper systemParameterMapper;

	public List<NominationDeadlineBean> selectNominationDeadlines(NominationDeadlineFilter filter) {
		// return nominationDeadlineMapper.selectNominationDeadlines(filter);
		List<NominationDeadlineBean> list = nominationDeadlineMapper.selectNominationDeadlines(filter);
		for (int i = 0; i < list.size(); i++) {
			String sHour = list.get(i).getShour();

			Date hour = ParseFecha(sHour);
			list.get(i).setHour(hour);
		}

		return list;
	}

	public static Date ParseFecha(String fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
		Date fechaDate = null;
		try {
			fechaDate = formato.parse(fecha);
		} catch (ParseException ex) {
			System.out.println(ex);
		}
		return fechaDate;
	}

	@Override
	public Map<BigDecimal, Object> selectTypes() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = nominationDeadlineMapper.selectTypes();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectDeadlineType() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = nominationDeadlineMapper.selectDeadlineType();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	public String validateNominationOrders(NominationDeadlineBean nominationDeadline) throws Exception {
		/*
		 * cada calendario tiene que acabar antes que el siguiente (segun el
		 * orden de execution_order)
		 * 
		 * primero standard.reception (submission) => 18:00 luego
		 * standard.management (management) => 20:00 luego
		 * renomination.reception (reception of renomination) =>22:00 ultimo
		 * renomination.management (validity response of renomination) => 23:59
		 * 
		 * el calendario acaba los dias antes que se pongan a la hora que se
		 * indique los de nominacion diara acaban todos el dia antes de la
		 * operacion (dias = 1)
		 */

		List<BigDecimal> list = nominationDeadlineMapper.getIdnOperationNomination(nominationDeadline);

		if (list.size() > 0) {
			nominationDeadline.setIdn_operation(list.get(0));
		} else {
			// id not find
			throw new Exception("-1");
		}

		// las horas en diseño son tipo date... pero en bd son estring hay que
		// tener dos campos en el bean (shour(string) y hour(date))
		String sHour = nominationDeadline.getHour().toString().substring(11, 16);
		nominationDeadline.setShour(sHour);

		// function that checks whether to allow sending / RV calendar
		// re-nominations . adm is allowed. renom lime
		NominationDeadlineBean validBean = new NominationDeadlineBean();
		validBean.setIdn_operation(nominationDeadline.getIdn_operation());
		validBean.setIdn_deadline_type(nominationDeadline.getIdn_deadline_type());
		validBean.setIdn_deadline_limit(nominationDeadline.getIdn_deadline_limit());
		validBean.setStartDate(nominationDeadline.getStartDate());
		validBean.setShour(nominationDeadline.getShour());
		validBean.setGasDay(nominationDeadline.getGasDay());
		validBean.setUser((String) SecurityUtils.getSubject().getPrincipal());
		validBean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());

		nominationDeadlineMapper.getValidateNominationOrders(validBean);

		if (validBean.getValid() != 0) {
			throw new Exception(validBean.getError_desc());
		}

		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertNominationDeadline(NominationDeadlineBean nominationDeadline) throws Exception {

		List<BigDecimal> list = nominationDeadlineMapper.getIdnOperationNomination(nominationDeadline);

		if (list.size() > 0) {
			nominationDeadline.setIdn_operation(list.get(0));
		} else {
			// id not find
			throw new Exception("-1");
		}

		// the hours are displayed date ... but bd are string: you have to have
		// two fields in bean (shour(string) y hour(date))
		String sHour = nominationDeadline.getHour().toString().substring(11, 16);
		nominationDeadline.setShour(sHour);

		int ins1 = nominationDeadlineMapper.insertNominationDeadline(nominationDeadline);
		if (ins1 != 1) {
			throw new Exception("-2");
		}
		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String updateNominationDeadline(NominationDeadlineBean nominationDeadline) throws Exception {

		// the hours are displayed date ... but bd are string: you have to have
		// two fields in bean (shour(string) y hour(date))
		String sHour = nominationDeadline.getHour().toString().substring(11, 16);
		nominationDeadline.setShour(sHour);

		int upd1 = nominationDeadlineMapper.insertNominationDeadline(nominationDeadline);
		if (upd1 != 1) {
			throw new Exception("-1");
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

	@Override
	public String deleteNominationDeadline(NominationDeadlineBean nominationDeadline) throws Exception {
		// We calculate the end date, which will be the startDate -1 day
		Date startDateBd = nominationDeadlineMapper.getNominationDeadlineStarDate(nominationDeadline);

		Date endDate = restarDiasFecha(startDateBd, 1);
		nominationDeadline.setEndDate(endDate);

		int ins = nominationDeadlineMapper.deleteNominationDeadline(nominationDeadline);
		if (ins != 1) {
			throw new Exception("-10");
		}

		return "0";
	}

	public Date restarDiasFecha(Date fecha, int dias) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); //
		calendar.add(Calendar.DATE, -dias); // numero de días a añadir, o restar
											// en caso de días<0
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
	}

}
