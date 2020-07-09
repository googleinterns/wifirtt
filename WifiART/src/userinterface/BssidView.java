package userinterface;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridLayout;

/**
 * A JPanel representing the view for the Co-located BSSID List subelement.
 */
public class BssidView extends JPanel {

    // Labels and Components
    private final JLabel bssidPanelTitle = new JLabel("BSSID List Subelement");
    private final JLabel bssidAddLabel = new JLabel("Add a new BSSID: ");
    private final JTextField bssidAddField = new JTextField();
    private final JButton bssidAddButton = new JButton(" Add ");
    private final JLabel bssidListInstructionsLabel = new JLabel("List of added BSSIDs (Select a BSSID to edit): ");
    /* TODO(dmevans): Make this JPanel change dynamically to keep a list of added BSSIDs, with
        the ability to edit individual BSSIDs.
     */
    private final JPanel bssidListPanel = new JPanel();

    // TODO(dmevans): Add MaxBSSID Indicator field.

    /**
     * Constructs the view for the Co-located BSSID List subelement.
     */
    public BssidView() {
        setup();
    }

    private void setup() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel bssidPanelTitlePanel = new JPanel();
        bssidPanelTitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bssidPanelTitlePanel.add(bssidPanelTitle);
        this.add(bssidPanelTitlePanel);

        JPanel bssidAddPanel = new JPanel();
        bssidAddPanel.setLayout(new GridLayout(1, 3));
        bssidAddPanel.add(bssidAddLabel);
        bssidAddPanel.add(bssidAddField);
        bssidAddPanel.add(bssidAddButton);
        this.add(bssidAddPanel);

        JPanel bssidListInstructionsLabelPanel = new JPanel();
        bssidListInstructionsLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bssidListInstructionsLabelPanel.add(bssidListInstructionsLabel);
        this.add(bssidListInstructionsLabelPanel);

        bssidListPanel.setLayout(new BoxLayout(bssidListPanel, BoxLayout.Y_AXIS));
        JScrollPane existingBssidScrollPane = new JScrollPane(bssidListPanel);
        this.add(existingBssidScrollPane);
    }

}
