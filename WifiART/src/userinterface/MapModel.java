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

import structs.ImageTypes;
import structs.MapState;
import structs.Subelement;

import java.nio.charset.StandardCharsets;

/**
 * The model class for the Map Image subelement.
 */
public class MapModel implements Subelement {

    // Constants
    private static final byte SUBELEMENT_ID = 5;
    private static final int MAX_FIELDS_LENGTH = 255; // Length field is 1 byte
    private static final String ERROR_MAP_IMAGE_URL_TOO_LONG = "Map Image Url can be at most 254 characters.";
    private static final String ERROR_NOT_A_MAP_TYPE = "You must choose a map type.";

    // Lengths (in bytes)
    private static final int SUBELEMENT_ID_LENGTH = 1;
    private static final int LENGTH_FIELD_LENGTH = 1;
    private static final int MAP_TYPE_LENGTH = 1;

    // Indices (in bytes)
    private static final int SUBELEMENT_ID_INDEX = 0;
    private static final int LENGTH_INDEX = 1;
    private static final int MAP_TYPE_INDEX = 2;
    private static final int MAP_URL_INDEX = 3;

    private MapState state;
    private MapController fc;

    /**
     * Constructor.
     *
     * @param state the state for the Map Image subelement
     */
    public MapModel(MapState state) {
        this.state = state;
    }

    /**
     * Get the current Map Image subelement state from the model.
     *
     * @return the Map Image subelement state
     */
    public MapState getState() {
        return this.state;
    }

    /**
     * Set the Map Image subelement state in the model.
     *
     * @param state the Map Image subelement state
     */
    public void setState(MapState state) {
        this.state = state;
    }


    /**
     * The callback into the Map Image controller used when an asynchronous even occurs.
     *
     * @param fc the Map Image controller in the MVC pattern
     */
    public void setCallback(MapController fc) {
        this.fc = fc;
    }

    @Override
    public String toHexBuffer() throws NullPointerException, IllegalArgumentException {
        String mapType = state.getMapType();
        byte mapTypeEncoding;
        try {
            mapTypeEncoding = ImageTypes.getImageTypeEncoding(mapType);
        } catch (NullPointerException exception) {
            throw new NullPointerException(ERROR_NOT_A_MAP_TYPE);
        }

        String mapUrl = state.getMapUrl();
        byte[] mapUrlBytes = mapUrl.getBytes(StandardCharsets.UTF_8);

        int fieldsLength = MAP_TYPE_LENGTH + mapUrlBytes.length;
        if (fieldsLength > MAX_FIELDS_LENGTH) {
            throw new IllegalArgumentException(ERROR_MAP_IMAGE_URL_TOO_LONG);
        }
        byte[] buffer = new byte[SUBELEMENT_ID_LENGTH + LENGTH_FIELD_LENGTH + fieldsLength];
        buffer[SUBELEMENT_ID_INDEX] = SUBELEMENT_ID;
        buffer[LENGTH_INDEX] = (byte) fieldsLength;
        buffer[MAP_TYPE_INDEX] = mapTypeEncoding;
        System.arraycopy(mapUrlBytes, 0, buffer, MAP_URL_INDEX, mapUrlBytes.length);

        StringBuilder result = new StringBuilder();
        for (byte b : buffer) {
            result.append(String.format("%02x", b)); // Convert the byte to a hex string of 2 characters.
        }
        return result.toString();
    }
}
