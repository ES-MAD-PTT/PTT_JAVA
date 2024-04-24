package com.atos.beans.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AllocationQueryBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7488987616996704450L;

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
	private BigDecimal bookedCap;
	private BigDecimal nominatedCap;
	private BigDecimal allocationTPA;
	// Este atributo origin se obtiene en la consulta (por si es util a la hora de trazar datos), pero no se muestra en la pantalla porque
	// puede dar lugar a confusion, por este motivo: 
	// Allocation Origin field indicates the process responsible for the allocation data for one Gas Day (any contract and point) and
	// it doesn´t indicate the origin of every allocation data for one Gas Day,
	// Contract and Nom. Point
	// Por ejemplo, en la tabla de repartos se generan todos los repartos correspondientes a un dia, aunque solo se haya recibido medida para un punto.
	// El proceso que genera los repartos, es el de Repartos de Medidas, pero solo uno de los repartos se ha generado por la medida; el resto se calculan 
	// a partir de la ultima version.
	private String origin;
	private Date versionDate;
	
	public BigDecimal getPercent() {
		if (allocationTPA != null && bookedCap != null && bookedCap.doubleValue() != 0) {
			try {
			return allocationTPA.multiply(new BigDecimal(100)).divide(bookedCap, 2, RoundingMode.HALF_UP);
			} catch (Exception e) {
				// should not happend
				e.printStackTrace();
			}
		}
		return null;
	}

	public AllocationQueryBean() {
		this.allocationDataId = null;
		this.gasDay = null;
		this.shipperId = null;
		this.shipperCode = null;
		this.contractId = null;
		this.contractCode = null;
		this.shortName = null;
		this.nomPointId = null;
		this.nomPointCode = null;
		this.nomPointTypeCode = null;
		this.bookedCap = null;
		this.nominatedCap = null;
		this.allocationTPA = null;
		this.origin = null;
		this.versionDate = null;
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

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}

	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"gasDay;shipperCode;contractCode;nomPointCode;nomPointTypeCode;bookedCap;nominatedCap;allocationTPA;usage(%);timestamp");
		return builder.toString();
	}

	public String toCSV() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		StringBuilder builder = new StringBuilder();
		builder.append((gasDay==null ? "" : sdf.format(gasDay))+";");
		builder.append((shipperCode==null ? "" : shipperCode)+";");
		builder.append((contractCode==null ? "" : contractCode)+";");
		builder.append((nomPointCode==null ? "" : nomPointCode)+";");
		builder.append((nomPointTypeCode==null ? "" : nomPointTypeCode)+";");
		builder.append((bookedCap==null ? "" : bookedCap.doubleValue())+";");
		builder.append((nominatedCap==null ? "" : nominatedCap.doubleValue())+";");
		builder.append((allocationTPA==null ? "" : allocationTPA.doubleValue())+";");
		builder.append((getPercent()==null ? "" : getPercent().doubleValue())+";");
		builder.append((versionDate==null ? "" : sdf2.format(versionDate)));
		return builder.toString();
	}
	
	
	@Override
	public String toString() {
		return "AllocationQueryBean [allocationDataId=" + allocationDataId + ", gasDay=" + gasDay + ", shipperId="
				+ shipperId + ", shipperCode=" + shipperCode + ", shortName=" + shortName + ", contractId=" + contractId
				+ ", contractCode=" + contractCode + ", nomPointId=" + nomPointId + ", nomPointCode=" + nomPointCode
				+ ", nomPointTypeCode=" + nomPointTypeCode + ", bookedCap=" + bookedCap + ", nominatedCap="
				+ nominatedCap + ", allocationTPA=" + allocationTPA + ", origin=" + origin + ", versionDate="
				+ versionDate + "]";
	}


	public static void main(String[] args) {
		AllocationQueryBean aqb = new AllocationQueryBean();
		aqb.setAllocationTPA(new BigDecimal(0));
		aqb.setBookedCap(new BigDecimal(0.600));
		System.out.println(aqb.getPercent());

	}

}
