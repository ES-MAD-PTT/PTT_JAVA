package com.atos.views;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.annotation.PostConstruct; 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.atos.services.dam.ChangePictureService;

@ManagedBean(name = "homeView")
@SessionScoped
public class HomeView implements Serializable {
  
    /**
	 * 
	 */
	private static final long serialVersionUID = -2258047552010712161L;

	private byte[] image_byte;
	
	@ManagedProperty("#{changePictureService}")
    transient private ChangePictureService service;
  
	public void setService(ChangePictureService service) {
		this.service = service;
	}
	
    @PostConstruct
    public void init() {
    	image_byte = service.image();
    }
   
    public StreamedContent getImageFromDB() {
    	FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		}
		else {
			return new DefaultStreamedContent(new ByteArrayInputStream(image_byte), "image/png","imagen.png");

		}
    }
 /*   public void onCSV() {
    	service.onCSV();
    }*/
    
   /* parte xhtml
    * 			  <p:commandButton value="#{msg['search']}" icon="ui-icon fa fa-search" 
									actionListener="#{homeView.onCSV}"
									onstart="PF('cargando').show()"
									oncomplete="PF('cargando').hide()" />

    *  
    *  
    */
}