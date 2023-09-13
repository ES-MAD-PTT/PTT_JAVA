package com.atos.services;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.atos.beans.ComboFilter;
import com.atos.beans.MenuBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ShipperBean;
import com.atos.filters.dam.ShipperFilter;

public interface MenuService extends Serializable{

	public List<MenuBean> getMenus();

	public List<MenuBean> getPages(TreeMap<String, String> paramMap);
	
}
