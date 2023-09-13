package com.atos.views.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.RowEditEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.nominations.RenominationIntradayBean;
import com.atos.beans.nominations.RenominationIntradayDialogBean;
import com.atos.beans.nominations.RenominationIntradayDialogDetBean;
import com.atos.filters.nominations.RenominationIntradayFilter;
import com.atos.services.MailService;
import com.atos.services.allocation.AllocationReviewService;
import com.atos.services.nominations.RenominationIntradayService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;


@ManagedBean(name="renominationIntradayView")
@ViewScoped
public class RenominationIntradayView extends CommonView implements Serializable {

	
	private static final long serialVersionUID = 2379627102030784947L;
	private static final Logger log = LogManager.getLogger(RenominationIntradayView.class);
	
	// Filters
	private RenominationIntradayFilter filters;
	
	// Main
	private List<RenominationIntradayBean> items;
	
	private RenominationIntradayDialogBean newReIntraday;
	
	@ManagedProperty("#{renominationIntradayService}")
    transient private RenominationIntradayService service;
     
    public void setService(RenominationIntradayService service) {
        this.service = service;
    }
    
    @ManagedProperty("#{messagesView}")
    private MessagesView messages;

    public void setMessages(MessagesView messages) {
        this.messages = messages;
    }
    
    @ManagedProperty("#{mailService}")
    transient private MailService mailService;

    public void setMailService(MailService mailService) {
    	this.mailService = mailService;
    }
    
    @ManagedProperty("#{AllocationReviewService}")
    transient private AllocationReviewService serviceAlloc;
    
	public void setServiceAlloc(AllocationReviewService serviceAlloc) {
		this.serviceAlloc = serviceAlloc;
	}
    
    public RenominationIntradayFilter getFilters() {
		return filters;
	}
    
	public void setFilters(RenominationIntradayFilter filters) {
		this.filters = filters;
	}

	public List<RenominationIntradayBean> getItems() {
		return items;
	}
	
	public RenominationIntradayDialogBean getNewReIntraday() {
		return this.newReIntraday;
	}

	public void setNewReIntraday(RenominationIntradayDialogBean newReIntraday) {
		this.newReIntraday = newReIntraday;
	}

	@PostConstruct
    public void init() {
		filters = new RenominationIntradayFilter();
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		filters.setIsShipper(getIsShipper());		
		if (getIsShipper()) {
			filters.setShipperId(getUser().getIdn_user_group());
		}
		this.newReIntraday = new RenominationIntradayDialogBean();
		if(getIsShipper()) {
			this.newReIntraday.setShipperId(getUser().getIdn_user_group());
		}
		this.newReIntraday = service.getNewReIntraday(this.newReIntraday);
    }
	
	public boolean checkFilters() {
		if ((filters.getShipperId()==null && !filters.isAllShippers())
			|| (filters.getShipperId()!=null && filters.isAllShippers()))
		{
			messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "All Shippers OR a specific shipper should be selected",
					"Error filtering data", Calendar.getInstance().getTime()));
			return false;
		}
		return true;
	}
	
	// Methods
		public void onSearch(){
			if(checkFilters()) {
				items = service.selectRenominationIntraday(filters);
				
			}
		}
		
		public void onClear(){
			init();
	    	
	        if (items != null) {
	            items.clear();
	        }
	    }
		
		public void accept(RenominationIntradayBean item) throws Exception {

			String error = "";
			try {
				int resProc = service.prorate(item, getUser(), getLanguage());
				if(resProc!=0) 
					messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "Error prorating",
							"Error prorating data", Calendar.getInstance().getTime()));
				else {
					messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.INFO, "Information saved successffully",
							"Data has been stored", Calendar.getInstance().getTime()));
				}
				item.setUsername(getUser().getUsername());
				String exit = service.accept(item);
				if(!exit.equals("0")) {
					messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "Error when renomination intraday change to status ACCEPTED",
							"Error accepting renomination intraday", Calendar.getInstance().getTime()));
					return;
				}
			} catch (Exception e) {
				error = e.getMessage();	
			}
			if(error.equals("")) {
				messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.INFO, "Information saved successffully",
					"Data has been stored", Calendar.getInstance().getTime()));
			}
			else {
				messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "Error prorating",
						"Error prorating data", Calendar.getInstance().getTime()));						
			}
			if(isShipper()) {
				sendMail(item);
			}
			onClear();
			onSearch();
		}

		public void reject(RenominationIntradayBean item) {
			item.setUsername(getUser().getUsername());
			String exit = service.reject(item);
			if(!exit.equals("0")) {
				messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "Error when renomination intraday change to status REJECT",
						"Error rejecting renomination intraday", Calendar.getInstance().getTime()));
				return;
			}
			onSearch();
		}

		 public void cancel() {
			 if(getIsShipper()) {
					this.newReIntraday.setShipperId(getUser().getIdn_user_group());
				} else {
					this.newReIntraday = service.getNewReIntraday(new RenominationIntradayDialogBean());
				}
		    }
		    		
		
		public void save() throws Exception {
			
			if ((newReIntraday.getShipperId()==null && !newReIntraday.isAllShippers())|| (newReIntraday.getShipperId()!=null && newReIntraday.isAllShippers())){
				messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "All Shippers OR a specific shipper should be selected",
						"Error filtering data", Calendar.getInstance().getTime()));
				return;
			}
			if(newReIntraday.getHour()==null) {
				messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "You must select a hour",
						"Error filtering data", Calendar.getInstance().getTime()));
				return;
			}
			for(int i=0;i<newReIntraday.getDetail().size();i++) {
				RenominationIntradayDialogDetBean det = newReIntraday.getDetail().get(i);
				if(det.getUpdated().equals("S")) {
					if(det.getMinutes()==null){
						messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error updating","Period are required", Calendar.getInstance().getTime()));
						return;
					}
					if(det.getEnergy()==null || det.getVolume()==null){
						messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error updating","Energy and Volume are required", Calendar.getInstance().getTime()));
						return;
					}
				}
			}
			newReIntraday.setUsername(getUser().getUsername());
			String res = service.save(newReIntraday);
			if(res.equals("0")) {
				messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.INFO, "Information saved successffully",
						"Data has been stored", Calendar.getInstance().getTime()));
			} else if(res.equals("-1")) {
				messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "Error inserting header",
						"Error inserting data of header", Calendar.getInstance().getTime()));
				return;
			} else if(res.equals("-2")) {
				messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "Error inserting detail",
						"Error inserting data of header", Calendar.getInstance().getTime()));
				return;
			}
			this.newReIntraday = service.getNewReIntraday(new RenominationIntradayDialogBean());

			items = service.selectRenominationIntraday(filters);
		}

		public void onRowEdit(RowEditEvent event) {
			
			RenominationIntradayDialogDetBean det = (RenominationIntradayDialogDetBean) event.getObject();
			det.setUpdated("S");
			if(det.getMinutes()==null || det.getMinutes().equals("")){
				messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error updating","Period are required", Calendar.getInstance().getTime()));
				return;
			}
			if(det.getEnergy()==null || det.getVolume()==null){
				messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error updating","Energy and Volume are required", Calendar.getInstance().getTime()));
				return;
			}
		}
		
		public void onRowCancel(RowEditEvent event) {
			RenominationIntradayDialogDetBean det = (RenominationIntradayDialogDetBean) event.getObject();
			det.setMinutes(null);
			det.setEnergy(null);
			det.setVolume(null);
		}
		
		// Este booleano marcara si esta habilitado el filtro de shipper (isOperator = true) o esta deshabilitado, 
		// con un shipper fijo.
		public boolean getIsShipper() {
			return getUser().isUser_type(Constants.SHIPPER);
		}
		
		// Para los elementos del combo del filtro de shippers.
		public Map<BigDecimal, Object> getShipperIds() {
			return service.selectShipperId();
		}

		// Para los elementos del combo del filtro de status.
		public Map<String, String> getStatus() {
			return service.selectStatus();
		}
		
		// Para los elementos del combo del filtro de status.
		public Map<BigDecimal, String> getHours() {
			return service.selectHours();
		}
		
		
		public boolean disabledComboShippers() {
			if(filters.isAllShippers()) {
				filters.setShipperId(null);
				return true;
			}
			else 
				return false;
		}
		
		public boolean disabledComboShippersDialog() {
			if(newReIntraday.isAllShippers()) {
				newReIntraday.setShipperId(null);
				return true;
			}
			else 
				return false;
		}
		
		public void sendMail(RenominationIntradayBean bean) {
			
			List<String> points = new ArrayList<String> ();
			for(int i=0;i<bean.getDetail().size();i++) {
				points.add(bean.getDetail().get(i).getPoint_code());
			}
			
			HashMap<String,String> values = new HashMap<String,String>();

			
			values.put("1", bean.getUsername());
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String dateString = df.format(bean.getGas_day());
			values.put("2", dateString);
			BigDecimal uno = new BigDecimal(1);
			values.put("3", (bean.getHour().subtract(uno)).toString());
			values.put("4", points.stream().collect(
		            Collectors.joining(",")));
			String texto = "Shipper:"+values.get("1")+",Gas day:"+values.get("2")+",Time:"+values.get("3")+
					",Nomination Points:"+values.get("4");
			
			BigDecimal tso = new BigDecimal(-1);
			try {
				tso = serviceAlloc.getDefaultOperatorId(getUser(), getLanguage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			mailService.sendEmail("DAILY_ADJUST.SUBMISSION", values, getChangeSystemView().getIdn_active(), tso);
			//Para comprobar los valores que se pasan en el email
			messages.addMessage(Constants.head_menu[4],
					new MessageBean(Constants.INFO, "Mail values", texto, Calendar.getInstance().getTime())); 
		}

}
