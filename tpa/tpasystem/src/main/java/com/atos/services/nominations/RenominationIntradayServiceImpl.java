package com.atos.services.nominations;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.LanguageBean;
import com.atos.beans.UserBean;
import com.atos.beans.nominations.RenominationIntradayBean;
import com.atos.beans.nominations.RenominationIntradayDialogBean;
import com.atos.beans.nominations.RenominationIntradayDialogDetBean;
import com.atos.beans.nominations.RenominationIntradayProrateBean;
import com.atos.beans.scadaAlarms.IdEventBean;
import com.atos.filters.nominations.RenominationIntradayFilter;
import com.atos.mapper.nominations.RenominationIntradayMapper;
import com.atos.utils.DateUtil;


@Service("renominationIntradayService")
public class RenominationIntradayServiceImpl implements RenominationIntradayService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6008063454154512438L;
		
	@Autowired
	private RenominationIntradayMapper renomIntradayMapper;


	
	@Override
	public List<RenominationIntradayBean> selectRenominationIntraday(RenominationIntradayFilter filter) {
		return renomIntradayMapper.selectRenominationIntradayCab(filter);
	}
	
	@Override
	public Map<BigDecimal, Object> selectShipperId() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = renomIntradayMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	public IdEventBean getId(){
		IdEventBean bean = new IdEventBean();
		bean.setDate(renomIntradayMapper.getSysdate());
		bean.setType("INTRADAY.RENOM");
		renomIntradayMapper.getIdEvent(bean);
		return bean;
	}

	public String insertHeader(RenominationIntradayDialogBean bean) {
		
		// we add 1 hour to change from hours to period. In gui we need to substract 1 from the period to get the hour
		bean.setHour(bean.getHour().add(BigDecimal.valueOf(1)));
		
		int ins1 = renomIntradayMapper.insertHeader(bean);
		if (ins1 != 1) {
			return "-1";
		}
		return "0";
	}
	
	public String insertDetail(RenominationIntradayDialogDetBean bean) {
		int ins1 = renomIntradayMapper.insertDetail(bean);
		if (ins1 != 1) {
			return "-1";
		}
		return "0";
	}
	
	@Override
	public int prorate(RenominationIntradayBean renominationIntradayBean, UserBean user, LanguageBean lang) throws Exception {
		String errorMsg = null;

		RenominationIntradayProrateBean bean = new RenominationIntradayProrateBean();
		bean.setUser(user.getUsername());// ponemos el username para la auditoria
		bean.setLanguage(lang.getLocale());
		bean.setIdn_intraday_renom_cab(renominationIntradayBean.getIdn_intraday_renom_cab());
		bean.setHour(renominationIntradayBean.getHour());
		

		String resProrate = renomIntradayMapper.prorate(bean);

		// El error al tratar de obtener el codigo se trata como error tecnico,
		// que no se va a mostrar al usuario.
		if (bean == null || bean.getErrorCode() == null)
			throw new Exception("Error generating MOC.");

		int res = bean.getErrorCode().intValue();
		if (res != 0) {
			errorMsg = bean.getErrorDesc();
			throw new Exception(errorMsg);			
		}
		return res;

	}

	@Override
	public Map<String, String> selectStatus() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("SUBMITTED", "SUBMITTED");
		map.put("COMPLETE", "COMPLETE");
		map.put("ERROR", "ERROR");
		map.put("REJECTED", "REJECTED");
		
		return map;
	}

	@Override
	public Map<BigDecimal, String> selectHours() {
		Map<BigDecimal, String> map = new LinkedHashMap<BigDecimal, String>();
		Date today = renomIntradayMapper.getSysdate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int hour = cal.get(Calendar.HOUR_OF_DAY) +1;
		if(hour<24) {
			for(int i=hour;i<24;i++) {
				map.put(new BigDecimal(i), (new BigDecimal(i)).toPlainString());
			}
		}
		
		return map;
	}

	@Override
	public String accept(RenominationIntradayBean item) {
		
		item.setStatus("COMPLETE");
		int ins1 = renomIntradayMapper.updateStatus(item);
		if (ins1 != 1) {
			try {
				throw new Exception("-1");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "0";
		
	}

	@Override
	public String reject(RenominationIntradayBean item) {
		
		item.setStatus("REJECTED");
		int ins1 = renomIntradayMapper.updateStatus(item);
		if (ins1 != 1) {
			try {
				throw new Exception("-1");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "0";
	}

	@Override
	public RenominationIntradayDialogBean getNewReIntraday(RenominationIntradayDialogBean newReIntraday) {
		
		List<RenominationIntradayDialogDetBean> list = renomIntradayMapper.selectRenominationIntradayDialog(newReIntraday);
		for(int i=0;i<list.size();i++) {
			newReIntraday.getDetail().add(list.get(i));
		}
		
		return newReIntraday;
		
	}

	@Transactional( rollbackFor = { Throwable.class })
	public String save(RenominationIntradayDialogBean bean) {
		
		if(bean.isAllShippers()) {
			bean.setStrallShippers("Y");
			bean.setShipperId(null);
		} else {
			bean.setStrallShippers("N");
		}

		IdEventBean id = new IdEventBean();
		id = this.getId();
		bean.setIntraday_renom_code(id.getP_id());
		Calendar cal = Calendar.getInstance();
		
		bean.setGas_day(DateUtil.adjustDate(cal.getTime()));

		String resHeader = this.insertHeader(bean);
		if(!resHeader.equals("0")) {
			return "-1";
		}
		
		for(int i=0;i<bean.getDetail().size();i++) {
			RenominationIntradayDialogDetBean det = bean.getDetail().get(i);
			det.setUsername(bean.getUsername());
			det.setIdn_intraday_renom_cab(bean.getIdn_intraday_renom_cab());
			if(det.getUpdated().equals("S")) {
				String resDetail = this.insertDetail(det);
				if(!resDetail.equals("0")) {
					return "-2";
				}
			}
		}
		
		return "0";

	}
	
}