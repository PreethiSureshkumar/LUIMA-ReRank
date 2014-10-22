package Search;
import java.util.ArrayList;

/**
 * User: Ran Chen <ranc@cs.cmu.edu>
 * Date: 10/1/14
 * Time: 10:56 PM
 */
public class Result {
    Result() {
        results = new ArrayList<result>();
    }

    public class result {
        String id;
        String title;
        String rank;
        String relevant;

        result(String id, String title, String rank, String relevant) {
            this.id = id;
            this.title = title;
            this.rank = rank;
            this.relevant = relevant;
        }
    }

    String query;
    ArrayList<result> results;

    public void addResult(String id, String title, String rank, String relevant) {
        this.results.add(new result(id, title, rank, relevant));
    }

    public ArrayList<String> getIdList() {
        ArrayList<String> idList = new ArrayList<String>();
        for (result r : this.results) {
            idList.add(r.id);
        }
        return idList;
    }

    public boolean containsId(String id) {
        for (result r : results) {
            if (r.id.equals(id)) {
                return true;
            }
        }
        return false;
    }
    
    public int getIdIndex(String id) {
    	 int i = 0;
    	 for (result r : results) {
             if (r.id.equals(id)) {
                 return i;
             }
             i++;
         }
         return -1;
    }
   
}
