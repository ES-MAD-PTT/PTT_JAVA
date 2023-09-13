package com.atos.mapper.nominations;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.nominations.ExportSimoneBean;
import com.atos.filters.nominations.ExportSimoneFilter;

public interface ExportSimoneMapper extends Serializable{
	
	

	public List<ExportSimoneBean> selectExportSimone(ExportSimoneFilter filter);
	


}
