package com.atos.views.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
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
import com.atos.beans.nominations.UploadNomTemplateShipperBean;
import com.atos.filters.nominations.UploadNomTemplateShipperFilter;
import com.atos.services.nominations.UploadNomTemplateShipperService;
import com.atos.utils.Constants;
import com.atos.views.CommonView;
import com.atos.views.MessagesView;

@ManagedBean(name = "uploadNomTemplateShipperView")
@ViewScoped
public class UploadNomTemplateShipperView extends CommonView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1804211253281868923L;
	private static final Logger log = LogManager.getLogger(UploadNomTemplateShipperView.class);
	private UploadNomTemplateShipperFilter filters;
	private UploadNomTemplateShipperBean newTemplate;
	private UploadNomTemplateShipperBean selected;
	private UploadNomTemplateShipperBean editTemplateShipper;
	private List<UploadNomTemplateShipperBean> items;
	private UploadedFile file;
	private FileBean uploadFile;

	@ManagedProperty("#{uploadNomTemplateShipperService}")
	transient private UploadNomTemplateShipperService service;

	public void setService(UploadNomTemplateShipperService service) {
		this.service = service;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public UploadNomTemplateShipperBean getNewTemplate() {
		return newTemplate;
	}

	public void setNewTemplate(UploadNomTemplateShipperBean newTemplate) {
		this.newTemplate = newTemplate;
	}

	public UploadNomTemplateShipperBean getSelected() {
		return selected;
	}

	public void setSelected(UploadNomTemplateShipperBean selected) {
		this.selected = selected;
	}

	public UploadNomTemplateShipperBean getEditTemplateShipper() {
		return editTemplateShipper;
	}

	public void setEditTemplateShipper(UploadNomTemplateShipperBean editTemplateShipper) {
		this.editTemplateShipper = editTemplateShipper;
	}

	public List<UploadNomTemplateShipperBean> getItems() {
		return items;
	}

	public void setItems(List<UploadNomTemplateShipperBean> items) {
		this.items = items;
	}

	@ManagedProperty("#{messagesView}")
	private MessagesView messages;

	public void setMessages(MessagesView messages) {
		this.messages = messages;
	}

	@PostConstruct
	public void init() {
		newTemplate = new UploadNomTemplateShipperBean();
		selected = new UploadNomTemplateShipperBean();
		editTemplateShipper = new UploadNomTemplateShipperBean();
		filters = new UploadNomTemplateShipperFilter();
		items = new ArrayList<UploadNomTemplateShipperBean>();
		if (getIsShipper()) {
			filters.setShipperId(getUser().getIdn_user_group());
		}
		filters.setSystemId(getChangeSystemView().getIdn_active());		
	}

	public boolean getIsShipper() {
		return getUser().isUser_type(Constants.SHIPPER);
	}

	public void onSearch() {
		items = service.search(filters);
	}

	public void onClear() {
		init();
	}

	public Map<BigDecimal, Object> getShipperIds() {
		return service.selectShipperId();
	}
	
	public Map<BigDecimal, Object> getOperationIds() {
		return service.selectOperationId();
	}

	public Map<BigDecimal, Object> getContractIds() {
		return service.selectContractId(filters);
	}

	public Map<BigDecimal, Object> getContractIdsNew() {
		UploadNomTemplateShipperFilter filterNew = new UploadNomTemplateShipperFilter();
		if(newTemplate.getShipperGroupId()!=null)
			filterNew.setShipperId(new BigDecimal(newTemplate.getShipperGroupId()));
		filterNew.setSystemId(filters.getSystemId());
		return service.selectContractId(filterNew);
	}

	public UploadNomTemplateShipperFilter getFilters() {
		return filters;
	}

	public void setFilters(UploadNomTemplateShipperFilter filters) {
		this.filters = filters;
	}

	public void handleFileUpload(FileUploadEvent event) {

		file = event.getFile();
		if (file != null) {
			uploadFile = new FileBean(file.getFileName(), file.getContentType(), file.getContents());
		}

		if (file == null || uploadFile == null) {
			messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "Error saving file",
					"The close file should be selected", Calendar.getInstance().getTime()));
			return;
		}
	}

	public void cancel() {
		uploadFile = null;
		file = null;
	}

	public void save() {

		if (uploadFile == null) {
			messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "Error saving file",
					"The file should be selected", Calendar.getInstance().getTime()));
			return;
		}

		if (newTemplate.getShipperGroupId() == null || newTemplate.getShipperGroupId() == "") {
			messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "Error selecting shipper",
					"Shipper should be selected", Calendar.getInstance().getTime()));
			return;
		}

		if (newTemplate.getContractId() == null || newTemplate.getContractId() == "") {
			messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "Error selecting contract",
					"Contract should be selected", Calendar.getInstance().getTime()));
			return;
		}

		if (newTemplate.getOperation_desc() == null || newTemplate.getOperation_desc() == "") {
			messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "Error selecting operation",
					"Operation should be selected", Calendar.getInstance().getTime()));
			return;
		}

		int insert = -1;
		try {
			newTemplate.setDocument_name(file.getFileName());
			newTemplate.setBinary_data(file.getContents());
			insert = service.insertTemplateShipper(newTemplate);
		} catch (Exception e) {
			log.catching(e);
			// we assign the return message
		}

		if (insert != 0) {
			messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "Error inserting",
					"Error inserting data ", Calendar.getInstance().getTime()));
			return;
		}
		// We return to search
		onSearch();

		// clean the form new after save
		newTemplate = new UploadNomTemplateShipperBean();

		uploadFile = null;
		file = null;
		messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.INFO, "New Template",
				"New template created", Calendar.getInstance().getTime()));
	}

	public StreamedContent getTemplate(UploadNomTemplateShipperBean bean) {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");
		String summaryMsg = msgs.getString("msg_template_shipper");

		StreamedContent templateFile = null;
		try {
			templateFile = service.getFile(bean.getIdn_nom_template_contract());

			if (templateFile == null) {
				String infoMsg = msgs.getString("daily_nomination_no_template");
				getMessages().addMessage(Constants.head_menu[4],
						new MessageBean(Constants.INFO, summaryMsg, infoMsg, Calendar.getInstance().getTime()));
				log.info(infoMsg);
			}
		} catch (Exception e) {
			String errorMsg = msgs.getString("download_error") + ".";
			getMessages().addMessage(Constants.head_menu[4],
					new MessageBean(Constants.ERROR, summaryMsg, errorMsg, Calendar.getInstance().getTime()));
			log.error(e.getMessage(), e);
		}

		return templateFile;
	}

	public void saveTemplateEditShipper() {
		int update = -1;
		try {
			if (file != null) {
				editTemplateShipper.setDocument_name(file.getFileName());
				editTemplateShipper.setBinary_data(file.getContents());
			}
			update = service.updateTemplateShipper(editTemplateShipper);
		} catch (Exception e) {
			log.catching(e);
		}

		if (update != 0) {
			messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.ERROR, "Error updating",
					"Error updating data ", Calendar.getInstance().getTime()));
			return;
		}

		// We return to search
		onSearch();

		editTemplateShipper = new UploadNomTemplateShipperBean();

		uploadFile = null;
		file = null;
		messages.addMessage(Constants.head_menu[4], new MessageBean(Constants.INFO, "Edit Template",
				"Template updated successfully", Calendar.getInstance().getTime()));
	}

	public void calcelTemplateEditShipper() {
		uploadFile = null;
		file = null;
	}
}
