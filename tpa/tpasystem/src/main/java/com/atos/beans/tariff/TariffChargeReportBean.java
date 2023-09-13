package com.atos.beans.tariff;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.atos.beans.UserAudBean;

public class TariffChargeReportBean extends UserAudBean implements Serializable{

	private static final long serialVersionUID = -5255211382691787452L;
	
	private BigDecimal idn_tariff_code;
	private String tariff_desc;
	private String tariff_code;
	private BigDecimal idn_tariff_code_detail;
	private String  detail_desc;
	private String  detail_code;
	
	private BigDecimal idn_unit;
	private String  unit_desc;
	
	private BigDecimal idn_tariff_charge_month;
	private BigDecimal idn_tariff_charge;
	private BigDecimal idn_contract;
		
	private BigDecimal quantity;
	private BigDecimal fee;
	private BigDecimal amount;
	private BigDecimal amount_compare;
	private BigDecimal difference;

	private BigDecimal idn_user_group;
	private Date charge_date;
	private Date runTariffDate;
	
	private String  is_quantity_user_filled;
	private String  is_fee_user_filled;
	private String  is_amount_user_filled;
	
	//private String detail_type;
	private BigDecimal idn_system_point_type; // campo oculto indica si el detail es de entrada o de salida
	private String type_code;
	private BigDecimal idn_tariff_charge_compare;
	private String tariff_charge_code;
	
	//parametros llamadas a procedimiento
	private int valid;
	private String user;
	private String language;
	private String error_desc;
	private Integer error_code;
	private String tariff_charge_code_comp;
	//parametros llamadas a procedimiento
	
	private String contract_code;
    private String operation_desc;
    private BigDecimal idn_nueva_cabecera;
    
    //pantalla calculo bac
    private BigDecimal bac_idn_user_group; //shipper
	private BigDecimal bac_idn_tariff_charge;
	private Date bac_shortDate;			
	private String bac_user_group_id; //value que se muestra en el combo shipper
	private String bac_tariff_charge_id;//value que se muestra en el combo Tariff id
	
	private BigDecimal bac_idn_tariff_charge_last;
	private String     bac_code_tariff_charge_last;
	private BigDecimal bac_idn_tariff_charge_comp;
	
	private boolean invoiceSent;
	private BigDecimal reduction;
	private List<TariffChargeDetailBean> itemsDetail;
	private String comments;
	private BigDecimal amount_shipper;
	private BigDecimal amount_operator;
	private String comments_charge_month;

	private String parameter_value_criteria;
	private String parameter_value_mode;
	
	//offshore
	private BigDecimal idn_system; //idn del system activo
	private String pipeline_system_code; //code del system activo
	
	public TariffChargeReportBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
    public BigDecimal getReduction() {
		return reduction;
	}


	public void setReduction(BigDecimal reduction) {
		this.reduction = reduction;
	}


	public String getBac_code_tariff_charge_last() {
		return bac_code_tariff_charge_last;
	}

	public void setBac_code_tariff_charge_last(String bac_code_tariff_charge_last) {
		this.bac_code_tariff_charge_last = bac_code_tariff_charge_last;
	}

	
	public List<TariffChargeDetailBean> getItemsDetail() {
		return itemsDetail;
	}

	public void setItemsDetail(List<TariffChargeDetailBean> itemsDetail) {
		this.itemsDetail = itemsDetail;
	}
	
	public BigDecimal getBac_idn_tariff_charge_last() {
		return bac_idn_tariff_charge_last;
	}

	public void setBac_idn_tariff_charge_last(BigDecimal bac_idn_tariff_charge_last) {
		this.bac_idn_tariff_charge_last = bac_idn_tariff_charge_last;
	}

	public BigDecimal getBac_idn_tariff_charge_comp() {
		return bac_idn_tariff_charge_comp;
	}


	public void setBac_idn_tariff_charge_comp(BigDecimal bac_idn_tariff_charge_comp) {
		this.bac_idn_tariff_charge_comp = bac_idn_tariff_charge_comp;
	}

	public BigDecimal getIdn_tariff_code() {
		return idn_tariff_code;
	}

	public void setIdn_tariff_code(BigDecimal idn_tariff_code) {
		this.idn_tariff_code = idn_tariff_code;
	}

	public String getTariff_desc() {
		return tariff_desc;
	}

	public void setTariff_desc(String tariff_desc) {
		this.tariff_desc = tariff_desc;
	}


	public String getTariff_code() {
		return tariff_code;
	}


	public void setTariff_code(String tariff_code) {
		this.tariff_code = tariff_code;
	}


	public BigDecimal getIdn_tariff_code_detail() {
		return idn_tariff_code_detail;
	}


	public void setIdn_tariff_code_detail(BigDecimal idn_tariff_code_detail) {
		this.idn_tariff_code_detail = idn_tariff_code_detail;
	}


	public String getDetail_desc() {
		return detail_desc;
	}


	public void setDetail_desc(String detail_desc) {
		this.detail_desc = detail_desc;
	}


	public BigDecimal getIdn_unit() {
		return idn_unit;
	}


	public void setIdn_unit(BigDecimal idn_unit) {
		this.idn_unit = idn_unit;
	}


	public String getUnit_desc() {
		return unit_desc;
	}


	public void setUnit_desc(String unit_desc) {
		this.unit_desc = unit_desc;
	}


	public BigDecimal getIdn_tariff_charge_month() {
		return idn_tariff_charge_month;
	}


	public void setIdn_tariff_charge_month(BigDecimal idn_tariff_charge_month) {
		this.idn_tariff_charge_month = idn_tariff_charge_month;
	}


	public BigDecimal getIdn_tariff_charge() {
		return idn_tariff_charge;
	}


	public void setIdn_tariff_charge(BigDecimal idn_tariff_charge) {
		this.idn_tariff_charge = idn_tariff_charge;
	}


	public BigDecimal getIdn_contract() {
		return idn_contract;
	}


	public void setIdn_contract(BigDecimal idn_contract) {
		this.idn_contract = idn_contract;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}


	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}


	public BigDecimal getFee() {
		return fee;
	}


	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getAmount_compare() {
		return amount_compare;
	}

	public void setAmount_compare(BigDecimal amount_compare) {
		this.amount_compare = amount_compare;
	}
	public BigDecimal getDifference() {
		double dif = (amount_shipper==null ? 0.0 : amount_shipper.doubleValue()) - (amount_compare==null ? 0.0 : amount_compare.doubleValue());
		BigDecimal difference = new BigDecimal(dif);
		
		return difference;
	}


	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}


	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}


	public BigDecimal getIdn_system_point_type() {
		return idn_system_point_type;
	}


	public void setIdn_system_point_type(BigDecimal idn_system_point_type) {
		this.idn_system_point_type = idn_system_point_type;
	}


	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public Date getCharge_date() {
		return charge_date;
	}


	public void setCharge_date(Date charge_date) {
		this.charge_date = charge_date;
	}


	public Date getRunTariffDate() {
		return runTariffDate;
	}


	public void setRunTariffDate(Date runTariffDate) {
		this.runTariffDate = runTariffDate;
	}


	public String getIs_quantity_user_filled() {
		return is_quantity_user_filled;
	}


	public void setIs_quantity_user_filled(String is_quantity_user_filled) {
		this.is_quantity_user_filled = is_quantity_user_filled;
	}


	public String getIs_fee_user_filled() {
		return is_fee_user_filled;
	}


	public void setIs_fee_user_filled(String is_fee_user_filled) {
		this.is_fee_user_filled = is_fee_user_filled;
	}


	public String getIs_amount_user_filled() {
		return is_amount_user_filled;
	}


	public void setIs_amount_user_filled(String is_amount_user_filled) {
		this.is_amount_user_filled = is_amount_user_filled;
	}


	public int getValid() {
		return valid;
	}


	public void setValid(int valid) {
		this.valid = valid;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getError_desc() {
		return error_desc;
	}


	public void setError_desc(String error_desc) {
		this.error_desc = error_desc;
	}


	public Integer getError_code() {
		return error_code;
	}


	public void setError_code(Integer error_code) {
		this.error_code = error_code;
	}


	public String getTariff_charge_code_comp() {
		return tariff_charge_code_comp;
	}


	public void setTariff_charge_code_comp(String tariff_charge_code_comp) {
		this.tariff_charge_code_comp = tariff_charge_code_comp;
	}


	public String getContract_code() {
		return contract_code;
	}


	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}


	public String getOperation_desc() {
		return operation_desc;
	}


	public void setOperation_desc(String operation_desc) {
		this.operation_desc = operation_desc;
	}


	public BigDecimal getIdn_nueva_cabecera() {
		return idn_nueva_cabecera;
	}


	public void setIdn_nueva_cabecera(BigDecimal idn_nueva_cabecera) {
		this.idn_nueva_cabecera = idn_nueva_cabecera;
	}


	public BigDecimal getBac_idn_user_group() {
		return bac_idn_user_group;
	}


	public void setBac_idn_user_group(BigDecimal bac_idn_user_group) {
		this.bac_idn_user_group = bac_idn_user_group;
	}


	public BigDecimal getBac_idn_tariff_charge() {
		return bac_idn_tariff_charge;
	}


	public void setBac_idn_tariff_charge(BigDecimal bac_idn_tariff_charge) {
		this.bac_idn_tariff_charge = bac_idn_tariff_charge;
	}


	public Date getBac_shortDate() {
		return bac_shortDate;
	}


	public void setBac_shortDate(Date bac_shortDate) {
		this.bac_shortDate = bac_shortDate;
	}


	public String getBac_user_group_id() {
		return bac_user_group_id;
	}


	public void setBac_user_group_id(String bac_user_group_id) {
		this.bac_user_group_id = bac_user_group_id;
	}


	public String getBac_tariff_charge_id() {
		return bac_tariff_charge_id;
	}


	public void setBac_tariff_charge_id(String bac_tariff_charge_id) {
		this.bac_tariff_charge_id = bac_tariff_charge_id;
	}



	public BigDecimal getIdn_tariff_charge_compare() {
		return idn_tariff_charge_compare;
	}



	public void setIdn_tariff_charge_compare(BigDecimal idn_tariff_charge_compare) {
		this.idn_tariff_charge_compare = idn_tariff_charge_compare;
	}



	public String getTariff_charge_code() {
		return tariff_charge_code;
	}



	public void setTariff_charge_code(String tariff_charge_code) {
		this.tariff_charge_code = tariff_charge_code;
	}



	public boolean isInvoiceSent() {
		return invoiceSent;
	}



	public void setInvoiceSent(boolean invoiceSent) {
		this.invoiceSent = invoiceSent;
	}



	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	

	public String getDetail_code() {
		return detail_code;
	}


	public void setDetail_code(String detail_code) {
		this.detail_code = detail_code;
	}


	public BigDecimal getIdn_system() {
		return idn_system;
	}


	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}


	public String getPipeline_system_code() {
		return pipeline_system_code;
	}


	public void setPipeline_system_code(String pipeline_system_code) {
		this.pipeline_system_code = pipeline_system_code;
	}

	public BigDecimal getAmount_shipper() {
		return amount_shipper;
	}


	public void setAmount_shipper(BigDecimal amount_shipper) {
		this.amount_shipper = amount_shipper;
	}


	public BigDecimal getAmount_operator() {
		return amount_operator;
	}


	public void setAmount_operator(BigDecimal amount_operator) {
		this.amount_operator = amount_operator;
	}


	public String getComments_charge_month() {
		return comments_charge_month;
	}


	public void setComments_charge_month(String comments_charge_month) {
		this.comments_charge_month = comments_charge_month;
	}


	public String getParameter_value_criteria() {
		return parameter_value_criteria;
	}


	public void setParameter_value_criteria(String parameter_value_criteria) {
		this.parameter_value_criteria = parameter_value_criteria;
	}


	public String getParameter_value_mode() {
		return parameter_value_mode;
	}


	public void setParameter_value_mode(String parameter_value_mode) {
		this.parameter_value_mode = parameter_value_mode;
	}

	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"tariff_desc;tariff_code;detail_desc;detail_code;unit_desc;quantity;fee;amount;amount_compare;difference;charge_date;runTariffDate;type_code;tariff_charge_code;tariff_charge_code_comp;contract_code;operation_desc;invoiceSent;reduction;comments;amount_shipper;amount_operator;comments_charge_month;parameter_value_criteria;parameter_value_mode");
		return builder.toString();
	}
	
	public String toCSV() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder builder = new StringBuilder();
		builder.append((tariff_desc==null ? "" : tariff_desc) +";");
		builder.append((tariff_code==null ? "" : tariff_code) +";");
		builder.append((detail_desc==null ? "" : detail_desc) +";");
		builder.append((detail_code==null ? "" : detail_code) +";");
		builder.append((unit_desc==null ? "" : unit_desc) +";");
		builder.append((quantity==null ? "" : quantity.toPlainString()) +";");
		builder.append((fee==null ? "" : fee.doubleValue()) +";");
		builder.append((amount==null ? "" : amount.toPlainString()) +";");
		builder.append((amount_compare==null ? "" : amount_compare.doubleValue()) +";");
		builder.append((difference==null ? "" : difference.doubleValue()) +";");
		builder.append((charge_date==null ? "" : sdf.format(charge_date)) +";");
		builder.append((runTariffDate==null ? "" : sdf.format(runTariffDate)) +";");
		builder.append((type_code==null ? "" : type_code) +";");
		builder.append((tariff_charge_code==null ? "" : tariff_charge_code) +";");
		builder.append((tariff_charge_code_comp==null ? "" : tariff_charge_code_comp) +";");
		builder.append((contract_code==null ? "" : contract_code) +";");
		builder.append((operation_desc==null ? "" : operation_desc) +";");
		builder.append((invoiceSent ? "true" : "false" ) +";");
		builder.append((reduction==null ? "" : reduction.doubleValue()) +";");
		builder.append((comments==null ? "" : comments) +";");
		builder.append((amount_shipper==null ? "" : amount_shipper.toPlainString()) +";");
		builder.append((amount_operator==null ? "" : amount_operator.doubleValue()) +";");
		builder.append((comments_charge_month==null ? "" : comments_charge_month) +";");
		builder.append((parameter_value_criteria==null ? "" : parameter_value_criteria) +";");
		builder.append((parameter_value_mode==null ? "" : parameter_value_mode));
		return builder.toString();
	}


	@Override
	public String toString() {
		return "TariffChargeReportBean [idn_tariff_code=" + idn_tariff_code + ", tariff_desc=" + tariff_desc
				+ ", tariff_code=" + tariff_code + ", idn_tariff_code_detail=" + idn_tariff_code_detail
				+ ", detail_desc=" + detail_desc + ", detail_code=" + detail_code + ", idn_unit=" + idn_unit
				+ ", unit_desc=" + unit_desc + ", idn_tariff_charge_month=" + idn_tariff_charge_month
				+ ", idn_tariff_charge=" + idn_tariff_charge + ", idn_contract=" + idn_contract + ", quantity="
				+ quantity + ", fee=" + fee + ", amount=" + amount + ", amount_compare=" + amount_compare
				+ ", difference=" + difference + ", idn_user_group=" + idn_user_group + ", charge_date=" + charge_date
				+ ", runTariffDate=" + runTariffDate + ", is_quantity_user_filled=" + is_quantity_user_filled
				+ ", is_fee_user_filled=" + is_fee_user_filled + ", is_amount_user_filled=" + is_amount_user_filled
				+ ", idn_system_point_type=" + idn_system_point_type + ", type_code=" + type_code
				+ ", idn_tariff_charge_compare=" + idn_tariff_charge_compare + ", tariff_charge_code="
				+ tariff_charge_code + ", valid=" + valid + ", user=" + user + ", language=" + language
				+ ", error_desc=" + error_desc + ", error_code=" + error_code + ", tariff_charge_code_comp="
				+ tariff_charge_code_comp + ", contract_code=" + contract_code + ", operation_desc=" + operation_desc
				+ ", idn_nueva_cabecera=" + idn_nueva_cabecera + ", bac_idn_user_group=" + bac_idn_user_group
				+ ", bac_idn_tariff_charge=" + bac_idn_tariff_charge + ", bac_shortDate=" + bac_shortDate
				+ ", bac_user_group_id=" + bac_user_group_id + ", bac_tariff_charge_id=" + bac_tariff_charge_id
				+ ", bac_idn_tariff_charge_last=" + bac_idn_tariff_charge_last + ", bac_code_tariff_charge_last="
				+ bac_code_tariff_charge_last + ", bac_idn_tariff_charge_comp=" + bac_idn_tariff_charge_comp
				+ ", invoiceSent=" + invoiceSent + ", reduction=" + reduction + ", itemsDetail=" + itemsDetail
				+ ", comments=" + comments + ", amount_shipper=" + amount_shipper + ", amount_operator="
				+ amount_operator + ", comments_charge_month=" + comments_charge_month + ", parameter_value_criteria="
				+ parameter_value_criteria + ", parameter_value_mode=" + parameter_value_mode + ", idn_system="
				+ idn_system + ", pipeline_system_code=" + pipeline_system_code + "]";
	}
	
}
