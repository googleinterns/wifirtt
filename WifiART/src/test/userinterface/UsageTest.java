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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests methods and the encoding for the Usage Rules/Policy subelement.
 */
class UsageTest {

    // Expire Time values
    private static final int MAX_EXPIRE_TIME_HOURS = 65535;
    private static final int MIN_EXPIRE_TIME_HOURS = 0;


    // States and expected buffers for testing

    // Default state with false/null values.
    private static final UsageState STATE_DEFAULT = buildUsageState(
        false,
        false,
        null,
        false);
    private static final String BUFFER_DEFAULT = "060100";

    // Retransmission Allowed parameter set to true
    private static final UsageState STATE_WITH_RETRANSMISSION_ALLOWED_TRUE = buildUsageState(
        true,
        STATE_DEFAULT.getRetentionExpires(),
        STATE_DEFAULT.getExpireTimeHours(),
        STATE_DEFAULT.getStaLocationPolicy());
    private static final String BUFFER_WITH_RETRANSMISSION_ALLOWED_TRUE = "060101";

    // Retention Expires parameter set to true, medium expire time
    private static final UsageState STATE_WITH_MEDIUM_EXPIRE_TIME = buildUsageState(
        STATE_DEFAULT.getRetransmissionAllowed(),
        true,
        32768,
        STATE_DEFAULT.getStaLocationPolicy());
    private static final String BUFFER_WITH_MEDIUM_EXPIRE_TIME = "0603020080";

    // Retention Expires parameter set to true, minimum expire time
    private static final UsageState STATE_WITH_MINIMUM_EXPIRE_TIME = buildUsageState(
        STATE_DEFAULT.getRetransmissionAllowed(),
        true,
        MIN_EXPIRE_TIME_HOURS,
        STATE_DEFAULT.getStaLocationPolicy());
    private static final String BUFFER_WITH_MINIMUM_EXPIRE_TIME = "0603020000";

    // Retention Expires parameter set to true, maximum expire time
    private static final UsageState STATE_WITH_MAXIMUM_EXPIRE_TIME = buildUsageState(
        STATE_DEFAULT.getRetransmissionAllowed(),
        true,
        MAX_EXPIRE_TIME_HOURS,
        STATE_DEFAULT.getStaLocationPolicy());
    private static final String BUFFER_WITH_MAXIMUM_EXPIRE_TIME = "060302ffff";

    // STA Location Policy parameter set to true
    private static final UsageState STATE_WITH_STA_LOCATION_POLICY_TRUE = buildUsageState(
        STATE_DEFAULT.getRetransmissionAllowed(),
        STATE_DEFAULT.getRetentionExpires(),
        STATE_DEFAULT.getExpireTimeHours(),
        true);
    private static final String BUFFER_WITH_STA_LOCATION_POLICY_TRUE = "060104";


    private final UsageModel model = new UsageModel(new UsageState());

    /**
     * Constructs a UsageState with pre-determined parameter values.
     *
     * @param retransmissionAllowed True if retransmission of the LCI information is allowed
     * @param retentionExpires True if LCI information is allowed to expire after an amount of time
     * @param expireTimeHours Number of hours after which retention expires (can be null)
     * @param staLocationPolicy True if additional STA location information exists
     */
    private static UsageState buildUsageState(boolean retransmissionAllowed, boolean retentionExpires,
                                       Integer expireTimeHours, boolean staLocationPolicy) {
        UsageState state = new UsageState();
        state.setRetransmissionAllowed(retransmissionAllowed);
        state.setRetentionExpires(retentionExpires);
        if (expireTimeHours != null) {
            state.setExpireTimeHours(expireTimeHours);
        }
        state.setStaLocationPolicy(staLocationPolicy);
        return state;
    }

    /**
     * Test setting the Retransmission Allowed parameter to true.
     */
    @Test
    void testSetRetransmissionAllowedTrue() {
        UsageState state = new UsageState();

        state.setRetransmissionAllowed(true);

        assertTrue(state.getRetransmissionAllowed());
    }

    /**
     * Test setting the Retransmission Allowed parameter to false.
     */
    @Test
    void testSetRetransmissionAllowedFalse() {
        UsageState state = new UsageState();

        state.setRetransmissionAllowed(false);

        assertFalse(state.getRetransmissionAllowed());
    }

    /**
     * Test setting the Retention Expires parameter to true.
     */
    @Test
    void testSetRetentionExpiresTrue() {
        UsageState state = new UsageState();

        state.setRetentionExpires(true);

        assertTrue(state.getRetentionExpires());
    }

    /**
     * Test setting the Retention Expires parameter to false.
     */
    @Test
    void testSetRetentionExpiresFalse() {
        UsageState state = new UsageState();

        state.setRetentionExpires(false);

        assertFalse(state.getRetentionExpires());
    }

    /**
     * Test setting the STA Location Policy parameter to true.
     */
    @Test
    void testSetStaLocationPolicyTrue() {
        UsageState state = new UsageState();

        state.setStaLocationPolicy(true);

        assertTrue(state.getStaLocationPolicy());
    }

    /**
     * Test setting the STA Location Policy parameter to false.
     */
    @Test
    void testSetStaLocationPolicyFalse() {
        UsageState state = new UsageState();

        state.setStaLocationPolicy(false);

        assertFalse(state.getStaLocationPolicy());
    }

    /**
     * Test setting the Expire Time parameter to the maximum value.
     */
    @Test
    void testSetMaxExpireTime() {
        UsageState state = new UsageState();

        state.setExpireTimeHours(MAX_EXPIRE_TIME_HOURS);

        assertEquals(MAX_EXPIRE_TIME_HOURS, state.getExpireTimeHours());
    }

    /**
     * Test setting the Expire Time parameter to the minimum value.
     */
    @Test
    void testSetMinExpireTime() {
        UsageState state = new UsageState();

        state.setExpireTimeHours(MIN_EXPIRE_TIME_HOURS);

        assertEquals(MIN_EXPIRE_TIME_HOURS, state.getExpireTimeHours());
    }

    /**
     * Test setting the Expire Time parameter to above the maximum value.
     */
    @Test
    void testSetExpireTimeTooLarge() {
        UsageState state = new UsageState();

        assertThrows(NumberFormatException.class,
            () -> state.setExpireTimeHours(MAX_EXPIRE_TIME_HOURS + 10));
    }

    /**
     * Test setting the Expire Time parameter to below the minimum value.
     */
    @Test
    void testSetExpireTimeTooSmall() {
        UsageState state = new UsageState();

        assertThrows(NumberFormatException.class,
            () -> state.setExpireTimeHours(MIN_EXPIRE_TIME_HOURS - 10));
    }

    /**
     * Test that values are null after calling the UsageState constructor.
     */
    @Test
    void testUsageStateConstructor() {
        UsageState state = new UsageState();

        // All null values (false for booleans, 0 for integer)
        assertFalse(state.getRetransmissionAllowed());
        assertFalse(state.getRetentionExpires());
        assertEquals(0, state.getExpireTimeHours());
        assertFalse(state.getStaLocationPolicy());
    }


    /**
     * Test the encoding with default values (false, null).
     */
    @Test
    void testDefaultBuffer() {
        model.setState(STATE_DEFAULT);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_DEFAULT, buffer);
    }

    /**
     * Test the encoding where Retransmission Allowed is true.
     */
    @Test
    void testBufferWithRetransmissionAllowedTrue() {
        model.setState(STATE_WITH_RETRANSMISSION_ALLOWED_TRUE);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_RETRANSMISSION_ALLOWED_TRUE, buffer);
    }

    /**
     * Test the encoding where Retransmission Allowed is true.
     */
    @Test
    void testBufferWithMediumExpireTime() {
        model.setState(STATE_WITH_MEDIUM_EXPIRE_TIME);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_MEDIUM_EXPIRE_TIME, buffer);
    }

    /**
     * Test the encoding where Retransmission Allowed is true.
     */
    @Test
    void testBufferWithMaximumExpireTime() {
        model.setState(STATE_WITH_MAXIMUM_EXPIRE_TIME);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_MAXIMUM_EXPIRE_TIME, buffer);
    }

    /**
     * Test the encoding where Retransmission Allowed is true.
     */
    @Test
    void testBufferWithMinimumExpireTime() {
        model.setState(STATE_WITH_MINIMUM_EXPIRE_TIME);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_MINIMUM_EXPIRE_TIME, buffer);
    }

    /**
     * Test the encoding where the STA Location Policy parameter is true.
     */
    @Test
    void testBufferWithStaLocationPolicyTrue() {
        model.setState(STATE_WITH_STA_LOCATION_POLICY_TRUE);

        String buffer = model.toHexBuffer();

        assertEquals(BUFFER_WITH_STA_LOCATION_POLICY_TRUE, buffer);
    }
}
