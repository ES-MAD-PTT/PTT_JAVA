package com.atos.mapper.nominations;

import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

import com.atos.beans.ComboFilter;
import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.NominationBean;
import com.atos.beans.nominations.NominationDeadlineBean;
import com.atos.beans.nominations.OperationFileBean;
import com.atos.filters.nominations.IntermediateSubmissionFileFilter;
import com.atos.filters.nominations.ShipperSubmissionFileFilter;

public interface ShipperSubmissionNominationsMapper {

	public List<ComboFilterNS> selectContractCodeDayByUser(ShipperSubmissionFileFilter filter);

	public List<ComboFilterNS> selectContractCodeWeekByUser(ShipperSubmissionFileFilter filter);
	
	public List<BigDecimal> selectIdnUserGroup(ShipperSubmissionFileFilter filter);
	
	public List<ComboFilter> selectShipperIdNominations(IntermediateSubmissionFileFilter filter);

	public List<ComboFilterNS> selectContractCodeByShipper(IntermediateSubmissionFileFilter filter);
	
	public List<String> selectStartDayOfWeek();

	public List<BigDecimal> selectOperationCategory(String type);
	
	public List<BigDecimal> selectOperationTerm(String type);

	public List<BigDecimal> selectOperation(TreeMap<String,BigDecimal> map);
	
	public List<NominationBean> selectIdnNomination(NominationBean bean);
	
	public int insertOperationFile(OperationFileBean bean);
	
	public int insertNewNomination(NominationBean bean);
	
	public int insertVersionNomination(NominationBean bean);
	
	public NominationDeadlineBean selectNominationDeadlines(NominationDeadlineBean ndlb);
}
