package Search;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Ran Chen <ranc@cs.cmu.edu>
 * Date: 10/2/14
 * Time: 11:47 AM
 */
public class ResultList {
    Map<String, Result> resultMap = new HashMap<String, Result>();

    public void add(String query, Result result) {
        resultMap.put(query, result);
    }
}
