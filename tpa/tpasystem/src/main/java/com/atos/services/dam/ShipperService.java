package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ShipperBean;
import com.atos.filters.dam.ShipperFilter;

public interface ShipperService extends Serializable{

	public List<ShipperBean> selectShippers(ShipperFilter filter, BigDecimal systemId);
	
	public Map<String, Object> selectShipperId();
	
	public List<String> selectCompanyName(String query);

	public List<ComboFilterNS> selectAreas(BigDecimal systemId);

	public List<ComboFilterNS> selectContractPoints(BigDecimal systemId);

	public void refreshDualList(ShipperBean bShipper, BigDecimal systemId, BigDecimal[] filterAreaIds);

	public SystemParameterBean getSystemParameter(String str);
	
	public String updateShipper(ShipperBean shipper, BigDecimal[] filterAreaIds) throws Exception;
	
	public String insertShipper(ShipperBean shipper) throws Exception;
}
