package com.atos.mapper.maintenance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.maintenance.MaintenanceFileBean;
import com.atos.beans.maintenance.WorkOperatorMaintenanceBean;
import com.atos.beans.maintenance.WorkSubmissionBean;
import com.atos.filters.maintenance.WorkMaintenanceFilter;

public interface MaintenanceMapper extends Serializable{

	public List<ComboFilterNS> getMaintenanceCode();
	
	public List<ComboFilterNS> getMaintenanceArea(BigDecimal idnActive);

	public List<ComboFilterNS> getMaintenanceSubarea(WorkMaintenanceFilter filter);

	public List<String> getMaintenanceCodeSecuential(String query);
	
	public List<WorkSubmissionBean> getAllEngineeringMaintenance(BigDecimal idnActive);
	
	public List<WorkSubmissionBean> getEngineeringMaintenance(WorkMaintenanceFilter filter);
	
	public int insertEngineeringMaintenance(WorkSubmissionBean bean);
	
	public int insertOperationsMaintenance(WorkOperatorMaintenanceBean bean);

	public int updateStatusMaintenance(WorkSubmissionBean bean);
	
	public int updateEngineeringMaintenance1(WorkSubmissionBean bean);
	
	public int updateActualEndDateMaintenance(WorkSubmissionBean bean);
	
	public int updateCssColorMaintenance(WorkSubmissionBean bean);
	
	public int updateEngineeringMaintenance2(WorkOperatorMaintenanceBean bean);

	public int updateOperationsCommentMaintenance(WorkSubmissionBean bean);

	public int updateOperationsMaintenance(WorkOperatorMaintenanceBean bean);
	
	public int insertMaintenanceFile(MaintenanceFileBean bean);
	
	public List<MaintenanceFileBean> selectGetFile(TreeMap<String,BigDecimal> map);
	
}
