package com.atos.views.nominations;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.primefaces.model.StreamedContent;

import com.atos.beans.MessageBean;
import com.atos.beans.nominations.QueryShipperNominationFileBean;
import com.atos.filters.nominations.QueryShipperNominationFileFilter;
import com.atos.services.nominations.QueryShipperWeeklyNominationFileService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="queryShipperWeeklyNominationFileView")
@ViewScoped
public class QueryShipperWeeklyNominationFileView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8323625700626448118L;

	private static final Logger log = LogManager.getLogger(QueryShipperWeeklyNominationFileView.class);

	private QueryShipperNominationFileFilter filters;
	private List<QueryShipperNominationFileBean> items;
	private QueryShipperNominationFileBean selected;
	private int dayStartWeek;
	

	@ManagedProperty("#{queryShipperWeeklyNominationFileService}")
    transient private QueryShipperWeeklyNominationFileService service;
    
    public void setService(QueryShipperWeeklyNominationFileService service) {
        this.service = service;
    }
    
	public QueryShipperNominationFileFilter getFilters() {
		return filters;
	}

	public void setFilters(QueryShipperNominationFileFilter filters) {
		this.filters = filters;
	}

	public QueryShipperNominationFileBean getSelected() {
		return selected;
	}

	public void setSelected(QueryShipperNominationFileBean selected) {
		this.selected = selected;
	}

	public List<QueryShipperNominationFileBean> getItems() {
		return items;
	}
	public int getDayStartWeek() {
		return dayStartWeek;
	}

	public void setDayStartWeek() {
		this.dayStartWeek = service.selectStartDayOfWeek();
	}

	@PostConstruct
    public void init() {
		setDayStartWeek();
		initFilters();
    }

	private void initFilters(){
    	filters = new QueryShipperNominationFileFilter();

		filters.setCategory_code(Constants.NOMINATION);
		filters.setTerm_code(Constants.WEEKLY);
		
    	if(getUser().isUser_type(Constants.SHIPPER)){
    		filters.setShipper_code(getUser().getIdn_user_group().toString());
    	}
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
    	int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
    	if(day_of_week == Calendar.MONDAY){
    		cal.add(Calendar.DAY_OF_MONTH, 6);
    	} else if(day_of_week == Calendar.TUESDAY){
    		cal.add(Calendar.DAY_OF_MONTH, 5);
    	} else if(day_of_week == Calendar.WEDNESDAY){
    		cal.add(Calendar.DAY_OF_MONTH, 4);
    	} else if(day_of_week == Calendar.THURSDAY){
    		cal.add(Calendar.DAY_OF_MONTH, 3);
    	} else if(day_of_week == Calendar.FRIDAY){
    		cal.add(Calendar.DAY_OF_MONTH, 2);
    	} else if(day_of_week == Calendar.SATURDAY){
    		cal.add(Calendar.DAY_OF_MONTH, 1);
    	}
    	filters.setStart_date(cal.getTime());
    	
    	filters.setSystemId(getChangeSystemView().getIdn_active());
	}
	
	public Map<String, Object> getShipperCodes() {
		filters.setUser((String)SecurityUtils.getSubject().getPrincipal());
		return service.selectShipperIdNominations(filters);
	}

	public Map<String, Object> getContractCodes() {
		filters.setUser((String)SecurityUtils.getSubject().getPrincipal());
		return service.selectContractCodeByUser(filters);
	}


	// Methods
	public void onSearch(){
		items = service.selectQueryNomination(filters);
	}
	
	public void onClear(){
		initFilters();
    	
        if (items != null) {
            items.clear();
        }
    }
	
	public StreamedContent getIntermediateFile() {
		if(selected==null){
			getMessages().addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Downloading file", "There is no a register selected", Calendar.getInstance().getTime()));
	    	return null;
		} else {
			if(selected.getIdn_operator_file()==null){
				getMessages().addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Downloading file", "There is no a file to download", Calendar.getInstance().getTime()));
		    	return null;
			}
		}
		TreeMap<String,BigDecimal> map = new TreeMap<String,BigDecimal>();
		map.put("idn_operation_file", selected.getIdn_operator_file());
		
		return service.getFile(map);
	}
	
}
