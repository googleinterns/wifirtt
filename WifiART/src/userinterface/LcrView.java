package userinterface;

import structs.CivicAddressElements;
import structs.CountryCodes;
import structs.LanguageCodes;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

/**
 * A JPanel representing the view for the Location Civic subelement.
 */
public class LcrView extends JPanel {

    // Constants
    private static final String SELECT_COUNTRY_PROMPT = "SELECT A COUNTRY";
    private static final int NUMBER_OF_COUNTRIES_VIEWABLE = 16;
    private static final String[] LANGUAGE_LIST = LanguageCodes.LANGUAGES_NAMES;
    private static final int NUMBER_OF_LANGUAGES_VIEWABLE = 16;
    private static final String SELECT_ADDRESS_ELEMENT_PROMPT = "SELECT TYPE";

    // Labels and Components
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

    /**
     * Constructs the view for the Location Civic subelement.
     */
    public LcrView() {
        setup();
    }

    private void setup() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel lcrPanelTitlePanel = new JPanel();
        lcrPanelTitlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        lcrPanelTitlePanel.add(lcrPanelTitle);
        this.add(lcrPanelTitlePanel);

        // Panel for choosing the country.
        JPanel countryChooserPanel = new JPanel();
        countryChooserPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        countryChooserPanel.add(countriesComboboxLabel);
        countriesCombobox.setMaximumRowCount(NUMBER_OF_COUNTRIES_VIEWABLE);
        countryChooserPanel.add(countriesCombobox);
        this.add(countryChooserPanel);

        // Panel containing the prompt to add civic address elements.
        JPanel addAddressElementsPromptPanel = new JPanel();
        addAddressElementsPromptPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addAddressElementsPromptPanel.add(addAddressElementLabel);
        this.add(addAddressElementsPromptPanel);

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
        this.add(addAddressElementsPanel);

        // Panel containing the list of added address elements.
        addressElementsListPanel.setLayout(new BoxLayout(addressElementsListPanel, BoxLayout.Y_AXIS));
        JScrollPane existingAddressElementsScrollPane = new JScrollPane(addressElementsListPanel);
        this.add(existingAddressElementsScrollPane);
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

}
