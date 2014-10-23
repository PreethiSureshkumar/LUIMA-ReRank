package util;
import weka.core.Instance;

public interface FeaturesExtractor<E> {
  public abstract Instance getFeatures(E obj);
}
