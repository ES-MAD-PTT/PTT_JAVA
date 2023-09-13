package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.NominationPointBean;
import com.atos.filters.dam.NominationPointFilter;

public interface NominationPointMapper {

	public List<NominationPointBean> selectNominationPoints(NominationPointFilter filters);

	public List<ComboFilterNS> selectPointTypes();

	//public List<String> selectIds(String query);

	public List<ComboFilterNS> selectIdsComboSystem(BigDecimal idn_system);// offshore

	public List<ComboFilterNS> selectSystem(BigDecimal idn_system);// offshore
	
	public List<ComboFilterNS> selectZonesSystem(BigDecimal idn_system);// offshore

	public List<ComboFilterNS> selectAreas(NominationPointBean nominationPoint);

	public List<ComboFilterNS> selectAreasSystem(BigDecimal idn_system);// offshore

	public int insertNominationPoint(NominationPointBean nominationPoint);

	// 20/05/2021 AGA se elimina la insercion del contract point ya que se crea una pantalla especifica para ello
	//public int insertContractPoint(NominationPointBean nominationPoint);

	public int insertNominationPointParam(NominationPointBean nominationPoint);

	public int updateNominationPoint(NominationPointBean nominationPoint);

	public int updateNominationPointParam(NominationPointBean nominationPoint);

	public List<String> getNominationPointId(NominationPointBean bean);

	public List<ComboFilterNS> selectAreaNominationPoint(NominationPointBean newNominationPoint);

	public List<ComboFilterNS> selectSubAreaNominationPoint(NominationPointBean newNominationPoint);

	public List<String> getNominationPointCode(NominationPointBean nominationPoint);

	public int updateMocMetering(NominationPointBean nominationPoint);

	public int deleteNominationPointParam(NominationPointBean nominationPoint);

	public Date getNominationPointParamStarDate(NominationPointBean nominationPoint);

}
