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

import com.atos.beans.csv.WeeklyNominationCSVBean;
import com.atos.beans.nominations.ExportSimoneWeeklyBean;
import com.atos.filters.nominations.ExportSimoneWeeklyFilter;
import com.atos.mapper.SystemMapper;
import com.atos.mapper.csv.CSVMapper;
import com.atos.mapper.nominations.ExportSimoneWeeklyMapper;

@Component
public class CSVWeeklyGeneration  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4152000942038567057L;
	private static final Logger log = LogManager.getLogger(CSVWeeklyGeneration.class);

	@Autowired
	private CSVMapper csvMapper;

	@Autowired
	private SystemMapper systemMapper;

	@Autowired
	private ExportSimoneWeeklyMapper exportSimoneMapper;

	@Autowired
	private HashMap<String, String> parameterMap;

	public void generateCSV() {
		
		log.info("Job generateWeeklyCSV is running", Calendar.getInstance().getTime());
		
		// 6 weekly nomination
		csvWeeklyNomination();

		// 8 export simone weekly
		csvExportSimoneWeekly();
		log.info("Job generateWeeklyCSV finish", Calendar.getInstance().getTime());

	}
	
	
	
	private void csvWeeklyNomination() {
		log.info("Start generation CSV weekly nomination", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		
		Calendar today = Calendar.getInstance();
		today.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		today.set(Calendar.HOUR_OF_DAY,0);
		today.set(Calendar.MINUTE,0);
		today.set(Calendar.SECOND,0);
		today.set(Calendar.MILLISECOND, 0);

		Date start_date = today.getTime();
		map.put("start_date", start_date);
		today.add(Calendar.DAY_OF_MONTH, 6);
		map.put("end_date", today.getTime());
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			map.put("idn_system", list_onshore.get(0));
		}
		
		List<WeeklyNominationCSVBean> list = csvMapper.getCSVWeeklyNomination(map);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "weeklyNomination", sdf.format(start_date));
		
		log.info("End generation CSV weekly nomination", Calendar.getInstance().getTime());

	}

	private void csvExportSimoneWeekly() {
		log.info("Start generation CSV export simone weekly", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ExportSimoneWeeklyFilter filter = new ExportSimoneWeeklyFilter();
		
		Calendar c=Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND, 0);
		
		filter.setStart_date(c.getTime());
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			filter.setIdn_pipeline_system(list_onshore.get(0));
		}
		

		List<ExportSimoneWeeklyBean> list = exportSimoneMapper.selectExportWeeklySimone(filter);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "exportSimoneWeekly", sdf.format(c.getTime()));
		
		log.info("End generation CSV export simone Weekly", Calendar.getInstance().getTime());

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
