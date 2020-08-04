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

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LcrState {

    private String country;
    private HashMap<StringBuilder, AddressElement> addressElements;

    class AddressElement {
        private final StringBuilder addressElementName;
        private final StringBuilder addressElementLanguage;
        private final StringBuilder addressElementDescription;

        /**
         * Creates a new AddressElement with a description, name, and associated language.
         *
         * @param name The name of the address element.
         * @param language The language associated with the address element.
         * @param description The description of the address element.
         */
        AddressElement(StringBuilder name, StringBuilder language, StringBuilder description) {
            addressElementName = name;
            addressElementLanguage = language;
            addressElementDescription = description;
        }
    }

    /**
     * Contructs a LcrState to represent the value of the Location Civic subelement.
     */
    public LcrState() {
        addressElements = new HashMap<>();
    }

    /**
     * Set which country the access point is located in.
     *
     * @param country The country where the access point is located
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Add an address element to the address.
     *
     * @param name The StringBuilder for the name of the address element.
     * @param language The language associated with this address element
     * @param addressType The StringBuilder for the address type (state, city, etc.) for the address element
     */
    public void addAddressElement(StringBuilder name, StringBuilder language, StringBuilder addressType) {
        addressElements.put(name, new AddressElement(name, language, addressType));
    }

    /**
     * Remove an address element from the address.
     *
     * @param addressElementName The StringBuilder for the name of the address element to be removed.
     */
    public void removeAddressElement(StringBuilder addressElementName) {
        addressElements.remove(addressElementName);
    }

    // TODO(dmevans) Add getter methods.
}
