package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ContractShadowBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7731929324670859688L;
	
	private BigDecimal idnContractShadow;
	private BigDecimal idnContract;
	private Date versionDate;
	private Integer shadowTime;
	private Integer shadowPeriod;
	
	public ContractShadowBean(BigDecimal idnContract, int shadowTime, int shadowPeriod) {
		super();
		this.idnContractShadow = null;
		this.idnContract = idnContract;
		this.versionDate = null;
		this.shadowTime = shadowTime;
		this.shadowPeriod = shadowPeriod;
	}
	
	public ContractShadowBean() {
		this.idnContractShadow = null;
		this.idnContract = null;
		this.versionDate = null;
		this.shadowTime = null;
		this.shadowPeriod = null;
	}

	public BigDecimal getIdnContractShadow() {
		return idnContractShadow;
	}

	public void setIdnContractShadow(BigDecimal idnContractShadow) {
		this.idnContractShadow = idnContractShadow;
	}

	public BigDecimal getIdnContract() {
		return idnContract;
	}

	public void setIdnContract(BigDecimal idnContract) {
		this.idnContract = idnContract;
	}

	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}

	public Integer getShadowTime() {
		return shadowTime;
	}

	public void setShadowTime(Integer shadowTime) {
		this.shadowTime = shadowTime;
	}

	public Integer getShadowPeriod() {
		return shadowPeriod;
	}

	public void setShadowPeriod(Integer shadowPeriod) {
		this.shadowPeriod = shadowPeriod;
	}
}
