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

import structs.SubelementName;

import java.util.List;

public class ArtMvcController {

    private final ArtMvcView view;   // MVC View
    private final ArtMvcModel model; // MVC Model

    private SubelementName lastVisitedSubelement;
    private String lciBuffer;
    private String zBuffer;
    private String usageBuffer;
    private String bssidBuffer;
    private String lcrBuffer;
    private String mapBuffer;

    /** Constructor.
     *
     * @param fv the MVC view
     * @param fm the MVC model
     */
    public ArtMvcController(ArtMvcView fv, ArtMvcModel fm) {
        this.model = fm;
        this.view = fv;

        // Initialize GUI by copying state from model
        this.view.setViewState(fm.getState());
        this.model.setCallback(this);

        // Listeners for the checkboxes determining which subelements are included.
        view.addLciIncludedListener(actionEvent ->
            model.getState().setLciIncluded(view.getLciIncluded()));
        view.addZIncludedListener(actionEvent ->
            model.getState().setZIncluded(view.getZIncluded()));
        view.addUsageIncludedListener(actionEvent ->
            model.getState().setUsageIncluded(view.getUsageIncluded()));
        view.addBssidIncludedListener(actionEvent ->
            model.getState().setBssidIncluded(view.getBssidIncluded()));
        view.addLcrIncludedListener(actionEvent ->
            model.getState().setLcrIncluded(view.getLcrIncluded()));
        view.addMapIncludedListener(actionEvent ->
            model.getState().setMapIncluded(view.getMapIncluded()));

        // Listeners for the input/output file location.
        view.addInputFileNameListener(actionEvent ->
            model.getState().setInputFileName(view.getInputFileName()));
        view.addInputDirListener(actionEvent ->
            model.getState().setInputDir(view.getInputDir()));
        view.addOutputFileNameListener(actionEvent ->
            model.getState().setOutputFileName(view.getOutputFileName()));
        view.addOutputDirListener(actionEvent ->
            model.getState().setOutputDir(view.getOutputDir()));

        // Listeners for updating the buffer.
        view.addReadableListener(actionEvent -> {
            model.getState().setReadable(view.getReadable());
            updateTotalBuffer();
        });
        view.addAndroidVersionListener(actionEvent ->
            model.getState().setAndroidVersionNew(view.isAndroidVersionNew()));
        view.addGenerateBufferListener(actionEvent ->
            updateTotalBuffer());

        view.addTabbedPaneListener(changeEvent -> {
            if (lastVisitedSubelement != null) {
                updateSubelementBuffer(lastVisitedSubelement);
            }
            lastVisitedSubelement = view.getSelectedTab();
        });

    }

    private void updateSubelementBuffer(SubelementName subelementName) {
        if (subelementName == null) {
            return;
        }
        try {
            switch (subelementName) {
                case LCI:
                    model.updateLciSubelementBuffer();
                    break;
                case Z:
                    model.updateZSubelementBuffer();
                    break;
                case USAGE:
                    model.updateUsageSubelementBuffer();
                    break;
                case BSSID:
                    model.updateBssidSubelementBuffer();
                    break;
                case LCR:
                    model.updateLcrSubelementBuffer();
                    break;
                case MAP:
                    model.updateMapSubelementBuffer();
                    break;
            }

        } catch (RuntimeException exception) {
            view.displayErrorMessage(exception.getMessage());
        }
    }

    private void updateTotalBuffer() {
        try {
            List<String> lciBuffer = model.getLciBuffer();
            List<String> lcrBuffer = model.getLcrBuffer();
            view.displayBuffer(lciBuffer, lcrBuffer, model.getState().getReadable());
            writeBufferToFile(lciBuffer, lcrBuffer);
        } catch (RuntimeException exception) {
            view.displayErrorMessage(exception.getMessage());
        }
    }

    private void writeBufferToFile(List<String> lciBuffer, List<String> lcrBuffer) {
        // TODO(dmevans) write the buffer to the file
    }

    /** Events that occur asynchronously in the model call this method e.g. For an animation */
    void modelCallback() {
        view.setViewState(model.getState());
    }

}
