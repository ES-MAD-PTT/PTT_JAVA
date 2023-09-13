package com.atos.views.maintenance;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.atos.beans.maintenance.WorkSubmissionBean;
import com.atos.services.maintenance.PublicationService;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;
 

@ManagedBean
@ViewScoped
public class PublicationView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7917705303364760769L;

	private static final Logger log = LogManager.getLogger(PublicationView.class);

	private ScheduleModel eventModel;
	
	private ScheduleEvent event = new DefaultScheduleEvent();
	private List<WorkSubmissionBean> items;
	private WorkSubmissionBean selected;

    @ManagedProperty("#{messagesView}")
    private MessagesView messages;

    public void setMessages(MessagesView messages) {
        this.messages = messages;
    }

	@ManagedProperty("#{publicationService}")
    transient private PublicationService service;
    
    public void setService(PublicationService service) {
        this.service = service;
    }

    
    @PostConstruct
    public void init() {
    	refreshCalendar();
    }
    
    public ScheduleModel getEventModel() {
        return eventModel;
    }
     
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public WorkSubmissionBean getSelected() {
		return selected;
	}

	public void onEventSelect(SelectEvent selectEvent) {
        event = (DefaultScheduleEvent) selectEvent.getObject();
    	for(int i=0;i<items.size();i++){
    		//CH736. Ahora la informacion del code va en el campo desciption. 
    		//String code = event.getTitle().substring(0, event.getTitle().indexOf("(")-1);
    		String code =event.getDescription();
    		
    		if(items.get(i).getMaintenance_code().equals(code)){
    			selected = items.get(i);
    			break;
    		}
    	}

    }
    
    private void refreshCalendar(){
    	eventModel = new DefaultScheduleModel();
		items = service.getAllEngineeringMaintenance(getChangeSystemView().getIdn_active());
        for(int i=0;i<items.size();i++){
        	WorkSubmissionBean bean = items.get(i);
        	if(bean.isIs_daily()){
        		Calendar cal_ini = Calendar.getInstance();
        		Calendar cal_end = Calendar.getInstance();
        		Calendar cal_temp_end = Calendar.getInstance();
        		cal_ini.setTime(bean.getStart_date());
				cal_end.setTime(bean.getActual_end_date() == null ? bean.getEnd_date() : bean.getActual_end_date());
        		
				cal_temp_end.setTime(bean.getEnd_date());
        		cal_temp_end.set(Calendar.DATE, cal_ini.get(Calendar.DATE));
        		cal_temp_end.set(Calendar.MONTH, cal_ini.get(Calendar.MONTH));
        		cal_temp_end.set(Calendar.YEAR, cal_ini.get(Calendar.YEAR));
        		
        		while(cal_ini.compareTo(cal_end)<=0){
        			
        			/*DefaultScheduleEvent event = new DefaultScheduleEvent(items.get(i).getMaintenance_code() + 
    	        			" (Area "+ items.get(i).getArea()+"; "
    	        			+ "Subarea "+items.get(i).getSubarea() + ")",
							cal_ini.getTime(), 
							items.get(i).getActual_end_date() == null ? cal_temp_end.getTime(): items.get(i).getActual_end_date());
        			event.setId(items.get(i).getMaintenance_code());
    	        	if(items.get(i).getCss_color()==null){
    	        		event.setStyleClass("pubgreencss");
    	        	} else {
    	        		event.setStyleClass(items.get(i).getCss_color());
    	        	}
    	        	eventModel.addEvent(event);*/
        			
        			//B006	CH718
        			if( items.get(i).getActual_end_date()!=null && items.get(i).getActual_end_date().equals(cal_ini)){
        				
        				//CH736. No sacamos el code, sino el subject. Gueardo el code en el campo descriptiondel event (en el id no lo coge bien..)
        				/*DefaultScheduleEvent event = new DefaultScheduleEvent(items.get(i).getMaintenance_code() + 
        	        			" (Area "+ items.get(i).getArea()+"; "
        	        			+ "Subarea "+items.get(i).getSubarea() + ")",
    							cal_ini.getTime(), 
    						    items.get(i).getActual_end_date());*/
        				
        				DefaultScheduleEvent event = new DefaultScheduleEvent(items.get(i).getMaintenance_subject() + 
        	        			" (Area "+ items.get(i).getArea()+"; "
        	        			+ "Subarea "+items.get(i).getSubarea() + ")",
    							cal_ini.getTime(), 
    						    items.get(i).getActual_end_date());
        				event.setDescription(items.get(i).getMaintenance_code());
        				
        				
        				event.setId(items.get(i).getMaintenance_code());
        	        	if(items.get(i).getCss_color()==null){
        	        		event.setStyleClass("pubgreencss");
        	        	} else {
        	        		event.setStyleClass(items.get(i).getCss_color());
        	        	}
        	        	eventModel.addEvent(event);
            			
        			}else{
        				//CH736
        				/*DefaultScheduleEvent event = new DefaultScheduleEvent(items.get(i).getMaintenance_code() + 
        	        			" (Area "+ items.get(i).getArea()+"; "
        	        			+ "Subarea "+items.get(i).getSubarea() + ")",
    							cal_ini.getTime(), 
    							cal_temp_end.getTime());*/
        				
        				DefaultScheduleEvent event = new DefaultScheduleEvent(items.get(i).getMaintenance_subject() + 
        	        			" (Area "+ items.get(i).getArea()+"; "
        	        			+ "Subarea "+items.get(i).getSubarea() + ")",
    							cal_ini.getTime(), 
    							cal_temp_end.getTime());
        				event.setDescription(items.get(i).getMaintenance_code());
            			
        				event.setId(items.get(i).getMaintenance_code());
        	        	if(items.get(i).getCss_color()==null){
        	        		event.setStyleClass("pubgreencss");
        	        	} else {
        	        		event.setStyleClass(items.get(i).getCss_color());
        	        	}
        	        	eventModel.addEvent(event);
        			}
        			
    	        
    	        	
    	        	cal_temp_end.add(Calendar.DATE, 1);
        			
    	        	cal_ini.add(Calendar.DATE, 1);
        		}
        	} else {
        		//CH736
        		/*DefaultScheduleEvent event = new DefaultScheduleEvent(items.get(i).getMaintenance_code() + 
	        			" (Area "+ items.get(i).getArea()+"; Subarea "+items.get(i).getSubarea() + ")",
						items.get(i).getStart_date(), items.get(i).getActual_end_date() == null
								? items.get(i).getEnd_date() : items.get(i).getActual_end_date());
        		*/
        		
        		DefaultScheduleEvent event = new DefaultScheduleEvent(items.get(i).getMaintenance_subject() + 
	        			" (Area "+ items.get(i).getArea()+"; Subarea "+items.get(i).getSubarea() + ")",
						items.get(i).getStart_date(), items.get(i).getActual_end_date() == null
								? items.get(i).getEnd_date() : items.get(i).getActual_end_date());
	        	event.setDescription(items.get(i).getMaintenance_code());
	        	
	        	
	        	event.setId(items.get(i).getMaintenance_code());
	        	if(items.get(i).getCss_color()==null){
	        		event.setStyleClass("pubbluecss");
	        	} else {
	        		event.setStyleClass(items.get(i).getCss_color());
	        	}
	        	eventModel.addEvent(event);
        	}
        }
        
    }
}
