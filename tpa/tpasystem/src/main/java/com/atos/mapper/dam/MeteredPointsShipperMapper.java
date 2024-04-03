package com.atos.mapper.dam;

import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.MeteredPointShipperBean;
import com.atos.filters.dam.MeteredPointsShipperFilter;

public interface MeteredPointsShipperMapper {

	public List<ComboFilterNS> selectShippers();
	
	public List<ComboFilterNS> selectMeteredPoin(MeteredPointShipperBean selection);
	
	public List<ComboFilterNS> selectCustomerType(MeteredPointShipperBean selection);
	
	public List<ComboFilterNS> selectGroupId(MeteredPointShipperBean selection);
	
	public List<MeteredPointShipperBean> selectMeteredPointShipper(MeteredPointsShipperFilter filters);
	
	public List<MeteredPointShipperBean> selectAllDataMeteredPointShipper(MeteredPointShipperBean item);
	
	public List<MeteredPointShipperBean> selectMetPointCustomerGroup (MeteredPointShipperBean item);
	
	public int insertMeteredPointShipper(MeteredPointShipperBean item);
	
	public int deleteMeteredPointShipper(MeteredPointShipperBean item);
	
}
