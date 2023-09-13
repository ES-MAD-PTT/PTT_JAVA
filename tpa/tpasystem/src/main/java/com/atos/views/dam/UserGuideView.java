package com.atos.views.dam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.MessageBean;
import com.atos.beans.dam.NewDocumentBean;
import com.atos.beans.dam.UserGuideBean;
import com.atos.beans.nominations.NominationHeaderBean;
import com.atos.exceptions.ValidationException;
import com.atos.services.dam.UserGuideService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;

@ManagedBean(name="userGuideView")
@ViewScoped
public class UserGuideView extends CommonView implements Serializable {

	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(UserGuideView.class);
	private UploadedFile file;
	private ArrayList<UserGuideBean> list_files;
	private UserGuideBean selected, selectedDelete;
	private NewDocumentBean newDocument;
	
	private UserGuideBean newUserGuide;
	private List<UserGuideBean> items;
	
	@ManagedProperty("#{userGuideService}")
    transient private UserGuideService service;
    
    public void setService(UserGuideService service) {
        this.service = service;
    }
    
    private Calendar sysdate;
    
    
    public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}


	public UserGuideBean getNewUserGuide() {
		return newUserGuide;
	}

	public void setNewUserGuide(UserGuideBean newUserGuide) {
		this.newUserGuide = newUserGuide;
	}
	
	
	public List<UserGuideBean> getItems() {
		return items;
	}
	
	public void setItems(List<UserGuideBean> items) {
		this.items = items;
	}
	 
    public ArrayList<UserGuideBean> getList_files() {
		return list_files;
	}

	public void setList_files(ArrayList<UserGuideBean> list_files) {
		this.list_files = list_files;
	}

	public UserGuideBean getSelected() {
		return selected;
	}

	public void setSelected(UserGuideBean selected) {
		this.selected = selected;
	}

	public UserGuideBean getSelectedDelete() {
		return selectedDelete;
	}

	public void setSelectedDelete(UserGuideBean selectedDelete) {
		this.selectedDelete = selectedDelete;
	}

	public NewDocumentBean getNewDocument() {
		return newDocument;
	}

	public void setNewDocument(NewDocumentBean newDocument) {
		this.newDocument = newDocument;
	}

	@PostConstruct
    public void init() {
    	
    	newUserGuide = new UserGuideBean();
    	newDocument = new NewDocumentBean();
    	//realizamos la busqueda nada mas entrar
    	items = service.selectUserGuides();  
    	sysdate = Calendar.getInstance();   
        selected = new UserGuideBean();
    }
	
	
	public Map<BigDecimal, Object> getDocumentDescription() {
		return service.selectUserGuideDocumentType();
	}

	public Map<BigDecimal, Object> getUserGroupType() {
		return service.selectUserGroupType();
	}

	public void saveNewDocument() {

		String summaryMsg = null;
		String errorMsg = null;
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		summaryMsg = msgs.getString("saving_error");
		
		newDocument.setSort_value(new BigDecimal(1));
		
		String retorno = service.insertNewDocument(newDocument);
		
		if(!retorno.equals("0")) {
			errorMsg = msgs.getString("failed_insert_newDocument_UserGuide");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsg, errorMsg, Calendar.getInstance().getTime()));
		} else {
			errorMsg = msgs.getString("userGuide_new_document_save");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,errorMsg, errorMsg, Calendar.getInstance().getTime()));

		}
		items = service.selectUserGuides();
		newDocument = new NewDocumentBean();
		return;

	}
	
	public void deleteFile() {
		String summaryMsg = null;
		String errorMsg = null;
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		summaryMsg = msgs.getString("delete_error");
		
		if(selectedDelete==null) {
			errorMsg = msgs.getString("userGuide_no_file_selected");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			items = service.selectUserGuides();
			return;
			
		}
		
		String retorno = service.deleteFile(selectedDelete);
		
		if(!retorno.equals("0")) {
			errorMsg = msgs.getString("failed_delete_file_UserGuide");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsg, errorMsg, Calendar.getInstance().getTime()));
		} else {
			errorMsg = msgs.getString("userGuide_delete_document");
			summaryMsg = msgs.getString("userGuide_delete_ok");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsg, errorMsg, Calendar.getInstance().getTime()));
		}
		items = service.selectUserGuides();
		return;
		
	}
	
	public void save() {

		String summaryMsg = null;
		String errorMsg = null;
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		summaryMsg = msgs.getString("saving_error");
		
		if (newUserGuide.getIdn_document_type()==null){
			errorMsg = msgs.getString("userGuide_mandatory_description");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			items = service.selectUserGuides();
			return;
		}
		
		if(list_files==null) {
			errorMsg = msgs.getString("userGuide_file_selected");
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);    					
			return;
		}
		if(list_files.size()==0) {
			errorMsg = msgs.getString("userGuide_file_selected");
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);    					
			return;
		}

		for(int i=0;i<list_files.size();i++){

			UserGuideBean bean = list_files.get(i); 
	        newUserGuide.setDocument_name(bean.getDocument_name());
			newUserGuide.setBinary_data(bean.getBinary_data());
			newUserGuide.setVersion_date(bean.getVersion_date());
			newUserGuide.setVersion_comment(bean.getVersion_comment());
			newUserGuide.setVersion_date(bean.getVersion_date());
			
			String error = "0";
			try {
				error = service.insertUserGuide(newUserGuide);
			
			} catch(Exception e) {
				log.catching(e);
				// we assign the return message 
				error = e.getMessage();
			}
			
			if(error!=null && error.equals("-1")){			
				errorMsg = msgs.getString("failed_insert_UserGuide");
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);										
			}else if(error!=null && error.equals("-2")){			
				errorMsg = msgs.getString("failed_Update_UserGuide");
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);										
			}else
				if(error!=null && error.equals("0")){		
				summaryMsg = msgs.getString("file_saved");
				errorMsg = msgs.getString("userGuide_created");
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);				
			}else {
				summaryMsg = msgs.getString("saving_error");
				errorMsg = msgs.getString("failed_save");
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
			}
			
			
		}
		
		
/*		if(list_files.size()>1) {
		
			ByteArrayOutputStream baos = new ByteArrayOutputStream();	
	        ZipOutputStream zip = new ZipOutputStream(baos);
	        boolean hay = false;
	
	        try {
		        for(int i=0;i<list_files.size();i++){
		        	UserGuideBean bean = list_files.get(i);
		        	if (bean.getBinary_data()!=null) {
		                hay = true;
		                String file_name= list_files.get(i).getDocument_name();
						zip.putNextEntry(new ZipEntry(file_name));
		                zip.write(bean.getBinary_data());
		                zip.closeEntry();
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
	        newUserGuide.setDocument_name("user_guide.zip");
			newUserGuide.setBinary_data(baos.toByteArray());
			newUserGuide.setVersion_date(sysdate.getTime());
	        
	        
		} else {
			UserGuideBean bean = list_files.get(0); 
	        newUserGuide.setDocument_name(bean.getDocument_name());
			newUserGuide.setBinary_data(bean.getBinary_data());
			newUserGuide.setVersion_date(bean.getVersion_date());
		}*/
		
		
/*		String error = "0";
		try {
			error = service.insertUserGuide(newUserGuide);
		
		} catch(Exception e) {
			log.catching(e);
			// we assign the return message 
			error = e.getMessage();
		}
		
		if(error!=null && error.equals("-1")){			
			errorMsg = msgs.getString("failed_insert_UserGuide");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);										
		}else if(error!=null && error.equals("-2")){			
			errorMsg = msgs.getString("failed_Update_UserGuide");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);										
		}else
			if(error!=null && error.equals("0")){		
			summaryMsg = msgs.getString("file_saved");
			errorMsg = msgs.getString("userGuide_created");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);				
		}else {
			summaryMsg = msgs.getString("saving_error");
			errorMsg = msgs.getString("failed_save");
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);
		}
*/		
		
		items = service.selectUserGuides();
    	
    	//clean the formu new after save
    	newUserGuide = new UserGuideBean();
    	newUserGuide.setVersion_date(sysdate.getTime());
    	list_files=null;
		
	}

	
	public void handleFileUpload(FileUploadEvent event) {		
		String summaryMsg = null;
		String errorMsg = null;
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		summaryMsg = msgs.getString("saving_error");
		if(list_files==null) {
			list_files = new ArrayList<UserGuideBean>();
		}
		if(list_files.size()>=50) {
			errorMsg = msgs.getString("userGuide_exced_50_files");
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);    					
			return;
		}
		file = event.getFile();
		if(file!=null){
			UserGuideBean bean = new UserGuideBean();
			bean.setDocument_name(file.getFileName());
			bean.setBinary_data(file.getContents());
			bean.setVersion_date(sysdate.getTime());
			list_files.add(bean);
		}
        
		if(file==null ){			
			errorMsg = msgs.getString("userGuide_file_selected");
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
	    	log.error(errorMsg);    					
			return;
		}
			
		
	}
	
	public StreamedContent getTemplate(UserGuideBean userGuide)  throws ValidationException{
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
		
		String summaryMsg = msgs.getString("userGuide_downloading_file");
		String errorMsg = null;
		try {
			return service.getFile(userGuide);
		} catch (ValidationException ve) {
			errorMsg = msgs.getString("download_error") + " " + msgs.getString("userGuide") + ve.getMessage();
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			return null; 
		} catch (Exception e) {
			errorMsg = msgs.getString("download_error") + " User Guide. ";
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));			
			return null; 
		}	
		
		
	}
	
	public StreamedContent getTemplate(BigDecimal idn_user_group) throws ValidationException{
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
		
		String summaryMsg = msgs.getString("userGuide_downloading_file");
		String errorMsg = null;
		try {
			return service.getFile(idn_user_group);
		} catch (ValidationException ve) {
			errorMsg = msgs.getString("download_error") + " User Guide. " + ve.getMessage();
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			return null; 
		} catch (Exception e) {
			errorMsg = msgs.getString("download_error") + " User Guide. ";
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));			
			return null; 
		}
	}
		
	 public void cancel() {
	       // RequestContext.getCurrentInstance().reset("formNewEntity");	    	
		 newUserGuide = new UserGuideBean();
		 newUserGuide.setVersion_date(sysdate.getTime());
		 list_files=null;
		 System.gc();
	 }
	 
	 public void cancelNewDocument() {
		 newDocument= new NewDocumentBean();
		 System.gc();
	 }
	 
	 public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
	 public void remove(UserGuideBean bean) {
		 if(this.list_files!=null) {
			 for(int i=0;i<list_files.size();i++) {
				 UserGuideBean b = list_files.get(i);
				 if(b.getDocument_name().equals(bean.getDocument_name()) && b.getVersion_date().equals(bean.getVersion_date())) {
					 list_files.remove(i);
					 return;
				 }
			 }
		 }
	 }
	 
}
