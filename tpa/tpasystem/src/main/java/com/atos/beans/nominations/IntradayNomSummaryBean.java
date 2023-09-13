package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class IntradayNomSummaryBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4341204774785510709L;
	
	private Date gasDay;
	private String nominationCode;
	private String intraday;
	private String contractCode;
	private BigDecimal contractId; 
	private BigDecimal shipperId;
	private String shipperName;
	private String systemPoint;
	private String unit;
	private BigDecimal h1;
	private BigDecimal h2;
	private BigDecimal h3;
	private BigDecimal h4;
	private BigDecimal h5;
	private BigDecimal h6;
	private BigDecimal h7;
	private BigDecimal h8;
	private BigDecimal h9;
	private BigDecimal h10;
	private BigDecimal h11;
	private BigDecimal h12;
	private BigDecimal h13;
	private BigDecimal h14;
	private BigDecimal h15;
	private BigDecimal h16;
	private BigDecimal h17;
	private BigDecimal h18;
	private BigDecimal h19;
	private BigDecimal h20;
	private BigDecimal h21;
	private BigDecimal h22;
	private BigDecimal h23;
	private BigDecimal h24;
	private BigDecimal total;
	
	public IntradayNomSummaryBean() {
		super();
		this.gasDay = null;
		this.nominationCode = null;
		this.intraday = null;
		this.contractCode = null;
		this.contractId = null;
		this.shipperName = null;
		this.shipperId = null;
		this.systemPoint = null;
		this.unit = null;
		this.h1 = null;
		this.h2 = null;
		this.h3 = null;
		this.h4 = null;
		this.h5 = null;
		this.h6 = null;
		this.h7 = null;
		this.h8 = null;
		this.h9 = null;
		this.h10 = null;
		this.h11 = null;
		this.h12 = null;
		this.h13 = null;
		this.h14 = null;
		this.h15 = null;
		this.h16 = null;
		this.h17 = null;
		this.h18 = null;
		this.h19 = null;
		this.h20 = null;
		this.h21 = null;
		this.h22 = null;
		this.h23 = null;
		this.h24 = null;
		this.total = null;
	}
	
	public IntradayNomSummaryBean(Date gasDay, String nominationCode, String intraday, String contractCode, BigDecimal contractId,
			String shipperName, BigDecimal shipperId, String systemPoint, String unit, BigDecimal h1, BigDecimal h2, BigDecimal h3,
			BigDecimal h4, BigDecimal h5, BigDecimal h6, BigDecimal h7, BigDecimal h8, BigDecimal h9, BigDecimal h10,
			BigDecimal h11, BigDecimal h12, BigDecimal h13, BigDecimal h14, BigDecimal h15, BigDecimal h16,
			BigDecimal h17, BigDecimal h18, BigDecimal h19, BigDecimal h20, BigDecimal h21, BigDecimal h22, BigDecimal h23, BigDecimal h24, BigDecimal total) {
		super();
		this.gasDay = gasDay;
		this.nominationCode = nominationCode;
		this.intraday = intraday;
		this.contractCode = contractCode;
		this.contractId = contractId;
		this.shipperName = shipperName;
		this.shipperId = shipperId;
		this.systemPoint = systemPoint;
		this.unit = unit;
		this.h1 = h1;
		this.h2 = h2;
		this.h3 = h3;
		this.h4 = h4;
		this.h5 = h5;
		this.h6 = h6;
		this.h7 = h7;
		this.h8 = h8;
		this.h9 = h9;
		this.h10 = h10;
		this.h11 = h11;
		this.h12 = h12;
		this.h13 = h13;
		this.h14 = h14;
		this.h15 = h15;
		this.h16 = h16;
		this.h17 = h17;
		this.h18 = h18;
		this.h19 = h19;
		this.h20 = h20;
		this.h21 = h21;
		this.h22 = h22;
		this.h23 = h23;
		this.h24 = h24;
		this.total = total;
	}

	public Date getGasDay() {
		return gasDay;
	}

	public void setGasDay(Date gasDay) {
		this.gasDay = gasDay;
	}

	public String getNominationCode() {
		return nominationCode;
	}

	public void setNominationCode(String nominationCode) {
		this.nominationCode = nominationCode;
	}

	public String getIntraday() {
		return intraday;
	}

	public void setIntraday(String intraday) {
		this.intraday = intraday;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public BigDecimal getShipperId() {
		return shipperId;
	}

	public void setShipperId(BigDecimal shipperId) {
		this.shipperId = shipperId;
	}

	public String getSystemPoint() {
		return systemPoint;
	}

	public void setSystemPoint(String systemPoint) {
		this.systemPoint = systemPoint;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getH1() {
		return h1;
	}

	public void setH1(BigDecimal h1) {
		this.h1 = h1;
	}

	public BigDecimal getH2() {
		return h2;
	}

	public void setH2(BigDecimal h2) {
		this.h2 = h2;
	}

	public BigDecimal getH3() {
		return h3;
	}

	public void setH3(BigDecimal h3) {
		this.h3 = h3;
	}

	public BigDecimal getH4() {
		return h4;
	}

	public void setH4(BigDecimal h4) {
		this.h4 = h4;
	}

	public BigDecimal getH5() {
		return h5;
	}

	public void setH5(BigDecimal h5) {
		this.h5 = h5;
	}

	public BigDecimal getH6() {
		return h6;
	}

	public void setH6(BigDecimal h6) {
		this.h6 = h6;
	}

	public BigDecimal getH7() {
		return h7;
	}

	public void setH7(BigDecimal h7) {
		this.h7 = h7;
	}

	public BigDecimal getH8() {
		return h8;
	}

	public void setH8(BigDecimal h8) {
		this.h8 = h8;
	}

	public BigDecimal getH9() {
		return h9;
	}

	public void setH9(BigDecimal h9) {
		this.h9 = h9;
	}

	public BigDecimal getH10() {
		return h10;
	}

	public void setH10(BigDecimal h10) {
		this.h10 = h10;
	}

	public BigDecimal getH11() {
		return h11;
	}

	public void setH11(BigDecimal h11) {
		this.h11 = h11;
	}

	public BigDecimal getH12() {
		return h12;
	}

	public void setH12(BigDecimal h12) {
		this.h12 = h12;
	}

	public BigDecimal getH13() {
		return h13;
	}

	public void setH13(BigDecimal h13) {
		this.h13 = h13;
	}

	public BigDecimal getH14() {
		return h14;
	}

	public void setH14(BigDecimal h14) {
		this.h14 = h14;
	}

	public BigDecimal getH15() {
		return h15;
	}

	public void setH15(BigDecimal h15) {
		this.h15 = h15;
	}

	public BigDecimal getH16() {
		return h16;
	}

	public void setH16(BigDecimal h16) {
		this.h16 = h16;
	}

	public BigDecimal getH17() {
		return h17;
	}

	public void setH17(BigDecimal h17) {
		this.h17 = h17;
	}

	public BigDecimal getH18() {
		return h18;
	}

	public void setH18(BigDecimal h18) {
		this.h18 = h18;
	}

	public BigDecimal getH19() {
		return h19;
	}

	public void setH19(BigDecimal h19) {
		this.h19 = h19;
	}

	public BigDecimal getH20() {
		return h20;
	}

	public void setH20(BigDecimal h20) {
		this.h20 = h20;
	}

	public BigDecimal getH21() {
		return h21;
	}

	public void setH21(BigDecimal h21) {
		this.h21 = h21;
	}

	public BigDecimal getH22() {
		return h22;
	}

	public void setH22(BigDecimal h22) {
		this.h22 = h22;
	}

	public BigDecimal getH23() {
		return h23;
	}

	public void setH23(BigDecimal h23) {
		this.h23 = h23;
	}

	public BigDecimal getH24() {
		return h24;
	}

	public void setH24(BigDecimal h24) {
		this.h24 = h24;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getContractId() {
		return contractId;
	}

	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
	}
	
}