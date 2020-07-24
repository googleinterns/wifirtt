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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZTest {

    private final ZModel model = new ZModel(new ZState());

    /**
     * Test the encoding with a stationary STA, floor number = 4, height of 2.8m above the floor,
     *  with height uncertainty of 0.1m. New Android version.
     */
    @Test
    void testBufferNew() {
        ZState state = new ZState();
        state.setExpectedToMove(ExpectedToMove.NOT_EXPECTED_TO_MOVE);
        state.setFloor(4);
        state.setHeightAboveFloorMeters(2.8);
        state.setHeightAboveFloorUncertaintyMeters(0.1);
        model.setState(state);
        model.setIfAndroidVersionNew(true); // New Android version

        String buffer = model.toHexBuffer();
        String correctBuffer = "04060001cd2c0002";

        boolean isBufferValid = buffer.equalsIgnoreCase(correctBuffer);

        assertTrue(isBufferValid);
    }

    /**
     * Test the encoding with a stationary STA, floor number = 4, height of 2.8m above the floor,
     *  with height uncertainty of 0.1m. Old Android version.
     */
    @Test
    void testBufferOld() {
        ZState state = new ZState();
        state.setExpectedToMove(ExpectedToMove.NOT_EXPECTED_TO_MOVE);
        state.setFloor(4);
        state.setHeightAboveFloorMeters(2.8);
        state.setHeightAboveFloorUncertaintyMeters(0.1);
        model.setState(state);
        model.setIfAndroidVersionNew(false); // Old Android version

        String buffer = model.toHexBuffer();
        String correctBuffer = "04060001cd2c000e";

        boolean isBufferValid = buffer.equalsIgnoreCase(correctBuffer);

        assertTrue(isBufferValid);
    }

}
