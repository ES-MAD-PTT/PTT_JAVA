package com.atos.beans.quality;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.SortedSet;
import java.util.TreeSet;

public class OffSpecStatusBean implements Serializable, Comparable<OffSpecStatusBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -440409295342451290L;

	private BigDecimal statusId;
	private String statusDesc;
	private BigDecimal incidentTypeId;		// Se guardan este par de campos, porque la incidencia podria cambiar 
	private String incidentTypeDesc;		// podria cambiar de tipo, al cambiar de estado.
	private Integer statusSort;
	// En este conjunto se guardan los siguientes estados posibles a partir de este.
	// Al recuperarlos, se obtendran ordenados, puesto que se insertaran elementos
	// de esta misma clase OffSpecStatusBean, que implementa el interfaz Comparable (con un metodo compareTo()).
	private SortedSet<OffSpecStatusBean> nextStatusSet;
	
	public OffSpecStatusBean() {
		this.statusId = null;
		this.statusDesc = null;
		this.incidentTypeId = null;
		this.incidentTypeDesc = null;
		this.statusSort = null;
		this.nextStatusSet = new TreeSet<OffSpecStatusBean>();
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public BigDecimal getIncidentTypeId() {
		return incidentTypeId;
	}

	public void setIncidentTypeId(BigDecimal incidentTypeId) {
		this.incidentTypeId = incidentTypeId;
	}

	public String getIncidentTypeDesc() {
		return incidentTypeDesc;
	}

	public void setIncidentTypeDesc(String incidentTypeDesc) {
		this.incidentTypeDesc = incidentTypeDesc;
	}

	public Integer getStatusSort() {
		return statusSort;
	}

	public void setStatusSort(Integer statusSort) {
		this.statusSort = statusSort;
	}

	public SortedSet<OffSpecStatusBean> getNextStatusSet() {
		return nextStatusSet;
	}

	//	java.lang.Comparable: int compareTo(Object o):
	//		This method compares this object with o object. Returned int value has the following meanings.
	//		positive – this object is greater than o
	//		zero – this object equals to o
	//		negative – this object is less than o
	@Override
	public int compareTo(OffSpecStatusBean _bean) {
		return (this.statusSort - _bean.getStatusSort());
	}
	
	/*@Override
	public String toString() {
		return "OffSpecStatusBean [statusId=" + statusId + ", statusDesc=" + statusDesc + ", incidentTypeId="
				+ incidentTypeId + ", incidentTypeDesc=" + incidentTypeDesc + ", statusSort=" + statusSort
				+ ", nextStatusSet=" + nextStatusSet + "]";
	} */
	
}
