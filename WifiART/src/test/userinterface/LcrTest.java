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

package userinterface;

import org.junit.jupiter.api.Test;
import structs.LcrState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for methods and encodings of the Location Civic subelement.
 */
public class LcrTest {
    // Constants
    private static final String UNITED_STATES = "United States of America";
    private static final String GERMANY = "Germany";

    // Default state with no address elements.
    private static final LcrState STATE_DEFAULT = buildLcrState(
        UNITED_STATES,
        new String[0][0]);
    private static final String BUFFER_DEFAULT = "00025553";

    // An example address in Munich, with multiple languages.
    private static final LcrState STATE_MUNICH = buildLcrState(
        GERMANY,
        new String[][] {
            {"Oberbayern", "German", "County"},
            {"München", "German", "City"},
            {"Munich", "English", "City"},
            {"Bayern", "German", "State"},
            {"Marienplatz", "German", "Group of Streets"},
            {"8", "German", "House Number"}
        }
    );
    private static final String BUFFER_MUNICH = "0040"
        + "4445" // Germany
        + "00026465" // German language
        + "010642617965726e" // State
        + "020a4f62657262617965726e" // County
        + "03084dc3bc6e6368656e" // City
        + "060b4d617269656e706c61747a" // Group of Streets
        + "130138" // House Number
        + "0002656e" // English language
        + "03064d756e696368"; // City

    // An example address in Mountain View, with a normal set of parameters.
    private static final LcrState STATE_MOUNTAIN_VIEW = buildLcrState(
        UNITED_STATES,
        new String[][] {
            {"CA", "English", "State"},
            {"Mtn View", "English", "City"},
            {"Alta Road", "English", "Street Suffix or Type"},
            {"15", "English", "House Number"},
            {"Floor 8", "English", "Floor"},
            {"Room 2", "English", "Room"},
            {"Pole 3", "English", "Type of Place"},
        }
    );
    private static final String BUFFER_MOUNTAIN_VIEW = "003c"
        + "5553" // Country code
        + "0002656e" // English language
        + "01024341" // State
        + "03084d746e2056696577" // City
        + "1209416c746120526f6164" // Street Suffix or Type
        + "13023135" // House Number
        + "1b07466c6f6f722038" // Floor
        + "1c06526f6f6d2032" // Room
        + "1d06506f6c652033"; // Type of Place

    // A state resulting in a 255-byte data field, the maximum length.
    private static final LcrState STATE_WITH_MAXIMUM_LENGTH_ADDRESS = buildLcrState(
        STATE_DEFAULT.getCountry(),
        new String[][] {
            {"a".repeat(247), "English", "State"}
        }
    );
    private static final String BUFFER_WITH_MAXIMUM_LENGTH_ADDRESS = "00ff"
        + "5553"
        + "0002656e"
        + "01f7" + "61".repeat(247);

    // A state with a 256-byte data field, which is above the maximum length.
    private static final LcrState STATE_WITH_TOO_LONG_ADDRESS = buildLcrState(
        STATE_DEFAULT.getCountry(),
        new String[][] {
            {"a".repeat(248), "English", "State"}
        }
    );

    private final LcrModel model = new LcrModel(new LcrState());

    /**
     * Constructs an LcrState with predetermined values.
     *
     * @param country the country where the address is located
     * @param addressElements the list of address elements, where each address element is represented
     *                        as an array of length 3: ["Name", "Language", "Type"]
     * @return an LcrState containing the given values
     */
    private static LcrState buildLcrState(String country, String[][] addressElements) {
        LcrState state = new LcrState();
        state.setCountry(country);
        for (String[] addressElement : addressElements) {
            StringBuilder addressElementName = new StringBuilder(addressElement[0]);
            StringBuilder addressElementLanguage = new StringBuilder(addressElement[1]);
            StringBuilder addressElementType = new StringBuilder(addressElement[2]);
            state.addAddressElement(addressElementName, addressElementLanguage, addressElementType);
        }
        return state;
    }

    /**
     * Test the buffer encoding for a default state.
     */
    @Test
    void testBufferDefault() {
        model.setState(STATE_DEFAULT);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_DEFAULT, buffer);
    }

    /**
     * Test the buffer encoding for the Munich example with multiple languages.
     */
    @Test
    void testBufferMunich() {
        model.setState(STATE_MUNICH);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_MUNICH, buffer);
    }

    /**
     * Test the buffer encoding for the Mountain View example with a normal set of parameters.
     */
    @Test
    void testBufferMountainView() {
        model.setState(STATE_MOUNTAIN_VIEW);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_MOUNTAIN_VIEW, buffer);
    }

    /**
     * Test the buffer encoding with an address of the maximum length.
     */
    @Test
    void testBufferWithMaximumLengthAddress() {
        model.setState(STATE_WITH_MAXIMUM_LENGTH_ADDRESS);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_MAXIMUM_LENGTH_ADDRESS, buffer);
    }

    /**
     * Test the buffer encoding with an address that is too long to be encoded.
     */
    @Test
    void testBufferWithTooLongAddress() {
        model.setState(STATE_WITH_TOO_LONG_ADDRESS);

        assertThrows(IllegalArgumentException.class, model::toHexBuffer);
    }
}
