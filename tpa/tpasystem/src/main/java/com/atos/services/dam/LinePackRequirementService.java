package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.LinePackRequirementBean;
import com.atos.beans.dam.ShrinkageFactorBean;
import com.atos.filters.dam.LinePackRequirementFilter;

public interface LinePackRequirementService extends Serializable {

	public List<LinePackRequirementBean> selectLinePackRequirements(LinePackRequirementFilter filter);

	public SystemParameterBean getSystemParameter(String str);

	public Map<BigDecimal, Object> selectLinePackRequirementZoneSystem(BigDecimal idn_system);// offshore

	public String insertLinePackRequirement(LinePackRequirementBean linePackRequirement) throws Exception;

	public String deleteLinePackRequirement(LinePackRequirementBean linePackRequirement) throws Exception;// update
	
	public List<LinePackRequirementBean> getLinePackRequirement(LinePackRequirementBean linePackRequirement);

}
