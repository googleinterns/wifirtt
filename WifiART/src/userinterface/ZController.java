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

public class ZController {
    private final ZView view;
    private final ZModel model;

    /**
     * Creates the controller for the Z subelement.
     * @param view the Z subelement view
     * @param model the Z subelement model
     */
    public ZController(ZView view, ZModel model) {
        this.model = model;
        this.view = view;
        this.model.setCallback(this);

        // TODO(dmevans) Add listeners here.
    }
}
