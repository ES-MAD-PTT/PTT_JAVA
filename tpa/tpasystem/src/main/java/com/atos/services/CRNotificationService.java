package com.atos.services;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.CRNotificationBean;
import com.atos.beans.NotificationBean;

public interface CRNotificationService extends Serializable{
	
	public List<CRNotificationBean> selectListNotification(Integer Dif);
	
}
