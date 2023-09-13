package com.atos.views.nominations;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.primefaces.model.StreamedContent;

import com.atos.beans.MessageBean;
import com.atos.beans.nominations.QueryShipperNominationFileBean;
import com.atos.filters.nominations.QueryShipperNominationFileFilter;
import com.atos.services.nominations.QueryShipperDailyNominationFileService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="queryShipperDailyNominationFileView")
@ViewScoped
public class QueryShipperDailyNominationFileView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4562877935079864350L;

	private static final Logger log = LogManager.getLogger(QueryShipperDailyNominationFileView.class);

	private QueryShipperNominationFileFilter filters;
	private List<QueryShipperNominationFileBean> items;
	private QueryShipperNominationFileBean selected;
	private ResourceBundle msgs;

	@ManagedProperty("#{queryShipperDailyNominationFileService}")
    transient private QueryShipperDailyNominationFileService service;
    
    public void setService(QueryShipperDailyNominationFileService service) {
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

	@PostConstruct
    public void init() {
		msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		initFilters();
    }

	private void initFilters(){
    	filters = new QueryShipperNominationFileFilter();
		
    	filters.setCategory_code(Constants.NOMINATION);
		filters.setTerm_code(Constants.DAILY);
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
    	cal.add(Calendar.DAY_OF_MONTH, 1);
    	filters.setStart_date(cal.getTime());
    	if(getUser().isUser_type(Constants.SHIPPER)){
    		filters.setShipper_code(getUser().getIdn_user_group().toString());
    	}
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
		String summaryMsg = null;
		String errorMsg = null;
		
		summaryMsg = msgs.getString("error_parameters");
		if(filters.getStart_date()==null){		
			errorMsg = msgs.getString("date_selected");
			getMessages().addMessage(Constants.head_menu[4],	new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
		}

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
