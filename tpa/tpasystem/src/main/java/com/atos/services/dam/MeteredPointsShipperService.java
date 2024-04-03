package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.dam.MeteredPointShipperBean;
import com.atos.filters.dam.MeteredPointsShipperFilter;

public interface MeteredPointsShipperService extends Serializable {

	public Map<BigDecimal, Object> selectShippers();
	
	public Map<BigDecimal, Object> selectMeteredPoin(MeteredPointShipperBean selection);
	
	public Map<BigDecimal, Object> selectCustomerType(MeteredPointShipperBean selection);
	
	public Map<BigDecimal, Object> selectGroupId(MeteredPointShipperBean selection);
	
	public List<MeteredPointShipperBean> selectMeteredPointShipper(MeteredPointsShipperFilter filters);
	
	public List<MeteredPointShipperBean> selectAllDataMeteredPointShipper(MeteredPointShipperBean item);
	
	public List<MeteredPointShipperBean> selectMetPointCustomerGroup (MeteredPointShipperBean item);
	
	public String insertMeteredPointShipper(MeteredPointShipperBean meteredPoint, List<MeteredPointShipperBean> listMeteredPoint, String userName) throws Throwable;
	
	public String deleteMeteredPointShipper(List<MeteredPointShipperBean> listMeteredPoint) throws Throwable;

}
