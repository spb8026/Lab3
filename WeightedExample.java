
import java.util.ArrayList;

public class WeightedExample extends Example  {

    private double weight;


    public WeightedExample(ArrayList<String> examples, String lang, double weight)
    {
        super(examples, lang);
        this.weight = weight;

    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
}
