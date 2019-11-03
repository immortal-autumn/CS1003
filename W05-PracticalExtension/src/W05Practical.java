import java.io.FileNotFoundException;
import java.io.IOException;

public class W05Practical {

    public static void main(String[] args) throws IOException, FileNotFoundException {
	// write your code here

        if(args.length != 2){
            System.out.println("Usage: W05Practical <File> <Query>");
            System.exit(1);
        }
        try {
            SentenceReader sentenceReader = new SentenceReader(args[0],args[1]);
        }
        catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        }
        catch (IOException e){
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
}
