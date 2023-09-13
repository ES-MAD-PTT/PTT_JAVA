package com.atos.beans.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.primefaces.model.DualListModel;

import com.atos.beans.UserAudBean;
import com.atos.beans.dam.ShipperBean;

public class EmergencyDiffDayBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -8210572551497660753L;

	private BigDecimal idn_tso_event;
	private String event_id;
	private String type_desc;
	private Date start_date;
	private Date end_date;
	private Date aud_last_date;
	private String zone_desc;
	private String status;
	private String comments;
	private String opening_file_name;
	private String closing_file_name;
	private byte[] opening_binary_data;
	private byte[] closing_binary_data;
	private List<EmergencyDiffDayDetailsBean> details;
	
	
	protected DualListModel<String> shippers;
	
	public EmergencyDiffDayBean() {
		super();
		this.idn_tso_event = null;
		this.event_id = null;
		this.type_desc = null;
		this.start_date = null;
		this.end_date = null;
		this.aud_last_date = null;
		this.zone_desc = null;
		this.status = null;
		this.comments = null;
		this.opening_file_name = null;
		this.closing_file_name = null;
		this.shippers = null;
		this.opening_binary_data = null;
		this.closing_binary_data = null;
		this.details = null;
		
	}
	

	public EmergencyDiffDayBean(BigDecimal idn_tso_event, String event_id, String type_desc, Date start_date,
			Date end_date, Date auDate, String zone_desc, String status, String comments, String opening_file_name,
			String closing_file_name, DualListModel<String> shippers, byte[] opening_binary_data,  byte[] closing_binary_data, List<EmergencyDiffDayDetailsBean> details) {
		super();
		this.idn_tso_event = idn_tso_event;
		this.event_id = event_id;
		this.type_desc = type_desc;
		this.start_date = start_date;
		this.end_date = end_date;
		this.aud_last_date = auDate;
		this.zone_desc = zone_desc;
		this.status = status;
		this.comments = comments;
		this.opening_file_name = opening_file_name;
		this.closing_file_name = closing_file_name;
		this.shippers = shippers;
		this.opening_binary_data = opening_binary_data;
		this.closing_binary_data = closing_binary_data;
		this.details = details;
	}



	public EmergencyDiffDayBean(String _username) {
		super(_username);
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getIdn_tso_event() {
		return idn_tso_event;
	}
	
	public void setIdn_tso_event(BigDecimal idn_tso_event) {
		this.idn_tso_event = idn_tso_event;
	}
	
	public String getEvent_id() {
		return event_id;
	}
	
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	
	public String getType_desc() {
		return type_desc;
	}
	
	public void setType_desc(String type_desc) {
		this.type_desc = type_desc;
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
	
	public Date getAud_last_date() {
		return aud_last_date;
	}
	
	public void setAud_last_date(Date lastDate) {
		this.aud_last_date = lastDate;
	}
	
	public String getZone_desc() {
		return zone_desc;
	}
	
	public void setZone_desc(String zone_desc) {
		this.zone_desc = zone_desc;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
	
	public String getClosing_file_name() {
		return closing_file_name;
	}
	
	public void setClosing_file_name(String closing_file_name) {
		this.closing_file_name = closing_file_name;
	}
	
	public DualListModel<String> getShippers() {
		return shippers;
	}

	public void setShippers(DualListModel<String> shippers) {
		this.shippers = shippers;
	}

	public byte[] getOpening_binary_data() {
		return opening_binary_data;
	}
	
	public void setOpening_binary_data(byte[] binary_data) {
		this.opening_binary_data = binary_data;
	}
	
	public byte[] getClosing_binary_data() {
		return closing_binary_data;
	}
	
	public void setClosing_binary_data(byte[] binary_data) {
		this.closing_binary_data = binary_data;
	}


	public List<EmergencyDiffDayDetailsBean> getDetails() {
		return details;
	}


	public void setDetails(List<EmergencyDiffDayDetailsBean> details) {
		this.details = details;
	}
	
	
}
