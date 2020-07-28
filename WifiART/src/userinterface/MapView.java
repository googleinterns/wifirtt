package userinterface;

import structs.ImageTypes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
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
     * Get the selected Map Image Type.
     *
     * @return the Map Image Type.
     */
    public String getMapType() {
        return mapImageTypeCombobox.getSelectedItem().toString();
    }

    /**
     * Get the user-inputted value of the Map URL parameter.
     *
     * @return the Map URL.
     */
    public String getMapUrl() {
        return mapUrlField.getText();
    }

    /**
     * Add a listener for the Map Type parameter (the image type).
     *
     * @param listener the listener for the parameter.
     */
    public void addMapTypeListener(ActionListener listener) {
        mapImageTypeCombobox.addActionListener(listener);
    }

    /**
     * Add a listener for the Map URL parameter.
     *
     * @param listener the listener for the parameter.
     */
    public void addMapUrlListener(ActionListener listener) {
        mapUrlField.addActionListener(listener);
    }

    /**
     * Displays a preview of the map image in the GUI.
     *
     * @param urlString The URL for the image.
     * @throws IOException If the image could not be read from the URL.
     */
    public void displayMapPreviewImage(String urlString) throws IOException {
        URL url = new URL(urlString);
        BufferedImage image = ImageIO.read(url);
        this.remove(imagePreviewScrollPane);
        imagePreviewScrollPane = new JScrollPane(new JLabel(new ImageIcon(image)));
        this.add(imagePreviewScrollPane);
    }

    /**
     * Displays an error in a pop-up window.
     *
     * @param message The error message to be displayed.
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
