package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class TravellingTimeBean extends UserAudBean implements Serializable {

	/**
	 *			 tt.origin_point        AS idn_origin_point,
		       tt.destination_point   AS idn_destination_point,
		       sp_o.point_code        AS originPointCode,
		       sp_d.point_code        AS destinationPointCode,
		       tt.is_valid_route      AS destination, 
		       tt.travelling_time	  AS travelllingTime,
		       tt.start_date		  AS startDate
	 */
	private static final long serialVersionUID = -5255211382691787452L;

	private String originPointCode;
	private String destinationPointCode;
	private String destination;
	private BigDecimal travelllingTime;

	private Date startDate;
	private Date endDate;


	private BigDecimal idn_origin_point;
	private BigDecimal idn_destination_point;
	

	public TravellingTimeBean() {
		super();
	}

	public TravellingTimeBean(String originPointCode, String destinationPointCode, String destination, BigDecimal travelllingTime, Date startDate,Date endtDate,
			BigDecimal idn_origin_point, BigDecimal idn_destination_point ) {
		super();
		this.originPointCode = originPointCode;
		this.destinationPointCode = destinationPointCode;
		this.destination = destination;
		this.travelllingTime = travelllingTime;
		this.startDate = startDate;
		this.endDate=endDate;
	
		this.idn_origin_point = idn_origin_point;
		this.idn_destination_point = idn_destination_point;
	
	}

	

	public String getOriginPointCode() {
		return originPointCode;
	}

	public void setOriginPointCode(String originPointCode) {
		this.originPointCode = originPointCode;
	}

	public String getDestinationPointCode() {
		return destinationPointCode;
	}

	public void setDestinationPointCode(String destinationPointCode) {
		this.destinationPointCode = destinationPointCode;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public BigDecimal getTravelllingTime() {
		return travelllingTime;
	}

	public void setTravelllingTime(BigDecimal travelllingTime) {
		this.travelllingTime = travelllingTime;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getIdn_origin_point() {
		return idn_origin_point;
	}

	public void setIdn_origin_point(BigDecimal idn_origin_point) {
		this.idn_origin_point = idn_origin_point;
	}

	public BigDecimal getIdn_destination_point() {
		return idn_destination_point;
	}

	public void setIdn_destination_point(BigDecimal idn_destination_point) {
		this.idn_destination_point = idn_destination_point;
	}

	@Override
	public String toString() {
		return "TravellingTimeBean [originPointCode=" + originPointCode + ", destinationPointCode="
				+ destinationPointCode + ", destination=" + destination + ", travelllingTime=" + travelllingTime
				+ ", startDate=" + startDate + ", idn_origin_point=" + idn_origin_point + ", idn_destination_point="
				+ idn_destination_point + "]";
	}

}
