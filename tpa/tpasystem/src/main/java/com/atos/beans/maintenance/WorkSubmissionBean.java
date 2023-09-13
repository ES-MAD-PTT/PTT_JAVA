package com.atos.beans.maintenance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.UserAudBean;

public class WorkSubmissionBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6773871560561563186L;

	private BigDecimal idn_maintenance;
	private String maintenance_code;
	private String maintenance_subject; //CH736
	private String status;
	private String area;
	private BigDecimal idn_area;
	private String subarea;
	private BigDecimal idn_subarea;
	private Date start_date;
	private Date end_date;
	private boolean is_daily;
	private String daily;
	private String engineering_comments;
	private String operator_comments;
	private String operator_comments_old;
	private BigDecimal idn_maintenance_file;
	private String css_color;
	private Date actual_end_date;
	private String file_name;
	
	

	private List<WorkOperatorMaintenanceBean> operator_data;

	public BigDecimal getIdn_maintenance() {
		return idn_maintenance;
	}

	public void setIdn_maintenance(BigDecimal idn_maintenance) {
		this.idn_maintenance = idn_maintenance;
	}

	public String getMaintenance_code() {
		return maintenance_code;
	}

	public void setMaintenance_code(String maintenance_code) {
		this.maintenance_code = maintenance_code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public BigDecimal getIdn_area() {
		return idn_area;
	}

	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}

	public String getSubarea() {
		return subarea;
	}

	public void setSubarea(String subarea) {
		this.subarea = subarea;
	}

	public BigDecimal getIdn_subarea() {
		return idn_subarea;
	}

	public void setIdn_subarea(BigDecimal idn_subarea) {
		this.idn_subarea = idn_subarea;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getEngineering_comments() {
		return engineering_comments;
	}

	public void setEngineering_comments(String engineering_comments) {
		this.engineering_comments = engineering_comments;
	}

	public String getOperator_comments() {
		return operator_comments;
	}

	public void setOperator_comments(String operator_comments) {
		this.operator_comments = operator_comments;
	}

	public String getOperator_comments_old() {
		return operator_comments_old;
	}

	public void setOperator_comments_old(String operator_comments_old) {
		this.operator_comments_old = operator_comments_old;
	}

	public List<WorkOperatorMaintenanceBean> getOperator_data() {
		return operator_data;
	}

	public void setOperator_data(List<WorkOperatorMaintenanceBean> operator_data) {
		this.operator_data = operator_data;
	}

	public boolean isIs_daily() {
		return is_daily;
	}

	public void setIs_daily(boolean is_daily) {
		this.is_daily = is_daily;
		if(is_daily){
			this.daily = "Y";
		} else {
			this.daily = "N";
		}
	}

	public String getDaily() {
		return daily;
	}

	public void setDaily(String daily) {
		this.daily = daily;
		if(daily!=null){
			if(daily.equals("Y")){
				this.is_daily = true;
			} else {
				this.is_daily = false;
			}
		} else {
			this.is_daily = false;
		}
	}

	public BigDecimal getIdn_maintenance_file() {
		return idn_maintenance_file;
	}

	public void setIdn_maintenance_file(BigDecimal idn_maintenance_file) {
		this.idn_maintenance_file = idn_maintenance_file;
	}

	public String getCss_color() {
		return css_color;
	}

	public void setCss_color(String css_color) {
		this.css_color = css_color;
	}

	public Date getActual_end_date() {
		return actual_end_date;
	}

	public void setActual_end_date(Date actual_end_date) {
		this.actual_end_date = actual_end_date;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actual_end_date == null) ? 0 : actual_end_date.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((css_color == null) ? 0 : css_color.hashCode());
		result = prime * result + ((daily == null) ? 0 : daily.hashCode());
		result = prime * result + ((end_date == null) ? 0 : end_date.hashCode());
		result = prime * result + ((engineering_comments == null) ? 0 : engineering_comments.hashCode());
		result = prime * result + ((file_name == null) ? 0 : file_name.hashCode());
		result = prime * result + ((idn_area == null) ? 0 : idn_area.hashCode());
		result = prime * result + ((idn_maintenance == null) ? 0 : idn_maintenance.hashCode());
		result = prime * result + ((idn_maintenance_file == null) ? 0 : idn_maintenance_file.hashCode());
		result = prime * result + ((idn_subarea == null) ? 0 : idn_subarea.hashCode());
		result = prime * result + (is_daily ? 1231 : 1237);
		result = prime * result + ((maintenance_code == null) ? 0 : maintenance_code.hashCode());
		result = prime * result + ((operator_comments == null) ? 0 : operator_comments.hashCode());
		result = prime * result + ((operator_comments_old == null) ? 0 : operator_comments_old.hashCode());
		result = prime * result + ((operator_data == null) ? 0 : operator_data.hashCode());
		result = prime * result + ((start_date == null) ? 0 : start_date.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((subarea == null) ? 0 : subarea.hashCode());
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
		WorkSubmissionBean other = (WorkSubmissionBean) obj;
		if (actual_end_date == null) {
			if (other.actual_end_date != null)
				return false;
		} else if (!actual_end_date.equals(other.actual_end_date))
			return false;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (css_color == null) {
			if (other.css_color != null)
				return false;
		} else if (!css_color.equals(other.css_color))
			return false;
		if (daily == null) {
			if (other.daily != null)
				return false;
		} else if (!daily.equals(other.daily))
			return false;
		if (end_date == null) {
			if (other.end_date != null)
				return false;
		} else if (!end_date.equals(other.end_date))
			return false;
		if (engineering_comments == null) {
			if (other.engineering_comments != null)
				return false;
		} else if (!engineering_comments.equals(other.engineering_comments))
			return false;
		if (file_name == null) {
			if (other.file_name != null)
				return false;
		} else if (!file_name.equals(other.file_name))
			return false;
		if (idn_area == null) {
			if (other.idn_area != null)
				return false;
		} else if (!idn_area.equals(other.idn_area))
			return false;
		if (idn_maintenance == null) {
			if (other.idn_maintenance != null)
				return false;
		} else if (!idn_maintenance.equals(other.idn_maintenance))
			return false;
		if (idn_maintenance_file == null) {
			if (other.idn_maintenance_file != null)
				return false;
		} else if (!idn_maintenance_file.equals(other.idn_maintenance_file))
			return false;
		if (idn_subarea == null) {
			if (other.idn_subarea != null)
				return false;
		} else if (!idn_subarea.equals(other.idn_subarea))
			return false;
		if (is_daily != other.is_daily)
			return false;
		if (maintenance_code == null) {
			if (other.maintenance_code != null)
				return false;
		} else if (!maintenance_code.equals(other.maintenance_code))
			return false;
		if (operator_comments == null) {
			if (other.operator_comments != null)
				return false;
		} else if (!operator_comments.equals(other.operator_comments))
			return false;
		if (operator_comments_old == null) {
			if (other.operator_comments_old != null)
				return false;
		} else if (!operator_comments_old.equals(other.operator_comments_old))
			return false;
		if (operator_data == null) {
			if (other.operator_data != null)
				return false;
		} else if (!operator_data.equals(other.operator_data))
			return false;
		if (start_date == null) {
			if (other.start_date != null)
				return false;
		} else if (!start_date.equals(other.start_date))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (subarea == null) {
			if (other.subarea != null)
				return false;
		} else if (!subarea.equals(other.subarea))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WorkSubmissionBean [idn_maintenance=" + idn_maintenance + ", maintenance_code=" + maintenance_code
				+ ", status=" + status + ", area=" + area + ", idn_area=" + idn_area + ", subarea=" + subarea
				+ ", idn_subarea=" + idn_subarea + ", start_date=" + start_date + ", end_date=" + end_date
				+ ", is_daily=" + is_daily + ", daily=" + daily + ", engineering_comments=" + engineering_comments
				+ ", operator_comments=" + operator_comments + ", operator_comments_old=" + operator_comments_old
				+ ", idn_maintenance_file=" + idn_maintenance_file + ", css_color=" + css_color + ", actual_end_date="
				+ actual_end_date + ", file_name=" + file_name + ", operator_data=" + operator_data + "]";
	}

	public String getMaintenance_subject() {
		return maintenance_subject;
	}

	public void setMaintenance_subject(String maintenance_subject) {
		this.maintenance_subject = maintenance_subject;
	}

	

}
