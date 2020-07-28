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
import structs.ImageTypes;
import structs.MapState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the encodings and methods for the Map Image subelement.
 */
public class MapTest {

    private static final String URL_DEFINED = ImageTypes.IMAGE_TYPES_LIST.get(0);
    private static final String ICO = ImageTypes.IMAGE_TYPES_LIST.get(17);
    private static final String EXAMPLE_URL = "http://map.google.com/b40.jpg";

    // Default state with Map Type = URL defined, URL = null
    private static final MapState STATE_DEFAULT = buildMapState(
        URL_DEFINED,
        ""
    );
    private static final String BUFFER_DEFAULT = "050100";

    // An example state.
    private static final MapState STATE_EXAMPLE = buildMapState(
        URL_DEFINED,
        EXAMPLE_URL
    );
    private static final String BUFFER_EXAMPLE =
        "051e00687474703a2f2f6d61702e676f6f676c652e636f6d2f6234302e6a7067";

    // A URL with 255
    private static final MapState STATE_WITH_MAX_LENGTH_URL = buildMapState(
        STATE_DEFAULT.getMapType(),
        "http://aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaa"
    );
    private static final String BUFFER_WITH_MAX_LENGTH_URL =
        "05ff00687474703a2f2f616161616161616161616161616161616161616161616161616161616161616161"
            + "616161616161616161616161616161616161616161616161616161616161616161616161616161616161"
            + "616161616161616161616161616161616161616161616161616161616161616161616161616161616161"
            + "616161616161616161616161616161616161616161616161616161616161616161616161616161616161"
            + "616161616161616161616161616161616161616161616161616161616161616161616161616161616161"
            + "616161616161616161616161616161616161616161616161616161616161616161616161616161616161"
            + "61616161";

    // A URL with 255 characters, which is above the maximum.
    private static final MapState STATE_WITH_TOO_BIG_URL = buildMapState(
        STATE_DEFAULT.getMapType(),
        "http://aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaa"
    );

    // ICO is the map type with the largest encoding value.
    private static final MapState STATE_ICO = buildMapState(
        ICO,
        STATE_DEFAULT.getMapUrl()
    );
    private static final String BUFFER_ICO = "050111";

    // Map type is not one of the available options.
    private static final MapState STATE_WITH_BAD_INPUT_MAP_TYPE = buildMapState(
        "Not a map type",
        STATE_DEFAULT.getMapUrl()
    );


    private final MapModel model = new MapModel(new MapState());

    /**
     * Constructs a MapState with predetermined values.
     *
     * @param mapType the image type String
     * @param mapUrl the url String
     * @return A MapState with the given image type and url.
     */
    private static MapState buildMapState(String mapType, String mapUrl) {
        MapState state = new MapState();
        state.setMapType(mapType);
        state.setMapUrl(mapUrl);
        return state;
    }

    /**
     * Test setting the map type to its first value (URL defined).
     */
    @Test
    void testSetMapTypeUrlDefined() {
        MapState state = new MapState();

        state.setMapType(URL_DEFINED);

        assertEquals(URL_DEFINED, state.getMapType());
    }

    /**
     * Test setting the map type to its last value (ICO).
     */
    @Test
    void testSetMapTypeIco() {
        MapState state = new MapState();

        state.setMapType(ICO);

        assertEquals(ICO, state.getMapType());
    }


    /**
     * Test setting the map URL to an example value.
     */
    @Test
    void testSetMapUrl() {
        MapState state = new MapState();

        state.setMapUrl(EXAMPLE_URL);

        assertEquals(EXAMPLE_URL, state.getMapUrl());
    }

    /**
     * Test the encoding on default values (URL-defined map type, null URL).
     */
    @Test
    void testBufferDefault() {
        model.setState(STATE_DEFAULT);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_DEFAULT, buffer);
    }

    /**
     * Test the encoding on an example set of parameters.
     */
    @Test
    void testBufferExample() {
        model.setState(STATE_EXAMPLE);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_EXAMPLE, buffer);
    }

    /**
     * Test the encoding when the URL has the maximum number of characters.
     */
    @Test
    void testBufferWithMaxLengthUrl() {
        model.setState(STATE_WITH_MAX_LENGTH_URL);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_MAX_LENGTH_URL, buffer);
    }

    /**
     * Test that the encoding responds appropriately when the given URL is too long.
     */
    @Test
    void testBufferWithTooBigUrl() {
        model.setState(STATE_WITH_TOO_BIG_URL);

        assertThrows(IllegalArgumentException.class, model::toHexBuffer);
    }

    /**
     * Test the encoding when the map type is ICO.
     */
    @Test
    void testBufferIco() {
        model.setState(STATE_ICO);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_ICO, buffer);
    }

    /**
     * Test the encoding when the map type is not one of the available options.
     */
    @Test
    void testBufferBadInputMapType() {
        model.setState(STATE_WITH_BAD_INPUT_MAP_TYPE);

        assertThrows(NullPointerException.class, model::toHexBuffer);
    }
}
