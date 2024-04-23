package com.atos.beans.tariff;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.UserAudBean;

public class CreditDebitNoteBean extends UserAudBean implements Serializable{
	private static final long serialVersionUID = -5255211382691787452L;
	
	private BigDecimal noteId;
	private String shipper;
	private String shortName;
	private BigDecimal shipperId;
	private Date monthYear;
	private String cndnId;
	private BigDecimal typeId;
	private String type;
	private String comments;
	private boolean checked;
	private String sent;
	private BigDecimal idn_system;
	private String username;
	private boolean updated=false;
	private boolean newBean=false;
	private List <CreditDebitNoteDetailBean> details;
	
	public CreditDebitNoteBean() {
		super();
		this.noteId = null;
		this.shipper = null;
		this.shortName = null;
		this.shipperId = null;
		this.monthYear = null;
		this.cndnId = null;
		this.type = null;
		this.typeId = null;
		this.comments = null;
		this.sent = null;
		this.idn_system = null;
		this.username = null;
		this.checked=false;
		this.updated=false;
		this.newBean=false;
		this.details = null;
	}
	
	public CreditDebitNoteBean(BigDecimal noteId, String shipper, String shortName,BigDecimal shipperId, Date monthYear, String cndnId, BigDecimal typeId, String type, String comments, String sent, BigDecimal idn_system, String username,
			boolean updated, boolean newBean, boolean checked, List<CreditDebitNoteDetailBean> details) {
		super();
		this.noteId = noteId;
		this.shipper = shipper;
		this.shortName = shortName;
		this.shipperId = shipperId;
		this.monthYear = monthYear;
		this.cndnId = cndnId;
		this.type = type;
		this.typeId = typeId;
		this.comments = comments;
		this.sent = sent;
		this.idn_system = idn_system;
		this.username = username;
		this.updated = updated;		
		this.newBean = newBean;
		this.checked = checked;
		this.details = details;
	}

	public BigDecimal getNoteId() {
		return noteId;
	}

	public void setNoteId(BigDecimal noteId) {
		this.noteId = noteId;
	}

	public String getShipper() {
		return shipper;
	}
	
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public BigDecimal getShipperId() {
		return shipperId;
	}
	
	public void setShipperId(BigDecimal shipperId) {
		this.shipperId = shipperId;
	}
	
	public Date getMonthYear() {
		return monthYear;
	}
	
	public void setMonthYear(Date monthYear) {
		this.monthYear = monthYear;
	}
	
	public String getCndnId() {
		return cndnId;
	}
	
	public void setCndnId(String cndnId) {
		this.cndnId = cndnId;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getTypeId() {
		return typeId;
	}

	public void setTypeId(BigDecimal typeId) {
		this.typeId = typeId;
	}

	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getSent() {
		return sent;
	}
	
	public void setSent(String sent) {
		this.sent = sent;
	}
	
	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<CreditDebitNoteDetailBean> getDetails() {
		return details;
	}
	
	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public boolean isNewBean() {
		return newBean;
	}

	public void setNewBean(boolean newBean) {
		this.newBean = newBean;
	}
	
	

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setDetails(List<CreditDebitNoteDetailBean> details) {
		this.details = details;
	}
	
}
