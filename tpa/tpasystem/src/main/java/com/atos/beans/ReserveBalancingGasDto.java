package com.atos.beans;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.primefaces.model.DefaultStreamedContent;

public class ReserveBalancingGasDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5893188894241379170L;
	
	
		//@Column(name="IDN_RESBAL_FORECAST_FILE")
		private BigDecimal idnResbalForecastFile;
		//@Column(name="IDN_USER_GROUP")
		private BigDecimal idnUserGroup;
		//@Column(name="USER_GROUP_ID")
		private String userGroupId;
		//@Column(name="IDN_RESBAL_CONTRACT")
		private BigDecimal idnResbalContract;
		//@Column(name="CONTRACT_CODE")
		private String contractCode;
		//@Column(name="MONTH_YEAR")
		private Date monthYear;
		//@Column(name="FILE_NAME")
		private String fileName;

		transient private DefaultStreamedContent scFile;
		private byte[] binaryData;	
		
		/**
		* devuelve la realacion entre columnas y propiedades.
		*
		* @return Map con la relacion
		*/
		public static Map<String, String> getColumToProperty() {
		HashMap<String, String> columToProp = new HashMap<String, String>();
		columToProp.put("IDN_RESBAL_FORECAST_FILE", "idnResbalForecastFile");
		columToProp.put("IDN_USER_GROUP", "idnUserGroup");
		columToProp.put("USER_GROUP_ID", "userGroupId");
		columToProp.put("IDN_RESBAL_CONTRACT", "idnResbalContract");
		columToProp.put("CONTRACT_CODE", "contractCode");
		columToProp.put("MONTH_YEAR", "monthYear");
		columToProp.put("FILE_NAME", "fileName");

		 return columToProp;

		}
		
		
		public BigDecimal getIdnResbalForecastFile() {
			return idnResbalForecastFile;
		}
		public void setIdnResbalForecastFile(BigDecimal idnResbalForecastFile) {
			this.idnResbalForecastFile = idnResbalForecastFile;
		}
		public BigDecimal getIdnUserGroup() {
			return idnUserGroup;
		}
		public void setIdnUserGroup(BigDecimal idnUserGroup) {
			this.idnUserGroup = idnUserGroup;
		}
		public String getUserGroupId() {
			return userGroupId;
		}
		public void setUserGroupId(String userGroupId) {
			this.userGroupId = userGroupId;
		}
		public BigDecimal getIdnResbalContract() {
			return idnResbalContract;
		}
		public void setIdnResbalContract(BigDecimal idnResbalContract) {
			this.idnResbalContract = idnResbalContract;
		}
		public String getContractCode() {
			return contractCode;
		}
		public void setContractCode(String contractCode) {
			this.contractCode = contractCode;
		}
		public Date getMonthYear() {
			return monthYear;
		}
		public void setMonthYear(Date monthYear) {
			this.monthYear = monthYear;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}


		public DefaultStreamedContent getScFile() {

		if( binaryData != null ) {
			ByteArrayInputStream bais = new ByteArrayInputStream(binaryData);
			scFile = new DefaultStreamedContent(bais);
			scFile.setName(fileName);
			// No se conoce el contentType.
			//scFile.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); 
		}
			return scFile;
		}


		public void setScFile(DefaultStreamedContent scFile) {
			this.scFile = scFile;
		}


		public byte[] getBinaryData() {
			return binaryData;
		}


		public void setBinaryData(byte[] binaryData) {
			this.binaryData = binaryData;
		}

}
