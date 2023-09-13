package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ContractDeadlineBean extends UserAudBean implements Serializable {

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

	private String deadlineType; // combo process type=> idn_deadline_type
	private String type; // combo Contract Type => IDN_OPERATION_TERM

	private String shourLimit;// no se usa Al almacenar registros nuevos se debe
								// insertar '23:59'
	private BigDecimal dayLimit;
	private BigDecimal monthLimit;
	private BigDecimal daysBefore; // no se usa en pantalla va un 0 al insert
	private BigDecimal monthsBefore;
	private BigDecimal yearsBefore;

	private Date startDate; // ok
	private Date endDate;

	private int valid;
	private String user;
	private String language;
	private String error_desc;

	public ContractDeadlineBean(BigDecimal idn_operation, String operation_desc, BigDecimal idn_operation_category,
			String category_code, BigDecimal idn_operation_term, BigDecimal idn_deadline_limit,
			BigDecimal idn_deadline_type, String deadline_type, String deadline_desc, BigDecimal idn_operation_deadline,
			String deadlineType, String type, String shourLimit, BigDecimal dayLimit, BigDecimal monthLimit,
			BigDecimal daysBefore, BigDecimal monthsBefore, BigDecimal yearsBefore, Date startDate, Date endDate,
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
		this.shourLimit = shourLimit;
		this.dayLimit = dayLimit;
		this.monthLimit = monthLimit;
		this.daysBefore = daysBefore;
		this.monthsBefore = monthsBefore;
		this.yearsBefore = yearsBefore;
		this.startDate = startDate;
		this.endDate = endDate;
		this.valid = valid;
		this.user = user;
		this.language = language;
		this.error_desc = error_desc;
	}

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

	public ContractDeadlineBean() {
		super();
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDeadline_desc() {
		return deadline_desc;
	}

	public void setDeadline_desc(String deadline_desc) {
		this.deadline_desc = deadline_desc;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(BigDecimal dayLimit) {
		this.dayLimit = dayLimit;
	}

	public BigDecimal getMonthLimit() {
		return monthLimit;
	}

	public void setMonthLimit(BigDecimal monthLimit) {
		this.monthLimit = monthLimit;
	}

	public BigDecimal getMonthsBefore() {
		return monthsBefore;
	}

	public void setMonthsBefore(BigDecimal monthsBefore) {
		this.monthsBefore = monthsBefore;
	}

	public BigDecimal getYearsBefore() {
		return yearsBefore;
	}

	public void setYearsBefore(BigDecimal yearsBefore) {
		this.yearsBefore = yearsBefore;
	}

	public String getShourLimit() {
		return shourLimit;
	}

	public void setShourLimit(String shourLimit) {
		this.shourLimit = shourLimit;
	}

	public BigDecimal getDaysBefore() {
		return daysBefore;
	}

	public void setDaysBefore(BigDecimal daysBefore) {
		this.daysBefore = daysBefore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category_code == null) ? 0 : category_code.hashCode());
		result = prime * result + ((dayLimit == null) ? 0 : dayLimit.hashCode());
		result = prime * result + ((daysBefore == null) ? 0 : daysBefore.hashCode());
		result = prime * result + ((deadlineType == null) ? 0 : deadlineType.hashCode());
		result = prime * result + ((deadline_desc == null) ? 0 : deadline_desc.hashCode());
		result = prime * result + ((deadline_type == null) ? 0 : deadline_type.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((error_desc == null) ? 0 : error_desc.hashCode());
		result = prime * result + ((idn_deadline_limit == null) ? 0 : idn_deadline_limit.hashCode());
		result = prime * result + ((idn_deadline_type == null) ? 0 : idn_deadline_type.hashCode());
		result = prime * result + ((idn_operation == null) ? 0 : idn_operation.hashCode());
		result = prime * result + ((idn_operation_category == null) ? 0 : idn_operation_category.hashCode());
		result = prime * result + ((idn_operation_deadline == null) ? 0 : idn_operation_deadline.hashCode());
		result = prime * result + ((idn_operation_term == null) ? 0 : idn_operation_term.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((monthLimit == null) ? 0 : monthLimit.hashCode());
		result = prime * result + ((monthsBefore == null) ? 0 : monthsBefore.hashCode());
		result = prime * result + ((operation_desc == null) ? 0 : operation_desc.hashCode());
		result = prime * result + ((shourLimit == null) ? 0 : shourLimit.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + valid;
		result = prime * result + ((yearsBefore == null) ? 0 : yearsBefore.hashCode());
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
		ContractDeadlineBean other = (ContractDeadlineBean) obj;
		if (category_code == null) {
			if (other.category_code != null)
				return false;
		} else if (!category_code.equals(other.category_code))
			return false;
		if (dayLimit == null) {
			if (other.dayLimit != null)
				return false;
		} else if (!dayLimit.equals(other.dayLimit))
			return false;
		if (daysBefore == null) {
			if (other.daysBefore != null)
				return false;
		} else if (!daysBefore.equals(other.daysBefore))
			return false;
		if (deadlineType == null) {
			if (other.deadlineType != null)
				return false;
		} else if (!deadlineType.equals(other.deadlineType))
			return false;
		if (deadline_desc == null) {
			if (other.deadline_desc != null)
				return false;
		} else if (!deadline_desc.equals(other.deadline_desc))
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
		if (error_desc == null) {
			if (other.error_desc != null)
				return false;
		} else if (!error_desc.equals(other.error_desc))
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
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (monthLimit == null) {
			if (other.monthLimit != null)
				return false;
		} else if (!monthLimit.equals(other.monthLimit))
			return false;
		if (monthsBefore == null) {
			if (other.monthsBefore != null)
				return false;
		} else if (!monthsBefore.equals(other.monthsBefore))
			return false;
		if (operation_desc == null) {
			if (other.operation_desc != null)
				return false;
		} else if (!operation_desc.equals(other.operation_desc))
			return false;
		if (shourLimit == null) {
			if (other.shourLimit != null)
				return false;
		} else if (!shourLimit.equals(other.shourLimit))
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
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (valid != other.valid)
			return false;
		if (yearsBefore == null) {
			if (other.yearsBefore != null)
				return false;
		} else if (!yearsBefore.equals(other.yearsBefore))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ContractDeadlineBean [idn_operation=" + idn_operation + ", operation_desc=" + operation_desc
				+ ", idn_operation_category=" + idn_operation_category + ", category_code=" + category_code
				+ ", idn_operation_term=" + idn_operation_term + ", idn_deadline_limit=" + idn_deadline_limit
				+ ", idn_deadline_type=" + idn_deadline_type + ", deadline_type=" + deadline_type + ", deadline_desc="
				+ deadline_desc + ", idn_operation_deadline=" + idn_operation_deadline + ", deadlineType="
				+ deadlineType + ", type=" + type + ", shourLimit=" + shourLimit + ", dayLimit=" + dayLimit
				+ ", monthLimit=" + monthLimit + ", daysBefore=" + daysBefore + ", monthsBefore=" + monthsBefore
				+ ", yearsBefore=" + yearsBefore + ", startDate=" + startDate + ", endDate=" + endDate + ", valid="
				+ valid + ", user=" + user + ", language=" + language + ", error_desc=" + error_desc + "]";
	}

}
