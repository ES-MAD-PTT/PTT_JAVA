package com.atos.mapper.allocation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.allocation.AdjustBean;
import com.atos.beans.allocation.AdjustBeanQryResult;
import com.atos.beans.allocation.ContractBean;
import com.atos.filters.allocation.AllocationAdjustFilter;

public interface AllocationAdjustMapper {
	List<ContractBean> selectContractId(AllocationAdjustFilter filter);

	List<AdjustBeanQryResult> search(AllocationAdjustFilter filter);

	List<ContractBean> selectOriginContractId(AdjustBean newAdjust);

	List<ContractBean> selectDestinationContractId(AdjustBean newAdjust);

	List<ComboFilterNS> selectZoneIds(BigDecimal origcontractId);

	AdjustBean getAdjustCode(AdjustBean newAdjust);

	void insertAdjust(Map<String, Object> params);
}
