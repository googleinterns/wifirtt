package userinterface;

import structs.ARTSystemState;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import java.io.IOException;

public class ARTMvcView extends JFrame {
    // Height Adjustments
    // Pixel height not included in rows for tab panel:
    private static final int TABBED_HEIGHT_OFFSET = 76;
    // Dimensions (some package-private to allow other windows to adjust automatically)
    private static final int ROW_WD = 30;
    private static final int CONTROL_ROWS = 10;
    private static final int WINDOW_POSITION_X = 10;
    private static final int WINDOW_POSITION_Y = 10;
    private static final int FRAME_WIDTH =780;
    private static final int FRAME_HEIGHT = TABBED_HEIGHT_OFFSET + CONTROL_ROWS * ROW_WD;

    /* Tab name declarations  */
    private static final String GENERATE_TAB_NAME = "Generate";
    private static final String LCI_TAB_NAME = "LCI (sub)";
    private static final String Z_TAB_NAME = "  Z  ";
    private static final String USAGE_TAB_NAME = "Usage";
    private static final String BSSID_TAB_NAME = "BSSID List";
    private static final String LCR_TAB_NAME = "Loc. Civic";
    private static final String MAP_TAB_NAME = "Map Image";


    /** Constructor. */
    public ARTMvcView(ARTSystemState state) {
        /* Top level JFRAME */
        this.setTitle("Tabbed Pane Example");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLocation(WINDOW_POSITION_X, WINDOW_POSITION_Y);
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Main Panel Tabbed Pane
        JTabbedPane tabbedPanel = new JTabbedPane();
        getContentPane().add(tabbedPanel);

        // Tab = Generate
        JPanel generatePanel = new JPanel();
        tabbedPanel.addTab(GENERATE_TAB_NAME, generatePanel);

        // Tab = LCI
        JPanel lciPanel = new JPanel();
        tabbedPanel.addTab(LCI_TAB_NAME, lciPanel);

        // Tab = Z
        JPanel zPanel = new JPanel();
        tabbedPanel.addTab(Z_TAB_NAME, zPanel);

        // Tab = Usage
        JPanel usagePanel = new JPanel();
        tabbedPanel.addTab(USAGE_TAB_NAME, usagePanel);

        // Tab = BSSID
        JPanel bssidPanel = new JPanel();
        tabbedPanel.addTab(BSSID_TAB_NAME, bssidPanel);

        // Tab = LCR
        JPanel lcrPanel = new JPanel();
        tabbedPanel.addTab(LCR_TAB_NAME, lcrPanel);

        // Tab = Map Image
        JPanel mapPanel = new JPanel();
        tabbedPanel.addTab(MAP_TAB_NAME, mapPanel);

    }


    /**
     * Display an Error message.
     *
     * @param errorMessage the error message string
     */
    void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }


    /**
     * Get the System State from the View.
     *
     * @param state the current system state
     * @return the updated system state
     * @throws IOException if the background image cannot be read
     */
    ARTSystemState getViewState(ARTSystemState state) {
        return state;
    }

    /**
     * Update the View from the System State.
     *
     * @param state the current system state
     */
    public void setViewState(ARTSystemState state) {

    }



}
