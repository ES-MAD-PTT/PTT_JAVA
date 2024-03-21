package com.atos.services.dam;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
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

	
}
