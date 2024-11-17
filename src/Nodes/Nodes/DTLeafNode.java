package Nodes;
import java.util.ArrayList;

import src.Example;

public class DTLeafNode extends DTNode{

    private String decesion;
    
    public DTLeafNode(ArrayList<Example> examples, String decesion)
    {
        super(examples);
        this.decesion= decesion;
    }

    public String printTree(String indent) {
        String str = indent + "Leaf (Classification: " + decesion + ")\n";
        // String ex = "";
        // for (ArrayList<String> ah: examples)
        // {
        //    ex += ah.getLast();
        // }
        return str;
   }
}
