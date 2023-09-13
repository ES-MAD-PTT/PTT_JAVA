package com.atos.services.dam;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.PimsGraphicMenuBean;
import com.atos.filters.dam.PimsGraphicMenuFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.PimsGraphicMenuMapper;

@Service("pimsGraphicMenuService")
public class PimsGraphicMenuServiceImpl implements PimsGraphicMenuService {

	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private PimsGraphicMenuMapper pimsGraphicMenuMapper;

	@Autowired
	private SystemParameterMapper systemParameterMapper;

	public List<PimsGraphicMenuBean> selectPmisGraphicMenus(PimsGraphicMenuFilter filter) {
		return pimsGraphicMenuMapper.selectPmisGraphicMenus(filter);

	}

	@Override
	public String getCheckValueSystemParameter(PimsGraphicMenuBean systemParameter) throws Exception {

		String parameter_code = pimsGraphicMenuMapper.getParameterCode(systemParameter);
		systemParameter.setParameter_code(parameter_code);

		String parameter_desc = pimsGraphicMenuMapper.getParameterDesc(systemParameter);
		systemParameter.setParameter_desc(parameter_desc);

		pimsGraphicMenuMapper.getCheckValueSystemParameter(systemParameter);
		if (systemParameter.getValid() != 0) {
			throw new Exception(systemParameter.getError_desc());
		}
		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertSystemParameter(PimsGraphicMenuBean systemParameter) throws Exception {

		int ins = pimsGraphicMenuMapper.insertSystemParameter(systemParameter);
		if (ins != 1) {
			throw new Exception("-1");
		}

		return "0";
	}

	public SystemParameterBean getSystemParameter(String str) {
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(systemParameterMapper.getSysdate().get(0));
		bean.setParameter_name(str);
		bean.setUser_id((String) SecurityUtils.getSubject().getPrincipal());
		bean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		systemParameterMapper.getIntegerSystemParameter(bean);
		return bean;
	}

	
	@Transactional(rollbackFor = { Throwable.class })
	public String deleteSystemParameter(PimsGraphicMenuBean systemParameter) throws Exception {

		// We calculate the end date, which will be the startDate -1 day
		Date startDateBd = pimsGraphicMenuMapper.getSystemParameterStarDate(systemParameter);

		// Date endDate = restarDiasFecha(shrinkageFactor.getStartDate(),1);
		Date endDate = restarDiasFecha(startDateBd, 1);
		systemParameter.setEndDate(endDate);

		int ins = pimsGraphicMenuMapper.deleteSystemParameter(systemParameter);
		if (ins != 1) {
			throw new Exception("-10");
		}

		return "0";
	}

	public Date restarDiasFecha(Date fecha, int dias) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); //
		calendar.add(Calendar.DATE, -dias); // numero de días a añadir, o restar en caso de días<0
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
	}

	public String selectPmisGraphicMenusValue() {
		List<String> lista = pimsGraphicMenuMapper.selectPmisGraphicMenusValue();
		if(lista.size()==0) {
			return "Not defined";
		} else {
			return lista.get(0);
		}
	}
	
}
