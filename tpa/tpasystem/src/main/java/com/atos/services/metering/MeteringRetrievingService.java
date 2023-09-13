package com.atos.services.metering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.StreamedContent;

import com.atos.beans.metering.MeteringRetrievingBean;
import com.atos.filters.metering.MeteringRetrievingFilter;

public interface MeteringRetrievingService extends Serializable {

	
	public Date selectLastOKMeteringInputDate();
	public Map<BigDecimal, Object> selectMeteringInputCodes(MeteringRetrievingFilter filter);
	public List<MeteringRetrievingBean> selectMeteringRetrieving(MeteringRetrievingFilter filter);
	public List<MeteringRetrievingBean> selectMeteringRetrievingWarning(MeteringRetrievingFilter filter);
	public MeteringRetrievingBean selectCabMetRetrieving(MeteringRetrievingFilter filter);
	
	public StreamedContent getFile(MeteringRetrievingFilter filter);
	public StreamedContent getFile(BigDecimal idnMeteringInput);
	
}
