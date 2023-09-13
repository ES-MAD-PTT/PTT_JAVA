package com.atos.services.metering;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.metering.MeteringRetrievingBean;
import com.atos.filters.metering.MeteringRetrievingFilter;
import com.atos.mapper.metering.MeteringRetrievingMapper;


@Service("meteringRetrievingService")
public class MeteringRetrievingServiceImpl implements MeteringRetrievingService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1616500768028567685L;
	@Autowired
	private MeteringRetrievingMapper meteringRetrieveMapper;
		
	private static final Logger log = LogManager.getLogger("com.atos.services.metering.MeteringRetrievingServiceImpl");

	public List<MeteringRetrievingBean> selectMeteringRetrieving(MeteringRetrievingFilter filter){
		return meteringRetrieveMapper.selectMeteringRetrieving(filter);
		
	}
	public List<MeteringRetrievingBean> selectMeteringRetrievingWarning(MeteringRetrievingFilter filter){
		return meteringRetrieveMapper.selectMeteringRetrievingWarning(filter);
		
	}
	 
	@Override
	public MeteringRetrievingBean selectCabMetRetrieving(MeteringRetrievingFilter filter){
		 
		 return meteringRetrieveMapper.selectCabMetRetrieving(filter);
	 }
	 
	 
	@Override
	public Date selectLastOKMeteringInputDate() {
		return meteringRetrieveMapper.selectLastOKMeteringInputDate();
	}


	@Override
	public Map<BigDecimal, Object> selectMeteringInputCodes(MeteringRetrievingFilter filter) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = meteringRetrieveMapper.selectMeteringInputCodes(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 
	}

	

	public StreamedContent getFile(BigDecimal idnMeteringInput) {
		List<MeteringRetrievingBean> l =  meteringRetrieveMapper.selectFileXmlbyIdn(idnMeteringInput);
		   
		if(l.size()==0){
			return null;
		} else {
			MeteringRetrievingBean tmpRTBean = l.get(0);
			
			InputStream stream = new ByteArrayInputStream(tmpRTBean.getXml_data().getBytes());
		    StreamedContent file = new DefaultStreamedContent(stream, "application/xml",  "File.xml");
			return file;
			 
		}	
	}
	
	public StreamedContent getFile(MeteringRetrievingFilter filter) {
	   List<MeteringRetrievingBean> l =  meteringRetrieveMapper.selectFileXml(filter);
	   
		if(l.size()==0){
			return null;
		} else {
			MeteringRetrievingBean tmpRTBean = l.get(0);
			
			InputStream stream = new ByteArrayInputStream(tmpRTBean.getXml_data().getBytes());
		    StreamedContent file = new DefaultStreamedContent(stream, "application/xml",  "File.xml");
			return file;
			 
		}	
	} 
	
}
