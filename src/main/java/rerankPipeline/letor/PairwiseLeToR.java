package rerankPipeline.letor;

import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;

public class PairwiseLeToR extends Ranker {
  
  private PairwiseLeToR () {
    
  }
  
  public static PairwiseLeToR getInstance(Classifier c){
    if (c == null) {
      throw new IllegalArgumentException();
    }
    PairwiseLeToR p = new PairwiseLeToR();
    p.classifier = c;
    return p;
  }
  
  private Instances alterTrainData(Instances instances) {
    Instances train = new Instances(instances, 0);
    int numInstances = instances.numInstances();
    int numAttributes = instances.numAttributes();
    for (int i = 0; i < numInstances; ++i) {
      for (int j = 0; j < numInstances; ++j) {
        if (i == j) continue;
        Instance i1 = instances.instance(i);
        Instance i2 = instances.instance(j);
        Instance positive;
        Instance negative;
        if (i1.value(numAttributes - 1) != i2.value(numAttributes - 1)) {
          int better = i1.value(numAttributes - 1) > i2.value(numAttributes - 1) ? 1 : -1; 
          if (i1 instanceof DenseInstance) {
            positive = new DenseInstance(numAttributes);
            negative = new DenseInstance(numAttributes);
            for (int k = 0; k < numAttributes - 1; ++k) {
              positive.setValue(k, i1.value(k) - i2.value(k));
              negative.setValue(k, i1.value(k) - i2.value(k));
            }
          } else {
            positive = new SparseInstance(numAttributes);
            negative = new SparseInstance(numAttributes);
            int k1 = 0; 
            int k2 = 0;
            while (k1 < positive.numValues() && k2 < positive.numValues()) {
              int kk1 = positive.index(k1); // real index
              int kk2 = negative.index(k2);
              int smaller;
              if (kk1 == kk2) {
                k1 += 1;
                k2 += 1;
                smaller = kk1;
              } else if (kk1 < kk2) {
                k1 += 1;
                smaller = k1;
              } else{
                k2 += 1;
                smaller = k2;
              }
              positive.setValue(smaller, i1.value(smaller) - i2.value(smaller));
              negative.setValue(smaller, i1.value(smaller) - i2.value(smaller));
            }
          }
          positive.setValue(instances.numAttributes() - 1, better);
          negative.setValue(instances.numAttributes() - 1, 1 - better);
          train.add(positive);
          train.add(negative);
        }
      }
    }
    return train;
  }
  
  @Override
  public void trainModel(Instances instances) throws Exception {
    Instances train = alterTrainData(instances);
    super.trainModel(train);
  }
}
