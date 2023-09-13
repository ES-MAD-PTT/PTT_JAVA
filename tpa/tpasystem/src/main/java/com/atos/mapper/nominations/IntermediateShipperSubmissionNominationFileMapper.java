package com.atos.mapper.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.nominations.IntermediateNomFileMailBean;
import com.atos.beans.nominations.NominationDeadlineBean;
import com.atos.beans.nominations.OperationTemplateBean;
import com.atos.beans.nominations.ProcessIntermediateBean;

public interface IntermediateShipperSubmissionNominationFileMapper extends Serializable{

	public List<OperationTemplateBean> selectTemplateFile(OperationTemplateBean bean);
	
	public List<OperationTemplateBean> selectTemplateInfo(OperationTemplateBean bean);
	
	public List<ProcessIntermediateBean> getProcesssIntermediate(ProcessIntermediateBean bean);
	
	public List<String> selectStartDayOfWeek();
	
	public NominationDeadlineBean selectNominationDeadlines(NominationDeadlineBean ndlb);
	
	public IntermediateNomFileMailBean getInfoMailSubNomBean(BigDecimal idn_nomination);
}
