import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class lab3 {
public static ArrayList<Example> loadExampleswithLabel(String fName) {
    ArrayList<Example> examples = new ArrayList<>();
    try {
        Scanner scanner = new Scanner(new File(fName));
        while (scanner.hasNextLine()) {
            String[] parts = scanner.nextLine().split("\\|");
            
            // Ensure the split results in at least two parts (language + features)
            if (parts.length < 2) {
                System.err.println("Invalid line format, skipping: " + parts[0]);
                continue;
            }

            String lang = parts[0].trim(); // Extract the language
            ArrayList<String> features = new ArrayList<>(List.of(parts[1].trim().split(" "))); // Extract features separated by spaces

            examples.add(new Example(features, lang));
        }
        scanner.close(); // Close scanner to avoid resource leak
    } catch (FileNotFoundException e) {
        System.err.println("File not found: " + fName);
        e.printStackTrace();
    }
    return examples;
}

public static ArrayList<Example> loadExamples(String fName) {
    ArrayList<Example> examples = new ArrayList<>();
    try {
        Scanner scanner = new Scanner(new File(fName));
        while (scanner.hasNextLine()) {
            String[] parts = scanner.nextLine().split(" ");
            ArrayList<String> features = new ArrayList<>(List.of(parts)); // Extract features separated by spaces

            examples.add(new Example(features, "unknown"));
        }
        scanner.close(); // Close scanner to avoid resource leak
    } catch (FileNotFoundException e) {
        System.err.println("File not found: " + fName);
        e.printStackTrace();
    }
    return examples;
}

    
public static ArrayList<String> loadFeatures(String fName) throws FileNotFoundException
{
    Scanner scanner = new Scanner(new File(fName));
    ArrayList<String> features = new ArrayList<>();
    while (scanner.hasNext())
    {
        features.add(scanner.next());
    }
    scanner.close();;
    return features;
}

public static void learnDT(ArrayList<Example> examples, ArrayList<String> features, String hypothFile) throws IOException
    {
    DTTrainer dtTrain = new DTTrainer();
    DTNode root = dtTrain.DTL(examples, features, new DTNode(examples), 0);
    Model mod = new DTExecutor(root);
    FileOutputStream file = new FileOutputStream(hypothFile + ".model");
    ObjectOutputStream out = new ObjectOutputStream(file);
    out.writeObject(mod);
    out.close();
    file.close();
    }

    
public static void learnAda(ArrayList<Example> examples, ArrayList<String> features, String hypothFile) throws IOException
{
    ADALearner ada = new ADALearner();
    Adaboost mod = ada.adaboost(examples, 100, features);
    FileOutputStream file = new FileOutputStream(hypothFile + ".model");
    ObjectOutputStream out = new ObjectOutputStream(file);
    out.writeObject(mod);
    out.close();
    file.close();
}

    public static Model loadModel(String hypothFile) throws Exception, IOException
        {
            FileInputStream file = new FileInputStream(hypothFile);
            ObjectInputStream out = new ObjectInputStream(file);
            Model mod = (Model) out.readObject();
            out.close();
            file.close();
            return mod;
        }

                
                
    public static void main(String[] args) throws IOException {
        // DataExtractor dt = new DataExtractor();
        // dt.createExamples();
        String mode = args[0];
        String exampleFile = args[1];
        String featureFile = args[2];
        String hypothFile = args[3];
        if (mode.equals("train"))
        {
            String learningType = args[4];
            if (learningType.equals("dt"))
            {
                try {
                    ArrayList<Example> examples = loadExampleswithLabel(exampleFile);
                    ArrayList<String> features = loadFeatures(featureFile);
                    learnDT(examples,features,hypothFile);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            else if (learningType.equals("ad"))
            {
                ArrayList<Example> examples = loadExamples(exampleFile);
                try {
                    ArrayList<String> features = loadFeatures(featureFile);
                    learnAda(examples, features, hypothFile);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        else if (mode.equals("predict"))
        {
            try {
                Model mod = loadModel(hypothFile);
                ArrayList<Example> examples = loadExamples(exampleFile);
                ArrayList<String> features = loadFeatures(featureFile);
                mod.runTestData(examples);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }



     }

}
