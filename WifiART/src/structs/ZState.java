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
    private double heightAboveFloorMeters;
    private double heightAboveFloorUncertaintyMeters;

    /**
     * The expectedToMove field specifies whether the STA is expected to change location,
     *  not expected to change location, or if this is unknown.
     */
    private ExpectedToMove expectedToMove;


    /**
     * Get the floor number.
     *
     * @return the floor number.
     */
    public int getFloor()  {
        return floor;
    }

    /**
     * Get the height above the floor.
     *
     * @return the height above the floor (in meters)
     */
    public double getHeightAboveFloorMeters() {
        return heightAboveFloorMeters;
    }

    /**
     * Get the uncertainty for the height above the floor.
     *
     * @return the uncertainty for the height (in meters)
     */
    public double getHeightAboveFloorUncertaintyMeters() {
        return heightAboveFloorUncertaintyMeters;
    }

    /**
     * Gets whether or not the STA is expected to change location, or if this is unknown.
     *
     * @return the enum representing the STA location movement pattern.
     */
    public ExpectedToMove getExpectedToMove() {
        return expectedToMove;
    }

    /**
     * Set the floor number.
     *
     * @param floor the floor number.
     */
    public void setFloor(int floor) {
        this.floor = floor;
    }

    /**
     * Set the height above the floor.
     *
     * @param heightAboveFloorMeters the height above the floor (in meters)
     */
    public void setHeightAboveFloorMeters(double heightAboveFloorMeters) {
        this.heightAboveFloorMeters = heightAboveFloorMeters;
    }

    /**
     * Set the uncertainty for the height above the floor.
     *
     * @param heightAboveFloorUncertaintyMeters the uncertainty for the height (in meters)
     */
    public void setHeightAboveFloorUncertaintyMeters(double heightAboveFloorUncertaintyMeters) {
        this.heightAboveFloorUncertaintyMeters = heightAboveFloorUncertaintyMeters;
    }

    /**
     * Sets whether or not the STA is expected to change location, or if this is unknown.
     *
     * @param expectedToMove the enum representing the STA location movement pattern.
     */
    public void setExpectedToMove(ExpectedToMove expectedToMove) {
        this.expectedToMove = expectedToMove;
    }


}
