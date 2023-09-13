package com.atos.mapper.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.balancing.InstructedOperationFlowShipperFileBean;
import com.atos.beans.balancing.InstructedOperationFlowShippersBean;
import com.atos.filters.balancing.InstructedOperationFlowShippersFilter;

public interface InstructedOperationFlowShippersMapper extends Serializable {
	
	public List<ComboFilterNS> selectShipperId();
	public List<ComboFilterNS> selectTimestampIds(InstructedOperationFlowShippersFilter filters);
	public List<ComboFilterNS> selectZonesSystem(BigDecimal idn_system);
	public List<InstructedOperationFlowShippersBean> selectInstructedOperationFlowShippers (InstructedOperationFlowShippersFilter filter);
	public int updateComments(InstructedOperationFlowShippersBean bean);

	public int updateFileGasFlow(InstructedOperationFlowShipperFileBean bean);
	public List<InstructedOperationFlowShipperFileBean> selectFileGasFlow (BigDecimal idn_intraday_gas_flow_file);
	public int insertFileGasFlow(InstructedOperationFlowShipperFileBean bean);

}
