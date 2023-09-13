package com.atos.beans.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ContractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 628084701607721726L;
	private BigDecimal idn_contract;
	private String contract_code;
	private Date contract_end;
	private Date contract_start;

	public BigDecimal getIdn_contract() {
		return idn_contract;
	}

	public void setIdn_contract(BigDecimal idn_contract) {
		this.idn_contract = idn_contract;
	}

	public String getContract_code() {
		return contract_code;
	}

	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}

	public Date getContract_end() {
		return contract_end;
	}

	public void setContract_end(Date contract_end) {
		this.contract_end = contract_end;
	}

	public Date getContract_start() {
		return contract_start;
	}

	public void setContract_start(Date contract_start) {
		this.contract_start = contract_start;
	}


}
