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
import structs.SubelementName;

import java.util.HashMap;

public class ArtMvcModel {
    private ArtSystemState state;
    private ArtMvcController controller;

    // Models for the subelements
    private final LciModel lciModel;
    private final ZModel zModel;
    private final UsageModel usageModel;
    private final BssidModel bssidModel;
    private final LcrModel lcrModel;
    private final MapModel mapModel;

    // Buffer strings for the subelements
    private String lciSubelementBuffer;
    private String zSubelementBuffer;
    private String usageSubelementBuffer;
    private String bssidSubelementBuffer;
    private String lcrSubelementBuffer;
    private String mapSubelementBuffer;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public ArtMvcModel(ArtSystemState state) {
        this.state = state;
        lciModel = new LciModel(state.getLciState());
        zModel = new ZModel(state.getZState());
        usageModel = new UsageModel(state.getUsageState());
        bssidModel = new BssidModel(state.getBssidState());
        lcrModel = new LcrModel(state.getLcrState());
        mapModel = new MapModel(state.getMapState());
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
     * Sets the state contained in the model.
     *
     * @param state the system state
     */
    public void setState(ArtSystemState state) {
        this.state = state;
        lciModel.setState(state.getLciState());
        zModel.setState(state.getZState());
        usageModel.setState(state.getUsageState());
        bssidModel.setState(state.getBssidState());
        lcrModel.setState(state.getLcrState());
        mapModel.setState(state.getMapState());
    }

    // Getters for the sub-models

    /**
     * Get the model for the LCI subelement.
     *
     * @return the LCI subelement model
     */
    public LciModel getLciModel() {
        return lciModel;
    }

    /**
     * Get the model for the Z subelement.
     *
     * @return the Z subelement model
     */
    public ZModel getZModel() {
        return zModel;
    }

    /**
     * Get the model for the Usage Rules/Policy subelement.
     *
     * @return the Usage Rules/Policy subelement model
     */
    public UsageModel getUsageModel() {
        return usageModel;
    }

    /**
     * Get the model for the BSSID List subelement.
     *
     * @return the BSSID List subelement model
     */
    public BssidModel getBssidModel() {
        return bssidModel;
    }

    /**
     * Get the model for the Location Civic subelement.
     *
     * @return the Location Civic subelement model
     */
    public LcrModel getLcrModel() {
        return lcrModel;
    }

    /**
     * Get the model for the Map Image subelement.
     *
     * @return the Map Image subelement model
     */
    public MapModel getMapModel() {
        return mapModel;
    }

    /**
     * Recalculates and revalidates the LCI subelement buffer.
     */
    public void updateLciSubelementBuffer() {
        if (state.isLciIncluded()) {
            lciSubelementBuffer = lciModel.toHexBuffer();
        }
    }

    /**
     * Recalculates and revalidates the Z subelement buffer.
     */
    public void updateZSubelementBuffer() {
        if (state.isZIncluded()) {
            zSubelementBuffer = zModel.toHexBuffer();
        }
    }

    /**
     * Recalculates and revalidates the Usage Rules/Policy subelement buffer.
     */
    public void updateUsageSubelementBuffer() {
        if (state.isUsageIncluded()) {
            usageSubelementBuffer = usageModel.toHexBuffer();
        }
    }

    /**
     * Recalculates and revalidates the BSSID List subelement buffer.
     */
    public void updateBssidSubelementBuffer() {
        if (state.isBssidIncluded()) {
            bssidSubelementBuffer = bssidModel.toHexBuffer();
        }
    }

    /**
     * Recalculates and revalidates the Location Civic subelement buffer.
     */
    public void updateLcrSubelementBuffer() {
        if (state.isLcrIncluded()) {
            lcrSubelementBuffer = lcrModel.toHexBuffer();
        }
    }

    /**
     * Recalculates and revalidates the Map Image subelement buffer.
     */
    public void updateMapSubelementBuffer() {
        if (state.isMapIncluded()) {
            mapSubelementBuffer = mapModel.toHexBuffer();
        }
    }

    /**
     * Gets a list of the buffers for included LCI subelements.
     */
    public HashMap<SubelementName, String> getLciSubelementBuffersList() {
        HashMap<SubelementName, String> buffer = new HashMap<>();
        if (state.isLciIncluded()) {
            updateLciSubelementBuffer();
            buffer.put(SubelementName.LCI, lciSubelementBuffer);
        }
        if (state.isZIncluded()) {
            updateZSubelementBuffer();
            buffer.put(SubelementName.Z, zSubelementBuffer);
        }
        if (state.isUsageIncluded()) {
            updateUsageSubelementBuffer();
            buffer.put(SubelementName.USAGE, usageSubelementBuffer);
        }
        if (state.isBssidIncluded()) {
            updateBssidSubelementBuffer();
            buffer.put(SubelementName.BSSID, bssidSubelementBuffer);
        }
        return buffer;
    }

    /**
     * Gets a list of the buffers for included LCR subelements.
     */
    public HashMap<SubelementName, String> getLcrSubelementBuffersList() {
        HashMap<SubelementName, String> buffer = new HashMap<>();
        if (state.isLcrIncluded()) {
            updateLcrSubelementBuffer();
            buffer.put(SubelementName.LCR, lcrSubelementBuffer);
        }
        if (state.isMapIncluded()) {
            updateMapSubelementBuffer();
            buffer.put(SubelementName.MAP, mapSubelementBuffer);
        }
        return buffer;
    }

    /**
     * The callback into the ArtMvcController used when an asynchronous even occurs.
     *
     * @param controller the Controller in the MVC pattern
     */
    public void setCallback(ArtMvcController controller) {
        this.controller = controller;
    }

}
