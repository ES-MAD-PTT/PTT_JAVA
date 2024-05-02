package com.atos.services.quality;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.primefaces.model.StreamedContent;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.UserBean;
import com.atos.beans.quality.OffSpecIncidentBean;
import com.atos.beans.quality.OffSpecResponseBean;
import com.atos.beans.quality.OffSpecStatusBean;
import com.atos.filters.quality.OffSpecGasReportManagementFilter;

public interface OffSpecGasReportResponseService extends Serializable {

	public List<ComboFilterNS> selectIncidentTypes();
	public BigDecimal selectIncidentTypeIdFromCode(String incidentTypeCode);
	public List<BigDecimal> getDisclosedStatusIds() throws Exception;
	//public BigDecimal getDisclosedStatusIdNewFlow() throws Exception;
	public Map<BigDecimal, Object> selectQualityPoints(BigDecimal systemId);
	public Map<BigDecimal, Object> selectShipperId();
	public Map<BigDecimal, Object> selectShipperAction(OffSpecIncidentBean item);
	public List<ComboFilterNS> getAllShippers();
	public List<OffSpecStatusBean> selectStatusIds(OffSpecGasReportManagementFilter filter);
	public List<OffSpecIncidentBean> search(OffSpecGasReportManagementFilter filter);
	public StreamedContent getResponseFile(OffSpecResponseBean bean) throws Exception;
	public void saveResponse(OffSpecIncidentBean _incid, UserBean _user, List <BigDecimal> _disclosedStatusId) throws Exception;
}
