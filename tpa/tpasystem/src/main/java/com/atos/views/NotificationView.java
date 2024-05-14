package com.atos.views;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.primefaces.context.RequestContext;

import com.atos.beans.MessageBean;
import com.atos.beans.NotificationBean;
import com.atos.beans.NotificationOriginBean;
import com.atos.services.NotificationService;
import com.atos.utils.Constants;


@ManagedBean
@SessionScoped
public class NotificationView implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2208853064900908676L;
	private static final Logger log = LogManager.getLogger(NotificationView.class);

	private List<NotificationBean> notifList, notifListBalancing, notifListMoc, notifListContract, notifListBooking, notifListMetering, notifListTariff, notifListAllocation, notifListQuality, notifListMaintenance, notifListNomination, notifListEvent;
	private int numberNotBlue;
	
	private String longMessage;
	
	public String getLongMessage() {
		if(longMessage!=null){
			String text = longMessage.replaceAll("<p>", "");
			text = text.replaceAll("</p>", "\n");
			return text;
		} else {
			return longMessage;
		}
	}

	public void setLongMessage(String longMessage) {
		this.longMessage = longMessage;
	}

	@ManagedProperty("#{notificationService}")
    transient private NotificationService service;
    
    public void setService(NotificationService service) {
        this.service = service;
    }

    @ManagedProperty("#{messagesView}")
    private MessagesView messages;
    
    public void setMessages(MessagesView messages) {
        this.messages = messages;
    }
	
	@PostConstruct
    public void init() {
		checkNotifications();
    }
    
	public List<NotificationBean> getNotifList() {
		return notifList;
	}
	
	public List<NotificationBean> getNotifListBalancing() {
		return notifListBalancing;
	}

	public List<NotificationBean> getNotifList(String origen) {
		switch(origen) {

			case "BALANCING": return notifListBalancing;
			case "MOC": return notifListMoc;
			case "BOOKING": return notifListBooking;
			case "CONTRACT": return notifListContract;
			case "METERING": return notifListMetering;
			case "TARIFF": return notifListTariff;
			case "ALLOCATION": return notifListAllocation;
			case "QUALITY": return notifListQuality;
			case "MAINTENANCE": return notifListMaintenance;
			case "NOMINATION": return notifListNomination;
			case "EVENT": return notifListEvent;
		}
		return null;
	}

	public int getNumberNotBlue() {
		return numberNotBlue;
	}


	public void checkNotifications() {
		notifList = service.getNotifications((String)SecurityUtils.getSubject().getPrincipal());
		notifListBalancing = service.getNotificationsOrigin(new NotificationOriginBean("BALANCING", (String)SecurityUtils.getSubject().getPrincipal()));
		notifListMoc = service.getNotificationsOrigin(new NotificationOriginBean("MOC", (String)SecurityUtils.getSubject().getPrincipal()));
		notifListBooking = service.getNotificationsOrigin(new NotificationOriginBean("BOOKING", (String)SecurityUtils.getSubject().getPrincipal()));
		notifListContract = service.getNotificationsOrigin(new NotificationOriginBean("CONTRACT", (String)SecurityUtils.getSubject().getPrincipal()));
		notifListMetering = service.getNotificationsOrigin(new NotificationOriginBean("METERING", (String)SecurityUtils.getSubject().getPrincipal()));
		notifListTariff = service.getNotificationsOrigin(new NotificationOriginBean("TARIFF", (String)SecurityUtils.getSubject().getPrincipal()));
		notifListAllocation = service.getNotificationsOrigin(new NotificationOriginBean("ALLOCATION", (String)SecurityUtils.getSubject().getPrincipal()));
		notifListQuality = service.getNotificationsOrigin(new NotificationOriginBean("QUALITY", (String)SecurityUtils.getSubject().getPrincipal()));
		notifListMaintenance = service.getNotificationsOrigin(new NotificationOriginBean("MAINTENANCE", (String)SecurityUtils.getSubject().getPrincipal()));
		notifListNomination = service.getNotificationsOrigin(new NotificationOriginBean("NOMINATION", (String)SecurityUtils.getSubject().getPrincipal()));
		notifListEvent = service.getNotificationsOrigin(new NotificationOriginBean("EVENT", (String)SecurityUtils.getSubject().getPrincipal()));
		
   		numberNotBlue= notifList.size();
		RequestContext.getCurrentInstance().update("formHeader:notif_blue");
    }
	
	public void acknowledgeNotification(NotificationBean notif){
		String error = "0";
		try {
			error = service.updateNotification(notif);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		if(error!=null && error.equals("0")){
			messages.addMessage(Constants.head_menu[11],new MessageBean(Constants.INFO,"Notification updated", "Notification acknowledge confirmed", Calendar.getInstance().getTime()));	
		} else {
			messages.addMessage(Constants.head_menu[11],new MessageBean(Constants.ERROR,"Notification error", "Error updating notification acknowledge" , Calendar.getInstance().getTime()));			
		}
		checkNotifications();
 	}
	
	public void removeAllNotifications(){
		String error = "0";
		for(int i=0;i<notifList.size();i++){
			try {
				error = service.updateNotification(notifList.get(i));
			} catch (Exception e) {
				log.catching(e);
				// we assign the return message 
				error = e.getMessage();
			}
			if(error!=null && error.equals("0")){
				continue;
			} else {
				messages.addMessage(Constants.head_menu[11],new MessageBean(Constants.ERROR,"Notification error", "Error updating notification acknowledge" , Calendar.getInstance().getTime()));
				return;
			}
		}
		if(notifList.size()==0){
			messages.addMessage(Constants.head_menu[11],new MessageBean(Constants.WARNING,"Notifications warning", "No notifications available to be updated", Calendar.getInstance().getTime()));	
		} else {
			messages.addMessage(Constants.head_menu[11],new MessageBean(Constants.INFO,"Notifications updated", "All notification acknowledge confirmed", Calendar.getInstance().getTime()));
			
			
		}
		checkNotifications();
	}
	
	
	public void removeAllNotificationsOrigin(String origin){
		String error = "0";
		List<NotificationBean> notifListToRemove = this.getNotifList(origin);
		for(int i=0;i<notifListToRemove.size();i++){
			try {
				error = service.updateNotification(notifListToRemove.get(i));
			} catch (Exception e) {
				log.catching(e);
				// we assign the return message 
				error = e.getMessage();
			}
			if(error!=null && error.equals("0")){
				continue;
			} else {
				messages.addMessage(Constants.head_menu[11],new MessageBean(Constants.ERROR,"Notification error", "Error updating notification acknowledge" , Calendar.getInstance().getTime()));
				return;
			}
		}
		if(notifListToRemove.size()==0){
			messages.addMessage(Constants.head_menu[11],new MessageBean(Constants.WARNING,"Notifications warning", "No notifications available to be updated", Calendar.getInstance().getTime()));	
		} else {
			messages.addMessage(Constants.head_menu[11],new MessageBean(Constants.INFO,"Notifications updated", "All notification "+origin+" acknowledge confirmed", Calendar.getInstance().getTime()));
			
			
		}
		checkNotifications();
	}
	
	
}