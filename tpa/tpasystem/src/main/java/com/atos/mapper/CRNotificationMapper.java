package com.atos.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.CRNotificationBean;
import com.atos.beans.NotificationBean;

public interface CRNotificationMapper {
	
	public List<CRNotificationBean> selectListNotification(Integer Dif);
	public List<BigDecimal> selectGroupIdFromGroupCode(String groupCode);
}
