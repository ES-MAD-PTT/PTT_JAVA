package com.atos.services.nominations;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.primefaces.model.StreamedContent;

import com.atos.beans.nominations.IntermediateNomFileMailBean;
import com.atos.beans.nominations.NominationDetailBean;
import com.atos.beans.nominations.NominationDetailWeekBean;
import com.atos.beans.nominations.NominationHeaderBean;
import com.atos.beans.nominations.NominationQualityGasDayBean;
import com.atos.beans.nominations.NominationQualityGasWeekBean;
import com.atos.beans.nominations.QualityTableBean;
import com.atos.filters.nominations.NominationFilter;
import com.atos.views.ChangeSystemView;

public interface WeeklyNominationService extends Serializable{

	public Map<String, Object> selectContractCodeByUser(NominationFilter filter);
	
	public Map<String, Object> selectShipperIdNominations(NominationFilter filter);
	
	public Map<String, BigDecimal> selectZonesNomination();
	
	public BigDecimal selectOperationCategory();
	
	public BigDecimal selectOperationTerm();

	public BigDecimal selectOperation(TreeMap<String,BigDecimal> map);

	public List<NominationHeaderBean> selectNomination(NominationFilter filter);

	public NominationHeaderBean selectMaxVersionNomination(String nomCode);
	
	public List<NominationDetailWeekBean> selectDetailWeeklyNomination(NominationHeaderBean bean);

	public List<NominationDetailBean> selectParkUnparkNomination(NominationHeaderBean bean);

	public List<NominationQualityGasDayBean> selectQualityGasDayNomination(NominationHeaderBean bean);

	public List<NominationQualityGasWeekBean> selectQualityGasWeekNomination(NominationHeaderBean bean);
	
	public List<QualityTableBean> selectQualityNominationTable(NominationHeaderBean bean);
	
	public int selectStartDayOfWeek();
	
	public StreamedContent getFile(TreeMap<String,BigDecimal> map);
	
	public void prorateCalculation(List<NominationDetailWeekBean> area_tab, List<NominationQualityGasWeekBean> quality);

	public IntermediateNomFileMailBean getInfoMailSubNomBean(BigDecimal idn_nomination);
	
	public ByteArrayOutputStream generateXlsx(NominationHeaderBean selected, List<NominationDetailWeekBean> east_tab,
			List<NominationDetailWeekBean> west_tab, List<NominationDetailWeekBean> mix_tab, List<NominationDetailWeekBean> o_east_tab,
			List<NominationQualityGasWeekBean> east_quality,
			List<NominationQualityGasWeekBean> west_quality, List<NominationQualityGasWeekBean> mix_quality,
			List<QualityTableBean> quality, ChangeSystemView system);
	
	public String saveFile(byte[] file, 
								BigDecimal idn_active, 
								String action, 
								Date start_date, 
								NominationHeaderBean selectedNomHeader) throws Exception;
}
