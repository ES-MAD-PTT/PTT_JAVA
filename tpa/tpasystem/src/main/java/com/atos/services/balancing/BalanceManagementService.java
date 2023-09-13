package com.atos.services.balancing;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.atos.beans.UserBean;
import com.atos.filters.balancing.BalanceManagementFilter;

public interface BalanceManagementService extends Serializable {

	public Date selectOpenPeriodFirstDay(Map<String, Object> params);
	public Map<String, Object> selectBalanceClosingTypeCodes();
	public void closeBalance(BalanceManagementFilter _bean, UserBean _user) throws Exception;
}
