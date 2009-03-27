package com.mmimport.test.services;

import junit.framework.TestCase;

import com.mmimport.utils.StringUtils;

public class StringUtilsTest extends TestCase {

	public void testApplyConstraints() {

		String testOne = "hwkh&%#1212_-232AAAA";

		String testOneApplied = StringUtils.applyConstraints(testOne);

		assertFalse(testOneApplied.contains("#"));

	}
}
