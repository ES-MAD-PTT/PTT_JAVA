package com.atos.views;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.atos.beans.LanguageBean;
import com.atos.beans.MenuBean;
import com.atos.services.MenuService;
import com.atos.services.dam.PimsGraphicMenuService;
import com.atos.utils.Constants;
import com.atos.views.dam.PimsGraphicMenuView;

@ManagedBean(name="menuView")
@SessionScoped
public class MenuView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2232237138895980734L;

	private static final Logger log = LogManager.getLogger(MenuView.class);
	
	private MenuModel model;
	private MenuModel onshore_model, offshore_model;

    @ManagedProperty("#{languageBean}")
    private LanguageBean language;
     
    public void setLanguage(LanguageBean language) {
        this.language = language;
    }

    @ManagedProperty("#{changeSystemView}")
    private ChangeSystemView system;
     
    public void setSystem(ChangeSystemView system) {
		this.system = system;
	}

	@ManagedProperty("#{menuService}")
    transient private MenuService service;

    public void setService(MenuService service) {
		this.service = service;
	}

	@ManagedProperty("#{pimsGraphicMenuService}")
    transient private PimsGraphicMenuService servicePmisGraphicMenu;

    public void setServicePmisGraphicMenu(PimsGraphicMenuService servicePmisGraphicMenu) {
		this.servicePmisGraphicMenu = servicePmisGraphicMenu;
	}

	@PostConstruct
    public void init() {
    	ResourceBundle b = ResourceBundle.getBundle("messages", new Locale(this.language.getLocale())); 
    	
       // model = new DefaultMenuModel();
        
        onshore_model = this.buildMenu(Constants.ONSHORE,b);
        if(onshore_model==null){
        	onshore_model = new DefaultMenuModel();
        }
        
        offshore_model = this.buildMenu(Constants.OFFSHORE,b);
        if(offshore_model==null){
        	offshore_model = new DefaultMenuModel();
        }

    }
    
	public MenuModel buildMenu(String system, ResourceBundle b){
		
		MenuModel newmodel= new DefaultMenuModel();
		
		List<MenuBean> menus = this.service.getMenus();
		if(menus.size()==0){
			return null;
		}
		TreeMap<String, String> paramMap = new TreeMap<String, String>();
		paramMap.put("system", system);
		paramMap.put("user", (String)SecurityUtils.getSubject().getPrincipal());
		List<MenuBean> list = this.service.getPages(paramMap);
		if(list.size()==0){
			return null;
		}
		
		TreeMap<String,MenuBean> mapa_menu = new TreeMap<String,MenuBean>();
		for(int i=0;i<menus.size();i++){
			mapa_menu.put(menus.get(i).getPosition(), menus.get(i));
		}
		
		TreeMap<String,Object> menu_final = new TreeMap<String,Object>();
		for(int i=0;i<list.size();i++){
			MenuBean option = list.get(i);
			String positions = option.getPosition();
			String[] pos =  positions.split("\\.");
			String buildPos = "", prev_buildPos=""; 
			for(int j=0;j<pos.length;j++){
				prev_buildPos = buildPos;
				buildPos = (j==0 ? pos[j] : buildPos + "." + pos[j]); 
				if(!menu_final.containsKey(buildPos)){
					MenuBean m = mapa_menu.get(buildPos);
					if(m!=null){
						if(m.getIdn_page()==null){
							DefaultSubMenu submenu = new DefaultSubMenu(getLabel(b,m.getI18n()));
							DefaultSubMenu prev_submenu = (DefaultSubMenu)menu_final.get(prev_buildPos);
							if(prev_submenu!=null){
								prev_submenu.addElement(submenu);
								menu_final.put(buildPos, submenu);
							} else {
								newmodel.addElement(submenu);
								menu_final.put(buildPos, submenu);
							}
						}
					/*	if(m.getIdn_page()!=null){
							DefaultMenuItem item = new DefaultMenuItem(b.getString(m.getI18n()));
							item.setUrl(m.getUrl());
							DefaultSubMenu prev_submenu = (DefaultSubMenu)menu_final.get(prev_buildPos);
							if(prev_submenu!=null){
								prev_submenu.addElement(item);
								menu_final.put(buildPos, item);
							} else {
								menu_final.put(buildPos, item);
							}
						}*/
						
					}
				}
			}
			DefaultMenuItem item = new DefaultMenuItem(getLabel(b,option.getI18n()));
			item.setUrl(option.getUrl());
			DefaultSubMenu prev_submenu = (DefaultSubMenu)menu_final.get(prev_buildPos);
			prev_submenu.addElement(item);
			menu_final.put(option.getPosition(), option);
		}
		String url = servicePmisGraphicMenu.selectPmisGraphicMenusValue();
		DefaultMenuItem item = new DefaultMenuItem(getLabel(b,"menu_pmis_graphics"));
		item.setUrl(url);
		newmodel.addElement(item);
		menu_final.put("999", item);
		

		return newmodel;
	}

    
    public MenuModel getModel() {
    	String s = system.getSystem();
    	if(s.equals(Constants.ONSHORE)){
    		model = onshore_model;
    	} else {
    		model = offshore_model;
    	}
        return model;
    }   
     	
    private String getLabel(ResourceBundle b, String value) {
    	try {
    		String label = b.getString(value);
    		return label;
    	} catch(Exception e) {
    		log.catching(e);
    	}
    	return value;
    }
    
}
