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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import structs.AltitudeType;
import structs.LciState;
import structs.MapDatum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests methods and the encoding for the LCI subelement.
 */
class LciTest {

    // Extreme values
    private static final double MAX_LATITUDE = 90.0;
    private static final double MIN_LATITUDE = -90.0;
    private static final double MAX_LATITUDE_UNCERTAINTY = Math.pow(2.0, 7.0);
    private static final double MIN_LATITUDE_UNCERTAINTY = Math.pow(2.0, -26.0);
    private static final double MAX_LONGITUDE = 180.0;
    private static final double MIN_LONGITUDE = -180.0;
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

    // State representing the Sydney Opera House.
    private static final LciState STATE_SYDNEY_OPERA_HOUSE_EXAMPLE = buildLciState(
        -33.8570095,
        0.0007105,
        151.2152005,
        0.0007055,
        11.2,
        33.7,
        AltitudeType.ALTITUDE_IN_METERS,
        MapDatum.WGS84,
        false,
        false,
        false,
        1
    );
    private static final String BUFFER_SYDNEY_OPERA_HOUSE_EXAMPLE = "001052834d12efd2b08b9b4bf1cc2c000041";

    // Maximum values
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
    private static final String BUFFER_WITH_MAX_LAT_LONG_ALT = "0010010000002d010000005a10fcffff7f41";

    // Minimum values
    private static final LciState STATE_WITH_MIN_LAT_LONG_ALT = buildLciState(
        MIN_LATITUDE,
        MIN_LATITUDE_UNCERTAINTY,
        MIN_LONGITUDE,
        MIN_LONGITUDE_UNCERTAINTY,
        MIN_ALTITUDE,
        MIN_ALTITUDE_UNCERTAINTY,
        STATE_DEFAULT.getAltitudeType(),
        STATE_DEFAULT.getMapDatum(),
        STATE_DEFAULT.getRegLocAgreement(),
        STATE_DEFAULT.getRegLocDse(),
        STATE_DEFAULT.getDependentSta(),
        STATE_DEFAULT.getLciVersion()
    );
    private static final String BUFFER_WITH_MIN_LAT_LONG_ALT = "001022000000d322000000a6e00100008041";

    // Altitude Type is set to Altitude In Floors.
    private static final LciState STATE_WITH_ALTITUDE_IN_FLOORS = buildLciState(
        STATE_DEFAULT.getLatitude(),
        STATE_DEFAULT.getLatitudeUncertainty(),
        STATE_DEFAULT.getLongitude(),
        STATE_DEFAULT.getLongitudeUncertainty(),
        STATE_DEFAULT.getAltitude(),
        STATE_DEFAULT.getAltitudeUncertainty(),
        AltitudeType.ALTITUDE_IN_FLOORS,
        STATE_DEFAULT.getMapDatum(),
        STATE_DEFAULT.getRegLocAgreement(),
        STATE_DEFAULT.getRegLocDse(),
        STATE_DEFAULT.getDependentSta(),
        STATE_DEFAULT.getLciVersion()
    );
    private static final String BUFFER_WITH_ALTITUDE_IN_FLOORS = "001000000000000000000000020000000041";

    // Altitude Type is set to Altitude In Meters.
    private static final LciState STATE_WITH_ALTITUDE_IN_METERS = buildLciState(
        STATE_DEFAULT.getLatitude(),
        STATE_DEFAULT.getLatitudeUncertainty(),
        STATE_DEFAULT.getLongitude(),
        STATE_DEFAULT.getLongitudeUncertainty(),
        STATE_DEFAULT.getAltitude(),
        STATE_DEFAULT.getAltitudeUncertainty(),
        AltitudeType.ALTITUDE_IN_METERS,
        STATE_DEFAULT.getMapDatum(),
        STATE_DEFAULT.getRegLocAgreement(),
        STATE_DEFAULT.getRegLocDse(),
        STATE_DEFAULT.getDependentSta(),
        STATE_DEFAULT.getLciVersion()
    );
    private static final String BUFFER_WITH_ALTITUDE_IN_METERS = "001000000000000000000000010000000041";

    // Map Datum is set to NAD83 (NAVD88).
    private static final LciState STATE_WITH_NAD83_NAVD88 = buildLciState(
        STATE_DEFAULT.getLatitude(),
        STATE_DEFAULT.getLatitudeUncertainty(),
        STATE_DEFAULT.getLongitude(),
        STATE_DEFAULT.getLongitudeUncertainty(),
        STATE_DEFAULT.getAltitude(),
        STATE_DEFAULT.getAltitudeUncertainty(),
        STATE_DEFAULT.getAltitudeType(),
        MapDatum.NAD83_NAVD88,
        STATE_DEFAULT.getRegLocAgreement(),
        STATE_DEFAULT.getRegLocDse(),
        STATE_DEFAULT.getDependentSta(),
        STATE_DEFAULT.getLciVersion()
    );
    private static final String BUFFER_WITH_NAD83_NAVD88 = "001000000000000000000000000000000042";

    // Map Datum is set to NAD83 (MLLW).
    private static final LciState STATE_WITH_NAD83_MLLW = buildLciState(
        STATE_DEFAULT.getLatitude(),
        STATE_DEFAULT.getLatitudeUncertainty(),
        STATE_DEFAULT.getLongitude(),
        STATE_DEFAULT.getLongitudeUncertainty(),
        STATE_DEFAULT.getAltitude(),
        STATE_DEFAULT.getAltitudeUncertainty(),
        STATE_DEFAULT.getAltitudeType(),
        MapDatum.NAD83_MLLW,
        STATE_DEFAULT.getRegLocAgreement(),
        STATE_DEFAULT.getRegLocDse(),
        STATE_DEFAULT.getDependentSta(),
        STATE_DEFAULT.getLciVersion()
    );
    private static final String BUFFER_WITH_NAD83_MLLW = "001000000000000000000000000000000043";

    // RegLoc Agreement parameter is true.
    private static final LciState STATE_WITH_REG_LOC_AGREEMENT_TRUE = buildLciState(
        STATE_DEFAULT.getLatitude(),
        STATE_DEFAULT.getLatitudeUncertainty(),
        STATE_DEFAULT.getLongitude(),
        STATE_DEFAULT.getLongitudeUncertainty(),
        STATE_DEFAULT.getAltitude(),
        STATE_DEFAULT.getAltitudeUncertainty(),
        STATE_DEFAULT.getAltitudeType(),
        STATE_DEFAULT.getMapDatum(),
        true,
        STATE_DEFAULT.getRegLocDse(),
        STATE_DEFAULT.getDependentSta(),
        STATE_DEFAULT.getLciVersion()
    );
    private static final String BUFFER_WITH_REG_LOC_AGREEMENT_TRUE = "001000000000000000000000000000000049";

    // RegLoc DSE parameter is true.
    private static final LciState STATE_WITH_REG_LOC_DSE_TRUE = buildLciState(
        STATE_DEFAULT.getLatitude(),
        STATE_DEFAULT.getLatitudeUncertainty(),
        STATE_DEFAULT.getLongitude(),
        STATE_DEFAULT.getLongitudeUncertainty(),
        STATE_DEFAULT.getAltitude(),
        STATE_DEFAULT.getAltitudeUncertainty(),
        STATE_DEFAULT.getAltitudeType(),
        STATE_DEFAULT.getMapDatum(),
        STATE_DEFAULT.getRegLocAgreement(),
        true,
        STATE_DEFAULT.getDependentSta(),
        STATE_DEFAULT.getLciVersion()
    );
    private static final String BUFFER_WITH_REG_LOC_DSE_TRUE = "001000000000000000000000000000000051";

    // Dependent STA parameter is true.
    private static final LciState STATE_WITH_DEPENDENT_STA_TRUE = buildLciState(
        STATE_DEFAULT.getLatitude(),
        STATE_DEFAULT.getLatitudeUncertainty(),
        STATE_DEFAULT.getLongitude(),
        STATE_DEFAULT.getLongitudeUncertainty(),
        STATE_DEFAULT.getAltitude(),
        STATE_DEFAULT.getAltitudeUncertainty(),
        STATE_DEFAULT.getAltitudeType(),
        STATE_DEFAULT.getMapDatum(),
        STATE_DEFAULT.getRegLocAgreement(),
        STATE_DEFAULT.getRegLocDse(),
        true,
        STATE_DEFAULT.getLciVersion()
    );
    private static final String BUFFER_WITH_DEPENDENT_STA_TRUE = "001000000000000000000000000000000061";


    private static LciModel model = new LciModel(new LciState());

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
     *                        international agreement area near a national border
     * @param regLocDse true if the enabling STA is enabling the operation of STAs with DSE
     * @param dependentSta true if the STA is operating with the enablement of the enabling STA
     *                     whose LCI is being reported
     * @param lciVersion the LCI version
     * @return the LciState with the parameter values set
     */
    private static LciState buildLciState(double latitude, double latitudeUncertainty,
        double longitude, double longitudeUncertainty,
        double altitude, double altitudeUncertainty,
        AltitudeType altitudeType, MapDatum mapDatum,
        boolean regLocAgreement, boolean regLocDse,
        boolean dependentSta, int lciVersion) {
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

    @BeforeAll
    static void setController() {
        model.setCallback(
            new LciController(new LciView(), model) {
                @Override
                public void updateState() {}
            });
    }

    /**
     * Test setting the latitude to the minimum value.
     */
    @Test
    void testSetMinLatitude() {
        LciState state = new LciState();

        state.setLatitude(MIN_LATITUDE);

        assertEquals(MIN_LATITUDE, state.getLatitude());
    }

    /**
     * Test setting the latitude to the maximum value.
     */
    @Test
    void testSetMaxLatitude() {
        LciState state = new LciState();

        state.setLatitude(MAX_LATITUDE);

        assertEquals(MAX_LATITUDE, state.getLatitude());
    }

    /**
     * Test setting the latitude to below the minimum value.
     */
    @Test
    void testSetTooSmallLatitude() {
        LciState state = new LciState();

        assertThrows(NumberFormatException.class,
            () -> state.setLatitude(MIN_LATITUDE - 1));
    }

    /**
     * Test setting the latitude to above the maximum value.
     */
    @Test
    void testSetTooLargeLatitude() {
        LciState state = new LciState();

        assertThrows(NumberFormatException.class,
            () -> state.setLatitude(MAX_LATITUDE + 1));
    }

    /**
     * Test setting the latitude uncertainty to the minimum value.
     */
    @Test
    void testSetMinLatitudeUncertainty() {
        LciState state = new LciState();

        state.setLatitudeUncertainty(MIN_LATITUDE_UNCERTAINTY);

        assertEquals(MIN_LATITUDE_UNCERTAINTY, state.getLatitudeUncertainty());
    }

    /**
     * Test setting the latitude uncertainty to the maximum value.
     */
    @Test
    void testSetMaxLatitudeUncertainty() {
        LciState state = new LciState();

        state.setLatitudeUncertainty(MAX_LATITUDE_UNCERTAINTY);

        assertEquals(MAX_LATITUDE_UNCERTAINTY, state.getLatitudeUncertainty());
    }

    /**
     * Test setting the longitude to the minimum value.
     */
    @Test
    void testSetMinLongitude() {
        LciState state = new LciState();

        state.setLongitude(MIN_LONGITUDE);

        assertEquals(MIN_LONGITUDE, state.getLongitude());
    }

    /**
     * Test setting the longitude to the maximum value.
     */
    @Test
    void testSetMaxLongitude() {
        LciState state = new LciState();

        state.setLongitude(MAX_LONGITUDE);

        assertEquals(MAX_LONGITUDE, state.getLongitude());
    }

    /**
     * Test setting the longitude to below the minimum value.
     */
    @Test
    void testSetTooSmallLongitude() {
        LciState state = new LciState();

        assertThrows(NumberFormatException.class,
            () -> state.setLongitude(MIN_LONGITUDE - 1));
    }

    /**
     * Test setting the longitude to above the maximum value.
     */
    @Test
    void testSetTooLargeLongitude() {
        LciState state = new LciState();

        assertThrows(NumberFormatException.class,
            () -> state.setLongitude(MAX_LONGITUDE + 1));
    }

    /**
     * Test setting the longitude uncertainty to the minimum value.
     */
    @Test
    void testSetMinLongitudeUncertainty() {
        LciState state = new LciState();

        state.setLongitudeUncertainty(MIN_LONGITUDE_UNCERTAINTY);

        assertEquals(MIN_LONGITUDE_UNCERTAINTY, state.getLongitudeUncertainty());
    }

    /**
     * Test setting the longitude uncertainty to the maximum value.
     */
    @Test
    void testSetMaxLongitudeUncertainty() {
        LciState state = new LciState();

        state.setLongitudeUncertainty(MAX_LONGITUDE_UNCERTAINTY);

        assertEquals(MAX_LONGITUDE_UNCERTAINTY, state.getLongitudeUncertainty());
    }

    /**
     * Test setting the altitude to the minimum value.
     */
    @Test
    void testSetMinAltitude() {
        LciState state = new LciState();

        state.setAltitude(MIN_ALTITUDE);

        assertEquals(MIN_ALTITUDE, state.getAltitude());
    }

    /**
     * Test setting the altitude to the maximum value.
     */
    @Test
    void testSetMaxAltitude() {
        LciState state = new LciState();

        state.setAltitude(MAX_ALTITUDE);

        assertEquals(MAX_ALTITUDE, state.getAltitude());
    }

    /**
     * Test setting the altitude to below the minimum value.
     */
    @Test
    void testSetTooSmallAltitude() {
        LciState state = new LciState();

        assertThrows(NumberFormatException.class,
            () -> state.setAltitude(MIN_ALTITUDE - 1));
    }

    /**
     * Test setting the altitude to above the maximum value.
     */
    @Test
    void testSetTooLargeAltitude() {
        LciState state = new LciState();

        assertThrows(NumberFormatException.class,
            () -> state.setAltitude(MAX_ALTITUDE + 1));
    }

    /**
     * Test setting the altitude uncertainty to the minimum value.
     */
    @Test
    void testSetMinAltitudeUncertainty() {
        LciState state = new LciState();

        state.setAltitudeUncertainty(MIN_ALTITUDE_UNCERTAINTY);

        assertEquals(MIN_ALTITUDE_UNCERTAINTY, state.getAltitudeUncertainty());
    }

    /**
     * Test setting the altitude uncertainty to the maximum value.
     */
    @Test
    void testSetMaxAltitudeUncertainty() {
        LciState state = new LciState();

        state.setAltitudeUncertainty(MAX_ALTITUDE_UNCERTAINTY);

        assertEquals(MAX_ALTITUDE_UNCERTAINTY, state.getAltitudeUncertainty());
    }

    /**
     * Test setting the Altitude Type to meters.
     */
    @Test
    void testSetAltitudeInMeters() {
        LciState state = new LciState();

        state.setAltitudeType(AltitudeType.ALTITUDE_IN_METERS);

        assertEquals(AltitudeType.ALTITUDE_IN_METERS, state.getAltitudeType());
    }

    /**
     * Test setting the Altitude Type to floors.
     */
    @Test
    void testSetAltitudeInFloors() {
        LciState state = new LciState();

        state.setAltitudeType(AltitudeType.ALTITUDE_IN_FLOORS);

        assertEquals(AltitudeType.ALTITUDE_IN_FLOORS, state.getAltitudeType());
    }

    /**
     * Test setting the Altitude Type to unknown.
     */
    @Test
    void testSetAltitudeUnknown() {
        LciState state = new LciState();

        state.setAltitudeType(AltitudeType.NO_KNOWN_ALTITUDE);

        assertEquals(AltitudeType.NO_KNOWN_ALTITUDE, state.getAltitudeType());
    }

    /**
     * Test setting the Map Datum to WGS84.
     */
    @Test
    void testSetDatumWgs84() {
        LciState state = new LciState();

        state.setMapDatum(MapDatum.WGS84);

        assertEquals(MapDatum.WGS84, state.getMapDatum());
    }

    /**
     * Test setting the Map Datum to NAD83 (NAVD88).
     */
    @Test
    void testSetDatumNad83Navd88() {
        LciState state = new LciState();

        state.setMapDatum(MapDatum.NAD83_NAVD88);

        assertEquals(MapDatum.NAD83_NAVD88, state.getMapDatum());
    }

    /**
     * Test setting the Map Datum to NAD83 (MLLW).
     */
    @Test
    void testSetDatumNad83Mllw() {
        LciState state = new LciState();

        state.setMapDatum(MapDatum.NAD83_MLLW);

        assertEquals(MapDatum.NAD83_MLLW, state.getMapDatum());
    }

    /**
     * Test setting the RegLoc Agreement parameter to true.
     */
    @Test
    void testSetRegLocAgreementTrue() {
        LciState state = new LciState();

        state.setRegLocAgreement(true);

        assertTrue(state.getRegLocAgreement());
    }

    /**
     * Test setting the RegLoc Agreement parameter to false.
     */
    @Test
    void testSetRegLocAgreementFalse() {
        LciState state = new LciState();

        state.setRegLocAgreement(false);

        assertFalse(state.getRegLocAgreement());
    }

    /**
     * Test setting the RegLoc DSE parameter to true.
     */
    @Test
    void testSetRegLocDseTrue() {
        LciState state = new LciState();

        state.setRegLocDse(true);

        assertTrue(state.getRegLocDse());
    }

    /**
     * Test setting the RegLoc DSE parameter to false.
     */
    @Test
    void testSetRegLocDseFalse() {
        LciState state = new LciState();

        state.setRegLocDse(false);

        assertFalse(state.getRegLocDse());
    }

    /**
     * Test setting the Dependent STA parameter to true.
     */
    @Test
    void testSetDependentStaTrue() {
        LciState state = new LciState();

        state.setDependentSta(true);

        assertTrue(state.getDependentSta());
    }

    /**
     * Test setting the Dependent STA parameter to false.
     */
    @Test
    void testSetDependentStaFalse() {
        LciState state = new LciState();

        state.setDependentSta(false);

        assertFalse(state.getDependentSta());
    }

    /**
     * Test setting the LCI version.
     */
    @Test
    void testSetLciVersion() {
        LciState state = new LciState();

        state.setLciVersion(1);

        assertEquals(1, state.getLciVersion());
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

    /**
     * Test the encoding with the Sydney Opera House example.
     */
    @Test
    void testBufferSydneyOperaHouseExample() {
        model.setState(STATE_SYDNEY_OPERA_HOUSE_EXAMPLE);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_SYDNEY_OPERA_HOUSE_EXAMPLE, buffer);
    }

    /**
     * Test the encoding with maximum values.
     */
    @Test
    void testBufferWithMaxLatLongAlt() {
        model.setState(STATE_WITH_MAX_LAT_LONG_ALT);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_MAX_LAT_LONG_ALT, buffer);
    }

    /**
     * Test the encoding with minimum values.
     */
    @Test
    void testBufferWithMinLatLongAlt() {
        model.setState(STATE_WITH_MIN_LAT_LONG_ALT);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_MIN_LAT_LONG_ALT, buffer);
    }

    /**
     * Test the encoding with the altitude in floors.
     */
    @Test
    void testBufferWithAltitudeInFloors() {
        model.setState(STATE_WITH_ALTITUDE_IN_FLOORS);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_ALTITUDE_IN_FLOORS, buffer);
    }

    /**
     * Test the encoding with altitude in meters.
     */
    @Test
    void testBufferWithAltitudeInMeters() {
        model.setState(STATE_WITH_ALTITUDE_IN_METERS);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_ALTITUDE_IN_METERS, buffer);
    }

    /**
     * Test the encoding with the NAD83 (NAVD88) datum.
     */
    @Test
    void testBufferWithNad83Navd88() {
        model.setState(STATE_WITH_NAD83_NAVD88);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_NAD83_NAVD88, buffer);
    }

    /**
     * Test the encoding with the NAD83 (MLLW) datum.
     */
    @Test
    void testBufferWithNad83Mllw() {
        model.setState(STATE_WITH_NAD83_MLLW);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_NAD83_MLLW, buffer);
    }

    /**
     * Test the encoding with the RegLoc Agreement parameter set to true.
     */
    @Test
    void testBufferWithRegLocAgreementTrue() {
        model.setState(STATE_WITH_REG_LOC_AGREEMENT_TRUE);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_REG_LOC_AGREEMENT_TRUE, buffer);
    }

    /**
     * Test the encoding with the RegLoc DSE parameter set to true.
     */
    @Test
    void testBufferWithRegLocDseTrue() {
        model.setState(STATE_WITH_REG_LOC_DSE_TRUE);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_REG_LOC_DSE_TRUE, buffer);
    }

    /**
     * Test the encoding with the Dependent STA parameter set to true.
     */
    @Test
    void testBufferWithDependentStaTrue() {
        model.setState(STATE_WITH_DEPENDENT_STA_TRUE);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_DEPENDENT_STA_TRUE, buffer);
    }

}
