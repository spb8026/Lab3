
import java.io.Serializable;
import java.util.ArrayList;

public class Example implements Serializable{
    private ArrayList<String> examples;
    private String language;

    public Example(ArrayList<String> examples, String lang)
    {
        this.examples = examples;
        this.language = lang;
    }

    public ArrayList<String> getExamples() {
        return examples;
    }

    public String getLanguage() {
        return language;
    }
}
