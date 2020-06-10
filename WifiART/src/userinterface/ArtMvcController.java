package userinterface;

public class ArtMvcController {
    private final ArtMvcView fv; // fv = MVC Filter View
    private final ArtMvcModel fm; // fm = MVC Filter Model

    /** Constructor.
     *
     * @param fv the MVC view
     * @param fm the MVC model
     *
     * */
    public ArtMvcController(ArtMvcView fv, ArtMvcModel fm) {
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
