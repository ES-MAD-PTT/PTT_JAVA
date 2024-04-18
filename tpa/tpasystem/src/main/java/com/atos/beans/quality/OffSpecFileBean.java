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
	private byte[] finaryData;
	private String userName;
	
	public OffSpecFileBean() {}
	
	
	public OffSpecFileBean(String fileName, byte[] finaryData, String userName) {
		this.fileName = fileName;
		this.finaryData = finaryData;
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
	public byte[] getFinaryData() {
		return finaryData;
	}
	public void setFinaryData(byte[] finaryData) {
		this.finaryData = finaryData;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "OffSpecFileBean [idnOffspecFile=" + idnOffspecFile + ", fileName=" + fileName + ", finaryData="
				+ Arrays.toString(finaryData) + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(finaryData);
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
		return Objects.equals(fileName, other.fileName) && Arrays.equals(finaryData, other.finaryData)
				&& Objects.equals(idnOffspecFile, other.idnOffspecFile);
	}
	
	
	
}
