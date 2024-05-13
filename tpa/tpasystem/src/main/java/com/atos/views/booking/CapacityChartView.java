package com.atos.views.booking;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct; 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.LineChartModel;

import com.atos.filters.booking.CapacityPublicationFilter;
import com.atos.services.booking.CapacityChartService;
import com.atos.views.CommonView;

@ManagedBean
@ViewScoped
public class CapacityChartView extends CommonView {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 320559660004817675L;
	private CapacityPublicationFilter filters;
	private LineChartModel graphicLine1;
	
	@ManagedProperty("#{capacityChartService}")
    transient private CapacityChartService service;
    
    public void setService(CapacityChartService service) {
        this.service = service;
    }
	
    public LineChartModel getGraphicLine1() {
		return graphicLine1;
	}

	@PostConstruct
    public void init() {
    	filters = new CapacityPublicationFilter();
    	filters.setOnshoreSystem(getChangeSystemView().isOnshore());
    	
		Calendar cal = Calendar.getInstance();
		filters.setSelectedYear(cal.get(Calendar.YEAR));

		graphicLine1 = service.createDefaultDateModel(null,null);
    }
  
	public CapacityPublicationFilter getFilters() {
		return filters;
	}

	public void setFilters(CapacityPublicationFilter filters) {
		this.filters = filters;
	}
 
	public List<Integer> getYears() {
		ArrayList<Integer> years = new ArrayList<Integer>();
		int initialyear = filters.getSelectedYear()-11;
		for(int i=0;i<21;i++){
			years.add(initialyear+i);
		}
		return years;
	}
	
	// Methods
	public void onSearch(){
		filters.setIdn_system(super.getChangeSystemView().getIdn_active());
		filters.setZones_query();
		Date start_date = filters.getStartDate();
		Calendar cal_start = Calendar.getInstance();
		cal_start.setTime(start_date);
		cal_start.set(cal_start.get(Calendar.YEAR), 0, 1, 0, 0, 0);
		Calendar cal_end = Calendar.getInstance();
		cal_end.setTime(start_date);
		cal_end.set(cal_start.get(Calendar.YEAR), 11, 31, 0, 0, 0);
		filters.setStartDate(cal_start.getTime());
		filters.setEndDate(cal_end.getTime());
		graphicLine1 = service.search(filters);
        
        
	}
	public void onClear(){
		filters = new CapacityPublicationFilter();
		graphicLine1= service.createDefaultDateModel(null,null);
    }

	public void selectAll() {
        filters.setSelectAll(filters.isSelectAll());
    }
     
	
}