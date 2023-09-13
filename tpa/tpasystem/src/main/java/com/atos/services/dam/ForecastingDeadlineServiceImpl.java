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

import com.atos.beans.ComboFilter;
import com.atos.beans.ComboFilterNS;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ForecastingDeadlineBean;
import com.atos.filters.dam.ForecastingDeadlineFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.ForecastingDeadlineMapper;

@Service("forecastingDeadlineService")
public class ForecastingDeadlineServiceImpl implements ForecastingDeadlineService{
	
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 5738619896981240370L;
		@Autowired
		private ForecastingDeadlineMapper forecastingDeadlineMapper;
		@Autowired
		private SystemParameterMapper systemParameterMapper;

		
		
		public List<ForecastingDeadlineBean> selectForecastingDeadlines(ForecastingDeadlineFilter filter){
			
			 List<ForecastingDeadlineBean> list = forecastingDeadlineMapper.selectForecastingDeadlines(filter);
			 for (int i=0;i<list.size();i++){
				 String sHour =list.get(i).getShour();
				 
				 Date hour=ParseFecha(sHour);
				 list.get(i).setHour(hour);
			 }

			return list;
			
		}

		
		public static Date ParseFecha(String fecha)
	    {
	        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
	        Date fechaDate = null;
	        try {
	            fechaDate = formato.parse(fecha);
	        } 
	        catch (ParseException ex) 
	        {
	            System.out.println(ex);
	        }
	        return fechaDate;
	    }
		@Override
		public Map<BigDecimal, Object> selectTypes() {
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = forecastingDeadlineMapper.selectTypes();
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}

		@Transactional( rollbackFor = { Throwable.class })
		public String insertForecastingDeadline(ForecastingDeadlineBean forecastingDeadline)  throws Exception {
			
			List<BigDecimal> list = forecastingDeadlineMapper.getIdnOperationForecasting(forecastingDeadline);
			
			if(list.size()>0){
				forecastingDeadline.setIdn_operation( list.get(0));		
			}else{
				//id not find
				throw new Exception("-1");
			}
			
			//las horas en diseño son tipo date... pero en bd son estring hay que tener dos campos en el bean (shour(string) y hour(date))
			String sHour=forecastingDeadline.getHour().toString().substring(11,16);
			forecastingDeadline.setShour(sHour);
			
			int ins1 = forecastingDeadlineMapper.insertForecastingDeadline(forecastingDeadline);
			if(ins1 != 1){
				throw new Exception("-2");
			}
				return "0";
		}
		
		@Transactional( rollbackFor = { Throwable.class })
		public String updateForecastingDeadline(ForecastingDeadlineBean forecastingDeadline) throws Exception {
			
			//las horas en diseño son tipo date... pero en bd son estring hay que tener dos campos en el bean (shour(string) y hour(date))
			String sHour=forecastingDeadline.getHour().toString().substring(11,16);
			forecastingDeadline.setShour(sHour);
			
			int ins1 = forecastingDeadlineMapper.insertForecastingDeadline(forecastingDeadline);
			if(ins1!=1){
				throw new Exception("-1");
			}
				return "0";
		}
		
		public SystemParameterBean getSystemParameter(String str){
			SystemParameterBean bean = new SystemParameterBean();
			bean.setDate(systemParameterMapper.getSysdate().get(0));
			bean.setParameter_name(str);
			bean.setUser_id((String)SecurityUtils.getSubject().getPrincipal());
			bean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
			systemParameterMapper.getIntegerSystemParameter(bean);
			return bean;
		}


		@Override
		public String deleteForecastingDeadline(ForecastingDeadlineBean forecastingDeadline) throws Exception {
			//We calculate the end date, which will be the startDate -1 day
			Date startDateBd = forecastingDeadlineMapper.getForecastingDeadlineStarDate(forecastingDeadline);
			
			//Date endDate = restarDiasFecha(shrinkageFactor.getStartDate(),1);
			Date endDate = restarDiasFecha(startDateBd,1);
			forecastingDeadline.setEndDate(endDate);		
			
			int ins = forecastingDeadlineMapper.deleteForecastingDeadline(forecastingDeadline);
			if(ins != 1){
				throw new Exception("-10");
			}
			
			return "0";
		}
		
		public Date restarDiasFecha(Date fecha, int dias){
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha); // 
			calendar.add(Calendar.DATE, -dias);  // numero de días a añadir, o restar en caso de días<0
			return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
		 }

		
}
