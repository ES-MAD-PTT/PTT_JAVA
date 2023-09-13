package com.atos.mapper;

import java.util.List;
import java.util.TreeMap;

public interface UserMapper {

	public List<TreeMap<String, Object>> getUserType(String user_id);
	
}
