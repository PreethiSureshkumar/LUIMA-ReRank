package rerankPipeline;

/**
 * Configuration file to perform feature extraction and re ranking
 * Date: 10/13/14
 * Time: 8:08 PM
 */
public class Parameters {
	public static String searchResult = "input/predictResult.txt";
	public static String FeaturesToBeExtracted = "VectorSpaceScoreExtractor;LuceneScoreExtractor";
	public static String trainFile = "input/test-abell.arff;input/test-edwards.arff;input/test-golkiewicz.arff";
	public static String testFile = "input/output.arff";
	public static String inputDirectory = "";
	public static String modelFile = "model/NaiveBayesUpdateable.model";
	public static Integer folds = 2;
}
