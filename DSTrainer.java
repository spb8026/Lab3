
import java.util.ArrayList;

public class DSTrainer {

    
    public static DecesionStump trainDecisionStump(ArrayList<WeightedExample> examples, ArrayList<String> features) {

        DecesionStump bestStump = null;
        double minError = Double.MAX_VALUE;

        for (String word : features) {
            DecesionStump stumpEnglish = new DecesionStump(word, "en", "nl");
            DecesionStump stumpDutch = new DecesionStump(word, "nl", "en");

    
            double errorEnglish = stumpEnglish.calculateError(examples);
            double errorDutch = stumpDutch.calculateError(examples);

            if (errorEnglish < minError) {
                minError = errorEnglish;
                bestStump = stumpEnglish;
            }
            if (errorDutch < minError) {
                minError = errorDutch;
                bestStump = stumpDutch;
            }
        }

        return bestStump;
    }


}
