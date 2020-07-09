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

import structs.LcrState;
import structs.Subelement;

public class LcrModel implements Subelement {

    private LcrState state;
    private LcrController fc;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public LcrModel(LcrState state) {
        this.state = state;
    }

    /**
     * Get the current Location Civic subelement state from the model.
     *
     * @return the Location Civic subelement state
     */
    public LcrState getState() {
        return this.state;
    }

    /**
     * Set the location civic subelement state.
     *
     * @param state the location civic subelement state
     */
    public void setState(LcrState state) {
        this.state = state;
    }

    /**
     * The callback into the Location Civic controller used when an asynchronous even occurs.
     *
     * @param fc the Location Civic controller in the MVC pattern
     */
    public void setCallback(LcrController fc) {
        this.fc = fc;
    }


    // TODO(dmevans) Implement the encoding in toHexBuffer().
    @Override
    public String toHexBuffer() {
        return null;
    }
}
