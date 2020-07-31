package userinterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * A JPanel representing the view for the Co-located BSSID List subelement.
 */
public class BssidView extends JPanel {
    // Constants
    private static final int BSSID_LIST_FIELD_WIDTH = 20;

    // Labels and Components
    private final JLabel bssidPanelTitle = new JLabel("BSSID List Subelement");
    private final JLabel maxBssidIndicatorLabel = new JLabel("Max possible number of BSSs which can share the same antenna connector: ");
    private final JTextField maxBssidIndicatorField = new JTextField();
    private final JLabel bssidAddLabel = new JLabel("Add a new BSSID: ");
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
        bssidListPanel.setPreferredSize(new Dimension(1000, 300));
        JScrollPane existingBssidScrollPane = new JScrollPane(bssidListPanel);
        this.add(existingBssidScrollPane);

    }

    public void addBssid(StringBuilder bssidBuilder, ActionListener editListener, ActionListener newBssidListener) {
        BssidListElement bssidListElement = new BssidListElement(bssidBuilder, editListener, newBssidListener);
        bssidList.put(bssidBuilder, bssidListElement);
        System.out.println("here");
        bssidListPanel.add(bssidListElement);
        bssidListPanel.revalidate();
        bssidListPanel.repaint();
    }
    public void removeBssid(StringBuilder bssidBuilder) {
        BssidListElement bssidListElement = bssidList.remove(bssidBuilder);
        bssidListPanel.remove(bssidListElement);
    }
    public void editBssid(StringBuilder bssidBuilder, String newBssid) {
        bssidList.get(bssidBuilder).changeBssid(newBssid);
    }

    public void toggleEditMode(StringBuilder bssidBuilder) {
        bssidList.get(bssidBuilder).toggleEditMode();
    }

    public String getEditedBssid(StringBuilder bssidBuilder) {
        return bssidList.get(bssidBuilder).getEditedBssid();
    }

    public String getAddedBssid() {
        return bssidAddField.getText();
    }

    public void addBssidAddListener(ActionListener listener) {
        bssidAddButton.addActionListener(listener);
    }



    private static class BssidListElement extends JPanel {
        private boolean editMode;
        final StringBuilder bssid;
        final JCheckBox selectionCheckbox;
        final JTextField bssidField;
        final JLabel bssidLabel;
        final JButton removeBssidButton = new JButton("Delete");

        BssidListElement(StringBuilder bssidBuilder, ActionListener editModeListener, ActionListener newBssidListener) {
            this.bssid = bssidBuilder;
            selectionCheckbox = new JCheckBox();
            selectionCheckbox.addActionListener(editModeListener);
            bssidLabel = new JLabel(bssidBuilder.toString());
            bssidField = new JTextField(bssidBuilder.toString(), BSSID_LIST_FIELD_WIDTH);
            bssidField.addActionListener(newBssidListener);

            this.editMode = false;
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.add(selectionCheckbox);
            this.add(bssidLabel);
        }
        void changeBssid(String newBssid) {
            this.bssid.replace(0, this.bssid.length(), newBssid);
            this.bssidLabel.setText(newBssid);
        }
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
            this.revalidate();
            this.repaint();
            this.editMode = false;
        }

        String getEditedBssid() {
            return bssidField.getText();
        }
    }

}
