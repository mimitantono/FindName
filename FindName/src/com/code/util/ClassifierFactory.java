/**
 * Name: ClassifierFactory
 * @author mimitantono
 * Purpose: Serve as a singleton classifier
 * Version information: 10:42:02 pm
 * Date: 10 Jun, 2014
 * Copyright notice: 
 */
package com.code.util;

import java.io.Serializable;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

/**
 * This class serves as the factory for the classifier singleton object.<br/>
 * CRFClassifier is initialized with a standard model trained data trained on
 * just the CoNLL 2003 (http://www.cnts.ua.ac.be/conll2003/ner/), able to
 * recognize: 3 classes of categories: Location, Person, Organization. <br/>
 * For languages other than English, a different model would give more accurate
 * result.
 * 
 * @author mimitantono
 * 
 */
public class ClassifierFactory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String serializedClassifier = "lib/english.all.3class.distsim.crf.ser.gz";

	private static CRFClassifier<CoreLabel> classifier;

	/**
	 * @return a singleton classifier
	 */
	public static final CRFClassifier<CoreLabel> getClassifier() {
		if (classifier == null) {
			classifier = CRFClassifier
					.getClassifierNoExceptions(serializedClassifier);
		}
		return classifier;
	}

}
