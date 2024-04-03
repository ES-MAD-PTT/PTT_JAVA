package com.atos.services.dam;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.MeteredPointShipperBean;
import com.atos.filters.dam.MeteredPointsShipperFilter;
import com.atos.mapper.dam.MeteredPointsShipperMapper;

@Service("meteredPointsShipperService")
public class MeteredPointsShipperServiceImpl implements MeteredPointsShipperService {

	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private MeteredPointsShipperMapper meteredPointsShipperMapper;

	@Override
	public Map<BigDecimal, Object> selectShippers() {
		return meteredPointsShipperMapper.selectShippers().stream().collect(
				Collectors.toMap(ComboFilterNS::getKey, ComboFilterNS::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	@Override
	public Map<BigDecimal, Object> selectMeteredPoin(MeteredPointShipperBean selection) {
		return meteredPointsShipperMapper.selectMeteredPoin(selection).stream().collect(
				Collectors.toMap(ComboFilterNS::getKey, ComboFilterNS::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	@Override
	public Map<BigDecimal, Object> selectCustomerType(MeteredPointShipperBean selection) {
		return meteredPointsShipperMapper.selectCustomerType(selection).stream().collect(
				Collectors.toMap(ComboFilterNS::getKey, ComboFilterNS::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	@Override
	public Map<BigDecimal, Object> selectGroupId(MeteredPointShipperBean selection) {
		return meteredPointsShipperMapper.selectGroupId(selection).stream().collect(
				Collectors.toMap(ComboFilterNS::getKey, ComboFilterNS::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	@Override
	public List<MeteredPointShipperBean> selectMeteredPointShipper(MeteredPointsShipperFilter filters) {
		return meteredPointsShipperMapper.selectMeteredPointShipper(filters);
	}

	@Override
	public List<MeteredPointShipperBean> selectAllDataMeteredPointShipper(MeteredPointShipperBean item) {
		return meteredPointsShipperMapper.selectAllDataMeteredPointShipper(item);
	}

	@Override
	public List<MeteredPointShipperBean> selectMetPointCustomerGroup(MeteredPointShipperBean item) {
		return meteredPointsShipperMapper.selectMetPointCustomerGroup(item);
	}

	@Override
	public String insertMeteredPointShipper(MeteredPointShipperBean meteredPoint, List<MeteredPointShipperBean> listMeteredPoint, String userName) throws Throwable {
		try {
			listMeteredPoint.forEach(item -> {
				meteredPoint.setIdnMeteringPoint(item.getIdnMeteringPoint());
				meteredPoint.setUserName(userName);
				meteredPointsShipperMapper.insertMeteredPointShipper(meteredPoint);
			});
		} catch (Exception e) {
			throw new Exception("1");
		}
		return "0";
	}

	@Override
	public String deleteMeteredPointShipper(List<MeteredPointShipperBean> listMeteredPoint) throws Throwable {
		try {
			listMeteredPoint.forEach(item -> {
				meteredPointsShipperMapper.deleteMeteredPointShipper(item);
			});
		} catch (Exception e) {
			throw new Exception("1");
		}
		return "0";
	}

	
}
