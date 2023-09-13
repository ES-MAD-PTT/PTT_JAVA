package com.atos.services.dam;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.NewDocumentBean;
import com.atos.beans.dam.UserGuideBean;
import com.atos.exceptions.ValidationException;
import com.atos.mapper.dam.UserGuideMapper;
import com.atos.views.dam.UserGuideView;

@Service("userGuideService")
public class UserGuideServiceImpl implements UserGuideService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5738619896981240370L;

	private static final Logger log = LogManager.getLogger(UserGuideServiceImpl.class);
	
	@Autowired
	private UserGuideMapper userGuideMapper;

	public List<UserGuideBean> selectUserGuides() {
		return userGuideMapper.selectUserGuides();

	}

	public Map<BigDecimal, Object> selectUserGuideDocumentType() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = userGuideMapper.selectUserGuideDocumentType();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;

	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertUserGuide(UserGuideBean userGuide) throws Exception {

/*		List<BigDecimal> list = userGuideMapper.getDocumentIdn(userGuide);

		if (list.size() > 0) {
			// the id is already inserted.. updated...
			BigDecimal idn = list.get(0);
			userGuide.setIdn_document(idn);
			int ins = userGuideMapper.updateUserGuide(userGuide);
			if (ins != 1) {
				throw new Exception("-2");
			}
		} else {
*/
			// aun no hay ninguno... insertamos
			int ins = userGuideMapper.insertUserGuide(userGuide);
			if (ins != 1) {
				throw new Exception("-1");
			}
//		}
		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertNewDocument(NewDocumentBean newDocument) {

		// aun no hay ninguno... insertamos
		int ins = userGuideMapper.insertDocumentType(newDocument);
		if (ins != 1) {
			return "-1";
		}
		return "0";
	}

	@Override
	public StreamedContent getFile(BigDecimal idn_user_group) throws ValidationException {

		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		UserGuideBean bean = new UserGuideBean();
		bean.setIdn_user_group(idn_user_group);

		List<UserGuideBean> l = userGuideMapper.selectUserGuideHeader(bean);

		if (l == null || l.size() == 0) {
			throw new ValidationException(msgs.getString("no_file_to_download"));
		} else {
			if(l.size()==1) {
				UserGuideBean b = l.get(0);
	
				if (b == null)
					throw new ValidationException(msgs.getString("no_file_to_download"));
	
				ByteArrayInputStream ba = new ByteArrayInputStream(b.getBinary_data());
	
				return new DefaultStreamedContent(ba, "", b.getDocument_name());
			} else {
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();	
		        ZipOutputStream zip = new ZipOutputStream(baos);
		        boolean hay = false;
		
		        try {
			        for(int i=0;i<l.size();i++){
			        	UserGuideBean file = l.get(i);
			        	if (file.getBinary_data()!=null) {
			                hay = true;
			                String file_name= l.get(i).getDocument_name();
							zip.putNextEntry(new ZipEntry(file_name));
			                zip.write(file.getBinary_data());
			                zip.closeEntry();
			            }
			        }
			        if (!hay) {
			            zip.putNextEntry(new ZipEntry("No_data"));
			            zip.write(new byte[0]);
			            zip.closeEntry();
			        }
			        zip.finish();
			        zip.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.catching(e);
				}

				ByteArrayInputStream ba = new ByteArrayInputStream(baos.toByteArray());
				
				return new DefaultStreamedContent(ba, "", "user_guide.zip");
				
			}
		}

	}

	@Override
	public StreamedContent getFile(UserGuideBean userGuide) throws ValidationException {

		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		List<UserGuideBean> l = userGuideMapper.selectUserGuide(userGuide);

		if (l == null || l.size() == 0) {
			throw new ValidationException(msgs.getString("no_file_to_download"));
		} else {
			UserGuideBean b = l.get(0);

			if (b == null)
				throw new ValidationException(msgs.getString("no_file_to_download"));

			ByteArrayInputStream ba = new ByteArrayInputStream(b.getBinary_data());

			return new DefaultStreamedContent(ba, "", b.getDocument_name());
		}
	}

	@Override
	public Map<BigDecimal, Object> selectUserGroupType() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = userGuideMapper.selectUserGroupType();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;


	}

	@Override
	public String deleteFile(UserGuideBean userGuide) {
		int upd = userGuideMapper.deleteFile(userGuide);
		if (upd != 1) {
			return "-1";
		}
		return "0";
	}
}
