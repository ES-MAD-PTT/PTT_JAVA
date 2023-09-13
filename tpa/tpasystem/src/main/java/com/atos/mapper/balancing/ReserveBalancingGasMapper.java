package com.atos.mapper.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ReserveBalancingGasDto;
import com.atos.beans.balancing.ReserveBalancingGasBean;
import com.atos.beans.dam.UserGuideBean;
import com.atos.filters.balancing.ReserveBalancigGasFilter;

public interface ReserveBalancingGasMapper extends Serializable {
	public List<ComboFilterNS> selectShipperId(BigDecimal idnUser);

	public List<ComboFilterNS> selectReserveBalId(HashMap<String, BigDecimal> params);
	public List<ReserveBalancingGasDto> search(ReserveBalancigGasFilter filter);
	public void save(ReserveBalancingGasBean newReserve);
	public List<ReserveBalancingGasDto> getFile(BigDecimal idFile);

	public int checkValidDateForContract(ReserveBalancingGasBean newReserve);
	
	public List<UserGuideBean> selectTemplate(UserGuideBean bean);

}
