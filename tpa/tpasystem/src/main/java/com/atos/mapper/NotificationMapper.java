package com.atos.mapper;

import java.util.List;

import com.atos.beans.NotificationBean;
import com.atos.beans.NotificationOriginBean;

public interface NotificationMapper {

	public List<NotificationBean> getNotifications(String user_id);
	
	public List<NotificationBean> getCreateNotification(NotificationBean bean);
	
	public int updateNotification(NotificationBean shipper);
	
	public List<NotificationBean> getNotificationsOrigin(NotificationOriginBean notifOrigin);
}
