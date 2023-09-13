package com.atos.services.dam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.AreaQualityPerShipperBean;
import com.atos.beans.dam.AreaQualityPerShipperDetailsBean;
import com.atos.beans.dam.EmailNotificationManagementBean;
import com.atos.beans.dam.ZoneBean;
import com.atos.filters.dam.AreaQualityPerShipperFilter;
import com.atos.filters.dam.EmailNotificationManagementFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.AreaQualityPerShipperMapper;
import com.atos.mapper.dam.EmailNotificationManagementMapper;
import com.atos.utils.Constants;


@Service("EmailNotificationManagementService")
public class EmailNotificationManagementServiceImpl implements EmailNotificationManagementService{

	
	private static final long serialVersionUID = 5738619896981240370L;
		
		@Autowired
		private EmailNotificationManagementMapper emailNotManMapper;
		
		public Map<BigDecimal, Object> selectModulesIds(){
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
			List<ComboFilterNS> list = emailNotManMapper.selectModulesIds();
			for (ComboFilterNS combo : list) {
				if (combo == null)
					continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map;
		}
		
		public List<EmailNotificationManagementBean> selectEmails(EmailNotificationManagementFilter filter) {
			return emailNotManMapper.selectEmails(filter);
		}
		
		@Transactional( rollbackFor = { Throwable.class })
		public String insertLogAndUpdateEmail(EmailNotificationManagementBean bean) throws Exception {
			int res= emailNotManMapper.insertLogEmail(bean);
			if(res!=1){
				throw new Exception("-1");
			}
			int res2 = emailNotManMapper.updateEmailEnabled(bean);
			if(res2!=1){
				throw new Exception("-1");
			}
			return "0";
		}
}
