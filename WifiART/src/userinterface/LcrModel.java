package userinterface;

import structs.LcrState;
import structs.Subelement;

public class LcrModel implements Subelement {

    private LcrState state;
    private LcrController fc;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public LcrModel(LcrState state) {
        this.state = state;
    }

    /**
     * Get the current Location Civic subelement state from the model.
     *
     * @return the Location Civic subelement state
     */
    public LcrState getState() {
        return this.state;
    }

    /**
     * Set the location civic subelement state.
     *
     * @param state the location civic subelement state
     */
    public void setState(LcrState state) {
        this.state = state;
    }

    /**
     * The callback into the Location Civic controller used when an asynchronous even occurs.
     *
     * @param fc the Location Civic controller in the MVC pattern
     */
    public void setCallback(LcrController fc) {
        this.fc = fc;
    }


    @Override
    public String toHexBuffer() {
        return null;
    }
}
