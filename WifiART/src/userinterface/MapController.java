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

import java.io.IOException;

/**
 * The Controller class for the Map Image subelement.
 */
public class MapController {
    private static final String READ_IMAGE_ERROR = "Could not read the image from the URL.";

    private final MapView view;
    private final MapModel model;

    /**
     * Creates the controller for the Map Image subelement.
     *
     * @param view the Map Image subelement view
     * @param model the Map Image subelement model
     */
    public MapController(MapView view, MapModel model) {
        this.model = model;
        this.view = view;
        this.model.setCallback(this);

        view.addMapTypeListener(actionEvent -> updateMapType());
        view.addMapUrlListener(actionEvent -> updateMapUrl());
    }

    /**
     * Update the state based on changes in the view that may not be saved.
     */
    public void updateState() {
        updateMapUrl();
    }

    private void updateMapType() {
        model.getState().setMapType(view.getMapType());
    }

    private void updateMapUrl() {
        model.getState().setMapUrl(view.getMapUrl());
        try {
            view.displayMapPreviewImage(model.getState().getMapUrl());
        } catch (IOException exception) {
            view.displayError(READ_IMAGE_ERROR);
        }
    }
}
