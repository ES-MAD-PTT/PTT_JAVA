package com.atos.views.booking;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.PostConstruct; 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.DataFormat;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

import com.atos.beans.MessageBean;
import com.atos.beans.booking.ContractCapacityConnectionPathsBean;
import com.atos.beans.booking.ContractCapacityPathBean;
import com.atos.beans.booking.ContractCapacityPathDetailBean;
import com.atos.beans.booking.ContractCapacityPathEntryExitBean;
import com.atos.beans.booking.ContractCapacityPathValuesBean;
import com.atos.filters.booking.ContractCapacityPathFilter;
import com.atos.services.booking.ContractCapacityPathService;
import com.atos.utils.Constants;
import com.atos.utils.POIXSSFExcelUtils;
import com.atos.utils.PropertiesManager;
import com.atos.views.CommonView;

@ManagedBean(name = "contractCapacityPathView")
@ViewScoped
public class ContractCapacityPathView extends CommonView {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 3153518271484925900L;

	private static final Logger log = LogManager.getLogger(ContractCapacityPathView.class);
	
	private ContractCapacityPathFilter filters, filters2;
	private List<LinkedHashMap<String,String>> items = new ArrayList<LinkedHashMap<String,String>>();
	private List<ContractCapacityPathDetailBean> tech_capacities = new ArrayList<ContractCapacityPathDetailBean>();
	private List<ContractCapacityConnectionPathsBean> connectionPaths = new ArrayList<ContractCapacityConnectionPathsBean>();
	private List<ContractCapacityPathEntryExitBean> entry_points;
	private List<ContractCapacityPathEntryExitBean> exit_points;
	
	
	private ContractCapacityConnectionPathsBean selected = new ContractCapacityConnectionPathsBean();
	
	private Map<String, Object> periods = new HashMap<String, Object>();
	private Map<String, Object> start_dates = new HashMap<String, Object>();
	private Map<String, Object> end_dates = new HashMap<String, Object>();
	private ArrayList<Map<String, Object>> combos = new ArrayList<Map<String, Object>>();
	private boolean period_boolean = true; 
	
	private ResourceBundle msgs;
	
	@ManagedProperty("#{contractCapacityPathService}")
    transient private ContractCapacityPathService service;
    
    public void setService(ContractCapacityPathService service) {
        this.service = service;
    }
	
	private Calendar sysdate;

	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}
	
	public Set<String> getColumnNames()
    {
        return items.size() > 0 ? items.get(0).keySet() : new HashSet<String>();
    }
    
	@PostConstruct
    public void init() {
    	msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	filters = new ContractCapacityPathFilter();
    	filters.setIdn_system(getChangeSystemView().getIdn_active());
    	filters2 = new ContractCapacityPathFilter();
    	filters2.setIdn_system(getChangeSystemView().getIdn_active());
    	this.sysdate = Calendar.getInstance();
    	this.sysdate.set(Calendar.HOUR_OF_DAY, 0);
    	this.sysdate.set(Calendar.MINUTE, 0);
    	this.sysdate.set(Calendar.SECOND, 0);
    	this.sysdate.set(Calendar.MILLISECOND, 0);
    }
  
	public ContractCapacityPathFilter getFilters() {
		return filters;
	}

	public void setFilters(ContractCapacityPathFilter filters) {
		this.filters = filters;
	}
 
	public ContractCapacityPathFilter getFilters2() {
		return filters2;
	}

	public void setFilters2(ContractCapacityPathFilter filters) {
		this.filters2 = filters;
	}
 
	public ContractCapacityConnectionPathsBean getSelected() {
		return selected;
	}

	public void setSelected(ContractCapacityConnectionPathsBean selected) {
		this.selected = selected;
	}

	// Methods
	public void onSearch(){
		if(filters.getIdn_booking()==null) {
			getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path", "Must select a Booking ID", Calendar.getInstance().getTime()));
	    	return;
		}
        items = service.search(filters);
	}
	public void onClear(){
		filters = new ContractCapacityPathFilter();
		filters.setIdn_system(getChangeSystemView().getIdn_active());
        if (items != null) {
            items.clear();
        }
    }
	public void onSearch2(){
		if(filters2.getIdn_booking()==null) {
			getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path", "Must select a Booking ID", Calendar.getInstance().getTime()));
	    	return;
		}
		tech_capacities = service.search2(filters2);
		onSearch3();
	}
	public void onClear2(){
		filters2 = new ContractCapacityPathFilter();
		filters2.setIdn_system(getChangeSystemView().getIdn_active());
        if (items != null) {
            items.clear();
        }
    }

	public List<LinkedHashMap<String,String>> getItems() {
		return items;
	}

	public List<ContractCapacityPathDetailBean> getTech_capacities() {
		return tech_capacities;
	}

	public Map<String, Object> getBookingIds() {
		return service.selectBookingIds(filters);
	}
	public Map<String, Object> getBookingIds2() {
		return service.selectBookingIds(filters2);
	}

	public void searchPeriod() {
		combos = service.selectPeriods(filters2);
		this.periods = combos.get(0);
		this.start_dates = combos.get(1);
		this.end_dates = combos.get(2);
		if(this.periods.size()!=0) {
			this.period_boolean = true;
		} else {
			this.period_boolean = false;
		}
		
	}
	public Map<String, Object> getPeriods() {
		return this.periods;
	}

	public Map<String, Object> getStart_dates() {
		return this.start_dates;
	}

	public Map<String, Object> getEnd_dates() {
		return this.end_dates;
	}

	
	public boolean getPeriod_boolean() {
		return period_boolean;
	}

	public Map<BigDecimal, Object> getEntryPoints() {
		
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		entry_points = service.selectEntryPoints(filters2);
		for (ContractCapacityPathEntryExitBean b : entry_points) {
			if (b == null) continue;
			map.put(b.getIdn_area(), b.getPoint_code());
		}
		return map;
	}

	public Map<BigDecimal, Object> getExitPoints() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		exit_points = service.selectExitPoints(filters2);
		for (ContractCapacityPathEntryExitBean b : exit_points) {
			if (b == null) continue;
			map.put(b.getIdn_area(), b.getPoint_code());
		}
		return map;
	}

	public void onSearch3() {
		if(filters2.getIdn_area_dest()!=null && filters2.getIdn_area_orig()!=null) {
			this.connectionPaths = service.selectConnectionPaths(filters2);
		}
		
	}
	
	public List<ContractCapacityConnectionPathsBean> getConnectionPaths() {
		return this.connectionPaths;
	}

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        
        if(newValue==null) {
        	newValue = new BigDecimal(0);
        }
        
        UIColumn col= event.getColumn();
        DataTable o=(DataTable) event.getSource();
        ContractCapacityConnectionPathsBean bean=(ContractCapacityConnectionPathsBean)o.getRowData();
        
        service.getCapacityPathStep(bean.getIdn_capacity_path());
        
    /*    for(int i=0;i<tech_capacities.size();i++) {
        	ContractCapacityPathDetailBean b = tech_capacities.get(i);
        	BigDecimal total_available = (BigDecimal)newValue;
        	BigDecimal total_remain_booked = (BigDecimal)newValue;
        	for(int j=0;j<b.getList_values_available().size();j++) {
        		total_available.add(b.getList_values_available().get(j));
        	}
        	if(b.getTechnical_capacity().doubleValue()<total_available.doubleValue()) {
            	getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path", "Error saving capacity path. Technical capacity exceded in area " + b.getArea_code(), Calendar.getInstance().getTime()));
        		return;
        	}
        	
        	for(int j=0;j<b.getList_values_remain_booked().size();j++) {
        		total_remain_booked.add(b.getList_values_remain_booked().get(j));
        	}
        	
        }
        */
        
        BigDecimal idn_entry_point = searchIdnPoint(filters2.getIdn_area_orig(), entry_points);
        BigDecimal idn_exit_point = searchIdnPoint(filters2.getIdn_area_dest(), exit_points);
        
        int salida = service.savePath(filters2, (BigDecimal)newValue, bean.getIdn_capacity_path(), idn_entry_point, idn_exit_point);
        if(salida!= 0) {
        	getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path", "Error saving capacity path", Calendar.getInstance().getTime()));
        }
        
        onSearch2();
        onSearch3();
        
    }
        
    private BigDecimal searchIdnPoint(BigDecimal idn_area, List<ContractCapacityPathEntryExitBean> list) {
    	
    	for(int i=0;i<list.size();i++) {
    		if(list.get(i).getIdn_area().equals(idn_area)) {
    			return list.get(i).getIdn_system_point();
    		}
    	}
    	return null;
    }
}