package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;

public class NominationConceptFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5384739501695766356L;

		private String nomConcept;
		private String nomConceptType;
		private BigDecimal unitType;
		private String type;
		private BigDecimal idn_system;//offshore
		
		private String is_area_concept;
		private String is_zone_concept;
		
		private BigDecimal idnUserGroup;
		private String userName;
		
		public NominationConceptFilter() {
			this.nomConcept= null;
			this.nomConceptType= null;
			this.idn_system=null;
		}

		

		public NominationConceptFilter(String nomConcept, String nomConceptType, BigDecimal unitType, String type,
				BigDecimal idn_system, String is_area_concept, String is_zone_concept, BigDecimal idnUserGroup,
				String userName) {
			super();
			this.nomConcept = nomConcept;
			this.nomConceptType = nomConceptType;
			this.unitType = unitType;
			this.type = type;
			this.idn_system = idn_system;
			this.is_area_concept = is_area_concept;
			this.is_zone_concept = is_zone_concept;
			this.idnUserGroup = idnUserGroup;
			this.userName = userName;
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

		public BigDecimal getUnitType() {
			return unitType;
		}

		public void setUnitType(BigDecimal unitType) {
			this.unitType = unitType;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public BigDecimal getIdnUserGroup() {
			return idnUserGroup;
		}

		public void setIdnUserGroup(BigDecimal idnUserGroup) {
			this.idnUserGroup = idnUserGroup;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getIs_area_concept() {
			return is_area_concept;
		}

		public void setIs_area_concept(String is_area_concept) {
			this.is_area_concept = is_area_concept;
		}

		public String getIs_zone_concept() {
			return is_zone_concept;
		}

		public void setIs_zone_concept(String is_zone_concept) {
			this.is_zone_concept = is_zone_concept;
		}

		@Override
		public String toString() {
			return "NominationConceptFilter [nomConcept=" + nomConcept + ", nomConceptType=" + nomConceptType
					+ ", unitType=" + unitType + ", type=" + type + ", idn_system=" + idn_system + ", is_area_concept="
					+ is_area_concept + ", is_zone_concept=" + is_zone_concept + ", idnUserGroup=" + idnUserGroup
					+ ", userName=" + userName + "]";
		}
	
	}
