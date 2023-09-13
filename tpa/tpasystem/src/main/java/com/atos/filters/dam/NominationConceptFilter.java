package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NominationConceptFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5384739501695766356L;

		private String nomConcept;
		private String nomConceptType;
		private BigDecimal idn_system;//offshore
		

		public NominationConceptFilter() {
			this.nomConcept= null;
			this.nomConceptType= null;
			this.idn_system=null;
		}

		public String getNomConcept() {
			return nomConcept;
		}

		public void setNomConcept(String nomConcept) {
			this.nomConcept = nomConcept;
		}
		
		public String getNomConceptType() {
			return nomConceptType;
		}

		public void setNomConceptType(String nomConceptType) {
			this.nomConceptType = nomConceptType;
		}

		public BigDecimal getIdn_system() {
			return idn_system;
		}

		public void setIdn_system(BigDecimal idn_system) {
			this.idn_system = idn_system;
		}

		@Override
		public String toString() {
			return "NominationConceptFilter [nomConcept=" + nomConcept + ", nomConceptType=" + nomConceptType
					+ ", idn_system=" + idn_system + "]";
		}
	
	}
