package com.atos.quartz;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atos.beans.balancing.BalanceReportBean;
import com.atos.beans.csv.TariffOveruseCSVBean;
import com.atos.beans.tariff.TariffChargeReportBean;
import com.atos.filters.balancing.BalanceReportFilter;
import com.atos.filters.tariff.TariffChargeReportFilter;
import com.atos.mapper.SystemMapper;
import com.atos.mapper.balancing.BalanceReportMapper;
import com.atos.mapper.csv.CSVMapper;
import com.atos.mapper.tariff.TariffChargeReportMapper;
import com.atos.utils.DateUtil;

@Component
public class CSVMonthGeneration  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4152000942038567057L;
	private static final Logger log = LogManager.getLogger(CSVMonthGeneration.class);

	@Autowired
	private CSVMapper csvMapper;

	@Autowired
	private SystemMapper systemMapper;

	@Autowired
	private BalanceReportMapper bsrMapper;

	@Autowired
	private TariffChargeReportMapper tariffChargeReportMapper;

	@Autowired
	private HashMap<String, String> parameterMap;
	
	public void generateCSV() {
		
		log.info("Job generateMonthCSV is running", Calendar.getInstance().getTime());
		
		// 17 balance report
		csvBalanceReport();
		
		// 19 tariff charge report
		csvTariffChargeReport();
		
		// 20 tariff daily overview
		csvTariffDailyOverview();

		log.info("Job generateMonthCSV finish", Calendar.getInstance().getTime());

	}
	
	private void csvBalanceReport() {
		log.info("Start generation CSV balance report", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		BalanceReportFilter filter = new BalanceReportFilter();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MONTH,-1);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		filter.setFromDate(cal.getTime());
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			filter.setSystemId(list_onshore.get(0));
		}
		
		List<BalanceReportBean> list = bsrMapper.selectBalances(filter);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "balanceReport", sdf.format(cal.getTime()));
		
		log.info("End generation CSV balance report", Calendar.getInstance().getTime());

	}	
	
	private void csvTariffChargeReport() {
		log.info("Start generation CSV tariff charge report", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		TariffChargeReportFilter filter = new TariffChargeReportFilter();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MONTH,-1);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		filter.setShortDate(cal.getTime());
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			filter.setIdn_system(list_onshore.get(0));
		}
		
		List<TariffChargeReportBean> list = tariffChargeReportMapper.selectTariffChargeReports(filter);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "tariffChargeReport", sdf.format(cal.getTime()));
		
		log.info("End generation CSV tariff charge report", Calendar.getInstance().getTime());

	}	
	
	private void csvTariffDailyOverview() {
		log.info("Start generation CSV tariff daily overview", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		map.put("gas_day", cal.getTime());
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			map.put("idn_system", list_onshore.get(0));
		}
		
		List<TariffOveruseCSVBean> list = csvMapper.getCSVTariffOveruse(map);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "tariffDailyOverview", sdf.format(cal.getTime()));
		
		log.info("End generation CSV tariff daily overview", Calendar.getInstance().getTime());

	}


	private void writeFile(StringBuilder csv, String fileName, String today) {
		
		Path path_file = Paths.get(parameterMap.get("csv.folder")+ fileName +"_" + today +".csv");
		
		byte[] strToBytes = csv.toString().getBytes();

	    try {
			Files.write(path_file, strToBytes);
		} catch (IOException e) {
			log.error("Error writting "+ fileName +" file: " + e.getMessage(), Calendar.getInstance().getTime());
			e.printStackTrace();
		}
	    

	}
	
}
