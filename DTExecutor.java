import java.util.ArrayList;

public class DTExecutor extends Model{
    public DTNode rootNode;

    public DTExecutor(DTNode root)
    {
        this.rootNode = root;
    }

    private boolean wordPresent(Example ex, String word)
    {
   
        for (String curWord: ex.getExamples())
        {
            if (curWord.contains(word))
            {
                return true;
            } 
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
}
