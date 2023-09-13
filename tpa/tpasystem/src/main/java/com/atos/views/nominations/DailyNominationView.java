package com.atos.views.nominations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.atos.beans.MessageBean;
import com.atos.beans.nominations.NominationDetailBean;
import com.atos.beans.nominations.NominationHeaderBean;
import com.atos.beans.nominations.NominationQualityGasDayBean;
import com.atos.beans.nominations.QualityTableBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.nominations.NominationFilter;
import com.atos.services.MailService;
import com.atos.services.nominations.DailyNominationService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name="dayNom")
@ViewScoped
public class DailyNominationView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2379627102030784947L;
	private static final Logger log = LogManager.getLogger(DailyNominationView.class);
	private final DateFormat dateFormatFileName = new SimpleDateFormat("yyyyMMdd");
	
	// Filters
	private NominationFilter filters;
	
	// Main
	private List<NominationHeaderBean> items;
	
	// Selected register
	private NominationHeaderBean selected;

	// Detail tabs
	private List<NominationDetailBean> east_tab, west_tab, mix_tab;
	
	private boolean showHideDaysEast1 = true; 
	private String widthEast1 = "4300px"; 
	private boolean showHideDaysEast2 = true; 
	private String widthEast2 = "2500px"; 
	private boolean showHideDaysOEast1 = true; 
	private String widthOEast1 = "4300px"; 
	private boolean showHideDaysOEast2 = true; 
	private String widthOEast2 = "2850px"; 
	private boolean showHideDaysWest1 = true; 
	private String widthWest1 = "4300px"; 
	private boolean showHideDaysWest2 = true; 
	private String widthWest2 = "2850px"; 
	private boolean showHideDaysEastWest1 = true; 
	private String widthEastWest1 = "4300px"; 
	private boolean showHideDaysEastWest2 = true; 
	private String widthEastWest2 = "2850px"; 

	// Detail tabs park/unpark
	private List<NominationDetailBean> east_tab_park, west_tab_park, mix_tab_park;
	
	// Quality tab
	private List<NominationQualityGasDayBean> east_quality, west_quality, mix_quality;
	
	// Quality gas day
	private List<QualityTableBean> quality;
	
	// Detail Shipper comment
	private String shipperLongMessage;
	
	// Detail submission comment
	private String longMessage;

	// To control shiro permissions.
	private Subject subject;
	private static final String strManagementPermission = ".NOMINATIONS.DAILY.MANAGEMENT.MAN";	
	private static final String strModificationPermission = ".NOMINATIONS.DAILY.MANAGEMENT.MOD";
	
    @ManagedProperty("#{dailyNominationService}")
    transient private DailyNominationService service;
     
    public void setService(DailyNominationService service) {
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

	public List<NominationHeaderBean> getItems() {
		return items;
	}

	public NominationHeaderBean getSelected() {
		return selected;
	}

	public void setSelected(NominationHeaderBean selected) {
		this.selected = selected;
	}

	public List<NominationDetailBean> getEast_tab() {
		return east_tab;
	}

	public List<NominationDetailBean> getWest_tab() {
		return west_tab;
	}

	public List<NominationDetailBean> getMix_tab() {
		return mix_tab;
	}

	public List<NominationDetailBean> getEast_tab_park() {
		return east_tab_park;
	}

	public List<NominationDetailBean> getWest_tab_park() {
		return west_tab_park;
	}

	public List<NominationDetailBean> getMix_tab_park() {
		return mix_tab_park;
	}

	public List<NominationQualityGasDayBean> getEast_quality() {
		return east_quality;
	}

	public List<NominationQualityGasDayBean> getWest_quality() {
		return west_quality;
	}

	public List<NominationQualityGasDayBean> getMix_quality() {
		return mix_quality;
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
		filters.setTerm_code(Constants.DAILY);
		filters.setType_code(Constants.SHIPPER);
		filters.setIdn_pipeline_system(getChangeSystemView().getIdn_active());
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
    	filters.setUser(getUser().getUsername());
	}
	
	public Map<String, Object> getShipperCodes() {
		return service.selectShipperIdNominations(filters);
	}

	public Map<String, Object> getContractCodes() {
		return service.selectContractCodeByUser(filters);
	}

	// Methods
	public void onSearch(){
    	// La fecha final del filtro se calcula en funcion de la inicial, justo antes de hacer la consulta.
		filters.setEnd_date(filters.getStart_date());
		items = service.selectNomination(filters);
	}
	
	public void onClear(){
		initFilters();
    	
        if (items != null) {
            items.clear();
        }
    }

/* 09/09/16 CH0036 PPM: Por peticion de cambios en FAT, se elimina boton "Open Renomination" en pantalla dailyNomination.xhtml.
 * 	
	public void onOpenRenomination(){
		ErrorBean error = null;
		String error_string = "0";
		try {
			error = service.openRenomination();
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error_string = e.getMessage();
		}

		if(error_string.equals("0") || (error!=null && error.getError_code().intValue()==0)){
			messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.INFO,"Renomination open","Renomination open for tomorrow only today", Calendar.getInstance().getTime()));			
		} else {
			if(error!=null && error.getError_msg()!=null){
				messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error saving file","Failed to save file: " + error.getError_msg(), Calendar.getInstance().getTime()));
			} else {
				messages.addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Error saving file","Failed to save file: " + error, Calendar.getInstance().getTime()));
			}
		}
		
	}
*/

	public void onRowSelect(NominationHeaderBean bean) {
		this.setSelected(bean);
		
		Map<String, BigDecimal> map = service.selectZonesNomination();
		
		bean.setIdn_zone(map.get(Constants.EAST));
		east_tab  = service.selectDetailNomination(bean);
		east_tab_park = service.selectParkUnparkNomination(bean);
		east_quality = service.selectQualityGasDayNomination(bean); 

		bean.setIdn_zone(map.get(Constants.WEST));
		west_tab  = service.selectDetailNomination(bean);
		west_tab_park = service.selectParkUnparkNomination(bean);
		west_quality = service.selectQualityGasDayNomination(bean);

		
		bean.setIdn_zone(map.get(Constants.MIX));
		mix_tab  = service.selectDetailNomination(bean);
		mix_tab_park = service.selectParkUnparkNomination(bean);
		mix_quality = service.selectQualityGasDayNomination(bean);
		
		// No existen nominaciones diarias para sistema offshore, asi que no se llegara a ejecutar este codigo en sistema offshore. 
		// No tiene sentido obtenerlas en onshore.
		//bean.setIdn_zone(map.get(Constants.O_EAST));
		//o_east_tab  = service.selectDetailNomination(bean);
		//o_east_tab_park = service.selectParkUnparkNomination(bean);
		//o_east_quality = service.selectQualityGasDayNomination(bean);
		
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
	
	public StreamedContent getSimoneExport() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();	
        ZipOutputStream zip = new ZipOutputStream(baos);
        boolean hay = false;

        try {
        	if(items==null){
        		items = new ArrayList<NominationHeaderBean>();
        	}
	        TreeMap<String,BigDecimal> map = new TreeMap<String,BigDecimal>();
	        for(int i=0;i<items.size();i++){
	        	if(items.get(i).getIdn_operator_file()!=null){
		    		map.put("idn_operation_file", items.get(i).getIdn_operator_file()); 
		        	byte[] b = service.getSimoneFile(map);
		        	if (b!=null) {
		                hay = true;
		                String file_name= dateFormatFileName.format(items.get(i).getStart_date());
		                file_name = file_name + "_" + items.get(i).getUser_group_id() + "_" + items.get(i).getContract_code() + "_";
		                Calendar cal = Calendar.getInstance();
		                file_name = file_name + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH) +
		                		cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND) + cal.get(Calendar.MILLISECOND)
		                		+".xlsx";
						zip.putNextEntry(new ZipEntry(file_name));
		                zip.write(b);
		                zip.closeEntry();
		            }
	        	}
	        }
	        if (!hay) {
	            zip.putNextEntry(new ZipEntry("No_data"));
	            zip.write(new byte[0]);
	            zip.closeEntry();
	        }
	        zip.finish();
	        zip.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.catching(e);
		}
        
		return new DefaultStreamedContent(new ByteArrayInputStream(baos.toByteArray()),"application/zip","simone.zip");
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
		System.out.println("idn active: " + getChangeSystemView().getIdn_active());
		service.prorateCalculation(east_tab, east_quality);
		service.prorateCalculation(west_tab, west_quality);
		service.prorateCalculation(mix_tab, mix_quality);
		// No existen nominaciones diarias para sistema offshore, asi que no se llegara a ejecutar este codigo en sistema offshore. 
		// No tiene sentido obtenerlas en onshore.
		//service.prorateCalculation(o_east_tab, o_east_quality);
	}
	
	public void onRowCancel(RowEditEvent event) {
		
    }
	
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
	
	private void executeSave(String action){

    	String summaryMsg = null;
    	String finalMsg = null;
    	int msgType = -1;

		// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
		service.prorateCalculation(east_tab, east_quality);
		service.prorateCalculation(west_tab, west_quality);
		service.prorateCalculation(mix_tab, mix_quality);
		// No existen nominaciones diarias para sistema offshore, asi que no se llegara a ejecutar este codigo en sistema offshore. 
		// No tiene sentido obtenerlas en onshore.
		//service.prorateCalculation(o_east_tab, o_east_quality);
		
		ByteArrayOutputStream ba = service.generateXlsx(selected,east_tab, west_tab, mix_tab,  
															east_tab_park, west_tab_park, mix_tab_park,  
															east_quality, west_quality, mix_quality, quality,
															getChangeSystemView().getIdn_active());
		if(ba==null){
			getMessages().addMessage(Constants.head_menu[4],new MessageBean(Constants.ERROR,"Downloading file", "New Excel File empty", Calendar.getInstance().getTime()));
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
	 public void daysDetailEast1() {
		 if(this.showHideDaysEast1==false) {
			 this.showHideDaysEast1=true;
		 } else {
			 this.showHideDaysEast1= false;
		 }
	 }
	 public boolean getShowHideDaysEast1() {
		 if(this.showHideDaysEast1 == true) {
			 this.widthEast1 = "4300px";
			 return true;
		 } else {
			 this.widthEast1 = "1900px";
			 return false;
		 }
	 }
	public String getWidthEast1() {
		if(this.showHideDaysEast1 == true) {
			this.widthEast1 = "4300px";
		} else {
			this.widthEast1 = "1900px";
		}
		return widthEast1; 
	}

	public void daysDetailEast2() {
		 if(this.showHideDaysEast2==false) {
			 this.showHideDaysEast2=true;
		 } else {
			 this.showHideDaysEast2= false;
		 }
	 }
	 public boolean getShowHideDaysEast2() {
		 if(this.showHideDaysEast2 == true) {
			 this.widthEast2 = "2850px";
			 return true;
		 } else {
			 this.widthEast2 = "450px";
			 return false;
		 }
	 }
	 public String getWidthEast2() {
		 if(this.showHideDaysEast2 == true) {
			 this.widthEast2 = "2850px";
		 } else {
			 this.widthEast2 = "450px";
		 }
		 return widthEast2; 
	 }

	public void daysDetailWest1() {
		 if(this.showHideDaysWest1==false) {
			 this.showHideDaysWest1=true;
		 } else {
			 this.showHideDaysWest1= false;
		 }
	 }
	 public boolean getShowHideDaysWest1() {
		 if(this.showHideDaysWest1 == true) {
			 this.widthWest1 = "4300px";
			 return true;
		 } else {
			 this.widthWest1 = "1900px";
			 return false;
		 }
	 }

	 public String getWidthWest1() {
		 if(this.showHideDaysWest1 == true) {
			 this.widthWest1 = "4300px";
		 } else {
			 this.widthWest1 = "1900px";
		 }
		 return widthWest1; 
	 }
	 
	 public void daysDetailWest2() {
		 if(this.showHideDaysWest2==false) {
			 this.showHideDaysWest2=true;
		 } else {
			 this.showHideDaysWest2= false;
		 }
	 }
	 public boolean getShowHideDaysWest2() {
		 if(this.showHideDaysWest2 == true) {
			 this.widthWest2 = "2850px";
			 return true;
		 } else {
			 this.widthWest2 = "450px";
			 return false;
		 }
	 }

	 public String getWidthWest2() {
		 if(this.showHideDaysWest2 == true) {
			 this.widthWest2 = "2850px";
		 } else {
			 this.widthWest2 = "450px";
		 }
		 return widthWest2; 
	 }
	 
	 public void daysDetailEastWest1() {
		 if(this.showHideDaysEastWest1==false) {
			 this.showHideDaysEastWest1=true;
		 } else {
			 this.showHideDaysEastWest1= false;
		 }
	 }
	 public boolean getShowHideDaysEastWest1() {
		 if(this.showHideDaysEastWest1 == true) {
			 this.widthEastWest1 = "4300px";
			 return true;
		 } else {
			 this.widthEastWest1 = "1900px";
			 return false;
		 }
	 }

	 public String getWidthEastWest1() {
		 if(this.showHideDaysEastWest1 == true) {
			 this.widthEastWest1 = "4300px";
		 } else {
			 this.widthEastWest1 = "1900px";
		 }
		 return widthEastWest1; 
	 }
	 
	 public void daysDetailEastWest2() {
		 if(this.showHideDaysEastWest2==false) {
			 this.showHideDaysEastWest2=true;
		 } else {
			 this.showHideDaysEastWest2= false;
		 }
	 }
	 public boolean getShowHideDaysEastWest2() {
		 if(this.showHideDaysEastWest2 == true) {
			 this.widthEastWest2 = "2850px";
			 return true;
		 } else {
			 this.widthEastWest2 = "450px";
			 return false;
		 }
	 }

	 public String getWidthEastWest2() {
		 if(this.showHideDaysEastWest2 == true) {
			 this.widthEastWest2 = "2850px";
		 } else {
			 this.widthEastWest2 = "450px";
		 }
		 return widthEastWest2; 
	 }
	 
	 public void daysDetailOEast1() {
		 if(this.showHideDaysOEast1==false) {
			 this.showHideDaysOEast1=true;
		 } else {
			 this.showHideDaysOEast1= false;
		 }
	 }
	 public boolean getShowHideDaysOEast1() {
		 if(this.showHideDaysOEast1 == true) {
			 this.widthOEast1 = "4300px";
			 return true;
		 } else {
			 this.widthOEast1 = "1900px";
			 return false;
		 }
	 }

		public String getWidthOEast1() {
			if(this.showHideDaysOEast1 == true) {
				this.widthOEast1 = "4300px";
			} else {
				this.widthOEast1 = "1900px";
			}
			return widthOEast1; 
		}
	 
	 public void daysDetailOEast2() {
		 if(this.showHideDaysOEast2==false) {
			 this.showHideDaysOEast2=true;
		 } else {
			 this.showHideDaysOEast2= false;
		 }
	 }
	 public boolean getShowHideDaysOEast2() {
		 if(this.showHideDaysOEast2 == true) {
			 this.widthOEast2 = "2850px";
			 return true;
		 } else {
			 this.widthOEast2 = "450px";
			 return false;
		 }
	 }

		public String getWidthOEast2() {
			if(this.showHideDaysOEast2 == true) {
				this.widthOEast2 = "2850px";
			} else {
				this.widthOEast2 = "450px";
			}
			return widthOEast2; 
		}

		public void sendMail(String action) {
			HashMap<String,String> values = new HashMap<String,String>();

			String type = "";
			
			if(action.equals(Constants.ACCEPT))
				type = "DAILY_NOMINATION.ACCEPT";
			else if (action.equals(Constants.REJECT))
				type = "DAILY_NOMINATION.REJECT";
		
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
		
		public boolean pointRed(NominationDetailBean bean) {
			if(bean.getIs_warned_quantity().equals("Y") || bean.getIs_warned_hv().equals("Y") ||
					bean.getIs_warned_sg().equals("Y") || bean.getIs_warned_wi().equals("Y"))
				return true;
			return false;
		}
}
