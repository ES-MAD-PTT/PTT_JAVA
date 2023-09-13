package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShippersNominationsReportsBean implements Serializable {
	
	private static final long serialVersionUID = 2160599673733279558L;
	private Date gas_day;
	private String user_group_id;
	private String user_group_name;
	private BigDecimal contracted_energy;
	private BigDecimal nominated_energy;
	private BigDecimal difference;
	private BigDecimal imbalance;
	private BigDecimal tolerancia_permitida;
	private String is_warning;
	
	public ShippersNominationsReportsBean() {
		this.gas_day = null;
		this.user_group_id = null;
		this.user_group_name = null;
		this.contracted_energy = null;
		this.nominated_energy = null;
		this.difference = null;
		this.imbalance = null;
		this.tolerancia_permitida = null;
		this.is_warning = null;
	}
	
	public ShippersNominationsReportsBean(Date gas_day, String user_group_id, String user_group_name,
			BigDecimal contracted_energy, BigDecimal nominated_energy, BigDecimal difference, BigDecimal imbalance,
			BigDecimal tolerancia_permitida, String is_warning) {
		super();
		this.gas_day = gas_day;
		this.user_group_id = user_group_id;
		this.user_group_name = user_group_name;
		this.contracted_energy = contracted_energy;
		this.nominated_energy = nominated_energy;
		this.difference = difference;
		this.imbalance = imbalance;
		this.tolerancia_permitida = tolerancia_permitida;
		this.is_warning = is_warning;
	}

	public Date getGas_day() {
		return gas_day;
	}

	public void setGas_day(Date gas_day) {
		this.gas_day = gas_day;
	}

	public String getUser_group_id() {
		return user_group_id;
	}

	public void setUser_group_id(String user_group_id) {
		this.user_group_id = user_group_id;
	}

	public String getUser_group_name() {
		return user_group_name;
	}

	public void setUser_group_name(String user_group_name) {
		this.user_group_name = user_group_name;
	}

	public BigDecimal getContracted_energy() {
		return contracted_energy;
	}

	public void setContracted_energy(BigDecimal contracted_energy) {
		this.contracted_energy = contracted_energy;
	}

	public BigDecimal getNominated_energy() {
		return nominated_energy;
	}

	public void setNominated_energy(BigDecimal nominated_energy) {
		this.nominated_energy = nominated_energy;
	}

	public BigDecimal getDifference() {
		return difference;
	}

	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}

	public BigDecimal getImbalance() {
		return imbalance;
	}

	public void setImbalance(BigDecimal imbalance) {
		this.imbalance = imbalance;
	}

	public BigDecimal getTolerancia_permitida() {
		return tolerancia_permitida;
	}

	public void setTolerancia_permitida(BigDecimal tolerancia_permitida) {
		this.tolerancia_permitida = tolerancia_permitida;
	}

	public String getIs_warning() {
		return is_warning;
	}

	public void setIs_warning(String is_warning) {
		this.is_warning = is_warning;
	}

	@Override
	public String toString() {
		return "ShippersNominationsReportsBean [gas_day=" + gas_day + ", user_group_id=" + user_group_id
				+ ", user_group_name=" + user_group_name + ", contracted_energy=" + contracted_energy
				+ ", nominated_energy=" + nominated_energy + ", difference=" + difference + ", imbalance=" + imbalance
				+ ", tolerancia_permitida=" + tolerancia_permitida + ", is_warning=" + is_warning + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contracted_energy == null) ? 0 : contracted_energy.hashCode());
		result = prime * result + ((difference == null) ? 0 : difference.hashCode());
		result = prime * result + ((gas_day == null) ? 0 : gas_day.hashCode());
		result = prime * result + ((imbalance == null) ? 0 : imbalance.hashCode());
		result = prime * result + ((is_warning == null) ? 0 : is_warning.hashCode());
		result = prime * result + ((nominated_energy == null) ? 0 : nominated_energy.hashCode());
		result = prime * result + ((tolerancia_permitida == null) ? 0 : tolerancia_permitida.hashCode());
		result = prime * result + ((user_group_id == null) ? 0 : user_group_id.hashCode());
		result = prime * result + ((user_group_name == null) ? 0 : user_group_name.hashCode());
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
		ShippersNominationsReportsBean other = (ShippersNominationsReportsBean) obj;
		if (contracted_energy == null) {
			if (other.contracted_energy != null)
				return false;
		} else if (!contracted_energy.equals(other.contracted_energy))
			return false;
		if (difference == null) {
			if (other.difference != null)
				return false;
		} else if (!difference.equals(other.difference))
			return false;
		if (gas_day == null) {
			if (other.gas_day != null)
				return false;
		} else if (!gas_day.equals(other.gas_day))
			return false;
		if (imbalance == null) {
			if (other.imbalance != null)
				return false;
		} else if (!imbalance.equals(other.imbalance))
			return false;
		if (is_warning == null) {
			if (other.is_warning != null)
				return false;
		} else if (!is_warning.equals(other.is_warning))
			return false;
		if (nominated_energy == null) {
			if (other.nominated_energy != null)
				return false;
		} else if (!nominated_energy.equals(other.nominated_energy))
			return false;
		if (tolerancia_permitida == null) {
			if (other.tolerancia_permitida != null)
				return false;
		} else if (!tolerancia_permitida.equals(other.tolerancia_permitida))
			return false;
		if (user_group_id == null) {
			if (other.user_group_id != null)
				return false;
		} else if (!user_group_id.equals(other.user_group_id))
			return false;
		if (user_group_name == null) {
			if (other.user_group_name != null)
				return false;
		} else if (!user_group_name.equals(other.user_group_name))
			return false;
		return true;
	}

}
