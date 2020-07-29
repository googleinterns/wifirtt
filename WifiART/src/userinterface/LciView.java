package userinterface;

import structs.AltitudeType;
import structs.MapDatum;

import javax.swing.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

/**
 * A JPanel representing the view for the LCI subelement.
 */
public class LciView extends JPanel {

    // Constants
    private static final Integer[] LCI_VERSION_LIST = {1};
    private static final String NO_KNOWN_ALTITUDE_LABEL = "No Altitude Provided";
    private static final String ALTITUDE_IN_METERS_LABEL = "Meters";
    private static final String ALTITUDE_IN_FLOORS_LABEL = "Floors";
    private static final String[] ALTITUDE_TYPE_LIST = {NO_KNOWN_ALTITUDE_LABEL,
        ALTITUDE_IN_METERS_LABEL, ALTITUDE_IN_FLOORS_LABEL};
    private static final String WGS84_DATUM_LABEL = "WGS84";
    private static final String NAD83_NAVD88_DATUM_LABEL = "NAD83 (NAVD88)";
    private static final String NAD83_MLLW_DATUM_LABEL = "NAD83 (MLLW)";
    private static final String[] MAP_DATUM_LIST = {WGS84_DATUM_LABEL,
        NAD83_NAVD88_DATUM_LABEL, NAD83_MLLW_DATUM_LABEL};

    // Labels for titled borders
    private static final String LCI_RESERVED_FIELDS_LABEL = "IEEE 802.11y features";

    // LCI Tab labels and components
    private final JLabel lciTabTitle = new JLabel(" Location Configuration Information Subelement");
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
    private final JLabel mapDatumComboBoxLabel = new JLabel(" Map Datum: ");
    private final JComboBox<String> mapDatumComboBox = new JComboBox<>(MAP_DATUM_LIST);
    private final JCheckBox regLocAgreementCheckbox = new JCheckBox("<html>The STA is operating within a national policy area "
        + "<br> or an international agreement area near a national border.</html>");
    private final JCheckBox regLocDseCheckbox = new JCheckBox("The enabling STA is enabling the operation of STAs with DSE.");
    private final JCheckBox dependentStaCheckbox = new JCheckBox("The STA is operating with the enablement of the enabling "
        + "STA whose LCI is being reported.");

    /**
     * Constructs the view for the LCI subelement.
     */
    public LciView() {
        setup();
    }

    private void setup() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.add(lciTabTitle);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(lciVersionComboBoxLabel);
        headerPanel.add(lciVersionComboBox);
        this.add(headerPanel);

        JPanel latitudePanel = new JPanel();
        latitudePanel.setLayout(new BoxLayout(latitudePanel, BoxLayout.X_AXIS));
        latitudePanel.add(latitudeFieldLabel);
        latitudePanel.add(latitudeField);
        latitudePanel.add(latitudeUncertaintyFieldLabel);
        latitudePanel.add(latitudeUncertaintyField);
        this.add(latitudePanel);

        JPanel longitudePanel = new JPanel();
        longitudePanel.setLayout(new BoxLayout(longitudePanel, BoxLayout.X_AXIS));
        longitudePanel.add(longitudeFieldLabel);
        longitudePanel.add(longitudeField);
        longitudePanel.add(longitudeUncertaintyFieldLabel);
        longitudePanel.add(longitudeUncertaintyField);
        this.add(longitudePanel);

        JPanel altitudePanel = new JPanel();
        altitudePanel.setLayout(new BoxLayout(altitudePanel, BoxLayout.X_AXIS));
        altitudePanel.add(altitudeFieldLabel);
        altitudePanel.add(altitudeField);
        altitudePanel.add(altitudeUncertaintyFieldLabel);
        altitudePanel.add(altitudeUncertaintyField);
        this.add(altitudePanel);

        JPanel altitudeTypeAndDatumPanel = new JPanel();
        altitudeTypeAndDatumPanel.setLayout(new BoxLayout(altitudeTypeAndDatumPanel, BoxLayout.X_AXIS));
        altitudeTypeAndDatumPanel.add(altitudeTypeComboBoxLabel);
        altitudeTypeAndDatumPanel.add(altitudeTypeComboBox);
        altitudeTypeAndDatumPanel.add(Box.createHorizontalGlue());
        altitudeTypeAndDatumPanel.add(mapDatumComboBoxLabel);
        altitudeTypeAndDatumPanel.add(mapDatumComboBox);
        this.add(altitudeTypeAndDatumPanel);

        JPanel lciReservedFieldsPanel = new JPanel();
        setupLciReservedFieldsPanel(lciReservedFieldsPanel);
        this.add(lciReservedFieldsPanel);

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


    // Getter methods for the parameters

    /**
     * Gets the LCI version.
     *
     * @return the LCI version.
     */
    public int getLciVersion() {
        // parseInt will work here because the JComboBox is populated with an integer array.
        return Integer.parseInt(String.valueOf(lciVersionComboBox.getSelectedItem()));
    }

    /**
     * Gets the latitude.
     *
     * @return the latitude, in degrees
     * @throws NumberFormatException if the user did not enter a decimal number
     */
    public double getLatitude() throws NumberFormatException {
        return Double.parseDouble(latitudeField.getText());
    }

    /**
     * Gets the latitude uncertainty.
     *
     * @return the latitude uncertainty, in degrees
     * @throws NumberFormatException if the user did not enter a decimal number
     */
    public double getLatitudeUncertainty() throws NumberFormatException {
        return Double.parseDouble(latitudeUncertaintyField.getText());
    }

    /**
     * Gets the longitude.
     *
     * @return the longitude, in degrees
     * @throws NumberFormatException if the user did not enter a decimal number
     */
    public double getLongitude() throws NumberFormatException {
        return Double.parseDouble(longitudeField.getText());
    }

    /**
     * Gets the longitude uncertainty.
     *
     * @return the longitude uncertainty, in degrees
     * @throws NumberFormatException if the user did not enter a decimal number
     */
    public double getLongitudeUncertainty() throws NumberFormatException {
        return Double.parseDouble(longitudeUncertaintyField.getText());
    }

    /**
     * Gets the altitude.
     *
     * @return the altitude
     * @throws NumberFormatException if the user did not enter a decimal number
     */
    public double getAltitude() throws NumberFormatException {
        return Double.parseDouble(altitudeField.getText());
    }

    /**
     * Gets the altitude uncertainty.
     *
     * @return the altitude uncertainty
     * @throws NumberFormatException if the user did not enter a decimal number
     */
    public double getAltitudeUncertainty() throws NumberFormatException {
        return Double.parseDouble(altitudeUncertaintyField.getText());
    }

    /**
     * Gets the altitude type.
     *
     * @return the altitude type (Meters, Floors, or No Known Altitude)
     */
    public AltitudeType getAltitudeType() {
        String altitudeTypeLabel = String.valueOf(altitudeTypeComboBox.getSelectedItem());
        if (altitudeTypeLabel.equals(NO_KNOWN_ALTITUDE_LABEL)) {
            return AltitudeType.NO_KNOWN_ALTITUDE;
        } else if (altitudeTypeLabel.equals(ALTITUDE_IN_METERS_LABEL)) {
            return AltitudeType.ALTITUDE_IN_METERS;
        } else if (altitudeTypeLabel.equals(ALTITUDE_IN_FLOORS_LABEL)) {
            return AltitudeType.ALTITUDE_IN_FLOORS;
        } else {
            return null; // JComboBox must have one of the three values selected.
        }
    }

    /**
     * Gets the map datum.
     *
     * @return the map datum
     */
    public MapDatum getMapDatum() {
        String datumLabel = String.valueOf(mapDatumComboBox.getSelectedItem());
        if (datumLabel.equals(WGS84_DATUM_LABEL)) {
            return MapDatum.WGS84;
        } else if (datumLabel.equals(NAD83_NAVD88_DATUM_LABEL)) {
            return MapDatum.NAD83_NAVD88;
        } else if (datumLabel.equals(NAD83_MLLW_DATUM_LABEL)) {
            return MapDatum.NAD83_MLLW;
        } else {
            return null; // JComboBox must have one of the three values selected.
        }
    }

    /**
     * Gets the Registered Location (RegLoc) Agreement parameter.
     *
     * @return the boolean value of the parameter.
     */
    public boolean getRegLocAgreement() {
        return regLocAgreementCheckbox.isSelected();
    }

    /**
     * Gets the Registered Location Dependent STA Enablement (RegLoc DSE) parameter.
     *
     * @return the boolean value of the parameter.
     */
    public boolean getRegLocDse() {
        return regLocDseCheckbox.isSelected();
    }

    /**
     * Gets the Dependent STA parameter.
     *
     * @return the boolean value of the parameter.
     */
    public boolean getDependentSta() {
        return dependentStaCheckbox.isSelected();
    }


    // Methods for adding listeners

    /**
     * Adds a listener for the Latitude parameter.
     *
     * @param listener the ActionListener for the parameter.
     */
    public void addLatitudeListener(ActionListener listener) {
        latitudeField.addActionListener(listener);
    }

    /**
     * Adds a listener for the Latitude Uncertainty parameter.
     *
     * @param listener the ActionListener for the parameter.
     */
    public void addLatitudeUncertaintyListener(ActionListener listener) {
        latitudeUncertaintyField.addActionListener(listener);
    }

    /**
     * Adds a listener for the Longitude parameter.
     *
     * @param listener the ActionListener for the parameter.
     */
    public void addLongitudeListener(ActionListener listener) {
        longitudeField.addActionListener(listener);
    }

    /**
     * Adds a listener for the Longitude Uncertainty parameter.
     *
     * @param listener the ActionListener for the parameter.
     */
    public void addLongitudeUncertaintyListener(ActionListener listener) {
        longitudeUncertaintyField.addActionListener(listener);
    }

    /**
     * Adds a listener for the Altitude parameter.
     *
     * @param listener the ActionListener for the parameter.
     */
    public void addAltitudeListener(ActionListener listener) {
        altitudeField.addActionListener(listener);
    }

    /**
     * Adds a listener for the Altitude Uncertainty parameter.
     *
     * @param listener the ActionListener for the parameter.
     */
    public void addAltitudeUncertaintyListener(ActionListener listener) {
        altitudeUncertaintyField.addActionListener(listener);
    }

    /**
     * Adds a listener for the Altitude Type parameter.
     *
     * @param listener the ActionListener for the parameter.
     */
    public void addAltitudeTypeListener(ActionListener listener) {
        altitudeTypeComboBox.addActionListener(listener);
    }

    /**
     * Adds a listener for the Map Datum parameter.
     *
     * @param listener the ActionListener for the parameter.
     */
    public void addMapDatumListener(ActionListener listener) {
        mapDatumComboBox.addActionListener(listener);
    }

    /**
     * Adds a listener for the Registered Location (RegLoc) Agreement parameter.
     *
     * @param listener the ActionListener for the parameter.
     */
    public void addRegLocAgreementListener(ActionListener listener) {
        regLocAgreementCheckbox.addActionListener(listener);
    }

    /**
     * Adds a listener for the Registered Location Dependent STA Enablement (RegLoc DSE) parameter.
     *
     * @param listener the ActionListener for the parameter.
     */
    public void addRegLocDseListener(ActionListener listener) {
        regLocDseCheckbox.addActionListener(listener);
    }

    /**
     * Adds a listener for the Dependent STA parameter.
     *
     * @param listener the ActionListener for the parameter.
     */
    public void addDependentStaListener(ActionListener listener) {
        dependentStaCheckbox.addActionListener(listener);
    }

    /**
     * Adds a listener for the LCI Version parameter.
     *
     * @param listener the ActionListener for the parameter.
     */
    public void addLciVersionListener(ActionListener listener) {
        lciVersionComboBox.addActionListener(listener);
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
