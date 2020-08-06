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

import structs.CountryCodes;
import structs.LcrState;
import structs.Subelement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class LcrModel implements Subelement {
    private static final byte SUBELEMENT_ID = 0;

    // Indices (in bytes)
    private static final int SUBELEMENT_ID_INDEX = 0;
    private static final int LENGTH_INDEX = 1;
    private static final int COUNTRY_CODE_INDEX = 2;
    private static final int ADDRESS_ELEMENT_LIST_INDEX = 3;

    // Lengths (in bytes)
    private static final int SUBELEMENT_ID_LENGTH = 1;
    private static final int LENGTH_FIELD_LENGTH = 1;
    private static final int COUNTRY_CODE_LENGTH = 2;

    // Indices and lengths within address elements
    private static final int ADDRESS_ELEMENT_ID_INDEX = 0;
    private static final int ADDRESS_ELEMENT_LENGTH_INDEX = 1;
    private static final int ADDRESS_ELEMENT_NAME_INDEX = 2;

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


    // TODO(dmevans) Implement the encoding in toHexBuffer().
    @Override
    public String toHexBuffer() {
        String country = state.getCountry();

        HashMap<String, HashSet<LcrState.AddressElement>> addressElementsPerLanguage = new HashMap<>();
        HashMap<StringBuilder, LcrState.AddressElement> addressElementsList = state.getAddressElementsList();
        for (StringBuilder addressElementName : addressElementsList.keySet()) {
            LcrState.AddressElement addressElement = addressElementsList.get(key);
            String language = addressElement.addressElementLanguage.toString();
            if (!(addressElementsPerLanguage.containsKey(language))) {
                addressElementsPerLanguage.put(language, new HashSet<>());
            }
            addressElementsPerLanguage.get(language).add(addressElement);
            totalLength += (2 + addressElementName.toString().length());
        }

        List<Byte> byteBuffer = new ArrayList<>(ADDRESS_ELEMENT_LIST_INDEX);
        byteBuffer.set(SUBELEMENT_ID_INDEX, SUBELEMENT_ID);
        byteBuffer.set(COUNTRY_CODE_INDEX, CountryCodes.COUNTRY_NAMES_TO_CODES_MAP.get(country))


        return null;
    }
}
