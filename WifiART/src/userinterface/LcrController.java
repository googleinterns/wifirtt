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

public class LcrController {
    private final LcrView view;
    private final LcrModel model;

    /**
     * Creates the controller for the Location Civic subelement.
     * @param view the Location Civic subelement view
     * @param model the Location Civic subelement model
     */
    public LcrController(LcrView view, LcrModel model) {
        this.model = model;
        this.view = view;
        this.model.setCallback(this);
    }
}