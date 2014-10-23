package main;

import java.io.BufferedReader;
import java.io.FileReader;

import letor.PairwiseLeToR;
import featureExtractor.ConfigurableInstanceExtractor;
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
    ConfigurableInstanceExtractor rr = new ConfigurableInstanceExtractor();
    
    p.trainModel(instances);
    p.saveModel(Parameters.rankModelFile);
  }
}
