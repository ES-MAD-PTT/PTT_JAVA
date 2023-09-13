package com.atos.services.booking;

import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.atos.beans.UserBean;
import com.atos.beans.booking.CRReviewDto;
import com.atos.beans.booking.CapacityDetailDto;
import com.atos.beans.booking.CapacityRequestMailBean;
import com.atos.beans.booking.GasQualityDto;
import com.atos.beans.booking.OperationFileUpdate;
import com.atos.mapper.booking.ContractReviewMapper;

@Service("contractReviewService")
public class ContractReviewService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3865225700480688812L;

	@Autowired
	private ContractReviewMapper mapper;

	@Transactional(rollbackFor = { Throwable.class })
	public HashMap<String, Object> save(List<CapacityDetailDto> entries, List<CapacityDetailDto> exits,
			String requestCode, String shipper,
			boolean shortContract, SimpleDateFormat contractFormatter, UserBean user, String comments)
			throws Exception {
		List<CapacityDetailDto> modifiedEntries = getModified(entries);
		List<CapacityDetailDto> modifiedExits = getModified(exits);
		modifiedExits.parallelStream().forEach(e -> e.setExitPoint(true));

		modifiedEntries.addAll(modifiedExits);
		String xml = composeXML(modifiedEntries, requestCode, shipper, shortContract, contractFormatter, comments);
		Integer fileId = entries.get(0).getOperationFileId();
		OperationFileUpdate ofu = new OperationFileUpdate();
		ofu.setIdnOperationFile(new BigDecimal(fileId));
		ofu.setIdnUserGroup(user.getIdn_user_group());
		ofu.setStatus("NEW");
		ofu.setXmlData(xml);
		// System.out.println(xml);
		ofu.setAudInsUser(user.getUsername());
		ofu.setAudLastUser(user.getUsername());
		mapper.insertModified(ofu);
		HashMap<String, Object> params = new HashMap<>();
		params.put("par_user", user.getUsername());
		params.put("par_file", ofu.getIdnOperationFileUpdate());
		params.put("par_language", "en");
		params.put("par_language", "en");
		params.put("par_error_code", "");
		params.put("par_error_desc", "");
		mapper.proRequestUpdate(params);

		// System.out.println(params);
		return params;

	}

	private String composeXML(List<CapacityDetailDto> modifiedEntries, String requestCode, String shipperName,
			boolean isShortContract, SimpleDateFormat sdf, String comments) throws Exception {
		SimpleDateFormat lsdf = (SimpleDateFormat) sdf.clone();
//		if (isShortContract) {
			lsdf = new SimpleDateFormat("dd/MM/YYYY");
//		}

		String xml = null;

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element contract = doc.createElement("contract");

		doc.appendChild(contract);
		Element header = doc.createElement("header");
		Element request_code = doc.createElement("request_code");
		request_code.appendChild(doc.createTextNode(requestCode));
		Element shipper_name = doc.createElement("shipper_name");
		shipper_name.appendChild(doc.createTextNode(shipperName));
		Element ecomments = doc.createElement("comments");
		if (comments != null) {
			ecomments.appendChild(doc.createTextNode(comments));
		}
		header.appendChild(request_code);
		header.appendChild(shipper_name);
		header.appendChild(shipper_name);
		header.appendChild(ecomments);
		contract.appendChild(header);

		String idpoint = null;
		//String vdate_type = isShortContract ? "MONTH" : "YEAR";
		String vdate_type = "MONTH";
		// elementos para cada entry point
		Element cdb_btu = null;
		Element mhb_bbtu = null;
		Element cdb_mmscfd = null;
		Element entry = null;
		Element mhb_mmscfd = null;
		CRReviewDto referencePoint = null;
		for (CapacityDetailDto item : modifiedEntries) {

				// inicializa datos del punto
				entry = doc.createElement(item.isExitPoint() ? "exit" : "entry");
				idpoint = item.getEntryPoint();
				cdb_btu = doc.createElement("cdb_bbtu");
				mhb_bbtu = doc.createElement("mhb_bbtu");
				cdb_mmscfd = doc.createElement("cdb_mmscfd");
				mhb_mmscfd = doc.createElement("mhb_mmscfd");
				// por cada punto
				Element point = doc.createElement("point");
				point.appendChild(doc.createTextNode(item.getEntryPoint()));
				Element zone = doc.createElement("zone");
				zone.appendChild(doc.createTextNode(item.getZone()));
				Element area = doc.createElement("area");
				area.appendChild(doc.createTextNode(item.getArea()));
				entry.appendChild(point);
				entry.appendChild(zone);
				entry.appendChild(area);

			Element date_data = doc.createElement("date_data");
			Element date_num = doc.createElement("date_num");
			date_num.appendChild(doc.createTextNode(lsdf.format(item.getMonthYear())));
			date_data.appendChild(date_num);
			Element value = doc.createElement("value");
			value.appendChild(doc.createTextNode(item.getDailyBookingmbtu().toString()));
			date_data.appendChild(value);
			Element date_type = doc.createElement("date_type");
			date_type.appendChild(doc.createTextNode(vdate_type));
			date_data.appendChild(date_type);

			cdb_btu.appendChild(date_data);

			Element date_data2 = doc.createElement("date_data");
			Element date_num2 = doc.createElement("date_num");
			date_num2.appendChild(doc.createTextNode(lsdf.format(item.getMonthYear())));
			Element value2 = doc.createElement("value");
			value2.appendChild(doc.createTextNode(item.getHourlyBookingMmbtu().toString()));
			Element date_type2 = doc.createElement("date_type");
			date_type2.appendChild(doc.createTextNode(vdate_type));
			date_data2.appendChild(date_type2);
			date_data2.appendChild(value2);
			date_data2.appendChild(date_num2);
			mhb_bbtu.appendChild(date_data2);
			if (!item.isExitPoint()) {
				Element date_data3 = doc.createElement("date_data");
				Element date_num3 = doc.createElement("date_num");
				date_num3.appendChild(doc.createTextNode(lsdf.format(item.getMonthYear())));
				Element value3 = doc.createElement("value");
				value3.appendChild(doc.createTextNode(item.getDailyBookingMmscfd().toString()));
				Element date_type3 = doc.createElement("date_type");
				date_type3.appendChild(doc.createTextNode(vdate_type));
				date_data3.appendChild(date_type3);
				date_data3.appendChild(value3);
				date_data3.appendChild(date_num3);
				cdb_mmscfd.appendChild(date_data3);
			}

			if (!item.isExitPoint()) {
				Element date_data4 = doc.createElement("date_data");
				Element date_num4 = doc.createElement("date_num");
				date_num4.appendChild(doc.createTextNode(lsdf.format(item.getMonthYear())));
				Element value4 = doc.createElement("value");
				value4.appendChild(doc.createTextNode(item.getHourlyBookingMmscfd().toString()));
				Element date_type4 = doc.createElement("date_type");
				date_type4.appendChild(doc.createTextNode(vdate_type));
				date_data4.appendChild(date_type4);
				date_data4.appendChild(value4);
				date_data4.appendChild(date_num4);
				mhb_mmscfd.appendChild(date_data4);
			}

			// añadir datos
			if (item.isDailyBookingmbtEdited()) {
				entry.appendChild(cdb_btu);
			}
			if (item.isHourlyBookingMmbtuEdited()) {
				entry.appendChild(mhb_bbtu);
			}
			if (item.isDailyBookingMmscfdEdited()) {
				entry.appendChild(cdb_mmscfd);
			}
			if (item.isHourlyBookingMmscfdEdited()) {
				entry.appendChild(mhb_mmscfd);
			}
			contract.appendChild(entry);
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DOMSource source = new DOMSource(doc);
		// StreamResult result = new StreamResult(new File("C:\\file.xml"));
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		try {
			transformer.transform(source, result);
			xml = writer.toString();
			// System.out.println(xml);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}

	public List<CapacityDetailDto> getModified(List<CapacityDetailDto> entries) {
		return entries.stream()
				.filter(e -> e.isDailyBookingmbtEdited() || e.isDailyBookingMmscfdEdited()
						|| e.isHourlyBookingMmbtuEdited() || e.isHourlyBookingMmscfdEdited())
				.collect(Collectors.toList());
	}

	public List<GasQualityDto> getQualityListList(String requestCode) {
		List<GasQualityDto> result = null;
		result = mapper.getGasQuality(requestCode);
		return result;
	}

	public Map<String, List<GasQualityDto>> getGasQualityDetails(List<GasQualityDto> qList) {
		Map<String, List<GasQualityDto>> result = null;
		result = qList.stream().collect(Collectors.groupingBy(g -> g.getPoint() + "-" + g.getGasParameter()));
		return result;
	}

	public List<Date> getDateList(List<CapacityDetailDto> capacities) {
		return capacities.stream().map(c -> c.getMonthYear()).distinct().sorted().collect(Collectors.toList());
	}

	/**
	 * get the list of dates for from / to filter
	 * 
	 * @param data
	 * @param fromTo
	 *            if true returns From dates otherwise To dates
	 * @return
	 */
	public List<Date> getDatesForFilters(List<CRReviewDto> data, boolean fromTo) {
		return data.stream().map(con -> fromTo ? con.getDateFrom() : con.getDateTo()).distinct().sorted()
				.collect(Collectors.toList());
	}

	public List<CRReviewDto> getSubtotals(List<CapacityDetailDto> capacities) {

		List<String> zoneList = capacities.stream().filter(distinctByKey(z -> z.getZone())).map(z -> z.getZone())
				.collect(Collectors.toList());

		List<CRReviewDto> subTotals = new ArrayList<>();

		for (String zone : zoneList) {
			Map<String, Double> dailyBookingmbtuMap = capacities.stream().filter(cap -> cap.getZone().equals(zone))
					.collect(Collectors.groupingBy(item -> item.getYear() + "-" + item.getZone(),
							Collectors.summingDouble(item -> item.getDailyBookingmbtu())));
			Map<String, Double> maxHourBookingmbtuMap = capacities.stream().filter(cap -> cap.getZone().equals(zone))
					.collect(Collectors.groupingBy(item -> item.getYear() + "-" + item.getZone(),
							Collectors.summingDouble(item -> item.getHourlyBookingMmbtu())));
			Map<String, Double> dailyBookingmscfMap = capacities.stream()
					.filter(cap -> cap.getDailyBookingMmscfd() != null).filter(cap -> cap.getZone().equals(zone))
					.collect(Collectors.groupingBy(item -> item.getYear() + "-" + item.getZone(),
							Collectors.summingDouble(item -> item.getDailyBookingMmscfd())));

			Map<String, Double> maxHourBookingscfMap = capacities.stream()
					.filter(cap -> cap.getHourlyBookingMmscfd() != null).filter(cap -> cap.getZone().equals(zone))
					.collect(Collectors.groupingBy(item -> item.getYear() + "-" + item.getZone(),
							Collectors.summingDouble(item -> item.getHourlyBookingMmscfd())));

			CRReviewDto sub = new CRReviewDto();
			sub.setRegisterType(CRReviewDto.SUBTOTAL_REGISTER);
			sub.setZone(zone);
			sub.setSubTotalcapacityDailyBookingByZone(dailyBookingmbtuMap);
			sub.setSubtotalMaximunHourBooking(maxHourBookingmbtuMap);
			sub.setSubtotalCapacityDailyBookingScf(dailyBookingmscfMap);
			sub.setSubtotalMaxHourBookingScf(maxHourBookingscfMap);
			subTotals.add(sub);

		}
		return subTotals;
	}

	public List<CRReviewDto> getTotals(List<CapacityDetailDto> capacities) {

		List<CRReviewDto> totals = new ArrayList<>();

		Map<String, Double> dailyBookingmbtuMap = capacities.stream().collect(Collectors
				.groupingBy(item -> item.getYear(), Collectors.summingDouble(item -> item.getDailyBookingmbtu())));
		Map<String, Double> maxHourBookingmbtuMap = capacities.stream().collect(Collectors
				.groupingBy(item -> item.getYear(), Collectors.summingDouble(item -> item.getHourlyBookingMmbtu())));
		Map<String, Double> dailyBookingmscfMap = capacities.stream().filter(cap -> cap.getDailyBookingMmscfd() != null)
				.collect(Collectors.groupingBy(item -> item.getYear(),
						Collectors.summingDouble(item -> item.getDailyBookingMmscfd())));

		Map<String, Double> maxHourBookingscfMap = capacities.stream()
				.filter(cap -> cap.getHourlyBookingMmscfd() != null).collect(Collectors.groupingBy(
						item -> item.getYear(), Collectors.summingDouble(item -> item.getHourlyBookingMmscfd())));

		CRReviewDto tot = new CRReviewDto();
		tot.setRegisterType(CRReviewDto.TOTAL_REGISTER);
		tot.setSubTotalcapacityDailyBookingByZone(dailyBookingmbtuMap);
		tot.setSubtotalMaximunHourBooking(maxHourBookingmbtuMap);
		tot.setSubtotalCapacityDailyBookingScf(dailyBookingmscfMap);
		tot.setSubtotalMaxHourBookingScf(maxHourBookingscfMap);
		if (!dailyBookingmbtuMap.isEmpty() || !maxHourBookingmbtuMap.isEmpty() || !dailyBookingmscfMap.isEmpty()
				|| !maxHourBookingscfMap.isEmpty()) {

			totals.add(tot);
		}

		return totals;
	}

	public List<CRReviewDto> getDiffEntryExit(List<CRReviewDto> entryCapacities, List<CRReviewDto> exitCapacities) {

		List<CRReviewDto> diff = new ArrayList<>();
		List<CRReviewDto> totalEntry = entryCapacities.stream()
				.filter(e -> e.getRegisterType() == CRReviewDto.TOTAL_REGISTER).collect(Collectors.toList());
		List<CRReviewDto> totalExit = exitCapacities.stream()
				.filter(e -> e.getRegisterType() == CRReviewDto.TOTAL_REGISTER).collect(Collectors.toList());
		if (totalExit == null || totalExit.isEmpty()) {
			return diff;
		}
		Map<String, Double> mapDiff = new HashMap<>();
		Map<String, Double> mapDiffHourly = new HashMap<>();
		int idx[] = { 0 };
		totalEntry.stream().forEach(e -> {

			for (Map.Entry<String, Double> entry : e.getSubTotalcapacityDailyBookingByZone().entrySet()) {
				// System.out.println("Key : " + entry.getKey() + " Value : " +
				// entry.getValue());
				Double entryVal = entry.getValue();

				Map<String, Double> exitMap = totalExit.get(idx[0]).getSubTotalcapacityDailyBookingByZone();
				if (exitMap != null) {
					Double exitVal = exitMap.get(entry.getKey());
					if (exitVal != null) {
						Double result = entryVal - exitVal;
						mapDiff.put(entry.getKey(), result);
					}
				}
			}
			for (Map.Entry<String, Double> entry : e.getSubtotalMaximunHourBooking().entrySet()) {
				// System.out.println("Key : " + entry.getKey() + " Value : " +
				// entry.getValue());
				Double entryVal = entry.getValue();
				Map<String, Double> exitMap = totalExit.get(idx[0]).getSubtotalMaximunHourBooking();
				if (exitMap != null) {
					Double exitVal = exitMap.get(entry.getKey());
					if (exitVal != null) {
						Double result = entryVal - exitVal;
						mapDiffHourly.put(entry.getKey(), result);
					}
				}

			}
		});


		CRReviewDto diffDto = new CRReviewDto();
		diffDto.setRegisterType(CRReviewDto.DIFF_REGISTER);
		diffDto.setDiffEntryExit(mapDiff);
		diffDto.setDiffMaximunHourBooking(mapDiffHourly);
		if (!mapDiff.isEmpty() || !mapDiffHourly.isEmpty()) {
			diff.add(diffDto);
		}

		return diff;
	}

	public List<String> getYears(List<Date> dates, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("en"));
		List<String> resultList = new ArrayList<String>();
		resultList = dates.stream().map(d -> sdf.format(d)).collect(Collectors.toList());
		return resultList;
	}

	public Map<String, CapacityDetailDto> getCapacitiesMap(List<CapacityDetailDto> detailList, String format) {
		Map<String, CapacityDetailDto> result = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("en"));
		for (CapacityDetailDto item : detailList) {
			StringBuilder keyBuilder = new StringBuilder();
			keyBuilder.append(item.getEntryPoint()).append('-').append(sdf.format(item.getMonthYear()));
			result.put(keyBuilder.toString(), item);
		}
		return result;
	}

	public List<CapacityDetailDto> getCapacities(String requestCode, String pointType) {

		HashMap<String, String> params = new HashMap<>();
		params.put("requestCode", requestCode);
		params.put("pointType", pointType);
		return mapper.getCapacities(params);
	}

	public List<CapacityDetailDto> getCapacitiesContractQuery(String requestCode, String pointType) {

		HashMap<String, String> params = new HashMap<>();
		params.put("requestCode", requestCode);
		params.put("pointType", pointType);
		return mapper.getCapacitiesContractQuery(params);
	}

	public List<CRReviewDto> getPoints(String resquestCode, String pointType) {
		HashMap<String, String> params = new HashMap<>();
		params.put("requestCode", resquestCode);
		params.put("pointType", pointType);
		return mapper.getPoints(params);
	}

	public ContractReviewMapper getMapper() {
		return mapper;
	}

	public void setMapper(ContractReviewMapper mapper) {
		this.mapper = mapper;
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	public List<GasQualityDto> reduceList(List<GasQualityDto> list) {
		list = list.stream().filter(distinctByKey(g -> g.getPoint())).collect(Collectors.toList());
		return list;
	}

	public boolean isEditable(String requestCode) {
		Map<String, String> mresult = mapper.isEditable(requestCode);
		String result = mresult.get("EDITABLE");
		return result.equals("Y");
	}

	public BigDecimal getIdnUserGroup(String user_group_id) {
		return mapper.getIdnUserGroup(user_group_id);
	}
	
	public CapacityRequestMailBean getMailData(String request_code) {
		return mapper.getMailData(request_code);
	}
}
