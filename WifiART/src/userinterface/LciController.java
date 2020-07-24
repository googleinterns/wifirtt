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

        view.addLatitudeListener(actionEvent ->
            model.getState().setLatitude(view.getLatitude()));
        view.addLatitudeUncertaintyListener(actionEvent ->
            model.getState().setLatitudeUncertainty(view.getLatitudeUncertainty()));
        view.addLongitudeListener(actionEvent ->
            model.getState().setLongitude(view.getLongitude()));
        view.addLongitudeUncertaintyListener(actionEvent ->
            model.getState().setLongitudeUncertainty(view.getLongitudeUncertainty()));
        view.addAltitudeListener(actionEvent ->
            model.getState().setAltitude(view.getAltitude()));
        view.addAltitudeUncertaintyListener(actionEvent ->
            model.getState().setAltitudeUncertainty(view.getAltitudeUncertainty()));
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
