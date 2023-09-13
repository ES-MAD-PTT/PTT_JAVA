package com.atos.beans.tariff;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class CreditDebitNoteDetailBean extends UserAudBean implements Serializable{
	private static final long serialVersionUID = -5255211382691787452L;
	
	private BigDecimal noteId;
	private BigDecimal charge;
	private String chargeDesc;
	private Date originalPeriod;
	private String contractType;
	private String contractNum;
	private BigDecimal contractNumId;
	private BigDecimal chargeDetail;
	private String chargeDetailDesc;
	private BigDecimal quantity;
	private String unit;
	private BigDecimal fee;
	private BigDecimal amount;
	private String remark;
	private boolean updated=false;
	private boolean newBean=false;
	private String username;
	
	
	public CreditDebitNoteDetailBean() {
		super();
		this.noteId = null;
		this.charge = null;
		this.chargeDesc = null;
		this.originalPeriod = null;
		this.contractType = null;
		this.contractNum = null;
		this.contractNumId = null;
		this.chargeDetail = null;
		this.chargeDetailDesc = null;
		this.quantity = null;
		this.unit = null;
		this.fee = null;
		this.amount = null;
		this.remark = null;
		this.updated = false;
		this.newBean = false;
		this.username = null;
	}

	public CreditDebitNoteDetailBean(BigDecimal noteId, BigDecimal charge, String chargeDesc, Date originalPeriod, String contractType, String contractNum,
			BigDecimal contractNumId, BigDecimal chargeDetail, String chargeDetailDesc, BigDecimal quantity, String unit, BigDecimal fee, BigDecimal amount,
			String remark, boolean updated, boolean newBean, String username) {
		super();
		this.noteId = noteId;
		this.charge = charge;
		this.chargeDesc = chargeDesc;
		this.originalPeriod = originalPeriod;
		this.contractType = contractType;
		this.contractNum = contractNum;
		this.contractNumId = contractNumId;
		this.chargeDetail = chargeDetail;
		this.chargeDetailDesc = chargeDetailDesc;
		this.quantity = quantity;
		this.unit = unit;
		this.fee = fee;
		this.amount = amount;
		this.remark = remark;
		this.updated = updated;
		this.newBean = newBean;
		this.username = username;
	}
	
	public BigDecimal getNoteId() {
		return noteId;
	}

	public void setNoteId(BigDecimal noteId) {
		this.noteId = noteId;
	}

	public BigDecimal getCharge() {
		return charge;
	}
	
	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}
	
	public Date getOriginalPeriod() {
		return originalPeriod;
	}
	
	public void setOriginalPeriod(Date originalPeriod) {
		this.originalPeriod = originalPeriod;
	}
	
	public String getContractType() {
		return contractType;
	}
	
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	
	public String getContractNum() {
		return contractNum;
	}
	
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	
	public BigDecimal getContractNumId() {
		return contractNumId;
	}

	public void setContractNumId(BigDecimal contractNumId) {
		this.contractNumId = contractNumId;
	}

	public BigDecimal getChargeDetail() {
		return chargeDetail;
	}
	
	public void setChargeDetail(BigDecimal chargeDetail) {
		this.chargeDetail = chargeDetail;
	}
	
	public BigDecimal getQuantity() {
		return quantity;
	}
	
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public BigDecimal getFee() {
		return fee;
	}
	
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getChargeDesc() {
		return chargeDesc;
	}

	public void setChargeDesc(String chargeDesc) {
		this.chargeDesc = chargeDesc;
	}

	public String getChargeDetailDesc() {
		return chargeDetailDesc;
	}

	public void setChargeDetailDesc(String chargeDetailDesc) {
		this.chargeDetailDesc = chargeDetailDesc;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public boolean isNewBean() {
		return newBean;
	}

	public void setNewBean(boolean newBean) {
		this.newBean = newBean;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((charge == null) ? 0 : charge.hashCode());
		result = prime * result + ((chargeDesc == null) ? 0 : chargeDesc.hashCode());
		result = prime * result + ((chargeDetail == null) ? 0 : chargeDetail.hashCode());
		result = prime * result + ((chargeDetailDesc == null) ? 0 : chargeDetailDesc.hashCode());
		result = prime * result + ((contractNum == null) ? 0 : contractNum.hashCode());
		result = prime * result + ((contractNumId == null) ? 0 : contractNumId.hashCode());
		result = prime * result + ((contractType == null) ? 0 : contractType.hashCode());
		result = prime * result + (newBean ? 1231 : 1237);
		result = prime * result + ((noteId == null) ? 0 : noteId.hashCode());
		result = prime * result + ((originalPeriod == null) ? 0 : originalPeriod.hashCode());
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
		CreditDebitNoteDetailBean other = (CreditDebitNoteDetailBean) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (charge == null) {
			if (other.charge != null)
				return false;
		} else if (!charge.equals(other.charge))
			return false;
		if (chargeDesc == null) {
			if (other.chargeDesc != null)
				return false;
		} else if (!chargeDesc.equals(other.chargeDesc))
			return false;
		if (chargeDetail == null) {
			if (other.chargeDetail != null)
				return false;
		} else if (!chargeDetail.equals(other.chargeDetail))
			return false;
		if (chargeDetailDesc == null) {
			if (other.chargeDetailDesc != null)
				return false;
		} else if (!chargeDetailDesc.equals(other.chargeDetailDesc))
			return false;
		if (contractNum == null) {
			if (other.contractNum != null)
				return false;
		} else if (!contractNum.equals(other.contractNum))
			return false;
		if (contractNumId == null) {
			if (other.contractNumId != null)
				return false;
		} else if (!contractNumId.equals(other.contractNumId))
			return false;
		if (contractType == null) {
			if (other.contractType != null)
				return false;
		} else if (!contractType.equals(other.contractType))
			return false;
		if (newBean != other.newBean)
			return false;
		if (noteId == null) {
			if (other.noteId != null)
				return false;
		} else if (!noteId.equals(other.noteId))
			return false;
		if (originalPeriod == null) {
			if (other.originalPeriod != null)
				return false;
		} else if (!originalPeriod.equals(other.originalPeriod))
			return false;
		return true;
	}
	
	
}
