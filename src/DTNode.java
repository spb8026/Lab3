package src;

import java.util.ArrayList;

public class DTNode implements java.io.Serializable{
    ArrayList<Example> examples;
    
    public DTNode(ArrayList<Example> examples)
    {
        this.examples = examples;
    }

    public String printTree(String indent) {
        // This method will be overridden in subclasses
        return "";
    }
    public boolean isLeaf()
    {
        return false;
    }
}
