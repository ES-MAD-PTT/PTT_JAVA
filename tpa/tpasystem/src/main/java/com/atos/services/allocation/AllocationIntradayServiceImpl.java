package com.atos.services.allocation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.LanguageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.UserBean;
import com.atos.beans.allocation.AllocationIntradayBean;
import com.atos.beans.allocation.AllocationIntradayDetailBean;
import com.atos.beans.allocation.AllocationReportDetailDto;
import com.atos.beans.allocation.AllocationReportDto;
import com.atos.exceptions.ValidationException;
import com.atos.filters.allocation.AllocRepQryFilter;
import com.atos.filters.allocation.AllocationIntradayFilter;
import com.atos.filters.nominations.IntradayNomSummaryFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.allocation.AllocationIntradayMapper;
import com.atos.mapper.allocation.AllocationManagementMapper;
import com.atos.runnable.allocation.AllocationBalanceTask;
import com.atos.runnable.allocation.AllocationIntradayBalanceTask;

@Service("AllocationIntradayService")
public class AllocationIntradayServiceImpl implements AllocationIntradayService {
	
	private static final long serialVersionUID = -2136706972150724501L;
	private static final String strAllocationMaxDateOffset = "ALLOCATION.MAX.DATE.OFFSET";
	private static final String strAllocationReviewMaxPercentChangeOnshore = "ALLOCATION.REVIEW.MAXIMUM.PERCENT.CHANGE.ONSHORE";
	private static final String strAllocationReviewMaxPercentChangeOffshore = "ALLOCATION.REVIEW.MAXIMUM.PERCENT.CHANGE.OFFSHORE";
	private static final String strNotifTypeAllocationReviewManaged = "ALLOCATION.REVIEW.MANAGED";
	private static final String strNotifOrigin = "ALLOCATION";
	private static final String strNotifSeparator = "~";
	
	private static final Logger log = LogManager.getLogger("com.atos.services.allocation.AllocationIntradayServiceImpl");
	
	@Autowired
	private AllocationIntradayMapper allocIntradayMapper;
	
	@Autowired
	private SystemParameterMapper sysParMapper;
	
	@Autowired
	private NotificationMapper notifMapper;	
	
	@Autowired
	@Qualifier("allocationBalanceTaskExecutor")
	private ThreadPoolTaskExecutor allBalTaskExecutor;
	
	@Override
	public Map<BigDecimal, Object> selectShipperId() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = allocIntradayMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public Map<BigDecimal, Object> selectContractId(AllocationIntradayFilter filter) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = allocIntradayMapper.selectContractId(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public Map<BigDecimal, Object> selectPointId(BigDecimal contractId) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = allocIntradayMapper.selectPointId(contractId);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public List<AllocationIntradayBean> search(AllocationIntradayFilter filters) {
		return allocIntradayMapper.search(filters);
	}

	@Override
	public List<AllocationIntradayDetailBean> searchDetail(AllocationIntradayFilter filters) {
		return allocIntradayMapper.searchDetail(filters);
	}
	
	public Map<String, List<AllocationIntradayDetailBean>> getDetailMap(List<AllocationIntradayDetailBean> detailList) {
		Map<String, List<AllocationIntradayDetailBean>> result = null;
		result = detailList.stream().collect(Collectors
				.groupingBy(d -> d.getIdnAllocation() + "-" + d.getIdnContract() + "-" + d.getIdnContractPoint()));
		return result;

	}
	
	public Integer selectAllocationMaxDateOffset(String userId, String lang) throws Exception {
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(new Date());	// Now
		bean.setParameter_name(strAllocationMaxDateOffset);
		bean.setUser_id(userId);
		bean.setLanguage(lang);
		sysParMapper.getIntegerSystemParameter(bean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(bean == null || bean.getInteger_exit()==null)
			throw new Exception("Error getting ALLOCATION.MAX.DATE.OFFSET parameter");
		
		return bean.getInteger_exit();
	}
	
	public Date selectOpenPeriodFirstDay(Map<String, Object> params) {
		return allocIntradayMapper.selectOpenPeriodFirstDay(params);
	}
	
	public void allocationAndBalance(Date _startDate, Date _endDate, UserBean _user, LanguageBean _lang,
			BigDecimal idnSystem) throws Exception {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	
    	try{
        	// Se lanza un thread para seguir con el proceso de forma asincrona/desatendida.
        	// Si se alcanza el numero maximo de threads concurrentes definidos en el metTaskExecutor,
        	// el siguiente thread no se puede lanzar y se genera una org.springframework.core.task.TaskRejectedException
			allBalTaskExecutor.execute(new AllocationIntradayBalanceTask(_startDate, _endDate, _user, _lang, msgs, allocIntradayMapper,
					notifMapper, idnSystem));
        }   
        catch (TaskRejectedException tre) {	// Excepcion para el caso de que no se pueda generar un thread porque se ha alcanzado el maximo numero de threads.
        			// En caso de error, se ha de liberar el bloqueo.
					// En caso de ok, el bloqueo se libera en el thread.
			log.error(tre.getMessage(), tre);
			throw new ValidationException(msgs.getString("all_man_max_processes_reached_error"));
		}        
	}

}
