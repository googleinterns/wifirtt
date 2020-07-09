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

public class BssidState {

    private final Set<String> bssidList;

    // TODO(dmevans): Add MaxBSSID Indicator field.

    public BssidState() {
        bssidList = new HashSet<>();
    }

    /**
     * Adds a BSSID to the list.
     * @param bssid the BSSID to be added
     */
    public void addBssid(String bssid) {

        // TODO(dmevans) Input validation.

        bssidList.add(bssid);
    }

    /**
     * Removes a BSSID from the list.
     * @param bssid the BSSID to be removed
     */
    public void deleteBssid(String bssid) {
        bssidList.remove(bssid);
    }

    /**
     * Replaces a BSSID in the list with a new one.
     * @param oldBssid the BSSID to be replaced.
     * @param newBssid the new BSSID to replace the old one.
     */
    public void editBssid(String oldBssid, String newBssid) {
        // TODO(dmevans) Input validation.

        bssidList.remove(oldBssid);
        bssidList.add(newBssid);
    }

    // TODO(dmevans) Add getter methods.

}
