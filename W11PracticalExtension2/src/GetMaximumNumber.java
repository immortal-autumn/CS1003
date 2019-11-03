import java.io.*;
import java.util.*;

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

    public void re_order(String inputFile, String outputPath){
        List<Tweets<String>> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            PrintWriter writer = new PrintWriter(outputPath + "/" + "output.txt");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] element = line.split("\t");
                list.add(new Tweets<>(element[0], Integer.parseInt(element[1])));
            }
            Collections.sort(list);
            for(Tweets<String> s : list) {
                writer.println(s.getKey() + "\t" + s.getValue());
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
