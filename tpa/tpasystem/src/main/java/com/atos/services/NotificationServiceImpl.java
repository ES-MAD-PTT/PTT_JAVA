package com.atos.services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.NotificationBean;
import com.atos.beans.NotificationOriginBean;
import com.atos.mapper.NotificationMapper;


@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5370591516440983887L;

	@Autowired
	private NotificationMapper notifMapper;
	
	//private HashMap<String,List<NotificationBean>> notifMap;

	@PostConstruct
	public void init(){
		/*this.notifMap = new HashMap<String,List<NotificationBean>>();
		for(int i=0;i<Constants.notifications_head_menu.length;i++){
			this.notifMap.put(Constants.notifications_head_menu[i], new ArrayList<NotificationBean>());
		}*/
		
	}

	@Override
	public List<NotificationBean> getNotifications(String user_id) {
		return notifMapper.getNotifications(user_id);
	}
	
	@Override
	public List<NotificationBean> getNotificationsOrigin(NotificationOriginBean notifOrigin) {
		return notifMapper.getNotificationsOrigin(notifOrigin);
		/*List<NotificationBean> data = notifMap.get(notifOrigin.getOrigin());
		List<NotificationBean> reverse = new ArrayList<NotificationBean>(data);
		Collections.reverse(reverse);
		return reverse;*/
	}

	@Override
	public List<NotificationBean> getCreateNotification(NotificationBean bean) {
		bean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		return notifMapper.getCreateNotification(bean);
	}

	@Transactional( rollbackFor = { Throwable.class })
	public String updateNotification(NotificationBean bean) throws Exception {
		bean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		int upd1 = notifMapper.updateNotification(bean);
		if(upd1!=1){
			throw new Exception("-1");
		}
		return "0";
	}
}
