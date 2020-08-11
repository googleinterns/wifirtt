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

import structs.ExpectedToMove;
import structs.ZState;
import structs.Subelement;

public class ZModel implements Subelement {

    // TODO: set the isFractionBitsPresent boolean based on the Android version entered by the user.
    /**
     * Represents whether or not the height above floor uncertainty encoding uses the uncertainty
     * in 1/4096th-meters.
     */
    private boolean isFractionBitsPresent = true;

    // Error message
    private static final String EXPECTED_TO_MOVE_IS_NULL =
        "You must choose one of the Location Movement options for the Z subelement.";

    // Constants
    private static final byte SUBELEMENT_ID = 4;

    /** Floor number magnitude can be up to 8191/16ths of a floor. */
    private static final int MAX_FLOOR_MAGNITUDE = 8191;

    /** Floor number is represented in 1/16ths of a floor. */
    private static final int FLOOR_FRACTION_BITS = 4;

    /** Height above floor magnitude can be up to 8388607/4096ths of a meter. */
    private static final int MAX_HEIGHT_ABOVE_FLOOR_MAGNITUDE = 8388607;

    /** Height above floor is represented in 1/4096ths of a meter. */
    private static final int HEIGHT_ABOVE_FLOOR_FRACTION_FACTOR = 4096;
    private static final int MIN_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_ENCODING = 1;
    private static final int MAX_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_ENCODING = 24;

    // Lengths (in bytes)
    private static final int SUBELEMENT_ID_LENGTH = 1;
    private static final int LENGTH_FIELD_LENGTH = 1;
    private static final int FLOOR_INFO_LENGTH = 2;
    private static final int HEIGHT_ABOVE_FLOOR_LENGTH = 3;
    private static final int HEIGHT_ABOVE_FLOOR_UNCERTAINTY_LENGTH = 1;

    // Indices (in bytes)
    private static final int SUBELEMENT_ID_INDEX = 0;
    private static final int LENGTH_INDEX = 1;
    private static final int FLOOR_INFO_INDEX = 2;
    private static final int HEIGHT_ABOVE_FLOOR_INDEX = 4;
    private static final int HEIGHT_ABOVE_FLOOR_UNCERTAINTY_INDEX = 7;

    // Indices for the Floor Info field (in bits).
    private static final int FLOOR_INDEX = 2;

    // Masks for turning on individual bits within the 16-bit Floor Info field.
    private static final int FLOOR_SIGN_MASK = 0x8000;      // bit 15
    private static final int FLOOR_MAGNITUDE_MASK = 0x7ffc; // bits 2-14

    // Masks for turning on individual bits within the 24-bit Height Above Floor field.
    private static final int HEIGHT_SIGN_MASK = 0x800000;      // bit 24
    private static final int HEIGHT_MAGNITUDE_MASK = 0x7fffff; // bits 0-23

    private ZState state;
    private ZController controller;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public ZModel(ZState state) {
        this.state = state;
    }

    /**
     * Get the current Z subelement state from the model.
     *
     * @return the Z subelement state
     */
    public ZState getState() {
        return this.state;
    }

    /**
     * Set the Z subelement state in the model.
     *
     * @param state the Z subelement state
     */
    public void setState(ZState state) {
        this.state = state;
    }

    /**
     * The callback into the Z controller used when an asynchronous even occurs.
     *
     * @param controller the Z subelement controller in the MVC pattern
     */
    public void setCallback(ZController controller) {
        this.controller = controller;
    }

    @Override
    public String toHexBuffer() {
        controller.updateState(); // Callback to update the state based on the view.

        byte fieldsLength = FLOOR_INFO_LENGTH + HEIGHT_ABOVE_FLOOR_LENGTH + HEIGHT_ABOVE_FLOOR_UNCERTAINTY_LENGTH;
        byte[] buffer = new byte[SUBELEMENT_ID_LENGTH + LENGTH_FIELD_LENGTH + fieldsLength];
        buffer[SUBELEMENT_ID_INDEX] = SUBELEMENT_ID;
        buffer[LENGTH_INDEX] = fieldsLength;
        int floorInfo = getFloorInfoField(); // throws NullPointerException
        fillLittleEndian(buffer, floorInfo, FLOOR_INFO_INDEX, FLOOR_INFO_LENGTH);
        int heightAboveFloor = getHeightAboveFloorField();
        fillLittleEndian(buffer, heightAboveFloor, HEIGHT_ABOVE_FLOOR_INDEX, HEIGHT_ABOVE_FLOOR_LENGTH);
        int heightAboveFloorUncertainty = getHeightAboveFloorUncertaintyField();
        fillLittleEndian(buffer, heightAboveFloorUncertainty, HEIGHT_ABOVE_FLOOR_UNCERTAINTY_INDEX, HEIGHT_ABOVE_FLOOR_UNCERTAINTY_LENGTH);

        StringBuilder result = new StringBuilder();
        for (byte b : buffer) {
            result.append(String.format("%02x", b)); // Convert the byte to a hex string of 2 characters.
        }
        return result.toString();
    }

    private int getFloorInfoField() throws NullPointerException {
        int result;

        ExpectedToMove locationMovement = state.getExpectedToMove();
        try {
            result = locationMovement.getEncoding(); // Bits 0 and 1
        } catch (NullPointerException exception) {
            throw new NullPointerException(EXPECTED_TO_MOVE_IS_NULL);
        }

        int floorNumber = state.getFloor();
        int floorNumberEncoding = floorNumber << FLOOR_FRACTION_BITS; // Convert to 1/16-ths of a floor.
        if (floorNumberEncoding > MAX_FLOOR_MAGNITUDE) {
            floorNumberEncoding = MAX_FLOOR_MAGNITUDE;
        } else if (floorNumberEncoding < -MAX_FLOOR_MAGNITUDE) {
            floorNumberEncoding = -MAX_FLOOR_MAGNITUDE;
        }
        if (floorNumber < 0) {
            result |= FLOOR_SIGN_MASK; // Set the sign bit
        }
        // Place bits 0-12 of floorNumberEncoding into bits 2-14 of the result.
        result |= ((floorNumberEncoding << FLOOR_INDEX) & FLOOR_MAGNITUDE_MASK);

        return result;
    }

    private int getHeightAboveFloorField() {
        int result = 0;

        double heightAboveFloor = state.getHeightAboveFloorMeters();
        // Convert to 1/4096-ths of a meter, rounded.
        long heightAboveFloorEncoding = Math.round(heightAboveFloor * HEIGHT_ABOVE_FLOOR_FRACTION_FACTOR);
        if (heightAboveFloorEncoding > MAX_HEIGHT_ABOVE_FLOOR_MAGNITUDE) {
            heightAboveFloorEncoding = MAX_HEIGHT_ABOVE_FLOOR_MAGNITUDE;
        } else if (heightAboveFloorEncoding < -MAX_HEIGHT_ABOVE_FLOOR_MAGNITUDE) {
            heightAboveFloorEncoding = -MAX_HEIGHT_ABOVE_FLOOR_MAGNITUDE;
        }
        if (heightAboveFloor < 0) {
            result |= HEIGHT_SIGN_MASK; // Set the sign bit
        }
        result |= (heightAboveFloorEncoding & HEIGHT_MAGNITUDE_MASK); // Set the magnitude bits

        return result;
    }

    private int getHeightAboveFloorUncertaintyField() {
        int result = 0;

        double heightAboveFloorUncertainty = state.getHeightAboveFloorUncertaintyMeters();
        double heightAboveFloorUncertaintyEncoding = 0;
        if (heightAboveFloorUncertainty > 0) {
            if (isFractionBitsPresent) {
                // Convert to 1/4096-ths of a meter.
                heightAboveFloorUncertaintyEncoding = heightAboveFloorUncertainty * HEIGHT_ABOVE_FLOOR_FRACTION_FACTOR;
            } else {
                heightAboveFloorUncertaintyEncoding = heightAboveFloorUncertainty;
            }
            // encoding = 11 - log2(uncertainty)
            heightAboveFloorUncertaintyEncoding = 11 - Math.log(heightAboveFloorUncertaintyEncoding) / Math.log(2);

            // Convert to integer, rounding down so the bound still applies.
            result = (int) (heightAboveFloorUncertaintyEncoding);
            if (result < MIN_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_ENCODING) {
                result = MIN_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_ENCODING;
            } else if (result > MAX_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_ENCODING) {
                result = MAX_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_ENCODING;
            }
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

    /**
     * Determines whether or not the Z subelement should encode according to the updated decoding
     *  or according to the already existing Android decoding.
     *
     * @param isAndndroidVersionNew true if the version used is Android version S or later.
     */
    public void setIfAndroidVersionNew(boolean isAndndroidVersionNew) {
        isFractionBitsPresent = isAndndroidVersionNew;
    }
}
