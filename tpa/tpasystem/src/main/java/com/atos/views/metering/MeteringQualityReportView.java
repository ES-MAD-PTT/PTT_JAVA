package com.atos.views.metering;

import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.atos.beans.MessageBean;
import com.atos.beans.metering.MeteringQualityReportBean;
import com.atos.beans.metering.MeteringQualityReportOffshoreBean;
import com.atos.filters.metering.MeteringQualityReportFilter;
import com.atos.services.metering.MeteringQualityReportService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name = "meteringQualityReportView")
@ViewScoped
public class MeteringQualityReportView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7811781475337829656L;

	private static final Logger log = LogManager.getLogger(MeteringQualityReportView.class);

	private MeteringQualityReportFilter filters;
	private List<MeteringQualityReportBean> items;
	private List<MeteringQualityReportOffshoreBean> itemsOffshore;
	
	@ManagedProperty("#{meteringQualityReportService}")
	transient private MeteringQualityReportService service;

	
	public MeteringQualityReportFilter getFilters() {
		return filters;
	}

	public void setFilters(MeteringQualityReportFilter filters) {
		this.filters = filters;
	}

	public List<MeteringQualityReportBean> getItems() {
		return items;
	}

	public void setItems(List<MeteringQualityReportBean> items) {
		this.items = items;
	}

	public List<MeteringQualityReportOffshoreBean> getItemsOffshore() {
		return itemsOffshore;
	}

	public void setItemsOffshore(List<MeteringQualityReportOffshoreBean> itemsOffshore) {
		this.itemsOffshore = itemsOffshore;
	}

	public void setService(MeteringQualityReportService service) {
		this.service = service;
	}

	@PostConstruct
	public void init() {
		filters = new MeteringQualityReportFilter();
	
		Calendar toDay = Calendar.getInstance(); 
		toDay.set(Calendar.HOUR_OF_DAY, 0);
		toDay.set(Calendar.MINUTE, 0);
		toDay.set(Calendar.SECOND, 0);
		toDay.set(Calendar.MILLISECOND, 0);
    	
    	filters.setGasDay(toDay.getTime());
 	}

	
	public void onClear() {
		filters = new MeteringQualityReportFilter();
		Calendar toDay = Calendar.getInstance(); 
		toDay.set(Calendar.HOUR_OF_DAY, 0);
		toDay.set(Calendar.MINUTE, 0);
		toDay.set(Calendar.SECOND, 0);
		toDay.set(Calendar.MILLISECOND, 0);
    	
    	filters.setGasDay(toDay.getTime());
    	
		if(getChangeSystemView().isOnshore()) {
	        if (items != null)
	            items.clear();

		} else {
	        if (itemsOffshore != null)
	        	itemsOffshore.clear();
		}		
	}
	
	public void onSearch() {
		
		// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	if (filters.getGasDay() == null) {
    		getMessages().addMessage(Constants.head_menu[7],
	    			new MessageBean(Constants.ERROR,
	    					msgs.getString("menu_metering_retrieving"), 
	    					msgs.getString("met_qreport_required_gas_day_error"), 
							Calendar.getInstance().getTime()));
	    	log.error("Gas Day cannot be empty.");
	    	return;
		}
    	
		if(getChangeSystemView().isOnshore()) {
	        items = service.search(filters);
		}
		else {
			itemsOffshore = service.searchOffshore(filters);
		}
	}

	
}