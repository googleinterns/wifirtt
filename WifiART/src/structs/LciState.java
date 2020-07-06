package structs;

public class LciState {
    // Parameters
    private double latitude;
    private double latitudeUncertainty;
    private double longitude;
    private double longitudeUncertainty;

    private double altitude;
    private double altitudeUncertainty;
    private String altitudeUnits;
    private String altitudeDatum;

    private boolean regLocAgreement;
    private boolean regLocDse;
    private boolean dependentSta;

    public LciState() {
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLatitudeUncertainty(double latitudeUncertainty) {
        this.latitudeUncertainty = latitudeUncertainty;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLongitudeUncertainty(double longitudeUncertainty) {
        this.longitudeUncertainty = longitudeUncertainty;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public void setAltitudeUncertainty(double altitudeUncertainty) {
        this.altitudeUncertainty = altitudeUncertainty;
    }

    public void setAltitudeUnits(String altitudeUnits) {
        this.altitudeUnits = altitudeUnits;
    }

    public void setAltitudeDatum(String altitudeDatum) {
        this.altitudeDatum = altitudeDatum;
    }

    public void setRegLocAgreement(boolean regLocAgreement) {
        this.regLocAgreement = regLocAgreement;
    }

    public void setRegLocDse(boolean regLocDse) {
        this.regLocDse = regLocDse;
    }

    public void setDependentSta(boolean dependentSta) {
        this.dependentSta = dependentSta;
    }
}
