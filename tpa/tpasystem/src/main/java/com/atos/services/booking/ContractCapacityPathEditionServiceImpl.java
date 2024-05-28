package com.atos.services.booking;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.booking.ContractCapacityConnectionPathsBean;
import com.atos.beans.booking.ContractCapacityPathAreaValuesBean;
import com.atos.beans.booking.ContractCapacityPathDetailBean;
import com.atos.beans.booking.ContractCapacityPathEditionBean;
import com.atos.beans.booking.ContractCapacityPathEditionDatesBean;
import com.atos.beans.booking.ContractCapacityPathEntryExitBean;
import com.atos.beans.booking.ContractCapacityPathInsertBean;
import com.atos.beans.booking.ContractCapacityPathPeriodBean;
import com.atos.filters.booking.ContractCapacityPathFilter;
import com.atos.mapper.booking.ContractCapacityPathMapper;

@Service("contractCapacityPathEditionService")
public class ContractCapacityPathEditionServiceImpl implements ContractCapacityPathEditionService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5074393510115503224L;
	
	@Autowired
	private ContractCapacityPathMapper ccpMapper;
	
	@Override
	public Map<BigDecimal, Object> selectShippers(ContractCapacityPathFilter filter) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = ccpMapper.selectShippersCombo(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}


	@Override
	public Map<String, Object> selectBookingIds(ContractCapacityPathFilter filters) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
 		List<ComboFilterNS> list = ccpMapper.selectBookingIdsByShipper(filters);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey().toString(), combo.getValue());
		}
		return map;

	}


	@Override
	public ArrayList<Map<String, Object>> selectPeriods(ContractCapacityPathFilter filters) {
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
 					start_dates.put(sdf.format(list.get(i).getStart_date()), sdf.format(list.get(i).getStart_date()));
 					end_dates.put(sdf.format(list.get(i).getEnd_date()), sdf.format(list.get(i).getEnd_date()));
 				}
	 		}
		}
 		combos.add(start_dates);
 		combos.add(end_dates);
 		
 		return combos;
	}


	@Override
	public List<ContractCapacityPathEditionDatesBean> search(ContractCapacityPathFilter filters) {
		
		List<ContractCapacityPathDetailBean> list = null;
		
		List<ContractCapacityPathPeriodBean> listPeriod = ccpMapper.selectPeriod(filters);
 		if(listPeriod.size()>0) {
 			ContractCapacityPathPeriodBean b = listPeriod.get(0);
			if(b.getIs_contract_complete().equals("Y") && b.getNum_agreements().intValue()>1) {
				list = ccpMapper.selectTechCapacityEditionDates(filters);
			} else {
				list = ccpMapper.selectTechCapacityEditionPeriod(filters);
			}
 		}

		
		// we calculate the available capacity value
		for(int i=0;i<list.size();i++) {
			ContractCapacityPathDetailBean b = list.get(i);
			b.setAvailable(b.getTechnical_capacity().subtract(b.getBooked_capacity()));
		}	
		
		// we make groups for start_date-end_date
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		TreeMap<String,ArrayList<ContractCapacityPathDetailBean>> mapa_tech_capacities = new TreeMap<String,ArrayList<ContractCapacityPathDetailBean>>();
		for(int i=0;i<list.size();i++) {
			ContractCapacityPathDetailBean b = list.get(i);
			String key = sdf.format(b.getStart_date()) + "-" + sdf.format(b.getEnd_date());
			if(mapa_tech_capacities.containsKey(key)){
				ArrayList<ContractCapacityPathDetailBean> l = mapa_tech_capacities.get(key);
				l.add(b);
				mapa_tech_capacities.put(key, l);
			} else {
				ArrayList<ContractCapacityPathDetailBean> l = new ArrayList<ContractCapacityPathDetailBean>();
				l.add(b);
				mapa_tech_capacities.put(key, l);
			}
		}
		
		List<ContractCapacityPathEditionDatesBean> tech_list = new ArrayList<ContractCapacityPathEditionDatesBean>();
		Iterator<String> it = mapa_tech_capacities.keySet().iterator();
		while(it.hasNext()) {
			ArrayList<ContractCapacityPathDetailBean> b = mapa_tech_capacities.get(it.next());
			ContractCapacityPathEditionDatesBean bean = new ContractCapacityPathEditionDatesBean();
			if(b.size()>0){
				bean.setStart_date(b.get(0).getStart_date());
				bean.setEnd_date(b.get(0).getEnd_date());
				bean.setList(b);
			}
			tech_list.add(bean);
		}
		for(int i=0;i<tech_list.size();i++) {
			ArrayList<ContractCapacityPathDetailBean> l = tech_list.get(i).getList();//new ArrayList<ContractCapacityPathDetailBean>();
			if(l.size()==0) {
				continue;
			}
			ContractCapacityPathFilter f = new ContractCapacityPathFilter();
			f.setStart_date(l.get(0).getStart_date());
			f.setEnd_date(l.get(0).getEnd_date());
			f.setIdn_booking(filters.getIdn_booking());
			
			f.setIs_publish("Y");
			List<ContractCapacityPathAreaValuesBean> list3 = ccpMapper.getContractCapacityPathAreaValuesBean(f);
			
			TreeMap<BigDecimal,ContractCapacityPathAreaValuesBean> mapa_min_path_step = new TreeMap<BigDecimal,ContractCapacityPathAreaValuesBean>();
			TreeMap<BigDecimal,ContractCapacityPathAreaValuesBean> mapa_max_path_step = new TreeMap<BigDecimal,ContractCapacityPathAreaValuesBean>();
			
			// we get min and max step, so there are the beginning and the end of the connection
			for(int j=0;j<list3.size();j++) {
				ContractCapacityPathAreaValuesBean bean = list3.get(j);
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
	
			TreeMap<BigDecimal,HashMap<BigDecimal,BigDecimal>> step_values = new TreeMap<BigDecimal,HashMap<BigDecimal,BigDecimal>>();
			

			ArrayList<BigDecimal> l_idn_capacity_path = new ArrayList<BigDecimal>();
			for(int j=0;j<list3.size();j++) {
				ContractCapacityPathAreaValuesBean bean = list3.get(j);
				HashMap<BigDecimal,BigDecimal> steps = null;
				if(step_values.containsKey(bean.getIdn_capacity_path())) {
					steps = step_values.get(bean.getIdn_capacity_path());
				} else {
					steps =  new HashMap<BigDecimal,BigDecimal>();
					step_values.put(bean.getIdn_capacity_path(), steps);
					l_idn_capacity_path.add(bean.getIdn_capacity_path());
				}
			}
			for(int j=0;j<l_idn_capacity_path.size();j++) {
				HashMap<BigDecimal,BigDecimal> steps = step_values.get(l_idn_capacity_path.get(j));
				for(int k=0;k<l.size();k++) {
					ContractCapacityPathDetailBean b = l.get(k);
					steps.put(b.getIdn_area(), null);
				}
			}
			for(int j=0;j<list3.size();j++) {
				ContractCapacityPathAreaValuesBean bean = list3.get(j);
				HashMap<BigDecimal,BigDecimal> steps = step_values.get(bean.getIdn_capacity_path());
				steps.put(bean.getIdn_area(), bean.getQuantity());
			}
			for(int k=0;k<l.size();k++) {
				ContractCapacityPathDetailBean b = l.get(k);
				for(int j=0;j<l_idn_capacity_path.size();j++) {
					HashMap<BigDecimal,BigDecimal> steps = step_values.get(l_idn_capacity_path.get(j));
					b.getList_values_available().add(steps.get(b.getIdn_area()));
				}
			}
			
			
	/*		for(int j=0;j<list.size();j++) {
				ContractCapacityPathDetailBean b = list.get(j);
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
			for(int j=0;j<list.size();j++) {
				ContractCapacityPathDetailBean b = list.get(j);
				for(int k=0;k<list3.size();k++) {
					ContractCapacityPathAreaValuesBean bean = list3.get(k);
					if(b.getIdn_area().intValue()==bean.getIdn_area().intValue()) {
						b.getList_values_available().add(bean.getQuantity());
					}
					
				}
			}*/
			
			
		}
		
		
		return tech_list;
		
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
	public List<ContractCapacityConnectionPathsBean> selectConnectionPaths(ContractCapacityPathFilter filters2, Date start_date, Date end_date) {
		filters2.setStart_date(start_date);
		filters2.setEnd_date(end_date);
		List<ContractCapacityConnectionPathsBean> list = ccpMapper.getConnectionPaths(filters2);
		
		ContractCapacityPathInsertBean bean = new ContractCapacityPathInsertBean();
		bean.setStart_date(start_date);
		bean.setEnd_date(end_date);
		bean.setPublished("Y");
		
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
	public int savePath(ContractCapacityPathEditionBean edit_bean, BigDecimal value, BigDecimal idn_capacity_path, BigDecimal idn_entry, BigDecimal idn_exit) {
		
		ContractCapacityPathInsertBean bean = new ContractCapacityPathInsertBean();
		
		bean.setStart_date(edit_bean.getStart_date());
		bean.setEnd_date(edit_bean.getEnd_date());
		bean.setAssigned_quantity((value==null ? new BigDecimal(0) : value));
		bean.setEntry_point(idn_entry);
		bean.setExit_point(idn_exit);
		bean.setIdn_contract(edit_bean.getIdn_booking());
		bean.setIdn_capacity_path(idn_capacity_path);
		bean.setVersion_date(Calendar.getInstance().getTime());
		bean.setPublished("N");
		
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


	@Override
	public ArrayList<String> getAreaCodes(List<ContractCapacityPathEditionDatesBean> tech_capacities) {
		
		ArrayList<String> area_codes = new ArrayList<String>();
		HashMap<String, String> map_areas = new HashMap<String,String>();
		
		for(int i=0;i<tech_capacities.size();i++) {
			
			for(int j=0;j<tech_capacities.get(i).getList().size();j++) {
				if(!map_areas.containsKey(tech_capacities.get(i).getList().get(j).getArea_code())) {
					area_codes.add(tech_capacities.get(i).getList().get(j).getArea_code());
					map_areas.put(tech_capacities.get(i).getList().get(j).getArea_code(), tech_capacities.get(i).getList().get(j).getArea_code());
				}
			}
		}
		
		return area_codes;
	}


	@Override
	public ArrayList<ContractCapacityPathEditionBean> getEditTable(List<ContractCapacityPathEditionDatesBean> tech_capacities, ContractCapacityPathFilter filters) {

		ArrayList<ContractCapacityPathEditionBean> list = new ArrayList<ContractCapacityPathEditionBean>();
		
		List<String> list_shipper_code = ccpMapper.getShipperCode(filters);
		String shipper_code = "";
		if(list_shipper_code.size()>0) {
			shipper_code = list_shipper_code.get(0);
		}
		List<String> list_shipper_short_name = ccpMapper.getShipperShortName(filters);
		String shipper_short_name = "";
		if(list_shipper_short_name.size()>0) {
			shipper_short_name = list_shipper_short_name.get(0);
		}
		List<String> list_contract_code = ccpMapper.getContractCode(filters);
		String contract_code = "";
		if(list_contract_code.size()>0) {
			contract_code = list_contract_code.get(0);
		}
		List<String> list_area_code_orig = ccpMapper.getAreaCode(filters.getIdn_area_orig());
		String area_code_orig = "";
		if(list_area_code_orig.size()>0) {
			area_code_orig = list_area_code_orig.get(0);
		}
		List<String> list_area_code_dest = ccpMapper.getAreaCode(filters.getIdn_area_dest());
		String area_code_dest = "";
		if(list_area_code_dest.size()>0) {
			area_code_dest = list_area_code_dest.get(0);
		}
		
		
		
		
		for(int i=0;i<tech_capacities.size();i++) {
			ContractCapacityPathEditionDatesBean b = tech_capacities.get(i);
			ContractCapacityPathEditionBean bean = new ContractCapacityPathEditionBean();
			
			bean.setIdn_shipper(filters.getIdn_shipper());
			bean.setShipper_code(shipper_code);
			bean.setShortName(shipper_short_name);
			bean.setIdn_booking(filters.getIdn_booking());
			bean.setContract_code(contract_code);
			bean.setIdn_area_orig(filters.getIdn_area_orig());
			bean.setArea_code_orig(area_code_orig);
			bean.setIdn_area_dest(filters.getIdn_area_dest());
			bean.setArea_code_dest(area_code_dest);
			bean.setStart_date(b.getStart_date());
			bean.setEnd_date(b.getEnd_date());
			
			List<ContractCapacityConnectionPathsBean> list_paths = this.selectConnectionPaths(filters, b.getStart_date(), b.getEnd_date());
			bean.setList_paths(list_paths);
			
			for(int j=0;j<list_paths.size();j++){
				bean.getList_path().add(list_paths.get(j).getPath_desc());
				bean.getList_booked().add(list_paths.get(j).getBooked());
			}
			
			
			list.add(bean);
		}
		
		return list;
	}

	@Override
	public int publishPath(ContractCapacityPathFilter filters2, String username) {
		
		ContractCapacityPathInsertBean bean = new ContractCapacityPathInsertBean();
		
		bean.setStart_date(filters2.getStart_date());
		bean.setEnd_date(filters2.getEnd_date());
		bean.setIdn_contract(filters2.getIdn_booking());
		bean.setVersion_date(Calendar.getInstance().getTime());
		bean.setUsername(username);

		ccpMapper.insertCapacityPathPublish(bean);
		
		return 0;
	}


}
