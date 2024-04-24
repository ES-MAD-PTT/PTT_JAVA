package com.atos.views.tariff;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.DataFormat;
import org.primefaces.context.RequestContext;

import com.atos.beans.MessageBean;
import com.atos.beans.tariff.TariffDailyOverviewBean;
import com.atos.filters.tariff.TariffDailyOverviewFilter;
import com.atos.services.tariff.TariffDailyOverviewService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name = "tariffDailyOverviewView")
@ViewScoped
public class TariffDailyOverviewView extends CommonView implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -2864933420481152981L;
	private String tariffCode ;
	private String tariffType ;
	
	private static final Logger log = LogManager.getLogger(TariffDailyOverviewView.class);

	private TariffDailyOverviewFilter filters;
	private List<TariffDailyOverviewBean> items;
	
	private Map<BigDecimal, Object> comboShipper;
	
	@ManagedProperty("#{tariffDailyOverviewService}")
	transient private TariffDailyOverviewService service;


	private Calendar firstOpenDay;

	public Calendar getFirstOpenDay() {
		return firstOpenDay;
	}

	public void setFirstOpenDay(Calendar firstOpenDay) {
		this.firstOpenDay = firstOpenDay;
	}
	
	public TariffDailyOverviewFilter getFilters() {
		return filters;
	}

	public void setFilters(TariffDailyOverviewFilter filters) {
		this.filters = filters;
	}

	public void setService(TariffDailyOverviewService service) {
		this.service = service;
	}

	public List<TariffDailyOverviewBean> getItems() {
		return items;
	}

	public void setItems(List<TariffDailyOverviewBean> items) {
		this.items = items;
	}

	
	
	public String getTariffCode() {
		return tariffCode;
	}

	public void setTariffCode(String tariffCode) {
		this.tariffCode = tariffCode;
	}

	public String getTariffType() {
		return tariffType;
	}

	public void setTariffType(String tariffType) {
		this.tariffType = tariffType;
	}

	@PostConstruct
	public void init() {
		filters = new TariffDailyOverviewFilter();
		
		tariffCode= new String();
		tariffType=new String();
		
		comboShipper= service.selectShippers(filters);

		//si el usuario que ha entrado en la pantalla es un shipper,
		//el combo mostrara solo su id
		if (getUser().isUser_type(Constants.SHIPPER)) {
			filters.setIdn_user_group(getUser().getIdn_user_group());
		}
		
	
	}

	
	public Map<BigDecimal, Object> getShippers() {
		return comboShipper;
	}
	
	public BigDecimal getIdnTariffCharge() {
		return service.getIdnTariffCharge(filters);
	}
	
	
	public Map<BigDecimal, Object> getContracts() {
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		return service.selectContracts(filters);
	}
	
	public Map<BigDecimal, Object> getVariables() {
		filters.setIdn_tariff_charge(getIdnTariffCharge());
		return service.selectVariable(filters);
	}
	
	public Map<BigDecimal, Object> getSystemPointTypes() {
		//if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE)){
		if (filters.getIdn_tariff_code()!=null && (tariffCode.equals(Constants.OVERUSAGE_CHARGE) || tariffCode.equals(Constants.COMMODITY_CHARGE))){
			return service.selectSystemPointType(filters);	
		}else 
			return service.selectSystemPointType(null);
		
	}
	

	// Methods
	public void onSearch() {

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_tariffDailyOverview1').clearFilters()");
		
		filters.setIdn_system(getChangeSystemView().getIdn_active());
		
		if (filters.getIdn_user_group() == null) {
			getMessages().addMessage(Constants.head_menu[10], new MessageBean(Constants.ERROR, "Error Shipper",
					"The Shipper must be informed.", Calendar.getInstance().getTime()));
			if (items != null) {
				items.clear();
			}
			return;
		}
		 
		if (filters.getShortDate() == null) {
			getMessages().addMessage(Constants.head_menu[10],
					new MessageBean(Constants.ERROR, "Error dates",
							"The Date Month/Year Charge in Fee Data Administration  must be informed.",
							Calendar.getInstance().getTime()));
			if (items != null) {
				items.clear();
			}
			return;
		}

		
		if (filters.getIdn_tariff_code() == null) {
			getMessages().addMessage(Constants.head_menu[10], new MessageBean(Constants.ERROR, "Error Tariff Code",
					"The Tariff Code must be informed.", Calendar.getInstance().getTime()));
			if (items != null) {
				items.clear();
			}
			return;
		}
		

		if (items != null) {
			items.clear();
		}
		
		tariffCode = service.getCodeTarifCode(filters);
		tariffType =service.getCodeTarifType(filters);
		
		if ( !tariffCode.equals(Constants.IMBALANCE_PENALTY_CHARGE) &&  filters.getIdn_contract() == null) {
			getMessages().addMessage(Constants.head_menu[10], new MessageBean(Constants.ERROR, "Error Contract Code",
					"The Contract Code must be informed.", Calendar.getInstance().getTime()));
			return;
		}
		
		
		if ( (tariffCode.equals(Constants.OVERUSAGE_CHARGE)|| tariffCode.equals(Constants.COMMODITY_CHARGE)) &&  tariffType == null) {
			getMessages().addMessage(Constants.head_menu[10], new MessageBean(Constants.ERROR, "Error Tariff Type",
					"The Tariff Type  must be informed.", Calendar.getInstance().getTime()));
			return;
		}
		
		
		if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.CAPACITY_CHARGE)){
			
			items = service.selectDetailCapacityCharge(filters);
		
		//ch715
		}else if(filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.COMMODITY_CHARGE)  && tariffType!=null && tariffType.equals(Constants.ENTRY)){
			
			items = service.selectDetailComodityChargeEntry(filters);
		}else if(filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.COMMODITY_CHARGE)  && tariffType!=null && tariffType.equals(Constants.EXIT)){
			
			items = service.selectDetailComodityChargeExit(filters);
			
		}else if(filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE) && tariffType!=null && tariffType.equals(Constants.ENTRY)){
			
			items = service.selectDetailOverUsageChargeEntry(filters);
			
		}else if(filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE)&& tariffType!=null && tariffType.equals(Constants.EXIT)){
			
			items = service.selectDetailOverUsageChargeExit(filters);
			
		}else if(filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.IMBALANCE_PENALTY_CHARGE)){
			
			items = service.selectDetailImbalancePenalty(filters);
		}
		
       
	}
	
	
	
	public boolean renderColumnDetail2( int columna) {
		
		
		//log.info("Error Tariff Type, columna:  " +columna, Calendar.getInstance().getTime());
		
		if (items ==null ||  items.size()==0){
			 return false;
		}
		
		if(columna==1 || columna==2){
			if(filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.CAPACITY_CHARGE)){
				return true;
			}else return false;
		}
		
		if(columna==31 ){
			if(filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.COMMODITY_CHARGE) && tariffType!=null && tariffType.equals(Constants.ENTRY)){
				return true;
			}else return false;
		}
		if(columna==32 ){
			if(filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.COMMODITY_CHARGE) && tariffType!=null && tariffType.equals(Constants.EXIT)){
				return true;
			}else return false;
		}

		if(columna==4){
			if(filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.COMMODITY_CHARGE) ){
				return true;
			}else return false;
		}

		
		//SAT CH522 quitar columna reduced exit capacity y reduced entry capacity
				//SAT CH710 Ahora ahi van las colunas nuevas Excl Tolerance si  las pintamos
				//if( columna==6 || columna==9){
			//		return false;
			//	}
		
		
		
		if(columna==5 || columna==6 || columna==7){
			//if(columna==5  columna==7){
			if(filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE)){
				if(filters.getIdn_system_point_type()!= null && tariffType!=null  &&  tariffType.equals(Constants.ENTRY))
				return true;
			}else return false;
		}
		
		if(columna==8 || columna==9 || columna==10){
		//if(columna==8 ||  columna==10){
			if(filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE)){
				if(filters.getIdn_system_point_type()!= null && tariffType!=null  && tariffType.equals(Constants.EXIT))
				return true;
			}else return false;
		}
		if(columna==11 || columna==12 || columna==13){
			if(filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.IMBALANCE_PENALTY_CHARGE)){
				return true;
			}else return false;
		}
		
		return false;
	}
	
	
/*	public boolean renderColumnDetail( int columna) {
		
		//if(filters.getIdn_tariff_code()!=null){
		if (items.size()>0){
		
		// tariffCode = service.getCodeTarifCode(filters);
		// tariffType =service.getCodeTarifType(filters);
		


			if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.CAPACITY_CHARGE) && columna == 1 ) {
				
				return true;
			} else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.CAPACITY_CHARGE) && columna == 2 ) {
				return true;
				
			} else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.COMMODITY_CHARGE)  && columna == 3 ) {
				return true;
				
			}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.COMMODITY_CHARGE) && columna == 4 ) {
				return true;
				
			}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE) && 
					  filters.getIdn_system_point_type()!= null && tariffType!=null  && tariffType.equals(Constants.ENTRY)&& columna == 5 ) {			
				return true;				

			}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE) &&
					 filters.getIdn_system_point_type()!= null && tariffType!=null  && tariffType.equals(Constants.ENTRY) && columna == 6) {
				return true;
				
			}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE) && 
					 filters.getIdn_system_point_type()!= null && tariffType!=null &&  tariffType.equals(Constants.ENTRY) && columna == 7 ) {
				return true;
				
			}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE) && 
					 filters.getIdn_system_point_type()!= null && tariffType!=null && tariffType.equals(Constants.EXIT) && columna == 8) {
				return true;
			}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE)  && 
					 filters.getIdn_system_point_type()!= null && tariffType!=null &&  tariffType.equals(Constants.EXIT) && columna == 9 ) {
				return true;
				
			}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE)  && 
					 filters.getIdn_system_point_type()!= null && tariffType!=null && tariffType.equals(Constants.EXIT)  && columna == 10 ) {
				return true;
				
			}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.IMBALANCE_PENALTY_CHARGE) && columna == 11 ) {
				return true;
			}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.IMBALANCE_PENALTY_CHARGE) && columna == 12 ) {
					return true;
			}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.IMBALANCE_PENALTY_CHARGE) && columna == 13 ) {
				return true;
				
			}else
		
				
				return false;
		}
		
		
		return false;
	}*/


	public void onClear() {
		// RequestContext.getCurrentInstance().reset("form");
		filters = new TariffDailyOverviewFilter();
		

		if (items != null) {
			items.clear();
		}

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('w_tariffDailyOverview1').clearFilters()");
		
	}

	
/*	//borrar
	public BigDecimal getTotalDailyBookedEntryCapacity() {

		BigDecimal total = new BigDecimal(0);
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getDailyBookedEntryCapacity() != null) {
				total = total.add(items.get(i).getDailyBookedEntryCapacity());
			}
		}
		return total;

	}
	
	//borrar
	public BigDecimal getTotalDailyCapacityCharge() {

		BigDecimal total = new BigDecimal(0);
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getDailyCapacityCharge() != null) {
				total = total.add(items.get(i).getDailyCapacityCharge());
			}
		}
		return total;

	}*/
		
	public String disabledSystemPointType(){
		//String tariffCode = service.getCodeTarifCode(filters);
		
		//CH715
		//if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE) ){
		if (filters.getIdn_tariff_code()!=null && (tariffCode.equals(Constants.OVERUSAGE_CHARGE) || (tariffType!=null && tariffCode.equals(Constants.COMMODITY_CHARGE) ))){
			
			return "false";
		}else
			
			 return "true";
		}
		
	public String disabledContract(){
		//String tariffCode = service.getCodeTarifCode(filters);
		if (filters.getIdn_tariff_code()!=null && !tariffCode.equals(Constants.IMBALANCE_PENALTY_CHARGE) ){
			return "false";
		}else
			 return "true";
		}
		
	
	public BigDecimal getTotal(int columna){
		
		BigDecimal total = new BigDecimal(0);
		//String tariffCode = service.getCodeTarifCode(filters);
		//String tariffType =service.getCodeTarifType(filters);
		
		if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.CAPACITY_CHARGE) && columna == 1 ) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getDailyBookedEntryCapacity() != null) {//dailyBookedEntryCapacity
					total = total.add(items.get(i).getDailyBookedEntryCapacity());
				}
			}
			return total;
		} else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.CAPACITY_CHARGE) && columna == 2) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getDailyCapacityCharge() != null) {//dailyCapacityCharge
					total = total.add(items.get(i).getDailyCapacityCharge());
				}
			}
			return total;
			
		} else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.COMMODITY_CHARGE) && columna == 31) {
			
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getDailyAllocatedEntryValue() != null) {//dailyAllocatedEntryValue
					total = total.add(items.get(i).getDailyAllocatedEntryValue());
				}
			}
			return total;
		} else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.COMMODITY_CHARGE) && columna == 32) {
			
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getDailyAllocatedExitValue() != null) {//dailyAllocatedExitValue
					total = total.add(items.get(i).getDailyAllocatedExitValue());
				}
			}
			return total;
		
			
		}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.COMMODITY_CHARGE) && columna == 4 ) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getDailyCommodityCharge() != null) {//dailyCommodityCharge
					total = total.add(items.get(i).getDailyCommodityCharge());
				}
			}
			return total;
			
		}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE) && tariffType!=null && tariffType.equals(Constants.ENTRY)&& columna == 5 ) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getDailyOriginalEntryCapacityOU() != null) { //dailyOriginalEntryCapacityOU
					total = total.add(items.get(i).getDailyOriginalEntryCapacityOU());
				}
			}
			
			return total;				

		}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE) && tariffType!=null && tariffType.equals(Constants.ENTRY) && columna == 6) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getDailyEntryCapacityOU() != null) {//dailyEntryCapacityOU
					total = total.add(items.get(i).getDailyEntryCapacityOU());
				}
			}
			return total;
			
		}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE) && tariffType!=null && tariffType.equals(Constants.ENTRY) && columna == 7 ) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getDailyEntryCapacityOUCharge() != null) {//dailyEntryCapacityOUCharge
					total = total.add(items.get(i).getDailyEntryCapacityOUCharge());
				}
			}
			return total;
			
		}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE) && tariffType!=null && tariffType.equals(Constants.EXIT) && columna == 8) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getDailyOriginalExitCapacityOU() != null) {//dailyOriginalExitCapacityOU
					total = total.add(items.get(i).getDailyOriginalExitCapacityOU());
				}
			}
			return total;
		}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE)  && tariffType!=null && tariffType.equals(Constants.EXIT) && columna == 9 ) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getDailyExitCapacityOU() != null) {//dailyExitCapacityOU
					total = total.add(items.get(i).getDailyExitCapacityOU());
				}
			}
			return total;
			
		}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE)  && tariffType!=null && tariffType.equals(Constants.EXIT)  && columna == 10 ) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getDailyExitCapacityOUCharge() != null) {//dailyExitCapacityOUCharge
					total = total.add(items.get(i).getDailyExitCapacityOUCharge());
				}
			}
			return total;
			
		}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.IMBALANCE_PENALTY_CHARGE) && columna == 11 ) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getDailyOriginalImbalance() != null) {//dailyOriginalImbalance
					total = total.add(items.get(i).getDailyOriginalImbalance());
				}
			}
			return total;
		}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.IMBALANCE_PENALTY_CHARGE) && columna == 12 ) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getDailyImbalance() != null) {//dailyImbalance
					total = total.add(items.get(i).getDailyImbalance());
				}
			}
			return total;
		}else if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.IMBALANCE_PENALTY_CHARGE) && columna == 13 ) {
			
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getDailyImbalanceCharge() != null) {//dailyImbalanceCharge
					total = total.add(items.get(i).getDailyImbalanceCharge());
				}
			}
			return total;
			
		}else
			return total;
	}
	
	
	/**********************************
	 * EXCEL
	 ***********************************/
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
	
	private HSSFWorkbook wb;
	// Fonts
	private HSSFFont headerFont;
	private HSSFFont contentFont;
	private HSSFFont totalFont;
	// Styles
	private HSSFCellStyle cellStyleHeader;

	private HSSFCellStyle cellStyleTwoeDec; 
	private HSSFCellStyle cellStyleThreeDec; 
	private HSSFCellStyle cellStyleFourDec; 
	private HSSFCellStyle cellStyleTotalTwoeDec; 
	private HSSFCellStyle cellStyleTotalThreeDec; 
	private HSSFCellStyle cellStyleTotalFourDec; 
	
	private HSSFCellStyle cellStyleText;
	private HSSFCellStyle cellStyleTotal;
	
	/**
	 * Create a new font on base workbook
	 * 
	 * @param fontColor       Font color (see {@link HSSFColor})
	 * @param fontHeight      Font height in points
	 * @param fontBold        Font is boldweight (<code>true</code>) or not (<code>false</code>)
	 * 
	 * @return New cell style
	 */
	private HSSFFont createFont(short fontColor, short fontHeight, boolean fontBold) {
 
		HSSFFont font = wb.createFont();
		font.setBold(fontBold);
		font.setColor(fontColor);
		font.setFontName("Calibri");
		font.setFontHeightInPoints(fontHeight);
 
		return font;
	}
	private HSSFCellStyle createStyle(HSSFFont font, short cellAlign, short cellColor, boolean cellBorder, short cellBorderColor, short border, 
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
	
	public void detailProcessXLS2(Object document) {
		
		wb = new HSSFWorkbook();
		wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		
		wb.setSheetName(0, "TariffDailyOverview");	
				
		// Generate fonts
		headerFont  = createFont(HSSFColor.BLACK.index, (short)12, true);
		contentFont = createFont(HSSFColor.BLACK.index, (short)10, false);
		totalFont = createFont(HSSFColor.BLACK.index, (short)12, true);
		
		
		// Generate styles
		DataFormat format = wb.createDataFormat();
		
		cellStyleHeader  =  createStyle(headerFont,  HSSFCellStyle.ALIGN_CENTER, HSSFColor.GREY_40_PERCENT.index, true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		cellStyleText  =    createStyle(contentFont, HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, false, format.getFormat("#,##"));
		cellStyleTotal  =    createStyle(totalFont,  HSSFCellStyle.ALIGN_LEFT,   HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, false, format.getFormat("#,##"));
		cellStyleTwoeDec =  createStyle(contentFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.00"));
		cellStyleThreeDec = createStyle(contentFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.000"));
		cellStyleFourDec = createStyle(contentFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.0000"));
		cellStyleTotalTwoeDec =  createStyle(totalFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,        true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, true, format.getFormat("#,##0.00"));
		cellStyleTotalThreeDec = createStyle(totalFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,        true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, true, format.getFormat("#,##0.000"));
		cellStyleTotalFourDec = createStyle(totalFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,        true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM, true, format.getFormat("#,##0.0000"));

				
	
		
		// Para convertir las cantidades string en double.
	    NumberFormat nf = NumberFormat.getInstance(new Locale(getLanguage().getLocale()));
	    
		//DETALLE	
	     for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				for (int j = 0; j <= sheet.getRow(i).getPhysicalNumberOfCells() - 1; j++) {
					
					HSSFCell cell = sheet.getRow(i).getCell(j);
					cell.setCellStyle(cellStyleText);
					String tmpStrValue = cell.getStringCellValue();
					
       			if(j==1){
       				//SAT CH524 cell.setCellStyle(cellStyleFourDec);
   					//cell.setCellStyle(cellStyleTwoeDec);	
   					cell.setCellStyle(cellStyleFourDec);
       				try {
           				cell.setCellValue(nf.parse(tmpStrValue).doubleValue());
           			}
           			catch(Exception e){
           				log.catching(e);
           			}
       			}else if (j==2){
       				/*//SAT CH522
       				if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE)&&( tariffType.equals(Constants.ENTRY))) {
       					//SAT CH524 cell.setCellStyle(cellStyleFourDec);
       					cell.setCellStyle(cellStyleTwoeDec);	
       				}else{
       					cell.setCellStyle(cellStyleTwoeDec);
       				}
       				*/
       				if (filters.getIdn_tariff_code()!=null && ( tariffCode.equals(Constants.CAPACITY_CHARGE) || tariffCode.equals(Constants.COMMODITY_CHARGE))) {
       					cell.setCellStyle(cellStyleTwoeDec);
       				} else {
       					cell.setCellStyle(cellStyleFourDec);
       				}
       				
       				try {
           				cell.setCellValue(nf.parse(tmpStrValue).doubleValue());
           			}
           			catch(Exception e){
           				log.catching(e);
           			}
       				
       			}else if (j==3){
       				cell.setCellStyle(cellStyleTwoeDec);
       				try {
           				cell.setCellValue(nf.parse(tmpStrValue).doubleValue());
           			}
           			catch(Exception e){
           				log.catching(e);
           			}
       			}
       			
				}
				
	     }//fin detalle
		
		  // TOTALES
	     
	        //String tariffCode = service.getCodeTarifCode(filters);
			//String tariffType =service.getCodeTarifType(filters);
			
			double total = 0;
			double total2 = 0;
			double total3 = 0;
			
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				for (int j = 0; j < sheet.getRow(i).getPhysicalNumberOfCells() ; j++) {
				
					if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.CAPACITY_CHARGE)){
						if (j == 1) {
							
							if(items.get(i - 1).getDailyBookedEntryCapacity()!=null){
								total = total + items.get(i - 1).getDailyBookedEntryCapacity().doubleValue();
							}
							
						}
						if (j == 2) {
							
								if (items.get(i - 1).getDailyCapacityCharge()!=null){
									total2 = total2 + items.get(i - 1).getDailyCapacityCharge().doubleValue();		
								}	
							
						}
						
					}//iff capacity charge
					
					if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.COMMODITY_CHARGE)  && tariffType!=null && tariffType.equals(Constants.EXIT)){
						if (j == 1) {
							
							if(items.get(i - 1).getDailyAllocatedExitValue()!=null){
								total = total + items.get(i - 1).getDailyAllocatedExitValue().doubleValue();
							}
							
						}
						if (j == 2) {
							
								if (items.get(i - 1).getDailyCommodityCharge()!=null){
									total2 = total2 + items.get(i - 1).getDailyCommodityCharge().doubleValue();		
								}	
							
						}
						
					}
					if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.COMMODITY_CHARGE)  && tariffType!=null && tariffType.equals(Constants.ENTRY)){
						if (j == 1) {
							
							if(items.get(i - 1).getDailyAllocatedEntryValue()!=null){
								total = total + items.get(i - 1).getDailyAllocatedEntryValue().doubleValue();
							}
							
						}
						if (j == 2) {
							
								if (items.get(i - 1).getDailyCommodityCharge()!=null){
									total2 = total2 + items.get(i - 1).getDailyCommodityCharge().doubleValue();		
								}	
							
						}
						
					}
					// SAT CH522 ahora solo tiene dos columnas OVERUSER ENTRY. Quitamos daily reduced 
					// SAT710 columna nueva original 
					if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE) && tariffType!=null && tariffType.equals(Constants.ENTRY) ){
						
						
						
						if (j == 1) {
							
							if(items.get(i - 1).getDailyOriginalEntryCapacityOU()!=null){//dailyOriginalEntryCapacityOU
								total = total + items.get(i - 1).getDailyOriginalEntryCapacityOU().doubleValue();
							}
							
						}
						
						if (j == 2) {
							
							if(items.get(i - 1).getDailyEntryCapacityOU()!=null){//dailyEntryCapacityOU
								total2 = total2 + items.get(i - 1).getDailyEntryCapacityOU().doubleValue();
							}
							
						}
					/*	if (j == 2) {
							
								if (items.get(i - 1).getDailyReducedEntryCapacityOU()!=null){
									total2 = total2 + items.get(i - 1).getDailyReducedEntryCapacityOU().doubleValue();		
								}	
							
						}*/
						
						if (j == 3) {
							
							if (items.get(i - 1).getDailyEntryCapacityOUCharge()!=null){//dailyEntryCapacityOUCharge
								total3 = total3 + items.get(i - 1).getDailyEntryCapacityOUCharge().doubleValue();//
							}	
						
						}
					
					}
					
					
				
						
					// SAT CH522 ahora solo tiene dos columnas OVERUSER EXIT. Quitamos daily reduced exit
					if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.OVERUSAGE_CHARGE) && tariffType!=null && tariffType.equals(Constants.EXIT) ){
						if (j == 1) {
							
							if(items.get(i - 1).getDailyOriginalExitCapacityOU()!=null){
								total = total + items.get(i - 1).getDailyOriginalExitCapacityOU().doubleValue();
							}
							
						}
						
						if (j == 2) {
							
							if(items.get(i - 1).getDailyExitCapacityOU()!=null){
								total2 = total2 + items.get(i - 1).getDailyExitCapacityOU().doubleValue();
							}
							
						}
						
					/*	if (j == 2) {
					
							if (items.get(i - 1).getDailyReducedExitCapacityOU()!=null){
								total2 = total2 + items.get(i - 1).getDailyReducedExitCapacityOU().doubleValue();		
							}	
					
						}*/
						
						if (j == 3) {
							
							if (items.get(i - 1).getDailyExitCapacityOUCharge()!=null){
								//total3 = total3 + items.get(i - 1).getDailyExitCapacityOUCharge().doubleValue();
								total3 = total3 + items.get(i - 1).getDailyExitCapacityOUCharge().doubleValue();		
							}	
						
						}
					
					}
					
					
					if (filters.getIdn_tariff_code()!=null && tariffCode.equals(Constants.IMBALANCE_PENALTY_CHARGE)){
						//CH711 SAT FASE III  se añade dailyOriginalImbalance
						if (j == 1) {
							
							if(items.get(i - 1).getDailyOriginalImbalance()!=null){
								total = total + items.get(i - 1).getDailyOriginalImbalance().doubleValue();
							}
							
						}
						if (j == 2) {
							
							if(items.get(i - 1).getDailyImbalance()!=null){
								total2 = total2 + items.get(i - 1).getDailyImbalance().doubleValue();
							}
							
						}
						if (j == 3) {
							
								if (items.get(i - 1).getDailyImbalanceCharge()!=null){
									total3 = total3 + items.get(i - 1).getDailyImbalanceCharge().doubleValue();		
								}	
							
						}
				}
					
					
				}//for

			}//for
			
			int newRow = sheet.getPhysicalNumberOfRows();
		
			sheet.createRow(newRow);

			HSSFCell cell = sheet.getRow(newRow).createCell(0);
			cell.setCellValue("Total:");
			cell.setCellStyle(cellStyleTotal);

			//primer total 
			cell = sheet.getRow(newRow).createCell(1);		
			cell.setCellValue(total);		
			cell.setCellStyle(cellStyleTotalFourDec);
			//cell.setCellStyle(cellStyleTotalTwoeDec);
			
			//segundo total 2 dec
			cell = sheet.getRow(newRow).createCell(2);
			cell.setCellValue(total2);
			if(tariffCode.equals(Constants.CAPACITY_CHARGE) || tariffCode.equals(Constants.COMMODITY_CHARGE)) {
				cell.setCellStyle(cellStyleTotalTwoeDec);
			} else {
				cell.setCellStyle(cellStyleTotalFourDec);
			}

			//SAT CH522 ya no hay total3 
			/*if(tariffCode.equals(Constants.OVERUSAGE_CHARGE) ){
				cell = sheet.getRow(newRow).createCell(3);
				cell.setCellValue(total3);
				cell.setCellStyle(cellStyleTotalTwoeDec);
			}*/
			
			//SAT CH710 ahora si total3 
			if(tariffCode.equals(Constants.OVERUSAGE_CHARGE) ){
				cell = sheet.getRow(newRow).createCell(3);
				cell.setCellValue(total3);
				cell.setCellStyle(cellStyleTotalTwoeDec);
			}
			
			//CH711 SAT FASE III  se añade dailyOriginalImbalance 
			if(tariffCode.equals(Constants.IMBALANCE_PENALTY_CHARGE) ){
				cell = sheet.getRow(newRow).createCell(3);
				cell.setCellValue(total3);
				cell.setCellStyle(cellStyleTotalTwoeDec);
			}
			
			//CABECERA: 
			for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
				HSSFCell cellHeader = header.getCell(i);
				cellHeader.setCellStyle(cellStyleHeader);
				sheet.autoSizeColumn(i);
				
			}
			

	}
	

	// Este booleano marcara si esta habilitado el filtro de shipper (isOperator = true) o esta deshabilitado, 
		// con un shipper fijo.
		public boolean getIsShipper() {
			return getUser().isUser_type(Constants.SHIPPER);
		}
			
		public void onTypeChange(){
			tariffType =service.getCodeTarifType(filters);
		}
		
		public void onVariableChange(){
			tariffCode =service.getCodeTarifCode(filters);
		}
		
	    
	    public int getItemsSize() { 
			if(this.items!=null && !this.items.isEmpty()){
				return this.items.size();
			}else{
				return 0;
			}
		}
}