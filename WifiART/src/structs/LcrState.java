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
import java.util.HashMap;
import java.util.List;

/**
 * The state representation for the Location Civic subelement.
 */
public class LcrState {

    private String country;
    private final HashMap<StringBuilder, AddressElement> addressElements;

    /**
     * Constructs a LcrState to represent the value of the Location Civic subelement.
     */
    public LcrState() {
        addressElements = new HashMap<>();
    }

    /**
     * Set which country the access point is located in.
     *
     * @param country the country where the access point is located
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Add an address element to the address.
     *
     * @param name the StringBuilder for the name of the address element
     * @param language the StringBuilder for the language associated with this address element
     * @param addressType the StringBuilder for the address type (state, city, etc.) of the address element
     */
    public void addAddressElement(StringBuilder name, StringBuilder language, StringBuilder addressType) {
        addressElements.put(name, new AddressElement(name, language, addressType));
    }

    /**
     * Remove an address element from the address.
     *
     * @param addressElementName the StringBuilder for the name of the address element to be removed
     */
    public void removeAddressElement(StringBuilder addressElementName) {
        addressElements.remove(addressElementName);
    }


    /**
     * Get the country where the address is located.
     *
     * @return the String name of the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Get the list of address elements.
     *
     * @return a List containing all AddressElements
     */
    public List<AddressElement> getAddressElementsList() {
        List<AddressElement> addressElementsList = new ArrayList<>();
        for (StringBuilder key : addressElements.keySet()) {
            addressElementsList.add(addressElements.get(key));
        }
        return addressElementsList;
    }
}
