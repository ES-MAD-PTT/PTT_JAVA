package com.atos.beans.csv;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.atos.beans.booking.ContractAttachmentBean;

public class ContractQueryCSVBean implements Serializable{

	private static final long serialVersionUID = -1530618884035858590L;
	
	private String request_code;
	private String shipper;
	private String contract_type;
	private String contract_code;
	private Date start_date;
	private Date end_date;
	private BigDecimal shadow_time;
	private BigDecimal shadow_period;
	private String zone;
	private String area;
	private String subarea;
	private String block_valve;
	private String entryMeterId;
	private String entryPoint;
	private String new_connection;
	private BigDecimal pr_max;
	private BigDecimal pr_min;
	private BigDecimal temperature_range_max;
	private BigDecimal temperature_range_min;
	private Date year;
	
	private String shipperName;
	private Date submittedTimestamp;
	private String xlsFileName;
	private String submissionComments;
	private BigDecimal idn_contract_request;
	private List<String> additionalDocs;	
	private Date acceptanceTimestamp;
	private String managementComments;
	
	private BigDecimal quantity;
	private BigDecimal max_hour_qty;
	private BigDecimal volume;
	private BigDecimal max_hour_vol;
	private BigDecimal hv_min;
	private BigDecimal hv_max;
	private BigDecimal wi_min;
	private BigDecimal wi_max;
	private BigDecimal c2_min;
	private BigDecimal c2_max;
	private BigDecimal co2_min;
	private BigDecimal co2_max;
	private BigDecimal o2_min;
	private BigDecimal o2_max;
	private BigDecimal n2_min;
	private BigDecimal n2_max;
	private BigDecimal h2s_min;
	private BigDecimal h2s_max;
	private BigDecimal s_min;
	private BigDecimal s_max;
	private BigDecimal hg_min;
	private BigDecimal hg_max;
	private BigDecimal h2o_min;
	private BigDecimal h2o_max;
	private BigDecimal hcdewpoint_min;
	private BigDecimal hcdewpoint_max;
	public String getRequest_code() {
		return request_code;
	}
	public void setRequest_code(String request_code) {
		this.request_code = request_code;
	}
	public String getShipper() {
		return shipper;
	}
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}
	public String getContract_type() {
		return contract_type;
	}
	public void setContract_type(String contract_type) {
		this.contract_type = contract_type;
	}
	public String getContract_code() {
		return contract_code;
	}
	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public BigDecimal getShadow_time() {
		return shadow_time;
	}
	public void setShadow_time(BigDecimal shadow_time) {
		this.shadow_time = shadow_time;
	}
	public BigDecimal getShadow_period() {
		return shadow_period;
	}
	public void setShadow_period(BigDecimal shadow_period) {
		this.shadow_period = shadow_period;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getSubarea() {
		return subarea;
	}
	public void setSubarea(String subarea) {
		this.subarea = subarea;
	}
	public String getBlock_valve() {
		return block_valve;
	}
	public void setBlock_valve(String block_valve) {
		this.block_valve = block_valve;
	}
	public String getEntryMeterId() {
		return entryMeterId;
	}
	public void setEntryMeterId(String entryMeterId) {
		this.entryMeterId = entryMeterId;
	}
	public String getEntryPoint() {
		return entryPoint;
	}
	public void setEntryPoint(String entryPoint) {
		this.entryPoint = entryPoint;
	}
	public String getNew_connection() {
		return new_connection;
	}
	public void setNew_connection(String new_connection) {
		this.new_connection = new_connection;
	}
	public BigDecimal getPr_max() {
		return pr_max;
	}
	public void setPr_max(BigDecimal pr_max) {
		this.pr_max = pr_max;
	}
	public BigDecimal getPr_min() {
		return pr_min;
	}
	public void setPr_min(BigDecimal pr_min) {
		this.pr_min = pr_min;
	}
	public BigDecimal getTemperature_range_max() {
		return temperature_range_max;
	}
	public void setTemperature_range_max(BigDecimal temperature_range_max) {
		this.temperature_range_max = temperature_range_max;
	}
	public BigDecimal getTemperature_range_min() {
		return temperature_range_min;
	}
	public void setTemperature_range_min(BigDecimal temperature_range_min) {
		this.temperature_range_min = temperature_range_min;
	}
	public Date getYear() {
		return year;
	}
	public void setYear(Date year) {
		this.year = year;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getMax_hour_qty() {
		return max_hour_qty;
	}
	public void setMax_hour_qty(BigDecimal max_hour_qty) {
		this.max_hour_qty = max_hour_qty;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public BigDecimal getMax_hour_vol() {
		return max_hour_vol;
	}
	public void setMax_hour_vol(BigDecimal max_hour_vol) {
		this.max_hour_vol = max_hour_vol;
	}
	public BigDecimal getHv_min() {
		return hv_min;
	}
	public void setHv_min(BigDecimal hv_min) {
		this.hv_min = hv_min;
	}
	public BigDecimal getHv_max() {
		return hv_max;
	}
	public void setHv_max(BigDecimal hv_max) {
		this.hv_max = hv_max;
	}
	public BigDecimal getWi_min() {
		return wi_min;
	}
	public void setWi_min(BigDecimal wi_min) {
		this.wi_min = wi_min;
	}
	public BigDecimal getWi_max() {
		return wi_max;
	}
	public void setWi_max(BigDecimal wi_max) {
		this.wi_max = wi_max;
	}
	public BigDecimal getC2_min() {
		return c2_min;
	}
	public void setC2_min(BigDecimal c2_min) {
		this.c2_min = c2_min;
	}
	public BigDecimal getC2_max() {
		return c2_max;
	}
	public void setC2_max(BigDecimal c2_max) {
		this.c2_max = c2_max;
	}
	public BigDecimal getCo2_min() {
		return co2_min;
	}
	public void setCo2_min(BigDecimal co2_min) {
		this.co2_min = co2_min;
	}
	public BigDecimal getCo2_max() {
		return co2_max;
	}
	public void setCo2_max(BigDecimal co2_max) {
		this.co2_max = co2_max;
	}
	public BigDecimal getO2_min() {
		return o2_min;
	}
	public void setO2_min(BigDecimal o2_min) {
		this.o2_min = o2_min;
	}
	public BigDecimal getO2_max() {
		return o2_max;
	}
	public void setO2_max(BigDecimal o2_max) {
		this.o2_max = o2_max;
	}
	public BigDecimal getN2_min() {
		return n2_min;
	}
	public void setN2_min(BigDecimal n2_min) {
		this.n2_min = n2_min;
	}
	public BigDecimal getN2_max() {
		return n2_max;
	}
	public void setN2_max(BigDecimal n2_max) {
		this.n2_max = n2_max;
	}
	public BigDecimal getH2s_min() {
		return h2s_min;
	}
	public void setH2s_min(BigDecimal h2s_min) {
		this.h2s_min = h2s_min;
	}
	public BigDecimal getH2s_max() {
		return h2s_max;
	}
	public void setH2s_max(BigDecimal h2s_max) {
		this.h2s_max = h2s_max;
	}
	public BigDecimal getS_min() {
		return s_min;
	}
	public void setS_min(BigDecimal s_min) {
		this.s_min = s_min;
	}
	public BigDecimal getS_max() {
		return s_max;
	}
	public void setS_max(BigDecimal s_max) {
		this.s_max = s_max;
	}
	public BigDecimal getHg_min() {
		return hg_min;
	}
	public void setHg_min(BigDecimal hg_min) {
		this.hg_min = hg_min;
	}
	public BigDecimal getHg_max() {
		return hg_max;
	}
	public void setHg_max(BigDecimal hg_max) {
		this.hg_max = hg_max;
	}
	public BigDecimal getH2o_min() {
		return h2o_min;
	}
	public void setH2o_min(BigDecimal h2o_min) {
		this.h2o_min = h2o_min;
	}
	public BigDecimal getH2o_max() {
		return h2o_max;
	}
	public void setH2o_max(BigDecimal h2o_max) {
		this.h2o_max = h2o_max;
	}
	public BigDecimal getHcdewpoint_min() {
		return hcdewpoint_min;
	}
	public void setHcdewpoint_min(BigDecimal hcdewpoint_min) {
		this.hcdewpoint_min = hcdewpoint_min;
	}
	public BigDecimal getHcdewpoint_max() {
		return hcdewpoint_max;
	}
	public void setHcdewpoint_max(BigDecimal hcdewpoint_max) {
		this.hcdewpoint_max = hcdewpoint_max;
	}
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	public Date getSubmittedTimestamp() {
		return submittedTimestamp;
	}
	public void setSubmittedTimestamp(Date submittedTimestamp) {
		this.submittedTimestamp = submittedTimestamp;
	}
	public String getXlsFileName() {
		return xlsFileName;
	}
	public void setXlsFileName(String xlsFileName) {
		this.xlsFileName = xlsFileName;
	}
	public String getSubmissionComments() {
		return submissionComments;
	}
	public void setSubmissionComments(String submissionComments) {
		this.submissionComments = submissionComments;
	}
	public BigDecimal getIdn_contract_request() {
		return idn_contract_request;
	}
	public void setIdn_contract_request(BigDecimal idn_contract_request) {
		this.idn_contract_request = idn_contract_request;
	}
	public Date getAcceptanceTimestamp() {
		return acceptanceTimestamp;
	}
	public void setAcceptanceTimestamp(Date acceptanceTimestamp) {
		this.acceptanceTimestamp = acceptanceTimestamp;
	}
	public String getManagementComments() {
		return managementComments;
	}
	public void setManagementComments(String managementComments) {
		this.managementComments = managementComments;
	}
	public List<String> getAdditionalDocs() {
		return additionalDocs;
	}
	public void setAdditionalDocs(List<String> additionalDocs) {
		this.additionalDocs = additionalDocs;
	}
	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"request_code;submittedTimestamp;shipper;shipperName;contract_type;contract_code;start_date;end_date;xlsFileName;submissionComments;additionalDocs;managementComments;shadow_time;shadow_period;acceptanceTimestamp;zone;area;subarea;block_valve;entryMeterId;entryPoint;new_connection;pr_max;pr_min;temperature_range_max;temperature_range_min;year;quantity;max_hour_qty;volume;max_hour_vol;hv_min;hv_max;wi_min;wi_max;c2_min;c2_max;co2_min;co2_max;o2_min;o2_max;n2_min;n2_max;h2s_min;h2s_max;s_min;s_max;hg_min;hg_max;h2o_min;h2o_max;hcdewpoint_min;hcdewpoint_max");
		return builder.toString();
	}
	
	public String toCSV() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder builder = new StringBuilder();
		builder.append((request_code==null ? "" : request_code) +";");
		builder.append((submittedTimestamp==null ? "" : sdf.format(submittedTimestamp)) +";");
		builder.append((shipper==null ? "" : shipper) +";");
		builder.append((shipperName==null ? "" : shipperName) +";");
		builder.append((contract_type==null ? "" : contract_type) +";");
		builder.append((contract_code==null ? "" : contract_code) +";");
		builder.append((start_date==null ? "" : sdf.format(start_date)) +";");
		builder.append((end_date==null ? "" : sdf.format(end_date)) +";");
		builder.append((xlsFileName==null ? "" : xlsFileName) +";");
		//builder.append((submissionComments==null ? "" : this.replace(submissionComments))+";");
		//submissionComments
		builder.append("See comments at PTT platform;");
		builder.append(listAddionalDocs()+";");
		builder.append((managementComments==null ? "" : managementComments) +";");
		builder.append((shadow_time==null ? "" : shadow_time.intValue()) +";");
		builder.append((shadow_period==null ? "" : shadow_period.intValue()) +";");
		builder.append((acceptanceTimestamp==null ? "" : sdf.format(acceptanceTimestamp)) +";");
		builder.append((zone==null ? "" : zone) +";");
		builder.append((area==null ? "" : area) +";");
		builder.append((subarea==null ? "" : subarea) +";");
		builder.append((block_valve==null ? "" : block_valve) +";");
		builder.append((entryMeterId==null ? "" : entryMeterId) +";");
		builder.append((entryPoint==null ? "" : entryPoint) +";");
		builder.append((new_connection==null ? "" : new_connection) +";");
		builder.append((pr_max==null ? "" : pr_max.intValue()) +";");
		builder.append((pr_min==null ? "" : pr_min.intValue()) +";");
		builder.append((temperature_range_max==null ? "" : temperature_range_max.intValue()) +";");
		builder.append((temperature_range_min==null ? "" : temperature_range_min.intValue()) +";");
		builder.append((year==null ? "" : sdf.format(year)) +";");
		builder.append((quantity==null ? "" : quantity.doubleValue()) +";");
		builder.append((max_hour_qty==null ? "" : max_hour_qty.doubleValue()) +";");
		builder.append((volume==null ? "" : volume.doubleValue()) +";");
		builder.append((max_hour_vol==null ? "" : max_hour_vol.doubleValue()) +";");
		builder.append((hv_min==null ? "" : hv_min.doubleValue()) +";");
		builder.append((hv_max==null ? "" : hv_max.doubleValue()) +";");
		builder.append((wi_min==null ? "" : wi_min.doubleValue()) +";");
		builder.append((wi_max==null ? "" : wi_max.doubleValue()) +";");
		builder.append((c2_min==null ? "" : c2_min.doubleValue()) +";");
		builder.append((c2_max==null ? "" : c2_max.doubleValue()) +";");
		builder.append((co2_min==null ? "" : co2_min.doubleValue()) +";");
		builder.append((co2_max==null ? "" : co2_max.doubleValue()) +";");
		builder.append((o2_min==null ? "" : o2_min.doubleValue()) +";");
		builder.append((o2_max==null ? "" : o2_max.doubleValue()) +";");
		builder.append((n2_min==null ? "" : n2_min.doubleValue()) +";");
		builder.append((n2_max==null ? "" : n2_max.doubleValue()) +";");
		builder.append((h2s_min==null ? "" : h2s_min.doubleValue()) +";");
		builder.append((h2s_max==null ? "" : h2s_max.doubleValue()) +";");
		builder.append((s_min==null ? "" : s_min.doubleValue()) +";");
		builder.append((s_max==null ? "" : s_max.doubleValue()) +";");
		builder.append((hg_min==null ? "" : hg_min.doubleValue()) +";");
		builder.append((hg_max==null ? "" : hg_max.doubleValue()) +";");
		builder.append((h2o_min==null ? "" : h2o_min.doubleValue()) +";");
		builder.append((h2o_max==null ? "" : h2o_max.doubleValue()) +";");
		builder.append((hcdewpoint_min==null ? "" : hcdewpoint_min.doubleValue()) +";");
		builder.append((hcdewpoint_max==null ? "" : hcdewpoint_max.doubleValue()));
		return builder.toString();
	}

	private String replace(String text) {
		text = text.replaceAll("\r", " ");
		text = text.replaceAll("\n", " ");
		text = text.replaceAll("\t", " ");
		return text;
	}

	private String listAddionalDocs() {
		String text = "";
		if(additionalDocs==null || additionalDocs.size()==0) {
			return text;
		} else {
			for(int i=0;i<additionalDocs.size();i++) {
				String s = additionalDocs.get(i);
				if(i==0) {
					text = text + s;
				} else {
					text = ", " + text + s;
				}
			}
			return text;
		}
	}
	
}
