package userinterface;

import structs.ImageTypes;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * A JPanel representing the view for the Map Image subelement.
 */
public class MapView extends JPanel {

    // Constants
    private static final String SELECT_IMAGE_TYPE_PROMPT = "SELECT IMAGE TYPE";

    // Labels and Components
    private final JLabel mapPanelTitle = new JLabel("Map Image Subelement");
    private final List<String> imageTypesList = ImageTypes.IMAGE_TYPES_LIST;
    private final JComboBox<String> mapImageTypeCombobox = new JComboBox<>(
        getArrayWithSelectionPrompt(imageTypesList, SELECT_IMAGE_TYPE_PROMPT));
    private final JLabel mapUrlFieldLabel = new JLabel(" Enter Map URL: ");
    private final JTextField mapUrlField = new JTextField();
    private final JLabel imagePreviewLabel = new JLabel(" Image Preview: ");
    private JScrollPane imagePreviewScrollPane = new JScrollPane();

    /**
     * Constructs the view for the Map Image subelement.
     */
    public MapView() {
        setup();
    }

    private void setup() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel mapPanelTitlePanel = new JPanel();
        mapPanelTitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mapPanelTitlePanel.add(mapPanelTitle);
        this.add(mapPanelTitlePanel);

        this.add(mapImageTypeCombobox);

        // Panel for entering the map URL.
        JPanel mapUrlPanel = new JPanel();
        mapUrlPanel.setLayout(new BoxLayout(mapUrlPanel, BoxLayout.X_AXIS));
        mapUrlPanel.add(mapUrlFieldLabel);
        mapUrlPanel.add(mapUrlField);
        this.add(mapUrlPanel);

        // Panel for displaying the image preview.
        JPanel imagePreviewLabelPanel = new JPanel();
        imagePreviewLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        imagePreviewLabelPanel.add(imagePreviewLabel);
        this.add(imagePreviewLabelPanel);
        this.add(imagePreviewScrollPane);

    }

    /**
     * Get the selected value of the Map Type parameter (the map image file type).
     *
     * @return the user-chosen String value for the Map Type parameter
     */
    public String getMapType() {
        return mapImageTypeCombobox.getSelectedItem().toString();
    }

    /**
     * Get the user-inputted value of the Map URL parameter (the URL location of the map image).
     *
     * @return the user-inputted URL String value for the Map URL parameter
     */
    public String getMapUrl() {
        return mapUrlField.getText();
    }

    /**
     * Add a listener for the Map Type parameter (the image file type).
     *
     * @param listener the ActionListener for the Map Type parameter
     */
    public void addMapTypeListener(ActionListener listener) {
        mapImageTypeCombobox.addActionListener(listener);
    }

    /**
     * Add a listener for the Map URL parameter (the URL location of the map image file).
     *
     * @param listener the listener for the Map URL parameter
     */
    public void addMapUrlListener(ActionListener listener) {
        mapUrlField.addActionListener(listener);
    }

    /**
     * Displays a preview of the map image in the GUI.
     *
     * @param urlString the URL location for the image file
     * @throws IOException if the image could not be read from the URL
     */
    public void displayMapPreviewImage(String urlString) throws IOException {
        URL url = new URL(urlString);
        BufferedImage image = ImageIO.read(url);
        this.remove(imagePreviewScrollPane);
        imagePreviewScrollPane = new JScrollPane(new JLabel(new ImageIcon(image)));
        this.add(imagePreviewScrollPane);
        this.revalidate();
        this.repaint();
    }

    /**
     * Displays an error in a pop-up window.
     *
     * @param message the error message String to be displayed
     */
    public void displayError(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message);
    }

    private String[] getArrayWithSelectionPrompt(List<String> list, String selectionPrompt) {
        list.add(0, selectionPrompt);
        String[] resultArray = new String[list.size()];
        resultArray = list.toArray(resultArray);
        return resultArray;
    }

}
