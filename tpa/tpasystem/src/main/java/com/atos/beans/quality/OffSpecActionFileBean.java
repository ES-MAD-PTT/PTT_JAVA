package com.atos.beans.quality;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class OffSpecActionFileBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -440409295342451290L;

	private BigDecimal idnOffspecActionFile;
	private BigDecimal idnOffspec;
	private BigDecimal groupId;
	private BigDecimal idnAction;
	private String fileName;
	private byte[] binaryData;
	private String userName;
	
	public OffSpecActionFileBean() {}
	
	
	public OffSpecActionFileBean(BigDecimal idnOffspec, BigDecimal groupId, BigDecimal idnAction, String fileName, byte[] binaryData, String userName) {
		this.idnOffspec = idnOffspec;
		this.groupId = groupId;
		this.idnAction = idnAction;
		this.fileName = fileName;
		this.binaryData = binaryData;
		this.userName = userName;
	}
	
	public OffSpecActionFileBean(BigDecimal idnOffspec, String fileName, byte[] binaryData, String userName) {
		this.idnOffspec = idnOffspec;
		this.fileName = fileName;
		this.binaryData = binaryData;
		this.userName = userName;
	}
	
	
	
	public BigDecimal getIdnOffspecActionFile() {
		return idnOffspecActionFile;
	}


	public void setIdnOffspecActionFile(BigDecimal idnOffspecActionFile) {
		this.idnOffspecActionFile = idnOffspecActionFile;
	}


	public BigDecimal getIdnOffspec() {
		return idnOffspec;
	}


	public void setIdnOffspec(BigDecimal idnOffspec) {
		this.idnOffspec = idnOffspec;
	}


	public BigDecimal getGroupId() {
		return groupId;
	}


	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}


	public BigDecimal getIdnAction() {
		return idnAction;
	}


	public void setIdnAction(BigDecimal idnAction) {
		this.idnAction = idnAction;
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
		return "OffSpecActionFileBean [idnOffspecActionFile=" + idnOffspecActionFile + ", idnOffspec=" + idnOffspec
				+ ", fileName=" + fileName + ", binaryData=" + Arrays.toString(binaryData) + ", userName=" + userName
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(binaryData);
		result = prime * result + Objects.hash(fileName, idnOffspec, idnOffspecActionFile, userName);
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
		OffSpecActionFileBean other = (OffSpecActionFileBean) obj;
		return Arrays.equals(binaryData, other.binaryData) && Objects.equals(fileName, other.fileName)
				&& Objects.equals(idnOffspec, other.idnOffspec)
				&& Objects.equals(idnOffspecActionFile, other.idnOffspecActionFile)
				&& Objects.equals(userName, other.userName);
	}
	
	
	
}
