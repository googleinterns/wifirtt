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

/**
 * An enum representing the map datum options for the LCI subelement.
 */
public enum MapDatum {

    /** WGS84 datum: World Geodetic System of 1984. */
    WGS84(1),

    /**
     * NAD83 (NAVD88) datum: horizontal datum is the North American Datum of 1983 (NAD83),
     *  vertical datum is the North American Vertical datum of 1988 (NAVD88).
     */
    NAD83_NAVD88(2),

    /**
     * NAD83 (MLLW) datum: horizontal datum is the North American Datum of 1983 (NAD83),
     *  vertical datum is the Mean Lower Low Water (MLLW) datum.
     */
    NAD83_MLLW(3);

    private final int value;

    MapDatum(int value) {
        this.value = value;
    }

    /**
     * Get the integer encoding representing the map datum.
     *
     * @return the integer encoding
     */
    public int getEncoding() {
        return value;
    }

}
