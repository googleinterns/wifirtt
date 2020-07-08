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

public class UsageState {
    // Parameters
    private boolean retransmissionAllowed;
    private boolean retentionExpires;
    private int expireTime;
    private boolean staLocationPolicy;

    /**
     * Set whether or not retransmission of the LCI information is allowed.
     * @param retransmissionAllowed the boolean value of the parameter.
     */
    public void setRetransmissionAllowed(boolean retransmissionAllowed) {
        this.retransmissionAllowed = retransmissionAllowed;
    }

    /**
     * Set whether or not LCI information is allowed to expire after an amount of time.
     * @param retentionExpires the boolean value of the parameter.
     */
    public void setRetentionExpires(boolean retentionExpires) {
        this.retentionExpires = retentionExpires;
    }

    /**
     * Set the amount of time after which LCI information expires.
     * @param expireTime the expire time (in hours)
     */
    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * Set whether or not additional STA location information exists.
     * @param staLocationPolicy the boolean value of the parameter.
     */
    public void setStaLocationPolicy(boolean staLocationPolicy) {
        this.staLocationPolicy = staLocationPolicy;
    }
}