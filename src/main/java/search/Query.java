package search;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Ran Chen <ranc@cs.cmu.edu>
 * Date: 10/8/14
 * Time: 9:31 PM
 */
public class Query {
    Map<String, String> field = new HashMap<String, String>();

    public static Query parse(String queryString) {
        Query query = new Query();
        String[] strings = queryString.split(";");
        for (String string : strings) {
            String[] content = string.split(":");
            if (content.length == 0) continue;
            query.field.put(content[0].trim(), content[1].trim());
        }
        return query;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : field.keySet()) {
            String value = field.get(key);
            sb.append(key + ":" + value + " ");
        }

        return sb.toString();
    }
}
