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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A wrapper class that provides data structures containing Map Image Types.
 */
public class ImageTypes {

    /** The index of each map type is equivalent to its encoding value. */
    private static final String[] IMAGE_TYPES = {
        "URL Defined",
        "Png",
        "Gif",
        "Jpeg",
        "Svg",
        "dxf",
        "Dwg",
        "Dwf",
        "cad",
        "Tiff",
        "gml",
        "Kml",
        "Bmp",
        "Pgm",
        "ppm",
        "Xbm",
        "Xpm",
        "ico"
    };

    private static final HashMap<String, Byte> IMAGE_TYPES_MAP = getImageTypesMap();

    /**
     * A List containing the image types.
     */
    public static final List<String> IMAGE_TYPES_LIST = getImageTypesList();

    // Private constructor to avoid instance creation.
    private ImageTypes() {}

    /**
     * Returns a HashMap mapping the image types to their index/encoding.
     *
     * @return the map mapping image types to their encoding.
     */
    private static HashMap<String, Byte> getImageTypesMap() {
        HashMap<String, Byte> imageTypesMap = new HashMap<>();
        for (int i = 0; i < IMAGE_TYPES.length; i++) {
            imageTypesMap.put(IMAGE_TYPES[i], (byte) i);
        }
        return imageTypesMap;
    }

    /**
     * Returns a variable-length list containing the image types.
     *
     * @return the list of image types
     */
    private static List<String> getImageTypesList() {
        List<String> imageTypesList = new ArrayList<>(IMAGE_TYPES.length);
        for (String imageType : IMAGE_TYPES) {
            imageTypesList.add(imageType);
        }
        return imageTypesList;
    }

    /**
     * Get the integer encoding for a given image type String.
     *
     * @param imageType the image type.
     * @return the integer encoding for the image type.
     * @throws NullPointerException if the image type is not one of the options.
     */
    public static byte getImageTypeEncoding(String imageType) throws NullPointerException {
        return IMAGE_TYPES_MAP.get(imageType);
    }

}
