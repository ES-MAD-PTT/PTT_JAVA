package com.atos.services.maintenance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.primefaces.model.StreamedContent;

import com.atos.beans.FileBean;
import com.atos.beans.maintenance.WorkSubmissionBean;
import com.atos.filters.maintenance.WorkMaintenanceFilter;

public interface WorkSubmissionService extends Serializable{

	public Map<String, Object> getMaintenanceCode();
	
	public Map<String, Object> getMaintenanceArea(BigDecimal idnActive);

	public Map<String, Object> getMaintenanceSubarea(WorkMaintenanceFilter filter);

	public List<WorkSubmissionBean> getEngineeringMaintenance(WorkMaintenanceFilter filter);
	
	public String insertEngineeringMaintenance(WorkSubmissionBean bean, FileBean file, BigDecimal _systemId) throws Exception;

	public String updateEngineeringMaintenance(WorkSubmissionBean bean, FileBean file) throws Exception;
	
	public String deleteEngineeringMaintenance(WorkSubmissionBean bean) throws Exception;
	
	public StreamedContent getFile(TreeMap<String,BigDecimal> map);

}
