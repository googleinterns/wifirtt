package structs;

import java.util.ArrayList;
import java.util.List;

public class LcrState {

    private List<AddressElement> addressElements;

    private class AddressElement {
        private final String addressElementLanguage;
        private final String addressElementDescription;
        private final String addressElementName;

        AddressElement(String language, String description, String name) {
            addressElementLanguage = language;
            addressElementDescription = description;
            addressElementName = name;
        }
    }

    public LcrState() {
        addressElements = new ArrayList<>();
    }

    public void addAddressElement(String language, String description, String name) {
        addressElements.add(new AddressElement(language, description, name));
    }

    public void removeAddressElement(int index) {
        addressElements.remove(index);
    }

    public void editAddressElement(int index, String language, String description, String name) {
        addressElements.set(index, new AddressElement(language, description, name));
    }
}
