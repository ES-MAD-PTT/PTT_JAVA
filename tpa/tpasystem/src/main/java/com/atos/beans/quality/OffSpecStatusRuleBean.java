package com.atos.beans.quality;

import java.io.Serializable;
import java.math.BigDecimal;

public class OffSpecStatusRuleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7251990220363347077L;

	// Modos que definen las caracteristicas de los campos a mostrar en la pantalla de cambio de estado.
	private static final String noVisibleMode = "NV";
	// Con este modo "NE", el campo se mostrara en la ventana, a partir del dato de la ultima version de la incidencia, pero no se guarda en la nueva version.
	private static final String noEditableMode = "NE";
	private static final String editableNoMandatoryMode = "ENM";
	private static final String editableMandatoryMode = "EM";
	
	public static final String originatorShipperNotifMode = "ORIGINATOR_SHIPPER";
	public static final String defaultOperatorNotifMode = "DEFAULT_OPERATOR";
	public static final String otherShippersNotifMode = "OTHER_SHIPPERS";
	public static final String allShippersNotifMode = "ALL_SHIPPERS";
	public static final String noneNotifMode = "NONE";
	
	private BigDecimal ruleId;
	private BigDecimal currentStatusId;	
	private String currentStatusCode;
	private String currentStatusDesc;
	private BigDecimal nextStatusId;
	private String nextStatusCode;
	private String nextStatusDesc;
	private String neededPermissionCode;
	
	private String shipperFieldMode;
	private String commentFieldMode;
	private String fileFieldMode;
	private String endDateFieldMode; 
	/* Los valores anteriores indican como se deben presentar distintos campos de entrada
	 * al usuario, segun sean necesarios en cada estado. Valores posibles:
	 *	-	NV – not visible field (campo no visible)
	 *	-	NE – visible field but not editable (campo visible pero no editable)
	 *	-	ENM – visible and editable field but not mandatory (campo visible y editable pero no obligatorio)
	 *	-	EM – visible, editable and mandatory field (campo visible, editable  y obligatorio)
	 */
	public static final String askOtherShippersYes = "Y";
	private String askOtherShippers;			// Valores posibles Y, N.
	
	private String notificationTypeCode;		// Indica el tipo de mensaje a enviar a grupos de usuarios.
	private String notificationMode;		
	//	Valores posibles:
	//		- ORIGINATOR_SHIPPER (se enviará una notificación al shipper indicado como Originator Shipper )
	//		- OTHER_SHIPPERS (se enviará una notificación a todos los usuarios de todos los shippers menos al shipper indicado como Originator Shipper)
	//		- ALL_OPERATORS (se enviará una notificación a todos los usuarios de todos los operadores)
	//		- ALL_SHIPPERS (se enviará una notificación a todos los usuarios de todos los shippers)
	
	//PARA LOS EMAILS
	private String emailTypeCode;		// Indica el tipo de correo a enviar a grupos de usuarios.
	private String emailMode;		
	
	public OffSpecStatusRuleBean() {
		this.ruleId = null;
		this.currentStatusId = null;
		this.currentStatusCode = null;
		this.currentStatusDesc = null;
		this.nextStatusId = null;
		this.nextStatusCode = null;
		this.nextStatusDesc = null;
		this.neededPermissionCode = null;
		this.shipperFieldMode = null;
		this.commentFieldMode = null;
		this.fileFieldMode = null;
		this.endDateFieldMode = null;
		this.askOtherShippers = null;		
		this.notificationTypeCode = null;
		this.notificationMode = null;
		this.emailTypeCode = null;
		this.emailMode = null;
	}
	public BigDecimal getRuleId() {
		return ruleId;
	}
	public void setRuleId(BigDecimal ruleId) {
		this.ruleId = ruleId;
	}
	public BigDecimal getCurrentStatusId() {
		return currentStatusId;
	}
	public void setCurrentStatusId(BigDecimal currentStatusId) {
		this.currentStatusId = currentStatusId;
	}
	public String getCurrentStatusCode() {
		return currentStatusCode;
	}
	public void setCurrentStatusCode(String currentStatusCode) {
		this.currentStatusCode = currentStatusCode;
	}
	public String getCurrentStatusDesc() {
		return currentStatusDesc;
	}
	public void setCurrentStatusDesc(String currentStatusDesc) {
		this.currentStatusDesc = currentStatusDesc;
	}
	public BigDecimal getNextStatusId() {
		return nextStatusId;
	}
	public void setNextStatusId(BigDecimal nextStatusId) {
		this.nextStatusId = nextStatusId;
	}
	public String getNextStatusCode() {
		return nextStatusCode;
	}
	public void setNextStatusCode(String nextStatusCode) {
		this.nextStatusCode = nextStatusCode;
	}
	public String getNextStatusDesc() {
		return nextStatusDesc;
	}
	public void setNextStatusDesc(String nextStatusDesc) {
		this.nextStatusDesc = nextStatusDesc;
	}
	public String getNeededPermissionCode() {
		return neededPermissionCode;
	}
	public void setNeededPermissionCode(String neededPermissionCode) {
		this.neededPermissionCode = neededPermissionCode;
	}
	public String getShipperFieldMode() {
		return shipperFieldMode;
	}
	public void setShipperFieldMode(String shipperFieldMode) {
		this.shipperFieldMode = shipperFieldMode;
	}
	public String getCommentFieldMode() {
		return commentFieldMode;
	}
	public void setCommentFieldMode(String commentFieldMode) {
		this.commentFieldMode = commentFieldMode;
	}
	public String getFileFieldMode() {
		return fileFieldMode;
	}
	public void setFileFieldMode(String fileFieldMode) {
		this.fileFieldMode = fileFieldMode;
	}
	public String getEndDateFieldMode() {
		return endDateFieldMode;
	}
	public void setEndDateFieldMode(String endDateFieldMode) {
		this.endDateFieldMode = endDateFieldMode;
	}
	public String getAskOtherShippers() {
		return askOtherShippers;
	}
	public void setAskOtherShippers(String askOtherShippers) {
		this.askOtherShippers = askOtherShippers;
	}
	public String getNotificationTypeCode() {
		return notificationTypeCode;
	}
	public void setNotificationTypeCode(String notificationTypeCode) {
		this.notificationTypeCode = notificationTypeCode;
	}
	public String getNotificationMode() {
		return notificationMode;
	}
	public void setNotificationMode(String notificationMode) {
		this.notificationMode = notificationMode;
	}
	public String getEmailTypeCode() {
		return emailTypeCode;
	}
	public void setEmailTypeCode(String emailTypeCode) {
		this.emailTypeCode = emailTypeCode;
	}
	public String getEmailMode() {
		return emailMode;
	}
	public void setEmailMode(String emailMode) {
		this.emailMode = emailMode;
	}
	public boolean shipperIsVisible(){
		return (! noVisibleMode.equals(this.shipperFieldMode));
	}
	
	public boolean shipperIsEditable(){
		return ( editableNoMandatoryMode.equals(this.shipperFieldMode) || editableMandatoryMode.equals(this.shipperFieldMode));
	}
	
	public boolean shipperIsEditMandatory(){
		return (editableMandatoryMode.equals(this.shipperFieldMode));
	}
	
	public boolean commentIsVisible(){
		return (! noVisibleMode.equals(this.commentFieldMode));
	}
	
	public boolean commentIsEditable(){
		return (editableNoMandatoryMode.equals(this.commentFieldMode) || editableMandatoryMode.equals(this.commentFieldMode));
	}
	
	public boolean commentIsEditMandatory(){
		return (editableMandatoryMode.equals(this.commentFieldMode));
	}
	
	public boolean fileIsVisible(){
		return (! noVisibleMode.equals(this.fileFieldMode));
	}
	
	public boolean fileIsEditable(){
		return (editableNoMandatoryMode.equals(this.fileFieldMode) || editableMandatoryMode.equals(this.fileFieldMode));
	}
	
	public boolean fileIsEditMandatory(){
		return (editableMandatoryMode.equals(this.fileFieldMode));
	}
	
	public boolean endDateIsVisible(){
		return (! noVisibleMode.equals(this.endDateFieldMode));
	}
	
	public boolean endDateIsEditable(){
		return (editableNoMandatoryMode.equals(this.endDateFieldMode) || editableMandatoryMode.equals(this.endDateFieldMode));
	}
	
	public boolean endDateIsEditMandatory(){
		return (editableMandatoryMode.equals(this.endDateFieldMode));
	}
	
	public boolean noneIsVisible(){
		return (!shipperIsVisible() && !commentIsVisible() && !fileIsVisible() && !endDateIsVisible());
	}
	
	@Override
	public String toString() {
		return "OffSpecStatusRuleBean [ruleId=" + ruleId + ", currentStatusId=" + currentStatusId
				+ ", currentStatusCode=" + currentStatusCode + ", currentStatusDesc=" + currentStatusDesc
				+ ", nextStatusId=" + nextStatusId + ", nextStatusCode=" + nextStatusCode + ", nextStatusDesc="
				+ nextStatusDesc + ", neededPermissionCode=" + neededPermissionCode + ", shipperFieldMode="
				+ shipperFieldMode + ", commentFieldMode=" + commentFieldMode + ", fileFieldMode=" + fileFieldMode
				+ ", endDateFieldMode=" + endDateFieldMode + ", askOtherShippers=" + askOtherShippers
				+ ", notificationTypeCode=" + notificationTypeCode + ", notificationMode=" + notificationMode
				+ ", emailTypeCode=" + emailTypeCode + ", emailMode=" + emailMode + "]";
	}
}
