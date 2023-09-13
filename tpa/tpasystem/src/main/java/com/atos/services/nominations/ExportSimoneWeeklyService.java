package com.atos.services.nominations;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.nominations.ExportSimoneWeeklyBean;
import com.atos.filters.nominations.ExportSimoneWeeklyFilter;

public interface ExportSimoneWeeklyService extends Serializable{
	
	public List<ExportSimoneWeeklyBean> selectExportWeeklySimone(ExportSimoneWeeklyFilter filter);
	
}
