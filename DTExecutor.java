import java.util.ArrayList;

public class DTExecutor extends Model{
    private static final long serialVersionUID = 1L;
    
    public DTNode rootNode;

    public DTExecutor(DTNode root)
    {
        this.rootNode = root;
    }

    private boolean wordPresent(Example ex, String word)
    {
        String fullSentence = "";
        for (String wrd: ex.getExamples())
        {
            fullSentence += wrd + " ";
        }
        if (fullSentence.contains(word))
        {
            return true;
        }
        return false;
    }

    public String runExample(Example ex)
    {
        DTNode curNode = rootNode;
        while (!(curNode instanceof DTLeafNode))
        {
            if (curNode instanceof DTMidNode)
            {
                String curAttr = ((DTMidNode)curNode).attribute;
                if (wordPresent(ex, curAttr))
                {
                    curNode = ((DTMidNode)curNode).right;
                }
                else
                {
                    curNode = ((DTMidNode)curNode).left;
                }

            }
        }
        String res = ((DTLeafNode)curNode).getDecesion();
        System.out.println(res);
        return res;
    }

    public void runTestData(ArrayList<Example> examples)
    {
        for (Example ex: examples)
        {
            String dec = runExample(ex);
        }
    }

    @Override
    public void testModel(ArrayList<Example> examples) {
        double correct = 0;
        for (Example ex: examples)
        {
            String res = runExample(ex);
            if (res.equals(ex.getLanguage())) correct += 1.0;
        }
        System.out.println("Model Accuracy:" + correct/(double)examples.size());
    }
}
