package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.shiro.SecurityUtils;

import com.atos.services.UserService;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3031038949135526414L;
	private String username;
	private String user_type;
	private BigDecimal idn_user;
	private BigDecimal idn_user_group;
	private String type_desc;
    private String user_name_db;
    private String user_group_id;
	
	@ManagedProperty("#{userService}")
    transient private UserService service;
    
    public void setService(UserService service) {
        this.service = service;
    }
	
	public UserBean(){
		this.username = (String) SecurityUtils.getSubject().getPrincipal();
	}
	
	public String getUsername() {
		return username;
	}

	public String getUser_type() {
		return user_type;
	}

	public BigDecimal getIdn_user() {
		return idn_user;
	}

	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}

	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}

	public boolean isUser_type(String type){
		if(user_type.equals(type)){
			return true;
		} else {
			return false;
		}
	}
	
	public String getType_desc() {
		return type_desc;
	}

	public void setType_desc(String type_desc) {
		this.type_desc = type_desc;
	}

	public String getUser_name_db() {
		return user_name_db;
	}

	public void setUser_name_db(String user_name_db) {
		this.user_name_db = user_name_db;
	}

	public String getUser_group_id() {
		return user_group_id;
	}

	public void setUser_group_id(String user_group_id) {
		this.user_group_id = user_group_id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public void setIdn_user(BigDecimal idn_user) {
		this.idn_user = idn_user;
	}

	@PostConstruct
    public void init() {
		TreeMap<String, Object> map = service.getUserType((String) SecurityUtils.getSubject().getPrincipal());
		idn_user = (BigDecimal)map.get("IDN_USER");
		user_type = (String)map.get("TYPE_CODE");
		idn_user_group = (BigDecimal)map.get("IDN_USER_GROUP");
		type_desc = (String)map.get("TYPE_DESC");
	    user_name_db = (String)map.get("USER_NAME_DB");
	    user_group_id = (String)map.get("USER_GROUP_ID");
	}
}
