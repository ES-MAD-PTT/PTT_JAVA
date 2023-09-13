package com.atos.services.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.primefaces.model.StreamedContent;

import com.atos.beans.nominations.QueryShipperNominationFileBean;
import com.atos.filters.nominations.QueryShipperNominationFileFilter;

public interface QueryShipperWeeklyNominationFileService extends Serializable{

	public Map<String, Object> selectContractCodeByUser(QueryShipperNominationFileFilter filter);
	
	public Map<String, Object> selectShipperIdNominations(QueryShipperNominationFileFilter filter);

	public List<QueryShipperNominationFileBean> selectQueryNomination(QueryShipperNominationFileFilter filter);
	
	public StreamedContent getFile(TreeMap<String,BigDecimal> map);
	
	public int selectStartDayOfWeek();
}
