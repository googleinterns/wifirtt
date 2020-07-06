package userinterface;

import structs.MapState;
import structs.Subelement;

public class MapModel implements Subelement {

    private MapState state;
    private MapController fc;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public MapModel(MapState state) {
        this.state = state;
    }

    /**
     * Get the current Map Image subelement state from the model.
     *
     * @return the Map Image subelement state
     */
    public MapState getState() {
        return this.state;
    }

    /**
     * Set the Map Image subelement state in the model.
     *
     * @param state the Map Image subelement state
     */
    public void setState(MapState state) {
        this.state = state;
    }


    /**
     * The callback into the Map Image controller used when an asynchronous even occurs.
     *
     * @param fc the Map Image controller in the MVC pattern
     */
    public void setCallback(MapController fc) {
        this.fc = fc;
    }


    @Override
    public String toHexBuffer() {
        return null;
    }
}
