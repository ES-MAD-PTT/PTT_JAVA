package com.atos.views.booking;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct; 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

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
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

import com.atos.beans.MessageBean;
import com.atos.beans.booking.CapacityPublicationBean;
import com.atos.filters.booking.CapacityPublicationFilter;
import com.atos.services.booking.CapacityPublicationService;
import com.atos.utils.Constants;
import com.atos.utils.POIXSSFExcelUtils;
import com.atos.utils.PropertiesManager;
import com.atos.views.CommonView;

@ManagedBean(name = "capacityPublicationView")
@ViewScoped
public class CapacityPublicationView extends CommonView {
  
    /**
	 * 
	 */
	private static final long serialVersionUID = -2258047552010712161L;
	private static final Logger log = LogManager.getLogger(CapacityPublicationView.class);
	
	private static final String onShoreMapCenterCoord = "13.65, 100.0";		// lat , long
	private static final String offShoreMapCenterCoord = "10.374238, 101.296684";		// lat , long
	private static final int onShoreMapZoom = 7;
	private static final int offShoreMapZoom = 6;
	
	private MapModel mapModel;
	private CapacityPublicationFilter filters;
	private List<CapacityPublicationBean> items;
	
	private ResourceBundle msgs;
	
	private Date queryDate;
	private Date currentDate = new Date();

	@ManagedProperty("#{capacityPublicationService}")
    transient private CapacityPublicationService service;
    
    public void setService(CapacityPublicationService service) {
        this.service = service;
    }
	
	private Calendar sysdate;

	public Calendar getSysdate() {
		return sysdate;
	}

	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

    
    @PostConstruct
    public void init() {
    	msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	filters = new CapacityPublicationFilter();
    	filters.setOnshoreSystem(getChangeSystemView().isOnshore());
    	this.setMapModel(this.buildMapModel(filters));
    	this.sysdate = Calendar.getInstance();
    	this.sysdate.set(Calendar.HOUR_OF_DAY, 0);
    	this.sysdate.set(Calendar.MINUTE, 0);
    	this.sysdate.set(Calendar.SECOND, 0);
    	this.sysdate.set(Calendar.MILLISECOND, 0);
    }
  
    public MapModel getMapModel() {
        return mapModel;
    }
    
	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}

	public CapacityPublicationFilter getFilters() {
		return filters;
	}

	public void setFilters(CapacityPublicationFilter filters) {
		this.filters = filters;
	}
 
	public Date getCurrentDate() {
		return this.currentDate;
	}
	
	// Methods
	public void onSearch(){
		if(filters.getStartDate()==null || filters.getEndDate()==null) {
			getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Capacity Publication", "From date and To date must not be empty", Calendar.getInstance().getTime()));
	    	return;
		}
    	if(filters.getStartDate()!=null && filters.getEndDate()!=null){
    		if(filters.getStartDate().after(filters.getEndDate())){
    	    	getMessages().addMessage(Constants.head_menu[1],new MessageBean(Constants.ERROR,"Capacity Publication", "From  date must be previous or equal to To date.", Calendar.getInstance().getTime()));
    	    	return;
    		}
    	}

		this.setMapModel(this.buildMapModel(filters));
		filters.setZones_query();
        items = service.search(filters);
        queryDate = new Date(System.currentTimeMillis());
	}
	public void onClear(){
		filters = new CapacityPublicationFilter();
        if (items != null) {
            items.clear();
        }
    }

	public List<CapacityPublicationBean> getItems() {
		return items;
	}
	public void selectAll() {
        filters.setSelectAll(filters.isSelectAll());
    }
	
	public String getMapCenter(){
		return getChangeSystemView().isOnshore()? onShoreMapCenterCoord: offShoreMapCenterCoord;
	}
	
	public int getMapZoom(){
		return getChangeSystemView().isOnshore()? onShoreMapZoom: offShoreMapZoom;
	}
	
	private HSSFWorkbook wb;
    private HSSFCellStyle cellStyleThreeDec;
    private HSSFFont contentFont;

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
    
	public void postProcessXLS(Object document) {
        wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);
         
        HSSFCellStyle cellStyleHeader = wb.createCellStyle();  
        cellStyleHeader.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
        cellStyleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

		// Generate styles
		DataFormat format = wb.createDataFormat();

        contentFont = createFont(HSSFColor.BLACK.index, (short)10, false);
		cellStyleThreeDec = createStyle(contentFont, HSSFCellStyle.ALIGN_RIGHT,  HSSFColor.WHITE.index,           true, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN, true, format.getFormat("#,##0.000"));

		HSSFCellStyle cellStyleHide  =     POIXSSFExcelUtils.createStyleHide(wb);
        
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            HSSFCell cell = header.getCell(i);
            
            if(i<=5) {
            	cell.setCellStyle(cellStyleHeader);
            } else {
        		cell.setCellStyle(cellStyleHide);
			}
        }
        HSSFCell cellHeader = header.createCell(header.getPhysicalNumberOfCells());
        cellHeader.setCellStyle(cellStyleHeader);
        cellHeader.setCellValue(msgs.getString("cap_pub_timestamp"));
        

        HSSFCellStyle cellStyleRow = wb.createCellStyle();
        cellStyleRow.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        cellStyleRow.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        cellStyleRow.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        cellStyleRow.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        
        
        for(int i=1;i<sheet.getPhysicalNumberOfRows();i++){
        	for(int j=0;j<sheet.getRow(i).getPhysicalNumberOfCells();j++){
	        	HSSFCell cell = sheet.getRow(i).getCell(j);
	        	if(j<=5) {
	        		cell.setCellStyle(cellStyleRow);
	        	}
	        	if(items.get(i-1)!=null){
	        		if(j==3) {
	        			cell.setCellStyle(cellStyleThreeDec);
		        		cell.setCellValue((items.get(i-1).getTech_cap()==null ? 0 : items.get(i-1).getTech_cap().doubleValue()));
	        		}
	        		if(j==4) {
	        			cell.setCellStyle(cellStyleThreeDec);
		        		cell.setCellValue((items.get(i-1).getAvailable_cap()==null ? 0 : items.get(i-1).getAvailable_cap().doubleValue()));
	        		}
	        		if(j==5) {
	        			cell.setCellStyle(cellStyleThreeDec);
		        		cell.setCellValue((items.get(i-1).getBooked_cap()==null ? 0 : items.get(i-1).getBooked_cap().doubleValue()));
	        		}
	        	}
	        	if (j > 5) {
	        		cell.setCellStyle(cellStyleHide);
	        		cell.setCellValue("");
				}
        	}
        	HSSFCell cell = sheet.getRow(i).createCell(sheet.getRow(i).getPhysicalNumberOfCells());
        	// Solo se pinta la fecha "Timestamp" en la primera linea de datos.
        	if(i==1) {
	        	DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	        	String dateString = df.format(queryDate);
	        	cell.setCellValue(dateString);
        	}
        	cell.setCellStyle(cellStyleRow);
        }
    }
	
	public void onRowEdit(RowEditEvent event) {

		CapacityPublicationBean capacityBean = (CapacityPublicationBean) event.getObject();

		String errorMsg = null;
		
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	String[] params = {msgs.getString("capacity_publicaction") };
    	String summaryMsgOk = CommonView.getMessageResourceString("update_ok", params);
    	String summaryMsgNotOk= CommonView.getMessageResourceString("update_noOk", params);

		if (capacityBean.getEndDate() != null) {
			if (capacityBean.getMonthYear().after(capacityBean.getEndDate())) {
				errorMsg = msgs.getString("start_previous_end"); //start_previous_end= Start date must be previous to end date
				getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR, summaryMsgNotOk, errorMsg, Calendar.getInstance().getTime()));
		    	log.error(errorMsg);
				return;
			}

		}
		String error = "0";
		boolean procesado = false;
		for(int i=0;i<items.size();i++) {
			CapacityPublicationBean bean = items.get(i);
			CapacityPublicationBean item = null;
			List<CapacityPublicationBean> list = service.selectCapacityBean(bean);
			if (list.size() > 0) {
				item = list.get(0);
			} else {
				item = bean;
			}
			if(item.getArea().equals(capacityBean.getArea())) {
/*				if(item.getClave_unica().equals(capacityBean.getClave_unica())) {
					continue;
				} else {*/
				if(item.getEndDate()!=null && item.getIdn_val_avail_capacity()!=null) {
					
					  /*    item   |-------------------|
			  	     	capbean	   |-------------------| 
			  	     item.enddate>=capbean.endDate && item.startdate>=capbean.startdate && item.startdate<=capbean.endDate*/
					if((item.getEndDate().compareTo(capacityBean.getEndDate())==0 && item.getMonthYear().compareTo(capacityBean.getMonthYear())==0 )){
						try {
							error = service.updateAvailableCapacityPublication(capacityBean);
							
						} catch (Exception e) {
							log.catching(e);
							// we assign the return message
							error = e.getMessage();
						}
						if(!error.equals("0")) {
							break;
						}
						procesado = true;
					}
					
				    /*    item                 |-------------------------|
				  	     capbean	  |-------------------| 
				  	     item.enddate>=capbean.endDate && item.startdate>=capbean.startdate && item.startdate<=capbean.endDate*/
					else if((item.getEndDate().compareTo(capacityBean.getEndDate())>=0 && item.getMonthYear().compareTo(capacityBean.getMonthYear())>=0 && item.getMonthYear().compareTo(capacityBean.getEndDate())<=0)){
						try {
							Calendar cal = Calendar.getInstance();
							cal.setTime(capacityBean.getEndDate());
							cal.add(Calendar.DAY_OF_MONTH, 1);
							item.setMonthYear(cal.getTime());
							error = service.updateAvailableCapacityPublication(item);
							
						} catch (Exception e) {
							log.catching(e);
							// we assign the return message
							error = e.getMessage();
						}
						if(!error.equals("0")) {
							break;
						}
						try {
							error = service.insertAvailableCapacityPublication(capacityBean);
						
						} catch (Exception e) {
							log.catching(e);
							// we assign the return message
							error = e.getMessage();
						}
						if(!error.equals("0")) {
							break;
						}
						procesado = true;
					
					}
					    /*      item               |-------------------------|
					          capbean        		   |-----------------|
					  item.enddate>=capbean.enddate && item.startdate <=capbean.startdate*/
					else if((item.getEndDate().compareTo(capacityBean.getEndDate())>=0 && item.getMonthYear().compareTo(capacityBean.getMonthYear())<=0)){
						Date endDate_item = item.getEndDate();
						try {
							Calendar cal = Calendar.getInstance();
							cal.setTime(capacityBean.getMonthYear());
							cal.add(Calendar.DAY_OF_MONTH, -1);
							item.setEndDate(cal.getTime());
							error = service.updateAvailableCapacityPublication(item);
							
						} catch (Exception e) {
							log.catching(e);
							// we assign the return message
							error = e.getMessage();
						}
						if(!error.equals("0")) {
							break;
						}
						try {
							error = service.insertAvailableCapacityPublication(capacityBean);
						
						} catch (Exception e) {
							log.catching(e);
							// we assign the return message
							error = e.getMessage();
						}
						if(!error.equals("0")) {
							break;
						}
						try {
							Calendar cal = Calendar.getInstance();
							cal.setTime(capacityBean.getEndDate());
							cal.add(Calendar.DAY_OF_MONTH, 1);
							item.setMonthYear(cal.getTime());
							item.setEndDate(endDate_item);
							error = service.insertAvailableCapacityPublication(item);
							
						} catch (Exception e) {
							log.catching(e);
							// we assign the return message
							error = e.getMessage();
						}
						if(!error.equals("0")) {
							break;
						}
						procesado = true;
					}
				    /*       item              |-------------------------|
				  		 capbean                           |-------------------|
				  	item.endate<=capbean.endDate && item.stardate<=capbean.startdate && item.endDate>=capbean.startdate			*/					 
					else if((item.getEndDate().compareTo(capacityBean.getEndDate())<=0 && item.getMonthYear().compareTo(capacityBean.getMonthYear())<=0 && item.getEndDate().compareTo(capacityBean.getMonthYear())>=0)){
						try {
							Calendar cal = Calendar.getInstance();
							cal.setTime(capacityBean.getMonthYear());
							cal.add(Calendar.DAY_OF_MONTH, -1);
							item.setEndDate(cal.getTime());
							error = service.updateAvailableCapacityPublication(item);
							
						} catch (Exception e) {
							log.catching(e);
							// we assign the return message
							error = e.getMessage();
						}
						if(!error.equals("0")) {
							break;
						}
						try {
							error = service.insertAvailableCapacityPublication(capacityBean);
						
						} catch (Exception e) {
							log.catching(e);
							// we assign the return message
							error = e.getMessage();
						}
						if(!error.equals("0")) {
							break;
						}
						procesado = true;
					
					}
				} 
				
			}
		}

		if(!procesado) {
			try {
				/*List<CapacityPublicationBean> list = service.selectCapacityBean(capacityBean);
				if (list.size() > 0) {
					CapacityPublicationBean bean = list.get(0);
					System.out.println("update idn: " + bean.getIdn_val_avail_capacity());
					capacityBean.setIdn_val_avail_capacity(bean.getIdn_val_avail_capacity());
	
					error = service.updateAvailableCapacityPublication(capacityBean);
				} else {
					System.out.println("inserting: " + capacityBean.toString());*/
					error = service.insertAvailableCapacityPublication(capacityBean);
			//	}
			} catch (Exception e) {
				log.catching(e);
				// we assign the return message
				error = e.getMessage();
			}
		}
		
		String[] par2 = {capacityBean.getArea(),msgs.getString("capacity_publicaction")};
		if (error != null && error.equals("0")) {
			String msg = CommonView.getMessageResourceString("updating_ok", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,summaryMsgOk, msg, Calendar.getInstance().getTime()));
			log.info("Available Capacity Updated", Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-1")) {
			String msg = CommonView.getMessageResourceString("error_updating", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating  Available Capacity", Calendar.getInstance().getTime());
		} else if (error != null && error.equals("-2")) {
			String msg = CommonView.getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error inserting Available Capacity",	Calendar.getInstance().getTime());
		} else {
			String msg = CommonView.getMessageResourceString("error_inserting", par2);
			getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.ERROR,summaryMsgNotOk, msg, Calendar.getInstance().getTime()));
			log.info("Error updating/insert Available Capacity. Error: " + error + ". ", Calendar.getInstance().getTime());
		}

	}
	
	public void onRowSearch() {
		items = service.search(filters);

	}
	
	public void onRowCancel(RowEditEvent event) {
	

	}
	
	public MapModel buildMapModel(CapacityPublicationFilter filters){
    	Properties coord;
    	
    	MapModel mapModel = new DefaultMapModel();
		try {
			coord = PropertiesManager.getPropertiesFromFile("coordinates.properties");
			
			for(int z=0;z<filters.getAreas().size();z++){
			
				String zone = (String)coord.get("zone"+filters.getAreas().get(z)); 
				String latZone_s = (String)coord.get("lat"+filters.getAreas().get(z)); 
				String lonZone_s = (String)coord.get("lon"+filters.getAreas().get(z)); 
				String colorZone = (String)coord.get("color"+filters.getAreas().get(z)); 
				
				String[] latZone = latZone_s.split(",");
				String[] lonZone = lonZone_s.split(",");
				
				if(filters.getAreas().get(z).equals("X1")||
						filters.getAreas().get(z).equals("X2")||
						filters.getAreas().get(z).equals("X3")||
						filters.getAreas().get(z).equals("Y")||
						filters.getAreas().get(z).equals("R")) {
					for(int i=0;i<latZone.length;i++) {
					
						Marker point = new Marker(new LatLng(new Double(latZone[i]), new Double(lonZone[i])), filters.getAreas().get(z));
							//"https://maps.google.com/mapfiles/ms/micons/"+colorZone+"-dot.png");
					
						mapModel.addOverlay(point);
					}
				}
				String[] zone_num_coordinates = zone.split(",");
				
				for(int i=0;i<zone_num_coordinates.length;i++){
				
				//String num_coordinates = (String)coord.get("num_coordinates");
		    	//for(int i=1;i<Integer.parseInt(num_coordinates)+1;i++){
		    		String lat_s = (String) coord.get("lat"+zone_num_coordinates[i]);
		    		String lon_s = (String) coord.get("lon"+zone_num_coordinates[i]);
		    		String color = (String) coord.get("color"+zone_num_coordinates[i]);
		    		String[] lat = lat_s.split(",");
		    		String[] lon = lon_s.split(",");
		    	
			        //Polyline
			        Polyline polyline = new Polyline();
			        for(int j=0;j<lat.length;j++){
			        	polyline.getPaths().add(new LatLng(new Double(lat[j]), new Double(lon[j])));
			        }
			        polyline.setData(filters.getAreas().get(z));
			        polyline.setStrokeWeight(5);
			        polyline.setStrokeColor(color);
			      //  polyline.setStrokeOpacity(1);
			          
			        mapModel.addOverlay(polyline);
		    	}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapModel;
	}

	public void onPolylineSelect(OverlaySelectEvent event) {
		Polyline polyline = (Polyline)event.getOverlay();
		String zone = (String)polyline.getData();
		getMessages().addMessage(Constants.head_menu[0],new MessageBean(Constants.INFO,"Area " + zone, "Area " + zone, Calendar.getInstance().getTime()));
    }
}