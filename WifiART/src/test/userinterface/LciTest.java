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

import org.junit.jupiter.api.Test;
import structs.AltitudeType;
import structs.LciState;
import structs.MapDatum;
import structs.UsageState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests methods and the encoding for the Usage Rules/Policy subelement.
 */
class LciTest {

    // Extreme values
    private static final double MAX_LATITUDE = 90;
    private static final double MIN_LATITUDE = -90;
    private static final double MAX_LATITUDE_UNCERTAINTY = Math.pow(2.0, 7.0);
    private static final double MIN_LATITUDE_UNCERTAINTY = Math.pow(2.0, -26.0);
    private static final double MAX_LONGITUDE = 180;
    private static final double MIN_LONGITUDE = -180;
    private static final double MAX_LONGITUDE_UNCERTAINTY = Math.pow(2.0, 7.0);
    private static final double MIN_LONGITUDE_UNCERTAINTY = Math.pow(2.0, -26.0);
    private static final double MAX_ALTITUDE = 0x1fffffff / 256.0;
    private static final double MIN_ALTITUDE = 0xe0000000 / 256.0;
    private static final double MAX_ALTITUDE_UNCERTAINTY = Math.pow(2.0, 20.0);
    private static final double MIN_ALTITUDE_UNCERTAINTY = Math.pow(2.0, -9.0);



    // States and expected buffers for testing

    // Default state with false/zero values.
    private static final LciState STATE_DEFAULT = buildLciState(
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        0.0,
        AltitudeType.NO_KNOWN_ALTITUDE,
        MapDatum.WGS84,
        false,
        false,
        false,
        1);
    private static final String BUFFER_DEFAULT = "001000000000000000000000000000000041";

    private static final LciState STATE_WITH_MAX_LAT_LONG_ALT = buildLciState(
        MAX_LATITUDE,
        MAX_LATITUDE_UNCERTAINTY,
        MAX_LONGITUDE,
        MAX_LONGITUDE_UNCERTAINTY,
        MAX_ALTITUDE,
        MAX_ALTITUDE_UNCERTAINTY,
        STATE_DEFAULT.getAltitudeType(),
        STATE_DEFAULT.getMapDatum(),
        STATE_DEFAULT.getRegLocAgreement(),
        STATE_DEFAULT.getRegLocDse(),
        STATE_DEFAULT.getDependentSta(),
        STATE_DEFAULT.getLciVersion()
    );


    private final LciModel model = new LciModel(new LciState());

    /**
     * Constructs an LciState with pre-determined parameter values.
     *
     * @param latitude the latitude, in degrees
     * @param latitudeUncertainty the latitude uncertainty, in degrees
     * @param longitude the longitude, in degrees
     * @param longitudeUncertainty the longitude uncertainty, in degrees
     * @param altitude the altitude
     * @param altitudeUncertainty the altitude uncertainty
     * @param altitudeType the altitude type (meters, floors, or no known altitude)
     * @param mapDatum the map datum
     * @param regLocAgreement true if the STA is operating within a national policy area or an
     *                        international agreement area near a national border.
     * @param regLocDse true if the enabling STA is enabling the operation of STAs with DSE.
     * @param dependentSta true if the STA is operating with the enablement of the enabling STA
     *                     whose LCI is being reported.
     * @param lciVersion the LCI version
     * @return the LciState with the parameter values set.
     */
    private static LciState buildLciState(double latitude, double latitudeUncertainty,
                                            double longitude, double longitudeUncertainty,
                                            double altitude, double altitudeUncertainty,
                                            AltitudeType altitudeType, MapDatum mapDatum,
                                            boolean regLocAgreement, boolean regLocDse,
                                            boolean dependentSta, int lciVersion){
        LciState state = new LciState();
        state.setLatitude(latitude);
        state.setLatitudeUncertainty(latitudeUncertainty);
        state.setLongitude(longitude);
        state.setLongitudeUncertainty(longitudeUncertainty);
        state.setAltitude(altitude);
        state.setAltitudeUncertainty(altitudeUncertainty);
        state.setAltitudeType(altitudeType);
        state.setMapDatum(mapDatum);
        state.setRegLocAgreement(regLocAgreement);
        state.setRegLocDse(regLocDse);
        state.setDependentSta(dependentSta);
        state.setLciVersion(lciVersion);
        return state;
    }

    /**
     * Test the encoding with default values (false, zero).
     */
    @Test
    void testDefaultBuffer() {
        model.setState(STATE_DEFAULT);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_DEFAULT, buffer);
    }

}

