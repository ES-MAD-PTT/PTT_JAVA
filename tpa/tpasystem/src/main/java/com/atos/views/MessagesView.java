package com.atos.views;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.atos.beans.MessageBean;
import com.atos.services.MessagesService;
import com.atos.utils.Constants;


@ManagedBean
@SessionScoped
public class MessagesView implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2208853064900908676L;
	private static final Logger log = LogManager.getLogger(MessagesView.class);
	private int infoMsg = 0;
	private int warningMsg = 0;
	private int errorMsg = 0;
	
	private String longMessage;
	
    @ManagedProperty("#{msgService}")
    private MessagesService service;
     
    public void setService(MessagesService service) {
        this.service = service;
    }

    @PostConstruct
    public void init() {
	}

    public List<MessageBean> getMessagesList(String key){
    	return service.getMessagesList(key);
    }
    
	public int getInfoMsg() {
		this.updateCounter();
		return infoMsg;
	}

	public int getWarningMsg() {
		this.updateCounter();
		return warningMsg;
	}

	public int getErrorMsg() {
		this.updateCounter();
		return errorMsg;
	}

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

	public void updateCounter(){
		this.errorMsg=0;
		this.warningMsg=0;
		this.infoMsg=0;
		for(int j=0;j<Constants.head_menu.length;j++){
			for(int i=0;i<service.getMessagesList(Constants.head_menu[j]).size();i++){
				if(service.getMessagesList(Constants.head_menu[j]).get(i).getType()==Constants.ERROR){
					this.errorMsg++;
				}
				if(service.getMessagesList(Constants.head_menu[j]).get(i).getType()==Constants.WARNING){
					this.warningMsg++;
				}
				if(service.getMessagesList(Constants.head_menu[j]).get(i).getType()==Constants.INFO){
					this.infoMsg++;
				}
			}
		}
	}
	public void addMessage(String key, MessageBean bean){
		this.service.addMessage(key, bean);
		this.logger(key, bean);
		this.updateComponent(bean.getType(),true);
		FacesMessage msg = new FacesMessage(getSeverity(bean.getType()), bean.getSummary(), this.getMessage(bean.getMessage()));
        FacesContext.getCurrentInstance().addMessage(null, msg);

	}
	public void removeMessage(String key, MessageBean bean){
		this.service.removeMessage(key, bean);
		this.updateComponent(bean.getType(),false);
	}
	public void removeAllMessages(String key){
		this.service.removeAllMessages(key);
		this.updateComponent(0, false);
	}
	public void removeAllAreasMessages(){
		this.service.removeAllAreaMessages();
		this.updateComponent(0, false);
	}
	private void updateComponent(int type,boolean close){
		RequestContext context = RequestContext.getCurrentInstance();
		if(type==Constants.INFO){
			context.update("formHeader:msg_info");
		}
		if(type==Constants.WARNING){
			context.update("formHeader:msg_warning");
		}
		if(type==Constants.ERROR){
			context.update("formHeader:msg_error");
		}
		if(type==0){
			context.update("formHeader:msg_info");
			context.update("formHeader:msg_warning");
			context.update("formHeader:msg_error");
		}
		if(close){
			context.execute("PF('messagePanelWidget').hide()");
		}
	}
	private Severity getSeverity(int type){
		if(type==Constants.ERROR){
			return FacesMessage.SEVERITY_ERROR;
		}
		if(type==Constants.WARNING){
			return FacesMessage.SEVERITY_WARN;
		}
		if(type==Constants.INFO){
			return FacesMessage.SEVERITY_INFO;
		}
		return FacesMessage.SEVERITY_WARN;
	}
	private void logger(String key, MessageBean bean){
		if(bean.getType()==Constants.ERROR){
			log.error(bean.getSummary() + ": " + bean.getMessage());
		} else if(bean.getType()==Constants.WARNING){
			log.warn(bean.getSummary() + ": " + bean.getMessage());
		} else if(bean.getType()==Constants.INFO){
			log.info(bean.getSummary() + ": " + bean.getMessage());
		} else {
			log.trace(bean.getSummary() + ": " + bean.getMessage());
		}
	}
	private String getMessage(String message){
		message= message.replaceAll("<p>", "");
		message= message.replaceAll("</p>", "");
		return message;
		
	}
}