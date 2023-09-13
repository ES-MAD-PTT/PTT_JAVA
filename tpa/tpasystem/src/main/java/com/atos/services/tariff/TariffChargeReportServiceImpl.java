package com.atos.services.tariff;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilter;
import com.atos.beans.ComboFilterNS;
import com.atos.beans.ElementIdBean;
import com.atos.beans.NotificationBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.UserBean;
import com.atos.beans.tariff.TariffChargeDetailBean;
import com.atos.beans.tariff.TariffChargeReportBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.tariff.TariffChargeReportFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.tariff.TariffChargeReportMapper;
import com.atos.utils.Constants;

@Service("tariffChargeReportService")
public class TariffChargeReportServiceImpl implements TariffChargeReportService {

	private static final long serialVersionUID = 5738619896981240370L;
	private static final String strNotifSeparator = "~";
	private static final String strNotifTypeCodeTariffC = "TARIFF.CHARGE.CALCULATION.FINIFHED";

	@Autowired
	private SystemParameterMapper sysParMapper;

	@Autowired
	private NotificationMapper notifMapper;

	@Autowired
	private TariffChargeReportMapper tariffChargeReportMapper;
	private static final Logger log = LogManager.getLogger("com.atos.services.tariff.TariffChargeReportImpl");

	@Autowired
	private SystemParameterMapper systemParameterMapper;

	@Autowired
	@Qualifier("tariffTaskExecutor")
	private ThreadPoolTaskExecutor metTaskExecutor;

	
	
	public List<TariffChargeReportBean> selectTariffChargeReports(TariffChargeReportFilter filter) {
		return tariffChargeReportMapper.selectTariffChargeReports(filter);

	}

	public BigDecimal getIdnLastTarifCharge(TariffChargeReportFilter filter) {
		return tariffChargeReportMapper.getIdnLastTarifCharge(filter);

	}

	public String getCodeLastTarifCharge(TariffChargeReportFilter filter) {
		return tariffChargeReportMapper.getCodeLastTarifCharge(filter);

	}
	
	public BigDecimal getIdFromTariffCharge(String tariff_charge_code) {
		return tariffChargeReportMapper.getIdFromTariffCharge(tariff_charge_code);
	}

	public String getCodeCompareTarifCharge(TariffChargeReportFilter filter) {
		return tariffChargeReportMapper.getCodeCompareTarifCharge(filter);
	}

	public String getComments(TariffChargeReportFilter filter) {
		return tariffChargeReportMapper.getComments(filter);
	}

	public String getOffSpec(TariffChargeReportFilter filter) {
		return tariffChargeReportMapper.getOffSpec(filter);

	}

	public String getInvoiceSent(TariffChargeReportFilter filter) {
		return tariffChargeReportMapper.getInvoiceSent(filter);
	}
	
	public String getShipperGroupID(TariffChargeReportFilter filter) {
		return tariffChargeReportMapper.getShipperGroupID(filter.getIdn_user_group());
	}

	public String getShipperGroupID(TariffChargeReportBean selected) {
		return tariffChargeReportMapper.getShipperGroupID(selected.getIdn_user_group());
	}

	public SystemParameterBean getSystemParameter(String str) {
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(systemParameterMapper.getSysdate().get(0));
		bean.setParameter_name(str);
		bean.setUser_id((String) SecurityUtils.getSubject().getPrincipal());
		bean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		systemParameterMapper.getIntegerSystemParameter(bean);
		return bean;
	}

	@Override
	public Map<BigDecimal, Object> selectShippers(TariffChargeReportFilter filter) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = tariffChargeReportMapper.selectShippersCombo(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectTariffIdComboFiltro(TariffChargeReportFilter filter) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = tariffChargeReportMapper.selectTariffIdComboFiltro(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	public Map<BigDecimal, Object> selectTariffIdComboBac(TariffChargeReportFilter filter) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = tariffChargeReportMapper.selectTariffIdComboBac(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String updateTariffChargeMonth(TariffChargeReportBean tariffChargeReport, TariffChargeReportFilter filter)
			throws Exception {

		/*
		 * Al aprobar un cambio en uno de los details administrables por el
		 * usuario se debe: 
		 * 	• Caso1: "existe una cabecera (TPA_TTARIFF_CHARGE) con datos mensuales (TPA_TTARIFF_CHARGE_MONTH)":
		 *  o Si existe el  detail que aceptamos -> creamos nueva cabecera y arrastramos todo de la version anterior 
		 * o  Si no existe el detail que aceptamos  mantenemos la cabecera y anadimos el detalle nuevo (sin arrastra datos)
		 * 
		 * • Caso2: "no existe una cabecera (TPA_TTARIFF_CHARGE) ni datos mensuales (TPA_TTARIFF_CHARGE_MONTH)":
		 *  o Creamos la cabecera y anadimos el detalle nuevo (sin arrastra datos)
		 */

		String creoVersion = "N";
		// si Amount NO un dato administrable por el usuario lo calculamos
		if (tariffChargeReport.getIs_amount_user_filled().equals("N")) {
			tariffChargeReport.setAmount(tariffChargeReport.getFee().multiply(tariffChargeReport.getQuantity()));
		}

		if (tariffChargeReport.getIdn_tariff_charge() != null) { // si existe cabecera
			if (tariffChargeReport.getIdn_tariff_charge_month() != null) { // si existe detalle

				// > creamos nueva cabecera y ...
				TariffChargeReportBean bean = new TariffChargeReportBean();
				bean.setIdn_user_group(filter.getIdn_user_group());
				bean.setCharge_date(filter.getShortDate());
				bean.setIdn_system(filter.getIdn_system());

				// buscamos el codigo a enererar
				bean.setTariff_charge_code(getNewTariffChargeCode());

				int ins = tariffChargeReportMapper.insertTariffCharge(bean);
				if (ins != 1) {
					throw new Exception("-1");
				}
				creoVersion = "S";

				// recuperar idn_charge_new
				BigDecimal idn_nueva = tariffChargeReportMapper.getIdnNuevaCabecera(tariffChargeReport);
				tariffChargeReport.setIdn_nueva_cabecera(idn_nueva);

				// ... arrastramos todo de la version anterior
				int ins2 = tariffChargeReportMapper.insertSelectTariffChargeMonth(tariffChargeReport);
				if (ins2 < 1) {
					throw new Exception("-2");
					
				}

				// ... arrastramos todo de la version anterior del DIARIO
				int ins3 = tariffChargeReportMapper.insertSelectTariffChargeSource(tariffChargeReport);
				if (ins3 < 1) {
					throw new Exception("-5");
				}

				// update del detalle que estamos tratando
				int up = tariffChargeReportMapper.updateTariffChargeMonth(tariffChargeReport);
				if (up != 1) {
					throw new Exception("-3");
				}
			} else {// Si no existe el detail que aceptamos
					// mantenemos la cabecera y anadimos el detalle nuevo (sin
					// arrastra datos)
				int ins = tariffChargeReportMapper.insertTariffChargeMonth(tariffChargeReport);
				if (ins != 1) {
					throw new Exception("-4");
				}
			}

		} else {// "no existe una cabecera (TPA_TTARIFF_CHARGE) ni datos mensuales (TPA_TTARIFF_CHARGE_MONTH)":
				// Creamos la cabecera y ...
			TariffChargeReportBean bean = new TariffChargeReportBean();
			bean.setIdn_user_group(filter.getIdn_user_group());
			bean.setCharge_date(filter.getShortDate());
			bean.setIdn_system(filter.getIdn_system());

			int ins = tariffChargeReportMapper.insertTariffCharge(bean);
			if (ins != 1) {
				throw new Exception("-1");
			}
			creoVersion = "S";

			// ... anadimos el detalle nuevo (sin arrastra datos)
			int ins2 = tariffChargeReportMapper.insertTariffChargeMonth(tariffChargeReport);
			if (ins2 != 1) {
				throw new Exception("-4");
			}

		}

		if (creoVersion == "S") {
			return "10";
		} else {

			return "0";
		}

	}

	public String ejecBacCalc(TariffChargeReportBean feeData) throws Exception {

		feeData.setUser((String) SecurityUtils.getSubject().getPrincipal());
		feeData.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		tariffChargeReportMapper.ejecBacCalc(feeData);

		int res = feeData.getError_code();
		if (res != 0) {
			if (res >= 1000) // Errores funcionales.
				throw new ValidationException(feeData.getError_desc());
			else // Errores tecnicos.
				throw new Exception(feeData.getError_desc());
		}

		return "0";

	}
	
	@Override
	public String updateNewVersion(TariffChargeReportBean bean) throws Exception {
		int upd1 = tariffChargeReportMapper.updateNewVersion(bean);
		if (upd1 == 0) {
			throw new Exception("-1");
		}
		return "0";
	}
	
	@Override
	public String getNewVersion(TariffChargeReportBean feeData) throws Exception {

		feeData.setUser((String) SecurityUtils.getSubject().getPrincipal());
		feeData.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		tariffChargeReportMapper.getNewVersion(feeData);

		String res = feeData.getError_desc();
		if (!res.equals("OK")) {
			throw new Exception(feeData.getError_desc());
		}
		return "0";
	}

	@Override
	public void ejecRunTariff(TariffChargeReportBean feeData, UserBean user) throws Exception {

		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		feeData.setUser((String) SecurityUtils.getSubject().getPrincipal());
		feeData.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());

		/*
		 * Se lanza un thread para seguir con el proceso de forma asincrona/desatendida. 
		 * Si se alcanza el numero maximo de threads concurrentes definidos en el metTaskExecutor, 
		 * el siguiente thread no se puede lanzar y se genera una
		 * org.springframework.core.task.TaskRejectedException
		 * metTaskExecutor.execute(new UpdateMeasurementsTask(filter, userId,
		 * lang));
		 */

		try {
			metTaskExecutor.execute(new RunTariffTask(feeData, msgs, user));
		} catch (TaskRejectedException tre) { // Excepcion para el caso de que
												// no se pueda generar un thread
												// porque se ha alcanzado el
												// maximo numero de threads.

			log.error(tre.getMessage(), tre);
			throw new ValidationException(msgs.getString("met_man_max_processes_reached_error"));
		} catch (Exception e) {
			throw e;
		}

	}

	/*
	 * Se define la tarea/thread como clase interna para tener acceso a las
	 * variables de la clase principal.
	 */
	private class RunTariffTask implements Runnable {
		private TariffChargeReportBean feeData;
		private UserBean user;

		public RunTariffTask(TariffChargeReportBean feeData, ResourceBundle msgs, UserBean user) {
			this.feeData = feeData;
			this.user = user;
		}

		public void run() {

			SimpleDateFormat sdf = new SimpleDateFormat("MMMM/yyyy");
			String idShipper = getShipperGroupID(this.feeData);

			String notifInfo = idShipper + strNotifSeparator
					+ sdf.format(this.feeData.getRunTariffDate());

			try {
				log.debug("[" + notifInfo + "] - RunTariffTask - run() start.");

				// llamamos al procedimiento de BD
				tariffChargeReportMapper.ejecRunTariff(feeData);

				int res = feeData.getError_code();

				if (feeData.getError_code() != 0) {
					notifInfo += strNotifSeparator + "has failed";
					if (res >= 1000) // Errores funcionales.
						sendNotification(strNotifTypeCodeTariffC, notifInfo, this.feeData, this.user);
					else// Errores tecnicos.
						sendNotification(strNotifTypeCodeTariffC, notifInfo + "Error Desc: " + feeData.getError_desc(),
								this.feeData, this.user);
				}

				// Se envia notificacion al usuario.
				notifInfo += strNotifSeparator + "has finished OK";
				sendNotification(strNotifTypeCodeTariffC, notifInfo, this.feeData, this.user);
				log.debug("[" + notifInfo + "] - sendNotification() OK finished.");

			} catch (Exception e) {

				log.error(e.getMessage(), e);
				// 5.- Se envia notificacion al usuario.
				try {
					notifInfo += strNotifSeparator + "has failed";
					sendNotification(strNotifTypeCodeTariffC, notifInfo, this.feeData, this.user);
					log.debug("[" + notifInfo + "] - sendNotification() Error finished.");
				} catch (Exception e2) {
					log.error(e2.getMessage(), e2);
				}

			} // catch (Exception e){

		}
	}

	private void sendNotification(String _notifTypeCode, String _info, TariffChargeReportBean feeData, UserBean user)
			throws Exception {
		int res = 0;
		
		String pipeline_code = feeData.getPipeline_system_code();
		
		NotificationBean notif = new NotificationBean();

		notif.setType_code(_notifTypeCode);
		notif.setOrigin("TARIFF");
		notif.setInformation(_info+strNotifSeparator+pipeline_code); //offshore... sacamos el pipeline
		notif.setUser_id(feeData.getUser());
		notif.setLanguage(feeData.getLanguage());
		notif.setIdn_user_group(user.getIdn_user_group());
		notif.setSystemId(feeData.getIdn_system());

		notifMapper.getCreateNotification(notif);
		if (notif == null || notif.getInteger_exit() == null) {
			throw new Exception("Error sending notification to shipper.");
		} else {
			// En caso de error funcional, el procedimiento devuelve un codigo
			// de error mayor o igual a 1000 y
			// se devuelve una ValidationException (funcional). Esta excepcion
			// se pintara en la ventana de mensajes al usuario.
			// En caso de error tecnico, el procedimiento devuelve un codigo de
			// error menor que 1000 y distinto de cero.
			// se devuelve una Exception normal (error tecnico). En la ventana
			// de mensajes al usuario se muestra un
			// "error interno" y los detalles se llevan al log.
			res = notif.getInteger_exit().intValue();
			if (res != 0) {
				if (res >= 1000) // Errores funcionales.
					throw new ValidationException(notif.getError_desc());
				else // Errores tecnicos.
					throw new Exception(notif.getError_desc());
			}
		}
	}

	public List<TariffChargeDetailBean> selectDetailCapacityCharge(TariffChargeReportBean item) {
		return tariffChargeReportMapper.selectDetailCapacityCharge(item);

	}

	//ch715
	@Override
	public List<TariffChargeDetailBean> selectDetailComodityChargeEntry(TariffChargeReportBean item) {
		return tariffChargeReportMapper.selectDetailComodityChargeEntry(item);
	}
	//ch715
	@Override
	public List<TariffChargeDetailBean> selectDetailComodityChargeExit(TariffChargeReportBean item) {
		return tariffChargeReportMapper.selectDetailComodityChargeExit(item);
	}

	
	@Override
	public List<TariffChargeDetailBean> selectDetailOverUsageChargeEntry(TariffChargeReportBean item) {
		return tariffChargeReportMapper.selectDetailOverUsageChargeEntry(item);
	}

	@Override
	public List<TariffChargeDetailBean> selectDetailOverUsageChargeExit(TariffChargeReportBean item) {
		return tariffChargeReportMapper.selectDetailOverUsageChargeExit(item);
	}

	@Override
	public List<TariffChargeDetailBean> selectDetailImbalancePenalty(TariffChargeReportBean item) {
		return tariffChargeReportMapper.selectDetailImbalancePenalty(item);
	}

	private String getNewTariffChargeCode() throws Exception {

		ElementIdBean tmpBean = new ElementIdBean();
		tmpBean.setGenerationCode(Constants.TARIFF_CHARGE);
		// Si se deja la fecha a nulo, en BD se toma systemdate.
		tmpBean.setDate(null);
		tmpBean.setUser((String) SecurityUtils.getSubject().getPrincipal());
		tmpBean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());

		sysParMapper.getElementId(tmpBean);

		// El error al tratar de obtener el codigo se trata como error tecnico,
		// que no se va a mostrar al usuario.
		if (tmpBean == null || (tmpBean.getIntegerExit() != 0))
			throw new Exception(tmpBean.getErrorDesc());

		return tmpBean.getId();
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String invoiceSentUpdate(TariffChargeReportFilter filter) throws Exception {
		int upd1 = tariffChargeReportMapper.invoiceSentUpdate(filter);
		if (upd1 == 0) {
			throw new Exception("-1");
		}
		return "0";
	}
	
	@Transactional(rollbackFor = { Throwable.class })
	public String invoiceSentFalseUpdate(TariffChargeReportFilter filter) throws Exception {
		int upd1 = tariffChargeReportMapper.invoiceSentFalseUpdate(filter);
		if (upd1 < 0) {
			throw new Exception("-1");
		}
		return "0";
	}

	@Override
	public BigDecimal getRepartosShipperDay(TariffChargeReportBean newRunTariff) {
		return tariffChargeReportMapper.getRepartosShipperDay(newRunTariff);

	}

	@Override
	public BigDecimal renderBacCal(TariffChargeReportFilter filter) {

		return tariffChargeReportMapper.renderBacCal(filter);
		
	}

	@Override
	public String updateTariffCharge(TariffChargeReportFilter filter) throws Exception {
		int upd1 = tariffChargeReportMapper.updateTariffCharge(filter);
		if (upd1 == 0) {
			throw new Exception("-1");
		}
		return "0";
	}

	@Override
	public String getCodeTarifCharge(TariffChargeReportFilter filter) {
		return tariffChargeReportMapper.getCodeTarifCharge(filter);
	}

	//CH721
	public BigDecimal getBacAcmount(TariffChargeReportFilter filter){
		return tariffChargeReportMapper.getBacAcmount(filter);
	}
	
	@Override
	public Map<String, Object> selectTypeCriteria() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<ComboFilter> list = tariffChargeReportMapper.selectTypeCriteria();
		for (ComboFilter combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public Map<String, Object> selectModeCriteria() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<ComboFilter> list = tariffChargeReportMapper.selectModeCriteria();
		for (ComboFilter combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Transactional(rollbackFor = { Throwable.class })
	public int insertTypeSelected(HashMap<String,Object> map) {
		
		int ins = tariffChargeReportMapper.insertTypeSelected(map);
		if (ins != 1) {
			return -1;
		} else {
			return 0;
		}
	}
	
	@Transactional(rollbackFor = { Throwable.class })
	public int insertModeSelected(HashMap<String,Object> map) {
		
		int ins = tariffChargeReportMapper.insertModeSelected(map);
		if (ins != 1) {
			return -1;
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean compare(BigDecimal idn_tariff_charge) {
		if(tariffChargeReportMapper.compare(idn_tariff_charge).isEmpty())
			return false;
		return true;
	}
    
	@Override
	public boolean compare2(BigDecimal idn_tariff_charge_compare) {
		if(tariffChargeReportMapper.compare2(idn_tariff_charge_compare).isEmpty())
			return false;
		return true;
	}
	
}
