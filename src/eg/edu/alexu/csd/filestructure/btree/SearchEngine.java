package eg.edu.alexu.csd.filestructure.btree;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.management.RuntimeErrorException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class SearchEngine implements ISearchEngine {
    /**private final List<String> IDs=new ArrayList<>();
    private final List<IBTree<String,Integer>> rankWords=new ArrayList<>();
    private int minDegree;
    public SearchEngine(int min){
        minDegree=min;
    }*/

    private HashMap<String, HashMap> files;

    public SearchEngine(){
        this.files=new HashMap();
    }


    List readFile(String filePath) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        List<Webpage> webpages = new ArrayList<Webpage>();
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String docTitle = node.getAttributes().getNamedItem("title").getNodeValue();
                String url = node.getAttributes().getNamedItem("url").getNodeValue();
                String ID = node.getAttributes().getNamedItem("id").getNodeValue();
                long id = Long.parseLong(ID);
                String value = node.getTextContent();

                webpages.add(new Webpage(docTitle, url, id, value));
            }
        }
        return webpages;
    }

    @Override
    public void indexWebPage(String filePath) {
        if(isNullOrEmpty(filePath)){
            throw new RuntimeErrorException(new Error());
        }

        File file =  new File(filePath);
        if(!file.exists()){
            throw new RuntimeErrorException(new Error());
        }

        List<Webpage> webpages = new ArrayList<Webpage>();

        try {
            webpages = readFile(filePath);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        HashMap indexes = new HashMap();
        for(int i=0; i<webpages.size(); i++){
            String[] words =  webpages.get(i).getValue().replaceAll("\n"," ").toLowerCase().split(" ");
            BTree WordTree = new BTree(128);
            for(int j = 0; j<words.length; j++){
                if(words[j].equals("")){
                    continue;
                }
                if(WordTree.search(words[j])==null){
                    WordTree.insert(words[j], 1);
                }else{
                    int rank = ((int)WordTree.search(words[j]))+1;
                    WordTree.delete(words[j]);
                    WordTree.insert(words[j],rank);
                }
            }
            indexes.put(webpages.get(i).getIndex(), WordTree);
        }
        files.put(filePath,indexes);
        /*DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder parser=factory.newDocumentBuilder();
            try {
                Document doc= parser.parse(file);
                NodeList parts = doc.getElementsByTagName("doc");
                for (int i=0;i<parts.getLength();i++){
                    Node x = parts.item(i);
                    if(x.getNodeType()==Node.ELEMENT_NODE) {
                        Element t = (Element) x;
                        IDs.add(t.getAttribute("id"));
                        NodeList txt = t.getChildNodes();
                        for(int m=0;m<txt.getLength();m++){

                        Node r = txt.item(m);
                        String y = r.getTextContent();
                        IBTree IDtree = new BTree(minDegree);
                        String tmp = "";
                        int flag = 0;
                        for (int k = 0; k < y.length(); k++) {
                            if (k != y.length() && y.charAt(k) == ' '||y.charAt(k)=='\n') {
                                int c = 1;
                                int s = k + 1;
                                while (true) {
                                    if (y.charAt(s) != ' '&&y.charAt(s)!='\n') {
                                        break;
                                    }
                                    if (s == y.length() - 1) {
                                        y = tmp;
                                        flag = 1;
                                        break;
                                    }
                                    s++;
                                    c++;
                                }
                                if (flag != 1) {
                                    if (k == 0) {
                                        k += c-1;
                                    } else {
                                        tmp += " ";
                                        if (c > 1) {
                                            k += c-1;
                                        } else {
                                            continue;
                                        }
                                    }
                                }
                            } else {
                                tmp += y.charAt(k);
                            }
                        }
                        if (flag != 1) {
                            y = tmp;
                        }
                        String[] words = y.split(" ");
                        for (int j = 0; j < words.length; i++) {
                            Object v = IDtree.search(words[j]);
                            if (v == null) {
                                IDtree.insert(words[j], 1);
                            } else {
                                int rank = (int) v;
                                IDtree.delete(words[j]);
                                IDtree.insert(words[j], rank++);
                            }
                        }
                        rankWords.add(IDtree);
                    }
                    }
                }
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public void indexDirectory(String directoryPath) {
        if(isNullOrEmpty(directoryPath)){
            throw new RuntimeErrorException(new Error());
        }
        File directory =  new File(directoryPath);
        if (!directory.exists()){
            throw new RuntimeErrorException(new Error());
        }
        for(int i =0; i<directory.listFiles().length; i++){
            indexWebPage(directory.listFiles()[i].getPath());
        }
    }

    @Override
    public void deleteWebPage(String filePath) {
        if(isNullOrEmpty(filePath)){
            throw new RuntimeErrorException(new Error());
        }
        File file =  new File(filePath);
        if(!file.exists()){
            throw new RuntimeErrorException(new Error());
        }
        for(int i = 0 ; i<files.size(); i++){
            Map.Entry temp = (Map.Entry)files.get(i);
            if(temp.getKey().equals(filePath)){
                files.remove(i);
            }
        }
    }

    @Override
    public List<ISearchResult> searchByWordWithRanking(String word) {
        List<ISearchResult> searched = new ArrayList<>();
        String toBeFound = word.toLowerCase();
        for(int i = 0; i<files.size(); i++){
            Iterator iterator =  files.get(i).entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry temp = (Map.Entry)iterator.next();
                BTree tree = (BTree) temp.getValue();
                if(tree.search(toBeFound)!= null){
                    searched.add(new SearchResult(toBeFound,(int)tree.search(toBeFound)));
                }
            }
        }
        return searched;
    }

    @Override
    public List<ISearchResult> searchByMultipleWordWithRanking(String sentence) {
        List<ISearchResult> searched = new ArrayList<>();
        String[] words = sentence.replaceAll("\n"," ").toLowerCase().split(" ");
        for(int i= 0; i<words.length;i++){
            List<ISearchResult> temp = searchByWordWithRanking(words[i]);
            for(int j = 0; j<temp.size(); j++){
                searched.add(temp.get(j));
            }
        }
        return searched;
    }

    private boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty() && !str.trim().isEmpty()){
            return false;
        }
        return true;
    }
}
