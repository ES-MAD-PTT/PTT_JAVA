package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.primefaces.model.DualListModel;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.UserAudBean;

public class ShipperBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_user_group;
	private BigDecimal idn_shipper;
	private String id;
	private String companyName;
	// Cada DualList tiene listas de puntos como estas.
	//private List<ComboFilterNS> availableContractPoints = new ArrayList<ComboFilterNS>();
	//private List<ComboFilterNS> contractPoints = new ArrayList<ComboFilterNS>();
	// Es la lista dual con listado de contratos disponibles y los asociados al shipper.
	private DualListModel<ComboFilterNS> dLContractPoints = new DualListModel<ComboFilterNS>();
	// Lista dual previa, para guardar datos antes de editar, por si hubiera que hacer Cancel/Rollback.
	private DualListModel<ComboFilterNS> dLPrevContractPoints = new DualListModel<ComboFilterNS>();
	// Para marcar en la pantalla DAM si se esta editando el shipper.
	private boolean editing = false;
	private String address;
	private String creditRating;
	private String eRCLicenseID;
	private String telephone;
	private String fax;
	private String sapId;
	private String bankAccount;
	private Date startDate;
	private Date endDate;

	public ShipperBean() {
		super();
	}

	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}

	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}

	public BigDecimal getIdn_shipper() {
		return idn_shipper;
	}

	public void setIdn_shipper(BigDecimal idn_shipper) {
		this.idn_shipper = idn_shipper;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public DualListModel<ComboFilterNS> getdLContractPoints() {
		return dLContractPoints;
	}

	public void setdLContractPoints(DualListModel<ComboFilterNS> dLContractPoints) {
		this.dLContractPoints = dLContractPoints;
	}

	public DualListModel<ComboFilterNS> getdLPrevContractPoints() {
		return dLPrevContractPoints;
	}

	public void setdLPrevContractPoints(DualListModel<ComboFilterNS> dLPrevContractPoints) {
		this.dLPrevContractPoints = dLPrevContractPoints;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreditRating() {
		return creditRating;
	}

	public void setCreditRating(String creditRating) {
		this.creditRating = creditRating;
	}

	public String geteRCLicenseID() {
		return eRCLicenseID;
	}

	public void seteRCLicenseID(String eRCLicenseID) {
		this.eRCLicenseID = eRCLicenseID;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSapId() {
		return sapId;
	}

	public void setSapId(String sapId) {
		this.sapId = sapId;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((bankAccount == null) ? 0 : bankAccount.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((creditRating == null) ? 0 : creditRating.hashCode());
		result = prime * result + ((dLContractPoints == null) ? 0 : dLContractPoints.hashCode());
		result = prime * result + ((dLPrevContractPoints == null) ? 0 : dLPrevContractPoints.hashCode());
		result = prime * result + ((eRCLicenseID == null) ? 0 : eRCLicenseID.hashCode());
		result = prime * result + (editing ? 1231 : 1237);
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((fax == null) ? 0 : fax.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idn_shipper == null) ? 0 : idn_shipper.hashCode());
		result = prime * result + ((idn_user_group == null) ? 0 : idn_user_group.hashCode());
		result = prime * result + ((sapId == null) ? 0 : sapId.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
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
		ShipperBean other = (ShipperBean) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (bankAccount == null) {
			if (other.bankAccount != null)
				return false;
		} else if (!bankAccount.equals(other.bankAccount))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (creditRating == null) {
			if (other.creditRating != null)
				return false;
		} else if (!creditRating.equals(other.creditRating))
			return false;
		if (dLContractPoints == null) {
			if (other.dLContractPoints != null)
				return false;
		} else if (!dLContractPoints.equals(other.dLContractPoints))
			return false;
		if (dLPrevContractPoints == null) {
			if (other.dLPrevContractPoints != null)
				return false;
		} else if (!dLPrevContractPoints.equals(other.dLPrevContractPoints))
			return false;
		if (eRCLicenseID == null) {
			if (other.eRCLicenseID != null)
				return false;
		} else if (!eRCLicenseID.equals(other.eRCLicenseID))
			return false;
		if (editing != other.editing)
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (fax == null) {
			if (other.fax != null)
				return false;
		} else if (!fax.equals(other.fax))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idn_shipper == null) {
			if (other.idn_shipper != null)
				return false;
		} else if (!idn_shipper.equals(other.idn_shipper))
			return false;
		if (idn_user_group == null) {
			if (other.idn_user_group != null)
				return false;
		} else if (!idn_user_group.equals(other.idn_user_group))
			return false;
		if (sapId == null) {
			if (other.sapId != null)
				return false;
		} else if (!sapId.equals(other.sapId))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ShipperBean [idn_user_group=" + idn_user_group + ", idn_shipper=" + idn_shipper + ", id=" + id
				+ ", companyName=" + companyName + ", dLContractPoints=" + dLContractPoints + ", dLPrevContractPoints="
				+ dLPrevContractPoints + ", editing=" + editing + ", address=" + address + ", creditRating="
				+ creditRating + ", eRCLicenseID=" + eRCLicenseID + ", telephone=" + telephone + ", fax=" + fax
				+ ", sapId=" + sapId + ", bankAccount=" + bankAccount + ", startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}


}
