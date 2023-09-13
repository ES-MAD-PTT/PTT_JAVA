package com.atos.mapper.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atos.beans.booking.CRReviewDto;
import com.atos.beans.booking.CapacityDetailDto;
import com.atos.beans.booking.CapacityRequestMailBean;
import com.atos.beans.booking.GasQualityDto;
import com.atos.beans.booking.OperationFileUpdate;

public interface ContractReviewMapper extends Serializable {

	List<CRReviewDto> getPoints(Map<String, String> params);

	List<CapacityDetailDto> getCapacities(HashMap<String, String> params);

	List<CapacityDetailDto> getCapacitiesContractQuery(HashMap<String, String> params);

	List<GasQualityDto> getGasQuality(String requestCode);

	Map<String, String> isEditable(String requestCode);

	int insertModified(OperationFileUpdate ofu);

	HashMap<String, Object> proRequestUpdate(HashMap<String, Object> params);
	
	BigDecimal getIdnUserGroup(String user_group_id);
	
	CapacityRequestMailBean getMailData(String request_code);

}
