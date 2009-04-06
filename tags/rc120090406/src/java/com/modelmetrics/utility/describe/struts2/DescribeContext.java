package com.modelmetrics.utility.describe.struts2;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.sforce.soap.partner.DescribeSObjectResult;

public class DescribeContext {

	private Collection<String> objectTypes;
	
	private String[] types;

	private String target;

	private Map<String, DescribeSObjectResult> descriptions = new HashMap<String, DescribeSObjectResult>();
	
	private DescribeSObjectResult lastResult;

	private String lastMessage;
	
	public void initialize() {

		this.types = null;

		this.descriptions = new HashMap<String, DescribeSObjectResult>();
	}

	public String[] getTypes() {
		return types;
	}

	public void setTypes(String[] types) {
		this.types = types;
	}

	public Map<String, DescribeSObjectResult> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Map<String, DescribeSObjectResult> descriptions) {
		this.descriptions = descriptions;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public DescribeSObjectResult getLastResult() {
		return lastResult;
	}

	public void setLastResult(DescribeSObjectResult lastResult) {
		this.lastResult = lastResult;
	}

	public Collection<String> getObjectTypes()
	{
		objectTypes = Arrays.asList(types);
		return objectTypes;
	}

	public void setObjectTypes(Collection<String> objectTypes)
	{
		this.objectTypes = objectTypes;
	}

	public String getLastMessage() {
		return lastMessage;
	}
	
	public String getLastMessageWithReset() {
		String ret = lastMessage;
		lastMessage = null;
		return ret;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}



}
