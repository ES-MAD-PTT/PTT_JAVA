package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import com.atos.beans.UserAudBean;

@XmlRootElement(name = "ZoneBean")
public class ZoneBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_zone;
	private BigDecimal idn_pipeline_system;
	private BigDecimal idn_zone_gas_quality;

	private BigDecimal minValue;
	private BigDecimal maxValue;
	private String parameterCode;

	private String id;
	private String name;
	
	private String type;
	private BigDecimal idn_zone_gasq_type;
	private Date startDate;
	private Date endDate;

	private String system;
	private Date startDateGasQuality;

	private BigDecimal wobbeIndexMin;
	private BigDecimal wobbeIndexMax;
	private BigDecimal grossCalorificValueMin;
	private BigDecimal grossCalorificValueMax;

	private BigDecimal methaneMin;
	private BigDecimal methaneMax;
	private BigDecimal c2Min;
	private BigDecimal c2Max;
	private BigDecimal oxygenMin;
	private BigDecimal oxygenMax;
	private BigDecimal nitrogenMin;
	private BigDecimal nitrogenMax;
	private BigDecimal carbonDioxideNitrogenMin;
	private BigDecimal carbonDioxideNitrogenMax;
	private BigDecimal hydrogenSulfideMin;
	private BigDecimal hydrogenSulfideMax;
	private BigDecimal totalSulphurMin;
	private BigDecimal totalSulphurMax;
	private BigDecimal mercuryMin;
	private BigDecimal mercuryMax;
	private BigDecimal otherParticulatesMin;
	private BigDecimal otherParticulatesMax;
	private BigDecimal steamMin;
	private BigDecimal steamMax;
	private BigDecimal hydrocarbonDewPointMin;
	private BigDecimal hydrocarbonDewPointMax;

	private BigDecimal carbonDioxideMin;
	private BigDecimal carbonDioxideMax;

	public ZoneBean() {
		super();
	}

	public ZoneBean(BigDecimal idn_zone, BigDecimal idn_pipeline_system, BigDecimal idn_zone_gas_quality,
			BigDecimal minValue, BigDecimal maxValue, String parameterCode, String id, String name, String type,
			BigDecimal idn_zone_gasq_type, Date startDate, Date endDate, String system, Date startDateGasQuality,
			BigDecimal wobbeIndexMin, BigDecimal wobbeIndexMax, BigDecimal grossCalorificValueMin,
			BigDecimal grossCalorificValueMax, BigDecimal methaneMin, BigDecimal methaneMax, BigDecimal c2Min,
			BigDecimal c2Max, BigDecimal oxygenMin, BigDecimal oxygenMax, BigDecimal nitrogenMin,
			BigDecimal nitrogenMax, BigDecimal carbonDioxideNitrogenMin, BigDecimal carbonDioxideNitrogenMax,
			BigDecimal hydrogenSulfideMin, BigDecimal hydrogenSulfideMax, BigDecimal totalSulphurMin,
			BigDecimal totalSulphurMax, BigDecimal mercuryMin, BigDecimal mercuryMax, BigDecimal otherParticulatesMin,
			BigDecimal otherParticulatesMax, BigDecimal steamMin, BigDecimal steamMax,
			BigDecimal hydrocarbonDewPointMin, BigDecimal hydrocarbonDewPointMax, BigDecimal carbonDioxideMin,
			BigDecimal carbonDioxideMax) {
		super();
		this.idn_zone = idn_zone;
		this.idn_pipeline_system = idn_pipeline_system;
		this.idn_zone_gas_quality = idn_zone_gas_quality;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.parameterCode = parameterCode;
		this.id = id;
		this.name = name;
		this.type = type;
		this.idn_zone_gasq_type = idn_zone_gasq_type;
		this.startDate = startDate;
		this.endDate = endDate;
		this.system = system;
		this.startDateGasQuality = startDateGasQuality;
		this.wobbeIndexMin = wobbeIndexMin;
		this.wobbeIndexMax = wobbeIndexMax;
		this.grossCalorificValueMin = grossCalorificValueMin;
		this.grossCalorificValueMax = grossCalorificValueMax;
		this.methaneMin = methaneMin;
		this.methaneMax = methaneMax;
		this.c2Min = c2Min;
		this.c2Max = c2Max;
		this.oxygenMin = oxygenMin;
		this.oxygenMax = oxygenMax;
		this.nitrogenMin = nitrogenMin;
		this.nitrogenMax = nitrogenMax;
		this.carbonDioxideNitrogenMin = carbonDioxideNitrogenMin;
		this.carbonDioxideNitrogenMax = carbonDioxideNitrogenMax;
		this.hydrogenSulfideMin = hydrogenSulfideMin;
		this.hydrogenSulfideMax = hydrogenSulfideMax;
		this.totalSulphurMin = totalSulphurMin;
		this.totalSulphurMax = totalSulphurMax;
		this.mercuryMin = mercuryMin;
		this.mercuryMax = mercuryMax;
		this.otherParticulatesMin = otherParticulatesMin;
		this.otherParticulatesMax = otherParticulatesMax;
		this.steamMin = steamMin;
		this.steamMax = steamMax;
		this.hydrocarbonDewPointMin = hydrocarbonDewPointMin;
		this.hydrocarbonDewPointMax = hydrocarbonDewPointMax;
		this.carbonDioxideMin = carbonDioxideMin;
		this.carbonDioxideMax = carbonDioxideMax;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getIdn_zone_gasq_type() {
		return idn_zone_gasq_type;
	}

	public void setIdn_zone_gasq_type(BigDecimal idn_zone_gasq_type) {
		this.idn_zone_gasq_type = idn_zone_gasq_type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public Date getStartDateGasQuality() {
		return startDateGasQuality;
	}

	public void setStartDateGasQuality(Date startDateGasQuality) {
		this.startDateGasQuality = startDateGasQuality;
	}

	public BigDecimal getWobbeIndexMin() {
		return wobbeIndexMin;
	}

	public void setWobbeIndexMin(BigDecimal wobbeIndexMin) {
		this.wobbeIndexMin = wobbeIndexMin;
	}

	public BigDecimal getWobbeIndexMax() {
		return wobbeIndexMax;
	}

	public void setWobbeIndexMax(BigDecimal wobbeIndexMax) {
		this.wobbeIndexMax = wobbeIndexMax;
	}

	public BigDecimal getGrossCalorificValueMin() {
		return grossCalorificValueMin;
	}

	public void setGrossCalorificValueMin(BigDecimal grossCalorificValueMin) {
		this.grossCalorificValueMin = grossCalorificValueMin;
	}

	public BigDecimal getGrossCalorificValueMax() {
		return grossCalorificValueMax;
	}

	public void setGrossCalorificValueMax(BigDecimal grossCalorificValueMax) {
		this.grossCalorificValueMax = grossCalorificValueMax;
	}

	public BigDecimal getMethaneMin() {
		return methaneMin;
	}

	public void setMethaneMin(BigDecimal methaneMin) {
		this.methaneMin = methaneMin;
	}

	public BigDecimal getMethaneMax() {
		return methaneMax;
	}

	public void setMethaneMax(BigDecimal methaneMax) {
		this.methaneMax = methaneMax;
	}

	public BigDecimal getC2Min() {
		return c2Min;
	}

	public void setC2Min(BigDecimal c2Min) {
		this.c2Min = c2Min;
	}

	public BigDecimal getC2Max() {
		return c2Max;
	}

	public void setC2Max(BigDecimal c2Max) {
		this.c2Max = c2Max;
	}

	public BigDecimal getOxygenMin() {
		return oxygenMin;
	}

	public void setOxygenMin(BigDecimal oxygenMin) {
		this.oxygenMin = oxygenMin;
	}

	public BigDecimal getOxygenMax() {
		return oxygenMax;
	}

	public void setOxygenMax(BigDecimal oxygenMax) {
		this.oxygenMax = oxygenMax;
	}

	public BigDecimal getNitrogenMin() {
		return nitrogenMin;
	}

	public void setNitrogenMin(BigDecimal nitrogenMin) {
		this.nitrogenMin = nitrogenMin;
	}

	public BigDecimal getNitrogenMax() {
		return nitrogenMax;
	}

	public void setNitrogenMax(BigDecimal nitrogenMax) {
		this.nitrogenMax = nitrogenMax;
	}

	public BigDecimal getCarbonDioxideNitrogenMin() {
		return carbonDioxideNitrogenMin;
	}

	public void setCarbonDioxideNitrogenMin(BigDecimal carbonDioxideNitrogenMin) {
		this.carbonDioxideNitrogenMin = carbonDioxideNitrogenMin;
	}

	public BigDecimal getCarbonDioxideNitrogenMax() {
		return carbonDioxideNitrogenMax;
	}

	public void setCarbonDioxideNitrogenMax(BigDecimal carbonDioxideNitrogenMax) {
		this.carbonDioxideNitrogenMax = carbonDioxideNitrogenMax;
	}

	public BigDecimal getHydrogenSulfideMin() {
		return hydrogenSulfideMin;
	}

	public void setHydrogenSulfideMin(BigDecimal hydrogenSulfideMin) {
		this.hydrogenSulfideMin = hydrogenSulfideMin;
	}

	public BigDecimal getHydrogenSulfideMax() {
		return hydrogenSulfideMax;
	}

	public void setHydrogenSulfideMax(BigDecimal hydrogenSulfideMax) {
		this.hydrogenSulfideMax = hydrogenSulfideMax;
	}

	public BigDecimal getTotalSulphurMin() {
		return totalSulphurMin;
	}

	public void setTotalSulphurMin(BigDecimal totalSulphurMin) {
		this.totalSulphurMin = totalSulphurMin;
	}

	public BigDecimal getTotalSulphurMax() {
		return totalSulphurMax;
	}

	public void setTotalSulphurMax(BigDecimal totalSulphurMax) {
		this.totalSulphurMax = totalSulphurMax;
	}

	public BigDecimal getMercuryMin() {
		return mercuryMin;
	}

	public void setMercuryMin(BigDecimal mercuryMin) {
		this.mercuryMin = mercuryMin;
	}

	public BigDecimal getMercuryMax() {
		return mercuryMax;
	}

	public void setMercuryMax(BigDecimal mercuryMax) {
		this.mercuryMax = mercuryMax;
	}

	public BigDecimal getOtherParticulatesMin() {
		return otherParticulatesMin;
	}

	public void setOtherParticulatesMin(BigDecimal otherParticulatesMin) {
		this.otherParticulatesMin = otherParticulatesMin;
	}

	public BigDecimal getOtherParticulatesMax() {
		return otherParticulatesMax;
	}

	public void setOtherParticulatesMax(BigDecimal otherParticulatesMax) {
		this.otherParticulatesMax = otherParticulatesMax;
	}

	public BigDecimal getSteamMin() {
		return steamMin;
	}

	public void setSteamMin(BigDecimal steamMin) {
		this.steamMin = steamMin;
	}

	public BigDecimal getSteamMax() {
		return steamMax;
	}

	public void setSteamMax(BigDecimal steamMax) {
		this.steamMax = steamMax;
	}

	public BigDecimal getHydrocarbonDewPointMin() {
		return hydrocarbonDewPointMin;
	}

	public void setHydrocarbonDewPointMin(BigDecimal hydrocarbonDewPointMin) {
		this.hydrocarbonDewPointMin = hydrocarbonDewPointMin;
	}

	public BigDecimal getHydrocarbonDewPointMax() {
		return hydrocarbonDewPointMax;
	}

	public void setHydrocarbonDewPointMax(BigDecimal hydrocarbonDewPointMax) {
		this.hydrocarbonDewPointMax = hydrocarbonDewPointMax;
	}

	public BigDecimal getIdn_zone() {
		return idn_zone;
	}

	public void setIdn_zone(BigDecimal idn_zone) {
		this.idn_zone = idn_zone;
	}

	public BigDecimal getIdn_pipeline_system() {
		return idn_pipeline_system;
	}

	public void setIdn_pipeline_system(BigDecimal idn_pipeline_system) {
		this.idn_pipeline_system = idn_pipeline_system;
	}

	public BigDecimal getIdn_zone_gas_quality() {
		return idn_zone_gas_quality;
	}

	public void setIdn_zone_gas_quality(BigDecimal idn_zone_gas_quality) {
		this.idn_zone_gas_quality = idn_zone_gas_quality;
	}

	@Override
	public String toString() {
		return "ZoneBean [idn_zone=" + idn_zone + ", idn_pipeline_system=" + idn_pipeline_system
				+ ", idn_zone_gas_quality=" + idn_zone_gas_quality + ", minValue=" + minValue + ", maxValue=" + maxValue
				+ ", parameterCode=" + parameterCode + ", id=" + id + ", name=" + name + ", type=" + type
				+ ", idn_zone_gasq_type=" + idn_zone_gasq_type + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", system=" + system + ", startDateGasQuality=" + startDateGasQuality + ", wobbeIndexMin="
				+ wobbeIndexMin + ", wobbeIndexMax=" + wobbeIndexMax + ", grossCalorificValueMin="
				+ grossCalorificValueMin + ", grossCalorificValueMax=" + grossCalorificValueMax + ", methaneMin="
				+ methaneMin + ", methaneMax=" + methaneMax + ", c2Min=" + c2Min + ", c2Max=" + c2Max + ", oxygenMin="
				+ oxygenMin + ", oxygenMax=" + oxygenMax + ", nitrogenMin=" + nitrogenMin + ", nitrogenMax="
				+ nitrogenMax + ", carbonDioxideNitrogenMin=" + carbonDioxideNitrogenMin + ", carbonDioxideNitrogenMax="
				+ carbonDioxideNitrogenMax + ", hydrogenSulfideMin=" + hydrogenSulfideMin + ", hydrogenSulfideMax="
				+ hydrogenSulfideMax + ", totalSulphurMin=" + totalSulphurMin + ", totalSulphurMax=" + totalSulphurMax
				+ ", mercuryMin=" + mercuryMin + ", mercuryMax=" + mercuryMax + ", otherParticulatesMin="
				+ otherParticulatesMin + ", otherParticulatesMax=" + otherParticulatesMax + ", steamMin=" + steamMin
				+ ", steamMax=" + steamMax + ", hydrocarbonDewPointMin=" + hydrocarbonDewPointMin
				+ ", hydrocarbonDewPointMax=" + hydrocarbonDewPointMax + ", carbonDioxideMin=" + carbonDioxideMin
				+ ", carbonDioxideMax=" + carbonDioxideMax + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c2Max == null) ? 0 : c2Max.hashCode());
		result = prime * result + ((c2Min == null) ? 0 : c2Min.hashCode());
		result = prime * result + ((carbonDioxideMax == null) ? 0 : carbonDioxideMax.hashCode());
		result = prime * result + ((carbonDioxideMin == null) ? 0 : carbonDioxideMin.hashCode());
		result = prime * result + ((carbonDioxideNitrogenMax == null) ? 0 : carbonDioxideNitrogenMax.hashCode());
		result = prime * result + ((carbonDioxideNitrogenMin == null) ? 0 : carbonDioxideNitrogenMin.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((grossCalorificValueMax == null) ? 0 : grossCalorificValueMax.hashCode());
		result = prime * result + ((grossCalorificValueMin == null) ? 0 : grossCalorificValueMin.hashCode());
		result = prime * result + ((hydrocarbonDewPointMax == null) ? 0 : hydrocarbonDewPointMax.hashCode());
		result = prime * result + ((hydrocarbonDewPointMin == null) ? 0 : hydrocarbonDewPointMin.hashCode());
		result = prime * result + ((hydrogenSulfideMax == null) ? 0 : hydrogenSulfideMax.hashCode());
		result = prime * result + ((hydrogenSulfideMin == null) ? 0 : hydrogenSulfideMin.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idn_pipeline_system == null) ? 0 : idn_pipeline_system.hashCode());
		result = prime * result + ((idn_zone == null) ? 0 : idn_zone.hashCode());
		result = prime * result + ((idn_zone_gas_quality == null) ? 0 : idn_zone_gas_quality.hashCode());
		result = prime * result + ((idn_zone_gasq_type == null) ? 0 : idn_zone_gasq_type.hashCode());
		result = prime * result + ((maxValue == null) ? 0 : maxValue.hashCode());
		result = prime * result + ((mercuryMax == null) ? 0 : mercuryMax.hashCode());
		result = prime * result + ((mercuryMin == null) ? 0 : mercuryMin.hashCode());
		result = prime * result + ((methaneMax == null) ? 0 : methaneMax.hashCode());
		result = prime * result + ((methaneMin == null) ? 0 : methaneMin.hashCode());
		result = prime * result + ((minValue == null) ? 0 : minValue.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nitrogenMax == null) ? 0 : nitrogenMax.hashCode());
		result = prime * result + ((nitrogenMin == null) ? 0 : nitrogenMin.hashCode());
		result = prime * result + ((otherParticulatesMax == null) ? 0 : otherParticulatesMax.hashCode());
		result = prime * result + ((otherParticulatesMin == null) ? 0 : otherParticulatesMin.hashCode());
		result = prime * result + ((oxygenMax == null) ? 0 : oxygenMax.hashCode());
		result = prime * result + ((oxygenMin == null) ? 0 : oxygenMin.hashCode());
		result = prime * result + ((parameterCode == null) ? 0 : parameterCode.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((startDateGasQuality == null) ? 0 : startDateGasQuality.hashCode());
		result = prime * result + ((steamMax == null) ? 0 : steamMax.hashCode());
		result = prime * result + ((steamMin == null) ? 0 : steamMin.hashCode());
		result = prime * result + ((system == null) ? 0 : system.hashCode());
		result = prime * result + ((totalSulphurMax == null) ? 0 : totalSulphurMax.hashCode());
		result = prime * result + ((totalSulphurMin == null) ? 0 : totalSulphurMin.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((wobbeIndexMax == null) ? 0 : wobbeIndexMax.hashCode());
		result = prime * result + ((wobbeIndexMin == null) ? 0 : wobbeIndexMin.hashCode());
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
		ZoneBean other = (ZoneBean) obj;
		if (c2Max == null) {
			if (other.c2Max != null)
				return false;
		} else if (!c2Max.equals(other.c2Max))
			return false;
		if (c2Min == null) {
			if (other.c2Min != null)
				return false;
		} else if (!c2Min.equals(other.c2Min))
			return false;
		if (carbonDioxideMax == null) {
			if (other.carbonDioxideMax != null)
				return false;
		} else if (!carbonDioxideMax.equals(other.carbonDioxideMax))
			return false;
		if (carbonDioxideMin == null) {
			if (other.carbonDioxideMin != null)
				return false;
		} else if (!carbonDioxideMin.equals(other.carbonDioxideMin))
			return false;
		if (carbonDioxideNitrogenMax == null) {
			if (other.carbonDioxideNitrogenMax != null)
				return false;
		} else if (!carbonDioxideNitrogenMax.equals(other.carbonDioxideNitrogenMax))
			return false;
		if (carbonDioxideNitrogenMin == null) {
			if (other.carbonDioxideNitrogenMin != null)
				return false;
		} else if (!carbonDioxideNitrogenMin.equals(other.carbonDioxideNitrogenMin))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (grossCalorificValueMax == null) {
			if (other.grossCalorificValueMax != null)
				return false;
		} else if (!grossCalorificValueMax.equals(other.grossCalorificValueMax))
			return false;
		if (grossCalorificValueMin == null) {
			if (other.grossCalorificValueMin != null)
				return false;
		} else if (!grossCalorificValueMin.equals(other.grossCalorificValueMin))
			return false;
		if (hydrocarbonDewPointMax == null) {
			if (other.hydrocarbonDewPointMax != null)
				return false;
		} else if (!hydrocarbonDewPointMax.equals(other.hydrocarbonDewPointMax))
			return false;
		if (hydrocarbonDewPointMin == null) {
			if (other.hydrocarbonDewPointMin != null)
				return false;
		} else if (!hydrocarbonDewPointMin.equals(other.hydrocarbonDewPointMin))
			return false;
		if (hydrogenSulfideMax == null) {
			if (other.hydrogenSulfideMax != null)
				return false;
		} else if (!hydrogenSulfideMax.equals(other.hydrogenSulfideMax))
			return false;
		if (hydrogenSulfideMin == null) {
			if (other.hydrogenSulfideMin != null)
				return false;
		} else if (!hydrogenSulfideMin.equals(other.hydrogenSulfideMin))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idn_pipeline_system == null) {
			if (other.idn_pipeline_system != null)
				return false;
		} else if (!idn_pipeline_system.equals(other.idn_pipeline_system))
			return false;
		if (idn_zone == null) {
			if (other.idn_zone != null)
				return false;
		} else if (!idn_zone.equals(other.idn_zone))
			return false;
		if (idn_zone_gas_quality == null) {
			if (other.idn_zone_gas_quality != null)
				return false;
		} else if (!idn_zone_gas_quality.equals(other.idn_zone_gas_quality))
			return false;
		if (idn_zone_gasq_type == null) {
			if (other.idn_zone_gasq_type != null)
				return false;
		} else if (!idn_zone_gasq_type.equals(other.idn_zone_gasq_type))
			return false;
		if (maxValue == null) {
			if (other.maxValue != null)
				return false;
		} else if (!maxValue.equals(other.maxValue))
			return false;
		if (mercuryMax == null) {
			if (other.mercuryMax != null)
				return false;
		} else if (!mercuryMax.equals(other.mercuryMax))
			return false;
		if (mercuryMin == null) {
			if (other.mercuryMin != null)
				return false;
		} else if (!mercuryMin.equals(other.mercuryMin))
			return false;
		if (methaneMax == null) {
			if (other.methaneMax != null)
				return false;
		} else if (!methaneMax.equals(other.methaneMax))
			return false;
		if (methaneMin == null) {
			if (other.methaneMin != null)
				return false;
		} else if (!methaneMin.equals(other.methaneMin))
			return false;
		if (minValue == null) {
			if (other.minValue != null)
				return false;
		} else if (!minValue.equals(other.minValue))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nitrogenMax == null) {
			if (other.nitrogenMax != null)
				return false;
		} else if (!nitrogenMax.equals(other.nitrogenMax))
			return false;
		if (nitrogenMin == null) {
			if (other.nitrogenMin != null)
				return false;
		} else if (!nitrogenMin.equals(other.nitrogenMin))
			return false;
		if (otherParticulatesMax == null) {
			if (other.otherParticulatesMax != null)
				return false;
		} else if (!otherParticulatesMax.equals(other.otherParticulatesMax))
			return false;
		if (otherParticulatesMin == null) {
			if (other.otherParticulatesMin != null)
				return false;
		} else if (!otherParticulatesMin.equals(other.otherParticulatesMin))
			return false;
		if (oxygenMax == null) {
			if (other.oxygenMax != null)
				return false;
		} else if (!oxygenMax.equals(other.oxygenMax))
			return false;
		if (oxygenMin == null) {
			if (other.oxygenMin != null)
				return false;
		} else if (!oxygenMin.equals(other.oxygenMin))
			return false;
		if (parameterCode == null) {
			if (other.parameterCode != null)
				return false;
		} else if (!parameterCode.equals(other.parameterCode))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (startDateGasQuality == null) {
			if (other.startDateGasQuality != null)
				return false;
		} else if (!startDateGasQuality.equals(other.startDateGasQuality))
			return false;
		if (steamMax == null) {
			if (other.steamMax != null)
				return false;
		} else if (!steamMax.equals(other.steamMax))
			return false;
		if (steamMin == null) {
			if (other.steamMin != null)
				return false;
		} else if (!steamMin.equals(other.steamMin))
			return false;
		if (system == null) {
			if (other.system != null)
				return false;
		} else if (!system.equals(other.system))
			return false;
		if (totalSulphurMax == null) {
			if (other.totalSulphurMax != null)
				return false;
		} else if (!totalSulphurMax.equals(other.totalSulphurMax))
			return false;
		if (totalSulphurMin == null) {
			if (other.totalSulphurMin != null)
				return false;
		} else if (!totalSulphurMin.equals(other.totalSulphurMin))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (wobbeIndexMax == null) {
			if (other.wobbeIndexMax != null)
				return false;
		} else if (!wobbeIndexMax.equals(other.wobbeIndexMax))
			return false;
		if (wobbeIndexMin == null) {
			if (other.wobbeIndexMin != null)
				return false;
		} else if (!wobbeIndexMin.equals(other.wobbeIndexMin))
			return false;
		return true;
	}

	public BigDecimal getMinValue() {
		return minValue;
	}

	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}

	public BigDecimal getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public BigDecimal getCarbonDioxideMin() {
		return carbonDioxideMin;
	}

	public void setCarbonDioxideMin(BigDecimal carbonDioxideMin) {
		this.carbonDioxideMin = carbonDioxideMin;
	}

	public BigDecimal getCarbonDioxideMax() {
		return carbonDioxideMax;
	}

	public void setCarbonDioxideMax(BigDecimal carbonDioxideMax) {
		this.carbonDioxideMax = carbonDioxideMax;
	}

}
