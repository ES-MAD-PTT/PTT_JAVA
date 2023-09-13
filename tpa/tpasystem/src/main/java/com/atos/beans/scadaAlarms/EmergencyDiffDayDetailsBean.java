package com.atos.beans.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class EmergencyDiffDayDetailsBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -8210572551497660753L;

	private BigDecimal idn_tso_event;
	private BigDecimal idn_tso_event_shipper;
	private BigDecimal idn_user_group;
	private String user_group_id;
	private String user_group_name;
	private String comments;
	private String opening_file_name;
	private byte[] opening_binary_data;
	private String closing_file_name;
	private byte[] closing_binary_data;
	private String user;
	
	public EmergencyDiffDayDetailsBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmergencyDiffDayDetailsBean(BigDecimal idn_tso_event, BigDecimal idn_tso_event_shipper, String user_group_id, String user_group_name, BigDecimal idn_user_group,
			String comments, String opening_file_name, byte[] opening_binary_data, String closing_file_name,
			byte[] closing_binary_data, String user) {
		super();
		this.idn_tso_event = idn_tso_event;
		this.idn_tso_event_shipper = idn_tso_event_shipper;
		this.idn_user_group = idn_user_group;
		this.user_group_id = user_group_id;
		this.user_group_name = user_group_name;
		this.comments = comments;
		this.opening_file_name = opening_file_name;
		this.opening_binary_data = opening_binary_data;
		this.closing_file_name = closing_file_name;
		this.closing_binary_data = closing_binary_data;
		this.user = user;
	}
	
	public BigDecimal getIdn_tso_event() {
		return idn_tso_event;
	}
	
	public void setIdn_tso_event(BigDecimal idn_tso_event) {
		this.idn_tso_event = idn_tso_event;
	}
	
	public BigDecimal getIdn_tso_event_shipper() {
		return idn_tso_event_shipper;
	}
	
	public void setIdn_tso_event_shipper(BigDecimal idn_tso_event_shipper) {
		this.idn_tso_event_shipper = idn_tso_event_shipper;
	}

	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}

	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getOpening_file_name() {
		return opening_file_name;
	}

	public void setOpening_file_name(String opening_file_name) {
		this.opening_file_name = opening_file_name;
	}

	public byte[] getOpening_binary_data() {
		return opening_binary_data;
	}

	public void setOpening_binary_data(byte[] opening_binary_data) {
		this.opening_binary_data = opening_binary_data;
	}

	public String getClosing_file_name() {
		return closing_file_name;
	}

	public void setClosing_file_name(String closing_file_name) {
		this.closing_file_name = closing_file_name;
	}

	public byte[] getClosing_binary_data() {
		return closing_binary_data;
	}

	public void setClosing_binary_data(byte[] closing_binary_data) {
		this.closing_binary_data = closing_binary_data;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "EmergencyDiffDayDetailsBean [idn_tso_event=" + idn_tso_event + ", idn_tso_event_shipper="
				+ idn_tso_event_shipper + ", idn_user_group=" + idn_user_group + ", user_group_id=" + user_group_id
				+ ", user_group_name=" + user_group_name + ", comments=" + comments + ", opening_file_name="
				+ opening_file_name + ", opening_binary_data=" + Arrays.toString(opening_binary_data)
				+ ", closing_file_name=" + closing_file_name + ", closing_binary_data="
				+ Arrays.toString(closing_binary_data) + ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(closing_binary_data);
		result = prime * result + ((closing_file_name == null) ? 0 : closing_file_name.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((idn_tso_event == null) ? 0 : idn_tso_event.hashCode());
		result = prime * result + ((idn_tso_event_shipper == null) ? 0 : idn_tso_event_shipper.hashCode());
		result = prime * result + ((idn_user_group == null) ? 0 : idn_user_group.hashCode());
		result = prime * result + Arrays.hashCode(opening_binary_data);
		result = prime * result + ((opening_file_name == null) ? 0 : opening_file_name.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		EmergencyDiffDayDetailsBean other = (EmergencyDiffDayDetailsBean) obj;
		if (!Arrays.equals(closing_binary_data, other.closing_binary_data))
			return false;
		if (closing_file_name == null) {
			if (other.closing_file_name != null)
				return false;
		} else if (!closing_file_name.equals(other.closing_file_name))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (idn_tso_event == null) {
			if (other.idn_tso_event != null)
				return false;
		} else if (!idn_tso_event.equals(other.idn_tso_event))
			return false;
		if (idn_tso_event_shipper == null) {
			if (other.idn_tso_event_shipper != null)
				return false;
		} else if (!idn_tso_event_shipper.equals(other.idn_tso_event_shipper))
			return false;
		if (idn_user_group == null) {
			if (other.idn_user_group != null)
				return false;
		} else if (!idn_user_group.equals(other.idn_user_group))
			return false;
		if (!Arrays.equals(opening_binary_data, other.opening_binary_data))
			return false;
		if (opening_file_name == null) {
			if (other.opening_file_name != null)
				return false;
		} else if (!opening_file_name.equals(other.opening_file_name))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
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
