package com.atos.beans.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class AllocationBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2876588164803602137L;

	// Los campos clave de la tabla tpa_tallocation_data son
	// IDN_ALLOCATION, IDN_CONTRACT, IDN_ZONE, IDN_NOMINATION_POINT, IDN_NOMINATION_CONCEPT
	// Los campos clave de la tabla tpa_tallocation (aparte de IDN_ALLOCATION) son
	// IDN_ALLOCATION_TYPE, GAS_DAY, VERSION_DATE
	// Los campos clave de la tabla tpa_tallocation_review son
	// GAS_DAY, IDN_CONTRACT, IDN_SYSTEM_POINT, IDN_ZONE, IDN_NOMINATION_CONCEPT, VERSION_DATE
	// Estos campos seran necesarios cuando se quiera hacer insert en tpa_tallocation_review
	private BigDecimal allocationDataId;
	private Date gasDay;
	private BigDecimal shipperId;
	private String shipperCode;
	private String shortName;
	private BigDecimal contractId;
	private String contractCode; 
	private BigDecimal nomPointId;
	private String nomPointCode;
	private String nomPointTypeCode;
	private BigDecimal zoneId;
	private String zoneCode;
	private BigDecimal areaId;
	private String areaCode;
	private BigDecimal nominationConceptId;
	private String nominationConceptDesc;
	private BigDecimal bookedCap;
	private BigDecimal nominatedCap;
	private BigDecimal allocationTPA;
	private String allocationChanged;					// "Y"/"N" segun si hay un reparto (cabecera) posterior a la review.
	private BigDecimal originalAllocationForReview;		// Valor del reparto cuando se inserto la version de review.
	private BigDecimal reviewedAllocation;
	private String reviewComments;
	private BigDecimal allocationDiff;
	private BigDecimal allocationPerc;		// Percentage
	private String statusCode;
	private BigDecimal reviewId;
	private String reviewCode;
	private String isWarned; 				// "Y"/"N" segun si se ha avisado al operador antes de Accept.
	private BigDecimal conversionFactor; 				// Se usa tanto para consultar datos desde la unidad por defecto
														// en la BD, como para insertar.
	
	public AllocationBean() {
		this.allocationDataId = null;
		this.gasDay = null;
		this.shipperId = null;
		this.shipperCode = null;
		this.contractId = null;
		this.shortName = null;
		this.contractCode = null;
		this.nomPointId = null;
		this.nomPointCode = null;
		this.nomPointTypeCode = null;
		this.zoneId = null;
		this.zoneCode = null;
		this.areaId = null;
		this.areaCode = null;
		this.nominationConceptId = null;
		this.nominationConceptDesc = null;
		this.bookedCap = null;
		this.nominatedCap = null;
		this.allocationTPA = null;
		this.allocationChanged = null;
		this.originalAllocationForReview = null;
		this.reviewedAllocation = null;
		this.reviewComments = null;
		this.allocationDiff = null;
		this.allocationPerc = null;
		this.statusCode = null;
		this.reviewId = null;
		this.reviewCode = null;
		this.isWarned = "N";						// Por defecto se inicializa a no avisado.
		this.conversionFactor = new BigDecimal(1); 	// Por defecto, se multiplica por 1.
	}

	public BigDecimal getAllocationDataId() {
		return allocationDataId;
	}

	public void setAllocationDataId(BigDecimal allocationDataId) {
		this.allocationDataId = allocationDataId;
	}

	public Date getGasDay() {
		return gasDay;
	}

	public void setGasDay(Date gasDay) {
		this.gasDay = gasDay;
	}

	public BigDecimal getShipperId() {
		return shipperId;
	}

	public void setShipperId(BigDecimal shipperId) {
		this.shipperId = shipperId;
	}

	public String getShipperCode() {
		return shipperCode;
	}

	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public BigDecimal getContractId() {
		return contractId;
	}

	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public BigDecimal getNomPointId() {
		return nomPointId;
	}

	public void setNomPointId(BigDecimal nomPointId) {
		this.nomPointId = nomPointId;
	}

	public String getNomPointCode() {
		return nomPointCode;
	}

	public void setNomPointCode(String nomPointCode) {
		this.nomPointCode = nomPointCode;
	}

	public String getNomPointTypeCode() {
		return nomPointTypeCode;
	}

	public void setNomPointTypeCode(String nomPointTypeCode) {
		this.nomPointTypeCode = nomPointTypeCode;
	}

	public BigDecimal getZoneId() {
		return zoneId;
	}

	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public BigDecimal getAreaId() {
		return areaId;
	}

	public void setAreaId(BigDecimal areaId) {
		this.areaId = areaId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public BigDecimal getNominationConceptId() {
		return nominationConceptId;
	}

	public void setNominationConceptId(BigDecimal nominationConceptId) {
		this.nominationConceptId = nominationConceptId;
	}

	public String getNominationConceptDesc() {
		return nominationConceptDesc;
	}

	public void setNominationConceptDesc(String nominationConceptDesc) {
		this.nominationConceptDesc = nominationConceptDesc;
	}

	public BigDecimal getBookedCap() {
		return bookedCap;
	}

	public void setBookedCap(BigDecimal bookedCap) {
		this.bookedCap = bookedCap;
	}

	public BigDecimal getNominatedCap() {
		return nominatedCap;
	}

	public void setNominatedCap(BigDecimal nominatedCap) {
		this.nominatedCap = nominatedCap;
	}

	public BigDecimal getAllocationTPA() {
		return allocationTPA;
	}

	public void setAllocationTPA(BigDecimal allocationTPA) {
		this.allocationTPA = allocationTPA;
	}

	public String getAllocationChanged() {
		return allocationChanged;
	}

	public void setAllocationChanged(String allocationChanged) {
		this.allocationChanged = allocationChanged;
	}

	public BigDecimal getOriginalAllocationForReview() {
		return originalAllocationForReview;
	}

	public void setOriginalAllocationForReview(BigDecimal originalAllocationForReview) {
		this.originalAllocationForReview = originalAllocationForReview;
	}

	public BigDecimal getReviewedAllocation() {
		return reviewedAllocation;
	}

	public void setReviewedAllocation(BigDecimal reviewedAllocation) {
		this.reviewedAllocation = reviewedAllocation;
	}

	public String getReviewComments() {
		return reviewComments;
	}

	public void setReviewComments(String reviewComments) {
		this.reviewComments = reviewComments;
	}

	public BigDecimal getAllocationDiff() {
		return allocationDiff;
	}

	public void setAllocationDiff(BigDecimal allocationDiff) {
		this.allocationDiff = allocationDiff;
	}

	public BigDecimal getAllocationPerc() {
		return allocationPerc;
	}

	public void setAllocationPerc(BigDecimal allocationPerc) {
		this.allocationPerc = allocationPerc;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public BigDecimal getReviewId() {
		return reviewId;
	}

	public void setReviewId(BigDecimal reviewId) {
		this.reviewId = reviewId;
	}

	public String getReviewCode() {
		return reviewCode;
	}

	public void setReviewCode(String reviewCode) {
		this.reviewCode = reviewCode;
	}

	public String getIsWarned() {
		return isWarned;
	}

	public void setIsWarned(String isWarned) {
		this.isWarned = isWarned;
	}

	public BigDecimal getConversionFactor() {
		return conversionFactor;
	}

	public void setConversionFactor(BigDecimal conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	// 'Cap. Overuse (%) - despues de Allocation % -> será el valor de
	// 'Allocation TPA' / 'Booked Cap.'; con 2 decimales
	public BigDecimal getCapOveruse() {
		
		if(this.allocationTPA == null || this.bookedCap == null)
			return null;

		//ROUND_HALF_UP
		//Rounding mode to round towards "nearest neighbor" unless both neighbors are equidistant, in which case round up.
		//return this.allocationTPA.divide(this.bookedCap, 4, BigDecimal.ROUND_HALF_UP);
		
		//CH703. BUG div por zero.  ahora la bookedcap puede ser cero. evitar la div por cero. 
		if(this.bookedCap.equals(BigDecimal.ZERO)){
			return BigDecimal.ZERO;
		}else{
			return this.allocationTPA.divide(this.bookedCap, 4, BigDecimal.ROUND_HALF_UP);
			
		}
		
	}

	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"gasDay;shipperCode;contractCode;nomination_point_id;point_type;booked_cap;nominated_value;allocationTPA;originalAllocationForReview;reviewedAllocation;reviewComments;allocationDiff;allocationPerc;statusCode;reviewCode");
		return builder.toString();
	}

	public String toCSV() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder builder = new StringBuilder();
		builder.append((gasDay==null ? "" : sdf.format(gasDay))+";");
		builder.append((shipperCode==null ? "" : shipperCode)+";");
		builder.append((contractCode==null ? "" : contractCode)+";");
		builder.append((nomPointCode==null ? "" : nomPointCode)+";");
		builder.append((nomPointTypeCode==null ? "" : nomPointTypeCode)+";");
		builder.append((bookedCap==null ? "" : bookedCap.doubleValue())+";");
		builder.append((nominatedCap==null ? "" : nominatedCap.doubleValue())+";");
		builder.append((allocationTPA==null ? "" : allocationTPA.doubleValue())+";");
		builder.append((originalAllocationForReview==null ? "" : originalAllocationForReview.doubleValue())+";");
		builder.append((reviewedAllocation==null ? "" : reviewedAllocation.doubleValue())+";");
		builder.append((reviewComments==null ? "" : reviewComments.replaceAll("\r\n", " / "))+";");
		builder.append((allocationDiff==null ? "" : allocationDiff.doubleValue())+";");
		builder.append((allocationPerc==null ? "" : allocationPerc.doubleValue())+";");
		builder.append((statusCode==null ? "" : statusCode)+";");
		builder.append((reviewCode==null ? "" : reviewCode));
		return builder.toString();
	}


	public String toCSVHeaderReview() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"gasDay;shipperCode;contractCode;zoneCode;nominationConceptDesc;nominated_value;allocationTPA;originalAllocationForReview;reviewedAllocation;reviewComments;allocationDiff;allocationPerc;statusCode;reviewCode");
		return builder.toString();
	}

	public String toCSVReview() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder builder = new StringBuilder();
		builder.append((gasDay==null ? "" : sdf.format(gasDay))+";");
		builder.append((shipperCode==null ? "" : shipperCode)+";");
		builder.append((contractCode==null ? "" : contractCode)+";");
		builder.append((zoneCode==null ? "" : zoneCode)+";");
		builder.append((nominationConceptDesc==null ? "" : nominationConceptDesc)+";");
		builder.append((nominatedCap==null ? "" : nominatedCap.doubleValue())+";");
		builder.append((allocationTPA==null ? "" : allocationTPA.doubleValue())+";");
		builder.append((originalAllocationForReview==null ? "" : originalAllocationForReview.doubleValue())+";");
		builder.append((reviewedAllocation==null ? "" : reviewedAllocation.doubleValue())+";");
		builder.append((reviewComments==null ? "" : reviewComments.replaceAll("\r\n", " / "))+";");
		builder.append((allocationDiff==null ? "" : allocationDiff.doubleValue())+";");
		builder.append((allocationPerc==null ? "" : allocationPerc.doubleValue())+";");
		builder.append((statusCode==null ? "" : statusCode)+";");
		builder.append((reviewCode==null ? "" : reviewCode));
		return builder.toString();
	}



	
	@Override
	public String toString() {
		return "AllocationBean [allocationDataId=" + allocationDataId + ", gasDay=" + gasDay + ", shipperId="
				+ shipperId + ", shipperCode=" + shipperCode + ", shortName=" + shortName + ", contractId=" + contractId
				+ ", contractCode=" + contractCode + ", nomPointId=" + nomPointId + ", nomPointCode=" + nomPointCode
				+ ", nomPointTypeCode=" + nomPointTypeCode + ", zoneId=" + zoneId + ", zoneCode=" + zoneCode
				+ ", areaId=" + areaId + ", areaCode=" + areaCode + ", nominationConceptId=" + nominationConceptId
				+ ", nominationConceptDesc=" + nominationConceptDesc + ", bookedCap=" + bookedCap + ", nominatedCap="
				+ nominatedCap + ", allocationTPA=" + allocationTPA + ", allocationChanged=" + allocationChanged
				+ ", originalAllocationForReview=" + originalAllocationForReview + ", reviewedAllocation="
				+ reviewedAllocation + ", reviewComments=" + reviewComments + ", allocationDiff=" + allocationDiff
				+ ", allocationPerc=" + allocationPerc + ", statusCode=" + statusCode + ", reviewId=" + reviewId
				+ ", reviewCode=" + reviewCode + ", isWarned=" + isWarned + ", conversionFactor=" + conversionFactor
				+ "]";
	}
}
