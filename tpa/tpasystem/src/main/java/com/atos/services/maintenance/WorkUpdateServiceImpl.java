package com.atos.services.maintenance;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
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
import com.atos.beans.NotificationBean;
import com.atos.beans.maintenance.MaintenanceFileBean;
import com.atos.beans.maintenance.WorkOperatorMaintenanceBean;
import com.atos.beans.maintenance.WorkSubmissionBean;
import com.atos.filters.maintenance.WorkMaintenanceFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.maintenance.MaintenanceMapper;
import com.atos.utils.Constants;

@Service("workUpdateService")
public class WorkUpdateServiceImpl implements WorkUpdateService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6341958341743426473L;
	@Autowired
	private MaintenanceMapper maintenanceMapper;
	
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

	@Override
	public String updateOperationsCommentMaintenance(WorkSubmissionBean bean) throws Exception {
		int ret1 = maintenanceMapper.updateOperationsCommentMaintenance(bean);
		if(ret1!=1){
			throw new Exception("-1");
		}
		return "0";
	}

	@Override
	public String updateCssColorMaintenance(WorkSubmissionBean bean) throws Exception {
		int ret1 = maintenanceMapper.updateCssColorMaintenance(bean);
		if(ret1!=1){
			throw new Exception("-1");
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

	@Transactional( rollbackFor = { Throwable.class })
	public String updateOperationsPublishMaintenance(WorkSubmissionBean bean, BigDecimal _systemId) throws Exception {
		
		bean.setStatus(Constants.PUBLISHED);
		int ret1 = maintenanceMapper.updateStatusMaintenance(bean);
		if(ret1!=1){
			throw new Exception("-1");
		}

		NotificationBean notif = new NotificationBean();
		notif.setType_code("MAINTENANCE.MAINTENANCE_PUBLISHED");
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
	public String updateOperationsMaintenance(WorkOperatorMaintenanceBean bean) throws Exception {
		
		int ret1 = maintenanceMapper.updateOperationsMaintenance(bean);
		if(ret1!=1){
			throw new Exception("-1");
		}
		
		
		return "0";
	}

	@Transactional( rollbackFor = { Throwable.class })
	public String insertOperationsMaintenance(WorkOperatorMaintenanceBean bean, WorkSubmissionBean selected) throws Exception {
		
		bean.setInsert_group(Constants.OPERATIONS);
		int ret1 = maintenanceMapper.insertOperationsMaintenance(bean);
		if(ret1!=1){
			throw new Exception("-1");
		}
		
		return "0";
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
	
	@Override
	public String updateActualEndDateMaintenance(WorkSubmissionBean bean) throws Exception {
		int ret1 = maintenanceMapper.updateActualEndDateMaintenance(bean);
		if(ret1!=1){
			throw new Exception("-1");
		}
		return "0";
	}
	
	
}
