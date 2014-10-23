package letor;

import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;

public class PointwiseLeToR extends Ranker {
  
  private PointwiseLeToR () {
  }
  
  public static PointwiseLeToR getInstance(Classifier c){
    if (c == null) {
      throw new IllegalArgumentException();
    }
    PointwiseLeToR p = new PointwiseLeToR();
    p.classifier = c;
    return p;
  }
}
