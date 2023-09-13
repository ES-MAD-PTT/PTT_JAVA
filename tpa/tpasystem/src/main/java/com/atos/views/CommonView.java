package com.atos.views;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import com.atos.beans.LanguageBean;
import com.atos.beans.MessageBean;
import com.atos.beans.UserBean;
import com.atos.utils.Constants;

public abstract class CommonView implements Serializable {

	@ManagedProperty("#{userBean}")
	private UserBean user;
	@ManagedProperty("#{messagesView}")
    private MessagesView messages;
	@ManagedProperty("#{languageBean}")
	private LanguageBean language;
	@ManagedProperty("#{changeSystemView}")
	private ChangeSystemView changeSystemView;
	
	private int head=0;
	
	
	protected void addMessage(String summaryKey, String messageKey, int severity){
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		messages.addMessage(Constants.head_menu[head],
				new MessageBean(severity, msgs.getString(summaryKey), msgs.getString(messageKey),
						Calendar.getInstance().getTime()));
	}
	
	protected void addComposedMessage(String summary, String message, int severity) {

		messages.addMessage(Constants.head_menu[head],
				new MessageBean(severity, summary, message, Calendar.getInstance().getTime()));
	}

	public static String getMessageResourceString(String key, Object params[] ) {

        String text;
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

        try {
            text = msgs.getString(key);
        } catch (MissingResourceException e) {
            text = "?? key " + key + " not found ??";
        }

        if (params != null) {
            MessageFormat mf = new MessageFormat(text);
            text = mf.format(params, new StringBuffer(), null).toString();
        }

        return text;
    }

	public boolean isShipper() {
		return user.isUser_type(Constants.SHIPPER);
	}

	public boolean validateShipperActions(BigDecimal shipperId) {
		boolean result = true;
		if (isShipper()) {
			result = user.getIdn_user_group().equals(shipperId);
		}
		return result;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public MessagesView getMessages() {
		return messages;
	}

	public void setMessages(MessagesView messages) {
		this.messages = messages;
	}

	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public LanguageBean getLanguage() {
		return language;
	}

	public void setLanguage(LanguageBean language) {
		this.language = language;
	}

	public ChangeSystemView getChangeSystemView() {
		return changeSystemView;
	}

	public void setChangeSystemView(ChangeSystemView changeSystemView) {
		this.changeSystemView = changeSystemView;
	}
}
