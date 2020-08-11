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

import structs.UsageState;
import structs.Subelement;

public class UsageModel implements Subelement {

    // Constants
    private static final byte SUBELEMENT_ID = 6;

    // Lengths (in bytes)
    private static final int SUBELEMENT_ID_LENGTH = 1;
    private static final int LENGTH_FIELD_LENGTH = 1;
    private static final int USAGE_RULES_POLICY_PARAMETERS_LENGTH = 1;
    private static final int RETENTION_EXPIRES_RELATIVE_LENGTH = 2;

    // Indices (in bytes)
    private static final int SUBELEMENT_ID_INDEX = 0;
    private static final int LENGTH_INDEX = 1;
    private static final int USAGE_RULES_POLICY_PARAMETERS_INDEX = 2;
    private static final int RETENTION_EXPIRES_RELATIVE_INDEX = 3;

    // Masks for turning on individual bits within the Usage Rules/Policy Parameters octet.
    private static final int RETRANSMISSION_ALLOWED_MASK = 0x01; // bit 0
    private static final int RETENTION_EXPIRES_MASK = 0x02;      // bit 1
    private static final int STA_LOCATION_POLICY_MASK = 0x04;    // bit 3

    private UsageState state;
    private UsageController controller;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public UsageModel(UsageState state) {
        this.state = state;
    }

    /**
     * Get the current Usage subelement state from the model.
     *
     * @return the Usage Rules/Policy subelement state
     */
    public UsageState getState() {
        return this.state;
    }

    /**
     * Set the Usage Rules/Policy sublement state in the model.
     *
     * @param state the Usage Rules/Policy subelement state
     */
    public void setState(UsageState state) {
        this.state = state;
    }

    /**
     * The callback into the Usage controller used when an asynchronous even occurs.
     *
     * @param controller the Usage controller in the MVC pattern
     */
    public void setCallback(UsageController controller) {
        this.controller = controller;
    }

    @Override
    public String toHexBuffer() {
        controller.updateState(); // Callback to update the state based on the view.

        byte fieldsLength = USAGE_RULES_POLICY_PARAMETERS_LENGTH;
        if (state.getRetentionExpires()) {
            fieldsLength += RETENTION_EXPIRES_RELATIVE_LENGTH;
        }
        byte[] buffer = new byte[SUBELEMENT_ID_LENGTH + LENGTH_FIELD_LENGTH + fieldsLength];
        buffer[SUBELEMENT_ID_INDEX] = SUBELEMENT_ID;
        buffer[LENGTH_INDEX] = fieldsLength;
        buffer[USAGE_RULES_POLICY_PARAMETERS_INDEX] = getUsageRulesPolicyParametersByte();
        if (state.getRetentionExpires()) {
            int expireTimeHours = state.getExpireTimeHours();
            fillLittleEndian(buffer, expireTimeHours, RETENTION_EXPIRES_RELATIVE_INDEX, RETENTION_EXPIRES_RELATIVE_LENGTH);
        }

        StringBuilder result = new StringBuilder();
        for (byte b : buffer) {
            result.append(String.format("%02x", b)); // Convert the byte to a hex string of 2 characters.
        }
        return result.toString();
    }

    /**
     * Constructs the octet encoding the "Usage Rules/Policy Parameters" field, containing the
     *  RetransmissionAllowed, RetentionExpires, and StaLocationPolicy parameters.
     *
     * @return the octet encoding for the Usage Rules/Policy Parameters field
     */
    private byte getUsageRulesPolicyParametersByte() {
        byte result = 0;
        if (state.getRetransmissionAllowed()) {
            result |= RETRANSMISSION_ALLOWED_MASK;
        }
        if (state.getRetentionExpires()) {
            result |= RETENTION_EXPIRES_MASK;
        }
        if (state.getStaLocationPolicy()) {
            result |= STA_LOCATION_POLICY_MASK;
        }
        return result;
    }

    /**
     * Insert an integer into a byte array in little-endian format.
     *
     * @param arr the byte array being populated
     * @param num the integer to insert into the array
     * @param startIndex the starting index that the integer should appear in the array
     * @param length the number of bytes being populated
     */
    private void fillLittleEndian(byte[] arr, int num, int startIndex, int length) {
        for (int i = 0; i < length; i++) {
            arr[startIndex + i] = (byte)(num & 0xff); // Insert the least-significant byte into the array.
            num >>= 8; // Move the next byte into the least-significant position.
        }
    }
}
