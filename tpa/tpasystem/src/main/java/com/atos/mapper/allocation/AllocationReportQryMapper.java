package com.atos.mapper.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.allocation.AllocationReportDetailDto;
import com.atos.beans.allocation.AllocationReportDto;
import com.atos.filters.allocation.AllocRepQryFilter;

public interface AllocationReportQryMapper extends Serializable {

	List<ComboFilterNS> selectShipperId();

	List<ComboFilterNS> selectContractId(AllocRepQryFilter filters);

	List<ComboFilterNS> selectPointId(BigDecimal idnSystem);

	List<AllocationReportDto> search(AllocRepQryFilter filters);

	List<AllocationReportDetailDto> searchDetail(AllocRepQryFilter filters);

}
