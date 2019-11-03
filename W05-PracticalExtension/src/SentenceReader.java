import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class SentenceReader {
    private String filePath, query;
    int n_gram;

    /**
     * Constructor
     * get filePath and query from argument
     * Then call the show  result method
     * @param filepath
     * @param query
     * @throws IOException
     */
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(isr);
    public SentenceReader(String filepath, String query) throws IOException {
        this.filePath = filepath;
        this.query = query;
        System.out.println("Please enter the number of gram: ");
        n_gram = Integer.parseInt(br.readLine());
        showResults();
//        readAllSentences(filePath);
    }

//    public List<ScoredResult> esatblishScoredResults(){
//        List<ScoredResult> listOfResults  = new ArrayList<ScoredResult>();
//        // for each sentence
//        // compute Jaccard index of sentence for given search string
//        // create a new Scored Result for sentence and Jaccard index
//        // insert new Scored result into a list of scored results
//
//       ScoredResult<String> result = new ScoredResult<String>("hello mr darcy", 0.32);
//        ScoredResult<String> result2 = new ScoredResult<String>("hello mr darcy", 0.32);
//        int comparison = result.compareTo(result2);
//        listOfResults.add(result);
//
//        return listOfResults;
//    }

    //The method to show the calculated result
    //using collection.sort

    /**
     * This method is used to show the result by using decimal format method
     * To make the numbers be more accuracte, I defined >= 0.5 as 1 and the number lower than 0.5 is 0
     * Then I make sure that there is no boundary exception: The number with always lower than size of result
     * Finally I print the result
     * @throws IOException
     */
    private void showResults() throws IOException{

        DecimalFormat df = new DecimalFormat("0.0000");
        df.setRoundingMode(RoundingMode.HALF_UP);

        List<ScoredResult<String>> results;
        results = scoreCalculate();

        int num = 50;

        Collections.sort(results);
        if(results.size() < 50){
            num = results.size();
        }

        for (int i = 0; i < num; i++) {
            System.out.println(df.format(results.get(i).getScore()) + " " + results.get(i).getResult());
        }

    }
    //calculate the score
    //create arraylist to increase the speed
    //convert sentence to biagram

    /**
     * There are orders in list but there is no order in set.
     * While comparing the biagram, I put all the sentence in the list but put the biagram into sets
     * Then I compare words between two sets
     * The result is like the following
     * One thing puzzeled me a lot is sanitise and I have been stuck in it for a long time
     * I ignored to sanitise the input word and caused a lot of mistakes
     * @return
     * @throws IOException
     */
    private List<ScoredResult<String>> scoreCalculate() throws IOException {

        List<ScoredResult<String>> results = new ArrayList<>();
        double score = 0, totalScore;

        List<String> sentences = readAllSentences();

        Set<String> totalBiagram = new HashSet<>();

        Set<String> queryList = new HashSet<>();
        queryList.addAll(biagram(sanitiseSentence(query)));

        Set<String> sentenceBiagram = new HashSet<>();

        for (String s : sentences) {
            score = 0;
            sentenceBiagram.addAll(biagram(s));
            totalBiagram.addAll(sentenceBiagram);
            totalBiagram.addAll(queryList);
//            for(String s1 : sentenceBiagram){
//                for (String s2 : queryList){
//                    if (s1.equals(s2)){
//                        score = score + 1;
//                    }
//                }
//            }
            for(String s1 : queryList){
                if(sentenceBiagram.contains(s1)){
                    score += 1;
                }
            }
            //System.out.println(totalBiagram);
            //System.out.println();
            results.add(new ScoredResult<>(s, score / totalBiagram.size()));

            sentenceBiagram.clear();
            totalBiagram.clear();
        }
        return results;
    }

    //this is the biagram
    //use subString method

    /**
     * Biagram is required in requirement
     * I use subString to make a list that every elements with 2 words
     * @param sectence
     * @return
     * @throws IOException
     */

    private List<String> biagram(String sectence) throws IOException {
        List<String> biagram = new ArrayList<>();
        for (int i = 0; i < sectence.length() - n_gram - 1; i++) {
            biagram.add(sectence.substring(i, i + n_gram));
//            System.out.println(biagram.get(i));
        }
        return biagram;
    }

    //      Given a file path, this method will read the entire contents of the file,
//      split the text into sentences, and return a list of sentences.
//
//      The sentence splitting is very basic: we just split on the full-stop character.
//
//      The contents of each sentence are sanitised as well.
//      Sanitisation is done by replacing each character with the corresponding lower case character,
//      removing all punctuation characters, etc.

    //Read whole article and store it into the String
    //Use Buffered reader and split it with "."

    /**
     * There previously have a parametre but I defined the filePath and query in constructor
     * This method aims at read all text and make it as a list
     * @return
     * @throws IOException
     */
    private List<String> readAllSentences() throws IOException {

        String file = "";
        String line = "";
        List<String> list = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null) {
            file += " " + line;
        }
//        file = sanitiseSentence(file);
        String[] fields = file.split("\\.");


        for (String s : fields) {
            list.add(sanitiseSentence(s));
//            System.out.println(s);
        }
        //System.out.println(list.get(11));
//        sc.close();

        return list;

    }

    /**
     * Given a string, this method will sanitise it.
     * Sanitisation is done by replacing each character with the corresponding lower case character,
     * removing all punctuation characters, etc.
     *
     * @param sentence The input string
     * @return The output string
     */
    private String sanitiseSentence(String sentence) {
        List<String> words = new ArrayList<>();
        for (String word : sentence.split("\\s+")) {
            String sanitised = word.toLowerCase().replaceAll("[^a-z]+", "");
            if (!sanitised.isEmpty()) {
                words.add(sanitised);
            }
        }

        return joinWords(words);
    }

    /**
     * This is a private method to join a list of words into a sentence.
     *
     * @param words The list of words
     * @return A string which contains the words separated by spaces
     */
    private String joinWords(List<String> words) {
        String joined = "";
        if (words.size() > 0) {
            joined = words.get(0);
        }
        for (int i = 1; i < words.size(); i++) {
            joined += " " + words.get(i);
        }
        return joined;
    }
}



