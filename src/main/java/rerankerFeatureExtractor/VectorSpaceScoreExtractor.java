package rerankerFeatureExtractor;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rerankPipeline.Parameters;

public class VectorSpaceScoreExtractor {
	public static String getFeature(String line) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				Parameters.searchQuery));
		String query = "";
		while ((query = br.readLine()) != null) {
			Map<String, Integer> queryVector = new HashMap<String, Integer>();
			Matcher m = Pattern.compile("\\w+Mention\\b").matcher(query);
			while (m.find()) {
				queryVector.put(m.group(), 1);
			}
		}
		br.close();
		String[] docId = line.split("\\s([\\d\\.]+)");
		try {
			File file = new File(Parameters.searchDocument);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("doc");
			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element fstElmnt = (Element) fstNode;
					NodeList fstNmElmntLst = fstElmnt
							.getElementsByTagName("id");
					Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
					
					NodeList fstNm = fstNmElmnt.getChildNodes();
					System.out.println("First Name : "
							+ ((Node) fstNm.item(0)).getNodeValue());
					NodeList lstNmElmntLst = fstElmnt
							.getElementsByTagName("lastname");
					Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
					NodeList lstNm = lstNmElmnt.getChildNodes();
					System.out.println("Last Name : "
							+ ((Node) lstNm.item(0)).getNodeValue());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return line;
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
	private double computeCosineSimilarity(Map<String, Integer> queryVector,
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

		cosine_similarity = numerator / Math.sqrt(query) / Math.sqrt(docum);
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
