package com.atos.views.nominations;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.apache.shiro.subject.Subject;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.StreamedContent;

import com.atos.beans.MessageBean;
import com.atos.beans.nominations.NominationDetailBean;
import com.atos.beans.nominations.NominationDetailWeekBean;
import com.atos.beans.nominations.NominationHeaderBean;
import com.atos.beans.nominations.NominationQualityGasWeekBean;
import com.atos.beans.nominations.QualityTableBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.nominations.NominationFilter;
import com.atos.services.MailService;
import com.atos.services.nominations.WeeklyNominationService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="weekNom")
@ViewScoped
public class WeeklyNominationView extends CommonView {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8157362009956810120L;
	private static final Logger log = LogManager.getLogger(WeeklyNominationView.class);

	// Filters
	private NominationFilter filters;

	private int dayStartWeek;
	
	// Main
	private List<NominationHeaderBean> items;
	
	// Selected register
	private NominationHeaderBean selected;

	// Detail tabs
	private List<NominationDetailWeekBean> east_tab, west_tab, mix_tab, o_east_tab;
	
	// Quality tab
	private List<NominationQualityGasWeekBean> east_quality, west_quality, mix_quality;
	
	// Quality gas week
	private List<QualityTableBean> quality;

	// Detail Shipper comment
	private String shipperLongMessage;
	
	// Detail submission comment
	private String longMessage;

	// To control shiro permissions.
	private Subject subject;
	private static final String strManagementPermission = ".NOMINATIONS.WEEKLY.MANAGEMENT.MAN";	
	private static final String strModificationPermission = ".NOMINATIONS.WEEKLY.MANAGEMENT.MOD";
	
    @ManagedProperty("#{weeklyNominationService}")
    transient private WeeklyNominationService service;
     
    public void setService(WeeklyNominationService service) {
        this.service = service;
    }
    
    @ManagedProperty("#{mailService}")
    transient private MailService mailService;

    public void setMailService(MailService mailService) {
         this.mailService = mailService;
    }
    
    public NominationFilter getFilters() {
		return filters;
	}

	public void setFilters(NominationFilter filters) {
		this.filters = filters;
	}

	public int getDayStartWeek() {
		return dayStartWeek;
	}

	public void setDayStartWeek() {
		this.dayStartWeek = service.selectStartDayOfWeek();
	}

	public List<NominationHeaderBean> getItems() {
		return items;
	}

	public NominationHeaderBean getSelected() {
		return selected;
	}

	public void setSelected(NominationHeaderBean selected) {
		this.selected = selected;
	}

	public List<NominationDetailWeekBean> getEast_tab() {
		return east_tab;
	}

	public List<NominationDetailWeekBean> getWest_tab() {
		return west_tab;
	}

	public List<NominationDetailWeekBean> getMix_tab() {
		return mix_tab;
	}

	public List<NominationQualityGasWeekBean> getEast_quality() {
		return east_quality;
	}

	public List<NominationQualityGasWeekBean> getWest_quality() {
		return west_quality;
	}

	public List<NominationQualityGasWeekBean> getMix_quality() {
		return mix_quality;
	}

	public List<NominationDetailWeekBean> getO_east_tab() {
		return o_east_tab;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public List<QualityTableBean> getQuality() {
		List<QualityTableBean> list = new ArrayList<QualityTableBean>();
		if(quality!=null){
			for(int i=0;i<quality.size();i++){
				if(quality.get(i).getIdn_pipeline_system().equals(getChangeSystemView().getIdn_active())){
					list.add(quality.get(i));
				}
			}
		}
		return list;
	}

	public String getShipperLongMessage() {
		return shipperLongMessage;
	}

	public void setShipperLongMessage(String shipperLongMessage) {
		this.shipperLongMessage = shipperLongMessage;
	}
	
	public String getLongMessage() {
		return longMessage;
	}

	public void setLongMessage(String longMessage) {
		this.longMessage = longMessage;
	}

	@PostConstruct
    public void init() {
		initFilters();    	
    	subject = SecurityUtils.getSubject();
    }
	
	private void initFilters(){
    	filters = new NominationFilter();
		
		filters.setCategory_code(Constants.NOMINATION);
		filters.setTerm_code(Constants.WEEKLY);
		filters.setType_code(Constants.SHIPPER);
		filters.setIdn_pipeline_system(getChangeSystemView().getIdn_active());
		
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
    	this.setDayStartWeek();
    	
    	if(getUser().isUser_type(Constants.SHIPPER)){
    		filters.setShipper_code(getUser().getIdn_user_group().toString());
    	}
    	filters.setUser(getUser().getUsername());
	}
	
	public Map<String, Object> getShipperCodes() {
		return service.selectShipperIdNominations(filters);
	}

	public Map<String, Object> getContractCodes() {
    	// La fecha final del filtro se calcula en funcion de la inicial, justo antes de hacer la consulta.
		Calendar cal = Calendar.getInstance();
		cal.setTime(filters.getStart_date());
		cal.add(Calendar.DAY_OF_MONTH, 6);
		filters.setEnd_date(cal.getTime());
		
		return service.selectContractCodeByUser(filters);
	}

	// Methods
	public void onSearch(){
    	// La fecha final del filtro se calcula en funcion de la inicial, justo antes de hacer la consulta.
		Calendar cal = Calendar.getInstance();
		cal.setTime(filters.getStart_date());
		cal.add(Calendar.DAY_OF_MONTH, 6);
		filters.setEnd_date(cal.getTime());
		
		items = service.selectNomination(filters);
	}
	
	public void onClear(){
		initFilters();
    	
        if (items != null) {
            items.clear();
        }
    }
	
	public void onRowSelect(NominationHeaderBean bean) {
		this.setSelected(bean);
		
		Map<String, BigDecimal> map = service.selectZonesNomination();
		
		if(getChangeSystemView().isOnshore()){
			bean.setIdn_zone(map.get(Constants.EAST));
			east_tab  = service.selectDetailWeeklyNomination(bean);
			east_quality = service.selectQualityGasWeekNomination(bean); 
	
			bean.setIdn_zone(map.get(Constants.WEST));
			west_tab  = service.selectDetailWeeklyNomination(bean);
			west_quality = service.selectQualityGasWeekNomination(bean);
			
			bean.setIdn_zone(map.get(Constants.MIX));
			mix_tab  = service.selectDetailWeeklyNomination(bean);
			mix_quality = service.selectQualityGasWeekNomination(bean);
		}
		else {
			bean.setIdn_zone(map.get(Constants.O_EAST));
			o_east_tab  = service.selectDetailWeeklyNomination(bean);
			// En offshore no se muestran Zone Quality Parameters.
			//o_east_quality = service.selectQualityGasWeekNomination(bean); 
		}
		
		quality = service.selectQualityNominationTable(bean);
    }
	
	public StreamedContent getShipperFile() {
		if(selected==null){
			getMessages().addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Downloading file", "There is not a register selected", Calendar.getInstance().getTime()));
	    	return null;
		} else {
			if(selected.getIdn_shipper_file()==null){
				getMessages().addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Downloading file", "There is not a file to download", Calendar.getInstance().getTime()));
		    	return null;
			}
		}
		TreeMap<String,BigDecimal> map = new TreeMap<String,BigDecimal>();
		map.put("idn_operation_file", selected.getIdn_shipper_file());
		
		return service.getFile(map);
	}
	public StreamedContent getIntermediateFile() {
		if(selected==null){
			getMessages().addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Downloading file", "There is not a register selected", Calendar.getInstance().getTime()));
	    	return null;
		} else {
			if(selected.getIdn_operator_file()==null){
				getMessages().addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Downloading file", "There is not a file to download", Calendar.getInstance().getTime()));
		    	return null;
			}
		}
		TreeMap<String,BigDecimal> map = new TreeMap<String,BigDecimal>();
		map.put("idn_operation_file", selected.getIdn_operator_file());
		
		return service.getFile(map);
	}
	public boolean compareDates(Date date,int addDay){
		if(date==null){
			return true;
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if(addDay!=0){
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		Date sysdate = cal.getTime();
		
		if(sysdate.after(date)){
			return false; // posterior a sysdate
		} else {
			return true; // anterior a sysdate
		}
		
	}
	
	public void onRowEdit(RowEditEvent event) {
		if(getChangeSystemView().isOnshore()){
			service.prorateCalculation(east_tab, east_quality);
			service.prorateCalculation(west_tab, west_quality);
			service.prorateCalculation(mix_tab, mix_quality);
		}
		// En offshore no se muestran Zone Quality Parameters.
		//service.prorateCalculation(o_east_tab, o_east_quality);
	}
	
	public void onRowCancel(RowEditEvent event) {
		
    }

/*	public StreamedContent getSave(){
		service.prorateCalculation(east_tab, east_quality);
		service.prorateCalculation(west_tab, west_quality);
		service.prorateCalculation(mix_tab, mix_quality);
		service.prorateCalculation(o_east_tab, o_east_quality);
		
		ByteArrayOutputStream ba = service.generateXlsx(selected,east_tab, west_tab, mix_tab,o_east_tab,east_quality, west_quality, mix_quality,o_east_quality,quality);
		if(ba==null){
			messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Downloading file", "File empty", Calendar.getInstance().getTime()));
			return null;
		}
		ByteArrayInputStream bai = new ByteArrayInputStream(ba.toByteArray());
		return new DefaultStreamedContent(bai, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "intermediate.xlsx");
	}*/
	public void accept(){
		executeSave(Constants.ACCEPT);
	}
	public void reject(){
		executeSave(Constants.REJECT);
	}
	public void save(){
		// Para que cuando haga SAVE no se guarden estos comentarios en la nueva version de la nominacion, se ponen aqui a null.
		selected.setOperator_comments(null);

		executeSave(Constants.SAVE);
	}
	public void executeSave(String action){

    	String summaryMsg = null;
    	String finalMsg = null;
    	int msgType = -1;

		// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
		if(getChangeSystemView().isOnshore()){
	    	service.prorateCalculation(east_tab, east_quality);
			service.prorateCalculation(west_tab, west_quality);
			service.prorateCalculation(mix_tab, mix_quality);
		}
		// En offshore no se muestran Zone Quality Parameters.
		//service.prorateCalculation(o_east_tab, o_east_quality);
		
		ByteArrayOutputStream ba = service.generateXlsx(selected,east_tab, west_tab, mix_tab, o_east_tab,
														east_quality, west_quality, mix_quality, quality,
														getChangeSystemView());
		if(ba==null){
			getMessages().addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Downloading file", "File empty", Calendar.getInstance().getTime()));
			return;
		}

		try {
			finalMsg = service.saveFile(ba.toByteArray(),getChangeSystemView().getIdn_active(), action, filters.getStart_date(), selected);
			summaryMsg = msgs.getString("data_stored");
			msgType = Constants.INFO;
			
			// Se refrescan los datos de la pantalla, cargando la ultima version de nominacion recien guardada.
			// Si se hubiera producido un error antes, al guardar, no se refrescan los datos de la pantalla.
			selected = service.selectMaxVersionNomination(selected.getNomination_code());
			onRowSelect(selected);	
			sendMail(action);
		} 
		catch (ValidationException ve) {
			summaryMsg = msgs.getString("validation_error");
			finalMsg = ve.getMessage();
			msgType = Constants.ERROR;		
		}
		catch (Exception e) {
			summaryMsg = msgs.getString("saving_data_error");
			finalMsg = msgs.getString("data_not_stored_error") + " " + msgs.getString("internal_error");
			msgType = Constants.ERROR;

    		// Se guarda el detalle del error tecnico.
	    	log.error(e.getMessage(), e);			
		} 
		
		// Si se produce un error en el mensaje final, no se da por erronea la operacion.
		getMessages().addMessage(Constants.head_menu[4],
				new MessageBean(msgType, summaryMsg, finalMsg, Calendar.getInstance().getTime()));
	}
	
	public String formatAddDate(Date _date, int _daysToAdd) {

		String strDate = null;
		
		if(_date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(_date);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
	        cal.add(Calendar.DAY_OF_MONTH, _daysToAdd);		
	
			SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");	        
			strDate = sdformat.format(cal.getTime());
		}
		
		return strDate;
	}


	public boolean hasManagementPermission(){

			return subject.isPermitted(getChangeSystemView().getSystem() + strManagementPermission);
	}

	
	public boolean hasManagOrModifPermission(){

		return ( subject.isPermitted(getChangeSystemView().getSystem() + strManagementPermission) ||
				 subject.isPermitted(getChangeSystemView().getSystem() + strModificationPermission) );
	}
	
	 public int getItemsSize() { 
			if(this.items!=null && !this.items.isEmpty()){
				return this.items.size();
			}else{
				return 0;
			}
		}
	 public boolean showFile(NominationHeaderBean item) {
		 if(item==null) {
			 return false;
		 } 
		 if("Y".equalsIgnoreCase(item.getIs_valid())) {
			 if("N".equalsIgnoreCase(item.getIs_responsed())) {
				 return false;
			// is_responsed == "P" se toma igual que si fuera "Y".
			 } else if("Y".equalsIgnoreCase(item.getIs_responsed()) || "P".equalsIgnoreCase(item.getIs_responsed())) {
        		return true;
			 }
	    } else if("N".equalsIgnoreCase(item.getIs_valid())) {
	    	return false;
	    }
		 return false;
	 }
	 
	 public void sendMail(String action) {
			HashMap<String,String> values = new HashMap<String,String>();

			String type = "";
			
			if(action.equals(Constants.ACCEPT))
				type = "WEEKLY_NOMINATION.ACCEPT";
			else if (action.equals(Constants.REJECT))
				type = "WEEKLY_NOMINATION.REJECT";
				
			values.put("1", selected.getUser_group_id());
			values.put("2", selected.getContract_code());
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        	String dateString = df.format(selected.getStart_date());
			values.put("3", dateString);
			
			mailService.sendEmail(type, values, getChangeSystemView().getIdn_active(), selected.getIdn_user_group());
			
			//Para comprobar los valores que se pasan en el email
			getMessages().addMessage(Constants.head_menu[4],
					new MessageBean(Constants.INFO, "Mail values", "Shipper"+values.get("1")+",Contrato:"+values.get("2")+",Fecha:"+values.get("3")+". Destinatario:"+selected.getIdn_user_group(), Calendar.getInstance().getTime()));

	 	}
	 
	 public boolean pointRed(NominationDetailWeekBean bean) {
			if(bean.getIs_warned_quantity().equals("Y") || bean.getIs_warned_hv().equals("Y") ||
					bean.getIs_warned_sg().equals("Y") || bean.getIs_warned_wi().equals("Y"))
				return true;
			return false;
		}
}
