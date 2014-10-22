package Search;
/**
 * User: Ran Chen <ranc@cs.cmu.edu> Date: 10/1/14 Time: 11:54 PM
 */
public class ResultCalculator {

	public double getPrecision(ResultList trueResultList, Result predictResult) {
		Result trueResult = new Result();
		for (String query : trueResultList.resultMap.keySet()) {
			if (predictResult.query.equals(query)) {
				trueResult = trueResultList.resultMap.get(query);
				break;
			}
		}
		int totalCount = predictResult.results.size();
		int correctCount = 0;

		for (String id : predictResult.getIdList()) {
			if (trueResult.containsId(id)) {
				correctCount++;
			}
		}

		double precision = correctCount * 1.0 / totalCount;

		System.out.println("Precision: " + precision);

		return precision;

	}

	public double getRecall(ResultList trueResultList, Result predictResult) {
		Result trueResult = new Result();
		for (String query : trueResultList.resultMap.keySet()) {
			if (predictResult.query.equals(query)) {
				trueResult = trueResultList.resultMap.get(query);
				break;
			}
		}
		int totalCorrectCount = trueResult.results.size();
		int correctCount = 0;

		for (String id : predictResult.getIdList()) {
			if (trueResult.containsId(id)) {
				correctCount++;
			}
		}

		double recall = correctCount * 1.0 / totalCorrectCount;

		System.out.println("Recall: " + recall);

		return recall;

	}

	public double getNDCG(ResultList trueResultList, Result predictResult) {
		Result trueResult = new Result();
		for (String query : trueResultList.resultMap.keySet()) {
			if (predictResult.query.equals(query)) {
				trueResult = trueResultList.resultMap.get(query);
				break;
			}
		}
		
		double dcg = 0;
		int index = 1;
		for (String id : predictResult.getIdList()) {
			if (trueResult.containsId(id)) {
				if (index > 1) {
					dcg += Integer.parseInt(trueResult.results.get(trueResult
							.getIdIndex(id)).relevant) / log(index, 2);
				}
			}
			index++;
		}

		double idcg = 0;
		int ideal_index = 1;
		for (String id : trueResult.getIdList()) {
			if (ideal_index > 1) {
				idcg += Integer.parseInt(trueResult.results.get(trueResult
						.getIdIndex(id)).relevant) / log(ideal_index, 2);
			}
			ideal_index++;
		}
        
		System.out.println("NDCG: " + (dcg/idcg));
		return dcg/idcg;
	}

	static int log(int x, int base) {
		return (int) (Math.log(x) / Math.log(base));
	}
}
