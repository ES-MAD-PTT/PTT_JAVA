package com.atos.services.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.TreeMap;

import org.primefaces.model.chart.LineChartModel;

import com.atos.filters.booking.CapacityPublicationFilter;

public interface CapacityChartService extends Serializable{


	public LineChartModel search(CapacityPublicationFilter filters);
	
	public LineChartModel createDefaultDateModel(TreeMap<String,TreeMap<String,TreeMap<Date,BigDecimal>>> map,CapacityPublicationFilter filters);
}
