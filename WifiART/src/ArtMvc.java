import structs.ArtSystemState;
import userinterface.ArtMvcController;
import userinterface.ArtMvcModel;
import userinterface.ArtMvcView;

public class ArtMvc {

    public static void main(String[] args) {
        // Establish the system state
        final ArtSystemState state = new ArtSystemState();


        // Setup the Control GUI using the MVC coding pattern
        ArtMvcView fv = new ArtMvcView(state);
        ArtMvcModel fm = new ArtMvcModel(state);
        new ArtMvcController(fv, fm);
        fv.setVisible(true);

    }
}
