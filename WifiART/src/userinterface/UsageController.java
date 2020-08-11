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

public class UsageController {

    // Error messages
    private static final String EXPIRE_TIME_NOT_INTEGER_ERROR = "Expire time must be an integer.";
    private static final String EXPIRE_TIME_OUTSIDE_RANGE_ERROR = "Expire time must be between 0 and 65535";

    private final UsageView view;
    private final UsageModel model;

    /**
     * Creates the controller for the Usage Rules/Policy subelement.
     *
     * @param view the Usage Rules/Policy subelement view
     * @param model the Usage Rules/Policy subelement model
     */
    public UsageController(UsageView view, UsageModel model) {
        this.model = model;
        this.view = view;
        this.model.setCallback(this);

        view.addRetransmissionAllowedListener(actionEvent -> updateRetransmissionAllowed());
        view.addRetentionExpiresListener(actionEvent -> updateRetentionExpires());
        view.addExpireTimeListener(actionEvent -> updateExpireTime());
        view.addStaLocationPolicyListener(actionEvent -> updateStaLocationPolicy());
    }

    /**
     * Update the state based on changes in the view that may not be saved.
     */
    public void updateState() {
        if (model.getState().getRetentionExpires()) {
            updateExpireTime(); // Expire Time field is only relevant if Retention Expires is true.
        }
    }

    private void updateRetransmissionAllowed() {
        model.getState().setRetransmissionAllowed(view.getRetransmissionAllowed());
    }

    private void updateRetentionExpires() {
        model.getState().setRetentionExpires(view.getRetentionExpires());
    }

    private void updateExpireTime() {
        try {
            int expireTimeHours = view.getExpireTimeHours();
            try {
                model.getState().setExpireTimeHours(expireTimeHours);
            } catch (NumberFormatException exception) {
                view.displayError(EXPIRE_TIME_OUTSIDE_RANGE_ERROR);
            }
        } catch (NumberFormatException exception) {
            view.displayError(EXPIRE_TIME_NOT_INTEGER_ERROR);
        }
    }

    private void updateStaLocationPolicy() {
        model.getState().setStaLocationPolicy(view.getStaLocationPolicy());
    }
}
