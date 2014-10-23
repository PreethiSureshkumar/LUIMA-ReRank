package featureExtractor;
import weka.core.Instance;

public interface InstanceExtractor<E> {
  public abstract Instance getFeatures(E obj);
}
