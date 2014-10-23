package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import letor.PairwiseLeToR;
import featureExtractor.InstanceExtractor;
import featureExtractor.ConfigurableInstanceExtractor;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;

/**
 * User: Ran Chen <ranc@cs.cmu.edu> Preethi S. and Chen Wang
 * Date: 10/13/14
 * Time: 8:18 PM
 */
public class ExperimentPipeline {
    public static void main(String[] args) throws Exception {

      List<String> resultLines = new ArrayList<String>();
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Parameters.searchResult)));
      String line = br.readLine();
      while (line != null) {
        resultLines.add(line);
        line = br.readLine();
      }
      br.close();
      
    	//ReRanking Feature Extractor
    	InstanceExtractor<String> rrfe = new ConfigurableInstanceExtractor();
    	Classifier c = new LinearRegression();
    	PairwiseLeToR p =  PairwiseLeToR.getInstance(c);
    	p.loadModel(Parameters.rankModelFile);
    	List<Object> reranked = p.rank(resultLines, rrfe);
    	for (Object ranked: reranked) {
    	  System.out.println(ranked);
    	}
    	
    }
}
