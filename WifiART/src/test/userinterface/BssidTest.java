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
import structs.BssidState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests methods and the encoding for the Bssid List subelement.
 */
class BssidTest {

    // Constants
    private static final int MAX_SIZE_OF_BSSID_LIST = 42;
    private static final int MIN_SIZE_OF_BSSID_LIST = 0;
    private static final int MAX_FOR_MAX_BSSID_INDICATOR = 255;
    private static final int MIN_FOR_MAX_BSSID_INDICATOR = 0;
    private static final String[] EXAMPLE_BSSID_LIST = {"01:02:03:04:05:06", "F1:F2:F3:F4:F5:F6"};
    private static final String[] MAX_LENGTH_BSSID_LIST = {
        "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff", "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff",
        "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff", "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff",
        "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff", "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff",
        "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff", "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff",
        "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff", "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff",
        "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff", "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff",
        "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff", "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff",
        "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff", "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff",
        "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff", "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff",
        "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff", "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff",
        "FF:FF:FF:FF:FF:FF", "ff:ff:ff:ff:ff:ff"};
    private static final String[] MIN_LENGTH_BSSID_LIST = new String[0];

    // States and expected buffers for testing

    // Default state with zero/empty (minimum) values.
    private static final BssidState STATE_DEFAULT = buildBssidState(
        0,
        new String[0]);
    private static final String BUFFER_DEFAULT = "070100";

    // Example state with values within the normal range.
    private static final BssidState STATE_EXAMPLE = buildBssidState(
        2,
        EXAMPLE_BSSID_LIST);
    // Provide 2 possible buffers, since the BSSIDs can be in any order.
    private static final String BUFFER_EXAMPLE_1 = "070d02010203040506f1f2f3f4f5f6";
    private static final String BUFFER_EXAMPLE_2 = "070d02f1f2f3f4f5f6010203040506";

    // Maximum values for the Max BSSID Indicator parameter and the size of the list.
    private static final BssidState STATE_WITH_MAXIMUM_VALUES = buildBssidState(
        MAX_FOR_MAX_BSSID_INDICATOR,
        MAX_LENGTH_BSSID_LIST);
    private static final String BUFFER_WITH_MAXIMUM_VALUES = "07fdffffffffffffffffffffffffffffffff"
        + "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"
        + "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"
        + "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"
        + "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"
        + "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"
        + "ffffffffffffffffffffffffffffffffff";

    private final BssidModel model = new BssidModel(new BssidState());

    /**
     * Constructs a BssidState with pre-determined parameter values.
     *
     * @param maxBssidIndicator the maximum number of BSSs which can share the same antenna connector
     * @param bssidList the list of BSSID strings
     */
    private static BssidState buildBssidState(int maxBssidIndicator, String[] bssidList) {
        BssidState state = new BssidState();
        state.setMaxBssidIndicator(maxBssidIndicator);
        for (String bssidString : bssidList) {
            state.addBssid(bssidString);
        }
        return state;
    }

    /**
     * Test setting the Max BSSID Indicator parameter to its maximum value.
     */
    @Test
    void testSetMaxBssidIndicatorToMaximumValue() {
        BssidState state = new BssidState();

        state.setMaxBssidIndicator(MAX_FOR_MAX_BSSID_INDICATOR);

        assertEquals(MAX_FOR_MAX_BSSID_INDICATOR, state.getMaxBssidIndicator());
    }

    /**
     * Test setting the Max BSSID Indicator parameter to its minimum value.
     */
    @Test
    void testSetMaxBssidIndicatorToMinimumValue() {
        BssidState state = new BssidState();

        state.setMaxBssidIndicator(MIN_FOR_MAX_BSSID_INDICATOR);

        assertEquals(MIN_FOR_MAX_BSSID_INDICATOR, state.getMaxBssidIndicator());
    }

    /**
     * Test setting the Max BSSID Indicator parameter to a value above the maximum.
     */
    @Test
    void testSetMaxBssidIndicatorTooBig() {
        BssidState state = new BssidState();

        assertThrows(NumberFormatException.class,
            () -> state.setMaxBssidIndicator(MAX_FOR_MAX_BSSID_INDICATOR + 1));
    }

    /**
     * Test setting the Max BSSID Indicator parameter to a value below the minimum.
     */
    @Test
    void testSetMaxBssidIndicatorTooSmall() {
        BssidState state = new BssidState();

        assertThrows(NumberFormatException.class,
            () -> state.setMaxBssidIndicator(MIN_FOR_MAX_BSSID_INDICATOR - 1));
    }

    /**
     * Test setting the BSSID List parameter to a maximum size list.
     */
    @Test
    void testSetMaxSizeBssidList() {
        BssidState state = new BssidState();

        for (String bssidString : MAX_LENGTH_BSSID_LIST) {
            state.addBssid(bssidString);
        }

        assertEquals(MAX_SIZE_OF_BSSID_LIST, state.getBssidList().size());
    }

    /**
     * Test setting the BSSID List parameter to a minimum size list.
     */
    @Test
    void testSetMinSizeBssidList() {
        BssidState state = new BssidState();

        for (String bssidString : MIN_LENGTH_BSSID_LIST) {
            state.addBssid(bssidString);
        }

        assertEquals(MIN_SIZE_OF_BSSID_LIST, state.getBssidList().size());
    }

    /**
     * Test adding too many BSSIDs to the list.
     */
    @Test
    void testAddTooManyBssidsToList() {
        BssidState state = new BssidState();

        for (String bssidString : MAX_LENGTH_BSSID_LIST) {
            state.addBssid(bssidString);
        }

        assertThrows(IndexOutOfBoundsException.class,
            () -> state.addBssid( "FF:FF:FF:FF:FF:FF"));
    }

    /**
     * Test that values are null after calling the BssidState constructor.
     */
    @Test
    void testBssidStateConstructor() {
        BssidState state = new BssidState();

        // All null values
        assertEquals(0, state.getMaxBssidIndicator());
        assertTrue(state.getBssidList().isEmpty());
    }

    /**
     * Test the encoding with default values (false, null).
     */
    @Test
    void testDefaultBuffer() {
        model.setState(STATE_DEFAULT);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_DEFAULT, buffer);
    }

    /**
     * Test the encoding with example values within the normal range.
     */
    @Test
    void testBufferExample() {
        model.setState(STATE_EXAMPLE);

        String buffer = model.toHexBuffer();

        assertTrue(buffer.equals(BUFFER_EXAMPLE_1) || buffer.equals(BUFFER_EXAMPLE_2));
    }

    /**
     * Test the encoding with maximum values.
     */
    @Test
    void testBufferWithMaximumValues() {
        model.setState(STATE_WITH_MAXIMUM_VALUES);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_MAXIMUM_VALUES, buffer);
    }

}
