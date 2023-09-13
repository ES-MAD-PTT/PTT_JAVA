package com.atos.services.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.TreeMap;

import org.primefaces.model.StreamedContent;

import com.atos.beans.FileBean;
import com.atos.beans.nominations.IntermediateNomFileMailBean;

public interface WeeklyIntermediateSubmissionFileService extends Serializable{

	public HashMap<String,Object> saveFile(FileBean file, String shipperComments, BigDecimal systemId) throws Exception;

	public BigDecimal selectOperationCategory();
	
	public BigDecimal selectOperationTerm();

	public BigDecimal selectOperation(TreeMap<String,BigDecimal> map);

	public StreamedContent getFile(BigDecimal _systemId);
	
	public String selectOperationSubmissionDeadlinePhrase(String _operType);
	
	public IntermediateNomFileMailBean getInfoMailSubNomBean(BigDecimal idn_nomination);
}
