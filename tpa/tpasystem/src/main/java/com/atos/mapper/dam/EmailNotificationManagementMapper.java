package com.atos.mapper.dam;

import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.EmailNotificationManagementBean;
import com.atos.filters.dam.EmailNotificationManagementFilter;

public interface EmailNotificationManagementMapper {

	public List<ComboFilterNS>selectModulesIds();
	public List<EmailNotificationManagementBean> selectEmails(EmailNotificationManagementFilter filter);
	public int insertLogEmail(EmailNotificationManagementBean bean);
	public int updateEmailEnabled(EmailNotificationManagementBean bean);
}
