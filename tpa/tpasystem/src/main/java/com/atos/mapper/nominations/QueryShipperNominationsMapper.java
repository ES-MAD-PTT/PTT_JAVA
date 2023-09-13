package com.atos.mapper.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.OperationFileBean;
import com.atos.beans.nominations.QueryShipperNominationFileBean;
import com.atos.filters.nominations.QueryShipperNominationFileFilter;

public interface QueryShipperNominationsMapper extends Serializable{

	public List<ComboFilterNS> selectShipperIdNominations(QueryShipperNominationFileFilter filter);

	public List<ComboFilterNS> selectContractCodeDayByShipper(QueryShipperNominationFileFilter filter);
	
	public List<ComboFilterNS> selectContractCodeWeekByShipper(QueryShipperNominationFileFilter filter);
	
	public List<QueryShipperNominationFileBean> selectQueryNomination(QueryShipperNominationFileFilter filter);

	public List<OperationFileBean> selectGetFile(TreeMap<String,BigDecimal> map);
	
}
