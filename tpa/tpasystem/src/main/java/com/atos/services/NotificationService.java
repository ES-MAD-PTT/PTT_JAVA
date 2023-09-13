package com.atos.services;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.NotificationBean;
import com.atos.beans.NotificationOriginBean;

public interface NotificationService extends Serializable {

	public List<NotificationBean> getNotifications(String user_id);

	public List<NotificationBean> getCreateNotification(NotificationBean bean);

	public String updateNotification(NotificationBean bean) throws Exception;

	public List<NotificationBean> getNotificationsOrigin(NotificationOriginBean notifOrigin);

}
