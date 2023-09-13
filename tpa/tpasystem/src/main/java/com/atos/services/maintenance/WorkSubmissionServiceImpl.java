package com.atos.services.maintenance;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ElementIdBean;
import com.atos.beans.FileBean;
import com.atos.beans.NotificationBean;
import com.atos.beans.maintenance.MaintenanceFileBean;
import com.atos.beans.maintenance.WorkOperatorMaintenanceBean;
import com.atos.beans.maintenance.WorkSubmissionBean;
import com.atos.filters.maintenance.WorkMaintenanceFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.maintenance.MaintenanceMapper;
import com.atos.utils.Constants;

@Service("workSubmissionService")
public class WorkSubmissionServiceImpl implements WorkSubmissionService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6341958341743426473L;
	@Autowired
	private MaintenanceMapper maintenanceMapper;
	
	@Autowired
	private SystemParameterMapper sysParMapper;

	
	@Autowired
	private NotificationMapper notifMapper;
	
	@Override
	public Map<String, Object> getMaintenanceCode() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
 		List<ComboFilterNS> list = maintenanceMapper.getMaintenanceCode();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey().toString(), combo.getValue());
		}
		return map; 
	}

	@Override
	public Map<String, Object> getMaintenanceArea(BigDecimal idnActive) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<ComboFilterNS> list = maintenanceMapper.getMaintenanceArea(idnActive);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey().toString(), combo.getValue());
		}
		return map; 
	}

	@Override
	public Map<String, Object> getMaintenanceSubarea(WorkMaintenanceFilter filter) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
 		List<ComboFilterNS> list = maintenanceMapper.getMaintenanceSubarea(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey().toString(), combo.getValue());
		}
		return map; 
	}

	@Override
	public List<WorkSubmissionBean> getEngineeringMaintenance(WorkMaintenanceFilter filter) {
		return maintenanceMapper.getEngineeringMaintenance(filter);
	}

	@Transactional( rollbackFor = { Throwable.class })
	public String insertEngineeringMaintenance(WorkSubmissionBean bean, FileBean file, BigDecimal _systemId) throws Exception {

		MaintenanceFileBean fileBean = new MaintenanceFileBean();
		if(file!=null){
			fileBean.setContent_type(file.getContentType());
			fileBean.setFile_name(file.getFileName());
			fileBean.setBinary_data(file.getContents());
			
			// insertion of tOperation_file
			int ret3 = maintenanceMapper.insertMaintenanceFile(fileBean);
			if(ret3!=1){
				throw new Exception("-3");
			}
		}
		
		//campo modificado por la generacion id
		//bean.setMaintenance_code(getMaintenanceCodeSequential());
		  bean.setMaintenance_code(getNewMaintenanceCodeSequential());
			
		bean.setStatus(Constants.SUBMITTED);
		bean.setIdn_maintenance_file(fileBean.getIdn_maintenance_file());
		int ret1 = maintenanceMapper.insertEngineeringMaintenance(bean);
		if(ret1!=1){
			throw new Exception("-1");
		}
		
		WorkOperatorMaintenanceBean b = new WorkOperatorMaintenanceBean();
		b.setIdn_maintenance(bean.getIdn_maintenance());
		b.setIdn_subarea(bean.getIdn_subarea());
		b.setInsert_group(Constants.ENGINEERING);
		b.setCapacity_affected(null);
		
		int ret2 = maintenanceMapper.insertOperationsMaintenance(b);
		if(ret2!=1){
			throw new Exception("-2");
		}
		
		NotificationBean notif = new NotificationBean();
		notif.setType_code("MAINTENANCE.MAINTENANCE_CREATED");
		notif.setSystemId(_systemId);
		notif.setOrigin("MAINTENANCE");
		notif.setInformation(bean.getMaintenance_code() +"~");
		// Cuando user_group es null, la notificacion va a todos los usuarios que tengan permiso para recibirlo. 
		// Se tiene en cuenta tambien que el usuario tenga un rol en el sistema onshore/offshore igual a la pantalla desde la que se lanza la notificacion.
		notif.setIdn_user_group(null);
		notif.setUser_id((String)SecurityUtils.getSubject().getPrincipal());
		notif.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		
		notifMapper.getCreateNotification(notif);
		if(notif==null || notif.getInteger_exit()==null || notif.getInteger_exit().intValue()!=0){
			return notif.getError_desc();
		}
		
		return "0";
	}
	
	@Transactional( rollbackFor = { Throwable.class })
	public String updateEngineeringMaintenance(WorkSubmissionBean bean, FileBean file) throws Exception {
		MaintenanceFileBean fileBean = new MaintenanceFileBean();
		if(file!=null){
			fileBean.setContent_type(file.getContentType());
			fileBean.setFile_name(file.getFileName());
			fileBean.setBinary_data(file.getContents());
			
			// insertion of tOperation_file
			int ret3 = maintenanceMapper.insertMaintenanceFile(fileBean);
			if(ret3!=1){
				throw new Exception("-3");
			}
			bean.setIdn_maintenance_file(fileBean.getIdn_maintenance_file());
		}
		
		int ret1 = maintenanceMapper.updateEngineeringMaintenance1(bean);
		if(ret1!=1){
			throw new Exception("-1");
		}
		
		WorkOperatorMaintenanceBean b = new WorkOperatorMaintenanceBean();
		b.setIdn_maintenance(bean.getIdn_maintenance());
		b.setIdn_subarea(bean.getIdn_subarea());
		b.setInsert_group(Constants.ENGINEERING);
		b.setCapacity_affected(null);
		
		int ret2 = maintenanceMapper.updateEngineeringMaintenance2(b);
		if(ret2!=1){
			throw new Exception("-2");
		}
		
		return "0";
	}
	
	@Transactional( rollbackFor = { Throwable.class })
	public String deleteEngineeringMaintenance(WorkSubmissionBean bean) throws Exception {
		 
		bean.setStatus(Constants.DELETED);
		int ret1 = maintenanceMapper.updateStatusMaintenance(bean);
		if(ret1!=1){
			throw new Exception("-1");
		}
		
		
		return "0";
	}
	
	private String getMaintenanceCodeSequential(){
		Calendar cal = Calendar.getInstance();
		
		String maintenance_code = cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH)<9 ? "0" + (cal.get(Calendar.MONTH)+1) : ""+(cal.get(Calendar.MONTH)+1))
				+ (cal.get(Calendar.DAY_OF_MONTH)<10 ? "0" + cal.get(Calendar.DAY_OF_MONTH) : ""+cal.get(Calendar.DAY_OF_MONTH)) + ".";
		
		List<String> list_codes = maintenanceMapper.getMaintenanceCodeSecuential(maintenance_code + "%");
		if(list_codes.size()==0){
			maintenance_code = maintenance_code + "001";
		} else {
			String code = list_codes.get(0);
			String subcode = code.substring(9);
			int num = (new Integer(subcode)).intValue() + 1;
			maintenance_code = maintenance_code + String.format("%03d", num);
		}

		return maintenance_code;
	}

	@Override
	public StreamedContent getFile(TreeMap<String, BigDecimal> map) {
		List<MaintenanceFileBean> l = maintenanceMapper.selectGetFile(map);
		if(l.size()==0){
			return null;
		} else {
			MaintenanceFileBean bean = l.get(0);
			ByteArrayInputStream ba = new ByteArrayInputStream(bean.getBinary_data());
			return new DefaultStreamedContent(ba, bean.getContent_type(), bean.getFile_name());
		}
	}
	
private String getNewMaintenanceCodeSequential() throws Exception {
		
		ElementIdBean tmpBean = new ElementIdBean();
		tmpBean.setGenerationCode(Constants.MAINTENANCE);
		// Si se deja la fecha a nulo, en BD se toma systemdate.
		tmpBean.setDate(null);
		tmpBean.setUser((String)SecurityUtils.getSubject().getPrincipal());
		tmpBean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());

		sysParMapper.getElementId(tmpBean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(tmpBean == null || (tmpBean.getIntegerExit() != 0))
			throw new Exception(tmpBean.getErrorDesc());
		
		return tmpBean.getId();
	}
}
