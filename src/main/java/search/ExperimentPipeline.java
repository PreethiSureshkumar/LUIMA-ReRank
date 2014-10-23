package search;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * User: Ran Chen <ranc@cs.cmu.edu>
 * Date: 10/1/14
 * Time: 10:15 PM
 */
public class ExperimentPipeline {
//    static String TrueResultFile = Parameters.TrueResultFile;
//    static String QueryFile = "input/queries";
//    static String InputDocumentDirectory = "input/documents/";
//    static Boolean LoadAllTrueResult = false;

    public static void main(String[] args) throws Exception {
        System.out.println("---Read Query From:" + Parameters.QueryFile);
        String rawQueryString = getQuery();
        Query query = Query.parse(rawQueryString);

        System.out.println("---Read True Result From:" + Parameters.TrueResultFile);
        Reader reader = new Reader();
        ResultList resultList = new ResultList();
        if (Parameters.LoadAllTrueResult) {
            resultList = reader.readAll(Parameters.TrueResultFile);
        }

        System.out.println("---Start Search, Query: " + query.toString());
        Searcher searcher = new Searcher();
        searcher.search(new String[]{"-query", query.toString(), "-index", Parameters.indexDirectory, "-paging", Parameters.maxResult});
        Result predictResult = searcher.result;

        System.out.println("---Start Calculate Result---");
        ResultCalculator resultCalculator = new ResultCalculator();
        resultCalculator.getPrecision(resultList, predictResult);
        resultCalculator.getRecall(resultList, predictResult);
        resultCalculator.getNDCG(resultList, predictResult);
    }

    static String getQuery() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Parameters.QueryFile)));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
