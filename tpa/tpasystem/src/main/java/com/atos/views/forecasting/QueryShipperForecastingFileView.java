package com.atos.views.forecasting;

import java.io.Serializable;
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
import org.primefaces.model.StreamedContent;

import com.atos.beans.MessageBean;
import com.atos.beans.forecasting.QueryShipperForecastingFileBean;
import com.atos.filters.forecasting.QueryShipperForecastingFileFilter;
import com.atos.services.forecasting.QueryShipperForecastingFileService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="queryShipperForecastingFileView")
@ViewScoped
public class QueryShipperForecastingFileView extends CommonView implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3233361164918144867L;

	private static final Logger log = LogManager.getLogger(QueryShipperForecastingFileView.class);

	private QueryShipperForecastingFileFilter filters;
	private List<QueryShipperForecastingFileBean> items;
	private QueryShipperForecastingFileBean selected;
	

	@ManagedProperty("#{queryShipperForecastingFileService}")
    transient private QueryShipperForecastingFileService service;
    
    public void setService(QueryShipperForecastingFileService service) {
        this.service = service;
    }

	public QueryShipperForecastingFileFilter getFilters() {
		return filters;
	}

	public void setFilters(QueryShipperForecastingFileFilter filters) {
		this.filters = filters;
	}

	public QueryShipperForecastingFileBean getSelected() {
		return selected;
	}

	public void setSelected(QueryShipperForecastingFileBean selected) {
		this.selected = selected;
	}

	public List<QueryShipperForecastingFileBean> getItems() {
		return items;
	}

	@PostConstruct
    public void init() {
		initFilters();
    }

	private void initFilters(){
    	filters = new QueryShipperForecastingFileFilter();
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DAY_OF_MONTH, 1);
    	filters.setStart_date(cal.getTime());
    	filters.setEnd_date(cal.getTime());
    	
    	if(getUser().isUser_type(Constants.SHIPPER)){
    		filters.setShipper_code(getUser().getIdn_user_group().toString());
    	}
    	
		filters.setCategory_code(Constants.FORECASTING);
		
		//offShore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
	}
	
	public Map<String, Object> getShipperCodes() {
		filters.setUser(getUser().getUsername());
		return service.selectShipper(filters);
	}

	public Map<BigDecimal, Object> getTermCodes() {
		return service.selectTermCode();
	}

	// Methods
	public void onSearch(){
		String summaryMsg = null;
		String errorMsg = null;
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		summaryMsg = msgs.getString("error_parameters");
		
		if(filters.getStart_date()==null  || filters.getEnd_date()==null ){		
			errorMsg = msgs.getString("date_selected");
			getMessages().addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);

			return;
		}
		/*Calendar cal = Calendar.getInstance();
    	if(cal.getTime().after(filters.getStart_date()) ||  cal.getTime().after(filters.getEnd_date()) ){
    		errorMsg = msgs.getString("start_previous_end");
			getMessages().addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
			return;
    	}*/
    	
    	if ((filters.getStart_date().after(filters.getEnd_date()))){
    		errorMsg = msgs.getString("from_must_earlier_equal");
			getMessages().addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);    		
			return;
    	}  
		
		items = service.selectQueryForecasting(filters);
        
	}
	
	public void onClear(){
		initFilters();
    	
        if (items != null) {
            items.clear();
        }
    }

	public StreamedContent getShipperFile() {
		String summaryMsg = null;
		String errorMsg = null;
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		summaryMsg = msgs.getString("download_error");
		
		if(selected==null){
			errorMsg = msgs.getString("not_register_selected");
			getMessages().addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
	    	return null;
		} else {
			if(selected.getIdn_shipper_file()==null){
		    	errorMsg = msgs.getString("not_file_download");
				getMessages().addMessage(Constants.head_menu[2],
						new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
		    	return null;
			}
			
		}
		TreeMap<String,BigDecimal> map = new TreeMap<String,BigDecimal>();
		map.put("idn_operation_file", selected.getIdn_shipper_file());
		
		try {
			return service.getFile(map);
		} catch (Exception e) {
			log.error("error getting file", e);
			getMessages().addMessage(Constants.head_menu[2], new MessageBean(Constants.ERROR, "Internal Error",
					"error getting file", Calendar.getInstance().getTime()));
			return null;
		}
	}
	
	public StreamedContent getIntermediateFile() {
		String summaryMsg = null;
		String errorMsg = null;
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		summaryMsg = msgs.getString("download_error");
		
		if(selected==null){
			errorMsg = msgs.getString("not_register_selected");
			getMessages().addMessage(Constants.head_menu[2],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
	    	return null;
		} else {
			if(selected.getIdn_operator_file()==null){
				errorMsg = msgs.getString("not_file_download");
				getMessages().addMessage(Constants.head_menu[2],
						new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return null;
			}
		}
		TreeMap<String,BigDecimal> map = new TreeMap<String,BigDecimal>();
		map.put("idn_operation_file", selected.getIdn_operator_file());
		
		try {
			return service.getFile(map);
		} catch (Exception e) {
			log.error("error getting file", e);
			getMessages().addMessage(Constants.head_menu[2], new MessageBean(Constants.ERROR, "Internal Error",
					"error getting file", Calendar.getInstance().getTime()));
			return null;
		}
	}
	
}
