/*
Copyright 2020 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package userinterface;

import structs.ExpectedToMove;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

/**
 * A JPanel representing the view for the Z subelement.
 */
public class ZView extends JPanel {

    // Labels for titled borders
    private static final String LOCATION_MOVEMENT_LABEL = "Location Movement: ";

    // Radio button labels
    public static final String NOT_EXPECTED_TO_MOVE_LABEL = "STA is not expected to change location.";
    public static final String EXPECTED_TO_MOVE_LABEL = "STA is expected to change location.";
    public static final String MOVEMENT_UNKNOWN_LABEL = "Movement patterns are unknown.";


    // Labels and Components
    private final JLabel zPanelTitle = new JLabel("Z-Axis Subelement");
    private final JLabel floorLabel = new JLabel("STA Floor Number: ");
    private final JTextField floorField = new JTextField();
    private final JLabel heightAboveFloorLabel = new JLabel("<html>STA Height <br>Above Floor (m): </html>");
    private final JTextField heightAboveFloorField = new JTextField();
    private final JLabel heightAboveFloorUncertaintyLabel = new JLabel("<html>STA Height Above "
        + "<br>Floor Uncertainty (m): </html>");
    private final JTextField heightAboveFloorUncertaintyField = new JTextField();
    private final JRadioButton fixedLocationMovementRadioButton = new JRadioButton(NOT_EXPECTED_TO_MOVE_LABEL);
    private final JRadioButton variableLocationMovementRadioButton = new JRadioButton(EXPECTED_TO_MOVE_LABEL);
    private final JRadioButton unknownLocationMovementRadioButton = new JRadioButton(MOVEMENT_UNKNOWN_LABEL);

    /**
     * Constructs the view for the Z subelement.
     */
    public ZView() {
        setup();
    }

    private void setup() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Panel containing the title for this tab.
        JPanel zPanelTitlePanel = new JPanel();
        zPanelTitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        zPanelTitlePanel.add(zPanelTitle);
        this.add(zPanelTitlePanel);

        // Panel to set up the Z-Axis values.
        JPanel zValuesPanelPanel = new JPanel();
        zValuesPanelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel zValuesPanel = new JPanel();
        zValuesPanel.setLayout(new GridLayout(2, 4));
        zValuesPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // STA Floor Number
        JPanel floorLabelPanel = new JPanel();
        floorLabelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        floorLabelPanel.add(floorLabel);
        zValuesPanel.add(floorLabelPanel);                       // Row 1, Col 1
        zValuesPanel.add(floorField);                            // Row 1, Col 2
        zValuesPanel.add(new JPanel());                          // Row 1, Col 3
        zValuesPanel.add(new JPanel());                          // Row 1, Col 4

        // STA Height above floor
        JPanel heightAboveFloorLabelPanel = new JPanel();
        heightAboveFloorLabelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        heightAboveFloorLabelPanel.add(heightAboveFloorLabel);
        zValuesPanel.add(heightAboveFloorLabelPanel);
        zValuesPanel.add(heightAboveFloorField);

        // STA Height above floor uncertainty
        JPanel heightAboveFloorUncertaintyLabelPanel = new JPanel();
        heightAboveFloorUncertaintyLabelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        heightAboveFloorUncertaintyLabelPanel.add(heightAboveFloorUncertaintyLabel);
        zValuesPanel.add(heightAboveFloorUncertaintyLabelPanel); // Row 2, Col 1
        zValuesPanel.add(heightAboveFloorUncertaintyField);      // Row 2, Col 2
        zValuesPanelPanel.add(zValuesPanel);
        this.add(zValuesPanelPanel);

        // Panel to set the Location Movement field, with three radio buttons.
        JPanel locationMovementPanelPanel = new JPanel();
        locationMovementPanelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel locationMovementPanel = new JPanel();
        locationMovementPanel.setLayout(new BoxLayout(locationMovementPanel, BoxLayout.Y_AXIS));

        // Include the subtitle for this part of the panel using a titled border.
        locationMovementPanel.setBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), LOCATION_MOVEMENT_LABEL));

        // Add the three buttons.
        locationMovementPanel.add(fixedLocationMovementRadioButton);
        locationMovementPanel.add(variableLocationMovementRadioButton);
        locationMovementPanel.add(unknownLocationMovementRadioButton);

        // Group the buttons into a button group so only one at a time may be selected.
        ButtonGroup locationMovementButtonGroup = new ButtonGroup();
        locationMovementButtonGroup.add(fixedLocationMovementRadioButton);
        locationMovementButtonGroup.add(variableLocationMovementRadioButton);
        locationMovementButtonGroup.add(unknownLocationMovementRadioButton);
        locationMovementPanelPanel.add(locationMovementPanel);
        this.add(locationMovementPanelPanel);
    }

    // Getters

    /**
     * Get the floor number.
     *
     * @return The floor number.
     * @throws NumberFormatException If the user did not enter an integer.
     */
    public int getFloor() throws NumberFormatException {
        return Integer.parseInt(floorField.getText());
    }

    /**
     * Get the height above the floor.
     *
     * @return The height above the floor, in meters.
     * @throws NumberFormatException If the user did not enter a decimal number.
     */
    public double getHeightAboveFloorMeters() throws NumberFormatException {
        return Double.parseDouble(heightAboveFloorField.getText());
    }

    /**
     * Get the uncertainty for the height above the floor.
     *
     * @return The height above floor uncertainty, in meters.
     * @throws NumberFormatException If the user did not enter a decimal number.
     */
    public double getHeightAboveFloorUncertaintyMeters() throws NumberFormatException {
        return Double.parseDouble(heightAboveFloorUncertaintyField.getText());
    }

    /**
     * Get the String descriptor for the STA location movement.
     *
     * @return The String descriptor.
     */
    public ExpectedToMove getExpectedToMove() {
        if (fixedLocationMovementRadioButton.isSelected()) {
            return ExpectedToMove.NOT_EXPECTED_TO_MOVE;
        } else if (variableLocationMovementRadioButton.isSelected()) {
            return ExpectedToMove.EXPECTED_TO_MOVE;
        } else if (unknownLocationMovementRadioButton.isSelected()) {
            return ExpectedToMove.MOVEMENT_UNKNOWN;
        }
        return null; // Only null if the user has not chosen an option yet.
    }

    // Methods for adding listeners

    /**
     * Adds a listener for the Floor Number parameter.
     *
     * @param listener The listener being added.
     */
    public void addFloorListener(ActionListener listener) {
        floorField.addActionListener(listener);
    }

    /**
     * Adds a listener for the Height Above Floor parameter.
     *
     * @param listener The listener being added.
     */
    public void addHeightAboveFloorListener(ActionListener listener) {
        heightAboveFloorField.addActionListener(listener);
    }

    /**
     * Adds a listener for the Height Above Floor Uncertainty parameter.
     *
     * @param listener The listener being added.
     */
    public void addHeightAboveFloorUncertaintyListener(ActionListener listener) {
        heightAboveFloorUncertaintyField.addActionListener(listener);
    }

    /**
     * Adds a listener for the Expected To Move (location movement) parameter.
     *
     * @param listener The listener being added.
     */
    public void addLocationMovementListener(ActionListener listener) {
        fixedLocationMovementRadioButton.addActionListener(listener);
        variableLocationMovementRadioButton.addActionListener(listener);
        unknownLocationMovementRadioButton.addActionListener(listener);
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

