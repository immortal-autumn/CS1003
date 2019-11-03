import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This method get the value from output file and split it use line.split API.
 * Map is used to import all values into map and get the key and value pairs using Map.Entry.
 */

public class GetMaximumNumber {
    public void read(String inputFile) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] element = line.split("\t");
//                System.out.println(line);
                map.put(element[0], Integer.parseInt(element[1]));
            }
            int maxValue = Collections.max(map.values());
            for(Map.Entry<String, Integer> entry : map.entrySet()) {
                if(entry.getValue() == maxValue) {
                    System.out.println("\n\n\n\n\n\t The most popular tweet is:  " + entry.getKey() + "\t" + entry.getValue());
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getAssociatedUsers(String urls){

    }
}
