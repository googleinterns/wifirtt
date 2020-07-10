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

public class ArtMvcController {
    private final ArtMvcView view;   // MVC View
    private final ArtMvcModel model; // MVC Model

    /** Constructor.
     *
     * @param fv the MVC view
     * @param fm the MVC model
     *
     * */
    public ArtMvcController(ArtMvcView fv, ArtMvcModel fm) {
        this.model = fm;
        this.view = fv;

        // Initialize GUI by copying state from model
        this.view.setViewState(fm.getState());
        this.model.setCallback(this);

        // Listeners for the checkboxes determining which subelements are included.
        view.setLciIncludedListener(actionEvent ->
            model.getState().setLciIncluded(view.getLciIncluded()));
        view.setZIncludedListener(actionEvent ->
            model.getState().setZIncluded(view.getZIncluded()));
        view.setUsageIncludedListener(actionEvent ->
            model.getState().setUsageIncluded(view.getUsageIncluded()));
        view.setBssidIncludedListener(actionEvent ->
            model.getState().setBssidIncluded(view.getBssidIncluded()));
        view.setLcrIncludedListener(actionEvent ->
            model.getState().setLcrIncluded(view.getLcrIncluded()));
        view.setMapIncludedListener(actionEvent ->
            model.getState().setMapIncluded(view.getMapIncluded()));

        // Listeners for the input/output file location.
        view.setInputFileNameListener(actionEvent ->
            model.getState().setInputFileName(view.getInputFileName()));
        view.setInputDirListener(actionEvent ->
            model.getState().setInputDir(view.getInputDir()));
        view.setOutputFileNameListener(actionEvent ->
            model.getState().setOutputFileName(view.getOutputFileName()));
        view.setOutputDirListener(actionEvent ->
            model.getState().setOutputDir(view.getOutputDir()));
    }

    /** Events that occur asynchronously in the model call this method e.g. For an animation */
    void modelCallback() {
        view.setViewState(model.getState());
    }

}
