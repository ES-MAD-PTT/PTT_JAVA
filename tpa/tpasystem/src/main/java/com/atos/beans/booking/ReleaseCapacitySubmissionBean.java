package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReleaseCapacitySubmissionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4520699298321436147L;

	// Estos dos campos contractPointId, contractAgreementId solo tiene sentido rellenarlos al consultar datos
	// asociados a una capacity Request, en la que buscan todas las filas.
	// En las consultas utilizadas por la rama de contratos, no tiene sentido rellenarlo, porque se usan cantidades agregadas
	// agrupadas por systemPointId, agreementStartDate, agreementEndDate, operationId.
	// Estos dos campos se utilizan para facilitar la actualiacion en BD.
	private BigDecimal contractPointId;
	private BigDecimal contractAgreementId;
	private BigDecimal systemPointId;	
	private String systemPointCode;
	private String pointTypeCode;	
	private Date agreementStartDate;
	// Esta variable no se establece desde fuera del bean, sino que se calcula a partir de agreementStartDate.
	//private Date from;
	//private Date to;	
	private Date agreementEndDate;
	private BigDecimal operationId;
	// En la consulta inicial de Release Capacity Submission, en las cantidades contratadas se deben sumar
	// las cantidades para un mismo contrato, punto y periodo (agreement). Podria haber varias adendas
	// (contrato inicial y releases posteriores aceptadas) para un mismo contrato, punto y periodo.
	private Float contratedBBTuDay;
	private Float contratedMMscfd;
	private Float releaseBBTuDay;
	private Float releaseMMscfd;
	
	public ReleaseCapacitySubmissionBean() {
		super();
		this.contractPointId = null;
		this.contractAgreementId = null;
		this.systemPointId = null;
		this.systemPointCode = null;
		this.pointTypeCode = null;
		this.agreementStartDate = null;
		//this.from = null;
		//this.to = null;
		this.agreementEndDate = null;
		this.operationId = null;		
		this.contratedBBTuDay = null;
		this.contratedMMscfd = null;
		this.releaseBBTuDay = null;
		this.releaseMMscfd = null;
	}


	public ReleaseCapacitySubmissionBean(ReleaseCapacitySubmissionBean _rcsBean) {
		super();
		this.setContractPointId(_rcsBean.contractPointId);
		this.setContractAgreementId(_rcsBean.contractAgreementId);
		this.setSystemPointId(_rcsBean.systemPointId);
		this.setSystemPointCode(_rcsBean.systemPointCode);
		this.setPointTypeCode(_rcsBean.pointTypeCode);
		this.setAgreementStartDate(_rcsBean.agreementStartDate);
		this.setAgreementEndDate(_rcsBean.agreementEndDate);
		this.setOperationId(_rcsBean.operationId);
		this.setContratedBBTuDay(_rcsBean.contratedBBTuDay);
		this.setContratedMMscfd(_rcsBean.contratedMMscfd);
		this.setReleaseBBTuDay(_rcsBean.releaseBBTuDay);
		this.setReleaseMMscfd(_rcsBean.releaseMMscfd);
	}
	
	// En los metodos set() de mas abajo se hacen copias de cada elemento para asegurar que se copian valores (y no referencias)
	// al ir incorporando informacion en los metodos de ReleaseCapacityManagementServiceImpl.
	
	public BigDecimal getContractPointId() {
		return contractPointId;
	}

	public void setContractPointId(BigDecimal contractPointId) {
		if(contractPointId!=null)
			this.contractPointId = BigDecimal.valueOf(contractPointId.doubleValue()).setScale(0);
		else
			this.contractPointId = null;
	}

	public BigDecimal getContractAgreementId() {
		return contractAgreementId;
	}

	public void setContractAgreementId(BigDecimal contractAgreementId) {
		if(contractAgreementId!=null)
			this.contractAgreementId = BigDecimal.valueOf(contractAgreementId.doubleValue()).setScale(0);
		else
			this.contractAgreementId = null;
	}

	public BigDecimal getSystemPointId() {
		return systemPointId;
	}

	public void setSystemPointId(BigDecimal systemPointId) {
		if(systemPointId!=null)
			this.systemPointId = BigDecimal.valueOf(systemPointId.doubleValue()).setScale(0);
		else
			this.systemPointId = null;
	}

	public String getSystemPointCode() {
		return systemPointCode;
	}

	public void setSystemPointCode(String systemPointCode) {
		this.systemPointCode = systemPointCode;
	}

	public String getPointTypeCode() {
		return pointTypeCode;
	}

	public void setPointTypeCode(String pointTypeCode) {
		this.pointTypeCode = pointTypeCode;
	}

	public Date getAgreementStartDate() {
		return agreementStartDate;
	}

	public void setAgreementStartDate(Date agreementStartDate) {
		if(agreementStartDate!=null)
			this.agreementStartDate = new Date(agreementStartDate.getTime());
		else
			this.agreementStartDate = null;
	}

	public Date getAgreementEndDate() {
		return agreementEndDate;
	}

	public void setAgreementEndDate(Date agreementEndDate) {
		if(agreementEndDate!=null)
			this.agreementEndDate = new Date(agreementEndDate.getTime());
		else
			this.agreementEndDate = null;
	}

	public BigDecimal getOperationId() {
		return operationId;
	}

	public void setOperationId(BigDecimal operationId) {
		if(operationId!=null)
			this.operationId = BigDecimal.valueOf(operationId.doubleValue()).setScale(0);
		else
			this.operationId = null;
	}

	public Float getContratedBBTuDay() {
		return contratedBBTuDay;
	}

	public void setContratedBBTuDay(Float contratedBBTuDay) {
		if(contratedBBTuDay!=null)
			this.contratedBBTuDay = new Float(contratedBBTuDay.floatValue());
		else
			this.contratedBBTuDay = null;
	}

	public Float getContratedMMscfd() {
		return contratedMMscfd;
	}

	public void setContratedMMscfd(Float contratedMMscfd) {
		if(contratedMMscfd!= null)
			this.contratedMMscfd = new Float(contratedMMscfd.floatValue());
		else
			this.contratedMMscfd = null;
	}

	public Float getReleaseBBTuDay() {
		return releaseBBTuDay;
	}

	public void setReleaseBBTuDay(Float releaseBBTuDay) {
		if(releaseBBTuDay!= null)
			this.releaseBBTuDay = new Float(releaseBBTuDay.floatValue());
		else
			this.releaseBBTuDay = null;
	}

	public Float getReleaseMMscfd() {
		return releaseMMscfd;
	}

	public void setReleaseMMscfd(Float releaseMMscfd) {
		if(releaseMMscfd!= null)
			this.releaseMMscfd = new Float(releaseMMscfd.floatValue());
		else
			this.releaseMMscfd = null;
	}

	// Se devuelve el año correspondiente al startDate del agreemet. Se supone que un agreement no puede abarcar
	// dos años distintos, ni puede haber 2 agreements de un mismo punto, contrato y request (adenda), que correspondan 
	// a periodos de un mismo año.
	/*public Date getFrom() {
		
		if ( from == null ) {
			if( agreementStartDate != null ) {
				Calendar tmpCal = new GregorianCalendar();
				tmpCal.setTime(agreementStartDate);
				from = tmpCal.getTime();//new Integer(tmpCal.get(Calendar.YEAR));
			}
		}
		
		return from;
	}

	public Date getTo() {
		
		if ( to == null ) {
			if( agreementStartDate != null ) {
				Calendar tmpCal = new GregorianCalendar();
				tmpCal.setTime(agreementStartDate);
				to = tmpCal.getTime();//new Integer(tmpCal.get(Calendar.YEAR));
			}
		}
		
		return to;
	}*/

	@Override
	public String toString() {
		return "ReleaseCapacitySubmissionBean [contractPointId=" + contractPointId + ", contractAgreementId="
				+ contractAgreementId + ", systemPointId=" + systemPointId + ", systemPointCode=" + systemPointCode
				+ ", pointTypeCode=" + pointTypeCode + ", agreementStartDate=" + agreementStartDate + ", agreementEndDate=" + agreementEndDate + ", operationId=" + operationId
				+ ", contratedBBTuDay=" + contratedBBTuDay + ", contratedMMscfd=" + contratedMMscfd
				+ ", releaseBBTuDay=" + releaseBBTuDay + ", releaseMMscfd=" + releaseMMscfd + "]";
	}
	
}
