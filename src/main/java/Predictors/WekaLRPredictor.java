package Predictors;

import weka.classifiers.functions.LinearRegression;

/**
 * User: Ran Chen <ranc@cs.cmu.edu>
 * Date: 10/14/14
 * Time: 12:10 PM
 */
public class WekaLRPredictor extends WekaPredictor {
    public WekaLRPredictor() {
        classifier = new LinearRegression();
    }

}
