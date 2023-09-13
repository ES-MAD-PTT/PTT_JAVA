package com.atos.services;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

public interface UserService extends Serializable{

	public TreeMap<String, Object> getUserType(String user_id);
	
}
