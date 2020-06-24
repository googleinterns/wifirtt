package userinterface;

import structs.ImageTypes;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.util.List;

/**
 * A JPanel representing the view for the Map Image subelement.
 */
public class MapView extends JPanel {

    // Constants
    private static final String SELECT_IMAGE_TYPE_PROMPT = "SELECT IMAGE TYPE";

    // Labels and Components
    private final JLabel mapPanelTitle = new JLabel("Map Image Subelement");
    private List<String> imageTypesList = ImageTypes.IMAGE_TYPES_LIST;
    private final JComboBox<String> mapImageTypeCombobox = new JComboBox<>(
        getArrayWithSelectionPrompt(imageTypesList, SELECT_IMAGE_TYPE_PROMPT));
    private final JLabel mapUrlFieldLabel = new JLabel(" Enter Map URL: ");
    private final JTextField mapUrlField = new JTextField();
    private final JLabel imagePreviewLabel = new JLabel(" Image Preview: ");
    // TODO(dmevans): Make the "imagePreview" object show the image as entered by the user.
    private final JPanel imagePreview = new JPanel();

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
        this.add(imagePreview);

    }

    private String[] getArrayWithSelectionPrompt(List<String> list, String selectionPrompt) {
        list.add(0, selectionPrompt);
        String[] resultArray = new String[list.size()];
        resultArray = list.toArray(resultArray);
        return resultArray;
    }
}
