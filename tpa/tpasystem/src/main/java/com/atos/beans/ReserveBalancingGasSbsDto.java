package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class ReserveBalancingGasSbsDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8943957974868738846L;
	//@Column(name="IDN_BALANCE_RESERVE_GAS")
	private BigDecimal idnBalanceReserveGas;
	//@Column(name="GAS_DAY")
	private Date gasDay;
	//@Column(name="IDN_USER_GROUP")
	private BigDecimal idnUserGroup;
	//@Column(name="USER_GROUP_ID")
	private String userGroupId;
	//@Column(name="IDN_RESBAL_CONTRACT")
	private BigDecimal idnResbalContract;
	//@Column(name="IDN_CONTRACT")
	private BigDecimal idnContract;
	//@Column(name="CONTRACT_CODE")
	private String contractCode;
	//@Column(name="COMMENTS")
	private String comments;
	//@Column(name="IDN_ZONE")
	private BigDecimal idnZone;
	//@Column(name="ZONE_CODE")
	private String zoneCode;
	//@Column(name="IDN_SYSTEM_POINT_TYPE")
	private BigDecimal idnSystemPointType;
	//@Column(name="TYPE_CODE")
	private String typeCode;
	//@Column(name="IDN_SYSTEM_POINT")
	private BigDecimal idnSystemPoint;
	//@Column(name="POINT_CODE")
	private String pointCode;
	//@Column(name="QUANTITY")
	private Double quantity;
	private String capContractCode;
	private String operatorComments;
	/**
	* devuelve idnBalanceReserveGas.
	* @return idnBalanceReserveGas
	*/
	public BigDecimal getIdnBalanceReserveGas(){
		 return idnBalanceReserveGas;
	}
	/**
	* establece el valor de idnBalanceReserveGas.
	 *@param valor nuevo valor
	*/
	public void setIdnBalanceReserveGas(BigDecimal valor){
		idnBalanceReserveGas = valor;
	}
	/**
	* devuelve gasDay.
	* @return gasDay
	*/
	public Date getGasDay(){
		 return gasDay;
	}
	/**
	* establece el valor de gasDay.
	 *@param valor nuevo valor
	*/
	public void setGasDay(Date valor){
		gasDay = valor;
	}
	/**
	* devuelve idnUserGroup.
	* @return idnUserGroup
	*/
	public BigDecimal getIdnUserGroup(){
		 return idnUserGroup;
	}
	/**
	* establece el valor de idnUserGroup.
	 *@param valor nuevo valor
	*/
	public void setIdnUserGroup(BigDecimal valor){
		idnUserGroup = valor;
	}
	/**
	* devuelve userGroupId.
	* @return userGroupId
	*/
	public String getUserGroupId(){
		 return userGroupId;
	}
	/**
	* establece el valor de userGroupId.
	 *@param valor nuevo valor
	*/
	public void setUserGroupId(String valor){
		userGroupId = valor;
	}
	/**
	* devuelve idnResbalContract.
	* @return idnResbalContract
	*/
	public BigDecimal getIdnResbalContract(){
		 return idnResbalContract;
	}
	/**
	* establece el valor de idnResbalContract.
	 *@param valor nuevo valor
	*/
	public void setIdnResbalContract(BigDecimal valor){
		idnResbalContract = valor;
	}
	/**
	* devuelve idnContract.
	* @return idnContract
	*/
	public BigDecimal getIdnContract(){
		 return idnContract;
	}
	/**
	* establece el valor de idnContract.
	 *@param valor nuevo valor
	*/
	public void setIdnContract(BigDecimal valor){
		idnContract = valor;
	}
	/**
	* devuelve contractCode.
	* @return contractCode
	*/
	public String getContractCode(){
		 return contractCode;
	}
	/**
	* establece el valor de contractCode.
	 *@param valor nuevo valor
	*/
	public void setContractCode(String valor){
		contractCode = valor;
	}
	/**
	* devuelve comments.
	* @return comments
	*/
	public String getComments(){
		 return comments;
	}
	/**
	* establece el valor de comments.
	 *@param valor nuevo valor
	*/
	public void setComments(String valor){
		comments = valor;
	}
	/**
	* devuelve idnZone.
	* @return idnZone
	*/
	public BigDecimal getIdnZone(){
		 return idnZone;
	}
	/**
	* establece el valor de idnZone.
	 *@param valor nuevo valor
	*/
	public void setIdnZone(BigDecimal valor){
		idnZone = valor;
	}
	/**
	* devuelve zoneCode.
	* @return zoneCode
	*/
	public String getZoneCode(){
		 return zoneCode;
	}
	/**
	* establece el valor de zoneCode.
	 *@param valor nuevo valor
	*/
	public void setZoneCode(String valor){
		zoneCode = valor;
	}
	/**
	* devuelve idnSystemPointType.
	* @return idnSystemPointType
	*/
	public BigDecimal getIdnSystemPointType(){
		 return idnSystemPointType;
	}
	/**
	* establece el valor de idnSystemPointType.
	 *@param valor nuevo valor
	*/
	public void setIdnSystemPointType(BigDecimal valor){
		idnSystemPointType = valor;
	}
	/**
	* devuelve typeCode.
	* @return typeCode
	*/
	public String getTypeCode(){
		 return typeCode;
	}
	/**
	* establece el valor de typeCode.
	 *@param valor nuevo valor
	*/
	public void setTypeCode(String valor){
		typeCode = valor;
	}
	/**
	* devuelve idnSystemPoint.
	* @return idnSystemPoint
	*/
	public BigDecimal getIdnSystemPoint(){
		 return idnSystemPoint;
	}
	/**
	* establece el valor de idnSystemPoint.
	 *@param valor nuevo valor
	*/
	public void setIdnSystemPoint(BigDecimal valor){
		idnSystemPoint = valor;
	}
	/**
	* devuelve pointCode.
	* @return pointCode
	*/
	public String getPointCode(){
		 return pointCode;
	}
	/**
	* establece el valor de pointCode.
	 *@param valor nuevo valor
	*/
	public void setPointCode(String valor){
		pointCode = valor;
	}
	/**
	* devuelve quantity.
	* @return quantity
	*/
	public Double getQuantity(){
		 return quantity;
	}
	/**
	* establece el valor de quantity.
	 *@param valor nuevo valor
	*/
	public void setQuantity(Double valor){
		quantity = valor;
	}

	/**
	* devuelve la realacion entre columnas y propiedades.
	*
	* @return Map con la relacion
	*/
	public static Map<String, String> getColumToProperty() {
	HashMap<String, String> columToProp = new HashMap<String, String>();
	columToProp.put("IDN_BALANCE_RESERVE_GAS", "idnBalanceReserveGas");
	columToProp.put("GAS_DAY", "gasDay");
	columToProp.put("IDN_USER_GROUP", "idnUserGroup");
	columToProp.put("USER_GROUP_ID", "userGroupId");
	columToProp.put("IDN_RESBAL_CONTRACT", "idnResbalContract");
	columToProp.put("CONTRACT_CODE", "contractCode");
	columToProp.put("IDN_CONTRACT", "idnContract");
	columToProp.put("CONTRACT_CODE", "contractCode");
	columToProp.put("COMMENTS", "comments");
	columToProp.put("IDN_ZONE", "idnZone");
	columToProp.put("ZONE_CODE", "zoneCode");
	columToProp.put("IDN_SYSTEM_POINT_TYPE", "idnSystemPointType");
	columToProp.put("TYPE_CODE", "typeCode");
	columToProp.put("IDN_SYSTEM_POINT", "idnSystemPoint");
	columToProp.put("POINT_CODE", "pointCode");
	columToProp.put("QUANTITY", "quantity");

	 return columToProp;

	}
	@Override
	public String toString() {
	StringBuffer toStr = new StringBuffer();
	toStr.append(idnBalanceReserveGas);
	toStr.append(',');
	toStr.append(gasDay);
	toStr.append(',');
	toStr.append(idnUserGroup);
	toStr.append(',');
	toStr.append(userGroupId);
	toStr.append(',');
	toStr.append(idnResbalContract);
	toStr.append(',');
	toStr.append(contractCode);
	toStr.append(',');
	toStr.append(idnContract);
	toStr.append(',');
	toStr.append(contractCode);
	toStr.append(',');
	toStr.append(comments);
	toStr.append(',');
	toStr.append(idnZone);
	toStr.append(',');
	toStr.append(zoneCode);
	toStr.append(',');
	toStr.append(idnSystemPointType);
	toStr.append(',');
	toStr.append(typeCode);
	toStr.append(',');
	toStr.append(idnSystemPoint);
	toStr.append(',');
	toStr.append(pointCode);
	toStr.append(',');
	toStr.append(quantity);
	toStr.append(',');

	 return toStr.toString().replaceAll("null", "");
	 
	}

	public String getCapContractCode() {
		return capContractCode;
	}

	public void setCapContractCode(String capContractCode) {
		this.capContractCode = capContractCode;
	}

	public String getOperatorComments() {
		return operatorComments;
	}

	public void setOperatorComments(String operatorComments) {
		this.operatorComments = operatorComments;
	}
	}