import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class W03PracticalExt {
    //step1 : basic change
    public static void main(String[] args) throws IOException{
	// write your code here

        System.out.println("Please select type of output of your input: \n1. WebPage File\n2. TXT File" );
        try {
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader bf = new BufferedReader(reader);
            int i = Integer.parseInt(bf.readLine());
            switch (i) {
                case 1:{
                    RW_to_html rw = new RW_to_html();
                    rw.access(args);
                    break;
                }
                case 2:{
                    RW rw = new RW();
                    rw.access(args);
                    break;
                }
                default:{
                    System.out.println("No related command.");
                    break;
                }
            }
        }
        catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        }

    }

}
