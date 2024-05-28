package com.atos.beans.quality;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

import com.atos.beans.UserAudBean;
import com.atos.beans.UserBean;
import com.atos.utils.Constants;

public class OffSpecIncidentBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5715428377766750639L;
	
	//private static final String updateString = "incidents1"; 
	// Comando para que al pulsar el boton "NextStatus" se guarde:
	// - La instancia que se ha pulsado, como selected en la vista.
	// - el id del nuevo estado para poder identificar la regla de cambio de estado a aplicar.
	private static final String commandStringStart = "#{OSGRManagementView.prepareChangeStatus(incident,'";
	private static final String commandStringEnd = "')}";
	
	private BigDecimal incidentId;
	private BigDecimal incidentTypeId;
	private String incidentTypeDesc;
	private String incidentCode;
	private BigDecimal incidentVersionId;		// Se utilizara para cargar el fichero.
	private BigDecimal qualityPointId;
	private String qualityPointCode;
	private BigDecimal groupId;			// Del usuario que esta haciendo el cambio. Para el insert en tabla off_spec_log.
	private String groupCode;
	private BigDecimal originatorShipperId;
	private String originatorShipperCode;
	private String shortName;
	private Date startDate;
	private Date endDate;
	private BigDecimal userId; 					// Para guardar el id de usuario en cada una de las versiones, en la tabla de log.
	private BigDecimal idnFirstUser;
	private String firstUserCode; 			// usuario que ha reportado la incidencia.
	private Date lastModifiedDate;
	private BigDecimal statusId;
	private String statusDesc;
	// A cada incidencia se asocia un objeto status para poder obtener la lista de los
	// nextStatus, cuando se pida pintar el menuButton, de forma independiente para cada incidencia.
	private OffSpecStatusBean status;
	// Cuando el usuario pulse el boton, aqui se guarda su eleccion, para identificar que regla usar
	// (fijara las caracteristicas de los parametros de la vista).
	private	OffSpecStatusRuleBean chosenNextStatusRule;
	private String initialComments;
	private String comments;
	private String fileName;
	// binaryData y scFile deben tener la misma info. scFile se genera a partir de binaryData.
	// binaryData necesita metodo set (desde vista o desde consulta a BD)
	// y scFile solo tendra metodo get (desde la vista), y se genera a partir de binaryData.
	// scFile y BinaryData no se pinta en el toString();
	private byte[] binaryData;	
	transient private DefaultStreamedContent scFile;
	// Falta incluir la lista de parametros de calidad, para cargarla de una vez con una asociacion.
	private List<OffSpecGasQualityParameterBean> gasParams;
	private List<OffSpecResponseBean> discloseResponses;
		
	private BigDecimal newStatusId; 
	private BigDecimal newOriginatorShipperId;
	private String newComments;
	// newBinaryData y newUlFile deben tener la misma info. newBinaryData se genera a partir de newUlFile.
	// newUlFile y newBinaryData no se pinta en el toString();
	private String newFileName;
	private UploadedFile newUlFile;
	private byte[] newBinaryData;
	private Date newEndDate;
	private String newIncidentTypeDesc;		// Esta variable no se usa en inserts de tablas, sino en las notificaciones.
	
	// Este modelo de menu se usa para pintar el boton de cambio de estado en la tabla de la pantalla,
	// de forma independiente para cada incidencia.
	// En este model no se pueden incluir submenus, porque no estan soportados dentro del <p:menuButton>.
    private MenuModel model;
    
    
    
    private BigDecimal idnAction;
    private String action;
    private String commentsShipper;
    private String commentsOperator;
    private String commentsUser;
    private String shipper;
    private String commentClosed;
    private String firstUserType;
    private List<BigDecimal> multiShippers = new ArrayList<BigDecimal>();
    private List<OffSpecFileBean> files = new ArrayList<OffSpecFileBean>();
    private List<OffSpecActionFileBean> filesAction = new ArrayList<OffSpecActionFileBean>();
    private Map<BigDecimal, Object> actionsFree= new HashMap<BigDecimal, Object>();
    
    //CH706
    private String operatorComments; // en realidad es Transporter Response comment
    private BigDecimal idn_pipeline_system;
		
	public OffSpecIncidentBean() {
		this.incidentId = null;
		this.incidentTypeId = null;
		this.incidentTypeDesc = null;
		this.incidentCode = null;
		this.incidentVersionId = null;
		this.qualityPointId = null;
		this.qualityPointCode = null;
		this.groupId = null;
		this.originatorShipperId = null;
		this.originatorShipperCode = null;
		this.shortName = null;
		this.startDate = null;
		this.endDate = null;
		this.userId = null;
		this.firstUserCode = null;
		this.lastModifiedDate = null;
		this.statusId = null;
		this.statusDesc = null;
		this.initialComments = null;
		this.comments = null;
		this.fileName = null;
		this.binaryData = null;
		this.scFile = null;
		this.gasParams = new ArrayList<OffSpecGasQualityParameterBean>();
		this.newStatusId = null;
		this.newOriginatorShipperId = null;
		this.newComments = null;
		this.newFileName = null;
		this.newUlFile = null;
		this.newBinaryData = null;
		this.newEndDate = null;
		this.newIncidentTypeDesc = null;		
		this.model = null;
		this.idn_pipeline_system = null;
	}

	public BigDecimal getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(BigDecimal incidentId) {
		this.incidentId = incidentId;
	}

	public List<OffSpecFileBean> getFiles() {
		return files;
	}

	public void setFiles(List<OffSpecFileBean> files) {
		this.files = files;
	}

	public Map<BigDecimal, Object> getActionsFree() {
		return actionsFree;
	}

	public void setActionsFree(Map<BigDecimal, Object> actionsFree) {
		this.actionsFree = actionsFree;
	}

	public String getCommentsShipper() {
		return commentsShipper;
	}

	public void setCommentsShipper(String commentsShipper) {
		this.commentsShipper = commentsShipper;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getFirstUserType() {
		return firstUserType;
	}

	public void setFirstUserType(String firstUserType) {
		this.firstUserType = firstUserType;
	}

	public String getCommentClosed() {
		return commentClosed;
	}

	public void setCommentClosed(String commentClosed) {
		this.commentClosed = commentClosed;
	}

	public String getCommentsOperator() {
		return commentsOperator;
	}

	public void setCommentsOperator(String commentsOperator) {
		this.commentsOperator = commentsOperator;
	}

	public List<OffSpecActionFileBean> getFilesAction() {
		return filesAction;
	}

	public void setFilesAction(List<OffSpecActionFileBean> filesAction) {
		this.filesAction = filesAction;
	}

	public String getCommentsUser() {
		return commentsUser;
	}

	public void setCommentsUser(String commentsUser) {
		this.commentsUser = commentsUser;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public BigDecimal getIdnAction() {
		return idnAction;
	}

	public void setIdnAction(BigDecimal idnAction) {
		this.idnAction = idnAction;
	}

	public List<BigDecimal> getMultiShippers() {
		return multiShippers;
	}

	public void setMultiShippers(List<BigDecimal> multiShippers) {
		this.multiShippers = multiShippers;
	}

	public BigDecimal getIncidentTypeId() {
		return incidentTypeId;
	}

	public void setIncidentTypeId(BigDecimal incidentTypeId) {
		this.incidentTypeId = incidentTypeId;
	}

	public String getIncidentTypeDesc() {
		return incidentTypeDesc;
	}

	public void setIncidentTypeDesc(String incidentTypeDesc) {
		this.incidentTypeDesc = incidentTypeDesc;
	}

	public String getIncidentCode() {
		return incidentCode;
	}

	public void setIncidentCode(String incidentCode) {
		this.incidentCode = incidentCode;
	}

	public BigDecimal getIncidentVersionId() {
		return incidentVersionId;
	}

	public void setIncidentVersionId(BigDecimal incidentVersionId) {
		this.incidentVersionId = incidentVersionId;
	}

	public BigDecimal getQualityPointId() {
		return qualityPointId;
	}

	public void setQualityPointId(BigDecimal qualityPointId) {
		this.qualityPointId = qualityPointId;
	}

	public String getQualityPointCode() {
		return qualityPointCode;
	}

	public void setQualityPointCode(String qualityPointCode) {
		this.qualityPointCode = qualityPointCode;
	}
	
	public BigDecimal getGroupId() {
		return groupId;
	}

	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public BigDecimal getOriginatorShipperId() {
		return originatorShipperId;
	}

	public void setOriginatorShipperId(BigDecimal originatorShipperId) {
		this.originatorShipperId = originatorShipperId;
	}

	public String getOriginatorShipperCode() {
		return originatorShipperCode;
	}

	public void setOriginatorShipperCode(String originatorShipperCode) {
		this.originatorShipperCode = originatorShipperCode;
	}
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public String getFirstUserCode() {
		return firstUserCode;
	}

	public void setFirstUserCode(String firstUserCode) {
		this.firstUserCode = firstUserCode;
	}

	public BigDecimal getIdnFirstUser() {
		return idnFirstUser;
	}

	public void setIdnFirstUser(BigDecimal idnFirstUser) {
		this.idnFirstUser = idnFirstUser;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public BigDecimal getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public OffSpecStatusBean getStatus() {
		return status;
	}

	public void setStatus(OffSpecStatusBean status) {
		this.status = status;
	}

	public OffSpecStatusRuleBean getChosenNextStatusRule() {
		return chosenNextStatusRule;
	}

	public void setChosenNextStatusRule(OffSpecStatusRuleBean chosenNextStatusRule) {
		this.chosenNextStatusRule = chosenNextStatusRule;
	}

	public String getInitialComments() {
		return initialComments;
	}
	
	public void setInitialComments(String initialComments) {
		this.initialComments = initialComments;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public byte[] getBinaryData() {
		return binaryData;
	}

	public void setBinaryData(byte[] binaryData) {
		this.binaryData = binaryData;
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

	public List<OffSpecGasQualityParameterBean> getGasParams() {
		return gasParams;
	}

	public void setGasParams(List<OffSpecGasQualityParameterBean> gasParams) {
		this.gasParams = gasParams;
	}

    public List<OffSpecResponseBean> getDiscloseResponses() {
		return discloseResponses;
	}

	public void setDiscloseResponses(List<OffSpecResponseBean> discloseResponses) {
		this.discloseResponses = discloseResponses;
	}

	// Este metodo se usa desde la pantalla OffSpecGasReportResponse, para utilizar datos asociados a la respuesta
	// a la incidencia. Solo debe haber una respuesta por incidencia; por esto se toma el primer elemento de la lista.
	public OffSpecResponseBean getFirstResponse(){
		OffSpecResponseBean tmpRes = null;
		
		if(discloseResponses!= null && !discloseResponses.isEmpty())
			tmpRes = discloseResponses.get(0); 
		
		return tmpRes;
	}
	
	public BigDecimal getNewStatusId() {
		return newStatusId;
	}

	public void setNewStatusId(BigDecimal newStatusId) {
		this.newStatusId = newStatusId;
	}

	public BigDecimal getNewOriginatorShipperId() {
		return newOriginatorShipperId;
	}

	public void setNewOriginatorShipperId(BigDecimal newOriginatorShipperId) {
		this.newOriginatorShipperId = newOriginatorShipperId;
	}

	public String getNewComments() {
		return newComments;
	}

	public void setNewComments(String newComments) {
		this.newComments = newComments;
	}

	public String getNewFileName() {
		if(newUlFile != null)
			this.newFileName = newUlFile.getFileName();
		
		return newFileName;
	}

//	public void setNewFileName(String newFileName) {
//		this.newFileName = newFileName;
//	}

	public UploadedFile getNewUlFile() {
		return newUlFile;
	}

	public void setNewUlFile(UploadedFile newUlFile) {
		this.newUlFile = newUlFile;
	}

	public byte[] getNewBinaryData() {
		if(newUlFile != null)
			this.newBinaryData = newUlFile.getContents();
		
		return newBinaryData;
	}
	
//	public void setNewBinaryData(byte[] newBinaryData) {
//		this.newBinaryData = newBinaryData;
//	}

	public Date getNewEndDate() {
		return newEndDate;
	}

	public void setNewEndDate(Date newEndDate) {
		this.newEndDate = newEndDate;
	}

	public String getNewIncidentTypeDesc() {
		return newIncidentTypeDesc;
	}

	public void setNewIncidentTypeDesc(String newIncidentTypeDesc) {
		this.newIncidentTypeDesc = newIncidentTypeDesc;
	}

	public MenuModel getModel(UserBean _user) {
    	// Solo se genera el modelo 1 vez.
    	if(model == null)
    		model = prepareModel(_user);
    	
        return model;
    }  

	
	
	public String getOperatorComments() {
		return operatorComments;
	}

	public void setOperatorComments(String operatorComments) {
		this.operatorComments = operatorComments;
	}
	

	public BigDecimal getIdn_pipeline_system() {
		return idn_pipeline_system;
	}

	public void setIdn_pipeline_system(BigDecimal idn_pipeline_system) {
		this.idn_pipeline_system = idn_pipeline_system;
	}

	@Override
	public String toString() {
		return "OffSpecIncidentBean [incidentId=" + incidentId + ", incidentTypeId=" + incidentTypeId
				+ ", incidentTypeDesc=" + incidentTypeDesc + ", incidentCode=" + incidentCode + ", incidentVersionId="
				+ incidentVersionId + ", qualityPointId=" + qualityPointId + ", qualityPointCode=" + qualityPointCode
				+ ", groupId=" + groupId + ", originatorShipperId=" + originatorShipperId + ", originatorShipperCode="
				+ originatorShipperCode + ", shortName=\"\r\n"
						+ "				+ shortName + \", startDate=" + startDate + ", endDate=" + endDate + ", userId=" + userId
				+ ", firstUserCode=" + firstUserCode + ", lastModifiedDate=" + lastModifiedDate + ", statusId="
				+ statusId + ", statusDesc=" + statusDesc + ", status=" + status + ", chosenNextStatusRule="
				+ chosenNextStatusRule + ", initialComments=" + initialComments + ", comments=" + comments
				+ ", fileName=" + fileName + ", binaryData=" + Arrays.toString(binaryData) + ", gasParams=" + gasParams
				+ ", discloseResponses=" + discloseResponses + ", newStatusId=" + newStatusId
				+ ", newOriginatorShipperId=" + newOriginatorShipperId + ", newComments=" + newComments
				+ ", newFileName=" + newFileName + ", newUlFile=" + newUlFile + ", newBinaryData="
				+ Arrays.toString(newBinaryData) + ", newEndDate=" + newEndDate + ", newIncidentTypeDesc="
				+ newIncidentTypeDesc + ", model=" + model + ", operatorComments=" + operatorComments
				+ ", idn_pipeline_system=" + idn_pipeline_system + "]";
	}

	// Para pintar o no el boton de cambio de estado de cada incidencia.
	// Antes de generar los botones que permitan el cambio de estado se comprueba:
	// - Si el usuario es operador (puede hacer cualquiera cambio estado, para los que tenga permiso (definido en la carga de reglas de cambio de estado))
	// - O la incidencia no tiene originatorShipper con lo que la incidencia esta en un estado inicial, con lo que no ha llegado al estado en que se pidan respuestas
	// a otros shippers
	// - O hay originatorShipper, y entonces se comprueba que corresponde al del propio usuario conectado.
	// Asi se evita que un shipper que ve una incidencia por habersele pedido una respuesta, pueda realizar cambio de estado sobre una incidencia que no es suya.
	public boolean canChangeStatus(UserBean _user) {
		return ((_user.isUser_type(Constants.OPERATOR) ||
				this.originatorShipperId==null ||
				(this.originatorShipperId!=null && this.originatorShipperId.compareTo(_user.getIdn_user_group()) == 0) ) &&
				(this.getStatus().getNextStatusSet().size()>0));
	}
	
	private MenuModel prepareModel(UserBean _user) {
		DefaultMenuModel tmpModel = new DefaultMenuModel();
		DefaultMenuItem tmpItem = null;
		
		// Antes de generar los botones que permitan el cambio de estado se comprueba:
		// - Si el usuario es operador (puede hacer cualquiera cambio estado, para los que tenga permiso (definido en la carga de reglas de cambio de estado))
		// - O la incidencia no tiene originatorShipper con lo que la incidencia esta en un estado inicial, con lo que no ha llegado al estado en que se pidan respuestas
		// a otros shippers
		// - O hay originatorShipper, y entonces se comprueba que corresponde al del propio usuario conectado.
		// Asi se evita que un shipper que ve una incidencia por habersele pedido una respuesta, pueda realizar cambio de estado sobre una incidencia que no es suya.
		if( _user.isUser_type(Constants.OPERATOR) ||
			this.originatorShipperId==null ||
			(this.originatorShipperId!=null && this.originatorShipperId.compareTo(_user.getIdn_user_group()) == 0) ) {
			for(OffSpecStatusBean nextStatus: this.getStatus().getNextStatusSet()){
				if(this.firstUserType.equals(Constants.SHIPPER) || 
						(this.firstUserType.equals(Constants.OPERATOR) && !nextStatus.getStatusCode().equals("EV.ACCEPTED - CLOSED"))) {
					tmpItem = new DefaultMenuItem(nextStatus.getStatusDesc());
					tmpItem.setCommand(commandStringStart + nextStatus.getStatusId() + commandStringEnd);
					tmpModel.addElement(tmpItem);
				}
			}
		}

		return tmpModel;
	}
	
}
