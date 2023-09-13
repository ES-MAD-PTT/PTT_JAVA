package com.atos.services.nominations;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.nominations.ExportSimoneBean;
import com.atos.filters.nominations.ExportSimoneFilter;

public interface ExportSimoneService extends Serializable{
	
	public List<ExportSimoneBean> selectExportSimone(ExportSimoneFilter filter);
	
}
