package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.LinePackRequirementBean;
import com.atos.filters.dam.LinePackRequirementFilter;

public interface LinePackRequirementMapper {

	public List<LinePackRequirementBean> selectLinePackRequirements(LinePackRequirementFilter filters);

	public List<ComboFilterNS> selectLinePackRequirementZoneSystem(BigDecimal idn_system);// offshore

	public List<String> getLinePackRequirementId(LinePackRequirementBean bean);

	public int insertLinePackRequirement(LinePackRequirementBean linePackRequirement);

	public int deleteLinePackRequirement(LinePackRequirementBean linePackRequirement);// es un update

	public Date getLinePackRequirementStarDate(LinePackRequirementBean linePackRequirement);

	public List<LinePackRequirementBean> getLinePackRequirement(LinePackRequirementBean linePackRequirement);

}
