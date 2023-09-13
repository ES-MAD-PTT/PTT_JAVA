package com.atos.services.maintenance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.primefaces.model.StreamedContent;

import com.atos.beans.maintenance.WorkOperatorMaintenanceBean;
import com.atos.beans.maintenance.WorkSubmissionBean;
import com.atos.filters.maintenance.WorkMaintenanceFilter;

public interface WorkUpdateService extends Serializable{

	public Map<String, Object> getMaintenanceCode();
	
	public Map<String, Object> getMaintenanceArea(BigDecimal idnActive);

	public Map<String, Object> getMaintenanceSubarea(WorkMaintenanceFilter filter);

	public List<WorkSubmissionBean> getEngineeringMaintenance(WorkMaintenanceFilter filter);
	
	public String updateOperationsCommentMaintenance(WorkSubmissionBean bean) throws Exception;
	
	public String updateOperationsPublishMaintenance(WorkSubmissionBean bean, BigDecimal _systemId) throws Exception;

	public String updateCssColorMaintenance(WorkSubmissionBean bean) throws Exception;
	
	public String deleteEngineeringMaintenance(WorkSubmissionBean bean) throws Exception;

	public String updateOperationsMaintenance(WorkOperatorMaintenanceBean bean) throws Exception;
	
	public String insertOperationsMaintenance(WorkOperatorMaintenanceBean bean, WorkSubmissionBean selected) throws Exception;

	public StreamedContent getFile(TreeMap<String,BigDecimal> map);

	public String updateActualEndDateMaintenance(WorkSubmissionBean bean) throws Exception;
}
