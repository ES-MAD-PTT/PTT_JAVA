package com.atos.mapper.metering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.LockBean;
import com.atos.beans.OpTemplateBean;
import com.atos.beans.WebserviceLogBean;
import com.atos.beans.metering.MeasureGasQualityParamBean;
import com.atos.beans.metering.MeasurementBean;
import com.atos.beans.metering.MeteringInputBean;
import com.atos.beans.metering.MeteringProcBean;
import com.atos.beans.metering.PointDto;
import com.atos.filters.metering.MeteringManagementFilter;

public interface MeteringManagementMapper extends Serializable{

	public Date selectOpenPeriodFirstDay();
	public Date selectLastOKMeteringInputDate();
	public List<ComboFilterNS> selectZonesFromSystemCode(String systemCode);
	public List<ComboFilterNS> selectAreasFromZoneId(MeteringManagementFilter filter);
	public List<ComboFilterNS> selectMeteringSystemPoints(MeteringManagementFilter filter);
	public List<ComboFilterNS> selectMeteringInputCodes(MeteringManagementFilter filter);
	public List<MeasurementBean> selectMeasurements(MeteringManagementFilter filter);
	public List<MeasureGasQualityParamBean> selectGasQualityParametersFromMeasurementId(BigDecimal measurementId);
	public Integer exclusiveLockRequest(LockBean bean);
	public Integer exclusiveLockRelease(LockBean bean);	
	public int insertRequestWebserviceLog(WebserviceLogBean bean);
	public int updateResponseWebserviceLog(WebserviceLogBean bean);
	public int insertMeteringInput(MeteringInputBean bean);
	public void meteringSave(MeteringProcBean mpb);	
	public List<ComboFilterNS> selectUserGroupByUserId(String userId);
	public List<OpTemplateBean> getOpTemplateByCatTermFiletypeSystem(OpTemplateBean otb);

	public List<PointDto> checkPoints(Date checkDate);
}
