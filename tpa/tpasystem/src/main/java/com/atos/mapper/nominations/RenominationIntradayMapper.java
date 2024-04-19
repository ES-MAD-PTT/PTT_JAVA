package com.atos.mapper.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.RenominationIntradayBean;
import com.atos.beans.nominations.RenominationIntradayDetBean;
import com.atos.beans.nominations.RenominationIntradayDialogBean;
import com.atos.beans.nominations.RenominationIntradayDialogDetBean;
import com.atos.beans.nominations.RenominationIntradayProrateBean;
import com.atos.beans.scadaAlarms.IdEventBean;
import com.atos.filters.nominations.RenominationIntradayFilter;

public interface RenominationIntradayMapper extends Serializable{

	public List<RenominationIntradayBean> selectRenominationIntradayCab(RenominationIntradayFilter filter);
	public List<RenominationIntradayDetBean> selectRenominationIntradayDet(RenominationIntradayFilter filter);
	public List<RenominationIntradayDialogDetBean> selectRenominationIntradayDialog(RenominationIntradayDialogBean bean);
	public List<RenominationIntradayDialogDetBean> selectRenominationIntradayWeeklyDialog(RenominationIntradayDialogBean bean);
	public List<ComboFilterNS> selectShipperId();
	public Date getSysdate();
	public IdEventBean getIdEvent(IdEventBean bean);
	public int insertHeader(RenominationIntradayDialogBean bean);
	public int updateStatus(RenominationIntradayBean bean);
	public int insertDetail(RenominationIntradayDialogDetBean bean);
	public String prorate(RenominationIntradayProrateBean bean);

}