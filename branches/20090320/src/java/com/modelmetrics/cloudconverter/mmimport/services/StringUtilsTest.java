package com.modelmetrics.cloudconverter.mmimport.services;

import junit.framework.TestCase;


public class StringUtilsTest extends TestCase {

	public void testApplyConstraints() {

		String testOne = "hwkh&%#1212_-232AAAA";

		String testOneApplied = StringUtils.applyConstraints(testOne);

		assertFalse(testOneApplied.contains("#"));

	}
	

}
