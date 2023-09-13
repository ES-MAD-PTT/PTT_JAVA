package com.atos.mapper.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.NominationDetailBean;
import com.atos.beans.nominations.NominationDetailWeekBean;
import com.atos.beans.nominations.NominationHeaderBean;
import com.atos.beans.nominations.NominationQualityGasDayBean;
import com.atos.beans.nominations.NominationQualityGasWeekBean;
import com.atos.beans.nominations.OperationFileBean;
import com.atos.beans.nominations.ProcessManagementBean;
import com.atos.beans.nominations.QualityTableBean;
import com.atos.filters.nominations.NominationFilter;

public interface NominationMapper extends Serializable{

	public List<ComboFilterNS> selectShipperIdNominations(NominationFilter filter);

	public List<ComboFilterNS> selectContractCodeDayByShipper(NominationFilter filter);
	
	public List<ComboFilterNS> selectContractCodeWeekByShipper(NominationFilter filter);
	
	public List<ComboFilterNS> selectZonesNomination();
	
	public List<NominationHeaderBean> selectNomination(NominationFilter filter);
	
	public List<NominationHeaderBean> selectMaxVersionNomination(String nomCode);
	
	public List<NominationDetailBean> selectDetailNomination(NominationHeaderBean bean);
	
	public List<NominationDetailWeekBean> selectDetailWeeklyNomination(NominationHeaderBean bean);
	
	public List<NominationDetailBean> selectParkUnparkNomination(NominationHeaderBean bean);
	
	public List<NominationQualityGasDayBean> selectQualityGasDayNomination(NominationHeaderBean bean);
	
	public List<NominationQualityGasWeekBean> selectQualityGasWeekNomination(NominationHeaderBean bean);
	
	public List<QualityTableBean> selectQualityNominationTable(NominationHeaderBean bean);
	
	public List<OperationFileBean> selectGetFile(TreeMap<String,BigDecimal> map);
	
	public List<ProcessManagementBean> getProcesssManagement(ProcessManagementBean bean);
	
//	09/09/16 CH0036 PPM: Por peticion de cambios en FAT, se elimina boton "Open Renomination" en pantalla dailyNomination.xhtml.
//	public List<ProcessManagementBean> getOpenRenominacion(ProcessManagementBean bean);
}
