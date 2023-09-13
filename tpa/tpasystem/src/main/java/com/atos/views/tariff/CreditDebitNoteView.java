package com.atos.views.tariff;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.xml.soap.Detail;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import com.atos.beans.MessageBean;
import com.atos.beans.tariff.CreditDebitNoteBean;
import com.atos.beans.tariff.CreditDebitNoteDetailBean;
import com.atos.filters.tariff.CreditDebitNoteDetailFilter;
import com.atos.filters.tariff.CreditDebitNoteFilter;
import com.atos.filters.tariff.CreditDebitNoteIdnOperTermFilter;
import com.atos.services.tariff.CreditDebitNoteService;
import com.atos.utils.Constants;
import com.atos.utils.POIXSSFExcelUtils;
import com.atos.views.CommonView;

@ManagedBean(name = "creditDebitNoteView")
@ViewScoped
public class CreditDebitNoteView extends CommonView implements Serializable {

	private static final long serialVersionUID = 3798530949273594690L;

	private static final Logger log = LogManager.getLogger(CreditDebitNoteView.class);

	private CreditDebitNoteFilter filters;
	private CreditDebitNoteDetailFilter filtersDetail;
	private List<CreditDebitNoteBean> items;
	private List<CreditDebitNoteDetailBean> itemsDetail;
	private CreditDebitNoteBean selected;
	private CreditDebitNoteBean newNote;
	private CreditDebitNoteBean bean;
	private CreditDebitNoteDetailBean newDetailNote;
	
	private static final String excelTotalLabel ="Total";
	
	
	@ManagedProperty("#{creditDebitNoteService}")
	transient private CreditDebitNoteService service;
	
	public CreditDebitNoteFilter getFilters() {
		return filters;
	}

	public void setFilters(CreditDebitNoteFilter filters) {
		this.filters = filters;
	}		

	public CreditDebitNoteDetailFilter getFiltersDetail() {
		return filtersDetail;
	}

	public void setFiltersDetail(CreditDebitNoteDetailFilter filtersDetail) {
		this.filtersDetail = filtersDetail;
	}

	public List<CreditDebitNoteBean> getItems() {
		return items;
	}

	public void setItems(List<CreditDebitNoteBean> items) {
		this.items = items;
	}

	public void setService(CreditDebitNoteService service) {
		this.service = service;
	}

	public CreditDebitNoteBean getNewNote() {
		return newNote;
	}

	public void setNewNote(CreditDebitNoteBean newNote) {
		this.newNote = newNote;
	}

	public CreditDebitNoteBean getSelected() {
		return selected;
	}

	public List<CreditDebitNoteDetailBean> getItemsDetail() {
		return itemsDetail;
	}

	public void setItemsDetail(List<CreditDebitNoteDetailBean> itemsDetail) {
		this.itemsDetail = itemsDetail;
	}

	public void setSelected(CreditDebitNoteBean selected) {
		this.selected = selected;
	}

	public CreditDebitNoteDetailBean getNewDetailNote() {
		return newDetailNote;
	}

	public void setNewDetailNote(CreditDebitNoteDetailBean newDetailNote) {
		this.newDetailNote = newDetailNote;
	}

	@PostConstruct
	public void init() {
		
		filters = new CreditDebitNoteFilter();
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		if (getIsShipper()) {
			filters.setIdn_user_group(getUser().getIdn_user_group());
		}
		filters.setIsShipper(isShipper());
		filtersDetail = new CreditDebitNoteDetailFilter();
		selected = new CreditDebitNoteBean();
		newNote = new CreditDebitNoteBean();
		newDetailNote = new CreditDebitNoteDetailBean();
	}
	
	public boolean getIsShipper() {
		return getUser().isUser_type(Constants.SHIPPER);
	}

	public Map<BigDecimal, Object> getTypes() {
		return service.selectTypes();
	}
	
	public Map<BigDecimal, Object> getShippers() {
		return service.selectShippers();
	}
	
	public Map<BigDecimal, Object> getCndnIds() {
		return service.selectCnDnIds(filters);
	}
	
	public Map<BigDecimal, Object> getCharges() {
		return service.selectCharges();
	}
	
	public Map<BigDecimal, Object> getChargeDetails() {
		return service.selectChargeDetails(newDetailNote.getCharge());
	}
	
	public Map<BigDecimal, Object> getContracts() {
		return service.selectContracts(filtersDetail);
	}
	
	public boolean disabledContract() {
		
		if(newDetailNote.getCharge()!=null) {
			BigDecimal bd = newDetailNote.getCharge();
			BigDecimal bd2 = newDetailNote.getChargeDetail();
			
			CreditDebitNoteIdnOperTermFilter f = new CreditDebitNoteIdnOperTermFilter();
			f.setCharge(bd);
			f.setIdn_tariff_code_detail(bd2);		
			
			BigDecimal idnOT = service.selectOperationTerm(f);
			if(idnOT==null)
				return true;
			return false;
		}		
		return false;
	}
	
	public void postProcessXLS(Object document) {
		
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		XSSFWorkbook wb = (XSSFWorkbook) document;
		XSSFSheet sheet = wb.getSheetAt(0);
		wb.setSheetName(0, "Credit Debit Notes");
		XSSFRow header = sheet.getRow(0);
		NumberFormat nf = NumberFormat.getInstance(new Locale(getLanguage().getLocale()));

		// Generate fonts
		XSSFFont headerFont = createFont(wb, Color.BLACK, (short) 12, true);
		XSSFFont contentFont = createFont(wb, Color.BLACK, (short) 10, false);
		// Generate styles
		DataFormat format = wb.createDataFormat();
		
		byte[] rgb = new byte[3];
        rgb[0] = (byte) 153; // red
        rgb[1] = (byte) 230; // green
        rgb[2] = (byte) 230; // blue
        XSSFColor totalColor = new XSSFColor(rgb); // #f2dcdb
        Color tColor = new Color(153,230,230);
        
		XSSFCellStyle cellStyleTotalRow = wb.createCellStyle();
        cellStyleTotalRow.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyleTotalRow.setFillForegroundColor(totalColor);
        cellStyleTotalRow.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

		XSSFCellStyle cellStyleHeader = createStyle(wb, headerFont, XSSFCellStyle.ALIGN_CENTER, Color.cyan.darker(),
				true, Color.BLACK, XSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		XSSFCellStyle cellStyleText = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_LEFT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##"));
		XSSFCellStyle cellStyleFourDecTotal = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT, tColor, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.0000"));
		XSSFCellStyle cellStyleTwoDec = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.00"));
		

		int i = -1; // Filas
		int j = -1; // Columnas
		
		ArrayList<String> cabeceras = new ArrayList<>();
		
		sheet.setColumnHidden(0, true);
		sheet.setColumnHidden(7, true);
		
		try {
			// Cabecera i=0
			for (j = 0; j < header.getPhysicalNumberOfCells(); j++) {
				XSSFCell cell = header.getCell(j);
				cell.setCellStyle(cellStyleHeader);				
			}
			
			int numRows = sheet.getPhysicalNumberOfRows();
			for (i = 1; i < numRows; i++) {				
				XSSFRow row = sheet.getRow(i);
				/*int[] idx = { 0, 0 };*/
				for (j = 0; j < row.getPhysicalNumberOfCells(); j++) {
					XSSFCell cell = row.getCell(j);
					cell.setCellStyle(cellStyleText);	
				}
			
			}
			// Se supone que todas las filas tienen el mismo numero de columnas.
			// Se actualiza el final el tamaño de las columnas porque consumiria
			// mucho tiempo si se hiciera fila por fila.
			for (j = 0; j < sheet.getRow(0).getPhysicalNumberOfCells(); j++) {
				sheet.autoSizeColumn(j, true);
			}
			
			cabeceras.add(0, msgs.getString("creditDebitNote_Shipper"));
			cabeceras.add(1, msgs.getString("creditDebitNote_Month_Year"));
			cabeceras.add(2, msgs.getString("creditDebitNote_CNDN"));
			cabeceras.add(3, msgs.getString("creditDebitNote_Type_Note"));
			cabeceras.add(4, msgs.getString("creditDebitNote_Charges"));
			cabeceras.add(5, msgs.getString("creditDebitNote_Charge_Detail"));
			cabeceras.add(6, msgs.getString("creditDebitNote_Original_Period"));
			cabeceras.add(7, msgs.getString("creditDebitNote_Contract_Num"));
			cabeceras.add(8, msgs.getString("creditDebitNote_Contract_Type"));				
			cabeceras.add(9, msgs.getString("creditDebitNote_Quantity"));
			cabeceras.add(10, msgs.getString("creditDebitNote_Unit"));
			cabeceras.add(11, msgs.getString("creditDebitNote_Fee"));
			cabeceras.add(12, msgs.getString("creditDebitNote_Amount"));
			cabeceras.add(13, msgs.getString("creditDebitNote_Remark"));
			
			//Para cada cabecera, se pinta el detalle (y los campos de referencia de la cabecera 
			for(i=0; i<numRows-1; i++) {
				int r = i;	
				
				DateFormat dateFormat = new SimpleDateFormat("MM-yyyy");  
				String strDate = dateFormat.format(items.get(i).getMonthYear());  
				String sheet_text = i + "-" + items.get(i).getShipper() + " " + strDate + " " + items.get(i).getCndnId();
				
				XSSFSheet sheet1 = wb.createSheet(sheet_text);
				// CABECERA
				XSSFRow rowHead = sheet1.createRow(0);
				int[] idx = { 0, 0 };
				cabeceras.stream().forEachOrdered(item -> {
	
					Cell cab = rowHead.createCell(idx[0]++);
					cab.setCellStyle(cellStyleHeader);
					cab.setCellValue(item);
	
				});
				idx[0] = 0;
				idx[1] = 1;
				SimpleDateFormat sdf = new SimpleDateFormat("MMMM/YYYY");
				items.get(i).getDetails().stream().forEachOrdered(det -> {
					XSSFRow row2 = sheet1.createRow(idx[1]++);
					Cell c2 = row2.createCell(idx[0]++);
					c2.setCellValue(items.get(r).getShipper());
					c2.setCellStyle(cellStyleText);
					c2 = row2.createCell(idx[0]++);
					c2.setCellValue(sdf.format(items.get(r).getMonthYear()));
					c2.setCellStyle(cellStyleText);
					c2 = row2.createCell(idx[0]++);
					c2.setCellValue(items.get(r).getCndnId());
					c2.setCellStyle(cellStyleText);
					c2 = row2.createCell(idx[0]++);
					c2.setCellValue(items.get(r).getType());
					c2.setCellStyle(cellStyleText);
					c2 = row2.createCell(idx[0]++);
					c2.setCellValue(det.getChargeDesc());
					c2.setCellStyle(cellStyleText);					
					c2 = row2.createCell(idx[0]++);
					c2.setCellValue(det.getChargeDetailDesc());
					c2.setCellStyle(cellStyleText);
					c2 = row2.createCell(idx[0]++);
					c2.setCellValue(sdf.format(det.getOriginalPeriod()));
					c2.setCellStyle(cellStyleText);
					c2 = row2.createCell(idx[0]++);
					c2.setCellValue(det.getContractNum());
					c2.setCellStyle(cellStyleText);
					c2 = row2.createCell(idx[0]++);
					c2.setCellValue(det.getContractType());
					c2.setCellStyle(cellStyleText);					
					c2 = row2.createCell(idx[0]++);
					c2.setCellValue(det.getQuantity().doubleValue());
					c2.setCellStyle(cellStyleFourDecTotal);
					c2 = row2.createCell(idx[0]++);
					c2.setCellValue(det.getUnit());
					c2.setCellStyle(cellStyleText);
					c2 = row2.createCell(idx[0]++);
					c2.setCellValue(det.getFee().doubleValue());
					c2.setCellStyle(cellStyleFourDecTotal);
					c2 = row2.createCell(idx[0]++);
					c2.setCellValue(det.getAmount().doubleValue());
					c2.setCellStyle(cellStyleFourDecTotal);
					c2 = row2.createCell(idx[0]++);
					c2.setCellValue(det.getRemark());
					c2.setCellStyle(cellStyleText);
					idx[0]=0;
	
				});
				for (j = 0; j < sheet1.getRow(0).getPhysicalNumberOfCells(); j++) {
					sheet1.autoSizeColumn(j, true);
				}
			}
			
			
		} catch (Exception e) {

			ResourceBundle msgs2 = FacesContext.getCurrentInstance().getApplication()
					.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
			getMessages().addMessage(Constants.head_menu[8],
					new MessageBean(Constants.ERROR, msgs2.getString("internal_error"),
							"error exportig data", Calendar.getInstance().getTime()));
			log.error("error exporting data: " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private XSSFFont createFont(XSSFWorkbook wb, Color fontColor, short fontHeight, boolean fontBold) {

		XSSFFont font = wb.createFont();
		font.setBold(fontBold);
		font.setColor(new XSSFColor(fontColor));
		font.setFontName("Calibri");
		font.setFontHeightInPoints(fontHeight);

		return font;
	}

	private XSSFCellStyle createStyle(XSSFWorkbook wb, XSSFFont font, short cellAlign, Color cellColor,
			boolean cellBorder, Color cellBorderColor, short border, boolean numberFormat, short dataFormat) {

		XSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(cellAlign);
		style.setFillForegroundColor(new XSSFColor(cellColor));
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

		if (cellBorder) {
			style.setBorderTop(border);
			style.setBorderLeft(border);
			style.setBorderRight(border);
			style.setBorderBottom(border);

			XSSFColor xsCellBorderColor = new XSSFColor(cellBorderColor);
			style.setTopBorderColor(xsCellBorderColor);
			style.setLeftBorderColor(xsCellBorderColor);
			style.setRightBorderColor(xsCellBorderColor);
			style.setBottomBorderColor(xsCellBorderColor);
		}

		if (numberFormat) {
			style.setDataFormat(dataFormat);
		}

		return style;
	}	
	
	// Methods
	public void onSearch() {
		
		// En cada busqueda se resetea la fila seleccionada.
        selected = new CreditDebitNoteBean();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		try {
			items = service.selectCreditDebitNotes(filters);
			} catch (Exception e) {
				String strError = msgs.getString("creditDebitNote_ErrorWhileWSarchingResults");
				e.printStackTrace();
				
				getMessages().addMessage(Constants.head_menu[10], new MessageBean(Constants.ERROR, "Internal Error",
						"Error while searching results", Calendar.getInstance().getTime()));
			}
	}

	public void onClear() {
		// RequestContext.getCurrentInstance().reset("form");
		filters = new CreditDebitNoteFilter();
		filters.setIdn_system(getChangeSystemView().getIdn_active());

		if (items != null) {
			items.clear();
		}

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_creditDebitNote1').clearFilters()");
		selected = new CreditDebitNoteBean();
	}

	
	public void onRowEdit(RowEditEvent event) {
		
		CreditDebitNoteBean item = (CreditDebitNoteBean) event.getObject();
		item.setIdn_system(getChangeSystemView().getIdn_active());
		item.setUsername(getUser().getUsername());	
		if(item.getSent().equals("true"))
			item.setSent("Y");
		else
			item.setSent("N");
		int error = 0;
		String message;		
		
		try {
			error = service.insertCreditDebitNote(item);
			if(error != 1)
				getMessages().addMessage(Constants.head_menu[10], new MessageBean(Constants.ERROR, "Internal Error",
						"Error while inserting data", Calendar.getInstance().getTime()));
			else {
				for(int i = 0; i< item.getDetails().size(); i++) {
					item.getDetails().get(i).setNoteId(item.getNoteId());
					error = service.insertCreditDebitNoteDetails(item.getDetails().get(i));
				}
			}
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			message = e.getMessage();
		}
		onSearch();
	}

	public void onRowCancel(RowEditEvent event) {
		
	}

	public void onRowEditDetail(RowEditEvent event) {
		CreditDebitNoteDetailBean item = (CreditDebitNoteDetailBean) event.getObject();		
		item.setUsername(getUser().getUsername());	
		item.setUpdated(true);
		
		CreditDebitNoteBean bean=null;
		BigDecimal id = item.getNoteId();
		for(int i=0; i<items.size(); i++)
			if(items.get(i).getNoteId().compareTo(id)==0) {
				bean = items.get(i);//service.selectBean(id);
				break;
			}
		if(bean!=null) {
			int error = 0;
			String message;
			bean.setIdn_system(getChangeSystemView().getIdn_active());
			try {
					error = service.insertCreditDebitNote(bean);					
					if(error != 1)
						getMessages().addMessage(Constants.head_menu[10], new MessageBean(Constants.ERROR, "Internal Error",
								"Error while inserting data", Calendar.getInstance().getTime()));
					else {
						BigDecimal idNew = bean.getNoteId();
						for(int i = 0; i< bean.getDetails().size(); i++) {
							if(bean.getDetails().get(i).isUpdated()) {
								item.setNoteId(idNew);	
								error = service.insertCreditDebitNoteDetails(item);
							}
							else {
								bean.getDetails().get(i).setNoteId(idNew);
								error = service.insertCreditDebitNoteDetails(bean.getDetails().get(i));
							}
						}
					}	
			} catch (Exception e) {
				log.catching(e);
				// we assign the return message
				message = e.getMessage();
			}
		}
	}

	public void onRowCancelDetail(RowEditEvent event) {
		
	}

	public BigDecimal getTotalAmount(CreditDebitNoteBean item) {
		BigDecimal total = new BigDecimal(0);
		for (int i = 0; i < item.getDetails().size(); i++) {
			if (item.getDetails().get(i).getAmount()!=null) {
				total = total.add(item.getDetails().get(i).getAmount());
			}
		}
		return total;
	}
	
	public void createVersion() {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		selected = bean;
		selected.getDetails().clear();
		bean.getDetails().clear();
		
		String strError = msgs.getString("creditDebitNote_saved");
		getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.INFO, msgs.getString("creditDebitNote_saved"), strError, Calendar.getInstance().getTime()));
	}

	public void save() {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		String strError="";
		bean = null;
		//service.insertNote(newNote);		
		if(!noteExits()) {
			if(items==null) {
				this.onSearch();
				//this.
			}
			boolean encontrado = false;
			for(int i=0;i<items.size();i++) {				
				bean = items.get(i);
				if(bean.getCndnId().equals(newNote.getCndnId()) &&  bean.getMonthYear().equals(newNote.getMonthYear()) 
						&& bean.getShipperId().equals(newNote.getShipperId())) {
					bean.setComments(newNote.getComments());
					if(newNote.isChecked())
						bean.setSent("Y");
					else
						bean.setSent("N");
					bean.setUpdated(true);
					bean.setIdn_system(getChangeSystemView().getIdn_active());
					encontrado = true;
					
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('w_confirmVersionDlg').show()");
					
					break;
				}
				
			}
			if(!encontrado) {
				newNote.setIdn_system(getChangeSystemView().getIdn_active());
				newNote.setNoteId(new BigDecimal(-1));
				if(newNote.isChecked())
					newNote.setSent("Y");
				else
					newNote.setSent("N");
				newNote.setUsername(getUser().getUsername());
				newNote.setNewBean(true);
				newNote.setShipper(service.getShipper(newNote.getShipperId()));
				newNote.setType(service.getType(newNote.getTypeId()));
				items.add(newNote);
				
				newNote=new CreditDebitNoteBean();
				strError = msgs.getString("creditDebitNote_saved");
				getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.INFO, msgs.getString("creditDebitNote_saved"), strError, Calendar.getInstance().getTime()));
			}
			
		}
		else {
			strError = msgs.getString("creditDebitNote_exists");
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR, msgs.getString("creditDebitNote_exists"), strError, Calendar.getInstance().getTime()));
		}
			
	}
	
	public void saveDetail() {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		if(!newDetailNote.getOriginalPeriod().before(selected.getMonthYear())) {
			String strError = msgs.getString("creditDebitNote_dateDetailError");
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.ERROR, msgs.getString("creditDebitNote_dateDetailError"), strError, Calendar.getInstance().getTime()));
		}			
		else {
			newDetailNote.setNoteId(selected.getNoteId());
			newDetailNote.setUsername(getUser().getUsername());
			newDetailNote.setChargeDesc(service.getChargeDesc(newDetailNote.getCharge()));
			newDetailNote.setContractNum(service.getContractDesc(newDetailNote.getContractNumId()));
			newDetailNote.setChargeDetailDesc(service.getChargeDetailDesc(newDetailNote.getChargeDetail()));
			if(newDetailNote.getContractNumId()==null) {
				newDetailNote.setContractNum("All");
				newDetailNote.setContractType("All");
			}
			else
				newDetailNote.setContractType(service.selectTypeContract(newDetailNote.getContractNumId()));
			if(selected.getDetails()==null) {
				List<CreditDebitNoteDetailBean> items = new ArrayList<CreditDebitNoteDetailBean>();
				selected.setDetails(items);
			}
			selected.getDetails().add(newDetailNote);
			newDetailNote = new CreditDebitNoteDetailBean();
			String strError = msgs.getString("creditDebitNote_detail_saved");
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.INFO, msgs.getString("creditDebitNote_detail_saved"), strError, Calendar.getInstance().getTime()));
		}
	}
	
	public boolean getChangesPending() {
		/*if(items!=null && items.size()>0) {
			for(int i=0; i<items.size(); i++)
				if(items.get(i).isUpdated() || items.get(i).isNewBean())
					return true;
			return false;
		}*/
		if(selected!=null && selected.getNoteId()!=null)
			return true;
		return false;
	}
	
	public void cancel() {
		
	}
	
	public boolean noteExits() {
		return service.noteExists(newNote);
	}
	
	public void saveBlock() {
		/*for(int i=0; i<items.size(); i++)
			if(items.get(i).isUpdated()) {
				CreditDebitNoteBean item = items.get(i);				
				int error = 0;
				String message;		
				
				try {
					error = service.insertCreditDebitNote(item);
				} catch(Exception e) {}
			}*/
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		selected.setIdn_system(getChangeSystemView().getIdn_active());
		int error = service.insertCreditDebitNote(selected);
		if(error != 1)
			getMessages().addMessage(Constants.head_menu[10], new MessageBean(Constants.ERROR, "Internal Error",
					"Error while inserting data", Calendar.getInstance().getTime()));
		else {
			BigDecimal idNew = selected.getNoteId();
			for(int i = 0; i< selected.getDetails().size(); i++) {
				selected.getDetails().get(i).setNoteId(idNew);	
				error = service.insertCreditDebitNoteDetails(selected.getDetails().get(i));
			}
			String str = msgs.getString("creditDebitNote_saved_bd");
			getMessages().addMessage(Constants.head_menu[10],new MessageBean(Constants.INFO, msgs.getString("creditDebitNote_saved_bd"), str, Calendar.getInstance().getTime()));

		}
		selected = new CreditDebitNoteBean();
		onSearch();
	}
	
	public void selectDetailsChange() {
		this.getChargeDetails();
	}
	
	public void setSelectedParent(CreditDebitNoteBean bean) {
		setSelected(bean);
	}
	
	public void onSelectContractChangeDetails() {
		
		newDetailNote.setOriginalPeriod(null);
		newDetailNote.setContractNumId(null);
		filtersDetail.setOriginalPeriod(null);
	}
	
	public void onSelectContractChange(SelectEvent event) {
		Date date = (Date)event.getObject();
		newDetailNote.setOriginalPeriod(date);
		
		
		BigDecimal bd = newDetailNote.getCharge();
		BigDecimal bd2 = newDetailNote.getChargeDetail();
		
		CreditDebitNoteIdnOperTermFilter f = new CreditDebitNoteIdnOperTermFilter();
		f.setCharge(bd);
		f.setIdn_tariff_code_detail(bd2);		
		
		BigDecimal idnOT = service.selectOperationTerm(f);
		
		try {		
			filtersDetail.setIdnOperationTerm(idnOT);
			filtersDetail.setShipper(selected.getShipperId());
			filtersDetail.setOriginalPeriod(newDetailNote.getOriginalPeriod());		
			filtersDetail.setIdn_system(getChangeSystemView().getIdn_active());
		} catch(Exception e) {}
			
		
		this.getContracts();
	}
 
	public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
	
	public boolean renderItemEditor(CreditDebitNoteBean item){
   		if(item.getSent().equals("Y"))
   			return false;
   		return true;
    }
	
	public String shipperName (BigDecimal idn_user_group) {
		return service.getShipper(idn_user_group);
	}
}