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

import structs.ZState;
import structs.Subelement;

public class ZModel implements Subelement {

    private ZState state;
    private ZController fc;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public ZModel(ZState state) {
        this.state = state;
    }

    /**
     * Get the current Z subelement state from the model.
     *
     * @return the Z subelement state
     */
    public ZState getState() {
        return this.state;
    }

    /**
     * Set the Z subelement state in the model.
     *
     * @param state the Z subelement state
     */
    public void setState(ZState state) {
        this.state = state;
    }


    /**
     * The callback into the Z controller used when an asynchronous even occurs.
     *
     * @param fc the Z controller in the MVC pattern
     */
    public void setCallback(ZController fc) {
        this.fc = fc;
    }


    // TODO(dmevans) Implement the encoding in toHexBuffer().
    @Override
    public String toHexBuffer() {
        return null;
    }
}
