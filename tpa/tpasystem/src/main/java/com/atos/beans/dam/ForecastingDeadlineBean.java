package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ForecastingDeadlineBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_operation;
	private String operation_desc;
	private BigDecimal idn_operation_category;
	private String category_code;
	private BigDecimal idn_operation_term;
	private BigDecimal idn_deadline_limit;
	private BigDecimal idn_deadline_type;
	private String deadline_type;
	private BigDecimal idn_operation_deadline;

	private String type;
	private Date hour;
	private String shour;
	private Integer day;
	private Integer month;
	private Date startDate;
	private Date endDate;

	public ForecastingDeadlineBean(BigDecimal idn_operation, String operation_desc, BigDecimal idn_operation_category,
			String category_code, BigDecimal idn_operation_term, BigDecimal idn_deadline_limit,
			BigDecimal idn_deadline_type, String deadline_type, BigDecimal idn_operation_deadline, String type,
			Date hour, String shour, Integer day, Integer month, Date startDate, Date endDate) {
		super();
		this.idn_operation = idn_operation;
		this.operation_desc = operation_desc;
		this.idn_operation_category = idn_operation_category;
		this.category_code = category_code;
		this.idn_operation_term = idn_operation_term;
		this.idn_deadline_limit = idn_deadline_limit;
		this.idn_deadline_type = idn_deadline_type;
		this.deadline_type = deadline_type;
		this.idn_operation_deadline = idn_operation_deadline;
		this.type = type;
		this.hour = hour;
		this.shour = shour;
		this.day = day;
		this.month = month;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public BigDecimal getIdn_operation() {
		return idn_operation;
	}

	public void setIdn_operation(BigDecimal idn_operation) {
		this.idn_operation = idn_operation;
	}

	public ForecastingDeadlineBean() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getHour() {
		return hour;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setHour(Date hour) {
		this.hour = hour;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getOperation_desc() {
		return operation_desc;
	}

	public void setOperation_desc(String operation_desc) {
		this.operation_desc = operation_desc;
	}

	public BigDecimal getIdn_operation_category() {
		return idn_operation_category;
	}

	public void setIdn_operation_category(BigDecimal idn_operation_category) {
		this.idn_operation_category = idn_operation_category;
	}

	public String getCategory_code() {
		return category_code;
	}

	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}

	public BigDecimal getIdn_operation_term() {
		return idn_operation_term;
	}

	public void setIdn_operation_term(BigDecimal idn_operation_term) {
		this.idn_operation_term = idn_operation_term;
	}

	public BigDecimal getIdn_deadline_limit() {
		return idn_deadline_limit;
	}

	public void setIdn_deadline_limit(BigDecimal idn_deadline_limit) {
		this.idn_deadline_limit = idn_deadline_limit;
	}

	public BigDecimal getIdn_deadline_type() {
		return idn_deadline_type;
	}

	public void setIdn_deadline_type(BigDecimal idn_deadline_type) {
		this.idn_deadline_type = idn_deadline_type;
	}

	public String getDeadline_type() {
		return deadline_type;
	}

	public void setDeadline_type(String deadline_type) {
		this.deadline_type = deadline_type;
	}

	public BigDecimal getIdn_operation_deadline() {
		return idn_operation_deadline;
	}

	public void setIdn_operation_deadline(BigDecimal idn_operation_deadline) {
		this.idn_operation_deadline = idn_operation_deadline;
	}

	public String getShour() {
		return shour;
	}

	public void setShour(String shour) {
		this.shour = shour;
	}

	@Override
	public String toString() {
		return "ForecastingDeadline [type=" + idn_operation_term + ", hour=" + hour + ", day=" + day + ", month="
				+ month + ", startDate=" + startDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category_code == null) ? 0 : category_code.hashCode());
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((deadline_type == null) ? 0 : deadline_type.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((hour == null) ? 0 : hour.hashCode());
		result = prime * result + ((idn_deadline_limit == null) ? 0 : idn_deadline_limit.hashCode());
		result = prime * result + ((idn_deadline_type == null) ? 0 : idn_deadline_type.hashCode());
		result = prime * result + ((idn_operation == null) ? 0 : idn_operation.hashCode());
		result = prime * result + ((idn_operation_category == null) ? 0 : idn_operation_category.hashCode());
		result = prime * result + ((idn_operation_deadline == null) ? 0 : idn_operation_deadline.hashCode());
		result = prime * result + ((idn_operation_term == null) ? 0 : idn_operation_term.hashCode());
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((operation_desc == null) ? 0 : operation_desc.hashCode());
		result = prime * result + ((shour == null) ? 0 : shour.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ForecastingDeadlineBean other = (ForecastingDeadlineBean) obj;
		if (category_code == null) {
			if (other.category_code != null)
				return false;
		} else if (!category_code.equals(other.category_code))
			return false;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (deadline_type == null) {
			if (other.deadline_type != null)
				return false;
		} else if (!deadline_type.equals(other.deadline_type))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (hour == null) {
			if (other.hour != null)
				return false;
		} else if (!hour.equals(other.hour))
			return false;
		if (idn_deadline_limit == null) {
			if (other.idn_deadline_limit != null)
				return false;
		} else if (!idn_deadline_limit.equals(other.idn_deadline_limit))
			return false;
		if (idn_deadline_type == null) {
			if (other.idn_deadline_type != null)
				return false;
		} else if (!idn_deadline_type.equals(other.idn_deadline_type))
			return false;
		if (idn_operation == null) {
			if (other.idn_operation != null)
				return false;
		} else if (!idn_operation.equals(other.idn_operation))
			return false;
		if (idn_operation_category == null) {
			if (other.idn_operation_category != null)
				return false;
		} else if (!idn_operation_category.equals(other.idn_operation_category))
			return false;
		if (idn_operation_deadline == null) {
			if (other.idn_operation_deadline != null)
				return false;
		} else if (!idn_operation_deadline.equals(other.idn_operation_deadline))
			return false;
		if (idn_operation_term == null) {
			if (other.idn_operation_term != null)
				return false;
		} else if (!idn_operation_term.equals(other.idn_operation_term))
			return false;
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		if (operation_desc == null) {
			if (other.operation_desc != null)
				return false;
		} else if (!operation_desc.equals(other.operation_desc))
			return false;
		if (shour == null) {
			if (other.shour != null)
				return false;
		} else if (!shour.equals(other.shour))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
