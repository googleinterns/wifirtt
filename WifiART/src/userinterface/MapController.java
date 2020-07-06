package userinterface;

public class MapController {
    private final MapView view;
    private final MapModel model;

    public MapController(MapView view, MapModel model) {
        this.model = model;
        this.view = view;
        this.model.setCallback(this);
    }
}
