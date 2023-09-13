package com.atos.views.dam;

import java.util.Calendar;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.FileBean;
import com.atos.beans.MessageBean;
import com.atos.exceptions.ValidationException;
import com.atos.services.dam.ChangePictureService;
import com.atos.services.nominations.DailyIntermediateSubmissionFileService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;

@ManagedBean(name = "changePictureView")
@ViewScoped
public class ChangePictureView extends CommonView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1804211253281868923L;
	private static final Logger log = LogManager.getLogger(ChangePictureView.class);

	private UploadedFile file;
	private FileBean uploadFile;

	@ManagedProperty("#{changePictureService}")
	transient private ChangePictureService service;

	public void setService(ChangePictureService service) {
		this.service = service;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	@PostConstruct
	public void init() {
	}

	public void handleFileUpload(FileUploadEvent event) {

		// Utilizo un ResourceBundle local por si el scope fuera Session o Application.
		// En estos casos no se actualizaria el idioma.
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		String okMessage = null;
		String errorMsg = null;
		String summaryMsg = msgs.getString("change_picture_file");
		int resSaveFile;

		// Antes de insertar el fichero en la BD se comprueba si existe plantilla/config
		// de mape en la BD
		// (tabla tpa_toperation_template).
		// En principio, no se deben permitir nominaciones diarias para offshore, pero
		// si se inserta
		// la configuracion en tpa_toperation_template, si se podria.
		/*if (!service.existsOpTemplate(getChangeSystemView().getIdn_active())) {
			String infoMsg = msgs.getString("change_picture_not_allowed");
			getMessages().addMessage(Constants.head_menu[1],
					new MessageBean(Constants.INFO, summaryMsg, infoMsg, Calendar.getInstance().getTime()));
			log.info(infoMsg);
			return;
		}*/

		file = event.getFile();
		if (file != null) {
			uploadFile = new FileBean(file.getFileName(), file.getContentType(), file.getContents());
		}

		if (file == null || uploadFile == null) {
			getMessages().addMessage(Constants.head_menu[0], new MessageBean(Constants.ERROR, "Error saving file",
					"The picture file should be selected", Calendar.getInstance().getTime()));
			return;
		}

		try {
			resSaveFile = service.saveFile(uploadFile, getChangeSystemView().getIdn_active());
			summaryMsg = msgs.getString("data_stored");
			okMessage =  "";
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.INFO, summaryMsg, okMessage, Calendar.getInstance().getTime()));

		} catch (Exception e) {
			summaryMsg = msgs.getString("saving_data_error");
			errorMsg = msgs.getString("data_not_stored_error") + " " + msgs.getString("internal_error")
						+ " " + msgs.getString("picture_changed_error");
			getMessages().addMessage(Constants.head_menu[0],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			// Se guarda el detalle del error tecnico.
			log.error(e.getMessage(), e);
		}
	}
}
