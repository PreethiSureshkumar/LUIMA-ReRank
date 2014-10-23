package featureExtractor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import main.Parameters;
import weka.core.DenseInstance;
import weka.core.Instance;

public class ConfigurableInstanceExtractor implements InstanceExtractor<String> {

	public Instance getFeatures(String line) {
		String[] FeatureExtractors = Parameters.FeaturesToBeExtracted.split(";");
		Instance instance = new DenseInstance(FeatureExtractors.length + 1);
    for (int i = 0; i < FeatureExtractors.length; i++) {
      try {
        Class fe = Class.forName("rerankerFeatureExtractor."
            + FeatureExtractors[i].trim());
        Method methodToBeInvoked = fe.getMethod("getFeature", new Class[] { String.class });
        String val = (String) methodToBeInvoked.invoke(null, line);
        instance.setValue(i, Double.valueOf(val));
      } catch (Exception e){
        System.err.println(FeatureExtractors[i] + " got errors");
        e.printStackTrace();
        System.exit(1);
      }
		}
		return instance;
	}
}
