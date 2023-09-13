package com.atos.services.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.booking.NewConnectionBean;
import com.atos.filters.booking.NewConnectionFilter;

public interface NewConnectionService extends Serializable {

	public Map<BigDecimal, Object> selectShipperId();
	public List<NewConnectionBean> search(NewConnectionFilter filter);
	public void getFileByOpFileId(NewConnectionBean nc) throws Exception;
	public void generatePdfFile(NewConnectionBean nc, BigDecimal _systemId) throws Exception;	
}
