package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ContractDeadlineSpecialBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;
/*
 * SELECT 
 *     odc.idn_oper_deadline_contract,
 *     
       			odc.idn_operation,
       			o.operation_desc,
       			odc.start_date,
       			odc.end_date,
       odc.contract_min_start_date,
       odc.contract_min_start_date,
       odc.submission_deadline,
       odc.management_deadline
       
       
 */
		private BigDecimal idn_oper_deadline_contract;
		private BigDecimal idn_operation;
		
		private String operation_desc;
		private Date startDate;
		private Date endDate;
		
		private Date contract_min_start_date;
		private Date contract_max_start_date;
		
		private Date submission_deadline;
		private Date management_deadline;
		
		private String type;
		private BigDecimal idn_operation_term;
		
		public ContractDeadlineSpecialBean() {
			super();
			// TODO Auto-generated constructor stub
		}
		public ContractDeadlineSpecialBean(String _username) {
			super(_username);
			// TODO Auto-generated constructor stub
		}
		
		public ContractDeadlineSpecialBean(BigDecimal idn_operation, String operation_desc, Date startDate,
				Date endDate, Date contract_min_start_date, Date contract_max_start_date, Date submission_deadline,
				Date management_deadline) {
			super();
			this.idn_operation = idn_operation;
			this.operation_desc = operation_desc;
			this.startDate = startDate;
			this.endDate = endDate;
			this.contract_min_start_date = contract_min_start_date;
			this.contract_max_start_date = contract_max_start_date;
			this.submission_deadline = submission_deadline;
			this.management_deadline = management_deadline;
		}
		public BigDecimal getIdn_operation() {
			return idn_operation;
		}
		public void setIdn_operation(BigDecimal idn_operation) {
			this.idn_operation = idn_operation;
		}
		public String getOperation_desc() {
			return operation_desc;
		}
		public void setOperation_desc(String operation_desc) {
			this.operation_desc = operation_desc;
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
		public Date getContract_min_start_date() {
			return contract_min_start_date;
		}
		public void setContract_min_start_date(Date contract_min_start_date) {
			this.contract_min_start_date = contract_min_start_date;
		}
		public Date getContract_max_start_date() {
			return contract_max_start_date;
		}
		public void setContract_max_start_date(Date contract_max_start_date) {
			this.contract_max_start_date = contract_max_start_date;
		}
		public Date getSubmission_deadline() {
			return submission_deadline;
		}
		public void setSubmission_deadline(Date submission_deadline) {
			this.submission_deadline = submission_deadline;
		}
		public Date getManagement_deadline() {
			return management_deadline;
		}
		public void setManagement_deadline(Date management_deadline) {
			this.management_deadline = management_deadline;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public BigDecimal getIdn_operation_term() {
			return idn_operation_term;
		}
		public void setIdn_operation_term(BigDecimal idn_operation_term) {
			this.idn_operation_term = idn_operation_term;
		}
		public BigDecimal getIdn_oper_deadline_contract() {
			return idn_oper_deadline_contract;
		}
		public void setIdn_oper_deadline_contract(BigDecimal idn_oper_deadline_contract) {
			this.idn_oper_deadline_contract = idn_oper_deadline_contract;
		}
		
		@Override
		public String toString() {
			return "ContractDeadlineSpecialBean [idn_oper_deadline_contract=" + idn_oper_deadline_contract
					+ ", idn_operation=" + idn_operation + ", operation_desc=" + operation_desc + ", startDate="
					+ startDate + ", endDate=" + endDate + ", contract_min_start_date=" + contract_min_start_date
					+ ", contract_max_start_date=" + contract_max_start_date + ", submission_deadline="
					+ submission_deadline + ", management_deadline=" + management_deadline + ", type=" + type
					+ ", idn_operation_term=" + idn_operation_term + "]";
		}
		
		
		

	

}
