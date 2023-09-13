package com.atos.mapper.dam;

import com.atos.beans.dam.ChangePictureBean;

public interface ChangePictureMapper {

	public int updateChangePicture(ChangePictureBean file); 
	
	public ChangePictureBean selectChangePicture();
}
