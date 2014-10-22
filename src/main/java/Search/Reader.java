package Search;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * User: Ran Chen <ranc@cs.cmu.edu>
 * Date: 10/1/14
 * Time: 11:05 PM
 */
public class Reader {


    public Result read(String inputFile) throws Exception {
        Result result = new Result();
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(new File(inputFile));
        NodeList queryNode = doc.getElementsByTagName("query");
        result.query = queryNode.item(0).getTextContent();
        NodeList resultNodeList = doc.getElementsByTagName("result");
        for (int i = 0; i < resultNodeList.getLength(); i++) {
            Node node = resultNodeList.item(i);
            String id = node.getChildNodes().item(1).getTextContent();
            String title = node.getChildNodes().item(3).getTextContent();
            String rank = node.getChildNodes().item(5).getTextContent();
            String relevant = node.getChildNodes().item(7).getTextContent();
            result.addResult(id, title, rank, relevant);
        }
        return result;
    }

    public ResultList readAll(String inputFile) throws Exception {
        ResultList resultList = new ResultList();
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(new File(inputFile));
        NodeList nodeList = doc.getElementsByTagName("experiment");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NodeList childList = node.getChildNodes();
            Result result = new Result();
            String query = childList.item(1).getTextContent();
            result.query = query;
            NodeList resultNodeList = childList.item(3).getChildNodes().item(1).getChildNodes();
            for (int j = 0; j < resultNodeList.getLength(); j++) {
                Node n = resultNodeList.item(j);
                String id = n.getChildNodes().item(1).getTextContent();
                String title = n.getChildNodes().item(3).getTextContent();
                String rank = n.getChildNodes().item(5).getTextContent();
                String relevant = n.getChildNodes().item(7).getTextContent();
                result.addResult(id, title, rank, relevant);
            }
            resultList.add(query,result);
        }
        return resultList;
    }


}
