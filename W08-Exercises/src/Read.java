import javax.json.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Read {
    private String file;
    public Read(String file) throws FileNotFoundException{
        this.file = file;
//        find();
        reader();
    }
    public void reader() throws FileNotFoundException {
        JsonReader reader = Json.createReader(new BufferedReader(new FileReader(file)));
        JsonObject obj = reader.readObject();
        JsonArray name = obj.getJsonArray("modules");
        for(JsonValue s : name){
            System.out.println(s.toString());

        }
    }
//    public void find() throws FileNotFoundException {
//
//    }

}
