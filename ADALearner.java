
import java.util.ArrayList;

public class ADALearner {
    

    public Adaboost adaboost(ArrayList<Example> examples,int K, ArrayList<String> features)
    {
        ArrayList<DecesionStump> H = new ArrayList<>();
        ArrayList<WeightedExample> wExamples = new ArrayList<>();
        double initial_weight = (1.0 /examples.size());
        for (Example ex: examples)
        {
            wExamples.add(new WeightedExample(ex.getExamples(), ex.getLanguage(),initial_weight ));
        }
        for(int k = 1; k <= K; k++)
        {
            DecesionStump curStump = DSTrainer.trainDecisionStump(wExamples,features);
            double err = curStump.calculateError(wExamples);
            if (err == 0) {
                err = 1e-10;
            }
            if (err >= 1) {
                err = 1 - 1e-10;
            }
            
            
            double weightChange = err/(1-err);
            for (WeightedExample ex: wExamples)
            {
                if (curStump.runStump(ex.getExamples()).equals(ex.getLanguage()))
                {
                    ex.setWeight(ex.getWeight()*weightChange);
                }
            }
            double totalWeight = 0.0;
            for (WeightedExample ex : wExamples) {
                totalWeight += ex.getWeight();
            }
            for (WeightedExample ex : wExamples) {
                ex.setWeight(ex.getWeight() / totalWeight);
            }
            
            curStump.setWeight(.5*Math.log((1-err)/err));
            H.add(curStump);
        }
        return new Adaboost(H);
    } 
}
