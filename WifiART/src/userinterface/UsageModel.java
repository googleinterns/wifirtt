package userinterface;

import structs.UsageState;
import structs.Subelement;

public class UsageModel implements Subelement {

    private UsageState state;
    private UsageController fc;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public UsageModel(UsageState state) {
        this.state = state;
    }

    /**
     * Get the current Usage subelement state from the model.
     *
     * @return the Usage subelement state
     */
    public UsageState getState() {
        return this.state;
    }

    /**
     * Set the Usage Rules/Policy sublement state in the model.
     *
     * @param state the Usage Rules/Policy subelement state
     */
    public void setState(UsageState state) {
        this.state = state;
    }


    /**
     * The callback into the Usage controller used when an asynchronous even occurs.
     *
     * @param fc the Usage controller in the MVC pattern
     */
    public void setCallback(UsageController fc) {
        this.fc = fc;
    }


    @Override
    public String toHexBuffer() {
        return null;
    }
}
