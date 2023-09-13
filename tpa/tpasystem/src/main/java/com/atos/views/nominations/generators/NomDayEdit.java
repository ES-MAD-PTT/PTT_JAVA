package com.atos.views.nominations.generators;

public class NomDayEdit {

	static public String prefix = "nom_";
	public static void main(String[] args) {
		
		
		// areas
		String[] names = {"nom_supply_demand","nom_area_desc","nom_area_code", "nom_subarea","nom_point_id","nom_type","nom_unit","nom_entry_exit","nom_wi","nom_hv","nom_sg","nom_hour_01","nom_hour_02","nom_hour_03","nom_hour_04","nom_hour_05","nom_hour_06","nom_hour_07","nom_hour_08","nom_hour_09","nom_hour_10","nom_hour_11","nom_hour_12","nom_hour_13","nom_hour_14","nom_hour_15","nom_hour_16","nom_hour_17","nom_hour_18","nom_hour_19","nom_hour_20","nom_hour_21","nom_hour_22","nom_hour_23","nom_hour_24","nom_total"};
		String[] values = {"Supply/Demand","Area Description", "Area", "Subarea", "Point ID", "Type","Unit", "Entry/Exit", "WI", "HV","SG","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","total"};
		String[] codes = {"supply_demand","area", "area_code", "subarea","point_id", "cust_type", "unit","entry_exit","wi","hv","sg","hour_01","hour_02","hour_03","hour_04","hour_05","hour_06","hour_07","hour_08","hour_09","hour_10","hour_11","hour_12","hour_13","hour_14","hour_15","hour_16","hour_17","hour_18","hour_19","hour_20","hour_21","hour_22","hour_23","hour_24","total"};
		String[] edit = {"N","N","N","N","N","N","N","N","N","N","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","N"};

		String[] names2 = {"nom_supply_demand","nom_area_desc","nom_area_code", "nom_subarea","nom_concept_id","nom_type","nom_unit","nom_entry_exit","nom_total"};
		String[] values2 = {"Supply/Demand","Area Description", "Area", "Subarea", "Concept ID", "Type","Unit", "Entry/Exit", "total"};
		String[] codes2 = {"supply_demand","area", "area_code", "subarea","point_id", "cust_type", "unit","entry_exit","total"};
		String[] edit2 = {"N","N","N","N","N","N","N","N","Y"};

		// quality areas
		String[] q_names = {"nom_concept","nom_unit","nom_hour_01","nom_hour_02","nom_hour_03","nom_hour_04","nom_hour_05","nom_hour_06","nom_hour_07","nom_hour_08","nom_hour_09","nom_hour_10","nom_hour_11","nom_hour_12","nom_hour_13","nom_hour_14","nom_hour_15","nom_hour_16","nom_hour_17","nom_hour_18","nom_hour_19","nom_hour_20","nom_hour_21","nom_hour_22","nom_hour_23","nom_hour_24"};
		String[] q_values = {"Concept", "Uni", "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"};
		String[] q_codes = {"concept","unit","hour_01","hour_02","hour_03","hour_04","hour_05","hour_06","hour_07","hour_08","hour_09","hour_10","hour_11","hour_12","hour_13","hour_14","hour_15","hour_16","hour_17","hour_18","hour_19","hour_20","hour_21","hour_22","hour_23","hour_24"};
		String[] q_edit = {"N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N"};
		
		// quality tab
		String[] k_names = {"nom_zone","nom_point","nom_co2","nom_c1","nom_c2","nom_c3","nom_ic4","nom_nc4","nom_ic5","nom_nc5","nom_c6","nom_c7","nom_c8","nom_n2","nom_o2","nom_h2s","nom_s","nom_hg"};
		String[] k_values = {"Zone", "Point", "CO2","C1","C2","C3","IC4","nC4","IC5","nC5","C6","C7","C8","N2","O2","H2S","S","HG"};
		String[] k_codes = {"zone","point","co2","c1","c2","c3","ic4","nc4","ic5","nc5","c6","c7","c8","n2","o2","h2s","s","hg"};
		String[] k_edit = {"N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N","N"};
		
		
		generateProperties(values,names);
		System.out.println("---------------");
		generateProperties(values2,names2);
		System.out.println("---------------");
		generateProperties(q_values,q_names);
		System.out.println("---------------");
		generateProperties(k_values,k_names);

		generateTable("nom_east_tab","east_table", "east", codes,edit,"east_tab",names);
		generateTable("nom_east_tab_park","east_table_park", "east_park", codes2,edit2,"east_tab_park",names2);
		generateTable("nom_east_quality_tab","east_quality_table", "east_quality", q_codes, q_edit,"east_quality",q_names);


		generateTable("nom_west_tab","west_table", "west", codes,edit,"west_tab", names);
		generateTable("nom_west_tab_park","west_table_park", "west_park", codes2,edit2,"west_tab_park", names2);
		generateTable("nom_west_quality_tab","west_quality_table", "west_quality", q_codes, q_edit,"west_quality", q_names);

		generateTable("nom_mix_tab","mix_table", "mix", codes, edit,"mix_tab",names);
		generateTable("nom_mix_tab_park","mix_table_park", "mix_park", codes2,edit2,"mix_tab_park", names2);
		generateTable("nom_mix_quality_tab","mix_quality_table", "mix_quality", q_codes, q_edit,"mix_quality", q_names);

		generateTable("nom_quality_tab","quality_table", "quality", k_codes, k_edit, "quality", k_names);
	}

	private static void generateTable(String header_text,String id_table, String var, String[] codes,String[] edit, String variable, String[] labels){
		System.out.println("\n\n\n"+"--"+id_table);

		StringBuffer sb_h = new StringBuffer();

		sb_h.append("<ui:composition").append("\n").
		append("\txmlns=\"http://www.w3.org/1999/xhtml\"").append("\n").
		append("\txmlns:f=\"http://xmlns.jcp.org/jsf/core\"").append("\n").
		append("\txmlns:h=\"http://java.sun.com/jsf/html\"").append("\n").
		append("\txmlns:p=\"http://primefaces.org/ui\"").append("\n").
		append("\txmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\">").append("\n");
	    
		
		sb_h.append("<p:dataTable id=\""+id_table+"\" var=\""+var+"\" value=\"#{dayNom."+variable+"}\" editable=\"true\" style=\"margin-bottom:20px;border-collapse:collapse\"").append("\n").
		append("paginator=\"true\" rows=\"15\" rowsPerPageTemplate=\"5,10,15,20,25\">").append("\n").append("\n").
				 
		append("\t<p:ajax event=\"rowEdit\" listener=\"#{dayNom.onRowEdit}\" update=\":form:msgs\" />").append("\n").
		append("\t<p:ajax event=\"rowEditCancel\" listener=\"#{dayNom.onRowCancel}\" update=\":form:msgs\" />").append("\n");
		
		StringBuffer sb_t = new StringBuffer();

		for(int i=0;i<codes.length;i++){
			String label = labels[i];
			String code = codes[i];
			
			if(edit[i].equals("N")){
				sb_t.append("\t").append("<p:column headerText=\"#{msg['"+label+"']}\">").append("\n").
				append("\t").append("<h:outputText value=\"#{"+var+"."+code+"}\" />").append("\n").
				append("\t").append("</p:column>").append("\n");
			}
			if(edit[i].equals("Y")){
				sb_t.append("\t").append("<p:column headerText=\"#{msg['"+label+"']}\" rendered=\"#{!dayNom.compareDates(dayNom.selected.start_date,1)}\">").append("\n").
				append("\t").append("<h:outputText value=\"#{"+var+"."+code+"}\" />").append("\n").
				append("\t").append("</p:column>").append("\n");
				
				
				sb_t.append("\t").append("<p:column headerText=\"#{msg['"+label+"']}\" rendered=\"#{dayNom.compareDates(dayNom.selected.start_date,1)}\">").append("\n").
				append("\t").append("\t").append("<p:cellEditor>").append("\n").
				append("\t").append("\t").append("<f:facet name=\"output\"><h:outputText value=\"#{"+var+"."+code+"}\" /></f:facet>").append("\n").
				append("\t").append("\t").append("<f:facet name=\"input\"><p:inputText value=\"#{"+var+"."+code+"}\" style=\"width:100%\" label=\"#{msg['"+label+"']}\"/></f:facet>").append("\n").
				append("\t").append("\t").append("</p:cellEditor>").append("\n").
				append("\t").append("</p:column>").append("\n");
			}
			
		}
		
		sb_t.append("\t").append("<p:column style=\"width:32px\">").append("\n").
		append("\t").append("\t").append("<p:rowEditor />").append("\n").
		append("\t").append("</p:column>").append("\n");
		
		
		
		
		
		StringBuffer sb_h_f = new StringBuffer();
		sb_h_f.append("</p:dataTable>").append("\n").
		append("</ui:composition>");
		
		System.out.println(sb_h.toString() +
				sb_t.toString() +
				sb_h_f.toString());
		
	}
	
	private static void generateProperties(String[] names, String[] codes){
		for(int i=0;i<names.length;i++){
			System.out.println(codes[i]+"="+names[i]);
		}

		
	}
}
