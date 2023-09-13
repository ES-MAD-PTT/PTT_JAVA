package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.primefaces.model.StreamedContent;

import com.atos.beans.dam.NewDocumentBean;
import com.atos.beans.dam.UserGuideBean;
import com.atos.exceptions.ValidationException;

public interface UserGuideService extends Serializable {

	public List<UserGuideBean> selectUserGuides();

	public Map<BigDecimal, Object> selectUserGuideDocumentType();

	public Map<BigDecimal, Object> selectUserGroupType();

	public String insertUserGuide(UserGuideBean userGuide) throws Exception;

	public String insertNewDocument(NewDocumentBean newDocument) ;

	public StreamedContent getFile(BigDecimal idn_user_group) throws ValidationException;

	public StreamedContent getFile(UserGuideBean userGuide) throws ValidationException;

	public String deleteFile(UserGuideBean userGuide);
}
