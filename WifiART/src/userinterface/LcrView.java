package userinterface;

import structs.CivicAddressElements;
import structs.CountryCodes;
import structs.LanguageCodes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A JPanel representing the view for the Location Civic subelement.
 */
public class LcrView extends JPanel {

    // Constants
    private static final String SELECT_COUNTRY_PROMPT = "SELECT A COUNTRY";
    private static final List<String> COUNTRY_LIST = CountryCodes.COUNTRIES_NAMES_LIST;
    private static final int NUMBER_OF_COUNTRIES_VIEWABLE = 16;
    private static final String[] LANGUAGE_LIST = LanguageCodes.LANGUAGES_NAMES;
    private static final int NUMBER_OF_LANGUAGES_VIEWABLE = 16;
    private static final String ENGLISH_LANGUAGE_LABEL = "English";
    private static final String SELECT_ADDRESS_ELEMENT_TYPE_PROMPT = "SELECT TYPE";
    private static final String[] ADDRESS_ELEMENT_LIST =
        getArrayWithSelectionPrompt(CivicAddressElements.ADDRESS_ELEMENT_LIST, SELECT_ADDRESS_ELEMENT_TYPE_PROMPT);
    private static final int PREFERRED_ADDRESS_ELEMENTS_LIST_HEIGHT = 800;

    // Labels and Components
    private final JLabel lcrPanelTitle = new JLabel("Location Civic (Address) Subelement");
    private final JLabel countriesComboboxLabel = new JLabel(" Country: ");
    private final JComboBox<String> countriesCombobox = new JComboBox<>(
        getArrayWithSelectionPrompt(COUNTRY_LIST, SELECT_COUNTRY_PROMPT));
    private final JLabel addAddressElementLabel = new JLabel("Add individual address elements below: ");
    private final JLabel languageComboboxLabel = new JLabel("Language: ");
    private final JLabel addressElementTypeComboboxLabel = new JLabel("Address Element Type: ");
    private final JLabel addressElementFieldLabel = new JLabel("Name: ");
    private final JComboBox<String> languageCombobox = new JComboBox<>(LANGUAGE_LIST);
    private final JComboBox<String> addressElementTypeCombobox = new JComboBox<>(ADDRESS_ELEMENT_LIST);
    private final JTextField addressElementNameField = new JTextField();
    private final JButton addAddressElementsButton = new JButton("Add");
    private final JLabel editAddressElementLabel = new JLabel("Added address elements (Select an address element to edit and press return to save changes): ");
    private final JPanel addressElementsListPanel = new JPanel();

    private final HashMap<StringBuilder, AddressListElement> addressElementsList = new HashMap<>();

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
        lcrPanelTitlePanel.setPreferredSize(new Dimension(800, 10));
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
        languageCombobox.setSelectedItem(ENGLISH_LANGUAGE_LABEL);
        addAddressElementsPanel.add(languageCombobox);                // Row 2, Col 1
        addAddressElementsPanel.add(addressElementTypeCombobox);      // Row 2, Col 2
        addAddressElementsPanel.add(addressElementNameField);         // Row 2, Col 3
        addAddressElementsPanel.add(addAddressElementsButton);        // Row 2, Col 4
        this.add(addAddressElementsPanel);

        // Panel with instructions for editing address elements.
        JPanel editAddressElementsPromptPanel = new JPanel();
        editAddressElementsPromptPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        editAddressElementsPromptPanel.add(editAddressElementLabel);
        this.add(editAddressElementsPromptPanel);

        // Panel containing the list of added address elements.
        addressElementsListPanel.setLayout(new BoxLayout(addressElementsListPanel, BoxLayout.Y_AXIS));
        JScrollPane existingAddressElementsScrollPane = new JScrollPane(addressElementsListPanel);
        existingAddressElementsScrollPane.setPreferredSize(new Dimension(0, PREFERRED_ADDRESS_ELEMENTS_LIST_HEIGHT));
        this.add(existingAddressElementsScrollPane);
    }

    /**
     * Given a List of elements and a selection prompt, returns a combined array for use in a JComboBox.
     *
     * @param list The List containing the elements.
     * @param selectionPrompt The default option prompting the user to select an option.
     * @return An array containing the elements, with the selection prompt at index 0.
     */
    private static String[] getArrayWithSelectionPrompt(List<String> list, String selectionPrompt) {
        list.add(0, selectionPrompt);
        String[] resultArray = new String[list.size()];
        resultArray = list.toArray(resultArray);
        return resultArray;
    }

    /**
     * Get the country where the access point is located.
     *
     * @return The country where the access point is located.
     * @throws IllegalArgumentException If no valid country was chosen.
     */
    public String getCountry() throws IllegalArgumentException {
        String country = countriesCombobox.getSelectedItem().toString();
        if (country.equals(SELECT_COUNTRY_PROMPT)) {
            throw new IllegalArgumentException("No valid country chosen.");
        }
        return country;
    }

    public String getAddedAddressElementName() {
        return addressElementNameField.getText();
    }
    public String getAddedAddressElementLanguage() {
        return languageCombobox.getSelectedItem().toString();
    }
    public String getAddedAddressElementType() throws IllegalArgumentException {
        String addressElementType = addressElementTypeCombobox.getSelectedItem().toString();
        if (addressElementType.equals(SELECT_ADDRESS_ELEMENT_TYPE_PROMPT)) {
            throw new IllegalArgumentException("No valid address element type chosen.");
        }
        return addressElementType;
    }

    // Methods for adding listeners

    public void addCountryListener(ActionListener listener) {
        countriesCombobox.addActionListener(listener);
    }

    public void addAddressElementAddListener(ActionListener listener) {
        addAddressElementsButton.addActionListener(listener);
    }

    public void addAddressElement(StringBuilder addressElementNameBuilder,
                                  StringBuilder languageBuilder,
                                  StringBuilder addressTypeBuilder,
                                  ActionListener editModeListener,
                                  ActionListener newAddressElementNameListener,
                                  ActionListener newLanguageListener,
                                  ActionListener newAddressTypeListener,
                                  ActionListener removeAddressElementListener) {
        AddressListElement addressListElement = new AddressListElement(
            addressElementNameBuilder,
            languageBuilder,
            addressTypeBuilder,
            editModeListener,
            newAddressElementNameListener,
            newLanguageListener,
            newAddressTypeListener,
            removeAddressElementListener);
        addressElementsList.put(addressElementNameBuilder, addressListElement);
        addressElementsListPanel.add(addressListElement);
        addressElementsListPanel.revalidate();
        addressElementsListPanel.repaint();
    }

    public void toggleEditMode(StringBuilder addressElementName) {
        addressElementsList.get(addressElementName).toggleEditMode();
    }

    public void editAddressElementName(StringBuilder addressElementName) {
        addressElementsList.get(addressElementName).editAddressElementName();
    }
    public void editAddressElementLanguage(StringBuilder addressElementName) {
        System.out.println("editing address element language");
        addressElementsList.get(addressElementName).editAddressElementLanguage();
    }
    public void editAddressElementType(StringBuilder addressElementName) {
        addressElementsList.get(addressElementName).editAddressElementType();
    }
    public void removeAddressElement(StringBuilder addressElementName) {
        AddressListElement addressListElement = addressElementsList.remove(addressElementName);
        addressElementsListPanel.remove(addressListElement);
        addressElementsListPanel.revalidate();
        addressElementsListPanel.repaint();
    }

    private static class AddressListElement extends JPanel {
        private boolean editMode;
        private final StringBuilder addressElementName;
        private final StringBuilder addressElementLanguage;
        private final StringBuilder addressElementType;
        private final JCheckBox selectionCheckbox;
        private final JTextField addressElementField;
        private final JLabel addressElementLabel;
        private final JComboBox<String> languageCombobox  = new JComboBox<>(LANGUAGE_LIST);
        private final JLabel languageLabel;
        // Remove selection prompt for address element types
        private final JComboBox<String> addressTypeCombobox =
            new JComboBox<>(Arrays.copyOfRange(ADDRESS_ELEMENT_LIST, 1, ADDRESS_ELEMENT_LIST.length));
        private final JLabel addressTypeLabel;
        private final JButton removeAddressElementButton = new JButton("Delete");
        private final JPanel parametersPanel = new JPanel();
        private final JPanel fillerPanel = new JPanel();

        /**
         * Constructs an AddressListElement JPanel to add to the address element list in the GUI.
         *
         * @param addressElementNameBuilder the StringBuilder for the address element name.
         * @param addressElementLanguageBuilder the StringBuilder for the language of this address element.
         * @param addressTypeBuilder the StringBuilder for the address element type (state, city, etc.) of this address element.
         * @param editModeListener the listener for toggling edit mode.
         * @param newAddressElementNameListener the listener for editing the name of this address element.
         * @param newLanguageListener the listener for editing the address element's language.
         * @param newAddressTypeListener the listener for editing the address element type of this address element.
         * @param removeAddressElementListener the listener for removing this address element from the list.
         */
        AddressListElement(StringBuilder addressElementNameBuilder,
                           StringBuilder addressElementLanguageBuilder,
                           StringBuilder addressTypeBuilder,
                           ActionListener editModeListener,
                           ActionListener newAddressElementNameListener,
                           ActionListener newLanguageListener,
                           ActionListener newAddressTypeListener,
                           ActionListener removeAddressElementListener) {
            this.addressElementName = addressElementNameBuilder;
            this.addressElementLanguage = addressElementLanguageBuilder;
            this.addressElementType = addressTypeBuilder;
            selectionCheckbox = new JCheckBox();
            selectionCheckbox.addActionListener(editModeListener);
            addressElementLabel = new JLabel(addressElementName.toString());
            addressElementField = new JTextField(addressElementName.toString());
            addressElementField.addActionListener(newAddressElementNameListener);
            languageLabel = new JLabel(addressElementLanguageBuilder.toString());
            languageCombobox.setSelectedItem(addressElementLanguageBuilder.toString());
            languageCombobox.addActionListener(newLanguageListener);
            addressTypeLabel = new JLabel(addressTypeBuilder.toString());
            addressTypeCombobox.setSelectedItem(addressTypeBuilder.toString());
            addressTypeCombobox.addActionListener(newAddressTypeListener);
            removeAddressElementButton.addActionListener(removeAddressElementListener);

            this.editMode = false;
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.add(selectionCheckbox);
            parametersPanel.setPreferredSize(new Dimension(1000, 20));
            parametersPanel.setLayout(new GridLayout(1, 4));
            parametersPanel.add(languageLabel);
            parametersPanel.add(addressTypeLabel);
            parametersPanel.add(addressElementLabel);
            parametersPanel.add(fillerPanel);
            this.add(parametersPanel);
        }

        /**
         * Change the name of the address element.
         *
         * @param newAddressElementName the new name of the address element.
         */
        void changeAddressElementName(String newAddressElementName) {
            this.addressElementName.replace(0, this.addressElementName.length(), newAddressElementName);
            this.addressElementLabel.setText(newAddressElementName);
        }

        /**
         * Update the name for this address element based on new user input.
         */
        void editAddressElementName() {
            String newName = addressElementField.getText();
            addressElementName.replace(0, addressElementName.length(), newName);
            this.addressElementLabel.setText(newName);
            exitEditMode();
        }

        /**
         * Update the language for this address element based on new user input.
         */
        void editAddressElementLanguage() {
            String newLanguage = languageCombobox.getSelectedItem().toString();
            addressElementLanguage.replace(0, addressElementLanguage.length(), newLanguage);
            System.out.println("New language: " + newLanguage);
            this.languageLabel.setText(newLanguage);
        }

        /**
         * Update the address element type (state, city, etc.) for this address element based on new user input.
         */
        void editAddressElementType() {
            String newAddressElementType = addressTypeCombobox.getSelectedItem().toString();
            addressElementType.replace(0, addressElementType.length(), newAddressElementType);
            this.addressTypeLabel.setText(newAddressElementType);
        }

        /**
         * Change the address element type (state, city, etc.) for this address element.
         *
         * @param newLanguage the new address element type.
         */
        void changeAddressType(String newLanguage) {
            this.addressTypeLabel.setText(newLanguage);
        }

        /**
         * Enter or exit edit mode.
         */
        void toggleEditMode() {
            if (editMode) {
                exitEditMode();
            } else {
                enterEditMode();
            }
        }

        private void enterEditMode() {
            parametersPanel.remove(languageLabel);
            parametersPanel.remove(addressTypeLabel);
            parametersPanel.remove(addressElementLabel);
            parametersPanel.remove(fillerPanel);
            parametersPanel.add(languageCombobox);
            parametersPanel.add(addressTypeCombobox);
            parametersPanel.add(addressElementField);
            parametersPanel.add(removeAddressElementButton);
            this.revalidate();
            this.repaint();
            this.editMode = true;
        }

        private void exitEditMode() {
            parametersPanel.remove(languageCombobox);
            parametersPanel.remove(addressTypeCombobox);
            parametersPanel.remove(addressElementField);
            parametersPanel.remove(removeAddressElementButton);
            parametersPanel.add(languageLabel);
            parametersPanel.add(addressTypeLabel);
            parametersPanel.add(addressElementLabel);
            parametersPanel.add(fillerPanel);
            this.selectionCheckbox.setSelected(false);
            this.revalidate();
            this.repaint();
            this.editMode = false;
        }

        /**
         * Get the new address element name that the user entered to replace the existing one.
         *
         * @return the new edited address element name.
         */
        String getEditedAddressElement() {
            return addressElementField.getText();
        }

        /**
         * Get the new language that the user chose to replace the existing one.
         *
         * @return the new language.
         */
        String getEditedLanguage() {
            return languageCombobox.getSelectedItem().toString();
        }

        /**
         * Get the new address element type that the user chose to replace the existing one.
         *
         * @return the new address element type.
         */
        String getEditedAddressType() {
            return addressTypeCombobox.getSelectedItem().toString();
        }
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
