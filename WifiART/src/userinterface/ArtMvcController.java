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
    private final ArtMvcView fv; // fv = MVC View
    private final ArtMvcModel fm; // fm = MVC Model

    /** Constructor.
     *
     * @param fv the MVC view
     * @param fm the MVC model
     *
     * */
    public ArtMvcController(ArtMvcView fv, ArtMvcModel fm) {
        this.fm = fm;
        this.fv = fv;

        // Initialize GUI by copying state from model
        this.fv.setViewState(fm.getState());
        this.fm.setCallback(this);

        // TODO(dmevans) Add the listeners to the View here
    }

    /** Events that occur asynchronously in the model call this method e.g. For an animation */
    void modelCallback() {
        fv.setViewState(fm.getState());
    }

    // TODO(dmevans) Define listener classes here
}
