package userinterface;

import structs.BssidState;
import structs.Subelement;

public class BssidModel implements Subelement {

    private BssidState state;
    private BssidController fc;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public BssidModel(BssidState state) {
        this.state = state;
    }

    /**
     * Get the current Bssid subelement state from the model.
     *
     * @return the Bssid subelement state
     */
    public BssidState getState() {
        return this.state;
    }

    /**
     * Set the Bssid subelement state in the model.
     * @param state the Bssid subelement state
     */
    public void setState(BssidState state) {
        this.state = state;
    }


    /**
     * The callback into the Bssid controller used when an asynchronous even occurs.
     *
     * @param fc the Bssid controller in the MVC pattern
     */
    public void setCallback(BssidController fc) {
        this.fc = fc;
    }


    @Override
    public String toHexBuffer() {
        return null;
    }
}
