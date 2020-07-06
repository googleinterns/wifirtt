package structs;

public class ZState {
    // Parameters
    private int floor;
    private double heightAboveFloor;
    private double heightAboveFloorUncertainty;
    private String locationMovement;

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setHeightAboveFloor(double heightAboveFloor) {
        this.heightAboveFloor = heightAboveFloor;
    }

    public void setHeightAboveFloorUncertainty(double heightAboveFloorUncertainty) {
        this.heightAboveFloorUncertainty = heightAboveFloorUncertainty;
    }

    public void setLocationMovement(String locationMovement) {
        this.locationMovement = locationMovement;
    }
}
