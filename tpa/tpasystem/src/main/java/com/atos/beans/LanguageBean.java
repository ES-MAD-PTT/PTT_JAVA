package com.atos.beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;


@ManagedBean(name = "languageBean", eager = true)
@SessionScoped
public class LanguageBean implements Serializable {

   private static final long serialVersionUID = 1L;
   private String locale;

   private static Map<String,Object> countries;
   
   static{
      countries = new LinkedHashMap<String,Object>();
      countries.put("English", new Locale("en"));
      countries.put("Spanish", new Locale("es"));
      countries.put("Thai", new Locale("th"));
   }

/*   public LanguageBean(){
	   //Locale browserLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	   Locale browserLocale = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getLocale();
	   this.setLocale(browserLocale.getLanguage());
   }*/
   @PostConstruct
   public void init(){
	   Locale browserLocale = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getLocale();
	   this.setLocale(browserLocale.getLanguage());
	   
   }
   
   public Map<String, Object> getCountries() {
      return countries;
   }

   public String getLocale() {
      return locale;
   }

   public void setLocale(String locale) {
      this.locale = locale;
   }

   //value change event listener
   public void localeChanged(ValueChangeEvent e){
	   
      String newLocaleValue = e.getNewValue().toString();
      for (Map.Entry<String, Object> entry : countries.entrySet()) {
         if(entry.getValue().toString().equals(newLocaleValue)){
            FacesContext.getCurrentInstance()
               .getViewRoot().setLocale((Locale)entry.getValue());         
         }
      }
   }
}