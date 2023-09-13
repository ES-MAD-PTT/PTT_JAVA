package com.atos.services.maintenance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.maintenance.WorkSubmissionBean;

public interface PublicationService extends Serializable{

	public List<WorkSubmissionBean> getAllEngineeringMaintenance(BigDecimal idnActive);
	
}
