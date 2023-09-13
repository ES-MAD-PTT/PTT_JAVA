package com.atos.services.dam;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.MessageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ZoneBean;
import com.atos.filters.dam.ZoneFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.ZoneMapper;
import com.atos.utils.Constants;


@Service("zoneService")
public class ZoneServiceImpl implements ZoneService{

	
	private static final long serialVersionUID = 5738619896981240370L;
		
		@Autowired
		private ZoneMapper zoneMapper;
		
		@Autowired
		private SystemParameterMapper systemParameterMapper;
	
		
		public List<ZoneBean> selectZones(ZoneFilter filter){
			return zoneMapper.selectZones(filter);
		}

		public String getName(BigDecimal idn_zone){
			return zoneMapper.getName(idn_zone);
		}
		public BigDecimal getPipelineSystem(BigDecimal idn_zone){
			return zoneMapper.getPipelineSystem(idn_zone);
		}
		
		
		@Override
		public ZoneBean getQuality(ZoneBean zone) {
			
			ZoneBean bean = new ZoneBean();	
						
			zone.setParameterCode(Constants.WI);
			bean= zoneMapper.getMaxMin(zone);
			if (bean!= null) {
				zone.setWobbeIndexMax(bean.getMaxValue());
				zone.setWobbeIndexMin(bean.getMinValue());
			}
			
			zone.setParameterCode(Constants.CH4);
			bean= zoneMapper.getMaxMin(zone);
			if (bean!= null) {
				zone.setMethaneMax(bean.getMaxValue());
				zone.setMethaneMin(bean.getMinValue());
			}	
			
			zone.setParameterCode(Constants.O2);
			bean= zoneMapper.getMaxMin(zone);
			if (bean!= null) {
				zone.setOxygenMax(bean.getMaxValue());
				zone.setOxygenMin(bean.getMinValue());
			}
			
			
			zone.setParameterCode(Constants.CO2_N);
			bean= zoneMapper.getMaxMin(zone);
			if (bean!= null) {
				zone.setCarbonDioxideNitrogenMax(bean.getMaxValue());
				zone.setCarbonDioxideNitrogenMin(bean.getMinValue());
			}
			//DEFECT 330653
			zone.setParameterCode(Constants.CO2);
			bean= zoneMapper.getMaxMin(zone);
			if (bean!= null) {
				zone.setCarbonDioxideMax(bean.getMaxValue());
				zone.setCarbonDioxideMin(bean.getMinValue());
			}
			
			zone.setParameterCode(Constants.S);
			bean= zoneMapper.getMaxMin(zone);
			if (bean!= null) {
				zone.setTotalSulphurMax(bean.getMaxValue());
				zone.setTotalSulphurMin(bean.getMinValue());
			}
			
			zone.setParameterCode(Constants.OtherParticles);
			bean= zoneMapper.getMaxMin(zone);
			if (bean!= null) {
				zone.setOtherParticulatesMax(bean.getMaxValue());
				zone.setOtherParticulatesMin(bean.getMinValue());
			}
			
			
			zone.setParameterCode(Constants.HCDewPoint);
			bean= zoneMapper.getMaxMin(zone);
			if (bean!= null) {
				zone.setHydrocarbonDewPointMax(bean.getMaxValue());
				zone.setHydrocarbonDewPointMin(bean.getMinValue());
			}
			
			zone.setParameterCode(Constants.HV);
			bean= zoneMapper.getMaxMin(zone);
			if (bean!= null) {
				zone.setGrossCalorificValueMax(bean.getMaxValue());
				zone.setGrossCalorificValueMin(bean.getMinValue());
			}
			
			zone.setParameterCode(Constants.C2);
			bean= zoneMapper.getMaxMin(zone);
			if (bean!= null) {
				zone.setC2Max(bean.getMaxValue());
				zone.setC2Min(bean.getMinValue());
			}
			
			zone.setParameterCode(Constants.N2);
			bean= zoneMapper.getMaxMin(zone);
			if (bean!= null) {
				zone.setNitrogenMax(bean.getMaxValue());
				zone.setNitrogenMin(bean.getMinValue());
			}
			
			
			zone.setParameterCode(Constants.H2S);
			bean= zoneMapper.getMaxMin(zone);
			if (bean!= null) {
				zone.setHydrogenSulfideMax(bean.getMaxValue());
				zone.setHydrogenSulfideMin(bean.getMinValue());
			}
			
			zone.setParameterCode(Constants.Hg);
			bean= zoneMapper.getMaxMin(zone);
			if (bean!= null) {
				zone.setMercuryMax(bean.getMaxValue());
				zone.setMercuryMin(bean.getMinValue());
			}
			
			zone.setParameterCode(Constants.H2O);
			bean= zoneMapper.getMaxMin(zone);
			if (bean!= null) {
				zone.setSteamMax(bean.getMaxValue());
				zone.setSteamMin(bean.getMinValue());
			}
			return zone;
		}
		
		public SystemParameterBean getSystemParameter(String str){
			SystemParameterBean bean = new SystemParameterBean();
			bean.setDate(systemParameterMapper.getSysdate().get(0));
			bean.setParameter_name(str);
			bean.setUser_id((String)SecurityUtils.getSubject().getPrincipal());
			bean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
			systemParameterMapper.getIntegerSystemParameter(bean);
			return bean;
		}


		@Override
		public List<String> selectIds(String query) {
			return zoneMapper.selectIds(query);
		}

		@Override
		public List<String> selectNames(String query) {
			return zoneMapper.selectNames(query);
			
		}


		/*@Override
		public Map<BigDecimal, Object> selectSystems() {
			
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = zoneMapper.selectSystems();
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}*/
		@Override
		public Map<BigDecimal, Object> selectSystems(BigDecimal idn_system) {
			
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = zoneMapper.selectSystems(idn_system);
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}

		

/*
		@Override
		public Map<BigDecimal, Object> selectComboIds() {
			
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = zoneMapper.selectComboIds();
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}
*/
		@Override
		public Map<BigDecimal, Object> selectZonesSystem(BigDecimal idn_system) {
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = zoneMapper.selectZonesSystem(idn_system);
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 	
		}
		
		@Transactional( rollbackFor = { Throwable.class })
		public String insertZone(ZoneBean zone) throws Exception {
		
			//para saber si quiere insertar una zona nueva o la calidad solamente
			 if (zone.getIdn_zone()!=null){
				 
				 //quality
				 	int ins2= zoneMapper.insertZoneGasQuality(zone);
					if(ins2!=1){
						throw new Exception("-2");
					}
					
					int ins3 =insertaGasqValue(zone);
					if(ins3!=0){
						throw new Exception("-2");
					}
					return "0";
			 }else{
				 //zone
				 	List<String> list = zoneMapper.getZoneId(zone);
					if(list.size()>0){
						// the id is already inserted
							throw new Exception("-1");
						}
					
					int ins =zoneMapper.insertZone(zone);
					if(ins != 1){
						throw new Exception("-2");
					}
					
					int ins2= zoneMapper.insertZoneGasQuality(zone);
					if(ins2!=1){
						throw new Exception("-2");
					}
					
					int ins3 =insertaGasqValue(zone);
					if(ins3!=0){
						throw new Exception("-2");
					}
					return "0";
			 }
			 
			
			
		}
		
	
		@Transactional( rollbackFor = { Throwable.class })
		public String updateZone(ZoneBean zone) throws Exception {
			
			//la tab la zone no se actualiza ningun dato
			/*int upd = zoneMapper.updateZone(zone);
			if(upd != 1){
				throw new Exception("-1");
			}*/
			
			//We calculate the end date, which will be the startDate -1 day
			Date startDateBd = zoneMapper.getGasQualityStarDate(zone);
			Date endDate = restarDiasFecha(startDateBd,1);
			zone.setEndDate(endDate);		
			
			int ins = zoneMapper.deleteZoneGasQuality(zone);
			if(ins != 1){
				throw new Exception("-10");
			}
			//
			
			
			//to do insert instead of update because they are historical	
			int ins2= zoneMapper.insertZoneGasQuality(zone);
			if(ins2!=1){
				throw new Exception("-2");
			}
			
			
			
			
			int ins3 =insertaGasqValue(zone);
			if(ins3!=0){
				throw new Exception("-2");
			}
		
			return "0";
		}


		public boolean existsValues(BigDecimal max, BigDecimal min){
		//Solo si los dos vienen vacios no insertamos ninguno
			
			if (max==null && min==null){
				return false;
			}else return true;
		}
		
		@Transactional( rollbackFor = { Throwable.class })
		public int insertaGasqValue(ZoneBean zone)throws Exception  {
			//INSETAMOS EN ZONE GAS VALUE LOS  16 PARAMETROS
			// wi
			
			//if( res == 0 ) Both values are equal 
			//else if( res == 1 ) "First Value is greater ";
			//else if( res == -1 )"Second value is greater";
			
			
			BigDecimal maxValue=zone.getWobbeIndexMax();
			BigDecimal minValue=zone.getWobbeIndexMin();
			
			if (existsValues(maxValue, minValue)){
				zone.setParameterCode(Constants.WI);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) <0){	
						throw new Exception("-16");
					}
				}	
				zone.setMaxValue(maxValue);
				zone.setMinValue(minValue);
				int ins3= zoneMapper.insertZoneGasValue(zone);
				if(ins3!=1){
					throw new Exception("-3");		
					
				}
				
				
			}
			
			// CH4		
			maxValue=zone.getMethaneMax();
			minValue=zone.getMethaneMin();
			if (existsValues(maxValue, minValue)){
				zone.setParameterCode(Constants.CH4);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) <0){	
						throw new Exception("-16");
					}
				}	
				zone.setMaxValue(maxValue);
				zone.setMinValue(minValue);
				int ins3= zoneMapper.insertZoneGasValue(zone);
				if(ins3!=1){
					throw new Exception("-4");		
					
				}
			
			}
			
			//O2
			maxValue=zone.getOxygenMax();
			minValue=zone.getOxygenMin();
			if (existsValues(maxValue, minValue)){
				zone.setParameterCode(Constants.O2);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) <0){	
						throw new Exception("-16");
					}
				}	
				zone.setMaxValue(maxValue);
				zone.setMinValue(minValue);
				int ins3= zoneMapper.insertZoneGasValue(zone);
				if(ins3!=1){
					throw new Exception("-5");		
					
				}
			}
			
			//CO2_N
			maxValue=zone.getCarbonDioxideNitrogenMax();
			minValue=zone.getCarbonDioxideNitrogenMin();
			if (existsValues(maxValue, minValue)){
				zone.setParameterCode(Constants.CO2_N);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) <0){	
						throw new Exception("-16");
					}
				}	
				zone.setMaxValue(maxValue);
				zone.setMinValue(minValue);
				int ins3= zoneMapper.insertZoneGasValue(zone);
				if(ins3!=1){
					throw new Exception("-6");		
					
				}
			}
			
			//CO2 .- 330653 (DEFECT)
			 maxValue=zone.getCarbonDioxideMax();
			 minValue=zone.getCarbonDioxideMin();
			 if (existsValues(maxValue, minValue)){
				zone.setParameterCode(Constants.CO2);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) <0){	
						throw new Exception("-16");
					}
				}	
				zone.setMaxValue(maxValue);
				zone.setMinValue(minValue);
				int ins3= zoneMapper.insertZoneGasValue(zone);
				if(ins3!=1){
					throw new Exception("-16");		
					
				}
			 }
			
			
			//S
			 maxValue=zone.getTotalSulphurMax();
			 minValue=zone.getTotalSulphurMin();
			 if (existsValues(maxValue, minValue)){ 
				zone.setParameterCode(Constants.S);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) <0){	
						throw new Exception("-16");
					}
				}	
				zone.setMaxValue(maxValue);
				zone.setMinValue(minValue);
				int ins3= zoneMapper.insertZoneGasValue(zone);
				if(ins3!=1){
					throw new Exception("-7");		
					
				}
			 }
			 
			//OtherParticles
			 maxValue=zone.getOtherParticulatesMax();
			 minValue=zone.getOtherParticulatesMin();
			 if (existsValues(maxValue, minValue)){			 
				zone.setParameterCode(Constants.OtherParticles);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) <0){	
						throw new Exception("-16");
					}
				}	
				zone.setMaxValue(maxValue);
				zone.setMinValue(minValue);
				int ins3= zoneMapper.insertZoneGasValue(zone);
				if(ins3!=1){
					throw new Exception("-8");		
					
				}
			 }
			 
			 
			//HCDewPoint
			 maxValue=zone.getHydrocarbonDewPointMax();
			 minValue=zone.getHydrocarbonDewPointMin();
			 if (existsValues(maxValue, minValue)){			 
				zone.setParameterCode(Constants.HCDewPoint);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) <0){	
						throw new Exception("-16");
					}
				}	
				zone.setMaxValue(maxValue);
				zone.setMinValue(minValue);
				int ins3= zoneMapper.insertZoneGasValue(zone);
				if(ins3!=1){
					throw new Exception("-9");		
					
				}
			 }
			 
			 
			//HV?			 
			maxValue=zone.getGrossCalorificValueMax();
			minValue=zone.getGrossCalorificValueMin();
			if (existsValues(maxValue, minValue)){			
				zone.setParameterCode(Constants.HV);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) <0){	
						throw new Exception("-16");
					}
				}	
				zone.setMaxValue(maxValue);
				zone.setMinValue(minValue);
				int ins3= zoneMapper.insertZoneGasValue(zone);
				if(ins3!=1){
					throw new Exception("-10");		
					
				}
			}
			
			
			//C2
			 maxValue=zone.getC2Max();
			 minValue=zone.getC2Min();
			 
			 if (existsValues(maxValue, minValue)){
				zone.setParameterCode(Constants.C2);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) <0){	
						throw new Exception("-16");
					}
				}	
				zone.setMaxValue(maxValue);
				zone.setMinValue(minValue);
				int ins3= zoneMapper.insertZoneGasValue(zone);
				if(ins3!=1){
					throw new Exception("-11");		
					
				}
			 }
				
			//N2
			 maxValue=zone.getNitrogenMax();
			 minValue=zone.getNitrogenMin();
			 if (existsValues(maxValue, minValue)){
				zone.setParameterCode(Constants.N2);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) <0){	
						throw new Exception("-16");
					}
				}	
				zone.setMaxValue(maxValue);
				zone.setMinValue(minValue);
				int ins3= zoneMapper.insertZoneGasValue(zone);
				if(ins3!=1){
					throw new Exception("-12");		
					
				}
			 }
			
			 
			//H2S
			maxValue=zone.getHydrogenSulfideMax();
			minValue=zone.getHydrogenSulfideMin();
			
			if (existsValues(maxValue, minValue)){
				zone.setParameterCode(Constants.H2S);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) <0){	
						throw new Exception("-16");
					}
				}	
				zone.setMaxValue(maxValue);
				zone.setMinValue(minValue);
				int ins3= zoneMapper.insertZoneGasValue(zone);
				if(ins3!=1){
					throw new Exception("-13");		
					
				}
			}
			
			
			//Hg
			 maxValue=zone.getMercuryMax();
			 minValue=zone.getMercuryMin();
			 if (existsValues(maxValue, minValue)){			 
				zone.setParameterCode(Constants.Hg);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) <0){	
						throw new Exception("-16");
					}
				}	
				zone.setMaxValue(maxValue);
				zone.setMinValue(minValue);
				int ins3= zoneMapper.insertZoneGasValue(zone);
				if(ins3!=1){
					throw new Exception("-14");		
					
				}
			 }
			
			 
			//H2O
			 maxValue=zone.getSteamMax();
			 minValue=zone.getSteamMin();
			 if (existsValues(maxValue, minValue)){
				zone.setParameterCode(Constants.H2O);
				if (maxValue!=null & minValue!= null ){
					if (maxValue.compareTo(minValue) <0){	
						throw new Exception("-16");
					}
				}	
				zone.setMaxValue(maxValue);
				zone.setMinValue(minValue);
				int ins3= zoneMapper.insertZoneGasValue(zone);
				if(ins3!=1){
					throw new Exception("-15");		
					
				}
			 }
			 
			return 0;
		}

		@Override
		public Map<BigDecimal, Object> selectTypesNewZone() {
			
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = zoneMapper.selectTypesNewZone();
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}
		@Override
		public Map<BigDecimal, Object> selectTypesNewQuality() {
			
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
	 		List<ComboFilterNS> list = zoneMapper.selectTypesNewQuality();
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 
		}

		@Override
		public ZoneBean getZone(BigDecimal idn_zone) {
			
			return zoneMapper.getZone(idn_zone);
		}
		
       public Date restarDiasFecha(Date fecha, int dias){
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha); // 
			calendar.add(Calendar.DATE, -dias);  // numero de días a añadir, o restar en caso de días<0
			return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
		 }
}
