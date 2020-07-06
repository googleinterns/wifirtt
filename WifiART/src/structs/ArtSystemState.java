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

package structs;

import java.util.HashMap;

/**
 * The system state enclosing the LCI/LCR parameters.
 */
public class ArtSystemState {

    // Enum identifying the different subelements.
    private enum SubelementName {
        LCI,
        Z,
        USAGE,
        BSSID,
        LCR,
        MAP
    }

    // Parameters
    private LciState lciState;
    private ZState zState;
    private UsageState usageState;
    private BssidState bssidState;
    private LcrState lcrState;
    private MapState mapState;
    private HashMap<SubelementName, Boolean> includedSubelements;
    private String inputFileName;
    private String inputDir;
    private String outputFileName;
    private String outputDir;

    /** Constructor. */
    public ArtSystemState() {
        init();
    }

    /** Initializes the state variables. */
    public void init() {
        lciState = new LciState();
        zState = new ZState();
        usageState = new UsageState();
        bssidState = new BssidState();

        lcrState = new LcrState();
        mapState = new MapState();

        includedSubelements = new HashMap<>();
        includedSubelements.put(SubelementName.LCI, false);
        includedSubelements.put(SubelementName.Z, false);
        includedSubelements.put(SubelementName.USAGE, false);
        includedSubelements.put(SubelementName.BSSID, false);
        includedSubelements.put(SubelementName.LCR, false);
        includedSubelements.put(SubelementName.MAP, false);

        inputFileName = "";
        inputDir = "";
        outputFileName = "";
        outputDir = "";
    }

    // Getters for the subelement states
    public LciState getLciState() {
        return lciState;
    }
    public ZState getZState() {
        return zState;
    }
    public UsageState getUsageState() {
        return usageState;
    }
    public BssidState getBssidState() {
        return bssidState;
    }
    public LcrState getLcrState() {
        return lcrState;
    }
    public MapState getMapState() {
        return mapState;
    }

    // Setters for the subelement states
    public void setLciState(LciState lciState) {
        this.lciState = lciState;
    }
    public void setZState(ZState zState) {
        this.zState = zState;
    }
    public void setUsageState(UsageState usageState) {
        this.usageState = usageState;
    }
    public void setBssidState(BssidState bssidState) {
        this.bssidState = bssidState;
    }
    public void setLcrState(LcrState lcrState) {
        this.lcrState = lcrState;
    }
    public void setMapState(MapState mapState) {
        this.mapState = mapState;
    }


    // Setters for which subelements are included
    public void setLciIncluded(boolean included) {
        includedSubelements.put(SubelementName.LCI, included);
    }
    public void setZIncluded(boolean included) {
        includedSubelements.put(SubelementName.Z, included);
    }
    public void setUsageIncluded(boolean included) {
        includedSubelements.put(SubelementName.USAGE, included);
    }
    public void setBssidIncluded(boolean included) {
        includedSubelements.put(SubelementName.BSSID, included);
    }
    public void setLcrIncluded(boolean included) {
        includedSubelements.put(SubelementName.LCR, included);
    }
    public void setMapIncluded(boolean included) {
        includedSubelements.put(SubelementName.MAP, included);
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }
    public void setInputDir(String inputDir) {
        this.inputDir = inputDir;
    }
    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }
}
