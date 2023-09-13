package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import com.atos.beans.UserAudBean;

public class RoleBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_pipeline_system;
	private BigDecimal idn_profile;
	private BigDecimal idn_profile_pipeline;
	private BigDecimal idn_profile_permission;
	private BigDecimal idn_permission;
	private String permission_code;

	private String profile_desc;

	private String system; // pipeline
	private String role; // profile_code
	private String process; /// permission

	private boolean bIsEnabled;
	private String sIsEnabled;
	private Date startDate;
	private Date endDate;

	private BigDecimal[] selectedProcess;

	public RoleBean() {
		super();
	}

	public RoleBean(BigDecimal idn_pipeline_system, BigDecimal idn_profile, BigDecimal idn_profile_pipeline,
			BigDecimal idn_profile_permission, BigDecimal idn_permission, String permission_code, String profile_desc,
			String system, String role, String process, boolean bIsEnabled, String sIsEnabled, Date startDate,
			Date endDate, BigDecimal[] selectedProcess) {
		super();
		this.idn_pipeline_system = idn_pipeline_system;
		this.idn_profile = idn_profile;
		this.idn_profile_pipeline = idn_profile_pipeline;
		this.idn_profile_permission = idn_profile_permission;
		this.idn_permission = idn_permission;
		this.permission_code = permission_code;
		this.profile_desc = profile_desc;
		this.system = system;
		this.role = role;
		this.process = process;
		this.bIsEnabled = bIsEnabled;
		this.sIsEnabled = sIsEnabled;
		this.startDate = startDate;
		this.endDate = endDate;
		this.selectedProcess = selectedProcess;
	}

	public BigDecimal getIdn_profile_permission() {
		return idn_profile_permission;
	}

	public void setIdn_profile_permission(BigDecimal idn_profile_permission) {
		this.idn_profile_permission = idn_profile_permission;
	}

	public BigDecimal getIdn_profile_pipeline() {
		return idn_profile_pipeline;
	}

	public void setIdn_profile_pipeline(BigDecimal idn_profile_pipeline) {
		this.idn_profile_pipeline = idn_profile_pipeline;
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

	public String getProfile_desc() {
		return profile_desc;
	}

	public void setProfile_desc(String profile_desc) {
		this.profile_desc = profile_desc;
	}

	public BigDecimal getIdn_pipeline_system() {
		return idn_pipeline_system;
	}

	public void setIdn_pipeline_system(BigDecimal idn_pipeline_system) {
		this.idn_pipeline_system = idn_pipeline_system;
	}

	public BigDecimal getIdn_profile() {
		return idn_profile;
	}

	public void setIdn_profile(BigDecimal idn_profile) {
		this.idn_profile = idn_profile;
	}

	public BigDecimal getIdn_permission() {
		return idn_permission;
	}

	public void setIdn_permission(BigDecimal idn_permission) {
		this.idn_permission = idn_permission;
	}

	public String getPermission_code() {
		return permission_code;
	}

	public void setPermission_code(String permission_code) {
		this.permission_code = permission_code;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public boolean isbIsEnabled() {
		return bIsEnabled;
	}

	public void setbIsEnabled(boolean bIsEnabled) {
		this.bIsEnabled = bIsEnabled;
	}

	public String getsIsEnabled() {

		return sIsEnabled;
	}

	// OJO aqui rellenamos los booleanos
	public void setsIsEnabled(String sIsEnabled) {

		if (sIsEnabled.equals("N")) {
			bIsEnabled = false;
		} else
			bIsEnabled = true;

		this.sIsEnabled = sIsEnabled;
	}

	public BigDecimal[] getSelectedProcess() {
		return selectedProcess;
	}

	public void setSelectedProcess(BigDecimal[] selectedProcess) {
		this.selectedProcess = selectedProcess;
	}

	@Override
	public String toString() {
		return "RoleBean [idn_pipeline_system=" + idn_pipeline_system + ", idn_profile=" + idn_profile
				+ ", idn_profile_pipeline=" + idn_profile_pipeline + ", idn_profile_permission="
				+ idn_profile_permission + ", idn_permission=" + idn_permission + ", permission_code=" + permission_code
				+ ", profile_desc=" + profile_desc + ", system=" + system + ", role=" + role + ", process=" + process
				+ ", bIsEnabled=" + bIsEnabled + ", sIsEnabled=" + sIsEnabled + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", selectedProcess=" + Arrays.toString(selectedProcess) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (bIsEnabled ? 1231 : 1237);
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((idn_permission == null) ? 0 : idn_permission.hashCode());
		result = prime * result + ((idn_pipeline_system == null) ? 0 : idn_pipeline_system.hashCode());
		result = prime * result + ((idn_profile == null) ? 0 : idn_profile.hashCode());
		result = prime * result + ((idn_profile_permission == null) ? 0 : idn_profile_permission.hashCode());
		result = prime * result + ((idn_profile_pipeline == null) ? 0 : idn_profile_pipeline.hashCode());
		result = prime * result + ((permission_code == null) ? 0 : permission_code.hashCode());
		result = prime * result + ((process == null) ? 0 : process.hashCode());
		result = prime * result + ((profile_desc == null) ? 0 : profile_desc.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((sIsEnabled == null) ? 0 : sIsEnabled.hashCode());
		result = prime * result + Arrays.hashCode(selectedProcess);
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((system == null) ? 0 : system.hashCode());
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
		RoleBean other = (RoleBean) obj;
		if (bIsEnabled != other.bIsEnabled)
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (idn_permission == null) {
			if (other.idn_permission != null)
				return false;
		} else if (!idn_permission.equals(other.idn_permission))
			return false;
		if (idn_pipeline_system == null) {
			if (other.idn_pipeline_system != null)
				return false;
		} else if (!idn_pipeline_system.equals(other.idn_pipeline_system))
			return false;
		if (idn_profile == null) {
			if (other.idn_profile != null)
				return false;
		} else if (!idn_profile.equals(other.idn_profile))
			return false;
		if (idn_profile_permission == null) {
			if (other.idn_profile_permission != null)
				return false;
		} else if (!idn_profile_permission.equals(other.idn_profile_permission))
			return false;
		if (idn_profile_pipeline == null) {
			if (other.idn_profile_pipeline != null)
				return false;
		} else if (!idn_profile_pipeline.equals(other.idn_profile_pipeline))
			return false;
		if (permission_code == null) {
			if (other.permission_code != null)
				return false;
		} else if (!permission_code.equals(other.permission_code))
			return false;
		if (process == null) {
			if (other.process != null)
				return false;
		} else if (!process.equals(other.process))
			return false;
		if (profile_desc == null) {
			if (other.profile_desc != null)
				return false;
		} else if (!profile_desc.equals(other.profile_desc))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (sIsEnabled == null) {
			if (other.sIsEnabled != null)
				return false;
		} else if (!sIsEnabled.equals(other.sIsEnabled))
			return false;
		if (!Arrays.equals(selectedProcess, other.selectedProcess))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (system == null) {
			if (other.system != null)
				return false;
		} else if (!system.equals(other.system))
			return false;
		return true;
	}

}
