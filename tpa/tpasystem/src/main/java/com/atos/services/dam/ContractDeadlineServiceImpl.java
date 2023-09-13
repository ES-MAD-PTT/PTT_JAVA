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
import com.atos.beans.dam.ContractDeadlineBean;
import com.atos.filters.dam.ContractDeadlineFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.ContractDeadlineMapper;

@Service("contractDeadlineService")
public class ContractDeadlineServiceImpl implements ContractDeadlineService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5738619896981240370L;
	@Autowired
	private ContractDeadlineMapper contractDeadlineMapper;
	@Autowired
	private SystemParameterMapper systemParameterMapper;

	public List<ContractDeadlineBean> selectContractDeadlines(ContractDeadlineFilter filter) {
		
		List<ContractDeadlineBean> list = contractDeadlineMapper.selectContractDeadlines(filter);
		
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
		List<ComboFilterNS> list = contractDeadlineMapper.selectTypes();
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
		List<ComboFilterNS> list = contractDeadlineMapper.selectDeadlineType();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	public String validateContractOrders(ContractDeadlineBean contractDeadline) throws Exception {
		/*
		 * cada calendario tiene que acabar antes que el siguiente (segun el
		 * orden de execution_order)
		 * 
		 * primero standard.reception (submission) => 18:00 luego
		 * standard.management (management) => 20:00 luego
		 * recontract.reception (reception of recontract) =>22:00 ultimo
		 * recontract.management (validity response of recontract) => 23:59
		 * 
		 * el calendario acaba los dias antes que se pongan a la hora que se
		 * indique los de nominacion diara acaban todos el dia antes de la
		 * operacion (dias = 1)
		 */

		List<BigDecimal> list = contractDeadlineMapper.getIdnOperationContract(contractDeadline);

		if (list.size() > 0) {
			contractDeadline.setIdn_operation(list.get(0));
		} else {
			// id not find
			throw new Exception("-1");
		}

		contractDeadline.setShourLimit("23:59");
		contractDeadline.setDaysBefore(BigDecimal.ZERO);

		
		ContractDeadlineBean validBean = new ContractDeadlineBean();
		validBean.setIdn_operation(contractDeadline.getIdn_operation());
		validBean.setIdn_deadline_type(contractDeadline.getIdn_deadline_type());
		validBean.setIdn_deadline_limit(contractDeadline.getIdn_deadline_limit());
		validBean.setStartDate(contractDeadline.getStartDate());
		
		validBean.setShourLimit(contractDeadline.getShourLimit());
		validBean.setDayLimit(contractDeadline.getDayLimit());
		validBean.setMonthLimit(contractDeadline.getMonthLimit());
		validBean.setMonthsBefore(contractDeadline.getMonthsBefore());
		
		validBean.setUser((String) SecurityUtils.getSubject().getPrincipal());
		validBean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());

		contractDeadlineMapper.getValidateContractOrders(validBean);

		if (validBean.getValid() != 0) {
			throw new Exception(validBean.getError_desc());
		}
		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertContractDeadline(ContractDeadlineBean contractDeadline) throws Exception {

		List<BigDecimal> list = contractDeadlineMapper.getIdnOperationContract(contractDeadline);

		if (list.size() > 0) {
			contractDeadline.setIdn_operation(list.get(0));
		} else {
			// id not find
			throw new Exception("-1");
		}
		int ins1 = contractDeadlineMapper.insertContractDeadline(contractDeadline);
		if (ins1 != 1) {
			throw new Exception("-2");
		}
		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String updateContractDeadline(ContractDeadlineBean contractDeadline) throws Exception {

		int upd1 = contractDeadlineMapper.insertContractDeadline(contractDeadline);
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
	public String deleteContractDeadline(ContractDeadlineBean contractDeadline) throws Exception {
		// We calculate the end date, which will be the startDate -1 day
		Date startDateBd = contractDeadlineMapper.getContractDeadlineStarDate(contractDeadline);

		Date endDate = restarDiasFecha(startDateBd, 1);
		contractDeadline.setEndDate(endDate);

		int ins = contractDeadlineMapper.deleteContractDeadline(contractDeadline);
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
