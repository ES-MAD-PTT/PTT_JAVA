package com.atos.services.nominations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.NotificationBean;
import com.atos.beans.nominations.NominationDetailBean;
import com.atos.beans.nominations.NominationHeaderBean;
import com.atos.beans.nominations.NominationQualityGasDayBean;
import com.atos.beans.nominations.NominationQualityGasWeekBean;
import com.atos.beans.nominations.OperationFileBean;
import com.atos.beans.nominations.OperationTemplateBean;
import com.atos.beans.nominations.ProcessManagementBean;
import com.atos.beans.nominations.QualityTableBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.nominations.NominationFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.nominations.IntermediateShipperSubmissionNominationFileMapper;
import com.atos.mapper.nominations.NominationMapper;
import com.atos.mapper.nominations.ShipperSubmissionNominationsMapper;
import com.atos.mapper.utils.Xlsx2XmlMapper;
import com.atos.utils.Constants;
import com.atos.utils.Xlsx2XmlConverter;

@Service("dailyNominationService")
public class DailyNominationServiceImpl implements DailyNominationService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6008063454154512438L;
	private static final String strNotifTypeCode = "DAILY.NOMINATION.REJECTED";	
	private static final String strNotifOrigin = "NOMINATION";
	private static final Logger log = LogManager.getLogger(DailyNominationServiceImpl.class);		
	@Autowired
	private NominationMapper nomMapper;

	@Autowired
	private IntermediateShipperSubmissionNominationFileMapper intMapper;
	
	@Autowired
	private ShipperSubmissionNominationsMapper dailyNomMapper;

	@Autowired
	private Xlsx2XmlMapper xMapper;
	
	private Xlsx2XmlConverter xmlConverter = null;

	@Autowired
	private NotificationMapper notifMapper;
	
	@Override
	public List<NominationHeaderBean> selectNomination(NominationFilter filter) {
		return nomMapper.selectNomination(filter);
	}

	@Override
	public NominationHeaderBean selectMaxVersionNomination(String nomCode) {
		List<NominationHeaderBean> lnhBean = nomMapper.selectMaxVersionNomination(nomCode);
		if(lnhBean.size()==0){
			return null;
		} else {
			return lnhBean.get(0);
		}
	}
	
	@Override
	public List<NominationDetailBean> selectDetailNomination(NominationHeaderBean bean) {
		return nomMapper.selectDetailNomination(bean);
	}
	
	@Override
	public Map<String, Object> selectContractCodeByUser(NominationFilter filter) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
 		List<ComboFilterNS> list = nomMapper.selectContractCodeDayByShipper(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey().toString(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<String, Object> selectShipperIdNominations(NominationFilter filter) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
 		List<ComboFilterNS> list = nomMapper.selectShipperIdNominations(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey().toString(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<String, BigDecimal> selectZonesNomination() {
		Map<String, BigDecimal> map = new LinkedHashMap<String, BigDecimal>();
 		List<ComboFilterNS> list = nomMapper.selectZonesNomination();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getValue(), combo.getKey());
		}
		return map;
	}

	@Override
	public BigDecimal selectOperationCategory() {
		List<BigDecimal> bd = dailyNomMapper.selectOperationCategory(Constants.NOMINATION);
		if(bd.size()==0){
			return null;
		} else {
			return bd.get(0);
		}
	}

	@Override
	public BigDecimal selectOperationTerm() {
		List<BigDecimal> bd = dailyNomMapper.selectOperationTerm(Constants.DAILY);
		if(bd.size()==0){
			return null;
		} else {
			return bd.get(0);
		}
	}	
	
	@Override
	public BigDecimal selectOperation(TreeMap<String, BigDecimal> map) {
		List<BigDecimal> bd = dailyNomMapper.selectOperation(map);
		if(bd.size()==0){
			return null;
		} else {
			return bd.get(0);
		}
	}

	@Override
	public StreamedContent getFile(TreeMap<String, BigDecimal> map) {
		List<OperationFileBean> l = nomMapper.selectGetFile(map);
		if(l.size()==0){
			return null;
		} else {
			OperationFileBean bean = l.get(0);
			ByteArrayInputStream ba = new ByteArrayInputStream(bean.getBinary_data());
			return new DefaultStreamedContent(ba, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", bean.getFile_name());
		}
	}

	@Override
	public byte[] getSimoneFile(TreeMap<String, BigDecimal> map) {
		List<OperationFileBean> l = nomMapper.selectGetFile(map);
		if(l.size()==0){
			return null;
		} else {
			OperationFileBean bean = l.get(0);
			return bean.getBinary_data();
		}
	}

	
	@Override
	public List<NominationQualityGasDayBean> selectQualityGasDayNomination(NominationHeaderBean bean) {
		return nomMapper.selectQualityGasDayNomination(bean);
	}

	@Override
	public List<NominationQualityGasWeekBean> selectQualityGasWeekNomination(NominationHeaderBean bean) {
		return nomMapper.selectQualityGasWeekNomination(bean);
	}

	@Override
	public List<QualityTableBean> selectQualityNominationTable(NominationHeaderBean bean) {
		return nomMapper.selectQualityNominationTable(bean);
	}

	@Override
	public List<NominationDetailBean> selectParkUnparkNomination(NominationHeaderBean bean) {
		return nomMapper.selectParkUnparkNomination(bean);
	}

	@Override
	public void prorateCalculation(List<NominationDetailBean> area_tab, List<NominationQualityGasDayBean> quality) {
		
		// prorate all the registers of the quality of the area
		for(int i=0;i<quality.size();i++){
			NominationQualityGasDayBean bd = quality.get(i);
			// for all the hours of the day
			for(int h=1;h<26;h++){
				BigDecimal division_sup = new BigDecimal(0);
				BigDecimal division_inf = new BigDecimal(0);
				// we search for all the points
				for(int k=0;k<bd.getPoints().size();k++){
					
					// we search the point in the area array list
					for(int j=0;j<area_tab.size();j++){
						NominationDetailBean b = area_tab.get(j);
						// if the points matches
						if(bd.getPoints().get(k).getUsed_point().equals(b.getPoint_id())){
							// if we must calculate with WI
							if(bd.getPoints().get(k).getParameter_code().equals("WI") && b.getWi() !=null){
								BigDecimal wi = (b.getWi() !=null ? b.getWi() : new BigDecimal(0));
								division_sup =  division_sup.add(wi.multiply(getHour(h,b)));
								division_inf = division_inf.add(getHour(h,b));
							}
							// if we must calculate with HV
							if(bd.getPoints().get(k).getParameter_code().equals("HV") && b.getHv() != null){
								BigDecimal hv = (b.getHv() !=null ? b.getHv() : new BigDecimal(0));
								division_sup =  division_sup.add(hv.multiply(getHour(h,b)));
								division_inf = division_inf.add(getHour(h,b));
							}
							// if we must calculate with SG
							if(bd.getPoints().get(k).getParameter_code().equals("SG") && b.getSg() != null){
								BigDecimal sg = (b.getSg() !=null ? b.getSg() : new BigDecimal(0));
								division_sup =  division_sup.add(sg.multiply(getHour(h,b)));
								division_inf = division_inf.add(getHour(h,b));
							}
						}
					}
				}
				if(division_inf.doubleValue()!=0){
					setHour(h, bd, division_sup.divide(division_inf,3,RoundingMode.HALF_UP));
				} else {
					setHour(h, bd, new BigDecimal(0));
				}
				
			}
		}
		setTotal(area_tab);

	}
	
	private void setTotal(List<NominationDetailBean> area_tab) {
		
		for(int i=0;i<area_tab.size();i++){
			NominationDetailBean b = area_tab.get(i);
			BigDecimal total = new BigDecimal(0);
			for(int h=1;h<25;h++){
				total = total.add(getHour(h,b));
			}
			b.setTotal(total);
		}
		
	}

	private void setHour(int hour, NominationQualityGasDayBean b, BigDecimal value){
		if(hour == 1){
			b.setHour_01(value);
		}
		if(hour == 2){
			b.setHour_02(value);
		}
		if(hour == 3){
			b.setHour_03(value);
		}
		if(hour == 4){
			b.setHour_04(value);
		}
		if(hour == 5){
			b.setHour_05(value);
		}
		if(hour == 6){
			b.setHour_06(value);
		}
		if(hour == 7){
			b.setHour_07(value);
		}
		if(hour == 8){
			b.setHour_08(value);
		}
		if(hour == 9){
			b.setHour_09(value);
		}
		if(hour == 10){
			b.setHour_10(value);
		}
		if(hour == 11){
			b.setHour_11(value);
		}
		if(hour == 12){
			b.setHour_12(value);
		}
		if(hour == 13){
			b.setHour_13(value);
		}
		if(hour == 14){
			b.setHour_14(value);
		}
		if(hour == 15){
			b.setHour_15(value);
		}
		if(hour == 16){
			b.setHour_16(value);
		}
		if(hour == 17){
			b.setHour_17(value);
		}
		if(hour == 18){
			b.setHour_18(value);
		}
		if(hour == 19){
			b.setHour_19(value);
		}
		if(hour == 20){
			b.setHour_20(value);
		}
		if(hour == 21){
			b.setHour_21(value);
		}
		if(hour == 22){
			b.setHour_22(value);
		}
		if(hour == 23){
			b.setHour_23(value);
		}
		if(hour == 24){
			b.setHour_24(value);
		}
		// We consider the hour 25 like the total
		if(hour == 25){
			b.setTotal(value);
		}
	}
	private BigDecimal getHour(int hour, NominationDetailBean b){
		if(hour == 1){
			return (b!=null && b.getHour_01()!=null ? b.getHour_01() : new BigDecimal(0));
		}
		if(hour == 2){
			return (b!=null && b.getHour_02()!=null ? b.getHour_02() : new BigDecimal(0));
		}
		if(hour == 3){
			return (b!=null && b.getHour_03()!=null ? b.getHour_03() : new BigDecimal(0));
		}
		if(hour == 4){
			return (b!=null && b.getHour_04()!=null ? b.getHour_04() : new BigDecimal(0));
		}
		if(hour == 5){
			return (b!=null && b.getHour_05()!=null ? b.getHour_05() : new BigDecimal(0));
		}
		if(hour == 6){
			return (b!=null && b.getHour_06()!=null ? b.getHour_06() : new BigDecimal(0));
		}
		if(hour == 7){
			return (b!=null && b.getHour_07()!=null ? b.getHour_07() : new BigDecimal(0));
		}
		if(hour == 8){
			return (b!=null && b.getHour_08()!=null ? b.getHour_08() : new BigDecimal(0));
		}
		if(hour == 9){
			return (b!=null && b.getHour_09()!=null ? b.getHour_09() : new BigDecimal(0));
		}
		if(hour == 10){
			return (b!=null && b.getHour_10()!=null ? b.getHour_10() : new BigDecimal(0));
		}
		if(hour == 11){
			return (b!=null && b.getHour_11()!=null ? b.getHour_11() : new BigDecimal(0));
		}
		if(hour == 12){
			return (b!=null && b.getHour_12()!=null ? b.getHour_12() : new BigDecimal(0));
		}
		if(hour == 13){
			return (b!=null && b.getHour_13()!=null ? b.getHour_13() : new BigDecimal(0));
		}
		if(hour == 14){
			return (b!=null && b.getHour_14()!=null ? b.getHour_14() : new BigDecimal(0));
		}
		if(hour == 15){
			return (b!=null && b.getHour_15()!=null ? b.getHour_15() : new BigDecimal(0));
		}
		if(hour == 16){
			return (b!=null && b.getHour_16()!=null ? b.getHour_16() : new BigDecimal(0));
		}
		if(hour == 17){
			return (b!=null && b.getHour_17()!=null ? b.getHour_17() : new BigDecimal(0));
		}
		if(hour == 18){
			return (b!=null && b.getHour_18()!=null ? b.getHour_18() : new BigDecimal(0));
		}
		if(hour == 19){
			return (b!=null && b.getHour_19()!=null ? b.getHour_19() : new BigDecimal(0));
		}
		if(hour == 20){
			return (b!=null && b.getHour_20()!=null ? b.getHour_20() : new BigDecimal(0));
		}
		if(hour == 21){
			return (b!=null && b.getHour_21()!=null ? b.getHour_21() : new BigDecimal(0));
		}
		if(hour == 22){
			return (b!=null && b.getHour_22()!=null ? b.getHour_22() : new BigDecimal(0));
		}
		if(hour == 23){
			return (b!=null && b.getHour_23()!=null ? b.getHour_23() : new BigDecimal(0));
		}
		if(hour == 24){
			return (b!=null && b.getHour_24()!=null ? b.getHour_24() : new BigDecimal(0));
		}
		// We consider the hour 25 like the total
		if(hour == 25){
			return (b!=null && b.getTotal()!=null ? b.getTotal() : new BigDecimal(0));
		}
		return new BigDecimal(0);
	}

	@Override
	public ByteArrayOutputStream generateXlsx(NominationHeaderBean selected, List<NominationDetailBean> east_tab,
			List<NominationDetailBean> west_tab, List<NominationDetailBean> mix_tab,
			List<NominationDetailBean> east_tab_park, List<NominationDetailBean> west_tab_park,
			List<NominationDetailBean> mix_tab_park, List<NominationQualityGasDayBean> east_quality,
			List<NominationQualityGasDayBean> west_quality, List<NominationQualityGasDayBean> mix_quality, 
			List<QualityTableBean> quality, BigDecimal _systemId){
		
		OperationTemplateBean bean = new OperationTemplateBean();
		bean.setIdn_operation_category(this.selectOperationCategory());
		bean.setIdn_operation_term(this.selectOperationTerm());
		bean.setFile_type(Constants.INTERNAL_GENERATION);
		bean.setSystemId(_systemId);
		
		XSSFWorkbook workbook = null;
		ByteArrayInputStream ba = null;
		// Para generar una nueva version de la nominacion se utiliza una plantilla (INTERNAL_GENERATION 
		// en la tabla tpa_toperation_template).
		// En principio, en nominacion diaria para offshore no hay registro para esa plantilla en la tabla 
		// tpa_toperation_template. En ese caso, aqui no se devolveria registro y en la vista se mostrara
		// un error al usuario. Pero esto no debiera ocurrir porque no deben existir nominaciones diarias
		// para offshore, que haya que modificar (generar una nueva version).
		// La unica posibilidad es que se hubieran permitido nominaciones diarias para offshore durante
		// un tiempo y luego se borrara el registro para la plantilla de nom.diaria offshore. Y 
		// se quisiera modificar alguna de esas nominaciones que se hubieran permitido.
		List<OperationTemplateBean> l = intMapper.selectTemplateFile(bean);
		if(l.size()==0){
			return null;
		} else {
			OperationTemplateBean template = l.get(0);
			ba = new ByteArrayInputStream(template.getBinary_data());
			try {
				workbook = new XSSFWorkbook(ba);
			} catch (IOException e) {
				e.printStackTrace();
				log.catching(e);
				return null;
			}
		}
		XSSFSheet nom_sheet = workbook.getSheetAt(0);
		
		XSSFRow row = this.getRow(nom_sheet,1);
		this.getCell(nom_sheet,row,0).setCellValue(selected.getUser_group_id());
		this.getCell(nom_sheet,row,1).setCellValue(selected.getContract_code());		
		this.getCell(nom_sheet,row,2).setCellValue(selected.getStart_date());
		
		int row_cont=3;
		
		row_cont = generateRowNom(row_cont, east_tab, nom_sheet);
		row_cont = generateRowNom(row_cont, east_tab_park, nom_sheet);
		row_cont = generateRowQuality(row_cont, east_quality, nom_sheet);
		row_cont = generateRowNom(row_cont, west_tab, nom_sheet);
		row_cont = generateRowNom(row_cont, west_tab_park, nom_sheet);
		row_cont = generateRowQuality(row_cont, west_quality, nom_sheet);
		row_cont = generateRowNom(row_cont, mix_tab, nom_sheet);
		row_cont = generateRowNom(row_cont, mix_tab_park, nom_sheet);
		row_cont = generateRowQuality(row_cont, mix_quality, nom_sheet);
		// No existen nominaciones diarias para sistema offshore, asi que no se llegara a ejecutar este codigo en sistema offshore. 
		// No tiene sentido obtenerlas en onshore.
		//row_cont = generateRowNom(row_cont, o_east_tab, nom_sheet);
		//row_cont = generateRowNom(row_cont, o_east_tab_park, nom_sheet);
		//row_cont = generateRowQuality(row_cont, o_east_quality, nom_sheet);
		
		// we write the indicator of end of file
		row = this.getRow(nom_sheet,row_cont);
		this.getCell(nom_sheet,row, 0).setCellValue("*");
		
		XSSFSheet qu_sheet = workbook.getSheetAt(1);
		row_cont=1;
		row_cont = generateSheetQuality(row_cont, quality, qu_sheet);
		row = this.getRow(qu_sheet,row_cont);
		this.getCell(qu_sheet,row, 0).setCellValue("*");

		ByteArrayOutputStream output = new ByteArrayOutputStream() ;
		try {
			workbook.write(output);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.catching(e1);
		}
		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.catching(e);
			return null;
		}
		return output;
	}
	
	private int generateRowNom(int row_cont, List<NominationDetailBean> area_tab, XSSFSheet nom_sheet){
		XSSFRow row = null;
		for(int i=0;i<area_tab.size();i++){
			NominationDetailBean b = area_tab.get(i);
			row = this.getRow(nom_sheet,row_cont);

			this.getCell(nom_sheet,row,0).setCellValue(b.getZone_code());
			this.getCell(nom_sheet,row,1).setCellValue(b.getSupply_demand());
			this.getCell(nom_sheet,row,2).setCellValue(b.getArea());
			this.getCell(nom_sheet,row,3).setCellValue(b.getPoint_id());
			this.getCell(nom_sheet,row,4).setCellValue(b.getWi_hv());
			this.getCell(nom_sheet,row,5).setCellValue(b.getArea_concept());
			this.getCell(nom_sheet,row,6).setCellValue(b.getCust_type());
			this.getCell(nom_sheet,row,7).setCellValue(b.getArea_code());
			this.getCell(nom_sheet,row,8).setCellValue(b.getSubarea());
			this.getCell(nom_sheet,row,9).setCellValue(b.getUnit());
			this.getCell(nom_sheet,row,10).setCellValue(b.getEntry_exit());
			if(b.getWi()!=null){
				this.getCell(nom_sheet,row,11).setCellValue(b.getWi().doubleValue());
			}
			if(b.getHv()!=null){
				this.getCell(nom_sheet,row,12).setCellValue(b.getHv().doubleValue());
			}
			if(b.getSg()!=null){
				this.getCell(nom_sheet,row,13).setCellValue(b.getSg().doubleValue());
			}
			if(b.getHour_01()!=null){
				this.getCell(nom_sheet,row,14).setCellValue(b.getHour_01().doubleValue());
			}
			if(b.getHour_02()!=null){
				this.getCell(nom_sheet,row,15).setCellValue(b.getHour_02().doubleValue());
			}
			if(b.getHour_03()!=null){
				this.getCell(nom_sheet,row,16).setCellValue(b.getHour_03().doubleValue());
			}
			if(b.getHour_04()!=null){
				this.getCell(nom_sheet,row,17).setCellValue(b.getHour_04().doubleValue());
			}
			if(b.getHour_05()!=null){
				this.getCell(nom_sheet,row,18).setCellValue(b.getHour_05().doubleValue());
			}
			if(b.getHour_06()!=null){
				this.getCell(nom_sheet,row,19).setCellValue(b.getHour_06().doubleValue());
			}
			if(b.getHour_07()!=null){
				this.getCell(nom_sheet,row,20).setCellValue(b.getHour_07().doubleValue());
			}
			if(b.getHour_08()!=null){
				this.getCell(nom_sheet,row,21).setCellValue(b.getHour_08().doubleValue());
			}
			if(b.getHour_09()!=null){
				this.getCell(nom_sheet,row,22).setCellValue(b.getHour_09().doubleValue());
			}
			if(b.getHour_10()!=null){
				this.getCell(nom_sheet,row,23).setCellValue(b.getHour_10().doubleValue());
			}
			if(b.getHour_11()!=null){
				this.getCell(nom_sheet,row,24).setCellValue(b.getHour_11().doubleValue());
			}
			if(b.getHour_12()!=null){
				this.getCell(nom_sheet,row,25).setCellValue(b.getHour_12().doubleValue());
			}
			if(b.getHour_13()!=null){
				this.getCell(nom_sheet,row,26).setCellValue(b.getHour_13().doubleValue());
			}
			if(b.getHour_14()!=null){
				this.getCell(nom_sheet,row,27).setCellValue(b.getHour_14().doubleValue());
			}
			if(b.getHour_15()!=null){
				this.getCell(nom_sheet,row,28).setCellValue(b.getHour_15().doubleValue());
			}
			if(b.getHour_16()!=null){
				this.getCell(nom_sheet,row,29).setCellValue(b.getHour_16().doubleValue());
			}
			if(b.getHour_17()!=null){
				this.getCell(nom_sheet,row,30).setCellValue(b.getHour_17().doubleValue());
			}
			if(b.getHour_18()!=null){
				this.getCell(nom_sheet,row,31).setCellValue(b.getHour_18().doubleValue());
			}
			if(b.getHour_19()!=null){
				this.getCell(nom_sheet,row,32).setCellValue(b.getHour_19().doubleValue());
			}
			if(b.getHour_20()!=null){
				this.getCell(nom_sheet,row,33).setCellValue(b.getHour_20().doubleValue());
			}
			if(b.getHour_21()!=null){
				this.getCell(nom_sheet,row,34).setCellValue(b.getHour_21().doubleValue());
			}
			if(b.getHour_22()!=null){
				this.getCell(nom_sheet,row,35).setCellValue(b.getHour_22().doubleValue());
			}
			if(b.getHour_23()!=null){
				this.getCell(nom_sheet,row,36).setCellValue(b.getHour_23().doubleValue());
			}
			if(b.getHour_24()!=null){
				this.getCell(nom_sheet,row,37).setCellValue(b.getHour_24().doubleValue());
			}
			if(b.getTotal()!=null){
				this.getCell(nom_sheet,row,38).setCellValue(b.getTotal().doubleValue());
			}
			
			row_cont++;
		}
		return row_cont;
	}

	private int generateRowQuality(int row_cont, List<NominationQualityGasDayBean> area_tab, XSSFSheet nom_sheet){
		XSSFRow row = null;
		for(int i=0;i<area_tab.size();i++){
			NominationQualityGasDayBean b = area_tab.get(i);
			row = this.getRow(nom_sheet,row_cont);
			this.getCell(nom_sheet,row,0).setCellValue(b.getZone_code());
			this.getCell(nom_sheet,row,1).setCellValue(b.getSupply_demand());
			this.getCell(nom_sheet,row,2).setCellValue(b.getArea());
			this.getCell(nom_sheet,row,3).setCellValue(b.getPoint_id());
			this.getCell(nom_sheet,row,4).setCellValue(b.getConcept());
			this.getCell(nom_sheet,row,5).setCellValue(b.getArea_concept());
			this.getCell(nom_sheet,row,6).setCellValue(b.getCust_type());
			this.getCell(nom_sheet,row,7).setCellValue(b.getArea_code());
			this.getCell(nom_sheet,row,8).setCellValue(b.getSubarea());
			this.getCell(nom_sheet,row,9).setCellValue(b.getUnit());
			this.getCell(nom_sheet,row,10).setCellValue(b.getEntry_exit());
			if(b.getWi()!=null){
				this.getCell(nom_sheet,row,11).setCellValue(b.getWi().doubleValue());
			}
			if(b.getHv()!=null){
				this.getCell(nom_sheet,row,12).setCellValue(b.getHv().doubleValue());
			}
			if(b.getSg()!=null){
				this.getCell(nom_sheet,row,13).setCellValue(b.getSg().doubleValue());
			}
			if(b.getHour_01()!=null){
				this.getCell(nom_sheet,row,14).setCellValue(b.getHour_01().doubleValue());
			}
			if(b.getHour_02()!=null){
				this.getCell(nom_sheet,row,15).setCellValue(b.getHour_02().doubleValue());
			}
			if(b.getHour_03()!=null){
				this.getCell(nom_sheet,row,16).setCellValue(b.getHour_03().doubleValue());
			}
			if(b.getHour_04()!=null){
				this.getCell(nom_sheet,row,17).setCellValue(b.getHour_04().doubleValue());
			}
			if(b.getHour_05()!=null){
				this.getCell(nom_sheet,row,18).setCellValue(b.getHour_05().doubleValue());
			}
			if(b.getHour_06()!=null){
				this.getCell(nom_sheet,row,19).setCellValue(b.getHour_06().doubleValue());
			}
			if(b.getHour_07()!=null){
				this.getCell(nom_sheet,row,20).setCellValue(b.getHour_07().doubleValue());
			}
			if(b.getHour_08()!=null){
				this.getCell(nom_sheet,row,21).setCellValue(b.getHour_08().doubleValue());
			}
			if(b.getHour_09()!=null){
				this.getCell(nom_sheet,row,22).setCellValue(b.getHour_09().doubleValue());
			}
			if(b.getHour_10()!=null){
				this.getCell(nom_sheet,row,23).setCellValue(b.getHour_10().doubleValue());
			}
			if(b.getHour_11()!=null){
				this.getCell(nom_sheet,row,24).setCellValue(b.getHour_11().doubleValue());
			}
			if(b.getHour_12()!=null){
				this.getCell(nom_sheet,row,25).setCellValue(b.getHour_12().doubleValue());
			}
			if(b.getHour_13()!=null){
				this.getCell(nom_sheet,row,26).setCellValue(b.getHour_13().doubleValue());
			}
			if(b.getHour_14()!=null){
				this.getCell(nom_sheet,row,27).setCellValue(b.getHour_14().doubleValue());
			}
			if(b.getHour_15()!=null){
				this.getCell(nom_sheet,row,28).setCellValue(b.getHour_15().doubleValue());
			}
			if(b.getHour_16()!=null){
				this.getCell(nom_sheet,row,29).setCellValue(b.getHour_16().doubleValue());
			}
			if(b.getHour_17()!=null){
				this.getCell(nom_sheet,row,30).setCellValue(b.getHour_17().doubleValue());
			}
			if(b.getHour_18()!=null){
				this.getCell(nom_sheet,row,31).setCellValue(b.getHour_18().doubleValue());
			}
			if(b.getHour_19()!=null){
				this.getCell(nom_sheet,row,32).setCellValue(b.getHour_19().doubleValue());
			}
			if(b.getHour_20()!=null){
				this.getCell(nom_sheet,row,33).setCellValue(b.getHour_20().doubleValue());
			}
			if(b.getHour_21()!=null){
				this.getCell(nom_sheet,row,34).setCellValue(b.getHour_21().doubleValue());
			}
			if(b.getHour_22()!=null){
				this.getCell(nom_sheet,row,35).setCellValue(b.getHour_22().doubleValue());
			}
			if(b.getHour_23()!=null){
				this.getCell(nom_sheet,row,36).setCellValue(b.getHour_23().doubleValue());
			}
			if(b.getHour_24()!=null){
				this.getCell(nom_sheet,row,37).setCellValue(b.getHour_24().doubleValue());
			}
			if(b.getTotal()!=null){
				this.getCell(nom_sheet,row,38).setCellValue(b.getTotal().doubleValue());
			}
			
			row_cont++;
		}
		return row_cont;
	}
	
	
	private int generateSheetQuality(int row_cont, List<QualityTableBean> quality, XSSFSheet qu_sheet){
		XSSFRow row = null;
		for(int i=0;i<quality.size();i++){
			QualityTableBean b = quality.get(i);
			row = this.getRow(qu_sheet,row_cont);
			this.getCellQuality(qu_sheet,row,0).setCellValue(b.getZone());
			this.getCellQuality(qu_sheet,row,1).setCellValue(b.getPoint());
			if(b.getCo2()!=null){
				this.getCellQuality(qu_sheet,row,2).setCellValue(b.getCo2().doubleValue());
			}
			if(b.getC1()!=null){
				this.getCellQuality(qu_sheet,row,3).setCellValue(b.getC1().doubleValue());
			}
			if(b.getC2()!=null){
				this.getCellQuality(qu_sheet,row,4).setCellValue(b.getC2().doubleValue());
			}
			if(b.getC3()!=null){
				this.getCellQuality(qu_sheet,row,5).setCellValue(b.getC3().doubleValue());
			}
			if(b.getIc4()!=null){
				this.getCellQuality(qu_sheet,row,6).setCellValue(b.getIc4().doubleValue());
			}
			if(b.getNc4()!=null){
				this.getCellQuality(qu_sheet,row,7).setCellValue(b.getNc4().doubleValue());
			}
			if(b.getIc5()!=null){
				this.getCellQuality(qu_sheet,row,8).setCellValue(b.getIc5().doubleValue());
			}
			if(b.getNc5()!=null){
				this.getCellQuality(qu_sheet,row,9).setCellValue(b.getNc5().doubleValue());
			}
			if(b.getC6()!=null){
				this.getCellQuality(qu_sheet,row,10).setCellValue(b.getC6().doubleValue());
			}
			if(b.getC7()!=null){
				this.getCellQuality(qu_sheet,row,11).setCellValue(b.getC7().doubleValue());
			}
			if(b.getC8()!=null){
				this.getCellQuality(qu_sheet,row,12).setCellValue(b.getC8().doubleValue());
			}
			if(b.getN2()!=null){
				this.getCellQuality(qu_sheet,row,13).setCellValue(b.getN2().doubleValue());
			}
			if(b.getO2()!=null){
				this.getCellQuality(qu_sheet,row,14).setCellValue(b.getO2().doubleValue());
			}
			if(b.getH2s()!=null){
				this.getCellQuality(qu_sheet,row,15).setCellValue(b.getH2s().doubleValue());
			}
			if(b.getS()!=null){
				this.getCellQuality(qu_sheet,row,16).setCellValue(b.getS().doubleValue());
			}
			if(b.getHg()!=null){
				this.getCellQuality(qu_sheet,row,17).setCellValue(b.getHg().doubleValue());
			}
			row_cont++;
		}
		return row_cont;
	}

	private XSSFCell getCell(XSSFSheet sheet,XSSFRow row,int pos){
		if(row.getCell(pos)==null){
			XSSFCell cell = row.createCell(pos);
			XSSFRow old_row = sheet.getRow(3);
			if(old_row.getCell(pos)!=null){
				cell.setCellStyle(old_row.getCell(pos).getCellStyle());
			}
			return cell;
		} else {
			return row.getCell(pos);
		}
	}
	private XSSFCell getCellQuality(XSSFSheet sheet,XSSFRow row,int pos){
		if(row.getCell(pos)==null){
			XSSFCell cell = row.createCell(pos);
			XSSFRow old_row = sheet.getRow(1);
			if(old_row.getCell(pos)!=null){
				cell.setCellStyle(old_row.getCell(pos).getCellStyle());
			}
			return cell;
		} else {
			return row.getCell(pos);
		}
	}
	private XSSFRow getRow(XSSFSheet sheet, int row_cont){
		if(sheet.getRow(row_cont)==null){
			return sheet.createRow(row_cont);
		} else {
			return sheet.getRow(row_cont);
		}
		
	}

	@Override
	public String saveFile(byte[] file, 
								BigDecimal idn_active, 
								String action, 
								Date start_date, 
								NominationHeaderBean selectedNomHeader) throws Exception {	

		if(xmlConverter==null){
			xmlConverter = new Xlsx2XmlConverter();
			xmlConverter.setxMapper(xMapper);
			xmlConverter.init("NOMINATION", "DAILY", idn_active);
    	}       	
		boolean xml_generated = true;
		String xml_data = null;
		try {
			xml_data = xmlConverter.getXMLFromExcel(file);
		} catch (Exception e){
			xml_generated = false;
			log.catching(e);
		}
		
		OperationFileBean bean = new OperationFileBean();
		bean.setFile_name("DailyNominationIntermediate.xlsx");
		bean.setBinary_data(file);
		bean.setXml_data(xml_data);
		Calendar cal = Calendar.getInstance();
		bean.setVersion_date(cal.getTime());
		bean.setIdn_operation_category(this.selectOperationCategory());
		bean.setIdn_operation_term(this.selectOperationTerm());
		
		TreeMap<String,BigDecimal> paramMap = new TreeMap<String,BigDecimal>();
		paramMap.put("idn_operation_category", bean.getIdn_operation_category());
		paramMap.put("idn_operation_term", bean.getIdn_operation_term());
		
		// the operation "NOMINATION" + "DAILY" does not exists
		BigDecimal bd_operation = this.selectOperation(paramMap);
		if(bd_operation==null){
			// Un error tecnico que se guardara en log. No es necesario guardar en messages.properties, para presentar al usuario.
    		throw new Exception("The operation NOMINATION + DAILY does not exist.");
		}
		
		// insertion of tOperation_file
		int ret3 = dailyNomMapper.insertOperationFile(bean);
		if(ret3!=1){
			throw new Exception("Failed when trying to insert the file in operation file.");
		}
		
		if(!xml_generated){
			throw new Exception("Failed in the generation of the XML file.");
		}
		
		ProcessManagementBean manag = new ProcessManagementBean();
		manag.setTerm_code(Constants.DAILY);
		manag.setIdn_operation_file(bean.getIdn_operation_file());
		manag.setAction(action);
		manag.setIdn_pipeline_system(idn_active);
		manag.setStart_date(start_date);
		manag.setShipper_comments(selectedNomHeader.getShipper_comments());
		manag.setComments(selectedNomHeader.getOperator_comments());
		manag.setUser_id((String)SecurityUtils.getSubject().getPrincipal());
		manag.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());

		try {
			nomMapper.getProcesssManagement(manag);
		}
		catch( Exception e )
		{
	    	log.error(e.getMessage(), e);
	    	// Si hay un error en la llamada al procedimiento, se toma como error tecnico.
			throw new Exception("Error in getProcesssManagement database procedure."); 
		}
	
		// El procedimiento va a devolver 0 en caso de OK o warning. En caso de warning, se devuelve
		// un string distinto de null.
		// En caso de error funcional, el procedimiento devuelve un codigo de error mayor o igual a 1000 y 
		// se devuelve una ValidationException (funcional). Esta excepcion se pintara en la ventana de mensajes al usuario.
		// En caso de error tecnico, el procedimiento devuelve un codigo de error menor que 1000 y distinto de cero.
		// se devuelve una Exception normal (error tecnico). En la ventana de mensajes al usuario se muestra un 
		// "error interno" y los detalles se llevan al log.
		int res = manag.getInteger_exit().intValue();
		if( res != 0) {
			if( res >= 1000 )	// Errores funcionales.
	    		throw new ValidationException(manag.getError_desc());
			else				// Errores tecnicos.
	    		throw new Exception(manag.getError_desc());
		}

		// En caso de OK, en el parametro error_desc se va a devolver el codigo y version de nominacion.
		// Ademas, puede ser que se devuelvan warnings.
		String strOutMsg = manag.getError_desc();
		String resWarnings = manag.getWarnings();
		if( resWarnings != null )
			strOutMsg += System.getProperty("line.separator") + 
						resWarnings.replace(";", System.getProperty("line.separator"));
	
		if(Constants.REJECT.equalsIgnoreCase(action)) {
			try {
				sendNotificationToShipper(selectedNomHeader, idn_active);
			}
			catch( Exception e )
			{
		    	// Si falla la notificacion al shipper no se va a dar por error la operacion.
	    		log.error(e.getMessage(), e);
	    	}
		}
		
		return strOutMsg;
	}


	private void sendNotificationToShipper(NominationHeaderBean selectedNomHeader, BigDecimal _systemId) 
			throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	// Si falla la notificacion al shipper no se va a dar por error la operacion. Solo se hace log.
    	int res = 0;
    	
		// Se genera la notificacion para el shipper, correspondiente a que el operador ha procesado su peticion de contrato.
		NotificationBean notif = new NotificationBean();
		notif.setType_code(strNotifTypeCode);
		notif.setSystemId(_systemId);
		notif.setOrigin(strNotifOrigin);
		if(selectedNomHeader != null) {
			// Para un mensaje como "Weekly nomination {1} has been rejected by operator"
			// se pasa como parametro el codigo de la nominacion, unicamente.
			notif.setInformation(selectedNomHeader.getNomination_code());
			notif.setIdn_user_group(selectedNomHeader.getIdn_user_group());
		}
		notif.setUser_id((String)SecurityUtils.getSubject().getPrincipal());
		notif.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		
		notifMapper.getCreateNotification(notif);
		if( notif==null || notif.getInteger_exit()==null ){
			log.error(msgs.getString("error_sending_notification_to_shipper"));
		}
		else {
	    	// Si falla la notificacion al shipper no se va a dar por error la operacion. Solo se hace log.
			res = notif.getInteger_exit().intValue();
			if( res != 0) {
				log.error(msgs.getString("error_sending_notification_to_shipper"));				
			}
		}
	}


/* 09/09/16 CH0036 PPM: Por peticion de cambios en FAT, se elimina boton "Open Renomination" en pantalla dailyNomination.xhtml.
 * 	
	@Override
	public ErrorBean openRenomination() {
		ProcessManagementBean manag = new ProcessManagementBean();
		manag.setUser_id((String)SecurityUtils.getSubject().getPrincipal());
		manag.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		
		nomMapper.getOpenRenominacion(manag);
		if(manag.getInteger_exit()!=0){
			return new ErrorBean(manag.getInteger_exit(),manag.getError_desc());
		} else {
			return new ErrorBean(manag.getInteger_exit(),manag.getError_desc());
		}

	}
*/
}
