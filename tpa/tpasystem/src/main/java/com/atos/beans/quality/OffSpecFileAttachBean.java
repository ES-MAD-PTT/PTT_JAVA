package com.atos.beans.quality;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class OffSpecFileAttachBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -440409295342451290L;

	private BigDecimal idnOffspecFileAttach;
	private BigDecimal idnOffspec;
	private BigDecimal idnOffspecFile;
	private String userName;
	
	public OffSpecFileAttachBean() {}
	
	
	public OffSpecFileAttachBean(BigDecimal idnOffspec, BigDecimal idnOffspecFile, String userName) {
		this.idnOffspec = idnOffspec;
		this.idnOffspecFile = idnOffspecFile;
		this.userName = userName;
	}
	public BigDecimal getIdnOffspecFileAttach() {
		return idnOffspecFileAttach;
	}
	public void setIdnOffspecFileAttach(BigDecimal idnOffspecFileAttach) {
		this.idnOffspecFileAttach = idnOffspecFileAttach;
	}
	public BigDecimal getIdnOffspec() {
		return idnOffspec;
	}
	public void setIdnOffspec(BigDecimal idnOffspec) {
		this.idnOffspec = idnOffspec;
	}
	public BigDecimal getIdnOffspecFile() {
		return idnOffspecFile;
	}
	public void setIdnOffspecFile(BigDecimal idnOffspecFile) {
		this.idnOffspecFile = idnOffspecFile;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "OffSpecFileAttachBean [idnOffspecFileAttach=" + idnOffspecFileAttach + ", idnOffspec=" + idnOffspec
				+ ", idnOffspecFile=" + idnOffspecFile + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idnOffspec, idnOffspecFile, idnOffspecFileAttach);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OffSpecFileAttachBean other = (OffSpecFileAttachBean) obj;
		return Objects.equals(idnOffspec, other.idnOffspec) && Objects.equals(idnOffspecFile, other.idnOffspecFile)
				&& Objects.equals(idnOffspecFileAttach, other.idnOffspecFileAttach);
	}
	
	
	
	
}
