package com.atos.services.quality;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.UserBean;
import com.atos.beans.quality.OffSpecActionFileBean;
import com.atos.beans.quality.OffSpecIncidentBean;
import com.atos.beans.quality.OffSpecResponseBean;
import com.atos.beans.quality.OffSpecStatusBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.quality.OffSpecGasReportManagementFilter;
import com.atos.mapper.quality.OffSpecGasReportManagementMapper;

@Service("OSGRResponseService")
public class OffSpecGasReportResponseServiceImpl implements OffSpecGasReportResponseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3112115390690206850L;
	//private static final String strDisclosedStatusCode = "EV.NOTIFIED.ORIGINATOR.DISCLOSED";
	//private static final String strDisclosedStatusCodeNewFlow = "EV.ORIGINATOR.INFORMED.SHIPPERS";

	
	@Autowired
	private OffSpecGasReportManagementMapper osgrmMapper;
	
	public List<BigDecimal> getDisclosedStatusIds() throws Exception {
		//BigDecimal bdDisclosedStatusId = null;
		
		List<BigDecimal> tmpLStatusId = osgrmMapper.selectStatusIdFromStatusCodes();
		if(tmpLStatusId!=null)
			return tmpLStatusId;// = tmpLStatusId.get(0);
		else
			throw new Exception("OffSpecGasReportResponseServiceImpl: DISCLOSED status not found in database.");
		
		/*if(bdDisclosedStatusId==null)
			throw new Exception("OffSpecGasReportResponseServiceImpl: DISCLOSED status not found in database.");
		
		return bdDisclosedStatusId;*/
	}
	
	/*public BigDecimal getDisclosedStatusIdNewFlow() throws Exception {
		BigDecimal bdDisclosedStatusId = null;
		
		List<BigDecimal> tmpLStatusId = osgrmMapper.selectStatusIdFromStatusCode(strDisclosedStatusCodeNewFlow);
		if(tmpLStatusId!=null)
			bdDisclosedStatusId = tmpLStatusId.get(0);
		else
			throw new Exception("OffSpecGasReportResponseServiceImpl: DISCLOSED status not found in database.");
		
		if(bdDisclosedStatusId==null)
			throw new Exception("OffSpecGasReportResponseServiceImpl: DISCLOSED status not found in database.");
		
		return bdDisclosedStatusId;
	}*/
	
	public List<ComboFilterNS> selectIncidentTypes(){
		return osgrmMapper.selectIncidentTypes();	
	}

	public BigDecimal selectIncidentTypeIdFromCode(String incidentTypeCode){
		return osgrmMapper.selectIncidentTypeIdFromCode(incidentTypeCode);	
	}

	// Si el usuario es un operador, se obtienen todos los shippers.
	// Si no, solo se obtiene el propio shipper.
	public Map<BigDecimal, Object> selectShipperId(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();

		List<ComboFilterNS> tmpAllShippers = getAllShippers();
			
		for (ComboFilterNS combo : tmpAllShippers) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public List<ComboFilterNS> getAllShippers() {
		
		return osgrmMapper.selectShipperId();
	}

	public Map<BigDecimal, Object> selectQualityPoints(BigDecimal systemId){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> allQualityPoints = osgrmMapper.selectQualityPoints(systemId);
		
		for (ComboFilterNS combo : allQualityPoints) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 	
	}
	
	public List<OffSpecStatusBean> selectStatusIds(OffSpecGasReportManagementFilter filter){
		return osgrmMapper.selectStatusIds(filter);
	}
	
	public List<OffSpecIncidentBean> search(OffSpecGasReportManagementFilter filter){
		
		List<OffSpecIncidentBean> tmpLIncidents = null;
		
		// Se crea un nuevo filtro, para anadir los % e invocar la consulta.
		OffSpecGasReportManagementFilter tmpFilter = new OffSpecGasReportManagementFilter(filter);
		String tmpIncidCode = filter.getIncidentCode();
		
		if((tmpIncidCode != null) && (!"".equalsIgnoreCase(tmpIncidCode)))
			tmpFilter.setIncidentCode("%" + tmpIncidCode + "%");

		// Se consultan las respuestas asociadas a cada incidencia.
		// Independientemente del tipo de usuario (shipper u operador) siempre se consultan las respuestas para
		// un shipper concreto, el que este indicado en el filtro.
		Map<String, BigDecimal> params = new HashMap<String, BigDecimal>();
		params.put("shipperId", filter.getShipperId());
		
		tmpLIncidents = osgrmMapper.selectIncidentsToRespond(tmpFilter);
		if(tmpLIncidents!=null)
			for(OffSpecIncidentBean incid: tmpLIncidents) {
				params.put("incidentId", incid.getIncidentId());
				incid.setDiscloseResponses(osgrmMapper.selectDiscloseResponsesFromIncidentId(params));
			}
		
		return tmpLIncidents;
	}
	
	public void saveResponse(OffSpecIncidentBean _incid, 
							UserBean _user,
							List<BigDecimal> _disclosedStatusIds) throws Exception {
		validateResponse(_incid, _disclosedStatusIds);
		saveIncidentResponse(_incid, _user);
	}
	
	private void validateResponse(OffSpecIncidentBean _incid,
								  List<BigDecimal> _disclosedStatusIds) throws ValidationException {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	if(_incid.getFirstResponse().getResponseValue()==null)
			throw new ValidationException(msgs.getString("the_following_mandatory_fields_error") + " " + 
											msgs.getString("osgr_man_lab_response") + ".");
    	
    	// Se comprueba que no se haya cambiado el estado de la incidencia desde que se cargo el menu principal.
    	String msgNotFound = msgs.getString("osgr_man_off_specification_event") + " " + 
								msgs.getString("with_id") + " " + _incid.getIncidentCode() + " " + 
								msgs.getString("not_found") + ".";
    	List<OffSpecIncidentBean> tmpLIncid = osgrmMapper.selectIncidentFromId(_incid.getIncidentId());
    	OffSpecIncidentBean currIncident = null;
    	if(tmpLIncid==null)
    		throw new ValidationException(msgNotFound);
    	
    	currIncident = tmpLIncid.get(0);
    	if(currIncident==null)
    		throw new ValidationException(msgNotFound);

    	//if(currIncident.getStatusId().compareTo(_disclosedStatusId) != 0)
    	if(!_disclosedStatusIds.contains(currIncident.getStatusId()))
    		throw new ValidationException(msgs.getString("osgr_man_wrong_status_error"));
	}
	

	private void saveIncidentResponse(OffSpecIncidentBean _incid, UserBean _user) throws Exception {

		OffSpecResponseBean osResponse  =  _incid.getFirstResponse();

		osResponse.setIncidentId(_incid.getIncidentId());
		osResponse.setIsResponded(OffSpecResponseBean.isRespondedYes);
		osResponse.setResponseDate(new Date());
		osResponse.setUserId(_user.getIdn_user());
		osResponse.setIdnAction(_incid.getIdnAction());
		
		// Se inserta un registro en la tabla de respuestas para el disclose.
		int res = osgrmMapper.insertOffSpecResponse(osResponse);
		if(res!=1){
    		throw new Exception("Error inserting into Off Specification Event Response table.");   		
    	}
		//Insertamos los ficheros
		for(OffSpecActionFileBean item : _incid.getFilesAction()) {
			osgrmMapper.insertFileAction(item);
		}
	}
	
	@Override
	public StreamedContent getResponseFile(OffSpecResponseBean bean) throws Exception {

		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		OffSpecResponseBean b = osgrmMapper.selectResponseFile(bean);

		
		if (b == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));

		if(b.getAttachedFile()!=null) {
			ByteArrayInputStream ba = new ByteArrayInputStream(b.getAttachedFile());
			return new DefaultStreamedContent(ba, "", b.getFileName());
		}
		return null;
	}

	@Override
	public Map<BigDecimal, Object> selectShipperAction(OffSpecIncidentBean item) {
		return osgrmMapper.selectShipperAction(item).stream().collect(
				Collectors.toMap(ComboFilterNS::getKey, ComboFilterNS::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}
}
