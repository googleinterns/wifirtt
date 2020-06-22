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

/**
 * A wrapper class that provides data structures containing Civic Address Types (CATypes),
 * as specified in RFC 4776.
 */
public class CivicAddressTypes {
    private static final String SELECTION_PROMPT = "SELECT TYPE";
    private static final AddressType[] CIVIC_ADDRESS_TYPE_LIST = {
        new AddressType("Additional Code", 32),
        new AddressType("Additional Loc. Info", 22),
        new AddressType("Block", 5),
        new AddressType("Borough", 4),
        new AddressType("Borough (Alaska)", 2),
        new AddressType("Branch Road Name", 36),
        new AddressType("Building (Structure)", 25),
        new AddressType("Canton", 1),
        new AddressType("Chou (Japan)", 4),
        new AddressType("City", 3),
        new AddressType("City District", 4),
        new AddressType("City Division", 4),
        new AddressType("County", 2),
        new AddressType("District (India)", 2),
        new AddressType("Floor", 27),
        new AddressType("Gun (Japan)", 2),
        new AddressType("House Number", 19),
        new AddressType("House Number Suffix", 20),
        new AddressType("Landmark or Vanity Address", 21),
        new AddressType("Leading Street Direction", 16),
        new AddressType("Name (Residence/Office Occupant)", 23),
        new AddressType("Neighborhood", 5),
        new AddressType("Parish", 2),
        new AddressType("Postal Community Name", 30),
        new AddressType("Postal/Zip Code", 24),
        new AddressType("Post Office Box (P.O. Box)", 31),
        new AddressType("Prefecture", 1),
        new AddressType("Primary Road Name", 34),
        new AddressType("Province", 1),
        new AddressType("Region", 1),
        new AddressType("Road Section", 35),
        new AddressType("Room", 28),
        new AddressType("Seat (Desk, Cubicle, Workstation)", 33),
        new AddressType("Shi (Japan)", 3),
        new AddressType("State", 1),
        new AddressType("Street Suffix or Type", 18),
        new AddressType("Sub-branch Road Name", 37),
        new AddressType("Street Name Pre-Modifier", 38),
        new AddressType("Street Name Post-Modifier", 39),
        new AddressType("Town", 3),
        new AddressType("Township", 3),
        new AddressType("Trailing Street Suffix", 17),
        new AddressType("Type of Place", 29),
        new AddressType("Unit (Apartment, Suite)", 26),
        new AddressType("Ward", 4)
    };

    /* A list of civic address type names, prepared for use in a ComboBox,
        with a selection prompt occupying index 0.
     */
    public static final String[] ADDRESS_ELEMENT_TYPES_WITH_PLACEHOLDER = getAddressElementTypesWithPlaceholder();

    private static class AddressType {
        // The name of the address element type.
        public final String NAME;
        // The civic address type number associated with this address element type.
        public final int CA_TYPE;

        /**
         * Constructs a AddressType containing a name and civic address type number.
         * @param name The name of the address element type.
         * @param caType The civic address type number associated with this address element type.
         */
        AddressType(String name, int caType) {
            this.NAME = name;
            this.CA_TYPE = caType;
        }
    }

    /**
     * Returns the list of all civic address element types for use in a ComboBox.
     * @return The list of address element types, with a selection prompt occupying the 0th index.
     */
    private static String[] getAddressElementTypesWithPlaceholder() {
        int numberOfAddressElementTypes = CIVIC_ADDRESS_TYPE_LIST.length;
        String[] addressElementTypes = new String[numberOfAddressElementTypes + 1];
        addressElementTypes[0] = SELECTION_PROMPT;
        for (int i = 0; i < numberOfAddressElementTypes; i++) {
            addressElementTypes[i + 1] = CIVIC_ADDRESS_TYPE_LIST[i].NAME;
        }
        return addressElementTypes;
    }
}
