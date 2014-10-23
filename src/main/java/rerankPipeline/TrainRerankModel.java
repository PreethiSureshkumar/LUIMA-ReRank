package rerankPipeline;

import java.io.BufferedReader;
import java.io.FileReader;
import rerankPipeline.letor.PairwiseLeToR;
import rerankerFeatureExtractor.ReRankingFeatureExtractor;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;

public class TrainRerankModel {
  public static void main(String[] args) throws Exception {

    BufferedReader br = new BufferedReader(new FileReader(Parameters.rankTrainFile));
    Instances instances = new Instances(br);
    instances.setClassIndex(instances.numAttributes() - 1);
    br.close();
    
    Classifier c = new LinearRegression();
    PairwiseLeToR p =  PairwiseLeToR.getInstance(c);
    ReRankingFeatureExtractor rr = new ReRankingFeatureExtractor();
    
    p.trainModel(instances);
    p.saveModel(Parameters.rankModelFile);
  }
}
