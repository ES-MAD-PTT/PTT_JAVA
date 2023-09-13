package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.List;
import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.NewDocumentBean;
import com.atos.beans.dam.UserGuideBean;

public interface UserGuideMapper {

	public List<UserGuideBean> selectUserGuides();

	public List<ComboFilterNS> selectUserGuideDocumentType();
	
	public List<ComboFilterNS> selectUserGroupType();

	public int insertUserGuide(UserGuideBean userGuide);

	public int insertDocumentType(NewDocumentBean newDocument);
	
	public List<BigDecimal> getDocumentIdn(UserGuideBean userGuide);

	public int updateUserGuide(UserGuideBean userGuide);

	public List<UserGuideBean> selectUserGuideHeader(UserGuideBean bean);

	public List<UserGuideBean> selectUserGuide(UserGuideBean bean);

	public int deleteFile(UserGuideBean userGuide);
	
}
