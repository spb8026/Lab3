
import java.util.ArrayList;

public class Adaboost extends Model {
    ArrayList<DecesionStump> hypothesis;
    
    public Adaboost (ArrayList<DecesionStump> hypothesis)
    {
        this.hypothesis = hypothesis;
    }

    public String runAda(ArrayList<String> ex)
    {
        double en = 0;
        double nl = 0;
        for (DecesionStump ds: hypothesis)
        {
            String result = ds.runStump(ex);
            if (result.equals("en"))
            {
                en += ds.getWeight();
            }
            else
            {
                nl += ds.getWeight();
            }
        }
        if (en > nl) return "en";
        else return "nl";
    }

    @Override
    public void runTestData(ArrayList<Example> examples) {
        for (Example ex: examples)
        {
            String res = runAda(ex.getExamples());
            System.out.println(res);
        }
    }
    
}
