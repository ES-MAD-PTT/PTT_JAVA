package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.TreeMap;

import org.primefaces.model.StreamedContent;

import com.atos.beans.FileBean;

public interface ChangePictureService extends Serializable{
	
	public int saveFile(FileBean file, BigDecimal systemId) throws Exception;
	
	public byte[] image();

//	public void onCSV();
}
