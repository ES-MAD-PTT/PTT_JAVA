package com.atos.services.dam;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.atos.beans.dam.AreaQualityPerShipperBean;
import com.atos.beans.dam.AreaQualityPerShipperDetailsBean;
import com.atos.filters.dam.AreaQualityPerShipperFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.AreaQualityPerShipperMapper;
import com.atos.utils.Constants;


@Service("areaQualityPerShipperService")
public class AreaQualityPerShipperServiceImpl implements AreaQualityPerShipperService{

	
	private static final long serialVersionUID = 5738619896981240370L;
		
		@Autowired
		private AreaQualityPerShipperMapper areaQualityPerShipperMapper;
		
		@Autowired
		private SystemParameterMapper systemParameterMapper;
	
		
		public Map<BigDecimal, Object> selectAreasIds(BigDecimal idn_system){
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
			List<ComboFilterNS> list = areaQualityPerShipperMapper.selectAreasIds(idn_system);
			for (ComboFilterNS combo : list) {
				if (combo == null)
					continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map;
		}
		
		public Map<BigDecimal, Object> selectShippersIds(){
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
			List<ComboFilterNS> list = areaQualityPerShipperMapper.selectShippersIds();
			for (ComboFilterNS combo : list) {
				if (combo == null)
					continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map;
		}
		
		public List<AreaQualityPerShipperBean> selectAreasShipper(AreaQualityPerShipperFilter filter) {
			return areaQualityPerShipperMapper.selectAreasShipper(filter);
		}
		
		public List<AreaQualityPerShipperDetailsBean> getMaxMin() {
			return areaQualityPerShipperMapper.getMaxMin();
		}

		public String getName(BigDecimal idn_zone){
			return "";
			//return areaQuantityPerShipperMapper.getName(idn_zone);
		}
		public BigDecimal getPipelineSystem(BigDecimal idn_zone){
			return new BigDecimal(2);
			//return areaQuantityPerShipperMapper.getPipelineSystem(idn_zone);
		}
		
		public String getAreaDesc(BigDecimal idn_area) {
			return areaQualityPerShipperMapper.areaDesc(idn_area);
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


		
		@Transactional( rollbackFor = { Throwable.class })
		public String insert(AreaQualityPerShipperBean bean) throws Exception {
		
		 	int ins2= areaQualityPerShipperMapper.insert(bean);
			if(ins2!=1){
				throw new Exception("-2");
			}
			
			BigDecimal maxValue=bean.getWi_max();
			BigDecimal minValue=bean.getWi_min();
			
			if (existsValues(maxValue, minValue)){
				bean.setParameterCode1(Constants.WI);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) < 0){	
						throw new Exception("-16");
					}
				}	
				bean.setWi_max(maxValue);
				bean.setWi_min(minValue);
				int ins3 =areaQualityPerShipperMapper.insertParamsWi(bean);
				if(ins3!=1){
					throw new Exception("-3");		
					
				}
			}
			
			maxValue=bean.getHv_max();
			minValue=bean.getHv_min();
			if (existsValues(maxValue, minValue)){			
				bean.setParameterCode2(Constants.HV);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) < 0){	
						throw new Exception("-16");
					}
				}	
				bean.setHv_max(maxValue);
				bean.setHv_min(minValue);
				int ins4 =areaQualityPerShipperMapper.insertParamsHv(bean);
				if(ins4!=1){
					throw new Exception("-10");		
					
				}
			}
			
			return "0";
		}
		
	
		@Transactional( rollbackFor = { Throwable.class })
		public String update(AreaQualityPerShipperBean bean) throws Exception {
			int res = areaQualityPerShipperMapper.updateStartDateGasQuality(bean);
			if(res<=0)
				throw new Exception("-1");
			
			return "0";
		}


		public boolean existsValues(BigDecimal max, BigDecimal min){
		//Solo si los dos vienen vacios no insertamos ninguno
			
			if (max==null && min==null){
				return false;
			}else return true;
		}
		
       public Date restarDiasFecha(Date fecha, int dias){
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha); // 
			calendar.add(Calendar.DATE, -dias);  // numero de días a añadir, o restar en caso de días<0
			return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
		 }
}
