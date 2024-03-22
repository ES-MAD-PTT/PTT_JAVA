package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.atos.beans.UserAudBean;

public class ContractNomPointBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_contract_nom_point;
	private BigDecimal idn_contract;
	private BigDecimal idn_contract_point;
	private BigDecimal idn_nomination_point;
	private BigDecimal idn_system;
	private String shipper;
	private BigDecimal idn_shipper;

	private String contract_id;
	private Date startDate;
	private Date endDate;
	private Date startDateActive;
	private Date endDateActive;
	private String contract_point;
	private String nomination_point;
	private List<BigDecimal> lisIdnNominationPoint;
	
	public ContractNomPointBean() {
		super();
	}

	public ContractNomPointBean(BigDecimal idn_contract_nom_point, BigDecimal idn_contract,
			BigDecimal idn_contract_point, BigDecimal idn_nomination_point, BigDecimal idn_system, String shipper,
			BigDecimal idn_shipper, String contract_id, Date startDate, Date endDate, Date startDateActive,
			Date endDateActive, String contract_point, String nomination_point,
			List<BigDecimal> lisIdnNominationPoint) {
		super();
		this.idn_contract_nom_point = idn_contract_nom_point;
		this.idn_contract = idn_contract;
		this.idn_contract_point = idn_contract_point;
		this.idn_nomination_point = idn_nomination_point;
		this.idn_system = idn_system;
		this.shipper = shipper;
		this.idn_shipper = idn_shipper;
		this.contract_id = contract_id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startDateActive = startDateActive;
		this.endDateActive = endDateActive;
		this.contract_point = contract_point;
		this.nomination_point = nomination_point;
		this.lisIdnNominationPoint = lisIdnNominationPoint;
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

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public BigDecimal getIdn_shipper() {
		return idn_shipper;
	}

	public void setIdn_shipper(BigDecimal idn_shipper) {
		this.idn_shipper = idn_shipper;
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

	public List<BigDecimal> getLisIdnNominationPoint() {
		return lisIdnNominationPoint;
	}

	public void setLisIdnNominationPoint(List<BigDecimal> lisIdnNominationPoint) {
		this.lisIdnNominationPoint = lisIdnNominationPoint;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contract_id, contract_point, endDate, endDateActive, idn_contract, idn_contract_nom_point,
				idn_contract_point, idn_nomination_point, idn_shipper, idn_system, lisIdnNominationPoint,
				nomination_point, shipper, startDate, startDateActive);
	}

	public Date getStartDateActive() {
		return startDateActive;
	}


	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}


	public Date getEndDateActive() {
		return endDateActive;
	}


	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
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
		return Objects.equals(contract_id, other.contract_id) && Objects.equals(contract_point, other.contract_point)
				&& Objects.equals(endDate, other.endDate) && Objects.equals(endDateActive, other.endDateActive)
				&& Objects.equals(idn_contract, other.idn_contract)
				&& Objects.equals(idn_contract_nom_point, other.idn_contract_nom_point)
				&& Objects.equals(idn_contract_point, other.idn_contract_point)
				&& Objects.equals(idn_nomination_point, other.idn_nomination_point)
				&& Objects.equals(idn_shipper, other.idn_shipper) && Objects.equals(idn_system, other.idn_system)
				&& Objects.equals(lisIdnNominationPoint, other.lisIdnNominationPoint)
				&& Objects.equals(nomination_point, other.nomination_point) && Objects.equals(shipper, other.shipper)
				&& Objects.equals(startDate, other.startDate) && Objects.equals(startDateActive, other.startDateActive);
	}

	@Override
	public String toString() {
		return "ContractNomPointBean [idn_contract_nom_point=" + idn_contract_nom_point + ", idn_contract="
				+ idn_contract + ", idn_contract_point=" + idn_contract_point + ", idn_nomination_point="
				+ idn_nomination_point + ", idn_system=" + idn_system + ", shipper=" + shipper + ", idn_shipper="
				+ idn_shipper + ", contract_id=" + contract_id + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", startDateActive=" + startDateActive + ", endDateActive=" + endDateActive + ", contract_point="
				+ contract_point + ", nomination_point=" + nomination_point + ", lisIdnNominationPoint="
				+ lisIdnNominationPoint + "]";
	}

	

}
