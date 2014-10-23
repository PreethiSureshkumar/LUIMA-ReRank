package rerankPipeline;

/**
 * Configuration file to perform feature extraction and re ranking
 * Date: 10/13/14
 * Time: 8:08 PM
 */
public class Parameters {
	public static String searchQuery = "input/search/queries";
	public static String searchResult = "input/search/search_output.txt";
	public static String searchDocument = "input/search/documents/roper.xml";
	public static String FeaturesToBeExtracted = "LuceneScoreExtractor;VectorSpaceScoreExtractor";
	public static String trainFile = "input/test-abell.arff;input/test-edwards.arff;input/test-golkiewicz.arff";
	public static String testFile = "input/output.arff";
	public static String inputDirectory = "";
	public static String modelFile = "model/NaiveBayesUpdateable.model";
	public static Integer folds = 2;
	public static String rankTrainFile = "input/traintrain.arff";
	public static String rankModelFile = "model/LRRerankModel.model";
}
