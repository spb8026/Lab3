package src;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import Nodes.DTNode;
import Nodes.DTTrainer;

public class lab3 {


    public static void main(String[] args) {
        DTTrainer dtTrainer = new DTTrainer();
        DataExtractor dataExtract =  new DataExtractor();
        try {
            dataExtract.createExamples();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ArrayList<Example> examples = dataExtract.allExamples;
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("the");
        attributes.add("de");
        DTNode root = dtTrainer.DTL(examples, attributes, new DTNode(examples), 0);
        System.out.println(root.printTree(""));
    }
    
}
