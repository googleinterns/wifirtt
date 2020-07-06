package userinterface;

import structs.ZState;
import structs.Subelement;

public class ZModel implements Subelement {

    private ZState state;
    private ZController fc;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public ZModel(ZState state) {
        this.state = state;
    }

    /**
     * Get the current Z subelement state from the model.
     *
     * @return the Z subelement state
     */
    public ZState getState() {
        return this.state;
    }

    /**
     * Set the Z subelement state in the model.
     *
     * @param state the Z subelement state
     */
    public void setState(ZState state) {
        this.state = state;
    }


    /**
     * The callback into the Z controller used when an asynchronous even occurs.
     *
     * @param fc the Z controller in the MVC pattern
     */
    public void setCallback(ZController fc) {
        this.fc = fc;
    }


    @Override
    public String toHexBuffer() {
        return null;
    }
}
