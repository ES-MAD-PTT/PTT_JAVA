package com.atos.services.nominations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.nominations.ExportSimoneBean;
import com.atos.filters.nominations.ExportSimoneFilter;
import com.atos.mapper.nominations.ExportSimoneMapper;

@Service("exportSimoneService")
public class ExportSimoneServiceImpl implements ExportSimoneService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6008063454154512438L;
		
	@Autowired
	private ExportSimoneMapper exportMapper;


	
	@Override
	public List<ExportSimoneBean> selectExportSimone(ExportSimoneFilter filter) {
		return exportMapper.selectExportSimone(filter);
	}

	
}
