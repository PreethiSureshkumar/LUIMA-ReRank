package featureExtractor;

public class LuceneScoreExtractor {

	public static String getFeature(String line) { 
		String[] segments = line.split("\\s");
		return segments[0];
	}
}
