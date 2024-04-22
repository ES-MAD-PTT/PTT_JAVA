package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.primefaces.model.DualListModel;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.UserAudBean;

public class ShipperBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_user_group;
	private BigDecimal idn_shipper;
	private String id;
	private String companyName;
	private String shortName;
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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
		return Objects.hash(address, bankAccount, companyName, creditRating, dLContractPoints, dLPrevContractPoints,
				eRCLicenseID, editing, endDate, fax, id, idn_shipper, idn_user_group, sapId, shortName, startDate,
				telephone);
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
		return Objects.equals(address, other.address) && Objects.equals(bankAccount, other.bankAccount)
				&& Objects.equals(companyName, other.companyName) && Objects.equals(creditRating, other.creditRating)
				&& Objects.equals(dLContractPoints, other.dLContractPoints)
				&& Objects.equals(dLPrevContractPoints, other.dLPrevContractPoints)
				&& Objects.equals(eRCLicenseID, other.eRCLicenseID) && editing == other.editing
				&& Objects.equals(endDate, other.endDate) && Objects.equals(fax, other.fax)
				&& Objects.equals(id, other.id) && Objects.equals(idn_shipper, other.idn_shipper)
				&& Objects.equals(idn_user_group, other.idn_user_group) && Objects.equals(sapId, other.sapId)
				&& Objects.equals(shortName, other.shortName) && Objects.equals(startDate, other.startDate)
				&& Objects.equals(telephone, other.telephone);
	}

	@Override
	public String toString() {
		return "ShipperBean [idn_user_group=" + idn_user_group + ", idn_shipper=" + idn_shipper + ", id=" + id
				+ ", companyName=" + companyName + ", shortName=" + shortName + ", dLContractPoints=" + dLContractPoints
				+ ", dLPrevContractPoints=" + dLPrevContractPoints + ", editing=" + editing + ", address=" + address
				+ ", creditRating=" + creditRating + ", eRCLicenseID=" + eRCLicenseID + ", telephone=" + telephone
				+ ", fax=" + fax + ", sapId=" + sapId + ", bankAccount=" + bankAccount + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}


}
