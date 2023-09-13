package com.atos.services.nominations;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.primefaces.model.StreamedContent;

import com.atos.beans.nominations.NominationDetailBean;
import com.atos.beans.nominations.NominationHeaderBean;
import com.atos.beans.nominations.NominationQualityGasDayBean;
import com.atos.beans.nominations.NominationQualityGasWeekBean;
import com.atos.beans.nominations.QualityTableBean;
import com.atos.filters.nominations.NominationFilter;

public interface DailyNominationService extends Serializable{

	public Map<String, Object> selectContractCodeByUser(NominationFilter filter);
	
	public Map<String, Object> selectShipperIdNominations(NominationFilter filter);
	
	public Map<String, BigDecimal> selectZonesNomination();
	
	public BigDecimal selectOperationCategory();
	
	public BigDecimal selectOperationTerm();

	public BigDecimal selectOperation(TreeMap<String,BigDecimal> map);

	public List<NominationHeaderBean> selectNomination(NominationFilter filter);
	
	public NominationHeaderBean selectMaxVersionNomination(String nomCode);
	
	public List<NominationDetailBean> selectDetailNomination(NominationHeaderBean bean);
	
	public List<NominationDetailBean> selectParkUnparkNomination(NominationHeaderBean bean);

	public List<NominationQualityGasDayBean> selectQualityGasDayNomination(NominationHeaderBean bean);

	public List<NominationQualityGasWeekBean> selectQualityGasWeekNomination(NominationHeaderBean bean);
	
	public List<QualityTableBean> selectQualityNominationTable(NominationHeaderBean bean);
	
	public StreamedContent getFile(TreeMap<String,BigDecimal> map);

	public void prorateCalculation(List<NominationDetailBean> area_tab, List<NominationQualityGasDayBean> quality);

	public ByteArrayOutputStream generateXlsx(NominationHeaderBean selected, List<NominationDetailBean> east_tab,
			List<NominationDetailBean> west_tab, List<NominationDetailBean> mix_tab,
			List<NominationDetailBean> east_tab_park, List<NominationDetailBean> west_tab_park,
			List<NominationDetailBean> mix_tab_park, List<NominationQualityGasDayBean> east_quality,
			List<NominationQualityGasDayBean> west_quality, List<NominationQualityGasDayBean> mix_quality,
			List<QualityTableBean> quality, BigDecimal _systemId);
	
	public String saveFile(byte[] file, 
							BigDecimal systemId, 
							String action, 
							Date start_date, 
							NominationHeaderBean selectedNomHeader) throws Exception;

	public byte[] getSimoneFile(TreeMap<String, BigDecimal> map);

// 09/09/16 CH0036 PPM: Por peticion de cambios en FAT, se elimina boton "Open Renomination" en pantalla dailyNomination.xhtml.	
//		public ErrorBean openRenomination();

}
