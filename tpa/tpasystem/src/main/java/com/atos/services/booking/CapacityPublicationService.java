package com.atos.services.booking;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.booking.CapacityPublicationBean;
import com.atos.filters.booking.CapacityPublicationFilter;

public interface CapacityPublicationService extends Serializable{

	public List<CapacityPublicationBean> search(CapacityPublicationFilter filters);
	
	public List<CapacityPublicationBean> selectCapacityBean(CapacityPublicationBean capacityBean);
	
	public String insertAvailableCapacityPublication(CapacityPublicationBean capacityBean) throws Exception;

	public String updateAvailableCapacityPublication(CapacityPublicationBean capacityBean) throws Exception;
}
