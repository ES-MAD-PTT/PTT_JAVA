package com.atos.services.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.ContractNomPointBean;
import com.atos.filters.dam.ContractNomPointFilter;
import com.atos.mapper.dam.ContractNomPointMapper;

@Service("contractNomPointService")
public class ContractNomPointServiceImpl implements ContractNomPointService {

	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private ContractNomPointMapper contractNomPointMapper;

	public List<ContractNomPointBean> selectContractNomPoints(ContractNomPointFilter filter) {
		return contractNomPointMapper.selectContractNomPoints(filter);

	}

	@Override
	public Map<BigDecimal, Object> selectShippers() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractNomPointMapper.selectShippers();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectContractIds(ContractNomPointFilter filters){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractNomPointMapper.selectContractIds(filters);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectContractForm(ContractNomPointBean newContractNomPoint){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractNomPointMapper.selectContractForm(newContractNomPoint);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectContractPoints(ContractNomPointFilter filters) {

		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractNomPointMapper.selectContractPoints(filters);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectNominationPoints(ContractNomPointFilter filters) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractNomPointMapper.selectNominationPoints(filters);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertContractNomPoint(ContractNomPointBean contractNomPoint) throws Exception {

		List<BigDecimal> list = contractNomPointMapper.getContractNomPointCode(contractNomPoint);
		if (list.size() > 0) {
			// the id is already inserted
			throw new Exception("-1");
		}
		int ins = contractNomPointMapper.insertContractNomPoint(contractNomPoint);
		if (ins != 1) {
			throw new Exception("-2");
		}
		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String updateContractNomPoint(ContractNomPointBean contractNomPoint) throws Exception {
		int upd = contractNomPointMapper.updateContractNomPoint(contractNomPoint);
		if (upd != 1) {
			throw new Exception("-1");
		}

		return "0";
	}

	@Override
	public Map<BigDecimal, Object> selectContractNomPointsForm(ContractNomPointBean newContractNomPoint) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractNomPointMapper.selectContractNomPointsForm(newContractNomPoint);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}


	@Override
	public Map<BigDecimal, Object> selectNominationPointsForm(ContractNomPointBean newContractNomPoint) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractNomPointMapper.selectNominationPointsForm(newContractNomPoint);		
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Transactional( rollbackFor = { Throwable.class })
	public String deleteContractNomPoint(ContractNomPointBean bean) throws Exception {
		 
		int ret1 = contractNomPointMapper.deleteContractNomPoint(bean);
		if(ret1!=1){
			throw new Exception("-1");
		}
		
		
		return "0";
	}

	@Override
	public List<ContractNomPointBean> selectContractNomPointsFormTable(ContractNomPointBean contractNomPoint) {
		return  contractNomPointMapper.selectContractNomPointsFormTable(contractNomPoint);
	}

	@Override
	public List<ContractNomPointBean> selectContractNomPointsNullFormTable(ContractNomPointBean contractNomPoint) {
		return  contractNomPointMapper.selectContractNomPointsNullFormTable(contractNomPoint);
	}

	@Override
	public List<ContractNomPointBean> selectContractNomPointsFormEdit(ContractNomPointBean contractNomPoint) {
		return  contractNomPointMapper.selectContractNomPointsFormEdit(contractNomPoint);
	}

	@Override
	public ContractNomPointBean selectIdShipper(ContractNomPointBean contractNomPoint) {		
		return contractNomPointMapper.selectIdShipper(contractNomPoint);
	}

	@Override
	public BigDecimal selectExistingNumSlop(ContractNomPointBean contractNomPoint) {
		BigDecimal existingNumSlop = contractNomPointMapper.selectExistingNumSlop(contractNomPoint);
		return existingNumSlop;
	}

	@Override
	public Date selectDateContra(ContractNomPointBean contractNomPoint) {
		Date dateContrat = contractNomPointMapper.selectDateContra(contractNomPoint);
		return dateContrat;
	}

	@Override
	public ContractNomPointBean selectContraCodeById(ContractNomPointBean contractNomPoint) {
		return contractNomPointMapper.selectContraCodeById(contractNomPoint);
	}

	@Override
	public ContractNomPointBean selectCodeNomPointById(ContractNomPointBean contractNomPoint) {
		return contractNomPointMapper.selectCodeNomPointById(contractNomPoint);
	}

	@Override
	public BigDecimal getIdnSystemPoint(ContractNomPointBean contractNomPoint) {
		return contractNomPointMapper.getIdnSystemPoint(contractNomPoint);
	}

//	@Override
//	public void downloadExcel(XSSFWorkbook wb, List<ContractNomPointBean> items) throws IOException {
//		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
//				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
//		
//		// Generate fonts
//		Font contentFontBold = POIXSSFExcelUtils.createFont(wb, IndexedColors.BLACK.index, (short)11, true);
//		Font contentFont = POIXSSFExcelUtils.createFont(wb, IndexedColors.BLACK.index, (short)11, false);
//		// Generate styles
//		DataFormat format = wb.createDataFormat();
//		CellStyle cellStyleContentFontBold = POIXSSFExcelUtils.createStyle(wb, contentFontBold, HorizontalAlignment.CENTER, null, IndexedColors.WHITE.index, 
//				false, IndexedColors.GREY_80_PERCENT.index, BorderStyle.NONE, false, format.getFormat("#,##"));
//		CellStyle cellStyleTableGreyCenter = POIXSSFExcelUtils.createStyle(wb, contentFontBold, HorizontalAlignment.CENTER, null, IndexedColors.GREY_25_PERCENT.index, 
//				true, IndexedColors.GREY_80_PERCENT.index, BorderStyle.THIN, false, wb.getCreationHelper().createDataFormat().getFormat("dd-MMM-yyyy"));
//		CellStyle cellStyleTableBoldFormatTexto = POIXSSFExcelUtils.createStyle2(wb, contentFontBold, HorizontalAlignment.LEFT, IndexedColors.WHITE.index, 
//				true, IndexedColors.GREY_80_PERCENT.index, BorderStyle.THIN, true, format.getFormat("#,##0.00"));
//		CellStyle cellStyleTableWithoutFormatNumberAndCenter = POIXSSFExcelUtils.createStyle2(wb, contentFont, HorizontalAlignment.CENTER, IndexedColors.WHITE.index, 
//				true, IndexedColors.GREY_80_PERCENT.index, BorderStyle.THIN, true, format.getFormat("#,##0.00"));
//		CellStyle cellStyleTableBoldFormatNumber = POIXSSFExcelUtils.createStyle(wb, contentFontBold, HorizontalAlignment.RIGHT, null, IndexedColors.WHITE.index, 
//				true, IndexedColors.GREY_80_PERCENT.index, BorderStyle.THIN, true, format.getFormat("#,##0.000"));
//		CellStyle cellStyleTableFormatNumber = POIXSSFExcelUtils.createStyle(wb, contentFont, HorizontalAlignment.RIGHT, null, IndexedColors.WHITE.index, 
//				true, IndexedColors.GREY_80_PERCENT.index, BorderStyle.THIN, true, format.getFormat("#,##0.000"));
//		
//		XSSFSheet sheet = wb.createSheet("Contract Nom. Point Relation");
//		if(wb!=null) {
//			//Tabla1
//			List<String> headerTableTitle = Arrays.asList(msgs.getString("contractNomPoint_id_shipper"), msgs.getString("contractNomPoint_id_contract"), 
//					msgs.getString("contractNomPoint_date_from_contract"),msgs.getString("contractNomPoint_date_to_contract"),msgs.getString("contractNomPoint_date_from_active"),
//					msgs.getString("contractNomPoint_date_to_active"));
//			
//			XSSFRow row = sheet.createRow(0);
//			XSSFCell cell = row.createCell(0);
//			
//			int cellNum = 0;
//			for(int i = 0; i < headerTableTitle.size(); i++) {//Insertamos cabecera
//			cell = row.createCell(cellNum);
//			cell.setCellStyle(cellStyleTableGreyCenter);
//			cell.setCellValue(headerTableTitle.get(i));				
//			cellNum++;
//			}
//		}
//		
//	}
	

}
