package com.atos.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.activation.MimetypesFileTypeMap;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class POIXSSFExcelUtils implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5949845195364290251L;

	/**
	 * @param newSheet the sheet to create from the copy.
	 * @param sheet the sheet to copy.
	 */
	public void copySheets(XSSFSheet srcSheet, XSSFSheet destSheet){   
		copySheets(srcSheet, destSheet, true);   
	}   

	/**
	 * @param newSheet the sheet to create from the copy.
	 * @param sheet the sheet to copy.
	 * @param copyStyle true copy the style.
	 */
	public void copySheets(XSSFSheet srcSheet, XSSFSheet destSheet, boolean copyStyle){   
		int maxColumnNum = 0;   
		Map<Integer, XSSFCellStyle> styleMap = (copyStyle) ? new HashMap<Integer, XSSFCellStyle>() : null;
		// manage a list of merged zone in order to not insert two times a merged zone
		Set<CellRangeAddressWrapper> mergedRegions = new TreeSet<CellRangeAddressWrapper>();
		
		for (int i = srcSheet.getFirstRowNum(); i <= srcSheet.getLastRowNum(); i++) {   
			XSSFRow srcRow = srcSheet.getRow(i);   
			XSSFRow destRow = destSheet.createRow(i);   
			if (srcRow != null) {   
				copyRow(srcSheet, destSheet, srcRow, destRow, styleMap, mergedRegions);   
				if (srcRow.getLastCellNum() > maxColumnNum) {   
					maxColumnNum = srcRow.getLastCellNum();   
				}   
			}   
		}   
		for (int i = 0; i <= maxColumnNum; i++) {   
			destSheet.setColumnWidth(i, srcSheet.getColumnWidth(i));   
		}   
	}   

	/**
	 * @param srcSheet the sheet to copy.
	 * @param destSheet the sheet to create.
	 * @param srcRow the row to copy.
	 * @param destRow the row to create.
	 * @param styleMap -
	 */
	public void copyRow(XSSFSheet srcSheet, XSSFSheet destSheet, XSSFRow srcRow, XSSFRow destRow, 
						Map<Integer, XSSFCellStyle> styleMap, Set<CellRangeAddressWrapper> mergedRegions) {   
 		destRow.setHeight(srcRow.getHeight());   
		// pour chaque row
		for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {   
			XSSFCell oldCell = srcRow.getCell(j);   // ancienne cell
			XSSFCell newCell = destRow.getCell(j);  // new cell 
			if (oldCell != null) {   
				if (newCell == null) {   
					newCell = destRow.createCell(j);   
				}   
				// copy chaque cell
				copyCell(oldCell, newCell, styleMap);   
				// copy les informations de fusion entre les cellules
				//System.out.println("row num: " + srcRow.getRowNum() + " , col: " + (short)oldCell.getColumnIndex());
				CellRangeAddress mergedRegion = getMergedRegion(srcSheet, srcRow.getRowNum(), (short)oldCell.getColumnIndex());   

				if (mergedRegion != null) { 
					//System.out.println("Selected merged region: " + mergedRegion.toString());
					CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow(), mergedRegion.getLastRow(), mergedRegion.getFirstColumn(),  mergedRegion.getLastColumn());
					//System.out.println("New merged region: " + newMergedRegion.toString());
					CellRangeAddressWrapper wrapper = new CellRangeAddressWrapper(newMergedRegion);
					if (isNewMergedRegion(wrapper, mergedRegions)) {
						mergedRegions.add(wrapper);
						destSheet.addMergedRegion(wrapper.range);   
					}   
				}   
			}   
		}   

	}   

	/**
	 * @param oldCell
	 * @param newCell
	 * @param styleMap
	 */
	public static void copyCell(XSSFCell oldCell, XSSFCell newCell, Map<Integer, XSSFCellStyle> styleMap) {   
		if(styleMap != null) {   
			if(oldCell.getSheet().getWorkbook() == newCell.getSheet().getWorkbook()){   
				newCell.setCellStyle(oldCell.getCellStyle());   
			} else{   
				int stHashCode = oldCell.getCellStyle().hashCode();   
				XSSFCellStyle newCellStyle = styleMap.get(stHashCode);   
				if(newCellStyle == null){   
					newCellStyle = newCell.getSheet().getWorkbook().createCellStyle();   
					newCellStyle.cloneStyleFrom(oldCell.getCellStyle());   
					styleMap.put(stHashCode, newCellStyle);   
				}   
				newCell.setCellStyle(newCellStyle);   
			}   
		}   
		switch(oldCell.getCellType()) {   
		case XSSFCell.CELL_TYPE_STRING:   
			newCell.setCellValue(oldCell.getStringCellValue());   
			break;   
		case XSSFCell.CELL_TYPE_NUMERIC:   
			newCell.setCellValue(oldCell.getNumericCellValue());   
			break;   
		case XSSFCell.CELL_TYPE_BLANK:   
			newCell.setCellType(XSSFCell.CELL_TYPE_BLANK);   
			break;   
		case XSSFCell.CELL_TYPE_BOOLEAN:   
			newCell.setCellValue(oldCell.getBooleanCellValue());   
			break;   
		case XSSFCell.CELL_TYPE_ERROR:   
			newCell.setCellErrorValue(oldCell.getErrorCellValue());   
			break;   
		case XSSFCell.CELL_TYPE_FORMULA:   
			newCell.setCellFormula(oldCell.getCellFormula());   
			break;   
		default:   
			break;   
		}   

	}   

	/**
	 * @param sheet the sheet containing the data.
	 * @param rowNum the num of the row to copy.
	 * @param cellNum the num of the cell to copy.
	 * @return the CellRangeAddress created.
	 */
	private static CellRangeAddress getMergedRegion(XSSFSheet sheet, int rowNum, short cellNum) {   
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) { 
			CellRangeAddress merged = sheet.getMergedRegion(i);   
			if (merged.isInRange(rowNum, cellNum)) {   
				return merged;   
			}   
		}   
		return null;   
	}   

	/**
	 * Check that the merged region has been created in the destination sheet.
	 * @param newMergedRegion the merged region to copy or not in the destination sheet.
	 * @param mergedRegions the list containing all the merged region.
	 * @return true if the merged region is already in the list or not.
	 */
	private static boolean isNewMergedRegion(CellRangeAddressWrapper newMergedRegion, Set<CellRangeAddressWrapper> mergedRegions) {
		return !mergedRegions.contains(newMergedRegion);   
	}   

	private class CellRangeAddressWrapper implements Comparable<CellRangeAddressWrapper> {

		public CellRangeAddress range;

		/**
		 * @param theRange the CellRangeAddress object to wrap.
		 */
		public CellRangeAddressWrapper(CellRangeAddress theRange) {
			this.range = theRange;
		}

		/**
		 * @param o the object to compare.
		 * @return -1 the current instance is prior to the object in parameter, 0: equal, 1: after...
		 */
		public int compareTo(CellRangeAddressWrapper o) {

			if (range.getFirstColumn() == o.range.getFirstColumn()
					&& range.getFirstRow() == o.range.getFirstRow()) {
				return 0;
			} else if (range.getFirstColumn() <= o.range.getFirstColumn()
						&& range.getFirstRow() <= o.range.getFirstRow()) {
					return -1;
			} else {
				return 1;
			}
		}

	}

	
	//****************************************************
	/**
	 * Create a style on base workbook
	 * 
	 * @param font            Font used by the style
	 * @param cellAlign       Cell alignment for contained text (see {@link HSSFCellStyle})
	 * @param cellColor       Cell background color (see {@link HSSFColor})
	 * @param cellBorder      Cell has border (<code>true</code>) or not (<code>false</code>)
	 * @param cellBorderColor Cell border color (see {@link HSSFColor})
	 * 
	 * @return New cell style
	 */
	
	//private static HSSFWorkbook wb;
	// Fonts
	/*private HSSFFont headerFont;
	private HSSFFont contentFont;
	private HSSFFont totalFont;
	// Styles
	private HSSFCellStyle cellStyleHeader;
	private HSSFCellStyle cellStyleHide;
	
	private HSSFCellStyle cellStyleTwoeDec; 
	private HSSFCellStyle cellStyleThreeDec; 
	private HSSFCellStyle cellStyleFourDec;
	private HSSFCellStyle cellStylZeroDec;
	
	private HSSFCellStyle cellStyleText;
	private HSSFCellStyle cellStyleTotal;
	private HSSFCellStyle cellStyleTotalTwoeDec; 
	private HSSFCellStyle cellStyleTotalThreeDec; 
	
	*/
	
	/**
	 * Create a new font on base workbook
	 * 
	 * @param fontColor       Font color (see {@link HSSFColor})
	 * @param fontHeight      Font height in points
	 * @param fontBold        Font is boldweight (<code>true</code>) or not (<code>false</code>)
	 * 
	 * @return New cell style
	 */
	
	
	public static HSSFFont createFont(HSSFWorkbook wb,short fontColor, short fontHeight, boolean fontBold) {
 
		HSSFFont font = wb.createFont();
		font.setBold(fontBold);
		font.setColor(fontColor);
		font.setFontName("Calibri");
		font.setFontHeightInPoints(fontHeight);
 
		return font;
	}
	
			
	
	public static HSSFCellStyle createStyleHeader(HSSFWorkbook wb){
		HSSFFont headerFont= POIXSSFExcelUtils.createFont(wb, HSSFColor.BLACK.index, (short)12, true);
		DataFormat format = wb.createDataFormat();
		return createStyle(wb,headerFont,  HSSFCellStyle.ALIGN_CENTER, HSSFColor.GREY_40_PERCENT.index, true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
	}
	
	public static HSSFCellStyle createStyleHide(HSSFWorkbook wb){
		HSSFFont contentFont = POIXSSFExcelUtils.createFont(wb,HSSFColor.BLACK.index, (short)10, false); 
		DataFormat format = wb.createDataFormat();
		return createStyle(wb,contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index,        true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_HAIR, false, format.getFormat("#,##"));
	}
	
	
	public static HSSFCellStyle createStyleText(HSSFWorkbook wb){
		HSSFFont contentFont = POIXSSFExcelUtils.createFont(wb,HSSFColor.BLACK.index, (short)10, false); 
		DataFormat format = wb.createDataFormat();
		return createStyle(wb,contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##"));
	}
	
	public static HSSFCellStyle createStyleTotal(HSSFWorkbook wb){
		HSSFFont totalFont = POIXSSFExcelUtils.createFont(wb,HSSFColor.BLACK.index, (short)12, true);
		DataFormat format = wb.createDataFormat();
		return createStyle(wb,totalFont,  HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
	}
	public static HSSFCellStyle createStyleTotalTwoDec(HSSFWorkbook wb){
		HSSFFont totalFont = POIXSSFExcelUtils.createFont(wb,HSSFColor.BLACK.index, (short)12, true);
		DataFormat format = wb.createDataFormat();
		return createStyle(wb,totalFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,        true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, true, format.getFormat("#,##0.00"));
	}
	
	public static HSSFCellStyle createStyleZeroDec(HSSFWorkbook wb){
		HSSFFont contentFont = POIXSSFExcelUtils.createFont(wb,HSSFColor.BLACK.index, (short)10, false);
		DataFormat format = wb.createDataFormat();
		return createStyle(wb,contentFont, HSSFCellStyle.ALIGN_RIGHT,   HSSFColor.WHITE.index,       true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##"));
	}
	public static HSSFCellStyle createStyleTwoDec(HSSFWorkbook wb){
		HSSFFont contentFont = POIXSSFExcelUtils.createFont(wb,HSSFColor.BLACK.index, (short)10, false);
		DataFormat format = wb.createDataFormat();
		return createStyle(wb,contentFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.00"));
	}
	public static HSSFCellStyle createStyleThreeDec(HSSFWorkbook wb){
		HSSFFont contentFont = POIXSSFExcelUtils.createFont(wb,HSSFColor.BLACK.index, (short)10, false);
		DataFormat format = wb.createDataFormat();
		return createStyle(wb,contentFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.000"));
	}
	public static HSSFCellStyle createStyleFourDec(HSSFWorkbook wb){
		HSSFFont contentFont = POIXSSFExcelUtils.createFont(wb,HSSFColor.BLACK.index, (short)10, false);
		DataFormat format = wb.createDataFormat();
		return createStyle(wb,contentFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.0000"));
	}
	
	
	

	
	public static HSSFCellStyle createStyle(HSSFWorkbook wb ,HSSFFont font, short cellAlign, short cellColor, boolean cellBorder, short cellBorderColor, short border, 
			boolean numberFormat,short dataFormat) {
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(cellAlign);
		style.setFillForegroundColor(cellColor);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
 
		if (cellBorder) {
			style.setBorderTop(border);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setBorderBottom(border);
 
			style.setTopBorderColor(cellBorderColor);
			style.setLeftBorderColor(cellBorderColor);
			style.setRightBorderColor(cellBorderColor);
			style.setBottomBorderColor(cellBorderColor);
		}
		
		 if(numberFormat){
			 style.setDataFormat(dataFormat);
		 }
	  
		
		
		return style;
	}
	
	public static void downloadFile(File file) {
		int DEFAULT_BUFFER_SIZE = 10240;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
 
		// Prepare.
		try {
			ExternalContext externalContext = facesContext.getExternalContext();
			HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
 
			// Open file.
			input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
			// Init servlet response.
			response.reset();
			response.setContentType(new MimetypesFileTypeMap().getContentType(file));
			response.setContentLength((int) file.length());
			response.setHeader("Content-disposition", "attachment; filename=\"" + file.getName() + "\"");
 
			try {
				output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
 
				// Write file contents to response.
				byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
				int length;
				while ((length = input.read(buffer)) > 0) {
					output.write(buffer, 0, length);
				}
 
				// Finalize task.
				output.flush();
 
				// Inform JSF that it doesn't need to handle response.
				// This is very important, otherwise you will get the following
				// exception in the logs:
				// java.lang.IllegalStateException: Cannot forward after
				// response has
				// been committed.
				facesContext.responseComplete();
 
			} catch (IOException err) {
				err.printStackTrace();
			}
 
		} catch (Exception re) {
			// return UiConstants.FAILURE;
		} finally {
			// Gently close streams.
			close(output);
			close(input);
		}
	}
 
	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				// Do your thing with the exception. Print it, log it or mail
				// it. It may be useful to
				// know that this will generally only be thrown when the client
				// aborted the download.
				e.printStackTrace();
			}
		}
	}
	
	public static Font createFont(XSSFWorkbook wb, short fontColor, short fontHeight, boolean fontBold) {
		Font font = wb.createFont();
		font.setBold(fontBold);
		font.setColor(fontColor);
		font.setFontName("Calibri");
		font.setFontHeightInPoints(fontHeight);
 
		return font;
	}
	
	public static CellStyle createStyle(XSSFWorkbook wb, Font font, short cellAlign, short cellColor, boolean cellBorder, short cellBorderColor, short border, 
			boolean numberFormat,short dataFormat) {
		
		CellStyle style = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(cellAlign);
		style.setFillForegroundColor(cellColor);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		if (cellBorder) {
			style.setBorderTop(border);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setBorderBottom(border);
 
			style.setTopBorderColor(cellBorderColor);
			style.setLeftBorderColor(cellBorderColor);
			style.setRightBorderColor(cellBorderColor);
			style.setBottomBorderColor(cellBorderColor);
		}
		if(numberFormat){
			style.setDataFormat(dataFormat);
		}
		return style;
	}
	
	public static void createSimpleHeaderTable(Sheet sheet, Row row, Cell cell, int numRow, int startCell, List<String> headerTitles, CellStyle style) {
		row = sheet.createRow(numRow);
		for(int i = 0, j = 0 + startCell; j < headerTitles.size() + startCell; i++, j++) {//Insertamos cabecera
			cell = row.createCell(j);
			cell.setCellStyle(style);
			cell.setCellValue(headerTitles.get(i));	
		}
	}
	
	public static void createCellsTable(Row row, Cell cell, int startCell,CellStyle cellStyleTableFormatDate, CellStyle cellStyleTableFormatNumber, CellStyle cellNormalStyle, Object[] properties) {
		for (int i = 0, j = 0 + startCell; j < properties.length + startCell; i++, j++) {
	        cell = row.createCell(j);
	        if(properties[i] != null) {
	        	if (properties[i] instanceof Date) {
		            cell.setCellValue((Date) properties[i]);
		            cell.setCellStyle(cellStyleTableFormatDate);
		        }else if (properties[i] instanceof Number) {
		        	cell.setCellValue(((Number) properties[i]).doubleValue());
		        	cell.setCellStyle(cellStyleTableFormatNumber);
	        	}else if (properties[i] instanceof String) {
	        		cell.setCellValue((String) properties[i]);
	        	}
	        }else {
	        	cell.setCellValue("");
	        }
	    }
	}
}
