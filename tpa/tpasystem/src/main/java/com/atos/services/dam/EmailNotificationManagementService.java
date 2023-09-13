package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.AreaNominalCapacityBean;
import com.atos.beans.dam.AreaQualityPerShipperBean;
import com.atos.beans.dam.AreaQualityPerShipperDetailsBean;
import com.atos.beans.dam.EmailNotificationManagementBean;
import com.atos.beans.dam.ZoneBean;
import com.atos.filters.dam.AreaQualityPerShipperFilter;
import com.atos.filters.dam.EmailNotificationManagementFilter;
import com.atos.filters.dam.ZoneFilter;

public interface EmailNotificationManagementService extends Serializable {

	public  Map<BigDecimal, Object> selectModulesIds();
	public List<EmailNotificationManagementBean> selectEmails(EmailNotificationManagementFilter filter);
	public String insertLogAndUpdateEmail(EmailNotificationManagementBean bean) throws Exception;
	
}
