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

        view.addLatitudeListener(actionEvent -> {
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
        });
        view.addLatitudeUncertaintyListener(actionEvent -> {
            try {
                double latitudeUncertainty = view.getLatitudeUncertainty();
                model.getState().setLatitudeUncertainty(latitudeUncertainty);
            } catch (NumberFormatException exception) {
                view.displayError(LATITUDE_UNCERTAINTY_NOT_NUMBER_ERROR);
            }
        });
        view.addLongitudeListener(actionEvent -> {
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
        });
        view.addLongitudeUncertaintyListener(actionEvent -> {
            try {
                double longitudeUncertainty = view.getLongitudeUncertainty();
                model.getState().setLongitudeUncertainty(longitudeUncertainty);
            } catch (NumberFormatException exception) {
                view.displayError(LONGITUDE_UNCERTAINTY_NOT_NUMBER_ERROR);
            }
        });
        view.addAltitudeListener(actionEvent -> {
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
        });
        view.addAltitudeUncertaintyListener(actionEvent -> {
            try {
                double altitudeUncertainty = view.getAltitudeUncertainty();
                model.getState().setAltitudeUncertainty(altitudeUncertainty);
            } catch (NumberFormatException exception) {
                view.displayError(ALTITUDE_UNCERTAINTY_NOT_NUMBER_ERROR);
            }
        });
        view.addAltitudeTypeListener(actionEvent ->
            model.getState().setAltitudeType(view.getAltitudeType()));
        view.addMapDatumListener(actionEvent ->
            model.getState().setMapDatum(view.getMapDatum()));
        view.addRegLocAgreementListener(actionEvent ->
            model.getState().setRegLocAgreement(view.getRegLocAgreement()));
        view.addRegLocDseListener(actionEvent ->
            model.getState().setRegLocDse(view.getRegLocDse()));
        view.addDependentStaListener(actionEvent ->
            model.getState().setDependentSta(view.getDependentSta()));
        view.addLciVersionListener(actionEvent ->
            model.getState().setLciVersion(view.getLciVersion()));
    }
}
