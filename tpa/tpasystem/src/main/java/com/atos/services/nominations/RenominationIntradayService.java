package com.atos.services.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.LanguageBean;
import com.atos.beans.UserBean;
import com.atos.beans.nominations.RenominationIntradayBean;
import com.atos.beans.nominations.RenominationIntradayDialogBean;
import com.atos.beans.nominations.RenominationIntradayDialogDetBean;
import com.atos.beans.scadaAlarms.IdEventBean;
import com.atos.filters.nominations.RenominationIntradayFilter;

public interface RenominationIntradayService extends Serializable{
	
	public List<RenominationIntradayBean> selectRenominationIntraday(RenominationIntradayFilter filter);
	public Map<BigDecimal, Object> selectShipperId(); 
	public Map<String, String> selectStatus(); 
	public IdEventBean getId();
	public String insertHeader(RenominationIntradayDialogBean bean);
	public String insertDetail(RenominationIntradayDialogDetBean bean);
	public int prorate(RenominationIntradayBean renominationIntradayBean, UserBean user, LanguageBean lang) throws Exception;
	public Map<BigDecimal, String> selectHours();
	public String accept(RenominationIntradayBean item);
	public String reject(RenominationIntradayBean item);
	public RenominationIntradayDialogBean getNewReIntraday(RenominationIntradayDialogBean newReIntraday);
	public String save(RenominationIntradayDialogBean bean);
}