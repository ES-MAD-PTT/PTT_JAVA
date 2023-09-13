package com.atos.views.balancing;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.ErrorBean;
import com.atos.beans.FileBean;
import com.atos.beans.MessageBean;
import com.atos.beans.allocation.AccumulatedImbalanceAllocationBean;
import com.atos.beans.allocation.AllocationBean;
import com.atos.beans.balancing.InstructedOperationFlowShippersBean;
import com.atos.filters.balancing.InstructedOperationFlowShippersFilter;
import com.atos.services.MailService;
import com.atos.services.balancing.InstructedOperationFlowShippersService;
import com.atos.utils.Constants;
import com.atos.utils.DateUtil;
import com.atos.views.CommonView;

@ManagedBean(name = "insOpFlowShippersView")
@ViewScoped
public class InstructedOperationFlowShippersView extends CommonView implements Serializable {

	private static final long serialVersionUID = -8682819137997451254L;

	private static final Logger log = LogManager.getLogger("com.atos.views.balancing.InstructedOperationFlowShippers");
	private InstructedOperationFlowShippersFilter filters;
	private List<InstructedOperationFlowShippersBean> resultList;
	private InstructedOperationFlowShippersBean selectedItem;

	private UploadedFile file;
	private FileBean uploadFile = null;
	
	@ManagedProperty("#{InstructedOperationFlowShippersService}")
	transient private InstructedOperationFlowShippersService service;

	public void setService(InstructedOperationFlowShippersService service) {
		this.service = service;
	}
	
	private Calendar sysdate;
	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}
	
	@ManagedProperty("#{mailService}")
    transient private MailService mailService;

    public void setMailService(MailService mailService) {
    	this.mailService = mailService;
    }

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public InstructedOperationFlowShippersBean getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(InstructedOperationFlowShippersBean selectedItem) {
		this.selectedItem = selectedItem;
	}

	@PostConstruct
	public void init() {
		filters = new InstructedOperationFlowShippersFilter();
		filters.setSystemId(getChangeSystemView().getIdn_active());
		filters.setIsShipper(getIsShipper());
		if (getIsShipper()) {
			filters.setShipperId(getUser().getIdn_user_group());
		}
		sysdate= gettingValidDateStart();
		/*
		 * super.setHead(4); factorFromDefaultUnit =
		 * e_service.selectFactorFromDefaultUnit(EnergyUnit); Date d =
		 * DateUtil.getToday();
		 * 
		 * if (getIsShipper()) { filters.setShipperId(getUser().getIdn_user_group()); }
		 * filters.setDateFrom(d); filters.setDateTo(d);
		 * filters.setFactorFromDefaultUnit(factorFromDefaultUnit);
		 * filters.setSystemId(getChangeSystemView().getIdn_active()); onSearch();
		 */
	}
	
	//sysdate 
    public Calendar gettingValidDateStart(){
    	
    	Calendar sysdate = Calendar.getInstance(); 
    	filters.setDate(sysdate.getTime());
    	
    	sysdate.setTime(sysdate.getTime());
    	sysdate.set(Calendar.HOUR_OF_DAY, 0);
    	sysdate.set(Calendar.MINUTE, 0);
    	sysdate.set(Calendar.SECOND, 0);
    	sysdate.set(Calendar.MILLISECOND, 0);
    	
        return sysdate;
    }

	public boolean getIsShipper() {
		return getUser().isUser_type(Constants.SHIPPER);
	}


	public void postProcessXLS(Object document) {
		XSSFWorkbook wb = (XSSFWorkbook) document;
		XSSFSheet sheet = wb.getSheetAt(0);
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
		XSSFCellStyle cellStyleFourDec = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.0000"));
		XSSFCellStyle cellStyleFourDecTotal = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT, tColor, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.0000"));
		XSSFCellStyle cellStyleTwoDec = createStyle(wb, contentFont, XSSFCellStyle.ALIGN_RIGHT, Color.WHITE, true,
				Color.BLACK, XSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.00"));
		
		ArrayList<Integer> allTotalRows = new ArrayList<Integer>();
		
		/*for(int i=0;i<sheet.getPhysicalNumberOfRows();i++){
        	XSSFCell cell = sheet.getRow(i).getCell(1);
   			String tmpStrValue = cell.getStringCellValue();
   			if(tmpStrValue.startsWith(excelTotalLabel))
   				allTotalRows.add(new Integer(i));
        }*/

		String tmpStrValue = null;
		int i = -1; // Filas
		int j = -1; // Columnas

		try {
			// Cabecera i=0
			for (j = 0; j < header.getPhysicalNumberOfCells()-1; j++) {
				XSSFCell cell = header.getCell(j);
				cell.setCellStyle(cellStyleHeader);
			}

			for (i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

				XSSFRow row = sheet.getRow(i);

				for (j = 0; j < row.getPhysicalNumberOfCells(); j++) {
					XSSFCell cell = row.getCell(j);

					// Se borran los valores de la primera columna.

					if (j == 0 || j == 1 || j == 2 || j == 5 || j == 9 || j == 11 || j == 12) { // Estas
																					// columnas
																					// deben
																					// ser
																					// de
																					// texto
																					// y
																					// el
																					// resto
																					// numericas.
						if(allTotalRows.contains(i))
		        			cell.setCellStyle(cellStyleTotalRow);
		        		else
		        			cell.setCellStyle(cellStyleText);						
					} 
					// el lapiz
					else if (j == 13) {
						cell.setCellValue(" ");
					}
					else {

						if(allTotalRows.contains(i))
							cell.setCellStyle(cellStyleFourDecTotal);
						else
							cell.setCellStyle(cellStyleFourDec);
						
						if (cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {

							tmpStrValue = cell.getStringCellValue();
							if (tmpStrValue != null && (!"".equalsIgnoreCase(tmpStrValue))) {
								cell.setCellType(Cell.CELL_TYPE_NUMERIC);

								try {
									cell.setCellValue(nf.parse(tmpStrValue).doubleValue());
								} catch (Exception e) {
									log.catching(e);
								}

								// Se resetea la variable temporal.
								tmpStrValue = null;
							}
						}
					}

					// Se hace abajo, porque consume tiempo.
					// sheet.autoSizeColumn(j, true);
				}
			}

			// Se supone que todas las filas tienen el mismo numero de columnas.
			// Se actualiza el final el tamaño de las columnas porque consumiria
			// mucho tiempo si se hiciera fila por fila.
			for (j = 0; j < sheet.getRow(0).getPhysicalNumberOfCells()-1; j++) {
				sheet.autoSizeColumn(j, true);
			}
		} catch (Exception e) {

			ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
					.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
			getMessages().addMessage(Constants.head_menu[9],
					new MessageBean(Constants.ERROR, msgs.getString("internal_error"),
							"error exportig data", Calendar.getInstance().getTime()));
			log.error("error exporting data: " + e.getMessage(), e);
			e.printStackTrace();
		}
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

	private XSSFFont createFont(XSSFWorkbook wb, Color fontColor, short fontHeight, boolean fontBold) {

		XSSFFont font = wb.createFont();
		font.setBold(fontBold);
		font.setColor(new XSSFColor(fontColor));
		font.setFontName("Calibri");
		font.setFontHeightInPoints(fontHeight);

		return font;
	}

	public void onSearch() {
		 resultList = service.search(filters);
		
	}
	
	public boolean forPublicate(InstructedOperationFlowShippersBean bean) {
		return bean.getTimestamp()!=null && bean.getOpInsFlowOrderMMSCF()!=null && bean.getOpInsFlowOrderMMSCF().compareTo(BigDecimal.ZERO)!=0;
	}

	public void onPublicate() {
		
		boolean publish = false;
		if(this.resultList==null) {
			getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.WARNING, "There is not registers to publish","There is not registers to publish",Calendar.getInstance().getTime()));
			log.warn("There is not registers to publish", Calendar.getInstance().getTime());
			return;
		}
		for(int i=0; i<this.resultList.size(); i++) {
			if(this.resultList.get(i).isCheckPublicate()) {
				publish = true;
			}
		}
		if(!publish) {
			getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.WARNING, "Must select some register to publish","Must select some register to publish",Calendar.getInstance().getTime()));
			log.warn("Must select some register to publish", Calendar.getInstance().getTime());
			return;
		}
		for(int i=0; i<this.resultList.size(); i++) {
			InstructedOperationFlowShippersBean bean = this.resultList.get(i); 
			if(this.resultList.get(i).isCheckPublicate()) {
				if(forPublicate(bean)) {								
					String pattern = "dd/MM/yyyy";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
					String date = simpleDateFormat.format(bean.getTimestamp());
					DecimalFormat df = new DecimalFormat("#.0000");
					String info = date + " ~ " + bean.getZoneCode() + " ~ " + df.format(bean.getOpInsFlowOrderMMSCF()) + " ~ " + bean.getResolvedTime(); 
					BigDecimal userGroupId = resultList.get(i).getShipperId();
					try {
						service.sendNotification("BALANCING.ALARM_RECEIVED", "BALANCING", info, userGroupId, getChangeSystemView().getIdn_active());
						sendMail(bean);
					} catch (Exception e) {
						getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.ERROR, "Error sending notification","Error sending  notification",Calendar.getInstance().getTime()));
						log.info("Not informed all shippers", "Error sending Notification to" + bean.getShipperCode());
	
						// TODO Auto-generated catch block
						e.printStackTrace();					
					}				
				}
			}
		}
		getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.INFO, "Notifications have been sent correctly","Notifications sent.",Calendar.getInstance().getTime()));
		log.info("Notifications sent", Calendar.getInstance().getTime());

	}

	public void onClear() {
		init();
		if (resultList != null) {
			resultList.clear();
        }
        
        RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_insOpFlowShippers1').clearFilters()");
	}

	public Map<BigDecimal, Object> getShipperIds() {
		return service.selectShipperId();
	}

	// Para los elementos del combo del filtro de contratos.
	public Map<BigDecimal, Object> getZoneIds() {
		return service.selectZoneId(getChangeSystemView().getIdn_active());
	}
	
	public Map<BigDecimal, Object> getTimestampIds() {
    	if(filters.getStrLastVersion().equals("N"))
    		return service.selectTimestampIds(filters);
		else
			return null;
	}

	public InstructedOperationFlowShippersFilter getFilters() {
		return filters;
	}

	public void setFilters(InstructedOperationFlowShippersFilter filters) {
		this.filters = filters;
	}

	public List<InstructedOperationFlowShippersBean> getResultList() {
		return resultList;
	}

	public void setResultList(List<InstructedOperationFlowShippersBean> resultList) {
		this.resultList = resultList;
	}
	
	public void onRowEdit(RowEditEvent event) {
		
		InstructedOperationFlowShippersBean bean = (InstructedOperationFlowShippersBean)event.getObject();
		bean.setUsername(getUser().getUsername());
		String ins = "";
		if(file!=null && uploadFile!=null) {
			if(bean.getIdn_intraday_gas_flow_file()==null) {
				try {
					ins = service.insertFile(file, bean);
					if(ins.equals("0")){
						getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.INFO, "File inserted","File inserted.",Calendar.getInstance().getTime()));
						log.info("File inserted: " + bean.toString(), Calendar.getInstance().getTime());
					} else {
						getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.ERROR, "File Not inserted","Error inserting file",Calendar.getInstance().getTime()));
						log.error("File Not inserted", "Error inserting file" + bean.toString(),Calendar.getInstance().getTime());
					}
				} catch (Exception e) {
					getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.ERROR, "File Not inserted","Error inserting file",Calendar.getInstance().getTime()));
					log.error("File Not inserted", "Error inserting file" + bean.toString(),Calendar.getInstance().getTime());
				}
				
			} else {
				try {
					ins = service.updateFile(file, bean);
					if(ins.equals("0")){
						getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.INFO, "File inserted","File inserted.",Calendar.getInstance().getTime()));
						log.info("File inserted: " + bean.toString(), Calendar.getInstance().getTime());
					} else {
						getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.ERROR, "File Not inserted","Error inserting file",Calendar.getInstance().getTime()));
						log.error("File Not inserted", "Error inserting file" + bean.toString(),Calendar.getInstance().getTime());
					}
				} catch (Exception e) {
					getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.ERROR, "File Not inserted","Error inserting file",Calendar.getInstance().getTime()));
					log.error("File Not inserted", "Error inserting file" + bean.toString(),Calendar.getInstance().getTime());
				}
				
			}
		}
		
		int error=-1;
		String err ="";
		try {
			// En caso de que se quiera enviar una notificacion al usuario, se ha de indicar un systemId distinto de null.
			error = service.updateComments(bean);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
			err = e.getMessage();
		}
		if (error==1) {
			getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.INFO, "Comments Updated","Comments Updated.",Calendar.getInstance().getTime()));
			log.info("Comments Updated: " + bean.toString(), Calendar.getInstance().getTime());
		} else {
			getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.ERROR, "Comments Not Updated","Error updating  Comments",Calendar.getInstance().getTime()));
			log.info("Comments Not Updated", "Error updating  Comments " + bean.toString(),Calendar.getInstance().getTime());
		}
		
		onSearch();
		
		
	}
	
	public boolean renderItemEditor(InstructedOperationFlowShippersBean item){
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if (item.getTimestamp()!=null) {
			String dateString = df.format(item.getTimestamp());
			if(dateString.equals(df.format(sysdate.getTime()))) 
				return true;
			else
				return false;
    	} else {
    		return false;
    	}
    }

    public void onRowCancel(RowEditEvent event) {    
    	file = null;
    	uploadFile = null;
    }


	public int getItemsSize() {
		if (this.resultList != null && !this.resultList.isEmpty()) {
			return this.resultList.size();
		} else {
			return 0;
		}
	}
	
	public void updateTimestamp() {
    	if(filters.getStrLastVersion().equals("Y")) {
    		filters.setTimestampVar(null);
    		onSearch();
    	}
    }
	
	public boolean disabledPublicate() {
		Date hoy = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String date1 = sdf.format(hoy);
		String date2 = sdf.format(filters.getDate());
		
		if(filters.getStrLastVersion().equals("Y") && date1.equals(date2))
			return false;
		return true;
	}
	
	public void sendMail(InstructedOperationFlowShippersBean bean) {
		HashMap<String,String> values = new HashMap<String,String>();
		
		String type = "";
		
		if(bean.getFlowType().contains("OPERATION"))
			type = "OPERATION_FLOW.SHIPPER";
		else if (bean.getFlowType().contains("INSTRUCTED"))
			type = "INSTRUCTED_FLOW.SHIPPER";
	
		values.put("1", bean.getShipperCode());
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	String dateString = df.format(bean.getTimestamp());
		values.put("2", dateString);
		values.put("3", bean.getResolvedTime());
		values.put("4", bean.getZoneCode());
		
		mailService.sendEmail(type, values, getChangeSystemView().getIdn_active(), bean.getShipperId());
		//Para comprobar los valores que se pasan en el email
		getMessages().addMessage(Constants.head_menu[9],
				new MessageBean(Constants.INFO, "Mail values", "Shipper"+values.get("1")+",Fecha:"+values.get("2")+",Time:"+values.get("3")+",Zone:"+values.get("4")+". Destinatario:"+bean.getShipperId(), Calendar.getInstance().getTime()));
	}
	

    public void handleFileUpload(FileUploadEvent event) {
    	
		file = event.getFile();
		if(file!=null){
			uploadFile = new FileBean(file.getFileName(), file.getContentType(), file.getContents());
		}
        
		if(file==null || uploadFile==null){
			getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.ERROR,"Error saving file","The instructed flow file should be selected", Calendar.getInstance().getTime()));
			return;
		}

    }

	public StreamedContent getFlowFile() {
		if(selectedItem==null){
			getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.ERROR,"Downloading file", "There is no a register selected", Calendar.getInstance().getTime()));
	    	return null;
		} else {
			if(selectedItem.getIdn_intraday_gas_flow_file()==null){
				getMessages().addMessage(Constants.head_menu[9],new MessageBean(Constants.ERROR,"Downloading file", "There is no a file to download", Calendar.getInstance().getTime()));
		    	return null;
			}
			
		}
		
		
		return service.getFile(selectedItem.getIdn_intraday_gas_flow_file());
	}

}
