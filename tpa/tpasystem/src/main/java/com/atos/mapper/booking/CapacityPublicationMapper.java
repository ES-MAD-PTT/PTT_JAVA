package com.atos.mapper.booking;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.booking.CapacityPublicationBean;
import com.atos.filters.booking.CapacityPublicationFilter;

public interface CapacityPublicationMapper extends Serializable {

	public List<CapacityPublicationBean> selectCapacityPublication(CapacityPublicationFilter filters);
	
	public List<CapacityPublicationBean> selectCapacityChart(CapacityPublicationFilter filters);
	
	public List<CapacityPublicationBean> selectCapacityBean(CapacityPublicationBean capacityBean);

	public int insertAvailableCapacityPublication(CapacityPublicationBean capacityBean);
	
	public int updateAvailableCapacityPublication(CapacityPublicationBean capacityBean);
}
