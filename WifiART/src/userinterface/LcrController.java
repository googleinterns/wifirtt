package userinterface;

public class LcrController {
    private final LcrView view;
    private final LcrModel model;

    public LcrController(LcrView view, LcrModel model) {
        this.model = model;
        this.view = view;
        this.model.setCallback(this);
    }
}
