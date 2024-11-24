
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
public class DataExtractor
{
    static ArrayList<Example> allExamples;
    
       public static ArrayList<Example> extractData(String fileName, String lang) throws FileNotFoundException {
               ArrayList<Example> examples = new ArrayList<>();
               Scanner scanner = new Scanner(new File("./rawData/" + fileName));
               
               String wordRegex = "^[a-zA-Z]+$";
               
               while (scanner.hasNext()) {
                   ArrayList<String> exWords = new ArrayList<>();
                   
                   for (int i = 0; i < 15 && scanner.hasNext(); ) {
                       String word = scanner.next().toLowerCase();
                       
                       if (word.matches(wordRegex)) {
                           exWords.add(word);
                           i++;
                       }
                   }
                   
       
                   if (!exWords.isEmpty()) {
                       Example newEx = new Example(exWords, lang);
                       examples.add(newEx);
                   }
               }
               scanner.close();
               return examples;
           }
           
               public static ArrayList<Example> createExamples(ArrayList<String> files, ArrayList<String> langs) throws FileNotFoundException
                       {
                           ArrayList<Example> examples = new ArrayList<>();
                           for (int i = 0; i < files.size(); i++)
                           {
                               examples.addAll(extractData(files.get(i),langs.get(i)));
                        }
                        Collections.shuffle(examples);
                        return examples;
                    }
                
                    public static void createExamples() throws FileNotFoundException
                                {
                                    ArrayList<String> files = new ArrayList<String>();
                                    ArrayList<String> langs = new ArrayList<String>();
                                    files.add("englishtest_wikipedia_articles.txt");
                                    langs.add("en");
                                    files.add("dutchtest_wikipedia_articles.txt");
                                    langs.add("nl");
                                    // files.add("english_wikipedia_articles.txt");
                                    // langs.add("en");
                                    // files.add("dutch_wikipedia_articles.txt");
                                    // langs.add("nl");
                                    // files.add("english_wikipedia_articles2.txt");
                                    // langs.add("en");
                                    // files.add("dutch_wikipedia_articles2.txt");
                                    // langs.add("nl");
                                    // files.add("eng1.txt");
                                    // langs.add("en");
                                    // files.add("eng2.txt");
                                    // langs.add("en");
                                    // files.add("eng3.txt");
                                    // langs.add("en");
                                    // files.add("eng4.txt");
                                    // langs.add("en");
                                    // files.add("eng5.txt");
                                    // langs.add("en");
                                    // files.add("dut1.txt");
                                    // langs.add("nl");
                                    // files.add("dut2.txt");
                                    // langs.add("nl");
                                    // files.add("dut3.txt");
                                    // langs.add("nl");
                                    // files.add("dut4.txt");
                                    // langs.add("nl");
                                    // files.add("dut5.txt");
                                    // langs.add("nl");
                                    ArrayList<Example> examples = createExamples(files, langs);
                            allExamples = examples;
                    try (FileWriter fw = new FileWriter(new File("train.dat"))) {
                        for (Example ex: examples)
                        {
                            String exLine = ex.getLanguage() + "|";
                            // String exLine = "";
                            for (String word: ex.getExamples())
                            {
                                exLine += word + " ";
                            }
                            exLine  += "\n";
                            fw.append(exLine);
                        }
                    } catch (FileNotFoundException e) {
                        throw e;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                public static void main(String[] args) {
                    try {
                        createExamples();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
        }
}

