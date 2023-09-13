package com.atos.mapper.nominations;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.ShippersNominationsReportsBean;
import com.atos.filters.nominations.ShippersNominationsReportsFilter;

public interface ShippersNominationsReportsMapper extends Serializable {
	
	public List<ShippersNominationsReportsBean> selectShippersNomReports(ShippersNominationsReportsFilter filter);
	public List<ComboFilterNS>selectShippersIds();
}
