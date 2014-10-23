package rerankerFeatureExtractor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import rerankPipeline.Parameters;

public class ReRankingFeatureExtractor  {

	public void performFeatureExtraction() throws ClassNotFoundException,
			IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String[] FeatureExtractors = Parameters.FeaturesToBeExtracted
				.split(";");
		List<String[]> featureList = new ArrayList<String[]>();
		BufferedReader br = new BufferedReader(new FileReader(
				Parameters.searchResult));
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] features = new String[FeatureExtractors.length];
			for (int i = 0; i < FeatureExtractors.length; i++) {
				Class fe = Class.forName("rerankerFeatureExtractor."
						+ FeatureExtractors[i]);
				Method methodToBeInvoked = fe.getMethod("getFeature", new Class[]{String.class});
				features[i] = (String) methodToBeInvoked.invoke(null, line);
			}
			featureList.add(features);
		}
	}
}
