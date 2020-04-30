package eg.edu.alexu.csd.filestructure.btree;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class trials{
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        String file = "C:\\Users\\Adel\\Desktop\\CS223\\Lab3\\BTree\\Wikipedia Data Sample\\Wikipedia Data Sample\\wiki_00";
        SearchEngine searchEngine = new SearchEngine();
        searchEngine.indexWebPage(file);

       /* //search.indexWebPage("C:\\Users\\Adel\\Desktop\\CS223\\Lab3\\BTree\\Wikipedia Data Sample\\Wikipedia Data Sample\\wiki_00");
        //System.out.println(10);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Load the input XML document, parse it and return an instance of the
        // Document class.
        Document document = builder.parse(file);

        List<Webpage> webpages = new ArrayList<Webpage>();
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                // Get the value of the ID attribute.
                String docTitle = node.getAttributes().getNamedItem("title").getNodeValue();
                String url = node.getAttributes().getNamedItem("url").getNodeValue();
                long ID = Long.parseLong(node.getAttributes().getNamedItem("id").getNodeValue());
                String value = node.getTextContent();


                // Get the value of all sub-elements.
                //String firstname = elem.getElementsByTagName("Firstname").item(0).getChildNodes().item(0).getNodeValue();

                //String lastname = elem.getElementsByTagName("Lastname").item(0).getChildNodes().item(0).getNodeValue();

                //Integer age = Integer.parseInt(elem.getElementsByTagName("Age").item(0).getChildNodes().item(0).getNodeValue());

              //Double salary = Double.parseDouble(elem.getElementsByTagName("Salary").item(0).getChildNodes().item(0).getNodeValue());

                webpages.add(new Webpage(docTitle, url, ID, value));
            }
        }

           String[] words =  webpages.get(0).getValue().replaceAll("\n"," ").split(" ");*/

        System.out.println(10);
    }
}
