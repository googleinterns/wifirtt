package userinterface;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

/**
 * A JPanel representing the view for the Z subelement.
 */
public class ZView extends JPanel {

    // Labels for titled borders
    private static final String LOCATION_MOVEMENT_LABEL = "Location Movement: ";

    // Labels and Components
    private final JLabel zPanelTitle = new JLabel("Z-Axis Subelement");
    private final JLabel floorLabel = new JLabel("STA Floor Number: ");
    private final JTextField floorField = new JTextField();
    private final JLabel heightAboveFloorLabel = new JLabel("<html>STA Height <br>Above Floor (m): </html>");
    private final JTextField heightAboveFloorField = new JTextField();
    private final JLabel heightAboveFloorUncertaintyLabel = new JLabel("<html>STA Height Above "
        + "<br>Floor Uncertainty (m): </html>");
    private final JTextField heightAboveFloorUncertaintyField = new JTextField();
    private final JRadioButton fixedLocationMovementRadioButton = new JRadioButton("STA is not expected to change location.");
    private final JRadioButton variableLocationMovementRadioButton = new JRadioButton("STA is expected to change location.");
    private final JRadioButton unknownLocationMovementRadioButton = new JRadioButton("Movement patterns are unknown.");

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

}

