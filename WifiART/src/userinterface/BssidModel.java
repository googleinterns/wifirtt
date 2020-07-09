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

import structs.BssidState;
import structs.Subelement;

public class BssidModel implements Subelement {

    private BssidState state;
    private BssidController fc;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public BssidModel(BssidState state) {
        this.state = state;
    }

    /**
     * Get the current Bssid subelement state from the model.
     *
     * @return the Bssid subelement state
     */
    public BssidState getState() {
        return this.state;
    }

    /**
     * Set the Bssid subelement state in the model.
     * @param state the Bssid subelement state
     */
    public void setState(BssidState state) {
        this.state = state;
    }


    /**
     * The callback into the Bssid controller used when an asynchronous even occurs.
     *
     * @param fc the Bssid controller in the MVC pattern
     */
    public void setCallback(BssidController fc) {
        this.fc = fc;
    }

    // TODO(dmevans) Implement the encoding in toHexBuffer().
    @Override
    public String toHexBuffer() {
        return null;
    }
}
