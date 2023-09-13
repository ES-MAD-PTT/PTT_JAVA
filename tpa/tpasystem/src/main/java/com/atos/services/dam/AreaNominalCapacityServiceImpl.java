package com.atos.services.dam;

import java.math.BigDecimal;
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
import com.atos.beans.dam.AreaNominalCapacityBean;
import com.atos.filters.dam.AreaNominalCapacityFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.AreaNominalCapacityMapper;


@Service("areaNominalCapacityService")
public class AreaNominalCapacityServiceImpl implements AreaNominalCapacityService{

		private static final long serialVersionUID = 5738619896981240370L;
		
		@Autowired
		private AreaNominalCapacityMapper areaNominalCapacityMapper;
		
		@Autowired
		private SystemParameterMapper systemParameterMapper;

		
		public List<AreaNominalCapacityBean> selectAreaNominalCapacitys(AreaNominalCapacityFilter filter){
			return areaNominalCapacityMapper.selectAreaNominalCapacitys(filter);
			
		}

		//offShore
		public Map<BigDecimal, Object> selectAreaNominalCapacityAreaSystem(BigDecimal idn_system){
	 		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = areaNominalCapacityMapper.selectAreaNominalCapacityAreaSystem(idn_system);
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 		

		}

		@Transactional( rollbackFor = { Throwable.class })
		public String insertAreaNominalCapacity(AreaNominalCapacityBean areaNominalCapacity) throws Exception {
			
			int ins =areaNominalCapacityMapper.insertAreaNominalCapacity(areaNominalCapacity);
			if(ins != 1){
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
		public Map<BigDecimal, Object> selectAreaNominalCapacityYear(int year) {
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
			
			int initYear= year+1;
			
			int endYear= year+21;			
	            while(initYear < endYear){            	
	            	map.put(new BigDecimal(initYear), initYear);            
	                initYear++;
	            }
	            
			return map; 
		}
		
		
		@Transactional( rollbackFor = { Throwable.class })
		public String deleteAreaNominalCapacity(AreaNominalCapacityBean areaNominalCapacity) throws Exception {
			
			Date startDateBd = areaNominalCapacityMapper.getAreaNominalStartDate(areaNominalCapacity);
			//Date endDate = restarDiasFecha(areaNominalCapacity.getStartDate(),1);
			Date endDate = restarDiasFecha(startDateBd,1);
			
			areaNominalCapacity.setEndDate(endDate);		
			
			int ins = areaNominalCapacityMapper.deleteAreaNominalCapacity(areaNominalCapacity);
			if(ins != 1){
				throw new Exception("-10");
			}
			
			return "0";
		}
		
		public Date restarDiasFecha(Date fecha, int dias){
		
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha); // Configuramos la fecha que se recibe
			calendar.add(Calendar.DATE, -dias);  // numero de días a añadir, o restar en caso de días<0
			return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
		 }
}
