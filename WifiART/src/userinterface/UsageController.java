package userinterface;

public class UsageController {
    private final UsageView view;
    private final UsageModel model;

    public UsageController(UsageView view, UsageModel model) {
        this.model = model;
        this.view = view;
        this.model.setCallback(this);
    }
}
