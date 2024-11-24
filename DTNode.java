
import java.util.ArrayList;

public class DTNode implements java.io.Serializable{
    ArrayList<Example> examples;
    
    public DTNode(ArrayList<Example> examples)
    {
        this.examples = examples;
    }

    public String printTree(String indent) {
        return "";
    }
    public boolean isLeaf()
    {
        return false;
    }
}
