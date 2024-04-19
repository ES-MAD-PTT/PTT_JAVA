package com.atos.beans.quality;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class OffSpecFileBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -440409295342451290L;

	private BigDecimal idnOffspecFile;
	private String fileName;
	private byte[] binaryData;
	private String userName;
	
	public OffSpecFileBean() {}
	
	
	public OffSpecFileBean(String fileName, byte[] binaryData, String userName) {
		this.fileName = fileName;
		this.binaryData = binaryData;
		this.userName = userName;
	}
	
	
	public BigDecimal getIdnOffspecFile() {
		return idnOffspecFile;
	}
	public void setIdnOffspecFile(BigDecimal idnOffspecFile) {
		this.idnOffspecFile = idnOffspecFile;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "OffSpecFileBean [idnOffspecFile=" + idnOffspecFile + ", fileName=" + fileName + ", binaryData="
				+ Arrays.toString(binaryData) + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(binaryData);
		result = prime * result + Objects.hash(fileName, idnOffspecFile);
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
		OffSpecFileBean other = (OffSpecFileBean) obj;
		return Objects.equals(fileName, other.fileName) && Arrays.equals(binaryData, other.binaryData)
				&& Objects.equals(idnOffspecFile, other.idnOffspecFile);
	}
	
	
	
}
