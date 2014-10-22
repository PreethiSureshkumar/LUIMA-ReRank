package Predictors;

import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.classifiers.bayes.NaiveBayesMultinomialUpdateable;
import weka.classifiers.bayes.*;

/**
 * User: Ran Chen <ranc@cs.cmu.edu>
 * Date: 10/13/14
 * Time: 8:49 PM
 */
public class WekaNaiveBayesPredictor extends WekaPredictor {
    public WekaNaiveBayesPredictor() {
        classifier = new NaiveBayesUpdateable();
    }
}
