package rerankerFeatureExtractor;

import rerankPipeline.Parameters;

public class ReRankingFeatureExtractor {

	public void performFeatureExtraction() throws ClassNotFoundException{
		String[] FeatureExtractors = Parameters.FeaturesToBeExtracted.split(";");
		for(int i = 0; i<FeatureExtractors.length; i++){
			Class fe = Class.forName("rerankerFeatureExtractor."+FeatureExtractors[i]); 
		    
		}
	}
}
