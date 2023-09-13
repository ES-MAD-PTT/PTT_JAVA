package com.atos.views.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.atos.beans.MessageBean;
import com.atos.beans.nominations.QualityPublicationShipperBean;
import com.atos.filters.nominations.QualityPublicationShipperFilter;
import com.atos.services.nominations.QualityPublicationShipperService;
import com.atos.utils.Constants;
import com.atos.views.ChangeSystemView;
import com.atos.views.MessagesView;

@ManagedBean(name = "QualityPublicationShipperView")
@ViewScoped
public class QualityPublicationShipperView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8374191689908278581L;

	private QualityPublicationShipperFilter filters;
	private List<QualityPublicationShipperBean> items;

	private static final Logger log = LogManager.getLogger("com.atos.views.nominations.QualityPublicationView");

	@ManagedProperty("#{qualityPublicationShipperService}")
	transient private QualityPublicationShipperService service;

	public void setService(QualityPublicationShipperService service) {
		this.service = service;
	}

	@ManagedProperty("#{messagesView}")
	private MessagesView messages;

	public void setMessages(MessagesView messages) {
		this.messages = messages;
	}

	@ManagedProperty("#{changeSystemView}")
	private ChangeSystemView systemView;

	public void setSystemView(ChangeSystemView systemView) {
		this.systemView = systemView;
	}

	// geters/seters
	public QualityPublicationShipperFilter getFilters() {
		return filters;
	}

	public void setFilters(QualityPublicationShipperFilter filters) {
		this.filters = filters;
	}

	public List<QualityPublicationShipperBean> getItems() {
		return items;
	}

	public void setItems(List<QualityPublicationShipperBean> items) {
		this.items = items;
	}

	@PostConstruct
	public void init() {

		filters = new QualityPublicationShipperFilter();

		Calendar cTomorrow = Calendar.getInstance();
		cTomorrow.set(Calendar.HOUR_OF_DAY, 0);
		cTomorrow.set(Calendar.MINUTE, 0);
		cTomorrow.set(Calendar.SECOND, 0);
		cTomorrow.set(Calendar.MILLISECOND, 0);
		cTomorrow.add(Calendar.DAY_OF_MONTH, 1);
		filters.setStartDate(cTomorrow.getTime());
		filters.setEndDate(cTomorrow.getTime());
		
		Object[] tmpObjectArray = getAreasSystem().keySet().toArray();
		filters.setAreaId(Arrays.copyOf(tmpObjectArray, tmpObjectArray.length, BigDecimal[].class));

		items = service.search(filters);
	}

	public Map<BigDecimal, Object> getZones() {
		return service.selectZones();
	}

	public Map<BigDecimal, Object> getAreasSystem() {
		return service.selectAreasSystem(systemView.getIdn_active());
	}

	public Map<BigDecimal, Object> getSelectShippers() {
		return service.selectShippers();
	}	
	
	public void onSearch() {
		// Utilizo un ResourceBundle local por si el scope fuera Session o
		// Application. En estos casos no se actualizaria el idioma.
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		if ((filters.getStartDate() != null) && (filters.getEndDate() != null)) {
			if (filters.getStartDate().after(filters.getEndDate())) {
				messages.addMessage(Constants.head_menu[4],
						new MessageBean(Constants.ERROR, msgs.getString("menu_quality_publication"),
								msgs.getString("dates_order"), Calendar.getInstance().getTime()));
				log.error("Start date must be previous or equal to end date.");
				return;
			}
		}

		items = service.search(filters);
	}

	public void onClear() {
		filters = new QualityPublicationShipperFilter();
		Calendar cTomorrow = Calendar.getInstance();
		cTomorrow.set(Calendar.HOUR_OF_DAY, 0);
		cTomorrow.set(Calendar.MINUTE, 0);
		cTomorrow.set(Calendar.SECOND, 0);
		cTomorrow.set(Calendar.MILLISECOND, 0);
		cTomorrow.add(Calendar.DAY_OF_MONTH, 1);
		filters.setStartDate(cTomorrow.getTime());
		filters.setEndDate(cTomorrow.getTime());

		Object[] tmpObjectArray = getAreasSystem().keySet().toArray();
		filters.setAreaId(Arrays.copyOf(tmpObjectArray, tmpObjectArray.length, BigDecimal[].class));

		if (items != null) {
			items.clear();
		}

		items = service.search(filters);
	}
}
