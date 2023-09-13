package com.atos.utils.jsf;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("com.atos.utils.jsf.Html5MonthConverter")
public class Html5MonthConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String[] val = value.split("-");
		try {
			int year = Integer.parseInt(val[0]);
			int month = Integer.parseInt(val[1]);
			// month--;
			Calendar cal = Calendar.getInstance();
			cal.set(year, month, 1, 0, 0, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTime();

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Date Conversion error.", "Invalid date Format.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ConverterException(msg);

		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return "";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-");
			Date val = (Date) value;

			String sval = sdf.format(val);
			return sval + val.getMonth();

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Date Conversion error.", "Invalid Date.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ConverterException(msg);

		}
	}

}
