import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is mainly design as the main function class to use the input variables
 * to achieve different functions.
 */
public class Search {

    private String search;
    private String query;
    private String cache;

    private HTMLWriter htmlWriter;
    private List<String> list;

    /**
     * This constructor is used for save the string and call the search method.
     * @param search is called from the main class which aims at save the value to this class
     * @param query
     * @param cache
     */
    public Search(String search, String query, String cache) throws IOException {
        this.search = search;
        this.query = query;
        this.cache = cache;
        search();
    }

    private final String dblpUrl = "http://dblp.org/search/";
    private final String searchFor = "q=";
    private final String format = "format=xml";
    private final String maxHints = "h=40";
    private final String maxConpletion = "c=0";

    private String url() {
        String inner = "";
        switch (search) {
            case "publication" :
                inner = "publ";
                break;
            case "author" :
                inner = "author";
                break;
            case "venue" :
                inner = "venue";
                break;
            default :
                System.out.println("Invalid input!");
        }
        String dblpSearch = dblpUrl + inner;
        dblpSearch += "/api";
        dblpSearch += "?" + format;
        dblpSearch += "&" + maxConpletion;
        dblpSearch += "&" + maxHints;
        dblpSearch += "&" + searchFor + query;

        return dblpSearch;
    }

    /**
     * This method is mainly for common part of the search function.
     * To avoid space, " " will be replace as "+".
     * The program will find whether the file available in the cache first and if so, this path will be used as a
     * xml file and to run the program
     * While the file is not available, this file will be created and this is the same as author detail method.
     */
    private void search() throws IOException {
        String dblpUrl = url().replace(" ", "+");
//        System.out.println(dblpUrl);// <- issue with no protocol
//        dblpUrl = URLEncoder.encode(dblpUrl);
//        System.out.println(dblpUrl);
        File file = null;
        URL url = null;
        Boolean network = false;
        Document doc = null;

        try {
            String path = cache + "/" + URLEncoder.encode(dblpUrl, "UTF-8");
            file = new File(path);
            if (!file.exists()) {
                url = new URL(dblpUrl);
                network = true;
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            if (network) {
                doc = db.parse(url.openStream());
                saveCache(dblpUrl, doc);
            }
            else {
                doc = db.parse(file);
            }

            doc.getDocumentElement().normalize();

//            Element element = doc.getDocumentElement();
//            System.out.println(doc.getDocumentElement().getChildNodes().item(0).getAttributes());
            if (doc.hasChildNodes()) {
                NodeList hits = doc.getElementsByTagName("hit");
//                System.out.println(hits.getLength());
                switch (search) {
                    case "author" :
                        author(hits);
                        break;
                    case "venue" :
                        venue(hits);
                        break;
                    case "publication" :
                        publication(hits);
                        break;
                    default :
                        System.out.println("Wrong search category. Please try again!");
                        break;
                }
            }
            else {
                System.out.print("No details found. Please try another query!");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is designed for reading the text which is searched as venue.
     * There is a trick I have caused is that I looked at the xml on the webpage and found that there is a acronym.
     * However it printed twice, I asked teacher why it happens,
     * then I found that it has already after the title.
     * @param hits
     */

    private void venue(NodeList hits) throws IOException {
        //body
//        System.out.println(hits.getLength());
        htmlWriter = new HTMLWriter();
        list = new ArrayList<>();
        for (int i = 0; i < hits.getLength(); i++) {
            NodeList hitItem = hits.item(i).getChildNodes();
            for (int j = 0; j < hitItem.getLength(); j++) {
                Node allChildNodeOfHit = hitItem.item(j);
                if (allChildNodeOfHit.getNodeName().equals("info")) {
//                    String acronym = "";
                    String venue = allChildNodeOfHit.getChildNodes().item(0).getTextContent();
//                    if (allChildNodeOfHit.getChildNodes().item(1).getNodeName().equals("acronym")) {
//                        acronym += " (";
//                        acronym += allChildNodeOfHit.getChildNodes().item(1).getTextContent();
//                        acronym += ")";
//                    }
                    System.out.println(venue);
                    String code = "<tr><td>" + venue + "</td></tr>";
                    list.add(code);
//                    htmlWriter.venue(venue);
                }
            }
        }
        htmlWriter.heading(search, list);
    }

    /**
     * This search method is designed for search the publications.
     * The difference between this method and author search is that this will judge whether author item available and
     * if not, there are 0 author available.
     * @param hits
     */
    private void publication(NodeList hits) throws IOException {
        //body
//        System.out.println(hits.getLength());
        htmlWriter = new HTMLWriter();
        list = new ArrayList<>();
        for (int i = 0; i < hits.getLength(); i++) {
            NodeList hitItem = hits.item(i).getChildNodes();
            for (int j = 0; j < hitItem.getLength(); j++) {
                Node allChildNodeOfHit = hitItem.item(j);
                if (allChildNodeOfHit.getNodeName().equals("info")) {
                    NodeList author = allChildNodeOfHit.getFirstChild().getChildNodes();
                    Node numberOfAuthor = author.item(0);
                    int number_of_author;
                    if (numberOfAuthor.getNodeName().equals("author")) {
                        number_of_author = author.getLength();
                    }
                    else {
                        number_of_author = 0;
                    }
                    String title = ""; // = allChildNodeOfHit.getChildNodes().item(1).getTextContent(); //<- will finish at wed.
                    NodeList findTitle = allChildNodeOfHit.getChildNodes();
                    for (int s = 0; s < findTitle.getLength(); s++) {
                        if (findTitle.item(s).getNodeName().equals("title")) {
                            title = findTitle.item(s).getTextContent();
                        }
                    }
                    System.out.println(title + " (number of authors: " + number_of_author + ")");
                    String code = "<tr><td>" + title + "</td><td>" + number_of_author + "</td></tr>";
                    list.add(code);
//                    htmlWriter.publication(title,number_of_author);
                }
            }
        }
        htmlWriter.heading(search, list);
    }

    /**
     * This method is mainly for print the information about author.
     * @param hits
     */
    private void author(NodeList hits) throws IOException {
        list = new ArrayList<>();
        htmlWriter = new HTMLWriter();
        //body
        for (int i = 0; i < hits.getLength(); i++) {
            NodeList everyHitItem = hits.item(i).getChildNodes();
            for (int j = 0; j < everyHitItem.getLength(); j++) {
                Node allChildNodesOfHit = everyHitItem.item(j);
                if (allChildNodesOfHit.getNodeName().equals("info")) {
                    String authorName = allChildNodesOfHit.getChildNodes().item(0).getTextContent();
                    //author URL
                    NodeList authorURL = allChildNodesOfHit.getChildNodes();
                    String authorUrl = "";
                    for (int s = 0; s < authorURL.getLength(); s++) {
                        if (authorURL.item(s).getNodeName().equals("url")) {
                            authorUrl =  authorURL.item(s).getTextContent();
                        }
                    }
                    System.out.print(authorName + " - ");
                    calculateDetails(authorUrl, authorName);
                }
            }
//             System.out.println(list.size());
        }
        htmlWriter.heading(search, list);
    }

    /**
     * This method is mainly for the author and is to get the details of author's publications and co-authors.
     * Issue available for cache is not solved <- Tuesday 17.07.
     * @param secondURL
     */
    private void calculateDetails(String secondURL, String authorName) {
//        System.out.println(secondURL+".xml");
//        htmlWriter = new HTMLWriter();
        URL newURL = null;
        File offload;
        Boolean network = false;
        Document doc = null;
        try {
            //network available and local file
            String path = cache + "/" + URLEncoder.encode(secondURL) + ".xml";
            offload = new File(path);
            if (!offload.exists()) {
                newURL = new URL(secondURL + ".xml");
                network = true;
            }
//            System.out.println(offload.exists());

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            if (network) {
                doc = db.parse(newURL.openStream());
                saveCache(secondURL, doc);
            }
            else {
                doc = db.parse(offload);
            }

            doc.getDocumentElement().normalize();

            //number of publications
            NodeList publications = doc.getElementsByTagName("r");
            System.out.print(publications.getLength());

            //between data
            System.out.print(" publications with ");

            //number of co-authors
            NodeList coauthors = doc.getElementsByTagName("co");
            System.out.println(coauthors.getLength() + " co-authors.");
            String code = "<tr><td>" + authorName + "</td><td>" + publications.getLength() + "</td><td>" + coauthors.getLength() + "</td></tr>";
            list.add(code);
//            htmlWriter.author(authorName, publications.getLength(), coauthors.getLength());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


        //test
        //


//
//        NodeList hit1 = hits.item(0).getChildNodes();
//
//       for(int i = 0; i<hit1.getLength(); i++){
//           Node childNode = hit1.item(i);
//        //   System.out.println(childNode.getNodeName());
//           // If you want to get the author:
//           if(childNode.getNodeName() == "info"){
//               System.out.println(childNode.getChildNodes().item(0).getTextContent());
//           }

    //   }

//        String hit2 = hits.item(1).getTextContent();
//        String hit3 = hits.item(2).getTextContent();

//        System.out.println("first hit " + hit1);
//        System.out.println("second hit " + hit2);
//        System.out.println("third hit " + hit3);

        //body
//        for (int i = 0; i < hits.getLength(); i++) {
//            Node info = hits.item(0);
//            String value = info.getChildNodes().item(0).getNodeValue();
//
//            // System.out.println(info);
//        }

//        for (int i=0; i<hits.getLength(); i++) {
//            NodeList hit = hits.item(i).getChildNodes();
//            System.out.println(hit.item(i).getNodeName());
//            for (int j=0; j<hit.getLength(); j++){
//                Node authors = hit.item(0).getFirstChild().getFirstChild();
//                NodeList author = authors.getChildNodes();
//                for (int k=0; k<author.getLength(); k++) {
//                    String authorResult = author.item(k).getTextContent();
//                    System.out.println(authorResult);
//                }
//            }
//                System.out.println(author.getNodeName());
//        }


    /**
     * This method is used to save the cache to the exact file which named by encoder.encode method.
     * there is a judgement inside of the program which is to make sure that if there is no directory,
     * the program will creat one.
     * @param url Fvenue%252Fapi%253Fq%253Dlogic%2526format%253Dxml%2526h%253D40%2526c%253D0.xml
     * @param doc
     * @throws TransformerException
     */
    public void saveCache(String url, Document doc) throws TransformerException, UnsupportedEncodingException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();

        File outPut = new File(cache);
        if (!outPut.exists()) {
            outPut.mkdir();
            System.out.println("Cache directory doesn't exist: " + outPut.getName());
        }
        String urlE = URLEncoder.encode(url, "UTF-8");
//        urlE.replace("%25 20", "%2B");
        t.transform(new DOMSource(doc), new StreamResult(outPut + "/" + urlE));
    }
}
