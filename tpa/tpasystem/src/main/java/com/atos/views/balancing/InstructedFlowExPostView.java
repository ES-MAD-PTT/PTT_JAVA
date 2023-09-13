package com.atos.views.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.atos.beans.MessageBean;
import com.atos.beans.UserBean;
import com.atos.beans.balancing.InstructedFlowExPostBean;
import com.atos.filters.balancing.InstructedFlowExPostFilter;
import com.atos.services.balancing.InstructedFlowExPostService;
import com.atos.utils.Constants;
import com.atos.views.ChangeSystemView;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;


@ManagedBean(name="instructedFlowExPostView")
@ViewScoped
public class InstructedFlowExPostView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(InstructedFlowExPostView.class);
	
	private InstructedFlowExPostFilter filters;
	private InstructedFlowExPostBean newInstructedFlowExPost;
	private List<InstructedFlowExPostBean> items;
	
	@ManagedProperty("#{instructedFlowExPostService}")
    transient private InstructedFlowExPostService service;
    
	@ManagedProperty("#{messagesView}")
    private MessagesView messages;

	private Map<BigDecimal, Object> comboContracts;
		
	private Calendar firstOpenDay;
	
	@ManagedProperty("#{userBean}")
    private UserBean userBean;
	public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

	@ManagedProperty("#{changeSystemView}")
    private ChangeSystemView systemView;
	
	public void setSystemView(ChangeSystemView systemView) {
		this.systemView = systemView;
	}
		
	public Calendar getfirstOpenDay() {
		return firstOpenDay;
	}

	public void setfirstOpenDay(Calendar firstOpenDay) {
		this.firstOpenDay = firstOpenDay;
	}

	 public InstructedFlowExPostFilter getFilters() {
		return filters;
	}

	public void setFilters(InstructedFlowExPostFilter filters) {
		this.filters = filters;
	}
	
	public InstructedFlowExPostBean getNewInstructedFlowExPost() {
		return newInstructedFlowExPost;
	}

	public void setNewInstructedFlowExPost(InstructedFlowExPostBean newInstructedFlowExPost) {
		this.newInstructedFlowExPost = newInstructedFlowExPost;
	}
	
	
	public List<InstructedFlowExPostBean> getItems() {
		return items;
	}
	
	public void setItems(List<InstructedFlowExPostBean> items) {
		this.items = items;
	}
	 
    public void setService(InstructedFlowExPostService service) {
        this.service = service;
    }
    
    
    public void setMessages(MessagesView messages) {
        this.messages = messages;
    }
    

	@PostConstruct
    public void init() {
    	filters = new InstructedFlowExPostFilter();
    	newInstructedFlowExPost = new InstructedFlowExPostBean();
     	
    	
    	if(userBean.isUser_type(Constants.SHIPPER)){
			filters.setIdn_user_group(userBean.getIdn_user_group());
    	}
    	
     	Calendar today= Calendar.getInstance();
     	today.set(Calendar.HOUR_OF_DAY, 0);
     	today.set(Calendar.MINUTE, 0);
     	today.set(Calendar.SECOND, 0);
     	today.set(Calendar.MILLISECOND, 0);
    	
    	filters.setStartDate(today.getTime());
		filters.setEndDate(today.getTime());
    	
		firstOpenDay=gettingValidOpenDay();
    	
    	//inicializamos con el dia de hoy
    	newInstructedFlowExPost.setGas_day(Calendar.getInstance().getTime());
    	
    }
	
	
    public Calendar gettingValidOpenDay(){
    	
       	Calendar day= Calendar.getInstance();

		HashMap<String, Object> params = new HashMap<>();
		params.put("closingTypeCode", "DEFINITIVE");
		params.put("idnSystem", getChangeSystemView().getIdn_active());
		params.put("sysCode", getChangeSystemView().getSystem());
		day.setTime(service.getFirstOpenDay(params));
    	day.set(Calendar.HOUR_OF_DAY, 0);
    	day.set(Calendar.MINUTE, 0);
    	day.set(Calendar.SECOND, 0);
    	day.set(Calendar.MILLISECOND, 0);
        return day;
    }

	
	
	public Map<BigDecimal, Object> getShippersFilter() {
		// si ha entrado un shipper sacamos en el combo solo a él
		if(userBean.isUser_type(Constants.SHIPPER)){
			return service.selectShippersFilter(userBean.getIdn_user_group());
    	} else {
    		//si no es shipper sacamos a todos los shipper
    		return service.selectShippersFilter();
    	}
	}
		
	public Map<BigDecimal, Object> getShippersNew() {
		return service.selectShippersNew();
    }
	

/*	offshore
 * public Map<BigDecimal, Object> getContracts() {
		return service.selectContracts(filters);
		
	}
*/	public Map<BigDecimal, Object> getContractsSystem() {
		filters.setIdn_system(systemView.getIdn_active());
		return service.selectContracts(filters);
		
	}
	
	/* offshore
	public Map<BigDecimal, Object> getContractsNew() {
		return service.selectContracts(newInstructedFlowExPost);		
	}
	*/
	public Map<BigDecimal, Object> getContractsNewSystem() {
		newInstructedFlowExPost.setIdn_system(systemView.getIdn_active());
		return service.selectContracts(newInstructedFlowExPost);		
	}
	
	public Map<BigDecimal, Object> getConcepts() {
		return service.selectConcepts();
		
	}
	
	
	public Map<BigDecimal, Object> getZones(String systemCode) {
		return service.selectZones(systemCode);
	}
	
	// Methods
	public void onSearch(){
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_instructedFlowExPost1').clearFilters()");
		
	
		if (filters.getEndDate()!=null &&  filters.getStartDate()!=null ) {
			if(filters.getStartDate().after(filters.getEndDate())){
		    	messages.addMessage(Constants.head_menu[9],new MessageBean(Constants.ERROR,"Error dates", "From must be previous to To date", Calendar.getInstance().getTime()));
		    	return;
		    }
		}
		
		//offshore
		filters.setIdn_system(systemView.getIdn_active());
		
		items = service.selectInstructedFlowExPosts(filters);
       
	}
	
	public void onClear(){
	  //  RequestContext.getCurrentInstance().reset("form");
		filters = new InstructedFlowExPostFilter();
		filters.setStartDate(Calendar.getInstance().getTime());
		filters.setEndDate(Calendar.getInstance().getTime());
		
        if (items != null) {
            items.clear();
        }
        
        RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_instructedFlowExPost1').clearFilters()");
		
		
    }

  public void cancel() {
       // RequestContext.getCurrentInstance().reset("formNewEntity");
    	initNewBean();
    }
    
    
    public void initNewBean(){
    	newInstructedFlowExPost = new InstructedFlowExPostBean();
    	//newInstructedFlowExPost.setGas_day(firstOpenDay.getTime());
    	newInstructedFlowExPost.setGas_day(Calendar.getInstance().getTime());
    	
    }
    
    public void save(){
  	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	
    	// LA FECHA SEA POSTERIOR O IGUAL AL PRIMER DE CON BALANCE ABIERTO. 
    	if(newInstructedFlowExPost.getGas_day()!=null ){
			// Mientras estamos en esta pantalla, se pueden cerrar balances. Por
			// eso, esta fecha se debe buscar cada vez se pulse el botón Save.
    		firstOpenDay=gettingValidOpenDay();
	    	if(newInstructedFlowExPost.getGas_day().before(firstOpenDay.getTime())){
	    		String firstOpenDayFormatted = format.format(firstOpenDay.getTime());
		    	messages.addMessage(Constants.head_menu[9],new MessageBean(Constants.ERROR,"InstructedFlowExPost Not Inserted", "Gas Day must be later than: "  + firstOpenDayFormatted +". ", Calendar.getInstance().getTime()));
		    	return;
			}
    	}
    	
    	
    	
		String gasDayFormatted = format.format(newInstructedFlowExPost.getGas_day().getTime());
		
    	newInstructedFlowExPost.setContrac_code(service.getContractCode(newInstructedFlowExPost.getIdn_contract()));
    	
    	
    	String error = "0";
		try {
			error =  service.insertInstructedFlowExPost(newInstructedFlowExPost);
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		
		if(error!=null && error.equals("0")){
			messages.addMessage(Constants.head_menu[9],new MessageBean(Constants.INFO,"InstructedFlowExPost Inserted.", "InstructedFlowExPost Inserted ok. " + "Gas Day: " + gasDayFormatted +". Contract: " + newInstructedFlowExPost.getContrac_code(), Calendar.getInstance().getTime()));
			log.info("InstructedFlowExPost Inserted ok" + newInstructedFlowExPost.toString(), Calendar.getInstance().getTime());
		}else if(error!=null && error.equals("-1")){
			messages.addMessage(Constants.head_menu[9],new MessageBean(Constants.ERROR,"InstructedFlowExPost Not Inserted.", "Error inserting instructedFlowExPost. The InstructedFlowExPost Gas Day: " + newInstructedFlowExPost.getGas_day() +" already exists in the System.", Calendar.getInstance().getTime()));
			log.info("InstructedFlowExPost Inserted", "Error inserting instructedFlowExPost. The " + newInstructedFlowExPost.toString() +" already exists in the System ", Calendar.getInstance().getTime());
		}
		

    	items = service.selectInstructedFlowExPosts(filters);
       	newInstructedFlowExPost = new InstructedFlowExPostBean();
    	newInstructedFlowExPost.setGas_day(Calendar.getInstance().getTime());
    	
    }
    
 
	// Este booleano marcara si esta habilitado el filtro de shipper (isOperator = true) o esta deshabilitado, 
	// con un shipper fijo.
	public boolean getIsShipper() {
		return userBean.isUser_type(Constants.SHIPPER);
	}
	
}
