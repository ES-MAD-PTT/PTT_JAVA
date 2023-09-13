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
import com.atos.beans.dam.ContractDeadlineSpecialBean;
import com.atos.filters.dam.ContractDeadlineSpecialFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.ContractDeadlineSpecialMapper;

@Service("contractDeadlineSpecialService")
public class ContractDeadlineSpecialServiceImpl implements ContractDeadlineSpecialService{
	
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 5738619896981240370L;
		@Autowired
		private ContractDeadlineSpecialMapper contractDeadlineSpecialMapper;
		@Autowired
		private SystemParameterMapper systemParameterMapper;

		
		
		public List<ContractDeadlineSpecialBean> selectContractDeadlineSpecials(ContractDeadlineSpecialFilter filter){
			
			 List<ContractDeadlineSpecialBean> list = contractDeadlineSpecialMapper.selectContractDeadlineSpecials(filter);
			/* for (int i=0;i<list.size();i++){
				 String sHour =list.get(i).getShour();
				 
				 Date hour=ParseFecha(sHour);
				 list.get(i).setHour(hour);
			 }
*/
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
	 		List<ComboFilterNS> list = contractDeadlineSpecialMapper.selectTypes();
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}

		@Transactional( rollbackFor = { Throwable.class })
		public String insertContractDeadlineSpecial(ContractDeadlineSpecialBean contractDeadlineSpecial)  throws Exception {
			
	
			List<BigDecimal> list = contractDeadlineSpecialMapper.getIdnOperationContractDeadLine(contractDeadlineSpecial);
			
			if(list.size()>0){
				contractDeadlineSpecial.setIdn_operation( list.get(0));		
			}else{
				//id not find
				throw new Exception("-1");
			}
			
			//las horas en diseño son tipo date... pero en bd son estring hay que tener dos campos en el bean (shour(string) y hour(date))
			//String sHour=contractDeadlineSpecial.getHour().toString().substring(11,16);
			//contractDeadlineSpecial.setShour(sHour);
			
			int ins1 = contractDeadlineSpecialMapper.insertContractDeadlineSpecial(contractDeadlineSpecial);
			if(ins1 != 1){
				throw new Exception("-2");
			}
				return "0";
		}
		
		@Transactional( rollbackFor = { Throwable.class })
		public String updateContractDeadlineSpecial(ContractDeadlineSpecialBean contractDeadlineSpecial) throws Exception {
			
			//las horas en diseño son tipo date... pero en bd son estring hay que tener dos campos en el bean (shour(string) y hour(date))
			//String sHour=contractDeadlineSpecial.getHour().toString().substring(11,16);
			//contractDeadlineSpecial.setShour(sHour);
			
			int ins1 = contractDeadlineSpecialMapper.insertContractDeadlineSpecial(contractDeadlineSpecial);
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
		public String deleteContractDeadlineSpecial(ContractDeadlineSpecialBean contractDeadlineSpecial) throws Exception {
			//We calculate the end date, which will be the startDate -1 day
			Date startDateBd = contractDeadlineSpecialMapper.getContractDeadlineSpecialStarDate(contractDeadlineSpecial);
			
			//Date endDate = restarDiasFecha(shrinkageFactor.getStartDate(),1);
			Date endDate = restarDiasFecha(startDateBd,1);
			contractDeadlineSpecial.setEndDate(endDate);		
			
			int ins = contractDeadlineSpecialMapper.deleteContractDeadlineSpecial(contractDeadlineSpecial);
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
