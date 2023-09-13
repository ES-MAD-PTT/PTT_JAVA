package com.atos.beans.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ScadaLabelBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -8210572551497660753L;

	private BigDecimal idnScadaLabel;
	private String scadaLabel;

	public ScadaLabelBean() {
		super();
	}

	public BigDecimal getIdnScadaLabel() {
		return idnScadaLabel;
	}

	public void setIdnScadaLabel(BigDecimal idnScadaLabel) {
		this.idnScadaLabel = idnScadaLabel;
	}

	public String getScadaLabel() {
		return scadaLabel;
	}

	public void setScadaLabel(String scadaLabel) {
		this.scadaLabel = scadaLabel;
	}

	@Override
	public String toString() {
		return "ScadaLabelBean [idnScadaLabel=" + idnScadaLabel + ", scadaLabel=" + scadaLabel + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idnScadaLabel == null) ? 0 : idnScadaLabel.hashCode());
		result = prime * result + ((scadaLabel == null) ? 0 : scadaLabel.hashCode());
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
		ScadaLabelBean other = (ScadaLabelBean) obj;
		if (idnScadaLabel == null) {
			if (other.idnScadaLabel != null)
				return false;
		} else if (!idnScadaLabel.equals(other.idnScadaLabel))
			return false;
		if (scadaLabel == null) {
			if (other.scadaLabel != null)
				return false;
		} else if (!scadaLabel.equals(other.scadaLabel))
			return false;
		return true;
	}

	
	
}
