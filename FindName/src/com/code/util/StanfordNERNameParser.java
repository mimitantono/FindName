/**
 * Name: StandfordNERNameParser
 * @author mimitantono
 * Purpose: A variant of abstract name parser.
 * Version information: 07:19:50 pm
 * Date: 10 Jun, 2014
 * Copyright notice: 
 */
package com.code.util;

import java.util.List;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

/**
 * This class is using NER (Named Entity Recognition) CRF implementation from
 * Stanford.<br/>
 * For more information: http://nlp.stanford.edu/software/CRF-NER.shtml<br/>
 * It is claimed to be more accurate than HMM but consumes more processing time.<br/>
 * 
 * @author mimitantono
 * 
 */
public class StanfordNERNameParser extends AbstractNameParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Tag for text classified as "name"
	 */
	private static String NAME_TAG = "PERSON";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.code.util.AbstractNameParser#identifyNames()
	 */
	@Override
	public void identifyNames() throws Exception {
		String sentences[] = getHtmlPage().getPlainContent().toString()
				.split("\\r?\\n");
		for (int i = 0; i < sentences.length; i++) {
			analyzeSentence(sentences[i].concat(" ."));
		}
	}

	private void analyzeSentence(String sentence) {
		String previousTag = "";
		String tag = "";
		StringBuffer identifiedName = new StringBuffer();
		for (List<CoreLabel> listCl : ClassifierFactory.getClassifier()
				.classify(sentence)) {
			for (CoreLabel cl : listCl) {
				tag = cl.get(CoreAnnotations.AnswerAnnotation.class);
				if (tag.equals(NAME_TAG)) {
					// Accumulate first name, middle name, last name of the same
					// person
					identifiedName.append(" ").append(cl.toString());
				}
				/**
				 * A full name was collected. Identified by comparing previous
				 * word's tag vs current word's tag.
				 */
				if (previousTag.equals(NAME_TAG) && !tag.equals(NAME_TAG)) {
					String add = identifiedName.toString();
					add = add.trim();
					identifiedName = new StringBuffer();
					/**
					 * Before adding to list, verify that: 1. The list does not
					 * already contain this name. 2. This name is not blank. 3.
					 * This name is a full name.
					 */
					if (!getNames().contains(add) && add.length() > 0
							&& add.split(" ").length > 1) {
						getNames().add(add);
					}
				}
				previousTag = tag;
			}
		}
	}

}
