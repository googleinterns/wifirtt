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
     * @param view the Usage Rules/Policy subelement view.
     * @param model the Usage Rules/Policy subelement model.
     */
    public UsageController(UsageView view, UsageModel model) {
        this.model = model;
        this.view = view;
        this.model.setCallback(this);

        view.addRetransmissionAllowedListener(actionEvent ->
            model.getState().setRetransmissionAllowed(view.getRetransmissionAllowed()));
        view.addRetentionExpiresListener(actionEvent ->
            model.getState().setRetentionExpires(view.getRetentionExpires()));
        view.addExpireTimeListener(actionEvent -> {
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
        });
        view.addStaLocationPolicyListener(actionEvent ->
            model.getState().setStaLocationPolicy(view.getStaLocationPolicy()));
    }
}
