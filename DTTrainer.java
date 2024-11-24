
import java.util.ArrayList;
import java.util.HashMap;

public class DTTrainer {
    private String res1 = "en";
    private String res2 = "nl";
    private int maxDepth = 6;
    private HashMap<String, Boolean> wordPresenceCache = new HashMap<>();

    private String createCacheKey(Example ex, String word) {
        return ex.hashCode() + ":" + word;
    }


    private boolean wordPresent(Example ex, String word)
    {
        String cacheKey = createCacheKey(ex, word);
        if (wordPresenceCache.containsKey(cacheKey))
        {
            return wordPresenceCache.get(cacheKey);
        }
        String fullSentence = "";
        for (String wrd: ex.getExamples())
        {
            fullSentence += wrd + " ";
        }
        if (fullSentence.contains(word))
        {
            wordPresenceCache.put(cacheKey, true);
            return true;
        }
        wordPresenceCache.put(cacheKey, false);
        return false;
    }

    private double entropy(int aCount, int bCount) {
        int total = aCount + bCount;
        if (total == 0) return 0.0;

        double pA = (double) aCount / total;
        double pB = (double) bCount / total;
        
        double entropyA = pA == 0 ? 0 : -pA * Math.log(pA) / Math.log(2);
        double entropyB = pB == 0 ? 0 : -pB * Math.log(pB) / Math.log(2);
        
        return entropyA + entropyB;
    }

    public double getInformationGain(int subPresentEngl, int subNotPresentEngl, int subPresentDut, int subNotPresentDut) {
        int totalEngl = subNotPresentEngl + subPresentEngl;
        int totalDut = subNotPresentDut + subPresentDut;
        int total = totalEngl + totalDut;
        double parentEntropy = entropy(totalEngl, totalDut);

        int presTotal = subPresentEngl + subPresentDut;
        int notPresTotal = subNotPresentDut + subNotPresentEngl;

        double presEntropy = entropy(subPresentEngl, subPresentDut);
        double notPresEntropy = entropy(subNotPresentEngl, subNotPresentDut);

        double weightedEntropy = ((double) presTotal / total) * presEntropy
                               + ((double) notPresTotal / total) * notPresEntropy;

        return parentEntropy - weightedEntropy;
    }

    public String getBestAttribute(ArrayList<Example> examples, ArrayList<String> attributes)
    {
        String bestAtr = "";
        double bestInfo = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < attributes.size(); i ++)
        {
            int subPresentEngl = 0;
            int subNotPresentEngl = 0;
            int subPresentDut = 0;
            int subNotPresentDut = 0;
            for (Example ex: examples)
            {
                if (ex.getLanguage().contains(res1))
                {
                    boolean subPres = wordPresent(ex, attributes.get(i));
                    if (subPres) subPresentEngl += 1;
                    else         subNotPresentEngl += 1;
                }
                else
                {
                    boolean subPres = wordPresent(ex, attributes.get(i));
                    if (subPres) subPresentDut += 1;
                    else         subNotPresentDut += 1;
                }

            }
            double infoGain = getInformationGain(subPresentEngl,subNotPresentEngl,subPresentDut,subNotPresentDut);
            if (infoGain > bestInfo)
            {
                bestInfo = infoGain;
                bestAtr = attributes.get(i);
            }
        }
        return bestAtr;
    }

    public boolean sameClassifcation(ArrayList<Example> examples)
    {
        String res = examples.get(0).getLanguage();
        for (Example i: examples)
        {
            if (!i.getLanguage().equals(res))
            {
                return false;
            }
        }
        return true;
    }

    private DTNode majorityAns(ArrayList<Example> examples) {
        int engl = 0;
        int dutch = 0;
        for (Example ex: examples)
        {
            if (ex.getLanguage().equals(res1))
            {
                engl+= 1;
            }
            else
            {
                dutch += 1;
            }
        }
        if (engl > dutch) return new DTLeafNode(examples, res1);
        else return new DTLeafNode(examples, res2);
    }

    public DTNode DTL(ArrayList<Example> examples, ArrayList<String> attributes, DTNode parentNode, int depth)
    {
        if (examples.isEmpty())
        {
            return majorityAns(parentNode.examples);
        }
        if (depth == maxDepth || attributes.isEmpty() || sameClassifcation(examples))
        {
            return majorityAns(examples);
        }
        String bestAttribute = getBestAttribute(examples,attributes);
        ArrayList<Example> presEx = new ArrayList<>();
        ArrayList<Example> notPresEx = new ArrayList<>();
        for (Example ex: examples)
        {
            if (wordPresent(ex, bestAttribute)) presEx.add(ex);
            else notPresEx.add(ex);
        }
        ArrayList<String> newAttributes = new ArrayList<>();
        for (String att: attributes)
        {
            if (!att.equals(bestAttribute))
            {
                newAttributes.add(att);
            }
        }
        return new DTMidNode(examples, DTL(notPresEx, newAttributes, parentNode, depth + 1), DTL(presEx, newAttributes, parentNode, depth + 1), bestAttribute);


    }
    
}
