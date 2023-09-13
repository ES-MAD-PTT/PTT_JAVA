package com.atos.services.balancing;

import java.math.BigDecimal;
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
import com.atos.beans.balancing.InstructedFlowExPostBean;
import com.atos.filters.balancing.InstructedFlowExPostFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.allocation.AllocationManagementMapper;
import com.atos.mapper.balancing.InstructedFlowExPostMapper;


@Service("instructedFlowExPostService")
public class InstructedFlowExPostServiceImpl implements InstructedFlowExPostService{
	
	private static final long serialVersionUID = 5738619896981240370L;
		
		@Autowired
		private InstructedFlowExPostMapper instructedFlowExPostMapper;
	@Autowired
	private AllocationManagementMapper amMapper;
		
		@Autowired
		private SystemParameterMapper systemParameterMapper;

		
		public List<InstructedFlowExPostBean> selectInstructedFlowExPosts(InstructedFlowExPostFilter filter){
			return instructedFlowExPostMapper.selectInstructedFlowExPosts(filter);
			
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
		public Map<BigDecimal, Object> selectShippersFilter(BigDecimal idn_user_group) {
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = instructedFlowExPostMapper.selectShippersComboAlone(idn_user_group);
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}
		
		@Override
		public Map<BigDecimal, Object> selectShippersFilter() {
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = instructedFlowExPostMapper.selectShippersComboFilter();
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}
		
		public Map<BigDecimal, Object> selectShippersNew() {
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = instructedFlowExPostMapper.selectShippersComboNew();
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}

	public Date getFirstOpenDay(Map<String, Object> params) {
		Date FirstOpenDay = amMapper.selectOpenPeriodFirstDay(params);
			return FirstOpenDay;
		}
		


		@Override
		public Map<BigDecimal, Object> selectContracts(InstructedFlowExPostFilter filter) {
			
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = instructedFlowExPostMapper.selectContractsFilter(filter);
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}

		@Override
		public Map<BigDecimal, Object> selectContracts(InstructedFlowExPostBean bean) {
			
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = instructedFlowExPostMapper.selectContractsNew(bean);
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}
		
		@Override
		public Map<BigDecimal, Object> selectConcepts() {
			
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = instructedFlowExPostMapper.selectConcepts();
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}
		@Override
		public Map<BigDecimal, Object> selectZones(String systemCode) {
			
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = instructedFlowExPostMapper.selectZones(systemCode);
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}

		@Transactional( rollbackFor = { Throwable.class })
		public String insertInstructedFlowExPost(InstructedFlowExPostBean instructedFlowExPost) throws Exception {
		
			/*List<String> list = instructedFlowExPostMapper.getInstructedFlowExPostId(instructedFlowExPost);
			if(list.size()>0){
				// the id is already inserted
					throw new Exception("-1");
				}*/
			int ins =instructedFlowExPostMapper.insertInstructedFlowExPost(instructedFlowExPost);
			if(ins != 1){
				throw new Exception("-2");
			}
			
			return "0";
		}


		@Override
		public String getContractCode(BigDecimal idnContract) {
			String contractCode = instructedFlowExPostMapper.getContracCode(idnContract);
			return contractCode;
		}
		


	

		
}
