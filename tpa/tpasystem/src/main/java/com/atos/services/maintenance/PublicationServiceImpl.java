package com.atos.services.maintenance;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.maintenance.WorkSubmissionBean;
import com.atos.mapper.maintenance.MaintenanceMapper;

@Service("publicationService")
public class PublicationServiceImpl implements PublicationService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1816524511870808127L;

	@Autowired
	private MaintenanceMapper maintenanceMapper;
	
	@Override
	public List<WorkSubmissionBean> getAllEngineeringMaintenance(BigDecimal idnActive) {
		return maintenanceMapper.getAllEngineeringMaintenance(idnActive);
	}

}
