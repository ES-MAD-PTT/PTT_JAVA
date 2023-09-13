package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.NominationDeadlineBean;
import com.atos.filters.dam.NominationDeadlineFilter;

public interface NominationDeadlineMapper {

	public List<NominationDeadlineBean> selectNominationDeadlines(NominationDeadlineFilter filters);

	public List<ComboFilterNS> selectTypes();

	public List<ComboFilterNS> selectDeadlineType();

	public int insertNominationDeadline(NominationDeadlineBean forecastingDeadline);

	public List<BigDecimal> getIdnOperationNomination(NominationDeadlineBean nominationDeadline);

	public List<NominationDeadlineBean> getValidateNominationOrders(NominationDeadlineBean nominationDeadline);

	public int deleteNominationDeadline(NominationDeadlineBean nominationDeadline); // Es update

	public Date getNominationDeadlineStarDate(NominationDeadlineBean nominationDeadline);

}
