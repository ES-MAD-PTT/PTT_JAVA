package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OperationFileUpdate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal idnOperationFileUpdate;
	private BigDecimal idnOperationFile;
	private Date versionDate;
	private BigDecimal idnUserGroup;
	private String status;
	private String xmlData;
	private Date audInsDate;
	private Date audLastDate;
	private String audInsUser;
	private String audLastUser;

	/**
	 * devuelve idnOperationFileUpdate.
	 * 
	 * @return idnOperationFileUpdate
	 */
	public BigDecimal getIdnOperationFileUpdate() {
		return idnOperationFileUpdate;
	}

	/**
	 * establece el valor de idnOperationFileUpdate.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setIdnOperationFileUpdate(BigDecimal valor) {
		idnOperationFileUpdate = valor;
	}

	/**
	 * devuelve idnOperationFile.
	 * 
	 * @return idnOperationFile
	 */
	public BigDecimal getIdnOperationFile() {
		return idnOperationFile;
	}

	/**
	 * establece el valor de idnOperationFile.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setIdnOperationFile(BigDecimal valor) {
		idnOperationFile = valor;
	}

	/**
	 * devuelve versionDate.
	 * 
	 * @return versionDate
	 */
	public Date getVersionDate() {
		return versionDate;
	}

	/**
	 * establece el valor de versionDate.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setVersionDate(Date valor) {
		versionDate = valor;
	}

	/**
	 * devuelve idnUserGroup.
	 * 
	 * @return idnUserGroup
	 */
	public BigDecimal getIdnUserGroup() {
		return idnUserGroup;
	}

	/**
	 * establece el valor de idnUserGroup.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setIdnUserGroup(BigDecimal valor) {
		idnUserGroup = valor;
	}

	/**
	 * devuelve status.
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * establece el valor de status.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setStatus(String valor) {
		status = valor;
	}

	/**
	 * devuelve xmlData.
	 * 
	 * @return xmlData
	 */
	public String getXmlData() {
		return xmlData;
	}

	/**
	 * establece el valor de xmlData.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setXmlData(String valor) {
		xmlData = valor;
	}

	/**
	 * devuelve audInsDate.
	 * 
	 * @return audInsDate
	 */
	public Date getAudInsDate() {
		return audInsDate;
	}

	/**
	 * establece el valor de audInsDate.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setAudInsDate(Date valor) {
		audInsDate = valor;
	}

	/**
	 * devuelve audLastDate.
	 * 
	 * @return audLastDate
	 */
	public Date getAudLastDate() {
		return audLastDate;
	}

	/**
	 * establece el valor de audLastDate.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setAudLastDate(Date valor) {
		audLastDate = valor;
	}

	/**
	 * devuelve audInsUser.
	 * 
	 * @return audInsUser
	 */
	public String getAudInsUser() {
		return audInsUser;
	}

	/**
	 * establece el valor de audInsUser.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setAudInsUser(String valor) {
		audInsUser = valor;
	}

	/**
	 * devuelve audLastUser.
	 * 
	 * @return audLastUser
	 */
	public String getAudLastUser() {
		return audLastUser;
	}

	/**
	 * establece el valor de audLastUser.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setAudLastUser(String valor) {
		audLastUser = valor;
	}

	/**
	 * devuelve la realacion entre columnas y propiedades.
	 *
	 * @return Map con la relacion
	 */
	public static Map<String, String> getColumToProperty() {
		HashMap<String, String> columToProp = new HashMap<String, String>();
		columToProp.put("IDN_OPERATION_FILE_UPDATE", "idnOperationFileUpdate");
		columToProp.put("IDN_OPERATION_FILE", "idnOperationFile");
		columToProp.put("VERSION_DATE", "versionDate");
		columToProp.put("IDN_USER_GROUP", "idnUserGroup");
		columToProp.put("STATUS", "status");
		columToProp.put("XML_DATA", "xmlData");
		columToProp.put("AUD_INS_DATE", "audInsDate");
		columToProp.put("AUD_LAST_DATE", "audLastDate");
		columToProp.put("AUD_INS_USER", "audInsUser");
		columToProp.put("AUD_LAST_USER", "audLastUser");

		return columToProp;

	}

	@Override
	public String toString() {
		StringBuffer toStr = new StringBuffer();
		toStr.append(idnOperationFileUpdate);
		toStr.append(',');
		toStr.append(idnOperationFile);
		toStr.append(',');
		toStr.append(versionDate);
		toStr.append(',');
		toStr.append(idnUserGroup);
		toStr.append(',');
		toStr.append(status);
		toStr.append(',');
		toStr.append(xmlData);
		toStr.append(',');
		toStr.append(audInsDate);
		toStr.append(',');
		toStr.append(audLastDate);
		toStr.append(',');
		toStr.append(audInsUser);
		toStr.append(',');
		toStr.append(audLastUser);
		toStr.append(',');

		return toStr.toString().replaceAll("null", "");

	}
}
