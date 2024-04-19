package com.atos.mapper.quality;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.quality.OffSpecActionBean;
import com.atos.beans.quality.OffSpecActionFileBean;
import com.atos.beans.quality.OffSpecFileAttachBean;
import com.atos.beans.quality.OffSpecFileBean;
import com.atos.beans.quality.OffSpecGasQualityParameterBean;
import com.atos.beans.quality.OffSpecIncidentBean;
import com.atos.beans.quality.OffSpecResponseBean;
import com.atos.beans.quality.OffSpecStatusBean;
import com.atos.beans.quality.OffSpecStatusRuleBean;
import com.atos.filters.quality.OffSpecGasReportManagementFilter;

public interface OffSpecGasReportManagementMapper extends Serializable {

	public List<ComboFilterNS> selectIncidentTypes();
	public BigDecimal selectIncidentTypeIdFromCode(String incidentTypeCode);
	public List<ComboFilterNS> selectQualityPoints(BigDecimal systemId);
	public String selectPointCodeFromId(BigDecimal pointId);
	public List<ComboFilterNS> selectQualityPointsForInsert(BigDecimal systemId);
	public List<ComboFilterNS> selectShipperId();
	public List<OffSpecActionBean> selectAllActions(@Param("isShipper") Boolean isShipper);
	public List<ComboFilterNS> selectShipperIdForInsert();
	public List<BigDecimal> selectGroupIdFromGroupCode(String groupCode);
	public List<OffSpecStatusBean> selectStatusIds(OffSpecGasReportManagementFilter filter);
	public List<BigDecimal> selectStatusIdFromStatusCodes();
	public List<BigDecimal> selectStatusIdFromStatusCode(String status);
	public List<OffSpecStatusRuleBean> selectStatusRulesByUserId(String userId);
	public List<OffSpecIncidentBean> selectIncidentFromId(BigDecimal incidId);    
	public List<OffSpecIncidentBean> selectIncidents(OffSpecGasReportManagementFilter filter);
	public List<OffSpecIncidentBean> selectIncidentsToRespond(OffSpecGasReportManagementFilter filter);
	public List<OffSpecGasQualityParameterBean> selectGasQualityParameters();
	public List<OffSpecFileBean> selectFiles(OffSpecIncidentBean item);
	public List<OffSpecActionFileBean> selectActionFiles(OffSpecIncidentBean item);
	public String selectCommentsShipperOperator(OffSpecIncidentBean item);
	// En params, se esperan los siguientes datos para filtrar: incidentId y shipperId.
	public List<OffSpecResponseBean> selectDiscloseResponsesFromIncidentId(Map<String, BigDecimal> params);
	public List<OffSpecIncidentBean> getFileByOffSpecLogId(BigDecimal logId);
	public int insertOffSpecIncident(OffSpecIncidentBean osi);
	public int updateOffSpecIncident(OffSpecIncidentBean osi);
	public int insertOffSpecIncidentLog(OffSpecIncidentBean osi);
	public int insertGasQualityParameter(OffSpecGasQualityParameterBean osgqp);
	public int insertOffSpecResponse(OffSpecResponseBean osr);
	public int insertFileNewEvent(OffSpecFileBean file);
	public int insertFileAttachNewEvent(OffSpecFileAttachBean fileAttach);
	public int insertFileAction(OffSpecActionFileBean fileAction);
	public int updateActionOffspec(OffSpecIncidentBean item);
	
	public OffSpecResponseBean selectResponseFile(OffSpecResponseBean bean);
	//CH706
	public int updateTransporterComments(OffSpecResponseBean response);
	public String getZoneCode(OffSpecIncidentBean incident);
}
