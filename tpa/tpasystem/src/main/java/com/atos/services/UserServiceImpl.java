package com.atos.services;

import java.util.List;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.mapper.UserMapper;

@Service("userService")
public class UserServiceImpl implements UserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8457323231809057581L;
	private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public TreeMap<String, Object> getUserType(String user_id) {
		TreeMap<String, Object> map = null;
		List<TreeMap<String, Object>> list = userMapper.getUserType(user_id);
		if(list==null || list.size()==0){
			log.error("Error searching for user data: " + user_id);
		} else {
			map = list.get(0);
		}
		return map;
	}
	
}
