package com.atos.mapper.nominations;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.nominations.ExportSimoneWeeklyBean;
import com.atos.filters.nominations.ExportSimoneWeeklyFilter;

public interface ExportSimoneWeeklyMapper extends Serializable{
	
	

	public List<ExportSimoneWeeklyBean> selectExportWeeklySimone(ExportSimoneWeeklyFilter filter);
	


}
