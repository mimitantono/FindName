/**
 * Name: Trace
 * @author mimitantono
 * Purpose: Printing a stack trace into String so that it is log-able.
 * Version information: 7:55:21 pm
 * Date: 10 Jun, 2014
 * Copyright notice: 
 */
package com.code.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * @author mimi
 */
public class Trace {
	
	/**
	 * Get the stack trace string of an exception
	 * @param t exception to be logged
	 * @return the string of the exception's stack trace
	 */
	public static String getStackTrace(Throwable t)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }
}

