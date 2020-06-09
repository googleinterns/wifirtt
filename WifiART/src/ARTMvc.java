import structs.ARTSystemState;
import structs.JChart;
import structs.SystemState;
import userinterface.*;

public class ARTMvc {

    public static void main(String[] args) {
        // Establish the system state
        final ARTSystemState state = new ARTSystemState();


        // Setup the Control GUI using the MVC coding pattern
        ARTMvcView fv = new ARTMvcView(state);
        ARTMvcModel fm = new ARTMvcModel(state);
        new ARTMvcController(fv, fm);
        fv.setVisible(true);

    }
}
