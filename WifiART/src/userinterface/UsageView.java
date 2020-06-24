package userinterface;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

/**
 * A JPanel representing the view for the Usage Rules/Policy subelement.
 */
public class UsageView extends JPanel {

    // Labels for titled borders
    private static final String STA_LOCATION_POLICY_LABEL = "STA Location Policy: ";

    // Labels and Components
    private final JLabel usagePanelTitle = new JLabel("Usage Rules/Policy");
    private final JCheckBox retransmissionAllowedCheckbox = new JCheckBox("Allow retransmission of LCI information.");
    private final JCheckBox retentionExpiresCheckbox = new JCheckBox("Allow LCI information retention to expire after an amount of time (specify below).");
    private final JLabel expireTimeLabel = new JLabel("    Time (hours): ");
    private final JTextField expireTimeField = new JTextField();
    private final JCheckBox staLocationPolicyCheckbox = new JCheckBox("Additional STA location information exists.");

    /**
     * Constructs the view for the Usage Rules/Policy subelement.
     */
    public UsageView() {
        setup();
    }

    private void setup() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel usagePanelTitlePanel = new JPanel();
        usagePanelTitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        usagePanelTitlePanel.add(usagePanelTitle);
        this.add(usagePanelTitlePanel);
        JPanel retransmissionAllowedCheckboxPanel = new JPanel();
        retransmissionAllowedCheckboxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        retransmissionAllowedCheckboxPanel.add(retransmissionAllowedCheckbox);
        this.add(retransmissionAllowedCheckboxPanel);
        JPanel retentionExpiresCheckboxPanel = new JPanel();
        retentionExpiresCheckboxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        retentionExpiresCheckboxPanel.add(retentionExpiresCheckbox);
        this.add(retentionExpiresCheckboxPanel);

        JPanel expireTimePanelPanel = new JPanel();
        expireTimePanelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel expireTimePanel = new JPanel();
        expireTimePanel.setLayout(new GridLayout(1, 2));
        expireTimePanel.add(expireTimeLabel);
        expireTimePanel.add(expireTimeField);
        expireTimePanelPanel.add(expireTimePanel);
        this.add(expireTimePanelPanel);

        JPanel staLocationPolicyPanelPanel = new JPanel();
        staLocationPolicyPanelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel staLocationPolicyPanel = new JPanel();
        staLocationPolicyPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), STA_LOCATION_POLICY_LABEL));
        staLocationPolicyPanel.add(staLocationPolicyCheckbox);
        staLocationPolicyPanelPanel.add(staLocationPolicyPanel);
        this.add(staLocationPolicyPanelPanel);
    }

}
