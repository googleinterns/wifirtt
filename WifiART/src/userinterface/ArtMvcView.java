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

import structs.ArtSystemState;
import structs.CivicAddressElements;
import structs.CountryCodes;
import structs.ImageTypes;
import structs.LanguageCodes;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

public class ArtMvcView extends JFrame {

    // Dimensions
    private static final int WINDOW_POSITION_X = 10;
    private static final int WINDOW_POSITION_Y = 10;
    private static final int FRAME_WIDTH = 1080;
    private static final int FRAME_HEIGHT = 480;

    // Constants
    private static final Integer[] LCI_VERSION_LIST = {1};
    private static final String[] ALTITUDE_TYPE_LIST = {"No Altitude Provided", "Meters", "Floors"};
    private static final String[] MAP_DATUM_LIST = {"WGS84", "NAD83 (NAVD88)", "NAD83 (MLLW)"};
    private static final String SELECT_COUNTRY_PROMPT = "SELECT A COUNTRY";
    private static final int NUMBER_OF_COUNTRIES_VIEWABLE = 16;
    private static final String[] LANGUAGE_LIST = LanguageCodes.LANGUAGES_NAMES;
    private static final int NUMBER_OF_LANGUAGES_VIEWABLE = 16;
    private static final String SELECT_ADDRESS_ELEMENT_PROMPT = "SELECT TYPE";
    private static final String SELECT_IMAGE_TYPE_PROMPT = "SELECT IMAGE TYPE";

    // Tab name declarations
    private static final String GENERATE_TAB_NAME = "Generate";
    private static final String LCI_TAB_NAME = "LCI subelement";
    private static final String Z_TAB_NAME = "Z-Axis";
    private static final String USAGE_TAB_NAME = "Usage";
    private static final String BSSID_TAB_NAME = "BSSID List";
    private static final String LCR_TAB_NAME = "Location Civic (Address)";
    private static final String MAP_TAB_NAME = "Map Image";

    // Labels for titled borders
    private static final String LCI_RESERVED_FIELDS_LABEL = "IEEE 802.11y features";
    private static final String LOCATION_MOVEMENT_LABEL = "Location Movement: ";
    private static final String STA_LOCATION_POLICY_LABEL = "STA Location Policy: ";

    // Generate Tab labels and components
    private final JLabel lciSubelementsLabel = new JLabel(" LCI Subelements Included:");
    private final JCheckBox lciCheckbox = new JCheckBox("Location Configuration Information (LCI)");
    private final JCheckBox zCheckbox = new JCheckBox("Z (floor number, height above floor)");
    private final JCheckBox usageCheckbox = new JCheckBox("Usage Rules / Policy");
    private final JCheckBox bssidCheckbox = new JCheckBox("BSSID List (co-located BSS's in Access Point)");
    private final JLabel lcrSubelementsLabel = new JLabel(" LCR Subelements Included:                           ");
    private final JCheckBox lcrCheckbox = new JCheckBox("Location Civic Report (Address)");
    private final JCheckBox mapCheckbox = new JCheckBox("Map Image");
    private final JLabel inputFileNameLabel = new JLabel(" Output File: ");
    private final JTextField inputFileNameField = new JTextField();
    private final JLabel inputDirLabel = new JLabel(" Output Dir: ");
    private final JTextField inputDirField = new JTextField();
    private final JLabel outputFileNameLabel = new JLabel(" Output File: ");
    private final JTextField outputFileNameField = new JTextField();
    private final JLabel outputDirLabel = new JLabel(" Output Dir: ");
    private final JTextField outputDirField = new JTextField();
    private final JLabel bufferTextLabel = new JLabel("Hex Output LCI/LCR Buffer: ");
    private final JTextArea bufferTextArea = new JTextArea();

    // LCI Tab labels and components
    private final JLabel lciTabTitle = new JLabel("Location Configuration Information Subelement");
    private final JLabel lciVersionComboBoxLabel = new JLabel("LCI version: ");
    private final JComboBox<Integer> lciVersionComboBox = new JComboBox<>(LCI_VERSION_LIST);
    private final JLabel latitudeFieldLabel = new JLabel("                    Latitude (degrees): ");
    private final JTextField latitudeField = new JTextField();
    private final JLabel latitudeUncertaintyFieldLabel = new JLabel("    Latitude Uncertainty (deg.): ");
    private final JTextField latitudeUncertaintyField = new JTextField();
    private final JLabel longitudeFieldLabel = new JLabel("                 Longitude (degrees): ");
    private final JTextField longitudeField = new JTextField();
    private final JLabel longitudeUncertaintyFieldLabel = new JLabel(" Longitude Uncertainty (deg.): ");
    private final JTextField longitudeUncertaintyField = new JTextField();
    private final JLabel altitudeFieldLabel = new JLabel(" Altitude (choose units below): ");
    private final JTextField altitudeField = new JTextField();
    private final JLabel altitudeUncertaintyFieldLabel = new JLabel("                Altitude Uncertainty: ");
    private final JTextField altitudeUncertaintyField = new JTextField();
    private final JLabel altitudeTypeComboBoxLabel = new JLabel(" Altitude Units: ");
    private final JComboBox<String> altitudeTypeComboBox = new JComboBox<>(ALTITUDE_TYPE_LIST);
    private final JLabel mapDatumComboBoxLabel = new JLabel(" Altitude Datum: ");
    private final JComboBox<String> mapDatumComboBox = new JComboBox<>(MAP_DATUM_LIST);
    private final JCheckBox regLocAgreementCheckbox = new JCheckBox("<html>The STA is operating within a national policy area "
        + "<br> or an international agreement area near a national border.</html>");
    private final JCheckBox regLocDseCheckbox = new JCheckBox("The enabling STA is enabling the operation of STAs with DSE.");
    private final JCheckBox dependentStaCheckbox = new JCheckBox("The STA is operating with the enablement of the enabling "
        + "STA whose LCI is being reported.");

    // Z Tab labels and components
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

    // Usage Tab labels and components
    private final JLabel usagePanelTitle = new JLabel("Usage Rules/Policy");
    private final JCheckBox retransmissionAllowedCheckbox = new JCheckBox("Allow retransmission of LCI information.");
    private final JCheckBox retentionExpiresCheckbox = new JCheckBox("Allow LCI information retention to expire after an amount of time (specify below).");
    private final JLabel expireTimeLabel = new JLabel("    Time (hours): ");
    private final JTextField expireTimeField = new JTextField();
    private final JCheckBox staLocationPolicyCheckbox = new JCheckBox("Additional STA location information exists.");

    // BSSID Tab labels and components
    private final JLabel bssidPanelTitle = new JLabel("BSSID List Subelement");
    private final JLabel bssidAddLabel = new JLabel("Add a new BSSID: ");
    private final JTextField bssidAddField = new JTextField();
    private final JButton bssidAddButton = new JButton(" Add ");
    private final JLabel bssidListInstructionsLabel = new JLabel("List of added BSSIDs (Select a BSSID to edit): ");
    /* TODO(dmevans): Make this JPanel change dynamically to keep a list of added BSSIDs, with
        the ability to edit individual BSSIDs.
     */
    private final JPanel bssidListPanel = new JPanel();

    // LCR Tab labels and components
    private final JLabel lcrPanelTitle = new JLabel("Location Civic (Address) Subelement");
    private final JLabel countriesComboboxLabel = new JLabel(" Country: ");
    private List<String> countryList = CountryCodes.COUNTRIES_NAMES_LIST;
    private final JComboBox<String> countriesCombobox = new JComboBox<>(
        getArrayWithSelectionPrompt(countryList, SELECT_COUNTRY_PROMPT));
    private final JLabel addAddressElementLabel = new JLabel("Add individual address elements below: ");
    private final JLabel languageComboboxLabel = new JLabel("Language: ");
    private final JLabel addressElementTypeComboboxLabel = new JLabel("Address Element Type: ");
    private final JLabel addressElementFieldLabel = new JLabel("Name: ");
    private final JComboBox<String> languageCombobox = new JComboBox<>(LANGUAGE_LIST);
    private List<String> addressElementList = CivicAddressElements.ADDRESS_ELEMENT_LIST;
    private final JComboBox<String> addressElementTypeCombobox = new JComboBox<>(
        getArrayWithSelectionPrompt(addressElementList, SELECT_ADDRESS_ELEMENT_PROMPT));
    private final JTextField addressElementField = new JTextField();
    private final JButton addAddressElementsButton = new JButton("Add");
    /* TODO(dmevans): Make this JPanel change dynamically to keep a list of the
        new address elements added, with the ability to edit individual elements.
     */
    private final JPanel addressElementsListPanel = new JPanel();

    // Map Image Tab labels and components
    private final JLabel mapPanelTitle = new JLabel("Map Image Subelement");
    private List<String> imageTypesList = ImageTypes.IMAGE_TYPES_LIST;
    private final JComboBox<String> mapImageTypeCombobox = new JComboBox<>(
        getArrayWithSelectionPrompt(imageTypesList, SELECT_IMAGE_TYPE_PROMPT));
    private final JLabel mapUrlFieldLabel = new JLabel(" Enter Map URL: ");
    private final JTextField mapUrlField = new JTextField();
    private final JLabel imagePreviewLabel = new JLabel(" Image Preview: ");
    // TODO(dmevans): Make the "imagePreview" object show the image as entered by the user.
    private final JPanel imagePreview = new JPanel();


    /** Constructor.
     *
     * @param state the system state
     * */
    public ArtMvcView(ArtSystemState state) {
        // Top level JFrame
        this.setTitle("LCI/LCR Tools");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLocation(WINDOW_POSITION_X, WINDOW_POSITION_Y);
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Main Panel Tabbed Pane
        JTabbedPane tabbedPanel = new JTabbedPane();
        getContentPane().add(tabbedPanel);

        // Tab = Generate
        JPanel generatePanel = new JPanel();
        setupGeneratePanel(generatePanel);
        tabbedPanel.addTab(GENERATE_TAB_NAME, generatePanel);

        // Tab = LCI
        JPanel lciPanel = new JPanel();
        setupLciPanel(lciPanel);
        tabbedPanel.addTab(LCI_TAB_NAME, lciPanel);

        // Tab = Z
        JPanel zPanel = new JPanel();
        setupZPanel(zPanel);
        tabbedPanel.addTab(Z_TAB_NAME, zPanel);

        // Tab = Usage
        JPanel usagePanel = new JPanel();
        setupUsagePanel(usagePanel);
        tabbedPanel.addTab(USAGE_TAB_NAME, usagePanel);

        // Tab = BSSID
        JPanel bssidPanel = new JPanel();
        setupBssidPanel(bssidPanel);
        tabbedPanel.addTab(BSSID_TAB_NAME, bssidPanel);

        // Tab = LCR
        JPanel lcrPanel = new JPanel();
        setupLcrPanel(lcrPanel);
        tabbedPanel.addTab(LCR_TAB_NAME, lcrPanel);

        // Tab = Map Image
        JPanel mapPanel = new JPanel();
        setupMapPanel(mapPanel);
        tabbedPanel.addTab(MAP_TAB_NAME, mapPanel);
    }

    // Setting up the "Generate" panel.
    private void setupGeneratePanel(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel subelementChooserPanel = new JPanel();
        subelementChooserPanel.setLayout(new BoxLayout(subelementChooserPanel, BoxLayout.X_AXIS));
        JPanel lciSubelementChooserPanel = new JPanel();
        setupLciSubelementChooserPanel(lciSubelementChooserPanel);
        subelementChooserPanel.add(lciSubelementChooserPanel);
        subelementChooserPanel.add(Box.createHorizontalGlue());
        JPanel lcrSubelementChooserPanel = new JPanel();
        setupLcrSubelementChooserPanel(lcrSubelementChooserPanel);
        subelementChooserPanel.add(lcrSubelementChooserPanel);
        subelementChooserPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(subelementChooserPanel);

        JPanel inputFileChooserPanel = new JPanel();
        setupInputFileChooserPanel(inputFileChooserPanel);
        panel.add(inputFileChooserPanel);

        JPanel outputFileChooserPanel = new JPanel();
        setupOutputFileChooserPanel(outputFileChooserPanel);
        panel.add(outputFileChooserPanel);

        JPanel bufferTextPanel = new JPanel();
        bufferTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bufferTextPanel.add(bufferTextLabel);
        panel.add(bufferTextPanel);
        bufferTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(bufferTextArea);

    }
    private void setupLciSubelementChooserPanel(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(lciSubelementsLabel);
        panel.add(lciCheckbox);
        panel.add(zCheckbox);
        panel.add(usageCheckbox);
        panel.add(bssidCheckbox);
        panel.setAlignmentY(Component.TOP_ALIGNMENT);
    }
    private void setupLcrSubelementChooserPanel(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(lcrSubelementsLabel);
        panel.add(lcrCheckbox);
        panel.add(mapCheckbox);
        panel.setAlignmentY(Component.TOP_ALIGNMENT);
    }
    private void setupInputFileChooserPanel(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(inputFileNameLabel);
        panel.add(inputFileNameField);
        panel.add(inputDirLabel);
        panel.add(inputDirField);
    }
    private void setupOutputFileChooserPanel(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(outputFileNameLabel);
        panel.add(outputFileNameField);
        panel.add(outputDirLabel);
        panel.add(outputDirField);
    }

    // Setting up the panel for the LCI subelement.
    private void setupLciPanel(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.add(lciTabTitle);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(lciVersionComboBoxLabel);
        headerPanel.add(lciVersionComboBox);
        panel.add(headerPanel);

        JPanel latitudePanel = new JPanel();
        latitudePanel.setLayout(new BoxLayout(latitudePanel, BoxLayout.X_AXIS));
        latitudePanel.add(latitudeFieldLabel);
        latitudePanel.add(latitudeField);
        latitudePanel.add(latitudeUncertaintyFieldLabel);
        latitudePanel.add(latitudeUncertaintyField);
        panel.add(latitudePanel);

        JPanel longitudePanel = new JPanel();
        longitudePanel.setLayout(new BoxLayout(longitudePanel, BoxLayout.X_AXIS));
        longitudePanel.add(longitudeFieldLabel);
        longitudePanel.add(longitudeField);
        longitudePanel.add(longitudeUncertaintyFieldLabel);
        longitudePanel.add(longitudeUncertaintyField);
        panel.add(longitudePanel);

        JPanel altitudePanel = new JPanel();
        altitudePanel.setLayout(new BoxLayout(altitudePanel, BoxLayout.X_AXIS));
        altitudePanel.add(altitudeFieldLabel);
        altitudePanel.add(altitudeField);
        altitudePanel.add(altitudeUncertaintyFieldLabel);
        altitudePanel.add(altitudeUncertaintyField);
        panel.add(altitudePanel);

        JPanel altitudeTypeAndDatumPanel = new JPanel();
        altitudeTypeAndDatumPanel.setLayout(new BoxLayout(altitudeTypeAndDatumPanel, BoxLayout.X_AXIS));
        altitudeTypeAndDatumPanel.add(altitudeTypeComboBoxLabel);
        altitudeTypeAndDatumPanel.add(altitudeTypeComboBox);
        altitudeTypeAndDatumPanel.add(Box.createHorizontalGlue());
        altitudeTypeAndDatumPanel.add(mapDatumComboBoxLabel);
        altitudeTypeAndDatumPanel.add(mapDatumComboBox);
        panel.add(altitudeTypeAndDatumPanel);

        JPanel lciReservedFieldsPanel = new JPanel();
        setupLciReservedFieldsPanel(lciReservedFieldsPanel);
        panel.add(lciReservedFieldsPanel);

    }
    private void setupLciReservedFieldsPanel(JPanel lciReservedFieldsPanel) {
        lciReservedFieldsPanel.setLayout(new BoxLayout(lciReservedFieldsPanel, BoxLayout.Y_AXIS));
        lciReservedFieldsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), LCI_RESERVED_FIELDS_LABEL));
        JPanel regLocAgreementCheckboxPanel = new JPanel();
        regLocAgreementCheckboxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        regLocAgreementCheckboxPanel.add(regLocAgreementCheckbox);
        lciReservedFieldsPanel.add(regLocAgreementCheckboxPanel);
        JPanel regLocDseCheckboxPanel = new JPanel();
        regLocDseCheckboxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        regLocDseCheckboxPanel.add(regLocDseCheckbox);
        lciReservedFieldsPanel.add(regLocDseCheckboxPanel);
        JPanel dependentStaCheckboxPanel = new JPanel();
        dependentStaCheckboxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        dependentStaCheckboxPanel.add(dependentStaCheckbox);
        lciReservedFieldsPanel.add(dependentStaCheckboxPanel);
    }

    // Setting up the panel for the Z subelement.
    private void setupZPanel(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Panel containing the title for this tab.
        JPanel zPanelTitlePanel = new JPanel();
        zPanelTitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        zPanelTitlePanel.add(zPanelTitle);
        panel.add(zPanelTitlePanel);

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
        panel.add(zValuesPanelPanel);

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
        panel.add(locationMovementPanelPanel);

    }

    // Setting up the panel for the Usage Rules/Policy subelement.
    private void setupUsagePanel(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel usagePanelTitlePanel = new JPanel();
        usagePanelTitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        usagePanelTitlePanel.add(usagePanelTitle);
        panel.add(usagePanelTitlePanel);
        JPanel retransmissionAllowedCheckboxPanel = new JPanel();
        retransmissionAllowedCheckboxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        retransmissionAllowedCheckboxPanel.add(retransmissionAllowedCheckbox);
        panel.add(retransmissionAllowedCheckboxPanel);
        JPanel retentionExpiresCheckboxPanel = new JPanel();
        retentionExpiresCheckboxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        retentionExpiresCheckboxPanel.add(retentionExpiresCheckbox);
        panel.add(retentionExpiresCheckboxPanel);

        JPanel expireTimePanelPanel = new JPanel();
        expireTimePanelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel expireTimePanel = new JPanel();
        expireTimePanel.setLayout(new GridLayout(1, 2));
        expireTimePanel.add(expireTimeLabel);
        expireTimePanel.add(expireTimeField);
        expireTimePanelPanel.add(expireTimePanel);
        panel.add(expireTimePanelPanel);

        JPanel staLocationPolicyPanelPanel = new JPanel();
        staLocationPolicyPanelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel staLocationPolicyPanel = new JPanel();
        staLocationPolicyPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), STA_LOCATION_POLICY_LABEL));
        staLocationPolicyPanel.add(staLocationPolicyCheckbox);
        staLocationPolicyPanelPanel.add(staLocationPolicyPanel);
        panel.add(staLocationPolicyPanelPanel);
    }

    // Setting up the panel for the Co-located BSSID List subelement.
    private void setupBssidPanel(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(bssidPanelTitle);

        JPanel bssidAddPanel = new JPanel();
        bssidAddPanel.setLayout(new GridLayout(1, 3));
        bssidAddPanel.add(bssidAddLabel);
        bssidAddPanel.add(bssidAddField);
        bssidAddPanel.add(bssidAddButton);
        panel.add(bssidAddPanel);

        JPanel bssidListInstructionsLabelPanel = new JPanel();
        bssidListInstructionsLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bssidListInstructionsLabelPanel.add(bssidListInstructionsLabel);
        panel.add(bssidListInstructionsLabelPanel);

        bssidListPanel.setLayout(new BoxLayout(bssidListPanel, BoxLayout.Y_AXIS));
        JScrollPane existingBssidScrollPane = new JScrollPane(bssidListPanel);
        panel.add(existingBssidScrollPane);
    }

    // Setting up the panel for the Location Civic subelement.
    private void setupLcrPanel(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(lcrPanelTitle);

        // Panel for choosing the country.
        JPanel countryChooserPanel = new JPanel();
        countryChooserPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        countryChooserPanel.add(countriesComboboxLabel);
        countriesCombobox.setMaximumRowCount(NUMBER_OF_COUNTRIES_VIEWABLE);
        countryChooserPanel.add(countriesCombobox);
        panel.add(countryChooserPanel);

        // Panel containing the prompt to add civic address elements.
        JPanel addAddressElementsPromptPanel = new JPanel();
        addAddressElementsPromptPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addAddressElementsPromptPanel.add(addAddressElementLabel);
        panel.add(addAddressElementsPromptPanel);

        // Panel allowing user to add new address elements.
        JPanel addAddressElementsPanel = new JPanel();
        addAddressElementsPanel.setLayout(new GridLayout(2, 4));
        addAddressElementsPanel.add(languageComboboxLabel);           // Row 1, Col 1
        addAddressElementsPanel.add(addressElementTypeComboboxLabel); // Row 1, Col 2
        addAddressElementsPanel.add(addressElementFieldLabel);        // Row 1, Col 3
        addAddressElementsPanel.add(new JPanel());                    // Row 1, Col 4

        languageCombobox.setMaximumRowCount(NUMBER_OF_LANGUAGES_VIEWABLE);
        languageCombobox.setSelectedItem("English");
        addAddressElementsPanel.add(languageCombobox);                // Row 2, Col 1
        addAddressElementsPanel.add(addressElementTypeCombobox);      // Row 2, Col 2
        addAddressElementsPanel.add(addressElementField);             // Row 2, Col 3
        addAddressElementsPanel.add(addAddressElementsButton);        // Row 2, Col 4
        panel.add(addAddressElementsPanel);

        // Panel containing the list of added address elements.
        addressElementsListPanel.setLayout(new BoxLayout(addressElementsListPanel, BoxLayout.Y_AXIS));
        JScrollPane existingAddressElementsScrollPane = new JScrollPane(addressElementsListPanel);
        panel.add(existingAddressElementsScrollPane);
    }

    // Setting up the panel for the Map Image subelement.
    private void setupMapPanel(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(mapPanelTitle);
        panel.add(mapImageTypeCombobox);

        // Panel for entering the map URL.
        JPanel mapUrlPanel = new JPanel();
        mapUrlPanel.setLayout(new BoxLayout(mapUrlPanel, BoxLayout.X_AXIS));
        mapUrlPanel.add(mapUrlFieldLabel);
        mapUrlPanel.add(mapUrlField);
        panel.add(mapUrlPanel);

        // Panel for displaying the image preview.
        JPanel imagePreviewLabelPanel = new JPanel();
        imagePreviewLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        imagePreviewLabelPanel.add(imagePreviewLabel);
        panel.add(imagePreviewLabelPanel);
        panel.add(imagePreview);

    }

    /**
     * Given a List of elements and a selection prompt, returns a combined array for use in a JComboBox.
     * @param list The List containing the elements.
     * @param selectionPrompt The default option prompting the user to select an option.
     * @return An array containing the elements, with the selection prompt at index 0.
     */
    private String[] getArrayWithSelectionPrompt(List<String> list, String selectionPrompt) {
        list.add(0, selectionPrompt);
        String[] resultArray = new String[list.size()];
        resultArray = list.toArray(resultArray);
        return resultArray;
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
     */
    ArtSystemState getViewState(ArtSystemState state) {
        return state;
    }

    /**
     * Update the View from the System State.
     *
     * @param state the current system state
     */
    public void setViewState(ArtSystemState state) {

    }
}

