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

import structs.ArtSystemState;

public class ArtMvcModel {
    private final ArtSystemState state;
    private ArtMvcController fc;


    /**
     * Constructor.
     *
     * @param state the system state
     */
    public ArtMvcModel(ArtSystemState state) {
        this.state = state;
    }

    /**
     * Get the current system state from the model.
     *
     * @return the system state
     */
    public ArtSystemState getState() {
        return this.state;
    }


    /**
     * The callback into the BfaController used when an asynchronous even occurs e.g. animation timer.
     *
     * @param fc the Bfa controller in the MVC pattern
     */
    public void setCallback(ArtMvcController fc) {
        this.fc = fc;
    }


    /** Update the view by executing the call back on the Bfa Controller. */
    private void updateView() {
        if (fc != null) {
            fc.modelCallback();
        }
    }
}
