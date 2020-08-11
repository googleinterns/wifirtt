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

import org.jetbrains.annotations.NotNull;

/**
 * A class for representing a civic address element with a name, key, and associated language.
 */
public class AddressElement implements Comparable<AddressElement> {
    private final StringBuilder nameBuilder;
    private final StringBuilder languageBuilder;
    private final StringBuilder addressElementKeyBuilder;

    /**
     * Creates a new AddressElement with a description, name, and associated language.
     *
     * @param name the name of the address element
     * @param language the language associated with the address element
     * @param key the description of the address element
     */
    public AddressElement(StringBuilder name, StringBuilder language, StringBuilder key) {
        nameBuilder = name;
        languageBuilder = language;
        addressElementKeyBuilder = key;
    }

    /**
     * Get the name for this address element.
     * 
     * @return the String name of the address element (e.g. "New York")
     */
    public String getName() {
        return nameBuilder.toString();
    }

    /**
     * Get the language code for the language of this address element.
     * 
     * @return the language code String for this address element's language
     */
    public String getLanguageCode() {
        return LanguageCodes.getLanguageCode(languageBuilder.toString());
    }

    /**
     * Get CA Type encoding for the key of this address element.
     * 
     * @return the Civic Address Type integer for the address element key
     */
    public byte getCivicAddressType() {
        return CivicAddressElementKeys.getCivicAddressType(this.addressElementKeyBuilder.toString());
    }

    @Override
    public int compareTo(@NotNull AddressElement other) {
        int languageComparison = this.getLanguageCode().compareTo(other.getLanguageCode());
        if (languageComparison == 0) {
            return Integer.compare(this.getCivicAddressType(), other.getCivicAddressType());
        } else {
            return languageComparison;
        }
    }
}
