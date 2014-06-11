/**
 * Name: Suite
 * @author mimitantono
 * Purpose: 
 * Version information: 07:20:29 pm
 * Date: 10 Jun, 2014
 * Copyright notice: 
 */
package com.code.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class Suite extends TestCase {
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new TestSuite(ParserTest.class));
		return suite;
	}

	public static void main(String args[]) {
		TestRunner.run(suite());
	}
}