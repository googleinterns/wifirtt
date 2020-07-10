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

package structs;

import java.util.ArrayList;
import java.util.List;

public class LcrState {

    private List<AddressElement> addressElements;

    private class AddressElement {
        private final String addressElementLanguage;
        private final String addressElementDescription;
        private final String addressElementName;

        /**
         * Creates a new AddressElement with a description, name, and associated language.
         * @param language The language associated with the address element.
         * @param description The description of the address element.
         * @param name The name of the address element.
         */
        AddressElement(String language, String description, String name) {
            addressElementLanguage = language;
            addressElementDescription = description;
            addressElementName = name;
        }
    }

    /**
     * Contructs a LcrState to represent the value of the Location Civic subelement.
     */
    public LcrState() {
        addressElements = new ArrayList<>();
    }

    /**
     * Add an address element to the address.
     * @param language The language associated with this address element
     * @param description The description of the address element
     * @param name The name of the address element.
     */
    public void addAddressElement(String language, String description, String name) {
        addressElements.add(new AddressElement(language, description, name));
    }

    /**
     * Remove an address element from the address.
     * @param index The index of the address element to be removed.
     */
    public void removeAddressElement(int index) {
        addressElements.remove(index);
    }

    /**
     * Replace an address element with a new one.
     * @param index The index of the address element to be replaced.
     * @param language The language associated with the new address element.
     * @param description The description of the new address element (e.g. "City").
     * @param name The name of the new address element.
     */
    public void editAddressElement(int index, String language, String description, String name) {
        addressElements.set(index, new AddressElement(language, description, name));
    }

    // TODO(dmevans) Add getter methods.
}
