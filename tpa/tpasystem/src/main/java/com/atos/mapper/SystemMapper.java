package com.atos.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

public interface SystemMapper {

	public List<TreeMap<String, Object>> getSystems();
	public List<BigDecimal> getOnshoreSystem();
	public Integer userHasOnshoreProfile(String userName);
	public List<BigDecimal> getIdnUserGroupOperator();
}
