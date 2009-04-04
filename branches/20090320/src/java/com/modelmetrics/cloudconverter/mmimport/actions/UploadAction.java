package com.modelmetrics.cloudconverter.mmimport.actions;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.modelmetrics.cloudconverter.mmimport.services.FileService;
import com.modelmetrics.cloudconverter.mmimport.services.ParseException;
import com.modelmetrics.cloudconverter.mmimport.services.SalesforceService;
import com.modelmetrics.cloudconverter.mmimport.services.StringUtils;
import com.modelmetrics.cloudconverter.mmimport.services.ValueId;
import com.modelmetrics.cloudconverter.mmimport.services.WrapperBean;

public class UploadAction extends AbstractUploadContextAware {

	private static final Logger log = Logger.getLogger(UploadAction.class);

	private static final long serialVersionUID = 1760991341958287065L;

	private FileService fileService;

	private SalesforceService salesforceService;

	private File upload;

	private String uploadContentType;

	private String password;

	private String username;

	private Boolean override;

	private String uploadFileName;

	private List<ValueId> fieldTypes;

	/**
	 * initializes form input page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String init() throws Exception {

		return INPUT;
	}

	/**
	 * Uploads the XLS and transformes it to a WrapperBean object to be sent to
	 * view
	 * 
	 * @return
	 */
	public String advanceOptionsOne() {

		try {
			validateData();

			/*
			 * let's check the username and password now
			 */
			if (getActionMessages().isEmpty()) {
				try {
					this.getSalesforceSessionContext()
							.setSalesforceCredentials(this.getUsername(),
									this.getPassword());
				} catch (Exception e) {
					// this.getUploadContext().setLastException(e);
					addActionMessage("Could not initialize your Salesforce session.  You might need your security token.");
				}
			}

			/*
			 * failed?
			 */
			if (!getActionMessages().isEmpty()) {
				return INPUT;
			}

			salesforceService.setSalesforceSession(this
					.getSalesforceSessionContext().getSalesforceSession());

		
			this.getUploadContext().setWrapperBean(fileService.parseXLS(upload));

			log.info("File uploaded successfully");

			fieldTypes = StringUtils.getAllFieldTypes();
			return SUCCESS;
		} catch (ParseException e) {
			message = "There has been a problem uploading the file";
			log.error(message, e);
			addActionMessage(e.getMessage());
			// this.getUploadContext().setLastException(e);
			return ERROR;

		}
	}

	public String checkOverride() {
		try {
			WrapperBean bean = this.getUploadContext().getWrapperBean();
			boolean containsObject = salesforceService.checkObject(this.getUploadContext());
			if (containsObject) {
				return "override";
			} else {
				log.info("Generating Salesforce object now...");
				bean.setOverride(Boolean.FALSE);
				salesforceService.execute(this.getUploadContext());
			}
			this.getUploadContext().setWrapperBean(bean);
			return SUCCESS;
		} catch (Exception e) {
			message = "There has been a problem generating salesforce objects";
			log.error(message, e);
			addActionMessage(message);
			// this.getUploadContext().setLastException(e);
			return ERROR;
		}
	}

	

	public String override() {
		try {
			WrapperBean bean = this.getUploadContext().getWrapperBean();

			log.info("Generating Salesforce object now...");
			bean = this.getUploadContext().getWrapperBean();
			bean.setOverride(Boolean.TRUE);
			salesforceService.setSalesforceSession(this
					.getSalesforceSessionContext().getSalesforceSession());
			salesforceService.execute(this.getUploadContext());

			log.info("Object sent successfully");
			this.getUploadContext().setWrapperBean(bean);
			return SUCCESS;
		} catch (Exception e) {
			message = "There has been a problem generating salesforce objects";
			log.error(message, e);
			addActionMessage(message);
			// this.getUploadContext().setLastException(e);
			return ERROR;
		}
	}

	private void validateData() {
		if ("".equals(username)) {
			addActionMessage("Username is required");
		}
		if ("".equals(password)) {
			addActionMessage("Password is required");
		}
		if (upload == null) {
			addActionMessage("Please select a file");
		}

	}
	
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	public FileService getFileService() {
		return fileService;
	}

	public void setSalesforceService(SalesforceService salesforceService) {
		this.salesforceService = salesforceService;
	}

	public SalesforceService getSalesforceService() {
		return this.salesforceService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getOverride() {
		return override;
	}

	public void setOverride(Boolean override) {
		this.override = override;
	}

	public List<ValueId> getFieldTypes() {
		return fieldTypes;
	}

	public void setFieldTypes(List<ValueId> fieldTypes) {
		this.fieldTypes = fieldTypes;
	}

}
