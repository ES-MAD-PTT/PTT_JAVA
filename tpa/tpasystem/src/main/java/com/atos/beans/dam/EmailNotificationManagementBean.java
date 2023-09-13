package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import com.atos.beans.UserAudBean;

@XmlRootElement(name = "EmailNotificationManagementBean")
public class EmailNotificationManagementBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_email_type;
	private String module;
	private BigDecimal idn_module;
	private String activity;
	private BigDecimal idn_activity;
	private boolean enabled;
	private String strEnabled;
	private String user;

	public EmailNotificationManagementBean() {
		super();
	}

	public EmailNotificationManagementBean(BigDecimal idn_email_type, String module, BigDecimal idn_module,
			String activity, BigDecimal idn_activity, boolean enabled, String strEnabled, String user) {
		super();
		this.idn_email_type = idn_email_type;
		this.module = module;
		this.idn_module = idn_module;
		this.activity = activity;
		this.idn_activity = idn_activity;
		this.enabled = enabled;
		this.strEnabled = strEnabled;
		this.user = user;
	}

	public BigDecimal getIdn_email_type() {
		return idn_email_type;
	}

	public void setIdn_email_type(BigDecimal idn_email_type) {
		this.idn_email_type = idn_email_type;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public BigDecimal getIdn_module() {
		return idn_module;
	}

	public void setIdn_module(BigDecimal idn_module) {
		this.idn_module = idn_module;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public BigDecimal getIdn_activity() {
		return idn_activity;
	}

	public void setIdn_activity(BigDecimal idn_activity) {
		this.idn_activity = idn_activity;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getStrEnabled() {
		return strEnabled;
	}

	public void setStrEnabled(String strEnabled) {
		this.strEnabled = strEnabled;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
