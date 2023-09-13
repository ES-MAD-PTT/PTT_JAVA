package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;

public class ContractAttachTypeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7351938664218293667L;

	public static final String BANK_GUARANTEE = "BANK_GUARANTEE";
	
	private BigDecimal contractAttachTypeId;
	private String typeCode;
	private Integer maxNumber;
	
	public ContractAttachTypeBean() {
		super();
		this.contractAttachTypeId = null;
		this.typeCode = null;
		this.maxNumber = null;
	}

	public BigDecimal getContractAttachTypeId() {
		return contractAttachTypeId;
	}

	public void setContractAttachTypeId(BigDecimal contractAttachTypeId) {
		this.contractAttachTypeId = contractAttachTypeId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}


	public Integer getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(Integer maxNumber) {
		this.maxNumber = maxNumber;
	}

	@Override
	public String toString() {
		return "ContractAttachType [contractAttachTypeId=" + contractAttachTypeId + ", typeCode=" + typeCode
				+ ", maxNumber=" + maxNumber + "]";
	}

}
