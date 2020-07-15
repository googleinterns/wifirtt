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
import structs.UsageState;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the encoding for the Usage Rules/Policy subelement.
 */
class UsageTest {

    private final UsageModel model = new UsageModel(new UsageState());

    /**
     * Test the encoding where retransmission is allowed,
     *  expire time is not present,
     *  and additional STA location information does not exist.
     */
    @Test
    void testBuffer1() {
        UsageState state = new UsageState();
        state.setRetransmissionAllowed(true);
        state.setRetentionExpires(false);
        state.setStaLocationPolicy(false);
        model.setState(state);

        String buffer = model.toHexBuffer();
        boolean isBufferValid = buffer.equalsIgnoreCase("060101");

        assertTrue(isBufferValid);
    }

    /**
     * Test the encoding where retransmission is not allowed,
     *  LCI information expires after 32768 hours,
     *  and additional STA location information exists.
     */
    @Test
    void testBuffer2() {
        UsageState state = new UsageState();
        state.setRetransmissionAllowed(false);
        state.setRetentionExpires(true);
        state.setExpireTime(32768);
        state.setStaLocationPolicy(true);
        model.setState(state);

        String buffer = model.toHexBuffer();
        boolean isBufferValid = buffer.equalsIgnoreCase("0603060080");

        assertTrue(isBufferValid);
    }
}