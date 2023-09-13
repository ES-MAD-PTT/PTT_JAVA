package com.atos.views.balancing;

import java.io.Serializable;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.balancing.IntradayAccImbalanceInventoryAdjustmentBean;
import com.atos.filters.balancing.IntradayAccImbalanceInventoryAdjustmentFilter;

import com.atos.services.balancing.IntradayAccImbalanceInvAdjustmentService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="intradayAccImbInvAdjView")
@ViewScoped
public class IntradayAccImbalanceInventoryAdjustmentView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(IntradayAccImbalanceInventoryAdjustmentView.class);
	
	private IntradayAccImbalanceInventoryAdjustmentFilter filters;
	private IntradayAccImbalanceInventoryAdjustmentBean newIntradayAccImbalanceInventoryAdjustment;
	private List<IntradayAccImbalanceInventoryAdjustmentBean> items;
	
	@ManagedProperty("#{intradayAccImbInvAdjService}")
    transient private IntradayAccImbalanceInvAdjustmentService service;
		
	private Calendar sysdate;
	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	public IntradayAccImbalanceInventoryAdjustmentFilter getFilters() {
		return filters;
	}

	public void setFilters(IntradayAccImbalanceInventoryAdjustmentFilter filters) {
		this.filters = filters;
	}
	
	public IntradayAccImbalanceInventoryAdjustmentBean getNewIntradayAccImbalanceInventoryAdjustment() {
		return newIntradayAccImbalanceInventoryAdjustment;
	}

	public void setNewIntradayAccImbalanceInventoryAdjustmentFilter(IntradayAccImbalanceInventoryAdjustmentBean newIntradayAccImbalanceInventoryAdjustment) {
		this.newIntradayAccImbalanceInventoryAdjustment = newIntradayAccImbalanceInventoryAdjustment;
	}
	
	public List<IntradayAccImbalanceInventoryAdjustmentBean> getItems() {
		return items;
	}
	
	public void setItems(List<IntradayAccImbalanceInventoryAdjustmentBean> items) {
		this.items = items;
	}
	 
    public void setService(IntradayAccImbalanceInvAdjustmentService service) {
        this.service = service;
    }
    

	@PostConstruct
    public void init() {
    	filters = new IntradayAccImbalanceInventoryAdjustmentFilter();
    	//LOOKING TO THE SYSDATE parameter BD
    	sysdate= gettingValidDateStart();
    	
    	//StartDate New => sysdate +n
    	initNewBean();
    }
	
	//sysdate 
    public Calendar gettingValidDateStart(){
    	
    	Calendar sysdate = Calendar.getInstance(); 
    	filters.setGasDay(sysdate.getTime());
    	
    	sysdate.setTime(sysdate.getTime());
    	sysdate.set(Calendar.HOUR_OF_DAY, 0);
    	sysdate.set(Calendar.MINUTE, 0);
    	sysdate.set(Calendar.SECOND, 0);
    	sysdate.set(Calendar.MILLISECOND, 0);
    	
        return sysdate;
    }


	
	// Methods
	public void onSearch(){
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_intradayAccImbInvAdj1').clearFilters()");
		
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectIntradayAccImbalanceInvAdjustment(filters);
       
	}
	
	public void onClear(){
		init();
        if (items != null) {
            items.clear();
        }
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('w_intradayAccImbInvAdj1').clearFilters()");
    }


	public void onRowEdit(RowEditEvent event) {
		IntradayAccImbalanceInventoryAdjustmentBean bean = (IntradayAccImbalanceInventoryAdjustmentBean) event.getObject();
	    
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("IntradayAccImbalanceInventoryAdjustment") };
    	String summaryMsgOk =  getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("update_noOk", params);
		
    	String error = "0";
		try {
			//Es igual que el insert porque se cambia el version_date
			error = service.insertIntradayAccImbalanceInvAdjustment(bean);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		String[] par2 = {msgs.getString("IntradayAccImbalanceInventoryAdjustment")};
		
		if(error!=null && error.equals("0")){
    		String msg =  getMessageResourceString("update_ok", par2);
			getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
    		log.info("Intraday Acc Imbalance Inventory Adjustment Updated", "Intraday Acc Imbalance Inventory Adjustment Updated: " + bean.toString(), Calendar.getInstance().getTime());
    	}else if (error!=null && error.equals("-1")){
    		String msg =  getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.error("Intraday Acc Imbalance Inventory Adjustment Not Updated", "Error updating Intraday Acc Imbalance Inventory Adjustment " + bean.toString(), Calendar.getInstance().getTime());
    		return;
    	}
		
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		
		//offshore
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		items = service.selectIntradayAccImbalanceInvAdjustment(filters);
		requestContext.execute("PF('w_intradayAccImbInvAdj1').filter()"); 
		

    }
     
    public void onRowCancel(RowEditEvent event) {
    }
    
  
    
    public void cancel() {
    	initNewBean();
    }
    
    
    public void initNewBean(){
    	newIntradayAccImbalanceInventoryAdjustment = new IntradayAccImbalanceInventoryAdjustmentBean();
    	newIntradayAccImbalanceInventoryAdjustment.setGasDay(sysdate.getTime());  
    }
    
    public void save(){
  	
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	
    	String[] params = {msgs.getString("IntradayAccImbalanceInventoryAdjustment") };
    	String summaryMsgOk =  getMessageResourceString("insert_ok", params);
    	String summaryMsgNotOk=  getMessageResourceString("insert_noOk", params); 
    	
    	if(newIntradayAccImbalanceInventoryAdjustment.getGasDay()==null
    			|| newIntradayAccImbalanceInventoryAdjustment.getEast()==null 
    					|| newIntradayAccImbalanceInventoryAdjustment.getWest()==null )
    	{
    		String msg = "Gas day, west and east are required";
			getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
    		log.error("Gas day, west and east are required", "Error new Intraday Acc Imbalance Inventory Adjustment" + newIntradayAccImbalanceInventoryAdjustment.toString(), Calendar.getInstance().getTime());
    		return;
    	}
    	
    	String error = "0";
		try {
			error =  service.insertIntradayAccImbalanceInvAdjustment(newIntradayAccImbalanceInventoryAdjustment);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		String[] par2 = {msgs.getString("IntradayAccImbalanceInventoryAdjustment") };
		
		if(error!=null && error.equals("0")){
			String msg = getMessageResourceString("insert_ok", par2);
			getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("Intraday Acc Imbalance Inventory Adjustment Inserted ok" + newIntradayAccImbalanceInventoryAdjustment.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-1")){
			String msg = getMessageResourceString("error_aready_exit", par2); //error_aready_exit = The ID {0} already exists in the System
			getMessages().addMessage(Constants.head_menu[9], new MessageBean(Constants.ERROR,summaryMsgNotOk,msg, Calendar.getInstance().getTime()));
			log.error("Intraday Acc Imbalance Inventory Adjustment Inserted", "Error inserting data. The " + newIntradayAccImbalanceInventoryAdjustment.toString() +" already exists in the System ", Calendar.getInstance().getTime());
			return;
		}
		
		//offshore
		//filters.setIdn_system(getChangeSystemView().getIdn_active());
    	//items = service.selectIntradayAccImbalanceInvAdjustment(filters);
		initNewBean(); 
		onSearch();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_intradayAccImbInvAdj1').filter()");
    }	
    
    
    public boolean renderItemEditor(IntradayAccImbalanceInventoryAdjustmentBean item){
     	
    	if (item.getGasDay().before(sysdate.getTime())) {
    		return false;
    		
    	}else{
    		return true;
    	}
    }
    
    public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}

}
