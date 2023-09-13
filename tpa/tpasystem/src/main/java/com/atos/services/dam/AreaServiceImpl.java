package com.atos.services.dam;

import java.math.BigDecimal;
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
import com.atos.beans.dam.AreaBean;
import com.atos.filters.dam.AreaFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.AreaMapper;


@Service("areaService")
public class AreaServiceImpl implements AreaService{
	
	private static final long serialVersionUID = 5738619896981240370L;
		
		@Autowired
		private AreaMapper areaMapper;
		
		@Autowired
		private SystemParameterMapper systemParameterMapper;

		
		public List<AreaBean> selectAreas(AreaFilter filter){
			return areaMapper.selectAreas(filter);
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
		
		//offshore
		@Override
		public Map<BigDecimal, Object> selectIds(BigDecimal idn_system) {
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = areaMapper.selectIdsCombo(idn_system);
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}

		@Override
		public List<String> selectNames(String query) {
			return areaMapper.selectNames(query);
			
		}

		//offshore
		@Override
		public Map<BigDecimal, Object> selectSystems(BigDecimal idn_system) {
			
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = areaMapper.selectSystems(idn_system);
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}

		//offshore
		@Override
		public Map<BigDecimal, Object> selectZonesSystem(BigDecimal idn_system) {
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = areaMapper.selectZonesSystem(idn_system);
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 	
		}
		
		@Transactional( rollbackFor = { Throwable.class })
		public String insertArea(AreaBean area) throws Exception {
		
			List<String> list = areaMapper.getAreaId(area);
			if(list.size()>0){
				// the id is already inserted
					throw new Exception("-1");
				}
			int ins =areaMapper.insertArea(area);
			if(ins != 1){
				throw new Exception("-2");
			}
			
			return "0";
		}
		
		@Transactional( rollbackFor = { Throwable.class })
		public String updateArea(AreaBean area) throws Exception {
			
			int upd = areaMapper.updateArea(area);
			if(upd != 1){
				throw new Exception("-1");
			}
			

			return "0";
		}


}
