package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;

import com.atos.beans.UserAudBean;

public class ContractConsolidateBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3285041848341873801L;

	private BigDecimal idnContractConsolidate;
	private BigDecimal idnContractAgreement;
	private BigDecimal idnContract;

	public ContractConsolidateBean(BigDecimal idnContractAgreement,
			BigDecimal idnContract) {
		super();
		this.idnContractConsolidate = null;
		this.idnContractAgreement = idnContractAgreement;
		this.idnContract = idnContract;
	}

	public ContractConsolidateBean() {
		this.idnContractConsolidate = null;
		this.idnContractAgreement = null;
		this.idnContract = null;
	}

	public BigDecimal getIdnContractConsolidate() {
		return idnContractConsolidate;
	}

	public void setIdnContractConsolidate(BigDecimal idnContractConsolidate) {
		this.idnContractConsolidate = idnContractConsolidate;
	}

	public BigDecimal getIdnContractAgreement() {
		return idnContractAgreement;
	}

	public void setIdnContractAgreement(BigDecimal idnContractAgreement) {
		this.idnContractAgreement = idnContractAgreement;
	}

	public BigDecimal getIdnContract() {
		return idnContract;
	}

	public void setIdnContract(BigDecimal idnContract) {
		this.idnContract = idnContract;
	}

	@Override
	public String toString() {
		return "ContractConsolidateBean [idnContractConsolidate=" + idnContractConsolidate + ", idnContractAgreement="
				+ idnContractAgreement + ", idnContract=" + idnContract + "]";
	}
}
