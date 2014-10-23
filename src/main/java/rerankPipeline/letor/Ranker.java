package rerankPipeline.letor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import edu.stanford.nlp.util.FixedPrioritiesPriorityQueue;
import util.FeaturesExtractor;
import weka.classifiers.Classifier;
import weka.classifiers.misc.SerializedClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

public abstract class Ranker {

  Classifier classifier;
  
  public void saveModel(String fileName) throws Exception {
    SerializationHelper.write(fileName, classifier);
  }
  
  public void loadModel(String fileName) throws Exception {
    SerializedClassifier classifier = new SerializedClassifier();
    classifier.setModelFile(new File(fileName));
    this.classifier = classifier.getCurrentModel();
  }
  
  public void trainModel(Instances instances) throws Exception {
    this.classifier.buildClassifier(instances);
  }
  
  public List<Double> predict(Instances instances) throws Exception {
    List<Double> ret = new ArrayList<Double>();
    for (int i = 0; i < instances.numInstances(); ++i) {
      ret.add(classifier.classifyInstance(instances.instance(i)));
    }
    return ret;
  }
  
  public double predict(Instance instance) throws Exception {
    return classifier.classifyInstance(instance);
  }
  
  public List<Object> rank(List<? extends Object> obj, FeaturesExtractor fe) throws Exception {
    FixedPrioritiesPriorityQueue queue = new FixedPrioritiesPriorityQueue();
    for (Object o: obj) {
      Instance i = fe.getFeatures(o);
      queue.add(o, predict(i));
    }
    return queue.toSortedList();
  }
}
