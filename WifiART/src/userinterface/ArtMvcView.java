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

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
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

    public boolean getReadable() {
        return readableBufferCheckbox.isSelected();
    }

    public boolean isAndroidVersionNew() {
        return androidVersionCheckbox.isSelected();
    }

    public SubelementName getSelectedTab() {
        int index = tabbedPanel.getSelectedIndex();
        switch (index) {
            case 1:
                return SubelementName.LCI;
            case 2:
                return SubelementName.Z;
            case 3:
                return SubelementName.USAGE;
            case 4:
                return SubelementName.BSSID;
            case 5:
                return SubelementName.LCR;
            case 6:
                return SubelementName.MAP;
        }
        return null; // Return null if no subelement tab is selected.
    }

    public void addLciIncludedListener(ActionListener listener) {
        lciCheckbox.addActionListener(listener);
    }
    public void addZIncludedListener(ActionListener listener) {
        zCheckbox.addActionListener(listener);
    }
    public void addUsageIncludedListener(ActionListener listener) {
        usageCheckbox.addActionListener(listener);
    }
    public void addBssidIncludedListener(ActionListener listener) {
        bssidCheckbox.addActionListener(listener);
    }
    public void addLcrIncludedListener(ActionListener listener) {
        lcrCheckbox.addActionListener(listener);
    }
    public void addMapIncludedListener(ActionListener listener) {
        mapCheckbox.addActionListener(listener);
    }
    public void addInputFileNameListener(ActionListener listener) {
        inputFileNameField.addActionListener(listener);
    }
    public void addInputDirListener(ActionListener listener) {
        inputDirField.addActionListener(listener);
    }
    public void addOutputFileNameListener(ActionListener listener) {
        outputFileNameField.addActionListener(listener);
    }
    public void addOutputDirListener(ActionListener listener) {
        outputDirField.addActionListener(listener);
    }
    public void addReadableListener(ActionListener listener) {
        readableBufferCheckbox.addActionListener(listener);
    }
    public void addAndroidVersionListener(ActionListener listener) {
        androidVersionCheckbox.addActionListener(listener);
    }
    public void addGenerateBufferListener(ActionListener listener) {
        generateButton.addActionListener(listener);
    }
    public void addTabbedPaneListener(ChangeListener listener) {
        tabbedPanel.addChangeListener(listener);
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

    public void displayBuffer(List<String> lciBuffer, List<String> lcrBuffer, boolean readable) {
        StringBuilder totalText = new StringBuilder();
        String betweenSubelements = "";
        String betweenBytes = "";
        if (readable) {
            // Readable buffer display has a line break between subelements.
            betweenSubelements = "\n";
            // Readable buffer display has a space between bytes.
            betweenBytes = " ";
        }

        for (String subelementBuffer : lciBuffer) {
            for (int i = 0; i <= subelementBuffer.length() - 2; i += 2) {
                totalText.append(subelementBuffer, i, i + 2).append(betweenBytes);
            }
            totalText.append(betweenSubelements);
        }
        if (!(lcrBuffer.isEmpty())) {
            totalText.append("\n");
        }
        for (String subelementBuffer : lcrBuffer) {
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

