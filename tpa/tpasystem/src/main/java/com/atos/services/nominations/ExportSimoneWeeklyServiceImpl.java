package com.atos.services.nominations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.nominations.ExportSimoneWeeklyBean;
import com.atos.filters.nominations.ExportSimoneWeeklyFilter;
import com.atos.mapper.nominations.ExportSimoneWeeklyMapper;

@Service("exportSimoneWeeklyService")
public class ExportSimoneWeeklyServiceImpl implements ExportSimoneWeeklyService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6008063454154512438L;
		
	@Autowired
	private ExportSimoneWeeklyMapper exportMapper;


	
	@Override
	public List<ExportSimoneWeeklyBean> selectExportWeeklySimone(ExportSimoneWeeklyFilter filter) {
		return exportMapper.selectExportWeeklySimone(filter);
	}

	
}
