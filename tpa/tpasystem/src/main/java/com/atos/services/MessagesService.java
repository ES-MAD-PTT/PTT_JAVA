package com.atos.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.atos.beans.MessageBean;
import com.atos.utils.Constants;

@ManagedBean(name = "msgService")
@SessionScoped
public class MessagesService implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6819959718993956417L;
	private HashMap<String,List<MessageBean>> messageMap;

	@PostConstruct
	public void init(){
		this.messageMap = new HashMap<String,List<MessageBean>>();
		for(int i=0;i<Constants.head_menu.length;i++){
			this.messageMap.put(Constants.head_menu[i], new ArrayList<MessageBean>());
		}
		
	}
	
	public List<MessageBean> getMessagesList(String key) {
		
		List<MessageBean> data = messageMap.get(key);
		List<MessageBean> reverse = new ArrayList<MessageBean>(data);
		Collections.reverse(reverse);
		return reverse;
	}

	public void setMessagesList(String key, List<MessageBean> messagesList) {
		this.messageMap.put(key, messagesList);
	}

	public void addMessage(String key, MessageBean bean){
		this.messageMap.get(key).add(bean);
	}
	public void removeMessage(String key, MessageBean bean){
		for(int i=0;i<this.messageMap.get(key).size();i++){
			if(this.messageMap.get(key).get(i).equals(bean)){
				this.messageMap.get(key).remove(i);
				return;
			}
		}
	}
	public void removeAllMessages(String key){
		this.messageMap.put(key, new ArrayList<MessageBean>());
	}
	public void removeAllAreaMessages(){
		for(int i=0;i<Constants.head_menu.length;i++){
			this.messageMap.put(Constants.head_menu[i], new ArrayList<MessageBean>());
		}
	}
}