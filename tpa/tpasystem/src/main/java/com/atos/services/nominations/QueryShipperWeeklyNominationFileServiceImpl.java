package com.atos.services.nominations;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.OperationFileBean;
import com.atos.beans.nominations.QueryShipperNominationFileBean;
import com.atos.filters.nominations.QueryShipperNominationFileFilter;
import com.atos.mapper.nominations.QueryShipperNominationsMapper;
import com.atos.mapper.nominations.ShipperSubmissionNominationsMapper;
import com.atos.utils.Constants;

@Service("queryShipperWeeklyNominationFileService")
public class QueryShipperWeeklyNominationFileServiceImpl implements QueryShipperWeeklyNominationFileService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6008063454154512438L;
		
	@Autowired
	private QueryShipperNominationsMapper queryShipperMapper;

	@Autowired
	private ShipperSubmissionNominationsMapper weeklyNomMapper;


	@Override
	public List<QueryShipperNominationFileBean> selectQueryNomination(QueryShipperNominationFileFilter filter) {
		return queryShipperMapper.selectQueryNomination(filter);
	}

	
	@Override
	public Map<String, Object> selectContractCodeByUser(QueryShipperNominationFileFilter filter) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
 		List<ComboFilterNS> list = queryShipperMapper.selectContractCodeWeekByShipper(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey().toString(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<String, Object> selectShipperIdNominations(QueryShipperNominationFileFilter filter) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
 		List<ComboFilterNS> list = queryShipperMapper.selectShipperIdNominations(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey().toString(), combo.getValue());
		}
		return map;
	}

	@Override
	public StreamedContent getFile(TreeMap<String, BigDecimal> map) {
		List<OperationFileBean> l = queryShipperMapper.selectGetFile(map);
		if(l.size()==0){
			return null;
		} else {
			OperationFileBean bean = l.get(0);
			ByteArrayInputStream ba = new ByteArrayInputStream(bean.getBinary_data());
			return new DefaultStreamedContent(ba, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", bean.getFile_name());
		}
	}
	@Override
	public int selectStartDayOfWeek() {
		List<String> list = weeklyNomMapper.selectStartDayOfWeek();
		if(list.size()==0){
			return 0;
		} else {
			String day = list.get(0);
			if(day.equals(Constants.SUN)){
				return Constants.SUNDAY;
			} else if(day.equals(Constants.MON)){
				return Constants.MONDAY;
			} else if(day.equals(Constants.TUE)){
				return Constants.TUESDAY;
			} else if(day.equals(Constants.WED)){
				return Constants.WEDNESDAY;
			} else if(day.equals(Constants.THU)){
				return Constants.THURSDAY;
			} else if(day.equals(Constants.FRI)){
				return Constants.FRIDAY;
			} else if(day.equals(Constants.SAT)){
				return Constants.SATURDAY;
			} else {
				return Constants.SUNDAY;
			}
			
			//return list.get(0);
		}
	}
	
	



}
