package structs;

import java.util.ArrayList;

/**
 * The system state enclosing the LCI/LCR parameters.
 */
public class ArtSystemState {

    // Constants

    // Parameters
    private ArrayList<Subelement> subelements;

    /** Constructor. */
    public ArtSystemState() {
        init();
    }

    /** Initializes the state variables. */
    public void init() {
        subelements = new ArrayList<>();
    }
}
