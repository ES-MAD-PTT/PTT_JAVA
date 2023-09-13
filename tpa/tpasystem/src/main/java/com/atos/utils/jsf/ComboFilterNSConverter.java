package com.atos.utils.jsf;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import com.atos.beans.ComboFilterNS;

@FacesConverter("com.atos.utils.jsf.ComboFilterNSConverter")
public class ComboFilterNSConverter implements Converter {

	private static final Logger log = LogManager.getLogger(ComboFilterNSConverter.class);
	
	// Metodo para obtener objeto a partir de la clave.
	// La key del ComboFilterNS es un BigDecimal que nos llegara transformada en String.
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String strIn) {
			
	    Object oRes = null;
	    
	    try {
		    if (strIn!=null && component!=null && component instanceof PickList) {
		        Object dualList = ((PickList) component).getValue();
		        DualListModel<ComboFilterNS> dl = (DualListModel<ComboFilterNS>) dualList;
		        for (ComboFilterNS cfnsCombo : dl.getSource()) {
		            String strKey = cfnsCombo.getKey().toString();
		            if (strIn.equals(strKey)) {
		            	oRes = cfnsCombo;
		                break;
		            }
		        }

		        if (oRes == null) {
			        for (ComboFilterNS cfnsCombo : dl.getTarget()) {
			            String strKey = cfnsCombo.getKey().toString();
			            if (strIn.equals(strKey)) {
			            	oRes = cfnsCombo;
			                break;
			            }
			        }
		        }
		    }
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			log.error(sw.toString());
			
			FacesMessage msg = new FacesMessage("Date Conversion error.", 
													"Error obtaining ComboFilterNS in PickList: " + e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ConverterException(msg);
		}
	    
	    return oRes;
	}

	// Metodo para obtener clave a partir del objeto.
	// La key del ComboFilterNS es un BigDecimal que devolvemos transformada en String.
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

	    String strRes = "";
	    try {
		    if (value!=null && value instanceof ComboFilterNS) {
		    	strRes = ((ComboFilterNS) value).getKey().toString();
		    }
		    
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			log.error(sw.toString());
			
			FacesMessage msg = new FacesMessage("Date Conversion error.", 
													"Error obtaining String key in PickList: " + e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ConverterException(msg);
		}
	    
	    return strRes;
	}

}
