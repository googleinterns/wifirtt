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
 * The state representation for the Map Image subelement.
 */
public class MapState {

    private String mapType;
    private String mapUrl;

    /**
     * Get the Map Type.
     *
     * @return mapType the map type.
     */
    public String getMapType() {
        return mapType;
    }

    /**
     * Get the Map URL.
     * @return mapUrl the map URL.
     */
    public String getMapUrl() {
        return mapUrl;
    }

    /**
     * Set the Map Type.
     *
     * @param mapType the map type.
     */
    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    /**
     * Set the Map URL.
     *
     * @param mapUrl the map URL.
     */
    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

}
