package com.atos.services.nominations;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.FileBean;
import com.atos.beans.ValidateDeadlineBean;
import com.atos.beans.nominations.NominationBean;
import com.atos.beans.nominations.NominationDeadlineBean;
import com.atos.beans.nominations.OperationFileBean;
import com.atos.filters.nominations.ShipperSubmissionFileFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.nominations.ShipperSubmissionNominationsMapper;
import com.atos.utils.Constants;

@Service("shipperDailySubmissionFileService")
public class ShipperDailySubmissionFileServiceImpl implements ShipperDailySubmissionFileService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1474869475323574106L;
	private static final String dayLabel = "DAY";
	private static final String hourLabel = "HH:MI";
		
	@Autowired
	private ShipperSubmissionNominationsMapper dailyNomMapper;
	
	@Autowired
	private SystemParameterMapper sysMapper;

	@Override
	public Map<String, Object> selectContractCodeByUser(ShipperSubmissionFileFilter filter) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
 		List<ComboFilterNS> list = dailyNomMapper.selectContractCodeDayByUser(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey().toString(), combo.getValue());
		}
		return map;
	}

	@Override
	public BigDecimal selectIdnUserGroup(ShipperSubmissionFileFilter filter) {
		List<BigDecimal> bd = dailyNomMapper.selectIdnUserGroup(filter);
		if(bd.size()==0){
			return null;
		} else {
			return bd.get(0);
		}
	}

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
	public NominationBean selectIdnNomination(NominationBean bean) {
		List<NominationBean> list = dailyNomMapper.selectIdnNomination(bean);
		if(list.size()==0){
			return null;
		} else {
			return list.get(0);
		}
	}
	
	@Transactional( rollbackFor = { Throwable.class })
	public String saveFile(ShipperSubmissionFileFilter filters, FileBean file) throws Exception{
		OperationFileBean bean = new OperationFileBean();
		bean.setFile_name(file.getFileName());
		bean.setBinary_data(file.getContents());
		Calendar cal = Calendar.getInstance();
		bean.setVersion_date(cal.getTime());
		bean.setIdn_operation_category(this.selectOperationCategory());
		bean.setIdn_operation_term(this.selectOperationTerm());
		
		TreeMap<String,BigDecimal> paramMap = new TreeMap<String,BigDecimal>();
		paramMap.put("idn_operation_category", bean.getIdn_operation_category());
		paramMap.put("idn_operation_term", bean.getIdn_operation_term());
		
		// the operation "NOMINATION" + "DAILY" does not exists
		BigDecimal bd_operation = this.selectOperation(paramMap);
		if(bd_operation==null){
			return "-1";
		}

		cal.setTime(filters.getStart_date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		NominationBean nomBean = new NominationBean();
		nomBean.setIdn_operation(bd_operation);
		nomBean.setIdn_contract(new BigDecimal(filters.getContract_code()));
		
		nomBean.setStart_date(cal.getTime());
		NominationBean nomination = this.selectIdnNomination(nomBean);
		String ret = "0";

		// We check if the date is inside the nomination o renomination calendar
		ValidateDeadlineBean vdb = new ValidateDeadlineBean();
		vdb.setReference_date(cal.getTime());
		vdb.setCategory_code(Constants.NOMINATION);
		vdb.setUser(filters.getUser());
		vdb.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		vdb.setTerm_code(Constants.DAILY);

		// We try to nominate
		vdb.setDeadline_type(Constants.STANDARD_RECEPTION);

		boolean renomination = false;
		sysMapper.getValidateDeadline(vdb);
		if(vdb.getValid()!=0 || !vdb.getError_desc().equals("OK")){
			// if we get an error, we try to renominate
			vdb.setDeadline_type(Constants.RENOMINATION_RECEPTION);
			sysMapper.getValidateDeadline(vdb);
			if(vdb.getValid()!=0 || !vdb.getError_desc().equals("OK")){
				// if the renomination fails too, we generate an error
				// Se recoge mensaje de error desde funcion PL/SQL para mostrarle al usuario.
				throw new Exception("-6" + ShipperDailySubmissionFileService.errorSeparator + vdb.getError_desc());
			}
			renomination = true;
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
			nomBean.setEnd_date(cal.getTime());
			if(!renomination){
				nomBean.setIs_renomination("N");
			} else {
				nomBean.setIs_renomination("Y");
			}
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
			nomination.setIdn_operator_file(null);
			if(!renomination){
				nomination.setIs_renomination("N");
			} else {
				nomination.setIs_renomination("Y");
			}
			
			int retNom = dailyNomMapper.insertVersionNomination(nomination);
			if(retNom!=1){
				throw new Exception("-5");
			}
			ret = nomination.getNomination_code() + "/" + nomination.getNomination_version();
		}
		
		return ret;
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
		bean = dailyNomMapper.selectNominationDeadlines(bean);
		
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

}
