package com.atos.filters.forecasting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class QueryShipperForecastingFileFilter implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4180804328871513047L;
	
	private String forecasting_code;
	private String shipper_code;
	private Date start_date;
	private Date end_date;
	private String user;
	private String category_code;
	private String term_code;
	private BigDecimal idn_system;//offshore
	
	
	public QueryShipperForecastingFileFilter() {
		super();
		this.forecasting_code = null;
		this.shipper_code = null;
		this.start_date = null;
		this.end_date = null;
		this.user = null;
		this.category_code = null;
		this.term_code = null;
		this.idn_system=null;
	}
	
	public QueryShipperForecastingFileFilter( QueryShipperForecastingFileFilter _filter ) {
		this();
		if(_filter != null) {
			this.forecasting_code = _filter.getForecasting_code();
			this.shipper_code = _filter.getShipper_code();
			this.start_date = _filter.getStart_date();
			this.end_date = _filter.getEnd_date();
			this.user = _filter.getUser();
			this.category_code = _filter.getCategory_code();
			this.term_code = _filter.getTerm_code();
			this.idn_system=_filter.getIdn_system();
		}
	}
	
	public String getForecasting_code() {
		return forecasting_code;
	}
	public void setForecasting_code(String forecasting_code) {
		this.forecasting_code = forecasting_code;
	}
	public String getShipper_code() {
		return shipper_code;
	}
	public void setShipper_code(String shipper_code) {
		this.shipper_code = shipper_code;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
		/*this.start_date = start_date;
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.start_date);
		cal.add(Calendar.DAY_OF_MONTH, 6);
		this.setEnd_date(cal.getTime());*/
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getCategory_code() {
		return category_code;
	}
	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}
	public String getTerm_code() {
		return term_code;
	}
	public void setTerm_code(String term_code) {
		this.term_code = term_code;
	}
	
	
	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category_code == null) ? 0 : category_code.hashCode());
		result = prime * result + ((end_date == null) ? 0 : end_date.hashCode());
		result = prime * result + ((forecasting_code == null) ? 0 : forecasting_code.hashCode());
		result = prime * result + ((idn_system == null) ? 0 : idn_system.hashCode());
		result = prime * result + ((shipper_code == null) ? 0 : shipper_code.hashCode());
		result = prime * result + ((start_date == null) ? 0 : start_date.hashCode());
		result = prime * result + ((term_code == null) ? 0 : term_code.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryShipperForecastingFileFilter other = (QueryShipperForecastingFileFilter) obj;
		if (category_code == null) {
			if (other.category_code != null)
				return false;
		} else if (!category_code.equals(other.category_code))
			return false;
		if (end_date == null) {
			if (other.end_date != null)
				return false;
		} else if (!end_date.equals(other.end_date))
			return false;
		if (forecasting_code == null) {
			if (other.forecasting_code != null)
				return false;
		} else if (!forecasting_code.equals(other.forecasting_code))
			return false;
		if (idn_system == null) {
			if (other.idn_system != null)
				return false;
		} else if (!idn_system.equals(other.idn_system))
			return false;
		if (shipper_code == null) {
			if (other.shipper_code != null)
				return false;
		} else if (!shipper_code.equals(other.shipper_code))
			return false;
		if (start_date == null) {
			if (other.start_date != null)
				return false;
		} else if (!start_date.equals(other.start_date))
			return false;
		if (term_code == null) {
			if (other.term_code != null)
				return false;
		} else if (!term_code.equals(other.term_code))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "QueryShipperForecastingFileFilter [forecasting_code=" + forecasting_code + ", shipper_code="
				+ shipper_code + ", start_date=" + start_date + ", end_date=" + end_date + ", user=" + user
				+ ", category_code=" + category_code + ", term_code=" + term_code + ", idn_system=" + idn_system + "]";
	}
	
}
