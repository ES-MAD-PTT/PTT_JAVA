package com.atos.mapper.utils.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.booking.SignedContractTemplateBean;

public interface SignedContractTemplateGeneratorMapper extends Serializable {

	public List<SignedContractTemplateBean> selectSignedContractTempDetails(BigDecimal capReqId);
}
