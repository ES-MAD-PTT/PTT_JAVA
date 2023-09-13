package com.atos.services.booking;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.LanguageBean;
import com.atos.beans.OpTemplateBean;
import com.atos.beans.UserBean;
import com.atos.beans.booking.BulletinBoardBean;
import com.atos.beans.booking.BulletinBoardMailBean;
import com.atos.beans.booking.CapacityRequestSubmissionBean;
import com.atos.beans.booking.OperationFileBean;
import com.atos.exceptions.ValidationException;
import com.atos.mapper.booking.BulletinBoardMapper;
import com.atos.mapper.utils.Xlsx2XmlMapper;
import com.atos.utils.Constants;
import com.atos.utils.Xlsx2XmlConverter;
import com.atos.views.ChangeSystemView;

@Service("BulletinBoardService")
@Scope("session")
public class BulletinBoardServiceImpl implements BulletinBoardService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4644726646376825461L;

	// PARAMETROS para el Xlsx2XmlService.
	// Para parsear los ficheros de contratos son necesarios los siguientes offsets.
	// Solo hacen falta 3 posiciones, puesto que los ficheros de contratos solo tienen 3 bloques.
	// Para especificar las lineas que separan la marca de start de bloque y el comienzo de los datos.
	private int[] aiStartRowOffset = {0, 2, 2};
	// Si no existiera marca de fin de bloque, se puede especificar aqui que se limite por offset desde la fila header, en lugar de buscar marca.
	// Valores:
	// -1 indica que no se tenga en cuenta, y que se busque la marca de End.
	// 1  indica que la fila de fin de bloque (no incluida en el bloque) es igual a la siguiente de inicio de bloque (si incluida en el bloque),
	// 		de manera que el bloque solo tendra una linea.
	private int[] aiExitRowOffsetFromHeaderRow = {1, -1, -1};
	// Para especificar las lineas que separan la marca de start de bloque y el comienzo de los datos de HeaderTag.
	private int[] aiHeaderTagRowOffset = {0, 1, 1};

	@Autowired
	private BulletinBoardMapper bbMapper;

	@Autowired
	private Xlsx2XmlMapper xMapper;

	private static final Logger log = LogManager.getLogger("com.atos.services.booking.BulletinBoardServiceImpl");

	// Este metodo se ha de invocar antes de saveFile, en el que se llama al proc.almacenado CAPACITY_REQUEST_SUB.PRO_CAPACITY_REQUEST_SUB.
	public String getShipperIdByUserId(String _userId) {
		String shipperCode = null;
		ComboFilterNS combo = null;
		List<ComboFilterNS> list = bbMapper.selectShipperIdByUserId(_userId);
		
		// Podria ocurrir que el usuario no existiera, o no estuviera asociado a un shipper.
		if( list.size()>0 )
			combo = list.get(0);		// Como mucho, el usuario pertenecera a un solo shipper.
		
		if( combo != null)
			shipperCode = combo.getValue();
		
		return shipperCode;
	}	

	public List<BulletinBoardBean> search(UserBean _user, LanguageBean _lang) {
		BulletinBoardBean bean = new BulletinBoardBean();
		bean.setUser(_user.getUsername());
		bean.setLanguage(_lang.getLocale());
		
		return bbMapper.selectBulletinBoard(bean);
	}

	public DefaultStreamedContent selectTemplateFile( String termCode, ChangeSystemView _system ) throws Exception {
	   	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		OpTemplateBean otbEntrada = new OpTemplateBean();
		otbEntrada.setOpCategoryCode(Constants.CONTRACT);
		otbEntrada.setOpTermCode(termCode);
		otbEntrada.setFileType(Constants.INTERMEDIATE);
		otbEntrada.setSystemId(_system.getIdn_active());
	
		List<OpTemplateBean> lotbSalida = bbMapper.getOpTemplateByCatTermFiletypeSystem(otbEntrada);
		if( lotbSalida.size() == 0) {
    		throw new Exception(msgs.getString("cr_bb_template_file") + " " + msgs.getString("not_found"));
		}
		else {
			// Solo se va a tener un fichero por cada template.
			OpTemplateBean otbXLSTemplate = lotbSalida.get(0);
			byte[] ba = otbXLSTemplate.getBinaryData();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(ba);
			DefaultStreamedContent tmpSCont = 
					new DefaultStreamedContent(bais,
												"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", 
												otbXLSTemplate.getFileName());
			return tmpSCont;
		}
	}

	
	// Este metodo NO sera transaccional. Se guarda el fichero excel, luego el xml y luego se invoca proc.almacenado.
	// Si falla alguno de los casos no hay que hacer rollback.
	public CapacityRequestSubmissionBean saveFile( String _termCode, OperationFileBean _ofbfile, UserBean _user, LanguageBean _lang, ChangeSystemView _system ) throws Exception {

		Xlsx2XmlConverter xmlConverter = null;		
		int res = -1;
		
		// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	// 1.- Se inserta el fichero excel
		_ofbfile.setIdnOperationCategory(bbMapper.selectContractIdnOperationCategory());
		_ofbfile.setIdnOperationTerm(bbMapper.selectOperationTermIdFromCode(_termCode));

		try {
			res = bbMapper.insertOperationfile(_ofbfile);
		}
		catch( Exception e )
		{
	    	log.error(e.getMessage(), e);
    		throw new Exception(msgs.getString("insert_error") + " " + msgs.getString("cr_bb_operation_file")); 
		}		
    	if(res!=1)
    		throw new Exception(msgs.getString("insert_error") + " " + msgs.getString("cr_bb_operation_file"));
    	
		// 2.- Se obtiene el fichero xml
    	// Se carga aqui por primera vez el converter para que no de problemas en el arranque de la aplicacion, en caso
    	// de que hubiera algun error de configuracion del converter en BD.   
    	xmlConverter = getXmlConverter(_termCode, _system.getIdn_active());   	
		String xml = xmlConverter.getXMLFromExcel(_ofbfile.getBinaryData());   	
		log.debug("Generated XML File: " + xml);

		// 3.- Se inserta el fichero xml
		_ofbfile.setXmlData(xml);	
		try {
			res = bbMapper.updateXMLIntoOperationfile(_ofbfile);
		}
		catch( Exception e )
		{
	    	log.error(e.getMessage(), e);
    		throw new Exception(msgs.getString("update_error") + " " + msgs.getString("cr_bb_operation_file")); 
		}		
    	if(res!=1)
    		throw new Exception(msgs.getString("update_error") + " " + msgs.getString("cr_bb_operation_file"));    		

    	
		// 4.- Se invoca al proc.almacenado para hacer la validacion de datos.
		CapacityRequestSubmissionBean crsb = new CapacityRequestSubmissionBean();
		crsb.setUserId(_user.getUsername());
		crsb.setShipperCode(getShipperIdByUserId(_user.getUsername()));
		crsb.setLanguageCode(_lang.getLocale());
		crsb.setSystemId(_system.getIdn_active());
		crsb.setTermCode(_termCode);
		crsb.setOperationFileId(_ofbfile.getIdnOperationFile());
		
		try {
			bbMapper.capacityRequestSubmission(crsb);
		}
		catch( Exception e )
		{
	    	log.error(e.getMessage(), e);
	    	// Si hay un error en la llamada al procedimiento, se toma como error tecnico.
    		throw new Exception(msgs.getString("validating_error") + "."); 
		}

		// El procedimiento va a devolver 0 en caso de OK o warning. En caso de warning, se devuelve
		// un string distinto de null.
		// En caso de error funcional, el procedimiento devuelve un codigo de error mayor o igual a 1000 y 
		// se devuelve una ValidationException (funcional). Esta excepcion se pintara en la ventana de mensajes al usuario.
		// En caso de error tecnico, el procedimiento devuelve un codigo de error menor que 1000 y distinto de cero.
		// se devuelve una Exception normal (error tecnico). En la ventana de mensajes al usuario se muestra un 
		// "error interno" y los detalles se llevan al log.
		res = crsb.getErrorCode().intValue();
		if( res != 0) {
			if( res >= 1000 )	// Errores funcionales.
	    		throw new ValidationException(crsb.getErrorDesc());
			else				// Errores tecnicos.
	    		throw new Exception(crsb.getErrorDesc());
		}
		
		return crsb;
	}

	
	private Xlsx2XmlConverter getXmlConverter(String _termCode, BigDecimal _systemId) throws Exception {

		Xlsx2XmlConverter tmpXmlConverter = new Xlsx2XmlConverter();
		tmpXmlConverter.setxMapper(xMapper);
		tmpXmlConverter.init(Constants.CONTRACT, _termCode, _systemId);
    	// Los parametros de abajo solo son necesario para parsear excels de contratos.
		tmpXmlConverter.setAiStartRowOffset(aiStartRowOffset);
		tmpXmlConverter.setAiExitRowOffsetFromHeaderRow(aiExitRowOffsetFromHeaderRow);
		tmpXmlConverter.setAiHeaderTagRowOffset(aiHeaderTagRowOffset);
	        	
		return tmpXmlConverter;		
	}	
	
	public BulletinBoardMailBean getInfoMailBulletingBoard (BigDecimal idn_contract_request) {
		return bbMapper.getInfoMailBulletingBoard(idn_contract_request);
	}
}
