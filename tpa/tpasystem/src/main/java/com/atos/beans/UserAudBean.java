package com.atos.beans;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;

public abstract class UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3031038949135526414L;

	private static final Logger log = LogManager.getLogger("com.atos.beans.UserAudBean");
	
	private String username;

	public UserAudBean() {
		try{
			this.username = (String) SecurityUtils.getSubject().getPrincipal();
		}
		catch(Exception e)
		{
			// Por si no se puede coger el usuario de la sesion HTTP.
			log.error(e.getMessage(), e);
			this.username = "N/A";
		}
	}

	public UserAudBean(String _username) {
		this.username = _username;
	}
	/*
	  public UserAudBean(UserBean user){ 
	  	this.username = user.getUsername(); 
	 }
	 */

	public String getUsername() {
		return username;

	}

	public void setUsername(String username) {
		this.username = username;
	}

}
