package com.atos.beans.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

@SuppressWarnings("serial")
public class ReserveBalancingGasSbsBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4255807478558813320L;
	
	private BigDecimal shipper;
	private BigDecimal contractId;
	private BigDecimal zoneId;
	private BigDecimal nominationPoint;
	private BigDecimal capContractCode;
	private BigDecimal idnSystem;
	private Date startDate;
	private Date endDate;
	private BigDecimal quantity;
	private String comments;
	private String operatorComments;
	private Date insertDate;

	public String validate() {
		String result = null;
		if (shipper == null) {
			return "res_bal_sbs_shipper_req";
		}
		if (contractId == null) {
			return "res_bal_sbs_contract_req";
		}
		if (zoneId == null) {
			return "res_bal_sbs_zone_req";
		}
		if (nominationPoint == null) {
			return "res_bal_sbs_point_req";
		}
		if (capContractCode == null) {
			return "res_bal_sbs_cap_req";
		}
		if (startDate == null) {
			return "res_bal_sbs_start_req";
		}
		if (endDate == null) {
			return "res_bal_sbs_end_req";
		}
		if (endDate.before(startDate)) {
			return "res_bal_sbs_date_before";
		}

		if (quantity == null) {
			return "res_bal_sbs_qua_req";
		} else if (quantity.signum() == -1) {
			return "res_bal_sbs_qua_neg";

		}
		return result;
	}

	public BigDecimal getShipper() {
		return shipper;
	}

	public void setShipper(BigDecimal shipper) {
		this.shipper = shipper;
	}

	public BigDecimal getContractId() {
		return contractId;
	}

	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
	}

	public BigDecimal getZoneId() {
		return zoneId;
	}

	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}

	public BigDecimal getNominationPoint() {
		return nominationPoint;
	}

	public void setNominationPoint(BigDecimal nominationPoint) {
		this.nominationPoint = nominationPoint;
	}

	public BigDecimal getCapContractCode() {
		return capContractCode;
	}

	public void setCapContractCode(BigDecimal capContractCode) {
		this.capContractCode = capContractCode;
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

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getOperatorComments() {
		return operatorComments;
	}

	public void setOperatorComments(String operatorComments) {
		this.operatorComments = operatorComments;
	}

	public BigDecimal getIdnSystem() {
		return idnSystem;
	}

	public void setIdnSystem(BigDecimal idnSystem) {
		this.idnSystem = idnSystem;
	}
	
	

}
