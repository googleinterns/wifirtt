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
    // Constants
    private static final int DEFAULT_LCI_VERSION = 1;
    private static final AltitudeType DEFAULT_ALTITUDE_TYPE = AltitudeType.NO_KNOWN_ALTITUDE;
    private static final MapDatum DEFAULT_MAP_DATUM = MapDatum.WGS84;

    private static final double MAX_LATITUDE = 90;
    private static final double MIN_LATITUDE = -90;
    private static final double MAX_LONGITUDE = 180;
    private static final double MIN_LONGITUDE = -180;
    private static final double MAX_ALTITUDE_MAGNITUDE = Math.pow(2, 21);

    // Parameters

    private int lciVersion;

    private double latitude;
    private double latitudeUncertainty;
    private double longitude;
    private double longitudeUncertainty;

    private double altitude;
    private double altitudeUncertainty;
    private AltitudeType altitudeType; // Meters, Floors, or no altitude provided
    private MapDatum mapDatum; // WGS84, NAD83 (NAVD88), or NAD83 (MLLW).

    /**
     * The Registered Location (RegLoc) Agreement parameter is true if the STA is operating within
     *  a national policy area or an international agreement area near a national border.
     */
    private boolean regLocAgreement;

    /**
     * The Registered Location Dependent STA Enablement (RegLoc DSE) parameter is true if the
     *  enabling STA is enabling the operation of STAs with DSE.
     */
    private boolean regLocDse;

    /**
     * The dependentSta parameter is true if the STA is operating with the enablement
     *  of the enabling STA whose LCI is being reported.
     */
    private boolean dependentSta;

    /**
     * Constructs an LciState, assigning default values when needed.
     */
    public LciState() {
        lciVersion = DEFAULT_LCI_VERSION;
        altitudeType = DEFAULT_ALTITUDE_TYPE;
        mapDatum = DEFAULT_MAP_DATUM;
    }


    // Getters for the parameters.

    /**
     * Gets the LCI version.
     *
     * @return the LCI version being used
     */
    public int getLciVersion() {
        return lciVersion;
    }

    /**
     * Gets the latitude.
     *
     * @return the latitude, in degrees
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets the latitude uncertainty.
     *
     * @return the latitude uncertainty, in degrees
     */
    public double getLatitudeUncertainty() {
        return latitudeUncertainty;
    }

    /**
     * Gets the longitude.
     *
     * @return the longitude, in degrees
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Gets the longitude uncertainty.
     *
     * @return the longitude uncertainty, in degrees
     */
    public double getLongitudeUncertainty() {
        return longitudeUncertainty;
    }

    /**
     * Gets the altitude.
     *
     * @return the altitude, in meters or floors
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * Gets the altitude uncertainty.
     *
     * @return the altitude uncertainty, in meters or floors
     */
    public double getAltitudeUncertainty() {
        return altitudeUncertainty;
    }

    /**
     * Gets the altitude type (meters, floors, or no known altitude).
     *
     * @return the altitude type
     */
    public AltitudeType getAltitudeType() {
        return altitudeType;
    }

    /**
     * Gets the map datum.
     *
     * @return the map datum
     */
    public MapDatum getMapDatum() {
        return mapDatum;
    }

    /**
     * Gets the Registered Location (RegLoc) Agreement parameter, which is true if the STA is
     *  operating within a national policy area or an international agreement area near a national
     *  border.
     *
     * @return the boolean value of the parameter.
     */
    public boolean getRegLocAgreement() {
        return regLocAgreement;
    }

    /**
     * Gets the Registered Location Dependent STA Enablement (RegLoc DSE) parameter, which is true
     *  if the enabling STA is enabling the operation of STAs with DSE.
     *
     * @return the boolean value of the parameter.
     */
    public boolean getRegLocDse() {
        return regLocDse;
    }

    /**
     * Gets the dependentSta parameter, which is true if the STA is operating with the enablement
     *  of the enabling STA whose LCI is being reported.
     *
     * @return the boolean value of the parameter.
     */
    public boolean getDependentSta() {
        return dependentSta;
    }


    // Setters for the parameters

    /**
     * Sets the LCI version.
     *
     * @param lciVersion the LCI version
     */
    public void setLciVersion(int lciVersion) {
        this.lciVersion = lciVersion;
    }

    /**
     * Sets the latitude.
     *
     * @param latitude the latitude, in degrees
     */
    public void setLatitude(double latitude) {
        if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE) {
            throw new NumberFormatException();
        }
        this.latitude = latitude;
    }

    /**
     * Sets the latitude uncertainty.
     *
     * @param latitudeUncertainty the latitude uncertainty, in degrees
     */
    public void setLatitudeUncertainty(double latitudeUncertainty) {
        this.latitudeUncertainty = latitudeUncertainty;
    }

    /**
     * Sets the longitude.
     *
     * @param longitude the longitude, in degrees
     */
    public void setLongitude(double longitude) {
        if (longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE) {
            throw new NumberFormatException();
        }
        this.longitude = longitude;
    }

    /**
     * Sets the longitude uncertainty.
     *
     * @param longitudeUncertainty the longitude uncertainty, in degrees
     */
    public void setLongitudeUncertainty(double longitudeUncertainty) {
        this.longitudeUncertainty = longitudeUncertainty;
    }

    /**
     * Sets the altitude.
     *
     * @param altitude the altitude, in meters or floors
     */
    public void setAltitude(double altitude) {
        if (altitude < -MAX_ALTITUDE_MAGNITUDE || altitude >= MAX_ALTITUDE_MAGNITUDE) {
            throw new NumberFormatException();
        }
        this.altitude = altitude;
    }

    /**
     * Sets the altitude uncertainty.
     *
     * @param altitudeUncertainty the altitude uncertainty, in meters or floors
     */
    public void setAltitudeUncertainty(double altitudeUncertainty) {
        this.altitudeUncertainty = altitudeUncertainty;
    }

    /**
     * Sets the altitude type (meters, floors, or no known altitude).
     *
     * @param altitudeType the altitude type (meters, floors, or no known altitude)
     */
    public void setAltitudeType(AltitudeType altitudeType) {
        this.altitudeType = altitudeType;
    }

    /**
     * Sets the map datum.
     *
     * @param mapDatum the map datum
     */
    public void setMapDatum(MapDatum mapDatum) {
        this.mapDatum = mapDatum;
    }

    /**
     * Sets the Registered Location (RegLoc) Agreement parameter, which is true if the STA is
     *  operating within a national policy area or an international agreement area near a national
     *  border.
     *
     * @param regLocAgreement the boolean value of the parameter.
     */
    public void setRegLocAgreement(boolean regLocAgreement) {
        this.regLocAgreement = regLocAgreement;
    }

    /**
     * Sets the Registered Location Dependent STA Enablement (RegLoc DSE) parameter, which is true
     *  if the enabling STA is enabling the operation of STAs with DSE.
     *
     * @param regLocDse the boolean value of the parameter.
     */
    public void setRegLocDse(boolean regLocDse) {
        this.regLocDse = regLocDse;
    }

    /**
     * Sets the dependentSta parameter, which is true if the STA is operating with the enablement
     *  of the enabling STA whose LCI is being reported.
     *
     * @param dependentSta the boolean value of the parameter.
     */
    public void setDependentSta(boolean dependentSta) {
        this.dependentSta = dependentSta;
    }


}
