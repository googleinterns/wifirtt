package userinterface;

import structs.ArtSystemState;

public class ArtMvcModel {
    private final ArtSystemState state;
    private ArtMvcController fc;


    /**
     * Constructor.
     *
     * @param state the system state
     */
    public ArtMvcModel(ArtSystemState state) {
        this.state = state;
    }

    /**
     * Get the current system state from the model.
     *
     * @return the system state
     */
    public ArtSystemState getState() {
        return this.state;
    }


    /**
     * The callback into the BfaController used when an asynchronous even occurs e.g. animation timer.
     *
     * @param fc the Bfa controller in the MVC pattern
     */
    public void setCallback(ArtMvcController fc) {
        this.fc = fc;
    }


    /** Update the view by executing the call back on the Bfa Controller. */
    private void updateView() {
        if (fc != null) {
            fc.modelCallback();
        }
    }
}
