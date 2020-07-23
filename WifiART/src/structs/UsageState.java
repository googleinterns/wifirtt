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

/**
 * Contains the parameters for the Usage Rules/Policy subelement.
 */
public class UsageState {

    // Constants
    private static final int MAX_EXPIRE_TIME_HOURS = 65535;
    private static final int MIN_EXPIRE_TIME_HOURS = 0;


    // Parameters

    /**
     * The retransmissionAllowed field is true if retransmission of the LCI information is allowed.
     */
    private boolean retransmissionAllowed;

    /**
     * The retentionExpires field is true if LCI information is allowed to expire
     * after an amount of time.
     */
    private boolean retentionExpires;

    /** Number of hours after which retention expires. */
    private int expireTimeHours;

    /** The staLocationPolicy field is true if additional STA location information exists. */
    private boolean staLocationPolicy;


    /**
     * Constructs a UsageState, without setting values.
     */
    public UsageState() {}


    // Getter methods for the parameters

    /**
     * Get whether or not retransmission of the LCI information is allowed.
     *
     * @return The boolean value of the parameter
     */
    public boolean getRetransmissionAllowed() {
        return retransmissionAllowed;
    }

    /**
     * Get whether or not LCI information is allowed to expire after an amount of time.
     *
     * @return The boolean value of the parameter.
     */
    public boolean getRetentionExpires() {
        return retentionExpires;
    }

    /**
     * Get the amount of time after which LCI information expires.
     *
     * @return The amount of time, in hours.
     */
    public int getExpireTimeHours() {
        return expireTimeHours;
    }

    /**
     * Get whether or not additional STA location information exists.
     *
     * @return the boolean value of the parameter.
     */
    public boolean getStaLocationPolicy() {
        return staLocationPolicy;
    }


    // Setter methods for the parameters

    /**
     * Set whether or not retransmission of the LCI information is allowed.
     *
     * @param retransmissionAllowed the boolean value of the parameter.
     */
    public void setRetransmissionAllowed(boolean retransmissionAllowed) {
        this.retransmissionAllowed = retransmissionAllowed;
    }

    /**
     * Set whether or not LCI information is allowed to expire after an amount of time.
     *
     * @param retentionExpires the boolean value of the parameter.
     */
    public void setRetentionExpires(boolean retentionExpires) {
        this.retentionExpires = retentionExpires;
    }

    /**
     * Set the amount of time after which LCI information expires.
     *
     * @param expireTimeHours the expire time, in hours.
     */
    public void setExpireTimeHours(int expireTimeHours) throws NumberFormatException {
        if (expireTimeHours < MIN_EXPIRE_TIME_HOURS || expireTimeHours > MAX_EXPIRE_TIME_HOURS) {
            throw new NumberFormatException();
        }
        this.expireTimeHours = expireTimeHours;
    }

    /**
     * Set whether or not additional STA location information exists.
     *
     * @param staLocationPolicy the boolean value of the parameter.
     */
    public void setStaLocationPolicy(boolean staLocationPolicy) {
        this.staLocationPolicy = staLocationPolicy;
    }

}
