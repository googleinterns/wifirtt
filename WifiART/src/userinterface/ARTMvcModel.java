package userinterface;

import structs.ARTSystemState;

public class ARTMvcModel {
    private final ARTSystemState state;
    private ARTMvcController fc;


    /**
     * Constructor.
     *
     * @param state the Bfa system state
     */
    public ARTMvcModel(ARTSystemState state) {
        this.state = state;
    }

    /**
     * Get the current system state from the model.
     *
     * @return the system state
     */
    public ARTSystemState getState() {
        return this.state;
    }


    /**
     * The callback into the BfaController used when an asynchronous even occurs e.g. animation timer.
     *
     * @param fc the Bfa controller in the MVC pattern
     */
    public void setCallback(ARTMvcController fc) {
        this.fc = fc;
    }


    /** Update the view by executing the call back on the Bfa Controller. */
    private void updateView() {
        if (fc != null) {
            fc.modelCallback();
        }
    }
}
