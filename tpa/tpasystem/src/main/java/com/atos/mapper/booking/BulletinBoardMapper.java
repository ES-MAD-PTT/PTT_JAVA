package com.atos.mapper.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.OpTemplateBean;
import com.atos.beans.booking.BulletinBoardBean;
import com.atos.beans.booking.BulletinBoardMailBean;
import com.atos.beans.booking.CapacityRequestSubmissionBean;
import com.atos.beans.booking.OperationFileBean;

public interface BulletinBoardMapper extends Serializable{

	public List<BulletinBoardBean> selectBulletinBoard(BulletinBoardBean bbb);
	// CH011 - 05/09/16 - Se anade a esta pagina la funcionalidad de CRSubmission de descarga de template y envio ficheros excel.
	public List<OpTemplateBean> getOpTemplateByCatTermFiletypeSystem(OpTemplateBean otb);
	public BigDecimal selectContractIdnOperationCategory();
	public BigDecimal selectOperationTermIdFromCode(String termCode);
	public int insertOperationfile(OperationFileBean ofb);
	public int updateXMLIntoOperationfile(OperationFileBean ofb);	
	public List<ComboFilterNS> selectShipperIdByUserId(String userId);
	public void capacityRequestSubmission(CapacityRequestSubmissionBean crsb);
	public BulletinBoardMailBean getInfoMailBulletingBoard (BigDecimal idn_contract_request);
}
