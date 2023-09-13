package com.atos.mapper.utils;

import java.util.List;

import com.atos.beans.OpTemplateBean;
import com.atos.beans.WsTemplateBean;
import com.atos.beans.XMLMapBean;
import com.atos.beans.XMLMapBlockBean;
import com.atos.beans.XMLMapItemBean;
import com.atos.beans.XMLMapSheetBean;

public interface Xlsx2XmlMapper {

	public List<WsTemplateBean> getWsTemplateFiletypeSystem(String ws);
	public List<OpTemplateBean> getOpTemplateByCatTermFiletypeSystem(OpTemplateBean otb);
	public List<XMLMapBean> getXMLMapByXMLMapId(XMLMapBean xmb);	
	public List<XMLMapSheetBean> getXMLMapSheetByXMLMapId(XMLMapSheetBean xmsb);
	public List<XMLMapBlockBean> getXMLMapBlockByXMLMapSheetId(XMLMapBlockBean xmbb);
	public List<XMLMapItemBean> getXMLMapItemByXMLMapBlockId(XMLMapItemBean xmbb);	
	
}
