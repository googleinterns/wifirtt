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

import java.util.HashMap;

/**
 * Contains the constants associated with the "Expected To Move" field of the Z subelement.
 */
public class ExpectedToMove {

    /**
     * The String descriptor for a fixed location.
     */
    public static final String NOT_EXPECTED_TO_MOVE = "STA is not expected to change location.";

    /**
     * The String descriptor for a variable location.
     */
    public static final String EXPECTED_TO_MOVE = "STA is expected to change location.";

    /**
     * The String descriptor for an unknown movement pattern.
     */
    public static final String MOVEMENT_UNKNOWN = "Movement patterns are unknown.";

    /**
     * A HashMap mapping location movement descriptor Strings to their encoding values.
     */
    public static final HashMap<String, Integer> FIELD_VALUES_MAP = getFieldValuesMap();

    // Private constructor to avoid instance creation.
    private ExpectedToMove() {}

    /**
     * Constructs a HashMap with the location movement String options as keys
     *  and their associated 2-bit encodings as values.
     *
     * @return The HashMap mapping location movement descriptor Strings to their encoding values.
     */
    private static HashMap<String, Integer> getFieldValuesMap() {
        HashMap<String, Integer> fieldValuesMap = new HashMap<>();
        fieldValuesMap.put(NOT_EXPECTED_TO_MOVE, 0);
        fieldValuesMap.put(EXPECTED_TO_MOVE, 1);
        fieldValuesMap.put(MOVEMENT_UNKNOWN, 2);
        return fieldValuesMap;
    }
}
