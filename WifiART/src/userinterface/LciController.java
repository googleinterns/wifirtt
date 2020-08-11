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

import structs.LciState;

public class LciController {
    // Error messages
    private static final String LATITUDE_OUT_OF_RANGE_ERROR = "Latitude must be between -90 and 90 degrees.";
    private static final String LONGITUDE_OUT_OF_RANGE_ERROR = "Longitude must be between -180 and 180 degrees.";
    private static final String ALTITUDE_OUT_OF_RANGE_ERROR = "Altitude must be between -2097152 and 2097152.";
    private static final String LATITUDE_NOT_NUMBER_ERROR = "Latitude must be a decimal number.";
    private static final String LATITUDE_UNCERTAINTY_NOT_NUMBER_ERROR = "Latitude uncertainty must be a decimal number.";
    private static final String LONGITUDE_NOT_NUMBER_ERROR = "Longitude must be a decimal number.";
    private static final String LONGITUDE_UNCERTAINTY_NOT_NUMBER_ERROR = "Longitude uncertainty must be a decimal number.";
    private static final String ALTITUDE_NOT_NUMBER_ERROR = "Altitude must be a decimal number.";
    private static final String ALTITUDE_UNCERTAINTY_NOT_NUMBER_ERROR = "Altitude uncertainty must be a decimal number.";

    private final LciView view;
    private final LciModel model;

    /**
     * Creates the controller for the LCI subelement.
     * @param view the LCI subelement view
     * @param model the LCI subelement model
     */
    public LciController(LciView view, LciModel model) {
        this.model = model;
        this.view = view;
        this.model.setCallback(this);

        view.addLatitudeListener(actionEvent -> updateLatitude());
        view.addLatitudeUncertaintyListener(actionEvent -> updateLatitudeUncertainty());
        view.addLongitudeListener(actionEvent -> updateLongitude());
        view.addLongitudeUncertaintyListener(actionEvent -> updateLongitudeUncertainty());
        view.addAltitudeListener(actionEvent -> updateAltitude());
        view.addAltitudeUncertaintyListener(actionEvent -> updateAltitudeUncertainty());
        view.addAltitudeTypeListener(actionEvent -> updateAltitudeType());
        view.addMapDatumListener(actionEvent -> updateMapDatum());
        view.addRegLocAgreementListener(actionEvent -> updateRegLocAgreement());
        view.addRegLocDseListener(actionEvent -> updateRegLocDse());
        view.addDependentStaListener(actionEvent -> updateDependentSta());
        view.addLciVersionListener(actionEvent -> updateLciVersion());
    }

    /**
     * Update the state based on changes in the view that may not be saved.
     */
    public void updateState() {
        updateLatitude();
        updateLatitudeUncertainty();
        updateLongitude();
        updateLongitudeUncertainty();
        updateAltitude();
        updateAltitudeUncertainty();
    }

    private void updateLatitude() {
        try {
            double latitude = view.getLatitude();
            try {
                model.getState().setLatitude(latitude);
            } catch (NumberFormatException exception) {
                view.displayError(LATITUDE_OUT_OF_RANGE_ERROR);
            }
        } catch (NumberFormatException exception) {
            view.displayError(LATITUDE_NOT_NUMBER_ERROR);
        }
    }

    private void updateLatitudeUncertainty() {
        try {
            double latitudeUncertainty = view.getLatitudeUncertainty();
            model.getState().setLatitudeUncertainty(latitudeUncertainty);
        } catch (NumberFormatException exception) {
            view.displayError(LATITUDE_UNCERTAINTY_NOT_NUMBER_ERROR);
        }
    }

    private void updateLongitude() {
        try {
            double longitude = view.getLongitude();
            try {
                model.getState().setLongitude(longitude);
            } catch (NumberFormatException exception) {
                view.displayError(LONGITUDE_OUT_OF_RANGE_ERROR);
            }
        } catch (NumberFormatException exception) {
            view.displayError(LONGITUDE_NOT_NUMBER_ERROR);
        }
    }

    private void updateLongitudeUncertainty() {
        try {
            double longitudeUncertainty = view.getLongitudeUncertainty();
            model.getState().setLongitudeUncertainty(longitudeUncertainty);
        } catch (NumberFormatException exception) {
            view.displayError(LONGITUDE_UNCERTAINTY_NOT_NUMBER_ERROR);
        }
    }

    private void updateAltitude() {
        try {
            double altitude = view.getAltitude();
            try {
                model.getState().setAltitude(altitude);
            } catch (NumberFormatException exception) {
                view.displayError(ALTITUDE_OUT_OF_RANGE_ERROR);
            }
        } catch (NumberFormatException exception) {
            view.displayError(ALTITUDE_NOT_NUMBER_ERROR);
        }
    }

    private void updateAltitudeUncertainty() {
        try {
            double altitudeUncertainty = view.getAltitudeUncertainty();
            model.getState().setAltitudeUncertainty(altitudeUncertainty);
        } catch (NumberFormatException exception) {
            view.displayError(ALTITUDE_UNCERTAINTY_NOT_NUMBER_ERROR);
        }
    }

    private void updateAltitudeType() {
        model.getState().setAltitudeType(view.getAltitudeType());
    }

    private void updateMapDatum() {
        model.getState().setMapDatum(view.getMapDatum());
    }

    private void updateRegLocAgreement() {
        model.getState().setRegLocAgreement(view.getRegLocAgreement());
    }

    private void updateRegLocDse() {
        model.getState().setRegLocDse(view.getRegLocDse());
    }

    private void updateDependentSta() {
        model.getState().setDependentSta(view.getDependentSta());
    }

    private void updateLciVersion() {
        model.getState().setLciVersion(view.getLciVersion());
    }

}
