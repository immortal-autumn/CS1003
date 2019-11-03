import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class W05Practical {

    public static void main(String[] args) throws IOException, FileNotFoundException {
	// write your code here

        if(args.length != 2){
            System.out.println("Usage: W05Practical <File> <Query>");
            System.exit(1);
        }
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            int i = 0;
            while (i == 0) {
                SentenceReader sentenceReader = new SentenceReader(args[0], args[1]);
                System.out.println("Please select the next file (exit for 1) :");
                String nextFile = br.readLine();
                switch (nextFile){
                    case "1" : {
                        i += 1;
                    }
                    default:{
                        args[0] = nextFile;
                    }
                }
            }
            br.close();
            isr.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        }
        catch (IOException e){
            System.out.println("IO Exception: " + e.getMessage());
        }
        finally {
            System.out.println("Thank you for using this software!");
        }
    }
}
