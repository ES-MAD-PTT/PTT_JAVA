package com.atos.services.booking;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import java.math.BigDecimal;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.NotificationBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.booking.ContractQueryBean;
import com.atos.beans.booking.ContractAttachTypeBean;
import com.atos.beans.booking.ContractAttachmentBean;
import com.atos.beans.booking.ContractRejectedPointBean;
import com.atos.beans.booking.OperationFileBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.booking.CRManagementFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.booking.ContractQueryMapper;
import com.atos.utils.Constants;

import org.apache.shiro.SecurityUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ContractQueryService")
public class ContractQueryServiceImpl implements ContractQueryService{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3387425404695877624L;
	private static final String strNotifTypeCodeToOperator = "CAPACITY.REQUEST.DELETED";
	private static final String strNotifTypeCodeToShipper = "CONTRACT.CAP_REQUEST_MANAGED";
	private static final String strNotifOrigin = "CONTRACT";
	
	@Autowired
	private ContractQueryMapper crMapper;
	@Autowired
	private SystemParameterMapper systemParameterMapper;
	@Autowired
	private NotificationMapper notifMapper;	
	
	
	public Map<BigDecimal, Object> selectShipperId(){
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
			List<ComboFilterNS> list = crMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}


	public Map<BigDecimal, Object> selectContractTypes(){
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
			List<ComboFilterNS> list = crMapper.selectContractTypes();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}	


	public List<ContractQueryBean> search(CRManagementFilter filter){
		
		// Se crea un nuevo filtro, para anadir los % e invocar la consulta.
		CRManagementFilter tmpFilter = new CRManagementFilter(filter);
		String tmpCapReqCode = filter.getCapacityRequestCode();
		
		if((tmpCapReqCode != null) && (!"".equalsIgnoreCase(tmpCapReqCode)))
			tmpFilter.setCapacityRequestCode("%" + filter.getCapacityRequestCode() + "%");
		
		return crMapper.selectContractQueryRequests(tmpFilter);
	}

	public SystemParameterBean getSystemParameter(String str){
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(systemParameterMapper.getSysdate().get(0));
		bean.setParameter_name(str);
		bean.setUser_id((String)SecurityUtils.getSubject().getPrincipal());
		bean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		systemParameterMapper.getIntegerSystemParameter(bean);
		return bean;
	}

	public void getFileByOpFileId(ContractQueryBean cr) throws Exception{
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		BigDecimal tmpIdnOperationFile = cr.getIdnOperationFile();		
		if(tmpIdnOperationFile == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));
		
		List<OperationFileBean> lData = crMapper.getFileByOpFileId(tmpIdnOperationFile);
		if(lData == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));
		
		// Solo se va a tener un fichero por cada capacity request.
		OperationFileBean tmpOfBean = lData.get(0);
		if(tmpOfBean == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));		

		byte[] ba = tmpOfBean.getBinaryData();
		if(ba == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));		
		
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);
		DefaultStreamedContent tmpSCont = 
				new DefaultStreamedContent(bais, 
											"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", 
											cr.getXlsFileName());
		cr.setXlsFile(tmpSCont);
	}



    private void validateRejectTechCapacityRequest(ContractQueryBean _crbSelected) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	if(_crbSelected.getDualListPointCodes().getTarget().isEmpty())
    		throw new ValidationException(msgs.getString("cr_man_rejected_points") + ": " + msgs.getString("empty_field"));
    }

	private void sendNotificationToShipper(ContractQueryBean _crbSelected) throws Exception {
		if(_crbSelected != null)
			sendNotification(strNotifTypeCodeToShipper, 
								_crbSelected.getRequestCode(),
								_crbSelected.getShipperId(),
								_crbSelected.getIdn_system());
	}


	private void sendNotification(String _notifTypeCode, String _info, BigDecimal _userGroupId, BigDecimal _systemId) throws Exception {		
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	int res = 0;
   	
		// Se genera la notificacion para el shipper, correspondiente a que el operador ha procesado su peticion de contrato.
		NotificationBean notif = new NotificationBean();
		notif.setType_code(_notifTypeCode);
		notif.setSystemId(_systemId);
		notif.setOrigin(strNotifOrigin);
		// Para un mensaje como "Capacity request {1} has been managed by operator"
		// se pasa como parametro el id de Capapacity Request, unicamente.
		notif.setInformation(_info);
		notif.setIdn_user_group(_userGroupId);
		notif.setUser_id((String)SecurityUtils.getSubject().getPrincipal());
		notif.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		
		notifMapper.getCreateNotification(notif);
		if( notif==null || notif.getInteger_exit()==null ){
			throw new Exception(msgs.getString("error_sending_notification_to_shipper"));
		}
		else {
			// En caso de error funcional, el procedimiento devuelve un codigo de error mayor o igual a 1000 y 
			// se devuelve una ValidationException (funcional). Esta excepcion se pintara en la ventana de mensajes al usuario.
			// En caso de error tecnico, el procedimiento devuelve un codigo de error menor que 1000 y distinto de cero.
			// se devuelve una Exception normal (error tecnico). En la ventana de mensajes al usuario se muestra un 
			// "error interno" y los detalles se llevan al log.
			res = notif.getInteger_exit().intValue();
			if( res != 0) {
				if( res >= 1000 )	// Errores funcionales.
		    		throw new ValidationException(notif.getError_desc());
				else				// Errores tecnicos.
		    		throw new Exception(notif.getError_desc());
			}				
		}
	}


	public List<ContractAttachTypeBean> selectContractAttachTypes(){
		
		return crMapper.selectContractAttachTypes();
	}


	public void selectAdditionalDocs(ContractQueryBean cr) {
		
		// Cada vez que se consulta desde la vista, se consulta la BD,
		// para que se vaya actualizando la vista, en caso de que se guarden
		// docuementos nuevos, o se borren.
		cr.setAdditionalDocs(crMapper.selectAdditionalDocs(cr.getId()));
	}


	public void selectAdditionalDocsBankGuarantee(ContractQueryBean cr) {
		
		// Cada vez que se consulta desde la vista, se consulta la BD,
		// para que se vaya actualizando la vista, en caso de que se guarden
		// docuementos nuevos, o se borren.
		cr.setAdditionalDocs(crMapper.selectAdditionalDocsBankGuarantee(cr.getId()));
	}


	
	private void validateInsertContractAttachment(ContractQueryBean _selectedCapReq, ContractAttachmentBean _newDoc) 
		throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
			
		// 1.- Se ha de comprobar que con el nuevo documento, no se supera el numero
		// maximo de documentos de su tipo.
		BigDecimal bdTipoDocNewDoc = _newDoc.getContractAttachTypeId();
		int iMaxNumberTipoDocNewDoc = 0;
		List<ContractAttachTypeBean> lDocTypes = crMapper.selectContractAttachTypes();
		if( lDocTypes != null ) {
			for(int i=0; i<lDocTypes.size(); i++) {
				if(lDocTypes.get(i) != null &&
					lDocTypes.get(i).getContractAttachTypeId() != null) {
	
					if(lDocTypes.get(i).getContractAttachTypeId().compareTo(bdTipoDocNewDoc) == 0) {
						if(lDocTypes.get(i).getMaxNumber() != null)
							iMaxNumberTipoDocNewDoc = lDocTypes.get(i).getMaxNumber().intValue();
					}

				} else {
					throw new Exception(msgs.getString("cr_man_doc_type_list_not_available"));
				}

			}
		} else {
			throw new Exception(msgs.getString("cr_man_doc_type_list_not_available"));
		}


		int iCurrentNumDocsByDocTypeInCapReq = 0;
		// Se compara con los documentos que ya tiene la CapacityRequest.
		// Si la Capacity Request no tuviera documentos adicionales, no pasa nada.
		if(  _selectedCapReq != null && 
				_selectedCapReq.getAdditionalDocs()	!= null ) {
			
			for(int j=0; j<_selectedCapReq.getAdditionalDocs().size(); j++) {
				if(	_selectedCapReq.getAdditionalDocs().get(j) != null &&
						_selectedCapReq.getAdditionalDocs().get(j).getContractAttachTypeId() != null ) {
	
					if( _selectedCapReq.getAdditionalDocs().get(j).getContractAttachTypeId().compareTo(bdTipoDocNewDoc) == 0 ) {
						iCurrentNumDocsByDocTypeInCapReq++;
					}
				}
			}
		}
		
		// Si el numero de documentos (del tipo que se quiere anadir), antes de anadir, ya es igual al maximo por tipo, se devuelve una excepcion y
		// no se deja anadir.
		if(iCurrentNumDocsByDocTypeInCapReq >= iMaxNumberTipoDocNewDoc)
			throw new ValidationException(msgs.getString("cr_man_max_doc_type_number_reached"));		
	}
	

	// Para obtener identificadores de punto a partir de su codigo.
	// Hay una relacion 1 a 1 entre codigos e identificadores.
	private static Object getKeyFromValue(Map hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}

	
}
