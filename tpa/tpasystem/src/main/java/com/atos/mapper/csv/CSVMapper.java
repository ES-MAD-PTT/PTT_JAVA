package com.atos.mapper.csv;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.atos.beans.csv.AllocationReportCSVBean;
import com.atos.beans.csv.ContractQueryCSVBean;
import com.atos.beans.csv.DailyNominationCSVBean;
import com.atos.beans.csv.TariffOveruseCSVBean;
import com.atos.beans.csv.WeeklyNominationCSVBean;

public interface CSVMapper {

	public List<DailyNominationCSVBean> getCSVDailyNomination(HashMap<String,Object> map);
	
	public List<AllocationReportCSVBean> getCSVAllocationReport(HashMap<String,Object> map);
	
	public List<TariffOveruseCSVBean> getCSVTariffOveruse(HashMap<String,Object> map);

	public List<WeeklyNominationCSVBean> getCSVWeeklyNomination(HashMap<String,Object> map);

	public List<ContractQueryCSVBean> getCSVContractQuery(HashMap<String,Object> map, RowBounds rowBound);

	public List<BigDecimal> getCountCSVContractQuery(HashMap<String,Object> map);
	
}
