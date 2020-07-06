package userinterface;

public class BssidController {
    private final BssidView view;
    private final BssidModel model;

    public BssidController(BssidView view, BssidModel model) {
        this.model = model;
        this.view = view;
        this.model.setCallback(this);
    }
}
