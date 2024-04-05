package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ShippersNominationsReportsBean implements Serializable {
	
	private static final long serialVersionUID = 2160599673733279558L;
	private Date gas_day;
	private String user_group_id;
	private String user_group_name;
	private BigDecimal idn_user_group;
	private String area_code;
	private BigDecimal contracted_energy;
	private BigDecimal nominated_energy;
	private BigDecimal overusage;
	private BigDecimal imbalance;
	private BigDecimal tolerancia_permitida;
	private String is_warning;
	private BigDecimal idn_system;
	private BigDecimal idn_zone;
	
	private List<ShippersNominationsReportsBean> details;
	
	public ShippersNominationsReportsBean() {
		this.gas_day = null;
		this.user_group_id = null;
		this.user_group_name = null;
		this.idn_user_group = null;
		this.area_code = null;
		this.contracted_energy = null;
		this.nominated_energy = null;
		this.overusage = null;
		this.imbalance = null;
		this.tolerancia_permitida = null;
		this.is_warning = null;
		this.idn_system = null;
		this.idn_zone = null;
		this.details = null;
	}
	
	public ShippersNominationsReportsBean(Date gas_day, String user_group_id, String user_group_name, BigDecimal idn_user_group, String area_code,
			BigDecimal contracted_energy, BigDecimal nominated_energy, BigDecimal overusage, BigDecimal imbalance,
			BigDecimal tolerancia_permitida, String is_warning, BigDecimal idn_system, BigDecimal idn_zone) {
		super();
		this.gas_day = gas_day;
		this.user_group_id = user_group_id;
		this.user_group_name = user_group_name;
		this.idn_user_group = idn_user_group;
		this.area_code = area_code;
		this.contracted_energy = contracted_energy;
		this.nominated_energy = nominated_energy;
		this.overusage = overusage;
		this.imbalance = imbalance;
		this.tolerancia_permitida = tolerancia_permitida;
		this.is_warning = is_warning;
		this.idn_system = idn_system;
		this.idn_zone = idn_zone;
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

	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}

	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
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

	public BigDecimal getOverusage() {
		return overusage;
	}

	public void setOverusage(BigDecimal overusage) {
		this.overusage = overusage;
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

	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	public BigDecimal getIdn_zone() {
		return idn_zone;
	}

	public void setIdn_zone(BigDecimal idn_zone) {
		this.idn_zone = idn_zone;
	}

	public List<ShippersNominationsReportsBean> getDetails() {
		return details;
	}

	public void setDetails(List<ShippersNominationsReportsBean> details) {
		this.details = details;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area_code == null) ? 0 : area_code.hashCode());
		result = prime * result + ((contracted_energy == null) ? 0 : contracted_energy.hashCode());
		result = prime * result + ((gas_day == null) ? 0 : gas_day.hashCode());
		result = prime * result + ((idn_system == null) ? 0 : idn_system.hashCode());
		result = prime * result + ((idn_user_group == null) ? 0 : idn_user_group.hashCode());
		result = prime * result + ((idn_zone == null) ? 0 : idn_zone.hashCode());
		result = prime * result + ((imbalance == null) ? 0 : imbalance.hashCode());
		result = prime * result + ((is_warning == null) ? 0 : is_warning.hashCode());
		result = prime * result + ((nominated_energy == null) ? 0 : nominated_energy.hashCode());
		result = prime * result + ((overusage == null) ? 0 : overusage.hashCode());
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
		if (area_code == null) {
			if (other.area_code != null)
				return false;
		} else if (!area_code.equals(other.area_code))
			return false;
		if (contracted_energy == null) {
			if (other.contracted_energy != null)
				return false;
		} else if (!contracted_energy.equals(other.contracted_energy))
			return false;
		if (gas_day == null) {
			if (other.gas_day != null)
				return false;
		} else if (!gas_day.equals(other.gas_day))
			return false;
		if (idn_system == null) {
			if (other.idn_system != null)
				return false;
		} else if (!idn_system.equals(other.idn_system))
			return false;
		if (idn_user_group == null) {
			if (other.idn_user_group != null)
				return false;
		} else if (!idn_user_group.equals(other.idn_user_group))
			return false;
		if (idn_zone == null) {
			if (other.idn_zone != null)
				return false;
		} else if (!idn_zone.equals(other.idn_zone))
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
		if (overusage == null) {
			if (other.overusage != null)
				return false;
		} else if (!overusage.equals(other.overusage))
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ShippersNominationsReportsBean [gas_day=");
		builder.append(gas_day);
		builder.append(", user_group_id=");
		builder.append(user_group_id);
		builder.append(", user_group_name=");
		builder.append(user_group_name);
		builder.append(", idn_user_group=");
		builder.append(idn_user_group);
		builder.append(", area_code=");
		builder.append(area_code);
		builder.append(", contracted_energy=");
		builder.append(contracted_energy);
		builder.append(", nominated_energy=");
		builder.append(nominated_energy);
		builder.append(", overusage=");
		builder.append(overusage);
		builder.append(", imbalance=");
		builder.append(imbalance);
		builder.append(", tolerancia_permitida=");
		builder.append(tolerancia_permitida);
		builder.append(", is_warning=");
		builder.append(is_warning);
		builder.append(", idn_system=");
		builder.append(idn_system);
		builder.append(", idn_zone=");
		builder.append(idn_zone);
		builder.append("]");
		return builder.toString();
	}


}
