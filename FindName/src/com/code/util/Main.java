/**
 * Name: Main
 * @author mimitantono
 * Purpose: 
 * Version information: 8:06:59 pm
 * Date: 11 Jun, 2014
 * Copyright notice: 
 */
package com.code.util;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * @author mimitantono
 * 
 */
public class Main {
	private static Logger log = Logger.getLogger(Main.class.getName());

	/**
	 * Simple CLI for testing this application. Currently only support 1 URL per
	 * runtime.
	 * 
	 * @param args one URL string
	 */
	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			printUsage();
		} else {
			HtmlPage htmlPage = new HtmlPage(args[0]);
			StanfordNERNameParser nameParser = new StanfordNERNameParser();
			nameParser.setHtmlPage(htmlPage);
			try {
				nameParser.identifyNames();
				ArrayList<String> names = nameParser.getNames();
				
				//Printing out names to the CLI screen
				System.out.println("\nFound " + names.size() + " names:");
				int index = 0;
				for (String name : names) {
					index++;
					System.out.println("\t" + index + "\t: " + name);
				}
			} catch (Exception e) {
				System.out.println("Encountered error : " + e.getMessage());
				System.out.println("Please check your URL.");
				log.error(Trace.getStackTrace(e));
			}
		}
	}

	/**
	 * Print this message when wrong format was submitted
	 */
	private static void printUsage() {
		System.out.println("Usage:");
		System.out.println("java -jar findName.jar [URL]");
	}
}
