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
import com.atos.beans.dam.ModeZoneBaseInvBean;
import com.atos.filters.dam.ModeZoneBaseInvFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.ModeZoneBaseInvMapper;


@Service("modeZoneBaseInvService")
public class ModeZoneBaseInvServiceImpl implements ModeZoneBaseInvService{
	
	private static final long serialVersionUID = 5738619896981240370L;
		
		
		@Autowired
		private SystemParameterMapper systemParameterMapper;
		
		@Autowired
		private ModeZoneBaseInvMapper modeZoneBaseInvMapper;

		
		public List<ModeZoneBaseInvBean> selectModeZoneBaseInv(ModeZoneBaseInvFilter filter){
			return modeZoneBaseInvMapper.selectModeZoneBaseInv(filter);
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
		public Map<BigDecimal, Object> selectZonesSystem(BigDecimal idn_system) {
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = modeZoneBaseInvMapper.selectZonesSystem(idn_system);
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 	
		}
		
		//offshore
		@Override
		public Map<BigDecimal, Object> selectModesSystem(BigDecimal idn_zone) {
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = modeZoneBaseInvMapper.selectModesSystem(idn_zone);
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 	
		}
		
		@Transactional( rollbackFor = { Throwable.class })
		public String insertModeZoneBaseInv(ModeZoneBaseInvBean modeZoneBaseInv) throws Exception {
		
			int ins = modeZoneBaseInvMapper.insertModeZoneBaseInv(modeZoneBaseInv);
			if(ins != 1){
				throw new Exception("-2");
			}
			
			return "0";
		}
		
		@Transactional( rollbackFor = { Throwable.class })
		public String updateModeZoneBaseInv(ModeZoneBaseInvBean modeZoneBaseInv) throws Exception {
			
			int upd = modeZoneBaseInvMapper.updateModeZoneBaseInv(modeZoneBaseInv);
			if(upd != 1){
				throw new Exception("-1");
			}
			
			return "0";
		}
}

