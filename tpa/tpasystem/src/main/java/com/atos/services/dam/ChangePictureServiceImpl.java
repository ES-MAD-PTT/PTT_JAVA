package com.atos.services.dam;

import java.math.BigDecimal;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.FileBean;
import com.atos.beans.dam.ChangePictureBean;
import com.atos.mapper.dam.ChangePictureMapper;
/*import com.atos.quartz.CSVDailyGeneration;
import com.atos.quartz.CSVMonthGeneration;
import com.atos.quartz.CSVWeeklyGeneration;*/

@Service("changePictureService")
public class ChangePictureServiceImpl implements ChangePictureService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7519246586679794246L;
	
	@Autowired
	private ChangePictureMapper pictureMapper;

/*	@Autowired
	private CSVDailyGeneration csv;

	@Autowired
	private CSVMonthGeneration csvMonth;

	@Autowired
	private CSVWeeklyGeneration csvWeekly;*/

	@Override
	public int saveFile(FileBean file, BigDecimal systemId) throws Exception{
				
	    ChangePictureBean bean = new ChangePictureBean();
		bean.setFile_name(file.getFileName());
		bean.setBinary_data(file.getContents());
		Calendar cal = Calendar.getInstance();
		bean.setVersion_date(cal.getTime());

		int ret3 = pictureMapper.updateChangePicture(bean);
		if(ret3!=1){
			return -1;
			//throw new Exception("Failed when trying to insert the file in operation file.");
		}

		// En caso de OK, en el parametro error_desc se va a devolver el codigo y version de nominacion.
		// Ademas, puede ser que se devuelvan warnings.
		/*String strOutMsg = inter.getError_desc();
		String resWarnings = inter.getWarnings();
		if( resWarnings != null )
			strOutMsg += System.getProperty("line.separator") + 
						resWarnings.replace(";", System.getProperty("line.separator"));
		
		return strOutMsg;*/
		return 0;
	}
	
	public byte[] image() {
		ChangePictureBean pictureBean = pictureMapper.selectChangePicture();
		return pictureBean.getBinary_data();
	}
	
/*	public void onCSV() {
		csv.generateCSV();
		csvWeekly.generateCSV();
		csvMonth.generateCSV();
	}*/
}
