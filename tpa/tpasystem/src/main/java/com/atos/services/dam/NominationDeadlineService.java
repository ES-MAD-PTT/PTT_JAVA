package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.NominationDeadlineBean;
import com.atos.filters.dam.NominationDeadlineFilter;

public interface NominationDeadlineService extends Serializable {
	public List<NominationDeadlineBean> selectNominationDeadlines(NominationDeadlineFilter filter);

	public Map<BigDecimal, Object> selectTypes();

	public Map<BigDecimal, Object> selectDeadlineType();

	public SystemParameterBean getSystemParameter(String str);

	public String updateNominationDeadline(NominationDeadlineBean nominationDeadline) throws Exception;

	public String insertNominationDeadline(NominationDeadlineBean nominationDeadline) throws Exception;

	public String deleteNominationDeadline(NominationDeadlineBean nominationDeadline) throws Exception;

	public String validateNominationOrders(NominationDeadlineBean nominationDeadline) throws Exception;

}
