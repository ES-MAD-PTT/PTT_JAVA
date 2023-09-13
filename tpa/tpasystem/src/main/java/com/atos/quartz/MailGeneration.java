package com.atos.quartz;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atos.beans.forecasting.ForecastingDatesBean;
import com.atos.mapper.SystemMapper;
import com.atos.mapper.mail.MailGenerationMapper;
import com.atos.services.MailService;
import com.atos.utils.DateUtil;

@Component
public class MailGeneration  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4152000942038567057L;
	private static final Logger log = LogManager.getLogger(MailGeneration.class);
	private static final String forecasting_submission = "FORECASTING.SUBMISSION.ALERT";
	private static final String term_short = "SHORT";
	private static final String term_medium = "MEDIUM";
	private static final String term_long = "LONG";
	
	@Autowired
	private MailService mailService;

	@Autowired
	private SystemMapper systemMapper;

	@Autowired
	private MailGenerationMapper mgMapper;
	
	public void generateShortMail() {
		
		log.info("Job generateShortMail is running", Calendar.getInstance().getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		Date today = DateUtil.getToday();

		map.put("startDate", today);
		map.put("endDate", today);
		
		BigDecimal idn_system = null;
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			idn_system = list_onshore.get(0);
		}
		
		ForecastingDatesBean bean = new ForecastingDatesBean();
		bean.setTerm_code(term_short);
		bean.setValid_date(today);
		bean.setUser("manager");
		bean.setLanguage("en");
		
		mgMapper.callForecastingDates(bean);
		
		HashMap<String,String> values = new HashMap<String,String>();
		values.put("1", term_short);
		values.put("2", sdf.format(bean.getStart_date()));
		values.put("3", sdf.format(bean.getEnd_date()));
		values.put("4", sdf.format(bean.getSubmission_deadline()));
		
		List<BigDecimal> list_shippers = mgMapper.getShippers(map);
		
		for(int i=0;i<list_shippers.size();i++) {
			mailService.sendEmail(forecasting_submission, values, idn_system, list_shippers.get(i));
		}
		
		log.info("Job generateShortMail finish", Calendar.getInstance().getTime());
	
	}

	public void generateMediumMail() {
		
		log.info("Job generateMediumMail is running", Calendar.getInstance().getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		Date today = DateUtil.getToday();

		map.put("startDate", today);
		map.put("endDate", today);
		
		BigDecimal idn_system = null;
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			idn_system = list_onshore.get(0);
		}
		
		ForecastingDatesBean bean = new ForecastingDatesBean();
		bean.setTerm_code(term_medium);
		bean.setValid_date(today);
		
		mgMapper.callForecastingDates(bean);
		
		HashMap<String,String> values = new HashMap<String,String>();
		values.put("1", term_medium);
		values.put("2", sdf.format(bean.getStart_date()));
		values.put("3", sdf.format(bean.getEnd_date()));
		values.put("4", sdf.format(bean.getSubmission_deadline()));
		
		List<BigDecimal> list_shippers = mgMapper.getShippers(map);
		
		for(int i=0;i<list_shippers.size();i++) {
			mailService.sendEmail(forecasting_submission, values, idn_system, list_shippers.get(i));
		}
		
		log.info("Job generateMediumMail finish", Calendar.getInstance().getTime());
	
	}

	public void generateLongMail() {
		
		log.info("Job generateLongMail is running", Calendar.getInstance().getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		Date today = DateUtil.getToday();

		map.put("startDate", today);
		map.put("endDate", today);
		
		BigDecimal idn_system = null;
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			idn_system = list_onshore.get(0);
		}
		
		ForecastingDatesBean bean = new ForecastingDatesBean();
		bean.setTerm_code(term_long);
		bean.setValid_date(today);
		
		mgMapper.callForecastingDates(bean);
		
		HashMap<String,String> values = new HashMap<String,String>();
		values.put("1", term_long);
		values.put("2", sdf.format(bean.getStart_date()));
		values.put("3", sdf.format(bean.getEnd_date()));
		values.put("4", sdf.format(bean.getSubmission_deadline()));
		
		List<BigDecimal> list_shippers = mgMapper.getShippers(map);
		
		for(int i=0;i<list_shippers.size();i++) {
			mailService.sendEmail(forecasting_submission, values, idn_system, list_shippers.get(i));
		}
		
		log.info("Job generateLongMail finish", Calendar.getInstance().getTime());
	
	}

}
