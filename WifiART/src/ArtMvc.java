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

import structs.ArtSystemState;
import userinterface.ArtMvcController;
import userinterface.ArtMvcModel;
import userinterface.ArtMvcView;
import userinterface.BssidController;
import userinterface.LciController;
import userinterface.LcrController;
import userinterface.MapController;
import userinterface.UsageController;
import userinterface.ZController;

public class ArtMvc {

    public static void main(String[] args) {
        // Establish the system state
        final ArtSystemState state = new ArtSystemState();


        // Setup the Control GUI using the MVC coding pattern
        ArtMvcView view = new ArtMvcView(state);
        ArtMvcModel model = new ArtMvcModel(state);
        new ArtMvcController(view, model);

        // Setup controllers for the subelements
        new LciController(view.getLciView(), model.getLciModel());
        new ZController(view.getZView(), model.getZModel());
        new UsageController(view.getUsageView(), model.getUsageModel());
        new BssidController(view.getBssidView(), model.getBssidModel());
        new LcrController(view.getLcrView(), model.getLcrModel());
        new MapController(view.getMapView(), model.getMapModel());

        view.setVisible(true);

    }
}
