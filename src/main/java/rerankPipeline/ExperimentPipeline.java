package rerankPipeline;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import rerankPipeline.letor.PairwiseLeToR;
import rerankerFeatureExtractor.ReRankingFeatureExtractor;
import util.FeaturesExtractor;
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
    	FeaturesExtractor<String> rrfe = new ReRankingFeatureExtractor();
    	Classifier c = new LinearRegression();
    	PairwiseLeToR p =  PairwiseLeToR.getInstance(c);
    	p.loadModel(Parameters.rankModelFile);
    	List<Object> reranked = p.rank(resultLines, rrfe);
    	for (Object ranked: reranked) {
    	  System.out.println(ranked);
    	}
    	
    	// Learning to Rank
        /*WekaPredictor nb = new WekaNaiveBayesPredictor();
        nb.loadTrainData(Parameters.trainFile);
        nb.loadTestData(Parameters.testFile);
        nb.stringToVector();
        //nb.loadModel(Parameters.modelFile);
        //nb.trainAndPrintResultInDetail();
        nb.trainAndPrintResultInDetail();
        nb.saveModel(Parameters.modelFile);*/
        //nb.crossValidationAndPrintResult(Parameters.folds);
        
        //Evaluator
    }
}
