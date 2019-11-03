import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class HTMLWriter {
    /**
     * This class is  used for write the html and show it as the web page
     */
//    private String search;
//    private List<String> input = new ArrayList<>();
    private PrintWriter writer;

    public HTMLWriter(){
    }

    /**
     * This method writes heading of the webpage
     */
    public void heading(String search, List<String> list) throws IOException {
        writer = new PrintWriter("../output.html");
        //head
        writer.println("<!DOCTYPE html>");
        writer.println("<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Result Page</title>\n" +
                "    <style type=\"text/css\">" +
                "    body{background-color:white}\n" +
                "    p{color:black}\n" +
                "    h1{color:black}\n" +
                "    </style>\n" +
                "    <meta name=\"data\" content=\"My web programmed by java\"" +
                "</head>\n");
        //body
        writer.println("<body>");

        writer.println("<h1 style=\"text-align:center\">Search Result</h1>\n" +
                "<p  style=\"text-align:center\"><a href=\"http://dblp.org/\" target=\"blank\">DBLP website</a></p>");
        writer.println("<table width=\"800\" border=\"1\" align=\"center\">");
        //switch
        String code = "";
        switch (search) {
            case "venue":
                code = "<tr><th>" + "venue" + "</th></tr>";
                break;
            case "author" :
                code = "<tr><th>" + "authorName" + "</th><th>" + "publications" + "</th><th>" + "coAuthors" + "</th></tr>";
                break;
            case "publication":
                code = "<tr><th>" + "title" + "</th><th>" + "number of author" + "</th></tr>";
                break;
        }
        writer.write(code);
        for(String s : list){
//            System.out.println(list.size());
            writer.println(s);
        }
        writer.println("</table>");
        //end
        writer.println("</body>");
        writer.println("</html>");

        writer.close();
    }


//    private void inputStream(String code){
//        input.add(code);
//        System.out.println(input.size());
//    }

//    public void author (String authorName, int publications, int coAuthors) {
//        String code = "<tr><td>" + authorName + "</td><td>" + publications + "</td><td>" + coAuthors + "</td></tr>";
////        inputStream(code);
////        input.add(code);
//    }
//
//    public void venue(String venue){
//        String code = "<tr><td>" + venue + "</td></tr>";
////        inputStream(code);
////        input.add(code);
//    }
//
//    public void publication(String title, int number_of_author){
//        String code = "<tr><td>" + title + "</td><td>" + number_of_author + "</td></tr>";
////        inputStream(code);
////        input.add(code);
//    }
}
