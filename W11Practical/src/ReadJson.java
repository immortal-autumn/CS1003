import javax.json.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for read the Json file and input them into Mapper and Reducer.
 * The class will uses JsonReader and input them as a list.
 */
public class ReadJson {

    /**
     * This list method is to get the extended_url from Json.
     * @param input
     * @return
     */
    public List<String> read(String input) {
        JsonReader jsReader;
        JsonObject object;

        List<String> list = new ArrayList<>();

        String line = "";

//        try {

//            FileReader fileReader = new FileReader(inputFile);
//            BufferedReader reader = new BufferedReader(fileReader);

//            while ((line = reader.readLine()) != null) {

        line = input;

        jsReader = Json.createReader(new StringReader(line));
        object = jsReader.readObject();
        if(object.containsKey("entities")){
            object = object.getJsonObject("entities");
            if(object.containsKey("urls")) {
                JsonArray urls = object.getJsonArray("urls");
                for (int i = 0; i < urls.size(); i++) {
                    for (String key : urls.getJsonObject(i).keySet())
                        if (key.equals("expanded_url")) {
                            JsonObject linkObject = urls.getJsonObject(i);
//                            JsonValue value = linkObject.getJsonString("key");
                            String link;
//                            System.out.println(linkObject.isNull(key) + key);
                            if (!linkObject.isNull(key)) {
                                if (linkObject.getString(key) != null) {
                                    link = "\"" + linkObject.getString(key) + "\""; //<- issue in 17:50 Tuesday
                                    list.add(link);
                                }
                            }
//                            System.out.println(linkObject.getJsonString(key).toString());
                        }
                }
            }
        }

        jsReader.close();
//            }
//            reader.close();
//        }
//        catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//        catch (IOException e) {
//            System.out.println(e.getMessage());
//        }

        return list;
    }

}
