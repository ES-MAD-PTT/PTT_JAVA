package com.atos.services;

import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.mapper.SystemMapper;

@Service("systemService")
public class SystemServiceImpl implements SystemService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1160827968870410442L;
	
	@Autowired
	private SystemMapper systemMapper;
	
	@Override
	public List<TreeMap<String, Object>> getSystems() {
		return systemMapper.getSystems();
	}

	@Override
	public boolean userHasOnshoreProfile(String userName){
		return (systemMapper.userHasOnshoreProfile(userName)!=0);
	}
}
