package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.atos.beans.UserAudBean;

public class ContractNomPointBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_contract_nom_point;
	private BigDecimal idn_contract;
	private BigDecimal idn_contract_point;
	private BigDecimal idn_nomination_point;
	private BigDecimal idn_system;

	private String contract_id;
	private Date startDate;
	private Date endDate;
	private String contract_point;
	private String nomination_point;
	
	public ContractNomPointBean() {
		super();
	}

	public ContractNomPointBean(BigDecimal idn_contract_nom_point, BigDecimal idn_contract, BigDecimal idn_contract_point, BigDecimal idn_nomination_point,
			String contract_id, String contract_point, String nomination_point,
			Date startDate, Date endDate) {
		super();
		this.idn_contract_nom_point = idn_contract_nom_point;
		this.idn_contract = idn_contract;
		this.idn_contract_point = idn_contract_point;
		this.idn_nomination_point = idn_nomination_point;
		this.contract_id = contract_id;
		this.contract_point = contract_point;
		this.nomination_point = nomination_point;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public BigDecimal getIdn_contract_nom_point() {
		return idn_contract_nom_point;
	}

	public void setIdn_contract_nom_point(BigDecimal idn_contract_nom_point) {
		this.idn_contract_nom_point = idn_contract_nom_point;
	}

	public BigDecimal getIdn_contract() {
		return idn_contract;
	}

	public void setIdn_contract(BigDecimal idn_contract) {
		this.idn_contract = idn_contract;
	}

	public BigDecimal getIdn_nomination_point() {
		return idn_nomination_point;
	}

	public void setIdn_nomination_point(BigDecimal idn_nomination_point) {
		this.idn_nomination_point = idn_nomination_point;
	}

	public String getContract_id() {
		return contract_id;
	}

	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getNomination_point() {
		return nomination_point;
	}

	public void setNomination_point(String nomination_point) {
		this.nomination_point = nomination_point;
	}

	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	public BigDecimal getIdn_contract_point() {
		return idn_contract_point;
	}

	public void setIdn_contract_point(BigDecimal idn_contract_point) {
		this.idn_contract_point = idn_contract_point;
	}

	public String getContract_point() {
		return contract_point;
	}

	public void setContract_point(String contract_point) {
		this.contract_point = contract_point;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contract_id == null) ? 0 : contract_id.hashCode());
		result = prime * result + ((contract_point == null) ? 0 : contract_point.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((idn_contract == null) ? 0 : idn_contract.hashCode());
		result = prime * result + ((idn_contract_nom_point == null) ? 0 : idn_contract_nom_point.hashCode());
		result = prime * result + ((idn_contract_point == null) ? 0 : idn_contract_point.hashCode());
		result = prime * result + ((idn_nomination_point == null) ? 0 : idn_nomination_point.hashCode());
		result = prime * result + ((idn_system == null) ? 0 : idn_system.hashCode());
		result = prime * result + ((nomination_point == null) ? 0 : nomination_point.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		ContractNomPointBean other = (ContractNomPointBean) obj;
		if (contract_id == null) {
			if (other.contract_id != null)
				return false;
		} else if (!contract_id.equals(other.contract_id))
			return false;
		if (contract_point == null) {
			if (other.contract_point != null)
				return false;
		} else if (!contract_point.equals(other.contract_point))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (idn_contract == null) {
			if (other.idn_contract != null)
				return false;
		} else if (!idn_contract.equals(other.idn_contract))
			return false;
		if (idn_contract_nom_point == null) {
			if (other.idn_contract_nom_point != null)
				return false;
		} else if (!idn_contract_nom_point.equals(other.idn_contract_nom_point))
			return false;
		if (idn_contract_point == null) {
			if (other.idn_contract_point != null)
				return false;
		} else if (!idn_contract_point.equals(other.idn_contract_point))
			return false;
		if (idn_nomination_point == null) {
			if (other.idn_nomination_point != null)
				return false;
		} else if (!idn_nomination_point.equals(other.idn_nomination_point))
			return false;
		if (idn_system == null) {
			if (other.idn_system != null)
				return false;
		} else if (!idn_system.equals(other.idn_system))
			return false;
		if (nomination_point == null) {
			if (other.nomination_point != null)
				return false;
		} else if (!nomination_point.equals(other.nomination_point))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContractNomPointBean [idn_contract_nom_point=");
		builder.append(idn_contract_nom_point);
		builder.append(", idn_contract=");
		builder.append(idn_contract);
		builder.append(", idn_contract_point=");
		builder.append(idn_contract_point);
		builder.append(", idn_nomination_point=");
		builder.append(idn_nomination_point);
		builder.append(", idn_system=");
		builder.append(idn_system);
		builder.append(", contract_id=");
		builder.append(contract_id);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", contract_point=");
		builder.append(contract_point);
		builder.append(", nomination_point=");
		builder.append(nomination_point);
		builder.append("]");
		return builder.toString();
	}

	

}
