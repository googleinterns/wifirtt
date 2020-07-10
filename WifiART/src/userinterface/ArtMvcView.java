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

import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        JTabbedPane tabbedPanel = new JTabbedPane();
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

    public boolean getLciIncluded() {
        return lciCheckbox.isSelected();
    }
    public boolean getZIncluded() {
        return zCheckbox.isSelected();
    }
    public boolean getUsageIncluded() {
        return usageCheckbox.isSelected();
    }
    public boolean getBssidIncluded() {
        return bssidCheckbox.isSelected();
    }
    public boolean getLcrIncluded() {
        return lcrCheckbox.isSelected();
    }
    public boolean getMapIncluded() {
        return mapCheckbox.isSelected();
    }

    public String getInputFileName() {
        return inputFileNameField.getText();
    }
    public String getInputDir() {
        return inputDirField.getText();
    }
    public String getOutputFileName() {
        return outputFileNameField.getText();
    }
    public String getOutputDir() {
        return outputDirField.getText();
    }

    public void setLciIncludedListener(ActionListener listener) {
        lciCheckbox.addActionListener(listener);
    }
    public void setZIncludedListener(ActionListener listener) {
        zCheckbox.addActionListener(listener);
    }
    public void setUsageIncludedListener(ActionListener listener) {
        usageCheckbox.addActionListener(listener);
    }
    public void setBssidIncludedListener(ActionListener listener) {
        bssidCheckbox.addActionListener(listener);
    }
    public void setLcrIncludedListener(ActionListener listener) {
        lcrCheckbox.addActionListener(listener);
    }
    public void setMapIncludedListener(ActionListener listener) {
        mapCheckbox.addActionListener(listener);
    }

    public void setInputFileNameListener(ActionListener listener) {
        inputFileNameField.addActionListener(listener);
    }
    public void setInputDirListener(ActionListener listener) {
        inputDirField.addActionListener(listener);
    }
    public void setOutputFileNameListener(ActionListener listener) {
        outputFileNameField.addActionListener(listener);
    }
    public void setOutputDirListener(ActionListener listener) {
        outputDirField.addActionListener(listener);
    }

    // Getters for the sub-views
    public LciView getLciView() {
        return lciView;
    }
    public ZView getZView() {
        return zView;
    }
    public UsageView getUsageView() {
        return usageView;
    }
    public BssidView getBssidView() {
        return bssidView;
    }
    public LcrView getLcrView() {
        return lcrView;
    }
    public MapView getMapView() {
        return mapView;
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

