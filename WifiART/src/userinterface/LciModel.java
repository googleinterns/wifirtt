package userinterface;

import structs.LciState;
import structs.Subelement;

public class LciModel implements Subelement {

    private LciState state;
    private LciController fc;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public LciModel(LciState state) {
        this.state = state;
    }

    /**
     * Get the current LCI subelement state from the model.
     *
     * @return the LCI subelement state
     */
    public LciState getState() {
        return this.state;
    }

    public void setState(LciState state) {
        this.state = state;
    }


    /**
     * The callback into the LCI controller used when an asynchronous even occurs.
     *
     * @param fc the LCI controller in the MVC pattern
     */
    public void setCallback(LciController fc) {
        this.fc = fc;
    }



    @Override
    public String toHexBuffer() {
        return null;
    }
}
