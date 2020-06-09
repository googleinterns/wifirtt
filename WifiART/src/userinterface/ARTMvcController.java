package userinterface;

public class ARTMvcController {
    private final ARTMvcView fv; // fv = MVC Filter View
    private final ARTMvcModel fm; // fm = MVC Filter Model

    /** Constructor. */
    public ARTMvcController(ARTMvcView fv, ARTMvcModel fm) {
        this.fm = fm;
        this.fv = fv;

        // Initialize GUI by copying state from model
        this.fv.setViewState(fm.getState());
        this.fm.setCallback(this);

        // Add the listeners to the view
    }

    /** Events that occur asynchronously in the model call this method e.g. For an animation */
    void modelCallback() {
        fv.setViewState(fm.getState());
    }

    //Listener classes:
}
