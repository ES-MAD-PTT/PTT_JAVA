package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class NominationDeadlineBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_operation;
	private String operation_desc;
	private BigDecimal idn_operation_category;
	private String category_code;
	private BigDecimal idn_operation_term;
	private BigDecimal idn_deadline_limit;
	private BigDecimal idn_deadline_type;
	private String deadline_type;
	private String deadline_desc;
	private BigDecimal idn_operation_deadline;

	private String deadlineType;
	private String type;
	private Date hour;
	private String shour;
	private BigDecimal gasDay;
	private Date startDate;
	private Date endDate;

	private int valid;
	private String user;
	private String language;
	private String error_desc;

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getError_desc() {
		return error_desc;
	}

	public void setError_desc(String error_desc) {
		this.error_desc = error_desc;
	}

	public NominationDeadlineBean() {
		super();
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public NominationDeadlineBean(BigDecimal idn_operation, String operation_desc, BigDecimal idn_operation_category,
			String category_code, BigDecimal idn_operation_term, BigDecimal idn_deadline_limit,
			BigDecimal idn_deadline_type, String deadline_type, String deadline_desc, BigDecimal idn_operation_deadline,
			String deadlineType, String type, Date hour, String shour, BigDecimal gasDay, Date startDate, Date endDate,
			int valid, String user, String language, String error_desc) {
		super();
		this.idn_operation = idn_operation;
		this.operation_desc = operation_desc;
		this.idn_operation_category = idn_operation_category;
		this.category_code = category_code;
		this.idn_operation_term = idn_operation_term;
		this.idn_deadline_limit = idn_deadline_limit;
		this.idn_deadline_type = idn_deadline_type;
		this.deadline_type = deadline_type;
		this.deadline_desc = deadline_desc;
		this.idn_operation_deadline = idn_operation_deadline;
		this.deadlineType = deadlineType;
		this.type = type;
		this.hour = hour;
		this.shour = shour;
		this.gasDay = gasDay;
		this.startDate = startDate;
		this.endDate = endDate;
		this.valid = valid;
		this.user = user;
		this.language = language;
		this.error_desc = error_desc;
	}

	public String getDeadline_desc() {
		return deadline_desc;
	}

	public void setDeadline_desc(String deadline_desc) {
		this.deadline_desc = deadline_desc;
	}

	public String getShour() {
		return shour;
	}

	public void setShour(String shour) {
		this.shour = shour;
	}

	public BigDecimal getIdn_operation() {
		return idn_operation;
	}

	public void setIdn_operation(BigDecimal idn_operation) {
		this.idn_operation = idn_operation;
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

	public String getDeadlineType() {
		return deadlineType;
	}

	public void setDeadlineType(String deadlineType) {
		this.deadlineType = deadlineType;
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

	public void setHour(Date hour) {
		this.hour = hour;
	}

	public BigDecimal getGasDay() {
		return gasDay;
	}

	public void setGasDay(BigDecimal gasDay) {
		this.gasDay = gasDay;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "NominationDeadline [idn_deadline_type=" + idn_deadline_type + ", idn_operation_term="
				+ idn_operation_term + ", shour=" + shour + ", gasDay=" + gasDay + ", startDate=" + startDate + "]";
	}

}
