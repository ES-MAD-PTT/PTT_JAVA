package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilter;
import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.ShipperBean;
import com.atos.beans.dam.ShipperPointBean;
import com.atos.filters.dam.ShipperFilter;
import com.atos.filters.dam.ShipperPointFilter;

public interface ShipperMapper {

	public List<ShipperBean> selectShippers(ShipperFilter filters);

	public List<ComboFilter> selectShipperId();

	public List<String> selectCompanyName(String query);
	
	public List<String> getShipperId(ShipperBean bean);
	
	public int insertShipper(ShipperBean shipper);	
	
	public int insertUserGroup(ShipperBean shipper);
	
	public int updateShipper(ShipperBean shipper);

	public int updateUserGroup(ShipperBean shipper);

	public List<ComboFilterNS> selectAreas(BigDecimal idn_system);
	
	// Parametros que se usan del filter:
	// systemId
	// areaIds
	public List<ComboFilterNS> selectContractPoints(ShipperPointFilter filters);
	
	// Parametros que se usan del filter:
	// userGroupId
	// nullEndDateFlag
	// areaIds
	//
	// Consulta la ultima version de puntos asociados a un shipper en TPA_TSHIPPER_POINT.
	// nullEndDateFlag == true, obtiene solo puntos con endDate nulo, es decir, activos.
	public List<ShipperPointBean> selectShipperPoints(ShipperPointFilter filters);
	
	public int insertShipperPoint(ShipperPointBean shipperPoint);	
	
	public int deleteShipperPoint(ShipperPointBean shipperPoint);		
}
