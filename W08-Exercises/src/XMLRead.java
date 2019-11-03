import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class XMLRead {
    final private String languageSearch = "https://en.wikipedia.org/w/api.php?format=xml&action=languagesearch&search=";
    final private String openSearch = "https://en.wikipedia.org/w/api.php?format=xml&action=opensearch&search=";
    private String query;

    public XMLRead(String query){
        this.query = query;
    }

    private void langSearch() throws MalformedURLException, IOException {


        try{
            URL url = new URL(languageSearch);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url.openStream());

            doc.getDocumentElement().normalize();


        }
        catch (Exception e){
            e.getMessage();
        }


    }

    private void openSearch() throws MalformedURLException {
        URL url = new URL(openSearch);
    }

}
