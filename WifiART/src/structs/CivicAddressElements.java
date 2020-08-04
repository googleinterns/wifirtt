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
    private static final AddressElementKey[] CIVIC_ADDRESS_TYPE_LIST = {
        new AddressElementKey("Additional Code", 32),
        new AddressElementKey("Additional Loc. Info", 22),
        new AddressElementKey("Block", 5),
        new AddressElementKey("Borough", 4),
        new AddressElementKey("Borough (Alaska)", 2),
        new AddressElementKey("Branch Road Name", 36),
        new AddressElementKey("Building (Structure)", 25),
        new AddressElementKey("Canton", 1),
        new AddressElementKey("Chou (Japan)", 4),
        new AddressElementKey("City", 3),
        new AddressElementKey("City District", 4),
        new AddressElementKey("City Division", 4),
        new AddressElementKey("County", 2),
        new AddressElementKey("District (India)", 2),
        new AddressElementKey("Floor", 27),
        new AddressElementKey("Gun (Japan)", 2),
        new AddressElementKey("House Number", 19),
        new AddressElementKey("House Number Suffix", 20),
        new AddressElementKey("Landmark or Vanity Address", 21),
        new AddressElementKey("Leading Street Direction", 16),
        new AddressElementKey("Name (Residence/Office Occupant)", 23),
        new AddressElementKey("Neighborhood", 5),
        new AddressElementKey("Parish", 2),
        new AddressElementKey("Postal Community Name", 30),
        new AddressElementKey("Postal/Zip Code", 24),
        new AddressElementKey("Post Office Box (P.O. Box)", 31),
        new AddressElementKey("Prefecture", 1),
        new AddressElementKey("Primary Road Name", 34),
        new AddressElementKey("Province", 1),
        new AddressElementKey("Region", 1),
        new AddressElementKey("Road Section", 35),
        new AddressElementKey("Room", 28),
        new AddressElementKey("Seat (Desk, Cubicle, Workstation)", 33),
        new AddressElementKey("Shi (Japan)", 3),
        new AddressElementKey("State", 1),
        new AddressElementKey("Street Suffix or Type", 18),
        new AddressElementKey("Sub-branch Road Name", 37),
        new AddressElementKey("Street Name Pre-Modifier", 38),
        new AddressElementKey("Street Name Post-Modifier", 39),
        new AddressElementKey("Town", 3),
        new AddressElementKey("Township", 3),
        new AddressElementKey("Trailing Street Suffix", 17),
        new AddressElementKey("Type of Place", 29),
        new AddressElementKey("Unit (Apartment, Suite)", 26),
        new AddressElementKey("Ward", 4)
    };

    /**
     *  A List of possible civic address elements.
     */
    public static final List<String> ADDRESS_ELEMENT_LIST = getAddressElementList();

    // Private constructor to avoid instance creation.
    private CivicAddressElements() {}

    private static class AddressElementKey {
        /**
         * The name of the address element key.
         */
        public final String name;

        /**
         * The civic address type number associated with this address element.
         * (IETF RFC-4776 labels this value "CAtype")
         */
        public final int caType;

        /**
         * Constructs an AddressElementKey containing a name and civic address type number.
         * @param name The name of the address element key.
         * @param caType The civic address type number associated with this address element type.
         */
        AddressElementKey(String name, int caType) {
            this.name = name;
            this.caType = caType;
        }
    }

    /**
     * Returns the list of names of all possible civic address elements.
     *
     * @return The list of address element types.
     */
    private static List<String> getAddressElementList() {
        List<String> addressElementList = new ArrayList<>(CIVIC_ADDRESS_TYPE_LIST.length);
        for (AddressElementKey addressElement : CIVIC_ADDRESS_TYPE_LIST) {
            addressElementList.add(addressElement.name);
        }
        return addressElementList;
    }
}
