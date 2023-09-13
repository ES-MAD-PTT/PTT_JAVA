package com.atos.views;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.context.RequestContext;

import com.atos.beans.AlarmBean;
import com.atos.beans.UserBean;
import com.atos.beans.scadaAlarms.ScadaAlarmBean;
import com.atos.services.scadaAlarms.ScadaAlarmPanelService;
import com.atos.utils.Constants;

@ManagedBean
@SessionScoped
public class AlarmView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7868104218263958791L;

	private int numberBlueAlarm;
	private int numberOrangeAlarm;
	private int numberRedAlarm;
	private List<ScadaAlarmBean> items;

	private List<ScadaAlarmBean> filteredAlarms;
	
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

	@ManagedProperty("#{userBean}")
    private UserBean userBean;

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

	@ManagedProperty("#{scadaAlarmPanelService}")
	transient private ScadaAlarmPanelService service;

	public void setService(ScadaAlarmPanelService service) {
		this.service = service;
	}

	public List<ScadaAlarmBean> getAlarmList() {
		TreeMap<String,String> map = new TreeMap<String,String>();
		if(userBean.isUser_type(Constants.SHIPPER)){
			map.put("shipper", "shipper");
    	}
		if(userBean.isUser_type(Constants.OPERATOR)){
			map.put("operator", "operator");
    	}
		items = service.selectScadaAlarmsPanel(map); 
		return items;
	}

	@PostConstruct
	public void init() {
		checkNotifications();
	}

	public List<ScadaAlarmBean> getItems() {
		return items;
	}


	public List<ScadaAlarmBean> getFilteredAlarms() {
		return filteredAlarms;
	}

	public void setFilteredAlarms(List<ScadaAlarmBean> filteredAlarms) {
		this.filteredAlarms = filteredAlarms;
	}

	public int getNumberBlueAlarm() {
		return numberBlueAlarm;
	}

	public int getNumberOrangeAlarm() {
		return numberOrangeAlarm;
	}

	public int getNumberRedAlarm() {
		return numberRedAlarm;
	}

	public void checkNotifications() {
		this.numberBlueAlarm = 0;
		this.numberOrangeAlarm = 0;
		this.numberRedAlarm = 0;
		List<ScadaAlarmBean> list = this.getAlarmList();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getSeverity()==null){
				this.numberBlueAlarm++;
			} else {
				if (list.get(i).getSeverity().equals("NO SEVERITY")) {
					this.numberBlueAlarm++;
				}
				if (list.get(i).getSeverity().equals("HIGH")) {
					this.numberRedAlarm++;
				}
				if (list.get(i).getSeverity().equals("MODERATE")) {
					this.numberOrangeAlarm++;
				}
			}
		}
		RequestContext.getCurrentInstance().update("formHeader:alarm_blue");
		RequestContext.getCurrentInstance().update("formHeader:alarm_orange");
		RequestContext.getCurrentInstance().update("formHeader:alarm_red");
		RequestContext.getCurrentInstance().update("formHeader:alarmTable");
	}

	public boolean isVisible(){
		if(userBean.isUser_type(Constants.SHIPPER)){
			return false;
		}
		if(userBean.isUser_type(Constants.OPERATOR)){
			return true;
		}
		return false;
	}
	

	public String isVisibleName(){
		if(userBean.isUser_type(Constants.SHIPPER)){
			return "false";
		}
		if(userBean.isUser_type(Constants.OPERATOR)){
			return "true";
		}
		return "false";
	}
	
}