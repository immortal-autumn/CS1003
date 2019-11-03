import java.util.Arrays;

/**
 * This class is the main class which is used for deal with the input stream and re-order it.
 */
public class W09Practical {

    private static final String argMissing = "Missing value for ";
    private static final String malformedMsg = "Malformed command line arguments.";
    private static final String argInvalid = "Invalid value for ";
    /**
     * This method is the main method to judge whether there are enough argument that have been inputted by the program.
     * Then the program will call the reorder method to reorder the input and can be solved by search class.
     * @param args
     */
    public static void main(String[] args) {
        //running program
        //This method is used as re-order the input stream
        re_order(args);

    }

    /**
     * This method is mainly used for solve the order of argument by using arrays.
     * @param args
     */
    private static void re_order(String[] args) {
        searchCheck(args);
        missingArgumentCheck(args);

        String[] action = new String[3];
        String[] query = new String[3];

        String[] re_order = new String[3];

        for (int i = 0; i < 3; i++) {
            action[i] = args[2 * i];
            query[i] = args[2 * i + 1];
        }

        for (int i = 0; i < 3; i++) {
            switch (action[i]) {
                case "--search" :
                    re_order[0] = query[i];
                    break;
                case "--query" :
                    re_order[1] = query[i];
                    break;
                case "--cache" :
                    re_order[2] = query[i];
                    break;
                default:
                    System.out.println("Command may not true, please try again!");
                    break;
            }
        }

//        System.out.println(Arrays.toString(re_order));

        Search search = new Search(re_order[0], re_order[1], re_order[2]);

    }

    /**
     * This method is used for checking whether there are missing argument.
     * If there it, the program will print mulitiple usage to tell users where is wrong.
     * @param args
     */

    private static void missingArgumentCheck(String[] args) {
//        System.out.println(args.length);
        //no args
        if (args.length < 5 || args.length > 6) {
            System.out.println("Usage: java W09Practical --search [search query] --query [query] --cache [path]");
            System.exit(0);
        }
        //missing single one
        Boolean malformed = false;

        for (int i = 0; i < args.length; i++) {
            String element = "";
            String addIn = "";
            malformed = false;
            switch (args[i]) {
                case "--query" :
                    if (i == args.length - 1) {
                        element = "--query";
                        malformed = true;
                    }
                    else {
                        if (args[i + 1].contains("--")) {
                            element = "--query: ";
                            addIn = args[i + 1];
                            malformed = true;
                        }
                    }
                    break;
                case "--cache" :
                    if (i == args.length - 1) {
                        element = "--cache";
                        malformed = true;
                    }
                    else {
                        if (args[i + 1].contains("--")) {
                            element = "--cache";
                            addIn = args[i + 1];
                            malformed = true;
                        }
                    }
                    break;
                case "--search" :
                    if (i == args.length - 1) {
                        element = "--search";
                        malformed = true;
                    }
                    else {
                        if (args[i + 1].contains("--")) {
                            element = "--search: ";
                            addIn = args[i + 1];
                            malformed = true;
                        }
                    }
                default :
                    break;
            }
            if (malformed) {
                if (element.equals("--cache")) {
                    System.out.println("Cache directory doesn't exist: " + addIn);
                }
                else {
                    System.out.println(argMissing + element);
                }
                if (!element.equals("--cache")) {
                    System.out.println(malformedMsg);
                }
//                System.out.println(malformed + " " + element);
                System.exit(2);
            }
        }


    }

    /**
     * this method is to check where the query following search is correct.
     * @param args
     */
    private static void searchCheck(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (i != args.length - 1 && args[i].equals("--search")) {
                String word = args[i + 1];
                if (!(word.equals("venue") || word.equals("author") || word.equals("publication"))) {
                    System.out.println(argInvalid + "--search: " + word);
                    System.out.println(malformedMsg);
                    System.exit(1);
                }
            }
        }
    }
}
