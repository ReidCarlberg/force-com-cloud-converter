/*
The MIT License

Copyright (c) 2008, 2009 Model Metrics, Inc.

http://ModelMetrics.com
http://ModelMetrics.com/authors/rcarlberg

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package com.modelmetrics.cloudconverter.describe.struts2;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.modelmetrics.cloudconverter.describe.DescribeExcelBuilderDelegateMetadataImpl;
import com.modelmetrics.cloudconverter.util.OperationStatusSubscriberLifoImpl;
import com.modelmetrics.common.poi.ExcelSupport;
import com.opensymphony.xwork2.Action;

public class DescribeAllInExcelAction extends DescribeAction {

//	private HSSFWorkbook workbook;

	public String execute() throws Exception {

		this.getDescribeContext().setStatusSubscriber(new OperationStatusSubscriberLifoImpl());
		
		this.getDescribeContext().getStatusSubscriber().publish("Beginning describe all.");

		
		ExcelSupport excelSupport = new ExcelSupport();

		HSSFWorkbook workbook = excelSupport.getWorkbook();

		DescribeExcelBuilderDelegateMetadataImpl delegate = new DescribeExcelBuilderDelegateMetadataImpl(excelSupport, workbook);

		for (String currentType: this.getDescribeContext().getTypes()) {

			this.setTarget(currentType);
			
			this.getDescribeContext().getStatusSubscriber().publish("Exporting " + currentType);

			super.execute();
			
			delegate.handleBuild(this.getDisplayableFields(),  this.getTarget());


		}

		this.getDescribeContext().getStatusSubscriber().publish("Complete.");

		

		this.getDescribeContext().setWorkbook(workbook);
		
		

		return Action.SUCCESS;
	}



}
