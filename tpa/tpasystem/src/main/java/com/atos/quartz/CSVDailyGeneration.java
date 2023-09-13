package com.atos.quartz;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.allocation.AllocationBean;
import com.atos.beans.allocation.AllocationQueryBean;
import com.atos.beans.allocation.AllocationReportDetailDto;
import com.atos.beans.allocation.AllocationReportDto;
import com.atos.beans.balancing.BalanceInProgressReportBean;
import com.atos.beans.balancing.BalanceIntradayReportBean;
import com.atos.beans.balancing.ShipperDailyStatusBean;
import com.atos.beans.booking.CapacityPublicationBean;
import com.atos.beans.booking.CapacityRequestBean;
import com.atos.beans.booking.ContractAttachmentBean;
import com.atos.beans.csv.AllocationReportCSVBean;
import com.atos.beans.csv.ContractQueryCSVBean;
import com.atos.beans.csv.DailyNominationCSVBean;
import com.atos.beans.dam.AreaNominalCapacityBean;
import com.atos.beans.metering.MeasurementBean;
import com.atos.beans.nominations.ExportSimoneBean;
import com.atos.beans.nominations.ParkingAllocationBean;
import com.atos.beans.nominations.QualityPublicationBean;
import com.atos.filters.allocation.AllocRepQryFilter;
import com.atos.filters.allocation.AllocationManagementFilter;
import com.atos.filters.allocation.AllocationQueryFilter;
import com.atos.filters.allocation.AllocationReviewSupplyEWFilter;
import com.atos.filters.balancing.BalanceInProgressReportFilter;
import com.atos.filters.balancing.BalanceIntradayReportFilter;
import com.atos.filters.balancing.ShipperDailyStatusFilter;
import com.atos.filters.booking.CRManagementFilter;
import com.atos.filters.booking.CapacityPublicationFilter;
import com.atos.filters.dam.AreaNominalCapacityFilter;
import com.atos.filters.metering.MeteringManagementFilter;
import com.atos.filters.nominations.ExportSimoneFilter;
import com.atos.filters.nominations.ParkingAllocationFilter;
import com.atos.filters.nominations.QualityPublicationFilter;
import com.atos.mapper.SystemMapper;
import com.atos.mapper.allocation.AllocationManagementMapper;
import com.atos.mapper.allocation.AllocationQueryMapper;
import com.atos.mapper.allocation.AllocationReportQryMapper;
import com.atos.mapper.allocation.AllocationReviewSupplyEWMapper;
import com.atos.mapper.balancing.BalanceInProgressReportMapper;
import com.atos.mapper.balancing.BalanceIntradayReportMapper;
import com.atos.mapper.balancing.ShipperDailyStatusMapper;
import com.atos.mapper.booking.CapacityPublicationMapper;
import com.atos.mapper.booking.CapacityRequestMapper;
import com.atos.mapper.booking.ContractQueryMapper;
import com.atos.mapper.csv.CSVMapper;
import com.atos.mapper.dam.AreaNominalCapacityMapper;
import com.atos.mapper.metering.MeteringManagementMapper;
import com.atos.mapper.nominations.ExportSimoneMapper;
import com.atos.mapper.nominations.ParkingAllocationMapper;
import com.atos.mapper.nominations.QualityPublicationMapper;
import com.atos.utils.Constants;
import com.atos.utils.DateUtil;

@Component
public class CSVDailyGeneration  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4152000942038567057L;
	private static final Logger log = LogManager.getLogger(CSVDailyGeneration.class);


	@Autowired
	private CSVMapper csvMapper;

	@Autowired
	private SystemMapper systemMapper;

	@Autowired
	private AreaNominalCapacityMapper areaNominalCapacityMapper;

	@Autowired
	private CapacityPublicationMapper capMapper;

	@Autowired
	private ContractQueryMapper contractQueryMapper;
	
	@Autowired
	private CapacityRequestMapper crMapper;

	@Autowired
	private ExportSimoneMapper exportSimoneMapper;

	@Autowired
	private QualityPublicationMapper qpMapper;	

	@Autowired
	private ParkingAllocationMapper parkingAllocationrMapper;

	@Autowired
	private MeteringManagementMapper mmMapper;

	@Autowired
	private AllocationManagementMapper amMapper;

	@Autowired
	private AllocationQueryMapper aqMapper;

	@Autowired
	private AllocationReviewSupplyEWMapper arSupMapper;

	@Autowired
	private BalanceInProgressReportMapper birMapper;

	@Autowired
	private ShipperDailyStatusMapper sdsMapper;

	@Autowired
	private HashMap<String, String> parameterMap;

	@Autowired
	private BalanceIntradayReportMapper binMapper;

	@Autowired
	private AllocationReportQryMapper arMapper;
	
	public void generateCSV() {
		
		log.info("Job generateDailyCSV is running", Calendar.getInstance().getTime());
		
		// 1 area nominal capacity
		csvAreaNominalCapacity();

		// 2 contract query
		csvContractQuery();
		
		// 3 capacity publication
		csvCapacityPublicacion();
		
		// 4 capacity request query
		csvCapacityRequestQuery();

		// 5 daily nomination
		csvDailyNomination();
		
		// 7 export simone daily
		csvExportSimoneDaily();
		
		// 9 quality publication
		csvQualityPublication();
		
		// 10 parking allocation
		csvParkingAllocation();
		
		// 11 metering management
		csvMeteringManagement();
		
		// 12 allocation report
		csvAllocationReport();
		
		// 13 allocation management
		csvAllocationManagement();
		
		// 14 allocation query
		csvAllocationQuery();

		// 15 allocation review supply eas-west
		csvAllocationReviewSupplyEW();
		
		// 16 in progress balance report
		csvInProgressBalanceReport();
		
		// 18 shipper daily status
		csvShipperDailyStatus();
		
		// 21 balance intraday report
		csvBalanceIntradayReport();
		
		log.info("Job generateDailyCSV finish", Calendar.getInstance().getTime());
	
	}
	
	private void csvContractQuery() {
		log.info("Start generation CSV contract query", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		Date today = DateUtil.getToday();

		map.put("startDate", today);
		map.put("endDate", today);
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			map.put("idn_system", list_onshore.get(0));
		}
		
		List<ContractQueryCSVBean> list = csvMapper.getCSVContractQuery(map);

		List<ContractAttachmentBean> l = new ArrayList<ContractAttachmentBean>();
		BigDecimal idn_contract_request_prev = null;
		for(int i=0;i<list.size();i++) {
			
			if(i==0) {
				l = contractQueryMapper.selectAdditionalDocs(list.get(i).getIdn_contract_request());
				idn_contract_request_prev = list.get(i).getIdn_contract_request();
			} else {
				if(idn_contract_request_prev.compareTo(list.get(i).getIdn_contract_request())!=0) {
					l = contractQueryMapper.selectAdditionalDocs(list.get(i).getIdn_contract_request());
					idn_contract_request_prev = list.get(i).getIdn_contract_request();
				}
			}
			ArrayList<String> fileNames = new ArrayList<String>();
			for(int j=0;j<l.size();j++) {
				fileNames.add(l.get(j).getFileName());
			}
			list.get(i).setAdditionalDocs(fileNames);
		}
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "contractQuery", sdf.format(today));
		
		log.info("End generation CSV contract query", Calendar.getInstance().getTime());

	}
	
	
	private void csvDailyNomination() {
		log.info("Start generation CSV daily nomination", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		Date today = DateUtil.getToday();

		map.put("start_date", today);
		map.put("end_date", today);
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			map.put("idn_system", list_onshore.get(0));
		}
		
		List<DailyNominationCSVBean> list = csvMapper.getCSVDailyNomination(map);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "dailyNomination", sdf.format(today));
		
		log.info("End generation CSV daily nomination", Calendar.getInstance().getTime());

	}

	private void csvAllocationReport() {
		log.info("Start generation CSV allocation report", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		AllocRepQryFilter filter = new AllocRepQryFilter();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date d = DateUtil.adjustDate(cal.getTime());

		filter.setDateFrom(d);
		filter.setDateTo(d);
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		BigDecimal idn_system = null;
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			idn_system= list_onshore.get(0);
		}
		filter.setSystemId(idn_system);
		String EnergyUnit = "MMBTU/D";
		BigDecimal factorFromDefaultUnit = amMapper.selectFactorFromDefaultUnit(EnergyUnit);
		filter.setFactorFromDefaultUnit(factorFromDefaultUnit);
		
	
		List<AllocationReportDto> list = arMapper.search(filter);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "allocationReportMain", sdf.format(d));
		
		List<AllocationReportDetailDto> det = arMapper.searchDetail(filter);
		
		StringBuilder csv_det = new StringBuilder();
		
		if(det.size()>0) {
			csv_det.append(det.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<det.size();i++) {
			csv_det.append(det.get(i).toCSV()).append("\n");
		}

		writeFile(csv_det, "allocationReportDetail", sdf.format(d));
		log.info("End generation CSV allocation report", Calendar.getInstance().getTime());
/*		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date today = DateUtil.adjustDate(cal.getTime());

		map.put("start_date", today);
		map.put("end_date", today);
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			map.put("idn_system", list_onshore.get(0));
		}
		
		List<AllocationReportCSVBean> list = csvMapper.getCSVAllocationReport(map);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "allocationReport", sdf.format(today));
		log.info("End generation CSV allocation report", Calendar.getInstance().getTime());*/
	}

	private void csvAreaNominalCapacity() {
		log.info("Start generation CSV area nominal capacity", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		AreaNominalCapacityFilter filter = new AreaNominalCapacityFilter();	
		Date today = DateUtil.getToday();

		filter.setStartDate(today);
		filter.setEndDate(today);
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			filter.setIdn_system(list_onshore.get(0));
		}
		
		List<AreaNominalCapacityBean> list = areaNominalCapacityMapper.selectAreaNominalCapacitys(filter);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "areaNominalCapacity", sdf.format(today));
		log.info("End generation CSV area nominal capacity", Calendar.getInstance().getTime());
	}
	
	private void csvCapacityPublicacion() {
		log.info("Start generation CSV capacity publication", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		CapacityPublicationFilter filters = new CapacityPublicationFilter();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date from = cal.getTime();
		filters.setStartDate(from);
		
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date to = cal.getTime();
		filters.setEndDate(to);
		
		filters.setPar_chart("N");
		
		filters.setArea_c3(false);
		filters.setArea_s(false);
		filters.setZones_query();
		
		List<CapacityPublicationBean> list = capMapper.selectCapacityPublication(filters);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			if(list.get(0)!=null) {
				csv.append(list.get(0).toCSVHeader()).append("\n");
			}
		}
		if(list.size()>0 && list.get(0)!=null) {
			for(int i=0;i<list.size();i++) {
				csv.append(list.get(i).toCSV()).append("\n");
			}
		}

		writeFile(csv, "capacityPublication", sdf.format(from) + "-" + sdf.format(to));
		log.info("End generation CSV capacity publication", Calendar.getInstance().getTime());
	}

	private void csvCapacityRequestQuery() {
		log.info("Start generation CSV capacity request query", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		CRManagementFilter filters = new CRManagementFilter();
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date from = cal.getTime();
		filters.setStartDate(from);
		
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date to = cal.getTime();
		filters.setEndDate(to);
		
		List<ComboFilterNS> combo_list = crMapper.selectContractTypes();
		BigDecimal[] contractType = new BigDecimal[combo_list.size()];
		for(int i=0;i<combo_list.size();i++) {
			contractType[i] = combo_list.get(i).getKey();
		}
		filters.setContractTypeId(contractType);
		
		String[] status = new String[4];
		status[0] = "SUBMITTED";
		status[1] = "ACCEPTED";
		status[2] = "COMPLETED";
		status[3] = "PTT_REJECTED";
				
		filters.setStatus(status);

		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			filters.setIdn_system(list_onshore.get(0));
		}
		
		List<CapacityRequestBean> list = crMapper.selectCapacityRequests(filters);
		
		for(int i=0;i<list.size();i++) {
			list.get(i).setAdditionalDocs(crMapper.selectAdditionalDocs(list.get(i).getId()));
			list.get(i).setRejectedPointCodes(crMapper.selectRejectedPoints(list.get(i)));
		}
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "capacityRequestQuery", sdf.format(from) + "-" + sdf.format(to));
		log.info("End generation CSV capacity request query", Calendar.getInstance().getTime());
	}

	private void csvExportSimoneDaily() {
		log.info("Start generation CSV export simone daily", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ExportSimoneFilter filter = new ExportSimoneFilter();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		//cal.add(Calendar.DAY_OF_MONTH, -1);

		filter.setStart_date(cal.getTime());
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			filter.setIdn_pipeline(list_onshore.get(0));
		}
		

		List<ExportSimoneBean> list = exportSimoneMapper.selectExportSimone(filter);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "exportSimoneDaily", sdf.format(cal.getTime()));
		
		log.info("End generation CSV export simone daily", Calendar.getInstance().getTime());

	}

	private void csvQualityPublication() {
		log.info("Start generation CSV quality publication", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		QualityPublicationFilter filter = new QualityPublicationFilter();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		filter.setStartDate(cal.getTime());
		filter.setEndDate(cal.getTime());
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		BigDecimal idn_system = null;
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			idn_system= list_onshore.get(0);
		}
		
		List<ComboFilterNS> combo_list = qpMapper.selectAreasSystem(idn_system);
		BigDecimal[] areas = new BigDecimal[combo_list.size()];
		for(int i=0;i<combo_list.size();i++) {
			areas[i] = combo_list.get(i).getKey();
		}
		filter.setAreaId(areas);

		BigDecimal[] bd_l = new BigDecimal[1];
		bd_l[0] = BigDecimal.valueOf(1);
		filter.setOperationId(bd_l);

		List<QualityPublicationBean> list = qpMapper.selectQualityPublication(filter);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "qualityPublication", sdf.format(cal.getTime()));
		
		log.info("End generation CSV quality publication", Calendar.getInstance().getTime());

	}

	private void csvParkingAllocation() {
		log.info("Start generation CSV parking allocation", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ParkingAllocationFilter filter = new ParkingAllocationFilter();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		filter.setGas_day(cal.getTime());
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		BigDecimal idn_system = null;
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			idn_system= list_onshore.get(0);
		}
		List<ComboFilterNS> combo_list = parkingAllocationrMapper.selectParkingAllocationZonesSystem(idn_system);
		
		BigDecimal[] zones = new BigDecimal[combo_list.size()];
		for(int i=0;i<combo_list.size();i++) {
			zones[i] = combo_list.get(i).getKey();
		}
		filter.setIdn_zone(zones);
		

		List<ParkingAllocationBean> list = parkingAllocationrMapper.selectParkingAllocation(filter);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "parkingAllocation", sdf.format(cal.getTime()));
		
		log.info("End generation CSV parking allocation", Calendar.getInstance().getTime());

	}

	private void csvMeteringManagement() {
		log.info("Start generation CSV metering management", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		MeteringManagementFilter filter = new MeteringManagementFilter();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date yesterday = DateUtil.adjustDate(cal.getTime());
		
		Calendar cal2 = Calendar.getInstance();
		Date today = DateUtil.adjustDate(cal2.getTime());
		
		
		filter.setGasDayFrom(yesterday);
		filter.setGasDayTo(today);
				
		List<ComboFilterNS> zone_list = mmMapper.selectZonesFromSystemCode("ONSHORE");
		BigDecimal[] zones = new BigDecimal[zone_list.size()];
		for(int i=0;i<zone_list.size();i++) {
			zones[i] = zone_list.get(i).getKey();
		}
		filter.setZoneIds(zones);
		
		List<ComboFilterNS> areas_list = mmMapper.selectAreasFromZoneId(filter);
		BigDecimal[] areas = new BigDecimal[areas_list.size()];
		for(int i=0;i<areas_list.size();i++) {
			areas[i] = areas_list.get(i).getKey();
		}
		filter.setAreaIds(areas);
		
		List<ComboFilterNS> points_list = mmMapper.selectMeteringSystemPoints(filter);
		BigDecimal[] points = new BigDecimal[points_list.size()];
		for(int i=0;i<points_list.size();i++) {
			points[i] = points_list.get(i).getKey();
		}
		filter.setSystemPointId(points);

		List<MeasurementBean> list = mmMapper.selectMeasurements(filter);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "meteringManagement", sdf.format(today));
		
		log.info("End generation CSV metering management", Calendar.getInstance().getTime());

	}

	private void csvAllocationManagement() {
		log.info("Start generation CSV allocation management", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		AllocationManagementFilter filter = new AllocationManagementFilter();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date today = DateUtil.adjustDate(cal.getTime());

		filter.setGasDay(today);
		
		filter.setStatusCode(new String[]{Constants.NOT_REVIEWED,
				Constants.INITIAL,
				Constants.ACCEPTED,
				Constants.REJECTED,
				Constants.ALLOCATED});
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		BigDecimal idn_system = null;
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			idn_system= list_onshore.get(0);
		}
		filter.setSystemId(idn_system);
		String EnergyUnit = "MMBTU/D";
		BigDecimal factorFromDefaultUnit = amMapper.selectFactorFromDefaultUnit(EnergyUnit);
		filter.setFactorFromDefaultUnit(factorFromDefaultUnit);
		
		List<AllocationBean> list = amMapper.selectAllocations(filter);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "allocationManagement", sdf.format(today));
		
		log.info("End generation CSV allocation management", Calendar.getInstance().getTime());

	}
	
	private void csvAllocationQuery() {
		log.info("Start generation CSV allocation query", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		AllocationQueryFilter filter = new AllocationQueryFilter();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date today = DateUtil.adjustDate(cal.getTime());

		filter.setGasDay(today);
		
		filter.setLastVersion(true);
		
		List<ComboFilterNS> zones_list = aqMapper.selectZonesFromSystemCode("ONSHORE");
		BigDecimal[] zones = new BigDecimal[zones_list.size()];
		for(int i=0;i<zones_list.size();i++) {
			zones[i] = zones_list.get(i).getKey();
		}
		filter.setZoneIds(zones);
		
		List<ComboFilterNS> areas_list = aqMapper.selectAreasFromZoneId(filter);
		BigDecimal[] areas = new BigDecimal[areas_list.size()];
		for(int i=0;i<areas_list.size();i++) {
			areas[i] = areas_list.get(i).getKey();
		}
		filter.setAreaIds(areas);
		
		List<ComboFilterNS> points_list = aqMapper.selectPointId(filter);
		BigDecimal[] points = new BigDecimal[points_list.size()];
		for(int i=0;i<points_list.size();i++) {
			points[i] = points_list.get(i).getKey();
		}
		filter.setNomPointIds(points);
		String EnergyUnit = "MMBTU/D";
		BigDecimal factorFromDefaultUnit = aqMapper.selectFactorFromDefaultUnit(EnergyUnit);
		filter.setFactorFromDefaultUnit(factorFromDefaultUnit);
		
		List<AllocationQueryBean> list = aqMapper.selectAllocations(filter);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "allocationQuery", sdf.format(today));
		
		log.info("End generation CSV allocation query", Calendar.getInstance().getTime());

	}

	private void csvAllocationReviewSupplyEW() {
		log.info("Start generation CSV allocation review supply East-West", Calendar.getInstance().getTime());

		String energyUnit = "MMBTU/D";	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		AllocationReviewSupplyEWFilter filter = new AllocationReviewSupplyEWFilter();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date today = DateUtil.adjustDate(cal.getTime());

		filter.setGasDay(today);
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			filter.setSystemId(list_onshore.get(0));
		}
		
		filter.setSystem("ONSHORE");
		
		filter.setStatusCode(new String[]{Constants.NOT_REVIEWED,
				Constants.ACCEPTED,
				Constants.ALLOCATED});
		
		filter.setFactorFromDefaultUnit(arSupMapper.selectFactorFromDefaultUnit(energyUnit));
		
		List<AllocationBean> list = arSupMapper.selectAllocations(filter);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeaderReview()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSVReview()).append("\n");
		}

		writeFile(csv, "allocationReviewSupplyEW", sdf.format(today));
		
		log.info("End generation CSV allocation review supply East-West", Calendar.getInstance().getTime());

	}
	
	private void csvInProgressBalanceReport() {
		log.info("Start generation CSV in progress balance report", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		BalanceInProgressReportFilter filter = new BalanceInProgressReportFilter();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH,-1);

		filter.setGasDay(cal.getTime());
		filter.setGeneratedFrom(cal.getTime());
		
		cal.add(Calendar.DAY_OF_MONTH, 1);
		filter.setGeneratedTo(cal.getTime());
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			filter.setSystemId(list_onshore.get(0));
		}
		filter.setLastVersion(false);
		filter.setTotalAllShippers(false);
		filter.setTotalRows(false);
		
		List<BalanceInProgressReportBean> list = birMapper.selectBalances(filter);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "inProgressBalanceReport", sdf.format(cal.getTime()));
		
		log.info("End generation CSV in progress balance report", Calendar.getInstance().getTime());

	}

	private void csvShipperDailyStatus() {
		log.info("Start generation CSV shipper daily status", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ShipperDailyStatusFilter filter = new ShipperDailyStatusFilter();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date yesterday = DateUtil.adjustDate(cal.getTime());

		filter.setFromDate(yesterday);
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			filter.setSystemId(list_onshore.get(0));
		}
		
		List<ShipperDailyStatusBean> list = sdsMapper.selectShipperDailyStatus(filter);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "shipperDailyStatus", sdf.format(yesterday));
		
		log.info("End generation CSV shipper daily status", Calendar.getInstance().getTime());

	}

	private void csvBalanceIntradayReport() {
		log.info("Start generation CSV balance intraday report", Calendar.getInstance().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		BalanceIntradayReportFilter filter = new BalanceIntradayReportFilter();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH,-1);

		filter.setGasDay(cal.getTime());
		filter.setGeneratedFrom(cal.getTime());
		
		cal.add(Calendar.DAY_OF_MONTH, 1);
		filter.setGeneratedTo(cal.getTime());
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			filter.setSystemId(list_onshore.get(0));
		}
		filter.setLastVersion(false);
		filter.setTotalAllShippers(false);
		filter.setTotalRows(false);
		
		List<BalanceIntradayReportBean> list = binMapper.selectBalances(filter);
		
		StringBuilder csv = new StringBuilder();
		
		if(list.size()>0) {
			csv.append(list.get(0).toCSVHeader()).append("\n");
		}
		for(int i=0;i<list.size();i++) {
			csv.append(list.get(i).toCSV()).append("\n");
		}

		writeFile(csv, "balanceIntradayReport", sdf.format(cal.getTime()));
		
		log.info("End generation CSV balance intraday report", Calendar.getInstance().getTime());

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
