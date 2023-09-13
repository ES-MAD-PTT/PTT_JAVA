package com.atos.services;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

public interface SystemService extends Serializable{

	public List<TreeMap<String, Object>> getSystems();
	public boolean userHasOnshoreProfile(String userName);	
}
