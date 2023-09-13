package com.atos.services.booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.booking.CapacityPublicationBean;
import com.atos.filters.booking.CapacityPublicationFilter;
import com.atos.mapper.booking.CapacityPublicationMapper;

@Service("capacityPublicationService")
public class CapacityPublicationServiceImpl implements CapacityPublicationService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5074393510115503224L;
	
	@Autowired
	private CapacityPublicationMapper capMapper;
	

	@Override
	public List<CapacityPublicationBean> search(CapacityPublicationFilter filters){
		filters.setPar_chart("N");
		List<CapacityPublicationBean> list = capMapper.selectCapacityPublication(filters);
		for(int i=0;i<list.size();i++) {
			CapacityPublicationBean bean = list.get(i);
			bean.setClave_unica(bean.getIdn_val_avail_capacity()+"-"+bean.getArea()+"-"+bean.getMonthYear()+"-"+bean.getEndDate()+"-"+bean.getAvailable_cap());
		}
		return list;
	}

	@Override
	public List<CapacityPublicationBean> selectCapacityBean(CapacityPublicationBean capacityBean){
		List<CapacityPublicationBean> list = capMapper.selectCapacityBean(capacityBean);
		return list;
	}
	
	@Transactional(rollbackFor = { Throwable.class })
	public String insertAvailableCapacityPublication(CapacityPublicationBean capacityBean) throws Exception{
		
		int ins = capMapper.insertAvailableCapacityPublication(capacityBean);
		if (ins != 1) {
			throw new Exception("-2");
		}			
		return "0";
	}
	
	@Transactional(rollbackFor = { Throwable.class })
	public String updateAvailableCapacityPublication(CapacityPublicationBean capacityBean) throws Exception{

		int ins = capMapper.updateAvailableCapacityPublication(capacityBean);
		if (ins != 1) {
			throw new Exception("-1");
		}			
		return "0";
	}
	

}
