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

    // Parameters
    private LciState lciState;
    private ZState zState;
    private UsageState usageState;
    private BssidState bssidState;
    private LcrState lcrState;
    private MapState mapState;

    /** The includedSubelements map specifies whether or not each subelement is to be included
       in the output. */
    private HashMap<SubelementName, Boolean> includedSubelements;
    private String inputFileName;
    private String inputDir;
    private String outputFileName;
    private String outputDir;

    private boolean readable;
    private boolean androidVersionAtLeastS;

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

    /**
     * Gets the subelement state for the LCI subelement.
     *
     * @return the LCI subelement state
     */
    public LciState getLciState() {
        return lciState;
    }

    /**
     * Gets the subelement state for the Z subelement.
     *
     * @return the Z subelement state
     */
    public ZState getZState() {
        return zState;
    }

    /**
     * Gets the subelement state for the Usage Rules/Policy subelement.
     *
     * @return the Usage Rules/Policy subelement state
     */
    public UsageState getUsageState() {
        return usageState;
    }

    /**
     * Gets the subelement state for the BSSID List subelement.
     *
     * @return the BSSID List subelement state
     */
    public BssidState getBssidState() {
        return bssidState;
    }

    /**
     * Gets the subelement state for the Location Civic subelement.
     *
     * @return the Location Civic subelement state
     */
    public LcrState getLcrState() {
        return lcrState;
    }

    /**
     * Gets the subelement state for the Map Image subelement.
     *
     * @return the Map Image subelement state
     */
    public MapState getMapState() {
        return mapState;
    }


    // Getters for which subelements are included

    /**
     * Gets whether or not a given subelement is to be included in the output.
     *
     * @param subelementName the subelement being inquired about
     * @return whether or not the given subelement should be included in the output
     */
    public boolean isSubelementIncluded(SubelementName subelementName) {
        return includedSubelements.get(subelementName);
    }

    /**
     * Gets whether or not the LCI subelement is to be included in the output.
     *
     * @return the boolean value of the parameter
     */
    public boolean isLciIncluded() {
        return includedSubelements.get(SubelementName.LCI);
    }

    /**
     * Gets whether or not the Z subelement is to be included in the output.
     *
     * @return the boolean value of the parameter
     */
    public boolean isZIncluded() {
        return includedSubelements.get(SubelementName.Z);
    }

    /**
     * Gets whether or not the Usage Rules/Policy subelement is to be included in the output.
     *
     * @return the boolean value of the parameter
     */
    public boolean isUsageIncluded() {
        return includedSubelements.get(SubelementName.USAGE);
    }

    /**
     * Gets whether or not the BSSID List subelement is to be included in the output.
     *
     * @return the boolean value of the parameter
     */
    public boolean isBssidIncluded() {
        return includedSubelements.get(SubelementName.BSSID);
    }

    /**
     * Gets whether or not the Location Civic subelement is to be included in the output.
     *
     * @return the boolean value of the parameter
     */
    public boolean isLcrIncluded() {
        return includedSubelements.get(SubelementName.LCR);
    }

    /**
     * Gets whether or not the Map Image subelement is to be included in the output.
     *
     * @return the boolean value of the parameter
     */
    public boolean isMapIncluded() {
        return includedSubelements.get(SubelementName.MAP);
    }

    /**
     * Gets whether or not the output should be displayed in a readable format.
     *
     * @return whether or not the buffer display should be readable
     */
    public boolean isReadable() {
        return readable;
    }

    // Setters for the subelement states

    /**
     * Sets the subelement state for the LCI subelement.
     *
     * @param lciState the LCI subelement state
     */
    public void setLciState(LciState lciState) {
        this.lciState = lciState;
    }

    /**
     * Sets the subelement state for the Z subelement.
     *
     * @param zState the Z subelement state
     */
    public void setZState(ZState zState) {
        this.zState = zState;
    }

    /**
     * Sets the subelement state for the Usage Rules/Policy subelement.
     *
     * @param usageState the Usage Rules/Policy subelement state
     */
    public void setUsageState(UsageState usageState) {
        this.usageState = usageState;
    }

    /**
     * Sets the subelement state for the BSSID List subelement.
     *
     * @param bssidState the BSSID List subelement state
     */
    public void setBssidState(BssidState bssidState) {
        this.bssidState = bssidState;
    }

    /**
     * Sets the subelement state for the Location Civic subelement.
     *
     * @param lcrState the Location Civic subelement state
     */
    public void setLcrState(LcrState lcrState) {
        this.lcrState = lcrState;
    }

    /**
     * Sets the subelement state for the Map Image subelement.
     *
     * @param mapState the Map Image subelement state
     */
    public void setMapState(MapState mapState) {
        this.mapState = mapState;
    }

    // Setters for which subelements are included

    /**
     * Set whether or not the LCI subelement is included in the output.
     *
     * @param included the boolean value of the parameter
     */
    public void setLciIncluded(boolean included) {
        includedSubelements.put(SubelementName.LCI, included);
    }

    /**
     * Set whether or not the Z subelement is included in the output.
     *
     * @param included the boolean value of the parameter
     */
    public void setZIncluded(boolean included) {
        includedSubelements.put(SubelementName.Z, included);
    }

    /**
     * Set whether or not the Usage Rules/Policy subelement is included in the output.
     *
     * @param included the boolean value of the parameter
     */
    public void setUsageIncluded(boolean included) {
        includedSubelements.put(SubelementName.USAGE, included);
    }

    /**
     * Set whether or not the BSSID List subelement is included in the output.
     *
     * @param included the boolean value of the parameter
     */
    public void setBssidIncluded(boolean included) {
        includedSubelements.put(SubelementName.BSSID, included);
    }

    /**
     * Set whether or not the Location Civic subelement is included in the output.
     *
     * @param included the boolean value of the parameter
     */
    public void setLcrIncluded(boolean included) {
        includedSubelements.put(SubelementName.LCR, included);
    }

    /**
     * Set whether or not the Map Image subelement is included in the output.
     *
     * @param included the boolean value of the parameter
     */
    public void setMapIncluded(boolean included) {
        includedSubelements.put(SubelementName.MAP, included);
    }

    /**
     * Set the input file name.
     *
     * @param inputFileName the input file name
     */
    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    /**
     * Set the input file directory.
     *
     * @param inputDir the input file directory
     */
    public void setInputDir(String inputDir) {
        this.inputDir = inputDir;
    }

    /**
     * Set the output file name.
     *
     * @param outputFileName the output file name
     */
    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    /**
     * Set the output file directory.
     *
     * @param outputDir the output file directory
     */
    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    /**
     * Set whether or not the output should be displayed in a readable format.
     *
     * @param readable whether or not the buffer should be readable
     */
    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    /**
     * Set whether or not the android version is S or later.
     *
     * @param androidVersionAtLeastS whether or not Android version is used is at least S
     */
    public void setAndroidVersionAtLeastS(boolean androidVersionAtLeastS) {
        this.androidVersionAtLeastS = androidVersionAtLeastS;
    }

}
