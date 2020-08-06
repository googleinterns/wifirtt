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
import structs.SubelementName;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The master view class including GUI elements not specific to any subelement.
 */
public class ArtMvcView extends JFrame {

    // Dimensions
    private static final int WINDOW_POSITION_X = 10;
    private static final int WINDOW_POSITION_Y = 10;
    private static final int FRAME_WIDTH = 1080;
    private static final int FRAME_HEIGHT = 480;

    // Tab name declarations
    private static final String GENERATE_TAB_NAME = "Generate";
    private static final String LCI_TAB_NAME = "LCI subelement";
    private static final String Z_TAB_NAME = "Z-Axis";
    private static final String USAGE_TAB_NAME = "Usage";
    private static final String BSSID_TAB_NAME = "BSSID List";
    private static final String LCR_TAB_NAME = "Location Civic (Address)";
    private static final String MAP_TAB_NAME = "Map Image";
    private static final SubelementName[] SUBELEMENT_NAMES_PER_TAB = {
        null,
        SubelementName.LCI,
        SubelementName.Z,
        SubelementName.USAGE,
        SubelementName.BSSID,
        SubelementName.LCR,
        SubelementName.MAP
    };

    // Generate Tab labels and components
    private final JLabel lciSubelementsLabel = new JLabel(" LCI Subelements Included:");
    private final JCheckBox lciCheckbox = new JCheckBox("Location Configuration Information (LCI)");
    private final JCheckBox zCheckbox = new JCheckBox("Z (floor number, height above floor)");
    private final JCheckBox usageCheckbox = new JCheckBox("Usage Rules / Policy");
    private final JCheckBox bssidCheckbox = new JCheckBox("BSSID List (co-located BSS's in Access Point)");
    private final JLabel lcrSubelementsLabel = new JLabel(" LCR Subelements Included:                           ");
    private final JCheckBox lcrCheckbox = new JCheckBox("Location Civic Report (Address)");
    private final JCheckBox mapCheckbox = new JCheckBox("Map Image");
    private final JLabel inputFileNameLabel = new JLabel(" Input File: ");
    private final JTextField inputFileNameField = new JTextField();
    private final JLabel inputDirLabel = new JLabel(" Input Dir: ");
    private final JTextField inputDirField = new JTextField();
    private final JLabel outputFileNameLabel = new JLabel(" Output File: ");
    private final JTextField outputFileNameField = new JTextField();
    private final JLabel outputDirLabel = new JLabel(" Output Dir: ");
    private final JTextField outputDirField = new JTextField();
    private final JLabel bufferTextLabel = new JLabel("Hex Output LCI/LCR Buffer: ");
    private final JTextArea bufferTextArea = new JTextArea();
    private final JButton generateButton = new JButton("Generate updated buffer");
    private final JCheckBox readableBufferCheckbox = new JCheckBox("Readable format ");
    private final JCheckBox androidVersionCheckbox = new JCheckBox("Android version S or later");

    private final JTabbedPane tabbedPanel = new JTabbedPane();


    // Sub-View JPanels for each subelement
    private final LciView lciView = new LciView();
    private final ZView zView = new ZView();
    private final UsageView usageView = new UsageView();
    private final BssidView bssidView = new BssidView();
    private final LcrView lcrView = new LcrView();
    private final MapView mapView = new MapView();


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
        getContentPane().add(tabbedPanel);

        // Tab = Generate
        JPanel generatePanel = new JPanel();
        setupGeneratePanel(generatePanel);
        tabbedPanel.addTab(GENERATE_TAB_NAME, generatePanel);

        // LCI subelement
        tabbedPanel.addTab(LCI_TAB_NAME, lciView);
        // Z subelement
        tabbedPanel.addTab(Z_TAB_NAME, zView);
        // Usage Rules/Policy subelement
        tabbedPanel.addTab(USAGE_TAB_NAME, usageView);
        // Co-located BSSID List subelement
        tabbedPanel.addTab(BSSID_TAB_NAME, bssidView);

        // Location Civic subelement
        tabbedPanel.addTab(LCR_TAB_NAME, lcrView);
        // Map Image subelement
        tabbedPanel.addTab(MAP_TAB_NAME, mapView);

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

        JPanel generateBufferPanel = new JPanel();
        generateBufferPanel.setLayout(new BoxLayout(generateBufferPanel, BoxLayout.X_AXIS));
        generateBufferPanel.add(readableBufferCheckbox);
        generateBufferPanel.add(generateButton);
        generateBufferPanel.add(androidVersionCheckbox);
        panel.add(generateBufferPanel);

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

    /**
     * Get whether or not the LCI subelement is to be included in the output.
     *
     * @return whether or not the LCI subelement is included
     */
    public boolean getLciIncluded() {
        return lciCheckbox.isSelected();
    }

    /**
     * Get whether or not the Z subelement is to be included in the output.
     *
     * @return whether or not the Z subelement is included
     */
    public boolean getZIncluded() {
        return zCheckbox.isSelected();
    }

    /**
     * Get whether or not the Usage Rules/Policy subelement is to be included in the output.
     *
     * @return whether or not the Usage Rules/Policy subelement is included
     */
    public boolean getUsageIncluded() {
        return usageCheckbox.isSelected();
    }

    /**
     * Get whether or not the BSSID List subelement is to be included in the output.
     *
     * @return whether or not the BSSID List subelement is included
     */
    public boolean getBssidIncluded() {
        return bssidCheckbox.isSelected();
    }

    /**
     * Get whether or not the Location Civic subelement is to be included in the output.
     *
     * @return whether or not the Location Civic subelement is included
     */
    public boolean getLcrIncluded() {
        return lcrCheckbox.isSelected();
    }

    /**
     * Get whether or not the Map Image subelement is to be included in the output.
     *
     * @return whether or not the Map Image subelement is included
     */
    public boolean getMapIncluded() {
        return mapCheckbox.isSelected();
    }

    /**
     * Get the name of the input file.
     *
     * @return the name of the input file
     */
    public String getInputFileName() {
        return inputFileNameField.getText();
    }

    /**
     * Get the input file directory.
     *
     * @return the input file directory
     */
    public String getInputDir() {
        return inputDirField.getText();
    }

    /**
     * Get the name of the output file.
     *
     * @return the name of the output file
     */
    public String getOutputFileName() {
        return outputFileNameField.getText();
    }

    /**
     * Get the output file directory.
     *
     * @return the output file directory
     */
    public String getOutputDir() {
        return outputDirField.getText();
    }

    /**
     * Get whether or not the buffer should be displayed in a readable format.
     *
     * @return whether or not the buffer should be displayed in a readable format
     */
    public boolean isReadable() {
        return readableBufferCheckbox.isSelected();
    }

    /**
     * Get whether or not the encoding should match Android version S or later.
     *
     * @return whether or not the encoding should match Android version S or later
     */
    public boolean isAndroidVersionAtLeastS() {
        return androidVersionCheckbox.isSelected();
    }

    /**
     * Gets which subelement's tab is currently selected.
     *
     * @return the SubelementName for the tab currently selected
     */
    public SubelementName getSelectedTab() {
        int tabIndex = tabbedPanel.getSelectedIndex();
        return SUBELEMENT_NAMES_PER_TAB[tabIndex]; // Null if no subelement tab is selected.
    }

    /**
     * Add a listener for including the LCI subelement.
     *
     * @param listener the ActionListener for the parameter
     */
    public void addLciIncludedListener(ActionListener listener) {
        lciCheckbox.addActionListener(listener);
    }

    /**
     * Add a listener for including the Z subelement.
     *
     * @param listener the ActionListener for the parameter
     */
    public void addZIncludedListener(ActionListener listener) {
        zCheckbox.addActionListener(listener);
    }

    /**
     * Add a listener for including the Usage Rules/Policy subelement.
     *
     * @param listener the ActionListener for the parameter
     */
    public void addUsageIncludedListener(ActionListener listener) {
        usageCheckbox.addActionListener(listener);
    }

    /**
     * Add a listener for including the BSSID List subelement.
     *
     * @param listener the ActionListener for the parameter
     */
    public void addBssidIncludedListener(ActionListener listener) {
        bssidCheckbox.addActionListener(listener);
    }

    /**
     * Add a listener for including the Location Civic subelement.
     *
     * @param listener the ActionListener for the parameter
     */
    public void addLcrIncludedListener(ActionListener listener) {
        lcrCheckbox.addActionListener(listener);
    }

    /**
     * Add a listener for including the Map Image subelement.
     *
     * @param listener the ActionListener for the parameter
     */
    public void addMapIncludedListener(ActionListener listener) {
        mapCheckbox.addActionListener(listener);
    }

    /**
     * Add a listener for the input file name.
     *
     * @param listener the ActionListener for the parameter
     */
    public void addInputFileNameListener(ActionListener listener) {
        inputFileNameField.addActionListener(listener);
    }

    /**
     * Add a listener for the input file directory.
     *
     * @param listener the ActionListener for the parameter
     */
    public void addInputDirListener(ActionListener listener) {
        inputDirField.addActionListener(listener);
    }

    /**
     * Add a listener for the output file name.
     *
     * @param listener the ActionListener for the parameter
     */
    public void addOutputFileNameListener(ActionListener listener) {
        outputFileNameField.addActionListener(listener);
    }

    /**
     * Add a listener for the output file directory.
     *
     * @param listener the ActionListener for the parameter
     */
    public void addOutputDirListener(ActionListener listener) {
        outputDirField.addActionListener(listener);
    }

    /**
     * Add a listener for whether or not the buffer display should be in a readable form.
     *
     * @param listener the ActionListener for the parameter
     */
    public void addReadableListener(ActionListener listener) {
        readableBufferCheckbox.addActionListener(listener);
    }

    /**
     * Add a listener for whether or not Android version S or later is being used.
     *
     * @param listener the ActionListener for the parameter
     */
    public void addAndroidVersionListener(ActionListener listener) {
        androidVersionCheckbox.addActionListener(listener);
    }

    /**
     * Add a listener for generating the buffer.
     *
     * @param listener the ActionListener for generating the buffer
     */
    public void addGenerateBufferListener(ActionListener listener) {
        generateButton.addActionListener(listener);
    }

    /**
     * Add a listener that is triggered by switching tabs.
     *
     * @param listener the ChangeListener to be triggered when switching tabs
     */
    public void addTabbedPaneListener(ChangeListener listener) {
        tabbedPanel.addChangeListener(listener);
    }

    // Getters for the sub-views

    /**
     * Gets the view for the LCI subelement.
     *
     * @return the LCI subelement view
     */
    public LciView getLciView() {
        return lciView;
    }

    /**
     * Gets the view for the Z subelement.
     *
     * @return the Z subelement view
     */
    public ZView getZView() {
        return zView;
    }

    /**
     * Gets the view for the Usage Rules/Policy subelement.
     *
     * @return the Usage Rules/Policy subelement view
     */
    public UsageView getUsageView() {
        return usageView;
    }

    /**
     * Gets the view for the BSSID List subelement.
     *
     * @return the BSSID List subelement view
     */
    public BssidView getBssidView() {
        return bssidView;
    }

    /**
     * Gets the view for the Location Civic subelement.
     *
     * @return the Location Civic subelement view
     */
    public LcrView getLcrView() {
        return lcrView;
    }

    /**
     * Gets the view for the Map Image subelement.
     *
     * @return the Map Image subelement view
     */
    public MapView getMapView() {
        return mapView;
    }

    /**
     * Displays the buffer in the window.
     *
     * @param lciSubelementBuffersList the included LCI subelement buffers
     * @param lcrSubelementBuffersList the included LCR subelement buffers
     * @param readable whether or not the output should be in a readable form
     */
    public void displayBuffer(List<String> lciSubelementBuffersList, List<String> lcrSubelementBuffersList, boolean readable) {
        StringBuilder totalText = new StringBuilder();
        String betweenSubelements = "";
        String betweenBytes = "";
        if (readable) {
            // Readable buffer display has a line break between subelements.
            betweenSubelements = "\n";
            // Readable buffer display has a space between bytes.
            betweenBytes = " ";
        }

        for (String subelementBuffer : lciSubelementBuffersList) {
            for (int i = 0; i <= subelementBuffer.length() - 2; i += 2) {
                totalText.append(subelementBuffer, i, i + 2).append(betweenBytes);
            }
            totalText.append(betweenSubelements);
        }
        if (!(lcrSubelementBuffersList.isEmpty())) {
            totalText.append("\n");
        }
        for (String subelementBuffer : lcrSubelementBuffersList) {
            for (int i = 0; i <= subelementBuffer.length() - 2; i += 2) {
                totalText.append(subelementBuffer, i, i + 2).append(" ");
            }
            totalText.append(betweenSubelements);
        }
        bufferTextArea.setText(totalText.toString());
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

