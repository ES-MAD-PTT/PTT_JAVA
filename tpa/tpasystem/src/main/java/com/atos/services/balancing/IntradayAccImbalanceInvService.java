package com.atos.services.balancing;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.atos.beans.FileBean;
import com.atos.beans.LanguageBean;
import com.atos.beans.UserBean;
import com.atos.beans.balancing.IntradayAccImbalanceInventoryBean;
import com.atos.filters.balancing.IntradayAccImbalanceInventoryFilter;
import com.atos.views.ChangeSystemView;

public interface IntradayAccImbalanceInvService extends Serializable {
	public List<IntradayAccImbalanceInventoryBean> selectIntradayAccImbalanceInv(IntradayAccImbalanceInventoryFilter filter);
	public void callWS() throws Exception;
	public Map<BigDecimal, Object> selectTimestampIds(IntradayAccImbalanceInventoryFilter filters);
	public ByteArrayInputStream getReportTemplate(BigDecimal systemId, boolean isShipper) throws Exception;
	public void copySheets(XSSFSheet srcSheet, XSSFSheet destSheet);
	public void updateWsFromFile(FileBean _file, UserBean _user, LanguageBean _lang, ChangeSystemView _system) throws Exception;
	public String saveTimestamp(IntradayAccImbalanceInventoryFilter filters_form, String user);
}
