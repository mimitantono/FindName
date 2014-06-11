/**
 * Name: AbstractNameParser
 * @author mimitantono
 * Purpose: Abstract class for parser. 
 * Version information: 07:18:01 pm
 * Date: 10 Jun, 2014
 * Copyright notice: 
 */
package com.code.util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is an abstract class of text parser.<br/>
 * To write another method of name parser, kindly extend this class and
 * implements the method "identifyNames".
 * 
 * @author mimitantono
 * 
 */
public abstract class AbstractNameParser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HtmlPage htmlPage;

	private ArrayList<String> names;

	/**
	 * This is the method where the method for identifying names should be
	 * implemented on child class to fill in the "names" property.
	 * 
	 * @throws Exception
	 */
	public abstract void identifyNames() throws Exception;

	/**
	 * @return the names
	 */
	public ArrayList<String> getNames() {
		return names;
	}

	/**
	 * @param names
	 *            the names to set
	 */
	public void setNames(ArrayList<String> names) {
		this.names = names;
	}

	/**
	 * @return the htmlPage
	 */
	public HtmlPage getHtmlPage() {
		return htmlPage;
	}

	/**
	 * @param htmlPage
	 *            the htmlPage to set
	 */
	public void setHtmlPage(HtmlPage htmlPage) {
		this.htmlPage = htmlPage;
		names = new ArrayList<String>();
	}
}
