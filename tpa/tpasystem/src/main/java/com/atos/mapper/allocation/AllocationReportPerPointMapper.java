package com.atos.mapper.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.allocation.AllocationReportDetailDto;
import com.atos.beans.allocation.AllocationReportDto;
import com.atos.beans.allocation.AllocationReportPerPointBean;
import com.atos.filters.allocation.AllocRepQryFilter;
import com.atos.filters.allocation.AllocationReportPerPointFilter;

public interface AllocationReportPerPointMapper extends Serializable {

	List<ComboFilterNS> selectShipperId();

	List<ComboFilterNS> selectContractId(AllocationReportPerPointFilter filters);

	List<ComboFilterNS> selectPointId(AllocationReportPerPointFilter filters);

	List<AllocationReportPerPointBean> selectReportPerPoint(AllocationReportPerPointFilter filters);

}
