package userinterface;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

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

    // Getters

    /**
     * Get the value of the RetransmissionAllowed parameter from the view.
     *
     * @return the value of the RetransmissionAllowed parameter.
     */
    public boolean getRetransmissionAllowed() {
        return retransmissionAllowedCheckbox.isSelected();
    }

    /**
     * Get the value of the RetentionExpires parameter from the view.
     *
     * @return the value of the RetentionExpires parameter.
     */
    public boolean getRetentionExpires() {
        return retentionExpiresCheckbox.isSelected();
    }

    /**
     * Get the value of the ExpireTime parameter from the view.
     *
     * @return the value of the ExpireTime parameter.
     * @throws NumberFormatException If the user did not enter an integer value.
     */
    public int getExpireTime() throws NumberFormatException {
        return Integer.parseInt(expireTimeField.getText());
    }

    /**
     * Get the value of the StaLocationPolicy parameter from the view.
     *
     * @return the value of the StaLocationPolicy parameter.
     */
    public boolean getStaLocationPolicy() {
        return staLocationPolicyCheckbox.isSelected();
    }

    // Methods for adding listeners

    /**
     * Add a listener for the RetransmissionAllowed parameter.
     *
     * @param listener the ActionListener for the RetransmissionAllowed parameter.
     */
    public void setRetransmissionAllowedListener(ActionListener listener) {
        retransmissionAllowedCheckbox.addActionListener(listener);
    }

    /**
     * Add a listener for the RetentionExpires parameter.
     *
     * @param listener the ActionListener for the RetentionExpires parameter.
     */
    public void setRetentionExpiresListener(ActionListener listener) {
        retentionExpiresCheckbox.addActionListener(listener);
    }

    /**
     * Add a listener for the ExpireTime parameter.
     *
     * @param listener the ActionListener for the ExpireTime parameter.
     */
    public void setExpireTimeListener(ActionListener listener) {
        expireTimeField.addActionListener(listener);
    }

    /**
     * Add a listener for the StaLocationPolicy parameter.
     *
     * @param listener the ActionListener for the StaLocationPolicy parameter.
     */
    public void setStaLocationPolicyListener(ActionListener listener) {
        staLocationPolicyCheckbox.addActionListener(listener);
    }

    /**
     * Displays an error in a pop-up window.
     *
     * @param message The error message to be displayed.
     */
    public void displayError(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }

}
