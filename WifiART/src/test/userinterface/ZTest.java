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
import structs.ExpectedToMove;
import structs.ZState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests methods and encodings for the Z subelement.
 */
public class ZTest {

    private static final int MAX_FLOOR_NUMBER = 512;
    private static final int MIN_FLOOR_NUMBER = -512;
    private static final double MAX_HEIGHT_ABOVE_FLOOR_METERS = 8388607.0 / 4096.0;
    private static final double MIN_HEIGHT_ABOVE_FLOOR_METERS = -8388607.0 / 4096.0;
    private static final double MAX_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_METERS_OLD = Math.pow(2, 10);
    private static final double MIN_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_METERS_OLD = Math.pow(2, -13);
    private static final double MAX_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_METERS_NEW = Math.pow(2, -2);
    private static final double MIN_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_METERS_NEW = Math.pow(2, -25);


    // Default state with zero values, movement unknown
    private static final ZState STATE_DEFAULT = buildZState(
        0,
        0.0,
        0.0,
        ExpectedToMove.MOVEMENT_UNKNOWN
    );
    private static final String BUFFER_DEFAULT = "0406020000000000";

    // Example state with values in a normal range
    private static final ZState STATE_EXAMPLE = buildZState(
        4,
        2.8,
        0.1,
        ExpectedToMove.NOT_EXPECTED_TO_MOVE
    );
    private static final String BUFFER_EXAMPLE_OLD = "04060001cd2c000e";
    private static final String BUFFER_EXAMPLE_NEW = "04060001cd2c0002";

    // Values set to maximum according to new encoding
    private static final ZState STATE_MAX_VALUES_NEW = buildZState(
        MAX_FLOOR_NUMBER,
        MAX_HEIGHT_ABOVE_FLOOR_METERS,
        MAX_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_METERS_NEW,
        STATE_DEFAULT.getExpectedToMove()
    );
    private static final String BUFFER_MAX_VALUES_NEW = "0406fe7fffff7f01";

    // Values set to maximum according to old encoding
    private static final ZState STATE_MAX_VALUES_OLD = buildZState(
        MAX_FLOOR_NUMBER,
        MAX_HEIGHT_ABOVE_FLOOR_METERS,
        MAX_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_METERS_OLD,
        STATE_DEFAULT.getExpectedToMove()
    );
    private static final String BUFFER_MAX_VALUES_OLD = "0406fe7fffff7f01";

    // Values set to minimum according to new encoding
    private static final ZState STATE_MIN_VALUES_NEW = buildZState(
        MIN_FLOOR_NUMBER,
        MIN_HEIGHT_ABOVE_FLOOR_METERS,
        MIN_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_METERS_NEW,
        STATE_DEFAULT.getExpectedToMove()
    );
    private static final String BUFFER_MIN_VALUES_NEW = "0406068001008018";

    // Values set to minimum according to old encoding
    private static final ZState STATE_MIN_VALUES_OLD = buildZState(
        MIN_FLOOR_NUMBER,
        MIN_HEIGHT_ABOVE_FLOOR_METERS,
        MIN_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_METERS_OLD,
        STATE_DEFAULT.getExpectedToMove()
    );
    private static final String BUFFER_MIN_VALUES_OLD = "0406068001008018";

    // Values set to default, with fixed location.
    private static final ZState STATE_WITH_FIXED_LOCATION = buildZState(
        STATE_DEFAULT.getFloor(),
        STATE_DEFAULT.getHeightAboveFloorMeters(),
        STATE_DEFAULT.getHeightAboveFloorUncertaintyMeters(),
        ExpectedToMove.NOT_EXPECTED_TO_MOVE
    );
    private static final String BUFFER_WITH_FIXED_LOCATION = "0406000000000000";

    // Values set to default, with variable location.
    private static final ZState STATE_WITH_VARIABLE_LOCATION = buildZState(
        STATE_DEFAULT.getFloor(),
        STATE_DEFAULT.getHeightAboveFloorMeters(),
        STATE_DEFAULT.getHeightAboveFloorUncertaintyMeters(),
        ExpectedToMove.EXPECTED_TO_MOVE
    );
    private static final String BUFFER_WITH_VARIABLE_LOCATION = "0406010000000000";


    private final ZModel model = new ZModel(new ZState());


    /**
     * Constructs a ZState with pre-determined parameter values.
     *
     * @param floor the floor number
     * @param heightAboveFloorMeters the height above the floor (in meters)
     * @param heightAboveFloorUncertaintyMeters the uncertainty for the height (in meters)
     * @param expectedToMove the enum representing the STA location movement pattern
     * @return the ZState with the paramter values set.
     */
    private static ZState buildZState(int floor,
                                          double heightAboveFloorMeters,
                                          double heightAboveFloorUncertaintyMeters,
                                          ExpectedToMove expectedToMove) {
        ZState state = new ZState();
        state.setFloor(floor);
        state.setHeightAboveFloorMeters(heightAboveFloorMeters);
        state.setHeightAboveFloorUncertaintyMeters(heightAboveFloorUncertaintyMeters);
        state.setExpectedToMove(expectedToMove);
        return state;
    }

    /**
     * Test setting the the Floor parameter to the maximum value.
     */
    @Test
    void testSetMaxFloor() {
        ZState state = new ZState();

        state.setFloor(MAX_FLOOR_NUMBER);

        assertEquals(MAX_FLOOR_NUMBER, state.getFloor());
    }

    /**
     * Test setting the the Floor parameter to the minimum value.
     */
    @Test
    void testSetMinFloor() {
        ZState state = new ZState();

        state.setFloor(MIN_FLOOR_NUMBER);

        assertEquals(MIN_FLOOR_NUMBER, state.getFloor());
    }

    /**
     * Test setting the the Height Above Floor parameter to the maximum value.
     */
    @Test
    void testSetMaxHeightAboveFloor() {
        ZState state = new ZState();

        state.setHeightAboveFloorMeters(MAX_HEIGHT_ABOVE_FLOOR_METERS);

        assertEquals(MAX_HEIGHT_ABOVE_FLOOR_METERS, state.getHeightAboveFloorMeters());
    }

    /**
     * Test setting the the Height Above Floor parameter to the minimum value.
     */
    @Test
    void testSetMinHeightAboveFloor() {
        ZState state = new ZState();

        state.setHeightAboveFloorMeters(MIN_HEIGHT_ABOVE_FLOOR_METERS);

        assertEquals(MIN_HEIGHT_ABOVE_FLOOR_METERS, state.getHeightAboveFloorMeters());
    }

    /**
     * Test setting the the Height Above Floor Uncertainty parameter to the maximum value.
     */
    @Test
    void testSetMaxHeightAboveFloorUncertainty() {
        ZState state = new ZState();

        state.setHeightAboveFloorUncertaintyMeters(MAX_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_METERS_OLD);

        assertEquals(MAX_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_METERS_OLD, state.getHeightAboveFloorUncertaintyMeters());
    }

    /**
     * Test setting the the Height Above Floor Uncertainty parameter to the minimum value.
     */
    @Test
    void testSetMinHeightAboveFloorUncertainty() {
        ZState state = new ZState();

        state.setHeightAboveFloorUncertaintyMeters(MIN_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_METERS_NEW);

        assertEquals(MIN_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_METERS_NEW, state.getHeightAboveFloorUncertaintyMeters());
    }

    /**
     * Test setting the Expected To Move parameter to represent a fixed location.
     */
    @Test
    void testSetFixedLocation() {
        ZState state = new ZState();

        state.setExpectedToMove(ExpectedToMove.NOT_EXPECTED_TO_MOVE);

        assertEquals(ExpectedToMove.NOT_EXPECTED_TO_MOVE, state.getExpectedToMove());
    }

    /**
     * Test setting the Expected To Move parameter to represent a variable location.
     */
    @Test
    void testSetVariableLocation() {
        ZState state = new ZState();

        state.setExpectedToMove(ExpectedToMove.EXPECTED_TO_MOVE);

        assertEquals(ExpectedToMove.EXPECTED_TO_MOVE, state.getExpectedToMove());
    }

    /**
     * Test setting the Expected To Move parameter to represent an unknown movement pattern.
     */
    @Test
    void testSetMovementUnknown() {
        ZState state = new ZState();

        state.setExpectedToMove(ExpectedToMove.MOVEMENT_UNKNOWN);

        assertEquals(ExpectedToMove.MOVEMENT_UNKNOWN, state.getExpectedToMove());
    }


    /**
     * Test the encoding with a default state. Old Android version.
     */
    @Test
    void testBufferDefaultOld() {
        model.setState(STATE_DEFAULT);
        model.setIfAndroidVersionNew(false); // Old Android version

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_DEFAULT, buffer);
    }

    /**
     * Test the encoding with a default state. New Android version.
     */
    @Test
    void testBufferDefaultNew() {
        model.setState(STATE_DEFAULT);
        model.setIfAndroidVersionNew(true); // New Android version

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_DEFAULT, buffer);
    }

    /**
     * Test the encoding with a stationary STA, floor number = 4, height of 2.8m above the floor,
     *  with height uncertainty of 0.1m. New Android version.
     */
    @Test
    void testExampleBufferNew() {
        model.setState(STATE_EXAMPLE);
        model.setIfAndroidVersionNew(true); // New Android version

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_EXAMPLE_NEW, buffer);
    }

    /**
     * Test the encoding with a stationary STA, floor number = 4, height of 2.8m above the floor,
     *  with height uncertainty of 0.1m. Old Android version.
     */
    @Test
    void testExampleBufferOld() {
        model.setState(STATE_EXAMPLE);
        model.setIfAndroidVersionNew(false); // Old Android version

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_EXAMPLE_OLD, buffer);
    }

    /**
     * Test the encoding with maximum values. New Android version.
     */
    @Test
    void testBufferWithMaxValuesNew() {
        model.setState(STATE_MAX_VALUES_NEW);
        model.setIfAndroidVersionNew(true); // New Android version

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_MAX_VALUES_NEW, buffer);
    }

    /**
     * Test the encoding with maximum values. Old Android version.
     */
    @Test
    void testBufferWithMaxValuesOld() {
        model.setState(STATE_MAX_VALUES_OLD);
        model.setIfAndroidVersionNew(false); // Old Android version

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_MAX_VALUES_OLD, buffer);
    }

    /**
     * Test the encoding with minimum values. New Android version.
     */
    @Test
    void testBufferWithMinValuesNew() {
        model.setState(STATE_MIN_VALUES_NEW);
        model.setIfAndroidVersionNew(true); // New Android version

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_MIN_VALUES_NEW, buffer);
    }

    /**
     * Test the encoding with minimum values. Old Android version.
     */
    @Test
    void testBufferWithMinValuesOld() {
        model.setState(STATE_MIN_VALUES_OLD);
        model.setIfAndroidVersionNew(false); // Old Android version

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_MIN_VALUES_OLD, buffer);
    }

    /**
     * Test the encoding with fixed location. New Android version.
     */
    @Test
    void testBufferWithFixedLocationNew() {
        model.setState(STATE_WITH_FIXED_LOCATION);
        model.setIfAndroidVersionNew(true); // New Android version

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_FIXED_LOCATION, buffer);
    }

    /**
     * Test the encoding with fixed location. Old Android version.
     */
    @Test
    void testBufferWithFixedLocationOld() {
        model.setState(STATE_WITH_FIXED_LOCATION);
        model.setIfAndroidVersionNew(false); // Old Android version

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_FIXED_LOCATION, buffer);
    }

    /**
     * Test the encoding with variable location. New Android version.
     */
    @Test
    void testBufferWithVariableLocationNew() {
        model.setState(STATE_WITH_VARIABLE_LOCATION);
        model.setIfAndroidVersionNew(true); // New Android version

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_VARIABLE_LOCATION, buffer);
    }

    /**
     * Test the encoding with variable location. Old Android version.
     */
    @Test
    void testBufferWithVariableLocationOld() {
        model.setState(STATE_WITH_VARIABLE_LOCATION);
        model.setIfAndroidVersionNew(false); // Old Android version

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_VARIABLE_LOCATION, buffer);
    }

}
