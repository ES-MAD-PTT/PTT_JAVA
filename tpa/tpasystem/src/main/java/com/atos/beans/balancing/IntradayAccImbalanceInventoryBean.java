package com.atos.beans.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class IntradayAccImbalanceInventoryBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5255211382691787452L;

	private String id;	
	private Date versionDate;
	private BigDecimal e_total_inventory_val;
	private String e_total_inventory_colour;
	private BigDecimal w_total_inventory_val;
	private String w_total_inventory_colour;
	private BigDecimal e_base_inventory_val;
	private String e_base_inventory_colour;
	private BigDecimal w_base_inventory_val;
	private String w_base_inventory_colour;
	private BigDecimal e_inventory_operator_val;
	private String e_inventory_operator_colour;
	private BigDecimal w_inventory_operator_val;
	private String w_inventory_operator_colour;
	private BigDecimal e_other_shippers_val;
	private String e_other_shippers_colour;
	private BigDecimal w_other_shippers_val;
	private String w_other_shippers_colour;
	private BigDecimal e_others_val;
	private String e_others_colour;
	private BigDecimal w_others_val;
	private String w_others_colour;
	private BigDecimal e_pttshipper_val;
	private String e_pttshipper_colour;
	private BigDecimal w_pttshipper_val;
	private String w_pttshipper_colour;
	private String e_zoneMode_val;
	private String w_zoneMode_val;


	public IntradayAccImbalanceInventoryBean() {
		super();
	}
	
	public IntradayAccImbalanceInventoryBean(String id, Date versionDate,
			BigDecimal e_total_inventory_val, String e_total_inventory_colour, BigDecimal w_total_inventory_val,
			String w_total_inventory_colour, BigDecimal e_base_inventory_val, String e_base_inventory_colour,
			BigDecimal w_base_inventory_val, String w_base_inventory_colour, BigDecimal e_inventory_operator_val,
			String e_inventory_operator_colour, BigDecimal w_inventory_operator_val, String w_inventory_operator_colour,
			BigDecimal e_other_shippers_val, String e_other_shippers_colour, BigDecimal w_other_shippers_val,
			String w_other_shippers_colour, BigDecimal e_others_val, String e_others_colour, BigDecimal w_others_val,
			String w_others_colour, BigDecimal e_pttShipper_val, String e_pttshipper_colour,
			BigDecimal w_pttshipper_val, String w_pttshipper_colour, String e_zoneMode_val,
			String w_zoneMode_val) {
		super();
		this.id = id;
		this.versionDate = versionDate;
		this.e_total_inventory_val = e_total_inventory_val;
		this.e_total_inventory_colour = e_total_inventory_colour;
		this.w_total_inventory_val = w_total_inventory_val;
		this.w_total_inventory_colour = w_total_inventory_colour;
		this.e_base_inventory_val = e_base_inventory_val;
		this.e_base_inventory_colour = e_base_inventory_colour;
		this.w_base_inventory_val = w_base_inventory_val;
		this.w_base_inventory_colour = w_base_inventory_colour;
		this.e_inventory_operator_val = e_inventory_operator_val;
		this.e_inventory_operator_colour = e_inventory_operator_colour;
		this.w_inventory_operator_val = w_inventory_operator_val;
		this.w_inventory_operator_colour = w_inventory_operator_colour;
		this.e_other_shippers_val = e_other_shippers_val;
		this.e_other_shippers_colour = e_other_shippers_colour;
		this.w_other_shippers_val = w_other_shippers_val;
		this.w_other_shippers_colour = w_other_shippers_colour;
		this.e_others_val = e_others_val;
		this.e_others_colour = e_others_colour;
		this.w_others_val = w_others_val;
		this.w_others_colour = w_others_colour;
		this.e_pttshipper_val = e_pttShipper_val;
		this.e_pttshipper_colour = e_pttshipper_colour;
		this.w_pttshipper_val = w_pttshipper_val;
		this.w_pttshipper_colour = w_pttshipper_colour;
		this.e_zoneMode_val = e_zoneMode_val;
		this.w_zoneMode_val = w_zoneMode_val;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}

	public BigDecimal getE_total_inventory_val() {
		return e_total_inventory_val;
	}

	public void setE_total_inventory_val(BigDecimal e_total_inventory_val) {
		this.e_total_inventory_val = e_total_inventory_val;
	}

	public String getE_total_inventory_colour() {
		return e_total_inventory_colour;
	}

	public void setE_total_inventory_colour(String e_total_inventory_colour) {
		this.e_total_inventory_colour = e_total_inventory_colour;
	}

	public BigDecimal getW_total_inventory_val() {
		return w_total_inventory_val;
	}

	public void setW_total_inventory_val(BigDecimal w_total_inventory_val) {
		this.w_total_inventory_val = w_total_inventory_val;
	}

	public String getW_total_inventory_colour() {
		return w_total_inventory_colour;
	}

	public void setW_total_inventory_colour(String w_total_inventory_colour) {
		this.w_total_inventory_colour = w_total_inventory_colour;
	}

	public BigDecimal getE_base_inventory_val() {
		return e_base_inventory_val;
	}

	public void setE_base_inventory_val(BigDecimal e_base_inventory_val) {
		this.e_base_inventory_val = e_base_inventory_val;
	}

	public String getE_base_inventory_colour() {
		return e_base_inventory_colour;
	}

	public void setE_base_inventory_colour(String e_base_inventory_colour) {
		this.e_base_inventory_colour = e_base_inventory_colour;
	}

	public BigDecimal getW_base_inventory_val() {
		return w_base_inventory_val;
	}

	public void setW_base_inventory_val(BigDecimal w_base_inventory_val) {
		this.w_base_inventory_val = w_base_inventory_val;
	}

	public String getW_base_inventory_colour() {
		return w_base_inventory_colour;
	}
	
	public void setW_base_inventory_colour(String w_base_inventory_colour) {
		this.w_base_inventory_colour = w_base_inventory_colour;
	}

	public BigDecimal getE_inventory_operator_val() {
		return e_inventory_operator_val;
	}

	public void setE_inventory_operator_val(BigDecimal e_inventory_operator_val) {
		this.e_inventory_operator_val = e_inventory_operator_val;
	}

	public String getE_inventory_operator_colour() {
		return e_inventory_operator_colour;
	}

	public void setE_inventory_operator_colour(String e_inventory_operator_colour) {
		this.e_inventory_operator_colour = e_inventory_operator_colour;
	}

	public BigDecimal getW_inventory_operator_val() {
		return w_inventory_operator_val;
	}

	public void setW_inventory_operator_val(BigDecimal w_inventory_operator_val) {
		this.w_inventory_operator_val = w_inventory_operator_val;
	}

	public String getW_inventory_operator_colour() {
		return w_inventory_operator_colour;
	}

	public void setW_inventory_operator_colour(String w_inventory_operator_colour) {
		this.w_inventory_operator_colour = w_inventory_operator_colour;
	}

	public BigDecimal getE_other_shippers_val() {
		return e_other_shippers_val;
	}

	public void setE_other_shippers_val(BigDecimal e_other_shippers_val) {
		this.e_other_shippers_val = e_other_shippers_val;
	}

	public String getE_other_shippers_colour() {
		return e_other_shippers_colour;
	}

	public void setE_other_shippers_colour(String e_other_shippers_colour) {
		this.e_other_shippers_colour = e_other_shippers_colour;
	}

	public BigDecimal getW_other_shippers_val() {
		return w_other_shippers_val;
	}

	public void setW_other_shippers_val(BigDecimal w_other_shippers_val) {
		this.w_other_shippers_val = w_other_shippers_val;
	}

	public String getW_other_shippers_colour() {
		return w_other_shippers_colour;
	}

	public void setW_other_shippers_colour(String w_other_shippers_colour) {
		this.w_other_shippers_colour = w_other_shippers_colour;
	}

	public BigDecimal getE_others_val() {
		return e_others_val;
	}

	public void setE_others_val(BigDecimal e_others_val) {
		this.e_others_val = e_others_val;
	}

	public String getE_others_colour() {
		return e_others_colour;
	}

	public void setE_others_colour(String e_others_colour) {
		this.e_others_colour = e_others_colour;
	}

	public BigDecimal getW_others_val() {
		return w_others_val;
	}

	public void setW_others_val(BigDecimal w_others_val) {
		this.w_others_val = w_others_val;
	}

	public String getW_others_colour() {
		return w_others_colour;
	}

	public void setW_others_colour(String w_others_colour) {
		this.w_others_colour = w_others_colour;
	}

	public BigDecimal getE_pttshipper_val() {
		return e_pttshipper_val;
	}

	public void setE_pttshipper_val(BigDecimal e_pttShipper_val) {
		this.e_pttshipper_val = e_pttShipper_val;
	}

	public String getE_pttshipper_colour() {
		return e_pttshipper_colour;
	}

	public void setE_pttshipper_colour(String e_pttshipper_colour) {
		this.e_pttshipper_colour = e_pttshipper_colour;
	}

	public BigDecimal getW_pttshipper_val() {
		return w_pttshipper_val;
	}

	public void setW_pttshipper_val(BigDecimal w_pttshipper_val) {
		this.w_pttshipper_val = w_pttshipper_val;
	}

	public String getW_pttshipper_colour() {
		return w_pttshipper_colour;
	}

	public void setW_pttshipper_colour(String w_pttshipper_colour) {
		this.w_pttshipper_colour = w_pttshipper_colour;
	}

	public String getE_zoneMode_val() {
		return e_zoneMode_val;
	}

	public void setE_zoneMode_val(String e_zoneMode_val) {
		this.e_zoneMode_val = e_zoneMode_val;
	}

	public String getW_zoneMode_val() {
		return w_zoneMode_val;
	}

	public void setW_zoneMode_val(String w_zoneMode_val) {
		this.w_zoneMode_val = w_zoneMode_val;
	}
}
