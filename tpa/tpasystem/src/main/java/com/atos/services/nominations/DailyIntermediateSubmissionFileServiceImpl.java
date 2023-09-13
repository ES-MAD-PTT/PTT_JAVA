package com.atos.services.nominations;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.FileBean;
import com.atos.beans.nominations.IntermediateNomFileMailBean;
import com.atos.beans.nominations.NominationDeadlineBean;
import com.atos.beans.nominations.OperationFileBean;
import com.atos.beans.nominations.OperationTemplateBean;
import com.atos.beans.nominations.ProcessIntermediateBean;
import com.atos.exceptions.ValidationException;
import com.atos.mapper.nominations.IntermediateShipperSubmissionNominationFileMapper;
import com.atos.mapper.nominations.ShipperSubmissionNominationsMapper;
import com.atos.mapper.utils.Xlsx2XmlMapper;
import com.atos.utils.Constants;
import com.atos.utils.Xlsx2XmlConverter;

@Service("dailyIntermediateSubmissionFileService")
public class DailyIntermediateSubmissionFileServiceImpl implements DailyIntermediateSubmissionFileService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7519246586679794246L;
	private static final Logger log = LogManager.getLogger(DailyIntermediateSubmissionFileServiceImpl.class);
	private static final String dayLabel = "DAY";
	private static final String hourLabel = "HH:MI";
	
	@Autowired
	private ShipperSubmissionNominationsMapper dailyNomMapper;
	
	@Autowired
	private IntermediateShipperSubmissionNominationFileMapper intMapper;
	
	@Autowired
	private Xlsx2XmlMapper xMapper;
	
	private Xlsx2XmlConverter xmlConverter = null;
	
	@Override
	public BigDecimal selectOperationCategory() {
		List<BigDecimal> bd = dailyNomMapper.selectOperationCategory(Constants.NOMINATION);
		if(bd.size()==0){
			return null;
		} else {
			return bd.get(0);
		}
	}

	@Override
	public BigDecimal selectOperationTerm() {
		List<BigDecimal> bd = dailyNomMapper.selectOperationTerm(Constants.DAILY);
		if(bd.size()==0){
			return null;
		} else {
			return bd.get(0);
		}
	}	
	@Override
	public BigDecimal selectOperation(TreeMap<String, BigDecimal> map) {
		List<BigDecimal> bd = dailyNomMapper.selectOperation(map);
		if(bd.size()==0){
			return null;
		} else {
			return bd.get(0);
		}
	}
	
	@Override
	public HashMap<String,Object> saveFile(FileBean file, String shipperComments, BigDecimal systemId) throws Exception{
		
		if(xmlConverter==null){
			xmlConverter = new Xlsx2XmlConverter();
			xmlConverter.setxMapper(xMapper);
			xmlConverter.init("NOMINATION", "DAILY", systemId);
    	}       	
		boolean xml_generated = true;
		String xml_data = null;
		try {
			xml_data = xmlConverter.getXMLFromExcel(file.getContents());
		} catch (Exception e){
			xml_generated = false;
			log.catching(e);
		}

		
		OperationFileBean bean = new OperationFileBean();
		bean.setFile_name(file.getFileName());
		bean.setBinary_data(file.getContents());
		bean.setXml_data(xml_data);
		Calendar cal = Calendar.getInstance();
		bean.setVersion_date(cal.getTime());
		bean.setIdn_operation_category(this.selectOperationCategory());
		bean.setIdn_operation_term(this.selectOperationTerm());
		
		TreeMap<String,BigDecimal> paramMap = new TreeMap<String,BigDecimal>();
		paramMap.put("idn_operation_category", bean.getIdn_operation_category());
		paramMap.put("idn_operation_term", bean.getIdn_operation_term());
		
		HashMap<String,Object>  map = new HashMap<String,Object>();
		
		// the operation "NOMINATION" + "DAILY" does not exists
		BigDecimal bd_operation = this.selectOperation(paramMap);
		if(bd_operation==null){
			// Un error tecnico que se guardara en log. No es necesario guardar en messages.properties, para presentar al usuario.
    		throw new Exception("The operation NOMINATION + DAILY does not exist.");
		}
			
		// insertion of tOperation_file
		int ret3 = dailyNomMapper.insertOperationFile(bean);
		if(ret3!=1){
			throw new Exception("Failed when trying to insert the file in operation file.");
		}
		if(!xml_generated){
			throw new ValidationException("The file format is incorrect. Use a Daily Nominations File Template.");
		}
		
		ProcessIntermediateBean inter = new ProcessIntermediateBean();
		inter.setTerm_code(Constants.DAILY);
		inter.setIdn_operation_file(bean.getIdn_operation_file());
		inter.setSystemId(systemId);
		inter.setShipper_comments(shipperComments);
		inter.setUser_id((String)SecurityUtils.getSubject().getPrincipal());
		inter.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		try {
			intMapper.getProcesssIntermediate(inter);
		}
		catch( Exception e )
		{
	    	log.error(e.getMessage(), e);
	    	// Si hay un error en la llamada al procedimiento, se toma como error tecnico.
			throw new Exception("Error in getProcesssIntermediate database procedure."); 
		}
	
		// El procedimiento va a devolver 0 en caso de OK o warning. En caso de warning, se devuelve
		// un string distinto de null.
		// En caso de error funcional, el procedimiento devuelve un codigo de error mayor o igual a 1000 y 
		// se devuelve una ValidationException (funcional). Esta excepcion se pintara en la ventana de mensajes al usuario.
		// En caso de error tecnico, el procedimiento devuelve un codigo de error menor que 1000 y distinto de cero.
		// se devuelve una Exception normal (error tecnico). En la ventana de mensajes al usuario se muestra un 
		// "error interno" y los detalles se llevan al log.
		int res = inter.getInteger_exit().intValue();
		if( res != 0) {
			if( res >= 1000 )	// Errores funcionales.
	    		throw new ValidationException(inter.getError_desc());
			else				// Errores tecnicos.
	    		throw new Exception(inter.getError_desc());
		}
		

		// En caso de OK, en el parametro error_desc se va a devolver el codigo y version de nominacion.
		// Ademas, puede ser que se devuelvan warnings.
		String strOutMsg = inter.getError_desc();
		String resWarnings = inter.getWarnings();
		if( resWarnings != null )
			strOutMsg += System.getProperty("line.separator") + 
						resWarnings.replace(";", System.getProperty("line.separator"));
		map.put("exit",strOutMsg);
		map.put("idn", inter.getIdn_nomination());
		return map;
	}

	@Override
	public StreamedContent getFile(BigDecimal _systemId) {
		OperationTemplateBean bean = new OperationTemplateBean();
		bean.setIdn_operation_category(this.selectOperationCategory());
		bean.setIdn_operation_term(this.selectOperationTerm());
		bean.setFile_type(Constants.INTERMEDIATE);
		bean.setSystemId(_systemId);
		
		List<OperationTemplateBean> l = intMapper.selectTemplateFile(bean);
		if(l.size()==0){
			return null;
		} else {
			OperationTemplateBean b = l.get(0);
			ByteArrayInputStream ba = new ByteArrayInputStream(b.getBinary_data());
			return new DefaultStreamedContent(ba, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", b.getFile_name());
		}
	}
	
	@Override
	public boolean existsOpTemplate(BigDecimal _systemId) {
		OperationTemplateBean bean = new OperationTemplateBean();
		bean.setIdn_operation_category(this.selectOperationCategory());
		bean.setIdn_operation_term(this.selectOperationTerm());
		bean.setFile_type(Constants.INTERMEDIATE);
		bean.setSystemId(_systemId);
		
		List<OperationTemplateBean> l = intMapper.selectTemplateInfo(bean);

		return (l.size()!=0);
	}
	
	// Segun los valores de _operType:
	// STANDARD.RECEPTION - Consulta de nominacion
	// RENOMINATION.RECEPTION - Consulta de renominacion
	public String selectOperationSubmissionDeadlinePhrase(String _operType){
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String res = null;
    	
		NominationDeadlineBean bean = selectOperationSubmissionDeadline(_operType);
		if(Constants.STANDARD_RECEPTION.equalsIgnoreCase(_operType))
			res = msgs.getString("daily_nomination_submission_deadline");
		else if(Constants.RENOMINATION_RECEPTION.equalsIgnoreCase(_operType))
			res = msgs.getString("daily_renomination_submission_deadline");
		else
			res = "Invalid operation.";
		
		res = res.replace(dayLabel, getDayDeadline(bean));
		res = res.replace(hourLabel, bean.getsHour());
		return res;
	}

	private NominationDeadlineBean selectOperationSubmissionDeadline(String _operType) {
		
		NominationDeadlineBean bean = new NominationDeadlineBean();
		bean.setTermCode(Constants.DAILY);
		bean.setDeadlineTypeCode(_operType);
		bean = intMapper.selectNominationDeadlines(bean);
		
		return bean;
	}

	private String getDayDeadline(NominationDeadlineBean _deadline) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	String res;
    	
		if(_deadline.getDaysBefore()==1)
			res = msgs.getString("today");
		else {
			res = msgs.getString("tomorrow") + " ";
			res += _deadline.getDaysBefore() + " ";
			res += msgs.getString("days") + ",";
		}
			
		return res;			
	}
	
	public IntermediateNomFileMailBean getInfoMailSubNomBean(BigDecimal idn_nomination) {
		return intMapper.getInfoMailSubNomBean(idn_nomination);
	}
}
