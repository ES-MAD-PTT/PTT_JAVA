package com.atos.beans.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.primefaces.model.UploadedFile;

import com.atos.beans.UserAudBean;

@SuppressWarnings("serial")
//@ManagedBean(name="reserveBalancingGasBean")
//@ViewScoped
public class ReserveBalancingGasBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4255807478558813320L;
	
	private UploadedFile file;
	private String fileName;
	private byte[] binaryData;
	private BigDecimal shipper;
	private BigDecimal contractId;
	private Date selDate;
	
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
	public Date getSelDate() {
		return selDate;
	}
	public void setSelDate(Date selDate) {
		this.selDate = selDate;
	}
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	

}
