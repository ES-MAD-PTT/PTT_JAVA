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

@Service("forecastingLongIntermediateSubmissionFileService")
public class ForecastingLongIntermediateSubmissionFileServiceImpl implements ForecastingLongIntermediateSubmissionFileService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6357571246342903457L;

	private static final Logger log = LogManager.getLogger(ForecastingLongIntermediateSubmissionFileServiceImpl.class);

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
		List<BigDecimal> bd = dailyNomMapper.selectOperationTerm(Constants.LONG);
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
	
	public HashMap<String,Object> saveFile(FileBean file,String user, BigDecimal systemId) throws Exception{
		
		if(xmlConverter==null){
			xmlConverter = new Xlsx2XmlConverter();
			xmlConverter.setxMapper(xMapper);
			xmlConverter.init("FORECASTING", "LONG", systemId);
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

		// the operation "FORECASTING" + "LONG" does not exists
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
		vfx.setParType(Constants.LONG);
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
