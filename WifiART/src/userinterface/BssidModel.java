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

import structs.BssidState;
import structs.Subelement;

import java.util.Set;

/**
 * The Model for the BSSID List subelement.
 */
public class BssidModel implements Subelement {

    // Constants
    private static final byte SUBELEMENT_ID = 7;

    // Lengths (in bytes)
    private static final int SUBELEMENT_ID_LENGTH = 1;
    private static final int LENGTH_FIELD_LENGTH = 1;
    private static final int MAX_BSSID_INDICATOR_LENGTH = 1;
    private static final int BSSID_LENGTH = 6;

    // Indices (in bytes)
    private static final int SUBELEMENT_ID_INDEX = 0;
    private static final int LENGTH_INDEX = 1;
    private static final int MAX_BSSID_INDICATOR_INDEX = 2;
    private static final int BSSID_LIST_INDEX = 3;

    private BssidState state;
    private BssidController controller;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public BssidModel(BssidState state) {
        this.state = state;
    }

    /**
     * Get the current BSSID subelement state from the model.
     *
     * @return the BSSID subelement state
     */
    public BssidState getState() {
        return this.state;
    }

    /**
     * Set the BSSID subelement state in the model.
     *
     * @param state the BSSID subelement state
     */
    public void setState(BssidState state) {
        this.state = state;
    }

    /**
     * The callback into the BSSID controller used when an asynchronous even occurs.
     *
     * @param controller the BSSID controller in the MVC pattern
     */
    public void setCallback(BssidController controller) {
        this.controller = controller;
    }

    @Override
    public String toHexBuffer() {
        controller.updateState(); // Callback to update the state based on the view.

        int maxBssidIndicator = state.getMaxBssidIndicator();
        Set<byte[]> bssidList = state.getBssidList();
        int fieldsLength = MAX_BSSID_INDICATOR_LENGTH + bssidList.size() * BSSID_LENGTH;
        byte[] buffer = new byte[SUBELEMENT_ID_LENGTH + LENGTH_FIELD_LENGTH + fieldsLength];
        buffer[SUBELEMENT_ID_INDEX] = SUBELEMENT_ID;
        buffer[LENGTH_INDEX] = (byte) fieldsLength;
        buffer[MAX_BSSID_INDICATOR_INDEX] = (byte) maxBssidIndicator;
        int index = BSSID_LIST_INDEX;
        for (byte[] bssid : bssidList) {
            System.arraycopy(bssid, 0, buffer, index, BSSID_LENGTH);
            index += BSSID_LENGTH;
        }

        StringBuilder result = new StringBuilder();
        for (byte b : buffer) {
            result.append(String.format("%02x", b)); // Convert the byte to a hex string of 2 characters.
        }
        return result.toString();
    }
}
