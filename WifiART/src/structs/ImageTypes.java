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
 * A wrapper class that provides data structures containing Map Image Types.
 */
public class ImageTypes {
    private static final String SELECTION_PROMPT = "SELECT IMAGE TYPE";
    // The index of each map type is equivalent to its value.
    private static final String[] imageTypes = {
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
     * Returns the list of image types, with a selection prompt at the 0th index.
     * @return The image types list with index 0 containing a selection prompt.
     */
    public static String[] getImageTypesWithPlaceholder() {
        int numberOfImageTypes = imageTypes.length;
        String[] imageTypesWithPlaceholder = new String[numberOfImageTypes + 1];
        imageTypesWithPlaceholder[0] = SELECTION_PROMPT;
        for (int i = 0; i < numberOfImageTypes; i++) {
            imageTypesWithPlaceholder[i + 1] = imageTypes[i];
        }
        return imageTypesWithPlaceholder;
    }
}
