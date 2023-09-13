package com.atos.mapper.balancing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.atos.beans.ComboFilter;
import com.atos.filters.balancing.BalanceManagementFilter;

public interface BalanceManagementMapper extends Serializable {
	public Date selectOpenPeriodFirstDay(Map<String, Object> params);
	public List<ComboFilter> selectBalanceClosingTypeCodes();
	public List<BalanceManagementFilter> closeBalance(BalanceManagementFilter bean);
}
