package com.atos.services;

import java.util.List;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.CRNotificationBean;
import com.atos.beans.NotificationBean;
import com.atos.mapper.CRNotificationMapper;
import com.atos.mapper.NotificationMapper;

@Service("crNotificationService")
public class CRNotificationServiceImpl implements CRNotificationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5370591516440983887L;

	@Autowired
	private CRNotificationMapper crNotifMapper;

	@Override
	public List<CRNotificationBean> selectListNotification(Integer dif) {
		return crNotifMapper.selectListNotification(dif);
	}

	

	
}
