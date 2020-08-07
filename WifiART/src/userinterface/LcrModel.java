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

import structs.AddressElement;
import structs.CountryCodes;
import structs.LcrState;
import structs.Subelement;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LcrModel implements Subelement {
    // Constants
    private static final byte SUBELEMENT_ID = 0;
    private static final byte LANGUAGE_CA_TYPE = 0; // Civic Address Type for language field
    private static final byte LANGUAGE_CODE_LENGTH = 2; // 2-letter language codes
    private static final int MAX_BYTE_VALUE = 255;

    // Error messages
    private static final String COUNTRY_NOT_CHOSEN = "You must select a valid country.";
    private static final String ADDRESS_IS_TOO_LONG = "Address is too long to be encoded.";

    // Indices (in bytes)
    private static final int SUBELEMENT_ID_INDEX = 0;
    private static final int LENGTH_INDEX = 1;
    private static final int COUNTRY_CODE_INDEX = 2;
    private static final int ADDRESS_ELEMENT_LIST_INDEX = 3;

    private LcrState state;
    private LcrController fc;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public LcrModel(LcrState state) {
        this.state = state;
    }

    /**
     * Get the current Location Civic subelement state from the model.
     *
     * @return the Location Civic subelement state
     */
    public LcrState getState() {
        return this.state;
    }

    /**
     * Set the location civic subelement state.
     *
     * @param state the location civic subelement state
     */
    public void setState(LcrState state) {
        this.state = state;
    }

    /**
     * The callback into the Location Civic controller used when an asynchronous even occurs.
     *
     * @param fc the Location Civic controller in the MVC pattern
     */
    public void setCallback(LcrController fc) {
        this.fc = fc;
    }

    @Override
    public String toHexBuffer() {
        // Initialize capacity of buffer to the minimum buffer size.
        List<Byte> byteBuffer = new ArrayList<>(ADDRESS_ELEMENT_LIST_INDEX);
        byteBuffer.add(SUBELEMENT_ID_INDEX, SUBELEMENT_ID); // Byte 0
        byteBuffer.add(LENGTH_INDEX, (byte) 0); // Byte 1, placeholder for length field

        // Encode the country,
        String country = state.getCountry();
        try {
            String countryCode = CountryCodes.COUNTRY_NAMES_TO_CODES_MAP.get(country);
            fillStringBytes(byteBuffer, countryCode); // Bytes 2-3
        } catch (NullPointerException exception) {
            throw new NullPointerException(COUNTRY_NOT_CHOSEN);
        }

        // Encode the address elements.
        List<AddressElement> addressElementsList = state.getAddressElementsList();
        // Comparator is null because Address Element implements Comparable.
        addressElementsList.sort(null);
        String currentLanguage = "";
        for (AddressElement addressElement : addressElementsList) {
            String languageCode = addressElement.getLanguageCode();
            if (!(languageCode.equals(currentLanguage))) {
                byteBuffer.add(LANGUAGE_CA_TYPE);
                byteBuffer.add(LANGUAGE_CODE_LENGTH);
                fillStringBytes(byteBuffer, languageCode);
                currentLanguage = languageCode;
            }
            byteBuffer.add(addressElement.getCivicAddressType());
            String addressElementName = addressElement.getName();
            int lengthIndex = byteBuffer.size();
            byteBuffer.add((byte) 0); // Placeholder for the length
            int addressElementLength = fillStringBytes(byteBuffer, addressElementName);
            if (addressElementLength > MAX_BYTE_VALUE) {
                throw new IllegalArgumentException(ADDRESS_IS_TOO_LONG);
            }
            byteBuffer.set(lengthIndex, (byte) addressElementLength);
        }

        // Add length field.
        int fieldsLength = byteBuffer.size() - COUNTRY_CODE_INDEX;
        if (fieldsLength > MAX_BYTE_VALUE) {
            throw new IllegalArgumentException(ADDRESS_IS_TOO_LONG);
        }
        byteBuffer.set(LENGTH_INDEX, (byte) fieldsLength); // Byte 1

        // Convert to hex string.
        StringBuilder result = new StringBuilder();
        for (byte b : byteBuffer) {
            result.append(String.format("%02x", b)); // Convert the byte to a hex string of 2 characters.
        }
        return result.toString();
    }

    /**
     * Appends a String as bytes to a byte listand returns the number of bytes added.
     *
     * @param list the list of bytes to which the String bytes will be appended
     * @param string the String whose bytes will be appended
     * @return the number of bytes added to the list
     */
    private int fillStringBytes(List<Byte> list, String string) {
        byte[] stringBytes = string.getBytes(StandardCharsets.UTF_8);
        for (byte b : stringBytes) {
            list.add(b);
        }
        return stringBytes.length;
    }
}
