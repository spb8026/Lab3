package src;

import java.util.ArrayList;

public class DTMidNode extends DTNode {
    DTNode left;
    DTNode right;
    String attribute;

    public DTMidNode(ArrayList<Example> examples,DTNode left, DTNode right, String attribute)
    {
        super(examples);
        this.left = left;
        this.right = right;
        this. attribute = attribute;
    }

    @Override
    public String printTree(String indent) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent).append("Node (Word ").append(attribute).append(")\n");
        builder.append(indent).append("├─ Not Present:\n");
        builder.append(left.printTree(indent + "│  "));
        builder.append(indent).append("└─ Present:\n");
        builder.append(right.printTree(indent + "   "));
        return builder.toString();
    }
    @Override
    public boolean isLeaf()
    {
        return false;
    }
    
}
