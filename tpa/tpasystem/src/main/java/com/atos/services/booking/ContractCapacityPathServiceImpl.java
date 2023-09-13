package com.atos.services.booking;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.booking.ContractCapacityConnectionPathsBean;
import com.atos.beans.booking.ContractCapacityPathAreaValuesBean;
import com.atos.beans.booking.ContractCapacityPathBean;
import com.atos.beans.booking.ContractCapacityPathDetailBean;
import com.atos.beans.booking.ContractCapacityPathEntryExitBean;
import com.atos.beans.booking.ContractCapacityPathInsertBean;
import com.atos.beans.booking.ContractCapacityPathPeriodBean;
import com.atos.beans.booking.ContractCapacityPathValuesBean;
import com.atos.filters.booking.ContractCapacityPathFilter;
import com.atos.mapper.booking.ContractCapacityPathMapper;

@Service("contractCapacityPathService")
public class ContractCapacityPathServiceImpl implements ContractCapacityPathService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5074393510115503224L;
	
	@Autowired
	private ContractCapacityPathMapper ccpMapper;
	

	@Override
	public List<LinkedHashMap<String,String>> search(ContractCapacityPathFilter filters){
		List<ContractCapacityPathBean> list = ccpMapper.selectCapacityPath(filters);
		
		
		List<LinkedHashMap<String,String>> exit = new ArrayList<LinkedHashMap<String,String>>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(3);
		df.setMinimumFractionDigits(0);
		df.setGroupingUsed(false);
		
		for(int i=0;i<list.size();i++) {
			
			ContractCapacityPathBean bean = list.get(i);
			
			LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
			map.put("Zone", bean.getZone());
			map.put("Area", bean.getArea());
			map.put("Contract Point", bean.getPoint_desc());
			map.put("From", sdf1.format(bean.getDate_from()));
			map.put("To", sdf1.format(bean.getDate_to()));
			
			for(int j=0;j<bean.getValues().size();j++) {
				ContractCapacityPathValuesBean val = bean.getValues().get(j);
				BigDecimal quantity = val.getQuantity();
				map.put(val.getMonth_year(), df.format(quantity));
			}
			exit.add(map);
		}
		
		
		return exit;
	}


	@Override
	public Map<String, Object> selectBookingIds(ContractCapacityPathFilter filters) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
 		List<ComboFilterNS> list = ccpMapper.selectBookingIds(filters);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey().toString(), combo.getValue());
		}
		return map;

	}


	@Override
	public ArrayList<Map<String, Object>> selectPeriods(ContractCapacityPathFilter filters) {
		Map<String, Object> period = new LinkedHashMap<String, Object>();
		Map<String, Object> start_dates = new LinkedHashMap<String, Object>();
		Map<String, Object> end_dates = new LinkedHashMap<String, Object>();
		ArrayList<Map<String, Object>> combos = new ArrayList<Map<String, Object>>();
 		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		List<ContractCapacityPathPeriodBean> list = ccpMapper.selectPeriod(filters);
 		if(list.size()>0) {
 			ContractCapacityPathPeriodBean b = list.get(0);
			if(b.getIs_contract_complete().equals("Y") && b.getNum_agreements().intValue()>1) {
				List<ContractCapacityPathPeriodBean> list2 = ccpMapper.selectPeriodContractComplete(filters);
 				for(int i=0;i<list2.size();i++) {
 					start_dates.put(sdf.format(list2.get(i).getStart_date()), sdf.format(list2.get(i).getStart_date()));
 					end_dates.put(sdf.format(list2.get(i).getEnd_date()), sdf.format(list2.get(i).getEnd_date()));
 				}
	 		} else {
	 			for(int i=0;i<list.size();i++) {
	 				period.put(list.get(i).getPeriod_text(), list.get(i).getPeriod_text());
				}
	 		}
		}
 		combos.add(period);
 		combos.add(start_dates);
 		combos.add(end_dates);
 		
 		return combos;
	}


	@Override
	public List<ContractCapacityPathDetailBean> search2(ContractCapacityPathFilter filters) {
		List<ContractCapacityPathDetailBean> list = ccpMapper.selectTechCapacity(filters);
		
		List<ContractCapacityPathDetailBean> list2 = ccpMapper.selectRemainBooked(filters);
		
		List<ContractCapacityPathDetailBean> list4 = ccpMapper.selectAvailableCapacity(filters);
		
		for(int i=0;i<list.size();i++) {
			ContractCapacityPathDetailBean b = list.get(i);
			
			for(int j=0;j<list4.size();j++) {
				ContractCapacityPathDetailBean b2 = list4.get(j);
				if(b.getArea_code() .equals(b2.getArea_code() )) {
					b.setAvailable(b2.getTechnical_capacity().subtract(b2.getBooked_capacity()));
					break;
				}
			}
		}
		
		
		List<ContractCapacityPathAreaValuesBean> list3 = ccpMapper.getContractCapacityPathAreaValuesBean(filters);
		
		for(int i=0;i<list.size();i++) {
			ContractCapacityPathDetailBean b = list.get(i);
			
			for(int j=0;j<list2.size();j++) {
				ContractCapacityPathDetailBean b2 = list2.get(j);
				
				if(b.getIdn_area().equals(b2.getIdn_area())) {
					b.setRemain_booked(b2.getRemain_booked());
					break;
				}
			}
		}
		
		TreeMap<BigDecimal,ContractCapacityPathAreaValuesBean> mapa_min_path_step = new TreeMap<BigDecimal,ContractCapacityPathAreaValuesBean>();
		TreeMap<BigDecimal,ContractCapacityPathAreaValuesBean> mapa_max_path_step = new TreeMap<BigDecimal,ContractCapacityPathAreaValuesBean>();
		
		// we get min and max step, so there are the beginning and the end of the connection
		for(int i=0;i<list3.size();i++) {
			ContractCapacityPathAreaValuesBean bean = list3.get(i);
			if(mapa_min_path_step.containsKey(bean.getIdn_capacity_path())) {
				ContractCapacityPathAreaValuesBean temp = mapa_min_path_step.get(bean.getIdn_capacity_path());
				if(temp.getPath_step().intValue()<bean.getPath_step().intValue()) {
					mapa_min_path_step.put(bean.getIdn_capacity_path(), bean);
				}
			} else {
				mapa_min_path_step.put(bean.getIdn_capacity_path(), bean);
			}
			if(mapa_max_path_step.containsKey(bean.getIdn_capacity_path())) {
				ContractCapacityPathAreaValuesBean temp = mapa_max_path_step.get(bean.getIdn_capacity_path());
				if(temp.getPath_step().intValue()>bean.getPath_step().intValue()) {
					mapa_max_path_step.put(bean.getIdn_capacity_path(), bean);
				}
			} else {
				mapa_max_path_step.put(bean.getIdn_capacity_path(), bean);
			}

		}
		
		for(int i=0;i<list.size();i++) {
			ContractCapacityPathDetailBean b = list.get(i);
			Iterator<ContractCapacityPathAreaValuesBean> it_min = mapa_min_path_step.values().iterator();
			while(it_min.hasNext()) {
				ContractCapacityPathAreaValuesBean bean = it_min.next();
				if(b.getIdn_area().intValue()==bean.getIdn_area().intValue()) {
					b.getList_values_remain_booked().add(bean.getQuantity());
				}
			}
			Iterator<ContractCapacityPathAreaValuesBean> it_max = mapa_max_path_step.values().iterator();
			while(it_max.hasNext()) {
				ContractCapacityPathAreaValuesBean bean = it_max.next();
				if(b.getIdn_area().intValue()==bean.getIdn_area().intValue()) {
					b.getList_values_remain_booked().add(bean.getQuantity());
				}
			}
			
		}
		for(int i=0;i<list.size();i++) {
			ContractCapacityPathDetailBean b = list.get(i);
			for(int j=0;j<list3.size();j++) {
				ContractCapacityPathAreaValuesBean bean = list3.get(j);
				if(b.getIdn_area().intValue()==bean.getIdn_area().intValue()) {
					b.getList_values_available().add(bean.getQuantity());
				}
				
			}
		}
		
		return list;
	}


	@Override
	public List<ContractCapacityPathEntryExitBean> selectEntryPoints(ContractCapacityPathFilter filters2) {
 		return ccpMapper.selectEntryPoints(filters2);

	}


	@Override
	public List<ContractCapacityPathEntryExitBean> selectExitPoints(ContractCapacityPathFilter filters2) {
 		return ccpMapper.selectExitPoints(filters2);
	}


	@Override
	public List<ContractCapacityConnectionPathsBean> selectConnectionPaths(ContractCapacityPathFilter filters2) {
		List<ContractCapacityConnectionPathsBean> list = ccpMapper.getConnectionPaths(filters2);
		
		ContractCapacityPathInsertBean bean = new ContractCapacityPathInsertBean();
		bean.setStart_date(filters2.getStart_date());
		bean.setEnd_date(filters2.getEnd_date());
		
		for(int i=0;i<list.size();i++) {
			bean.setIdn_capacity_path(list.get(i).getIdn_capacity_path());
			List<ContractCapacityPathInsertBean> list2 = ccpMapper.getContractCapacityPathValues(bean);
			if(list2.size()>=1) {
				list.get(i).setBooked(list2.get(0).getAssigned_quantity());
			}
				
		}
		
		
		return list;
	}


	@Override
	public int savePath(ContractCapacityPathFilter filters2, BigDecimal value, BigDecimal idn_capacity_path, BigDecimal idn_entry, BigDecimal idn_exit) {
		
		ContractCapacityPathInsertBean bean = new ContractCapacityPathInsertBean();
		
		bean.setStart_date(filters2.getStart_date());
		bean.setEnd_date(filters2.getEnd_date());
		bean.setAssigned_quantity(value);
		bean.setEntry_point(idn_entry);
		bean.setExit_point(idn_exit);
		bean.setIdn_contract(filters2.getIdn_booking());
		bean.setIdn_capacity_path(idn_capacity_path);
		bean.setVersion_date(Calendar.getInstance().getTime());

		List<ContractCapacityPathInsertBean> list = ccpMapper.getContractAgreementIdPoint(bean);
		if(list.size()==0) {
			return -1;
		} else {
			for(int i=0;i<list.size();i++) {
				
				bean.setIdn_contract_agreement(list.get(i).getIdn_contract_agreement());
				bean.setIdn_contract_point_orig(list.get(i).getIdn_contract_point_orig());
				bean.setIdn_contract_point_dest(list.get(i).getIdn_contract_point_dest());
				
				ccpMapper.insertCapacityPath(bean);
			}

		}
		
		
		return 0;
	}


	@Override
	public ArrayList<BigDecimal> getCapacityPathStep(BigDecimal idn_capacity_path) {
		
		ArrayList<BigDecimal> steps = new ArrayList<BigDecimal>();
		
		List<ContractCapacityPathAreaValuesBean> list = ccpMapper.getCapacityPathStep(idn_capacity_path);
		
		if(list.size()==1) {
			steps.add(list.get(0).getIdn_area());
			steps.add(list.get(0).getIdn_area());
		} else {
			steps.add(list.get(0).getIdn_area());
			steps.add(list.get(list.size()-1).getIdn_area());
		}
		
		return steps;
	}

	
}
