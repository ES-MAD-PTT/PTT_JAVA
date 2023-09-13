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

@Service("weeklyIntermediateSubmissionFileService")
public class WeeklyIntermediateSubmissionFileServiceImpl implements WeeklyIntermediateSubmissionFileService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7519246586679794246L;
	private static final Logger log = LogManager.getLogger(WeeklyIntermediateSubmissionFileServiceImpl.class);
	private static final String dayLabel = "DAY";
	private static final String hourLabel = "HH:MI";
	private int dayStartWeek = -1;

	@Autowired
	private ShipperSubmissionNominationsMapper weeklyNomMapper;
	
	@Autowired
	private IntermediateShipperSubmissionNominationFileMapper intMapper;
	
	@Autowired
	private Xlsx2XmlMapper xMapper;
	
	private Xlsx2XmlConverter xmlConverter = null;
	
	@Override
	public BigDecimal selectOperationCategory() {
		List<BigDecimal> bd = weeklyNomMapper.selectOperationCategory(Constants.NOMINATION);
		if(bd.size()==0){
			return null;
		} else {
			return bd.get(0);
		}
	}

	@Override
	public BigDecimal selectOperationTerm() {
		List<BigDecimal> bd = weeklyNomMapper.selectOperationTerm(Constants.WEEKLY);
		if(bd.size()==0){
			return null;
		} else {
			return bd.get(0);
		}
	}	
	@Override
	public BigDecimal selectOperation(TreeMap<String, BigDecimal> map) {
		List<BigDecimal> bd = weeklyNomMapper.selectOperation(map);
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
			xmlConverter.init("NOMINATION", "WEEKLY", systemId);
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
    		throw new Exception("The operation NOMINATION + WEEKLY does not exist.");
		}

		// insertion of tOperation_file
		int ret3 = weeklyNomMapper.insertOperationFile(bean);
		if(ret3!=1){
			throw new Exception("Failed when trying to insert the file in operation file.");
		}
		if(!xml_generated){
			throw new Exception("The file format is incorrect. Use a Weekly Nominations File Template.");
		}
		
		ProcessIntermediateBean inter = new ProcessIntermediateBean();
		inter.setTerm_code(Constants.WEEKLY);
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
	

	// Segun los valores de _operType:
	// STANDARD.RECEPTION - Consulta de nominacion
	// RENOMINATION.RECEPTION - Consulta de renominacion
	public String selectOperationSubmissionDeadlinePhrase(String _operType){
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String res = null;
    	
		NominationDeadlineBean bean = selectOperationSubmissionDeadline(_operType);
		if(Constants.STANDARD_RECEPTION.equalsIgnoreCase(_operType))
			res = msgs.getString("weekly_nomination_submission_deadline");
		else if(Constants.RENOMINATION_RECEPTION.equalsIgnoreCase(_operType))
			res = msgs.getString("weekly_renomination_submission_deadline");
		else
			res = "Invalid operation.";
		
		res = res.replace(dayLabel, getDayDeadline(bean));
		res = res.replace(hourLabel, bean.getsHour());
		return res;
	}

	private NominationDeadlineBean selectOperationSubmissionDeadline(String _operType) {
		
		NominationDeadlineBean bean = new NominationDeadlineBean();
		bean.setTermCode(Constants.WEEKLY);
		bean.setDeadlineTypeCode(_operType);
		bean = intMapper.selectNominationDeadlines(bean);
		
		return bean;
	}

	private int selectStartDayOfWeek() {

		// A la vez que se hace la consulta para la vista, se guarda el valor
		// en el servicio. Solo se consulta 1 vez.
		if(dayStartWeek == -1) {
			List<String> list = intMapper.selectStartDayOfWeek();
			if(list.size()==0){
				dayStartWeek = 0;
			} else {
				String day = list.get(0);
				if(day.equals(Constants.SUN)){
					dayStartWeek = Constants.SUNDAY;
				} else if(day.equals(Constants.MON)){
					dayStartWeek = Constants.MONDAY;
				} else if(day.equals(Constants.TUE)){
					dayStartWeek = Constants.TUESDAY;
				} else if(day.equals(Constants.WED)){
					dayStartWeek = Constants.WEDNESDAY;
				} else if(day.equals(Constants.THU)){
					dayStartWeek = Constants.THURSDAY;
				} else if(day.equals(Constants.FRI)){
					dayStartWeek = Constants.FRIDAY;
				} else if(day.equals(Constants.SAT)){
					dayStartWeek = Constants.SATURDAY;
				} else {
					dayStartWeek = Constants.SUNDAY;
				}
			}
		}

		return dayStartWeek;
	}

	private String getDayDeadline(NominationDeadlineBean _deadline) {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		int iDayDeadline = selectStartDayOfWeek() - _deadline.getDaysBefore();
		// Si al calcular el dia de deadline sale un numero negativo, lo regularizo a positivo.
		if(iDayDeadline < 0)
			iDayDeadline = iDayDeadline + 7;
		
        String strDayDeadline;
        switch (iDayDeadline) {
	        case Constants.SUNDAY:  strDayDeadline = msgs.getString("nom_day_1");
	        						break;
	        case Constants.MONDAY:  strDayDeadline = msgs.getString("nom_day_2");
									break;
	        case Constants.TUESDAY:  strDayDeadline = msgs.getString("nom_day_3");
									break;
	        case Constants.WEDNESDAY:  strDayDeadline = msgs.getString("nom_day_4");
									break;
	        case Constants.THURSDAY:  strDayDeadline = msgs.getString("nom_day_5");
									break;
	        case Constants.FRIDAY:  strDayDeadline = msgs.getString("nom_day_6");
									break;
	        case Constants.SATURDAY:  strDayDeadline = msgs.getString("nom_day_7");
									break;
            default: strDayDeadline = "Invalid day.";
            						break;
        }
        
        return strDayDeadline;
	}
	
	public IntermediateNomFileMailBean getInfoMailSubNomBean(BigDecimal idn_nomination) {
		return intMapper.getInfoMailSubNomBean(idn_nomination);
	}
}
