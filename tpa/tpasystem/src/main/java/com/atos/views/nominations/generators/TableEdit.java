package com.atos.views.nominations.generators;

public class TableEdit {

	public static void main(String[] args) {
		
		String[] supply_names1 = {"ERW","BPT","STN","JDA","FUN to 34\"","FUN to 36\"","ECP SideStream","PLT","NPAILIN","SPAILIN","BKT","TWN","BJM"};
		String[] supply_codes1 = {"S_ERW","S_BPT","S_STN","S_JDA_A18","S_FUN_TO_34","S_FUN_TO_36","S_ECP_SS","S_PLT","S_NPAILIN","S_SPAILIN","S_BKT","S_TWN","S_BJM"};
		String[] supply_names2 = {"DPCU1","DPCU2","GSP1","GSP2","GSP3","GSP5","GSP6","LNG"};
		String[] supply_codes2 = {"S_DPCU1","S_DPCU2","S_GSP1","S_GSP2","S_GSP3","S_GSP5","S_GSP6","S_LNG"};
		String[] supply_names3 = {"YADANA","YETAGUN","ZAWTIKA"};
		String[] supply_codes3 = {"S_YADANA","S_YETAGUN","S_ZAWTIKA"};		

		
		String[] spp_codes1 = {"SPP_GLOW","SPP_BKC","SPP_GLOW2","SPP_NPC","SPP_IP","SPP_GLOW4","GATE_MTP"};
		String[] spp_names1 = {"Glow Energy","BKC","Glow SPP2","PTTCH","Glow SPP 1","Glow SPP 4","GATE_MTP"};
		String[] spp_codes2 = {"SPP_TLPC","SPP_TNP","SPP_TNP2","SPP_ABPR12","SPP_SPC","SPP_GULF","SPP_NKCC","IND_DCAP","SPP_GTLC"};
		String[] spp_names2 = {"TLPC","TNP","TNP2","ABPR1-2","SPC","GULF","NKC","DCAP","Gulf TLC"};
		String[] spp_codes3 = {"SPP_TOP","SPP_AEP_AMP","SPP_SAHA","SPP_LCP","SPP_RJN","SPP_RJN2","SPP_GNNK","SPP_GNK2","SPP_GKP1","SPP_GKP2"};
		String[] spp_names3 = {"THAIOIL","AMATA ","SAHA","LCP","ROJANA","ROJANA2","GNNK","GNK2","GKP1","GKP2"};
		String[] spp_codes4 = {"SPP_SIPCO","SPP_GNLL_RIL","SPP_ABP3"};
		String[] spp_names4 = {"SIPCO","GNLL-RIL","ABP3"};
		String[] spp_codes5 = {"SPP_GCRN","SPP_BIC","SPP_NNE","SPP_RWC"};
		String[] spp_names5 = {"GCRN","BIC","NNE","RWC"};

		
		String[] egat_codes1 = {"OFF_ECP_SS","OFFTAK_BV2_1","OFFTAK_BV1","OFFTAK_BV3_1"};
		String[] egat_names1 = {"ECPSideStream","Offsh.36\"","Offsh.34\"","Offsh.42\""};
		String[] egat_codes2 = {"OFFTAKE_GSP4","EGAT_KNM"};
		String[] egat_names2 = {"GSP4","KNM"};
		String[] egat_codes3 = {"EGAT_BPK123","EGAT_BPK456","EGAT_SBK_E","EGAT_SBK_W","EGAT_WN_E","EGAT_WN_W","EGAT_WNCC4","EGAT_NB"};
		String[] egat_names3 = {"BPK","BPK","SBK (E)","SBK (W)","WN (E)","WN (W)","WN-CC4","NB"};
		String[] egat_codes4 = {"IPP_IPT","IPP_GLOW","IPP_EPEC","IPP_GULF","IPP_GNS","IPP_GUT","IPP_TECO","IPP_RB","IPP_RPCL"};
		String[] egat_names4 = {"IPT","GlowIPP","EPEC","Gulf","GNS","GUT","TECO","RBP","RPCL"};

		
		String[] ind_names1 = {"RIL+RIP+SSM","APEX+ROC+NGV","TTC","ESP"};
		String[] ind_codes1 = {"IND_RY","IND_APEX_ROC","IND_TTC","IND_ESP"};
		String[] ind_names2 = {"ASM"};
		String[] ind_codes2 = {"IND_ASM"};
		String[] ind_names3 = {"BPC+SSG","AMATA CITY RAYONG","ESIE","BOWIN Group","ROJANA(RAYONG)","Bangpakong Park1","QC Lab","SPG12","WGI","TGSG","MCKEY+GFPT","SDC","TCR","BCP","PRAPADAENG"};
		String[] ind_codes3 = {"IND_BPC_SSG","IND_AMATA","IND_ESIE","IND_BOWIN","IND_RJN_RY","IND_BPK1","IND_QCLAB","IND_SPG12","IND_WGI","IND_TGSG","IND_MCKEY","IND_SDC","IND_TCR","IND_BCP","IND_PRAPAD"};
		String[] ind_names4 = {"HESCO+CELLO+BKK","TAC","MI","HSK+KSI","ETC","Bangchan","RANGSIT+NGV+NGD","STAR+TBS","Rapheepat+SIL","KAENGKHOI","NONGKHAE+HINKONG","IKF+SISCO+SRIC(BV#22)","CWC","SFCC","TTOP","KENZAI","FMT","CMM"};
		String[] ind_codes4 = {"IND_CELLO","IND_TAC","IND_MI","IND_HSK_KSI","IND_ETC","IND_BANGCHAN","IND_RANGSIT","IND_STAR_TBS","GATE_661R1R2","GATE_673RX","GATE_671R1R2","IND_BV22","IND_CWC","IND_SFCC","IND_TTOP","IND_KENZAI","IND_FMT","IND_CMM"};
		String[] ind_names5 = {"HESIE","Bangpu","Bang phli","BPK Park2","Bangkadee"};
		String[] ind_codes5 = {"NGD_HESIE","NGD_BANGPU","NGD_BANGPHLI","NGD_BPK2","NGD_BKD"};
		String[] ind_names6 = {"KANLAYA","BV11","Theparak","BV13-14","NGV_BV3_6","NIMITMAI","BV14_15","BV16_17","BV17-18","BV19_20","BV20_21","BV20_23","BV23_24","BV24_25","ENCO","BK GOV CENTER"};
		String[] ind_codes6 = {"NGV_KANLAYA","NGV_BV11","NGV_THEPARAK","NGV_BV13_14","NGV_BV3_6","NGV_NIMIT","NGV_BV14_15","NGV_BV16_17","NGV_BV17_18","NGV_BV19_20","NGV_BV20_21","NGV_BV20_23","NGV_BV23_24","NGV_BV24_25","NGV_ENCO","NGV_BGC"};
		String[] ind_names7 = {"SAHA PHAT","ESSO Cogen","TOR_ESR","LEAMCHABANG","OHCT+QCP+BCC2","OSOTSPA","IRPC"};
		String[] ind_codes7 = {"IND_SAHAPAT","IND_ESSO_CO","IND_TOR_ESR","IND_LCB","IND_WN2","IND_OSOTSPA","IND_IRPC"};
		String[] ind_names8 = {"NGD+NGV_LADKR","ROJANA"};
		String[] ind_codes8 = {"NGD_LADKR","NGD_ROJANA"};
		String[] ind_names9 = {"IND_RIL2","IND_MDP"};
		String[] ind_codes9 = {"IND_RIL2","IND_MDP"};
		String[] ind_names10 = {"BV2.4","WN2","WK5","RJN","BANBUANG"};
		String[] ind_codes10 = {"NGV_BV2_4","NGV_WN2","NGV_WK5","NGV_RJN","NGV_BANBUNG"};
		String[] ind_names11 = {"KVT","SUKSAWAT","ASF"};
		String[] ind_codes11 = {"IND_KVT","IND_SUKSAWAT","IND_ASF"};
		String[] ind_names12 = {"NAVANAKORN"};
		String[] ind_codes12 = {"NGD_NAWA"};
		String[] ind_names13 = {"RB","LLK","SDMV","BP","MM1","Fleet1","TKU","BCP_SS","KRP","PPD"};
		String[] ind_codes13 = {"NGV_RB12","NGV_LLK12","NGV_SDMV","NGV_BP","NGV_MM1","NGV_FLEET1","NGV_TKU","NGV_BCP_SS","NGV_KRP","NGV_PPD"};

		
		// east area
		String[] east_names1 = {"ERW","BPT","STN","JDA","FUN to 34\"","FUN to 36\"","ECP SideStream","PLT","NPAILIN","SPAILIN","BKT","TWN","BJM"};
		String[] east_codes1 = {"S_ERW","S_BPT","S_STN","S_JDA_A18","S_FUN_TO_34","S_FUN_TO_36","S_ECP_SS","S_PLT","S_NPAILIN","S_SPAILIN","S_BKT","S_TWN","S_BJM"};
		
		
		// west area
		String[] west_names2 = {"DPCU1","DPCU2","GSP1","GSP2","GSP3","GSP5","GSP6","LNG"};
		String[] west_codes2 = {"S_DPCU1","S_DPCU2","S_GSP1","S_GSP2","S_GSP3","S_GSP5","S_GSP6","S_LNG"};
		
		// mix area
		String[] mix_names3 = {"YADANA","YETAGUN","ZAWTIKA"};
		String[] mix_codes3 = {"S_YADANA","S_YETAGUN","S_ZAWTIKA"};		

		
		
		
		
		
/*		System.out.println("<ui:composition");
		System.out.println("\txmlns=\"http://www.w3.org/1999/xhtml\"");
		System.out.println("\txmlns:f=\"http://xmlns.jcp.org/jsf/core\"");
		System.out.println("\txmlns:h=\"http://java.sun.com/jsf/html\"");
		System.out.println("\txmlns:p=\"http://primefaces.org/ui\"");
		System.out.println("\txmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\">");
	    */

		// Properties generation
/*		System.out.println("\n#Supply Nominations");
		System.out.println("supply_common_name=Node Name");
		for(int i=0;i<supply_names1.length;i++){
			System.out.println(supply_codes1[i]+"="+supply_names1[i]);
		}
		for(int i=0;i<supply_names2.length;i++){
			System.out.println(supply_codes2[i]+"="+supply_names2[i]);
		}
		for(int i=0;i<supply_names3.length;i++){
			System.out.println(supply_codes3[i]+"="+supply_names3[i]);
		}
		System.out.println("\n#SPP Nominations");
		System.out.println("spp_common_name=Offtake Name");
		for(int i=0;i<spp_names1.length;i++){
			System.out.println(spp_codes1[i]+"="+spp_names1[i]);
		}
		for(int i=0;i<spp_names2.length;i++){
			System.out.println(spp_codes2[i]+"="+spp_names2[i]);
		}
		for(int i=0;i<spp_names3.length;i++){
			System.out.println(spp_codes3[i]+"="+spp_names3[i]);
		}
		for(int i=0;i<spp_names4.length;i++){
			System.out.println(spp_codes4[i]+"="+spp_names4[i]);
		}
		for(int i=0;i<spp_names5.length;i++){
			System.out.println(spp_codes5[i]+"="+spp_names5[i]);
		}
		System.out.println("\n#EGAT + IPP Nominations");
		System.out.println("egat_common_name=Node Node");
		for(int i=0;i<egat_names1.length;i++){
			System.out.println(egat_codes1[i]+"="+egat_names1[i]);
		}
		for(int i=0;i<egat_names2.length;i++){
			System.out.println(egat_codes2[i]+"="+egat_names2[i]);
		}
		for(int i=0;i<egat_names3.length;i++){
			System.out.println(egat_codes3[i]+"="+egat_names3[i]);
		}
		for(int i=0;i<egat_names4.length;i++){
			System.out.println(egat_codes4[i]+"="+egat_names4[i]);
		}
		System.out.println("\n#Ind Nominations");
		System.out.println("ind_common_name=Offtake Name");
		
		for(int i=0;i<ind_names1.length;i++){
			System.out.println(ind_codes1[i]+"="+ind_names1[i]);
		}		
		for(int i=0;i<ind_names2.length;i++){
			System.out.println(ind_codes2[i]+"="+ind_names2[i]);
		}		
		for(int i=0;i<ind_names3.length;i++){
			System.out.println(ind_codes3[i]+"="+ind_names3[i]);
		}		
		for(int i=0;i<ind_names4.length;i++){
			System.out.println(ind_codes4[i]+"="+ind_names4[i]);
		}		
		for(int i=0;i<ind_names5.length;i++){
			System.out.println(ind_codes5[i]+"="+ind_names5[i]);
		}		
		for(int i=0;i<ind_names6.length;i++){
			System.out.println(ind_codes6[i]+"="+ind_names6[i]);
		}		
		for(int i=0;i<ind_names7.length;i++){
			System.out.println(ind_codes7[i]+"="+ind_names7[i]);
		}		
		for(int i=0;i<ind_names8.length;i++){
			System.out.println(ind_codes8[i]+"="+ind_names8[i]);
		}		
		for(int i=0;i<ind_names9.length;i++){
			System.out.println(ind_codes9[i]+"="+ind_names9[i]);
		}		
		for(int i=0;i<ind_names10.length;i++){
			System.out.println(ind_codes10[i]+"="+ind_names10[i]);
		}		
		for(int i=0;i<ind_names11.length;i++){
			System.out.println(ind_codes11[i]+"="+ind_names11[i]);
		}		
		for(int i=0;i<ind_names12.length;i++){
			System.out.println(ind_codes12[i]+"="+ind_names12[i]);
		}		
		for(int i=0;i<ind_names13.length;i++){
			System.out.println(ind_codes13[i]+"="+ind_names13[i]);
		}		
*/

/*		// Bean Generation
		System.out.println("//Supply Nominations");
		for(int i=0;i<supply_codes1.length;i++){
			System.out.println("private BigDecimal " +supply_codes1[i]+";");
		}
		for(int i=0;i<supply_codes2.length;i++){
			System.out.println("private BigDecimal " +supply_codes2[i]+";");
		}
		for(int i=0;i<supply_codes3.length;i++){
			System.out.println("private BigDecimal " +supply_codes3[i]+";");
		}
		System.out.println("//SPP Nominations");
		for(int i=0;i<spp_codes1.length;i++){
			System.out.println("private BigDecimal " +spp_codes1[i]+";");
		}
		for(int i=0;i<spp_codes2.length;i++){
			System.out.println("private BigDecimal " +spp_codes2[i]+";");
		}
		for(int i=0;i<spp_codes3.length;i++){
			System.out.println("private BigDecimal " +spp_codes3[i]+";");
		}
		for(int i=0;i<spp_codes4.length;i++){
			System.out.println("private BigDecimal " +spp_codes4[i]+";");
		}
		for(int i=0;i<spp_codes5.length;i++){
			System.out.println("private BigDecimal " +spp_codes5[i]+";");
		}
		System.out.println("//EGAT + IPP Nominations");
		for(int i=0;i<egat_codes1.length;i++){
			System.out.println("private BigDecimal " +egat_codes1[i]+";");
		}
		for(int i=0;i<egat_codes2.length;i++){
			System.out.println("private BigDecimal " +egat_codes2[i]+";");
		}
		for(int i=0;i<egat_codes3.length;i++){
			System.out.println("private BigDecimal " +egat_codes3[i]+";");
		}
		for(int i=0;i<egat_codes4.length;i++){
			System.out.println("private BigDecimal " +egat_codes4[i]+";");
		}
		System.out.println("//Ind Nominations");
		
		for(int i=0;i<ind_codes1.length;i++){
			System.out.println("private BigDecimal " +ind_codes1[i]+";");
		}		
		for(int i=0;i<ind_codes2.length;i++){
			System.out.println("private BigDecimal " +ind_codes2[i]+";");
		}		
		for(int i=0;i<ind_codes3.length;i++){
			System.out.println("private BigDecimal " +ind_codes3[i]+";");
		}		
		for(int i=0;i<ind_codes4.length;i++){
			System.out.println("private BigDecimal " +ind_codes4[i]+";");
		}		
		for(int i=0;i<ind_codes5.length;i++){
			System.out.println("private BigDecimal " +ind_codes5[i]+";");
		}		
		for(int i=0;i<ind_codes6.length;i++){
			System.out.println("private BigDecimal " +ind_codes6[i]+";");
		}		
		for(int i=0;i<ind_codes7.length;i++){
			System.out.println("private BigDecimal " +ind_codes7[i]+";");
		}		
		for(int i=0;i<ind_codes8.length;i++){
			System.out.println("private BigDecimal " +ind_codes8[i]+";");
		}		
		for(int i=0;i<ind_codes9.length;i++){
			System.out.println("private BigDecimal " +ind_codes9[i]+";");
		}		
		for(int i=0;i<ind_codes10.length;i++){
			System.out.println("private BigDecimal " +ind_codes10[i]+";");
		}		
		for(int i=0;i<ind_codes11.length;i++){
			System.out.println("private BigDecimal " +ind_codes11[i]+";");
		}		
		for(int i=0;i<ind_codes12.length;i++){
			System.out.println("private BigDecimal " +ind_codes12[i]+";");
		}		
		for(int i=0;i<ind_codes13.length;i++){
			System.out.println("private BigDecimal " +ind_codes13[i]+";");
		}		
*/

		generateTable("supply_common_name","supply_table1", "sup1", supply_codes1);
		generateTable("supply_common_name","supply_table2", "sup2", supply_codes2);
		generateTable("supply_common_name","supply_table3", "sup3", supply_codes3);
		
		generateTable("spp_common_name","spp_table1", "spp1", spp_codes1);
		generateTable("spp_common_name","spp_table2", "spp2", spp_codes2);
		generateTable("spp_common_name","spp_table3", "spp3", spp_codes3);
		generateTable("spp_common_name","spp_table4", "spp4", spp_codes4);
		generateTable("spp_common_name","spp_table5", "spp5", spp_codes5);
		
		generateTable("egat_common_name","egat_table1", "ega1", egat_codes1);
		generateTable("egat_common_name","egat_table2", "ega2", egat_codes2);
		generateTable("egat_common_name","egat_table3", "ega3", egat_codes3);
		generateTable("egat_common_name","egat_table4", "ega4", egat_codes4);

		generateTable("ind_common_name","ind_table1", "ind1", ind_codes1);
		generateTable("ind_common_name","ind_table2", "ind2", ind_codes2);
		generateTable("ind_common_name","ind_table3", "ind3", ind_codes3);
		generateTable("ind_common_name","ind_table4", "ind4", ind_codes4);
		generateTable("ind_common_name","ind_table5", "ind5", ind_codes5);
		generateTable("ind_common_name","ind_table6", "ind6", ind_codes6);
		generateTable("ind_common_name","ind_table7", "ind7", ind_codes7);
		generateTable("ind_common_name","ind_table8", "ind8", ind_codes8);
		generateTable("ind_common_name","ind_table9", "ind9", ind_codes9);
		generateTable("ind_common_name","ind_table10", "ind10", ind_codes10);
		generateTable("ind_common_name","ind_table11", "ind11", ind_codes11);
		generateTable("ind_common_name","ind_table12", "ind12", ind_codes12);
		generateTable("ind_common_name","ind_table13", "ind13", ind_codes13);
		
	}

	private static void generateTable(String header_text,String id_table, String var, String[] codes){
		System.out.println("\n\n\n"+"--"+id_table);

		StringBuffer sb_h = new StringBuffer();

		sb_h.append("<ui:composition").append("\n").
		append("\txmlns=\"http://www.w3.org/1999/xhtml\"").append("\n").
		append("\txmlns:f=\"http://xmlns.jcp.org/jsf/core\"").append("\n").
		append("\txmlns:h=\"http://java.sun.com/jsf/html\"").append("\n").
		append("\txmlns:p=\"http://primefaces.org/ui\"").append("\n").
		append("\txmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\">").append("\n");
	    
		
		sb_h.append("<p:dataTable id=\""+id_table+"\" var=\""+var+"\" value=\"#{dtDNom.noms}\" editable=\"true\" style=\"margin-bottom:20px;border-collapse:collapse\"").append("\n").
		append("paginator=\"true\" rows=\"15\" rowsPerPageTemplate=\"5,10,15,20,25\">").append("\n").append("\n").
				 
		append("\t<p:ajax event=\"rowEdit\" listener=\"#{dtDNom.onRowEdit}\" update=\":form:msgs\" />").append("\n").
		append("\t<p:ajax event=\"rowEditCancel\" listener=\"#{dtDNom.onRowCancel}\" update=\":form:msgs\" />").append("\n");
		
		StringBuffer sb_t = new StringBuffer();
		sb_t.append("<p:column headerText=\"#{msg['"+header_text+"']}\">").append("\n").
		append("\t").append("<h:outputText value=\"#{"+var+".date}\" >").append("\n").
		append("\t").append("<f:convertDateTime pattern=\"yyyy-MM-dd HH:mm:ss\" />").append("\n").
		append("\t").append("</h:outputText>").append("\n").
		append("\t").append("</p:column>");
		for(int i=0;i<codes.length;i++){
			String label = codes[i];
			String code = "v_" + codes[i];
			
			sb_t.append("\t").append("<p:column headerText=\"#{msg['"+label+"']}\">").append("\n").
			append("\t").append("\t").append("<p:cellEditor>").append("\n").
			append("\t").append("\t").append("<f:facet name=\"output\"><h:outputText value=\"#{"+var+"."+code+"}\" /></f:facet>").append("\n").
			append("\t").append("\t").append("<f:facet name=\"input\"><p:inputText value=\"#{"+var+"."+code+"}\" style=\"width:100%\" label=\"#{msg['"+label+"']}\"/></f:facet>").append("\n").
			append("\t").append("\t").append("</p:cellEditor>").append("\n").
			append("\t").append("</p:column>").append("\n");
			
			
		}
		//supply_codes1
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
}
