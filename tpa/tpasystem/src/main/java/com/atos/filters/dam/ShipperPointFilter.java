package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

// Esta clase no es un filtro para una pagina, sino para facilitar las consultas sobre la tabla
// TPA_TSHIPPER_POINT.
public class ShipperPointFilter implements Serializable{

	private static final long serialVersionUID = -8353176164832104091L;

	private BigDecimal systemId = null;
	private BigDecimal[] areaIds = null;
	private BigDecimal userGroupId = null;
	private boolean nullEndDateFlag = false;
	
	public ShipperPointFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}

	public BigDecimal[] getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(BigDecimal[] areaIds) {
		this.areaIds = areaIds;
	}

	public BigDecimal getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(BigDecimal userGroupId) {
		this.userGroupId = userGroupId;
	}

	public boolean isNullEndDateFlag() {
		return nullEndDateFlag;
	}

	public void setNullEndDateFlag(boolean nullEndDateFlag) {
		this.nullEndDateFlag = nullEndDateFlag;
	}

	@Override
	public String toString() {
		return "ShipperPointFilter [systemId=" + systemId + ", areaIds=" + Arrays.toString(areaIds) + ", userGroupId="
				+ userGroupId + ", nullEndDateFlag=" + nullEndDateFlag + "]";
	}
}
