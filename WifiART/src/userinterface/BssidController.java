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

/**
 * The Controller for the BSSID List subelement.
 */
public class BssidController {
    private static final String MAX_BSSID_INDICATOR_OUT_OF_RANGE = "The number of BSSs must be between 0 and 255.";
    private static final String MAX_BSSID_INDICATOR_NOT_INTEGER = "You must enter an integer number of BSSs.";
    private static final String BSSID_FORMAT_ERROR = "BSSIDs must be in the format \"00:00:00:00:00:00\"";
    private static final String TOO_MANY_BSSIDS_ERROR = "You cannot add any more BSSIDs.";

    private final BssidView view;
    private final BssidModel model;

    /**
     * Creates the controller for the Co-Located BSSID List subelement.
     *
     * @param view the BSSID subelement view
     * @param model the BSSID subelement model
     */
    public BssidController(BssidView view, BssidModel model) {
        this.model = model;
        this.view = view;
        this.model.setCallback(this);

        view.addMaxBssidIndicatorListener(actionEvent -> {
            try {
                int maxBssidIndicator = view.getMaxBssidIndicator();
                try {
                    model.getState().setMaxBssidIndicator(maxBssidIndicator);
                } catch (NumberFormatException exception) {
                    view.displayError(MAX_BSSID_INDICATOR_OUT_OF_RANGE);
                }
            } catch (NumberFormatException exception) {
                view.displayError(MAX_BSSID_INDICATOR_NOT_INTEGER);
            }
        });
        view.addBssidAddListener(actionEvent -> {
            String addedBssid = view.getAddedBssid();
            StringBuilder bssidBuilder = new StringBuilder(addedBssid);
            try {
                model.getState().addBssid(addedBssid);
                // Update the view if updating the model succeeded
                view.addBssid(
                    bssidBuilder,
                    editModeEvent -> view.toggleEditMode(bssidBuilder),
                    editEvent -> {
                        String oldBssid = bssidBuilder.toString();
                        String newBssid = view.getEditedBssid(bssidBuilder);
                        try {
                            model.getState().editBssid(oldBssid, newBssid);
                            // Update the view if updating the model succeeded
                            view.editBssid(bssidBuilder);
                        } catch (IllegalArgumentException exception) {
                            view.displayError(BSSID_FORMAT_ERROR);
                        } catch (IndexOutOfBoundsException exception) {
                            view.displayError(TOO_MANY_BSSIDS_ERROR);
                        }

                    },
                    removeEvent -> {
                        String bssidString = bssidBuilder.toString();
                        view.removeBssid(bssidBuilder);
                        model.getState().removeBssid(bssidString);
                    });
            } catch (IllegalArgumentException exception) {
                view.displayError(BSSID_FORMAT_ERROR);
            } catch (IndexOutOfBoundsException exception) {
                view.displayError(TOO_MANY_BSSIDS_ERROR);
            }
        });
    }

    /**
     * Update the state based on changes in the view that may not be saved.
     */
    public void updateState() {
        updateMaxBssidIndicator();
    }

    private void updateMaxBssidIndicator() {
        try {
            int maxBssidIndicator = view.getMaxBssidIndicator();
            try {
                model.getState().setMaxBssidIndicator(maxBssidIndicator);
            } catch (NumberFormatException exception) {
                view.displayError(MAX_BSSID_INDICATOR_OUT_OF_RANGE);
            }
        } catch (NumberFormatException exception) {
            view.displayError(MAX_BSSID_INDICATOR_NOT_INTEGER);
        }
    }
}
