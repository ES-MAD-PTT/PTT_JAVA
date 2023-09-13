package com.atos.mapper;

import java.util.List;
import java.util.TreeMap;

import com.atos.beans.MenuBean;

public interface MenuMapper {

	public List<MenuBean> getPages(TreeMap<String, String> paramMap);

	public List<MenuBean> getMenus();
}
