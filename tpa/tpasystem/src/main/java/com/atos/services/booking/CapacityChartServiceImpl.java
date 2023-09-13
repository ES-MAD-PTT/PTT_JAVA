package com.atos.services.booking;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.booking.CapacityPublicationBean;
import com.atos.filters.booking.CapacityPublicationFilter;
import com.atos.mapper.booking.CapacityPublicationMapper;

@Service("capacityChartService")
public class CapacityChartServiceImpl implements CapacityChartService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5950761143240025995L;
	private static final String TECH_CAP = "Tech Cap";
	private static final String AVAILABLE_CAP = "Available Cap";
	private static final String BOOKEK_CAP = "Booked Cap";

	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private CapacityPublicationMapper capMapper;

	@Override
	public LineChartModel search(CapacityPublicationFilter filters) {
		filters.setPar_chart("Y");
		List<CapacityPublicationBean> items = capMapper.selectCapacityChart(filters);
		TreeMap<String, TreeMap<String, TreeMap<Date, BigDecimal>>> map = new TreeMap<String, TreeMap<String, TreeMap<Date, BigDecimal>>>();

		for (int i = 0; i < items.size(); i++) {
			CapacityPublicationBean bean = items.get(i);

			// we already have an area in the map
			if (map.containsKey(bean.getArea())) {
				TreeMap<String, TreeMap<Date, BigDecimal>> map_values = map.get(bean.getArea());
				if (bean.getTech_cap() != null) {
					if (map_values.containsKey(CapacityChartServiceImpl.TECH_CAP)) {
						TreeMap<Date, BigDecimal> map_list = map_values.get(CapacityChartServiceImpl.TECH_CAP);
						map_list.put(bean.getMonthYear(), bean.getTech_cap());
					} else {
						TreeMap<Date, BigDecimal> map_list = new TreeMap<Date, BigDecimal>();
						map_list.put(bean.getMonthYear(), bean.getTech_cap());
						map_values.put(CapacityChartServiceImpl.TECH_CAP, map_list);
					}
				}
				if (bean.getAvailable_cap() != null) {
					if (map_values.containsKey(CapacityChartServiceImpl.AVAILABLE_CAP)) {
						TreeMap<Date, BigDecimal> map_list = map_values.get(CapacityChartServiceImpl.AVAILABLE_CAP);
						map_list.put(bean.getMonthYear(), bean.getAvailable_cap());
					} else {
						TreeMap<Date, BigDecimal> map_list = new TreeMap<Date, BigDecimal>();
						map_list.put(bean.getMonthYear(), bean.getAvailable_cap());
						map_values.put(CapacityChartServiceImpl.AVAILABLE_CAP, map_list);
					}
				}
				if (bean.getBooked_cap() != null) {
					if (map_values.containsKey(CapacityChartServiceImpl.BOOKEK_CAP)) {
						TreeMap<Date, BigDecimal> map_list = map_values.get(CapacityChartServiceImpl.BOOKEK_CAP);
						map_list.put(bean.getMonthYear(), bean.getBooked_cap());
					} else {
						TreeMap<Date, BigDecimal> map_list = new TreeMap<Date, BigDecimal>();
						map_list.put(bean.getMonthYear(), bean.getBooked_cap());
						map_values.put(CapacityChartServiceImpl.BOOKEK_CAP, map_list);
					}
				}

			} else {
				// we don't have an area in the map
				TreeMap<String, TreeMap<Date, BigDecimal>> map_values = new TreeMap<String, TreeMap<Date, BigDecimal>>();
				map.put(bean.getArea(), map_values);
				if (bean.getTech_cap() != null) {
					TreeMap<Date, BigDecimal> map_list = new TreeMap<Date, BigDecimal>();
					map_list.put(bean.getMonthYear(), bean.getTech_cap());
					map_values.put(CapacityChartServiceImpl.TECH_CAP, map_list);
				}
				if (bean.getAvailable_cap() != null) {
					TreeMap<Date, BigDecimal> map_list = new TreeMap<Date, BigDecimal>();
					map_list.put(bean.getMonthYear(), bean.getAvailable_cap());
					map_values.put(CapacityChartServiceImpl.AVAILABLE_CAP, map_list);
				}
				if (bean.getBooked_cap() != null) {
					TreeMap<Date, BigDecimal> map_list = new TreeMap<Date, BigDecimal>();
					map_list.put(bean.getMonthYear(), bean.getBooked_cap());
					map_values.put(CapacityChartServiceImpl.BOOKEK_CAP, map_list);
				}
			}
		}
		return createDefaultDateModel(map,filters);

	}

	@Override
	public LineChartModel createDefaultDateModel(TreeMap<String, TreeMap<String, TreeMap<Date, BigDecimal>>> map,CapacityPublicationFilter filters) {

/*		String zone_a1_tc = "cce0ff,", zone_a1_ac ="4d94ff,", zone_a1_bc="0052cc,";
		String zone_b_tc = "66ff99,", zone_b_ac ="00e64d,", zone_b_bc="00802b,";
		String zone_c1_tc = "ff8080,", zone_c1_ac ="ff3333,", zone_c1_bc="cc0000,";
		String zone_d_tc = "ffbf80,", zone_d_ac ="ff9933,", zone_d_bc="e67300,";
		String zone_e_tc = "ffffb3,", zone_e_ac ="ffff00,", zone_e_bc="cccc00,";
		String zone_f1_tc = "ffb3d9,", zone_f1_ac ="ff66b3,", zone_f1_bc="ff1a8c,";
		String zone_g_tc = "d98cd9,", zone_g_ac ="bf40bf,", zone_g_bc="732673,";
		
		String zone_a = "cce0ff,4d94ff,0052cc,";
		String zone_b = "66ff99,00e64d,00802b,";
		String zone_c = "ff8080,ff3333,cc0000,";
		String zone_d = "ffbf80,ff9933,e67300,";
		String zone_e = "ffffb3,ffff00,cccc00,";
		String zone_f = "ffb3d9,ff66b3,ff1a8c,";
		String zone_g = "d98cd9,bf40bf,732673,";*/
		String zones_colors = "";

		LineChartModel graphicLine1 = new LineChartModel();
		// xxx graphicLine1.setLegendPosition("s");
		graphicLine1.setLegendPosition("ne");
		Axis yAxis = graphicLine1.getAxis(AxisType.Y);
		yAxis.setTickFormat("Value %'.3f");
		
		graphicLine1.setTitle("Capacity Chart");
		// xxx graphicLine1.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		graphicLine1.setZoom(true);
		graphicLine1.setLegendCols(3);
		graphicLine1.getAxis(AxisType.Y).setLabel("Values (MMBTU/D)");
		DateAxis axis = new DateAxis("Dates");
		axis.setTickAngle(-50);
		if(filters!=null){
			axis.setMin(dateFormat.format(filters.getStartDate()));
			axis.setMax(dateFormat.format(filters.getEndDate()));
		}
		axis.setTickFormat("%d/%m/%Y");

		graphicLine1.getAxes().put(AxisType.X, axis);

		if (map == null || map.isEmpty()) {
			LineChartSeries serie = new LineChartSeries();
			serie.set(dateFormat.format(Calendar.getInstance().getTime()), 0);
			serie.setLabel("Area - Energy");
			yAxis.setTickFormat(" ");
			graphicLine1.addSeries(serie);
			return graphicLine1;
		}
		Iterator<String> keys = map.keySet().iterator();

		while (keys.hasNext()) {
			// xxx int lineType = 0;
			String key = keys.next();

/*			zones_colors = (key.equals("A") ? zones_colors + zone_a : zones_colors);
			zones_colors = (key.equals("B") ? zones_colors + zone_b : zones_colors);
			zones_colors = (key.equals("C") ? zones_colors + zone_c : zones_colors);
			zones_colors = (key.equals("D") ? zones_colors + zone_d : zones_colors);
			zones_colors = (key.equals("E") ? zones_colors + zone_e : zones_colors);
			zones_colors = (key.equals("F") ? zones_colors + zone_f : zones_colors);
			zones_colors = (key.equals("G") ? zones_colors + zone_g : zones_colors);*/

			TreeMap<String, TreeMap<Date, BigDecimal>> map_values = map.get(key);
			Iterator<String> keys2 = map_values.keySet().iterator();
			while (keys2.hasNext()) {
				// xxx lineType++;
				String key2 = keys2.next();
				zones_colors = this.setZonesColor(zones_colors, key, key2);
				TreeMap<Date, BigDecimal> map_list = map_values.get(key2);
				Iterator<Date> dates = map_list.keySet().iterator();
				LineChartSeries serie = new LineChartSeries();
				serie.setLabel(key + " - " + key2);
				/*
				 * xxx switch (lineType) { case 1:
				 * 
				 * serie.setMarkerStyle("O"); break; case 2:
				 * 
				 * serie.setMarkerStyle("x"); break;
				 * 
				 * default: break; }
				 */
				BigDecimal obj = null;
				Date date = null;
				while (dates.hasNext()) {
					date = dates.next();
					obj =  map_list.get(date);
					serie.set(dateFormat.format(date),obj);
				}
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.set(cal.get(Calendar.YEAR), 11, 31, 0, 0, 0);
				serie.set(dateFormat.format(cal.getTime()), obj);

				graphicLine1.addSeries(serie);
			}
		}
		graphicLine1.setSeriesColors(zones_colors);
		// xxx graphicLine1.setExtender("extLegend");
		return graphicLine1;
	}

	private String setZonesColor(String zones_colors, String key, String key2){

		String zone_a1_tc = "cce0ff,", zone_a1_ac ="4d94ff,", zone_a1_bc="0052cc,";
		String zone_a2_tc = "8080ff,", zone_a2_ac ="0000e6,", zone_a2_bc="000066,";
		String zone_a3_tc = "ccccff,", zone_a3_ac ="9966ff,", zone_a3_bc="6600cc,";
		String zone_b_tc = "66ff99,", zone_b_ac ="00e64d,", zone_b_bc="00802b,";
		String zone_c1_tc = "ff8080,", zone_c1_ac ="ff3333,", zone_c1_bc="cc0000,";
		String zone_c2_tc = "ff99ff,", zone_c2_ac ="ff33cc,", zone_c2_bc="990099,";
		String zone_c3_tc = "ff99cc,", zone_c3_ac ="ff3399,", zone_c3_bc="993366,";
		String zone_c4_tc = "ff9999,", zone_c4_ac ="cc0066,", zone_c4_bc="660033,";
		String zone_c5_tc = "ffcc66,", zone_c5_ac ="ff9900,", zone_c5_bc="cc3300,";
		String zone_c6_tc = "cc0000,", zone_c6_ac ="cc6600,", zone_c6_bc="993300,";
		String zone_d_tc = "ffbf80,", zone_d_ac ="ff9933,", zone_d_bc="e67300,";
		String zone_e_tc = "ffffb3,", zone_e_ac ="ffff00,", zone_e_bc="cccc00,";
		String zone_f1_tc = "ffb3d9,", zone_f1_ac ="ff66b3,", zone_f1_bc="ff1a8c,";
		String zone_f2_tc = "ffccff,", zone_f2_ac ="ff66cc,", zone_f2_bc="cc3399,";
		String zone_g_tc = "d98cd9,", zone_g_ac ="bf40bf,", zone_g_bc="732673,";
		String zone_h_tc = "ccccff,", zone_h_ac ="cc99ff,", zone_h_bc="cc33ff,";
		String zone_r_tc = "cc66ff,", zone_r_ac ="cc00ff,", zone_r_bc="9900cc,";
		String zone_s_tc = "ff33cc,", zone_s_ac ="cc0099,", zone_s_bc="660066,";
		String zone_x1_tc = "4d4d00,", zone_x1_ac ="333300,", zone_x1_bc="666600,";
		String zone_x2_tc = "66ff66,", zone_x2_ac ="009933,", zone_x2_bc="006600,";
		String zone_x3_tc = "99ff33,", zone_x3_ac ="99cc00,", zone_x3_bc="669900,";
		String zone_y_tc = "000000,", zone_y_ac ="37194b,", zone_y_bc="4f3767,";
		
		zones_colors = (key.equals("A1") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_a1_tc : zones_colors);
		zones_colors = (key.equals("A1") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_a1_ac : zones_colors);
		zones_colors = (key.equals("A1") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_a1_bc : zones_colors);
		zones_colors = (key.equals("A2") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_a2_tc : zones_colors);
		zones_colors = (key.equals("A2") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_a2_ac : zones_colors);
		zones_colors = (key.equals("A2") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_a2_bc : zones_colors);
		zones_colors = (key.equals("A3") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_a3_tc : zones_colors);
		zones_colors = (key.equals("A3") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_a3_ac : zones_colors);
		zones_colors = (key.equals("A3") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_a3_bc : zones_colors);
		zones_colors = (key.equals("B") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_b_tc : zones_colors);
		zones_colors = (key.equals("B") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_b_ac : zones_colors);
		zones_colors = (key.equals("B") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_b_bc : zones_colors);
		zones_colors = (key.equals("C1") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_c1_tc : zones_colors);
		zones_colors = (key.equals("C1") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_c1_ac : zones_colors);
		zones_colors = (key.equals("C1") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_c1_bc : zones_colors);
		zones_colors = (key.equals("C2") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_c2_tc : zones_colors);
		zones_colors = (key.equals("C2") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_c2_ac : zones_colors);
		zones_colors = (key.equals("C2") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_c2_bc : zones_colors);
		zones_colors = (key.equals("C3") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_c3_tc : zones_colors);
		zones_colors = (key.equals("C3") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_c3_ac : zones_colors);
		zones_colors = (key.equals("C3") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_c3_bc : zones_colors);
		zones_colors = (key.equals("C4") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_c4_tc : zones_colors);
		zones_colors = (key.equals("C4") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_c4_ac : zones_colors);
		zones_colors = (key.equals("C4") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_c4_bc : zones_colors);
		zones_colors = (key.equals("C5") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_c5_tc : zones_colors);
		zones_colors = (key.equals("C5") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_c5_ac : zones_colors);
		zones_colors = (key.equals("C5") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_c5_bc : zones_colors);
		zones_colors = (key.equals("C6") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_c6_tc : zones_colors);
		zones_colors = (key.equals("C6") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_c6_ac : zones_colors);
		zones_colors = (key.equals("C6") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_c6_bc : zones_colors);
		zones_colors = (key.equals("D") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_d_tc : zones_colors);
		zones_colors = (key.equals("D") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_d_ac : zones_colors);
		zones_colors = (key.equals("D") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_d_bc : zones_colors);
		zones_colors = (key.equals("E") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_e_tc : zones_colors);
		zones_colors = (key.equals("E") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_e_ac : zones_colors);
		zones_colors = (key.equals("E") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_e_bc : zones_colors);
		zones_colors = (key.equals("F1") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_f1_tc : zones_colors);
		zones_colors = (key.equals("F1") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_f1_ac : zones_colors);
		zones_colors = (key.equals("F1") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_f1_bc : zones_colors);
		zones_colors = (key.equals("F2") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_f2_tc : zones_colors);
		zones_colors = (key.equals("F2") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_f2_ac : zones_colors);
		zones_colors = (key.equals("F2") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_f2_bc : zones_colors);
		zones_colors = (key.equals("G") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_g_tc : zones_colors);
		zones_colors = (key.equals("G") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_g_ac : zones_colors);
		zones_colors = (key.equals("G") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_g_bc : zones_colors);
		zones_colors = (key.equals("H") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_h_tc : zones_colors);
		zones_colors = (key.equals("H") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_h_ac : zones_colors);
		zones_colors = (key.equals("H") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_h_bc : zones_colors);
		zones_colors = (key.equals("R") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_r_tc : zones_colors);
		zones_colors = (key.equals("R") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_r_ac : zones_colors);
		zones_colors = (key.equals("R") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_r_bc : zones_colors);
		zones_colors = (key.equals("S") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_s_tc : zones_colors);
		zones_colors = (key.equals("S") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_s_ac : zones_colors);
		zones_colors = (key.equals("S") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_s_bc : zones_colors);
		zones_colors = (key.equals("X1") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_x1_tc : zones_colors);
		zones_colors = (key.equals("X1") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_x1_ac : zones_colors);
		zones_colors = (key.equals("X1") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_x1_bc : zones_colors);
		zones_colors = (key.equals("X2") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_x2_tc : zones_colors);
		zones_colors = (key.equals("X2") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_x2_ac : zones_colors);
		zones_colors = (key.equals("X2") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_x2_bc : zones_colors);
		zones_colors = (key.equals("X3") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_x3_tc : zones_colors);
		zones_colors = (key.equals("X3") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_x3_ac : zones_colors);
		zones_colors = (key.equals("X3") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_x3_bc : zones_colors);
		zones_colors = (key.equals("Y") && key2.equals(CapacityChartServiceImpl.TECH_CAP) ? zones_colors + zone_y_tc : zones_colors);
		zones_colors = (key.equals("Y") && key2.equals(CapacityChartServiceImpl.AVAILABLE_CAP) ? zones_colors + zone_y_ac : zones_colors);
		zones_colors = (key.equals("Y") && key2.equals(CapacityChartServiceImpl.BOOKEK_CAP) ? zones_colors + zone_y_bc : zones_colors);
		return zones_colors;
	}
}
