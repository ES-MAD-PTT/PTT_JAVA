package com.atos.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface MailService extends Serializable{

	public void sendSimpleMessage(List<String> to, String subject, String body);
	
	public void sendEmail(String type_code, HashMap<String,String> values, BigDecimal idn_system, BigDecimal idn_user_group);
}
