/**
 * Name: ParserTest
 * @author mimitantono
 * Purpose: Test cases for Standford NER parser. Look at testdata folder.
 * Version information: 07:22:12 pm
 * Date: 10 Jun, 2014
 * Copyright notice: 
 */
package com.code.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.code.util.FileUtil;
import com.code.util.HtmlPage;
import com.code.util.StanfordNERNameParser;

public class ParserTest extends TestCase {
	private Logger log = Logger.getLogger(ParserTest.class.getName());

	public void testHtmlParser() throws Exception {
		for (int i = 1; i <= 5; i++) {
			runTestData(i);
		}
	}

	public void runTestData(int index) throws Exception {
		HtmlPage htmlPage = new HtmlPage();
		htmlPage.setHtmlContent(FileUtil.readFromFile("testdata/data" + index
				+ ".html"));
		StanfordNERNameParser parser = new StanfordNERNameParser();
		parser.setHtmlPage(htmlPage);
		parser.identifyNames();
		ArrayList<String> names = parser.getNames();
		assertNotNull(names);
		assertTrue(names.size() > 0);
		String answers = FileUtil.readFromFile(
				"testdata/data" + index + ".oracle.txt").toString();
		String[] answer = answers.split("\\r?\\n");
		log.info("Test Data #" + index + " identified " + names.size()
				+ " names, expected " + answer.length);
		for (int i = 0; i < answer.length; i++) {
			if (!names.contains(answer[i])) {
				log.warn("Missed out name: " + answer[i]);
			} else {
				names.remove(answer[i]);
			}
		}
		for (String wrongName : names) {
			log.warn("Unexpected name: " + wrongName);
		}
	}
}
