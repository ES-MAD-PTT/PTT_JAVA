
package com.atos.services.quality;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.LanguageBean;
import com.atos.beans.UserBean;
import com.atos.beans.quality.OffSpecActionBean;
import com.atos.beans.quality.OffSpecActionFileBean;
import com.atos.beans.quality.OffSpecFileBean;
import com.atos.beans.quality.OffSpecGasQualityParameterBean;
import com.atos.beans.quality.OffSpecIncidentBean;
import com.atos.beans.quality.OffSpecResponseBean;
import com.atos.beans.quality.OffSpecStatusBean;
import com.atos.beans.quality.OffSpecStatusRuleBean;
import com.atos.filters.quality.OffSpecGasReportManagementFilter;

public interface OffSpecGasReportManagementService extends Serializable {

	public List<ComboFilterNS> selectIncidentTypes();
	public Map<BigDecimal, Object> selectQualityPoints(BigDecimal systemId);
	public Map<BigDecimal, Object> selectQualityPointsForInsert(BigDecimal systemId);
	public Map<BigDecimal, Object> selectShipperId();
	public Map<BigDecimal, Object> selectShipperIdForInsert();
	public List<OffSpecActionBean> selectAllActions();
	public List<OffSpecStatusBean> selectStatusIds(OffSpecGasReportManagementFilter filter);
	public List<OffSpecStatusRuleBean> selectStatusRules(String userId);
	public List<OffSpecGasQualityParameterBean> selectGasQualityParameters();
	public List<OffSpecIncidentBean> search(OffSpecGasReportManagementFilter filter, UserBean _user);
	public List<OffSpecFileBean> selectFiles(OffSpecIncidentBean item);
	public List<OffSpecActionFileBean> selectActionFiles(OffSpecIncidentBean item);
	public String selectCommentsShipperOperator(OffSpecIncidentBean item);
	public void changeStatus(OffSpecIncidentBean incident, UserBean _user, LanguageBean _lang) throws Exception;
	public void sendNewStatusNotification(OffSpecIncidentBean _incid, UserBean _user, LanguageBean _lang, BigDecimal _systemId) throws Exception;
	public void createRequest(OffSpecIncidentBean _incid, UserBean _user, LanguageBean _lang) throws Exception;
	public void createEvent(OffSpecIncidentBean _incid, UserBean _user, LanguageBean _lang) throws Exception;
	public void sendNewRequestNotification(OffSpecIncidentBean _incid, UserBean _user, LanguageBean _lang, BigDecimal _systemId) throws Exception;
	public void sendNewEventNotification(OffSpecIncidentBean _incid, UserBean _user, LanguageBean _lang, BigDecimal _systemId) throws Exception;
	public List<BigDecimal> getNotifRecipients(String _notifMode,BigDecimal _origShipperId,UserBean _user,LanguageBean _lang) throws Exception;
	public BigDecimal getDefaultOperatorId(UserBean _user, LanguageBean _lang) throws Exception;
	public void getFileResponse(OffSpecResponseBean incident) throws Exception;
	public void getFile(OffSpecIncidentBean incident) throws Exception;
	public String getZoneCode(OffSpecIncidentBean incident);
	
	public Integer saveAction(OffSpecIncidentBean _incid, UserBean _user, boolean isShipper) throws Exception;
	
	//CH706
	public String updateTransporterComments(OffSpecResponseBean response) throws Exception;
}
