package userinterface;

public class LciController {
    private final LciView view;
    private final LciModel model;

    public LciController(LciView view, LciModel model) {
        this.model = model;
        this.view = view;
        this.model.setCallback(this);
    }
}
