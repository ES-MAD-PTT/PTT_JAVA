package com.atos.beans.metering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class MeteringRetrievingBean implements Serializable {
	

	private static final long serialVersionUID = 6371860709834194903L;
	private BigDecimal idnMeteringInput;
	private String inputCode;
	private Date lastModifDate;
	private String status;
	private byte[] binary_data;
	private String xml_data;
	private String file_name;
	private String wsErrorDesc;
	
	private String errorGasDay;
	private String errorMeteringPointID;
	private String errorEnergy; // es varchar en la bd
	private String errorTimeStamp;
	private String errorDescription;
	
	private String warningGasDay;
	private String warningMeteringPointID;
	private BigDecimal warningEnergy;
	private String warningTimeStamp;
	private String warning;
	
	public MeteringRetrievingBean() {
		this.idnMeteringInput= null;
		this.inputCode = null;
		this.lastModifDate = null;
		this.status = null;
		this.xml_data = null;
		this.file_name=null;
		this.wsErrorDesc=null;
		
		this.errorGasDay = null;
		this.errorMeteringPointID = null;
		this.errorEnergy = null;
		this.errorTimeStamp = null;
		this.errorDescription = null;
		
		this.warningGasDay = null;
		this.warningMeteringPointID = null;
		this.warningEnergy = null;
		this.warningTimeStamp = null;
		this.warning = null;
		
	}

	

	public String getXml_data() {
		return xml_data;
	}


	public void setXml_data(String xml_data) {
		this.xml_data = xml_data;
	}


	public String getInputCode() {
		return inputCode;
	}

	public void setInputCode(String inputCode) {
		this.inputCode = inputCode;
	}

	public Date getLastModifDate() {
		return lastModifDate;
	}

	public void setLastModifDate(Date lastModifDate) {
		this.lastModifDate = lastModifDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorGasDay() {
		return errorGasDay;
	}

	public void setErrorGasDay(String errorGasDay) {
		this.errorGasDay = errorGasDay;
	}

	public String getErrorMeteringPointID() {
		return errorMeteringPointID;
	}

	public void setErrorMeteringPointID(String errorMeteringPointID) {
		this.errorMeteringPointID = errorMeteringPointID;
	}

	public String getErrorEnergy() {
		return errorEnergy;
	}

	public void setErrorEnergy(String errorEnergy) {
		this.errorEnergy = errorEnergy;
	}

	public String getErrorTimeStamp() {
		return errorTimeStamp;
	}

	public void setErrorTimeStamp(String errorTimeStamp) {
		this.errorTimeStamp = errorTimeStamp;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getWarningGasDay() {
		return warningGasDay;
	}

	public void setWarningGasDay(String warningGasDay) {
		this.warningGasDay = warningGasDay;
	}

	public String getWarningMeteringPointID() {
		return warningMeteringPointID;
	}

	public void setWarningMeteringPointID(String warningMeteringPointID) {
		this.warningMeteringPointID = warningMeteringPointID;
	}

	public BigDecimal getWarningEnergy() {
		return warningEnergy;
	}

	public void setWarningEnergy(BigDecimal warningEnergy) {
		this.warningEnergy = warningEnergy;
	}

	public String getWarningTimeStamp() {
		return warningTimeStamp;
	}

	public void setWarningTimeStamp(String warningTimeStamp) {
		this.warningTimeStamp = warningTimeStamp;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}



	public String getFile_name() {
		return file_name;
	}



	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}



	public byte[] getBinary_data() {
		return binary_data;
	}



	public void setBinary_data(byte[] binary_data) {
		this.binary_data = binary_data;
	}



	public BigDecimal getIdnMeteringInput() {
		return idnMeteringInput;
	}



	public void setIdnMeteringInput(BigDecimal idnMeteringInput) {
		this.idnMeteringInput = idnMeteringInput;
	}



	public String getWsErrorDesc() {
		return wsErrorDesc;
	}



	public void setWsErrorDesc(String wsErrorDesc) {
		this.wsErrorDesc = wsErrorDesc;
	}


	@Override
	public String toString() {
		return "MeteringRetrievingBean [idnMeteringInput=" + idnMeteringInput + ", inputCode=" + inputCode
				+ ", lastModifDate=" + lastModifDate + ", status=" + status + ", binary_data="
				+ Arrays.toString(binary_data) + ", xml_data=" + xml_data + ", file_name=" + file_name
				+ ", wsErrorDesc=" + wsErrorDesc + ", errorGasDay=" + errorGasDay + ", errorMeteringPointID="
				+ errorMeteringPointID + ", errorEnergy=" + errorEnergy + ", errorTimeStamp=" + errorTimeStamp
				+ ", errorDescription=" + errorDescription + ", warningGasDay=" + warningGasDay
				+ ", warningMeteringPointID=" + warningMeteringPointID + ", warningEnergy=" + warningEnergy
				+ ", warningTimeStamp=" + warningTimeStamp + ", warning=" + warning + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(binary_data);
		result = prime * result + ((errorDescription == null) ? 0 : errorDescription.hashCode());
		result = prime * result + ((errorEnergy == null) ? 0 : errorEnergy.hashCode());
		result = prime * result + ((errorGasDay == null) ? 0 : errorGasDay.hashCode());
		result = prime * result + ((errorMeteringPointID == null) ? 0 : errorMeteringPointID.hashCode());
		result = prime * result + ((errorTimeStamp == null) ? 0 : errorTimeStamp.hashCode());
		result = prime * result + ((file_name == null) ? 0 : file_name.hashCode());
		result = prime * result + ((idnMeteringInput == null) ? 0 : idnMeteringInput.hashCode());
		result = prime * result + ((inputCode == null) ? 0 : inputCode.hashCode());
		result = prime * result + ((lastModifDate == null) ? 0 : lastModifDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((warning == null) ? 0 : warning.hashCode());
		result = prime * result + ((warningEnergy == null) ? 0 : warningEnergy.hashCode());
		result = prime * result + ((warningGasDay == null) ? 0 : warningGasDay.hashCode());
		result = prime * result + ((warningMeteringPointID == null) ? 0 : warningMeteringPointID.hashCode());
		result = prime * result + ((warningTimeStamp == null) ? 0 : warningTimeStamp.hashCode());
		result = prime * result + ((wsErrorDesc == null) ? 0 : wsErrorDesc.hashCode());
		result = prime * result + ((xml_data == null) ? 0 : xml_data.hashCode());
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
		MeteringRetrievingBean other = (MeteringRetrievingBean) obj;
		if (!Arrays.equals(binary_data, other.binary_data))
			return false;
		if (errorDescription == null) {
			if (other.errorDescription != null)
				return false;
		} else if (!errorDescription.equals(other.errorDescription))
			return false;
		if (errorEnergy == null) {
			if (other.errorEnergy != null)
				return false;
		} else if (!errorEnergy.equals(other.errorEnergy))
			return false;
		if (errorGasDay == null) {
			if (other.errorGasDay != null)
				return false;
		} else if (!errorGasDay.equals(other.errorGasDay))
			return false;
		if (errorMeteringPointID == null) {
			if (other.errorMeteringPointID != null)
				return false;
		} else if (!errorMeteringPointID.equals(other.errorMeteringPointID))
			return false;
		if (errorTimeStamp == null) {
			if (other.errorTimeStamp != null)
				return false;
		} else if (!errorTimeStamp.equals(other.errorTimeStamp))
			return false;
		if (file_name == null) {
			if (other.file_name != null)
				return false;
		} else if (!file_name.equals(other.file_name))
			return false;
		if (idnMeteringInput == null) {
			if (other.idnMeteringInput != null)
				return false;
		} else if (!idnMeteringInput.equals(other.idnMeteringInput))
			return false;
		if (inputCode == null) {
			if (other.inputCode != null)
				return false;
		} else if (!inputCode.equals(other.inputCode))
			return false;
		if (lastModifDate == null) {
			if (other.lastModifDate != null)
				return false;
		} else if (!lastModifDate.equals(other.lastModifDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (warning == null) {
			if (other.warning != null)
				return false;
		} else if (!warning.equals(other.warning))
			return false;
		if (warningEnergy == null) {
			if (other.warningEnergy != null)
				return false;
		} else if (!warningEnergy.equals(other.warningEnergy))
			return false;
		if (warningGasDay == null) {
			if (other.warningGasDay != null)
				return false;
		} else if (!warningGasDay.equals(other.warningGasDay))
			return false;
		if (warningMeteringPointID == null) {
			if (other.warningMeteringPointID != null)
				return false;
		} else if (!warningMeteringPointID.equals(other.warningMeteringPointID))
			return false;
		if (warningTimeStamp == null) {
			if (other.warningTimeStamp != null)
				return false;
		} else if (!warningTimeStamp.equals(other.warningTimeStamp))
			return false;
		if (wsErrorDesc == null) {
			if (other.wsErrorDesc != null)
				return false;
		} else if (!wsErrorDesc.equals(other.wsErrorDesc))
			return false;
		if (xml_data == null) {
			if (other.xml_data != null)
				return false;
		} else if (!xml_data.equals(other.xml_data))
			return false;
		return true;
	}
	
}
