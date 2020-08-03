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

    // Error messages
    private static final String FLOOR_NOT_INTEGER = "Floor number must be an integer.";
    private static final String HEIGHT_NOT_A_NUMBER = "Height above floor must be a decimal number.";
    private static final String HEIGHT_UNCERTAINTY_NOT_A_NUMBER = "Height above floor uncertainty must be a decimal number.";

    private final ZView view;
    private final ZModel model;

    /**
     * Creates the controller for the Z subelement.
     *
     * @param view the Z subelement view
     * @param model the Z subelement model
     */
    public ZController(ZView view, ZModel model) {
        this.model = model;
        this.view = view;
        this.model.setCallback(this);

        view.addFloorListener(actionEvent -> {
            try {
                model.getState().setFloor(view.getFloor());
            } catch (NumberFormatException exception) {
                view.displayError(FLOOR_NOT_INTEGER);
            }
        });
        view.addHeightAboveFloorListener(actionEvent -> {
            try {
                model.getState().setHeightAboveFloorMeters(view.getHeightAboveFloorMeters());
            } catch (NumberFormatException exception) {
                view.displayError(HEIGHT_NOT_A_NUMBER);
            }
        });
        view.addHeightAboveFloorUncertaintyListener(actionEvent -> {
            try {
                model.getState().setHeightAboveFloorUncertaintyMeters(view.getHeightAboveFloorUncertaintyMeters());
            } catch (NumberFormatException exception) {
                view.displayError(HEIGHT_UNCERTAINTY_NOT_A_NUMBER);
            }
        });
        view.addLocationMovementListener(actionEvent ->
            model.getState().setExpectedToMove(view.getExpectedToMove()));
    }
}
