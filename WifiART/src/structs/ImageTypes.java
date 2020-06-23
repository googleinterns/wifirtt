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
import java.util.List;

/**
 * A wrapper class that provides data structures containing Map Image Types.
 */
public class ImageTypes {
    // The index of each map type is equivalent to its encoding value.
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

    /**
     * A List containing the image types.
     */
    public static final List<String> IMAGE_TYPES_LIST = getImageTypesList();

    // Private constructor to avoid instance creation.
    private ImageTypes() {}

    /**
     * Returns a vdriable-length list containing the image types.
     * @return
     */
    private static List<String> getImageTypesList() {
        List<String> imageTypesList = new ArrayList<>(IMAGE_TYPES.length);
        for (String imageType : IMAGE_TYPES) {
            imageTypesList.add(imageType);
        }
        return imageTypesList;
    }

}
