package userinterface;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.FlowLayout;

/**
 * A JPanel representing the view for the LCI subelement.
 */
public class LciView extends JPanel {

    // Constants
    private static final Integer[] LCI_VERSION_LIST = {1};
    private static final String[] ALTITUDE_TYPE_LIST = {"No Altitude Provided", "Meters", "Floors"};
    private static final String[] MAP_DATUM_LIST = {"WGS84", "NAD83 (NAVD88)", "NAD83 (MLLW)"};

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
    private final JLabel mapDatumComboBoxLabel = new JLabel(" Altitude Datum: ");
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

}
