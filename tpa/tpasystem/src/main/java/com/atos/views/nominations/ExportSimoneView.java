package com.atos.views.nominations;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
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
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormat;
import org.primefaces.context.RequestContext;

import com.atos.beans.nominations.ExportSimoneBean;
import com.atos.filters.nominations.ExportSimoneFilter;
import com.atos.services.nominations.ExportSimoneService;
import com.atos.utils.POIXSSFExcelUtils;
import com.atos.views.CommonView;

@ManagedBean(name="simoneView")
@ViewScoped
public class ExportSimoneView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4826249901263281893L;
	private static final Logger log = LogManager.getLogger(ExportSimoneView.class);
	private ExportSimoneFilter filters;
	//private ExportSimoneBean newExportSimone;
	private List<ExportSimoneBean> items;
	
	@ManagedProperty("#{exportSimoneService}")
    transient private ExportSimoneService service;
    
    public void setService(ExportSimoneService service) {
        this.service = service;
    }

    private Calendar sysdate;
    public Calendar getSysdate() {
		return sysdate;
	}


	public void setSysdate(Calendar sysdate) {
		this.sysdate = sysdate;
	}

	public ExportSimoneFilter getFilters() {
		return filters;
	}

	public void setFilters(ExportSimoneFilter filters) {
		this.filters = filters;
	}
	
	
	public List<ExportSimoneBean> getItems() {
		return items;
	}
	
	public void setItems(List<ExportSimoneBean> items) {
		this.items = items;
	}
	 
    
	@PostConstruct
    public void init() {
		
    	filters = new ExportSimoneFilter();
    	filters.setIdn_pipeline(getChangeSystemView().getIdn_active());
    }
	

	// Methods
	public void onSearch(){
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
	//	requestContext.execute("PF('w_exportSimone1').clearFilters()");
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
		// offShore
		//filters.setIdn_system(getChangeSystemView().getIdn_active());	
		items = service.selectExportSimone(filters);
        
	}
	
	public void onClear(){
		//RequestContext.getCurrentInstance().reset("form");
		filters = new ExportSimoneFilter();
		filters.setStart_date(Calendar.getInstance().getTime());
        if (items != null) {
            items.clear();
        }
    }

  
 
	public void postProcessXLS(Object document) {
		
		HSSFWorkbook wb;
		
			wb = new HSSFWorkbook();
			wb = (HSSFWorkbook) document;
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow header = sheet.getRow(0);
			
			wb.setSheetName(0, "Export Simone");		
					
			// Generate styles
			DataFormat format = wb.createDataFormat();
			
			HSSFCellStyle cellStyleHeader  =  POIXSSFExcelUtils.createStyleHeader(wb);
			HSSFCellStyle cellStyleHide  =     POIXSSFExcelUtils.createStyleHide(wb);
			HSSFCellStyle cellStyleText  =     POIXSSFExcelUtils.createStyleText(wb);
			HSSFCellStyle cellStyleTotal  =     POIXSSFExcelUtils.createStyleTotal(wb);
			HSSFCellStyle cellStyleTwoeDec =   POIXSSFExcelUtils.createStyleTwoDec(wb);
			HSSFCellStyle cellStyleThreeDec =  POIXSSFExcelUtils.createStyleThreeDec(wb);
			HSSFCellStyle cellStyleFourDec =   POIXSSFExcelUtils.createStyleFourDec(wb);
			HSSFCellStyle cellStyleTotalTwoeDec =   POIXSSFExcelUtils.createStyleTotalTwoDec(wb);
			HSSFCellStyle cellStylZeroDec  =        POIXSSFExcelUtils.createStyleZeroDec(wb);
			
			//CABECERA
			for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
				HSSFCell cell = header.getCell(i);
				cell.setCellStyle(cellStyleHeader);
			}
			
			//ancho de la cabecera
			header.setHeight((short) 0x249);
		   
			//DETALLE	
		     for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
					for (int j = 0; j <= sheet.getRow(i).getPhysicalNumberOfCells() - 1; j++) {
						
						HSSFCell cell = sheet.getRow(i).getCell(j);
						cell.setCellStyle(cellStyleText);
											
					 
						if (j !=0 && j !=1 && j !=2 && j !=3 && j !=4 && j !=5) {
							cell.setCellStyle(cellStyleThreeDec);
						}else{
							if(j==6 || j ==7 || j==8){
								cell.setCellStyle(cellStylZeroDec);
							}
						}
						
					}//for (int j = 0; j <= sheet.getRow(i).getPhysicalNumberOfCells() - 1; j++) {
					
					for (int k = 0; k < sheet.getRow(i).getPhysicalNumberOfCells() - 1; k++) {
						sheet.autoSizeColumn(k);
					}
					
		     } //for (int i = 1; i < sheet.getPhysicalNumberOfRows()
		}    
   
    public int getItemsSize() { 
		if(this.items!=null && !this.items.isEmpty()){
			return this.items.size();
		}else{
			return 0;
		}
	}
	    
}
