package com.atos.beans.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InstructedOperationFlowShippersBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7488987616996704450L;

	private BigDecimal idn_intraday_gas_flow;
	private Date timestamp;
	private BigDecimal shipperId;
	private String shipperCode;
	private BigDecimal zoneId;
	private String zoneCode; 
	private BigDecimal accImbalanceImbalanceInventory;
	private BigDecimal accMargin;
	private String flowType;
	private BigDecimal opInsFlowOrderMMBTU;
	private BigDecimal opInsFlowOrderMMSCF;
	private BigDecimal opInsFlowOrderMMSCFH;
	private String resolvedTime;
	private BigDecimal hv;
	private String operatorComments;
	private String shipperComments;
	private Date versionDate;
	private String username;
	private BigDecimal idn_intraday_gas_flow_file;
	private String file_name;
	private String published;	
	
	private boolean checkPublicate=true;
	
	public InstructedOperationFlowShippersBean() {
		
		super();
		this.idn_intraday_gas_flow = null;
		this.timestamp = null;
		this.shipperId = null;
		this.shipperCode = null;
		this.zoneId = null;
		this.zoneCode = null;
		this.accImbalanceImbalanceInventory = null;
		this.accMargin = null;
		this.flowType = null;
		this.opInsFlowOrderMMBTU = null;
		this.opInsFlowOrderMMSCF = null;
		this.opInsFlowOrderMMSCFH = null;
		this.resolvedTime = null;
		this.hv = null;
		this.operatorComments = null;
		this.shipperComments = null;
		this.versionDate = null;
		this.username = null;
		this.idn_intraday_gas_flow_file = null;
		this.checkPublicate = true;
		this.file_name = null;
		this.published= null;
	}

	public InstructedOperationFlowShippersBean(BigDecimal idn_intraday_gas_flow, Date timestamp, BigDecimal shipperId, String shipperCode,
			BigDecimal zonetId, String zoneCode, BigDecimal accImabalnce, BigDecimal accImabalnceInventory,
			BigDecimal accMargin, String flowType, BigDecimal opInsFlowOrderMMBTU, BigDecimal opInsFlowOrderMMSCF,
			BigDecimal opInsFlowOrder, String resolvedTime, BigDecimal hv, String operatorComments,
			String shipperComments, Date versionDate, String username, BigDecimal idn_intraday_gas_flow_file, boolean checkPublicate, String file_name, String published) {
		super();
		this.idn_intraday_gas_flow = idn_intraday_gas_flow;
		this.timestamp = timestamp;
		this.shipperId = shipperId;
		this.shipperCode = shipperCode;
		this.zoneId = zonetId;
		this.zoneCode = zoneCode;
		this.accImbalanceImbalanceInventory = accImabalnceInventory;
		this.accMargin = accMargin;
		this.flowType = flowType;
		this.opInsFlowOrderMMBTU = opInsFlowOrderMMBTU;
		this.opInsFlowOrderMMSCF = opInsFlowOrderMMSCF;
		this.opInsFlowOrderMMSCFH = opInsFlowOrder;
		this.resolvedTime = resolvedTime;
		this.hv = hv;
		this.operatorComments = operatorComments;
		this.shipperComments = shipperComments;
		this.versionDate = versionDate;
		this.username = username;
		this.idn_intraday_gas_flow_file = idn_intraday_gas_flow_file;
		this.checkPublicate = checkPublicate;
		this.file_name = file_name;
		this.published = published;
	}

	public BigDecimal getIdn_intraday_gas_flow() {
		return idn_intraday_gas_flow;
	}

	public void setIdn_intraday_gas_flow(BigDecimal idn_intraday_gas_flow) {
		this.idn_intraday_gas_flow = idn_intraday_gas_flow;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getShipperId() {
		return shipperId;
	}

	public void setShipperId(BigDecimal shipperId) {
		this.shipperId = shipperId;
	}

	public String getShipperCode() {
		return shipperCode;
	}

	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}

	public BigDecimal getZoneId() {
		return zoneId;
	}

	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public BigDecimal getAccImbalanceImbalanceInventory() {
		return accImbalanceImbalanceInventory;
	}

	public void setAccImbalanceImbalanceInventory(BigDecimal accImbalanceImabalanceInventory) {
		this.accImbalanceImbalanceInventory = accImbalanceImabalanceInventory;
	}

	public BigDecimal getAccMargin() {
		return accMargin;
	}

	public void setAccMargin(BigDecimal accMargin) {
		this.accMargin = accMargin;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public BigDecimal getOpInsFlowOrderMMBTU() {
		return opInsFlowOrderMMBTU;
	}

	public void setOpInsFlowOrderMMBTU(BigDecimal opInsFlowOrderMMBTU) {
		this.opInsFlowOrderMMBTU = opInsFlowOrderMMBTU;
	}

	public BigDecimal getOpInsFlowOrderMMSCF() {
		return opInsFlowOrderMMSCF;
	}

	public void setOpInsFlowOrderMMSCF(BigDecimal opInsFlowOrderMMSCF) {
		this.opInsFlowOrderMMSCF = opInsFlowOrderMMSCF;
	}

	public BigDecimal getOpInsFlowOrderMMSCFH() {
		return opInsFlowOrderMMSCFH;
	}

	public void setOpInsFlowOrderMMSCFH(BigDecimal opInsFlowOrderMMSCFH) {
		this.opInsFlowOrderMMSCFH = opInsFlowOrderMMSCFH;
	}

	public String getResolvedTime() {
		return resolvedTime;
	}

	public void setResolvedTime(String resolvedTime) {
		this.resolvedTime = resolvedTime;
	}

	public BigDecimal getHv() {
		return hv;
	}

	public void setHv(BigDecimal hv) {
		this.hv = hv;
	}

	public String getOperatorComments() {
		return operatorComments;
	}

	public void setOperatorComments(String operatorComments) {
		this.operatorComments = operatorComments;
	}

	public String getShipperComments() {
		return shipperComments;
	}

	public void setShipperComments(String shipperComments) {
		this.shipperComments = shipperComments;
	}

	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getIdn_intraday_gas_flow_file() {
		return idn_intraday_gas_flow_file;
	}

	public void setIdn_intraday_gas_flow_file(BigDecimal idn_intraday_gas_flow_file) {
		this.idn_intraday_gas_flow_file = idn_intraday_gas_flow_file;
	}

	public boolean isCheckPublicate() {
		return checkPublicate;
	}

	public void setCheckPublicate(boolean checkPublicate) {
		this.checkPublicate = checkPublicate;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
		if(published!=null) {
			if(published.equals("Y")) {
				this.checkPublicate = true;
			} else {
				this.checkPublicate = false;
			}
				
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InstructedOperationFlowShippersBean [idn_intraday_gas_flow=");
		builder.append(idn_intraday_gas_flow);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append(", shipperId=");
		builder.append(shipperId);
		builder.append(", shipperCode=");
		builder.append(shipperCode);
		builder.append(", zoneId=");
		builder.append(zoneId);
		builder.append(", zoneCode=");
		builder.append(zoneCode);
		builder.append(", accImbalanceImbalanceInventory=");
		builder.append(accImbalanceImbalanceInventory);
		builder.append(", accMargin=");
		builder.append(accMargin);
		builder.append(", flowType=");
		builder.append(flowType);
		builder.append(", opInsFlowOrderMMBTU=");
		builder.append(opInsFlowOrderMMBTU);
		builder.append(", opInsFlowOrderMMSCF=");
		builder.append(opInsFlowOrderMMSCF);
		builder.append(", opInsFlowOrderMMSCFH=");
		builder.append(opInsFlowOrderMMSCFH);
		builder.append(", resolvedTime=");
		builder.append(resolvedTime);
		builder.append(", hv=");
		builder.append(hv);
		builder.append(", operatorComments=");
		builder.append(operatorComments);
		builder.append(", shipperComments=");
		builder.append(shipperComments);
		builder.append(", versionDate=");
		builder.append(versionDate);
		builder.append(", username=");
		builder.append(username);
		builder.append(", idn_intraday_gas_flow_file=");
		builder.append(idn_intraday_gas_flow_file);
		builder.append(", file_name=");
		builder.append(file_name);
		builder.append(", published=");
		builder.append(published);
		builder.append(", checkPublicate=");
		builder.append(checkPublicate);
		builder.append("]");
		return builder.toString();
	}	
}
