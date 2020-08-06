package userinterface;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * A JPanel representing the view for the Co-located BSSID List subelement.
 */
public class BssidView extends JPanel {
    // Constants
    private static final int BSSID_LIST_FIELD_WIDTH = 20;
    private static final int BSSID_LIST_PANEL_WIDTH = 1000;
    private static final int BSSID_LIST_PANEL_HEIGHT = 300;

    // Labels and Components
    private final JLabel bssidPanelTitle = new JLabel("BSSID List Subelement");
    private final JLabel maxBssidIndicatorLabel = new JLabel("Max possible number of BSSs which can share the same antenna connector: ");
    private final JTextField maxBssidIndicatorField = new JTextField();
    private final JLabel bssidAddLabel = new JLabel("Add a new BSSID (e.g. \"1F:2F:3F:4F:5F:6F\"): ");
    private final JTextField bssidAddField = new JTextField();
    private final JButton bssidAddButton = new JButton(" Add ");
    private final JLabel bssidListInstructionsLabel = new JLabel("List of added BSSIDs (Select a BSSID to edit and press return to save changes): ");
    private final JPanel bssidListPanel = new JPanel();

    private final HashMap<StringBuilder, BssidListElement> bssidList = new HashMap<>();

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

        JPanel maxBssidIndicatorPanel = new JPanel();
        maxBssidIndicatorPanel.setLayout(new BoxLayout(maxBssidIndicatorPanel, BoxLayout.X_AXIS));
        maxBssidIndicatorPanel.add(maxBssidIndicatorLabel);
        maxBssidIndicatorPanel.add(maxBssidIndicatorField);
        this.add(maxBssidIndicatorPanel);

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
        bssidListPanel.setPreferredSize(new Dimension(BSSID_LIST_PANEL_WIDTH, BSSID_LIST_PANEL_HEIGHT));
        JScrollPane existingBssidScrollPane = new JScrollPane(bssidListPanel);
        this.add(existingBssidScrollPane);

    }

    /**
     * Get the maximum number of BSSs which can share the same antenna connector.
     *
     * @return the maximum number of BSSs which can share the same antenna connector
     * @throws NumberFormatException if a non-integer value was provided
     */
    public int getMaxBssidIndicator() throws NumberFormatException {
        return Integer.parseInt(maxBssidIndicatorField.getText());
    }

    /**
     * Add a BSSID to the list.
     *
     * @param bssidBuilder the StringBuilder for the BSSID String
     * @param editModeListener the listener for entering edit mode for the BSSID
     * @param newBssidListener the listener for changing the BSSID String to a new BSSID
     * @param removeBssidListener the listener for removing the BSSID from the list
     */
    public void addBssid(StringBuilder bssidBuilder, ActionListener editModeListener,
                         ActionListener newBssidListener, ActionListener removeBssidListener) {
        BssidListElement bssidListElement = new BssidListElement(bssidBuilder, editModeListener,
            newBssidListener, removeBssidListener);
        bssidList.put(bssidBuilder, bssidListElement);
        bssidListPanel.add(bssidListElement);
        bssidListPanel.revalidate();
        bssidListPanel.repaint();
    }

    /**
     * Remove a BSSID from the list.
     *
     * @param bssidBuilder the StringBuilder for the BSSID String
     */
    public void removeBssid(StringBuilder bssidBuilder) {
        BssidListElement bssidListElement = bssidList.remove(bssidBuilder);
        bssidListPanel.remove(bssidListElement);
        bssidListPanel.revalidate();
        bssidListPanel.repaint();
    }

    /**
     * Edit a BSSID, replacing the BSSID String with a new one provided by the user.
     *
     * @param bssidBuilder the StringBuilder for the BSSID String
     */
    public void editBssid(StringBuilder bssidBuilder) {
        BssidListElement bssidListElement = bssidList.get(bssidBuilder);
        bssidListElement.changeBssid(bssidListElement.getEditedBssid());
        bssidListElement.toggleEditMode(); // Leave edit mode
    }

    /**
     * Toggle edit mode for a BSSID.
     *
     * @param bssidBuilder the StringBuilder for the BSSID String
     */
    public void toggleEditMode(StringBuilder bssidBuilder) {
        bssidList.get(bssidBuilder).toggleEditMode();
    }

    /**
     * Get the user-inputted BSSID String to be added to the list.
     *
     * @return the BSSID String to be added to the list
     */
    public String getAddedBssid() {
        return bssidAddField.getText();
    }

    /**
     * Get the user-inputted BSSID String to replace another BSSID in the list.
     *
     * @param bssidBuilder the StringBuilder for the BSSID in the list
     * @return the BSSID String to replace the existing one
     */
    public String getEditedBssid(StringBuilder bssidBuilder) {
        return bssidList.get(bssidBuilder).getEditedBssid();
    }

    /**
     * Add a listener for adding a new BSSID to the list.
     *
     * @param listener the listener for adding a new BSSID to the list
     */
    public void addBssidAddListener(ActionListener listener) {
        bssidAddButton.addActionListener(listener);
    }

    /**
     * Add a listener for the Max BSSID Indicator field.
     *
     * @param listener the listener for the Max BSSID Indicator field
     */
    public void addMaxBssidIndicatorListener(ActionListener listener) {
        maxBssidIndicatorField.addActionListener(listener);
    }

    /**
     * JPanel representing one item in the BSSID list, with GUI components for editing the BSSID
     *  and removing the BSSID from the list.
     */
    private static class BssidListElement extends JPanel {
        private boolean editMode;
        private final StringBuilder bssid;
        private final JCheckBox selectionCheckbox;
        private final JTextField bssidField;
        private final JLabel bssidLabel;
        private final JButton removeBssidButton = new JButton("Delete");

        /**
         * Constructs a BssidListElement JPanel to add to the BSSID list in the GUI.
         *
         * @param bssidBuilder the StringBuilder for the BSSID String
         * @param editModeListener the listener for toggling edit mode for the BSSID
         * @param newBssidListener the listener for changing the BSSID to a new BSSID
         * @param removeBssidListener the listener for removing the BSSID from the list
         */
        BssidListElement(StringBuilder bssidBuilder, ActionListener editModeListener,
                         ActionListener newBssidListener, ActionListener removeBssidListener) {
            this.bssid = bssidBuilder;
            selectionCheckbox = new JCheckBox();
            selectionCheckbox.addActionListener(editModeListener);
            bssidLabel = new JLabel(bssidBuilder.toString());
            bssidField = new JTextField(bssidBuilder.toString(), BSSID_LIST_FIELD_WIDTH);
            bssidField.addActionListener(newBssidListener);
            removeBssidButton.addActionListener(removeBssidListener);

            this.editMode = false;
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.add(selectionCheckbox);
            this.add(bssidLabel);
        }

        /**
         * Change the BSSID String to a new BSSID.
         *
         * @param newBssid the new BSSID String
         */
        void changeBssid(String newBssid) {
            this.bssid.replace(0, this.bssid.length(), newBssid);
            this.bssidLabel.setText(newBssid);
        }

        /**
         * Enter or exit edit mode.
         */
        void toggleEditMode() {
            if (editMode) {
                exitEditMode();
            } else {
                enterEditMode();
            }
        }

        private void enterEditMode() {
            this.remove(bssidLabel);
            this.add(bssidField);
            this.add(removeBssidButton);
            this.revalidate();
            this.repaint();
            this.editMode = true;
        }

        private void exitEditMode() {
            this.remove(bssidField);
            this.remove(removeBssidButton);
            this.add(bssidLabel);
            this.selectionCheckbox.setSelected(false);
            this.revalidate();
            this.repaint();
            this.editMode = false;
        }

        /**
         * Get the new BSSID that the user entered to replace the existing one.
         *
         * @return the new edited BSSID String
         */
        String getEditedBssid() {
            return bssidField.getText();
        }
    }

    /**
     * Displays an error in a pop-up window.
     *
     * @param message the error message to be displayed
     */
    public void displayError(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }

}
