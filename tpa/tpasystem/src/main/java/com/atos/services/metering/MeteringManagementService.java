package com.atos.services.metering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.DefaultStreamedContent;

import com.atos.beans.FileBean;
import com.atos.beans.LanguageBean;
import com.atos.beans.UserBean;
import com.atos.beans.metering.MeasureGasQualityParamBean;
import com.atos.beans.metering.MeasurementBean;
import com.atos.beans.metering.PointDto;
import com.atos.exceptions.ValidationException;
import com.atos.filters.metering.MeteringManagementFilter;
import com.atos.views.ChangeSystemView;

public interface MeteringManagementService extends Serializable {

	public Date selectOpenPeriodFirstDay();
	public Date selectLastOKMeteringInputDate();
	public int getMeteringMaxDateOffset(String userId, String lang) throws Exception;
	public Map<BigDecimal, Object> selectZones(String systemCode);
	public Map<BigDecimal, Object> selectAreas(MeteringManagementFilter filter);
	public Map<BigDecimal, Object> selectMeteringPoints(MeteringManagementFilter filter);
	public Map<BigDecimal, Object> selectMeteringInputCodes(MeteringManagementFilter filter);
	public List<MeasurementBean> search(MeteringManagementFilter filter);
	public List<MeasureGasQualityParamBean> selectGasQualityParams(MeasurementBean mBean);
	public void updateMeasurementsFromWebservice(MeteringManagementFilter filter, UserBean _user, LanguageBean _lang) throws Exception;
	public void updateMeasurementsFromFile(FileBean _file, UserBean _user, LanguageBean _lang, ChangeSystemView _system) throws Exception;
	public DefaultStreamedContent selectTemplateFile(BigDecimal _systemId) throws Exception;

	public List<PointDto> getCheckedPoints(Date checkDate);
	public void updateWebservice(Date _startDate, Date _endDate, UserBean _user, LanguageBean _lang, BigDecimal idnSystem) throws ValidationException;
}
