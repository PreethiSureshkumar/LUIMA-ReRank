package featureExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import main.Parameters;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class VectorSpaceScoreExtractor {
	public static String getFeature(String line) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				Parameters.searchQuery));
		Map<String, Integer> queryVector = new HashMap<String, Integer>();
		String query = "";
		while ((query = br.readLine()) != null) {
			
			Matcher m = Pattern.compile("\\w+Mention\\b").matcher(query);
			while (m.find()) {
				queryVector.put(m.group(), 1);
			}
		}
		br.close();
		String[] docId = line.split("\\s+");
		Map<String, Integer> docVector = new HashMap<String, Integer>();
		try {
			File fXmlFile = new File(Parameters.searchDocument);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("doc");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					if (docId[1].trim().equals(
							eElement.getElementsByTagName("field").item(0)
									.getTextContent())) {
						String about = eElement.getElementsByTagName("field")
								.item(5).getTextContent();
						String[] aboutMentions = about.split(";");
						for(int i=0;i<aboutMentions.length; i++){
							docVector.put(aboutMentions[i].trim(), 1);
						}

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		double vsm = computeCosineSimilarity(queryVector, docVector);
		return ""+vsm;
	}

	/**
	 * Numerator = product of common words in the question and in the answer
	 * Denominator = product of square root of (frequency of words in question
	 * and frequency of words in the answer)
	 * 
	 * @param queryVector
	 * @param docVector
	 * @return
	 */
	private static double computeCosineSimilarity(Map<String, Integer> queryVector,
			Map<String, Integer> docVector) {
		double cosine_similarity = 0.0;
		double query = 0.0;
		double docum = 0.0;
		// TODO :: compute cosine similarity between two sentences
		Iterator it = queryVector.entrySet().iterator();
		double numerator = 0.0;
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			String key = (String) pairs.getKey();
			query += ((Integer) queryVector.get(key)).intValue()
					* ((Integer) queryVector.get(key)).intValue();
			if (docVector.containsKey(key))
				numerator += ((Integer) docVector.get(key)).intValue()
						* ((Integer) queryVector.get(key)).intValue();
		}

		Iterator it1 = docVector.entrySet().iterator();
		while (it1.hasNext()) {
			Map.Entry pairs = (Map.Entry) it1.next();
			String key = (String) pairs.getKey();
			docum += ((Integer) docVector.get(key)).intValue()
					* ((Integer) docVector.get(key)).intValue();

		}

		cosine_similarity = 1.0 * numerator / Math.sqrt(query) / Math.sqrt(docum);
		return cosine_similarity;
	}

	/**
	 * The jaccard score is as simple as getting the frequency of common words
	 * in question and answer divided by the union of words in question and in
	 * the answer.
	 * 
	 * @param queryVector
	 * @param docVector
	 * @return
	 */
	private double computeJaccardCoefficient(Map<String, Integer> queryVector,
			Map<String, Integer> docVector) {
		double jaccard_Score = 0.0;
		double query = 0.0;
		double docum = 0.0;
		// TODO :: compute cosine similarity between two sentences
		Iterator it = queryVector.entrySet().iterator();
		double query_docum_common = 0.0;
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			String key = (String) pairs.getKey();
			query += ((Integer) queryVector.get(key)).intValue()
					* ((Integer) queryVector.get(key)).intValue();
			if (docVector.containsKey(key))
				query_docum_common += ((Integer) docVector.get(key)).intValue()
						* ((Integer) queryVector.get(key)).intValue();
		}
		Iterator it1 = docVector.entrySet().iterator();
		while (it1.hasNext()) {
			Map.Entry pairs = (Map.Entry) it1.next();
			String key = (String) pairs.getKey();
			docum += ((Integer) docVector.get(key)).intValue()
					* ((Integer) docVector.get(key)).intValue();

		}
		jaccard_Score = query_docum_common
				/ (Math.sqrt(query) + Math.sqrt(docum) - Math
						.sqrt(query_docum_common));
		return jaccard_Score;
	}

	/**
	 * The dice score is as simple as getting twice the frequency of common
	 * words in question and answer divided by the union of words in question
	 * and in the answer + the common words in question and answer.
	 * 
	 * @param queryVector
	 * @param docVector
	 * @return
	 */
	private double computeDiceCoefficient(Map<String, Integer> queryVector,
			Map<String, Integer> docVector) {
		double dice_Score = 0.0;
		double query = 0.0;
		double docum = 0.0;
		// TODO :: compute cosine similarity between two sentences
		Iterator it = queryVector.entrySet().iterator();
		double query_docum_common = 0.0;
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			String key = (String) pairs.getKey();
			query += ((Integer) queryVector.get(key)).intValue()
					* ((Integer) queryVector.get(key)).intValue();
			if (docVector.containsKey(key))
				query_docum_common += ((Integer) docVector.get(key)).intValue()
						* ((Integer) queryVector.get(key)).intValue();
		}
		Iterator it1 = docVector.entrySet().iterator();
		while (it1.hasNext()) {
			Map.Entry pairs = (Map.Entry) it1.next();
			String key = (String) pairs.getKey();
			docum += ((Integer) docVector.get(key)).intValue()
					* ((Integer) docVector.get(key)).intValue();

		}
		dice_Score = (2 * query_docum_common)
				/ (Math.sqrt(query) + Math.sqrt(docum));
		return dice_Score;
	}
}
