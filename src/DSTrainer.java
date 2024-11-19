package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DSTrainer {

    
    public static DecesionStump trainDecisionStump(ArrayList<WeightedExample> examples, ArrayList<String> features) {

        DecesionStump bestStump = null;
        double minError = Double.MAX_VALUE;

        // Try using each word as a decision attribute
        for (String word : features) {
            // Create two stumps: one for classifying as English if word is found, and vice versa
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
