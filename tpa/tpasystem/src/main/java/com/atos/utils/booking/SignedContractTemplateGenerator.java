package com.atos.utils.booking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute.Space;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLvl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;

import com.atos.beans.booking.CapacityRequestBean;
import com.atos.beans.booking.SignedContractTempPeriodBean;
import com.atos.beans.booking.SignedContractTempPointBean;
import com.atos.beans.booking.SignedContractTemplateBean;
import com.atos.mapper.utils.booking.SignedContractTemplateGeneratorMapper;

public class SignedContractTemplateGenerator implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1346792580885876001L;

	private static final int iTitle1FontSize = 24;
	private static final int iTitle2FontSize = 18;
	private static final int iTitle3FontSize = 16;
	private static final int iTitle4FontSize = 14;
	private static final int iNormalFontSize = 11;
	private static final int iLittleFontSize = 9;
	//private static final String strCommonFontFamily = "Verdana";
	private static final String strCommonFontFamily = "Leelawadee";		// Representa caracteres europeos y tailandeses.
	private static final SimpleDateFormat sdfNormalDate = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat sdfMonthYear = new SimpleDateFormat("MMMM yyyy");
	private static final SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	private static final String strEntryTableTitleColor = "6699FF";
	private static final String strEntryTableShadowColor = "bfe1ff";
	private static final String strExitTableTitleColor = "33CC33";
	private static final String strExitTableShadowColor = "99ff99";
	// Margenes en celdas en tablas Entry y Exit.
	private static final int iTopMargin = 200;
	private static final int iLeftMargin = 100;
	private static final int iBottomMargin = 0;
	private static final int iRightMargin = 100;
	// Marcadores para saber si hay que hacer merge de celdas en tablas Entry y Exit.
	private static final int iNoMerge = 0;
	private static final int iStartMerge = 1;
	private static final int iContinueMerge = 2;
	
	private static final Logger log = LogManager.getLogger("com.atos.utils.booking.SignedContractTemplateGenerator");
	

	public SignedContractTemplateGenerator() {
		super();
	}
	
	public byte[] getSignedContractTemplate(SignedContractTemplateGeneratorMapper signedMapper, ResourceBundle msgs, CapacityRequestBean cr) throws Exception {
    	
    	// Con esta consulta se obtiene de la BD toda la info a presentar en el documento.
    	List<SignedContractTemplateBean> sctDetails = signedMapper.selectSignedContractTempDetails(cr.getId());
    	if(sctDetails==null || sctDetails.size()==0)
    		throw new Exception("No data available to generate Signed Contract Template File.");
    	
    	SignedContractTemplateBean contractDet = sctDetails.get(0);
    	if(contractDet==null)
    		throw new Exception("No data available to generate Signed Contract Template File.");
    	
    	XWPFDocument document = null;
    	ByteArrayOutputStream baos = null;
    	byte[] binaryData = null;
    	try{
	        document = new XWPFDocument(); 

	        addFooter(document, msgs);
	        generateSignedContractCover(document, msgs, contractDet);
	        generateSignedContractPageTwo(document, msgs, contractDet);
	        generateSignedContractDetails(document, msgs,contractDet);
	        generateSignedContractEntryData(document, msgs, contractDet);
	        generateSignedContractExitData(document, msgs, contractDet);
	                      
	        // Se guarda el documento en un array para pasar de outputstream a inputstream.
	        baos = new ByteArrayOutputStream();
	        document.write(baos);
	        binaryData = baos.toByteArray();			
	    } catch (Exception e) {
	    	log.error(e.getMessage(), e);
	        throw e;
	    } finally {
	        try {
		        baos.close();
		        document.close();
	        } catch (IOException ioe) {
		    	log.error(ioe.getMessage(), ioe);
		        throw ioe;
	        }
	    }
    	
    	return binaryData;
	}

	private void generateSignedContractCover(XWPFDocument doc, ResourceBundle msgs, SignedContractTemplateBean contract) throws Exception {
        XWPFParagraph par = doc.createParagraph();

        if(contract.isFirm())
        	addText(par, 2, 2, msgs.getString("cr_man_firm_capacity_contract"), iTitle1FontSize);
        else
        	addText(par, 2, 2, msgs.getString("cr_man_non_firm_capacity_contract"), iTitle1FontSize);
        
    	addText(par, 0, 0, msgs.getString("cr_man_transporter_value"), iTitle2FontSize);
    	addText(par, 1, 1, msgs.getString("cr_man_registered_number_label") + " " + msgs.getString("cr_man_registered_number_value"), iNormalFontSize);
    	addText(par, 0, 2, msgs.getString("cr_man_transporter_label_1"), iTitle3FontSize);
    	
    	addText(par, 0, 2, msgs.getString("and"), iTitle4FontSize);
    	
    	addText(par, 0, 0, contract.getShipperName(), iTitle2FontSize);
    	String shipperRegNum = contract.getShipperSAPId()!=null? contract.getShipperSAPId() : ""; 
    	addText(par, 1, 1, msgs.getString("cr_man_registered_number_label") + " " + shipperRegNum, iNormalFontSize);
    	XWPFRun runFinal = addText(par, msgs.getString("cr_man_shipper_label_1"), iTitle3FontSize);
        
    	runFinal.addBreak(BreakType.PAGE);
	}
	 
	private XWPFRun addText(XWPFParagraph par, String text, int size){
		return addText(par, 0, 1, text, strCommonFontFamily, size, false);
	}
	
	private XWPFRun addText(XWPFParagraph par, int breaksBefore, int breaksAfter, String text, int size){
		return addText(par, breaksBefore, breaksAfter, text, strCommonFontFamily, size, false);
	}
	
	private XWPFRun addText(XWPFParagraph par, int breaksBefore, int breaksAfter, String text, String font, int size, boolean bold){
        XWPFRun run = par.createRun();
        for(int i=0; i<breaksBefore; i++)
        	run.addBreak();
        run.setFontFamily(font);        
        run.setFontSize(size);
        //run.setText(text);
        if(text!=null){
            // Para poder pintar retornos de carro.
        	String[] aTexts = text.split("\\n");
	        for(int j=0; j<aTexts.length; j++){
	        	if(j!=0)
	        		run.addBreak();
	            run.setText(aTexts[j]);        	
	        }
        }
        else{
        	run.setText("");
        }
        run.setBold(bold);
        for(int i=0; i<breaksAfter; i++)
        	run.addBreak();
        return run;
	}
	
	private void addFooter(XWPFDocument doc, ResourceBundle msgs) throws Exception{
		// create footer
		XWPFHeaderFooterPolicy policy = doc.getHeaderFooterPolicy();
		if(policy==null){
	        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
	        policy = new XWPFHeaderFooterPolicy(doc, sectPr);
		}
		CTP ctpFooter = CTP.Factory.newInstance();

		XWPFParagraph[] parsFooter;

		// add style (s.th.)
		CTPPr ctppr = ctpFooter.addNewPPr();
		CTString pst = ctppr.addNewPStyle();
		pst.setVal("style21");
		CTJc ctjc = ctppr.addNewJc();
		ctjc.setVal(STJc.RIGHT);
		ctppr.addNewRPr();

		// Add in word "Page "   
		CTR ctr = ctpFooter.addNewR();
		CTText t = ctr.addNewT();
		t.setStringValue(msgs.getString("cr_man_page_label") + " ");
		t.setSpace(Space.PRESERVE);

		// add everything from the footerXXX.xml you need
		ctr = ctpFooter.addNewR();
		ctr.addNewRPr();
		CTFldChar fch = ctr.addNewFldChar();
		fch.setFldCharType(STFldCharType.BEGIN);

		ctr = ctpFooter.addNewR();
		ctr.addNewInstrText().setStringValue(" " + msgs.getString("cr_man_page_label") + " ");

		ctpFooter.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);

		ctpFooter.addNewR().addNewT().setStringValue("1");

		ctpFooter.addNewR().addNewFldChar().setFldCharType(STFldCharType.END);

		XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, doc);

		parsFooter = new XWPFParagraph[1];

		parsFooter[0] = footerParagraph;

		policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);
	}
	
	private void generateSignedContractPageTwo(XWPFDocument doc, ResourceBundle msgs, SignedContractTemplateBean contract){

        XWPFParagraph parTitle = doc.createParagraph();
        parTitle.setAlignment(ParagraphAlignment.CENTER);
        addText(parTitle, 0, 1, msgs.getString("cr_man_capacity_contract_label"), strCommonFontFamily, iTitle4FontSize, true);
		
		XWPFTable table = doc.createTable();
        
        CTTblPr tblpro = table.getCTTbl().getTblPr();
        CTTblBorders borders = tblpro.addNewTblBorders();
        // Para pintar lineas STBorder.SINGLE, o no establecer (el valor por defecto es con lineas).
        borders.addNewBottom().setVal(STBorder.NONE); 
        borders.addNewLeft().setVal(STBorder.NONE);
        borders.addNewRight().setVal(STBorder.NONE);
        borders.addNewTop().setVal(STBorder.NONE);
        //also inner borders
        borders.addNewInsideH().setVal(STBorder.NONE);
        borders.addNewInsideV().setVal(STBorder.NONE);       

        pageTwoAddRow(table, 0,  true,  msgs.getString("cr_man_date_label"), "");
        pageTwoAddRow(table, 1,  false, msgs.getString("cr_man_this_agreement_label"), msgs.getString("cr_man_this_agreement_value"));
        pageTwoAddRow(table, 2,  true,  msgs.getString("cr_man_parties_label"), "");
        pageTwoAddRow(table, 3,  true,  msgs.getString("cr_man_transporter_label"), msgs.getString("cr_man_transporter_value"));
        pageTwoAddRow(table, 4,  false, msgs.getString("cr_man_trans_id_num_label"), "");
        pageTwoAddRow(table, 5,  false, msgs.getString("cr_man_trans_cap_cont_num_label"), contract.getContractCode());
        pageTwoAddRow(table, 6,  false, msgs.getString("cr_man_address_label"), "");
        pageTwoAddRow(table, 7,  false, msgs.getString("cr_man_tel_label"), "");
        pageTwoAddRow(table, 8,  false, msgs.getString("cr_man_fax_label"), "");
        pageTwoAddRow(table, 9,  false, msgs.getString("cr_man_trans_represent_label"), "");
        pageTwoAddRow(table, 10, false, msgs.getString("cr_man_email_label"), "");
        pageTwoAddRow(table, 11, true,  msgs.getString("cr_man_shipper_label"), "");
        pageTwoAddRow(table, 12, false, msgs.getString("cr_man_shipper_id_num_label"), "");
        pageTwoAddRow(table, 13, false, msgs.getString("cr_man_address_label"), "");
        pageTwoAddRow(table, 14, false, msgs.getString("cr_man_tel_label"), "");
        pageTwoAddRow(table, 15, false, msgs.getString("cr_man_fax_label"), "");
        pageTwoAddRow(table, 16, false, msgs.getString("cr_man_shipper_represent_label"), "");
        pageTwoAddRow(table, 17, false, msgs.getString("cr_man_email_label"), "");

        XWPFParagraph parBack1 = doc.createParagraph();
        addText(parBack1, 0, 1, msgs.getString("cr_man_background_label"), strCommonFontFamily, iNormalFontSize, true);
        
        XWPFParagraph parBack2 = doc.createParagraph();
        setCapsNumberInParagraph(doc, parBack2, 1);
        addText(parBack2, 0, 0, msgs.getString("cr_man_background_value_a"), strCommonFontFamily, iNormalFontSize, false);
        
        XWPFParagraph parBack3 = doc.createParagraph();
        setCapsNumberInParagraph(doc, parBack3, 2);
        addText(parBack3, 0, 0, msgs.getString("cr_man_background_value_b"), strCommonFontFamily, iNormalFontSize, false);
        
        XWPFParagraph parBack4 = doc.createParagraph();
        //setLatinNumberInParagraph(doc, parBack4, 1);
        parBack4.setIndentFromLeft(500);
        addText(parBack4, 0, 0, msgs.getString("cr_man_background_value_i"), strCommonFontFamily, iNormalFontSize, false);
        
        XWPFParagraph parBack5 = doc.createParagraph();
        //setLatinNumberInParagraph(doc, parBack5, 2);
        parBack5.setIndentFromLeft(500);
        addText(parBack5, 0, 0, msgs.getString("cr_man_background_value_ii"), strCommonFontFamily, iNormalFontSize, false);
        
        XWPFParagraph parBack6 = doc.createParagraph();
        //setLatinNumberInParagraph(doc, parBack6, 3);
        parBack6.setIndentFromLeft(500);
        addText(parBack6, 0, 0, msgs.getString("cr_man_background_value_iii"), strCommonFontFamily, iNormalFontSize, false);
        
        XWPFParagraph parBack7 = doc.createParagraph();
        //setLatinNumberInParagraph(doc, parBack7, 4);
        parBack7.setIndentFromLeft(500);
        addText(parBack7, 0, 1, msgs.getString("cr_man_background_value_iv"), strCommonFontFamily, iNormalFontSize, false);
        
        XWPFParagraph parFinal = doc.createParagraph();
        XWPFRun runFinal = parFinal.createRun();
    	runFinal.addBreak(BreakType.PAGE);
	}
	
	private void setCapsNumberInParagraph(XWPFDocument doc, XWPFParagraph par, int num){
		setNumberInParagraph(doc, par, 0, STNumberFormat.UPPER_LETTER, "%1.", num);
        par.setIndentationLeft(500);
        par.setIndentationHanging(450);
	}
	
	/*private void setLatinNumberInParagraph(XWPFDocument doc, XWPFParagraph par, int num){
		setNumberInParagraph(doc, par, 1, STNumberFormat.LOWER_ROMAN, "(%1)", num);
        par.setIndentFromLeft(500);
	}*/
	
	private void setNumberInParagraph(XWPFDocument doc, XWPFParagraph par, 
										int abstractNumberId, STNumberFormat.Enum bulletType, String levelText, int num){
        CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
        //Next we set the AbstractNumId. This requires care. 
        //Since we are in a new document we can start numbering from 0. 
        //But if we have an existing document, we must determine the next free number first.
        cTAbstractNum.setAbstractNumId(BigInteger.valueOf(abstractNumberId));

      /* Bullet list
        CTLvl cTLvl = cTAbstractNum.addNewLvl();
        cTLvl.addNewNumFmt().setVal(STNumberFormat.BULLET);
        cTLvl.addNewLvlText().setVal("•");
      */

      /* Decimal list */
        CTLvl cTLvl = cTAbstractNum.addNewLvl();
        cTLvl.addNewNumFmt().setVal(bulletType);
        cTLvl.addNewLvlText().setVal(levelText);
        cTLvl.addNewStart().setVal(BigInteger.valueOf(num));
        
        XWPFAbstractNum abstractNum = new XWPFAbstractNum(cTAbstractNum);
        XWPFNumbering numbering = doc.createNumbering();
        BigInteger abstractNumID = numbering.addAbstractNum(abstractNum);
        BigInteger numID = numbering.addNum(abstractNumID);
        
        par.setNumID(numID);
	}
	
	private void generateSignedContractDetails(XWPFDocument doc, ResourceBundle msgs, SignedContractTemplateBean contract) throws Exception{
       
		String strTmp = null;
		String xxLabel = "XX";
		String yyLabel = "YY";
        XWPFTable table = doc.createTable();
        
        CTTblPr tblpro = table.getCTTbl().getTblPr();
        CTTblBorders borders = tblpro.addNewTblBorders();
        // Para pintar lineas STBorder.SINGLE, o no establecer (el valor por defecto es con lineas).
        borders.addNewBottom().setVal(STBorder.NONE); 
        borders.addNewLeft().setVal(STBorder.NONE);
        borders.addNewRight().setVal(STBorder.NONE);
        borders.addNewTop().setVal(STBorder.NONE);
        //also inner borders
        borders.addNewInsideH().setVal(STBorder.NONE);
        borders.addNewInsideV().setVal(STBorder.NONE);       

        contractDetailsAddRow(table, 0, msgs.getString("cr_man_details_page_label"),"");
        contractDetailsAddRow(table, 1, msgs.getString("cr_man_commencement_date_label"), contract.getStrCommencementDate());
        contractDetailsAddRow(table, 2, msgs.getString("cr_man_end_date_label"), contract.getStrEndDate());
        contractDetailsAddRow(table, 3, msgs.getString("cr_man_contract_type_label"), contract.getOperationDesc());
        
        strTmp = msgs.getString("cr_man_mmbtu_day");
        strTmp = strTmp.replace(xxLabel, contract.getTotalEntry().toString());
        
        //CH728. 14/02/2019 se vuelve a ponerlos Total
        contractDetailsAddRow(table, 4, msgs.getString("cr_man_total_entry_daily_capacity_label"), strTmp);
       // contractDetailsAddRow(table, 4, msgs.getString("cr_man_max_entry_daily_capacity_label"), strTmp);
        
        strTmp = msgs.getString("cr_man_mmbtu_day_long");
        strTmp = strTmp.replace(xxLabel, contract.getTotalExit().toString());
        
        //CH728 14/02/2019 se vuelve a ponerlos Total
        contractDetailsAddRow(table, 5, msgs.getString("cr_man_total_exit_daily_capacity_label"), strTmp);
        //contractDetailsAddRow(table, 5, msgs.getString("cr_man_max_exit_daily_capacity_label"), strTmp);
        
        contractDetailsAddRow(table, 6, msgs.getString("cr_man_mixing_zone_entry_point_daily_capacity_label"), msgs.getString("cr_man_mmscfd_mmbtu_day"));
        contractDetailsAddRow(table, 7, msgs.getString("cr_man_max_gas_flow_label"), msgs.getString("cr_man_max_gas_flow_value"));
        contractDetailsAddRow(table, 8, msgs.getString("cr_man_entry_point_label"), msgs.getString("cr_man_entry_point_value"));
        contractDetailsAddRow(table, 9, msgs.getString("cr_man_min_injection_pressure_label"), msgs.getString("cr_man_min_injection_pressure_value"));
        contractDetailsAddRow(table, 10,msgs.getString("cr_man_max_injection_pressure_label"), msgs.getString("cr_man_max_injection_pressure_value"));
        contractDetailsAddRow(table, 11, msgs.getString("cr_man_min_withdrawal_pressure_label"), msgs.getString("cr_man_min_withdrawal_pressure_value"));
        contractDetailsAddRow(table, 11, msgs.getString("cr_man_max_withdrawal_pressure_label"), msgs.getString("cr_man_max_withdrawal_pressure_value"));        
        contractDetailsAddRow(table, 12, msgs.getString("cr_man_exit_point_label"), msgs.getString("cr_man_exit_point_value"));
        
        strTmp = msgs.getString("cr_man_temperature_range_value");
        strTmp = strTmp.replace(xxLabel, contract.getMinEntryTemp().toString());
        strTmp = strTmp.replace(yyLabel, contract.getMaxEntryTemp().toString());
        contractDetailsAddRow(table, 13, msgs.getString("cr_man_entry_temperature_range_label"), strTmp);
        
        strTmp = msgs.getString("cr_man_temperature_range_value");
        strTmp = strTmp.replace(xxLabel, contract.getMinExitTemp().toString());
        strTmp = strTmp.replace(yyLabel, contract.getMaxExitTemp().toString());
        contractDetailsAddRow(table, 14, msgs.getString("cr_man_exit_temperature_range_label"), strTmp);
        contractDetailsAddRow(table, 15, msgs.getString("cr_man_rates_label"),"");
        
        if(contract.isFirm())
        	contractDetailsAddRow(table, 16, msgs.getString("cr_man_firm_capacity_charge_fee_label"),msgs.getString("cr_man_thb_mmbtu"));
        else
        	contractDetailsAddRow(table, 16, msgs.getString("cr_man_non_firm_capacity_charge_fee_label"),msgs.getString("cr_man_thb_mmbtu"));
                
        contractDetailsAddRow(table, 17, msgs.getString("cr_man_commodity_charge_fee_label"),msgs.getString("cr_man_thb_mmbtu"));
        contractDetailsAddRow(table, 18, msgs.getString("cr_man_imbalance_penalty_fee_label"),msgs.getString("cr_man_thb_mmbtu"));
        contractDetailsAddRow(table, 19, msgs.getString("cr_man_negative_balancing_penalty_fee_label"),msgs.getString("cr_man_thb_mmbtu"));
        contractDetailsAddRow(table, 20, msgs.getString("cr_man_positive_balancing_penalty_fee_label"),msgs.getString("cr_man_thb_mmbtu"));
        contractDetailsAddRow(table, 21, msgs.getString("cr_man_capacity_over_use_charge_fee_coefficient_label"),"");
        contractDetailsAddRow(table, 22, msgs.getString("cr_man_jurisdiction_label"),msgs.getString("cr_man_jurisdiction_value"));
        contractDetailsAddRow(table, 23, msgs.getString("cr_man_liability_caps_label"),"");
        contractDetailsAddRow(table, 24, msgs.getString("cr_man_liability_cap_for_transporter_label"),msgs.getString("cr_man_box"));
        contractDetailsAddRow(table, 25, false, msgs.getString("cr_man_clause_of_standard_terms_and_conditions_label"),msgs.getString("cr_man_not_applicable_long"));
        contractDetailsAddRow(table, 26, msgs.getString("cr_man_liability_cap_for_shipper_label"),msgs.getString("cr_man_box"));
        contractDetailsAddRow(table, 27, false, msgs.getString("cr_man_clause_of_standard_terms_and_conditions_label"),msgs.getString("cr_man_not_applicable"));
        
        if(contract.isFirm()) {
	        Date dRenewalDeadline = contract.getRenewalDeadline();
	        String strRenewalDeadline = dRenewalDeadline!=null? sdfNormalDate.format(dRenewalDeadline) : "";
	        contractDetailsAddRow(table, 28, msgs.getString("cr_man_renewal_deadline_label"), strRenewalDeadline);
        }
        
        XWPFParagraph par = doc.createParagraph();
        XWPFRun runFinal = par.createRun();
    	runFinal.addBreak(BreakType.PAGE);
	}
	
	// Casi siempre se usara este metodo con la label en negrita.
	private void contractDetailsAddRow(XWPFTable tab, int rowId, String label, String value){
		contractDetailsAddRow(tab, rowId, true, label, value);
	}
	
	private void contractDetailsAddRow(XWPFTable tab, int rowId, boolean boldLabel, String label, String value){
		//column length 2880 is measured in twentieths of a point (dxa) or 1/1440 inch which is equal to 2 inches.
		twoColumnsTableAddRow(tab, 5040, 5040, rowId, boldLabel, label, value);
	}
	
	private void pageTwoAddRow(XWPFTable tab, int rowId, boolean boldLabel, String label, String value){
		//column length 2880 is measured in twentieths of a point (dxa) or 1/1440 inch which is equal to 2 inches.
		twoColumnsTableAddRow(tab, 4400, 5680, rowId, boldLabel, label, value);
	}
	
	private void twoColumnsTableAddRow(XWPFTable tab, int colOneSize, int colTwoSize, 
										int rowId, boolean boldLabel, String label, String value){
        XWPFTableRow tableRow;
        if(rowId==0)
        	tableRow = tab.getRow(0);
        else
        	tableRow = tab.createRow();
        
        XWPFTableCell cellOne = tableRow.getCell(0);
        //column length 2880 is measured in twentieths of a point (dxa) or 1/1440 inch which is equal to 2 inches.
        cellOne.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(colOneSize));
        // Al crearse la tabla la primera celda ya tiene un parrafo. Se obtiene la lista y se coge el primer elemento.
        List<XWPFParagraph> cellOneParagList = cellOne.getParagraphs();
        XWPFParagraph parCellOne;
        if(cellOneParagList!=null && cellOneParagList.size()!=0 && cellOneParagList.get(0)!=null)
        	parCellOne = cellOneParagList.get(0);
        else
        	parCellOne = cellOne.addParagraph();
        addText(parCellOne, 0, 0, label, strCommonFontFamily, iNormalFontSize, boldLabel);
        
        //Al tratar la primera fila, se ha de crear la segunda columna, pero en sucesivas filas, ya se la encuentra creada. 
        XWPFTableCell cellTwo;
        // Cuando se esta en la primera fila hay que añadir la segunda columna.
        if(rowId==0)
        	cellTwo = tableRow.addNewTableCell();
        else
        	cellTwo = tableRow.getCell(1);
        cellTwo.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(colTwoSize));
        //rowOneCellTwo.setText(value);
        List<XWPFParagraph> cellTwoParagList = cellTwo.getParagraphs();
        XWPFParagraph parCellTwo;
        if(cellTwoParagList!=null && cellTwoParagList.size()!=0 && cellTwoParagList.get(0)!=null)
        	parCellTwo = cellTwoParagList.get(0);
        else
        	parCellTwo = cellTwo.addParagraph();
        addText(parCellTwo, 0, 0, value, strCommonFontFamily, iNormalFontSize, false);
	}
	
	
	private void generateSignedContractEntryData(XWPFDocument doc, ResourceBundle msgs, SignedContractTemplateBean contract) throws Exception {
		Date dPageDate = null;
		String strPageDate = null;
		
        XWPFParagraph parSched = doc.createParagraph();
        parSched.setAlignment(ParagraphAlignment.CENTER);
        addText(parSched, 0, 1, msgs.getString("cr_man_schedule"), strCommonFontFamily, iTitle4FontSize, true);	

        XWPFParagraph parContType = doc.createParagraph();
        addText(parContType, 0, 0, msgs.getString("cr_man_contract_type_label"), strCommonFontFamily, iTitle4FontSize, true);
    	addText(parContType, 0, 4, "          " + contract.getContractType(), iNormalFontSize);
    	addText(parContType, 0, 0, msgs.getString("cr_man_entry_point_table_title"), strCommonFontFamily, iTitle4FontSize, true);
  	        
    	List<SignedContractTempPeriodBean> lPeriods = contract.getPeriods();
    	if(lPeriods!=null) {
	    	for(SignedContractTempPeriodBean period: lPeriods) {
	            XWPFParagraph parPeriod = doc.createParagraph();
	            dPageDate = period.getPageDate();
	            if(contract.isShort()){
		            addText(parPeriod, 1, 0, msgs.getString("cr_man_period"), strCommonFontFamily, iTitle4FontSize, true);
		            strPageDate = dPageDate!=null? sdfMonthYear.format(dPageDate) : "";
	            }
	            else {
		            addText(parPeriod, 1, 0, msgs.getString("cr_man_year"), strCommonFontFamily, iTitle4FontSize, true);
		            strPageDate = dPageDate!=null? sdfYear.format(dPageDate) : "";
	            }
	            addText(parPeriod, 0, 0, "          " + strPageDate, iNormalFontSize);
	    		
		        XWPFTable table = doc.createTable();
		        //table.setWidth(2000); No funciona
		        table.getCTTbl().addNewTblPr().addNewTblW().setW(BigInteger.valueOf(10000));
		        table.setCellMargins(iTopMargin, iLeftMargin, iBottomMargin, iRightMargin);	// (top, left, bottom, right)
		        addTitleCols2EntryTable(table, msgs);
				// Para ir acumulando valores.
				SignedContractTempPointBean totalPoint = new SignedContractTempPointBean();
		    	addDataRows2EntryTable(table, period.getEntryPoints(), totalPoint);
		    	addTotalCols2EntryTable(table, msgs, totalPoint);
	    	}
		}
	}
	
	private void generateSignedContractExitData(XWPFDocument doc, ResourceBundle msgs, SignedContractTemplateBean contract) throws Exception {
		Date dPageDate = null;
		String strPageDate = null;
		
		XWPFParagraph parExitPointsTitle = doc.createParagraph();
    	addText(parExitPointsTitle, 3, 0, msgs.getString("cr_man_exit_point_table_title"), strCommonFontFamily, iTitle4FontSize, true);
    	
    	List<SignedContractTempPeriodBean> lPeriods = contract.getPeriods();
    	if(lPeriods!=null) {
	    	for(SignedContractTempPeriodBean period: lPeriods) {
	            XWPFParagraph parPeriod = doc.createParagraph();
	            dPageDate = period.getPageDate();
	            if(contract.isShort()){
		            addText(parPeriod, 1, 0, msgs.getString("cr_man_period"), strCommonFontFamily, iTitle4FontSize, true);
		            strPageDate = dPageDate!=null? sdfMonthYear.format(dPageDate) : "";
	            }
	            else {
		            addText(parPeriod, 1, 0, msgs.getString("cr_man_year"), strCommonFontFamily, iTitle4FontSize, true);
		            strPageDate = dPageDate!=null? sdfYear.format(dPageDate) : "";
	            }
	            addText(parPeriod, 0, 0, "          " + strPageDate, iNormalFontSize);
	            
		        XWPFTable table = doc.createTable();
		        table.getCTTbl().addNewTblPr().addNewTblW().setW(BigInteger.valueOf(10000));
		        table.setCellMargins(iTopMargin, iLeftMargin, iBottomMargin, iRightMargin);	// (top, left, bottom, right)
		        addTitleCols2ExitTable(table, msgs);
				// Para ir acumulando valores.
				SignedContractTempPointBean totalPoint = new SignedContractTempPointBean();
		    	addDataRows2ExitTable(table, period.getExitPoints(), totalPoint);
		    	addTotalCols2ExitTable(table, msgs, totalPoint);
	    	}
		}
	}
	
	private void addTitleCols2EntryTable(XWPFTable table, ResourceBundle msgs) {
        // Al crear la tabla, se crea ya la primera fila.
        XWPFTableRow tableRow = table.getRow(0);
        tableRow.setRepeatHeader(true);
        
        addTitleCol2EntryRow(tableRow, 0, msgs.getString("cr_man_zone"), null, 1);
        addTitleCol2EntryRow(tableRow, 1, msgs.getString("cr_man_point_id"), null, 2);
        addTitleCol2EntryRow(tableRow, 2, msgs.getString("cr_man_pressure_min"), msgs.getString("cr_man_pressure_unit"), 1);
        addTitleCol2EntryRow(tableRow, 3, msgs.getString("cr_man_pressure_max"), msgs.getString("cr_man_pressure_unit"), 1);
        addTitleCol2EntryRow(tableRow, 4, msgs.getString("cr_man_daily_reserved_capacity"), msgs.getString("cr_man_daily_reserved_capacity_volume_unit"), 1);
        addTitleCol2EntryRow(tableRow, 5, msgs.getString("cr_man_daily_reserved_capacity"), msgs.getString("cr_man_daily_reserved_capacity_energy_unit"), 1);
        addTitleCol2EntryRow(tableRow, 6, msgs.getString("cr_man_max_gas_flow_label_rate"), msgs.getString("cr_man_max_gas_flow_label_rate_unit"), 1);
        addTitleCol2EntryRow(tableRow, 7, msgs.getString("cr_man_remark_label"), null, 2);
	}
	
	private void addTitleCols2ExitTable(XWPFTable table, ResourceBundle msgs) {
        // Al crear la tabla, se crea ya la primera fila.
        XWPFTableRow tableRow = table.getRow(0);
        tableRow.setRepeatHeader(true);
        
        addTitleCol2ExitRow(tableRow, 0, msgs.getString("cr_man_zone"), null, 1);
        addTitleCol2ExitRow(tableRow, 1, msgs.getString("cr_man_customer_type"), null, 2);
        addTitleCol2ExitRow(tableRow, 2, msgs.getString("cr_man_point_id"), null, 2);
        addTitleCol2ExitRow(tableRow, 3, msgs.getString("cr_man_pressure_min"), msgs.getString("cr_man_pressure_unit"), 1);
        addTitleCol2ExitRow(tableRow, 4, msgs.getString("cr_man_pressure_max"), msgs.getString("cr_man_pressure_unit"), 1);
        addTitleCol2ExitRow(tableRow, 5, msgs.getString("cr_man_daily_reserved_capacity"), msgs.getString("cr_man_daily_reserved_capacity_energy_unit"), 1);
        addTitleCol2ExitRow(tableRow, 6, msgs.getString("cr_man_max_gas_flow_label_rate"), msgs.getString("cr_man_max_gas_flow_label_rate_unit"), 1);
        addTitleCol2ExitRow(tableRow, 7, msgs.getString("cr_man_remark_label"), null, 2);	
	}
	
	private void addTitleCol2EntryRow(XWPFTableRow row, int colId, String title, String unit, double size) {
		addTitleCol2Row(row, colId, title, unit, size, strEntryTableTitleColor);
	}
	
	private void addTitleCol2ExitRow(XWPFTableRow row, int colId, String title, String unit, double size) {
		addTitleCol2Row(row, colId, title, unit, size, strExitTableTitleColor);
	}
	
	private void addTitleCol2Row(XWPFTableRow row, int colId, String title, String unit, double size, String color) {
        XWPFTableCell cell = null;
        if(colId == 0)
        	cell = row.getCell(0);
        else
        	cell = row.addNewTableCell();
        
        //column length 2880 is measured in twentieths of a point (dxa) or 1/1440 inch which is equal to 2 inches.
        cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf((long)(1440*size)));
        cell.setColor(color);
        // Al crearse la tabla la primera celda ya tiene un parrafo. Se obtiene la lista y se coge el primer elemento.
        List<XWPFParagraph> cellParagList = cell.getParagraphs();
        XWPFParagraph parCell;
        if(cellParagList!=null && cellParagList.size()!=0 && cellParagList.get(0)!=null)
        	parCell = cellParagList.get(0);
        else
        	parCell = cell.addParagraph();
        parCell.setAlignment(ParagraphAlignment.CENTER);
        parCell.setVerticalAlignment(TextAlignment.CENTER);
        addText(parCell, 0, 0, title, strCommonFontFamily, iLittleFontSize, true);
        
        // Si se indica pintar una unidad en el titulo, se pinta sin negrita.
        if(unit!=null){
            XWPFParagraph parCell2 = cell.addParagraph();
            parCell2.setAlignment(ParagraphAlignment.CENTER);
            parCell2.setVerticalAlignment(TextAlignment.CENTER);
            addText(parCell2, 0, 0, unit, strCommonFontFamily, iLittleFontSize, false);
        }
	}
	
	private void addDataRows2EntryTable(XWPFTable table, 
										List<SignedContractTempPointBean> points,
										SignedContractTempPointBean totalPoint) {
		
		// Se usa para guardar el estado del punto anterior al que se esta tratando para saber si hay que cambiar color shadow 
		// o combinar celdas.
		SignedContractTempPointBean lastPoint = new SignedContractTempPointBean();
		
    	if(points!=null){
    		for(int i=0; i<points.size(); i++){   
    			SignedContractTempPointBean point = points.get(i);
    			lastPoint = addDataCols2EntryTable(table, lastPoint, i, point, totalPoint);
    		}
    	}
	}
	
	private void addDataRows2ExitTable(XWPFTable table, 
										List<SignedContractTempPointBean> points,
										SignedContractTempPointBean totalPoint) {
		
		// Se usa para guardar el estado del punto anterior al que se esta tratando para saber si hay que cambiar color shadow 
		// o combinar celdas.
		SignedContractTempPointBean lastPoint = new SignedContractTempPointBean();
		
    	if(points!=null){
    		for(int i=0; i<points.size(); i++){   
    			SignedContractTempPointBean point = points.get(i);
    			lastPoint = addDataCols2ExitTable(table, lastPoint, i, point, totalPoint);
    		}
    	}
	}
	
	private SignedContractTempPointBean addDataCols2EntryTable(XWPFTable table, 
																SignedContractTempPointBean lastPoint,
																int rowId, 
																SignedContractTempPointBean entryPoint, 
																SignedContractTempPointBean totalPoint) {
		
		String lastZoneCode = lastPoint.getZoneCode();
		boolean bLastShadowColor = lastPoint.isbShadowColor();
		// Marcadores para ver si hay que hacer merge de la primera columna (ZoneCode) y de la segunda (CustomerType).
		// CustomerType solo se usa en salidas.
		int numColumns = table.getRow(0).getTableCells().size();
		int[] iMerge = new int[numColumns];
		for(int i=0; i<numColumns; i++)
			iMerge[i] = iNoMerge;
		
		// Al crear la tabla, se crea ya la primera fila, pero las sucesivas a partir del titulo, hay que crearlas.
        if(entryPoint!=null){
			XWPFTableRow tableRow = table.createRow();
	        String strTmp = null;
	        
	        strTmp = entryPoint.getZoneCode();
	        strTmp = strTmp!=null? strTmp : "";
			// Si cambia la zona, se cambia el valor del booleano que pinta color shadow.
			if(lastZoneCode==null){
				iMerge[0] = iStartMerge;
			}
			else {
				if(!lastZoneCode.equals(strTmp)) {
					bLastShadowColor = !bLastShadowColor;
					iMerge[0] = iStartMerge;
				}
				else {
					iMerge[0] = iContinueMerge;
				}
	        }
	        // Se actualizan los valores del ultimo punto.
			lastPoint.setZoneCode(strTmp);
			lastPoint.setbShadowColor(bLastShadowColor);
			addDataCol2EntryRow(tableRow, rowId, 0, strTmp, bLastShadowColor, iMerge);
	        
			strTmp = entryPoint.getPointCode();
			strTmp = strTmp!=null? strTmp : "";
			addDataCol2EntryRow(tableRow, rowId, 1, strTmp, bLastShadowColor);
	        
			strTmp = entryPoint.getMinPressure().toString();
			strTmp = strTmp!=null? strTmp : "";
			addDataCol2EntryRow(tableRow, rowId, 2, strTmp, bLastShadowColor);
	        
			strTmp = entryPoint.getMaxPressure().toString();
			strTmp = strTmp!=null? strTmp : "";
			addDataCol2EntryRow(tableRow, rowId, 3, strTmp, bLastShadowColor);
	        
			strTmp = entryPoint.getVolume().toString();
			strTmp = strTmp!=null? strTmp : "";
			addDataCol2EntryRow(tableRow, rowId, 4, strTmp, bLastShadowColor);
	        
			strTmp = entryPoint.getQuantity().toString();
			strTmp = strTmp!=null? strTmp : "";
			addDataCol2EntryRow(tableRow, rowId, 5, strTmp, bLastShadowColor);
	        
			strTmp = entryPoint.getMaxHourQuantity().toString();
			strTmp = strTmp!=null? strTmp : "";
			addDataCol2EntryRow(tableRow, rowId, 6, strTmp, bLastShadowColor);
	        
			// Remarks es una columna vacia en la que se pueda insertar texto.
			addDataCol2EntryRow(tableRow, rowId, 7, "", bLastShadowColor);
			
			// Para actualizar totales.
			BigDecimal tmpVol = totalPoint.getVolume();
			if(tmpVol!=null)
				totalPoint.setVolume(tmpVol.add(entryPoint.getVolume()));
			else
				totalPoint.setVolume(entryPoint.getVolume());
			
			BigDecimal tmpQua = totalPoint.getQuantity();
			if(tmpQua!=null)
				totalPoint.setQuantity(tmpQua.add(entryPoint.getQuantity()));
			else
				totalPoint.setQuantity(entryPoint.getQuantity());
        }

        return lastPoint;
    }

	private SignedContractTempPointBean addDataCols2ExitTable(XWPFTable table,
																SignedContractTempPointBean lastPoint,
																int rowId, 
																SignedContractTempPointBean exitPoint, 
																SignedContractTempPointBean totalPoint) {

		String lastZoneCode = lastPoint.getZoneCode();
		String lastCustCode = lastPoint.getCustomerCode();
		boolean bLastShadowColor = lastPoint.isbShadowColor();
		// Marcadores para ver si hay que hacer merge de la primera columna (ZoneCode) y de la segunda (CustomerType).
		// CustomerType solo se usa en salidas.
		int numColumns = table.getRow(0).getTableCells().size();
		int[] iMerge = new int[numColumns];
		for(int i=0; i<numColumns; i++)
			iMerge[i] = iNoMerge;
		
		// Al crear la tabla, se crea ya la primera fila, pero las sucesivas a partir del titulo, hay que crearlas.
        if(exitPoint!=null){
			XWPFTableRow tableRow = table.createRow();
	        String strTmp = null;
	        
	        strTmp = exitPoint.getZoneCode();
			strTmp = strTmp!=null? strTmp : "";
			// Si cambia la zona, se cambia el valor del booleano que pinta color shadow. Tambien se actualiza el marcador de merges.
			if(lastZoneCode==null){
				iMerge[0] = iStartMerge;
			}
			else {
				if(!lastZoneCode.equals(strTmp)) {
					bLastShadowColor = !bLastShadowColor;
					iMerge[0] = iStartMerge;
				}
				else {
					iMerge[0] = iContinueMerge;
				}
	        }
	        // Se actualizan los valores del ultimo punto.
			lastPoint.setZoneCode(strTmp);
			lastPoint.setbShadowColor(bLastShadowColor);
			addDataCol2ExitRow(tableRow, rowId, 0, strTmp, bLastShadowColor, iMerge);
	        
			strTmp = exitPoint.getCustomerCode();
			strTmp = strTmp!=null? strTmp : "";
			// Si cambia el customerType, tambien se actualiza el marcador de merges.
			if(lastCustCode==null){
				iMerge[1] = iStartMerge;
			}
			else {
				if(!lastCustCode.equals(strTmp)) {
					iMerge[1] = iStartMerge;
				}
				else {
					iMerge[1] = iContinueMerge;
				}
	        }
	        // Se actualizan los valores del ultimo punto.
			lastPoint.setCustomerCode(strTmp);
			addDataCol2ExitRow(tableRow, rowId, 1, strTmp, bLastShadowColor, iMerge);
			
			strTmp = exitPoint.getPointCode();
			strTmp = strTmp!=null? strTmp : "";
			addDataCol2ExitRow(tableRow, rowId, 2, strTmp, bLastShadowColor);
	        
			strTmp = exitPoint.getMinPressure().toString();
			strTmp = strTmp!=null? strTmp : "";
			addDataCol2ExitRow(tableRow, rowId, 3, strTmp, bLastShadowColor);
	        
			strTmp = exitPoint.getMaxPressure().toString();
			strTmp = strTmp!=null? strTmp : "";
			addDataCol2ExitRow(tableRow, rowId, 4, strTmp, bLastShadowColor);
	        
			strTmp = exitPoint.getQuantity().toString();
			strTmp = strTmp!=null? strTmp : "";
			addDataCol2ExitRow(tableRow, rowId, 5, strTmp, bLastShadowColor);
	        
			strTmp = exitPoint.getMaxHourQuantity().toString();
			strTmp = strTmp!=null? strTmp : "";
			addDataCol2ExitRow(tableRow, rowId, 6, strTmp, bLastShadowColor);
	        
			// Remarks es una columna vacia en la que se pueda insertar texto.
			addDataCol2ExitRow(tableRow, rowId, 7, "", bLastShadowColor);
			
			// Para actualizar totales.
			BigDecimal tmpQua = totalPoint.getQuantity();
			if(tmpQua!=null)
				totalPoint.setQuantity(tmpQua.add(exitPoint.getQuantity()));
			else
				totalPoint.setQuantity(exitPoint.getQuantity());
        }

        return lastPoint;
    }
	
	private void addDataCol2EntryRow(XWPFTableRow row, int rowId, int colId, String text, boolean bShadowColor) {
		int numColumns = row.getTableCells().size();
		int[] iMerge = new int[numColumns];
		for(int i=0; i<numColumns; i++)
			iMerge[i] = iNoMerge;
		addDataCol2EntryRow(row, rowId, colId, text, bShadowColor, iMerge);
	}
	
	private void addDataCol2EntryRow(XWPFTableRow row, int rowId, int colId, String text, boolean bShadowColor, int[] iMerge) {
		String colColor = null;
		if(bShadowColor)
			colColor = strEntryTableShadowColor;
		addDataCol2Row(row, rowId, colId, text, colColor, iMerge);
	}
	
	private void addDataCol2ExitRow(XWPFTableRow row, int rowId, int colId, String text, boolean bShadowColor) {
		int numColumns = row.getTableCells().size();
		int[] iMerge = new int[numColumns];
		for(int i=0; i<numColumns; i++)
			iMerge[i] = iNoMerge;
		addDataCol2ExitRow(row, rowId, colId, text, bShadowColor, iMerge);
	}
	
	private void addDataCol2ExitRow(XWPFTableRow row, int rowId, int colId, String text, boolean bShadowColor, int[] iMerge) {
		String colColor = null;
		if(bShadowColor)
			colColor = strExitTableShadowColor;
		addDataCol2Row(row, rowId, colId, text, colColor, iMerge);
	}
	
	private void addDataCol2Row(XWPFTableRow row, int rowId, int colId, String text, String shadowColor, int[] iMerge) {
        XWPFTableCell cell = row.getCell(colId);

        // Como la tabla ya esta creada a partir de las filas superiores, no hay que asignar el tamaño de la columna.
        //column length 2880 is measured in twentieths of a point (dxa) or 1/1440 inch which is equal to 2 inches.
        //cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf((long)(1440*size)));

        if(shadowColor != null)
        	cell.setColor(shadowColor);
        // Al crearse la tabla la primera celda ya tiene un parrafo. Se obtiene la lista y se coge el primer elemento.
        List<XWPFParagraph> cellParagList = cell.getParagraphs();
        XWPFParagraph parCell;
        if(cellParagList!=null && cellParagList.size()!=0 && cellParagList.get(0)!=null)
        	parCell = cellParagList.get(0);
        else
        	parCell = cell.addParagraph();
        parCell.setAlignment(ParagraphAlignment.RIGHT);
        addText(parCell, 0, 0, text, strCommonFontFamily, iLittleFontSize, false);
        
        if(iMerge[colId]!=iNoMerge){
        	CTVMerge vmerge = CTVMerge.Factory.newInstance();
        	if(iMerge[colId]==iStartMerge)  // First Row
	        	vmerge.setVal(STMerge.RESTART);
        	else	// Secound Row cell will be merged
        		vmerge.setVal(STMerge.CONTINUE);
        	
    		if(cell.getCTTc().getTcPr()!=null)
    			cell.getCTTc().getTcPr().setVMerge(vmerge);
    		else
    			cell.getCTTc().addNewTcPr().setVMerge(vmerge);
        }
	}
	
	private void addTotalCols2EntryTable(XWPFTable table, ResourceBundle msgs, SignedContractTempPointBean totalPoint) {

		XWPFTableRow tableRow = table.createRow();
		
        addTotalCol2EntryRow(tableRow, 0, msgs.getString("cr_man_total_entry"), ParagraphAlignment.LEFT);
        addTotalCol2EntryRow(tableRow, 1, "", ParagraphAlignment.LEFT);
        addTotalCol2EntryRow(tableRow, 2, "", ParagraphAlignment.RIGHT);
        addTotalCol2EntryRow(tableRow, 3, "", ParagraphAlignment.RIGHT);
        addTotalCol2EntryRow(tableRow, 4, totalPoint.getVolume().toString(), ParagraphAlignment.RIGHT);
        addTotalCol2EntryRow(tableRow, 5, totalPoint.getQuantity().toString(), ParagraphAlignment.RIGHT);
        addTotalCol2EntryRow(tableRow, 6, "", ParagraphAlignment.RIGHT);
        addTotalCol2EntryRow(tableRow, 7, "", ParagraphAlignment.LEFT);
	}
	
	private void addTotalCols2ExitTable(XWPFTable table, ResourceBundle msgs, SignedContractTempPointBean totalPoint) {

		XWPFTableRow tableRow = table.createRow();
		
		addTotalCol2ExitRow(tableRow, 0, msgs.getString("cr_man_total_exit"), ParagraphAlignment.LEFT);
		addTotalCol2ExitRow(tableRow, 1, "", ParagraphAlignment.LEFT);
		addTotalCol2ExitRow(tableRow, 2, "", ParagraphAlignment.LEFT);
		addTotalCol2ExitRow(tableRow, 3, "", ParagraphAlignment.RIGHT);
		addTotalCol2ExitRow(tableRow, 4, "", ParagraphAlignment.RIGHT);
        addTotalCol2ExitRow(tableRow, 5, totalPoint.getQuantity().toString(), ParagraphAlignment.RIGHT);
        addTotalCol2ExitRow(tableRow, 6, "", ParagraphAlignment.RIGHT);
        addTotalCol2ExitRow(tableRow, 7, "", ParagraphAlignment.LEFT);
	}

	private void addTotalCol2EntryRow(XWPFTableRow row, int colId, String text, ParagraphAlignment align) {
		addTotalCol2Row(row, colId, text, align, strEntryTableTitleColor);
	}
	
	private void addTotalCol2ExitRow(XWPFTableRow row, int colId, String text, ParagraphAlignment align) {
		addTotalCol2Row(row, colId, text, align, strExitTableTitleColor);
	}
	
	private void addTotalCol2Row(XWPFTableRow row, int colId, String text, ParagraphAlignment align, String color) {
        XWPFTableCell cell = row.getCell(colId);
        
       	cell.setColor(color);
        // Al crearse la tabla la primera celda ya tiene un parrafo. Se obtiene la lista y se coge el primer elemento.
        List<XWPFParagraph> cellParagList = cell.getParagraphs();
        XWPFParagraph parCell;
        if(cellParagList!=null && cellParagList.size()!=0 && cellParagList.get(0)!=null)
        	parCell = cellParagList.get(0);
        else
        	parCell = cell.addParagraph();
        parCell.setAlignment(align);
        addText(parCell, 0, 0, text, strCommonFontFamily, iLittleFontSize, true);
	}
}
