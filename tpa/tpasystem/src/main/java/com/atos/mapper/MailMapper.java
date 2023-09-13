package com.atos.mapper;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.atos.beans.MailTypeBean;

public interface MailMapper {

	public List<com.atos.beans.MailServerBean> getEmailServer(Date start_date);
	
	public List<String> getSendEmails(MailTypeBean bean);
	
	public List<MailTypeBean> getMailType(MailTypeBean bean);
	
	public List<String> getSubject(MailTypeBean bean);
}
