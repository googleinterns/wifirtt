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
import structs.Subelement;

public class LciModel implements Subelement {

    private LciState state;
    private LciController fc;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public LciModel(LciState state) {
        this.state = state;
    }

    /**
     * Get the current LCI subelement state from the model.
     *
     * @return the LCI subelement state
     */
    public LciState getState() {
        return this.state;
    }

    /**
     * Set the LCI subelement state in the model.
     * @param state the LCI subelement state
     */
    public void setState(LciState state) {
        this.state = state;
    }


    /**
     * The callback into the LCI controller used when an asynchronous even occurs.
     *
     * @param fc the LCI controller in the MVC pattern
     */
    public void setCallback(LciController fc) {
        this.fc = fc;
    }



    @Override
    public String toHexBuffer() {
        return null;
    }
}
