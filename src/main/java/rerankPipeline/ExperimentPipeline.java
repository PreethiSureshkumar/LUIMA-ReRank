package rerankPipeline;

import rerankerFeatureExtractor.ReRankingFeatureExtractor;
import Predictors.WekaLRPredictor;
import Predictors.WekaNaiveBayesPredictor;
import Predictors.WekaPredictor;

/**
 * User: Ran Chen <ranc@cs.cmu.edu> Preethi S. and Chen Wang
 * Date: 10/13/14
 * Time: 8:18 PM
 */
public class ExperimentPipeline {
    public static void main(String[] args) throws Exception {
    	
    	//ReRanking Feature Extractor
    	//ReRankingFeatureExtractor rrfe = new ReRankingFeatureExtractor();
    	//rrfe.performFeatureExtraction();
    	// Learning to Rank
        WekaPredictor nb = new WekaNaiveBayesPredictor();
        nb.loadTrainData(Parameters.trainFile);
        nb.loadTestData(Parameters.testFile);
        nb.stringToVector();
        //nb.loadModel(Parameters.modelFile);
        //nb.trainAndPrintResultInDetail();
        nb.trainAndPrintResultInDetail();
        nb.saveModel(Parameters.modelFile);
        //nb.crossValidationAndPrintResult(Parameters.folds);
        
        //Evaluator
    }
}
