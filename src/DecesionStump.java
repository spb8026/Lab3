package src;

import java.io.Serializable;
import java.util.ArrayList;

public class DecesionStump implements Serializable{
    private String feature;
    private String presentResult;
    private String notPresentResult;
    private double weight;

    public DecesionStump(String feature, String pres, String notPres)
    {
        this.feature = feature;
        this.presentResult = pres;
        this.notPresentResult = notPres;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getFeature() {
        return feature;
    }
    

    public String getNotPresentResult() {
        return notPresentResult;
    }

    public String getPresentResult() {
        return presentResult;
    }
    public double getWeight() {
        return weight;
    }

    public String runStump(ArrayList<String> ex)
    {
        for (String word: ex)
        {
            if (word.contains(feature))
            {
                return presentResult;
            }
        }
        return notPresentResult;
    }

    public double calculateError(ArrayList<WeightedExample> examples)
    {
        double err = 0;
        for (WeightedExample ex: examples)
        {
            if (!runStump(ex.getExamples()).equals(ex.getLanguage()))
            {
                err += ex.getWeight();
            }
        }
        return err;
    }
}
