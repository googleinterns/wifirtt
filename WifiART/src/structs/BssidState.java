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

import java.util.ArrayList;
import java.util.List;

public class BssidState {

    private List<String> bssidList;

    public BssidState() {
        bssidList = new ArrayList<>();
    }

    /**
     * Adds a BSSID to the list.
     * @param bssid the BSSID to be added
     */
    public void addBssid(String bssid) {
        bssidList.add(bssid);
    }

    /**
     * Removes a BSSID from the list.
     * @param index the index of the BSSID to be removed
     */
    public void deleteBssid(int index) {
        bssidList.remove(index);
    }

    /**
     * Replaces a BSSID in the list with a new one.
     * @param index the index of the BSSID to be replaced.
     * @param newBssid the new BSSID to replace the old one.
     */
    public void editBssid(int index, String newBssid) {
        bssidList.set(index, newBssid);
    }

}
