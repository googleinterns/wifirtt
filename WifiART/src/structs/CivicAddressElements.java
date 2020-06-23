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

/**
 * A wrapper class that provides data structures containing every address element description
 * and the associated Civic Address Type as specified in RFC 4776.
 */
public class CivicAddressElements {
    private static final AddressElement[] CIVIC_ADDRESS_TYPE_LIST = {
        new AddressElement("Additional Code", 32),
        new AddressElement("Additional Loc. Info", 22),
        new AddressElement("Block", 5),
        new AddressElement("Borough", 4),
        new AddressElement("Borough (Alaska)", 2),
        new AddressElement("Branch Road Name", 36),
        new AddressElement("Building (Structure)", 25),
        new AddressElement("Canton", 1),
        new AddressElement("Chou (Japan)", 4),
        new AddressElement("City", 3),
        new AddressElement("City District", 4),
        new AddressElement("City Division", 4),
        new AddressElement("County", 2),
        new AddressElement("District (India)", 2),
        new AddressElement("Floor", 27),
        new AddressElement("Gun (Japan)", 2),
        new AddressElement("House Number", 19),
        new AddressElement("House Number Suffix", 20),
        new AddressElement("Landmark or Vanity Address", 21),
        new AddressElement("Leading Street Direction", 16),
        new AddressElement("Name (Residence/Office Occupant)", 23),
        new AddressElement("Neighborhood", 5),
        new AddressElement("Parish", 2),
        new AddressElement("Postal Community Name", 30),
        new AddressElement("Postal/Zip Code", 24),
        new AddressElement("Post Office Box (P.O. Box)", 31),
        new AddressElement("Prefecture", 1),
        new AddressElement("Primary Road Name", 34),
        new AddressElement("Province", 1),
        new AddressElement("Region", 1),
        new AddressElement("Road Section", 35),
        new AddressElement("Room", 28),
        new AddressElement("Seat (Desk, Cubicle, Workstation)", 33),
        new AddressElement("Shi (Japan)", 3),
        new AddressElement("State", 1),
        new AddressElement("Street Suffix or Type", 18),
        new AddressElement("Sub-branch Road Name", 37),
        new AddressElement("Street Name Pre-Modifier", 38),
        new AddressElement("Street Name Post-Modifier", 39),
        new AddressElement("Town", 3),
        new AddressElement("Township", 3),
        new AddressElement("Trailing Street Suffix", 17),
        new AddressElement("Type of Place", 29),
        new AddressElement("Unit (Apartment, Suite)", 26),
        new AddressElement("Ward", 4)
    };

    /**
     *  A List of possible civic address elements.
     */
    public static final List<String> ADDRESS_ELEMENT_LIST = getAddressElementList();

    // Private constructor to avoid instance creation.
    private CivicAddressElements() {}

    private static class AddressElement {
        /**
         * The name of the address element.
         */
        public final String name;

        /**
         * The civic address type number associated with this address element.
         * (IETF RFC-4776 labels this value "CAtype")
         */
        public final int caType;

        /**
         * Constructs an AddressElement containing a name and civic address type number.
         * @param name The name of the address element type.
         * @param caType The civic address type number associated with this address element type.
         */
        AddressElement(String name, int caType) {
            this.name = name;
            this.caType = caType;
        }
    }

    /**
     * Returns the list of names of all possible civic address elements.
     * @return The list of address element names.
     */
    private static List<String> getAddressElementList() {
        List<String> addressElementList = new ArrayList<>(CIVIC_ADDRESS_TYPE_LIST.length);
        for (AddressElement addressElement : CIVIC_ADDRESS_TYPE_LIST) {
            addressElementList.add(addressElement.name);
        }
        return addressElementList;
    }
}
