package com.atos.mapper.metering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.metering.MeteringRetrievingBean;
import com.atos.filters.metering.MeteringRetrievingFilter;

public interface MeteringRetrievingMapper extends Serializable{

	public Date selectLastOKMeteringInputDate();
	public List<ComboFilterNS> selectMeteringInputCodes(MeteringRetrievingFilter filter);
	public List <MeteringRetrievingBean> selectMeteringRetrieving(MeteringRetrievingFilter filter);
	public List <MeteringRetrievingBean> selectMeteringRetrievingWarning(MeteringRetrievingFilter filter);
	public MeteringRetrievingBean selectCabMetRetrieving(MeteringRetrievingFilter filter);
	
	
	public List <MeteringRetrievingBean> selectFileXml(MeteringRetrievingFilter filter);
	public List <MeteringRetrievingBean> selectFileXmlbyIdn(BigDecimal idnMeteringInput);
	
}
