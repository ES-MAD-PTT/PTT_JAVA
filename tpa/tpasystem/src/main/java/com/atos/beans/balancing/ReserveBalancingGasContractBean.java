package com.atos.beans.balancing;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.primefaces.model.DefaultStreamedContent;

import com.atos.beans.UserAudBean;

public class ReserveBalancingGasContractBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2779031056627061984L;

	private BigDecimal contractId;
	private String contractCode;
	private BigDecimal shipperId;
	private String shipperCode;
	private String shortName;
	private String operatorComments;
	private String fileName;
	private BigDecimal idnSystem;
	// binaryData y scFile deben tener la misma info. scFile se genera a partir de binaryData.
	// binaryData necesita metodo set (desde vista o desde consulta a BD) y set (insert a BD)
	// y scFile solo tendra metodo get (desde la vista), y se genera a partir de binaryData.
	// BinaryData no se pinta en el toString();
	private byte[] binaryData;	
	transient private DefaultStreamedContent scFile;
	private List<ReserveBalancingGasContractDetailBean> details;
		
	public ReserveBalancingGasContractBean() {
		this.contractId = null;
		this.contractCode = null;		
		this.shipperId = null;
		this.shipperCode = null;
		this.shortName = null;
		this.operatorComments = null;
		this.fileName = null;
		this.binaryData = null;
		this.scFile = null;
		this.details = new ArrayList<ReserveBalancingGasContractDetailBean>();
	}

	public BigDecimal getContractId() {
		return contractId;
	}

	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
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

	public String getOperatorComments() {
		return operatorComments;
	}

	public void setOperatorComments(String operatorComments) {
		this.operatorComments = operatorComments;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getBinaryData() {
		return binaryData;
	}

	public void setBinaryData(byte[] binaryData) {
		this.binaryData = binaryData;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public DefaultStreamedContent getScFile() {
		
		if( binaryData != null ) {
			ByteArrayInputStream bais = new ByteArrayInputStream(binaryData);
			scFile = new DefaultStreamedContent(bais);
			scFile.setName(fileName);
			// No se conoce el contentType.
			//scFile.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); 
		}
		return scFile;
	}

	// scFile solo tendra metodo get (desde la vista), y se genera a partir de binaryData.
	//public void setScFile(DefaultStreamedContent scFile) {
	//	this.scFile = scFile;
	//}

	public List<ReserveBalancingGasContractDetailBean> getDetails() {
		return details;
	}

	public void setDetails(List<ReserveBalancingGasContractDetailBean> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "ReserveBalancingGasContractBean [contractId=" + contractId + ", contractCode=" + contractCode
				+ ", shipperId=" + shipperId + ", shipperCode=" + shipperCode + ", shortName=" + shortName
				+ ", operatorComments=" + operatorComments + ", fileName=" + fileName + ", idnSystem=" + idnSystem
				+ ", binaryData=" + Arrays.toString(binaryData) + ", details=" + details + "]";
	}

	public BigDecimal getIdnSystem() {
		return idnSystem;
	}

	public void setIdnSystem(BigDecimal idnSystem) {
		this.idnSystem = idnSystem;
	}

}
