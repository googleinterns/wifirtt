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

import java.util.HashSet;
import java.util.Set;

/**
 * The state representation for the BSSID List subelement.
 */
public class BssidState {

    private static final int BSSID_LENGTH = 6;
    private static final int BSSID_STRING_LENGTH = 17;
    private static final int MAX_BSSID_LIST_SIZE = 42;
    private static final int MAX_FOR_MAX_BSSID_INDICATOR = 255;
    private static final int MIN_FOR_MAX_BSSID_INDICATOR = 0;

    private final Set<byte[]> bssidList;
    private int maxBssidIndicator;

    public BssidState() {
        bssidList = new HashSet<>();
    }

    /**
     * Adds a BSSID to the list.
     *
     * @param bssidString the BSSID to be added
     * @throws IllegalArgumentException if the input is not a correctly formatted BSSID
     * @throws IndexOutOfBoundsException if the BSSID list cannot be expanded any more
     */
    public void addBssid(String bssidString) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (bssidList.size() == MAX_BSSID_LIST_SIZE) {
            throw new IndexOutOfBoundsException();
        }
        if (bssidString.length() != BSSID_STRING_LENGTH) {
            throw new IllegalArgumentException();
        }
        // Third character of every three characters must be a colon.
        for (int i = 2; i < BSSID_STRING_LENGTH; i += 3) {
            if (bssidString.charAt(i) != ':') {
                throw new IllegalArgumentException();
            }
        }
        try {
            byte[] bssid = getBssidFromString(bssidString);
            bssidList.add(bssid);
        } catch (NumberFormatException exception) {
            // Throw an exception if bytes could not be read from the String.
            throw new IllegalArgumentException();
        }
    }

    /**
     * Removes a BSSID from the list.
     *
     * @param bssid the BSSID to be removed
     */
    public void removeBssid(String bssid) {
        bssidList.remove(bssid);
    }

    /**
     * Replaces a BSSID in the list with a new one.
     *
     * @param oldBssid the BSSID to be replaced.
     * @param newBssid the new BSSID to replace the old one.
     * @throws IllegalArgumentException if the new BSSID is incorrectly formatted
     */
    public void editBssid(String oldBssid, String newBssid) throws IllegalArgumentException {
        removeBssid(oldBssid);
        addBssid(newBssid);
    }

    /**
     * Sets the maximum number of BSSs which can share the same antenna connector.
     *
     * @param maxBssidIndicator the maximum number of BSSs which can share the same antenna connector.
     * @throws NumberFormatException if the value is outside the acceptable range.
     */
    public void setMaxBssidIndicator(int maxBssidIndicator) throws NumberFormatException {
        if (maxBssidIndicator > MAX_FOR_MAX_BSSID_INDICATOR
            || maxBssidIndicator < MIN_FOR_MAX_BSSID_INDICATOR) {
            throw new NumberFormatException();
        }
        this.maxBssidIndicator = maxBssidIndicator;
    }

    /**
     * Get the list of BSSIDs.
     *
     * @return the list of BSSIDs.
     */
    public Set<byte[]> getBssidList() {
        return bssidList;
    }

    /**
     * Get the maximum number of BSSs which can share the same antenna connector.
     *
     * @return the maximum number of BSSs which can share the same antenna connector.
     */
    public int getMaxBssidIndicator() {
        return maxBssidIndicator;
    }

    private byte[] getBssidFromString(String bssid) throws NumberFormatException {
        String bssidLower = bssid.toLowerCase(); // Avoid capitalization issues
        byte[] result = new byte[BSSID_LENGTH];
        for (int i = 0; i < BSSID_LENGTH; i++) {
            // Parse the first 2 chars of every triplet as a hex byte (avoiding colons).
            System.out.println(bssidLower.substring(i * 3, i * 3 + 2));
            result[i] = (byte) (Integer.parseInt(bssidLower.substring(i * 3, i * 3 + 2), 16));
        }
        return result;
    }
}
