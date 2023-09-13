package com.atos.services;

import java.util.List;
import java.util.TreeMap;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.MenuBean;
import com.atos.mapper.MenuMapper;
import com.atos.utils.Constants;

@Service("menuService")
public class MenuServiceImpl implements MenuService {

	/**
	* 
	*/
	private static final long serialVersionUID = 8994968494994503856L;
	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<MenuBean> getPages(TreeMap<String, String> paramMap) {
		return menuMapper.getPages(paramMap);
	}
	@Override
	public List<MenuBean> getMenus() {
		return menuMapper.getMenus();
	}
	


}
