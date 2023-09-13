package com.atos.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atos.beans.AlarmBean;
import com.atos.beans.ElementIdBean;
import com.atos.beans.ScadaAlarmTagNameBean;
import com.atos.mapper.AlarmMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.utils.Constants;
import com.atos.wsdl.DoExecutionResponse;


@Component
public class WSClient implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9067325632463736509L;
	private static final Logger log = LogManager.getLogger(WSClient.class);

	@Autowired
	private ScadaClient client;
	
	@Autowired
	private AlarmMapper alarmMapper;

	@Autowired
	private SystemParameterMapper sysParMapper;	


	public void callWSClient(){
		try {
	        log.info("Connecting Scada webservice...");
	       DoExecutionResponse response = client.getData();
	        //System.out.println("Response: " + response.getDoExecutionReturn());
	        
	      processData(response.getDoExecutionReturn());

	        //String data = "<records><record>14/07/2016 15:30:33;SD4700-SI-5115AI;0<!record><record>14/07/2016 15:30:33;SD4700-SI-5115BI;0<!record><record>14/07/2016 15:30:33;SD4700-SI-5115CI;0<!record><record>14/07/2016 15:30:33;D310-XA-204A;0<!record><record>14/07/2016 15:30:33;D3000-C-001A12;0<!record><record>14/07/2016 15:30:33;D3000-C-001B12;0<!record><record>14/07/2016 15:30:33;D3000-C-001C12;0<!record><record>14/07/2016 15:30:33;D5560-C-001A;0<!record><record>14/07/2016 15:30:33;D5560-C-001B;0<!record><record>14/07/2016 15:30:33;SD4200-UCPC-12;0<!record><record>14/07/2016 15:30:33;SD4200-UCPB-12;0<!record><record>14/07/2016 15:30:33;SD4200-UCPA-12;0<!record><record>14/07/2016 15:30:33;SD4700-X400D1-XL;0<!record><record>14/07/2016 15:30:33;D310-XA-201A;0<!record><record>14/07/2016 15:30:33;D310-XA-202A;0<!record><record>14/07/2016 15:30:33;D310-XA-203A;0<!record><record>14/07/2016 15:30:33;D5560-C-001C;0<!record><record>14/07/2016 15:30:33;D6000-XL-202B;0<!record><record>14/07/2016 15:30:33;D6000-XL-202C;0<!record><record>14/07/2016 15:30:33;D6000-XL-202A;0<!record><record>14/07/2016 15:30:33;CA330-HWI-001;53.3010<!record><record>14/07/2016 15:30:33;CA4500-FR-810;0.0059<!record><record>14/07/2016 15:30:33;NSA4000-SAT-BUF;2144.0000<!record><record>14/07/2016 15:30:33;A632-FR-001;4288.0000<!record><record>14/07/2016 15:30:33;CA690-FR-712H;8990.0000<!record><record>14/07/2016 15:30:33;CA3600-FR-3606N;0.0496<!record><record>14/07/2016 15:30:33;SA506-FI-001;1618.0000<!record><record>14/07/2016 15:30:33;SA403-FR-001;7.8220<!record><record>14/07/2016 15:30:33;A330-H2O-001;3.1136<!record><record>14/07/2016 15:30:33;A4012-FR-001IMP;0.2086<!record><record>14/07/2016 15:30:33;NA4000-MOI-001;0.9815<!record><record>14/07/2016 15:30:33;CA4000-FYT-810;95.1600<!record><record>14/07/2016 15:30:33;NCA4300-ST-001;0.0000<!record><record>14/07/2016 15:30:33;NSA4000-N2-BUF;2144.0000<!record><record>14/07/2016 15:30:33;NSA4000-N2-GC;2144.0000<!record><record>14/07/2016 15:30:33;SA0637-FY-ACC;0.0032<!record><record>14/07/2016 15:30:33;NSA4000-WB-BUF;2144.0000<!record><record>14/07/2016 15:30:33;SA330-SAT-001;1618.0000<!record><record>14/07/2016 15:30:33;NSA4000-WE-GC;2144.0000<!record><record>14/07/2016 15:30:33;NSA4000-CO2-BUF;1618.0000<!record><record>14/07/2016 15:30:33;CA0697-FR-301;0.3614<!record><record>14/07/2016 15:30:33;CA6750-FRS-GUT;12.5600<!record><record>14/07/2016 15:30:33;CA330-C2P-001;13354.0000<!record><record>14/07/2016 15:30:33;A440-FR-005;5.3040<!record><record>14/07/2016 15:30:33;A5500-FT-760;2144.0000<!record><record>14/07/2016 15:30:33;A5500-FT-960;1618.0000<!record><record>14/07/2016 15:30:33;CA630-FI-001;239.7500<!record><record>14/07/2016 15:30:33;A400F060C;2144.0000<!record><record>14/07/2016 15:30:33;CAZMS-FRS-001D;0.3848<!record><record>14/07/2016 15:30:33;CA3506-FI-021V;38832.0000<!record><record>14/07/2016 15:30:33;CA4000-FI-TOTAL;0.2975<!record><record>14/07/2016 15:30:33;CA4450-FQI-TOTAL;11584.0000<!record><record>14/07/2016 15:30:33;A5500-FT-860;1618.0000<!record><record>14/07/2016 15:30:33;CA5920-FR-0141D;0.1507<!record><record>14/07/2016 15:30:33;SA330-CO2-001;1618.0000<!record><record>14/07/2016 15:30:33;NSA4000-CO2-GC;1618.0000<!record><record>14/07/2016 15:30:33;SA4920-WCT-002;2068.0000<!record><record>14/07/2016 15:30:33;A4000-FR-CRNT;7822.0000<!record><record>14/07/2016 15:30:33;SA4920-WCT-001;1618.0000<!record><record>14/07/2016 15:30:33;SA5920-CO2-GC;1618.0000<!record><record>14/07/2016 15:30:33;SA5920-WOB-GC;2068.0000<!record><record>14/07/2016 15:30:33;SA5920-GCV-GC;1618.0000<!record><record>14/07/2016 15:30:33;NSA4000-GCV-GC;2144.0000<!record><record>14/07/2016 15:30:33;A400RUN123C;2144.0000<!record><!records>";
	      
	       /* String data = "<records><record>01-04-2019 15:30:33.2;SA5010-GHVS-1683;1000</record>"
	      	   + "<record>01-04-2019 15:30:33.4;SA5010-GHVS-1684;500</record>"
	      	   + "<record>01-04-2019 15:30:33.6;SA5010-TI-1660;160</record>"
	      	   + "<record>01-04-2019 15:30:33.7;D200-CP-010A;0</record>"
	      	   + "<record>01-04-2019 15:30:33.8;D200-CP-010B;1</record>"
	      	   + "<record>01-04-2019 15:30:33.9;PEPITO;1</record>"	   
	      	   + "</records>";

	        processData(data);*/
		} catch(Exception e) {
			log.error(e.getMessage(),e);
			throw e;
		}
		
	}
	
	private void processData(String data){
		if(data==null || data.length()==0){

			log.error("no data in response " + data);
			return;
		}
		else {
			int begin = data.indexOf("<records>");
			int end = data.indexOf("</records>");
			end = end + 10;
			if (begin > -1 && end > 10) {
				data = data.substring(begin, end);
			}
		}
		if(!data.startsWith("<records>")){
			log.error("incorrect data format " + data);
			return;
		}
        if(data.length()>0 && !data.equals("<records></records>")){

        	List<ScadaAlarmTagNameBean> list_beans = alarmMapper.getScadaTagNameInfo();
        	HashMap<String,ScadaAlarmTagNameBean> map = new HashMap<String,ScadaAlarmTagNameBean>();
        	for(int i=0;i<list_beans.size();i++){
        		map.put(list_beans.get(i).getScada_tag_name(), list_beans.get(i));
        	}
        	
        	data = data.substring(9);
    		String[] blocks = data.split("</records>");
    		for(int j=0;j<blocks.length;j++){
    			String data_block = blocks[j];
    			
    			data_block = data_block.substring(data_block.indexOf("<record>")+8);
    			data_block = data_block.substring(0, data_block.lastIndexOf("</record>"));
	        	
	        	String[] registers = data_block.split("</record><record>");
	        	for(int i=0;i<registers.length;i++){
	        		String[] parts = registers[i].split(";");
	        		
	        		ScadaAlarmTagNameBean scadaBean = map.get(parts[1]);
	        		if(scadaBean!=null){
	        			//CH717 (1196114) 
	        			//if(checkAlarmValues(scadaBean, new BigDecimal(parts[2]))==0){
	        			String alarmType=checkAlarmValues(scadaBean, new BigDecimal(parts[2]));
	        			if(alarmType==null){
	        				log.info("Value between thresholds. It is not an alarm: "+ registers[i]);
	        				continue;
	        			}
	        			AlarmBean bean = new AlarmBean();
	        			
	        			bean.setAlarm_type(alarmType);//CH717 (1196114) 
	        			
	        			bean.setIdn_scada_tag_name(scadaBean.getIdn_scada_tag_name());
	        			try {
							Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S").parse(parts[0]);
							bean.setScada_timestamp(date);
						} catch (ParseException e) {
							log.error("Wrong date format "+ parts[0] + " in register: " + registers[i]);
							e.printStackTrace();
						}
	        			bean.setScada_tag_value(new BigDecimal(parts[2]));
	        			
	        			try {
		        			bean.setAlarm_id(this.getNewAlarmId());
							System.out.println("insertando " + bean.toString());
		        			alarmMapper.insertScadaAlarm(bean);
		        			log.info("New scada alarm inserted: "+ registers[i]);
							System.out.println("inserted: " + bean.toString());
	        			}
	        			catch(Exception e){
	        				log.error(e.getMessage(), e);
	        			}

	        		} else {
	        			log.warn("Scada tag name not exits: "+ parts[1] + " in register: " + registers[i]);
	        		}
	        		
	        	}
    		}
		} else {
			log.info("no data recived");

        }

		
	}

	private String getNewAlarmId() throws Exception {
		
		ElementIdBean tmpBean = new ElementIdBean();
		tmpBean.setGenerationCode(Constants.ALARM);
		// Si se deja la fecha a nulo, en BD se toma systemdate.
		tmpBean.setDate(null);
		// Por ser un proceso automatico no va a tener usuario de acceso a la aplicacion. Le ponemos el inicial 'manager'.
		tmpBean.setUser("manager");
		tmpBean.setLanguage("en");

		sysParMapper.getElementId(tmpBean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(tmpBean == null || (tmpBean.getIntegerExit() != 0))
			throw new Exception(tmpBean.getErrorDesc());
		
		return tmpBean.getId();
	}
	
	/*
	 * 382069 - P2/CH107 - Cambio en la codificacion de los IDs private String
	 * getAlarmIdSequential(){ Calendar cal = Calendar.getInstance();
	 * 
	 * String alarm_id = cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH)<9 ?
	 * "0" + (cal.get(Calendar.MONTH)+1) : ""+(cal.get(Calendar.MONTH)+1)) +
	 * (cal.get(Calendar.DAY_OF_MONTH)<10 ? "0" + cal.get(Calendar.DAY_OF_MONTH)
	 * : ""+cal.get(Calendar.DAY_OF_MONTH)) + ".";
	 * 
	 * List<String> list_codes = alarmMapper.getAlarmIdSecuential(alarm_id +
	 * "%"); if(list_codes.size()==0){ alarm_id = alarm_id + "00001"; } else {
	 * String code = list_codes.get(0); String subcode = code.substring(9); int
	 * num = (new Integer(subcode)).intValue() + 1; alarm_id = alarm_id +
	 * String.format("%05d", num); }
	 * 
	 * return alarm_id; }
	 */
	
	//CH717 (1196114) se cambia por otra ..
	private int checkAlarmValues_old(ScadaAlarmTagNameBean bean, BigDecimal value){
		
		// we have min and max
		if(bean.getMin_alarm_threshold()!=null && bean.getMax_alarm_threshold()!=null){
			// if value <= min or value >= max, then is an alarm
			if(value.compareTo(bean.getMin_alarm_threshold())<=0 || value.compareTo(bean.getMax_alarm_threshold()) >=0){
				return 1;
			} else {
				// else, value is between min and max
				return 0;
			}
		}
		// we only have min or max
		if(bean.getMin_alarm_threshold()!=null || bean.getMax_alarm_threshold()!=null){
			// if we only have min 
			if(bean.getMin_alarm_threshold()!=null){
				// and value <= min, then is an alarm
				if(value.compareTo(bean.getMin_alarm_threshold())<=0){
					return 1;
				} else {
					// else, value is > min
					return 0;
				}
			}
			// if we only have max 
			if(bean.getMax_alarm_threshold()!=null){
				// and value >= max, then is an alarm
				if(value.compareTo(bean.getMax_alarm_threshold())>=0){
					return 1;
				} else {
					// else, value is < max
					return 0;
				}
			}
		}
		// we don't have min or max. 
		if(bean.getMin_alarm_threshold()==null && bean.getMax_alarm_threshold()==null){
			// we check if we have on/off value
			if(bean.getOn_off_alarm_threshold()!=null){
				// if value = on/off, then we have an alarm
				if(value.intValue()==bean.getOn_off_alarm_threshold().intValue()){
					return 1;
				} else {
					// else we don't have alarm
					return 0;
				}
			} else {
				// there is an error in the configuration of the tag_name in database
				log.error("Scada tag name if not properly configured. We need min, max or on/off value to check the alarms: " + bean.toString());
			}
		}
		
		// if we return 0, is not an alarm
		return 0;
	}
	
//CH717 (1196114) 
private String checkAlarmValues(ScadaAlarmTagNameBean bean, BigDecimal value){

	//firstBigDecimal.compareTo(secondBigDecimal) < 0 // "<"
	//firstBigDecimal.compareTo(secondBigDecimal) > 0 // ">"    
	//firstBigDecimal.compareTo(secondBigDecimal) == 0 // "=="  
	//firstBigDecimal.compareTo(secondBigDecimal) >= 0 // ">="   

	if(bean.getIs_binary().equals("N")){
			if(bean.getMin_alarm_threshold()!=null && value.compareTo(bean.getMin_alarm_threshold())<=0){
					return "LOW";
			} else if(bean.getMax_alarm_threshold()!=null && value.compareTo(bean.getMax_alarm_threshold())>=0){
					return "HIGH";
				}else{
					return null;
				}
		}else{
			if(value.compareTo(BigDecimal.ZERO)==0){
				return "OFF";
			}else{
				return "ON";
			}
			
		}
	
	}

	public ScadaClient getClient() {
		return client;
	}

	public void setClient(ScadaClient client) {
		this.client = client;
	}

	public AlarmMapper getAlarmMapper() {
		return alarmMapper;
	}

	public void setAlarmMapper(AlarmMapper alarmMapper) {
		this.alarmMapper = alarmMapper;
	}

	public SystemParameterMapper getSysParMapper() {
		return sysParMapper;
	}

	public void setSysParMapper(SystemParameterMapper sysParMapper) {
		this.sysParMapper = sysParMapper;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
