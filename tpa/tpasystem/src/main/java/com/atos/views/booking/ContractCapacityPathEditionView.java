package com.atos.views.booking;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.PostConstruct; 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.booking.ContractCapacityConnectionPathsBean;
import com.atos.beans.booking.ContractCapacityPathDetailBean;
import com.atos.beans.booking.ContractCapacityPathEditionBean;
import com.atos.beans.booking.ContractCapacityPathEditionDatesBean;
import com.atos.beans.booking.ContractCapacityPathEntryExitBean;
import com.atos.filters.booking.ContractCapacityPathFilter;
import com.atos.services.booking.ContractCapacityPathEditionService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name = "contractCapacityPathEditionView")
@ViewScoped
public class ContractCapacityPathEditionView extends CommonView {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 3153518271484925900L;

	private static final Logger log = LogManager.getLogger(ContractCapacityPathEditionView.class);
	
	private ContractCapacityPathFilter filters;
	private ResourceBundle msgs;
	
	private Map<BigDecimal, Object> comboShipper;
	private Map<String, Object> comboBooking;
	private List<ContractCapacityPathEntryExitBean> entry_points;
	private List<ContractCapacityPathEntryExitBean> exit_points;
	private Map<String, Object> start_dates = new HashMap<String, Object>();
	private Map<String, Object> end_dates = new HashMap<String, Object>();
	private ArrayList<Map<String, Object>> combos = new ArrayList<Map<String, Object>>();

	private List<ContractCapacityPathEditionDatesBean> tech_capacities = new ArrayList<ContractCapacityPathEditionDatesBean>();
	private ArrayList<String> area_codes = new ArrayList<String>();
	private ArrayList<ContractCapacityPathEditionBean> items = new ArrayList<ContractCapacityPathEditionBean>();

	
	@ManagedProperty("#{contractCapacityPathEditionService}")
    transient private ContractCapacityPathEditionService service;
    
    public void setService(ContractCapacityPathEditionService service) {
        this.service = service;
    }
	
	private Calendar sysdate;

	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}
	
	@PostConstruct
    public void init() {
    	msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	filters = new ContractCapacityPathFilter();
    	filters.setIdn_system(getChangeSystemView().getIdn_active());

		comboShipper= service.selectShippers(filters);

    	this.sysdate = Calendar.getInstance();
    	this.sysdate.set(Calendar.HOUR_OF_DAY, 0);
    	this.sysdate.set(Calendar.MINUTE, 0);
    	this.sysdate.set(Calendar.SECOND, 0);
    	this.sysdate.set(Calendar.MILLISECOND, 0);
    }
  

	public ContractCapacityPathFilter getFilters() {
		return filters;
	}

	// Methods
	public Map<BigDecimal, Object> getShippers() {
		return comboShipper;
	}
	public Map<String, Object> getBookingIds() {
		return comboBooking;
	}
	public Map<String, Object> getStart_dates() {
		return this.start_dates;
	}

	public Map<String, Object> getEnd_dates() {
		return this.end_dates;
	}
	
	public List<ContractCapacityPathEditionDatesBean> getTech_capacities() {
		return tech_capacities;
	}

	public ArrayList<String> getArea_codes() {
		return area_codes;
	}

	public ArrayList<ContractCapacityPathEditionBean> getItems() {
		return items;
	}

	public void searchContract() {
		comboBooking = service.selectBookingIds(filters);
	}
	public Map<BigDecimal, Object> getEntryPoints() {
		
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		entry_points = service.selectEntryPoints(filters);
		for (ContractCapacityPathEntryExitBean b : entry_points) {
			if (b == null) continue;
			map.put(b.getIdn_area(), b.getPoint_code());
		}
		return map;
	}

	public Map<BigDecimal, Object> getExitPoints() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		exit_points = service.selectExitPoints(filters);
		for (ContractCapacityPathEntryExitBean b : exit_points) {
			if (b == null) continue;
			map.put(b.getIdn_area(), b.getPoint_code());
		}
		return map;
	}
	public void searchPeriod() {
		combos = service.selectPeriods(filters);
		this.start_dates = combos.get(0);
		this.end_dates = combos.get(1);
		
	}
	public void onSearch(){
		if(filters.getIdn_shipper()==null) {
			getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path", "Must select a Shipper", Calendar.getInstance().getTime()));
	    	return;
		}
		if(filters.getIdn_booking()==null) {
			getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path", "Must select a Booking ID", Calendar.getInstance().getTime()));
	    	return;
		}
		if(filters.getStart_date()==null) {
			getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path", "Must select a Contract From value", Calendar.getInstance().getTime()));
	    	return;
		}
		if(filters.getEnd_date()==null) {
			getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path", "Must select a Contract To value", Calendar.getInstance().getTime()));
	    	return;
		}
		if(filters.getIdn_area_orig()==null) {
			getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path", "Must select a Entry Area", Calendar.getInstance().getTime()));
	    	return;
		}
		if(filters.getIdn_area_dest()==null) {
			getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path", "Must select a Exit Area", Calendar.getInstance().getTime()));
	    	return;
		}
		if(filters.getStart_date().compareTo(filters.getEnd_date())>=0) {
			getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path", "Contract From must be before that Contract To", Calendar.getInstance().getTime()));
	    	return;
			
		}
		tech_capacities = service.search(filters);
		area_codes = service.getAreaCodes(tech_capacities);
		items = service.getEditTable(tech_capacities, filters);
		
	}

    public void publish() {

		if(filters.getIdn_booking()==null) {
			getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path", "Must select a Booking ID to publish", Calendar.getInstance().getTime()));
	    	return;
		}
		if(filters.getStart_date()==null) {
			getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path", "Must select a Contract From value to publish", Calendar.getInstance().getTime()));
	    	return;
		}
		if(filters.getEnd_date()==null) {
			getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path", "Must select a Contract To value to publish", Calendar.getInstance().getTime()));
	    	return;
		}
        int salida = service.publishPath(filters, getUser().getUsername());
        if(salida!= 0) {
        	getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path", "Error publishing capacity path", Calendar.getInstance().getTime()));
        } else {
        	getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.INFO,"Contract Capacity Path", "Contract path values published", Calendar.getInstance().getTime()));

        }
    }
	public void save() {
		
		for(int i=0;i<items.size();i++) {
			for(int j=0;j<items.get(i).getList_paths().size();j++) {
				if(items.get(i).getList_paths().get(j).getEdited().equals("Y")){
					
			        BigDecimal idn_entry_point = searchIdnPoint(items.get(i).getIdn_area_orig(), entry_points);
			        BigDecimal idn_exit_point = searchIdnPoint(items.get(i).getIdn_area_dest(), exit_points);
			        

			        
			        int salida = service.savePath(items.get(i), items.get(i).getList_paths().get(j).getBooked(), items.get(i).getList_paths().get(j).getIdn_capacity_path(), idn_entry_point, idn_exit_point);
			        if(salida!= 0) {
			        	getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Contract Capacity Path Edition", "Error saving capacity path", Calendar.getInstance().getTime()));
			        	return;
			        }

					
				}
			}
		}
    	getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.INFO,"Contract Capacity Path Edition", "Capacity Path Edition saved", Calendar.getInstance().getTime()));
		onSearch();
		
	}
    private BigDecimal searchIdnPoint(BigDecimal idn_area, List<ContractCapacityPathEntryExitBean> list) {
    	
    	for(int i=0;i<list.size();i++) {
    		if(list.get(i).getIdn_area().equals(idn_area)) {
    			return list.get(i).getIdn_system_point();
    		}
    	}
    	return null;
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
        bean.setEdited("Y");
    }	
}