package com.atos.beans.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

public class InstructedOperationFlowShipperFileBean implements Serializable{

	private static final long serialVersionUID = -846394731702045603L;
	
	private BigDecimal idn_intraday_gas_flow_file;
	private String file_name;
    private String content_type;
    private byte[] binary_data;
	public BigDecimal getIdn_intraday_gas_flow_file() {
		return idn_intraday_gas_flow_file;
	}
	public void setIdn_intraday_gas_flow_file(BigDecimal idn_intraday_gas_flow_file) {
		this.idn_intraday_gas_flow_file = idn_intraday_gas_flow_file;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getContent_type() {
		return content_type;
	}
	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}
	public byte[] getBinary_data() {
		return binary_data;
	}
	public void setBinary_data(byte[] binary_data) {
		this.binary_data = binary_data;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InstructedOperationFlowShipperFileBean [idn_intraday_gas_flow_file=");
		builder.append(idn_intraday_gas_flow_file);
		builder.append(", file_name=");
		builder.append(file_name);
		builder.append(", content_type=");
		builder.append(content_type);
		builder.append(", binary_data=");
		builder.append(Arrays.toString(binary_data));
		builder.append("]");
		return builder.toString();
	}

    
    
}
