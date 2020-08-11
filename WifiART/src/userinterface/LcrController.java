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
    private static final String ADDRESS_ELEMENT_TYPE_NOT_CHOSEN = "You must select an address element type.";
    private static final String COUNTRY_NOT_CHOSEN = "You must select a valid country.";

    private final LcrView view;
    private final LcrModel model;

    /**
     * Creates the controller for the Location Civic subelement.
     *
     * @param view the Location Civic subelement view
     * @param model the Location Civic subelement model
     */
    public LcrController(LcrView view, LcrModel model) {
        this.model = model;
        this.view = view;
        this.model.setCallback(this);

        view.addCountryListener(actionEvent -> {
            try {
                String country = view.getCountry();
                model.getState().setCountry(country);
            } catch (IllegalArgumentException exception) {
                view.displayError(COUNTRY_NOT_CHOSEN);
            }
        });
        view.addAddressElementAddListener(actionEvent -> {
            StringBuilder addedAddressElementName = new StringBuilder(view.getAddedAddressElementName());
            StringBuilder addedAddressElementLanguage = new StringBuilder(view.getAddedAddressElementLanguage());
            try {
                StringBuilder addedAddressElementType = new StringBuilder(view.getAddedAddressElementType());
                model.getState().addAddressElement(addedAddressElementName, addedAddressElementLanguage, addedAddressElementType);
                view.addAddressElement(
                    addedAddressElementName,
                    addedAddressElementLanguage,
                    addedAddressElementType,
                    editModeEvent -> view.toggleEditMode(addedAddressElementName),
                    newAddressElementNameEvent -> view.editAddressElementName(addedAddressElementName),
                    newAddressElementLanguageEvent -> view.editAddressElementLanguage(addedAddressElementName),
                    newAddressElementTypeEvent -> view.editAddressElementType(addedAddressElementName),
                    removeEvent -> {
                        view.removeAddressElement(addedAddressElementName);
                        model.getState().removeAddressElement(addedAddressElementName);
                    });
            } catch (IllegalArgumentException exception) {
                view.displayError(ADDRESS_ELEMENT_TYPE_NOT_CHOSEN);
            }
        });
    }
}
