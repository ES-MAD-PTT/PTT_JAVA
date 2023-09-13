package com.atos.services.forecasting;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.FileBean;
import com.atos.beans.forecasting.ForecastingMailBean;
import com.atos.beans.forecasting.OperationFileBean;
import com.atos.beans.forecasting.OperationTemplateBean;
import com.atos.beans.forecasting.ValidateForecastingXmlBean;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.forecasting.IntermediateShipperSubmissionForecastingFileMapper;
import com.atos.mapper.forecasting.ShipperSubmissionForecastingMapper;
import com.atos.mapper.utils.Xlsx2XmlMapper;
import com.atos.utils.Constants;
import com.atos.utils.Xlsx2XmlConverter;

@Service("forecastingShortIntermediateSubmissionFileService")
public class ForecastingShortIntermediateSubmissionFileServiceImpl implements ForecastingShortIntermediateSubmissionFileService{

	
	
	private static final long serialVersionUID = 8814994878744579443L;
	private static final Logger log = LogManager.getLogger(ForecastingShortIntermediateSubmissionFileServiceImpl.class);

	@Autowired
	private ShipperSubmissionForecastingMapper dailyNomMapper;
	
	@Autowired
	private SystemParameterMapper sysMapper;
	
	@Autowired
	private IntermediateShipperSubmissionForecastingFileMapper intMapper;
	
	@Autowired
	private Xlsx2XmlMapper xMapper;
	
	private Xlsx2XmlConverter xmlConverter = null;
	
	@Override
	public BigDecimal selectOperationCategory() {
		List<BigDecimal> bd = dailyNomMapper.selectOperationCategory(Constants.FORECASTING);
		if(bd.size()==0){
			return null;
		} else {
			return bd.get(0);
		}
	}

	@Override
	public BigDecimal selectOperationTerm() {
		List<BigDecimal> bd = dailyNomMapper.selectOperationTerm(Constants.SHORT);
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
	
	
	public HashMap<String,Object> saveFile(FileBean file, String user, BigDecimal systemId) throws Exception{
		
		if(xmlConverter==null){
			xmlConverter = new Xlsx2XmlConverter();
			xmlConverter.setxMapper(xMapper);
			xmlConverter.init("FORECASTING", "SHORT", systemId);
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
		bean.setVersion_date(Calendar.getInstance().getTime());
		bean.setIdn_operation_category(this.selectOperationCategory());
		bean.setIdn_operation_term(this.selectOperationTerm());
		
		TreeMap<String,BigDecimal> paramMap = new TreeMap<String,BigDecimal>();
		paramMap.put("idn_operation_category", bean.getIdn_operation_category());
		paramMap.put("idn_operation_term", bean.getIdn_operation_term());
		
		HashMap<String,Object>  map = new HashMap<String,Object>();
		
		// the operation "FORECASTING" + "SHORT" does not exists
		BigDecimal bd_operation = this.selectOperation(paramMap);
		if(bd_operation==null){
			map.put("exit", "-1");
			map.put("idn", null);
			return map;
		}
		
		// insertion of tOperation_file
		int ret3 = dailyNomMapper.insertOperationFile(bean);
		if(ret3!=1){
			throw new Exception("-2");
		}
		if(!xml_generated){
			map.put("exit", "-3");
			map.put("idn", null);
			return map;
		}
		
		
		// We check if the date is inside the forecasting
		ValidateForecastingXmlBean vfx = new ValidateForecastingXmlBean();
		vfx.setCodOperationFile(bean.getIdn_operation_file());			
		vfx.setParType(Constants.SHORT);
		vfx.setUser(user);
		vfx.setIdnSystem(systemId);
		vfx.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		
		sysMapper.getValidateForecastingXml(vfx);
		
		if(vfx.getValid()!=0 || !vfx.getErrorDesc().equals("OK")){
			
			if (vfx.getValid() < 1000) {
				map.put("exit", "-5");
				map.put("idn", null);
				return map;
			}
			else {
				map.put("exit", "-4" + ": " + vfx.getErrorDesc());
				map.put("idn", null);
				return map;
			}
				
		}
			
		map.put("exit", "OK");
		map.put("idn", vfx.getIdn_forecasting());
		return map;

/*		NominationBean nomBean = new NominationBean();
		nomBean.setIdn_operation(bd_operation);
		nomBean.setIdn_contract(new BigDecimal(filters.getContract_code()));
		nomBean.setStart_date(filters.getStart_date());
		NominationBean nomination = this.selectIdnNomination(nomBean);
		String ret = "0";

		// We check if the date is inside the nomination o renomination calendar
		ValidateDeadlineBean vdb = new ValidateDeadlineBean();
		vdb.setReference_date(filters.getStart_date());
		vdb.setCategory_code(Constants.NOMINATION);
		vdb.setUser(filters.getUser());
		vdb.setTerm_code(Constants.DAILY);
		
		if(nomination==null) {
			vdb.setDeadline_type(Constants.STANDARD_RECEPTION);
		} else{
			vdb.setDeadline_type(Constants.RENOMINATION_RECEPTION);
		}
		sysMapper.getValidateDeadline(vdb);
		if(vdb.getValid()!=0 || !vdb.getError_desc().equals("OK")){
			return "-6";
		}
		
		// insertion of tOperation_file
		int ret3 = dailyNomMapper.insertOperationFile(bean);
		if(ret3!=1){
			throw new Exception("-2");
		}

		// We do not have a previous version, so we need to insert a new nomination with version 1
		if(nomination==null){
			nomBean.setNomination_version(new BigDecimal(1));
			BigDecimal idn_user_group = this.selectIdnUserGroup(filters);
			if(idn_user_group==null){
				throw new Exception("-3");
			} else {
				nomBean.setIdn_user_group(idn_user_group);
			}
			nomBean.setIs_contracted("Y");
			nomBean.setEnd_date(filters.getStart_date());
			nomBean.setIs_renomination("N");
			nomBean.setIs_valid("N");
			nomBean.setIs_responsed("N");
			nomBean.setFeasibility_date(null);
			nomBean.setOperator_comments(null);
			nomBean.setIs_matched("N");
			nomBean.setIdn_shipper_file(bean.getIdn_operation_file());
			nomBean.setIdn_operator_file(null);
			
			int retNom = dailyNomMapper.insertNewNomination(nomBean);
			if(retNom!=1){
				throw new Exception("-4");
			}
			ret = nomBean.getNomination_code() + "/" + nomBean.getNomination_version();
			
		} else {
			// we have a previous version, so we need to insert a new nomination and increment the version
			nomination.setNomination_version(nomination.getNomination_version().add(new BigDecimal(1)));
			nomination.setIdn_shipper_file(bean.getIdn_operation_file());
			nomination.setIs_renomination("Y");
			
			int retNom = dailyNomMapper.insertVersionNomination(nomination);
			if(retNom!=1){
				throw new Exception("-5");
			}
			ret = nomination.getNomination_code() + "/" + nomination.getNomination_version();
		}
		
		return ret;*/
		
	}

	@Override
	public StreamedContent getFile(BigDecimal idnSystem) {
		OperationTemplateBean bean = new OperationTemplateBean();
		bean.setIdn_operation_category(this.selectOperationCategory());
		bean.setIdn_operation_term(this.selectOperationTerm());
		bean.setIdnSystem(idnSystem);
		
		
		List<OperationTemplateBean> l = intMapper.selectTemplateFile(bean);
		if(l.size()==0){
			return null;
		} else {
			OperationTemplateBean b = l.get(0);
			ByteArrayInputStream ba = new ByteArrayInputStream(b.getBinary_data());
			return new DefaultStreamedContent(ba, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", b.getFile_name());
		}
	}
	
	public ForecastingMailBean getInfoShipperSubForecastingBean(BigDecimal idn_forecasting) {
		return dailyNomMapper.getInfoShipperSubForecastingBean(idn_forecasting);
	}
}
