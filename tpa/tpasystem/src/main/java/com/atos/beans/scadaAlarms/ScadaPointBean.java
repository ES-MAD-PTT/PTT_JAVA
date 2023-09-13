package com.atos.beans.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ScadaPointBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -8210572551497660753L;

	private BigDecimal idnScadaPoint;
	private String scadaPoint;
	private BigDecimal idn_pipeline_system;

	public ScadaPointBean() {
		super();
	}

	public BigDecimal getIdnScadaPoint() {
		return idnScadaPoint;
	}

	public void setIdnScadaPoint(BigDecimal idnScadaPoint) {
		this.idnScadaPoint = idnScadaPoint;
	}

	public String getScadaPoint() {
		return scadaPoint;
	}

	public void setScadaPoint(String scadaPoint) {
		this.scadaPoint = scadaPoint;
	}
	

	public BigDecimal getIdn_pipeline_system() {
		return idn_pipeline_system;
	}

	public void setIdn_pipeline_system(BigDecimal idn_pipeline_system) {
		this.idn_pipeline_system = idn_pipeline_system;
	}

	@Override
	public String toString() {
		return "ScadaPointBean [idnScadaPoint=" + idnScadaPoint + ", scadaPoint=" + scadaPoint
				+ ", idn_pipeline_system=" + idn_pipeline_system + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idnScadaPoint == null) ? 0 : idnScadaPoint.hashCode());
		result = prime * result + ((idn_pipeline_system == null) ? 0 : idn_pipeline_system.hashCode());
		result = prime * result + ((scadaPoint == null) ? 0 : scadaPoint.hashCode());
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
		ScadaPointBean other = (ScadaPointBean) obj;
		if (idnScadaPoint == null) {
			if (other.idnScadaPoint != null)
				return false;
		} else if (!idnScadaPoint.equals(other.idnScadaPoint))
			return false;
		if (idn_pipeline_system == null) {
			if (other.idn_pipeline_system != null)
				return false;
		} else if (!idn_pipeline_system.equals(other.idn_pipeline_system))
			return false;
		if (scadaPoint == null) {
			if (other.scadaPoint != null)
				return false;
		} else if (!scadaPoint.equals(other.scadaPoint))
			return false;
		return true;
	}

	
	
}
