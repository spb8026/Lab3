package Nodes;
import java.util.ArrayList;

import src.Example;

public class DTNode {
    ArrayList<Example> examples;
    
    public DTNode(ArrayList<Example> examples)
    {
        this.examples = examples;
    }

    public String printTree(String indent) {
        // This method will be overridden in subclasses
        return "";
    }
}
