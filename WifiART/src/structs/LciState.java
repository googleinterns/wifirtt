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

public class LciState {
    // Parameters
    private double latitude;
    private double latitudeUncertainty;
    private double longitude;
    private double longitudeUncertainty;

    private double altitude;
    private double altitudeUncertainty;
    private String altitudeUnits; // "Meters" or "Floors"
    private String altitudeDatum; // "WGS84", "NAD83 (NAVD88)", or "NAD83 (MLLW)".

    /* The regLocAgreement parameter is true if the STA is operating within a national
       policy area or an international agreement area near a national border. */
    private boolean regLocAgreement;
    /* The regLocDse parameter is true if the enabling STA is enabling the operation
       of STAs with DSE. */
    private boolean regLocDse;
    /* The dependentSta parameter is true if the STA is operating with the enablement
       of the enabling STA whose LCI is being reported. */
    private boolean dependentSta;

    public LciState() {
    }

    // TODO(dmevans) Add getter methods.

    /**
     * Sets the latitude.
     * @param latitude the latitude, in degrees
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    /**
     * Sets the latitude uncertainty
     * @param latitudeUncertainty the latitude uncertainty, in degrees
     */
    public void setLatitudeUncertainty(double latitudeUncertainty) {
        this.latitudeUncertainty = latitudeUncertainty;
    }

    /**
     * Sets the longitude
     * @param longitude the longitude, in degrees
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Sets the longitude uncertainty
     * @param longitudeUncertainty the longitude uncertainty, in degrees
     */
    public void setLongitudeUncertainty(double longitudeUncertainty) {
        this.longitudeUncertainty = longitudeUncertainty;
    }

    /**
     * Sets the altitude.
     * @param altitude the altitude, in meters or floors
     */
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    /**
     * Sets the altitude uncertainty.
     * @param altitudeUncertainty the altitude uncertainty, in meters or floors
     */
    public void setAltitudeUncertainty(double altitudeUncertainty) {
        this.altitudeUncertainty = altitudeUncertainty;
    }

    /**
     * Sets the altitude units (meters or floors).
     * @param altitudeUnits the altitude units ("Meters" or "Floors")
     */
    public void setAltitudeUnits(String altitudeUnits) {
        this.altitudeUnits = altitudeUnits;
    }

    /**
     * Sets the altitude datum.
     * @param altitudeDatum the map datum ("WGS84", "NAD83 (NAVD88)", or "NAD83 (MLLW)")
     */
    public void setAltitudeDatum(String altitudeDatum) {
        this.altitudeDatum = altitudeDatum;
    }

    /**
     * Sets the regLocAgreement parameter, which is true if the STA is operating within a national
     *  policy area or an international agreement area near a national border.
     * @param regLocAgreement the boolean value of the parameter.
     */
    public void setRegLocAgreement(boolean regLocAgreement) {
        this.regLocAgreement = regLocAgreement;
    }

    /**
     * Sets the regLocDse parameter, which is true if the enabling STA is enabling the operation
     *  of STAs with DSE.
     * @param regLocDse the boolean value of the parameter.
     */
    public void setRegLocDse(boolean regLocDse) {
        this.regLocDse = regLocDse;
    }

    /**
     * Sets the dependentSta parameter, which is true if the STA is operating with the enablement
     *  of the enabling STA whose LCI is being reported.
     * @param dependentSta
     */
    public void setDependentSta(boolean dependentSta) {
        this.dependentSta = dependentSta;
    }
}
