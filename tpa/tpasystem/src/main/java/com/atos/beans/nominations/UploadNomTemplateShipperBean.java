package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

import com.atos.beans.UserAudBean;

public class UploadNomTemplateShipperBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_nom_template_contract;
	private String shipperGroupId;
	private String contractId;
	private String operation_desc;
	private String document_name;
	private byte[] binary_data;
	private String comments;	
	
	
	
	public UploadNomTemplateShipperBean() {
		super();
	}

	public UploadNomTemplateShipperBean(BigDecimal idn_nom_template_contract, String shipperGroupId, String contractId, String operation_desc, String document_name, byte[] binary_data, String comments) {
		super();
		this.idn_nom_template_contract = idn_nom_template_contract;
		this.shipperGroupId = shipperGroupId;
		this.operation_desc = operation_desc;
		this.document_name = document_name;
		this.binary_data = binary_data;
		this.comments = comments;
	}
	
	

	public BigDecimal getIdn_nom_template_contract() {
		return idn_nom_template_contract;
	}

	public void setIdn_nom_template_contract(BigDecimal idn_nom_template_contract) {
		this.idn_nom_template_contract = idn_nom_template_contract;
	}

	public String getShipperGroupId() {
		return shipperGroupId;
	}

	public void setShipperGroupId(String shipperGroupId) {
		this.shipperGroupId = shipperGroupId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getOperation_desc() {
		return operation_desc;
	}

	public void setOperation_desc(String operation_desc) {
		this.operation_desc = operation_desc;
	}

	public String getDocument_name() {
		return document_name;
	}

	public void setDocument_name(String document_name) {
		this.document_name = document_name;
	}

	public byte[] getBinary_data() {
		return binary_data;
	}

	public void setBinary_data(byte[] binary_data) {
		this.binary_data = binary_data;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "UploadNomTemplateShipperBean [idn_nom_template_contract=" + idn_nom_template_contract
				+ ", shipperGroupId=" + shipperGroupId + ", contractId=" + contractId + ", operation_desc="
				+ operation_desc + ", document_name=" + document_name + ", binary_data=" + Arrays.toString(binary_data)
				+ ", comments=" + comments + "]";
	}

}
