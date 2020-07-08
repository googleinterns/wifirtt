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

public class ZState {
    // Parameters
    private int floor;
    private double heightAboveFloor;
    private double heightAboveFloorUncertainty;
    private String locationMovement;

    /**
     * Set the floor number.
     * @param floor the floor number.
     */
    public void setFloor(int floor) {
        this.floor = floor;
    }

    /**
     * Set the height above the floor.
     * @param heightAboveFloor the height above the floor (in meters)
     */
    public void setHeightAboveFloor(double heightAboveFloor) {
        this.heightAboveFloor = heightAboveFloor;
    }

    /**
     * Set the uncertainty for the height above the floor.
     * @param heightAboveFloorUncertainty the uncertainty for the height (in meters)
     */
    public void setHeightAboveFloorUncertainty(double heightAboveFloorUncertainty) {
        this.heightAboveFloorUncertainty = heightAboveFloorUncertainty;
    }

    /**
     * Sets whether or not the STA is expected to change location, or if this is unknown.
     * @param locationMovement the String representing the STA location movement pattern.
     */
    public void setLocationMovement(String locationMovement) {
        this.locationMovement = locationMovement;
    }
}
