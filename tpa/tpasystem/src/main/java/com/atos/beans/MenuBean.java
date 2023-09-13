package com.atos.beans;

import java.io.Serializable;

public class MenuBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4975648900432543634L;

	private String system;
	private Integer idn_page;
	private Integer idn_menu;
	private String i18n;
	private String url;
	private String position;
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public Integer getIdn_page() {
		return idn_page;
	}
	public void setIdn_page(Integer idn_page) {
		this.idn_page = idn_page;
	}
	public Integer getIdn_menu() {
		return idn_menu;
	}
	public void setIdn_menu(Integer idn_menu) {
		this.idn_menu = idn_menu;
	}
	public String getI18n() {
		return i18n;
	}
	public void setI18n(String i18n) {
		this.i18n = i18n;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	@Override
	public String toString() {
		return "MenuBean [system=" + system + ", idn_page=" + idn_page + ", idn_menu=" + idn_menu + ", i18n=" + i18n
				+ ", url=" + url + ", position=" + position + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((i18n == null) ? 0 : i18n.hashCode());
		result = prime * result + ((idn_menu == null) ? 0 : idn_menu.hashCode());
		result = prime * result + ((idn_page == null) ? 0 : idn_page.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((system == null) ? 0 : system.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuBean other = (MenuBean) obj;
		if (i18n == null) {
			if (other.i18n != null)
				return false;
		} else if (!i18n.equals(other.i18n))
			return false;
		if (idn_menu == null) {
			if (other.idn_menu != null)
				return false;
		} else if (!idn_menu.equals(other.idn_menu))
			return false;
		if (idn_page == null) {
			if (other.idn_page != null)
				return false;
		} else if (!idn_page.equals(other.idn_page))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (system == null) {
			if (other.system != null)
				return false;
		} else if (!system.equals(other.system))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
