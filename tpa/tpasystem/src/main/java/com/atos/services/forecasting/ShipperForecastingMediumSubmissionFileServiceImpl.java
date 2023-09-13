package com.atos.services.forecasting;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ElementIdBean;
import com.atos.beans.FileBean;
import com.atos.beans.ValidateDeadlineBean;
//import com.atos.beans.ValidateDeadlineBean;
import com.atos.beans.ValidateIntervalBean;
import com.atos.beans.forecasting.ForecastingBean;
import com.atos.beans.forecasting.OperationFileBean;
import com.atos.filters.forecasting.ShipperForecastingSubmissionFileFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.forecasting.ShipperSubmissionForecastingMapper;
import com.atos.utils.Constants;

@Service("shipperForecastingMediumSubmissionFileService")
public class ShipperForecastingMediumSubmissionFileServiceImpl implements ShipperForecastingMediumSubmissionFileService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7472392119963706071L;

	@Autowired
	private ShipperSubmissionForecastingMapper dailyForMapper; 
	
	@Autowired
	private SystemParameterMapper sysMapper;

	@Override
	public BigDecimal selectIdnUserGroup(ShipperForecastingSubmissionFileFilter filter) {
		List<BigDecimal> bd = dailyForMapper.selectIdnUserGroup(filter);
		if(bd.size()==0){
			return null;
		} else {
			return bd.get(0);
		}
	}


	@Override
	public BigDecimal selectOperationCategory() {
		List<BigDecimal> bd = dailyForMapper.selectOperationCategory(Constants.FORECASTING);
		if(bd.size()==0){
			return null;
		} else {
			return bd.get(0);
		}
	}

	@Override
	public BigDecimal selectOperationTerm() {
		List<BigDecimal> bd = dailyForMapper.selectOperationTerm(Constants.MEDIUM);
		if(bd.size()==0){
			return null;
		} else {
			return bd.get(0);
		}
	}	
	@Override
	public BigDecimal selectOperation(TreeMap<String, BigDecimal> map) {
		List<BigDecimal> bd = dailyForMapper.selectOperation(map);
		if(bd.size()==0){
			return null;
		} else {
			return bd.get(0);
		}
	}
	

	private String getForecastingCode(ForecastingBean _bean, String _user) throws Exception {
		
		String tmpForCode = dailyForMapper.selectForecastingCode(_bean);
		
		// Se ha buscado si hay un codigo para un mismo tipo de operacion, shipper e intervalo.
		// Si no se ha encontrado, se genera uno nuevo.
		if(tmpForCode == null){
			
			ElementIdBean tmpBean = new ElementIdBean();
			tmpBean.setGenerationCode(Constants.FORECASTING);
			// Si se deja la fecha a nulo, en BD se toma systemdate.
			tmpBean.setDate(null);
			tmpBean.setUser(_user);
			tmpBean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());

			sysMapper.getElementId(tmpBean);
			
			// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
			if(tmpBean == null || (tmpBean.getIntegerExit() != 0))
				throw new Exception(tmpBean.getErrorDesc());
			
			tmpForCode = tmpBean.getId();
		}
		
		return tmpForCode;
	}
	
	
	@Transactional(rollbackFor = { Throwable.class })
	public String saveFile(ShipperForecastingSubmissionFileFilter filters, FileBean file) throws Exception{
		
		//Recalculates DATE FOR IF THE CHANGED IN BD
		Date interval_start = filters.getStart_date();
		ValidateIntervalBean valForecastingInterval = getValidateForecastingMediumInterval(interval_start);
		    	 
		Date max_end_date = valForecastingInterval.getMax_end_date();
		if (max_end_date!=null){
			filters.setEnd_date(max_end_date);
		} else {
			filters.setEnd_date(null);
		}
		
		
		//We check if the date is inside the forecasting calendar
		ValidateIntervalBean vib = new ValidateIntervalBean();
		vib.setInterval_start(filters.getStart_date());
		vib.setInterval_end(filters.getEnd_date());
		vib.setCategory_code(Constants.FORECASTING);
		vib.setTerm_code(Constants.MEDIUM);
		vib.setUser(filters.getUser());
		vib.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());

		sysMapper.getValidateInterval(vib);
		if(vib.getValid()!=0 || !vib.getError_desc().equals("OK")){
			return "-5";
		}	
		
		// We check if the date is inside the forecasting
		ValidateDeadlineBean vdb = new ValidateDeadlineBean();
		vdb.setReference_date(filters.getStart_date());
		vdb.setCategory_code(Constants.FORECASTING);
		vdb.setUser(filters.getUser());
		vdb.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		vdb.setTerm_code(Constants.MEDIUM);

		// We try to nominate
		vdb.setDeadline_type(Constants.STANDARD_RECEPTION);

		sysMapper.getValidateDeadline(vdb);
		if(vdb.getValid()!=0 || !vdb.getError_desc().equals("OK")){
				return "-6";
		}

		
		OperationFileBean bean = new OperationFileBean();
		bean.setFile_name(file.getFileName());
		bean.setBinary_data(file.getContents());
		bean.setIdn_operation_category(this.selectOperationCategory());	
		bean.setIdn_operation_term(this.selectOperationTerm());
		
		TreeMap<String,BigDecimal> paramMap = new TreeMap<String,BigDecimal>();
		paramMap.put("idn_operation_category", bean.getIdn_operation_category());
		paramMap.put("idn_operation_term", bean.getIdn_operation_term());
		
		// the operation "FORECASTING" + "MEDIUM""
		BigDecimal bd_operation = this.selectOperation(paramMap);
		if(bd_operation==null){
			return "-1";
		}

		ForecastingBean forBean = new ForecastingBean();
		forBean.setIdn_operation(bd_operation);		
		forBean.setStart_date(filters.getStart_date());
		forBean.setEnd_date(filters.getEnd_date());

		String ret = "0";
				
		
		// insertion of tOperation_file
		int ret3 = dailyForMapper.insertOperationFile(bean);
		if(ret3!=1){
			throw new Exception("-2");
		}

		BigDecimal idn_user_group = this.selectIdnUserGroup(filters);
		if(idn_user_group==null){
			throw new Exception("-3");
		} else {
			forBean.setIdn_user_group(idn_user_group);
		}
		
		forBean.setEnd_date(filters.getEnd_date());
		forBean.setIdn_shipper_file(bean.getIdn_operation_file());
		forBean.setIdn_operator_file(null);
		// A partir de idn_operation, idn_user_group, start_date y end_date se intenta obtener el forecasting_code.
		// Si no existe, se genera uno nuevo.
		forBean.setForecasting_code(getForecastingCode(forBean, filters.getUser()));
		forBean.setIdnSystem(filters.getIdnSystem());
		
		int retFor = dailyForMapper.insertNewForecasting(forBean);
		if(retFor!=1){
			throw new Exception("-4");
		}

		return ret;
	}

	
	@Override
	public ValidateIntervalBean getValidateInterval(ValidateIntervalBean bean) {
		List<ValidateIntervalBean> list = sysMapper.getValidateInterval(bean);
		if(list.size()!=0){
			list.get(0);
		} else {
			return null;
		}
		return (ValidateIntervalBean) list.get(0);
	}
	
	//Calculated To Date for the Date From given to file forecasting
	public ValidateIntervalBean getValidateForecastingMediumInterval(Date interval_start) {
		
		ValidateIntervalBean bean = new ValidateIntervalBean();
		bean.setCategory_code(Constants.FORECASTING);
		bean.setTerm_code(Constants.MEDIUM);
		bean.setInterval_start(interval_start);
		bean.setUser((String)SecurityUtils.getSubject().getPrincipal());
		bean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		
		sysMapper.getValidateForecastingInterval(bean);
		return bean;
	}

	
}
