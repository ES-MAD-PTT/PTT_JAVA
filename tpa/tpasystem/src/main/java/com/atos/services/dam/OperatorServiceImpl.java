package com.atos.services.dam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilter;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.OperatorBean;
import com.atos.filters.dam.OperatorFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.OperatorMapper;

@Service("operatorService")
public class OperatorServiceImpl implements OperatorService{
	
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 5738619896981240370L;
		@Autowired
		private OperatorMapper operatorMapper;
		@Autowired
		private SystemParameterMapper systemParameterMapper;

		public List<OperatorBean> selectOperators(OperatorFilter filter){
			return operatorMapper.selectOperators(filter);
		}

		public Map<String, Object> selectOperatorId(){
	 		Map<String, Object> map = new LinkedHashMap<String, Object>();
	 		List<ComboFilter> list = operatorMapper.selectOperatorId();
			for (ComboFilter combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 		

		}
		public List<String> selectName(String query){
			return operatorMapper.selectName(query);
		}
		

		@Transactional( rollbackFor = { Throwable.class })
		public String insertOperator(OperatorBean operator) throws Exception {
			List<String> list = operatorMapper.getOperatorId(operator);
			if(list.size()>0){
				throw new Exception("-1");
			}
			int ins1 = operatorMapper.insertUserGroup(operator);
			if(ins1 != 1){
				throw new Exception("-2");
			}
			
			int ins2 = operatorMapper.insertOperator(operator);
			if(ins2 != 1){
				throw new Exception("-3");
			}
			
			return "0";
		}
		
		@Transactional( rollbackFor = { Throwable.class })
		public String updateOperator(OperatorBean operator) throws Exception {
					
			int upd1 = operatorMapper.updateUserGroup(operator);
			if(upd1!=1){
				throw new Exception("-1");
			}
			
			int upd2= operatorMapper.updateOperator(operator);
			if(upd2!=1){
				throw new Exception("-2");
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
}
