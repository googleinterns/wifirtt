package userinterface;

public class ZController {
    private final ZView view;
    private final ZModel model;

    public ZController(ZView view, ZModel model) {
        this.model = model;
        this.view = view;
        this.model.setCallback(this);
    }
}
