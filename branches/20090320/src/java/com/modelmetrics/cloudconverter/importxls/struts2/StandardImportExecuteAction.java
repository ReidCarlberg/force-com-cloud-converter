package com.modelmetrics.cloudconverter.importxls.struts2;

import com.modelmetrics.cloudconverter.engine.ObjectDeleteHandler;
import com.modelmetrics.cloudconverter.importxls.services.SalesforceService;
import com.modelmetrics.cloudconverter.importxls.services.SalesforceServiceFactory;
import com.modelmetrics.cloudconverter.util.OperationStatusSubscriberLifoImpl;

public class StandardImportExecuteAction extends AbstractUploadContextAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1198350407323092698L;

	public String execute() throws Exception {

		if (this.getSalesforceSessionContext().getSalesforceSession() == null) {
			this.getUploadContext().setLastException(new RuntimeException("Missing Salesforce Session.  (This is sometimes an issue when your browser blocks cookies.)"));
			return ERROR;
		}

		// subscribe to the updates
		this.getUploadContext().setStatusSubscriber(
				new OperationStatusSubscriberLifoImpl());

		// instantiate the salesforce service
		SalesforceService salesforceService = new SalesforceServiceFactory()
				.build(this.getSalesforceSessionContext()
						.getSalesforceSession());

		// cleanup the target org first -- delete needs to be done here due to
		// the potential for running into related objects.
		new ObjectDeleteHandler().execute(this.getSalesforceSessionContext()
				.getSalesforceSession(), this.getUploadContext()
				.getCloudConverterObjects(), this.getUploadContext()
				.getStatusSubscriber());

		// giddyup
		try {
			salesforceService.execute(this.getUploadContext());
		} catch (Exception e) {
			this.getUploadContext().setLastException(e);
			if (e instanceof IndexOutOfBoundsException) {
				this.getUploadContext().setMessage("This message is usually caused by an Excel document that is not properly formatted.");
			}
			return ERROR;
		}

		// done
		return SUCCESS;
	}
}
