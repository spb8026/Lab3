package src;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Model implements Serializable {
    private static final long serialVersionUID = 1L;
    public abstract double runTestData(ArrayList<Example> examples);
}

