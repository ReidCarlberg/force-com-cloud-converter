package com.modelmetrics.cloudconverter.mmimport.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.modelmetrics.cloudconverter.mmimport.services.SingleFieldOptionsBean;
import com.modelmetrics.cloudconverter.mmimport.services.SheetOptionsBean;
import com.modelmetrics.cloudconverter.mmimport.services.SalesforceService;
import com.modelmetrics.cloudconverter.mmimport.services.StringUtils;
import com.modelmetrics.cloudconverter.mmimport.services.ExcelWorksheetWrapperBean;

public class UploadActionCompositeConfirm extends AbstractUploadContextAware
		implements ServletRequestAware {

	private static final long serialVersionUID = 5950249609611643673L;


	private static final Logger log = Logger
	.getLogger(UploadActionCompositeConfirm.class);

	
	private Long selectedOption;

	private SalesforceService salesforceService;

	private Map<Long, String> optionsList;

	private List<String> sheets;

	private HttpServletRequest request;
	
	private List<SheetOptionsBean> advanceOptionsWrapperBeans;



	public List<SheetOptionsBean> getAdvanceOptionsWrapperBeans() {
		return advanceOptionsWrapperBeans;
	}

	public void setAdvanceOptionsWrapperBeans(
			List<SheetOptionsBean> advanceOptionsWrapperBeans) {
		this.advanceOptionsWrapperBeans = advanceOptionsWrapperBeans;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String execute() throws Exception {

		// validate radiobutton
		if (selectedOption == null) {
			addActionMessage("Please select an option");
			// set radio button options for next page
			optionsList = StringUtils.getOptions();
			return INPUT;
		}

		if (NO.equals(selectedOption)) {

//			//go back to  options 1 page. So prepare information again
//			List<ExcelWorksheetWrapperBean> beans = this.getUploadContext()
//					.getWrapperBeans();
//
//			// prepare information on a different structure for view
//			advanceOptionsWrapperBeans = new ArrayList<SheetOptionsBean>();
//			for (ExcelWorksheetWrapperBean wrapperBean : beans) {
//				List<SingleFieldOptionsBean> advanceOptionsBeans = transformFromWrapperBean(wrapperBean);
//				SheetOptionsBean aux = new SheetOptionsBean();
//				aux.setSheetName(wrapperBean.getSheetName());
//				aux.setAdvanceOptionsBeans(advanceOptionsBeans);
//				advanceOptionsWrapperBeans.add(aux);
//			}
//
//			// set new structure in session
//			this.getUploadContext().setAdvanceOptionsWrapperBeans(
//					advanceOptionsWrapperBeans);

			
			return "startOver";
		} else {
			//go ahead and create objects
			
//			sheets = salesforceService.checkObject(this.getUploadContext());
//			if (!sheets.isEmpty()) {
//				request.setAttribute("backPage", "backToBranch");
//				request.setAttribute("sheets", sheets);
//				return "override";
//			} else {
//				// import directly
//				log.info("Generating Salesforce object now...");
//				// bean.setOverride(Boolean.FALSE);
//				salesforceService.execute(this.getUploadContext());
				return "view";
//			}
		}
	}
	
	private List<SingleFieldOptionsBean> transformFromWrapperBean(ExcelWorksheetWrapperBean bean) {
		List<SingleFieldOptionsBean> list = new ArrayList<SingleFieldOptionsBean>();

		for (int i = 0; i < bean.getNames().size(); i++) {
			SingleFieldOptionsBean advanceBean = new SingleFieldOptionsBean();
			advanceBean.setName(bean.getNames().get(i));
			advanceBean.setLabel(bean.getLabels().get(i));
			advanceBean.setType(bean.getTypes().get(i));
			List<Object> data = new ArrayList<Object>();
			for (List<Object> aux : bean.getData()) {
				data.add(aux.get(i));
			}
			advanceBean.setData(data);
			list.add(advanceBean);
		}
		return list;
	}

	public Long getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(Long selectedOption) {
		this.selectedOption = selectedOption;
	}

	public SalesforceService getSalesforceService() {
		return salesforceService;
	}

	public void setSalesforceService(SalesforceService salesforceService) {
		this.salesforceService = salesforceService;
	}

	public Map<Long, String> getOptionsList() {
		return optionsList;
	}

	public void setOptionsList(Map<Long, String> optionsList) {
		this.optionsList = optionsList;
	}

	public List<String> getSheets() {
		return sheets;
	}

	public void setSheets(List<String> sheets) {
		this.sheets = sheets;
	}

}
